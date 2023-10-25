package com.kcylog.system.service;

import com.kcylog.system.domain.SysProject;

import java.util.List;

/**
 * 项目Service接口
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public interface ISysProjectService 
{
    /**
     * 查询项目
     * 
     * @param projectId 项目主键
     * @return 项目
     */
    public SysProject selectSysProjectByProjectId(String projectId);

    /**
     * 查询项目列表
     * 
     * @param sysProject 项目
     * @return 项目集合
     */
    public List<SysProject> selectSysProjectList(SysProject sysProject);

    /**
     * 新增项目
     * 
     * @param sysProject 项目
     * @return 结果
     */
    public int insertSysProject(SysProject sysProject);

    /**
     * 修改项目
     * 
     * @param sysProject 项目
     * @return 结果
     */
    public int updateSysProject(SysProject sysProject);

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的项目主键集合
     * @return 结果
     */
    public int deleteSysProjectByProjectIds(String[] projectIds);

    /**
     * 删除项目信息
     * 
     * @param projectId 项目主键
     * @return 结果
     */
    public int deleteSysProjectByProjectId(String projectId);

    public int updateSysProjectByProjectNum(SysProject sysProject);

    public SysProject checkProjectKeyUnique(String projectNum);

    public List<SysProject> listProjectOperate(SysProject sysProject);

    public int updateOutputStatusByProjectId(SysProject sysProject);

    public List<SysProject> selectSysProjectExportList(SysProject sysProject);
}
