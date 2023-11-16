package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import com.kcylog.common.core.domain.entity.SysDept;
import com.kcylog.common.core.domain.entity.SysUser;
import com.kcylog.system.common.FileJson;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 部门日常对象 sys_department_daily
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
public class SysDepartmentDaily extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门日常ID */
    private Long dailyId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 会议类型：0其他、1部门会议、2交流会、3生产质量例会、4培训工作、5奖励信息 */
    @Excel(name = "会议类型：0其他、1部门会议、2交流会、3生产质量例会、4培训工作、5奖励信息")
    private Long meetingType;

    /** 主题 */
    @Excel(name = "主题")
    private String theme;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    private List<FileJson> imgJson;

    private List<FileJson> checkInJson;

    /** 照片 */
    private String img;

    /** 签到 */
    private String checkIn;

    private Date dailyTime;

    /**
     * 用户对象
     */
    private SysUser user;
    /**
     * 部门对象
     */
    private SysDept dept;

    public Date getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(Date dailyTime) {
        this.dailyTime = dailyTime;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public List<FileJson> getImgJson() {
        return imgJson;
    }

    public void setImgJson(List<FileJson> imgJson) {
        this.imgJson = imgJson;
    }

    public List<FileJson> getCheckInJson() {
        return checkInJson;
    }

    public void setCheckInJson(List<FileJson> checkInJson) {
        this.checkInJson = checkInJson;
    }

    public void setDailyId(Long dailyId) 
    {
        this.dailyId = dailyId;
    }

    public Long getDailyId() 
    {
        return dailyId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setMeetingType(Long meetingType) 
    {
        this.meetingType = meetingType;
    }

    public Long getMeetingType() 
    {
        return meetingType;
    }
    public void setTheme(String theme) 
    {
        this.theme = theme;
    }

    public String getTheme() 
    {
        return theme;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setImg(String img)
    {
        this.img = img;
    }

    public String getImg()
    {
        return img;
    }
    public void setCheckIn(String checkIn) 
    {
        this.checkIn = checkIn;
    }

    public String getCheckIn() 
    {
        return checkIn;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dailyId", getDailyId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("meetingType", getMeetingType())
            .append("theme", getTheme())
            .append("content", getContent())
            .append("img", getImg())
            .append("checkIn", getCheckIn())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}


