package com.kcylog.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 地理员工请假对象 sys_geo_holiday
 * 
 * @author ruoyi
 * @date 2024-01-02
 */
public class SysGeoHoliday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long holidayId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date start;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date end;

    /** 天数 */
    @Excel(name = "天数")
    private Double day;

    public void setHolidayId(Long holidayId) 
    {
        this.holidayId = holidayId;
    }

    public Long getHolidayId() 
    {
        return holidayId;
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
    public void setStart(Date start) 
    {
        this.start = start;
    }

    public Date getStart() 
    {
        return start;
    }
    public void setEnd(Date end) 
    {
        this.end = end;
    }

    public Date getEnd() 
    {
        return end;
    }
    public void setDay(Double day) 
    {
        this.day = day;
    }

    public Double getDay() 
    {
        return day;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("holidayId", getHolidayId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("start", getStart())
            .append("end", getEnd())
            .append("day", getDay())
            .append("remark", getRemark())
            .toString();
    }
}
