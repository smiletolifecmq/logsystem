package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 系数存档对象 sys_geo_coefficient_profile
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
public class SysGeoCoefficientProfile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增ID */
    private Long profileId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    public void setProfileId(Long profileId) 
    {
        this.profileId = profileId;
    }

    public Long getProfileId() 
    {
        return profileId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    private List<SysManageFile> manageFile;

    private List<UploadFileList> uploadFileList;

    public List<UploadFileList> getUploadFileList() {
        return uploadFileList;
    }

    public void setUploadFileList(List<UploadFileList> uploadFileList) {
        this.uploadFileList = uploadFileList;
    }

    public List<SysManageFile> getManageFile() {
        return manageFile;
    }

    public void setManageFile(List<SysManageFile> manageFile) {
        this.manageFile = manageFile;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("profileId", getProfileId())
            .append("title", getTitle())
            .toString();
    }
}
