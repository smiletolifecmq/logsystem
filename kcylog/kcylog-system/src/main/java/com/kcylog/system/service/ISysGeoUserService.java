package com.kcylog.system.service;

import com.kcylog.system.domain.SysGeoUser;

import java.util.List;

/**
 * 地理用户Service接口
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
public interface ISysGeoUserService 
{
    /**
     * 查询地理用户
     * 
     * @param geoUserId 地理用户主键
     * @return 地理用户
     */
    public SysGeoUser selectSysGeoUserByGeoUserId(Long geoUserId);

    /**
     * 查询地理用户列表
     * 
     * @param sysGeoUser 地理用户
     * @return 地理用户集合
     */
    public List<SysGeoUser> selectSysGeoUserList(SysGeoUser sysGeoUser);

    /**
     * 新增地理用户
     * 
     * @param sysGeoUser 地理用户
     * @return 结果
     */
    public int insertSysGeoUser(SysGeoUser sysGeoUser);

    /**
     * 修改地理用户
     * 
     * @param sysGeoUser 地理用户
     * @return 结果
     */
    public int updateSysGeoUser(SysGeoUser sysGeoUser);

    /**
     * 批量删除地理用户
     * 
     * @param geoUserIds 需要删除的地理用户主键集合
     * @return 结果
     */
    public int deleteSysGeoUserByGeoUserIds(Long[] geoUserIds);

    /**
     * 删除地理用户信息
     * 
     * @param geoUserId 地理用户主键
     * @return 结果
     */
    public int deleteSysGeoUserByGeoUserId(Long geoUserId);

    public SysGeoUser selectSysGeoUserByUserId(Long userId);

    public SysGeoUser selectSysGeoUserByGeoUser(SysGeoUser sysGeoUser);

    public int updateSysChildGeoUser(SysGeoUser sysGeoUser);

    public List<SysGeoUser> selectSysAssessUserByGeoUser(Long userId);
}
