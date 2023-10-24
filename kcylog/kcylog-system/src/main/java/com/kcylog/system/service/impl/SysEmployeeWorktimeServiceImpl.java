package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysEmployeeWorktime;
import com.kcylog.system.mapper.SysEmployeeWorktimeMapper;
import com.kcylog.system.service.ISysEmployeeWorktimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 雇工工作时间记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@Service
public class SysEmployeeWorktimeServiceImpl implements ISysEmployeeWorktimeService 
{
    @Autowired
    private SysEmployeeWorktimeMapper sysEmployeeWorktimeMapper;

    /**
     * 查询雇工工作时间记录
     * 
     * @param worktimeId 雇工工作时间记录主键
     * @return 雇工工作时间记录
     */
    @Override
    public SysEmployeeWorktime selectSysEmployeeWorktimeByWorktimeId(Long worktimeId)
    {
        return sysEmployeeWorktimeMapper.selectSysEmployeeWorktimeByWorktimeId(worktimeId);
    }

    /**
     * 查询雇工工作时间记录列表
     * 
     * @param sysEmployeeWorktime 雇工工作时间记录
     * @return 雇工工作时间记录
     */
    @Override
    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeList(SysEmployeeWorktime sysEmployeeWorktime)
    {
        return sysEmployeeWorktimeMapper.selectSysEmployeeWorktimeList(sysEmployeeWorktime);
    }

    /**
     * 新增雇工工作时间记录
     * 
     * @param sysEmployeeWorktime 雇工工作时间记录
     * @return 结果
     */
    @Override
    public int insertSysEmployeeWorktime(SysEmployeeWorktime sysEmployeeWorktime)
    {
        return sysEmployeeWorktimeMapper.insertSysEmployeeWorktime(sysEmployeeWorktime);
    }

    /**
     * 修改雇工工作时间记录
     * 
     * @param sysEmployeeWorktime 雇工工作时间记录
     * @return 结果
     */
    @Override
    public int updateSysEmployeeWorktime(SysEmployeeWorktime sysEmployeeWorktime)
    {
        return sysEmployeeWorktimeMapper.updateSysEmployeeWorktime(sysEmployeeWorktime);
    }

    /**
     * 批量删除雇工工作时间记录
     * 
     * @param worktimeIds 需要删除的雇工工作时间记录主键
     * @return 结果
     */
    @Override
    public int deleteSysEmployeeWorktimeByWorktimeIds(Long[] worktimeIds)
    {
        return sysEmployeeWorktimeMapper.deleteSysEmployeeWorktimeByWorktimeIds(worktimeIds);
    }

    /**
     * 删除雇工工作时间记录信息
     * 
     * @param worktimeId 雇工工作时间记录主键
     * @return 结果
     */
    @Override
    public int deleteSysEmployeeWorktimeByWorktimeId(Long worktimeId)
    {
        return sysEmployeeWorktimeMapper.deleteSysEmployeeWorktimeByWorktimeId(worktimeId);
    }

    @Override
    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeExists(SysEmployeeWorktime sysEmployeeWorktime)
    {
        return sysEmployeeWorktimeMapper.selectSysEmployeeWorktimeExists(sysEmployeeWorktime);
    }

    @Override
    public int deleteSysEmployeeWorktimeByReviewEmployeeIds(Long[] reviewEmployeeIds)
    {
        return sysEmployeeWorktimeMapper.deleteSysEmployeeWorktimeByReviewEmployeeIds(reviewEmployeeIds);
    }

    @Override
    public List<SysEmployeeWorktime> selectSysEmployeeWorktimeListByTime(SysEmployeeWorktime sysEmployeeWorktime)
    {
        return sysEmployeeWorktimeMapper.selectSysEmployeeWorktimeListByTime(sysEmployeeWorktime);
    }

    @Override
    public List<SysEmployeeWorktime> selectSysEmployeeSubWorktimeListByTime(SysEmployeeWorktime sysEmployeeWorktime)
    {
        return sysEmployeeWorktimeMapper.selectSysEmployeeSubWorktimeListByTime(sysEmployeeWorktime);
    }

}
