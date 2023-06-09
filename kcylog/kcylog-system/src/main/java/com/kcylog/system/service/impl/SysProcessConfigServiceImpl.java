package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysProcessConfigMapper;
import com.kcylog.system.domain.SysProcessConfig;
import com.kcylog.system.service.ISysProcessConfigService;

/**
 * 流程配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-08
 */
@Service
public class SysProcessConfigServiceImpl implements ISysProcessConfigService 
{
    @Autowired
    private SysProcessConfigMapper sysProcessConfigMapper;

    /**
     * 查询流程配置
     * 
     * @param processConfigId 流程配置主键
     * @return 流程配置
     */
    @Override
    public SysProcessConfig selectSysProcessConfigByProcessConfigId(String processConfigId)
    {
        return sysProcessConfigMapper.selectSysProcessConfigByProcessConfigId(processConfigId);
    }

    /**
     * 查询流程配置列表
     * 
     * @param sysProcessConfig 流程配置
     * @return 流程配置
     */
    @Override
    public List<SysProcessConfig> selectSysProcessConfigList(SysProcessConfig sysProcessConfig)
    {
        return sysProcessConfigMapper.selectSysProcessConfigList(sysProcessConfig);
    }

    /**
     * 新增流程配置
     * 
     * @param sysProcessConfig 流程配置
     * @return 结果
     */
    @Override
    public int insertSysProcessConfig(SysProcessConfig sysProcessConfig)
    {
        sysProcessConfig.setCreateTime(DateUtils.getNowDate());
        return sysProcessConfigMapper.insertSysProcessConfig(sysProcessConfig);
    }

    /**
     * 修改流程配置
     * 
     * @param sysProcessConfig 流程配置
     * @return 结果
     */
    @Override
    public int updateSysProcessConfig(SysProcessConfig sysProcessConfig)
    {
        sysProcessConfig.setUpdateTime(DateUtils.getNowDate());
        return sysProcessConfigMapper.updateSysProcessConfig(sysProcessConfig);
    }

    /**
     * 批量删除流程配置
     * 
     * @param processConfigIds 需要删除的流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessConfigByProcessConfigIds(String[] processConfigIds)
    {
        return sysProcessConfigMapper.deleteSysProcessConfigByProcessConfigIds(processConfigIds);
    }

    /**
     * 删除流程配置信息
     * 
     * @param processConfigId 流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessConfigByProcessConfigId(String processConfigId)
    {
        return sysProcessConfigMapper.deleteSysProcessConfigByProcessConfigId(processConfigId);
    }
}
