package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysProjectCar;

import java.util.List;

/**
 * 项目车辆使用情况登记Mapper接口
 * 
 * @author ruoyi
 * @date 2023-10-17
 */
public interface SysProjectCarMapper 
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
     * 删除项目车辆使用情况登记
     * 
     * @param carId 项目车辆使用情况登记主键
     * @return 结果
     */
    public int deleteSysProjectCarByCarId(Long carId);

    /**
     * 批量删除项目车辆使用情况登记
     * 
     * @param carIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysProjectCarByCarIds(Long[] carIds);

    public int insertProjectCarBatch(List<SysProjectCar> projectCars);

    public int deleteProjectCarByCarReviewIds(Long[] carReviewIds);

    public int deleteProjectCarByCarReviewId(Long carReviewId);
}
