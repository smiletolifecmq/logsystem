package com.kcylog.system.service.impl;

import com.kcylog.system.domain.ViewFqSalemapSelectmapTfinfo;
import com.kcylog.system.mapper.ViewFqSalemapSelectmapTfinfoMapper;
import com.kcylog.system.service.IViewFqSalemapSelectmapTfinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
@Service
public class ViewFqSalemapSelectmapTfinfoServiceImpl implements IViewFqSalemapSelectmapTfinfoService 
{
    @Autowired
    private ViewFqSalemapSelectmapTfinfoMapper viewFqSalemapSelectmapTfinfoMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public List<ViewFqSalemapSelectmapTfinfo> selectViewFqSalemapSelectmapTfinfoByProjectId(Long projectId)
    {
        return viewFqSalemapSelectmapTfinfoMapper.selectViewFqSalemapSelectmapTfinfoByProjectId(projectId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param viewFqSalemapSelectmapTfinfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ViewFqSalemapSelectmapTfinfo> selectViewFqSalemapSelectmapTfinfoList(ViewFqSalemapSelectmapTfinfo viewFqSalemapSelectmapTfinfo)
    {
        return viewFqSalemapSelectmapTfinfoMapper.selectViewFqSalemapSelectmapTfinfoList(viewFqSalemapSelectmapTfinfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertViewFqSalemapSelectmapTfinfo(ViewFqSalemapSelectmapTfinfo viewFqSalemapSelectmapTfinfo)
    {
        return viewFqSalemapSelectmapTfinfoMapper.insertViewFqSalemapSelectmapTfinfo(viewFqSalemapSelectmapTfinfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateViewFqSalemapSelectmapTfinfo(ViewFqSalemapSelectmapTfinfo viewFqSalemapSelectmapTfinfo)
    {
        return viewFqSalemapSelectmapTfinfoMapper.updateViewFqSalemapSelectmapTfinfo(viewFqSalemapSelectmapTfinfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteViewFqSalemapSelectmapTfinfoByProjectIds(Long[] projectIds)
    {
        return viewFqSalemapSelectmapTfinfoMapper.deleteViewFqSalemapSelectmapTfinfoByProjectIds(projectIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteViewFqSalemapSelectmapTfinfoByProjectId(Long projectId)
    {
        return viewFqSalemapSelectmapTfinfoMapper.deleteViewFqSalemapSelectmapTfinfoByProjectId(projectId);
    }
}
