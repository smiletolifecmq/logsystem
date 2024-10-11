package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysManagementResponsibilities;

/**
 * 责权发生制Mapper接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface SysManagementResponsibilitiesMapper 
{
    /**
     * 查询责权发生制
     * 
     * @param zqId 责权发生制主键
     * @return 责权发生制
     */
    public SysManagementResponsibilities selectSysManagementResponsibilitiesByZqId(Long zqId);

    /**
     * 查询责权发生制列表
     * 
     * @param sysManagementResponsibilities 责权发生制
     * @return 责权发生制集合
     */
    public List<SysManagementResponsibilities> selectSysManagementResponsibilitiesList(SysManagementResponsibilities sysManagementResponsibilities);

    /**
     * 新增责权发生制
     * 
     * @param sysManagementResponsibilities 责权发生制
     * @return 结果
     */
    public int insertSysManagementResponsibilities(SysManagementResponsibilities sysManagementResponsibilities);

    /**
     * 修改责权发生制
     * 
     * @param sysManagementResponsibilities 责权发生制
     * @return 结果
     */
    public int updateSysManagementResponsibilities(SysManagementResponsibilities sysManagementResponsibilities);

    /**
     * 删除责权发生制
     * 
     * @param zqId 责权发生制主键
     * @return 结果
     */
    public int deleteSysManagementResponsibilitiesByZqId(Long zqId);

    /**
     * 批量删除责权发生制
     * 
     * @param zqIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysManagementResponsibilitiesByZqIds(Long[] zqIds);
}
