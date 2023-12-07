package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysGeoTypeMapper;
import com.kcylog.system.domain.SysGeoType;
import com.kcylog.system.service.ISysGeoTypeService;

/**
 * 类型配置表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
@Service
public class SysGeoTypeServiceImpl implements ISysGeoTypeService 
{
    @Autowired
    private SysGeoTypeMapper sysGeoTypeMapper;

    /**
     * 查询类型配置表
     * 
     * @param typeId 类型配置表主键
     * @return 类型配置表
     */
    @Override
    public SysGeoType selectSysGeoTypeByTypeId(Long typeId)
    {
        return sysGeoTypeMapper.selectSysGeoTypeByTypeId(typeId);
    }

    /**
     * 查询类型配置表列表
     * 
     * @param sysGeoType 类型配置表
     * @return 类型配置表
     */
    @Override
    public List<SysGeoType> selectSysGeoTypeList(SysGeoType sysGeoType)
    {
        return sysGeoTypeMapper.selectSysGeoTypeList(sysGeoType);
    }

    /**
     * 新增类型配置表
     * 
     * @param sysGeoType 类型配置表
     * @return 结果
     */
    @Override
    public int insertSysGeoType(SysGeoType sysGeoType)
    {
        return sysGeoTypeMapper.insertSysGeoType(sysGeoType);
    }

    /**
     * 修改类型配置表
     * 
     * @param sysGeoType 类型配置表
     * @return 结果
     */
    @Override
    public int updateSysGeoType(SysGeoType sysGeoType)
    {
        return sysGeoTypeMapper.updateSysGeoType(sysGeoType);
    }

    /**
     * 批量删除类型配置表
     * 
     * @param typeIds 需要删除的类型配置表主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoTypeByTypeIds(Long[] typeIds)
    {
        return sysGeoTypeMapper.deleteSysGeoTypeByTypeIds(typeIds);
    }

    /**
     * 删除类型配置表信息
     * 
     * @param typeId 类型配置表主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoTypeByTypeId(Long typeId)
    {
        return sysGeoTypeMapper.deleteSysGeoTypeByTypeId(typeId);
    }
}
