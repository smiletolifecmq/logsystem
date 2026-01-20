package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 【请填写功能名称】对象 view_bcyj
 * 
 * @author ruoyi
 * @date 2026-01-19
 */
public class ViewBcyj extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工程编号 */
    @Excel(name = "工程编号")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 收件状态1:未收件，2:收件办结 */
    @Excel(name = "收件状态1:未收件，2:收件办结")
    private Long receiveStatus;

    /** 盖章状态1未盖章，2已盖章 */
    @Excel(name = "盖章状态1未盖章，2已盖章")
    private Long stampStatus;

    /** 验收状态1未验收2验收办结3验收拒绝 */
    @Excel(name = "验收状态1未验收2验收办结3验收拒绝")
    private Long checkStatus;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String customerName;

    /** 负责人 */
    @Excel(name = "负责人")
    private String managerUserName;

    /** 二检通过日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "二检通过日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date secondCheckTime;

    /** 收件截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收件截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveCutoffTime;

    /** 移交人 */
    @Excel(name = "移交人")
    private String yjUserName;

    /** 移交日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "移交日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yjsj;

    /** 收件人 */
    @Excel(name = "收件人")
    private String sjUserName;

    /** 收件日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收件日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sjsj;

    /** 退回整改日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退回整改日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date zgsj;

    /** 整改截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rectifyCutoffTime;

    /** 盖章日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盖章日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gzsj;

    /** 验收人 */
    @Excel(name = "验收人")
    private String ysUserName;

    /** 验收日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yssj;

    /** 是否有归档资料0否1是 */
    @Excel(name = "是否有归档资料0否1是")
    private Long isArchive;

    /** 部门 */
    @Excel(name = "部门")
    private String jobOrgName;

    private Long issjcq;

    private Long iszgcq;

    private Long sjmscq;

    private Long zgmscq;

    public Long getIssjcq() {
        return issjcq;
    }

    public void setIssjcq(Long issjcq) {
        this.issjcq = issjcq;
    }

    public Long getIszgcq() {
        return iszgcq;
    }

    public void setIszgcq(Long iszgcq) {
        this.iszgcq = iszgcq;
    }

    public Long getSjmscq() {
        return sjmscq;
    }

    public void setSjmscq(Long sjmscq) {
        this.sjmscq = sjmscq;
    }

    public Long getZgmscq() {
        return zgmscq;
    }

    public void setZgmscq(Long zgmscq) {
        this.zgmscq = zgmscq;
    }

    public void setProjectCode(String projectCode)
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode() 
    {
        return projectCode;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setReceiveStatus(Long receiveStatus) 
    {
        this.receiveStatus = receiveStatus;
    }

    public Long getReceiveStatus() 
    {
        return receiveStatus;
    }
    public void setStampStatus(Long stampStatus) 
    {
        this.stampStatus = stampStatus;
    }

    public Long getStampStatus() 
    {
        return stampStatus;
    }
    public void setCheckStatus(Long checkStatus) 
    {
        this.checkStatus = checkStatus;
    }

    public Long getCheckStatus() 
    {
        return checkStatus;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setManagerUserName(String managerUserName) 
    {
        this.managerUserName = managerUserName;
    }

    public String getManagerUserName() 
    {
        return managerUserName;
    }
    public void setSecondCheckTime(Date secondCheckTime) 
    {
        this.secondCheckTime = secondCheckTime;
    }

    public Date getSecondCheckTime() 
    {
        return secondCheckTime;
    }
    public void setReceiveCutoffTime(Date receiveCutoffTime) 
    {
        this.receiveCutoffTime = receiveCutoffTime;
    }

    public Date getReceiveCutoffTime() 
    {
        return receiveCutoffTime;
    }
    public void setYjUserName(String yjUserName) 
    {
        this.yjUserName = yjUserName;
    }

    public String getYjUserName() 
    {
        return yjUserName;
    }
    public void setYjsj(Date yjsj) 
    {
        this.yjsj = yjsj;
    }

    public Date getYjsj() 
    {
        return yjsj;
    }
    public void setSjUserName(String sjUserName) 
    {
        this.sjUserName = sjUserName;
    }

    public String getSjUserName() 
    {
        return sjUserName;
    }
    public void setSjsj(Date sjsj) 
    {
        this.sjsj = sjsj;
    }

    public Date getSjsj() 
    {
        return sjsj;
    }
    public void setZgsj(Date zgsj) 
    {
        this.zgsj = zgsj;
    }

    public Date getZgsj() 
    {
        return zgsj;
    }
    public void setRectifyCutoffTime(Date rectifyCutoffTime) 
    {
        this.rectifyCutoffTime = rectifyCutoffTime;
    }

    public Date getRectifyCutoffTime() 
    {
        return rectifyCutoffTime;
    }
    public void setGzsj(Date gzsj) 
    {
        this.gzsj = gzsj;
    }

    public Date getGzsj() 
    {
        return gzsj;
    }
    public void setYsUserName(String ysUserName) 
    {
        this.ysUserName = ysUserName;
    }

    public String getYsUserName() 
    {
        return ysUserName;
    }
    public void setYssj(Date yssj) 
    {
        this.yssj = yssj;
    }

    public Date getYssj() 
    {
        return yssj;
    }
    public void setIsArchive(Long isArchive) 
    {
        this.isArchive = isArchive;
    }

    public Long getIsArchive() 
    {
        return isArchive;
    }
    public void setJobOrgName(String jobOrgName) 
    {
        this.jobOrgName = jobOrgName;
    }

    public String getJobOrgName() 
    {
        return jobOrgName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectCode", getProjectCode())
            .append("projectName", getProjectName())
            .append("receiveStatus", getReceiveStatus())
            .append("stampStatus", getStampStatus())
            .append("checkStatus", getCheckStatus())
            .append("customerName", getCustomerName())
            .append("managerUserName", getManagerUserName())
            .append("secondCheckTime", getSecondCheckTime())
            .append("receiveCutoffTime", getReceiveCutoffTime())
            .append("yjUserName", getYjUserName())
            .append("yjsj", getYjsj())
            .append("sjUserName", getSjUserName())
            .append("sjsj", getSjsj())
            .append("zgsj", getZgsj())
            .append("rectifyCutoffTime", getRectifyCutoffTime())
            .append("gzsj", getGzsj())
            .append("ysUserName", getYsUserName())
            .append("yssj", getYssj())
            .append("isArchive", getIsArchive())
            .append("jobOrgName", getJobOrgName())
            .toString();
    }
}
