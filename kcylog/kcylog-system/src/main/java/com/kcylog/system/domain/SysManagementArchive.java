package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 经营归档对象 sys_management_archive
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
public class SysManagementArchive extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long technicalId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    private List<UploadFileList> uploadFileList;

    private List<SysManageFile> manageFile;

    public List<SysManageFile> getManageFile() {
        return manageFile;
    }

    public void setManageFile(List<SysManageFile> manageFile) {
        this.manageFile = manageFile;
    }

    public List<UploadFileList> getUploadFileList() {
        return uploadFileList;
    }

    public void setUploadFileList(List<UploadFileList> uploadFileList) {
        this.uploadFileList = uploadFileList;
    }

    public void setTechnicalId(Long technicalId)
    {
        this.technicalId = technicalId;
    }

    public Long getTechnicalId() 
    {
        return technicalId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("technicalId", getTechnicalId())
            .append("userName", getUserName())
            .append("title", getTitle())
            .append("introduction", getIntroduction())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
