package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysGeoProject;
import com.kcylog.system.mapper.SysGeoProjectMapper;
import com.kcylog.system.service.ISysGeoProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地理项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
@Service
public class SysGeoProjectServiceImpl implements ISysGeoProjectService 
{
    @Autowired
    private SysGeoProjectMapper sysGeoProjectMapper;

    /**
     * 查询地理项目
     * 
     * @param projectId 地理项目主键
     * @return 地理项目
     */
    @Override
    public SysGeoProject selectSysGeoProjectByProjectId(Long projectId)
    {
        return sysGeoProjectMapper.selectSysGeoProjectByProjectId(projectId);
    }

    /**
     * 查询地理项目列表
     * 
     * @param sysGeoProject 地理项目
     * @return 地理项目
     */
    @Override
    public List<SysGeoProject> selectSysGeoProjectList(SysGeoProject sysGeoProject)
    {
        return sysGeoProjectMapper.selectSysGeoProjectList(sysGeoProject);
    }

    /**
     * 新增地理项目
     * 
     * @param sysGeoProject 地理项目
     * @return 结果
     */
    @Override
    public int insertSysGeoProject(SysGeoProject sysGeoProject)
    {
        return sysGeoProjectMapper.insertSysGeoProject(sysGeoProject);
    }

    /**
     * 修改地理项目
     * 
     * @param sysGeoProject 地理项目
     * @return 结果
     */
    @Override
    public int updateSysGeoProject(SysGeoProject sysGeoProject)
    {
        return sysGeoProjectMapper.updateSysGeoProject(sysGeoProject);
    }

    /**
     * 批量删除地理项目
     * 
     * @param projectIds 需要删除的地理项目主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoProjectByProjectIds(Long[] projectIds)
    {
        return sysGeoProjectMapper.deleteSysGeoProjectByProjectIds(projectIds);
    }

    /**
     * 删除地理项目信息
     * 
     * @param projectId 地理项目主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoProjectByProjectId(Long projectId)
    {
        return sysGeoProjectMapper.deleteSysGeoProjectByProjectId(projectId);
    }

    @Override
    public List<SysGeoProject> selectSysGeoProjectAll()
    {
        return sysGeoProjectMapper.selectSysGeoProjectAll();
    }

    @Override
    public SysGeoProject selectSysGeoProjectByProjectNum(SysGeoProject sysGeoProject)
    {
        return sysGeoProjectMapper.selectSysGeoProjectByProjectNum(sysGeoProject);
    }

    @Override
    public SysGeoProject selectSysGeoProjectByProjectNumNotSelf(SysGeoProject sysGeoProject)
    {
        return sysGeoProjectMapper.selectSysGeoProjectByProjectNumNotSelf(sysGeoProject);
    }
}
