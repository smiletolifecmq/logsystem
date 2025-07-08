package com.kcylog.system.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BcProjectRes {
    private String createTime;
    private String projectCode;
    private String projectName;
    private String projectTypeName;
    private String customerName;
    private String branchOrgName;
    private String contractNo;
    private String contractAmount;
    private String contractFinalAmount;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchOrgName() {
        return branchOrgName;
    }

    public void setBranchOrgName(String branchOrgName) {
        this.branchOrgName = branchOrgName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractFinalAmount() {
        return contractFinalAmount;
    }

    public void setContractFinalAmount(String contractFinalAmount) {
        this.contractFinalAmount = contractFinalAmount;
    }
}
