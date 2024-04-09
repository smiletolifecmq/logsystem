package com.kcylog.system.mapper;

import com.kcylog.common.annotation.DataSource;
import com.kcylog.common.enums.DataSourceType;
import com.kcylog.system.domain.ViewFqSalemapSelectmapTfinfo;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
@DataSource(value = DataSourceType.SLAVE)
public interface ViewFqSalemapSelectmapTfinfoMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public List<ViewFqSalemapSelectmapTfinfo> selectViewFqSalemapSelectmapTfinfoByProjectId(Long projectId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param viewFqSalemapSelectmapTfinfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<ViewFqSalemapSelectmapTfinfo> selectViewFqSalemapSelectmapTfinfoList(ViewFqSalemapSelectmapTfinfo viewFqSalemapSelectmapTfinfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    public int insertViewFqSalemapSelectmapTfinfo(ViewFqSalemapSelectmapTfinfo viewFqSalemapSelectmapTfinfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param viewFqSalemapSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    public int updateViewFqSalemapSelectmapTfinfo(ViewFqSalemapSelectmapTfinfo viewFqSalemapSelectmapTfinfo);

    /**
     * 删除【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteViewFqSalemapSelectmapTfinfoByProjectId(Long projectId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqSalemapSelectmapTfinfoByProjectIds(Long[] projectIds);
}
