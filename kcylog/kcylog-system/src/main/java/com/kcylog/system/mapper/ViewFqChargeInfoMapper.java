package com.kcylog.system.mapper;

import com.kcylog.system.domain.ViewFqChargeInfo;

import java.util.List;

/**
 * 分包视图Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
public interface ViewFqChargeInfoMapper 
{
    /**
     * 查询分包视图
     * 
     * @param projectCode 分包视图主键
     * @return 分包视图
     */
    public List<ViewFqChargeInfo> selectViewFqChargeInfoByProjectCode(Long projectCode);

    /**
     * 查询分包视图列表
     * 
     * @param viewFqChargeInfo 分包视图
     * @return 分包视图集合
     */
    public List<ViewFqChargeInfo> selectViewFqChargeInfoList(ViewFqChargeInfo viewFqChargeInfo);

    /**
     * 新增分包视图
     * 
     * @param viewFqChargeInfo 分包视图
     * @return 结果
     */
    public int insertViewFqChargeInfo(ViewFqChargeInfo viewFqChargeInfo);

    /**
     * 修改分包视图
     * 
     * @param viewFqChargeInfo 分包视图
     * @return 结果
     */
    public int updateViewFqChargeInfo(ViewFqChargeInfo viewFqChargeInfo);

    /**
     * 删除分包视图
     * 
     * @param projectCode 分包视图主键
     * @return 结果
     */
    public int deleteViewFqChargeInfoByProjectCode(String projectCode);

    /**
     * 批量删除分包视图
     * 
     * @param projectCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteViewFqChargeInfoByProjectCodes(String[] projectCodes);
}
