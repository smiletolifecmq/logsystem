package com.kcylog.system.mapper;

import com.kcylog.system.domain.ViewFqSalemapSelectgeoGeoinfo;

import java.util.List;

/**
 * 项目坐标系Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
public interface ViewFqSalemapSelectgeoGeoinfoMapper
{
    /**
     * 查询项目坐标系
     * 
     * @param projectId 项目坐标系主键
     * @return 项目坐标系
     */
    public List<ViewFqSalemapSelectgeoGeoinfo> selectViewFqSalemapSelectgeoGeoinfoByProjectId(Long projectId);

    /**
     * 查询项目坐标系列表
     * 
     * @param viewFqSalemapSelectgeoGeoinfo 项目坐标系
     * @return 项目坐标系集合
     */
    public List<ViewFqSalemapSelectgeoGeoinfo> selectViewFqSalemapSelectgeoGeoinfoList(ViewFqSalemapSelectgeoGeoinfo viewFqSalemapSelectgeoGeoinfo);

    /**
     * 新增项目坐标系
     * 
     * @param viewFqSalemapSelectgeoGeoinfo 项目坐标系
     * @return 结果
     */
    public int insertViewFqSalemapSelectgeoGeoinfo(ViewFqSalemapSelectgeoGeoinfo viewFqSalemapSelectgeoGeoinfo);

    /**
     * 修改项目坐标系
     * 
     * @param viewFqSalemapSelectgeoGeoinfo 项目坐标系
     * @return 结果
     */
    public int updateViewFqSalemapSelectgeoGeoinfo(ViewFqSalemapSelectgeoGeoinfo viewFqSalemapSelectgeoGeoinfo);

    /**
     * 删除项目坐标系
     * 
     * @param projectId 项目坐标系主键
     * @return 结果
     */
    public int deleteViewFqSalemapSelectgeoGeoinfoByProjectId(Long projectId);

    /**
     * 批量删除项目坐标系
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqSalemapSelectgeoGeoinfoByProjectIds(Long[] projectIds);
}
