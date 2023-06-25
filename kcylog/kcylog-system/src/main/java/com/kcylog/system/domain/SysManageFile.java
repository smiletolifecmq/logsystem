package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 文件存储信息总对象 sys_manage_file
 * 
 * @author ruoyi
 * @date 2023-06-21
 */
public class SysManageFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件自增ID */
    private Long fileId;

    /** 新文件名称 */
    @Excel(name = "新文件名称")
    private String newFileName;

    /** 旧文件名称 */
    @Excel(name = "旧文件名称")
    private String oldFileName;

    /** 文件链接 */
    @Excel(name = "文件链接")
    private String url;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 对应项目自增ID */
    @Excel(name = "对应项目自增ID")
    private Long moduleId;

    /** 项目类型:1:技术更新2:知识分享3:作业规范 */
    @Excel(name = "项目类型:1:技术更新2:知识分享3:作业规范")
    private Long moduleType;

    public void setFileId(Long fileId) 
    {
        this.fileId = fileId;
    }

    public Long getFileId() 
    {
        return fileId;
    }
    public void setNewFileName(String newFileName) 
    {
        this.newFileName = newFileName;
    }

    public String getNewFileName() 
    {
        return newFileName;
    }
    public void setOldFileName(String oldFileName) 
    {
        this.oldFileName = oldFileName;
    }

    public String getOldFileName() 
    {
        return oldFileName;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setModuleId(Long moduleId) 
    {
        this.moduleId = moduleId;
    }

    public Long getModuleId() 
    {
        return moduleId;
    }
    public void setModuleType(Long moduleType) 
    {
        this.moduleType = moduleType;
    }

    public Long getModuleType() 
    {
        return moduleType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("newFileName", getNewFileName())
            .append("oldFileName", getOldFileName())
            .append("url", getUrl())
            .append("fileName", getFileName())
            .append("moduleId", getModuleId())
            .append("moduleType", getModuleType())
            .toString();
    }
}
