package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 类型配置表对象 sys_geo_type
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
public class SysGeoType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类型自增ID */
    private Long typeId;

    /** 类型名称 */
    @Excel(name = "类型名称")
    private String typeName;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal typeMoney;

    /** 父ID */
    @Excel(name = "父ID")
    private Long parentId;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 是否有难易程度，0否1是 */
    @Excel(name = "是否有难易程度，0否1是")
    private Long degree;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setTypeMoney(BigDecimal typeMoney) 
    {
        this.typeMoney = typeMoney;
    }

    public BigDecimal getTypeMoney() 
    {
        return typeMoney;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setDegree(Long degree) 
    {
        this.degree = degree;
    }

    public Long getDegree() 
    {
        return degree;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .append("typeMoney", getTypeMoney())
            .append("parentId", getParentId())
            .append("remark", getRemark())
            .append("unit", getUnit())
            .append("degree", getDegree())
            .toString();
    }
}
