package com.kcylog.system.service;

import com.kcylog.system.domain.SysManagementResponsibilitiesInfo;

import java.util.List;

/**
 * 责权发生制开票明细Service接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface ISysManagementResponsibilitiesInfoService 
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
     * 批量删除责权发生制开票明细
     * 
     * @param infoIds 需要删除的责权发生制开票明细主键集合
     * @return 结果
     */
    public int deleteSysManagementResponsibilitiesInfoByInfoIds(Long[] infoIds);

    /**
     * 删除责权发生制开票明细信息
     * 
     * @param infoId 责权发生制开票明细主键
     * @return 结果
     */
    public int deleteSysManagementResponsibilitiesInfoByInfoId(Long infoId);

    public List<SysManagementResponsibilitiesInfo> selectSysManagementResponsibilitiesInfoByZqId(Long zqId);
}
