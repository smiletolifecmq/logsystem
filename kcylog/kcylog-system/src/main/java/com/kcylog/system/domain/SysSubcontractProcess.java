package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 分包审核单流程对象 sys_subcontract_process
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public class SysSubcontractProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分包审核单流程ID */
    private Long subcontractProcessId;

    /** 分包单ID */
    @Excel(name = "分包单ID")
    private Long subcontractId;

    /** 审核人ID */
    @Excel(name = "审核人ID")
    private Long userId;

    /** 审核状态(0:未开始;1进行中;2通过;3:未通过) */
    @Excel(name = "审核状态(0:未开始;1进行中;2通过;3:未通过)")
    private Long status;

    /** 审核流程节点 */
    @Excel(name = "审核流程节点")
    private Long node;

    /** 理由 */
    @Excel(name = "理由")
    private String reason;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    /**
     * 用户对象
     */
    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public void setSubcontractProcessId(Long subcontractProcessId) 
    {
        this.subcontractProcessId = subcontractProcessId;
    }

    public Long getSubcontractProcessId() 
    {
        return subcontractProcessId;
    }
    public void setSubcontractId(Long subcontractId) 
    {
        this.subcontractId = subcontractId;
    }

    public Long getSubcontractId() 
    {
        return subcontractId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setNode(Long node) 
    {
        this.node = node;
    }

    public Long getNode() 
    {
        return node;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setReviewTime(Date reviewTime) 
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() 
    {
        return reviewTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("subcontractProcessId", getSubcontractProcessId())
            .append("subcontractId", getSubcontractId())
            .append("userId", getUserId())
            .append("status", getStatus())
            .append("node", getNode())
            .append("reason", getReason())
            .append("reviewTime", getReviewTime())
            .toString();
    }
}
