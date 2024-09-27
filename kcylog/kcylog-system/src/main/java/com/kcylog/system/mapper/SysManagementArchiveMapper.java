package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysManagementArchive;

import java.util.List;

/**
 * 经营归档Mapper接口
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
public interface SysManagementArchiveMapper 
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
     * 删除经营归档
     * 
     * @param technicalId 经营归档主键
     * @return 结果
     */
    public int deleteSysManagementArchiveByTechnicalId(Long technicalId);

    /**
     * 批量删除经营归档
     * 
     * @param technicalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysManagementArchiveByTechnicalIds(Long[] technicalIds);
}
