package com.kcylog.system.service;

import com.kcylog.system.domain.SysManagementWkparrival;

import java.util.List;

/**
 * 到账未开票统计Service接口
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
public interface ISysManagementWkparrivalService 
{
    /**
     * 查询到账未开票统计
     * 
     * @param dzId 到账未开票统计主键
     * @return 到账未开票统计
     */
    public SysManagementWkparrival selectSysManagementWkparrivalByDzId(Long dzId);

    /**
     * 查询到账未开票统计列表
     * 
     * @param sysManagementWkparrival 到账未开票统计
     * @return 到账未开票统计集合
     */
    public List<SysManagementWkparrival> selectSysManagementWkparrivalList(SysManagementWkparrival sysManagementWkparrival);

    /**
     * 新增到账未开票统计
     * 
     * @param sysManagementWkparrival 到账未开票统计
     * @return 结果
     */
    public int insertSysManagementWkparrival(SysManagementWkparrival sysManagementWkparrival);

    /**
     * 修改到账未开票统计
     * 
     * @param sysManagementWkparrival 到账未开票统计
     * @return 结果
     */
    public int updateSysManagementWkparrival(SysManagementWkparrival sysManagementWkparrival);

    /**
     * 批量删除到账未开票统计
     * 
     * @param dzIds 需要删除的到账未开票统计主键集合
     * @return 结果
     */
    public int deleteSysManagementWkparrivalByDzIds(Long[] dzIds);

    /**
     * 删除到账未开票统计信息
     * 
     * @param dzId 到账未开票统计主键
     * @return 结果
     */
    public int deleteSysManagementWkparrivalByDzId(Long dzId);
}
