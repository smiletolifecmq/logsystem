package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysProjectProgressMapper;
import com.kcylog.system.domain.SysProjectProgress;
import com.kcylog.system.service.ISysProjectProgressService;

/**
 * 项目进度管控Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
@Service
public class SysProjectProgressServiceImpl implements ISysProjectProgressService 
{
    @Autowired
    private SysProjectProgressMapper sysProjectProgressMapper;

    /**
     * 查询项目进度管控
     * 
     * @param progressId 项目进度管控主键
     * @return 项目进度管控
     */
    @Override
    public SysProjectProgress selectSysProjectProgressByProgressId(Long progressId)
    {
        return sysProjectProgressMapper.selectSysProjectProgressByProgressId(progressId);
    }

    /**
     * 查询项目进度管控列表
     * 
     * @param sysProjectProgress 项目进度管控
     * @return 项目进度管控
     */
    @Override
    public List<SysProjectProgress> selectSysProjectProgressList(SysProjectProgress sysProjectProgress)
    {
        return sysProjectProgressMapper.selectSysProjectProgressList(sysProjectProgress);
    }

    /**
     * 新增项目进度管控
     * 
     * @param sysProjectProgress 项目进度管控
     * @return 结果
     */
    @Override
    public int insertSysProjectProgress(SysProjectProgress sysProjectProgress)
    {
        sysProjectProgress.setCreateTime(DateUtils.getNowDate());
        return sysProjectProgressMapper.insertSysProjectProgress(sysProjectProgress);
    }

    /**
     * 修改项目进度管控
     * 
     * @param sysProjectProgress 项目进度管控
     * @return 结果
     */
    @Override
    public int updateSysProjectProgress(SysProjectProgress sysProjectProgress)
    {
        sysProjectProgress.setUpdateTime(DateUtils.getNowDate());
        return sysProjectProgressMapper.updateSysProjectProgress(sysProjectProgress);
    }

    /**
     * 批量删除项目进度管控
     * 
     * @param progressIds 需要删除的项目进度管控主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectProgressByProgressIds(Long[] progressIds)
    {
        return sysProjectProgressMapper.deleteSysProjectProgressByProgressIds(progressIds);
    }

    /**
     * 删除项目进度管控信息
     * 
     * @param progressId 项目进度管控主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectProgressByProgressId(Long progressId)
    {
        return sysProjectProgressMapper.deleteSysProjectProgressByProgressId(progressId);
    }
}
