package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysReviewSettlement;
import com.kcylog.system.mapper.SysReviewSettlementMapper;
import com.kcylog.system.service.ISysReviewSettlementService;
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
public class SysReviewSettlementServiceImpl implements ISysReviewSettlementService 
{
    @Autowired
    private SysReviewSettlementMapper sysReviewSettlementMapper;

    /**
     * 查询结算单
     * 
     * @param settlementId 结算单主键
     * @return 结算单
     */
    @Override
    public SysReviewSettlement selectSysReviewSettlementBySettlementId(Long settlementId)
    {
        return sysReviewSettlementMapper.selectSysReviewSettlementBySettlementId(settlementId);
    }

    /**
     * 查询结算单列表
     * 
     * @param sysReviewSettlement 结算单
     * @return 结算单
     */
    @Override
    public List<SysReviewSettlement> selectSysReviewSettlementList(SysReviewSettlement sysReviewSettlement)
    {
        return sysReviewSettlementMapper.selectSysReviewSettlementList(sysReviewSettlement);
    }

    /**
     * 新增结算单
     * 
     * @param sysReviewSettlement 结算单
     * @return 结果
     */
    @Override
    public int insertSysReviewSettlement(SysReviewSettlement sysReviewSettlement)
    {
        sysReviewSettlement.setCreateTime(DateUtils.getNowDate());
        return sysReviewSettlementMapper.insertSysReviewSettlement(sysReviewSettlement);
    }

    /**
     * 修改结算单
     * 
     * @param sysReviewSettlement 结算单
     * @return 结果
     */
    @Override
    public int updateSysReviewSettlement(SysReviewSettlement sysReviewSettlement)
    {
        sysReviewSettlement.setUpdateTime(DateUtils.getNowDate());
        return sysReviewSettlementMapper.updateSysReviewSettlement(sysReviewSettlement);
    }

    /**
     * 批量删除结算单
     * 
     * @param settlementIds 需要删除的结算单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSettlementBySettlementIds(Long[] settlementIds)
    {
        return sysReviewSettlementMapper.deleteSysReviewSettlementBySettlementIds(settlementIds);
    }

    /**
     * 删除结算单信息
     * 
     * @param settlementId 结算单主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSettlementBySettlementId(Long settlementId)
    {
        return sysReviewSettlementMapper.deleteSysReviewSettlementBySettlementId(settlementId);
    }
}
