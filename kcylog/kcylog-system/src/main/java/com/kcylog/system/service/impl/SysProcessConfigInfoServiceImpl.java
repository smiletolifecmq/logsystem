package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysProcessConfigInfo;
import com.kcylog.system.mapper.SysProcessConfigInfoMapper;
import com.kcylog.system.service.ISysProcessConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程配置过程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@Service
public class SysProcessConfigInfoServiceImpl implements ISysProcessConfigInfoService 
{
    @Autowired
    private SysProcessConfigInfoMapper sysProcessConfigInfoMapper;

    /**
     * 查询流程配置过程
     * 
     * @param infoId 流程配置过程主键
     * @return 流程配置过程
     */
    @Override
    public SysProcessConfigInfo selectSysProcessConfigInfoByInfoId(String infoId)
    {
        return sysProcessConfigInfoMapper.selectSysProcessConfigInfoByInfoId(infoId);
    }

    /**
     * 查询流程配置过程列表
     * 
     * @param sysProcessConfigInfo 流程配置过程
     * @return 流程配置过程
     */
    @Override
    public List<SysProcessConfigInfo> selectSysProcessConfigInfoList(SysProcessConfigInfo sysProcessConfigInfo)
    {
        return sysProcessConfigInfoMapper.selectSysProcessConfigInfoList(sysProcessConfigInfo);
    }

    /**
     * 新增流程配置过程
     * 
     * @param sysProcessConfigInfo 流程配置过程
     * @return 结果
     */
    @Override
    public int insertSysProcessConfigInfo(SysProcessConfigInfo sysProcessConfigInfo)
    {
        return sysProcessConfigInfoMapper.insertSysProcessConfigInfo(sysProcessConfigInfo);
    }

    /**
     * 修改流程配置过程
     * 
     * @param sysProcessConfigInfo 流程配置过程
     * @return 结果
     */
    @Override
    public int updateSysProcessConfigInfo(SysProcessConfigInfo sysProcessConfigInfo)
    {
        return sysProcessConfigInfoMapper.updateSysProcessConfigInfo(sysProcessConfigInfo);
    }

    /**
     * 批量删除流程配置过程
     * 
     * @param infoIds 需要删除的流程配置过程主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessConfigInfoByInfoIds(String[] infoIds)
    {
        return sysProcessConfigInfoMapper.deleteSysProcessConfigInfoByInfoIds(infoIds);
    }

    /**
     * 删除流程配置过程信息
     * 
     * @param infoId 流程配置过程主键
     * @return 结果
     */
    @Override
    public int deleteSysProcessConfigInfoByInfoId(String infoId)
    {
        return sysProcessConfigInfoMapper.deleteSysProcessConfigInfoByInfoId(infoId);
    }
}
