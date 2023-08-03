package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysSubcontractProcessConfigMapper;
import com.kcylog.system.domain.SysSubcontractProcessConfig;
import com.kcylog.system.service.ISysSubcontractProcessConfigService;

/**
 * 分包审核流程配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class SysSubcontractProcessConfigServiceImpl implements ISysSubcontractProcessConfigService 
{
    @Autowired
    private SysSubcontractProcessConfigMapper sysSubcontractProcessConfigMapper;

    /**
     * 查询分包审核流程配置
     * 
     * @param processConfigId 分包审核流程配置主键
     * @return 分包审核流程配置
     */
    @Override
    public SysSubcontractProcessConfig selectSysSubcontractProcessConfigByProcessConfigId(Long processConfigId)
    {
        return sysSubcontractProcessConfigMapper.selectSysSubcontractProcessConfigByProcessConfigId(processConfigId);
    }

    /**
     * 查询分包审核流程配置列表
     * 
     * @param sysSubcontractProcessConfig 分包审核流程配置
     * @return 分包审核流程配置
     */
    @Override
    public List<SysSubcontractProcessConfig> selectSysSubcontractProcessConfigList(SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        return sysSubcontractProcessConfigMapper.selectSysSubcontractProcessConfigList(sysSubcontractProcessConfig);
    }

    /**
     * 新增分包审核流程配置
     * 
     * @param sysSubcontractProcessConfig 分包审核流程配置
     * @return 结果
     */
    @Override
    public int insertSysSubcontractProcessConfig(SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        sysSubcontractProcessConfig.setCreateTime(DateUtils.getNowDate());
        return sysSubcontractProcessConfigMapper.insertSysSubcontractProcessConfig(sysSubcontractProcessConfig);
    }

    /**
     * 修改分包审核流程配置
     * 
     * @param sysSubcontractProcessConfig 分包审核流程配置
     * @return 结果
     */
    @Override
    public int updateSysSubcontractProcessConfig(SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        sysSubcontractProcessConfig.setUpdateTime(DateUtils.getNowDate());
        return sysSubcontractProcessConfigMapper.updateSysSubcontractProcessConfig(sysSubcontractProcessConfig);
    }

    /**
     * 批量删除分包审核流程配置
     * 
     * @param processConfigIds 需要删除的分包审核流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractProcessConfigByProcessConfigIds(Long[] processConfigIds)
    {
        return sysSubcontractProcessConfigMapper.deleteSysSubcontractProcessConfigByProcessConfigIds(processConfigIds);
    }

    /**
     * 删除分包审核流程配置信息
     * 
     * @param processConfigId 分包审核流程配置主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractProcessConfigByProcessConfigId(Long processConfigId)
    {
        return sysSubcontractProcessConfigMapper.deleteSysSubcontractProcessConfigByProcessConfigId(processConfigId);
    }
}
