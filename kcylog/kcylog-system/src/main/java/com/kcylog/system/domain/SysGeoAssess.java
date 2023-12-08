package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 评定表对象 sys_geo_assess
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public class SysGeoAssess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评定ID */
    private Long assessId;

    /** 评定日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评定日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date assessDate;

    /** 评定人ID */
    @Excel(name = "评定人ID")
    private Long assessUserId;

    /** 评定人名称 */
    @Excel(name = "评定人名称")
    private String assessUserName;

    public void setAssessId(Long assessId) 
    {
        this.assessId = assessId;
    }

    public Long getAssessId() 
    {
        return assessId;
    }
    public void setAssessDate(Date assessDate) 
    {
        this.assessDate = assessDate;
    }

    public Date getAssessDate() 
    {
        return assessDate;
    }
    public void setAssessUserId(Long assessUserId) 
    {
        this.assessUserId = assessUserId;
    }

    public Long getAssessUserId() 
    {
        return assessUserId;
    }
    public void setAssessUserName(String assessUserName) 
    {
        this.assessUserName = assessUserName;
    }

    public String getAssessUserName() 
    {
        return assessUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("assessId", getAssessId())
            .append("assessDate", getAssessDate())
            .append("assessUserId", getAssessUserId())
            .append("assessUserName", getAssessUserName())
            .toString();
    }
}
