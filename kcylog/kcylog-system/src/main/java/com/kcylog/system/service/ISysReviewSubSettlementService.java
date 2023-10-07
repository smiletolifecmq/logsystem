package com.kcylog.system.service;

import com.kcylog.system.domain.SysReviewSubSettlement;

import java.util.List;

/**
 * 结算单Service接口
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
public interface ISysReviewSubSettlementService
{
    /**
     * 查询结算单
     * 
     * @param settlementId 结算单主键
     * @return 结算单
     */
    public SysReviewSubSettlement selectSysReviewSubSettlementBySettlementId(Long settlementId);

    /**
     * 查询结算单列表
     * 
     * @param sysReviewSubSettlement 结算单
     * @return 结算单集合
     */
    public List<SysReviewSubSettlement> selectSysReviewSubSettlementList(SysReviewSubSettlement sysReviewSubSettlement);

    /**
     * 新增结算单
     * 
     * @param sysReviewSubSettlement 结算单
     * @return 结果
     */
    public int insertSysReviewSubSettlement(SysReviewSubSettlement sysReviewSubSettlement);

    /**
     * 修改结算单
     * 
     * @param sysReviewSubSettlement 结算单
     * @return 结果
     */
    public int updateSysReviewSubSettlement(SysReviewSubSettlement sysReviewSubSettlement);

    /**
     * 批量删除结算单
     * 
     * @param settlementIds 需要删除的结算单主键集合
     * @return 结果
     */
    public int deleteSysReviewSubSettlementBySettlementIds(Long[] settlementIds);

    /**
     * 删除结算单信息
     * 
     * @param settlementId 结算单主键
     * @return 结果
     */
    public int deleteSysReviewSubSettlementBySettlementId(Long settlementId);

    public int updateIsSettlementBySettlementId(Long settlementId);
}
