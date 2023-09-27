package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysProjectCarMapper;
import com.kcylog.system.domain.SysProjectCar;
import com.kcylog.system.service.ISysProjectCarService;

/**
 * 项目车辆使用情况登记Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-09-27
 */
@Service
public class SysProjectCarServiceImpl implements ISysProjectCarService 
{
    @Autowired
    private SysProjectCarMapper sysProjectCarMapper;

    /**
     * 查询项目车辆使用情况登记
     * 
     * @param carId 项目车辆使用情况登记主键
     * @return 项目车辆使用情况登记
     */
    @Override
    public SysProjectCar selectSysProjectCarByCarId(Long carId)
    {
        return sysProjectCarMapper.selectSysProjectCarByCarId(carId);
    }

    /**
     * 查询项目车辆使用情况登记列表
     * 
     * @param sysProjectCar 项目车辆使用情况登记
     * @return 项目车辆使用情况登记
     */
    @Override
    public List<SysProjectCar> selectSysProjectCarList(SysProjectCar sysProjectCar)
    {
        return sysProjectCarMapper.selectSysProjectCarList(sysProjectCar);
    }

    /**
     * 新增项目车辆使用情况登记
     * 
     * @param sysProjectCar 项目车辆使用情况登记
     * @return 结果
     */
    @Override
    public int insertSysProjectCar(SysProjectCar sysProjectCar)
    {
        return sysProjectCarMapper.insertSysProjectCar(sysProjectCar);
    }

    /**
     * 修改项目车辆使用情况登记
     * 
     * @param sysProjectCar 项目车辆使用情况登记
     * @return 结果
     */
    @Override
    public int updateSysProjectCar(SysProjectCar sysProjectCar)
    {
        return sysProjectCarMapper.updateSysProjectCar(sysProjectCar);
    }

    /**
     * 批量删除项目车辆使用情况登记
     * 
     * @param carIds 需要删除的项目车辆使用情况登记主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectCarByCarIds(Long[] carIds)
    {
        return sysProjectCarMapper.deleteSysProjectCarByCarIds(carIds);
    }

    /**
     * 删除项目车辆使用情况登记信息
     * 
     * @param carId 项目车辆使用情况登记主键
     * @return 结果
     */
    @Override
    public int deleteSysProjectCarByCarId(Long carId)
    {
        return sysProjectCarMapper.deleteSysProjectCarByCarId(carId);
    }
}
