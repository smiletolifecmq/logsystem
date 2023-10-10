package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysCarReviewConfigInfoMapper;
import com.kcylog.system.domain.SysCarReviewConfigInfo;
import com.kcylog.system.service.ISysCarReviewConfigInfoService;

/**
 * 车辆使用流程配置过程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class SysCarReviewConfigInfoServiceImpl implements ISysCarReviewConfigInfoService 
{
    @Autowired
    private SysCarReviewConfigInfoMapper sysCarReviewConfigInfoMapper;

    /**
     * 查询车辆使用流程配置过程
     * 
     * @param infoId 车辆使用流程配置过程主键
     * @return 车辆使用流程配置过程
     */
    @Override
    public SysCarReviewConfigInfo selectSysCarReviewConfigInfoByInfoId(Long infoId)
    {
        return sysCarReviewConfigInfoMapper.selectSysCarReviewConfigInfoByInfoId(infoId);
    }

    /**
     * 查询车辆使用流程配置过程列表
     * 
     * @param sysCarReviewConfigInfo 车辆使用流程配置过程
     * @return 车辆使用流程配置过程
     */
    @Override
    public List<SysCarReviewConfigInfo> selectSysCarReviewConfigInfoList(SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        return sysCarReviewConfigInfoMapper.selectSysCarReviewConfigInfoList(sysCarReviewConfigInfo);
    }

    /**
     * 新增车辆使用流程配置过程
     * 
     * @param sysCarReviewConfigInfo 车辆使用流程配置过程
     * @return 结果
     */
    @Override
    public int insertSysCarReviewConfigInfo(SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        return sysCarReviewConfigInfoMapper.insertSysCarReviewConfigInfo(sysCarReviewConfigInfo);
    }

    /**
     * 修改车辆使用流程配置过程
     * 
     * @param sysCarReviewConfigInfo 车辆使用流程配置过程
     * @return 结果
     */
    @Override
    public int updateSysCarReviewConfigInfo(SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        return sysCarReviewConfigInfoMapper.updateSysCarReviewConfigInfo(sysCarReviewConfigInfo);
    }

    /**
     * 批量删除车辆使用流程配置过程
     * 
     * @param infoIds 需要删除的车辆使用流程配置过程主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewConfigInfoByInfoIds(Long[] infoIds)
    {
        return sysCarReviewConfigInfoMapper.deleteSysCarReviewConfigInfoByInfoIds(infoIds);
    }

    /**
     * 删除车辆使用流程配置过程信息
     * 
     * @param infoId 车辆使用流程配置过程主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewConfigInfoByInfoId(Long infoId)
    {
        return sysCarReviewConfigInfoMapper.deleteSysCarReviewConfigInfoByInfoId(infoId);
    }
}
