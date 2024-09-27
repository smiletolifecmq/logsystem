package com.kcylog.system.service;

import com.kcylog.system.domain.SysManagementArchive;

import java.util.List;

/**
 * 经营归档Service接口
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
public interface ISysManagementArchiveService 
{
    /**
     * 查询经营归档
     * 
     * @param technicalId 经营归档主键
     * @return 经营归档
     */
    public SysManagementArchive selectSysManagementArchiveByTechnicalId(Long technicalId);

    /**
     * 查询经营归档列表
     * 
     * @param sysManagementArchive 经营归档
     * @return 经营归档集合
     */
    public List<SysManagementArchive> selectSysManagementArchiveList(SysManagementArchive sysManagementArchive);

    /**
     * 新增经营归档
     * 
     * @param sysManagementArchive 经营归档
     * @return 结果
     */
    public int insertSysManagementArchive(SysManagementArchive sysManagementArchive);

    /**
     * 修改经营归档
     * 
     * @param sysManagementArchive 经营归档
     * @return 结果
     */
    public int updateSysManagementArchive(SysManagementArchive sysManagementArchive);

    /**
     * 批量删除经营归档
     * 
     * @param technicalIds 需要删除的经营归档主键集合
     * @return 结果
     */
    public int deleteSysManagementArchiveByTechnicalIds(Long[] technicalIds);

    /**
     * 删除经营归档信息
     * 
     * @param technicalId 经营归档主键
     * @return 结果
     */
    public int deleteSysManagementArchiveByTechnicalId(Long technicalId);
}
