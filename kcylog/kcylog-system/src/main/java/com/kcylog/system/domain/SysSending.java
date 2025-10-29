package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 送件对象 sys_sending
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
public class SysSending extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 类型与份数 */
    @Excel(name = "类型与份数")
    private String lxfs;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 送件时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "送件时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date sjsj;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    private List<SjInfo> sjInfo;

    public List<SjInfo> getSjInfo() {
        return sjInfo;
    }

    public void setSjInfo(List<SjInfo> sjInfo) {
        this.sjInfo = sjInfo;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLxfs(String lxfs) 
    {
        this.lxfs = lxfs;
    }

    public String getLxfs() 
    {
        return lxfs;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setSjsj(Date sjsj) 
    {
        this.sjsj = sjsj;
    }

    public Date getSjsj() 
    {
        return sjsj;
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
            .append("lxfs", getLxfs())
            .append("projectId", getProjectId())
            .append("sjsj", getSjsj())
            .append("bz", getBz())
            .toString();
    }
}
