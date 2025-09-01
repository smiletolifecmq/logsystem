package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.SysServiceWork;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2025-09-01
 */
public interface ISysServiceWorkService 
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
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteSysServiceWorkByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSysServiceWorkById(Long id);
}
