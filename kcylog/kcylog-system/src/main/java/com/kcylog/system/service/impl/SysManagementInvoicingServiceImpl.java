package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysManagementInvoicing;
import com.kcylog.system.mapper.SysManagementInvoicingMapper;
import com.kcylog.system.service.ISysManagementInvoicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 经营开票统计Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
@Service
public class SysManagementInvoicingServiceImpl implements ISysManagementInvoicingService 
{
    @Autowired
    private SysManagementInvoicingMapper sysManagementInvoicingMapper;

    /**
     * 查询经营开票统计
     * 
     * @param kpId 经营开票统计主键
     * @return 经营开票统计
     */
    @Override
    public SysManagementInvoicing selectSysManagementInvoicingByKpId(Long kpId)
    {
        return sysManagementInvoicingMapper.selectSysManagementInvoicingByKpId(kpId);
    }

    /**
     * 查询经营开票统计列表
     * 
     * @param sysManagementInvoicing 经营开票统计
     * @return 经营开票统计
     */
    @Override
    public List<SysManagementInvoicing> selectSysManagementInvoicingList(SysManagementInvoicing sysManagementInvoicing)
    {
        return sysManagementInvoicingMapper.selectSysManagementInvoicingList(sysManagementInvoicing);
    }

    /**
     * 新增经营开票统计
     * 
     * @param sysManagementInvoicing 经营开票统计
     * @return 结果
     */
    @Override
    public int insertSysManagementInvoicing(SysManagementInvoicing sysManagementInvoicing)
    {
        return sysManagementInvoicingMapper.insertSysManagementInvoicing(sysManagementInvoicing);
    }

    /**
     * 修改经营开票统计
     * 
     * @param sysManagementInvoicing 经营开票统计
     * @return 结果
     */
    @Override
    public int updateSysManagementInvoicing(SysManagementInvoicing sysManagementInvoicing)
    {
        return sysManagementInvoicingMapper.updateSysManagementInvoicing(sysManagementInvoicing);
    }

    /**
     * 批量删除经营开票统计
     * 
     * @param kpIds 需要删除的经营开票统计主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementInvoicingByKpIds(Long[] kpIds)
    {
        return sysManagementInvoicingMapper.deleteSysManagementInvoicingByKpIds(kpIds);
    }

    /**
     * 删除经营开票统计信息
     * 
     * @param kpId 经营开票统计主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementInvoicingByKpId(Long kpId)
    {
        return sysManagementInvoicingMapper.deleteSysManagementInvoicingByKpId(kpId);
    }

    @Override
    public SysManagementInvoicing selectSysManagementInvoicingByKpFPH(String kpFph)
    {
        return sysManagementInvoicingMapper.selectSysManagementInvoicingByKpFPH(kpFph);
    }
}
