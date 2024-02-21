package com.kcylog.system.domain;

import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.LocalDateTime;

/**
 * 项目对象 view_fq_project
 * 
 * @author ruoyi
 * @date 2024-02-19
 */
public class ViewFqProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目名称 */
    private String projectName;
    /** 项目编号 */
    private String projectCode;
    //项目类型
    private String projectTypeName;
    //登记时间
    private LocalDateTime registerTime;
    //接待人
    private String createUserName;
    //工作量
    private String chargeItem;
    //工程负责人
    private String managerUserName;
    //委托单位
    private String customerName;
    //安排开始时间
    private LocalDateTime arrangeStartTime;
    //安排结束时间
    private LocalDateTime arrangeEndTime;
    //一检时间
    private LocalDateTime firstCheckTime;
    //二检时间
    private LocalDateTime secondCheckTime;
    //通知出件时间
    private LocalDateTime deliveryTime;
    //项目出件时间
    private LocalDateTime releaseTime;
    //送达时间
    private LocalDateTime arriveTime;
    //安排产值
    private Long arrangeProfit;
    //工作内容
    private String jobContent;
    //作业部门
    private String jobOrgName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getChargeItem() {
        return chargeItem;
    }

    public void setChargeItem(String chargeItem) {
        this.chargeItem = chargeItem;
    }

    public String getManagerUserName() {
        return managerUserName;
    }

    public void setManagerUserName(String managerUserName) {
        this.managerUserName = managerUserName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getArrangeStartTime() {
        return arrangeStartTime;
    }

    public void setArrangeStartTime(LocalDateTime arrangeStartTime) {
        this.arrangeStartTime = arrangeStartTime;
    }

    public LocalDateTime getArrangeEndTime() {
        return arrangeEndTime;
    }

    public void setArrangeEndTime(LocalDateTime arrangeEndTime) {
        this.arrangeEndTime = arrangeEndTime;
    }

    public LocalDateTime getFirstCheckTime() {
        return firstCheckTime;
    }

    public void setFirstCheckTime(LocalDateTime firstCheckTime) {
        this.firstCheckTime = firstCheckTime;
    }

    public LocalDateTime getSecondCheckTime() {
        return secondCheckTime;
    }

    public void setSecondCheckTime(LocalDateTime secondCheckTime) {
        this.secondCheckTime = secondCheckTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Long getArrangeProfit() {
        return arrangeProfit;
    }

    public void setArrangeProfit(Long arrangeProfit) {
        this.arrangeProfit = arrangeProfit;
    }

    public String getJobContent() {
        return jobContent;
    }

    public void setJobContent(String jobContent) {
        this.jobContent = jobContent;
    }

    public String getJobOrgName() {
        return jobOrgName;
    }

    public void setJobOrgName(String jobOrgName) {
        this.jobOrgName = jobOrgName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectName", getProjectName())
            .toString();
    }
}
