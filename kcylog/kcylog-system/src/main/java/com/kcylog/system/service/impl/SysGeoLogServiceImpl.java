package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysGeoLogMapper;
import com.kcylog.system.domain.SysGeoLog;
import com.kcylog.system.service.ISysGeoLogService;

/**
 * 地理部门日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@Service
public class SysGeoLogServiceImpl implements ISysGeoLogService 
{
    @Autowired
    private SysGeoLogMapper sysGeoLogMapper;

    /**
     * 查询地理部门日志
     * 
     * @param logId 地理部门日志主键
     * @return 地理部门日志
     */
    @Override
    public SysGeoLog selectSysGeoLogByLogId(Long logId)
    {
        return sysGeoLogMapper.selectSysGeoLogByLogId(logId);
    }

    /**
     * 查询地理部门日志列表
     * 
     * @param sysGeoLog 地理部门日志
     * @return 地理部门日志
     */
    @Override
    public List<SysGeoLog> selectSysGeoLogList(SysGeoLog sysGeoLog)
    {
        return sysGeoLogMapper.selectSysGeoLogList(sysGeoLog);
    }

    /**
     * 新增地理部门日志
     * 
     * @param sysGeoLog 地理部门日志
     * @return 结果
     */
    @Override
    public int insertSysGeoLog(SysGeoLog sysGeoLog)
    {
        sysGeoLog.setCreateTime(DateUtils.getNowDate());
        return sysGeoLogMapper.insertSysGeoLog(sysGeoLog);
    }

    /**
     * 修改地理部门日志
     * 
     * @param sysGeoLog 地理部门日志
     * @return 结果
     */
    @Override
    public int updateSysGeoLog(SysGeoLog sysGeoLog)
    {
        return sysGeoLogMapper.updateSysGeoLog(sysGeoLog);
    }

    /**
     * 批量删除地理部门日志
     * 
     * @param logIds 需要删除的地理部门日志主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoLogByLogIds(Long[] logIds)
    {
        return sysGeoLogMapper.deleteSysGeoLogByLogIds(logIds);
    }

    /**
     * 删除地理部门日志信息
     * 
     * @param logId 地理部门日志主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoLogByLogId(Long logId)
    {
        return sysGeoLogMapper.deleteSysGeoLogByLogId(logId);
    }
}
