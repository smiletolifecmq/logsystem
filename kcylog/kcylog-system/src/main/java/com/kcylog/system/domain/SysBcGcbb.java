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

    private Long totalCount; //项目总数年

    private Long weekCount; //项目总数周

    private Long sellYearCount; //含售年

    private Long sellWeekCount; //含售周

    private Long planYearCount; //含规年

    private Long planWeekCount; //含规周

    private Long pipeCctvCount; //含管cctv

    private Long pipeOtherCount; //含管不含cctv

    private Long govYearEarthControlCount; //含政年，含土方或者控制

    private Long govYearRoadCount; //含政年，不含土方和控制

    private Long govWeekEarthControlCount; //含政周，含土方或者控制

    private Long govWeekRoadCount; //含政周，不含土方和控制

    private Long jiCount; //含籍统计

    private Long jiCountMonth; //含籍统计月

    private Long pipeCctvCountMonth;

    private Long pipeOtherCountMonth;

    private int dataType;

    public Long getPipeOtherCountMonth() {
        return pipeOtherCountMonth;
    }

    public void setPipeOtherCountMonth(Long pipeOtherCountMonth) {
        this.pipeOtherCountMonth = pipeOtherCountMonth;
    }

    public Long getPipeCctvCountMonth() {
        return pipeCctvCountMonth;
    }

    public void setPipeCctvCountMonth(Long pipeCctvCountMonth) {
        this.pipeCctvCountMonth = pipeCctvCountMonth;
    }

    public Long getJiCountMonth() {
        return jiCountMonth;
    }

    public void setJiCountMonth(Long jiCountMonth) {
        this.jiCountMonth = jiCountMonth;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(Long weekCount) {
        this.weekCount = weekCount;
    }

    public Long getSellYearCount() {
        return sellYearCount;
    }

    public void setSellYearCount(Long sellYearCount) {
        this.sellYearCount = sellYearCount;
    }

    public Long getSellWeekCount() {
        return sellWeekCount;
    }

    public void setSellWeekCount(Long sellWeekCount) {
        this.sellWeekCount = sellWeekCount;
    }

    public Long getPlanYearCount() {
        return planYearCount;
    }

    public void setPlanYearCount(Long planYearCount) {
        this.planYearCount = planYearCount;
    }

    public Long getPlanWeekCount() {
        return planWeekCount;
    }

    public void setPlanWeekCount(Long planWeekCount) {
        this.planWeekCount = planWeekCount;
    }

    public Long getPipeCctvCount() {
        return pipeCctvCount;
    }

    public void setPipeCctvCount(Long pipeCctvCount) {
        this.pipeCctvCount = pipeCctvCount;
    }

    public Long getPipeOtherCount() {
        return pipeOtherCount;
    }

    public void setPipeOtherCount(Long pipeOtherCount) {
        this.pipeOtherCount = pipeOtherCount;
    }

    public Long getGovYearEarthControlCount() {
        return govYearEarthControlCount;
    }

    public void setGovYearEarthControlCount(Long govYearEarthControlCount) {
        this.govYearEarthControlCount = govYearEarthControlCount;
    }

    public Long getGovYearRoadCount() {
        return govYearRoadCount;
    }

    public void setGovYearRoadCount(Long govYearRoadCount) {
        this.govYearRoadCount = govYearRoadCount;
    }

    public Long getGovWeekEarthControlCount() {
        return govWeekEarthControlCount;
    }

    public void setGovWeekEarthControlCount(Long govWeekEarthControlCount) {
        this.govWeekEarthControlCount = govWeekEarthControlCount;
    }

    public Long getGovWeekRoadCount() {
        return govWeekRoadCount;
    }

    public void setGovWeekRoadCount(Long govWeekRoadCount) {
        this.govWeekRoadCount = govWeekRoadCount;
    }

    public Long getJiCount() {
        return jiCount;
    }

    public void setJiCount(Long jiCount) {
        this.jiCount = jiCount;
    }

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
