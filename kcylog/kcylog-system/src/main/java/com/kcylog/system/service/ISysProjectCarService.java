package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.SysProjectCar;

/**
 * 项目车辆使用情况登记Service接口
 * 
 * @author ruoyi
 * @date 2023-09-27
 */
public interface ISysProjectCarService 
{
    /**
     * 查询项目车辆使用情况登记
     * 
     * @param carId 项目车辆使用情况登记主键
     * @return 项目车辆使用情况登记
     */
    public SysProjectCar selectSysProjectCarByCarId(Long carId);

    /**
     * 查询项目车辆使用情况登记列表
     * 
     * @param sysProjectCar 项目车辆使用情况登记
     * @return 项目车辆使用情况登记集合
     */
    public List<SysProjectCar> selectSysProjectCarList(SysProjectCar sysProjectCar);

    /**
     * 新增项目车辆使用情况登记
     * 
     * @param sysProjectCar 项目车辆使用情况登记
     * @return 结果
     */
    public int insertSysProjectCar(SysProjectCar sysProjectCar);

    /**
     * 修改项目车辆使用情况登记
     * 
     * @param sysProjectCar 项目车辆使用情况登记
     * @return 结果
     */
    public int updateSysProjectCar(SysProjectCar sysProjectCar);

    /**
     * 批量删除项目车辆使用情况登记
     * 
     * @param carIds 需要删除的项目车辆使用情况登记主键集合
     * @return 结果
     */
    public int deleteSysProjectCarByCarIds(Long[] carIds);

    /**
     * 删除项目车辆使用情况登记信息
     * 
     * @param carId 项目车辆使用情况登记主键
     * @return 结果
     */
    public int deleteSysProjectCarByCarId(Long carId);
}
