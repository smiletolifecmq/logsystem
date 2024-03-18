package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysSettlementFile;

import java.util.List;

/**
 * 结算办结导出文件存储Mapper接口
 * 
 * @author ruoyi
 * @date 2024-03-18
 */
public interface SysSettlementFileMapper 
{
    /**
     * 查询结算办结导出文件存储
     * 
     * @param fileId 结算办结导出文件存储主键
     * @return 结算办结导出文件存储
     */
    public SysSettlementFile selectSysSettlementFileByFileId(Long fileId);

    /**
     * 查询结算办结导出文件存储列表
     * 
     * @param sysSettlementFile 结算办结导出文件存储
     * @return 结算办结导出文件存储集合
     */
    public List<SysSettlementFile> selectSysSettlementFileList(SysSettlementFile sysSettlementFile);

    /**
     * 新增结算办结导出文件存储
     * 
     * @param sysSettlementFile 结算办结导出文件存储
     * @return 结果
     */
    public int insertSysSettlementFile(SysSettlementFile sysSettlementFile);

    /**
     * 修改结算办结导出文件存储
     * 
     * @param sysSettlementFile 结算办结导出文件存储
     * @return 结果
     */
    public int updateSysSettlementFile(SysSettlementFile sysSettlementFile);

    /**
     * 删除结算办结导出文件存储
     * 
     * @param fileId 结算办结导出文件存储主键
     * @return 结果
     */
    public int deleteSysSettlementFileByFileId(Long fileId);

    /**
     * 批量删除结算办结导出文件存储
     * 
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSettlementFileByFileIds(Long[] fileIds);

    public List<SysSettlementFile> selectSysSettlementFileBySettlementId(Long settlementId);

    public int deleteSysSettlementFileBySettlementId(Long settlementId);
}
