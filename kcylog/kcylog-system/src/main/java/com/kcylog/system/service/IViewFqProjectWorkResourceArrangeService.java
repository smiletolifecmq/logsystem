package com.kcylog.system.service;

import com.kcylog.system.domain.ViewFqProjectWorkResourceArrange;

import java.util.List;

/**
 * 人员安排比例视图Service接口
 * 
 * @author ruoyi
 * @date 2024-03-27
 */
public interface IViewFqProjectWorkResourceArrangeService 
{
    /**
     * 查询人员安排比例视图
     * 
     * @param projectId 人员安排比例视图主键
     * @return 人员安排比例视图
     */
    public ViewFqProjectWorkResourceArrange selectViewFqProjectWorkResourceArrangeByProjectId(Long projectId);

    /**
     * 查询人员安排比例视图列表
     * 
     * @param viewFqProjectWorkResourceArrange 人员安排比例视图
     * @return 人员安排比例视图集合
     */
    public List<ViewFqProjectWorkResourceArrange> selectViewFqProjectWorkResourceArrangeList(ViewFqProjectWorkResourceArrange viewFqProjectWorkResourceArrange);

    /**
     * 新增人员安排比例视图
     * 
     * @param viewFqProjectWorkResourceArrange 人员安排比例视图
     * @return 结果
     */
    public int insertViewFqProjectWorkResourceArrange(ViewFqProjectWorkResourceArrange viewFqProjectWorkResourceArrange);

    /**
     * 修改人员安排比例视图
     * 
     * @param viewFqProjectWorkResourceArrange 人员安排比例视图
     * @return 结果
     */
    public int updateViewFqProjectWorkResourceArrange(ViewFqProjectWorkResourceArrange viewFqProjectWorkResourceArrange);

    /**
     * 批量删除人员安排比例视图
     * 
     * @param projectIds 需要删除的人员安排比例视图主键集合
     * @return 结果
     */
    public int deleteViewFqProjectWorkResourceArrangeByProjectIds(Long[] projectIds);

    /**
     * 删除人员安排比例视图信息
     * 
     * @param projectId 人员安排比例视图主键
     * @return 结果
     */
    public int deleteViewFqProjectWorkResourceArrangeByProjectId(Long projectId);
}
