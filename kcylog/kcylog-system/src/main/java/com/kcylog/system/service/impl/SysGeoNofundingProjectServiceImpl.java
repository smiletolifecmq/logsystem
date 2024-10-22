package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysGeoNofundingProjectMapper;
import com.kcylog.system.domain.SysGeoNofundingProject;
import com.kcylog.system.service.ISysGeoNofundingProjectService;

/**
 * 无经费服务项目统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-10-22
 */
@Service
public class SysGeoNofundingProjectServiceImpl implements ISysGeoNofundingProjectService 
{
    @Autowired
    private SysGeoNofundingProjectMapper sysGeoNofundingProjectMapper;

    /**
     * 查询无经费服务项目统计
     * 
     * @param id 无经费服务项目统计主键
     * @return 无经费服务项目统计
     */
    @Override
    public SysGeoNofundingProject selectSysGeoNofundingProjectById(Long id)
    {
        return sysGeoNofundingProjectMapper.selectSysGeoNofundingProjectById(id);
    }

    /**
     * 查询无经费服务项目统计列表
     * 
     * @param sysGeoNofundingProject 无经费服务项目统计
     * @return 无经费服务项目统计
     */
    @Override
    public List<SysGeoNofundingProject> selectSysGeoNofundingProjectList(SysGeoNofundingProject sysGeoNofundingProject)
    {
        return sysGeoNofundingProjectMapper.selectSysGeoNofundingProjectList(sysGeoNofundingProject);
    }

    /**
     * 新增无经费服务项目统计
     * 
     * @param sysGeoNofundingProject 无经费服务项目统计
     * @return 结果
     */
    @Override
    public int insertSysGeoNofundingProject(SysGeoNofundingProject sysGeoNofundingProject)
    {
        sysGeoNofundingProject.setCreateTime(DateUtils.getNowDate());
        return sysGeoNofundingProjectMapper.insertSysGeoNofundingProject(sysGeoNofundingProject);
    }

    /**
     * 修改无经费服务项目统计
     * 
     * @param sysGeoNofundingProject 无经费服务项目统计
     * @return 结果
     */
    @Override
    public int updateSysGeoNofundingProject(SysGeoNofundingProject sysGeoNofundingProject)
    {
        return sysGeoNofundingProjectMapper.updateSysGeoNofundingProject(sysGeoNofundingProject);
    }

    /**
     * 批量删除无经费服务项目统计
     * 
     * @param ids 需要删除的无经费服务项目统计主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoNofundingProjectByIds(Long[] ids)
    {
        return sysGeoNofundingProjectMapper.deleteSysGeoNofundingProjectByIds(ids);
    }

    /**
     * 删除无经费服务项目统计信息
     * 
     * @param id 无经费服务项目统计主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoNofundingProjectById(Long id)
    {
        return sysGeoNofundingProjectMapper.deleteSysGeoNofundingProjectById(id);
    }
}
