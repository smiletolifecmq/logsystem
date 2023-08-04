package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysSubcontractProcess;
import com.kcylog.system.mapper.SysSubcontractProcessMapper;
import com.kcylog.system.service.ISysSubcontractProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分包审核单流程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@Service
public class SysSubcontractProcessServiceImpl implements ISysSubcontractProcessService 
{
    @Autowired
    private SysSubcontractProcessMapper sysSubcontractProcessMapper;

    /**
     * 查询分包审核单流程
     * 
     * @param subcontractProcessId 分包审核单流程主键
     * @return 分包审核单流程
     */
    @Override
    public SysSubcontractProcess selectSysSubcontractProcessBySubcontractProcessId(Long subcontractProcessId)
    {
        return sysSubcontractProcessMapper.selectSysSubcontractProcessBySubcontractProcessId(subcontractProcessId);
    }

    /**
     * 查询分包审核单流程列表
     * 
     * @param sysSubcontractProcess 分包审核单流程
     * @return 分包审核单流程
     */
    @Override
    public List<SysSubcontractProcess> selectSysSubcontractProcessList(SysSubcontractProcess sysSubcontractProcess)
    {
        return sysSubcontractProcessMapper.selectSysSubcontractProcessList(sysSubcontractProcess);
    }

    /**
     * 新增分包审核单流程
     * 
     * @param sysSubcontractProcess 分包审核单流程
     * @return 结果
     */
    @Override
    public int insertSysSubcontractProcess(SysSubcontractProcess sysSubcontractProcess)
    {
        return sysSubcontractProcessMapper.insertSysSubcontractProcess(sysSubcontractProcess);
    }

    /**
     * 修改分包审核单流程
     * 
     * @param sysSubcontractProcess 分包审核单流程
     * @return 结果
     */
    @Override
    public int updateSysSubcontractProcess(SysSubcontractProcess sysSubcontractProcess)
    {
        return sysSubcontractProcessMapper.updateSysSubcontractProcess(sysSubcontractProcess);
    }

    /**
     * 批量删除分包审核单流程
     * 
     * @param subcontractProcessIds 需要删除的分包审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractProcessBySubcontractProcessIds(Long[] subcontractProcessIds)
    {
        return sysSubcontractProcessMapper.deleteSysSubcontractProcessBySubcontractProcessIds(subcontractProcessIds);
    }

    /**
     * 删除分包审核单流程信息
     * 
     * @param subcontractProcessId 分包审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysSubcontractProcessBySubcontractProcessId(Long subcontractProcessId)
    {
        return sysSubcontractProcessMapper.deleteSysSubcontractProcessBySubcontractProcessId(subcontractProcessId);
    }

    @Override
    public int insertSysSubcontractProcessBatch(List<SysSubcontractProcess> sysSubcontractProcess)
    {
        return sysSubcontractProcessMapper.insertSysSubcontractProcessBatch(sysSubcontractProcess);
    }

    @Override
    public int deleteSysSubcontractProcessByReviewIds(Long[] subcontractIds)
    {
        return sysSubcontractProcessMapper.deleteSysSubcontractProcessByReviewIds(subcontractIds);
    }

    @Override
    public int setStatusNotStartBySubcontractId(Long subcontractId)
    {
        return sysSubcontractProcessMapper.setStatusNotStartBySubcontractId(subcontractId);
    }

    @Override
    public int setStatusBySubcontractIdFirst(Long subcontractId)
    {
        return sysSubcontractProcessMapper.setStatusBySubcontractIdFirst(subcontractId);
    }

    @Override
    public int setStatusByUserIdAndSubcontractId(SysSubcontractProcess sysSubcontractProcess)
    {
        return sysSubcontractProcessMapper.setStatusByUserIdAndSubcontractId(sysSubcontractProcess);
    }

    @Override
    public int setNextStatusBySubcontractId(Long subcontractId)
    {
        return sysSubcontractProcessMapper.setNextStatusBySubcontractId(subcontractId);
    }
}
