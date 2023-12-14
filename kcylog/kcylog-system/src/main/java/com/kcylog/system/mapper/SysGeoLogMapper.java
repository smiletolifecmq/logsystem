package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysGeoLog;

import java.util.List;

/**
 * 地理部门日志Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
public interface SysGeoLogMapper 
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
     * 删除地理部门日志
     * 
     * @param logId 地理部门日志主键
     * @return 结果
     */
    public int deleteSysGeoLogByLogId(Long logId);

    /**
     * 批量删除地理部门日志
     * 
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoLogByLogIds(Long[] logIds);

    public SysGeoLog selectSysGeoLogByUserIdAndDate(SysGeoLog sysGeoLog);

    public SysGeoLog selectSysGeoLogByUserIdAndDateNotSelf(SysGeoLog sysGeoLog);

    public List<SysGeoLog> selectSysGeoLogListExport(SysGeoLog sysGeoLog);

    public List<SysGeoLog> selectSysGeoLogListExportByDate(SysGeoLog sysGeoLog);
}
