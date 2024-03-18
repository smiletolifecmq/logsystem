package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 结算办结导出文件存储对象 sys_settlement_file
 * 
 * @author ruoyis
 * @date 2024-03-18
 */
public class SysSettlementFile extends BaseEntity
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

    /** 结算单自增ID */
    @Excel(name = "结算单自增ID")
    private Long settlementId;

    /** 整理用户名称 */
    @Excel(name = "整理用户名称")
    private String name;

    /** 备注 */
    @Excel(name = "备注")
    private String bz;

    private List<UploadFileList> uploadFileList;

    public List<UploadFileList> getUploadFileList() {
        return uploadFileList;
    }

    public void setUploadFileList(List<UploadFileList> uploadFileList) {
        this.uploadFileList = uploadFileList;
    }

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
    public void setSettlementId(Long settlementId) 
    {
        this.settlementId = settlementId;
    }

    public Long getSettlementId() 
    {
        return settlementId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBz(String bz) 
    {
        this.bz = bz;
    }

    public String getBz() 
    {
        return bz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getFileId())
            .append("newFileName", getNewFileName())
            .append("oldFileName", getOldFileName())
            .append("url", getUrl())
            .append("fileName", getFileName())
            .append("settlementId", getSettlementId())
            .append("name", getName())
            .append("bz", getBz())
            .toString();
    }
}
