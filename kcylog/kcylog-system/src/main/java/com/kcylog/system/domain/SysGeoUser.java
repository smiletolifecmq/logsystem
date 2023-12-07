package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 地理用户对象 sys_geo_user
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
public class SysGeoUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long geoUserId;

    /** 父用户ID */
    @Excel(name = "父用户ID")
    private Long parentId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    private String oldAncestors;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 是否为检查人员，0否1是 */
    @Excel(name = "是否为检查人员，0否1是")
    private Long isCheck;

    public String getOldAncestors() {
        return oldAncestors;
    }

    public void setOldAncestors(String oldAncestors) {
        this.oldAncestors = oldAncestors;
    }

    private List<SysGeoUser> children = new ArrayList<SysGeoUser>();

    public List<SysGeoUser> getChildren() {
        return children;
    }

    public void setChildren(List<SysGeoUser> children) {
        this.children = children;
    }

    public void setGeoUserId(Long geoUserId)
    {
        this.geoUserId = geoUserId;
    }

    public Long getGeoUserId() 
    {
        return geoUserId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setIsCheck(Long isCheck) 
    {
        this.isCheck = isCheck;
    }

    public Long getIsCheck() 
    {
        return isCheck;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("geoUserId", getGeoUserId())
            .append("parentId", getParentId())
            .append("userId", getUserId())
            .append("ancestors", getAncestors())
            .append("userName", getUserName())
            .append("isCheck", getIsCheck())
            .toString();
    }
}
