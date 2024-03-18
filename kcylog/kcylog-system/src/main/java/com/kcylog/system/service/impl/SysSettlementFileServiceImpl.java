package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysSettlementFile;
import com.kcylog.system.mapper.SysSettlementFileMapper;
import com.kcylog.system.service.ISysSettlementFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 结算办结导出文件存储Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-03-18
 */
@Service
public class SysSettlementFileServiceImpl implements ISysSettlementFileService 
{
    @Autowired
    private SysSettlementFileMapper sysSettlementFileMapper;

    /**
     * 查询结算办结导出文件存储
     * 
     * @param fileId 结算办结导出文件存储主键
     * @return 结算办结导出文件存储
     */
    @Override
    public SysSettlementFile selectSysSettlementFileByFileId(Long fileId)
    {
        return sysSettlementFileMapper.selectSysSettlementFileByFileId(fileId);
    }

    /**
     * 查询结算办结导出文件存储列表
     * 
     * @param sysSettlementFile 结算办结导出文件存储
     * @return 结算办结导出文件存储
     */
    @Override
    public List<SysSettlementFile> selectSysSettlementFileList(SysSettlementFile sysSettlementFile)
    {
        return sysSettlementFileMapper.selectSysSettlementFileList(sysSettlementFile);
    }

    /**
     * 新增结算办结导出文件存储
     * 
     * @param sysSettlementFile 结算办结导出文件存储
     * @return 结果
     */
    @Override
    public int insertSysSettlementFile(SysSettlementFile sysSettlementFile)
    {
        return sysSettlementFileMapper.insertSysSettlementFile(sysSettlementFile);
    }

    /**
     * 修改结算办结导出文件存储
     * 
     * @param sysSettlementFile 结算办结导出文件存储
     * @return 结果
     */
    @Override
    public int updateSysSettlementFile(SysSettlementFile sysSettlementFile)
    {
        return sysSettlementFileMapper.updateSysSettlementFile(sysSettlementFile);
    }

    /**
     * 批量删除结算办结导出文件存储
     * 
     * @param fileIds 需要删除的结算办结导出文件存储主键
     * @return 结果
     */
    @Override
    public int deleteSysSettlementFileByFileIds(Long[] fileIds)
    {
        return sysSettlementFileMapper.deleteSysSettlementFileByFileIds(fileIds);
    }

    /**
     * 删除结算办结导出文件存储信息
     * 
     * @param fileId 结算办结导出文件存储主键
     * @return 结果
     */
    @Override
    public int deleteSysSettlementFileByFileId(Long fileId)
    {
        return sysSettlementFileMapper.deleteSysSettlementFileByFileId(fileId);
    }

    @Override
    public List<SysSettlementFile> selectSysSettlementFileBySettlementId(Long settlementId)
    {
        return sysSettlementFileMapper.selectSysSettlementFileBySettlementId(settlementId);
    }

    @Override
    public int deleteSysSettlementFileBySettlementId(Long settlementId)
    {
        return sysSettlementFileMapper.deleteSysSettlementFileBySettlementId(settlementId);
    }
}
