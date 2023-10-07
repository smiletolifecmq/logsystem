package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysReviewSubSettlement;
import com.kcylog.system.mapper.SysReviewSubSettlementMapper;
import com.kcylog.system.service.ISysReviewSubSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结算单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
@Service
public class SysReviewSubSettlementServiceImpl implements ISysReviewSubSettlementService
{
    @Autowired
    private SysReviewSubSettlementMapper sysReviewSubSettlementMapper;

    /**
     * 查询结算单
     * 
     * @param settlementId 结算单主键
     * @return 结算单
     */
    @Override
    public SysReviewSubSettlement selectSysReviewSubSettlementBySettlementId(Long settlementId)
    {
        return sysReviewSubSettlementMapper.selectSysReviewSubSettlementBySettlementId(settlementId);
    }

    /**
     * 查询结算单列表
     * 
     * @param sysReviewSubSettlement 结算单
     * @return 结算单
     */
    @Override
    public List<SysReviewSubSettlement> selectSysReviewSubSettlementList(SysReviewSubSettlement sysReviewSubSettlement)
    {
        return sysReviewSubSettlementMapper.selectSysReviewSubSettlementList(sysReviewSubSettlement);
    }

    /**
     * 新增结算单
     * 
     * @param sysReviewSubSettlement 结算单
     * @return 结果
     */
    @Override
    public int insertSysReviewSubSettlement(SysReviewSubSettlement sysReviewSubSettlement)
    {
        sysReviewSubSettlement.setCreateTime(DateUtils.getNowDate());
        return sysReviewSubSettlementMapper.insertSysReviewSubSettlement(sysReviewSubSettlement);
    }

    /**
     * 修改结算单
     * 
     * @param sysReviewSubSettlement 结算单
     * @return 结果
     */
    @Override
    public int updateSysReviewSubSettlement(SysReviewSubSettlement sysReviewSubSettlement)
    {
        sysReviewSubSettlement.setUpdateTime(DateUtils.getNowDate());
        return sysReviewSubSettlementMapper.updateSysReviewSubSettlement(sysReviewSubSettlement);
    }

    /**
     * 批量删除结算单
     * 
     * @param settlementIds 需要删除的结算单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubSettlementBySettlementIds(Long[] settlementIds)
    {
        return sysReviewSubSettlementMapper.deleteSysReviewSubSettlementBySettlementIds(settlementIds);
    }

    /**
     * 删除结算单信息
     * 
     * @param settlementId 结算单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubSettlementBySettlementId(Long settlementId)
    {
        return sysReviewSubSettlementMapper.deleteSysReviewSubSettlementBySettlementId(settlementId);
    }

    @Override
    public int updateIsSettlementBySettlementId(Long settlementId)
    {
        return sysReviewSubSettlementMapper.updateIsSettlementBySettlementId(settlementId);
    }
}
