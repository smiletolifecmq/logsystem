package com.kcylog.web.monitor;

import com.google.gson.Gson;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.common.MqMessage;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
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
import java.util.List;

@Component
public class MqConsume {
    @Autowired
    private IViewFqProjectService viewFqProjectService;

    @Autowired
    private IViewFqProjectLogService viewFqProjectLogService;

    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private IViewFqProjectWorkResourceArrangeService viewFqProjectWorkResourceArrangeService;

    @Autowired
    private ISysProjectValueService sysProjectValueService;

    @Autowired
    private IViewFqProjectArchiveTransferTrackService viewFqProjectArchiveTransferTrackService;

    @Autowired
    private IViewFqProjectWorkDoneService viewFqProjectWorkDoneService;

    @Autowired
    private IViewFqChargeMoneyService viewFqChargeMoneyService;

    @Autowired
    private IViewFqSalemapSelectgeoGeoinfoService viewFqSalemapSelectgeoGeoinfoService;

    @Autowired
    private ISysProjectGeoinfoService projectGeoinfoService;

    @Autowired
    private IViewFqSalemapSelectmapTfinfoService viewFqSalemapSelectmapTfinfoService;

    @Autowired
    private ISysProjectSelectmapTfinfoService sysProjectSelectmapTfinfoService;
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
            viewFqProjectLogService.insertViewFqProjectLog(viewFqProjectLog);
            //获取视图数据
            ViewFqProject viewFqProject = viewFqProjectService.selectViewFqProjectByProjectCode(Long.parseLong(mqMessage.getProjectId()));
            if (viewFqProject != null){
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
                    Date date = DateUtils.parseDate(sysProject.getTwoCheck(), "yyyy-MM-dd HH:mm:ss");
                    sysProject.setTwoCheckTime(date);
                }

                if (viewFqProject.getSubpackageType() != null){
                    sysProject.setSubpackageType(viewFqProject.getSubpackageType());
                }

                if (viewFqProject.getCustomerContractName() != null){
                    sysProject.setCustomerContractName(viewFqProject.getCustomerContractName());
                }

                if (viewFqProject.getCustomerContractPhone() != null){
                    sysProject.setCustomerContractPhone(viewFqProject.getCustomerContractPhone());
                }

                //作业状态
                ViewFqProjectArchiveTransferTrack projectArchiveTransferTrack = viewFqProjectArchiveTransferTrackService.selectViewFqProjectArchiveTransferTrackByProjectId(Long.parseLong(mqMessage.getProjectId()));
                if (projectArchiveTransferTrack != null && projectArchiveTransferTrack.getWorkStatus() != null){
                    sysProject.setWorkStatus(projectArchiveTransferTrack.getWorkStatus());
                }
                //作业办结时间
                ViewFqProjectWorkDone workDoneList = viewFqProjectWorkDoneService.selectViewFqProjectWorkDoneByProjectId(Long.parseLong(mqMessage.getProjectId()));
                if (workDoneList != null && workDoneList.getDoTime() != null){
                    sysProject.setDoTime(workDoneList.getDoTime().toString());
                }
                //分包金额
                ViewFqChargeMoney chargeMoney = viewFqChargeMoneyService.selectViewFqChargeMoneyByProjectCode(viewFqProject.getProjectCode());
                if (chargeMoney != null && chargeMoney.getSum() != null){
                    BigDecimal bigDecimalValue = new BigDecimal((chargeMoney.getSum()/100));
                    sysProject.setFbMoney(bigDecimalValue);
                }

