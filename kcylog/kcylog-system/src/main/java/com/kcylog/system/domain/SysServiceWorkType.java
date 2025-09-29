package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 内部服务类型表对象 sys_service_work_type
 * 
 * @author ruoyi
 * @date 2025-09-28
 */
public class SysServiceWorkType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 项目类型
 */
    @Excel(name = "项目类型")
    private String xmlx;

    /** 后缀 */
    @Excel(name = "后缀")
    private String suffix;

    /** 作业内容 */
    @Excel(name = "作业内容")
    private String zynr;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal dj;

    /** 单位 */
    @Excel(name = "单位")
    private String dw;

    /** 参照标准 */
    @Excel(name = "参照标准")
    private String czbz;

    /** 1软件开发维护、2数据处理 */
    @Excel(name = "1软件开发维护、2数据处理")
    private Long zylx;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setXmlx(String xmlx) 
    {
        this.xmlx = xmlx;
    }

    public String getXmlx() 
    {
        return xmlx;
    }
    public void setSuffix(String suffix) 
    {
        this.suffix = suffix;
    }

    public String getSuffix() 
    {
        return suffix;
    }
    public void setZynr(String zynr) 
    {
        this.zynr = zynr;
    }

    public String getZynr() 
    {
        return zynr;
    }
    public void setDj(BigDecimal dj) 
    {
        this.dj = dj;
    }

    public BigDecimal getDj() 
    {
        return dj;
    }
    public void setDw(String dw) 
    {
        this.dw = dw;
    }

    public String getDw() 
    {
        return dw;
    }
    public void setCzbz(String czbz) 
    {
        this.czbz = czbz;
    }

    public String getCzbz() 
    {
        return czbz;
    }
    public void setZylx(Long zylx) 
    {
        this.zylx = zylx;
    }

    public Long getZylx() 
    {
        return zylx;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("xmlx", getXmlx())
            .append("suffix", getSuffix())
            .append("zynr", getZynr())
            .append("dj", getDj())
            .append("dw", getDw())
            .append("czbz", getCzbz())
            .append("zylx", getZylx())
            .toString();
    }
}
