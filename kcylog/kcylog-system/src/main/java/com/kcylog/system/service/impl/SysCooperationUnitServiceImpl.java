package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysCooperationUnit;
import com.kcylog.system.mapper.SysCooperationUnitMapper;
import com.kcylog.system.service.ISysCooperationUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 协作单位Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class SysCooperationUnitServiceImpl implements ISysCooperationUnitService 
{
    @Autowired
    private SysCooperationUnitMapper sysCooperationUnitMapper;

    /**
     * 查询协作单位
     * 
     * @param unitId 协作单位主键
     * @return 协作单位
     */
    @Override
    public SysCooperationUnit selectSysCooperationUnitByUnitId(Long unitId)
    {
        return sysCooperationUnitMapper.selectSysCooperationUnitByUnitId(unitId);
    }

    /**
     * 查询协作单位列表
     * 
     * @param sysCooperationUnit 协作单位
     * @return 协作单位
     */
    @Override
    public List<SysCooperationUnit> selectSysCooperationUnitList(SysCooperationUnit sysCooperationUnit)
    {
        return sysCooperationUnitMapper.selectSysCooperationUnitList(sysCooperationUnit);
    }

    /**
     * 新增协作单位
     * 
     * @param sysCooperationUnit 协作单位
     * @return 结果
     */
    @Override
    public int insertSysCooperationUnit(SysCooperationUnit sysCooperationUnit)
    {
        return sysCooperationUnitMapper.insertSysCooperationUnit(sysCooperationUnit);
    }

    /**
     * 修改协作单位
     * 
     * @param sysCooperationUnit 协作单位
     * @return 结果
     */
    @Override
    public int updateSysCooperationUnit(SysCooperationUnit sysCooperationUnit)
    {
        return sysCooperationUnitMapper.updateSysCooperationUnit(sysCooperationUnit);
    }

    /**
     * 批量删除协作单位
     * 
     * @param unitIds 需要删除的协作单位主键
     * @return 结果
     */
    @Override
    public int deleteSysCooperationUnitByUnitIds(Long[] unitIds)
    {
        return sysCooperationUnitMapper.deleteSysCooperationUnitByUnitIds(unitIds);
    }

    /**
     * 删除协作单位信息
     * 
     * @param unitId 协作单位主键
     * @return 结果
     */
    @Override
    public int deleteSysCooperationUnitByUnitId(Long unitId)
    {
        return sysCooperationUnitMapper.deleteSysCooperationUnitByUnitId(unitId);
    }
}
