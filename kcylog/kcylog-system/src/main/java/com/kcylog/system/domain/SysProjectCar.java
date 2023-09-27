package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 项目车辆使用情况登记对象 sys_project_car
 * 
 * @author ruoyi
 * @date 2023-09-27
 */
public class SysProjectCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long carId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carNum;

    /** 用车类型：1拼车，2滴滴打车，3单位派车 */
    @Excel(name = "用车类型：1拼车，2滴滴打车，3单位派车")
    private Integer carType;

    /** 时间段：1上午 2下午 3上午-下午 */
    @Excel(name = "时间段：1上午 2下午 3上午-下午")
    private Integer timeType;

    /** 用车时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "用车时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date carTime;

    /** 用车负责人名称 */
    @Excel(name = "用车负责人名称")
    private String carUser;

    /** 用车负责人ID */
    @Excel(name = "用车负责人ID")
    private Long carUserId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 填写人ID */
    @Excel(name = "填写人ID")
    private Long fillUserId;

    public void setCarId(Long carId) 
    {
        this.carId = carId;
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
    public void setCarNum(String carNum) 
    {
        this.carNum = carNum;
    }

    public String getCarNum() 
    {
        return carNum;
    }
    public void setCarType(Integer carType) 
    {
        this.carType = carType;
    }

    public Integer getCarType() 
    {
        return carType;
    }
    public void setTimeType(Integer timeType) 
    {
        this.timeType = timeType;
    }

    public Integer getTimeType() 
    {
        return timeType;
    }
    public void setCarTime(Date carTime) 
    {
        this.carTime = carTime;
    }

    public Date getCarTime() 
    {
        return carTime;
    }
    public void setCarUser(String carUser) 
    {
        this.carUser = carUser;
    }

    public String getCarUser() 
    {
        return carUser;
    }
    public void setCarUserId(Long carUserId) 
    {
        this.carUserId = carUserId;
    }

    public Long getCarUserId() 
    {
        return carUserId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setFillUserId(Long fillUserId) 
    {
        this.fillUserId = fillUserId;
    }

    public Long getFillUserId() 
    {
        return fillUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carId", getCarId())
            .append("projectId", getProjectId())
            .append("carNum", getCarNum())
            .append("carType", getCarType())
            .append("timeType", getTimeType())
            .append("carTime", getCarTime())
            .append("carUser", getCarUser())
            .append("carUserId", getCarUserId())
            .append("deptId", getDeptId())
            .append("fillUserId", getFillUserId())
            .toString();
    }
}
