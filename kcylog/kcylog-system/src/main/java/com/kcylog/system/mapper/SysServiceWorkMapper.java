package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysServiceWork;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2025-09-01
 */
public interface SysServiceWorkMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public SysServiceWork selectSysServiceWorkById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysServiceWork 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysServiceWork> selectSysServiceWorkList(SysServiceWork sysServiceWork);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysServiceWork 【请填写功能名称】
     * @return 结果
     */
    public int insertSysServiceWork(SysServiceWork sysServiceWork);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysServiceWork 【请填写功能名称】
     * @return 结果
     */
    public int updateSysServiceWork(SysServiceWork sysServiceWork);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSysServiceWorkById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysServiceWorkByIds(Long[] ids);
}
