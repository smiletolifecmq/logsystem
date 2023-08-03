package com.kcylog.system.service.impl;

import java.util.List;
import com.kcylog.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysSubcontractMapper;
import com.kcylog.system.domain.SysSubcontract;
import com.kcylog.system.service.ISysSubcontractService;

/**
 * 分包Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class SysSubcontractServiceImpl implements ISysSubcontractService 
{
    @Autowired
    private SysSubcontractMapper sysSubcontractMapper;

    /**
     * 查询分包
     * 
     * @param subcontractId 分包主键
     * @return 分包
     */
    @Override
    public SysSubcontract selectSysSubcontractBySubcontractId(Long subcontractId)
    {
        return sysSubcontractMapper.selectSysSubcontractBySubcontractId(subcontractId);
    }

    /**
     * 查询分包列表
     * 
     * @param sysSubcontract 分包
     * @return 分包
     */
    @Override
    public List<SysSubcontract> selectSysSubcontractList(SysSubcontract sysSubcontract)
    {
        return sysSubcontractMapper.selectSysSubcontractList(sysSubcontract);
    }

    /**
     * 新增分包
     * 
     * @param sysSubcontract 分包
     * @return 结果
     */
    @Override
    public int insertSysSubcontract(SysSubcontract sysSubcontract)
    {
        sysSubcontract.setCreateTime(DateUtils.getNowDate());
        return sysSubcontractMapper.insertSysSubcontract(sysSubcontract);
    }

    /**
     * 修改分包
     * 
     * @param sysSubcontract 分包
     * @return 结果
     */
    @Override
    public int updateSysSubcontract(SysSubcontract sysSubcontract)
    {
        sysSubcontract.setUpdateTime(DateUtils.getNowDate());
        return sysSubcontractMapper.updateSysSubcontract(sysSubcontract);
    }

    /**
     * 批量删除分包
     * 
     * @param subcontractIds 需要删除的分包主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractBySubcontractIds(Long[] subcontractIds)
    {
        return sysSubcontractMapper.deleteSysSubcontractBySubcontractIds(subcontractIds);
    }

    /**
     * 删除分包信息
     * 
     * @param subcontractId 分包主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractBySubcontractId(Long subcontractId)
    {
        return sysSubcontractMapper.deleteSysSubcontractBySubcontractId(subcontractId);
    }
}
