package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysSubcontract;

import java.util.List;

/**
 * 分包Mapper接口
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
public interface SysSubcontractMapper 
{
    /**
     * 查询分包
     * 
     * @param subcontractId 分包主键
     * @return 分包
     */
    public SysSubcontract selectSysSubcontractBySubcontractId(Long subcontractId);

    /**
     * 查询分包列表
     * 
     * @param sysSubcontract 分包
     * @return 分包集合
     */
    public List<SysSubcontract> selectSysSubcontractList(SysSubcontract sysSubcontract);

    /**
     * 新增分包
     * 
     * @param sysSubcontract 分包
     * @return 结果
     */
    public int insertSysSubcontract(SysSubcontract sysSubcontract);

    /**
     * 修改分包
     * 
     * @param sysSubcontract 分包
     * @return 结果
     */
    public int updateSysSubcontract(SysSubcontract sysSubcontract);

    /**
     * 删除分包
     * 
     * @param subcontractId 分包主键
     * @return 结果
     */
    public int deleteSysSubcontractBySubcontractId(Long subcontractId);

    /**
     * 批量删除分包
     * 
     * @param subcontractIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSubcontractBySubcontractIds(Long[] subcontractIds);

    public int setSysSubcontractStatusBySubcontractId(SysSubcontract sysSubcontract);

    public List<SysSubcontract> selectSysUpcomingSubcontractList(SysSubcontract sysSubcontract);

    public int setSysReviewStatusBySubcontractId(SysSubcontract sysSubcontract);

    public List<SysSubcontract> selectDoneReviewList(SysSubcontract sysSubcontract);

    public List<SysSubcontract> selectCompletedReviewList(SysSubcontract sysSubcontract);

    public int setSubcontractIsPrintBySubcontractId(Long subcontractId);
}
