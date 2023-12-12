package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 地理部门日志对象 sys_geo_log
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public class SysGeoLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long logId;

    /** 日志日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日志日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logDate;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    private List<Long> lookUserIds;

    private List<SysGeoLogInfo> geoLogInfo;

    public List<SysGeoLogInfo> getGeoLogInfo() {
        return geoLogInfo;
    }

    public void setGeoLogInfo(List<SysGeoLogInfo> geoLogInfo) {
        this.geoLogInfo = geoLogInfo;
    }

    public List<Long> getLookUserIds() {
        return lookUserIds;
    }

    public void setLookUserIds(List<Long> lookUserIds) {
        this.lookUserIds = lookUserIds;
    }

    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }
    public void setLogDate(Date logDate) 
    {
        this.logDate = logDate;
    }

    public Date getLogDate() 
    {
        return logDate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("logDate", getLogDate())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
