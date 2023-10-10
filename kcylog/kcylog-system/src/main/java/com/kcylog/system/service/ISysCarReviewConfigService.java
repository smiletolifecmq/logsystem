package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.SysCarReviewConfig;

/**
 * 车辆审核流程配置Service接口
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public interface ISysCarReviewConfigService 
{
    /**
     * 查询车辆审核流程配置
     * 
     * @param reviewConfigId 车辆审核流程配置主键
     * @return 车辆审核流程配置
     */
    public SysCarReviewConfig selectSysCarReviewConfigByReviewConfigId(Long reviewConfigId);

    /**
     * 查询车辆审核流程配置列表
     * 
     * @param sysCarReviewConfig 车辆审核流程配置
     * @return 车辆审核流程配置集合
     */
    public List<SysCarReviewConfig> selectSysCarReviewConfigList(SysCarReviewConfig sysCarReviewConfig);

    /**
     * 新增车辆审核流程配置
     * 
     * @param sysCarReviewConfig 车辆审核流程配置
     * @return 结果
     */
    public int insertSysCarReviewConfig(SysCarReviewConfig sysCarReviewConfig);

    /**
     * 修改车辆审核流程配置
     * 
     * @param sysCarReviewConfig 车辆审核流程配置
     * @return 结果
     */
    public int updateSysCarReviewConfig(SysCarReviewConfig sysCarReviewConfig);

    /**
     * 批量删除车辆审核流程配置
     * 
     * @param reviewConfigIds 需要删除的车辆审核流程配置主键集合
     * @return 结果
     */
    public int deleteSysCarReviewConfigByReviewConfigIds(Long[] reviewConfigIds);

    /**
     * 删除车辆审核流程配置信息
     * 
     * @param reviewConfigId 车辆审核流程配置主键
     * @return 结果
     */
    public int deleteSysCarReviewConfigByReviewConfigId(Long reviewConfigId);
}
