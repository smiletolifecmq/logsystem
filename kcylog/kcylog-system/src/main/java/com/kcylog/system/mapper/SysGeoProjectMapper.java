package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysGeoProject;

/**
 * 地理项目Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
public interface SysGeoProjectMapper 
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
     * 删除地理项目
     * 
     * @param projectId 地理项目主键
     * @return 结果
     */
    public int deleteSysGeoProjectByProjectId(Long projectId);

    /**
     * 批量删除地理项目
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoProjectByProjectIds(Long[] projectIds);
}
