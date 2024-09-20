package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysManagementArrivalMapper;
import com.kcylog.system.domain.SysManagementArrival;
import com.kcylog.system.service.ISysManagementArrivalService;

/**
 * 经营到账统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
@Service
public class SysManagementArrivalServiceImpl implements ISysManagementArrivalService 
{
    @Autowired
    private SysManagementArrivalMapper sysManagementArrivalMapper;

    /**
     * 查询经营到账统计
     * 
     * @param dzId 经营到账统计主键
     * @return 经营到账统计
     */
    @Override
    public SysManagementArrival selectSysManagementArrivalByDzId(Long dzId)
    {
        return sysManagementArrivalMapper.selectSysManagementArrivalByDzId(dzId);
    }

    /**
     * 查询经营到账统计列表
     * 
     * @param sysManagementArrival 经营到账统计
     * @return 经营到账统计
     */
    @Override
    public List<SysManagementArrival> selectSysManagementArrivalList(SysManagementArrival sysManagementArrival)
    {
        return sysManagementArrivalMapper.selectSysManagementArrivalList(sysManagementArrival);
    }

    /**
     * 新增经营到账统计
     * 
     * @param sysManagementArrival 经营到账统计
     * @return 结果
     */
    @Override
    public int insertSysManagementArrival(SysManagementArrival sysManagementArrival)
    {
        return sysManagementArrivalMapper.insertSysManagementArrival(sysManagementArrival);
    }

    /**
     * 修改经营到账统计
     * 
     * @param sysManagementArrival 经营到账统计
     * @return 结果
     */
    @Override
    public int updateSysManagementArrival(SysManagementArrival sysManagementArrival)
    {
        return sysManagementArrivalMapper.updateSysManagementArrival(sysManagementArrival);
    }

    /**
     * 批量删除经营到账统计
     * 
     * @param dzIds 需要删除的经营到账统计主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementArrivalByDzIds(Long[] dzIds)
    {
        return sysManagementArrivalMapper.deleteSysManagementArrivalByDzIds(dzIds);
    }

    /**
     * 删除经营到账统计信息
     * 
     * @param dzId 经营到账统计主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementArrivalByDzId(Long dzId)
    {
        return sysManagementArrivalMapper.deleteSysManagementArrivalByDzId(dzId);
    }

    @Override
    public List<SysManagementArrival> selectSysManagementArrivalByDzFph(String dzFph)
    {
        return sysManagementArrivalMapper.selectSysManagementArrivalByDzFph(dzFph);
    }
}
