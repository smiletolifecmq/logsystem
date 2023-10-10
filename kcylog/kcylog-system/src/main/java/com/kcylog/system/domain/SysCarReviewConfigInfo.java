package com.kcylog.system.domain;

import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 车辆使用流程配置过程对象 sys_car_review_config_info
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public class SysCarReviewConfigInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程配置过程ID */
    private Long infoId;

    /** 流程配置ID */
    @Excel(name = "流程配置ID")
    private Long reviewConfigId;

    /** 审核节点 */
    @Excel(name = "审核节点")
    private String node;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long userId;

    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public void setInfoId(Long infoId) 
    {
        this.infoId = infoId;
    }

    public Long getInfoId() 
    {
        return infoId;
    }
    public void setReviewConfigId(Long reviewConfigId) 
    {
        this.reviewConfigId = reviewConfigId;
    }

    public Long getReviewConfigId() 
    {
        return reviewConfigId;
    }
    public void setNode(String node) 
    {
        this.node = node;
    }

    public String getNode() 
    {
        return node;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("infoId", getInfoId())
            .append("reviewConfigId", getReviewConfigId())
            .append("node", getNode())
            .append("userId", getUserId())
            .toString();
    }
}
