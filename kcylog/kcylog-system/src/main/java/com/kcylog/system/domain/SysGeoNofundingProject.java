package com.kcylog.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 无经费服务项目统计对象 sys_geo_nofunding_project
 * 
 * @author ruoyi
 * @date 2024-10-22
 */
public class SysGeoNofundingProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long id;

    @Excel(name = "序号")
    private int num;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tbsj;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 内容 */
    @Excel(name = "工作内容")
    private String content;

    /** 委托人 */
    @Excel(name = "委托单位")
    private String client;

    @Excel(name = "联系人（经营人员、领导、业主等）")
    private String lxr;

    /** 负责人 */
    @Excel(name = "负责人")
    private String fzr;

    /** 工作量 */
    private String workload;

    @Excel(name = "组天")
    private Long worknum;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public Long getWorknum() {
        return worknum;
    }

    public void setWorknum(Long worknum) {
        this.worknum = worknum;
    }

    public Date getTbsj() {
        return tbsj;
    }

    public void setTbsj(Date tbsj) {
        this.tbsj = tbsj;
    }

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
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setClient(String client) 
    {
        this.client = client;
    }

    public String getClient() 
    {
        return client;
    }
    public void setWorkload(String workload) 
    {
        this.workload = workload;
    }

    public String getWorkload() 
    {
        return workload;
    }
    public void setFzr(String fzr) 
    {
        this.fzr = fzr;
    }

    public String getFzr() 
    {
        return fzr;
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
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("content", getContent())
            .append("client", getClient())
            .append("workload", getWorkload())
            .append("fzr", getFzr())
            .append("bz", getBz())
            .toString();
    }
}
