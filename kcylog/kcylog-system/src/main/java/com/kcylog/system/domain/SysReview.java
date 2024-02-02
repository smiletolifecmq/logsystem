package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import com.kcylog.common.core.domain.entity.SysDept;
import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 审核单对象 sys_review
 *
 * @author ruoyi
 * @date 2023-06-09
 */
public class SysReview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK, width = 6)
    private int exportSerialNumber;

    /**
     * 部门对象
     */

    private SysDept dept;

    /** 审核单ID */
    private Long reviewId;

    /** 编号 */
    @Excel(name = "项目编号",width = 20, headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String serialNum;

    /** 项目名称 */
    @Excel(name = "项目名称",width = 60, headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String projectName;

    /** 委托单位 */
    private String requester;

    /** 项目金额 */
    private BigDecimal porjectMoney;

    /** 工作量 */
    @Excel(name = "工作量",width = 40, height = 28, headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String workload;

    @Excel(name = "姓名",width = 20, headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String hiredWorkerName;

    /** 用户ID(负责人) */
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 审核状态(0:未开始;1进行中;2通过;3:未通过) */
    private Long status;

    private Long subcontract;

    private Long manType;

    /** 人数 */
    private String peopleNum;

    private String employmentReason;

    /** 预估雇工工作开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 预估雇工工作结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 预估天数 */
    private float budgetDay;

    /** 预算金额 */
    private BigDecimal budgetMoney;

    /** 预算金额 */
    private BigDecimal guGongMoney;

    /** 填写单子的最终时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finalTime;

    /**
     * 用户对象
     */
    private SysUser user;

    private SysProjectRelation projectRelation;

    private int finalSecondStatus;

    private Long finalHire;

    private int startEdit;

    private List<SysReviewEmployee> reviewEmployee;

    private List<Long> reviewIds;

    private String settlementName;

    private Long settlementId;

    private Date projectStart;

    private Date projectEnd;

    private Long projectId;

    private Long fzrUserId;

    private SysProject project;

    private Long[] reviewExportIds;

    private Long[] reviewExportEmployeeIds;

    public Long getManType() {
        return manType;
    }

    public void setManType(Long manType) {
        this.manType = manType;
    }

    public BigDecimal getGuGongMoney() {
        return guGongMoney;
    }

    public void setGuGongMoney(BigDecimal guGongMoney) {
        this.guGongMoney = guGongMoney;
    }

    public Long[] getReviewExportIds() {
        return reviewExportIds;
    }

    public void setReviewExportIds(Long[] reviewExportIds) {
        this.reviewExportIds = reviewExportIds;
    }

    public Long[] getReviewExportEmployeeIds() {
        return reviewExportEmployeeIds;
    }

    public void setReviewExportEmployeeIds(Long[] reviewExportEmployeeIds) {
        this.reviewExportEmployeeIds = reviewExportEmployeeIds;
    }

    public Long getFzrUserId() {
        return fzrUserId;
    }

    public void setFzrUserId(Long fzrUserId) {
        this.fzrUserId = fzrUserId;
    }

    public SysProject getProject() {
        return project;
    }

    public void setProject(SysProject project) {
        this.project = project;
    }

    public SysProjectRelation getProjectRelation() {
        return projectRelation;
    }

    public void setProjectRelation(SysProjectRelation projectRelation) {
        this.projectRelation = projectRelation;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Date getProjectStart() {
        return projectStart;
    }

    public void setProjectStart(Date projectStart) {
        this.projectStart = projectStart;
    }

    public Date getProjectEnd() {
        return projectEnd;
    }

    public void setProjectEnd(Date projectEnd) {
        this.projectEnd = projectEnd;
    }


    public String getEmploymentReason() {
        return employmentReason;
    }

    public void setEmploymentReason(String employmentReason) {
        this.employmentReason = employmentReason;
    }

    public Long getFinalHire() {
        return finalHire;
    }

    public void setFinalHire(Long finalHire) {
        this.finalHire = finalHire;
    }

    public Long getSettlementId() {
        return settlementId;
    }

    public void setSettlementId(Long settlementId) {
        this.settlementId = settlementId;
    }

    public List<Long> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Long> reviewIds) {
        this.reviewIds = reviewIds;
    }

    public String getSettlementName() {
        return settlementName;
    }

    public void setSettlementName(String settlementName) {
        this.settlementName = settlementName;
    }

    public Long getSubcontract() {
        return subcontract;
    }

    public void setSubcontract(Long subcontract) {
        this.subcontract = subcontract;
    }

    public String getHiredWorkerName() {
        return hiredWorkerName;
    }

    public void setHiredWorkerName(String hiredWorkerName) {
        this.hiredWorkerName = hiredWorkerName;
    }

    public List<SysReviewEmployee> getReviewEmployee() {
        return reviewEmployee;
    }

    public int getExportSerialNumber() {
        return exportSerialNumber;
    }

    public void setExportSerialNumber(int exportSerialNumber) {
        this.exportSerialNumber = exportSerialNumber;
    }

    public int getStartEdit() {
        return startEdit;
    }

    public void setStartEdit(int startEdit) {
        this.startEdit = startEdit;
    }

    public int getFinalSecondStatus() {
        return finalSecondStatus;
    }

    public void setFinalSecondStatus(int finalSecondStatus) {
        this.finalSecondStatus = finalSecondStatus;
    }

    public void setReviewEmployee(List<SysReviewEmployee> reviewEmployee) {
        this.reviewEmployee = reviewEmployee;
    }

    public void setReviewId(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public Long getReviewId()
    {
        return reviewId;
    }
    public void setSerialNum(String serialNum)
    {
        this.serialNum = serialNum;
    }

    public String getSerialNum()
    {
        return serialNum;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setRequester(String requester)
    {
        this.requester = requester;
    }

    public String getRequester()
    {
        return requester;
    }
    public void setPorjectMoney(BigDecimal porjectMoney)
    {
        this.porjectMoney = porjectMoney;
    }

    public BigDecimal getPorjectMoney()
    {
        return porjectMoney;
    }
    public void setWorkload(String workload)
    {
        this.workload = workload;
    }

    public String getWorkload()
    {
        return workload;
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
    public void setPeopleNum(String peopleNum)
    {
        this.peopleNum = peopleNum;
    }

    public String getPeopleNum()
    {
        return peopleNum;
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
    public void setBudgetDay(float budgetDay)
    {
        this.budgetDay = budgetDay;
    }

    public float getBudgetDay()
    {
        return budgetDay;
    }
    public void setBudgetMoney(BigDecimal budgetMoney)
    {
        this.budgetMoney = budgetMoney;
    }

    public BigDecimal getBudgetMoney()
    {
        return budgetMoney;
    }
    public void setFinalTime(Date finalTime)
    {
        this.finalTime = finalTime;
    }

    public Date getFinalTime()
    {
        return finalTime;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysDept getDept() {
        return dept;
    }

    public void setDept(SysDept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("reviewId", getReviewId())
                .append("serialNum", getSerialNum())
                .append("projectName", getProjectName())
                .append("requester", getRequester())
                .append("porjectMoney", getPorjectMoney())
                .append("workload", getWorkload())
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("status", getStatus())
                .append("peopleNum", getPeopleNum())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("budgetDay", getBudgetDay())
                .append("budgetMoney", getBudgetMoney())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("finalTime", getFinalTime())
                .toString();
    }
}
