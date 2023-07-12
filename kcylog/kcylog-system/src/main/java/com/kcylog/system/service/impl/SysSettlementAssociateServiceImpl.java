package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysSettlementAssociateMapper;
import com.kcylog.system.domain.SysSettlementAssociate;
import com.kcylog.system.service.ISysSettlementAssociateService;

/**
 * 结算单关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-12
 */
@Service
public class SysSettlementAssociateServiceImpl implements ISysSettlementAssociateService 
{
    @Autowired
    private SysSettlementAssociateMapper sysSettlementAssociateMapper;

    /**
     * 查询结算单关联
     * 
     * @param associateId 结算单关联主键
     * @return 结算单关联
     */
    @Override
    public SysSettlementAssociate selectSysSettlementAssociateByAssociateId(Long associateId)
    {
        return sysSettlementAssociateMapper.selectSysSettlementAssociateByAssociateId(associateId);
    }

    /**
     * 查询结算单关联列表
     * 
     * @param sysSettlementAssociate 结算单关联
     * @return 结算单关联
     */
    @Override
    public List<SysSettlementAssociate> selectSysSettlementAssociateList(SysSettlementAssociate sysSettlementAssociate)
    {
        return sysSettlementAssociateMapper.selectSysSettlementAssociateList(sysSettlementAssociate);
    }

    /**
     * 新增结算单关联
     * 
     * @param sysSettlementAssociate 结算单关联
     * @return 结果
     */
    @Override
    public int insertSysSettlementAssociate(SysSettlementAssociate sysSettlementAssociate)
    {
        return sysSettlementAssociateMapper.insertSysSettlementAssociate(sysSettlementAssociate);
    }

    /**
     * 修改结算单关联
     * 
     * @param sysSettlementAssociate 结算单关联
     * @return 结果
     */
    @Override
    public int updateSysSettlementAssociate(SysSettlementAssociate sysSettlementAssociate)
    {
        return sysSettlementAssociateMapper.updateSysSettlementAssociate(sysSettlementAssociate);
    }

    /**
     * 批量删除结算单关联
     * 
     * @param associateIds 需要删除的结算单关联主键
     * @return 结果
     */
    @Override
    public int deleteSysSettlementAssociateByAssociateIds(Long[] associateIds)
    {
        return sysSettlementAssociateMapper.deleteSysSettlementAssociateByAssociateIds(associateIds);
    }

    /**
     * 删除结算单关联信息
     * 
     * @param associateId 结算单关联主键
     * @return 结果
     */
    @Override
    public int deleteSysSettlementAssociateByAssociateId(Long associateId)
    {
        return sysSettlementAssociateMapper.deleteSysSettlementAssociateByAssociateId(associateId);
    }
}
