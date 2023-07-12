package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 结算单对象 sys_review_settlement
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
public class SysReviewSettlement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 结算单ID */
    private Long settlementId;

    /** 结算单名称 */
    @Excel(name = "结算单名称")
    private String settlementName;

    /** 创建人 */
    @Excel(name = "创建人")
    private String userName;

    public void setSettlementId(Long settlementId) 
    {
        this.settlementId = settlementId;
    }

    public Long getSettlementId() 
    {
        return settlementId;
    }
    public void setSettlementName(String settlementName) 
    {
        this.settlementName = settlementName;
    }

    public String getSettlementName() 
    {
        return settlementName;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("settlementId", getSettlementId())
            .append("settlementName", getSettlementName())
            .append("userName", getUserName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
