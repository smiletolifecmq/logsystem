package com.kcylog.system.domain;

import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 项目进度管控对象 sys_project_progress
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
public class SysProjectProgress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目进度ID */
    private Long progressId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String itemNumber;

    /** 业主单位 */
    @Excel(name = "业主单位")
    private String ownerUnit;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目负责人ID */
    @Excel(name = "项目负责人ID")
    private Long userId;

    /** 可查看人员 */
    @Excel(name = "可查看人员")
    private String viewPeople;

    /** 可研 */
    @Excel(name = "可研")
    private String sharpening;

    /** 招标 */
    @Excel(name = "招标")
    private String bidding;

    /** 投标 */
    @Excel(name = "投标")
    private String tender;

    /** 合同 */
    @Excel(name = "合同")
    private String contract;

    /** 技术设计书 */
    @Excel(name = "技术设计书")
    private String designBook;

    /** 作业过程 */
    @Excel(name = "作业过程")
    private String jobProcess;

    /** 技术工作总结 */
    @Excel(name = "技术工作总结")
    private String workSummary;

    /** 质检报告 */
    @Excel(name = "质检报告")
    private String inspectionReport;

    /** 项目初验 */
    @Excel(name = "项目初验")
    private String initialTest;

    /** 项目终验 */
    @Excel(name = "项目终验")
    private String finalInspection;

    /**
     * 用户对象
     */
    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public void setProgressId(Long progressId)
    {
        this.progressId = progressId;
    }

    public Long getProgressId() 
    {
        return progressId;
    }
    public void setItemNumber(String itemNumber) 
    {
        this.itemNumber = itemNumber;
    }

    public String getItemNumber() 
    {
        return itemNumber;
    }
    public void setOwnerUnit(String ownerUnit) 
    {
        this.ownerUnit = ownerUnit;
    }

    public String getOwnerUnit() 
    {
        return ownerUnit;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setViewPeople(String viewPeople) 
    {
        this.viewPeople = viewPeople;
    }

    public String getViewPeople() 
    {
        return viewPeople;
    }
    public void setSharpening(String sharpening) 
    {
        this.sharpening = sharpening;
    }

    public String getSharpening() 
    {
        return sharpening;
    }
    public void setBidding(String bidding) 
    {
        this.bidding = bidding;
    }

    public String getBidding() 
    {
        return bidding;
    }
    public void setTender(String tender) 
    {
        this.tender = tender;
    }

    public String getTender() 
    {
        return tender;
    }
    public void setContract(String contract) 
    {
        this.contract = contract;
    }

    public String getContract() 
    {
        return contract;
    }
    public void setDesignBook(String designBook) 
    {
        this.designBook = designBook;
    }

    public String getDesignBook() 
    {
        return designBook;
    }
    public void setJobProcess(String jobProcess) 
    {
        this.jobProcess = jobProcess;
    }

    public String getJobProcess() 
    {
        return jobProcess;
    }
    public void setWorkSummary(String workSummary) 
    {
        this.workSummary = workSummary;
    }

    public String getWorkSummary() 
    {
        return workSummary;
    }
    public void setInspectionReport(String inspectionReport) 
    {
        this.inspectionReport = inspectionReport;
    }

    public String getInspectionReport() 
    {
        return inspectionReport;
    }
    public void setInitialTest(String initialTest) 
    {
        this.initialTest = initialTest;
    }

    public String getInitialTest() 
    {
        return initialTest;
    }
    public void setFinalInspection(String finalInspection) 
    {
        this.finalInspection = finalInspection;
    }

    public String getFinalInspection() 
    {
        return finalInspection;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("progressId", getProgressId())
            .append("itemNumber", getItemNumber())
            .append("ownerUnit", getOwnerUnit())
            .append("projectName", getProjectName())
            .append("userId", getUserId())
            .append("viewPeople", getViewPeople())
            .append("sharpening", getSharpening())
            .append("bidding", getBidding())
            .append("tender", getTender())
            .append("contract", getContract())
            .append("designBook", getDesignBook())
            .append("jobProcess", getJobProcess())
            .append("workSummary", getWorkSummary())
            .append("inspectionReport", getInspectionReport())
            .append("initialTest", getInitialTest())
            .append("finalInspection", getFinalInspection())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
