package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 协作单位对象 sys_cooperation_unit
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public class SysCooperationUnit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 协作单位ID */
    private Long unitId;

    /** 协作单位名称 */
    @Excel(name = "协作单位名称")
    private String unitName;

    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .toString();
    }
}
