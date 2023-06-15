package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;

/**
 * 雇工实际工作内容记录对象 sys_review_employee
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public class SysReviewEmployee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 雇工实际工作内容记录ID */
    private Long reviewEmployeeId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 身份证 */
    @Excel(name = "身份证")
    private String idCard;

    /** 作业时间 */
    @Excel(name = "作业时间")
    private String workTime;

    /** 天数 */
    @Excel(name = "天数")
    private float workDay;

    /** 费用 */
    @Excel(name = "费用")
    private BigDecimal cost;

    /** 审核单ID */
    @Excel(name = "审核单ID")
    private Long reviewId;

    private List<WorkTimeStamp> workTimeStamp;

    public void setWorkTimeStamp(List<WorkTimeStamp> workTimeStamp)
    {
        this.workTimeStamp = workTimeStamp;
    }

    public List<WorkTimeStamp> getWorkTimeStamp()
    {
        return workTimeStamp;
    }

    public void setReviewEmployeeId(Long reviewEmployeeId) 
    {
        this.reviewEmployeeId = reviewEmployeeId;
    }

    public Long getReviewEmployeeId() 
    {
        return reviewEmployeeId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIdCard(String idCard) 
    {
        this.idCard = idCard;
    }

    public String getIdCard() 
    {
        return idCard;
    }
    public void setWorkTime(String workTime) 
    {
        this.workTime = workTime;
    }

    public String getWorkTime() 
    {
        return workTime;
    }
    public void setWorkDay(Long workDay) 
    {
        this.workDay = workDay;
    }

    public float getWorkDay()
    {
        return workDay;
    }
    public void setCost(BigDecimal cost) 
    {
        this.cost = cost;
    }

    public BigDecimal getCost() 
    {
        return cost;
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
            .append("reviewEmployeeId", getReviewEmployeeId())
            .append("name", getName())
            .append("idCard", getIdCard())
            .append("workTime", getWorkTime())
            .append("workDay", getWorkDay())
            .append("cost", getCost())
            .append("reviewId", getReviewId())
            .toString();
    }
}
