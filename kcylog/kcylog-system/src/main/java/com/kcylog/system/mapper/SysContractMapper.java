package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysContract;

import java.util.List;

/**
 * 合同管理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-26
 */
public interface SysContractMapper 
{
    /**
     * 查询合同管理
     * 
     * @param conId 合同管理主键
     * @return 合同管理
     */
    public SysContract selectSysContractByConId(Long conId);

    /**
     * 查询合同管理列表
     * 
     * @param sysContract 合同管理
     * @return 合同管理集合
     */
    public List<SysContract> selectSysContractList(SysContract sysContract);

    /**
     * 新增合同管理
     * 
     * @param sysContract 合同管理
     * @return 结果
     */
    public int insertSysContract(SysContract sysContract);

    /**
     * 修改合同管理
     * 
     * @param sysContract 合同管理
     * @return 结果
     */
    public int updateSysContract(SysContract sysContract);

    /**
     * 删除合同管理
     * 
     * @param conId 合同管理主键
     * @return 结果
     */
    public int deleteSysContractByConId(Long conId);

    /**
     * 批量删除合同管理
     * 
     * @param conIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysContractByConIds(Long[] conIds);
}
