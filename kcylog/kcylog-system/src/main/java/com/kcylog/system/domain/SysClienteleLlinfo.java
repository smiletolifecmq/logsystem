package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 客户履历对象 sys_clientele_llInfo
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public class SysClienteleLlinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 关联id */
    @Excel(name = "关联id")
    private Long clienteleId;

    /** 单位 */
    @Excel(name = "单位")
    private String dw;

    /** 职位 */
    @Excel(name = "职位")
    private String zw;

    /** 时间段 */
    @Excel(name = "时间段")
    private String sjd;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClienteleId(Long clienteleId) 
    {
        this.clienteleId = clienteleId;
    }

    public Long getClienteleId() 
    {
        return clienteleId;
    }
    public void setDw(String dw) 
    {
        this.dw = dw;
    }

    public String getDw() 
    {
        return dw;
    }
    public void setZw(String zw) 
    {
        this.zw = zw;
    }

    public String getZw() 
    {
        return zw;
    }
    public void setSjd(String sjd) 
    {
        this.sjd = sjd;
    }

    public String getSjd() 
    {
        return sjd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clienteleId", getClienteleId())
            .append("dw", getDw())
            .append("zw", getZw())
            .append("sjd", getSjd())
            .toString();
    }
}
