package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysGeoNofundingProject;

import java.util.List;

/**
 * 无经费服务项目统计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-10-22
 */
public interface SysGeoNofundingProjectMapper 
{
    /**
     * 查询无经费服务项目统计
     * 
     * @param id 无经费服务项目统计主键
     * @return 无经费服务项目统计
     */
    public SysGeoNofundingProject selectSysGeoNofundingProjectById(Long id);

    /**
     * 查询无经费服务项目统计列表
     * 
     * @param sysGeoNofundingProject 无经费服务项目统计
     * @return 无经费服务项目统计集合
     */
    public List<SysGeoNofundingProject> selectSysGeoNofundingProjectList(SysGeoNofundingProject sysGeoNofundingProject);

    /**
     * 新增无经费服务项目统计
     * 
     * @param sysGeoNofundingProject 无经费服务项目统计
     * @return 结果
     */
    public int insertSysGeoNofundingProject(SysGeoNofundingProject sysGeoNofundingProject);

    /**
     * 修改无经费服务项目统计
     * 
     * @param sysGeoNofundingProject 无经费服务项目统计
     * @return 结果
     */
    public int updateSysGeoNofundingProject(SysGeoNofundingProject sysGeoNofundingProject);

    /**
     * 删除无经费服务项目统计
     * 
     * @param id 无经费服务项目统计主键
     * @return 结果
     */
    public int deleteSysGeoNofundingProjectById(Long id);

    /**
     * 批量删除无经费服务项目统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoNofundingProjectByIds(Long[] ids);
}
