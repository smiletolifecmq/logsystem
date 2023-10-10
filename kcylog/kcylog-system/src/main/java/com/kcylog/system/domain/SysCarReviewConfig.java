package com.kcylog.system.domain;

import com.kcylog.common.core.domain.entity.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 车辆审核流程配置对象 sys_car_review_config
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public class SysCarReviewConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程配置表ID */
    private Long reviewConfigId;

    /** 配置标题 */
    @Excel(name = "配置标题")
    private String title;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /**
     * 部门对象
     */
    private SysDept dept;

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public void setReviewConfigId(Long reviewConfigId) 
    {
        this.reviewConfigId = reviewConfigId;
    }

    public Long getReviewConfigId() 
    {
        return reviewConfigId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("reviewConfigId", getReviewConfigId())
            .append("title", getTitle())
            .append("deptId", getDeptId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
