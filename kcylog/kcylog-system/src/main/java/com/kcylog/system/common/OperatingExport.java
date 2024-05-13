package com.kcylog.system.common;

import com.kcylog.common.annotation.Excel;

import java.math.BigDecimal;

public class OperatingExport {
    @Excel(name = "序号", needMerge = true)
    private Integer num;

    @Excel(name = "工程编号", needMerge = true)
    private String projectNum;

    @Excel(name = "项目名称", needMerge = true)
    private String projectNameAlias;

    @Excel(name = "客户名称", needMerge = true)
    private String requesterAlias;

    @Excel(name = "产值类型", needMerge = true)
    private String valueType;

    @Excel(name = "项目类型", needMerge = true)
    private String projectType;

    @Excel(name = "经营人员", needMerge = true)
    private String receptionist;

    @Excel(name = "工作量", needMerge = true)
    private String workloadAlias;

    @Excel(name = "工程负责人", needMerge = true)
    private String userNameAlias;

    @Excel(name = "作业部门", needMerge = true)
    private String department;

    @Excel(name = "经营金额", needMerge = true)
    private BigDecimal operate;

    @Excel(name = "经营合同号", needMerge = true)
    private String contractNo;

    @Excel(name = "经营合同额", needMerge = true)
    private Long contractAmount;

    @Excel(name = "安排开始时间", needMerge = true)
    private String projectStartAlias;

    @Excel(name = "二检完成时间", needMerge = true)
    private String twoCheck;

    @Excel(name = "是否分包（是、否）", needMerge = true)
    private String subpackageType;

    @Excel(name = "分包合同号", needMerge = true)
    private String firmName;

    @Excel(name = "分包单位", needMerge = true)
    private String subcontractNo;

    @Excel(name = "分包金额", needMerge = true)
    private BigDecimal settleMoney;

    @Excel(name = "是否延期", needMerge = true)
    private String afterTime;

    @Excel(name = "是否超期", needMerge = true)
    private String isOverdue;

    @Excel(name = "超期天数", needMerge = true)
    private Integer overDay;

    @Excel(name = "工期系数", needMerge = true)
    private BigDecimal durationFactor;

    @Excel(name = "项目系数", needMerge = true)
    private BigDecimal projectCoefficient;

    @Excel(name = "备注", needMerge = true)
    private String bjRemark;

    public String getBjRemark() {
        return bjRemark;
    }

    public void setBjRemark(String bjRemark) {
        this.bjRemark = bjRemark;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectNameAlias() {
        return projectNameAlias;
    }

    public void setProjectNameAlias(String projectNameAlias) {
        this.projectNameAlias = projectNameAlias;
    }

    public String getRequesterAlias() {
        return requesterAlias;
    }

    public void setRequesterAlias(String requesterAlias) {
        this.requesterAlias = requesterAlias;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(String receptionist) {
        this.receptionist = receptionist;
    }

    public String getWorkloadAlias() {
        return workloadAlias;
    }

    public void setWorkloadAlias(String workloadAlias) {
        this.workloadAlias = workloadAlias;
    }

    public String getUserNameAlias() {
        return userNameAlias;
    }

    public void setUserNameAlias(String userNameAlias) {
        this.userNameAlias = userNameAlias;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getOperate() {
        return operate;
    }

    public void setOperate(BigDecimal operate) {
        this.operate = operate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getProjectStartAlias() {
        return projectStartAlias;
    }

    public void setProjectStartAlias(String projectStartAlias) {
        this.projectStartAlias = projectStartAlias;
    }

    public String getTwoCheck() {
        return twoCheck;
    }

    public void setTwoCheck(String twoCheck) {
        this.twoCheck = twoCheck;
    }

    public String getSubpackageType() {
        return subpackageType;
    }

    public void setSubpackageType(String subpackageType) {
        this.subpackageType = subpackageType;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getSubcontractNo() {
        return subcontractNo;
    }

    public void setSubcontractNo(String subcontractNo) {
        this.subcontractNo = subcontractNo;
    }

    public BigDecimal getSettleMoney() {
        return settleMoney;
    }

    public void setSettleMoney(BigDecimal settleMoney) {
        this.settleMoney = settleMoney;
    }

    public String getAfterTime() {
        return afterTime;
    }

    public void setAfterTime(String afterTime) {
        this.afterTime = afterTime;
    }

    public String getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(String isOverdue) {
        this.isOverdue = isOverdue;
    }

    public Integer getOverDay() {
        return overDay;
    }

    public void setOverDay(Integer overDay) {
        this.overDay = overDay;
    }

    public BigDecimal getDurationFactor() {
        return durationFactor;
    }

    public void setDurationFactor(BigDecimal durationFactor) {
        this.durationFactor = durationFactor;
    }

    public BigDecimal getProjectCoefficient() {
        return projectCoefficient;
    }

    public void setProjectCoefficient(BigDecimal projectCoefficient) {
        this.projectCoefficient = projectCoefficient;
    }
}
