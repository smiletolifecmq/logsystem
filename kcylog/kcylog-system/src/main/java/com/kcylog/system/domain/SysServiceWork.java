package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 【请填写功能名称】对象 sys_service_work
 * 
 * @author ruoyi
 * @date 2025-09-01
 */
public class SysServiceWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    /** 专业类型 */
    @Excel(name = "序号")
    private int num;

    /** 专业类型 */
    @Excel(name = "专业类型")
    private String zylx;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String xmlx;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String xmbh;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String xmmc;

    /** 文件名称/功能名称 */
    @Excel(name = "文件名称/功能名称")
    private String gnmc;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String gznr;

    /** 数量 */
    @Excel(name = "数量")
    private String sl;

    /** 任务安排日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务安排日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rwaprq;

    /** 作业人员 */
    @Excel(name = "作业人员")
    private String zyry;

    /** 作业时间段 */
    @Excel(name = "作业时间段")
    private String zysjd;

    /** 作业时长 */
    @Excel(name = "作业时长")
    private String zysc;

    /** 任务安排人员 */
    @Excel(name = "任务安排人员")
    private String rwapry;

    /** 业主单位 */
    @Excel(name = "业主单位")
    private String yzdw;

    /** 是否有归档资料 */
    @Excel(name = "是否有归档资料")
    private String gdcl;

    /** 无归档情况说明 */
    @Excel(name = "无归档情况说明")
    private String gdsm;

    /** 是否为已下单项目 */
    @Excel(name = "是否为已下单项目")
    private String xdxm;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setZylx(String zylx) 
    {
        this.zylx = zylx;
    }

    public String getZylx() 
    {
        return zylx;
    }
    public void setXmlx(String xmlx) 
    {
        this.xmlx = xmlx;
    }

    public String getXmlx() 
    {
        return xmlx;
    }
    public void setXmbh(String xmbh) 
    {
        this.xmbh = xmbh;
    }

    public String getXmbh() 
    {
        return xmbh;
    }
    public void setXmmc(String xmmc) 
    {
        this.xmmc = xmmc;
    }

    public String getXmmc() 
    {
        return xmmc;
    }
    public void setGnmc(String gnmc) 
    {
        this.gnmc = gnmc;
    }

    public String getGnmc() 
    {
        return gnmc;
    }
    public void setGznr(String gznr) 
    {
        this.gznr = gznr;
    }

    public String getGznr() 
    {
        return gznr;
    }
    public void setSl(String sl) 
    {
        this.sl = sl;
    }

    public String getSl() 
    {
        return sl;
    }
    public void setRwaprq(Date rwaprq) 
    {
        this.rwaprq = rwaprq;
    }

    public Date getRwaprq() 
    {
        return rwaprq;
    }
    public void setZyry(String zyry) 
    {
        this.zyry = zyry;
    }

    public String getZyry() 
    {
        return zyry;
    }
    public void setZysjd(String zysjd) 
    {
        this.zysjd = zysjd;
    }

    public String getZysjd() 
    {
        return zysjd;
    }
    public void setZysc(String zysc) 
    {
        this.zysc = zysc;
    }

    public String getZysc() 
    {
        return zysc;
    }
    public void setRwapry(String rwapry) 
    {
        this.rwapry = rwapry;
    }

    public String getRwapry() 
    {
        return rwapry;
    }
    public void setYzdw(String yzdw) 
    {
        this.yzdw = yzdw;
    }

    public String getYzdw() 
    {
        return yzdw;
    }
    public void setGdcl(String gdcl) 
    {
        this.gdcl = gdcl;
    }

    public String getGdcl() 
    {
        return gdcl;
    }
    public void setGdsm(String gdsm) 
    {
        this.gdsm = gdsm;
    }

    public String getGdsm() 
    {
        return gdsm;
    }
    public void setXdxm(String xdxm) 
    {
        this.xdxm = xdxm;
    }

    public String getXdxm() 
    {
        return xdxm;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("zylx", getZylx())
            .append("xmlx", getXmlx())
            .append("xmbh", getXmbh())
            .append("xmmc", getXmmc())
            .append("gnmc", getGnmc())
            .append("gznr", getGznr())
            .append("sl", getSl())
            .append("rwaprq", getRwaprq())
            .append("zyry", getZyry())
            .append("zysjd", getZysjd())
            .append("zysc", getZysc())
            .append("rwapry", getRwapry())
            .append("yzdw", getYzdw())
            .append("gdcl", getGdcl())
            .append("gdsm", getGdsm())
            .append("xdxm", getXdxm())
            .toString();
    }
}
