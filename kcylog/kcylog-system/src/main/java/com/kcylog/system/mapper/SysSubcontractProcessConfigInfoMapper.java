package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysSubcontractProcessConfigInfo;

import java.util.List;

/**
 * 流程配置过程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public interface SysSubcontractProcessConfigInfoMapper 
{
    /**
     * 查询流程配置过程
     * 
     * @param infoId 流程配置过程主键
     * @return 流程配置过程
     */
    public SysSubcontractProcessConfigInfo selectSysSubcontractProcessConfigInfoByInfoId(Long infoId);

    /**
     * 查询流程配置过程列表
     * 
     * @param sysSubcontractProcessConfigInfo 流程配置过程
     * @return 流程配置过程集合
     */
    public List<SysSubcontractProcessConfigInfo> selectSysSubcontractProcessConfigInfoList(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo);

    /**
     * 新增流程配置过程
     * 
     * @param sysSubcontractProcessConfigInfo 流程配置过程
     * @return 结果
     */
    public int insertSysSubcontractProcessConfigInfo(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo);

    /**
     * 修改流程配置过程
     * 
     * @param sysSubcontractProcessConfigInfo 流程配置过程
     * @return 结果
     */
    public int updateSysSubcontractProcessConfigInfo(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo);

    /**
     * 删除流程配置过程
     * 
     * @param infoId 流程配置过程主键
     * @return 结果
     */
    public int deleteSysSubcontractProcessConfigInfoByInfoId(Long infoId);

    /**
     * 批量删除流程配置过程
     * 
     * @param infoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSubcontractProcessConfigInfoByInfoIds(Long[] infoIds);
}
