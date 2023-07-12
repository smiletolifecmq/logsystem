package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 结算单关联对象 sys_settlement_associate
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
public class SysSettlementAssociate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long associateId;

    /** 结算单ID */
    @Excel(name = "结算单ID")
    private Long settlementId;

    /** 审核单ID */
    @Excel(name = "审核单ID")
    private Long reviewId;

    public void setAssociateId(Long associateId) 
    {
        this.associateId = associateId;
    }

    public Long getAssociateId() 
    {
        return associateId;
    }
    public void setSettlementId(Long settlementId) 
    {
        this.settlementId = settlementId;
    }

    public Long getSettlementId() 
    {
        return settlementId;
    }
    public void setReviewId(Long reviewId) 
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId() 
    {
        return reviewId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("associateId", getAssociateId())
            .append("settlementId", getSettlementId())
            .append("reviewId", getReviewId())
            .toString();
    }
}
