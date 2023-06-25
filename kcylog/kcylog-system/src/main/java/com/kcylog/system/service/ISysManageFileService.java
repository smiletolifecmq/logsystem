package com.kcylog.system.service;

import com.kcylog.system.domain.SysManageFile;

import java.util.List;

/**
 * 文件存储信息总Service接口
 * 
 * @author ruoyi
 * @date 2023-06-21
 */
public interface ISysManageFileService 
{
    /**
     * 查询文件存储信息总
     * 
     * @param fileId 文件存储信息总主键
     * @return 文件存储信息总
     */
    public SysManageFile selectSysManageFileByFileId(Long fileId);

    /**
     * 查询文件存储信息总列表
     * 
     * @param sysManageFile 文件存储信息总
     * @return 文件存储信息总集合
     */
    public List<SysManageFile> selectSysManageFileList(SysManageFile sysManageFile);

    /**
     * 新增文件存储信息总
     * 
     * @param sysManageFile 文件存储信息总
     * @return 结果
     */
    public int insertSysManageFile(SysManageFile sysManageFile);

    /**
     * 修改文件存储信息总
     * 
     * @param sysManageFile 文件存储信息总
     * @return 结果
     */
    public int updateSysManageFile(SysManageFile sysManageFile);

    /**
     * 批量删除文件存储信息总
     * 
     * @param fileIds 需要删除的文件存储信息总主键集合
     * @return 结果
     */
    public int deleteSysManageFileByFileIds(Long[] fileIds);

    /**
     * 删除文件存储信息总信息
     * 
     * @param fileId 文件存储信息总主键
     * @return 结果
     */
    public int deleteSysManageFileByFileId(Long fileId);

    public int deleteSysManageFileByModuleIdAndType(SysManageFile sysManageFile);
}
