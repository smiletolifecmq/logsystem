package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysManagementArchiveMapper;
import com.kcylog.system.domain.SysManagementArchive;
import com.kcylog.system.service.ISysManagementArchiveService;

/**
 * 经营归档Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
@Service
public class SysManagementArchiveServiceImpl implements ISysManagementArchiveService 
{
    @Autowired
    private SysManagementArchiveMapper sysManagementArchiveMapper;

    /**
     * 查询经营归档
     * 
     * @param technicalId 经营归档主键
     * @return 经营归档
     */
    @Override
    public SysManagementArchive selectSysManagementArchiveByTechnicalId(Long technicalId)
    {
        return sysManagementArchiveMapper.selectSysManagementArchiveByTechnicalId(technicalId);
    }

    /**
     * 查询经营归档列表
     * 
     * @param sysManagementArchive 经营归档
     * @return 经营归档
     */
    @Override
    public List<SysManagementArchive> selectSysManagementArchiveList(SysManagementArchive sysManagementArchive)
    {
        return sysManagementArchiveMapper.selectSysManagementArchiveList(sysManagementArchive);
    }

    /**
     * 新增经营归档
     * 
     * @param sysManagementArchive 经营归档
     * @return 结果
     */
    @Override
    public int insertSysManagementArchive(SysManagementArchive sysManagementArchive)
    {
        sysManagementArchive.setCreateTime(DateUtils.getNowDate());
        return sysManagementArchiveMapper.insertSysManagementArchive(sysManagementArchive);
    }

    /**
     * 修改经营归档
     * 
     * @param sysManagementArchive 经营归档
     * @return 结果
     */
    @Override
    public int updateSysManagementArchive(SysManagementArchive sysManagementArchive)
    {
        sysManagementArchive.setUpdateTime(DateUtils.getNowDate());
        return sysManagementArchiveMapper.updateSysManagementArchive(sysManagementArchive);
    }

    /**
     * 批量删除经营归档
     * 
     * @param technicalIds 需要删除的经营归档主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementArchiveByTechnicalIds(Long[] technicalIds)
    {
        return sysManagementArchiveMapper.deleteSysManagementArchiveByTechnicalIds(technicalIds);
    }

    /**
     * 删除经营归档信息
     * 
     * @param technicalId 经营归档主键
     * @return 结果
     */
    @Override
    public int deleteSysManagementArchiveByTechnicalId(Long technicalId)
    {
        return sysManagementArchiveMapper.deleteSysManagementArchiveByTechnicalId(technicalId);
    }
}
