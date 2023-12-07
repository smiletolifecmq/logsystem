package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysGeoType;

/**
 * 类型配置表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
public interface SysGeoTypeMapper 
{
    /**
     * 查询类型配置表
     * 
     * @param typeId 类型配置表主键
     * @return 类型配置表
     */
    public SysGeoType selectSysGeoTypeByTypeId(Long typeId);

    /**
     * 查询类型配置表列表
     * 
     * @param sysGeoType 类型配置表
     * @return 类型配置表集合
     */
    public List<SysGeoType> selectSysGeoTypeList(SysGeoType sysGeoType);

    /**
     * 新增类型配置表
     * 
     * @param sysGeoType 类型配置表
     * @return 结果
     */
    public int insertSysGeoType(SysGeoType sysGeoType);

    /**
     * 修改类型配置表
     * 
     * @param sysGeoType 类型配置表
     * @return 结果
     */
    public int updateSysGeoType(SysGeoType sysGeoType);

    /**
     * 删除类型配置表
     * 
     * @param typeId 类型配置表主键
     * @return 结果
     */
    public int deleteSysGeoTypeByTypeId(Long typeId);

    /**
     * 批量删除类型配置表
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoTypeByTypeIds(Long[] typeIds);
}
