package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 多版本移交对象 sys_version_handover
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
public class SysVersionHandover extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 版本 */
    @Excel(name = "版本")
    private String ver;

    /** 移交id */
    @Excel(name = "移交id")
    private Long processId;

    /** 移交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "移交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yjsj;

    /** 收件时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收件时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sjsj;

    /** 盖章时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盖章时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gzsj;

    /** 验收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yssj;

    /** 归档时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归档时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gdsj;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setVer(String ver) 
    {
        this.ver = ver;
    }

    public String getVer() 
    {
        return ver;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setYjsj(Date yjsj) 
    {
        this.yjsj = yjsj;
    }

    public Date getYjsj() 
    {
        return yjsj;
    }
    public void setSjsj(Date sjsj) 
    {
        this.sjsj = sjsj;
    }

    public Date getSjsj() 
    {
        return sjsj;
    }
    public void setGzsj(Date gzsj) 
    {
        this.gzsj = gzsj;
    }

    public Date getGzsj() 
    {
        return gzsj;
    }
    public void setYssj(Date yssj) 
    {
        this.yssj = yssj;
    }

    public Date getYssj() 
    {
        return yssj;
    }
    public void setGdsj(Date gdsj) 
    {
        this.gdsj = gdsj;
    }

    public Date getGdsj() 
    {
        return gdsj;
    }
    public void setBz(String bz) 
    {
        this.bz = bz;
    }

    public String getBz() 
    {
        return bz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ver", getVer())
            .append("processId", getProcessId())
            .append("yjsj", getYjsj())
            .append("sjsj", getSjsj())
            .append("gzsj", getGzsj())
            .append("yssj", getYssj())
            .append("gdsj", getGdsj())
            .append("bz", getBz())
            .toString();
    }
}
