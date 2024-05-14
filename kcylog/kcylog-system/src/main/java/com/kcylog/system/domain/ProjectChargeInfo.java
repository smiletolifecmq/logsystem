package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 分包明细表对象 project_charge_info
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
public class ProjectChargeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 项目ID */
    @Excel(name = "项目ID")
    private String projectId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectCode;

    /** 分包金额（分） */
    @Excel(name = "分包金额", readConverterExp = "分=")
    private Long settleMoney;

    /** 分包合同 */
    @Excel(name = "分包合同")
    private String firmName;

    /** 中签单位 */
    @Excel(name = "中签单位")
    private String subcontractNo;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String projectTypeName;

    private Date fbTime;

    private String projectStartAlias;

    private String projectEndAlias;

    public Date getFbTime() {
        return fbTime;
    }

    public void setFbTime(Date fbTime) {
        this.fbTime = fbTime;
    }

    public String getProjectStartAlias() {
        return projectStartAlias;
    }

    public void setProjectStartAlias(String projectStartAlias) {
        this.projectStartAlias = projectStartAlias;
    }

    public String getProjectEndAlias() {
        return projectEndAlias;
    }

    public void setProjectEndAlias(String projectEndAlias) {
        this.projectEndAlias = projectEndAlias;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProjectId(String projectId) 
    {
        this.projectId = projectId;
    }

    public String getProjectId() 
    {
        return projectId;
    }
    public void setProjectCode(String projectCode) 
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode() 
    {
        return projectCode;
    }
    public void setSettleMoney(Long settleMoney) 
    {
        this.settleMoney = settleMoney;
    }

    public Long getSettleMoney() 
    {
        return settleMoney;
    }
    public void setFirmName(String firmName) 
    {
        this.firmName = firmName;
    }

    public String getFirmName() 
    {
        return firmName;
    }
    public void setSubcontractNo(String subcontractNo) 
    {
        this.subcontractNo = subcontractNo;
    }

    public String getSubcontractNo() 
    {
        return subcontractNo;
    }
    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }
    public void setProjectTypeName(String projectTypeName) 
    {
        this.projectTypeName = projectTypeName;
    }

    public String getProjectTypeName() 
    {
        return projectTypeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectId", getProjectId())
            .append("projectCode", getProjectCode())
            .append("settleMoney", getSettleMoney())
            .append("firmName", getFirmName())
            .append("subcontractNo", getSubcontractNo())
            .append("createTime", getCreateTime())
            .append("projectName", getProjectName())
            .append("projectTypeName", getProjectTypeName())
            .toString();
    }
}
