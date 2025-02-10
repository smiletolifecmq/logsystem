package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 客户项目对接情况对象 sys_clientele_projectInfo
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public class SysClienteleProjectinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 关联id */
    @Excel(name = "关联id")
    private Long clienteleId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String num;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String xmname;

    /** 对接情况 */
    @Excel(name = "对接情况")
    private String content;

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
    public void setNum(String num) 
    {
        this.num = num;
    }

    public String getNum() 
    {
        return num;
    }
    public void setXmname(String xmname) 
    {
        this.xmname = xmname;
    }

    public String getXmname() 
    {
        return xmname;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clienteleId", getClienteleId())
            .append("num", getNum())
            .append("xmname", getXmname())
            .append("content", getContent())
            .toString();
    }
}
