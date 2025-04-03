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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private IViewFqSalemapSelectmapBaseinfoService viewFqSalemapSelectmapBaseinfoService;

    @Autowired
    private IFqProjectProcessService fqProjectProcessService;

    @Autowired
    private ISysReviewSubService sysReviewSubService;

    @Autowired
    private IViewFqChargeInfoService viewFqChargeInfoService;

    @Autowired
    private IProjectChargeInfoService projectChargeInfoService;

    @Autowired
    private IBcProjectService bcProjectService;
    /**
     * 监听一个简单的队列，队列不存在时候会创建
     */
    @RabbitListener(queuesToDeclare = @Queue(name = "${spring.rabbitmq.queue}"))
    public void consumerSimpleMessage(Message message, Channel channel) throws IOException {
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
        try {
            // 手动确认消息消费成功
            // 通过Message对象解析消息
            if (viewFqProject != null && viewFqProject.getProjectCode() != null && !viewFqProject.getProjectCode().equals("") && !viewFqProject.getProjectCode().contains("图") && !(mqMessage.getOpType().equals("DELETE") || mqMessage.getOpType().equals("PROJECT_INVALID") || mqMessage.getOpType().equals("PROJECT_HANG") || mqMessage.getOpType().equals("PROJECT_DELETE"))){
                //数据初始化
                SysProject sysProject = new SysProject();
                sysProject.setProjectNameAlias(viewFqProject.getProjectName());
                sysProject.setProjectNum(viewFqProject.getProjectCode());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                List<String> substrings = Arrays.asList("图", "售", "数");
                boolean contains = substrings.stream().anyMatch(sysProject.getProjectNum()::contains);

                sysProject.setViewProjectId(mqMessage.getProjectId());
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

                if (viewFqProject.getAfterTime() != null){
                    String dateString = viewFqProject.getAfterTime().format(formatter);
                    sysProject.setAfterTime(dateString);
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

                if (viewFqProject.getContractNo() != null){
                    sysProject.setContractNo(viewFqProject.getContractNo());
                }

                if (viewFqProject.getContractAmount() != null){
                    sysProject.setContractAmount(viewFqProject.getContractAmount());
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
//                ViewFqChargeMoney chargeMoney = viewFqChargeMoneyService.selectViewFqChargeMoneyByProjectCode(viewFqProject.getProjectCode());
//                if (chargeMoney != null && chargeMoney.getSum() != null){
//                    BigDecimal bigDecimalValue = new BigDecimal((chargeMoney.getSum()/100));
//                    sysProject.setFbMoney(bigDecimalValue);
//                }
                //地图比例
                ViewFqSalemapSelectmapBaseinfo mapBaseinfo = viewFqSalemapSelectmapBaseinfoService.selectViewFqSalemapSelectmapBaseinfoByProjectId(Long.parseLong(mqMessage.getProjectId()));
                if (mapBaseinfo != null && mapBaseinfo.getMapScale() != null){
                    sysProject.setMapScale(mapBaseinfo.getMapScale());
                }

                //同步人员安排配比
                if (mqMessage.getOpType().equals("RESOURCE_ARRANGE_CHANGE") || (mqMessage.getOpType().equals("SECOND_CHECK") && sysProject.getTwoCheck() != null && !sysProject.getTwoCheck().equals(""))){
                    // todo 需要同步人员安排
                    List<ViewFqProjectWorkResourceArrange>  resourceArrange = viewFqProjectWorkResourceArrangeService.selectViewFqProjectWorkResourceArrangeByProjectId(Long.parseLong(mqMessage.getProjectId()));
                    SysProject project = sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId());
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

                if (mqMessage.getOpType().equals("DELETE") || mqMessage.getOpType().equals("PROJECT_INVALID") || mqMessage.getOpType().equals("PROJECT_HANG") || mqMessage.getOpType().equals("PROJECT_DELETE")){
                    if (sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId()) != null) {
                        sysProject.setProjectId(sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId()).getProjectId());
                    }
                    sysProjectService.deleteSysProjectByCode(mqMessage.getProjectId());
                    projectGeoinfoService.deleteSysProjectGeoinfoByProjectId(sysProject.getProjectId());
                    sysProjectSelectmapTfinfoService.deleteSysProjectSelectmapTfinfoByProjectId(sysProject.getProjectId());
                    fqProjectProcessService.deleteFqProjectProcessById(sysProject.getProjectId());
                    sysProjectValueService.deleteSysProjectValueByProjectId(sysProject.getProjectId());
                    if (contains){
                        bcProjectService.deleteBcProjectByXMBH(sysProject.getProjectNum());
                    }
                }else {
                    if (sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId()) != null) {
                        sysProject.setProjectId(sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId()).getProjectId());
                        sysProjectService.updateSysProjectForMq(sysProject);
                        SysReviewSub reviewSub = new SysReviewSub();
                        reviewSub.setSerialNum(sysProject.getProjectNum());
                        if (viewFqProject.getSubpackageType() != null){
                            reviewSub.setSubpackageType(viewFqProject.getSubpackageType());
                        }else {
                            reviewSub.setSubpackageType((long)1);
                        }
                        sysReviewSubService.updateSubpackageTypeByProjectNum(reviewSub);
                    }else {
                        sysProjectService.insertSysProject(sysProject);
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
                    //同步流程
                    List<ViewFqProjectArchiveTransferTrack> projectArchiveTransferTrackList = viewFqProjectArchiveTransferTrackService.selectViewFqProjectArchiveTransferTrackListByProjectId(Long.parseLong(mqMessage.getProjectId()));
                    fqProjectProcessService.deleteFqProjectProcessById(sysProject.getProjectId());
                    if (projectArchiveTransferTrackList.size() != 0){
                        for (ViewFqProjectArchiveTransferTrack projectArchiveTransferTrackValue : projectArchiveTransferTrackList){
                            FqProjectProcess fqProjectProcessObj = new FqProjectProcess();
                            fqProjectProcessObj.setProjectId(sysProject.getProjectId());
                            if (projectArchiveTransferTrackValue.getTransferStatus() != null){
                                fqProjectProcessObj.setTransferStatus(projectArchiveTransferTrackValue.getTransferStatus());
                            }
                            if (projectArchiveTransferTrackValue.getTransferTime() != null){
                                String dateString = projectArchiveTransferTrackValue.getTransferTime().format(formatter);
                                fqProjectProcessObj.setTransferTime(dateString);
                            }
                            if (projectArchiveTransferTrackValue.getTransferUserName() != null){
                                fqProjectProcessObj.setTransferUserName(projectArchiveTransferTrackValue.getTransferUserName());
                            }
                            if (projectArchiveTransferTrackValue.getReceiveStatus() != null){
                                fqProjectProcessObj.setReceiveStatus(projectArchiveTransferTrackValue.getReceiveStatus());
                            }
                            if (projectArchiveTransferTrackValue.getSjsj() != null){
                                fqProjectProcessObj.setReceiveTime(projectArchiveTransferTrackValue.getSjsj().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getSjUserName() != null){
                                fqProjectProcessObj.setReceiveUserName(projectArchiveTransferTrackValue.getSjUserName());
                            }
                            if (projectArchiveTransferTrackValue.getStampStatus() != null){
                                fqProjectProcessObj.setStampStatus(projectArchiveTransferTrackValue.getStampStatus());
                            }
                            if (projectArchiveTransferTrackValue.getGzsj() != null){
                                fqProjectProcessObj.setStampTime(projectArchiveTransferTrackValue.getGzsj().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getGzUserName() != null){
                                fqProjectProcessObj.setStampUserName(projectArchiveTransferTrackValue.getGzUserName());
                            }

                            if (projectArchiveTransferTrackValue.getMarketingConfirm() != null){
                                fqProjectProcessObj.setMarketingConfirm(projectArchiveTransferTrackValue.getMarketingConfirm());
                            }

                            if (projectArchiveTransferTrackValue.getMarketingConfirmTime() != null){
                                fqProjectProcessObj.setMarketingTime(projectArchiveTransferTrackValue.getMarketingConfirmTime().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getMarketingUserName() != null){
                                fqProjectProcessObj.setMarketingUserName(projectArchiveTransferTrackValue.getMarketingUserName());
                            }
                            if (projectArchiveTransferTrackValue.getCheckStatus() != null){
                                fqProjectProcessObj.setCheckStatus(projectArchiveTransferTrackValue.getCheckStatus());
                            }
                            if (projectArchiveTransferTrackValue.getYstgsj() != null){
                                fqProjectProcessObj.setCheckTime(projectArchiveTransferTrackValue.getYstgsj().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getYsTgUserName() != null){
                                fqProjectProcessObj.setCheckUserName(projectArchiveTransferTrackValue.getYsTgUserName());
                            }
                            if (projectArchiveTransferTrackValue.getYssj() != null){
                                fqProjectProcessObj.setArchiveTime(projectArchiveTransferTrackValue.getYssj().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getYsUserName() != null){
                                fqProjectProcessObj.setArchiveUserName(projectArchiveTransferTrackValue.getYsUserName());
                            }
                            if (projectArchiveTransferTrackValue.getReceiveCutoffTime() != null){
                                fqProjectProcessObj.setReceiveCutoffTime(projectArchiveTransferTrackValue.getReceiveCutoffTime().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getRectifyCutoffTime() != null){
                                fqProjectProcessObj.setRectifyCutoffTime(projectArchiveTransferTrackValue.getRectifyCutoffTime().format(formatter));
                            }
                            if (projectArchiveTransferTrackValue.getVersion() != null){
                                fqProjectProcessObj.setVersion(projectArchiveTransferTrackValue.getVersion());
                            }
                            if (projectArchiveTransferTrackValue.getIsArchive() != null){
                                fqProjectProcessObj.setIsArchive(projectArchiveTransferTrackValue.getIsArchive());
                            }
                            fqProjectProcessService.insertFqProjectProcess(fqProjectProcessObj);
                        }
                    }
                    //同步分包合同
                    List<ViewFqChargeInfo> chargeInfoList = viewFqChargeInfoService.selectViewFqChargeInfoByProjectCode(Long.parseLong(mqMessage.getProjectId()));
                    projectChargeInfoService.deleteProjectChargeInfoById(mqMessage.getProjectId());
                    if (chargeInfoList.size() != 0){
                        for (ViewFqChargeInfo chargeInfo : chargeInfoList){
                            ProjectChargeInfo projectChargeInfoObj = new ProjectChargeInfo();
                            projectChargeInfoObj.setProjectId(mqMessage.getProjectId());
                            projectChargeInfoObj.setProjectCode(chargeInfo.getProjectCode());
                            if (chargeInfo.getSettleMoney() != null){
                                projectChargeInfoObj.setSettleMoney(chargeInfo.getSettleMoney());
                            }
                            if (chargeInfo.getFirmName() != null){
                                projectChargeInfoObj.setFirmName(chargeInfo.getFirmName());
                            }
                            if (chargeInfo.getSubcontractNo() != null){
                                projectChargeInfoObj.setSubcontractNo(chargeInfo.getSubcontractNo());
                            }
                            if (chargeInfo.getCreateTime() != null){
                                projectChargeInfoObj.setFbTime(chargeInfo.getCreateTime());
                            }
                            if (chargeInfo.getProjectName() != null){
                                projectChargeInfoObj.setProjectName(chargeInfo.getProjectName());
                            }
                            if (chargeInfo.getProjectTypeName() != null){
                                projectChargeInfoObj.setProjectTypeName(chargeInfo.getProjectTypeName());
                            }
                            if (sysProject.getProjectStartAlias() != null){
                                projectChargeInfoObj.setProjectStartAlias(sysProject.getProjectStartAlias());
                            }
                            if (sysProject.getProjectEndAlias() != null){
                                projectChargeInfoObj.setProjectEndAlias(sysProject.getProjectEndAlias());
                            }
                            if (sysProject.getUserNameAlias() != null){
                                projectChargeInfoObj.setUserNameAlias(sysProject.getUserNameAlias());
                            }
                            if (sysProject.getDepartment() != null){
                                projectChargeInfoObj.setDepartment(sysProject.getDepartment());
                            }
                            projectChargeInfoService.insertProjectChargeInfo(projectChargeInfoObj);
                        }
                    }
                    //同步坐标系
                    List<ViewFqSalemapSelectgeoGeoinfo> geoInfoList = viewFqSalemapSelectgeoGeoinfoService.selectViewFqSalemapSelectgeoGeoinfoByProjectId(Long.parseLong(mqMessage.getProjectId()));
                    projectGeoinfoService.deleteSysProjectGeoinfoByProjectId(sysProject.getProjectId());

                    if (contains){
                        bcProjectService.deleteBcProjectByXMBH(sysProject.getProjectNum());
                    }
                    if (geoInfoList != null){
                        for (ViewFqSalemapSelectgeoGeoinfo geoInfo : geoInfoList){
                            BcProject newbcproject = new BcProject();
                            newbcproject.setXmmc(viewFqProject.getProjectName());
                            newbcproject.setXmbh(viewFqProject.getProjectCode());
                            if (viewFqProject.getProjectTypeName() != null){
                                newbcproject.setXmlx(viewFqProject.getProjectTypeName());
                            }
                            if (viewFqProject.getRegisterTime() != null){
                                Date djsj = Date.from(viewFqProject.getRegisterTime().atZone(ZoneId.systemDefault()).toInstant());
                                newbcproject.setDjsj(djsj);
                            }
                            if (sysProject.getUserNameAlias() != null){
                                newbcproject.setFzr(sysProject.getUserNameAlias());
                            }
                            if (viewFqProject.getCustomerName() != null){
                                newbcproject.setWtdw(viewFqProject.getCustomerName());
                            }
                            if (viewFqProject.getArrangeStartTime() != null){
                                Date kssj = Date.from(viewFqProject.getArrangeStartTime().atZone(ZoneId.systemDefault()).toInstant());
                                newbcproject.setKssj(kssj);
                            }
                            if (viewFqProject.getArrangeEndTime() != null){
                                Date jssj = Date.from(viewFqProject.getArrangeEndTime().atZone(ZoneId.systemDefault()).toInstant());
                                newbcproject.setJssj(jssj);
                            }
                            if (viewFqProject.getJobContent() != null){
                                newbcproject.setGznr(viewFqProject.getJobContent());
                            }
                            if (viewFqProject.getJobOrgName() != null){
                                newbcproject.setBm(viewFqProject.getJobOrgName());
                            }
                            newbcproject.setGclx((long)1);
                            newbcproject.setLx((long)1);

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
                                    newbcproject.setShape(geoInfo.getGeometryGauss2000());
                                }
                                if (geoInfo.getBufferGeometryGauss2000() != null){
                                    sysProjectGeoinfo.setBufferGeometryGauss2000(geoInfo.getBufferGeometryGauss2000());
                                }
                                if (geoInfo.getBufferDistance() != null){
                                    sysProjectGeoinfo.setBufferDistance(geoInfo.getBufferDistance());
                                }
                                sysProjectGeoinfo.setProjectId(sysProject.getProjectId());
                            }
                            projectGeoinfoService.insertSysProjectGeoinfo(sysProjectGeoinfo);
                            if (newbcproject.getShape() != null && contains && newbcproject.getShape() != null && newbcproject.getShape().toUpperCase().contains("POLYGON")){
                                String wkt = newbcproject.getShape();
                                if (wkt.length() > 3000) {
                                    List<String> fragments = new ArrayList<>();
                                    int length = wkt.length();
                                    for (int i = 0; i < length; i += 3000) {
                                        fragments.add(wkt.substring(i, Math.min(length, i + 3000)));
                                    }
                                    newbcproject.setClobFragments(fragments);
                                }
                                bcProjectService.insertBcProject(newbcproject);
                            }
                        }

                    }
                }


            }

            if (mqMessage.getOpType().equals("DELETE") || mqMessage.getOpType().equals("PROJECT_INVALID") || mqMessage.getOpType().equals("PROJECT_HANG") || mqMessage.getOpType().equals("PROJECT_DELETE")){
                SysProject sysProject = new SysProject();
                if (sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId()) != null) {
                    sysProject.setProjectId(sysProjectService.checkProjectKeyUniqueByViewProjectId(mqMessage.getProjectId()).getProjectId());
                }
                sysProjectService.deleteSysProjectByCode(mqMessage.getProjectId());
                projectGeoinfoService.deleteSysProjectGeoinfoByProjectId(sysProject.getProjectId());
                sysProjectSelectmapTfinfoService.deleteSysProjectSelectmapTfinfoByProjectId(sysProject.getProjectId());
                fqProjectProcessService.deleteFqProjectProcessById(sysProject.getProjectId());
                sysProjectValueService.deleteSysProjectValueByProjectId(sysProject.getProjectId());
                projectChargeInfoService.deleteProjectChargeInfoById(mqMessage.getProjectId());
                List<String> substrings = Arrays.asList("图", "售", "数");
                if (viewFqProject != null && viewFqProject.getProjectCode() != null){
                    boolean contains = substrings.stream().anyMatch(viewFqProject.getProjectCode()::contains);
                    if (contains){
                        bcProjectService.deleteBcProjectByXMBH(sysProject.getProjectNum());
                    }
                }
            }

            System.out.println("通过Message:{}" + mqMessage.getOpType());
            System.out.println("通过Message:{}" + mqMessage.getProjectId());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 手动确认消息消费成功
        }catch (IOException | ParseException e) {
            // 处理其他确认失败的情况
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true); // 手动确认消息消费失败
            nowTime = DateUtils.getNowDate();
            viewFqProjectLog = new ViewFqProjectLog();
            viewFqProjectLog.setOperateTime(nowTime);
            if (viewFqProject != null && viewFqProject.getProjectCode() != null){
                viewFqProjectLog.setProjectCode(viewFqProject.getProjectCode());
            }
            viewFqProjectLog.setOperate("同步地理信息数据失败");
            viewFqProjectLogService.insertViewFqProjectLog(viewFqProjectLog);
        }
    }
}
