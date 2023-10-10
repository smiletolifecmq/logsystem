package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 车辆使用审核对象 sys_car_review
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public class SysCarReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 车辆审核单自增ID */
    private Long carReviewId;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordTime;

    /** 负责人ID */
    @Excel(name = "负责人ID")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 审核状态(0:未开始;1进行中;2通过;3:未通过) */
    @Excel(name = "审核状态(0:未开始;1进行中;2通过;3:未通过)")
    private Long reviewStatus;

    public void setCarReviewId(Long carReviewId) 
    {
        this.carReviewId = carReviewId;
    }

    public Long getCarReviewId() 
    {
        return carReviewId;
    }
    public void setRecordTime(Date recordTime) 
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime() 
    {
        return recordTime;
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
    public void setReviewStatus(Long reviewStatus) 
    {
        this.reviewStatus = reviewStatus;
    }

    public Long getReviewStatus() 
    {
        return reviewStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carReviewId", getCarReviewId())
            .append("recordTime", getRecordTime())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("reviewStatus", getReviewStatus())
            .toString();
    }
}