                if (mqMessage.getOpType().equals("DELETE") || mqMessage.getOpType().equals("PROJECT_INVALID") || mqMessage.getOpType().equals("PROJECT_HANG")){
                    sysProjectService.deleteSysProjectByCode(viewFqProject.getProjectCode());
                }else {
                    if (sysProjectService.checkProjectKeyUnique(viewFqProject.getProjectCode()) != null) {
                        sysProject.setProjectId(sysProjectService.checkProjectKeyUnique(viewFqProject.getProjectCode()).getProjectId());
                        sysProjectService.updateSysProjectForMq(sysProject);
                    }else {
                        sysProjectService.insertSysProject(sysProject);
                    }
                    //同步坐标系
                    List<ViewFqSalemapSelectgeoGeoinfo> geoInfoList = viewFqSalemapSelectgeoGeoinfoService.selectViewFqSalemapSelectgeoGeoinfoByProjectId(Long.parseLong(mqMessage.getProjectId()));
                    projectGeoinfoService.deleteSysProjectGeoinfoByProjectId(sysProject.getProjectId());
                    if (geoInfoList != null){
                        for (ViewFqSalemapSelectgeoGeoinfo geoInfo : geoInfoList){
                            SysProjectGeoinfo sysProjectGeoinfo = new SysProjectGeoinfo();
                            if (geoInfo != null){
                                if (geoInfo.getGeometry() != null){
                                    sysProjectGeoinfo.setGeometry(geoInfo.getGeometry());
                                }
                                if (geoInfo.getGeometry2000() != null){
                                    sysProjectGeoinfo.setGeometry2000(geoInfo.getGeometry2000());
                                }
                                if (geoInfo.getBufferGeometry() != null){
                                    sysProjectGeoinfo.setBufferGeometry(geoInfo.getBufferGeometry());
                                }
                                if (geoInfo.getBufferGeometry2000() != null){
                                    sysProjectGeoinfo.setBufferGeometry2000(geoInfo.getBufferGeometry2000());
                                }
                                if (geoInfo.getGeometryGauss2000() != null){
                                    sysProjectGeoinfo.setGeometryGauss2000(geoInfo.getGeometryGauss2000());
                                }
                                if (geoInfo.getBufferGeometryGauss2000() != null){
                                    sysProjectGeoinfo.setBufferGeometryGauss2000(geoInfo.getBufferGeometryGauss2000());
                                }
                                sysProjectGeoinfo.setProjectId(sysProject.getProjectId());
                            }
                            projectGeoinfoService.insertSysProjectGeoinfo(sysProjectGeoinfo);
                        }

                    }
                    //同步网格
                    List<ViewFqSalemapSelectmapTfinfo> tfinfoList = viewFqSalemapSelectmapTfinfoService.selectViewFqSalemapSelectmapTfinfoByProjectId(Long.parseLong(mqMessage.getProjectId()));
                    sysProjectSelectmapTfinfoService.deleteSysProjectSelectmapTfinfoByProjectId(sysProject.getProjectId());
                    if (tfinfoList != null){
                        for (ViewFqSalemapSelectmapTfinfo tfinfo : tfinfoList){
                            SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo = new SysProjectSelectmapTfinfo();
                            if (tfinfo != null){
                                if (tfinfo.getMapCode() != null){
                                    sysProjectSelectmapTfinfo.setMapCode(tfinfo.getMapCode());
                                }
                               if (tfinfo.getMapAddinfo() != null){
                                   sysProjectSelectmapTfinfo.setMapAddinfo(tfinfo.getMapAddinfo());
                               }
                                sysProjectSelectmapTfinfo.setProjectId(sysProject.getProjectId());
                           }
                            sysProjectSelectmapTfinfoService.insertSysProjectSelectmapTfinfo(sysProjectSelectmapTfinfo);
                       }
                   }

                }

                //同步人员安排配比
                if (mqMessage.getOpType().equals("RESOURCE_ARRANGE_CHANGE") || (mqMessage.getOpType().equals("SECOND_CHECK") && sysProject.getTwoCheck() != null && !sysProject.getTwoCheck().equals(""))){
                    // todo 需要同步人员安排
                    List<ViewFqProjectWorkResourceArrange>  resourceArrange = viewFqProjectWorkResourceArrangeService.selectViewFqProjectWorkResourceArrangeByProjectId(Long.parseLong(mqMessage.getProjectId()));
                    SysProject project = sysProjectService.checkProjectKeyUnique(viewFqProject.getProjectCode());
                    sysProjectValueService.deleteSysProjectValueByProjectId(project.getProjectId());
                    if (resourceArrange != null){
                        for (ViewFqProjectWorkResourceArrange resourceArrange1 : resourceArrange){
                            if(resourceArrange1 == null || resourceArrange1.getUserName() == null || resourceArrange1.getPerformanceRate() == null){
                                continue;
                            }
                            SysProjectValue sysProjectValue = new SysProjectValue();
                            sysProjectValue.setProjectId(project.getProjectId());
                            sysProjectValue.setUserName(resourceArrange1.getUserName());
                            sysProjectValue.setProportion(resourceArrange1.getPerformanceRate());
                            sysProjectValueService.insertSysProjectValue(sysProjectValue);
                        }
                    }
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
