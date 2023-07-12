package com.kcylog.system.service;

import com.kcylog.system.domain.SysSettlementAssociate;

import java.util.List;

/**
 * 结算单关联Service接口
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
public interface ISysSettlementAssociateService 
{
    /**
     * 查询结算单关联
     * 
     * @param associateId 结算单关联主键
     * @return 结算单关联
     */
    public SysSettlementAssociate selectSysSettlementAssociateByAssociateId(Long associateId);

    /**
     * 查询结算单关联列表
     * 
     * @param sysSettlementAssociate 结算单关联
     * @return 结算单关联集合
     */
    public List<SysSettlementAssociate> selectSysSettlementAssociateList(SysSettlementAssociate sysSettlementAssociate);

    /**
     * 新增结算单关联
     * 
     * @param sysSettlementAssociate 结算单关联
     * @return 结果
     */
    public int insertSysSettlementAssociate(SysSettlementAssociate sysSettlementAssociate);

    /**
     * 修改结算单关联
     * 
     * @param sysSettlementAssociate 结算单关联
     * @return 结果
     */
    public int updateSysSettlementAssociate(SysSettlementAssociate sysSettlementAssociate);

    /**
     * 批量删除结算单关联
     * 
     * @param associateIds 需要删除的结算单关联主键集合
     * @return 结果
     */
    public int deleteSysSettlementAssociateByAssociateIds(Long[] associateIds);

    /**
     * 删除结算单关联信息
     * 
     * @param associateId 结算单关联主键
     * @return 结果
     */
    public int deleteSysSettlementAssociateByAssociateId(Long associateId);

    public int deleteSysSettlementAssociateBySettlementId(Long settlementId);
}
