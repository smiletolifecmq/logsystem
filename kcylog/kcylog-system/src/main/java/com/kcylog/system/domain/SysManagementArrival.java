package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 经营到账统计对象 sys_management_arrival
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
public class SysManagementArrival extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 到账ID */
    private Long dzId;

    /** 序号 */
    @Excel(name = "序号")
    private int num;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String dzKhmc;

    /** 合同编号 */
    @Excel(name = "合同编号/工程编号")
    private String dzHtbh;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private BigDecimal dzHtje;

    /** 业务性质 */
    @Excel(name = "业务性质")
    private String dzYwxz;

    /** 负责人 */
    @Excel(name = "负责人")
    private String dzFzr;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dzKprq;

    /** 开票金额 */
    @Excel(name = "开票记录-开票金额")
    private BigDecimal dzKpje;

    /** 发票号 */
    @Excel(name = "发票号")
    private String dzFph;

    /** 到账金额 */
    @Excel(name = "开票记录-到账金额")
    private BigDecimal dzMoney;

    /** 到账日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到账日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dzRq;

    /** 到账类型 */
    @Excel(name = "到账类型")
    private String dzType;

    /** 是否专项债资金 */
    @Excel(name = "是否专项债资金")
    private String dzIsZx;

    /** 销售方 */
    @Excel(name = "销售方")
    private String dzXsf;

    /** 备注 */
    @Excel(name = "备注")
    private String dzBz;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDzId(Long dzId)
    {
        this.dzId = dzId;
    }

    public Long getDzId() 
    {
        return dzId;
    }
    public void setDzKhmc(String dzKhmc) 
    {
        this.dzKhmc = dzKhmc;
    }

    public String getDzKhmc() 
    {
        return dzKhmc;
    }
    public void setDzHtbh(String dzHtbh) 
    {
        this.dzHtbh = dzHtbh;
    }

    public String getDzHtbh() 
    {
        return dzHtbh;
    }
    public void setDzHtje(BigDecimal dzHtje) 
    {
        this.dzHtje = dzHtje;
    }

    public BigDecimal getDzHtje() 
    {
        return dzHtje;
    }
    public void setDzYwxz(String dzYwxz) 
    {
        this.dzYwxz = dzYwxz;
    }

    public String getDzYwxz() 
    {
        return dzYwxz;
    }
    public void setDzFzr(String dzFzr) 
    {
        this.dzFzr = dzFzr;
    }

    public String getDzFzr() 
    {
        return dzFzr;
    }
    public void setDzKprq(Date dzKprq) 
    {
        this.dzKprq = dzKprq;
    }

    public Date getDzKprq() 
    {
        return dzKprq;
    }
    public void setDzKpje(BigDecimal dzKpje) 
    {
        this.dzKpje = dzKpje;
    }

    public BigDecimal getDzKpje() 
    {
        return dzKpje;
    }
    public void setDzFph(String dzFph) 
    {
        this.dzFph = dzFph;
    }

    public String getDzFph() 
    {
        return dzFph;
    }
    public void setDzMoney(BigDecimal dzMoney) 
    {
        this.dzMoney = dzMoney;
    }

    public BigDecimal getDzMoney() 
    {
        return dzMoney;
    }
    public void setDzRq(Date dzRq) 
    {
        this.dzRq = dzRq;
    }

    public Date getDzRq() 
    {
        return dzRq;
    }
    public void setDzType(String dzType) 
    {
        this.dzType = dzType;
    }

    public String getDzType() 
    {
        return dzType;
    }
    public void setDzIsZx(String dzIsZx) 
    {
        this.dzIsZx = dzIsZx;
    }

    public String getDzIsZx() 
    {
        return dzIsZx;
    }
    public void setDzXsf(String dzXsf) 
    {
        this.dzXsf = dzXsf;
    }

    public String getDzXsf() 
    {
        return dzXsf;
    }
    public void setDzBz(String dzBz) 
    {
        this.dzBz = dzBz;
    }

    public String getDzBz() 
    {
        return dzBz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dzId", getDzId())
            .append("dzKhmc", getDzKhmc())
            .append("dzHtbh", getDzHtbh())
            .append("dzHtje", getDzHtje())
            .append("dzYwxz", getDzYwxz())
            .append("dzFzr", getDzFzr())
            .append("dzKprq", getDzKprq())
            .append("dzKpje", getDzKpje())
            .append("dzFph", getDzFph())
            .append("dzMoney", getDzMoney())
            .append("dzRq", getDzRq())
            .append("dzType", getDzType())
            .append("dzIsZx", getDzIsZx())
            .append("dzXsf", getDzXsf())
            .append("dzBz", getDzBz())
            .toString();
    }
}
