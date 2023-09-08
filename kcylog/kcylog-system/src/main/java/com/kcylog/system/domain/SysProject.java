package com.kcylog.system.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

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
    private String projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectNum;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String projectType;

    /** 登记时间 */
    @Excel(name = "登记时间")
    private String registerTime;

    /** 接待人 */
    @Excel(name = "接待人")
    private String receptionist;

    /** 工程内容 */
    @Excel(name = "工程内容")
    private String workload;

    /** 工程负责人 */
    @Excel(name = "工程负责人")
    private String userName;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String requester;

    /** 安排开始时间 */
    @Excel(name = "安排开始时间")
    private String projectStart;

    /** 安排结束时间 */
    @Excel(name = "安排结束时间")
    private String projectEnd;

    /** 一检时间 */
    @Excel(name = "一检时间")
    private String oneCheck;

    /** 二检时间 */
    @Excel(name = "二检时间")
    private String twoCheck;

    /** 通知出件时间 */
    @Excel(name = "通知出件时间")
    private String noticeTime;

    /** 项目出件时间 */
    @Excel(name = "项目出件时间")
    private String projectTime;

    /** 送达时间 */
    @Excel(name = "送达时间")
    private String deliveryTime;

    /** 项目金额 */
    @Excel(name = "项目金额")
    private BigDecimal porjectMoney;

    /** 经营产值 */
    @Excel(name = "经营产值")
    private BigDecimal operate;

    /** 填写经营产值人 */
    @Excel(name = "填写经营产值人")
    private String operateUser;

    /** 填写经营产值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "填写经营产值时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operateTime;

    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
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
    public void setWorkload(String workload) 
    {
        this.workload = workload;
    }

    public String getWorkload() 
    {
        return workload;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setRequester(String requester) 
    {
        this.requester = requester;
    }

    public String getRequester() 
    {
        return requester;
    }
    public void setProjectStart(String projectStart) 
    {
        this.projectStart = projectStart;
    }

    public String getProjectStart() 
    {
        return projectStart;
    }
    public void setProjectEnd(String projectEnd) 
    {
        this.projectEnd = projectEnd;
    }

    public String getProjectEnd() 
    {
        return projectEnd;
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
    public void setPorjectMoney(BigDecimal porjectMoney) 
    {
        this.porjectMoney = porjectMoney;
    }

    public BigDecimal getPorjectMoney() 
    {
        return porjectMoney;
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
            .append("projectName", getProjectName())
            .append("projectNum", getProjectNum())
            .append("projectType", getProjectType())
            .append("registerTime", getRegisterTime())
            .append("receptionist", getReceptionist())
            .append("workload", getWorkload())
            .append("userName", getUserName())
            .append("requester", getRequester())
            .append("projectStart", getProjectStart())
            .append("projectEnd", getProjectEnd())
            .append("oneCheck", getOneCheck())
            .append("twoCheck", getTwoCheck())
            .append("noticeTime", getNoticeTime())
            .append("projectTime", getProjectTime())
            .append("deliveryTime", getDeliveryTime())
            .append("porjectMoney", getPorjectMoney())
            .append("operate", getOperate())
            .append("operateUser", getOperateUser())
            .append("operateTime", getOperateTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
