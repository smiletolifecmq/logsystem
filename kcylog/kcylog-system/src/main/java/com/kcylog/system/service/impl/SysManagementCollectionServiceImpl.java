package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysManagementCollection;
import com.kcylog.system.mapper.SysManagementCollectionMapper;
import com.kcylog.system.service.ISysManagementCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应收账款Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-23
 */
@Service
public class SysManagementCollectionServiceImpl implements ISysManagementCollectionService 
{
    @Autowired
    private SysManagementCollectionMapper sysManagementCollectionMapper;

    /**
     * 查询应收账款
     * 
     * @param ysId 应收账款主键
     * @return 应收账款
     */
    @Override
    public SysManagementCollection selectSysManagementCollectionByYsId(Long ysId)
    {
        return sysManagementCollectionMapper.selectSysManagementCollectionByYsId(ysId);
    }

    /**
     * 查询应收账款列表
     * 
     * @param sysManagementCollection 应收账款
     * @return 应收账款
     */
    @Override
    public List<SysManagementCollection> selectSysManagementCollectionList(SysManagementCollection sysManagementCollection)
    {
        return sysManagementCollectionMapper.selectSysManagementCollectionList(sysManagementCollection);
    }

    /**
     * 新增应收账款
     * 
     * @param sysManagementCollection 应收账款
     * @return 结果
     */
    @Override
    public int insertSysManagementCollection(SysManagementCollection sysManagementCollection)
    {
        return sysManagementCollectionMapper.insertSysManagementCollection(sysManagementCollection);
    }

    /**
     * 修改应收账款
     * 
     * @param sysManagementCollection 应收账款
     * @return 结果
     */
    @Override
    public int updateSysManagementCollection(SysManagementCollection sysManagementCollection)
    {
        return sysManagementCollectionMapper.updateSysManagementCollection(sysManagementCollection);
    }

    /**
     * 批量删除应收账款
     * 
     * @param ysIds 需要删除的应收账款主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementCollectionByYsIds(Long[] ysIds)
    {
        return sysManagementCollectionMapper.deleteSysManagementCollectionByYsIds(ysIds);
    }

    /**
     * 删除应收账款信息
     * 
     * @param ysId 应收账款主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementCollectionByYsId(Long ysId)
    {
        return sysManagementCollectionMapper.deleteSysManagementCollectionByYsId(ysId);
    }

    @Override
    public SysManagementCollection selectSysManagementCollectionByYsFph(String ysFph)
    {
        return sysManagementCollectionMapper.selectSysManagementCollectionByYsFph(ysFph);
    }

    @Override
    public int updateCollectionDzInfo(SysManagementCollection sysManagementCollection)
    {
        return sysManagementCollectionMapper.updateCollectionDzInfo(sysManagementCollection);
    }
}
