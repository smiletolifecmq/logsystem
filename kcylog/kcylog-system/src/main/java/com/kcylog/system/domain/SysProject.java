package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
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
    @Excel(name = "项目名称", needMerge = true)
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


    /** 工程负责人 */


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

    private Date twoCheckTime;

    private Integer isTwoCheck;

    private Integer outputStatus;

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

    /** 填写经营产值人 */
    private String operateUser;

    private Integer isCarRegister;

    /** 填写经营产值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date operateTime;

    @Excel(name = "产值结算")
    private List<SysProjectValue> projectValue;

//    @Excel(name = "车辆使用情况")
    private List<SysProjectCar> projectCar;


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
