package com.kcylog.system.service;

import com.kcylog.system.domain.SysProjectGeoinfo;

import java.util.List;

/**
 * 项目坐标系Service接口
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
public interface ISysProjectGeoinfoService 
{
    /**
     * 查询项目坐标系
     * 
     * @param projectId 项目坐标系主键
     * @return 项目坐标系
     */
    public List<SysProjectGeoinfo> selectSysProjectGeoinfoByProjectId(Long projectId);

    /**
     * 查询项目坐标系列表
     * 
     * @param sysProjectGeoinfo 项目坐标系
     * @return 项目坐标系集合
     */
    public List<SysProjectGeoinfo> selectSysProjectGeoinfoList(SysProjectGeoinfo sysProjectGeoinfo);

    /**
     * 新增项目坐标系
     * 
     * @param sysProjectGeoinfo 项目坐标系
     * @return 结果
     */
    public int insertSysProjectGeoinfo(SysProjectGeoinfo sysProjectGeoinfo);

    /**
     * 修改项目坐标系
     * 
     * @param sysProjectGeoinfo 项目坐标系
     * @return 结果
     */
    public int updateSysProjectGeoinfo(SysProjectGeoinfo sysProjectGeoinfo);

    /**
     * 批量删除项目坐标系
     * 
     * @param projectIds 需要删除的项目坐标系主键集合
     * @return 结果
     */
    public int deleteSysProjectGeoinfoByProjectIds(Long[] projectIds);

    /**
     * 删除项目坐标系信息
     * 
     * @param projectId 项目坐标系主键
     * @return 结果
     */
    public int deleteSysProjectGeoinfoByProjectId(Long projectId);
}
