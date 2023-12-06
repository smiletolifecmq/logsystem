package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysGeoUserCoefficientMapper;
import com.kcylog.system.domain.SysGeoUserCoefficient;
import com.kcylog.system.service.ISysGeoUserCoefficientService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
@Service
public class SysGeoUserCoefficientServiceImpl implements ISysGeoUserCoefficientService 
{
    @Autowired
    private SysGeoUserCoefficientMapper sysGeoUserCoefficientMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param coefficientId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public SysGeoUserCoefficient selectSysGeoUserCoefficientByCoefficientId(String coefficientId)
    {
        return sysGeoUserCoefficientMapper.selectSysGeoUserCoefficientByCoefficientId(coefficientId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysGeoUserCoefficient 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysGeoUserCoefficient> selectSysGeoUserCoefficientList(SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        return sysGeoUserCoefficientMapper.selectSysGeoUserCoefficientList(sysGeoUserCoefficient);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysGeoUserCoefficient 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysGeoUserCoefficient(SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        return sysGeoUserCoefficientMapper.insertSysGeoUserCoefficient(sysGeoUserCoefficient);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysGeoUserCoefficient 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysGeoUserCoefficient(SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        return sysGeoUserCoefficientMapper.updateSysGeoUserCoefficient(sysGeoUserCoefficient);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param coefficientIds 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoUserCoefficientByCoefficientIds(String[] coefficientIds)
    {
        return sysGeoUserCoefficientMapper.deleteSysGeoUserCoefficientByCoefficientIds(coefficientIds);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param coefficientId 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoUserCoefficientByCoefficientId(String coefficientId)
    {
        return sysGeoUserCoefficientMapper.deleteSysGeoUserCoefficientByCoefficientId(coefficientId);
    }
}
