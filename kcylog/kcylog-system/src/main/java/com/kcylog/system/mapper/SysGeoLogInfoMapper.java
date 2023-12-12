package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysGeoLogInfo;

import java.util.List;

/**
 * 地理部门日志详情Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface SysGeoLogInfoMapper 
{
    /**
     * 查询地理部门日志详情
     * 
     * @param logInfoId 地理部门日志详情主键
     * @return 地理部门日志详情
     */
    public SysGeoLogInfo selectSysGeoLogInfoByLogInfoId(Long logInfoId);

    /**
     * 查询地理部门日志详情列表
     * 
     * @param sysGeoLogInfo 地理部门日志详情
     * @return 地理部门日志详情集合
     */
    public List<SysGeoLogInfo> selectSysGeoLogInfoList(SysGeoLogInfo sysGeoLogInfo);

    /**
     * 新增地理部门日志详情
     * 
     * @param sysGeoLogInfo 地理部门日志详情
     * @return 结果
     */
    public int insertSysGeoLogInfo(SysGeoLogInfo sysGeoLogInfo);

    /**
     * 修改地理部门日志详情
     * 
     * @param sysGeoLogInfo 地理部门日志详情
     * @return 结果
     */
    public int updateSysGeoLogInfo(SysGeoLogInfo sysGeoLogInfo);

    /**
     * 删除地理部门日志详情
     * 
     * @param logInfoId 地理部门日志详情主键
     * @return 结果
     */
    public int deleteSysGeoLogInfoByLogInfoId(Long logInfoId);

    /**
     * 批量删除地理部门日志详情
     * 
     * @param logInfoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoLogInfoByLogInfoIds(Long[] logInfoIds);

    public int deleteSysGeoLogInfoByLogIds(Long[] logIds);
}