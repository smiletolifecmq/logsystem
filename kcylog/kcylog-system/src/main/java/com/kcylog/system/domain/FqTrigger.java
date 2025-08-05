package com.kcylog.system.domain;

import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 百川项目操作对象 fq_trigger
 * 
 * @author ruoyi
 * @date 2025-07-23
 */
public class FqTrigger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private String id;

    /** 项目id */
    @Excel(name = "项目id")
    private String projectId;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String opType;


    public void setId(String id)
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setProjectId(String projectId)
    {
        this.projectId = projectId;
    }

    public String getProjectId()
    {
        return projectId;
    }
    public void setOpType(String opType) 
    {
        this.opType = opType;
    }

    public String getOpType() 
    {
        return opType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("opType", getOpType())
            .toString();
    }
}
