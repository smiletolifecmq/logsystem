package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysGeoUserCoefficient;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
public interface SysGeoUserCoefficientMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param coefficientId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public SysGeoUserCoefficient selectSysGeoUserCoefficientByCoefficientId(String coefficientId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysGeoUserCoefficient 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysGeoUserCoefficient> selectSysGeoUserCoefficientList(SysGeoUserCoefficient sysGeoUserCoefficient);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysGeoUserCoefficient 【请填写功能名称】
     * @return 结果
     */
    public int insertSysGeoUserCoefficient(SysGeoUserCoefficient sysGeoUserCoefficient);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysGeoUserCoefficient 【请填写功能名称】
     * @return 结果
     */
    public int updateSysGeoUserCoefficient(SysGeoUserCoefficient sysGeoUserCoefficient);

    /**
     * 删除【请填写功能名称】
     * 
     * @param coefficientId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSysGeoUserCoefficientByCoefficientId(String coefficientId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param coefficientIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoUserCoefficientByCoefficientIds(String[] coefficientIds);
}
