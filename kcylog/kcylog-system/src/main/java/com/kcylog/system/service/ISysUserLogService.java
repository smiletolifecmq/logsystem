package com.kcylog.system.service;

import com.kcylog.system.domain.SysUserLog;

import java.util.List;

/**
 * 用户日志Service接口
 *
 * @author ruoyi
 * @date 2023-05-06
 */
public interface ISysUserLogService {
    /**
     * 查询用户日志
     *
     * @param userLogId 用户日志主键
     * @return 用户日志
     */
    public SysUserLog selectSysUserLogByUserLogId(Long userLogId);

    /**
     * 查询用户日志列表
     *
     * @param sysUserLog 用户日志
     * @return 用户日志集合
     */
    public List<SysUserLog> selectSysUserLogList(SysUserLog sysUserLog);

    /**
     * 新增用户日志
     *
     * @param sysUserLog 用户日志
     * @return 结果
     */
    public int insertSysUserLog(SysUserLog sysUserLog);

    /**
     * 修改用户日志
     *
     * @param sysUserLog 用户日志
     * @return 结果
     */
    public int updateSysUserLog(SysUserLog sysUserLog);

    /**
     * 批量删除用户日志
     *
     * @param userLogIds 需要删除的用户日志主键集合
     * @return 结果
     */
    public int deleteSysUserLogByUserLogIds(Long[] userLogIds);

    /**
     * 删除用户日志信息
     *
     * @param userLogId 用户日志主键
     * @return 结果
     */
    public int deleteSysUserLogByUserLogId(Long userLogId);
}
