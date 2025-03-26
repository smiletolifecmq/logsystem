package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysContractMapper;
import com.kcylog.system.domain.SysContract;
import com.kcylog.system.service.ISysContractService;

/**
 * 合同管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-26
 */
@Service
public class SysContractServiceImpl implements ISysContractService 
{
    @Autowired
    private SysContractMapper sysContractMapper;

    /**
     * 查询合同管理
     * 
     * @param conId 合同管理主键
     * @return 合同管理
     */
    @Override
    public SysContract selectSysContractByConId(Long conId)
    {
        return sysContractMapper.selectSysContractByConId(conId);
    }

    /**
     * 查询合同管理列表
     * 
     * @param sysContract 合同管理
     * @return 合同管理
     */
    @Override
    public List<SysContract> selectSysContractList(SysContract sysContract)
    {
        return sysContractMapper.selectSysContractList(sysContract);
    }

    /**
     * 新增合同管理
     * 
     * @param sysContract 合同管理
     * @return 结果
     */
    @Override
    public int insertSysContract(SysContract sysContract)
    {
        return sysContractMapper.insertSysContract(sysContract);
    }

    /**
     * 修改合同管理
     * 
     * @param sysContract 合同管理
     * @return 结果
     */
    @Override
    public int updateSysContract(SysContract sysContract)
    {
        return sysContractMapper.updateSysContract(sysContract);
    }

    /**
     * 批量删除合同管理
     * 
     * @param conIds 需要删除的合同管理主键
     * @return 结果
     */
    @Override
    public int deleteSysContractByConIds(Long[] conIds)
    {
        return sysContractMapper.deleteSysContractByConIds(conIds);
    }

    /**
     * 删除合同管理信息
     * 
     * @param conId 合同管理主键
     * @return 结果
     */
    @Override
    public int deleteSysContractByConId(Long conId)
    {
        return sysContractMapper.deleteSysContractByConId(conId);
    }
}
