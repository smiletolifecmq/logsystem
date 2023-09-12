package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 项目关联审核对象 sys_project_relation
 * 
 * @author ruoyi
 * @date 2023-09-08
 */
public class SysProjectRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目关联ID */
    private Long relationId;

    /** 审核类型1:雇工，2:分包 */
    @Excel(name = "审核类型1:雇工，2:分包")
    private Integer reviewType;

    /** 审核ID */
    @Excel(name = "审核ID")
    private Long reviewId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    public void setRelationId(Long relationId) 
    {
        this.relationId = relationId;
    }

    public Long getRelationId() 
    {
        return relationId;
    }
    public void setReviewType(Integer reviewType) 
    {
        this.reviewType = reviewType;
    }

    public Integer getReviewType() 
    {
        return reviewType;
    }
    public void setReviewId(Long reviewId) 
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId() 
    {
        return reviewId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("relationId", getRelationId())
            .append("reviewType", getReviewType())
            .append("reviewId", getReviewId())
            .append("projectId", getProjectId())
            .toString();
    }
}
