package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysServiceWork;
import com.kcylog.system.mapper.SysServiceWorkMapper;
import com.kcylog.system.service.ISysServiceWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-09-01
 */
@Service
public class SysServiceWorkServiceImpl implements ISysServiceWorkService 
{
    @Autowired
    private SysServiceWorkMapper sysServiceWorkMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public SysServiceWork selectSysServiceWorkById(Long id)
    {
        return sysServiceWorkMapper.selectSysServiceWorkById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysServiceWork 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysServiceWork> selectSysServiceWorkList(SysServiceWork sysServiceWork)
    {
        return sysServiceWorkMapper.selectSysServiceWorkList(sysServiceWork);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysServiceWork 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysServiceWork(SysServiceWork sysServiceWork)
    {
        return sysServiceWorkMapper.insertSysServiceWork(sysServiceWork);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysServiceWork 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysServiceWork(SysServiceWork sysServiceWork)
    {
        return sysServiceWorkMapper.updateSysServiceWork(sysServiceWork);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysServiceWorkByIds(Long[] ids)
    {
        return sysServiceWorkMapper.deleteSysServiceWorkByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysServiceWorkById(Long id)
    {
        return sysServiceWorkMapper.deleteSysServiceWorkById(id);
    }
}
