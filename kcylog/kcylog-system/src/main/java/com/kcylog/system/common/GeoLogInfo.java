package com.kcylog.system.common;

import com.kcylog.common.annotation.Excel;

import java.util.Date;

public class GeoLogInfo {
    @Excel(name = "日志日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logDate;

    @Excel(name = "用户名称")
    private String userName;

    @Excel(name = "工作内容")
    private String gznr;

    @Excel(name = "说明")
    private String sm;

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }
}
