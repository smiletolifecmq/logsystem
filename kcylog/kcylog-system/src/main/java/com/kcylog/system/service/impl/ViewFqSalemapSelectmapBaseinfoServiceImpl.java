package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.ViewFqSalemapSelectmapBaseinfoMapper;
import com.kcylog.system.domain.ViewFqSalemapSelectmapBaseinfo;
import com.kcylog.system.service.IViewFqSalemapSelectmapBaseinfoService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-10
 */
@Service
public class ViewFqSalemapSelectmapBaseinfoServiceImpl implements IViewFqSalemapSelectmapBaseinfoService 
{
    @Autowired
    private ViewFqSalemapSelectmapBaseinfoMapper viewFqSalemapSelectmapBaseinfoMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public ViewFqSalemapSelectmapBaseinfo selectViewFqSalemapSelectmapBaseinfoByProjectId(Long projectId)
    {
        return viewFqSalemapSelectmapBaseinfoMapper.selectViewFqSalemapSelectmapBaseinfoByProjectId(projectId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param viewFqSalemapSelectmapBaseinfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<ViewFqSalemapSelectmapBaseinfo> selectViewFqSalemapSelectmapBaseinfoList(ViewFqSalemapSelectmapBaseinfo viewFqSalemapSelectmapBaseinfo)
    {
        return viewFqSalemapSelectmapBaseinfoMapper.selectViewFqSalemapSelectmapBaseinfoList(viewFqSalemapSelectmapBaseinfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapBaseinfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertViewFqSalemapSelectmapBaseinfo(ViewFqSalemapSelectmapBaseinfo viewFqSalemapSelectmapBaseinfo)
    {
        return viewFqSalemapSelectmapBaseinfoMapper.insertViewFqSalemapSelectmapBaseinfo(viewFqSalemapSelectmapBaseinfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapBaseinfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateViewFqSalemapSelectmapBaseinfo(ViewFqSalemapSelectmapBaseinfo viewFqSalemapSelectmapBaseinfo)
    {
        return viewFqSalemapSelectmapBaseinfoMapper.updateViewFqSalemapSelectmapBaseinfo(viewFqSalemapSelectmapBaseinfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteViewFqSalemapSelectmapBaseinfoByProjectIds(Long[] projectIds)
    {
        return viewFqSalemapSelectmapBaseinfoMapper.deleteViewFqSalemapSelectmapBaseinfoByProjectIds(projectIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteViewFqSalemapSelectmapBaseinfoByProjectId(Long projectId)
    {
        return viewFqSalemapSelectmapBaseinfoMapper.deleteViewFqSalemapSelectmapBaseinfoByProjectId(projectId);
    }
}
