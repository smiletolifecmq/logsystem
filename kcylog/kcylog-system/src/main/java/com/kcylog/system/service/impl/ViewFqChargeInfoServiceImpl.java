package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.ViewFqChargeInfo;
import com.kcylog.system.mapper.ViewFqChargeInfoMapper;
import com.kcylog.system.service.IViewFqChargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分包视图Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
@Service
public class ViewFqChargeInfoServiceImpl implements IViewFqChargeInfoService 
{
    @Autowired
    private ViewFqChargeInfoMapper viewFqChargeInfoMapper;

    /**
     * 查询分包视图
     * 
     * @param projectCode 分包视图主键
     * @return 分包视图
     */
    @Override
    public List<ViewFqChargeInfo> selectViewFqChargeInfoByProjectCode(Long projectCode)
    {
        return viewFqChargeInfoMapper.selectViewFqChargeInfoByProjectCode(projectCode);
    }

    /**
     * 查询分包视图列表
     * 
     * @param viewFqChargeInfo 分包视图
     * @return 分包视图
     */
    @Override
    public List<ViewFqChargeInfo> selectViewFqChargeInfoList(ViewFqChargeInfo viewFqChargeInfo)
    {
        return viewFqChargeInfoMapper.selectViewFqChargeInfoList(viewFqChargeInfo);
    }

    /**
     * 新增分包视图
     * 
     * @param viewFqChargeInfo 分包视图
     * @return 结果
     */
    @Override
    public int insertViewFqChargeInfo(ViewFqChargeInfo viewFqChargeInfo)
    {
        viewFqChargeInfo.setCreateTime(DateUtils.getNowDate());
        return viewFqChargeInfoMapper.insertViewFqChargeInfo(viewFqChargeInfo);
    }

    /**
     * 修改分包视图
     * 
     * @param viewFqChargeInfo 分包视图
     * @return 结果
     */
    @Override
    public int updateViewFqChargeInfo(ViewFqChargeInfo viewFqChargeInfo)
    {
        return viewFqChargeInfoMapper.updateViewFqChargeInfo(viewFqChargeInfo);
    }

    /**
     * 批量删除分包视图
     * 
     * @param projectCodes 需要删除的分包视图主键
     * @return 结果
     */
    @Override
    public int deleteViewFqChargeInfoByProjectCodes(String[] projectCodes)
    {
        return viewFqChargeInfoMapper.deleteViewFqChargeInfoByProjectCodes(projectCodes);
    }

    /**
     * 删除分包视图信息
     * 
     * @param projectCode 分包视图主键
     * @return 结果
     */
    @Override
    public int deleteViewFqChargeInfoByProjectCode(String projectCode)
    {
        return viewFqChargeInfoMapper.deleteViewFqChargeInfoByProjectCode(projectCode);
    }
}
