package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysSubcontractProcessConfigInfo;
import com.kcylog.system.mapper.SysSubcontractProcessConfigInfoMapper;
import com.kcylog.system.service.ISysSubcontractProcessConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程配置过程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class SysSubcontractProcessConfigInfoServiceImpl implements ISysSubcontractProcessConfigInfoService 
{
    @Autowired
    private SysSubcontractProcessConfigInfoMapper sysSubcontractProcessConfigInfoMapper;

    /**
     * 查询流程配置过程
     * 
     * @param infoId 流程配置过程主键
     * @return 流程配置过程
     */
    @Override
    public SysSubcontractProcessConfigInfo selectSysSubcontractProcessConfigInfoByInfoId(Long infoId)
    {
        return sysSubcontractProcessConfigInfoMapper.selectSysSubcontractProcessConfigInfoByInfoId(infoId);
    }

    /**
     * 查询流程配置过程列表
     * 
     * @param sysSubcontractProcessConfigInfo 流程配置过程
     * @return 流程配置过程
     */
    @Override
    public List<SysSubcontractProcessConfigInfo> selectSysSubcontractProcessConfigInfoList(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        return sysSubcontractProcessConfigInfoMapper.selectSysSubcontractProcessConfigInfoList(sysSubcontractProcessConfigInfo);
    }

    /**
     * 新增流程配置过程
     * 
     * @param sysSubcontractProcessConfigInfo 流程配置过程
     * @return 结果
     */
    @Override
    public int insertSysSubcontractProcessConfigInfo(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        return sysSubcontractProcessConfigInfoMapper.insertSysSubcontractProcessConfigInfo(sysSubcontractProcessConfigInfo);
    }

    /**
     * 修改流程配置过程
     * 
     * @param sysSubcontractProcessConfigInfo 流程配置过程
     * @return 结果
     */
    @Override
    public int updateSysSubcontractProcessConfigInfo(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        return sysSubcontractProcessConfigInfoMapper.updateSysSubcontractProcessConfigInfo(sysSubcontractProcessConfigInfo);
    }

    /**
     * 批量删除流程配置过程
     * 
     * @param infoIds 需要删除的流程配置过程主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractProcessConfigInfoByInfoIds(Long[] infoIds)
    {
        return sysSubcontractProcessConfigInfoMapper.deleteSysSubcontractProcessConfigInfoByInfoIds(infoIds);
    }

    /**
     * 删除流程配置过程信息
     * 
     * @param infoId 流程配置过程主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractProcessConfigInfoByInfoId(Long infoId)
    {
        return sysSubcontractProcessConfigInfoMapper.deleteSysSubcontractProcessConfigInfoByInfoId(infoId);
    }

    @Override
    public List<SysSubcontractProcessConfigInfo> selectSysSubcontractProcessConfigInfoListByDeptId(Long deptId)
    {
        return sysSubcontractProcessConfigInfoMapper.selectSysSubcontractProcessConfigInfoListByDeptId(deptId);
    }
}
