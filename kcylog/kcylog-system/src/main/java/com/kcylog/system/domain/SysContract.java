package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同管理对象 sys_contract
 * 
 * @author ruoyi
 * @date 2025-03-26
 */
public class SysContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 合同ID */
    private Long conId;

    /** 合同编号 */
    @Excel(name = "序号")
    private int number;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String num;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String name;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String khmc;

    /** 客户分类 */
    @Excel(name = "客户分类")
    private String classify;

    /** 负责人 */
    @Excel(name = "负责人")
    private String fzr;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private BigDecimal htje;

    /** 签订日期 */
    @Excel(name = "签订日期")
    private String qdrq;

    /** 结算金额 */
    @Excel(name = "结算金额")
    private BigDecimal jsje;

    /** 资金来源 */
    @Excel(name = "资金来源")
    private String zjly;

    /** 业务属性 */
    @Excel(name = "业务属性")
    private String ywsx;

    /** 已开票金额 */
    @Excel(name = "已开票金额")
    private BigDecimal ykpje;

    /** 未开票金额 */
    @Excel(name = "未开票金额")
    private BigDecimal wkpje;

    /** 已到账金额 */
    @Excel(name = "已到账金额")
    private BigDecimal ydzje;

    /** 未到账金额 */
    @Excel(name = "未到账金额")
    private BigDecimal wdzje;

    /** 合同约定支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "合同约定支付时间", width = 30, dateFormat = "yyyy年MM月dd日")
    private Date ydsj;

    /** 发函时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发函时间", width = 30, dateFormat = "yyyy年MM月dd日")
    private Date fhsj;

    /** 诉讼到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "诉讼到期时间", width = 30, dateFormat = "yyyy年MM月dd日")
    private Date ssdqsj;

    /** 财审情况 */
    @Excel(name = "财审情况")
    private String csqk;

    /** 合同支付条款 */
    @Excel(name = "合同支付条款")
    private String htzftk;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    private long day;

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setConId(Long conId)
    {
        this.conId = conId;
    }

    public Long getConId() 
    {
        return conId;
    }
    public void setNum(String num) 
    {
        this.num = num;
    }

    public String getNum() 
    {
        return num;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setKhmc(String khmc) 
    {
        this.khmc = khmc;
    }

    public String getKhmc() 
    {
        return khmc;
    }
    public void setClassify(String classify) 
    {
        this.classify = classify;
    }

    public String getClassify() 
    {
        return classify;
    }
    public void setFzr(String fzr) 
    {
        this.fzr = fzr;
    }

    public String getFzr() 
    {
        return fzr;
    }
    public void setHtje(BigDecimal htje) 
    {
        this.htje = htje;
    }

    public BigDecimal getHtje() 
    {
        return htje;
    }
    public void setQdrq(String qdrq) 
    {
        this.qdrq = qdrq;
    }

    public String getQdrq() 
    {
        return qdrq;
    }
    public void setJsje(BigDecimal jsje) 
    {
        this.jsje = jsje;
    }

    public BigDecimal getJsje() 
    {
        return jsje;
    }
    public void setZjly(String zjly) 
    {
        this.zjly = zjly;
    }

    public String getZjly() 
    {
        return zjly;
    }
    public void setYwsx(String ywsx) 
    {
        this.ywsx = ywsx;
    }

    public String getYwsx() 
    {
        return ywsx;
    }
    public void setYkpje(BigDecimal ykpje) 
    {
        this.ykpje = ykpje;
    }

    public BigDecimal getYkpje() 
    {
        return ykpje;
    }
    public void setWkpje(BigDecimal wkpje) 
    {
        this.wkpje = wkpje;
    }

    public BigDecimal getWkpje() 
    {
        return wkpje;
    }
    public void setYdzje(BigDecimal ydzje) 
    {
        this.ydzje = ydzje;
    }

    public BigDecimal getYdzje() 
    {
        return ydzje;
    }
    public void setWdzje(BigDecimal wdzje) 
    {
        this.wdzje = wdzje;
    }

    public BigDecimal getWdzje() 
    {
        return wdzje;
    }
    public void setYdsj(Date ydsj) 
    {
        this.ydsj = ydsj;
    }

    public Date getYdsj() 
    {
        return ydsj;
    }
    public void setFhsj(Date fhsj) 
    {
        this.fhsj = fhsj;
    }

    public Date getFhsj() 
    {
        return fhsj;
    }
    public void setSsdqsj(Date ssdqsj) 
    {
        this.ssdqsj = ssdqsj;
    }

    public Date getSsdqsj() 
    {
        return ssdqsj;
    }
    public void setCsqk(String csqk) 
    {
        this.csqk = csqk;
    }

    public String getCsqk() 
    {
        return csqk;
    }
    public void setHtzftk(String htzftk) 
    {
        this.htzftk = htzftk;
    }

    public String getHtzftk() 
    {
        return htzftk;
    }
    public void setBz(String bz) 
    {
        this.bz = bz;
    }

    public String getBz() 
    {
        return bz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("conId", getConId())
            .append("num", getNum())
            .append("name", getName())
            .append("khmc", getKhmc())
            .append("classify", getClassify())
            .append("fzr", getFzr())
            .append("htje", getHtje())
            .append("qdrq", getQdrq())
            .append("jsje", getJsje())
            .append("zjly", getZjly())
            .append("ywsx", getYwsx())
            .append("ykpje", getYkpje())
            .append("wkpje", getWkpje())
            .append("ydzje", getYdzje())
            .append("wdzje", getWdzje())
            .append("ydsj", getYdsj())
            .append("fhsj", getFhsj())
            .append("ssdqsj", getSsdqsj())
            .append("csqk", getCsqk())
            .append("htzftk", getHtzftk())
            .append("bz", getBz())
            .toString();
    }
}
