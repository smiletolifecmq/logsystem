package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysGeoCoefficientProfile;
import com.kcylog.system.mapper.SysGeoCoefficientProfileMapper;
import com.kcylog.system.service.ISysGeoCoefficientProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系数存档Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
@Service
public class SysGeoCoefficientProfileServiceImpl implements ISysGeoCoefficientProfileService 
{
    @Autowired
    private SysGeoCoefficientProfileMapper sysGeoCoefficientProfileMapper;

    /**
     * 查询系数存档
     * 
     * @param profileId 系数存档主键
     * @return 系数存档
     */
    @Override
    public SysGeoCoefficientProfile selectSysGeoCoefficientProfileByProfileId(Long profileId)
    {
        return sysGeoCoefficientProfileMapper.selectSysGeoCoefficientProfileByProfileId(profileId);
    }

    /**
     * 查询系数存档列表
     * 
     * @param sysGeoCoefficientProfile 系数存档
     * @return 系数存档
     */
    @Override
    public List<SysGeoCoefficientProfile> selectSysGeoCoefficientProfileList(SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        return sysGeoCoefficientProfileMapper.selectSysGeoCoefficientProfileList(sysGeoCoefficientProfile);
    }

    /**
     * 新增系数存档
     * 
     * @param sysGeoCoefficientProfile 系数存档
     * @return 结果
     */
    @Override
    public int insertSysGeoCoefficientProfile(SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        return sysGeoCoefficientProfileMapper.insertSysGeoCoefficientProfile(sysGeoCoefficientProfile);
    }

    /**
     * 修改系数存档
     * 
     * @param sysGeoCoefficientProfile 系数存档
     * @return 结果
     */
    @Override
    public int updateSysGeoCoefficientProfile(SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        return sysGeoCoefficientProfileMapper.updateSysGeoCoefficientProfile(sysGeoCoefficientProfile);
    }

    /**
     * 批量删除系数存档
     * 
     * @param profileIds 需要删除的系数存档主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoCoefficientProfileByProfileIds(Long[] profileIds)
    {
        return sysGeoCoefficientProfileMapper.deleteSysGeoCoefficientProfileByProfileIds(profileIds);
    }

    /**
     * 删除系数存档信息
     * 
     * @param profileId 系数存档主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoCoefficientProfileByProfileId(Long profileId)
    {
        return sysGeoCoefficientProfileMapper.deleteSysGeoCoefficientProfileByProfileId(profileId);
    }
}
