package com.ruoyi.system.domain;


import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.param.SysUserLogQueryParams;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 用户日志对象 sys_user_log
 *
 * @author ruoyi
 * @date 2023-05-06
 */
public class SysUserLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户日志ID
     */
    private Long userLogId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户对象
     */
    @Excels({
            @Excel(name = "用户名称", targetAttr = "userName", type = Excel.Type.EXPORT),
    })
    private SysUser user;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
    })
    private SysDept dept;

    /**
     * 工作内容
     */
    @Excel(name = "工作标题")
    private String content;

    /**
     * 工作量
     */
    @Excel(name = "工作内容")
    private String completeness;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /**
     * 查询参数
     */
    private Long deptId;
    private String userName;

    private List<SysUserLogQueryParams> logs;

    public List<SysUserLogQueryParams> getLogs() {
        return logs;
    }

    public void setLogs(List<SysUserLogQueryParams> logs) {
        this.logs = logs;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public void setUserLogId(Long userLogId) {
        this.userLogId = userLogId;
    }

    public Long getUserLogId() {
        return userLogId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    public String getCompleteness() {
        return completeness;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getDeptId() {
        return deptId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userLogId", getUserLogId())
                .append("userId", getUserId())
                .append("completeness", getCompleteness())
                .append("content", getContent())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("dept", getDept())
                .toString();
    }
}
