package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 作业办结时间对象 view_fq_project_work_done
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
public class ViewFqProjectWorkDone extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long projectId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private LocalDateTime doTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long opType;

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setDoTime(LocalDateTime doTime)
    {
        this.doTime = doTime;
    }

    public LocalDateTime getDoTime()
    {
        return doTime;
    }
    public void setOpType(Long opType) 
    {
        this.opType = opType;
    }

    public Long getOpType() 
    {
        return opType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("doTime", getDoTime())
            .append("opType", getOpType())
            .toString();
    }
}
