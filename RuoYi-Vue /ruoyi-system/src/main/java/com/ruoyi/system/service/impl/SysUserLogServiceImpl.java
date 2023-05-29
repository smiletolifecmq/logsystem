package com.ruoyi.system.service.impl;


import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.SysUserLog;
import com.ruoyi.system.mapper.SysUserLogMapper;
import com.ruoyi.system.service.ISysUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户日志Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-06
 */
@Service
public class SysUserLogServiceImpl implements ISysUserLogService {
    @Autowired
    private SysUserLogMapper sysUserLogMapper;

    /**
     * 查询用户日志
     *
     * @param userLogId 用户日志主键
     * @return 用户日志
     */
    @Override
    public SysUserLog selectSysUserLogByUserLogId(Long userLogId) {
        return sysUserLogMapper.selectSysUserLogByUserLogId(userLogId);
    }

    /**
     * 查询用户日志列表
     *
     * @param sysUserLog 用户日志
     * @return 用户日志
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUserLog> selectSysUserLogList(SysUserLog sysUserLog) {
        return sysUserLogMapper.selectSysUserLogList(sysUserLog);
    }

    /**
     * 新增用户日志
     *
     * @param sysUserLog 用户日志
     * @return 结果
     */
    @Override
    public int insertSysUserLog(SysUserLog sysUserLog) {
        sysUserLog.setCreateTime(DateUtils.getNowDate());
        return sysUserLogMapper.insertSysUserLog(sysUserLog);
    }

    /**
     * 修改用户日志
     *
     * @param sysUserLog 用户日志
     * @return 结果
     */
    @Override
    public int updateSysUserLog(SysUserLog sysUserLog) {
        sysUserLog.setUpdateTime(DateUtils.getNowDate());
        return sysUserLogMapper.updateSysUserLog(sysUserLog);
    }

    /**
     * 批量删除用户日志
     *
     * @param userLogIds 需要删除的用户日志主键
     * @return 结果
     */
    @Override
    public int deleteSysUserLogByUserLogIds(Long[] userLogIds) {
        return sysUserLogMapper.deleteSysUserLogByUserLogIds(userLogIds);
    }

    /**
     * 删除用户日志信息
     *
     * @param userLogId 用户日志主键
     * @return 结果
     */
    @Override
    public int deleteSysUserLogByUserLogId(Long userLogId) {
        return sysUserLogMapper.deleteSysUserLogByUserLogId(userLogId);
    }
}
