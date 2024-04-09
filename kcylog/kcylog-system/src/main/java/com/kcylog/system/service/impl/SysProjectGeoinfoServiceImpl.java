package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysProjectGeoinfo;
import com.kcylog.system.mapper.SysProjectGeoinfoMapper;
import com.kcylog.system.service.ISysProjectGeoinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目坐标系Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
@Service
public class SysProjectGeoinfoServiceImpl implements ISysProjectGeoinfoService 
{
    @Autowired
    private SysProjectGeoinfoMapper sysProjectGeoinfoMapper;

    /**
     * 查询项目坐标系
     * 
     * @param projectId 项目坐标系主键
     * @return 项目坐标系
     */
    @Override
    public List<SysProjectGeoinfo> selectSysProjectGeoinfoByProjectId(Long projectId)
    {
        return sysProjectGeoinfoMapper.selectSysProjectGeoinfoByProjectId(projectId);
    }

    /**
     * 查询项目坐标系列表
     * 
     * @param sysProjectGeoinfo 项目坐标系
     * @return 项目坐标系
     */
    @Override
    public List<SysProjectGeoinfo> selectSysProjectGeoinfoList(SysProjectGeoinfo sysProjectGeoinfo)
    {
        return sysProjectGeoinfoMapper.selectSysProjectGeoinfoList(sysProjectGeoinfo);
    }

    /**
     * 新增项目坐标系
     * 
     * @param sysProjectGeoinfo 项目坐标系
     * @return 结果
     */
    @Override
    public int insertSysProjectGeoinfo(SysProjectGeoinfo sysProjectGeoinfo)
    {
        return sysProjectGeoinfoMapper.insertSysProjectGeoinfo(sysProjectGeoinfo);
    }

    /**
     * 修改项目坐标系
     * 
     * @param sysProjectGeoinfo 项目坐标系
     * @return 结果
     */
    @Override
    public int updateSysProjectGeoinfo(SysProjectGeoinfo sysProjectGeoinfo)
    {
        return sysProjectGeoinfoMapper.updateSysProjectGeoinfo(sysProjectGeoinfo);
    }

    /**
     * 批量删除项目坐标系
     * 
     * @param projectIds 需要删除的项目坐标系主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectGeoinfoByProjectIds(Long[] projectIds)
    {
        return sysProjectGeoinfoMapper.deleteSysProjectGeoinfoByProjectIds(projectIds);
    }

    /**
     * 删除项目坐标系信息
     * 
     * @param projectId 项目坐标系主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectGeoinfoByProjectId(Long projectId)
    {
        return sysProjectGeoinfoMapper.deleteSysProjectGeoinfoByProjectId(projectId);
    }
}
