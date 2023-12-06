package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.SysGeoUserCoefficient;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
public interface ISysGeoUserCoefficientService 
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
     * 批量删除【请填写功能名称】
     * 
     * @param coefficientIds 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteSysGeoUserCoefficientByCoefficientIds(String[] coefficientIds);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param coefficientId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteSysGeoUserCoefficientByCoefficientId(String coefficientId);
}
