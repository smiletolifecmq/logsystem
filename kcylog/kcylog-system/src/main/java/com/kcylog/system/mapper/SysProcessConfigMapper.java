package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysProcessConfig;

import java.util.List;

/**
 * 流程配置Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-08
 */
public interface SysProcessConfigMapper 
{
    /**
     * 查询流程配置
     * 
     * @param processConfigId 流程配置主键
     * @return 流程配置
     */
    public SysProcessConfig selectSysProcessConfigByProcessConfigId(String processConfigId);

    /**
     * 查询流程配置列表
     * 
     * @param sysProcessConfig 流程配置
     * @return 流程配置集合
     */
    public List<SysProcessConfig> selectSysProcessConfigList(SysProcessConfig sysProcessConfig);

    /**
     * 新增流程配置
     * 
     * @param sysProcessConfig 流程配置
     * @return 结果
     */
    public int insertSysProcessConfig(SysProcessConfig sysProcessConfig);

    /**
     * 修改流程配置
     * 
     * @param sysProcessConfig 流程配置
     * @return 结果
     */
    public int updateSysProcessConfig(SysProcessConfig sysProcessConfig);

    /**
     * 删除流程配置
     * 
     * @param processConfigId 流程配置主键
     * @return 结果
     */
    public int deleteSysProcessConfigByProcessConfigId(String processConfigId);

    /**
     * 批量删除流程配置
     * 
     * @param processConfigIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProcessConfigByProcessConfigIds(String[] processConfigIds);
}
