package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysSending;
import com.kcylog.system.mapper.SysSendingMapper;
import com.kcylog.system.service.ISysSendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 送件Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
@Service
public class SysSendingServiceImpl implements ISysSendingService 
{
    @Autowired
    private SysSendingMapper sysSendingMapper;

    /**
     * 查询送件
     * 
     * @param id 送件主键
     * @return 送件
     */
    @Override
    public SysSending selectSysSendingById(Long id)
    {
        return sysSendingMapper.selectSysSendingById(id);
    }

    /**
     * 查询送件列表
     * 
     * @param sysSending 送件
     * @return 送件
     */
    @Override
    public List<SysSending> selectSysSendingList(SysSending sysSending)
    {
        return sysSendingMapper.selectSysSendingList(sysSending);
    }

    /**
     * 新增送件
     * 
     * @param sysSending 送件
     * @return 结果
     */
    @Override
    public int insertSysSending(SysSending sysSending)
    {
        return sysSendingMapper.insertSysSending(sysSending);
    }

    /**
     * 修改送件
     * 
     * @param sysSending 送件
     * @return 结果
     */
    @Override
    public int updateSysSending(SysSending sysSending)
    {
        return sysSendingMapper.updateSysSending(sysSending);
    }

    /**
     * 批量删除送件
     * 
     * @param ids 需要删除的送件主键
     * @return 结果
     */
    @Override
    public int deleteSysSendingByIds(Long[] ids)
    {
        return sysSendingMapper.deleteSysSendingByIds(ids);
    }

    /**
     * 删除送件信息
     * 
     * @param id 送件主键
     * @return 结果
     */
    @Override
    public int deleteSysSendingById(Long id)
    {
        return sysSendingMapper.deleteSysSendingById(id);
    }

    @Override
    public List<SysSending> selectSysSendingListForProjectIds(List<Long> projectIds)
    {
        return sysSendingMapper.selectSysSendingListForProjectIds(projectIds);
    }
}
