package com.kcylog.system.service.impl;

import com.kcylog.system.domain.ViewFqProjectWorkResourceArrange;
import com.kcylog.system.mapper.ViewFqProjectWorkResourceArrangeMapper;
import com.kcylog.system.service.IViewFqProjectWorkResourceArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员安排比例视图Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-03-27
 */
@Service
public class ViewFqProjectWorkResourceArrangeServiceImpl implements IViewFqProjectWorkResourceArrangeService 
{
    @Autowired
    private ViewFqProjectWorkResourceArrangeMapper viewFqProjectWorkResourceArrangeMapper;

    /**
     * 查询人员安排比例视图
     * 
     * @param projectId 人员安排比例视图主键
     * @return 人员安排比例视图
     */
    @Override
    public ViewFqProjectWorkResourceArrange selectViewFqProjectWorkResourceArrangeByProjectId(Long projectId)
    {
        return viewFqProjectWorkResourceArrangeMapper.selectViewFqProjectWorkResourceArrangeByProjectId(projectId);
    }

    /**
     * 查询人员安排比例视图列表
     * 
     * @param viewFqProjectWorkResourceArrange 人员安排比例视图
     * @return 人员安排比例视图
     */
    @Override
    public List<ViewFqProjectWorkResourceArrange> selectViewFqProjectWorkResourceArrangeList(ViewFqProjectWorkResourceArrange viewFqProjectWorkResourceArrange)
    {
        return viewFqProjectWorkResourceArrangeMapper.selectViewFqProjectWorkResourceArrangeList(viewFqProjectWorkResourceArrange);
    }

    /**
     * 新增人员安排比例视图
     * 
     * @param viewFqProjectWorkResourceArrange 人员安排比例视图
     * @return 结果
     */
    @Override
    public int insertViewFqProjectWorkResourceArrange(ViewFqProjectWorkResourceArrange viewFqProjectWorkResourceArrange)
    {
        return viewFqProjectWorkResourceArrangeMapper.insertViewFqProjectWorkResourceArrange(viewFqProjectWorkResourceArrange);
    }

    /**
     * 修改人员安排比例视图
     * 
     * @param viewFqProjectWorkResourceArrange 人员安排比例视图
     * @return 结果
     */
    @Override
    public int updateViewFqProjectWorkResourceArrange(ViewFqProjectWorkResourceArrange viewFqProjectWorkResourceArrange)
    {
        return viewFqProjectWorkResourceArrangeMapper.updateViewFqProjectWorkResourceArrange(viewFqProjectWorkResourceArrange);
    }

    /**
     * 批量删除人员安排比例视图
     * 
     * @param projectIds 需要删除的人员安排比例视图主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectWorkResourceArrangeByProjectIds(Long[] projectIds)
    {
        return viewFqProjectWorkResourceArrangeMapper.deleteViewFqProjectWorkResourceArrangeByProjectIds(projectIds);
    }

    /**
     * 删除人员安排比例视图信息
     * 
     * @param projectId 人员安排比例视图主键
     * @return 结果
     */
    @Override
    public int deleteViewFqProjectWorkResourceArrangeByProjectId(Long projectId)
    {
        return viewFqProjectWorkResourceArrangeMapper.deleteViewFqProjectWorkResourceArrangeByProjectId(projectId);
    }
}
