package com.kcylog.web.monitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.common.MqMessage;
import com.kcylog.system.domain.SysProject;
import com.kcylog.system.domain.ViewFqProject;
import com.kcylog.system.domain.ViewFqProjectLog;
import com.kcylog.system.service.ISysProjectService;
import com.kcylog.system.service.IViewFqProjectLogService;
import com.kcylog.system.service.IViewFqProjectService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class MqConsume {
    @Autowired
    private IViewFqProjectService viewFqProjectService;

    @Autowired
    private IViewFqProjectLogService viewFqProjectLogService;

    @Autowired
    private ISysProjectService sysProjectService;
    /**
     * 监听一个简单的队列，队列不存在时候会创建
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "${spring.rabbitmq.queue}"))
    public void consumerSimpleMessage(Message message, Channel channel) throws IOException {
        try {
            // 手动确认消息消费成功
            // 通过Message对象解析消息
            String messageStr = new String(message.getBody());
            Gson gson = new Gson();
            MqMessage mqMessage = gson.fromJson(messageStr, MqMessage.class);

            //存储日志
            Date nowTime = DateUtils.getNowDate();
            ViewFqProjectLog viewFqProjectLog = new ViewFqProjectLog();
            viewFqProjectLog.setOperateTime(nowTime);
            viewFqProjectLog.setProjectCode(mqMessage.getProjectId());
            viewFqProjectLog.setOperate(mqMessage.getOpType());
            ObjectMapper mapper = new ObjectMapper();
            //获取视图数据
            ViewFqProject viewFqProject = viewFqProjectService.selectViewFqProjectByProjectCode(Long.parseLong(mqMessage.getProjectId()));
//            String viewFqProjectJson = mapper.writeValueAsString(viewFqProject);
            //数据初始化
            SysProject sysProject = new SysProject();
            sysProject.setProjectNameAlias(viewFqProject.getProjectName());
            sysProject.setProjectNum(viewFqProject.getProjectCode());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if (viewFqProject.getProjectTypeName() != null){
                sysProject.setProjectType(viewFqProject.getProjectTypeName());
            }
            if (viewFqProject.getRegisterTime() != null){
                String dateString = viewFqProject.getRegisterTime().format(formatter);
                sysProject.setRegisterTime(dateString);
            }
            if (viewFqProject.getCreateUserName() != null){
                sysProject.setReceptionist(viewFqProject.getCreateUserName());
            }
            if (viewFqProject.getChargeItem() != null){
                sysProject.setWorkloadAlias(viewFqProject.getChargeItem());
            }
            if (viewFqProject.getManagerUserName() != null){
                sysProject.setUserNameAlias(viewFqProject.getManagerUserName());
            }
            if (viewFqProject.getCustomerName() != null){
                sysProject.setRequesterAlias(viewFqProject.getCustomerName());
            }
            if (viewFqProject.getArrangeStartTime() != null){
                String dateString = viewFqProject.getArrangeStartTime().format(formatter);
                sysProject.setProjectStartAlias(dateString);
            }
            if (viewFqProject.getArrangeEndTime() != null){
                String dateString = viewFqProject.getArrangeEndTime().format(formatter);
                sysProject.setProjectEndAlias(dateString);
            }
            if (viewFqProject.getFirstCheckTime() != null){
                String dateString = viewFqProject.getFirstCheckTime().format(formatter);
                sysProject.setOneCheck(dateString);
            }
            if (viewFqProject.getSecondCheckTime() != null){
                String dateString = viewFqProject.getSecondCheckTime().format(formatter);
                sysProject.setTwoCheck(dateString);
            }
            if (viewFqProject.getDeliveryTime() != null){
                String dateString = viewFqProject.getDeliveryTime().format(formatter);
                sysProject.setNoticeTime(dateString);
            }
            if (viewFqProject.getReleaseTime() != null){
                String dateString = viewFqProject.getReleaseTime().format(formatter);
                sysProject.setProjectTime(dateString);
            }
            if (viewFqProject.getArriveTime() != null){
                String dateString = viewFqProject.getArriveTime().format(formatter);
                sysProject.setDeliveryTime(dateString);
            }
            if (viewFqProject.getArrangeProfit() != null){
                BigDecimal bigDecimalValue = new BigDecimal(viewFqProject.getArrangeProfit());
                sysProject.setProjectMoneyAlias(bigDecimalValue);
            }
            if (viewFqProject.getJobContent() != null){
                sysProject.setWorkcontentAlias(viewFqProject.getJobContent());
            }
            if (viewFqProject.getJobOrgName() != null){
                sysProject.setDepartment(viewFqProject.getJobOrgName());
            }

            if (mqMessage.getOpType().equals("SECOND_CHECK") && sysProject.getTwoCheck() != null && !sysProject.getTwoCheck().equals("")){
                sysProject.setIsTwoCheck(1);
                Date date = DateUtils.parseDate(sysProject.getTwoCheck(), "yyyy-MM-dd HH:mm:ss");
                sysProject.setTwoCheckTime(date);
            }

            //判断状态
            if (viewFqProject.getManagerUserName() == null || viewFqProject.getManagerUserName().equals("")){
                sysProject.setStatus((long)0);
            }
            if ((viewFqProject.getManagerUserName() != null && !(viewFqProject.getManagerUserName().equals(""))) && (sysProject.getOneCheck() == null || sysProject.getOneCheck().equals("")) && (sysProject.getTwoCheck() == null || sysProject.getTwoCheck().equals(""))){
                sysProject.setStatus((long)1);
            }

            if ((sysProject.getOneCheck() != null && !sysProject.getOneCheck().equals("")) && (sysProject.getTwoCheck() == null || sysProject.getTwoCheck().equals(""))){
                sysProject.setStatus((long)2);
            }

            if (sysProject.getTwoCheck() != null && !sysProject.getTwoCheck().equals("")){
                sysProject.setStatus((long)3);
                sysProject.setIsTwoCheck(1);
            }

            if (viewFqProject.getSubpackageType() != null){
                sysProject.setSubpackageType(viewFqProject.getSubpackageType());
            }

//            String sysProjectJson = mapper.writeValueAsString(sysProject);
//            viewFqProjectLog.setViewFqProjectJson(viewFqProjectJson);
//            viewFqProjectLog.setSysProjectJson(sysProjectJson);
            viewFqProjectLogService.insertViewFqProjectLog(viewFqProjectLog);

            if (mqMessage.getOpType().equals("DELETE")){
                sysProjectService.deleteSysProjectByCode(viewFqProject.getProjectCode());
            }else {
                if (sysProjectService.checkProjectKeyUnique(viewFqProject.getProjectCode()) != null) {
                    sysProjectService.updateSysProjectForMq(sysProject);
                }else {
                    sysProjectService.insertSysProject(sysProject);
                }
            }

            System.out.println("通过Message:{}" + mqMessage.getOpType());
            System.out.println("通过Message:{}" + mqMessage.getProjectId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 手动确认消息消费成功
        }catch (IOException | ParseException e) {
            // 处理其他确认失败的情况
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // 手动确认消息消费失败
        }
    }
}
