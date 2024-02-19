package com.kcylog.system.service.impl;

import com.baomidou.dynamic.datasource.annotation.Slave;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.ViewFqProject;
import com.kcylog.system.mapper.ViewFqProjectMapper;
import com.kcylog.system.service.IViewFqProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-02-19
 */
@Service
public class ViewFqProjectServiceImpl implements IViewFqProjectService 
{
    @Autowired
    private ViewFqProjectMapper viewFqProjectMapper;

    /**
     * 查询项目
     * 
     * @param projectId 项目主键
     * @return 项目
     */
    @Override
    public ViewFqProject selectViewFqProjectByProjectId(String projectId)
    {
        return viewFqProjectMapper.selectViewFqProjectByProjectId(projectId);
    }

    /**
     * 查询项目列表
     * 
     * @param viewFqProject 项目
     * @return 项目
     */
    @Override
    public List<ViewFqProject> selectViewFqProjectList(ViewFqProject viewFqProject)
    {
        return viewFqProjectMapper.selectViewFqProjectList(viewFqProject);
    }

    /**
     * 新增项目
     * 
     * @param viewFqProject 项目
     * @return 结果
     */
    @Override
    public int insertViewFqProject(ViewFqProject viewFqProject)
    {
        viewFqProject.setCreateTime(DateUtils.getNowDate());
        return viewFqProjectMapper.insertViewFqProject(viewFqProject);
    }

    /**
     * 修改项目
     * 
     * @param viewFqProject 项目
     * @return 结果
     */
    @Override
    public int updateViewFqProject(ViewFqProject viewFqProject)
    {
        viewFqProject.setUpdateTime(DateUtils.getNowDate());
        return viewFqProjectMapper.updateViewFqProject(viewFqProject);
    }

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的项目主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectByProjectIds(String[] projectIds)
    {
        return viewFqProjectMapper.deleteViewFqProjectByProjectIds(projectIds);
    }

    /**
     * 删除项目信息
     * 
     * @param projectId 项目主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectByProjectId(String projectId)
    {
        return viewFqProjectMapper.deleteViewFqProjectByProjectId(projectId);
    }
}
