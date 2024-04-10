package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.ViewFqSalemapSelectmapBaseinfo;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2024-04-10
 */
public interface IViewFqSalemapSelectmapBaseinfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public ViewFqSalemapSelectmapBaseinfo selectViewFqSalemapSelectmapBaseinfoByProjectId(Long projectId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param viewFqSalemapSelectmapBaseinfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ViewFqSalemapSelectmapBaseinfo> selectViewFqSalemapSelectmapBaseinfoList(ViewFqSalemapSelectmapBaseinfo viewFqSalemapSelectmapBaseinfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapBaseinfo 【请填写功能名称】
     * @return 结果
     */
    public int insertViewFqSalemapSelectmapBaseinfo(ViewFqSalemapSelectmapBaseinfo viewFqSalemapSelectmapBaseinfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapBaseinfo 【请填写功能名称】
     * @return 结果
     */
    public int updateViewFqSalemapSelectmapBaseinfo(ViewFqSalemapSelectmapBaseinfo viewFqSalemapSelectmapBaseinfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteViewFqSalemapSelectmapBaseinfoByProjectIds(Long[] projectIds);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteViewFqSalemapSelectmapBaseinfoByProjectId(Long projectId);
}
