package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysSubcontractProcessConfig;

import java.util.List;

/**
 * 分包审核流程配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public interface SysSubcontractProcessConfigMapper 
{
    /**
     * 查询分包审核流程配置
     * 
     * @param processConfigId 分包审核流程配置主键
     * @return 分包审核流程配置
     */
    public SysSubcontractProcessConfig selectSysSubcontractProcessConfigByProcessConfigId(Long processConfigId);

    /**
     * 查询分包审核流程配置列表
     * 
     * @param sysSubcontractProcessConfig 分包审核流程配置
     * @return 分包审核流程配置集合
     */
    public List<SysSubcontractProcessConfig> selectSysSubcontractProcessConfigList(SysSubcontractProcessConfig sysSubcontractProcessConfig);

    /**
     * 新增分包审核流程配置
     * 
     * @param sysSubcontractProcessConfig 分包审核流程配置
     * @return 结果
     */
    public int insertSysSubcontractProcessConfig(SysSubcontractProcessConfig sysSubcontractProcessConfig);

    /**
     * 修改分包审核流程配置
     * 
     * @param sysSubcontractProcessConfig 分包审核流程配置
     * @return 结果
     */
    public int updateSysSubcontractProcessConfig(SysSubcontractProcessConfig sysSubcontractProcessConfig);

    /**
     * 删除分包审核流程配置
     * 
     * @param processConfigId 分包审核流程配置主键
     * @return 结果
     */
    public int deleteSysSubcontractProcessConfigByProcessConfigId(Long processConfigId);

    /**
     * 批量删除分包审核流程配置
     * 
     * @param processConfigIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSubcontractProcessConfigByProcessConfigIds(Long[] processConfigIds);
}
