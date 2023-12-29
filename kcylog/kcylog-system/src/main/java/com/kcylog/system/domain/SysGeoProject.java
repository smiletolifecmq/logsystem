package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 地理项目对象 sys_geo_project
 *
 * @author ruoyi
 * @date 2023-12-06
 */
public class SysGeoProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long projectId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectNum;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 负责人ID */
    @Excel(name = "负责人ID")
    private Long userId;

    /** 负责人名称 */
    @Excel(name = "负责人名称")
    private String userName;

    private Integer sort;

    private Long[] oneCheckJson;

    private Long[] twoCheckJson;

    private String oneCheck;

    private String twoCheck;

    public Long[] getOneCheckJson() {
        return oneCheckJson;
    }

    public void setOneCheckJson(Long[] oneCheckJson) {
        this.oneCheckJson = oneCheckJson;
    }

    public Long[] getTwoCheckJson() {
        return twoCheckJson;
    }

    public void setTwoCheckJson(Long[] twoCheckJson) {
        this.twoCheckJson = twoCheckJson;
    }

    public String getOneCheck() {
        return oneCheck;
    }

    public void setOneCheck(String oneCheck) {
        this.oneCheck = oneCheck;
    }

    public String getTwoCheck() {
        return twoCheck;
    }

    public void setTwoCheck(String twoCheck) {
        this.twoCheck = twoCheck;
    }



    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setProjectNum(String projectNum) 
    {
        this.projectNum = projectNum;
    }

    public String getProjectNum() 
    {
        return projectNum;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
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
            .append("projectId", getProjectId())
            .append("projectNum", getProjectNum())
            .append("projectName", getProjectName())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .toString();
    }
}
