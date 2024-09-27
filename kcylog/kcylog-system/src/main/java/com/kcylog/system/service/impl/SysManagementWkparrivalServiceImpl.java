package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysManagementWkparrivalMapper;
import com.kcylog.system.domain.SysManagementWkparrival;
import com.kcylog.system.service.ISysManagementWkparrivalService;

/**
 * 到账未开票统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
@Service
public class SysManagementWkparrivalServiceImpl implements ISysManagementWkparrivalService 
{
    @Autowired
    private SysManagementWkparrivalMapper sysManagementWkparrivalMapper;

    /**
     * 查询到账未开票统计
     * 
     * @param dzId 到账未开票统计主键
     * @return 到账未开票统计
     */
    @Override
    public SysManagementWkparrival selectSysManagementWkparrivalByDzId(Long dzId)
    {
        return sysManagementWkparrivalMapper.selectSysManagementWkparrivalByDzId(dzId);
    }

    /**
     * 查询到账未开票统计列表
     * 
     * @param sysManagementWkparrival 到账未开票统计
     * @return 到账未开票统计
     */
    @Override
    public List<SysManagementWkparrival> selectSysManagementWkparrivalList(SysManagementWkparrival sysManagementWkparrival)
    {
        return sysManagementWkparrivalMapper.selectSysManagementWkparrivalList(sysManagementWkparrival);
    }

    /**
     * 新增到账未开票统计
     * 
     * @param sysManagementWkparrival 到账未开票统计
     * @return 结果
     */
    @Override
    public int insertSysManagementWkparrival(SysManagementWkparrival sysManagementWkparrival)
    {
        return sysManagementWkparrivalMapper.insertSysManagementWkparrival(sysManagementWkparrival);
    }

    /**
     * 修改到账未开票统计
     * 
     * @param sysManagementWkparrival 到账未开票统计
     * @return 结果
     */
    @Override
    public int updateSysManagementWkparrival(SysManagementWkparrival sysManagementWkparrival)
    {
        return sysManagementWkparrivalMapper.updateSysManagementWkparrival(sysManagementWkparrival);
    }

    /**
     * 批量删除到账未开票统计
     * 
     * @param dzIds 需要删除的到账未开票统计主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementWkparrivalByDzIds(Long[] dzIds)
    {
        return sysManagementWkparrivalMapper.deleteSysManagementWkparrivalByDzIds(dzIds);
    }

    /**
     * 删除到账未开票统计信息
     * 
     * @param dzId 到账未开票统计主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementWkparrivalByDzId(Long dzId)
    {
        return sysManagementWkparrivalMapper.deleteSysManagementWkparrivalByDzId(dzId);
    }
}
