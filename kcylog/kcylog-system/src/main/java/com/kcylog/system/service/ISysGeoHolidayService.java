package com.kcylog.system.service;

import com.kcylog.system.domain.SysGeoHoliday;
import com.kcylog.system.domain.SysGeoLog;

import java.util.List;

/**
 * 地理员工请假Service接口
 * 
 * @author ruoyi
 * @date 2024-01-02
 */
public interface ISysGeoHolidayService 
{
    /**
     * 查询地理员工请假
     * 
     * @param holidayId 地理员工请假主键
     * @return 地理员工请假
     */
    public SysGeoHoliday selectSysGeoHolidayByHolidayId(Long holidayId);

    /**
     * 查询地理员工请假列表
     * 
     * @param sysGeoHoliday 地理员工请假
     * @return 地理员工请假集合
     */
    public List<SysGeoHoliday> selectSysGeoHolidayList(SysGeoHoliday sysGeoHoliday);

    /**
     * 新增地理员工请假
     * 
     * @param sysGeoHoliday 地理员工请假
     * @return 结果
     */
    public int insertSysGeoHoliday(SysGeoHoliday sysGeoHoliday);

    /**
     * 修改地理员工请假
     * 
     * @param sysGeoHoliday 地理员工请假
     * @return 结果
     */
    public int updateSysGeoHoliday(SysGeoHoliday sysGeoHoliday);

    /**
     * 批量删除地理员工请假
     * 
     * @param holidayIds 需要删除的地理员工请假主键集合
     * @return 结果
     */
    public int deleteSysGeoHolidayByHolidayIds(Long[] holidayIds);

    /**
     * 删除地理员工请假信息
     * 
     * @param holidayId 地理员工请假主键
     * @return 结果
     */
    public int deleteSysGeoHolidayByHolidayId(Long holidayId);

    public List<SysGeoHoliday> selectSysGeoHolidayListForLog(SysGeoLog sysGeoLog);
}
