package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 百川工程报表对象 sys_bc_gcbb
 * 
 * @author ruoyi
 * @date 2025-07-08
 */
public class SysBcGcbb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    @Excel(name = "登记时间")
    private String cTime;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String projectTypeName;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String customerName;

    /** 所属分院 */
    @Excel(name = "所属分院")
    private String branchOrgName;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private String contractAmount;

    /** 结算金额 */
    @Excel(name = "结算金额")
    private String contractFinalAmount;

    private String year;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public Long getId()
    {
        return id;
    }
    public void setProjectCode(String projectCode) 
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode() 
    {
        return projectCode;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setProjectTypeName(String projectTypeName) 
    {
        this.projectTypeName = projectTypeName;
    }

    public String getProjectTypeName() 
    {
        return projectTypeName;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setBranchOrgName(String branchOrgName) 
    {
        this.branchOrgName = branchOrgName;
    }

    public String getBranchOrgName() 
    {
        return branchOrgName;
    }
    public void setContractNo(String contractNo) 
    {
        this.contractNo = contractNo;
    }

    public String getContractNo() 
    {
        return contractNo;
    }
    public void setContractAmount(String contractAmount) 
    {
        this.contractAmount = contractAmount;
    }

    public String getContractAmount() 
    {
        return contractAmount;
    }
    public void setContractFinalAmount(String contractFinalAmount) 
    {
        this.contractFinalAmount = contractFinalAmount;
    }

    public String getContractFinalAmount() 
    {
        return contractFinalAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("projectCode", getProjectCode())
            .append("projectName", getProjectName())
            .append("projectTypeName", getProjectTypeName())
            .append("customerName", getCustomerName())
            .append("branchOrgName", getBranchOrgName())
            .append("contractNo", getContractNo())
            .append("contractAmount", getContractAmount())
            .append("contractFinalAmount", getContractFinalAmount())
            .toString();
    }
}
