package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysProjectSelectmapTfinfo;
import com.kcylog.system.mapper.SysProjectSelectmapTfinfoMapper;
import com.kcylog.system.service.ISysProjectSelectmapTfinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-09
 */
@Service
public class SysProjectSelectmapTfinfoServiceImpl implements ISysProjectSelectmapTfinfoService 
{
    @Autowired
    private SysProjectSelectmapTfinfoMapper sysProjectSelectmapTfinfoMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProjectSelectmapTfinfo> selectSysProjectSelectmapTfinfoByProjectId(Long projectId)
    {
        return sysProjectSelectmapTfinfoMapper.selectSysProjectSelectmapTfinfoByProjectId(projectId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysProjectSelectmapTfinfo 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysProjectSelectmapTfinfo> selectSysProjectSelectmapTfinfoList(SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo)
    {
        return sysProjectSelectmapTfinfoMapper.selectSysProjectSelectmapTfinfoList(sysProjectSelectmapTfinfo);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysProjectSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysProjectSelectmapTfinfo(SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo)
    {
        return sysProjectSelectmapTfinfoMapper.insertSysProjectSelectmapTfinfo(sysProjectSelectmapTfinfo);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysProjectSelectmapTfinfo 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysProjectSelectmapTfinfo(SysProjectSelectmapTfinfo sysProjectSelectmapTfinfo)
    {
        return sysProjectSelectmapTfinfoMapper.updateSysProjectSelectmapTfinfo(sysProjectSelectmapTfinfo);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param projectIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectSelectmapTfinfoByProjectIds(Long[] projectIds)
    {
        return sysProjectSelectmapTfinfoMapper.deleteSysProjectSelectmapTfinfoByProjectIds(projectIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param projectId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectSelectmapTfinfoByProjectId(Long projectId)
    {
        return sysProjectSelectmapTfinfoMapper.deleteSysProjectSelectmapTfinfoByProjectId(projectId);
    }
}
