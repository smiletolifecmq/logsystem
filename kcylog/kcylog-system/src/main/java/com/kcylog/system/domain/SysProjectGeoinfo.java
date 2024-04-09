package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 项目坐标系对象 sys_project_geoinfo
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
public class SysProjectGeoinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long projectId;

    /** 福州本地坐标系 */
    @Excel(name = "福州本地坐标系")
    private String geometry;

    /** 2000大地坐标系 */
    @Excel(name = "2000大地坐标系")
    private String geometry2000;

    /** 缓冲区坐标串，福州本地坐标系 */
    @Excel(name = "缓冲区坐标串，福州本地坐标系")
    private String bufferGeometry;

    /** 缓冲区坐标串，2000大地坐标系 */
    @Excel(name = "缓冲区坐标串，2000大地坐标系")
    private String bufferGeometry2000;

    /** 国家2000平面，福州 */
    @Excel(name = "国家2000平面，福州")
    private String geometryGauss2000;

    /** 国家2000平面，福州 */
    @Excel(name = "国家2000平面，福州")
    private String bufferGeometryGauss2000;

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setGeometry(String geometry) 
    {
        this.geometry = geometry;
    }

    public String getGeometry() 
    {
        return geometry;
    }
    public void setGeometry2000(String geometry2000) 
    {
        this.geometry2000 = geometry2000;
    }

    public String getGeometry2000() 
    {
        return geometry2000;
    }
    public void setBufferGeometry(String bufferGeometry) 
    {
        this.bufferGeometry = bufferGeometry;
    }

    public String getBufferGeometry() 
    {
        return bufferGeometry;
    }
    public void setBufferGeometry2000(String bufferGeometry2000) 
    {
        this.bufferGeometry2000 = bufferGeometry2000;
    }

    public String getBufferGeometry2000() 
    {
        return bufferGeometry2000;
    }
    public void setGeometryGauss2000(String geometryGauss2000) 
    {
        this.geometryGauss2000 = geometryGauss2000;
    }

    public String getGeometryGauss2000() 
    {
        return geometryGauss2000;
    }
    public void setBufferGeometryGauss2000(String bufferGeometryGauss2000) 
    {
        this.bufferGeometryGauss2000 = bufferGeometryGauss2000;
    }

    public String getBufferGeometryGauss2000() 
    {
        return bufferGeometryGauss2000;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("projectId", getProjectId())
            .append("geometry", getGeometry())
            .append("geometry2000", getGeometry2000())
            .append("bufferGeometry", getBufferGeometry())
            .append("bufferGeometry2000", getBufferGeometry2000())
            .append("geometryGauss2000", getGeometryGauss2000())
            .append("bufferGeometryGauss2000", getBufferGeometryGauss2000())
            .toString();
    }
}
