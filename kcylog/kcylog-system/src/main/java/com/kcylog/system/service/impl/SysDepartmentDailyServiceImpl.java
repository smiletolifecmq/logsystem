package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysDepartmentDailyMapper;
import com.kcylog.system.domain.SysDepartmentDaily;
import com.kcylog.system.service.ISysDepartmentDailyService;

/**
 * 部门日常Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
@Service
public class SysDepartmentDailyServiceImpl implements ISysDepartmentDailyService 
{
    @Autowired
    private SysDepartmentDailyMapper sysDepartmentDailyMapper;

    /**
     * 查询部门日常
     * 
     * @param dailyId 部门日常主键
     * @return 部门日常
     */
    @Override
    public SysDepartmentDaily selectSysDepartmentDailyByDailyId(Long dailyId)
    {
        return sysDepartmentDailyMapper.selectSysDepartmentDailyByDailyId(dailyId);
    }

    /**
     * 查询部门日常列表
     * 
     * @param sysDepartmentDaily 部门日常
     * @return 部门日常
     */
    @Override
    public List<SysDepartmentDaily> selectSysDepartmentDailyList(SysDepartmentDaily sysDepartmentDaily)
    {
        return sysDepartmentDailyMapper.selectSysDepartmentDailyList(sysDepartmentDaily);
    }

    /**
     * 新增部门日常
     * 
     * @param sysDepartmentDaily 部门日常
     * @return 结果
     */
    @Override
    public int insertSysDepartmentDaily(SysDepartmentDaily sysDepartmentDaily)
    {
        sysDepartmentDaily.setCreateTime(DateUtils.getNowDate());
        return sysDepartmentDailyMapper.insertSysDepartmentDaily(sysDepartmentDaily);
    }

    /**
     * 修改部门日常
     * 
     * @param sysDepartmentDaily 部门日常
     * @return 结果
     */
    @Override
    public int updateSysDepartmentDaily(SysDepartmentDaily sysDepartmentDaily)
    {
        sysDepartmentDaily.setUpdateTime(DateUtils.getNowDate());
        return sysDepartmentDailyMapper.updateSysDepartmentDaily(sysDepartmentDaily);
    }

    /**
     * 批量删除部门日常
     * 
     * @param dailyIds 需要删除的部门日常主键
     * @return 结果
     */
    @Override
    public int deleteSysDepartmentDailyByDailyIds(Long[] dailyIds)
    {
        return sysDepartmentDailyMapper.deleteSysDepartmentDailyByDailyIds(dailyIds);
    }

    /**
     * 删除部门日常信息
     * 
     * @param dailyId 部门日常主键
     * @return 结果
     */
    @Override
    public int deleteSysDepartmentDailyByDailyId(Long dailyId)
    {
        return sysDepartmentDailyMapper.deleteSysDepartmentDailyByDailyId(dailyId);
    }
}
