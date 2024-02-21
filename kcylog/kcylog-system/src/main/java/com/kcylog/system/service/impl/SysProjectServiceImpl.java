package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysProject;
import com.kcylog.system.mapper.SysProjectMapper;
import com.kcylog.system.service.ISysProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
@Service
public class SysProjectServiceImpl implements ISysProjectService 
{
    @Autowired
    private SysProjectMapper sysProjectMapper;

    /**
     * 查询项目
     * 
     * @param projectId 项目主键
     * @return 项目
     */
    @Override
    public SysProject selectSysProjectByProjectId(String projectId)
    {
        return sysProjectMapper.selectSysProjectByProjectId(projectId);
    }

    /**
     * 查询项目列表
     * 
     * @param sysProject 项目
     * @return 项目
     */
    @Override
    public List<SysProject> selectSysProjectList(SysProject sysProject)
    {
        return sysProjectMapper.selectSysProjectList(sysProject);
    }

    /**
     * 新增项目
     * 
     * @param sysProject 项目
     * @return 结果
     */
    @Override
    public int insertSysProject(SysProject sysProject)
    {
        sysProject.setCreateTime(DateUtils.getNowDate());
        return sysProjectMapper.insertSysProject(sysProject);
    }

    /**
     * 修改项目
     * 
     * @param sysProject 项目
     * @return 结果
     */
    @Override
    public int updateSysProject(SysProject sysProject)
    {
        sysProject.setUpdateTime(DateUtils.getNowDate());
        return sysProjectMapper.updateSysProject(sysProject);
    }

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的项目主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectByProjectIds(String[] projectIds)
    {
        return sysProjectMapper.deleteSysProjectByProjectIds(projectIds);
    }

    /**
     * 删除项目信息
     * 
     * @param projectId 项目主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectByProjectId(String projectId)
    {
        return sysProjectMapper.deleteSysProjectByProjectId(projectId);
    }

    @Override
    public int updateSysProjectByProjectNum(SysProject sysProject)
    {
        sysProject.setUpdateTime(DateUtils.getNowDate());
        return sysProjectMapper.updateSysProjectByProjectNum(sysProject);
    }

    @Override
    public SysProject checkProjectKeyUnique(String projectNum)
    {
        return sysProjectMapper.checkProjectKeyUnique(projectNum);
    }

    @Override
    public List<SysProject> listProjectOperate(SysProject sysProject)
    {
        return sysProjectMapper.listProjectOperate(sysProject);
    }

    @Override
    public int updateOutputStatusByProjectId(SysProject sysProject)
    {
        return sysProjectMapper.updateOutputStatusByProjectId(sysProject);
    }

    @Override
    public List<SysProject> selectSysProjectExportList(SysProject sysProject)
    {
        return sysProjectMapper.selectSysProjectExportList(sysProject);
    }

    @Override
    public int updateLatterTime(SysProject sysProject)
    {
        return sysProjectMapper.updateLatterTime(sysProject);
    }

    @Override
    public int jsProjectCz(String[] projectIds)
    {
        return sysProjectMapper.jsProjectCz(projectIds);
    }

    @Override
    public int updateFbMoney(Map<String, Object> params)
    {
        return sysProjectMapper.updateFbMoney(params);
    }

    @Override
    public List<SysProject> selectSysProjectListUpcoming(SysProject sysProject)
    {
        return sysProjectMapper.selectSysProjectListUpcoming(sysProject);
    }

    @Override
    public int updateIsShowByProjectId(SysProject sysProject)
    {
        return sysProjectMapper.updateIsShowByProjectId(sysProject);
    }

    @Override
    public int updateIsShowByReviewId(SysProject sysProject)
    {
        return sysProjectMapper.updateIsShowByReviewId(sysProject);
    }

    @Override
    public int updateSysProjectForMq(SysProject sysProject)
    {
        return sysProjectMapper.updateSysProjectForMq(sysProject);
    }
}
