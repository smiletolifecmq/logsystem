package com.kcylog.system.service.impl;

import java.util.List;

import com.kcylog.system.domain.SysGeoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysGeoHolidayMapper;
import com.kcylog.system.domain.SysGeoHoliday;
import com.kcylog.system.service.ISysGeoHolidayService;

/**
 * 地理员工请假Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-02
 */
@Service
public class SysGeoHolidayServiceImpl implements ISysGeoHolidayService 
{
    @Autowired
    private SysGeoHolidayMapper sysGeoHolidayMapper;

    /**
     * 查询地理员工请假
     * 
     * @param holidayId 地理员工请假主键
     * @return 地理员工请假
     */
    @Override
    public SysGeoHoliday selectSysGeoHolidayByHolidayId(Long holidayId)
    {
        return sysGeoHolidayMapper.selectSysGeoHolidayByHolidayId(holidayId);
    }

    /**
     * 查询地理员工请假列表
     * 
     * @param sysGeoHoliday 地理员工请假
     * @return 地理员工请假
     */
    @Override
    public List<SysGeoHoliday> selectSysGeoHolidayList(SysGeoHoliday sysGeoHoliday)
    {
        return sysGeoHolidayMapper.selectSysGeoHolidayList(sysGeoHoliday);
    }

    /**
     * 新增地理员工请假
     * 
     * @param sysGeoHoliday 地理员工请假
     * @return 结果
     */
    @Override
    public int insertSysGeoHoliday(SysGeoHoliday sysGeoHoliday)
    {
        return sysGeoHolidayMapper.insertSysGeoHoliday(sysGeoHoliday);
    }

    /**
     * 修改地理员工请假
     * 
     * @param sysGeoHoliday 地理员工请假
     * @return 结果
     */
    @Override
    public int updateSysGeoHoliday(SysGeoHoliday sysGeoHoliday)
    {
        return sysGeoHolidayMapper.updateSysGeoHoliday(sysGeoHoliday);
    }

    /**
     * 批量删除地理员工请假
     * 
     * @param holidayIds 需要删除的地理员工请假主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoHolidayByHolidayIds(Long[] holidayIds)
    {
        return sysGeoHolidayMapper.deleteSysGeoHolidayByHolidayIds(holidayIds);
    }

    /**
     * 删除地理员工请假信息
     * 
     * @param holidayId 地理员工请假主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoHolidayByHolidayId(Long holidayId)
    {
        return sysGeoHolidayMapper.deleteSysGeoHolidayByHolidayId(holidayId);
    }

    @Override
    public List<SysGeoHoliday> selectSysGeoHolidayListForLog(SysGeoLog sysGeoLog)
    {
        return sysGeoHolidayMapper.selectSysGeoHolidayListForLog(sysGeoLog);
    }
}
