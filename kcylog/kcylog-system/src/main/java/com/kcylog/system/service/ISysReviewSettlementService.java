package com.kcylog.system.service;

import com.kcylog.system.domain.SysReviewSettlement;

import java.util.List;

/**
 * 结算单Service接口
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
public interface ISysReviewSettlementService 
{
    /**
     * 查询结算单
     * 
     * @param settlementId 结算单主键
     * @return 结算单
     */
    public SysReviewSettlement selectSysReviewSettlementBySettlementId(Long settlementId);

    /**
     * 查询结算单列表
     * 
     * @param sysReviewSettlement 结算单
     * @return 结算单集合
     */
    public List<SysReviewSettlement> selectSysReviewSettlementList(SysReviewSettlement sysReviewSettlement);

    /**
     * 新增结算单
     * 
     * @param sysReviewSettlement 结算单
     * @return 结果
     */
    public int insertSysReviewSettlement(SysReviewSettlement sysReviewSettlement);

    /**
     * 修改结算单
     * 
     * @param sysReviewSettlement 结算单
     * @return 结果
     */
    public int updateSysReviewSettlement(SysReviewSettlement sysReviewSettlement);

    /**
     * 批量删除结算单
     * 
     * @param settlementIds 需要删除的结算单主键集合
     * @return 结果
     */
    public int deleteSysReviewSettlementBySettlementIds(Long[] settlementIds);

    /**
     * 删除结算单信息
     * 
     * @param settlementId 结算单主键
     * @return 结果
     */
    public int deleteSysReviewSettlementBySettlementId(Long settlementId);

    public int updateIsSettlementBySettlementId(Long settlementId);
}
