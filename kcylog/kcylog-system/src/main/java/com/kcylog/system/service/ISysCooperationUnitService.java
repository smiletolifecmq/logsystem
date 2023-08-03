package com.kcylog.system.service;

import com.kcylog.system.domain.SysCooperationUnit;

import java.util.List;

/**
 * 协作单位Service接口
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public interface ISysCooperationUnitService 
{
    /**
     * 查询协作单位
     * 
     * @param unitId 协作单位主键
     * @return 协作单位
     */
    public SysCooperationUnit selectSysCooperationUnitByUnitId(Long unitId);

    /**
     * 查询协作单位列表
     * 
     * @param sysCooperationUnit 协作单位
     * @return 协作单位集合
     */
    public List<SysCooperationUnit> selectSysCooperationUnitList(SysCooperationUnit sysCooperationUnit);

    /**
     * 新增协作单位
     * 
     * @param sysCooperationUnit 协作单位
     * @return 结果
     */
    public int insertSysCooperationUnit(SysCooperationUnit sysCooperationUnit);

    /**
     * 修改协作单位
     * 
     * @param sysCooperationUnit 协作单位
     * @return 结果
     */
    public int updateSysCooperationUnit(SysCooperationUnit sysCooperationUnit);

    /**
     * 批量删除协作单位
     * 
     * @param unitIds 需要删除的协作单位主键集合
     * @return 结果
     */
    public int deleteSysCooperationUnitByUnitIds(Long[] unitIds);

    /**
     * 删除协作单位信息
     * 
     * @param unitId 协作单位主键
     * @return 结果
     */
    public int deleteSysCooperationUnitByUnitId(Long unitId);
}
