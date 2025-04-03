package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 百川项目地理空间数据对象 bc_project
 * 
 * @author ruoyi
 * @date 2025-03-31
 */
public class BcProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long objectid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String shape;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String xmmc;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String xmbh;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String xmlx;

    /** 登记时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date djsj;

    /** 负责人 */
    @Excel(name = "负责人")
    private String fzr;

    /** 委托单位 */
    @Excel(name = "委托单位")
    private String wtdw;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date kssj;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jssj;

    /** 工作内容 */
    @Excel(name = "工作内容")
    private String gznr;

    /** 部门 */
    @Excel(name = "部门")
    private String bm;

    /** 工程类型（1测图类，2工程类） */
    @Excel(name = "工程类型", readConverterExp = "1=测图类，2工程类")
    private Long gclx;

    /** 1售图项目、2地籍图（宗地图）、3道路竣工（地形图）、4规划建筑竣工（地形图）、5园林竣工（测图）、6土方测量、7道路测量（含河道）、8管线探测
 */
    @Excel(name = "1售图项目、2地籍图", readConverterExp = "宗=地图")
    private Long lx;

    private List<String> clobFragments;  // 用于存储切割后的 CLOB 片段

    public List<String> getClobFragments() {
        return clobFragments;
    }

    public void setClobFragments(List<String> clobFragments) {
        this.clobFragments = clobFragments;
    }

    public void setObjectid(Long objectid)
    {
        this.objectid = objectid;
    }

    public Long getObjectid() 
    {
        return objectid;
    }
    public void setShape(String shape) 
    {
        this.shape = shape;
    }

    public String getShape() 
    {
        return shape;
    }
    public void setXmmc(String xmmc) 
    {
        this.xmmc = xmmc;
    }

    public String getXmmc() 
    {
        return xmmc;
    }
    public void setXmbh(String xmbh) 
    {
        this.xmbh = xmbh;
    }

    public String getXmbh() 
    {
        return xmbh;
    }
    public void setXmlx(String xmlx) 
    {
        this.xmlx = xmlx;
    }

    public String getXmlx() 
    {
        return xmlx;
    }
    public void setDjsj(Date djsj) 
    {
        this.djsj = djsj;
    }

    public Date getDjsj() 
    {
        return djsj;
    }
    public void setFzr(String fzr) 
    {
        this.fzr = fzr;
    }

    public String getFzr() 
    {
        return fzr;
    }
    public void setWtdw(String wtdw) 
    {
        this.wtdw = wtdw;
    }

    public String getWtdw() 
    {
        return wtdw;
    }
    public void setKssj(Date kssj) 
    {
        this.kssj = kssj;
    }

    public Date getKssj() 
    {
        return kssj;
    }
    public void setJssj(Date jssj) 
    {
        this.jssj = jssj;
    }

    public Date getJssj() 
    {
        return jssj;
    }
    public void setGznr(String gznr) 
    {
        this.gznr = gznr;
    }

    public String getGznr() 
    {
        return gznr;
    }
    public void setBm(String bm) 
    {
        this.bm = bm;
    }

    public String getBm() 
    {
        return bm;
    }
    public void setGclx(Long gclx) 
    {
        this.gclx = gclx;
    }

    public Long getGclx() 
    {
        return gclx;
    }
    public void setLx(Long lx) 
    {
        this.lx = lx;
    }

    public Long getLx() 
    {
        return lx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("objectid", getObjectid())
            .append("shape", getShape())
            .append("xmmc", getXmmc())
            .append("xmbh", getXmbh())
            .append("xmlx", getXmlx())
            .append("djsj", getDjsj())
            .append("fzr", getFzr())
            .append("wtdw", getWtdw())
            .append("kssj", getKssj())
            .append("jssj", getJssj())
            .append("gznr", getGznr())
            .append("bm", getBm())
            .append("gclx", getGclx())
            .append("lx", getLx())
            .toString();
    }
}
