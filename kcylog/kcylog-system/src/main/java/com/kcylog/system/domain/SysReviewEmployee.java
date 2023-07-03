package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 雇工实际工作内容记录对象 sys_review_employee
 *
 * @author ruoyi
 * @date 2023-06-15
 */
public class SysReviewEmployee extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 雇工实际工作内容记录ID
     */
    private Long reviewEmployeeId;

    @Excel(name = "序号", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK, width = 6)
    private int exportSerialNumber;

    /**
     * 姓名
     */
    @Excel(name = "姓名", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String name;

    /**
     * 天数
     */
    @Excel(name = "天数", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK, width = 8)
    private float workDay;

    @Excel(name = "金额/天（元）", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK, defaultValue = "200")
    private String exportEveryDayMoney;

    @Excel(name = "应付劳务费", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String exportPayableServiceFee;

    @Excel(name = "应扣税费", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String exportTaxDeductible;

    @Excel(name = "应扣个税", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String exportSelfDeductible;

    /**
     * 费用
     */
    @Excel(name = "实发劳务费", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private BigDecimal cost;

    @Excel(name = "开户行和银行卡号", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK, height = 32)
    private String exportBankAndCardNumber;

    /**
     * 身份证
     */
    @Excel(name = "身份证号", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String idCard;

    @Excel(name = "领取人签字", headerBackgroundColor = IndexedColors.WHITE, headerColor = IndexedColors.BLACK)
    private String exportRecipients;

    /**
     * 作业时间
     */
    private String workTime;

    /**
     * 审核单ID
     */
    private Long reviewId;

    private List<WorkTimeStamp> workTimeStamp;

    //    @Excels({
//            @Excel(name = "审核单创建时间", targetAttr = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT),
//    })
    private SysReview review;

    public int getExportSerialNumber() {
        return exportSerialNumber;
    }

    public void setExportSerialNumber(int exportSerialNumber) {
        this.exportSerialNumber = exportSerialNumber;
    }

    public String getExportEveryDayMoney() {
        return exportEveryDayMoney;
    }

    public void setExportEveryDayMoney(String exportEveryDayMoney) {
        this.exportEveryDayMoney = exportEveryDayMoney;
    }

    public String getExportPayableServiceFee() {
        return exportPayableServiceFee;
    }

    public void setExportPayableServiceFee(String exportPayableServiceFee) {
        this.exportPayableServiceFee = exportPayableServiceFee;
    }

    public String getExportTaxDeductible() {
        return exportTaxDeductible;
    }

    public void setExportTaxDeductible(String exportTaxDeductible) {
        this.exportTaxDeductible = exportTaxDeductible;
    }

    public String getExportSelfDeductible() {
        return exportSelfDeductible;
    }

    public void setExportSelfDeductible(String exportSelfDeductible) {
        this.exportSelfDeductible = exportSelfDeductible;
    }

    public String getExportBankAndCardNumber() {
        return exportBankAndCardNumber;
    }

    public void setExportBankAndCardNumber(String exportBankAndCardNumber) {
        this.exportBankAndCardNumber = exportBankAndCardNumber;
    }

    public String getExportRecipients() {
        return exportRecipients;
    }

    public void setExportRecipients(String exportRecipients) {
        this.exportRecipients = exportRecipients;
    }

    public SysReview getReview() {
        return review;
    }

    public void setReview(SysReview review) {
        this.review = review;
    }

    public void setWorkTimeStamp(List<WorkTimeStamp> workTimeStamp) {
        this.workTimeStamp = workTimeStamp;
    }

    public List<WorkTimeStamp> getWorkTimeStamp() {
        return workTimeStamp;
    }

    public void setReviewEmployeeId(Long reviewEmployeeId) {
        this.reviewEmployeeId = reviewEmployeeId;
    }

    public Long getReviewEmployeeId() {
        return reviewEmployeeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkDay(float workDay) {
        this.workDay = workDay;
    }

    public float getWorkDay() {
        return workDay;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getReviewId() {
        return reviewId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("reviewEmployeeId", getReviewEmployeeId())
                .append("name", getName())
                .append("idCard", getIdCard())
                .append("workTime", getWorkTime())
                .append("workDay", getWorkDay())
                .append("cost", getCost())
                .append("reviewId", getReviewId())
                .toString();
    }
}
