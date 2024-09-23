package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysManagementCollection;

import java.util.List;

/**
 * 应收账款Mapper接口
 * 
 * @author ruoyi
 * @date 2024-09-23
 */
public interface SysManagementCollectionMapper 
{
    /**
     * 查询应收账款
     * 
     * @param ysId 应收账款主键
     * @return 应收账款
     */
    public SysManagementCollection selectSysManagementCollectionByYsId(Long ysId);

    /**
     * 查询应收账款列表
     * 
     * @param sysManagementCollection 应收账款
     * @return 应收账款集合
     */
    public List<SysManagementCollection> selectSysManagementCollectionList(SysManagementCollection sysManagementCollection);

    /**
     * 新增应收账款
     * 
     * @param sysManagementCollection 应收账款
     * @return 结果
     */
    public int insertSysManagementCollection(SysManagementCollection sysManagementCollection);

    /**
     * 修改应收账款
     * 
     * @param sysManagementCollection 应收账款
     * @return 结果
     */
    public int updateSysManagementCollection(SysManagementCollection sysManagementCollection);

    /**
     * 删除应收账款
     * 
     * @param ysId 应收账款主键
     * @return 结果
     */
    public int deleteSysManagementCollectionByYsId(Long ysId);

    /**
     * 批量删除应收账款
     * 
     * @param ysIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysManagementCollectionByYsIds(Long[] ysIds);

    public SysManagementCollection selectSysManagementCollectionByYsFph(String ysFph);

    public int updateCollectionDzInfo(SysManagementCollection sysManagementCollection);

    public List<SysManagementCollection> selectSysManagementCollectionListYdz(SysManagementCollection sysManagementCollection);
}
