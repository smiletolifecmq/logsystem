package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysManagementArrival;

import java.util.List;

/**
 * 经营到账统计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
public interface SysManagementArrivalMapper 
{
    /**
     * 查询经营到账统计
     * 
     * @param dzId 经营到账统计主键
     * @return 经营到账统计
     */
    public SysManagementArrival selectSysManagementArrivalByDzId(Long dzId);

    /**
     * 查询经营到账统计列表
     * 
     * @param sysManagementArrival 经营到账统计
     * @return 经营到账统计集合
     */
    public List<SysManagementArrival> selectSysManagementArrivalList(SysManagementArrival sysManagementArrival);

    /**
     * 新增经营到账统计
     * 
     * @param sysManagementArrival 经营到账统计
     * @return 结果
     */
    public int insertSysManagementArrival(SysManagementArrival sysManagementArrival);

    /**
     * 修改经营到账统计
     * 
     * @param sysManagementArrival 经营到账统计
     * @return 结果
     */
    public int updateSysManagementArrival(SysManagementArrival sysManagementArrival);

    /**
     * 删除经营到账统计
     * 
     * @param dzId 经营到账统计主键
     * @return 结果
     */
    public int deleteSysManagementArrivalByDzId(Long dzId);

    /**
     * 批量删除经营到账统计
     * 
     * @param dzIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysManagementArrivalByDzIds(Long[] dzIds);

    public List<SysManagementArrival> selectSysManagementArrivalByDzFph(String dzFph);

    public List<SysManagementArrival> selectSysManagementArrivalListForFphs(List<String> fphs);
}
