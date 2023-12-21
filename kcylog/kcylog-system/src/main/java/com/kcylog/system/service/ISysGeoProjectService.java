package com.kcylog.system.service;

import com.kcylog.system.domain.SysGeoProject;

import java.util.List;

/**
 * 地理项目Service接口
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
public interface ISysGeoProjectService 
{
    /**
     * 查询地理项目
     * 
     * @param projectId 地理项目主键
     * @return 地理项目
     */
    public SysGeoProject selectSysGeoProjectByProjectId(Long projectId);

    /**
     * 查询地理项目列表
     * 
     * @param sysGeoProject 地理项目
     * @return 地理项目集合
     */
    public List<SysGeoProject> selectSysGeoProjectList(SysGeoProject sysGeoProject);

    /**
     * 新增地理项目
     * 
     * @param sysGeoProject 地理项目
     * @return 结果
     */
    public int insertSysGeoProject(SysGeoProject sysGeoProject);

    /**
     * 修改地理项目
     * 
     * @param sysGeoProject 地理项目
     * @return 结果
     */
    public int updateSysGeoProject(SysGeoProject sysGeoProject);

    /**
     * 批量删除地理项目
     * 
     * @param projectIds 需要删除的地理项目主键集合
     * @return 结果
     */
    public int deleteSysGeoProjectByProjectIds(Long[] projectIds);

    /**
     * 删除地理项目信息
     * 
     * @param projectId 地理项目主键
     * @return 结果
     */
    public int deleteSysGeoProjectByProjectId(Long projectId);

    public List<SysGeoProject> selectSysGeoProjectAll();

    public SysGeoProject selectSysGeoProjectByProjectNum(SysGeoProject sysGeoProject);

    public SysGeoProject selectSysGeoProjectByProjectNumNotSelf(SysGeoProject sysGeoProject);
}
