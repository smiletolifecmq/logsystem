package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysManagementResponsibilitiesInfo;
import com.kcylog.system.mapper.SysManagementResponsibilitiesInfoMapper;
import com.kcylog.system.service.ISysManagementResponsibilitiesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 责权发生制开票明细Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@Service
public class SysManagementResponsibilitiesInfoServiceImpl implements ISysManagementResponsibilitiesInfoService 
{
    @Autowired
    private SysManagementResponsibilitiesInfoMapper sysManagementResponsibilitiesInfoMapper;

    /**
     * 查询责权发生制开票明细
     * 
     * @param infoId 责权发生制开票明细主键
     * @return 责权发生制开票明细
     */
    @Override
    public SysManagementResponsibilitiesInfo selectSysManagementResponsibilitiesInfoByInfoId(Long infoId)
    {
        return sysManagementResponsibilitiesInfoMapper.selectSysManagementResponsibilitiesInfoByInfoId(infoId);
    }

    /**
     * 查询责权发生制开票明细列表
     * 
     * @param sysManagementResponsibilitiesInfo 责权发生制开票明细
     * @return 责权发生制开票明细
     */
    @Override
    public List<SysManagementResponsibilitiesInfo> selectSysManagementResponsibilitiesInfoList(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        return sysManagementResponsibilitiesInfoMapper.selectSysManagementResponsibilitiesInfoList(sysManagementResponsibilitiesInfo);
    }

    /**
     * 新增责权发生制开票明细
     * 
     * @param sysManagementResponsibilitiesInfo 责权发生制开票明细
     * @return 结果
     */
    @Override
    public int insertSysManagementResponsibilitiesInfo(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        return sysManagementResponsibilitiesInfoMapper.insertSysManagementResponsibilitiesInfo(sysManagementResponsibilitiesInfo);
    }

    /**
     * 修改责权发生制开票明细
     * 
     * @param sysManagementResponsibilitiesInfo 责权发生制开票明细
     * @return 结果
     */
    @Override
    public int updateSysManagementResponsibilitiesInfo(SysManagementResponsibilitiesInfo sysManagementResponsibilitiesInfo)
    {
        return sysManagementResponsibilitiesInfoMapper.updateSysManagementResponsibilitiesInfo(sysManagementResponsibilitiesInfo);
    }

    /**
     * 批量删除责权发生制开票明细
     * 
     * @param infoIds 需要删除的责权发生制开票明细主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementResponsibilitiesInfoByInfoIds(Long[] infoIds)
    {
        return sysManagementResponsibilitiesInfoMapper.deleteSysManagementResponsibilitiesInfoByInfoIds(infoIds);
    }

    /**
     * 删除责权发生制开票明细信息
     * 
     * @param infoId 责权发生制开票明细主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementResponsibilitiesInfoByInfoId(Long infoId)
    {
        return sysManagementResponsibilitiesInfoMapper.deleteSysManagementResponsibilitiesInfoByInfoId(infoId);
    }

    @Override
    public List<SysManagementResponsibilitiesInfo> selectSysManagementResponsibilitiesInfoByZqId(Long zqId)
    {
        return sysManagementResponsibilitiesInfoMapper.selectSysManagementResponsibilitiesInfoByZqId(zqId);
    }
}
