package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysManagementResponsibilitiesMapper;
import com.kcylog.system.domain.SysManagementResponsibilities;
import com.kcylog.system.service.ISysManagementResponsibilitiesService;

/**
 * 责权发生制Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@Service
public class SysManagementResponsibilitiesServiceImpl implements ISysManagementResponsibilitiesService 
{
    @Autowired
    private SysManagementResponsibilitiesMapper sysManagementResponsibilitiesMapper;

    /**
     * 查询责权发生制
     * 
     * @param zqId 责权发生制主键
     * @return 责权发生制
     */
    @Override
    public SysManagementResponsibilities selectSysManagementResponsibilitiesByZqId(Long zqId)
    {
        return sysManagementResponsibilitiesMapper.selectSysManagementResponsibilitiesByZqId(zqId);
    }

    /**
     * 查询责权发生制列表
     * 
     * @param sysManagementResponsibilities 责权发生制
     * @return 责权发生制
     */
    @Override
    public List<SysManagementResponsibilities> selectSysManagementResponsibilitiesList(SysManagementResponsibilities sysManagementResponsibilities)
    {
        return sysManagementResponsibilitiesMapper.selectSysManagementResponsibilitiesList(sysManagementResponsibilities);
    }

    /**
     * 新增责权发生制
     * 
     * @param sysManagementResponsibilities 责权发生制
     * @return 结果
     */
    @Override
    public int insertSysManagementResponsibilities(SysManagementResponsibilities sysManagementResponsibilities)
    {
        return sysManagementResponsibilitiesMapper.insertSysManagementResponsibilities(sysManagementResponsibilities);
    }

    /**
     * 修改责权发生制
     * 
     * @param sysManagementResponsibilities 责权发生制
     * @return 结果
     */
    @Override
    public int updateSysManagementResponsibilities(SysManagementResponsibilities sysManagementResponsibilities)
    {
        return sysManagementResponsibilitiesMapper.updateSysManagementResponsibilities(sysManagementResponsibilities);
    }

    /**
     * 批量删除责权发生制
     * 
     * @param zqIds 需要删除的责权发生制主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementResponsibilitiesByZqIds(Long[] zqIds)
    {
        return sysManagementResponsibilitiesMapper.deleteSysManagementResponsibilitiesByZqIds(zqIds);
    }

    /**
     * 删除责权发生制信息
     * 
     * @param zqId 责权发生制主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementResponsibilitiesByZqId(Long zqId)
    {
        return sysManagementResponsibilitiesMapper.deleteSysManagementResponsibilitiesByZqId(zqId);
    }
}
