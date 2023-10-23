package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysEmployeeWorktime;

import java.util.List;

/**
 * 雇工工作时间记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface SysEmployeeWorktimeMapper 
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
     * 删除雇工工作时间记录
     * 
     * @param worktimeId 雇工工作时间记录主键
     * @return 结果
     */
    public int deleteSysEmployeeWorktimeByWorktimeId(Long worktimeId);

    /**
     * 批量删除雇工工作时间记录
     * 
     * @param worktimeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysEmployeeWorktimeByWorktimeIds(Long[] worktimeIds);

    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeExists(SysEmployeeWorktime sysEmployeeWorktime);

    public int deleteSysEmployeeWorktimeByReviewEmployeeIds(Long[] reviewEmployeeIds);

    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeListByTime(SysEmployeeWorktime sysEmployeeWorktime);
}
