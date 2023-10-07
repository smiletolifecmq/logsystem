package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysReviewSubSettlement;

import java.util.List;

/**
 * 结算单Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
public interface SysReviewSubSettlementMapper
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
     * 删除结算单
     * 
     * @param settlementId 结算单主键
     * @return 结果
     */
    public int deleteSysReviewSubSettlementBySettlementId(Long settlementId);

    /**
     * 批量删除结算单
     * 
     * @param settlementIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysReviewSubSettlementBySettlementIds(Long[] settlementIds);

    public int updateIsSettlementBySettlementId(Long settlementId);
}
