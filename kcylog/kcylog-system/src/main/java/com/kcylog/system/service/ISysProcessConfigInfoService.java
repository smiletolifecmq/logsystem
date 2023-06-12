package com.kcylog.system.service;

import com.kcylog.system.domain.SysProcessConfigInfo;

import java.util.List;

/**
 * 流程配置过程Service接口
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
public interface ISysProcessConfigInfoService 
{
    /**
     * 查询流程配置过程
     * 
     * @param infoId 流程配置过程主键
     * @return 流程配置过程
     */
    public SysProcessConfigInfo selectSysProcessConfigInfoByInfoId(String infoId);

    /**
     * 查询流程配置过程列表
     * 
     * @param sysProcessConfigInfo 流程配置过程
     * @return 流程配置过程集合
     */
    public List<SysProcessConfigInfo> selectSysProcessConfigInfoList(SysProcessConfigInfo sysProcessConfigInfo);

    /**
     * 新增流程配置过程
     * 
     * @param sysProcessConfigInfo 流程配置过程
     * @return 结果
     */
    public int insertSysProcessConfigInfo(SysProcessConfigInfo sysProcessConfigInfo);

    /**
     * 修改流程配置过程
     * 
     * @param sysProcessConfigInfo 流程配置过程
     * @return 结果
     */
    public int updateSysProcessConfigInfo(SysProcessConfigInfo sysProcessConfigInfo);

    /**
     * 批量删除流程配置过程
     * 
     * @param infoIds 需要删除的流程配置过程主键集合
     * @return 结果
     */
    public int deleteSysProcessConfigInfoByInfoIds(String[] infoIds);

    /**
     * 删除流程配置过程信息
     * 
     * @param infoId 流程配置过程主键
     * @return 结果
     */
    public int deleteSysProcessConfigInfoByInfoId(String infoId);
}
