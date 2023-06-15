package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 雇工工作时间记录对象 sys_employee_worktime
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public class SysEmployeeWorktime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工作时间ID */
    private Long worktimeId;

    /** 雇工ID */
    @Excel(name = "雇工ID")
    private Long employeeId;

    /** 开始时间戳 */
    @Excel(name = "开始时间戳")
    private Integer startTime;

    /** 结束时间戳 */
    @Excel(name = "结束时间戳")
    private Integer endTime;

    /** 审核单ID */
    @Excel(name = "审核单ID")
    private Long reviewId;

    private Long reviewEmployeeId;

    public Long getReviewEmployeeId() {
        return reviewEmployeeId;
    }

    public void setReviewEmployeeId(Long reviewEmployeeId) {
        this.reviewEmployeeId = reviewEmployeeId;
    }

    public void setWorktimeId(Long worktimeId) 
    {
        this.worktimeId = worktimeId;
    }

    public Long getWorktimeId() 
    {
        return worktimeId;
    }
    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() 
    {
        return employeeId;
    }
    public void setStartTime(Integer startTime) 
    {
        this.startTime = startTime;
    }

    public Integer getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Integer endTime) 
    {
        this.endTime = endTime;
    }

    public Integer getEndTime() 
    {
        return endTime;
    }
    public void setReviewId(Long reviewId) 
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId() 
    {
        return reviewId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("worktimeId", getWorktimeId())
            .append("employeeId", getEmployeeId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("reviewId", getReviewId())
            .toString();
    }
}
