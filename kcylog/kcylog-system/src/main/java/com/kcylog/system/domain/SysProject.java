package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import com.kcylog.system.common.ProjectEmployee;
import com.kcylog.system.common.ProjectSubcontract;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 项目对象 sys_project
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public class SysProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目ID */
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称", needMerge = true,subIndexMax = 4)
    private String projectNameAlias;

    /** 项目编号 */
    @Excel(name = "项目编号", needMerge = true)
    private String projectNum;

    /** 项目类型 */
    @Excel(name = "项目类型", needMerge = true)
    private String projectType;

    /** 项目金额 */
    @Excel(name = "项目金额", needMerge = true)
    private BigDecimal projectMoneyAlias;

    @Excel(name = "工作量", needMerge = true)
    private String workcontentAlias;

    /** 工程内容 */
    @Excel(name = "工程内容", needMerge = true)
    private String workloadAlias;

    @Excel(name = "工程负责人", needMerge = true)
    private String userNameAlias;

    /** 登记时间 */
    @Excel(name = "登记时间", needMerge = true)
    private String registerTime;

    /** 接待人 */
    @Excel(name = "接待人", needMerge = true)
    private String receptionist;

    private Date settlementTime;

    /** 委托单位 */
    @Excel(name = "委托单位", needMerge = true)
    private String requesterAlias;

    /** 安排开始时间 */
    @Excel(name = "安排开始时间", needMerge = true)
    private String projectStartAlias;

    /** 安排结束时间 */
    @Excel(name = "安排结束时间", needMerge = true)
    private String projectEndAlias;

    /** 一检时间 */
    @Excel(name = "一检时间", needMerge = true)
    private String oneCheck;

    /** 二检时间 */
    @Excel(name = "二检时间", needMerge = true)
    private String twoCheck;

    private String department;

    private Date twoCheckTime;

    private Integer isTwoCheck;

    private Integer outputStatus;

    private Integer mapScale;

    private FqProjectProcess fqProjectProcess;

    private List<FqProjectProcess> fqProjectProcessList;

    private List<ProjectChargeInfo> projectChargeInfo;

    /** 通知出件时间 */
    @Excel(name = "通知出件时间", needMerge = true)
    private String noticeTime;

    /** 项目出件时间 */
    @Excel(name = "项目出件时间", needMerge = true)
    private String projectTime;

    /** 送达时间 */
    @Excel(name = "送达时间", needMerge = true)
    private String deliveryTime;

    /** 经营产值 */
    @Excel(name = "经营产值", needMerge = true)
    private BigDecimal operate;

    private BigDecimal guGongMoney;

    /** 填写经营产值人 */
    private String operateUser;

    private String fbWorkload;

    private Integer isCarRegister;

    private Integer isShow;

    private Integer leadTime;

    private Integer receiveDays;

    private Integer archiveDays;

    private Integer oneCheckDays;

    private Integer twoCheckDays;

    private Integer monthhj;

    /** 填写经营产值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operateTime;

    private String doTime;

    // 作业状态：(1： "新增作业"，2："作业中"，3："作业完成"，4："作业办结")
    private Long workStatus;

    // 登记状态： (1："登记中"，2："登记办结")
    private Long registerStatus;

    // 一检状态：1通过 2不通过 空：待办
    private Long firstCheck;

    // 二检状态：1通过 2不通过 空：待办
    private Long secondCheck;

    // 移交:（1："未移交"，2："移交办结")
    private Long transferStatus;

    // 收件:（ 1： "未收件"，2："收件办结")
    private Long receiveStatus;

    // 盖章:（1："未盖章"；2："已盖章"；3："盖章拒绝"；4："无需盖章"）
    private Long stampStatus;

    // 验收通过、验收办结(归档)：（1："未验收"；2："验收办结"；3："验收拒绝"，4： "验收通过"）
    private Long checkStatus;

    private Long deptId;

    // 盖章确认 0未确认，1确认
    private Long marketingConfirm;

    private LocalDateTime receiveCutoffTime;

    private LocalDateTime rectifyCutoffTime;

    private Integer receiveCutoffTimeDays;

    private Integer rectifyCutoffTimeDays;

    private Integer operateStatus;

    //经办人
    private String customerContractName;
    //经办人手机号
    private String customerContractPhone;

    //福州本地坐标系
    private String geometry;

    //2000大地坐标系
    private String geometry2000;

    //缓冲区坐标串，福州本地坐标系
    private String bufferGeometry;

    //缓冲区坐标串，2000大地坐标系
    private String bufferGeometry2000;

    //国家2000平面，福州
    private String geometryGauss2000;

    //缓冲区坐标串，国家2000平面，福州
    private String bufferGeometryGauss2000;

    private Date settleTime;

    private Date cgsdtime;

    private Long settle;

    private BigDecimal durationFactor;

    private BigDecimal qualityCoefficient;

    private BigDecimal projectCoefficientMoney;

    private BigDecimal projectCoefficient;

    private String completionNotes;

    private String oneCheckNotes;

    private String twoCheckNotes;

    private String contractNo;

    private Long contractAmount;

    private String bjRemark;

    private String userName;

    private Long drawStatus;

    private String winUnit;

    private Long gzStatus;

    private Long mapShow;

    private BigDecimal ygmoney;

    private BigDecimal moneyhj;

    private BigDecimal profitMoneyHj;

    private int num;

    private Long tjStatus;

    private Long userId;

    private Long cjstatus;

    private String deptName;

    private Date ygtime;

    public Long getCjstatus() {
        return cjstatus;
    }

    public void setCjstatus(Long cjstatus) {
        this.cjstatus = cjstatus;
    }

    public Date getYgtime() {
        return ygtime;
    }

    public void setYgtime(Date ygtime) {
        this.ygtime = ygtime;
    }

    public Integer getMonthhj() {
        return monthhj;
    }

    public void setMonthhj(Integer monthhj) {
        this.monthhj = monthhj;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTjStatus() {
        return tjStatus;
    }

    public void setTjStatus(Long tjStatus) {
        this.tjStatus = tjStatus;
    }

    public BigDecimal getMoneyhj() {
        return moneyhj;
    }

    public void setMoneyhj(BigDecimal moneyhj) {
        this.moneyhj = moneyhj;
    }

    public BigDecimal getProfitMoneyHj() {
        return profitMoneyHj;
    }

    public void setProfitMoneyHj(BigDecimal profitMoneyHj) {
        this.profitMoneyHj = profitMoneyHj;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getCgsdtime() {
        return cgsdtime;
    }

    public void setCgsdtime(Date cgsdtime) {
        this.cgsdtime = cgsdtime;
    }

    public BigDecimal getYgmoney() {
        return ygmoney;
    }

    public void setYgmoney(BigDecimal ygmoney) {
        this.ygmoney = ygmoney;
    }

    public Long getMapShow() {
        return mapShow;
    }

    public void setMapShow(Long mapShow) {
        this.mapShow = mapShow;
    }

    public Long getGzStatus() {
        return gzStatus;
    }

    public void setGzStatus(Long gzStatus) {
        this.gzStatus = gzStatus;
    }

    public String getWinUnit() {
        return winUnit;
    }

    public void setWinUnit(String winUnit) {
        this.winUnit = winUnit;
    }

    public Long getDrawStatus() {
        return drawStatus;
    }

    public void setDrawStatus(Long drawStatus) {
        this.drawStatus = drawStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBjRemark() {
        return bjRemark;
    }

    public void setBjRemark(String bjRemark) {
        this.bjRemark = bjRemark;
    }

    public String getContractNo() {
        return contractNo;
    }

    public List<ProjectChargeInfo> getProjectChargeInfo() {
        return projectChargeInfo;
    }

    public void setProjectChargeInfo(List<ProjectChargeInfo> projectChargeInfo) {
        this.projectChargeInfo = projectChargeInfo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public List<FqProjectProcess> getFqProjectProcessList() {
        return fqProjectProcessList;
    }

    public void setFqProjectProcessList(List<FqProjectProcess> fqProjectProcessList) {
        this.fqProjectProcessList = fqProjectProcessList;
    }

    public String getCompletionNotes() {
        return completionNotes;
    }

    public void setCompletionNotes(String completionNotes) {
        this.completionNotes = completionNotes;
    }

    public String getOneCheckNotes() {
        return oneCheckNotes;
    }

    public void setOneCheckNotes(String oneCheckNotes) {
        this.oneCheckNotes = oneCheckNotes;
    }

    public String getTwoCheckNotes() {
        return twoCheckNotes;
    }

    public void setTwoCheckNotes(String twoCheckNotes) {
        this.twoCheckNotes = twoCheckNotes;
    }

    public BigDecimal getProjectCoefficientMoney() {
        return projectCoefficientMoney;
    }

    public void setProjectCoefficientMoney(BigDecimal projectCoefficientMoney) {
        this.projectCoefficientMoney = projectCoefficientMoney;
    }

    public BigDecimal getProjectCoefficient() {
        return projectCoefficient;
    }

    public void setProjectCoefficient(BigDecimal projectCoefficient) {
        this.projectCoefficient = projectCoefficient;
    }

    public BigDecimal getDurationFactor() {
        return durationFactor;
    }

    public void setDurationFactor(BigDecimal durationFactor) {
        this.durationFactor = durationFactor;
    }

    public BigDecimal getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(BigDecimal qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public Long getSettle() {
        return settle;
    }

    public void setSettle(Long settle) {
        this.settle = settle;
    }

    public Integer getOneCheckDays() {
        return oneCheckDays;
    }

    public void setOneCheckDays(Integer oneCheckDays) {
        this.oneCheckDays = oneCheckDays;
    }

    public Integer getTwoCheckDays() {
        return twoCheckDays;
    }

    public void setTwoCheckDays(Integer twoCheckDays) {
        this.twoCheckDays = twoCheckDays;
    }

    public Integer getReceiveDays() {
        return receiveDays;
    }

    public void setReceiveDays(Integer receiveDays) {
        this.receiveDays = receiveDays;
    }

    public Integer getArchiveDays() {
        return archiveDays;
    }

    public void setArchiveDays(Integer archiveDays) {
        this.archiveDays = archiveDays;
    }

    public FqProjectProcess getFqProjectProcess() {
        return fqProjectProcess;
    }

    public void setFqProjectProcess(FqProjectProcess fqProjectProcess) {
        this.fqProjectProcess = fqProjectProcess;
    }

    public Integer getMapScale() {
        return mapScale;
    }

    public void setMapScale(Integer mapScale) {
        this.mapScale = mapScale;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getGeometry2000() {
        return geometry2000;
    }

    public void setGeometry2000(String geometry2000) {
        this.geometry2000 = geometry2000;
    }

    public String getBufferGeometry() {
        return bufferGeometry;
    }

    public void setBufferGeometry(String bufferGeometry) {
        this.bufferGeometry = bufferGeometry;
    }

    public String getBufferGeometry2000() {
        return bufferGeometry2000;
    }

    public void setBufferGeometry2000(String bufferGeometry2000) {
        this.bufferGeometry2000 = bufferGeometry2000;
    }

    public String getGeometryGauss2000() {
        return geometryGauss2000;
    }

    public void setGeometryGauss2000(String geometryGauss2000) {
        this.geometryGauss2000 = geometryGauss2000;
    }

    public String getBufferGeometryGauss2000() {
        return bufferGeometryGauss2000;
    }

    public void setBufferGeometryGauss2000(String bufferGeometryGauss2000) {
        this.bufferGeometryGauss2000 = bufferGeometryGauss2000;
    }


    public Integer getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(Integer operateStatus) {
        this.operateStatus = operateStatus;
    }

    public String getCustomerContractName() {
        return customerContractName;
    }

    public void setCustomerContractName(String customerContractName) {
        this.customerContractName = customerContractName;
    }

    public String getCustomerContractPhone() {
        return customerContractPhone;
    }

    public void setCustomerContractPhone(String customerContractPhone) {
        this.customerContractPhone = customerContractPhone;
    }

    public Long getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Long registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Long getFirstCheck() {
        return firstCheck;
    }

    public void setFirstCheck(Long firstCheck) {
        this.firstCheck = firstCheck;
    }

    public Long getSecondCheck() {
        return secondCheck;
    }

    public void setSecondCheck(Long secondCheck) {
        this.secondCheck = secondCheck;
    }

    public Long getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(Long transferStatus) {
        this.transferStatus = transferStatus;
    }

    public Long getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Long receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public Long getStampStatus() {
        return stampStatus;
    }

    public void setStampStatus(Long stampStatus) {
        this.stampStatus = stampStatus;
    }

    public Long getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Long checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Long getMarketingConfirm() {
        return marketingConfirm;
    }

    public void setMarketingConfirm(Long marketingConfirm) {
        this.marketingConfirm = marketingConfirm;
    }

    public Integer getLeadTime() {
        return leadTime;
    }

    public LocalDateTime getReceiveCutoffTime() {
        return receiveCutoffTime;
    }

    public void setReceiveCutoffTime(LocalDateTime receiveCutoffTime) {
        this.receiveCutoffTime = receiveCutoffTime;
    }

    public LocalDateTime getRectifyCutoffTime() {
        return rectifyCutoffTime;
    }

    public void setRectifyCutoffTime(LocalDateTime rectifyCutoffTime) {
        this.rectifyCutoffTime = rectifyCutoffTime;
    }

    public Integer getReceiveCutoffTimeDays() {
        return receiveCutoffTimeDays;
    }

    public void setReceiveCutoffTimeDays(Integer receiveCutoffTimeDays) {
        this.receiveCutoffTimeDays = receiveCutoffTimeDays;
    }

    public Integer getRectifyCutoffTimeDays() {
        return rectifyCutoffTimeDays;
    }

    public void setRectifyCutoffTimeDays(Integer rectifyCutoffTimeDays) {
        this.rectifyCutoffTimeDays = rectifyCutoffTimeDays;
    }


    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }

    public Long getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Long workStatus) {
        this.workStatus = workStatus;
    }

    public String getDoTime() {
        return doTime;
    }

    public void setDoTime(String doTime) {
        this.doTime = doTime;
    }

    public BigDecimal getGuGongMoney() {
        return guGongMoney;
    }

    public void setGuGongMoney(BigDecimal guGongMoney) {
        this.guGongMoney = guGongMoney;
    }

    @Excel(name = "产值结算", isIngoreSubList = true, subIndex = 0, headerBackgroundColor = IndexedColors.GREEN)
    private List<SysProjectValue> projectValue;

    @Excel(name = "车辆使用情况", isIngoreSubList = true, subIndex = 1, headerBackgroundColor = IndexedColors.BLUE)
    private List<SysProjectCar> projectCar;

    @Excel(name = "雇工详情", isIngoreSubList = true, subIndex = 2, headerBackgroundColor = IndexedColors.BROWN)
    private List<ProjectEmployee> projectEmployee;

    @Excel(name = "分包详情", isIngoreSubList = true, subIndex = 3, headerBackgroundColor = IndexedColors.INDIGO)
    private List<ProjectSubcontract> projectSubcontract;

    private List<SysReviewSub> reviewSub;

    private SysReviewSub reviewSubOne;

    private Long isJqr;

    private Long status;

    private Long subpackageType;

    private String afterTime;

    private String viewProjectId;

    public String getViewProjectId() {
        return viewProjectId;
    }

    public void setViewProjectId(String viewProjectId) {
        this.viewProjectId = viewProjectId;
    }

    public String getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(String afterTime) {
        this.afterTime = afterTime;
    }

    public SysReviewSub getReviewSubOne() {
        return reviewSubOne;
    }

    public void setReviewSubOne(SysReviewSub reviewSubOne) {
        this.reviewSubOne = reviewSubOne;
    }

    public String getFbWorkload() {
        return fbWorkload;
    }

    public void setFbWorkload(String fbWorkload) {
        this.fbWorkload = fbWorkload;
    }

    public Long getSubpackageType() {
        return subpackageType;
    }

    public void setSubpackageType(Long subpackageType) {
        this.subpackageType = subpackageType;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getIsJqr() {
        return isJqr;
    }

    private BigDecimal fbMoney;

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public void setIsJqr(Long isJqr) {
        this.isJqr = isJqr;
    }

    public BigDecimal getFbMoney() {
        return fbMoney;
    }

    public void setFbMoney(BigDecimal fbMoney) {
        this.fbMoney = fbMoney;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }

    public List<ProjectEmployee> getProjectEmployee() {
        return projectEmployee;
    }

    public void setProjectEmployee(List<ProjectEmployee> projectEmployee) {
        this.projectEmployee = projectEmployee;
    }

    public List<ProjectSubcontract> getProjectSubcontract() {
        return projectSubcontract;
    }

    public void setProjectSubcontract(List<ProjectSubcontract> projectSubcontract) {
        this.projectSubcontract = projectSubcontract;
    }

    public List<SysReviewSub> getReviewSub() {
        return reviewSub;
    }

    public void setReviewSub(List<SysReviewSub> reviewSub) {
        this.reviewSub = reviewSub;
    }

    public List<SysProjectCar> getProjectCar() {
        return projectCar;
    }

    public void setProjectCar(List<SysProjectCar> projectCar) {
        this.projectCar = projectCar;
    }

    public Integer getOutputStatus() {
        return outputStatus;
    }

    public void setOutputStatus(Integer outputStatus) {
        this.outputStatus = outputStatus;
    }

    public String getWorkcontentAlias() {
        return workcontentAlias;
    }

    public void setWorkcontentAlias(String workcontentAlias) {
        this.workcontentAlias = workcontentAlias;
    }

    public List<SysProjectValue> getProjectValue() {
        return projectValue;
    }

    public void setProjectValue(List<SysProjectValue> projectValue) {
        this.projectValue = projectValue;
    }

    public Integer getIsCarRegister() {
        return isCarRegister;
    }

    public void setIsCarRegister(Integer isCarRegister) {
        this.isCarRegister = isCarRegister;
    }

    public Date getTwoCheckTime() {
        return twoCheckTime;
    }

    public void setTwoCheckTime(Date twoCheckTime) {
        this.twoCheckTime = twoCheckTime;
    }

    public Integer getIsTwoCheck() {
        return isTwoCheck;
    }

    public void setIsTwoCheck(Integer isTwoCheck) {
        this.isTwoCheck = isTwoCheck;
    }

    public void setProjectId(Long projectId)
    {
        this.projectId = projectId;
    }

    public Long getProjectId()
    {
        return projectId;
    }
    public void setProjectNameAlias(String projectNameAlias)
    {
        this.projectNameAlias = projectNameAlias;
    }

    public String getProjectNameAlias()
    {
        return projectNameAlias;
    }
    public void setProjectNum(String projectNum) 
    {
        this.projectNum = projectNum;
    }

    public String getProjectNum() 
    {
        return projectNum;
    }
    public void setProjectType(String projectType) 
    {
        this.projectType = projectType;
    }

    public String getProjectType() 
    {
        return projectType;
    }
    public void setRegisterTime(String registerTime) 
    {
        this.registerTime = registerTime;
    }

    public String getRegisterTime() 
    {
        return registerTime;
    }
    public void setReceptionist(String receptionist) 
    {
        this.receptionist = receptionist;
    }

    public String getReceptionist() 
    {
        return receptionist;
    }
    public void setWorkloadAlias(String workloadAlias)
    {
        this.workloadAlias = workloadAlias;
    }

    public String getWorkloadAlias()
    {
        return workloadAlias;
    }
    public void setUserNameAlias(String userNameAlias)
    {
        this.userNameAlias = userNameAlias;
    }

    public String getUserNameAlias()
    {
        return userNameAlias;
    }
    public void setRequesterAlias(String requesterAlias)
    {
        this.requesterAlias = requesterAlias;
    }

    public String getRequesterAlias()
    {
        return requesterAlias;
    }
    public void setProjectStartAlias(String projectStartAlias)
    {
        this.projectStartAlias = projectStartAlias;
    }

    public String getProjectStartAlias()
    {
        return projectStartAlias;
    }
    public void setProjectEndAlias(String projectEndAlias)
    {
        this.projectEndAlias = projectEndAlias;
    }

    public String getProjectEndAlias()
    {
        return projectEndAlias;
    }
    public void setOneCheck(String oneCheck) 
    {
        this.oneCheck = oneCheck;
    }

    public String getOneCheck() 
    {
        return oneCheck;
    }
    public void setTwoCheck(String twoCheck) 
    {
        this.twoCheck = twoCheck;
    }

    public String getTwoCheck() 
    {
        return twoCheck;
    }
    public void setNoticeTime(String noticeTime) 
    {
        this.noticeTime = noticeTime;
    }

    public String getNoticeTime() 
    {
        return noticeTime;
    }
    public void setProjectTime(String projectTime) 
    {
        this.projectTime = projectTime;
    }

    public String getProjectTime() 
    {
        return projectTime;
    }
    public void setDeliveryTime(String deliveryTime) 
    {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryTime() 
    {
        return deliveryTime;
    }
    public void setProjectMoneyAlias(BigDecimal projectMoneyAlias)
    {
        this.projectMoneyAlias = projectMoneyAlias;
    }

    public BigDecimal getProjectMoneyAlias()
    {
        return projectMoneyAlias;
    }
    public void setOperate(BigDecimal operate) 
    {
        this.operate = operate;
    }

    public BigDecimal getOperate() 
    {
        return operate;
    }
    public void setOperateUser(String operateUser) 
    {
        this.operateUser = operateUser;
    }

    public String getOperateUser() 
    {
        return operateUser;
    }
    public void setOperateTime(Date operateTime) 
    {
        this.operateTime = operateTime;
    }

    public Date getOperateTime() 
    {
        return operateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("projectName", getProjectNameAlias())
            .append("projectNum", getProjectNum())
            .append("projectType", getProjectType())
            .append("registerTime", getRegisterTime())
            .append("receptionist", getReceptionist())
            .append("workload", getWorkloadAlias())
            .append("userName", getUserNameAlias())
            .append("requester", getRequesterAlias())
            .append("projectStart", getProjectStartAlias())
            .append("projectEnd", getProjectEndAlias())
            .append("oneCheck", getOneCheck())
            .append("twoCheck", getTwoCheck())
            .append("noticeTime", getNoticeTime())
            .append("projectTime", getProjectTime())
            .append("deliveryTime", getDeliveryTime())
            .append("projectMoney", getProjectMoneyAlias())
            .append("operate", getOperate())
            .append("operateUser", getOperateUser())
            .append("operateTime", getOperateTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
