package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_project_selectmap_tfinfo
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
public class SysProjectSelectmapTfinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 图幅号 */
    @Excel(name = "图幅号")
    private String mapCode;

    /** 图幅附加信息 */
    @Excel(name = "图幅附加信息")
    private String mapAddinfo;

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setMapCode(String mapCode) 
    {
        this.mapCode = mapCode;
    }

    public String getMapCode() 
    {
        return mapCode;
    }
    public void setMapAddinfo(String mapAddinfo) 
    {
        this.mapAddinfo = mapAddinfo;
    }

    public String getMapAddinfo() 
    {
        return mapAddinfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("mapCode", getMapCode())
            .append("mapAddinfo", getMapAddinfo())
            .toString();
    }
}
