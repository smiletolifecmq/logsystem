package com.kcylog.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 客户调查满意度对象 sys_khdcmyd
 * 
 * @author ruoyi
 * @date 2025-04-11
 */
public class SysKhdcmyd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 拜访时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拜访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date bfsj;

    /** 拜访形式 */
    @Excel(name = "拜访形式")
    private String bfxs;

    /** 市场专员 */
    @Excel(name = "市场专员")
    private String sczy;

    /** 随行人员 */
    @Excel(name = "随行人员")
    private String sxry;

    /** 拜访客户名称 */
    @Excel(name = "拜访客户名称")
    private String khmc;

    /** 拜访对象姓名 */
    @Excel(name = "拜访对象姓名")
    private String dxxm;

    /** 拜访具体内容 */
    @Excel(name = "拜访具体内容")
    private String nr;

    /** 需要改进工作 */
    @Excel(name = "需要改进工作")
    private String gjgz;

    /** 工期满意度 */
    @Excel(name = "工期满意度")
    private String gqmyd;

    /** 产品质量满意度 */
    @Excel(name = "产品质量满意度")
    private String zlmyd;

    /** 服务满意度 */
    @Excel(name = "服务满意度")
    private String fwmyd;

    /** 项目负责人服务态度 */
    @Excel(name = "项目负责人服务态度")
    private String fzrtd;

    /** 接洽人员服务态度 */
    @Excel(name = "接洽人员服务态度")
    private String ryfwd;

    /** 综合评价 */
    @Excel(name = "综合评价")
    private String zhpj;

    /** 满意率 */
    @Excel(name = "满意率")
    private String myl;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBfsj(Date bfsj) 
    {
        this.bfsj = bfsj;
    }

    public Date getBfsj() 
    {
        return bfsj;
    }
    public void setBfxs(String bfxs) 
    {
        this.bfxs = bfxs;
    }

    public String getBfxs() 
    {
        return bfxs;
    }
    public void setSczy(String sczy) 
    {
        this.sczy = sczy;
    }

    public String getSczy() 
    {
        return sczy;
    }
    public void setSxry(String sxry) 
    {
        this.sxry = sxry;
    }

    public String getSxry() 
    {
        return sxry;
    }
    public void setKhmc(String khmc) 
    {
        this.khmc = khmc;
    }

    public String getKhmc() 
    {
        return khmc;
    }
    public void setDxxm(String dxxm) 
    {
        this.dxxm = dxxm;
    }

    public String getDxxm() 
    {
        return dxxm;
    }
    public void setNr(String nr) 
    {
        this.nr = nr;
    }

    public String getNr() 
    {
        return nr;
    }
    public void setGjgz(String gjgz) 
    {
        this.gjgz = gjgz;
    }

    public String getGjgz() 
    {
        return gjgz;
    }
    public void setGqmyd(String gqmyd) 
    {
        this.gqmyd = gqmyd;
    }

    public String getGqmyd() 
    {
        return gqmyd;
    }
    public void setZlmyd(String zlmyd) 
    {
        this.zlmyd = zlmyd;
    }

    public String getZlmyd() 
    {
        return zlmyd;
    }
    public void setFwmyd(String fwmyd) 
    {
        this.fwmyd = fwmyd;
    }

    public String getFwmyd() 
    {
        return fwmyd;
    }
    public void setFzrtd(String fzrtd) 
    {
        this.fzrtd = fzrtd;
    }

    public String getFzrtd() 
    {
        return fzrtd;
    }
    public void setRyfwd(String ryfwd) 
    {
        this.ryfwd = ryfwd;
    }

    public String getRyfwd() 
    {
        return ryfwd;
    }
    public void setZhpj(String zhpj) 
    {
        this.zhpj = zhpj;
    }

    public String getZhpj() 
    {
        return zhpj;
    }
    public void setMyl(String myl) 
    {
        this.myl = myl;
    }

    public String getMyl() 
    {
        return myl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bfsj", getBfsj())
            .append("bfxs", getBfxs())
            .append("sczy", getSczy())
            .append("sxry", getSxry())
            .append("khmc", getKhmc())
            .append("dxxm", getDxxm())
            .append("nr", getNr())
            .append("gjgz", getGjgz())
            .append("gqmyd", getGqmyd())
            .append("zlmyd", getZlmyd())
            .append("fwmyd", getFwmyd())
            .append("fzrtd", getFzrtd())
            .append("ryfwd", getRyfwd())
            .append("zhpj", getZhpj())
            .append("myl", getMyl())
            .toString();
    }
}
