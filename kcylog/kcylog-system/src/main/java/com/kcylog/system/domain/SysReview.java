package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.annotation.Excels;
import com.kcylog.common.core.domain.BaseEntity;
import com.kcylog.common.core.domain.entity.SysDept;
import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 审核单对象 sys_review
 *
 * @author ruoyi
 * @date 2023-06-09
 */
public class SysReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**
     * 部门对象
     */
    @Excels({
            @Excel(name = "部门", targetAttr = "deptName", type = Excel.Type.EXPORT),
    })
    private SysDept dept;

    /** 审核单ID */
    private Long reviewId;

    /** 编号 */
    @Excel(name = "工程编号")
    private String serialNum;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String requester;

    /** 项目金额 */
    @Excel(name = "项目金额")
    private BigDecimal porjectMoney;

    /** 工作量 */
    @Excel(name = "工作量")
    private String workload;

    /** 用户ID(负责人) */
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 审核状态(0:未开始;1进行中;2通过;3:未通过) */
    private Long status;

    /** 人数 */
    private String peopleNum;

    /** 预估雇工工作开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 预估雇工工作结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 预估天数 */
    private float budgetDay;

    /** 预算金额 */
    private BigDecimal budgetMoney;

    /** 填写单子的最终时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finalTime;

    /**
     * 用户对象
     */
    @Excels({
            @Excel(name = "负责人", targetAttr = "userName", type = Excel.Type.EXPORT),
    })
    private SysUser user;

    public void setReviewId(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId()
    {
        return reviewId;
    }
    public void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }

    public String getSerialNum()
    {
        return serialNum;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setRequester(String requester)
    {
        this.requester = requester;
    }

    public String getRequester()
    {
        return requester;
    }
    public void setPorjectMoney(BigDecimal porjectMoney)
    {
        this.porjectMoney = porjectMoney;
    }

    public BigDecimal getPorjectMoney()
    {
        return porjectMoney;
    }
    public void setWorkload(String workload)
    {
        this.workload = workload;
    }

    public String getWorkload()
    {
        return workload;
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
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setPeopleNum(String peopleNum)
    {
        this.peopleNum = peopleNum;
    }

    public String getPeopleNum()
    {
        return peopleNum;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setBudgetDay(float budgetDay)
    {
        this.budgetDay = budgetDay;
    }

    public float getBudgetDay()
    {
        return budgetDay;
    }
    public void setBudgetMoney(BigDecimal budgetMoney)
    {
        this.budgetMoney = budgetMoney;
    }

    public BigDecimal getBudgetMoney()
    {
        return budgetMoney;
    }
    public void setFinalTime(Date finalTime)
    {
        this.finalTime = finalTime;
    }

    public Date getFinalTime()
    {
        return finalTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("reviewId", getReviewId())
                .append("serialNum", getSerialNum())
                .append("projectName", getProjectName())
                .append("requester", getRequester())
                .append("porjectMoney", getPorjectMoney())
                .append("workload", getWorkload())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("status", getStatus())
                .append("peopleNum", getPeopleNum())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("budgetDay", getBudgetDay())
                .append("budgetMoney", getBudgetMoney())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("finalTime", getFinalTime())
                .toString();
    }
}
