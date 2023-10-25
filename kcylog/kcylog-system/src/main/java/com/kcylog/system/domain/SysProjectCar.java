package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目车辆使用情况登记对象 sys_project_car
 * 
 * @author ruoyi
 * @date 2023-10-17
 */
public class SysProjectCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long carId;

    /** 项目ID */
    private Long projectId;

    /** 车辆审核单ID */
    private Long carReviewId;

    /** 项目名称 */
    private String projectName;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNum;

    /** 用车类型：1单位派车（拼车），2单位派车（独享），3滴滴 */
    @Excel(name = "用车类型")
    private Long carType;

    /** 次数：1拼车一趟（独享一趟）、拼车二趟（独享二趟）、拼车三趟（独享三趟）、拼车四趟（独享四趟）
 */
    @Excel(name = "次数")
    private Long number;

    /** 用车费用 */
    @Excel(name = "用车费用")
    private BigDecimal carExpenses;

    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordTime;

    public void setCarId(Long carId)
    {
        this.carId = carId;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public Long getCarId() 
    {
        return carId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setCarReviewId(Long carReviewId) 
    {
        this.carReviewId = carReviewId;
    }

    public Long getCarReviewId() 
    {
        return carReviewId;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setCarNum(String carNum) 
    {
        this.carNum = carNum;
    }

    public String getCarNum() 
    {
        return carNum;
    }
    public void setCarType(Long carType) 
    {
        this.carType = carType;
    }

    public Long getCarType() 
    {
        return carType;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setCarExpenses(BigDecimal carExpenses) 
    {
        this.carExpenses = carExpenses;
    }

    public BigDecimal getCarExpenses() 
    {
        return carExpenses;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carId", getCarId())
            .append("projectId", getProjectId())
            .append("carReviewId", getCarReviewId())
            .append("projectName", getProjectName())
            .append("carNum", getCarNum())
            .append("carType", getCarType())
            .append("number", getNumber())
            .append("carExpenses", getCarExpenses())
            .toString();
    }
}
