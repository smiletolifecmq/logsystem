package com.kcylog.system.service;

import com.kcylog.system.domain.SysProjectProgress;

import java.util.List;

/**
 * 项目进度管控Service接口
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
public interface ISysProjectProgressService 
{
    /**
     * 查询项目进度管控
     * 
     * @param progressId 项目进度管控主键
     * @return 项目进度管控
     */
    public SysProjectProgress selectSysProjectProgressByProgressId(Long progressId);

    /**
     * 查询项目进度管控列表
     * 
     * @param sysProjectProgress 项目进度管控
     * @return 项目进度管控集合
     */
    public List<SysProjectProgress> selectSysProjectProgressList(SysProjectProgress sysProjectProgress);

    /**
     * 新增项目进度管控
     * 
     * @param sysProjectProgress 项目进度管控
     * @return 结果
     */
    public int insertSysProjectProgress(SysProjectProgress sysProjectProgress);

    /**
     * 修改项目进度管控
     * 
     * @param sysProjectProgress 项目进度管控
     * @return 结果
     */
    public int updateSysProjectProgress(SysProjectProgress sysProjectProgress);

    /**
     * 批量删除项目进度管控
     * 
     * @param progressIds 需要删除的项目进度管控主键集合
     * @return 结果
     */
    public int deleteSysProjectProgressByProgressIds(Long[] progressIds);

    /**
     * 删除项目进度管控信息
     * 
     * @param progressId 项目进度管控主键
     * @return 结果
     */
    public int deleteSysProjectProgressByProgressId(Long progressId);
}
