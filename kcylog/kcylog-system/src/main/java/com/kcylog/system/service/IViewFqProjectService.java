package com.kcylog.system.service;

import com.kcylog.system.domain.ViewFqProject;

import java.util.List;

/**
 * 项目Service接口
 * 
 * @author ruoyi
 * @date 2024-02-19
 */
public interface IViewFqProjectService 
{
    /**
     * 查询项目
     * 
     * @param projectId 项目主键
     * @return 项目
     */
    public ViewFqProject selectViewFqProjectByProjectId(String projectId);

    /**
     * 查询项目列表
     * 
     * @param viewFqProject 项目
     * @return 项目集合
     */
    public List<ViewFqProject> selectViewFqProjectList(ViewFqProject viewFqProject);

    /**
     * 新增项目
     * 
     * @param viewFqProject 项目
     * @return 结果
     */
    public int insertViewFqProject(ViewFqProject viewFqProject);

    /**
     * 修改项目
     * 
     * @param viewFqProject 项目
     * @return 结果
     */
    public int updateViewFqProject(ViewFqProject viewFqProject);

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的项目主键集合
     * @return 结果
     */
    public int deleteViewFqProjectByProjectIds(String[] projectIds);

    /**
     * 删除项目信息
     * 
     * @param projectId 项目主键
     * @return 结果
     */
    public int deleteViewFqProjectByProjectId(String projectId);
}
