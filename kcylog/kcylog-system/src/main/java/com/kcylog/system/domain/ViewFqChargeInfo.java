package com.kcylog.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.kcylog.common.annotation.Excel;
import com.kcylog.common.core.domain.BaseEntity;

/**
 * 分包视图对象 view_fq_charge_info
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
public class ViewFqChargeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

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
