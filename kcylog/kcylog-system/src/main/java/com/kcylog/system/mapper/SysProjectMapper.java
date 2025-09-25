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

    public int jsProjectCzXs(String[] projectIds);

    public int updateFbMoney(Map<String, Object> params);

    public List<SysProject> selectSysProjectListUpcoming(SysProject sysProject);

    public int updateIsShowByProjectId(SysProject sysProject);

    public int updateIsShowByReviewId(SysProject sysProject);

    public int updateSysProjectForMq(SysProject sysProject);

    public int deleteSysProjectByCode(String projectCode);

    public int jsProjectLiRunCz(String[] projectIds);

    public int jsProjectLiRunCzForReview(SysProject sysProject);

    public List<SysProject> selectSysProjectListHandover(SysProject sysProject);

    public List<SysProject> selectSysProjectWaitOneCheck(SysProject sysProject);

    public List<SysProject> selectSysProjectWaitTwoCheck(SysProject sysProject);

    public SysProject checkProjectKeyUniqueByViewProjectId(String projectNum);

    public List<SysProject> listProjectOperateTJ(SysProject sysProject);

    public int updateSysProjectCqBz(SysProject sysProject);

    public List<SysProject> listProjectOperateCq(SysProject sysProject);

    public List<SysProject> listProjectOperateExport(SysProject sysProject);

    public int updateSysProjectDrawStatus(SysProject sysProject);

    public List<SysProject> selectSysProjectListForFb(SysProject sysProject);

    public List<SysProject> selectSysProjectListForFbTwoCheck(SysProject sysProject);

    public List<SysProject> listProjectOperateValue(SysProject sysProject);

    public SysProject selectSysProjectByProjectNum(String xmbh);

    public List<SysProject> selectRwapz(SysProject sysProject);

    public List<SysProject> selectZyz(SysProject sysProject);

    public List<SysProject> selectXmfbs(SysProject sysProject);

    public List<SysProject> selectZybj(SysProject sysProject);

    public List<SysProject> selectYgmoney(SysProject sysProject);

    public List<SysProject> selectYj(SysProject sysProject);

    public List<SysProject> selectEj(SysProject sysProject);

    public List<SysProject> selectXmcjscjy(SysProject sysProject);

    public List<SysProject> selectCgsd(SysProject sysProject);

    public List<SysProject> selectJyczwtb(SysProject sysProject);

    public List<SysProject> selectNdjycz(SysProject sysProject);

    public List<SysProject> selectXMCQ(SysProject sysProject);

    public List<SysProject> selectXMCQYGY(SysProject sysProject);

    public List<SysProject> selectWYIBJZ(SysProject sysProject);

    public List<SysProject> selectWYIBJY(SysProject sysProject);

    public List<SysProject> selectWEJZ(SysProject sysProject);

    public List<SysProject> selectWEJY(SysProject sysProject);

    public List<SysProject> listProjectHjMonth(SysProject sysProject);

    public List<SysProject> selectRwapzList(SysProject sysProject);

    public List<SysProject> selectSysProjectByNbcz();

    public List<SysProject> selectSysProjectNBCZ(SysProject sysProject);
}
