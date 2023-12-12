package com.kcylog.system.service;

import com.kcylog.system.domain.SysGeoLog;

import java.util.List;

/**
 * 地理部门日志Service接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface ISysGeoLogService 
{
    /**
     * 查询地理部门日志
     * 
     * @param logId 地理部门日志主键
     * @return 地理部门日志
     */
    public SysGeoLog selectSysGeoLogByLogId(Long logId);

    /**
     * 查询地理部门日志列表
     * 
     * @param sysGeoLog 地理部门日志
     * @return 地理部门日志集合
     */
    public List<SysGeoLog> selectSysGeoLogList(SysGeoLog sysGeoLog);

    /**
     * 新增地理部门日志
     * 
     * @param sysGeoLog 地理部门日志
     * @return 结果
     */
    public int insertSysGeoLog(SysGeoLog sysGeoLog);

    /**
     * 修改地理部门日志
     * 
     * @param sysGeoLog 地理部门日志
     * @return 结果
     */
    public int updateSysGeoLog(SysGeoLog sysGeoLog);

    /**
     * 批量删除地理部门日志
     * 
     * @param logIds 需要删除的地理部门日志主键集合
     * @return 结果
     */
    public int deleteSysGeoLogByLogIds(Long[] logIds);

    /**
     * 删除地理部门日志信息
     * 
     * @param logId 地理部门日志主键
     * @return 结果
     */
    public int deleteSysGeoLogByLogId(Long logId);

    public SysGeoLog selectSysGeoLogByUserIdAndDate(SysGeoLog sysGeoLog);

    public SysGeoLog selectSysGeoLogByUserIdAndDateNotSelf(SysGeoLog sysGeoLog);
}
