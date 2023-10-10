package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysCarReviewConfigMapper;
import com.kcylog.system.domain.SysCarReviewConfig;
import com.kcylog.system.service.ISysCarReviewConfigService;

/**
 * 车辆审核流程配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class SysCarReviewConfigServiceImpl implements ISysCarReviewConfigService 
{
    @Autowired
    private SysCarReviewConfigMapper sysCarReviewConfigMapper;

    /**
     * 查询车辆审核流程配置
     * 
     * @param reviewConfigId 车辆审核流程配置主键
     * @return 车辆审核流程配置
     */
    @Override
    public SysCarReviewConfig selectSysCarReviewConfigByReviewConfigId(Long reviewConfigId)
    {
        return sysCarReviewConfigMapper.selectSysCarReviewConfigByReviewConfigId(reviewConfigId);
    }

    /**
     * 查询车辆审核流程配置列表
     * 
     * @param sysCarReviewConfig 车辆审核流程配置
     * @return 车辆审核流程配置
     */
    @Override
    public List<SysCarReviewConfig> selectSysCarReviewConfigList(SysCarReviewConfig sysCarReviewConfig)
    {
        return sysCarReviewConfigMapper.selectSysCarReviewConfigList(sysCarReviewConfig);
    }

    /**
     * 新增车辆审核流程配置
     * 
     * @param sysCarReviewConfig 车辆审核流程配置
     * @return 结果
     */
    @Override
    public int insertSysCarReviewConfig(SysCarReviewConfig sysCarReviewConfig)
    {
        sysCarReviewConfig.setCreateTime(DateUtils.getNowDate());
        return sysCarReviewConfigMapper.insertSysCarReviewConfig(sysCarReviewConfig);
    }

    /**
     * 修改车辆审核流程配置
     * 
     * @param sysCarReviewConfig 车辆审核流程配置
     * @return 结果
     */
    @Override
    public int updateSysCarReviewConfig(SysCarReviewConfig sysCarReviewConfig)
    {
        sysCarReviewConfig.setUpdateTime(DateUtils.getNowDate());
        return sysCarReviewConfigMapper.updateSysCarReviewConfig(sysCarReviewConfig);
    }

    /**
     * 批量删除车辆审核流程配置
     * 
     * @param reviewConfigIds 需要删除的车辆审核流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewConfigByReviewConfigIds(Long[] reviewConfigIds)
    {
        return sysCarReviewConfigMapper.deleteSysCarReviewConfigByReviewConfigIds(reviewConfigIds);
    }

    /**
     * 删除车辆审核流程配置信息
     * 
     * @param reviewConfigId 车辆审核流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewConfigByReviewConfigId(Long reviewConfigId)
    {
        return sysCarReviewConfigMapper.deleteSysCarReviewConfigByReviewConfigId(reviewConfigId);
    }
}
