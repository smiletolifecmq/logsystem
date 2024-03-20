package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysProject;

import java.util.List;
import java.util.Map;

/**
 * 项目Mapper接口
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
public interface SysProjectMapper 
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
     * 删除项目
     * 
     * @param projectId 项目主键
     * @return 结果
     */
    public int deleteSysProjectByProjectId(String projectId);

    /**
     * 批量删除项目
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProjectByProjectIds(String[] projectIds);

    public int updateSysProjectByProjectNum(SysProject sysProject);

    public SysProject checkProjectKeyUnique(String projectNum);

    public List<SysProject> listProjectOperate(SysProject sysProject);

    public int updateOutputStatusByProjectId(SysProject sysProject);

    public List<SysProject> selectSysProjectExportList(SysProject sysProject);

    public int updateLatterTime(SysProject sysProject);

    public int jsProjectCz(String[] projectIds);

    public int updateFbMoney(Map<String, Object> params);

    public List<SysProject> selectSysProjectListUpcoming(SysProject sysProject);

    public int updateIsShowByProjectId(SysProject sysProject);

    public int updateIsShowByReviewId(SysProject sysProject);

    public int updateSysProjectForMq(SysProject sysProject);

    public int deleteSysProjectByCode(String projectCode);
}
