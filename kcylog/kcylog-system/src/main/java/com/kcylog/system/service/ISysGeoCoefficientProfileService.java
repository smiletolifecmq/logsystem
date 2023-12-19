package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.SysGeoCoefficientProfile;

/**
 * 系数存档Service接口
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
public interface ISysGeoCoefficientProfileService 
{
    /**
     * 查询系数存档
     * 
     * @param profileId 系数存档主键
     * @return 系数存档
     */
    public SysGeoCoefficientProfile selectSysGeoCoefficientProfileByProfileId(Long profileId);

    /**
     * 查询系数存档列表
     * 
     * @param sysGeoCoefficientProfile 系数存档
     * @return 系数存档集合
     */
    public List<SysGeoCoefficientProfile> selectSysGeoCoefficientProfileList(SysGeoCoefficientProfile sysGeoCoefficientProfile);

    /**
     * 新增系数存档
     * 
     * @param sysGeoCoefficientProfile 系数存档
     * @return 结果
     */
    public int insertSysGeoCoefficientProfile(SysGeoCoefficientProfile sysGeoCoefficientProfile);

    /**
     * 修改系数存档
     * 
     * @param sysGeoCoefficientProfile 系数存档
     * @return 结果
     */
    public int updateSysGeoCoefficientProfile(SysGeoCoefficientProfile sysGeoCoefficientProfile);

    /**
     * 批量删除系数存档
     * 
     * @param profileIds 需要删除的系数存档主键集合
     * @return 结果
     */
    public int deleteSysGeoCoefficientProfileByProfileIds(Long[] profileIds);

    /**
     * 删除系数存档信息
     * 
     * @param profileId 系数存档主键
     * @return 结果
     */
    public int deleteSysGeoCoefficientProfileByProfileId(Long profileId);
}
