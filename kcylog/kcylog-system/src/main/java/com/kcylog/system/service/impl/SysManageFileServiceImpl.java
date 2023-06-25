package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.mapper.SysManageFileMapper;
import com.kcylog.system.service.ISysManageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件存储信息总Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-21
 */
@Service
public class SysManageFileServiceImpl implements ISysManageFileService 
{
    @Autowired
    private SysManageFileMapper sysManageFileMapper;

    /**
     * 查询文件存储信息总
     * 
     * @param fileId 文件存储信息总主键
     * @return 文件存储信息总
     */
    @Override
    public SysManageFile selectSysManageFileByFileId(Long fileId)
    {
        return sysManageFileMapper.selectSysManageFileByFileId(fileId);
    }

    /**
     * 查询文件存储信息总列表
     * 
     * @param sysManageFile 文件存储信息总
     * @return 文件存储信息总
     */
    @Override
    public List<SysManageFile> selectSysManageFileList(SysManageFile sysManageFile)
    {
        return sysManageFileMapper.selectSysManageFileList(sysManageFile);
    }

    /**
     * 新增文件存储信息总
     * 
     * @param sysManageFile 文件存储信息总
     * @return 结果
     */
    @Override
    public int insertSysManageFile(SysManageFile sysManageFile)
    {
        return sysManageFileMapper.insertSysManageFile(sysManageFile);
    }

    /**
     * 修改文件存储信息总
     * 
     * @param sysManageFile 文件存储信息总
     * @return 结果
     */
    @Override
    public int updateSysManageFile(SysManageFile sysManageFile)
    {
        return sysManageFileMapper.updateSysManageFile(sysManageFile);
    }

    /**
     * 批量删除文件存储信息总
     * 
     * @param fileIds 需要删除的文件存储信息总主键
     * @return 结果
     */
    @Override
    public int deleteSysManageFileByFileIds(Long[] fileIds)
    {
        return sysManageFileMapper.deleteSysManageFileByFileIds(fileIds);
    }

    /**
     * 删除文件存储信息总信息
     * 
     * @param fileId 文件存储信息总主键
     * @return 结果
     */
    @Override
    public int deleteSysManageFileByFileId(Long fileId)
    {
        return sysManageFileMapper.deleteSysManageFileByFileId(fileId);
    }

    @Override
    public int deleteSysManageFileByModuleIdAndType(SysManageFile sysManageFile)
    {
        return sysManageFileMapper.deleteSysManageFileByModuleIdAndType(sysManageFile);
    }
}
