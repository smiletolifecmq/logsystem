package com.kcylog.system.domain;

import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 作业规范对象 sys_job_specification
 * 
 * @author ruoyi
 * @date 2023-06-25
 */
public class SysJobSpecification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作业ID */
    private Long jobId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    private String standard;

    private String remark;

    private List<UploadFileList> uploadFileList;

    private List<SysManageFile> manageFile;

    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }


    public void setUser(SysUser user) {
        this.user = user;
    }

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

    public void setJobId(Long jobId) 
    {
        this.jobId = jobId;
    }

    public Long getJobId() 
    {
        return jobId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("jobId", getJobId())
            .append("userId", getUserId())
            .append("title", getTitle())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
