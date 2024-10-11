package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 责权发生制对象 sys_management_responsibilities
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public class SysManagementResponsibilities extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long zqId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String zqXmmc;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String zqHtbh;

    /** 甲方单位 */
    @Excel(name = "甲方单位")
    private String zqJfdw;

    /** 签订合同额（元） */
    @Excel(name = "签订合同额（元）")
    private BigDecimal zqQdhtje;

    /** 审结后合同金额（元） */
    @Excel(name = "审结后合同金额（元）")
    private BigDecimal zqSjhtje;

    /** 合同专业类别 */
    @Excel(name = "合同专业类别")
    private String zqHtlb;

    /** 合同付款阶段 */
    @Excel(name = "合同付款阶段")
    private String zqHtjd;

    /** 合同可请款比例（%） */
    @Excel(name = "合同可请款比例（%）")
    private String zqHtqkbl;

    /** 合同可开票金额（元） */
    @Excel(name = "合同可开票金额（元）")
    private BigDecimal zqHtkkpje;

    /** 合同已开票金额（元） */
    @Excel(name = "合同已开票金额（元）")
    private BigDecimal zqYkpje;

    /** 已进款金额（元） */
    @Excel(name = "已进款金额（元）")
    private BigDecimal zqYjkje;

    /** 开票未进款金额（元） */
    @Excel(name = "开票未进款金额（元）")
    private BigDecimal zqWjkje;

    /** 本次可开票金额（元） */
    @Excel(name = "本次可开票金额（元）")
    private BigDecimal zqKkpje;

    /** 进度说明 */
    @Excel(name = "进度说明")
    private String zqJdsm;

    /** 备注 */
    @Excel(name = "备注")
    private String zqBz;

    @Excel(name = "备注")
    private String zqBzje;

    /** 市场专员 */
    @Excel(name = "市场专员")
    private String zqZrr;

    /** 责权发生制上报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "责权发生制上报时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date zqTime;

    /** 是否删除，0否 1是 */
    private Long isDelete;

    public String getZqBzje() {
        return zqBzje;
    }

    public void setZqBzje(String zqBzje) {
        this.zqBzje = zqBzje;
    }

    public void setZqId(Long zqId)
    {
        this.zqId = zqId;
    }

    public Long getZqId() 
    {
        return zqId;
    }
    public void setZqXmmc(String zqXmmc) 
    {
        this.zqXmmc = zqXmmc;
    }

    public String getZqXmmc() 
    {
        return zqXmmc;
    }
    public void setZqHtbh(String zqHtbh) 
    {
        this.zqHtbh = zqHtbh;
    }

    public String getZqHtbh() 
    {
        return zqHtbh;
    }
    public void setZqJfdw(String zqJfdw) 
    {
        this.zqJfdw = zqJfdw;
    }

    public String getZqJfdw() 
    {
        return zqJfdw;
    }
    public void setZqQdhtje(BigDecimal zqQdhtje) 
    {
        this.zqQdhtje = zqQdhtje;
    }

    public BigDecimal getZqQdhtje() 
    {
        return zqQdhtje;
    }
    public void setZqSjhtje(BigDecimal zqSjhtje) 
    {
        this.zqSjhtje = zqSjhtje;
    }

    public BigDecimal getZqSjhtje() 
    {
        return zqSjhtje;
    }
    public void setZqHtlb(String zqHtlb) 
    {
        this.zqHtlb = zqHtlb;
    }

    public String getZqHtlb() 
    {
        return zqHtlb;
    }
    public void setZqHtjd(String zqHtjd) 
    {
        this.zqHtjd = zqHtjd;
    }

    public String getZqHtjd() 
    {
        return zqHtjd;
    }
    public void setZqHtqkbl(String zqHtqkbl) 
    {
        this.zqHtqkbl = zqHtqkbl;
    }

    public String getZqHtqkbl() 
    {
        return zqHtqkbl;
    }
    public void setZqHtkkpje(BigDecimal zqHtkkpje) 
    {
        this.zqHtkkpje = zqHtkkpje;
    }

    public BigDecimal getZqHtkkpje() 
    {
        return zqHtkkpje;
    }
    public void setZqYkpje(BigDecimal zqYkpje) 
    {
        this.zqYkpje = zqYkpje;
    }

    public BigDecimal getZqYkpje() 
    {
        return zqYkpje;
    }
    public void setZqYjkje(BigDecimal zqYjkje) 
    {
        this.zqYjkje = zqYjkje;
    }

    public BigDecimal getZqYjkje() 
    {
        return zqYjkje;
    }
    public void setZqWjkje(BigDecimal zqWjkje) 
    {
        this.zqWjkje = zqWjkje;
    }

    public BigDecimal getZqWjkje() 
    {
        return zqWjkje;
    }
    public void setZqKkpje(BigDecimal zqKkpje) 
    {
        this.zqKkpje = zqKkpje;
    }

    public BigDecimal getZqKkpje() 
    {
        return zqKkpje;
    }
    public void setZqJdsm(String zqJdsm) 
    {
        this.zqJdsm = zqJdsm;
    }

    public String getZqJdsm() 
    {
        return zqJdsm;
    }
    public void setZqBz(String zqBz) 
    {
        this.zqBz = zqBz;
    }

    public String getZqBz() 
    {
        return zqBz;
    }
    public void setZqZrr(String zqZrr) 
    {
        this.zqZrr = zqZrr;
    }

    public String getZqZrr() 
    {
        return zqZrr;
    }
    public void setZqTime(Date zqTime) 
    {
        this.zqTime = zqTime;
    }

    public Date getZqTime() 
    {
        return zqTime;
    }
    public void setIsDelete(Long isDelete) 
    {
        this.isDelete = isDelete;
    }

    public Long getIsDelete() 
    {
        return isDelete;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("zqId", getZqId())
            .append("zqXmmc", getZqXmmc())
            .append("zqHtbh", getZqHtbh())
            .append("zqJfdw", getZqJfdw())
            .append("zqQdhtje", getZqQdhtje())
            .append("zqSjhtje", getZqSjhtje())
            .append("zqHtlb", getZqHtlb())
            .append("zqHtjd", getZqHtjd())
            .append("zqHtqkbl", getZqHtqkbl())
            .append("zqHtkkpje", getZqHtkkpje())
            .append("zqYkpje", getZqYkpje())
            .append("zqYjkje", getZqYjkje())
            .append("zqWjkje", getZqWjkje())
            .append("zqKkpje", getZqKkpje())
            .append("zqJdsm", getZqJdsm())
            .append("zqBz", getZqBz())
            .append("zqZrr", getZqZrr())
            .append("zqTime", getZqTime())
            .append("isDelete", getIsDelete())
            .toString();
    }
}
