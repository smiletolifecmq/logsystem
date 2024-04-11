package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 新系统项目流程同步对象 fq_project_process
 * 
 * @author ruoyi
 * @date 2024-04-11
 */
public class FqProjectProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 项目id */
    @Excel(name = "项目id")
    private Long projectId;

    /** 登记状态1："登记中"，2："登记办结" */
    private Long registerStatus;

    /** 登记办结时间 */
    @Excel(name = "登记办结时间")
    private String registerTime;

    /** 作业：1： "新增作业"，2："作业中"，3："作业完成"，4："作业办结" */
    private Long workStatus;

    /** 作业办结时间 */
    @Excel(name = "作业办结时间")
    private String workTime;

    /** 一检：1通过 2不通过 */
    @Excel(name = "一检：1通过 2不通过")
    private Long oneCheckStatus;

    /** 一检时间 */
    @Excel(name = "一检时间")
    private String oneCheckTime;

    /** 二检：1通过 2不通过 */
    @Excel(name = "二检：1通过 2不通过")
    private Long twoCheckStatus;

    /** 二检时间 */
    @Excel(name = "二检时间")
    private String twoCheckTime;

    /** 移交1："未移交"，2："移交办结" */
    private Long transferStatus;

    /** 移交办结时间 */
    @Excel(name = "移交办结时间")
    private String transferTime;

    /** 移交用户名 */
    @Excel(name = "移交用户名")
    private String transferUserName;

    /** 收件1： "未收件"，2："收件办结" */
    private Long receiveStatus;

    /** 收件办结时间 */
    @Excel(name = "收件办结时间")
    private String receiveTime;

    /** 收件用户名 */
    @Excel(name = "收件用户名")
    private String receiveUserName;

    /** 盖章1："未盖章"；2："已盖章"；3："盖章拒绝"；4："无需盖章" */
    private Long stampStatus;

    /** 盖章时间 */
    @Excel(name = "盖章时间")
    private String stampTime;

    /** 盖章用户 */
    @Excel(name = "盖章用户")
    private String stampUserName;

    /** 确认盖章0未确认，1确认 */
    @Excel(name = "确认盖章0未确认，1确认")
    private Long marketingConfirm;

    /** 确认盖章时间 */
    @Excel(name = "确认盖章时间")
    private String marketingTime;

    /** 确认盖章用户 */
    @Excel(name = "确认盖章用户")
    private String marketingUserName;

    /** 验收1："未验收"；2："验收办结"；3："验收拒绝"，4： "验收通过" */
    private Long checkStatus;

    /** 验收时间 */
    @Excel(name = "验收时间")
    private String checkTime;

    /** 验收通过用户 */
    @Excel(name = "验收通过用户")
    private String checkUserName;

    /** 归档时间 */
    @Excel(name = "归档时间")
    private String archiveTime;

    /** 归档用户 */
    @Excel(name = "归档用户")
    private String archiveUserName;

    /** 收件截止时间 */
    @Excel(name = "收件截止时间")
    private String receiveCutoffTime;

    /** 整改截止时间 */
    @Excel(name = "整改截止时间")
    private String rectifyCutoffTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }
    public void setRegisterStatus(Long registerStatus) 
    {
        this.registerStatus = registerStatus;
    }

    public Long getRegisterStatus() 
    {
        return registerStatus;
    }
    public void setRegisterTime(String registerTime) 
    {
        this.registerTime = registerTime;
    }

    public String getRegisterTime() 
    {
        return registerTime;
    }
    public void setWorkStatus(Long workStatus) 
    {
        this.workStatus = workStatus;
    }

    public Long getWorkStatus() 
    {
        return workStatus;
    }
    public void setWorkTime(String workTime) 
    {
        this.workTime = workTime;
    }

    public String getWorkTime() 
    {
        return workTime;
    }
    public void setOneCheckStatus(Long oneCheckStatus) 
    {
        this.oneCheckStatus = oneCheckStatus;
    }

    public Long getOneCheckStatus() 
    {
        return oneCheckStatus;
    }
    public void setOneCheckTime(String oneCheckTime) 
    {
        this.oneCheckTime = oneCheckTime;
    }

    public String getOneCheckTime() 
    {
        return oneCheckTime;
    }
    public void setTwoCheckStatus(Long twoCheckStatus) 
    {
        this.twoCheckStatus = twoCheckStatus;
    }

    public Long getTwoCheckStatus() 
    {
        return twoCheckStatus;
    }
    public void setTwoCheckTime(String twoCheckTime) 
    {
        this.twoCheckTime = twoCheckTime;
    }

    public String getTwoCheckTime() 
    {
        return twoCheckTime;
    }
    public void setTransferStatus(Long transferStatus) 
    {
        this.transferStatus = transferStatus;
    }

    public Long getTransferStatus() 
    {
        return transferStatus;
    }
    public void setTransferTime(String transferTime) 
    {
        this.transferTime = transferTime;
    }

    public String getTransferTime() 
    {
        return transferTime;
    }
    public void setTransferUserName(String transferUserName) 
    {
        this.transferUserName = transferUserName;
    }

    public String getTransferUserName() 
    {
        return transferUserName;
    }
    public void setReceiveStatus(Long receiveStatus) 
    {
        this.receiveStatus = receiveStatus;
    }

    public Long getReceiveStatus() 
    {
        return receiveStatus;
    }
    public void setReceiveTime(String receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public String getReceiveTime() 
    {
        return receiveTime;
    }
    public void setReceiveUserName(String receiveUserName) 
    {
        this.receiveUserName = receiveUserName;
    }

    public String getReceiveUserName() 
    {
        return receiveUserName;
    }
    public void setStampStatus(Long stampStatus) 
    {
        this.stampStatus = stampStatus;
    }

    public Long getStampStatus() 
    {
        return stampStatus;
    }
    public void setStampTime(String stampTime) 
    {
        this.stampTime = stampTime;
    }

    public String getStampTime() 
    {
        return stampTime;
    }
    public void setStampUserName(String stampUserName) 
    {
        this.stampUserName = stampUserName;
    }

    public String getStampUserName() 
    {
        return stampUserName;
    }
    public void setMarketingConfirm(Long marketingConfirm) 
    {
        this.marketingConfirm = marketingConfirm;
    }

    public Long getMarketingConfirm() 
    {
        return marketingConfirm;
    }
    public void setMarketingTime(String marketingTime) 
    {
        this.marketingTime = marketingTime;
    }

    public String getMarketingTime() 
    {
        return marketingTime;
    }
    public void setMarketingUserName(String marketingUserName) 
    {
        this.marketingUserName = marketingUserName;
    }

    public String getMarketingUserName() 
    {
        return marketingUserName;
    }
    public void setCheckStatus(Long checkStatus) 
    {
        this.checkStatus = checkStatus;
    }

    public Long getCheckStatus() 
    {
        return checkStatus;
    }
    public void setCheckTime(String checkTime) 
    {
        this.checkTime = checkTime;
    }

    public String getCheckTime() 
    {
        return checkTime;
    }
    public void setCheckUserName(String checkUserName) 
    {
        this.checkUserName = checkUserName;
    }

    public String getCheckUserName() 
    {
        return checkUserName;
    }
    public void setArchiveTime(String archiveTime) 
    {
        this.archiveTime = archiveTime;
    }

    public String getArchiveTime() 
    {
        return archiveTime;
    }
    public void setArchiveUserName(String archiveUserName) 
    {
        this.archiveUserName = archiveUserName;
    }

    public String getArchiveUserName() 
    {
        return archiveUserName;
    }
    public void setReceiveCutoffTime(String receiveCutoffTime) 
    {
        this.receiveCutoffTime = receiveCutoffTime;
    }

    public String getReceiveCutoffTime() 
    {
        return receiveCutoffTime;
    }
    public void setRectifyCutoffTime(String rectifyCutoffTime) 
    {
        this.rectifyCutoffTime = rectifyCutoffTime;
    }

    public String getRectifyCutoffTime() 
    {
        return rectifyCutoffTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("registerStatus", getRegisterStatus())
            .append("registerTime", getRegisterTime())
            .append("workStatus", getWorkStatus())
            .append("workTime", getWorkTime())
            .append("oneCheckStatus", getOneCheckStatus())
            .append("oneCheckTime", getOneCheckTime())
            .append("twoCheckStatus", getTwoCheckStatus())
            .append("twoCheckTime", getTwoCheckTime())
            .append("transferStatus", getTransferStatus())
            .append("transferTime", getTransferTime())
            .append("transferUserName", getTransferUserName())
            .append("receiveStatus", getReceiveStatus())
            .append("receiveTime", getReceiveTime())
            .append("receiveUserName", getReceiveUserName())
            .append("stampStatus", getStampStatus())
            .append("stampTime", getStampTime())
            .append("stampUserName", getStampUserName())
            .append("marketingConfirm", getMarketingConfirm())
            .append("marketingTime", getMarketingTime())
            .append("marketingUserName", getMarketingUserName())
            .append("checkStatus", getCheckStatus())
            .append("checkTime", getCheckTime())
            .append("checkUserName", getCheckUserName())
            .append("archiveTime", getArchiveTime())
            .append("archiveUserName", getArchiveUserName())
            .append("receiveCutoffTime", getReceiveCutoffTime())
            .append("rectifyCutoffTime", getRectifyCutoffTime())
            .toString();
    }
}
