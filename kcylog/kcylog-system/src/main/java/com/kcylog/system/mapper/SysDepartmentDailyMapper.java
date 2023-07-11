package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysDepartmentDaily;

import java.util.List;

/**
 * 部门日常Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
public interface SysDepartmentDailyMapper 
{
    /**
     * 查询部门日常
     * 
     * @param dailyId 部门日常主键
     * @return 部门日常
     */
    public SysDepartmentDaily selectSysDepartmentDailyByDailyId(Long dailyId);

    /**
     * 查询部门日常列表
     * 
     * @param sysDepartmentDaily 部门日常
     * @return 部门日常集合
     */
    public List<SysDepartmentDaily> selectSysDepartmentDailyList(SysDepartmentDaily sysDepartmentDaily);

    /**
     * 新增部门日常
     * 
     * @param sysDepartmentDaily 部门日常
     * @return 结果
     */
    public int insertSysDepartmentDaily(SysDepartmentDaily sysDepartmentDaily);

    /**
     * 修改部门日常
     * 
     * @param sysDepartmentDaily 部门日常
     * @return 结果
     */
    public int updateSysDepartmentDaily(SysDepartmentDaily sysDepartmentDaily);

    /**
     * 删除部门日常
     * 
     * @param dailyId 部门日常主键
     * @return 结果
     */
    public int deleteSysDepartmentDailyByDailyId(Long dailyId);

    /**
     * 批量删除部门日常
     * 
     * @param dailyIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDepartmentDailyByDailyIds(Long[] dailyIds);
}
