package com.kcylog.system.service;

import com.kcylog.system.domain.SysProjectSelectmapTfinfo;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
public interface ISysProjectSelectmapTfinfoService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public List<SysProjectSelectmapTfinfo> selectSysProjectSelectmapTfinfoByProjectId(Long projectId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProjectSelectmapTfinfo 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysProjectSelectmapTfinfo> selectSysProjectSelectmapTfinfoList(SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysProjectSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    public int insertSysProjectSelectmapTfinfo(SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysProjectSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    public int updateSysProjectSelectmapTfinfo(SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteSysProjectSelectmapTfinfoByProjectIds(Long[] projectIds);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSysProjectSelectmapTfinfoByProjectId(Long projectId);
}
