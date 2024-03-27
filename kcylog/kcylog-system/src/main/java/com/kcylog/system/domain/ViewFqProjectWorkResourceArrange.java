package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 人员安排比例视图对象 view_fq_project_work_resource_arrange
 * 
 * @author ruoyi
 * @date 2024-03-27
 */
public class ViewFqProjectWorkResourceArrange extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal performanceRate;

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setPerformanceRate(BigDecimal performanceRate) 
    {
        this.performanceRate = performanceRate;
    }

    public BigDecimal getPerformanceRate() 
    {
        return performanceRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userName", getUserName())
            .append("performanceRate", getPerformanceRate())
            .toString();
    }
}
