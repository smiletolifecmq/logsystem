package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 责权发生制开票明细对象 sys_management_responsibilities_info
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public class SysManagementResponsibilitiesInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long infoId;

    @Excel(name = "序列")
    private int num;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String infoXmmc;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String infoHtbh;

    /** 甲方单位 */
    @Excel(name = "甲方单位")
    private String infoJfdw;

    /** 签订合同额（元） */
    @Excel(name = "签订合同额（元）")
    private BigDecimal infoQdhtje;

    /** 开票时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date infoTime;

    private Long zqId;

    /** 开票金额 */
    @Excel(name = "开票金额")
    private BigDecimal infoMoney;

    @Excel(name = "市场专员")
    private String infoZrr;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getInfoZrr() {
        return infoZrr;
    }

    public void setInfoZrr(String infoZrr) {
        this.infoZrr = infoZrr;
    }

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public Long getInfoId() 
    {
        return infoId;
    }
    public void setInfoXmmc(String infoXmmc) 
    {
        this.infoXmmc = infoXmmc;
    }

    public String getInfoXmmc() 
    {
        return infoXmmc;
    }
    public void setInfoHtbh(String infoHtbh) 
    {
        this.infoHtbh = infoHtbh;
    }

    public String getInfoHtbh() 
    {
        return infoHtbh;
    }
    public void setInfoJfdw(String infoJfdw) 
    {
        this.infoJfdw = infoJfdw;
    }

    public String getInfoJfdw() 
    {
        return infoJfdw;
    }
    public void setInfoQdhtje(BigDecimal infoQdhtje) 
    {
        this.infoQdhtje = infoQdhtje;
    }

    public BigDecimal getInfoQdhtje() 
    {
        return infoQdhtje;
    }
    public void setInfoTime(Date infoTime) 
    {
        this.infoTime = infoTime;
    }

    public Date getInfoTime() 
    {
        return infoTime;
    }
    public void setZqId(Long zqId) 
    {
        this.zqId = zqId;
    }

    public Long getZqId() 
    {
        return zqId;
    }
    public void setInfoMoney(BigDecimal infoMoney) 
    {
        this.infoMoney = infoMoney;
    }

    public BigDecimal getInfoMoney() 
    {
        return infoMoney;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("infoId", getInfoId())
            .append("infoXmmc", getInfoXmmc())
            .append("infoHtbh", getInfoHtbh())
            .append("infoJfdw", getInfoJfdw())
            .append("infoQdhtje", getInfoQdhtje())
            .append("infoTime", getInfoTime())
            .append("zqId", getZqId())
            .append("infoMoney", getInfoMoney())
            .toString();
    }
}
