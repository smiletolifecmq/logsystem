package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 评定详情表对象 sys_geo_assess_info
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public class SysGeoAssessInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long assessInfoId;

    /** 评定自增ID */
    @Excel(name = "评定自增ID")
    private Long assessId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    /** 配合系数 */
    @Excel(name = "配合系数")
    private Double fitCoefficient;

    /** 工期系数 */
    @Excel(name = "工期系数")
    private Double workCoefficient;

    /** 评定日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评定日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date assessDate;

    public void setAssessInfoId(Long assessInfoId) 
    {
        this.assessInfoId = assessInfoId;
    }

    public Long getAssessInfoId() 
    {
        return assessInfoId;
    }
    public void setAssessId(Long assessId) 
    {
        this.assessId = assessId;
    }

    public Long getAssessId() 
    {
        return assessId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setFitCoefficient(Double fitCoefficient) 
    {
        this.fitCoefficient = fitCoefficient;
    }

    public Double getFitCoefficient() 
    {
        return fitCoefficient;
    }
    public void setWorkCoefficient(Double workCoefficient) 
    {
        this.workCoefficient = workCoefficient;
    }

    public Double getWorkCoefficient() 
    {
        return workCoefficient;
    }
    public void setAssessDate(Date assessDate) 
    {
        this.assessDate = assessDate;
    }

    public Date getAssessDate() 
    {
        return assessDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("assessInfoId", getAssessInfoId())
            .append("assessId", getAssessId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("fitCoefficient", getFitCoefficient())
            .append("workCoefficient", getWorkCoefficient())
            .append("remark", getRemark())
            .append("assessDate", getAssessDate())
            .toString();
    }
}
