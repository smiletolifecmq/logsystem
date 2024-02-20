package com.kcylog.system.domain;

import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

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
    private String registerTime;
    //接待人
    private String createUserName;
    //工作量
    private String chargeItem;
    //工程负责人
    private String managerUserName;
    //委托单位
    private String customerName;
    //安排开始时间
    private String arrangeStartTime;
    //安排结束时间
    private String arrangeEndTime;
    //一检时间
    private String firstCheckTime;
    //二检时间
    private String secondCheckTime;
    //通知出件时间
    private String deliveryTime;
    //项目出件时间
    private String releaseTime;
    //送达时间
    private String arriveTime;
    //安排产值
    private BigDecimal arrangeProfit;
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

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
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

    public String getArrangeStartTime() {
        return arrangeStartTime;
    }

    public void setArrangeStartTime(String arrangeStartTime) {
        this.arrangeStartTime = arrangeStartTime;
    }

    public String getArrangeEndTime() {
        return arrangeEndTime;
    }

    public void setArrangeEndTime(String arrangeEndTime) {
        this.arrangeEndTime = arrangeEndTime;
    }

    public String getFirstCheckTime() {
        return firstCheckTime;
    }

    public void setFirstCheckTime(String firstCheckTime) {
        this.firstCheckTime = firstCheckTime;
    }

    public String getSecondCheckTime() {
        return secondCheckTime;
    }

    public void setSecondCheckTime(String secondCheckTime) {
        this.secondCheckTime = secondCheckTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public BigDecimal getArrangeProfit() {
        return arrangeProfit;
    }

    public void setArrangeProfit(BigDecimal arrangeProfit) {
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
