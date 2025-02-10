package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 客户家庭信息对象 sys_clientele_jtInfo
 * 
 * @author ruoyi
 * @date 2025-02-10
 */
public class SysClienteleJtinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 关系 */
    @Excel(name = "关系")
    private String relation;

    /** 姓名 */
    @Excel(name = "姓名")
    private String jtname;

    /** 关联ID */
    @Excel(name = "关联ID")
    private Long clienteleId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRelation(String relation) 
    {
        this.relation = relation;
    }

    public String getRelation() 
    {
        return relation;
    }
    public void setJtname(String jtname) 
    {
        this.jtname = jtname;
    }

    public String getJtname() 
    {
        return jtname;
    }
    public void setClienteleId(Long clienteleId) 
    {
        this.clienteleId = clienteleId;
    }

    public Long getClienteleId() 
    {
        return clienteleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("relation", getRelation())
            .append("jtname", getJtname())
            .append("clienteleId", getClienteleId())
            .toString();
    }
}
