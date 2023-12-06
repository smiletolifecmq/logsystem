package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 个人系数对象 sys_geo_user_coefficient
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
public class SysGeoUserCoefficient extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long coefficientId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String name;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 系数 */
    @Excel(name = "系数")
    private Double coefficient;

    public void setCoefficientId(Long coefficientId) 
    {
        this.coefficientId = coefficientId;
    }

    public Long getCoefficientId() 
    {
        return coefficientId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setCoefficient(Double coefficient) 
    {
        this.coefficient = coefficient;
    }

    public Double getCoefficient() 
    {
        return coefficient;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("coefficientId", getCoefficientId())
            .append("name", getName())
            .append("userId", getUserId())
            .append("coefficient", getCoefficient())
            .toString();
    }
}
