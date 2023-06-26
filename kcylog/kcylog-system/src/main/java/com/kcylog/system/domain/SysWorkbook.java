package com.kcylog.system.domain;

import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 作业手册对象 sys_workbook
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public class SysWorkbook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 技术ID */
    private Long workbookId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String introduction;

    /** 事件 */
    @Excel(name = "事件")
    private String event;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    private List<UploadFileList> uploadFileList;

    private List<SysManageFile> manageFile;

    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

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


    public void setWorkbookId(Long workbookId) 
    {
        this.workbookId = workbookId;
    }

    public Long getWorkbookId() 
    {
        return workbookId;
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
    public void setIntroduction(String introduction) 
    {
        this.introduction = introduction;
    }

    public String getIntroduction() 
    {
        return introduction;
    }
    public void setEvent(String event) 
    {
        this.event = event;
    }

    public String getEvent() 
    {
        return event;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workbookId", getWorkbookId())
            .append("userId", getUserId())
            .append("title", getTitle())
            .append("introduction", getIntroduction())
            .append("event", getEvent())
            .append("sort", getSort())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
