package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 流程配置对象 sys_process_config
 * 
 * @author ruoyi
 * @date 2023-06-08
 */
public class SysProcessConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程配置表ID */
    private Long processConfigId;

    /** 配置标题 */
    @Excel(name = "配置标题")
    private String title;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    public void setProcessConfigId(Long processConfigId)
    {
        this.processConfigId = processConfigId;
    }

    public Long getProcessConfigId()
    {
        return processConfigId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("processConfigId", getProcessConfigId())
            .append("title", getTitle())
            .append("deptId", getDeptId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
