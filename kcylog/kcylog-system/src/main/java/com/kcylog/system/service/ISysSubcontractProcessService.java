package com.kcylog.system.service;

import com.kcylog.system.domain.SysSubcontractProcess;

import java.util.List;

/**
 * 分包审核单流程Service接口
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public interface ISysSubcontractProcessService 
{
    /**
     * 查询分包审核单流程
     * 
     * @param subcontractProcessId 分包审核单流程主键
     * @return 分包审核单流程
     */
    public SysSubcontractProcess selectSysSubcontractProcessBySubcontractProcessId(Long subcontractProcessId);

    /**
     * 查询分包审核单流程列表
     * 
     * @param sysSubcontractProcess 分包审核单流程
     * @return 分包审核单流程集合
     */
    public List<SysSubcontractProcess> selectSysSubcontractProcessList(SysSubcontractProcess sysSubcontractProcess);

    /**
     * 新增分包审核单流程
     * 
     * @param sysSubcontractProcess 分包审核单流程
     * @return 结果
     */
    public int insertSysSubcontractProcess(SysSubcontractProcess sysSubcontractProcess);

    /**
     * 修改分包审核单流程
     * 
     * @param sysSubcontractProcess 分包审核单流程
     * @return 结果
     */
    public int updateSysSubcontractProcess(SysSubcontractProcess sysSubcontractProcess);

    /**
     * 批量删除分包审核单流程
     * 
     * @param subcontractProcessIds 需要删除的分包审核单流程主键集合
     * @return 结果
     */
    public int deleteSysSubcontractProcessBySubcontractProcessIds(Long[] subcontractProcessIds);

    /**
     * 删除分包审核单流程信息
     * 
     * @param subcontractProcessId 分包审核单流程主键
     * @return 结果
     */
    public int deleteSysSubcontractProcessBySubcontractProcessId(Long subcontractProcessId);

    public int insertSysSubcontractProcessBatch(List<SysSubcontractProcess> sysSubcontractProcess);

    public int deleteSysSubcontractProcessByReviewIds(Long[] subcontractIds);
}
