package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysGeoLogInfo;
import com.kcylog.system.mapper.SysGeoLogInfoMapper;
import com.kcylog.system.service.ISysGeoLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地理部门日志详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class SysGeoLogInfoServiceImpl implements ISysGeoLogInfoService 
{
    @Autowired
    private SysGeoLogInfoMapper sysGeoLogInfoMapper;

    /**
     * 查询地理部门日志详情
     * 
     * @param logInfoId 地理部门日志详情主键
     * @return 地理部门日志详情
     */
    @Override
    public SysGeoLogInfo selectSysGeoLogInfoByLogInfoId(Long logInfoId)
    {
        return sysGeoLogInfoMapper.selectSysGeoLogInfoByLogInfoId(logInfoId);
    }

    /**
     * 查询地理部门日志详情列表
     * 
     * @param sysGeoLogInfo 地理部门日志详情
     * @return 地理部门日志详情
     */
    @Override
    public List<SysGeoLogInfo> selectSysGeoLogInfoList(SysGeoLogInfo sysGeoLogInfo)
    {
        return sysGeoLogInfoMapper.selectSysGeoLogInfoList(sysGeoLogInfo);
    }

    /**
     * 新增地理部门日志详情
     * 
     * @param sysGeoLogInfo 地理部门日志详情
     * @return 结果
     */
    @Override
    public int insertSysGeoLogInfo(SysGeoLogInfo sysGeoLogInfo)
    {
        return sysGeoLogInfoMapper.insertSysGeoLogInfo(sysGeoLogInfo);
    }

    /**
     * 修改地理部门日志详情
     * 
     * @param sysGeoLogInfo 地理部门日志详情
     * @return 结果
     */
    @Override
    public int updateSysGeoLogInfo(SysGeoLogInfo sysGeoLogInfo)
    {
        return sysGeoLogInfoMapper.updateSysGeoLogInfo(sysGeoLogInfo);
    }

    /**
     * 批量删除地理部门日志详情
     * 
     * @param logInfoIds 需要删除的地理部门日志详情主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoLogInfoByLogInfoIds(Long[] logInfoIds)
    {
        return sysGeoLogInfoMapper.deleteSysGeoLogInfoByLogInfoIds(logInfoIds);
    }

    /**
     * 删除地理部门日志详情信息
     * 
     * @param logInfoId 地理部门日志详情主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoLogInfoByLogInfoId(Long logInfoId)
    {
        return sysGeoLogInfoMapper.deleteSysGeoLogInfoByLogInfoId(logInfoId);
    }

    @Override
    public int deleteSysGeoLogInfoByLogIds(Long[] logIds)
    {
        return sysGeoLogInfoMapper.deleteSysGeoLogInfoByLogIds(logIds);
    }

    @Override
    public List<SysGeoLogInfo> selectSysGeoLogInfoByProjectId(Long[] projectIds)
    {
        return sysGeoLogInfoMapper.selectSysGeoLogInfoByProjectId(projectIds);
    }

    @Override
    public List<SysGeoLogInfo> selectSysGeoLogInfoByLogIds(List<Long> logIds)
    {
        return sysGeoLogInfoMapper.selectSysGeoLogInfoByLogIds(logIds);
    }
}
