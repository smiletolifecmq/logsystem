package com.kcylog.system.mapper;

import com.kcylog.system.domain.ViewFqProjectWorkResourceArrange;

import java.util.List;

/**
 * 人员安排比例视图Mapper接口
 * 
 * @author ruoyi
 * @date 2024-03-27
 */
public interface ViewFqProjectWorkResourceArrangeMapper 
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
     * 删除人员安排比例视图
     * 
     * @param projectId 人员安排比例视图主键
     * @return 结果
     */
    public int deleteViewFqProjectWorkResourceArrangeByProjectId(Long projectId);

    /**
     * 批量删除人员安排比例视图
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqProjectWorkResourceArrangeByProjectIds(Long[] projectIds);
}
