package com.kcylog.system.domain;

import com.kcylog.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 流程配置过程对象 sys_subcontract_process_config_info
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public class SysSubcontractProcessConfigInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程配置过程ID */
    private Long infoId;

    /** 流程配置ID */
    @Excel(name = "流程配置ID")
    private Long processConfigId;

    /** 审核节点 */
    @Excel(name = "审核节点")
    private String node;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long userId;

    private SysUser user;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public void setInfoId(Long infoId) 
    {
        this.infoId = infoId;
    }

    public Long getInfoId() 
    {
        return infoId;
    }
    public void setProcessConfigId(Long processConfigId) 
    {
        this.processConfigId = processConfigId;
    }

    public Long getProcessConfigId() 
    {
        return processConfigId;
    }
    public void setNode(String node) 
    {
        this.node = node;
    }

    public String getNode() 
    {
        return node;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("infoId", getInfoId())
            .append("processConfigId", getProcessConfigId())
            .append("node", getNode())
            .append("userId", getUserId())
            .toString();
    }
}
