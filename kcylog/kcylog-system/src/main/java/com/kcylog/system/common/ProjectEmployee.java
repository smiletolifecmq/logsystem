package com.kcylog.system.common;

import com.kcylog.common.annotation.Excel;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.util.Date;

public class ProjectEmployee {
    /** 工作量 */
    @Excel(name = "工作量", headerBackgroundColor = IndexedColors.BROWN)
    private String workload;

    /** 项目金额 */
    @Excel(name = "项目金额", headerBackgroundColor = IndexedColors.BROWN)
    private BigDecimal porjectMoney;

    @Excel(name = "分包情况", headerBackgroundColor = IndexedColors.BROWN, readConverterExp = "1=是,2=否")
    private Long subcontract;

    @Excel(name = "雇工内容", headerBackgroundColor = IndexedColors.BROWN)
    private String employmentReason;

    /** 预估雇工工作开始时间 */
    @Excel(name = "雇工开始时间", dateFormat = "yyyy-MM-dd", headerBackgroundColor = IndexedColors.BROWN)
    private Date startTime;

    /** 预估雇工工作结束时间 */
    @Excel(name = "雇工结束时间", dateFormat = "yyyy-MM-dd", headerBackgroundColor = IndexedColors.BROWN)
    private Date endTime;

    /** 人数 */
    @Excel(name = "雇工人数", headerBackgroundColor = IndexedColors.BROWN)
    private String peopleNum;

    /** 预估天数 */
    @Excel(name = "天数", headerBackgroundColor = IndexedColors.BROWN)
    private float budgetDay;

    /** 预算金额 */
    @Excel(name = "预算", headerBackgroundColor = IndexedColors.BROWN)
    private BigDecimal budgetMoney;

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public BigDecimal getPorjectMoney() {
        return porjectMoney;
    }

    public void setPorjectMoney(BigDecimal porjectMoney) {
        this.porjectMoney = porjectMoney;
    }

    public Long getSubcontract() {
        return subcontract;
    }

    public void setSubcontract(Long subcontract) {
        this.subcontract = subcontract;
    }

    public String getEmploymentReason() {
        return employmentReason;
    }

    public void setEmploymentReason(String employmentReason) {
        this.employmentReason = employmentReason;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public float getBudgetDay() {
        return budgetDay;
    }

    public void setBudgetDay(float budgetDay) {
        this.budgetDay = budgetDay;
    }

    public BigDecimal getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(BigDecimal budgetMoney) {
        this.budgetMoney = budgetMoney;
    }
}
