package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目对象 sys_project
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public class SysProjectNbcz
{
    /** 项目名称 */
    @Excel(name = "项目名称", needMerge = true)
    private String projectNameAlias;

    /** 项目编号 */
    @Excel(name = "项目编号", needMerge = true)
    private String projectNum;

    /** 项目类型 */
    @Excel(name = "项目类型", needMerge = true)
    private String projectType;

    @Excel(name = "工程负责人", needMerge = true)
    private String userNameAlias;

    /** 委托单位 */
    @Excel(name = "委托单位", needMerge = true)
    private String requesterAlias;

    @Excel(name = "作业部门", needMerge = true)
    private String department;

    @Excel(name = "项目预算金额", needMerge = true)
    private BigDecimal ygmoney;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "办结时间", needMerge = true,  dateFormat = "yyyy-MM-dd")
    private Date ygbjtime;

    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "计算时间", needMerge = true,  dateFormat = "yyyy-MM")
    private Date ygtime;

    public String getProjectNameAlias() {
        return projectNameAlias;
    }

    public void setProjectNameAlias(String projectNameAlias) {
        this.projectNameAlias = projectNameAlias;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getUserNameAlias() {
        return userNameAlias;
    }

    public void setUserNameAlias(String userNameAlias) {
        this.userNameAlias = userNameAlias;
    }

    public String getRequesterAlias() {
        return requesterAlias;
    }

    public void setRequesterAlias(String requesterAlias) {
        this.requesterAlias = requesterAlias;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getYgmoney() {
        return ygmoney;
    }

    public void setYgmoney(BigDecimal ygmoney) {
        this.ygmoney = ygmoney;
    }

    public Date getYgbjtime() {
        return ygbjtime;
    }

    public void setYgbjtime(Date ygbjtime) {
        this.ygbjtime = ygbjtime;
    }

    public Date getYgtime() {
        return ygtime;
    }

    public void setYgtime(Date ygtime) {
        this.ygtime = ygtime;
    }
}
