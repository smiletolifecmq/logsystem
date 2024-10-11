package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysManagementResponsibilitiesInfo;

import java.util.List;

/**
 * 责权发生制开票明细Mapper接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface SysManagementResponsibilitiesInfoMapper 
{
    /**
     * 查询责权发生制开票明细
     * 
     * @param infoId 责权发生制开票明细主键
     * @return 责权发生制开票明细
     */
    public SysManagementResponsibilitiesInfo selectSysManagementResponsibilitiesInfoByInfoId(Long infoId);

    /**
     * 查询责权发生制开票明细列表
     * 
     * @param sysManagementResponsibilitiesInfo 责权发生制开票明细
     * @return 责权发生制开票明细集合
     */
    public List<SysManagementResponsibilitiesInfo> selectSysManagementResponsibilitiesInfoList(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo);

    /**
     * 新增责权发生制开票明细
     * 
     * @param sysManagementResponsibilitiesInfo 责权发生制开票明细
     * @return 结果
     */
    public int insertSysManagementResponsibilitiesInfo(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo);

    /**
     * 修改责权发生制开票明细
     * 
     * @param sysManagementResponsibilitiesInfo 责权发生制开票明细
     * @return 结果
     */
    public int updateSysManagementResponsibilitiesInfo(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo);

    /**
     * 删除责权发生制开票明细
     * 
     * @param infoId 责权发生制开票明细主键
     * @return 结果
     */
    public int deleteSysManagementResponsibilitiesInfoByInfoId(Long infoId);

    /**
     * 批量删除责权发生制开票明细
     * 
     * @param infoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysManagementResponsibilitiesInfoByInfoIds(Long[] infoIds);

    public List<SysManagementResponsibilitiesInfo> selectSysManagementResponsibilitiesInfoByZqId(Long zqId);
}
