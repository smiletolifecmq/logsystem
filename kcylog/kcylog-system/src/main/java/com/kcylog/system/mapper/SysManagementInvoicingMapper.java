package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysManagementInvoicing;

import java.util.List;

/**
 * 经营开票统计Mapper接口
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
public interface SysManagementInvoicingMapper 
{
    /**
     * 查询经营开票统计
     * 
     * @param kpId 经营开票统计主键
     * @return 经营开票统计
     */
    public SysManagementInvoicing selectSysManagementInvoicingByKpId(Long kpId);

    /**
     * 查询经营开票统计列表
     * 
     * @param sysManagementInvoicing 经营开票统计
     * @return 经营开票统计集合
     */
    public List<SysManagementInvoicing> selectSysManagementInvoicingList(SysManagementInvoicing sysManagementInvoicing);

    /**
     * 新增经营开票统计
     * 
     * @param sysManagementInvoicing 经营开票统计
     * @return 结果
     */
    public int insertSysManagementInvoicing(SysManagementInvoicing sysManagementInvoicing);

    /**
     * 修改经营开票统计
     * 
     * @param sysManagementInvoicing 经营开票统计
     * @return 结果
     */
    public int updateSysManagementInvoicing(SysManagementInvoicing sysManagementInvoicing);

    /**
     * 删除经营开票统计
     * 
     * @param kpId 经营开票统计主键
     * @return 结果
     */
    public int deleteSysManagementInvoicingByKpId(Long kpId);

    /**
     * 批量删除经营开票统计
     * 
     * @param kpIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysManagementInvoicingByKpIds(Long[] kpIds);

    public SysManagementInvoicing selectSysManagementInvoicingByKpFPH(String kpFph);

    public List<SysManagementInvoicing> selectInvoicingListHcMonth(SysManagementInvoicing sysManagementInvoicing);
}
