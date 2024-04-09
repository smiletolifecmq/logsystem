package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.ViewFqSalemapSelectgeoGeoinfoMapper;
import com.kcylog.system.domain.ViewFqSalemapSelectgeoGeoinfo;
import com.kcylog.system.service.IViewFqSalemapSelectgeoGeoinfoService;

/**
 * 项目坐标系Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
@Service
public class ViewFqSalemapSelectgeoGeoinfoServiceImpl implements IViewFqSalemapSelectgeoGeoinfoService 
{
    @Autowired
    private ViewFqSalemapSelectgeoGeoinfoMapper viewFqSalemapSelectgeoGeoinfoMapper;

    /**
     * 查询项目坐标系
     * 
     * @param projectId 项目坐标系主键
     * @return 项目坐标系
     */
    @Override
    public ViewFqSalemapSelectgeoGeoinfo selectViewFqSalemapSelectgeoGeoinfoByProjectId(Long projectId)
    {
        return viewFqSalemapSelectgeoGeoinfoMapper.selectViewFqSalemapSelectgeoGeoinfoByProjectId(projectId);
    }

    /**
     * 查询项目坐标系列表
     * 
     * @param viewFqSalemapSelectgeoGeoinfo 项目坐标系
     * @return 项目坐标系
     */
    @Override
    public List<ViewFqSalemapSelectgeoGeoinfo> selectViewFqSalemapSelectgeoGeoinfoList(ViewFqSalemapSelectgeoGeoinfo viewFqSalemapSelectgeoGeoinfo)
    {
        return viewFqSalemapSelectgeoGeoinfoMapper.selectViewFqSalemapSelectgeoGeoinfoList(viewFqSalemapSelectgeoGeoinfo);
    }

    /**
     * 新增项目坐标系
     * 
     * @param viewFqSalemapSelectgeoGeoinfo 项目坐标系
     * @return 结果
     */
    @Override
    public int insertViewFqSalemapSelectgeoGeoinfo(ViewFqSalemapSelectgeoGeoinfo viewFqSalemapSelectgeoGeoinfo)
    {
        return viewFqSalemapSelectgeoGeoinfoMapper.insertViewFqSalemapSelectgeoGeoinfo(viewFqSalemapSelectgeoGeoinfo);
    }

    /**
     * 修改项目坐标系
     * 
     * @param viewFqSalemapSelectgeoGeoinfo 项目坐标系
     * @return 结果
     */
    @Override
    public int updateViewFqSalemapSelectgeoGeoinfo(ViewFqSalemapSelectgeoGeoinfo viewFqSalemapSelectgeoGeoinfo)
    {
        return viewFqSalemapSelectgeoGeoinfoMapper.updateViewFqSalemapSelectgeoGeoinfo(viewFqSalemapSelectgeoGeoinfo);
    }

    /**
     * 批量删除项目坐标系
     * 
     * @param projectIds 需要删除的项目坐标系主键
     * @return 结果
     */
    @Override
    public int deleteViewFqSalemapSelectgeoGeoinfoByProjectIds(Long[] projectIds)
    {
        return viewFqSalemapSelectgeoGeoinfoMapper.deleteViewFqSalemapSelectgeoGeoinfoByProjectIds(projectIds);
    }

    /**
     * 删除项目坐标系信息
     * 
     * @param projectId 项目坐标系主键
     * @return 结果
     */
    @Override
    public int deleteViewFqSalemapSelectgeoGeoinfoByProjectId(Long projectId)
    {
        return viewFqSalemapSelectgeoGeoinfoMapper.deleteViewFqSalemapSelectgeoGeoinfoByProjectId(projectId);
    }
}
