package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 经营开票统计对象 sys_management_invoicing
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
public class SysManagementInvoicing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 开票ID */
    private Long kpId;

    /** 序号 */
    @Excel(name = "序号")
    private int num;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String kpHtmc;

    /** 合同编号 */
    @Excel(name = "合同编号/工程编号")
    private String kpHtbh;

    /** 负责部门 */
    @Excel(name = "负责部门（负责部门）")
    private String kpFzbm;

    /** 负责人 */
    @Excel(name = "责任人")
    private String kpFzr;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String kpKhmc;

    /** 客户分类 */
    @Excel(name = "客户分类")
    private String kpKhfl;

    /** 业务性质 */
    @Excel(name = "业务性质")
    private String kpYwxz;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private String kpHtje;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date kpKprq;

    /** 开票金额 */
    @Excel(name = "开票记录-开票金额")
    private BigDecimal kpKpje;

    /** 发票号 */
    @Excel(name = "发票号")
    private String kpFph;

    /** 发票类型 */
    @Excel(name = "发票类型")
    private String kpFplx;

    /** 此票为红冲票的时候，记录被红冲的票号 */
    @Excel(name = "被红冲票号")
    private String kpHcph;

    /** 此票为红冲票的时候，记录被红冲的票号的开票时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "原发票开票时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date kpYfpsj;

    /** 是否属于上报权责发生制清单内 */
    @Excel(name = "是否属于上报权责发生制清单内")
    private String kpIsZqd;

    /** 属于上报权责发生制清单内开票金额 */
    @Excel(name = "属于上报权责发生制清单内开票金额")
    private BigDecimal kpZqdMoney;

    /** 开票对应的到账时间 */
    @Excel(name = "到账时间")
    private String kpDzsj;

    /** 销售方 */
    @Excel(name = "销售方")
    private String kpXsf;

    /** 备注 */
    @Excel(name = "备注")
    private String kpBz;

    /** 开票类型1正常2红冲 */
    private Long kpType;

    private String MonthString;

    public String getMonthString() {
        return MonthString;
    }

    public void setMonthString(String monthString) {
        MonthString = monthString;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setKpId(Long kpId)
    {
        this.kpId = kpId;
    }

    public Long getKpId() 
    {
        return kpId;
    }
    public void setKpHtmc(String kpHtmc) 
    {
        this.kpHtmc = kpHtmc;
    }

    public String getKpHtmc() 
    {
        return kpHtmc;
    }
    public void setKpHtbh(String kpHtbh) 
    {
        this.kpHtbh = kpHtbh;
    }

    public String getKpHtbh() 
    {
        return kpHtbh;
    }
    public void setKpFzbm(String kpFzbm) 
    {
        this.kpFzbm = kpFzbm;
    }

    public String getKpFzbm() 
    {
        return kpFzbm;
    }
    public void setKpFzr(String kpFzr) 
    {
        this.kpFzr = kpFzr;
    }

    public String getKpFzr() 
    {
        return kpFzr;
    }
    public void setKpKhmc(String kpKhmc) 
    {
        this.kpKhmc = kpKhmc;
    }

    public String getKpKhmc() 
    {
        return kpKhmc;
    }
    public void setKpKhfl(String kpKhfl) 
    {
        this.kpKhfl = kpKhfl;
    }

    public String getKpKhfl() 
    {
        return kpKhfl;
    }
    public void setKpYwxz(String kpYwxz) 
    {
        this.kpYwxz = kpYwxz;
    }

    public String getKpYwxz() 
    {
        return kpYwxz;
    }
    public void setKpHtje(String kpHtje)
    {
        this.kpHtje = kpHtje;
    }

    public String getKpHtje()
    {
        return kpHtje;
    }
    public void setKpKprq(Date kpKprq) 
    {
        this.kpKprq = kpKprq;
    }

    public Date getKpKprq() 
    {
        return kpKprq;
    }
    public void setKpKpje(BigDecimal kpKpje) 
    {
        this.kpKpje = kpKpje;
    }

    public BigDecimal getKpKpje() 
    {
        return kpKpje;
    }
    public void setKpFph(String kpFph) 
    {
        this.kpFph = kpFph;
    }

    public String getKpFph() 
    {
        return kpFph;
    }
    public void setKpFplx(String kpFplx) 
    {
        this.kpFplx = kpFplx;
    }

    public String getKpFplx() 
    {
        return kpFplx;
    }
    public void setKpHcph(String kpHcph) 
    {
        this.kpHcph = kpHcph;
    }

    public String getKpHcph() 
    {
        return kpHcph;
    }
    public void setKpYfpsj(Date kpYfpsj) 
    {
        this.kpYfpsj = kpYfpsj;
    }

    public Date getKpYfpsj() 
    {
        return kpYfpsj;
    }
    public void setKpIsZqd(String kpIsZqd) 
    {
        this.kpIsZqd = kpIsZqd;
    }

    public String getKpIsZqd() 
    {
        return kpIsZqd;
    }
    public void setKpZqdMoney(BigDecimal kpZqdMoney) 
    {
        this.kpZqdMoney = kpZqdMoney;
    }

    public BigDecimal getKpZqdMoney() 
    {
        return kpZqdMoney;
    }
    public void setKpDzsj(String kpDzsj) 
    {
        this.kpDzsj = kpDzsj;
    }

    public String getKpDzsj() 
    {
        return kpDzsj;
    }
    public void setKpXsf(String kpXsf) 
    {
        this.kpXsf = kpXsf;
    }

    public String getKpXsf() 
    {
        return kpXsf;
    }
    public void setKpBz(String kpBz) 
    {
        this.kpBz = kpBz;
    }

    public String getKpBz() 
    {
        return kpBz;
    }
    public void setKpType(Long kpType) 
    {
        this.kpType = kpType;
    }

    public Long getKpType() 
    {
        return kpType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("kpId", getKpId())
            .append("kpHtmc", getKpHtmc())
            .append("kpHtbh", getKpHtbh())
            .append("kpFzbm", getKpFzbm())
            .append("kpFzr", getKpFzr())
            .append("kpKhmc", getKpKhmc())
            .append("kpKhfl", getKpKhfl())
            .append("kpYwxz", getKpYwxz())
            .append("kpHtje", getKpHtje())
            .append("kpKprq", getKpKprq())
            .append("kpKpje", getKpKpje())
            .append("kpFph", getKpFph())
            .append("kpFplx", getKpFplx())
            .append("kpHcph", getKpHcph())
            .append("kpYfpsj", getKpYfpsj())
            .append("kpIsZqd", getKpIsZqd())
            .append("kpZqdMoney", getKpZqdMoney())
            .append("kpDzsj", getKpDzsj())
            .append("kpXsf", getKpXsf())
            .append("kpBz", getKpBz())
            .append("kpType", getKpType())
            .toString();
    }
}
