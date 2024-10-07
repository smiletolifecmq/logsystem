package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 应收账款对象 sys_management_collection
 * 
 * @author ruoyi
 * @date 2024-09-23
 */
public class SysManagementCollection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应收账款ID */
    private Long ysId;

    @Excel(name = "序号")
    private int num;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String ysHtmc;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String ysHtbh;

    /** 负责部门 */
    @Excel(name = "负责部门")
    private String ysFzbm;

    /** 负责人 */
    @Excel(name = "负责人")
    private String ysFzr;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String ysKhmc;

    /** 客户分类 */
    @Excel(name = "客户分类")
    private String ysKhfl;

    /** 合同金额 */
    @Excel(name = "合同金额")
    private String ysHtje;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ysKprq;

    /** 开票金额 */
    @Excel(name = "开票记录-开票金额")
    private BigDecimal ysKpje;

    /** 已到账金额 */
    @Excel(name = "开票记录-已到账金额")
    private BigDecimal ysYdzje;

    /** 未到账金额 */
    @Excel(name = "开票记录-未到账金额")
    private BigDecimal ysWdzje;

    /** 发函类型 */
    @Excel(name = "已发生催款函/请款函")
    private String ysFhlx;

    /** 发函时间 */
    @Excel(name = "发函时间（发几条写几条）")
    private String ysFhsj;

    /** 措施、未收回原因 */
    @Excel(name = "已采取催收措施，及未收回原因")
    private String ysCs;

    /** 备注 */
    @Excel(name = "备注")
    private String ysBz;

    /** 资金来源 */
    @Excel(name = "资金来源")
    private String ysZjly;

    @Excel(name = "账龄")
    private String ysZl;

    /** 发票号 */
    @Excel(name = "发票号")
    private String ysFph;

    /** 跟踪情况 */
    @Excel(name = "跟踪情况")
    private String ysGzqk;

    /** 最新一次进展情况 */
    @Excel(name = "最新一次进展情况")
    private String ysZxqk;

    /** 合同约定支付时间 */
    @Excel(name = "合同约定支付时间")
    private String ysHtrq;

    /** 诉讼到期时间 */
    @Excel(name = "诉讼到期时间")
    private String ysSsdqsj;

    /** 业主所在地是否为四城区 */
    @Excel(name = "业主所在地是否为四城区")
    private String ysIssq;

    private Long ysStatus;

    private Long ysIshz;

    private String ysKprqCs;

    private String ysKprqLast;

    private String ysSzqu;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getYsSzqu() {
        return ysSzqu;
    }

    public void setYsSzqu(String ysSzqu) {
        this.ysSzqu = ysSzqu;
    }

    public Long getYsIshz() {
        return ysIshz;
    }

    public void setYsIshz(Long ysIshz) {
        this.ysIshz = ysIshz;
    }

    public String getYsKprqLast() {
        return ysKprqLast;
    }

    public void setYsKprqLast(String ysKprqLast) {
        this.ysKprqLast = ysKprqLast;
    }

    public String getYsKprqCs() {
        return ysKprqCs;
    }

    public void setYsKprqCs(String ysKprqCs) {
        this.ysKprqCs = ysKprqCs;
    }

    public String getYsZl() {
        return ysZl;
    }

    public void setYsZl(String ysZl) {
        this.ysZl = ysZl;
    }

    public void setYsId(Long ysId)
    {
        this.ysId = ysId;
    }

    public Long getYsId() 
    {
        return ysId;
    }
    public void setYsHtmc(String ysHtmc) 
    {
        this.ysHtmc = ysHtmc;
    }

    public String getYsHtmc() 
    {
        return ysHtmc;
    }
    public void setYsHtbh(String ysHtbh) 
    {
        this.ysHtbh = ysHtbh;
    }

    public String getYsHtbh() 
    {
        return ysHtbh;
    }
    public void setYsFzbm(String ysFzbm) 
    {
        this.ysFzbm = ysFzbm;
    }

    public String getYsFzbm() 
    {
        return ysFzbm;
    }
    public void setYsFzr(String ysFzr) 
    {
        this.ysFzr = ysFzr;
    }

    public String getYsFzr() 
    {
        return ysFzr;
    }
    public void setYsKhmc(String ysKhmc) 
    {
        this.ysKhmc = ysKhmc;
    }

    public String getYsKhmc() 
    {
        return ysKhmc;
    }
    public void setYsKhfl(String ysKhfl) 
    {
        this.ysKhfl = ysKhfl;
    }

    public String getYsKhfl() 
    {
        return ysKhfl;
    }
    public void setYsHtje(String ysHtje)
    {
        this.ysHtje = ysHtje;
    }

    public String getYsHtje()
    {
        return ysHtje;
    }
    public void setYsKprq(Date ysKprq) 
    {
        this.ysKprq = ysKprq;
    }

    public Date getYsKprq() 
    {
        return ysKprq;
    }
    public void setYsKpje(BigDecimal ysKpje) 
    {
        this.ysKpje = ysKpje;
    }

    public BigDecimal getYsKpje() 
    {
        return ysKpje;
    }
    public void setYsYdzje(BigDecimal ysYdzje) 
    {
        this.ysYdzje = ysYdzje;
    }

    public BigDecimal getYsYdzje() 
    {
        return ysYdzje;
    }
    public void setYsWdzje(BigDecimal ysWdzje) 
    {
        this.ysWdzje = ysWdzje;
    }

    public BigDecimal getYsWdzje() 
    {
        return ysWdzje;
    }
    public void setYsFhlx(String ysFhlx) 
    {
        this.ysFhlx = ysFhlx;
    }

    public String getYsFhlx() 
    {
        return ysFhlx;
    }
    public void setYsFhsj(String ysFhsj) 
    {
        this.ysFhsj = ysFhsj;
    }

    public String getYsFhsj() 
    {
        return ysFhsj;
    }
    public void setYsCs(String ysCs) 
    {
        this.ysCs = ysCs;
    }

    public String getYsCs() 
    {
        return ysCs;
    }
    public void setYsBz(String ysBz) 
    {
        this.ysBz = ysBz;
    }

    public String getYsBz() 
    {
        return ysBz;
    }
    public void setYsZjly(String ysZjly) 
    {
        this.ysZjly = ysZjly;
    }

    public String getYsZjly() 
    {
        return ysZjly;
    }
    public void setYsFph(String ysFph) 
    {
        this.ysFph = ysFph;
    }

    public String getYsFph() 
    {
        return ysFph;
    }
    public void setYsGzqk(String ysGzqk) 
    {
        this.ysGzqk = ysGzqk;
    }

    public String getYsGzqk() 
    {
        return ysGzqk;
    }
    public void setYsZxqk(String ysZxqk) 
    {
        this.ysZxqk = ysZxqk;
    }

    public String getYsZxqk() 
    {
        return ysZxqk;
    }
    public void setYsHtrq(String ysHtrq) 
    {
        this.ysHtrq = ysHtrq;
    }

    public String getYsHtrq() 
    {
        return ysHtrq;
    }
    public void setYsSsdqsj(String ysSsdqsj) 
    {
        this.ysSsdqsj = ysSsdqsj;
    }

    public String getYsSsdqsj() 
    {
        return ysSsdqsj;
    }
    public void setYsIssq(String ysIssq) 
    {
        this.ysIssq = ysIssq;
    }

    public String getYsIssq() 
    {
        return ysIssq;
    }
    public void setYsStatus(Long ysStatus) 
    {
        this.ysStatus = ysStatus;
    }

    public Long getYsStatus() 
    {
        return ysStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ysId", getYsId())
            .append("ysHtmc", getYsHtmc())
            .append("ysHtbh", getYsHtbh())
            .append("ysFzbm", getYsFzbm())
            .append("ysFzr", getYsFzr())
            .append("ysKhmc", getYsKhmc())
            .append("ysKhfl", getYsKhfl())
            .append("ysHtje", getYsHtje())
            .append("ysKprq", getYsKprq())
            .append("ysKpje", getYsKpje())
            .append("ysYdzje", getYsYdzje())
            .append("ysWdzje", getYsWdzje())
            .append("ysFhlx", getYsFhlx())
            .append("ysFhsj", getYsFhsj())
            .append("ysCs", getYsCs())
            .append("ysBz", getYsBz())
            .append("ysZjly", getYsZjly())
            .append("ysFph", getYsFph())
            .append("ysGzqk", getYsGzqk())
            .append("ysZxqk", getYsZxqk())
            .append("ysHtrq", getYsHtrq())
            .append("ysSsdqsj", getYsSsdqsj())
            .append("ysIssq", getYsIssq())
            .append("ysStatus", getYsStatus())
            .toString();
    }
}
