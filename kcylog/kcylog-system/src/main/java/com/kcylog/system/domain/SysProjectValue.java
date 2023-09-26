package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 项目产值对象 sys_project_value
 * 
 * @author ruoyi
 * @date 2023-09-26
 */
public class SysProjectValue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 产值ID */
    private Long valueId;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 人员名称 */
    @Excel(name = "人员名称")
    private String userName;

    /** 占比 */
    @Excel(name = "占比")
    private Long proportion;

    public void setValueId(Long valueId) 
    {
        this.valueId = valueId;
    }

    public Long getValueId() 
    {
        return valueId;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setProportion(Long proportion) 
    {
        this.proportion = proportion;
    }

    public Long getProportion() 
    {
        return proportion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("valueId", getValueId())
            .append("projectId", getProjectId())
            .append("userName", getUserName())
            .append("proportion", getProportion())
            .toString();
    }
}
