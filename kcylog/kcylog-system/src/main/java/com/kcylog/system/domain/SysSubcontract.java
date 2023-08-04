package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import com.kcylog.common.core.domain.entity.SysDept;
import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 分包对象 sys_subcontract
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public class SysSubcontract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分包ID */
    private Long subcontractId;

    /** 工程编号 */
    @Excel(name = "工程编号")
    private String serialNum;

    /** 抽签时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "抽签时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lotTime;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 业务名称 */
    @Excel(name = "业务名称")
    private String businessName;

    /** 协作单位 */
    @Excel(name = "协作单位")
    private String cooperationUnit;

    private List<String> cooperationUnitJson;

    /** 中签单位 */
    @Excel(name = "中签单位")
    private String winUnit;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String entrustUnit;

    /** 工作量 */
    @Excel(name = "工作量")
    private String workload;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String workcontent;

    /** 用户ID(负责人) */
    @Excel(name = "用户ID(负责人)")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 审核状态(0:未开始;1进行中;2通过;3:未通过) */
    @Excel(name = "审核状态(0:未开始;1进行中;2通过;3:未通过)")
    private Long status;

    /** 工期开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工期开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 工期结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工期结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 部门对象
     */
    private SysDept dept;

    /**
     * 用户对象
     */
    private SysUser user;

    private Long isPrint;

    public Long getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Long isPrint) {
        this.isPrint = isPrint;
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

    public List<String> getCooperationUnitJson() {
        return cooperationUnitJson;
    }

    public void setCooperationUnitJson(List<String> cooperationUnitJson) {
        this.cooperationUnitJson = cooperationUnitJson;
    }

    public void setSubcontractId(Long subcontractId) 
    {
        this.subcontractId = subcontractId;
    }

    public Long getSubcontractId() 
    {
        return subcontractId;
    }
    public void setSerialNum(String serialNum) 
    {
        this.serialNum = serialNum;
    }

    public String getSerialNum() 
    {
        return serialNum;
    }
    public void setLotTime(Date lotTime) 
    {
        this.lotTime = lotTime;
    }

    public Date getLotTime() 
    {
        return lotTime;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setBusinessName(String businessName) 
    {
        this.businessName = businessName;
    }

    public String getBusinessName() 
    {
        return businessName;
    }
    public void setCooperationUnit(String cooperationUnit) 
    {
        this.cooperationUnit = cooperationUnit;
    }

    public String getCooperationUnit() 
    {
        return cooperationUnit;
    }
    public void setWinUnit(String winUnit) 
    {
        this.winUnit = winUnit;
    }

    public String getWinUnit() 
    {
        return winUnit;
    }
    public void setEntrustUnit(String entrustUnit) 
    {
        this.entrustUnit = entrustUnit;
    }

    public String getEntrustUnit() 
    {
        return entrustUnit;
    }
    public void setWorkload(String workload) 
    {
        this.workload = workload;
    }

    public String getWorkload() 
    {
        return workload;
    }
    public void setWorkcontent(String workcontent) 
    {
        this.workcontent = workcontent;
    }

    public String getWorkcontent() 
    {
        return workcontent;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("subcontractId", getSubcontractId())
            .append("serialNum", getSerialNum())
            .append("lotTime", getLotTime())
            .append("projectName", getProjectName())
            .append("businessName", getBusinessName())
            .append("cooperationUnit", getCooperationUnit())
            .append("winUnit", getWinUnit())
            .append("entrustUnit", getEntrustUnit())
            .append("workload", getWorkload())
            .append("workcontent", getWorkcontent())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("status", getStatus())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
