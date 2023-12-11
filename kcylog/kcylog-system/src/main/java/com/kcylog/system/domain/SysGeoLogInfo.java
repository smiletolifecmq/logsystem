package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 地理部门日志详情对象 sys_geo_log_info
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class SysGeoLogInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志详情自增ID */
    private Long logInfoId;

    /** 日志ID */
    @Excel(name = "日志ID")
    private Long logId;

    /** 类型树 */
    @Excel(name = "类型树")
    private String typeArr;

    /** 类型ID */
    @Excel(name = "类型ID")
    private Long typeId;

    /** 难度，1简单，2一般，3困难 */
    @Excel(name = "难度，1简单，2一般，3困难")
    private Long difficulty;

    /** 难度系数，简单-0.8，一般-1，困难-1.2 */
    @Excel(name = "难度系数，简单-0.8，一般-1，困难-1.2")
    private Double difficultyDegree;

    /** 日志日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日志日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logDate;

    /** 工作量 */
    @Excel(name = "工作量")
    private Double workload;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal typeMoney;

    /** 关联项目ID */
    @Excel(name = "关联项目ID")
    private Long projectId;

    public void setLogInfoId(Long logInfoId) 
    {
        this.logInfoId = logInfoId;
    }

    public Long getLogInfoId() 
    {
        return logInfoId;
    }
    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }
    public void setTypeArr(String typeArr) 
    {
        this.typeArr = typeArr;
    }

    public String getTypeArr() 
    {
        return typeArr;
    }
    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setDifficulty(Long difficulty) 
    {
        this.difficulty = difficulty;
    }

    public Long getDifficulty() 
    {
        return difficulty;
    }
    public void setDifficultyDegree(Double difficultyDegree) 
    {
        this.difficultyDegree = difficultyDegree;
    }

    public Double getDifficultyDegree() 
    {
        return difficultyDegree;
    }
    public void setLogDate(Date logDate) 
    {
        this.logDate = logDate;
    }

    public Date getLogDate() 
    {
        return logDate;
    }
    public void setWorkload(Double workload) 
    {
        this.workload = workload;
    }

    public Double getWorkload() 
    {
        return workload;
    }
    public void setTypeMoney(BigDecimal typeMoney) 
    {
        this.typeMoney = typeMoney;
    }

    public BigDecimal getTypeMoney() 
    {
        return typeMoney;
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
            .append("logInfoId", getLogInfoId())
            .append("logId", getLogId())
            .append("typeArr", getTypeArr())
            .append("typeId", getTypeId())
            .append("difficulty", getDifficulty())
            .append("difficultyDegree", getDifficultyDegree())
            .append("logDate", getLogDate())
            .append("workload", getWorkload())
            .append("typeMoney", getTypeMoney())
            .append("projectId", getProjectId())
            .toString();
    }
}
