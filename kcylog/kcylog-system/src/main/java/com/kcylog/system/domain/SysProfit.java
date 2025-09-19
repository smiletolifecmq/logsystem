package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 福清分公司净利润申报表对象 sys_profit
 * 
 * @author ruoyi
 * @date 2025-09-18
 */
public class SysProfit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 年份 */
    @Excel(name = "年份")
    private String nf;

    /** 月份 */
    @Excel(name = "月份")
    private String yf;

    /** 部门 */
    @Excel(name = "部门")
    private String bm;

    /** 完成生产经营收入 */
    @Excel(name = "完成生产经营收入")
    private BigDecimal jysr;

    /** 核减上月预结 */
    @Excel(name = "核减上月预结")
    private BigDecimal hjsyyj;

    /** 结算上月预留 */
    @Excel(name = "结算上月预留")
    private BigDecimal jssyyl;

    /** 预留 */
    @Excel(name = "预留")
    private BigDecimal yl;

    /** 预结 */
    @Excel(name = "预结")
    private BigDecimal yj;

    /** 上报生产经营收入 */
    @Excel(name = "上报生产经营收入")
    private BigDecimal sbjysr;

    /** 核定内部生产产值 */
    @Excel(name = "核定内部生产产值")
    private BigDecimal hdnbcz;

    /** 项目分包实际支出（不纳入绩效核算） */
    @Excel(name = "项目分包实际支出", readConverterExp = "不=纳入绩效核算")
    private BigDecimal fbje;

    /** 完成项目产生的分包金额（含第三方雇工） */
    @Excel(name = "完成项目产生的分包金额", readConverterExp = "含=第三方雇工")
    private BigDecimal wcxmje;

    /** 常规雇工 */
    @Excel(name = "常规雇工")
    private BigDecimal cggg;

    /** 劳务派遣 */
    @Excel(name = "劳务派遣")
    private BigDecimal lwpq;

    /** 博达支出 */
    @Excel(name = "博达支出")
    private BigDecimal bdzc;

    /** 研发人员申报产值（不纳入净利润核算） */
    @Excel(name = "研发人员申报产值", readConverterExp = "不=纳入净利润核算")
    private BigDecimal yfcz;

    private BigDecimal bdcchbWork;

    private BigDecimal dlxxbWork;

    private BigDecimal gcchbNumWork;

    private BigDecimal gxgcbWork;

    public BigDecimal getBdcchbWork() {
        return bdcchbWork;
    }

    public void setBdcchbWork(BigDecimal bdcchbWork) {
        this.bdcchbWork = bdcchbWork;
    }

    public BigDecimal getDlxxbWork() {
        return dlxxbWork;
    }

    public void setDlxxbWork(BigDecimal dlxxbWork) {
        this.dlxxbWork = dlxxbWork;
    }

    public BigDecimal getGcchbNumWork() {
        return gcchbNumWork;
    }

    public void setGcchbNumWork(BigDecimal gcchbNumWork) {
        this.gcchbNumWork = gcchbNumWork;
    }

    public BigDecimal getGxgcbWork() {
        return gxgcbWork;
    }

    public void setGxgcbWork(BigDecimal gxgcbWork) {
        this.gxgcbWork = gxgcbWork;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNf(String nf) 
    {
        this.nf = nf;
    }

    public String getNf() 
    {
        return nf;
    }
    public void setYf(String yf) 
    {
        this.yf = yf;
    }

    public String getYf() 
    {
        return yf;
    }
    public void setBm(String bm) 
    {
        this.bm = bm;
    }

    public String getBm() 
    {
        return bm;
    }
    public void setJysr(BigDecimal jysr) 
    {
        this.jysr = jysr;
    }

    public BigDecimal getJysr() 
    {
        return jysr;
    }
    public void setHjsyyj(BigDecimal hjsyyj) 
    {
        this.hjsyyj = hjsyyj;
    }

    public BigDecimal getHjsyyj() 
    {
        return hjsyyj;
    }
    public void setJssyyl(BigDecimal jssyyl) 
    {
        this.jssyyl = jssyyl;
    }

    public BigDecimal getJssyyl() 
    {
        return jssyyl;
    }
    public void setYl(BigDecimal yl) 
    {
        this.yl = yl;
    }

    public BigDecimal getYl() 
    {
        return yl;
    }
    public void setYj(BigDecimal yj) 
    {
        this.yj = yj;
    }

    public BigDecimal getYj() 
    {
        return yj;
    }
    public void setSbjysr(BigDecimal sbjysr) 
    {
        this.sbjysr = sbjysr;
    }

    public BigDecimal getSbjysr() 
    {
        return sbjysr;
    }
    public void setHdnbcz(BigDecimal hdnbcz) 
    {
        this.hdnbcz = hdnbcz;
    }

    public BigDecimal getHdnbcz() 
    {
        return hdnbcz;
    }
    public void setFbje(BigDecimal fbje) 
    {
        this.fbje = fbje;
    }

    public BigDecimal getFbje() 
    {
        return fbje;
    }
    public void setWcxmje(BigDecimal wcxmje) 
    {
        this.wcxmje = wcxmje;
    }

    public BigDecimal getWcxmje() 
    {
        return wcxmje;
    }
    public void setCggg(BigDecimal cggg) 
    {
        this.cggg = cggg;
    }

    public BigDecimal getCggg() 
    {
        return cggg;
    }
    public void setLwpq(BigDecimal lwpq) 
    {
        this.lwpq = lwpq;
    }

    public BigDecimal getLwpq() 
    {
        return lwpq;
    }
    public void setBdzc(BigDecimal bdzc) 
    {
        this.bdzc = bdzc;
    }

    public BigDecimal getBdzc() 
    {
        return bdzc;
    }
    public void setYfcz(BigDecimal yfcz) 
    {
        this.yfcz = yfcz;
    }

    public BigDecimal getYfcz() 
    {
        return yfcz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nf", getNf())
            .append("yf", getYf())
            .append("bm", getBm())
            .append("jysr", getJysr())
            .append("hjsyyj", getHjsyyj())
            .append("jssyyl", getJssyyl())
            .append("yl", getYl())
            .append("yj", getYj())
            .append("sbjysr", getSbjysr())
            .append("hdnbcz", getHdnbcz())
            .append("fbje", getFbje())
            .append("wcxmje", getWcxmje())
            .append("cggg", getCggg())
            .append("lwpq", getLwpq())
            .append("bdzc", getBdzc())
            .append("yfcz", getYfcz())
            .toString();
    }
}
