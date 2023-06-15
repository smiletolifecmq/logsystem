package com.kcylog.system.service;

import com.kcylog.system.domain.SysEmployeeWorktime;

import java.util.List;

/**
 * 雇工工作时间记录Service接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface ISysEmployeeWorktimeService 
{
    /**
     * 查询雇工工作时间记录
     * 
     * @param worktimeId 雇工工作时间记录主键
     * @return 雇工工作时间记录
     */
    public SysEmployeeWorktime selectSysEmployeeWorktimeByWorktimeId(Long worktimeId);

    /**
     * 查询雇工工作时间记录列表
     * 
     * @param sysEmployeeWorktime 雇工工作时间记录
     * @return 雇工工作时间记录集合
     */
    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeList(SysEmployeeWorktime sysEmployeeWorktime);

    /**
     * 新增雇工工作时间记录
     * 
     * @param sysEmployeeWorktime 雇工工作时间记录
     * @return 结果
     */
    public int insertSysEmployeeWorktime(SysEmployeeWorktime sysEmployeeWorktime);

    /**
     * 修改雇工工作时间记录
     * 
     * @param sysEmployeeWorktime 雇工工作时间记录
     * @return 结果
     */
    public int updateSysEmployeeWorktime(SysEmployeeWorktime sysEmployeeWorktime);

    /**
     * 批量删除雇工工作时间记录
     * 
     * @param worktimeIds 需要删除的雇工工作时间记录主键集合
     * @return 结果
     */
    public int deleteSysEmployeeWorktimeByWorktimeIds(Long[] worktimeIds);

    /**
     * 删除雇工工作时间记录信息
     * 
     * @param worktimeId 雇工工作时间记录主键
     * @return 结果
     */
    public int deleteSysEmployeeWorktimeByWorktimeId(Long worktimeId);

    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeExists(SysEmployeeWorktime sysEmployeeWorktime);

    public int deleteSysEmployeeWorktimeByReviewEmployeeIds(Long[] reviewEmployeeIds);
}
