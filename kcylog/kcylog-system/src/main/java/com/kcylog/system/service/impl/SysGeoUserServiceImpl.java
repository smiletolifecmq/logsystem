package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysGeoUser;
import com.kcylog.system.mapper.SysGeoUserMapper;
import com.kcylog.system.service.ISysGeoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地理用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
@Service
public class SysGeoUserServiceImpl implements ISysGeoUserService 
{
    @Autowired
    private SysGeoUserMapper sysGeoUserMapper;

    /**
     * 查询地理用户
     * 
     * @param geoUserId 地理用户主键
     * @return 地理用户
     */
    @Override
    public SysGeoUser selectSysGeoUserByGeoUserId(Long geoUserId)
    {
        return sysGeoUserMapper.selectSysGeoUserByGeoUserId(geoUserId);
    }

    /**
     * 查询地理用户列表
     * 
     * @param sysGeoUser 地理用户
     * @return 地理用户
     */
    @Override
    public List<SysGeoUser> selectSysGeoUserList(SysGeoUser sysGeoUser)
    {
        return sysGeoUserMapper.selectSysGeoUserList(sysGeoUser);
    }

    /**
     * 新增地理用户
     * 
     * @param sysGeoUser 地理用户
     * @return 结果
     */
    @Override
    public int insertSysGeoUser(SysGeoUser sysGeoUser)
    {
        return sysGeoUserMapper.insertSysGeoUser(sysGeoUser);
    }

    /**
     * 修改地理用户
     * 
     * @param sysGeoUser 地理用户
     * @return 结果
     */
    @Override
    public int updateSysGeoUser(SysGeoUser sysGeoUser)
    {
        return sysGeoUserMapper.updateSysGeoUser(sysGeoUser);
    }

    /**
     * 批量删除地理用户
     * 
     * @param geoUserIds 需要删除的地理用户主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoUserByGeoUserIds(Long[] geoUserIds)
    {
        return sysGeoUserMapper.deleteSysGeoUserByGeoUserIds(geoUserIds);
    }

    /**
     * 删除地理用户信息
     * 
     * @param geoUserId 地理用户主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoUserByGeoUserId(Long geoUserId)
    {
        return sysGeoUserMapper.deleteSysGeoUserByGeoUserId(geoUserId);
    }

    @Override
    public SysGeoUser selectSysGeoUserByUserId(Long userId)
    {
        return sysGeoUserMapper.selectSysGeoUserByUserId(userId);
    }
}
