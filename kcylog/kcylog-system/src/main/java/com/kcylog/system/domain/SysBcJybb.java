package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 百川分院经营金额对象 sys_bc_jybb
 * 
 * @author ruoyi
 * @date 2025-07-07
 */
public class SysBcJybb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 分院 */
    @Excel(name = "分院")
    private String orgName;

    /** 合同/订单金额 */
    @Excel(name = "合同/订单金额")
    private String finalAmount;

    /** 开票金额 */
    @Excel(name = "开票金额")
    private String invoiceAmount;

    /** 补助金额 */
    @Excel(name = "补助金额")
    private String subsidyAmount;

    /** 分配给本分院金额 */
    @Excel(name = "分配给本分院金额")
    private String ownToOwnAmount;

    /** 分配给其他分院金额 */
    @Excel(name = "分配给其他分院金额")
    private String ownToOtherAmount;

    /** 其他分院分配的金额 */
    @Excel(name = "其他分院分配的金额")
    private String otherToOwnAmount;

    /** 归属本分院金额 */
    @Excel(name = "归属本分院金额")
    private String ownAmount;

    /** 到账金额 */
    @Excel(name = "到账金额")
    private String paymentAmount;

    /** 应收账款 */
    @Excel(name = "应收账款")
    private String receivableAmount;

    /** 投标中标金额 */
    @Excel(name = "投标中标金额")
    private String tenderAmount;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    private String token;

    private String billingDateBegin;

    private String billingDateEnd;

    private int total;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBillingDateBegin() {
        return billingDateBegin;
    }

    public void setBillingDateBegin(String billingDateBegin) {
        this.billingDateBegin = billingDateBegin;
    }

    public String getBillingDateEnd() {
        return billingDateEnd;
    }

    public void setBillingDateEnd(String billingDateEnd) {
        this.billingDateEnd = billingDateEnd;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrgName(String orgName) 
    {
        this.orgName = orgName;
    }

    public String getOrgName() 
    {
        return orgName;
    }
    public void setFinalAmount(String finalAmount) 
    {
        this.finalAmount = finalAmount;
    }

    public String getFinalAmount() 
    {
        return finalAmount;
    }
    public void setInvoiceAmount(String invoiceAmount) 
    {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceAmount() 
    {
        return invoiceAmount;
    }
    public void setSubsidyAmount(String subsidyAmount) 
    {
        this.subsidyAmount = subsidyAmount;
    }

    public String getSubsidyAmount() 
    {
        return subsidyAmount;
    }
    public void setOwnToOwnAmount(String ownToOwnAmount) 
    {
        this.ownToOwnAmount = ownToOwnAmount;
    }

    public String getOwnToOwnAmount() 
    {
        return ownToOwnAmount;
    }
    public void setOwnToOtherAmount(String ownToOtherAmount) 
    {
        this.ownToOtherAmount = ownToOtherAmount;
    }

    public String getOwnToOtherAmount() 
    {
        return ownToOtherAmount;
    }
    public void setOtherToOwnAmount(String otherToOwnAmount) 
    {
        this.otherToOwnAmount = otherToOwnAmount;
    }

    public String getOtherToOwnAmount() 
    {
        return otherToOwnAmount;
    }
    public void setOwnAmount(String ownAmount) 
    {
        this.ownAmount = ownAmount;
    }

    public String getOwnAmount() 
    {
        return ownAmount;
    }
    public void setPaymentAmount(String paymentAmount) 
    {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentAmount() 
    {
        return paymentAmount;
    }
    public void setReceivableAmount(String receivableAmount) 
    {
        this.receivableAmount = receivableAmount;
    }

    public String getReceivableAmount() 
    {
        return receivableAmount;
    }
    public void setTenderAmount(String tenderAmount) 
    {
        this.tenderAmount = tenderAmount;
    }

    public String getTenderAmount() 
    {
        return tenderAmount;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orgName", getOrgName())
            .append("finalAmount", getFinalAmount())
            .append("invoiceAmount", getInvoiceAmount())
            .append("subsidyAmount", getSubsidyAmount())
            .append("ownToOwnAmount", getOwnToOwnAmount())
            .append("ownToOtherAmount", getOwnToOtherAmount())
            .append("otherToOwnAmount", getOtherToOwnAmount())
            .append("ownAmount", getOwnAmount())
            .append("paymentAmount", getPaymentAmount())
            .append("receivableAmount", getReceivableAmount())
            .append("tenderAmount", getTenderAmount())
            .append("year", getYear())
            .toString();
    }
}
