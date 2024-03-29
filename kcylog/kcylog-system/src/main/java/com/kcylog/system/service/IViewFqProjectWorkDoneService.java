package com.kcylog.system.service;

import com.kcylog.system.domain.ViewFqProjectWorkDone;

import java.util.List;

/**
 * 作业办结时间Service接口
 * 
 * @author ruoyi
 * @date 2024-03-29
 */
public interface IViewFqProjectWorkDoneService 
{
    /**
     * 查询作业办结时间
     * 
     * @param projectId 作业办结时间主键
     * @return 作业办结时间
     */
    public ViewFqProjectWorkDone selectViewFqProjectWorkDoneByProjectId(Long projectId);

    /**
     * 查询作业办结时间列表
     * 
     * @param viewFqProjectWorkDone 作业办结时间
     * @return 作业办结时间集合
     */
    public List<ViewFqProjectWorkDone> selectViewFqProjectWorkDoneList(ViewFqProjectWorkDone viewFqProjectWorkDone);

    /**
     * 新增作业办结时间
     * 
     * @param viewFqProjectWorkDone 作业办结时间
     * @return 结果
     */
    public int insertViewFqProjectWorkDone(ViewFqProjectWorkDone viewFqProjectWorkDone);

    /**
     * 修改作业办结时间
     * 
     * @param viewFqProjectWorkDone 作业办结时间
     * @return 结果
     */
    public int updateViewFqProjectWorkDone(ViewFqProjectWorkDone viewFqProjectWorkDone);

    /**
     * 批量删除作业办结时间
     * 
     * @param projectIds 需要删除的作业办结时间主键集合
     * @return 结果
     */
    public int deleteViewFqProjectWorkDoneByProjectIds(Long[] projectIds);

    /**
     * 删除作业办结时间信息
     * 
     * @param projectId 作业办结时间主键
     * @return 结果
     */
    public int deleteViewFqProjectWorkDoneByProjectId(Long projectId);

    public List<ViewFqProjectWorkDone> selectViewFqProjectWorkDoneByProjectCodes(List<String> projectCodes);
}
