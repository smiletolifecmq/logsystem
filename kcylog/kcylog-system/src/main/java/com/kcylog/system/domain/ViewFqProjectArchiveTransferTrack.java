package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 移交流程对象 view_fq_project_archive_transfer_track
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
public class ViewFqProjectArchiveTransferTrack extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long projectId;

    private String projectCode;

    /** 登记(1："登记中"，2："登记办结") */
    private Long registerStatus;

    /** 作业办结(1： "待办结"，2："作业中"，3："作业完成"，4："作业办结") */
    private Long workStatus;

    /** 移交（1："未移交"，2："移交办结") */
    private Long transferStatus;

    /** 收件（ 1： "未收件"，2："收件办结") */
    private Long receiveStatus;

    /** 盖章（1："未盖章"；2："已盖章"；3："盖章拒绝"；4："无需盖章"） */
    private Long stampStatus;

    /** 是否确认盖章 */
    @Excel(name = "是否确认盖章")
    private Long marketingConfirm;

    /** 验收（1："未验收"；2："验收办结"；3："验收拒绝"，4： "验收通过"） */
    private Long checkStatus;

    /** 收件截止时间（最后一次二检通过时间加上5个工作日） */
    @Excel(name = "收件截止时间", readConverterExp = "最=后一次二检通过时间加上5个工作日")
    private LocalDateTime receiveCutoffTime;

    /** 整改截止时间（第一次被退回整改的时间加上5个工作日） */
    @Excel(name = "整改截止时间", readConverterExp = "第=一次被退回整改的时间加上5个工作日")
    private LocalDateTime rectifyCutoffTime;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setRegisterStatus(Long registerStatus) 
    {
        this.registerStatus = registerStatus;
    }

    public Long getRegisterStatus() 
    {
        return registerStatus;
    }
    public void setWorkStatus(Long workStatus) 
    {
        this.workStatus = workStatus;
    }

    public Long getWorkStatus() 
    {
        return workStatus;
    }
    public void setTransferStatus(Long transferStatus) 
    {
        this.transferStatus = transferStatus;
    }

    public Long getTransferStatus() 
    {
        return transferStatus;
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
    public void setMarketingConfirm(Long marketingConfirm) 
    {
        this.marketingConfirm = marketingConfirm;
    }

    public Long getMarketingConfirm() 
    {
        return marketingConfirm;
    }
    public void setCheckStatus(Long checkStatus) 
    {
        this.checkStatus = checkStatus;
    }

    public Long getCheckStatus() 
    {
        return checkStatus;
    }
    public void setReceiveCutoffTime(LocalDateTime receiveCutoffTime)
    {
        this.receiveCutoffTime = receiveCutoffTime;
    }

    public LocalDateTime getReceiveCutoffTime()
    {
        return receiveCutoffTime;
    }
    public void setRectifyCutoffTime(LocalDateTime rectifyCutoffTime)
    {
        this.rectifyCutoffTime = rectifyCutoffTime;
    }

    public LocalDateTime getRectifyCutoffTime()
    {
        return rectifyCutoffTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("registerStatus", getRegisterStatus())
            .append("workStatus", getWorkStatus())
            .append("transferStatus", getTransferStatus())
            .append("receiveStatus", getReceiveStatus())
            .append("stampStatus", getStampStatus())
            .append("marketingConfirm", getMarketingConfirm())
            .append("checkStatus", getCheckStatus())
            .append("receiveCutofftime", getReceiveCutoffTime())
            .append("rectifyCutofftime", getRectifyCutoffTime())
            .toString();
    }
}
