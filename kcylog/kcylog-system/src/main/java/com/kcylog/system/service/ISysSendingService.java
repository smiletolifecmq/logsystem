package com.kcylog.system.service;

import com.kcylog.system.domain.SysSending;

import java.util.List;

/**
 * 送件Service接口
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
public interface ISysSendingService 
{
    /**
     * 查询送件
     * 
     * @param id 送件主键
     * @return 送件
     */
    public SysSending selectSysSendingById(Long id);

    /**
     * 查询送件列表
     * 
     * @param sysSending 送件
     * @return 送件集合
     */
    public List<SysSending> selectSysSendingList(SysSending sysSending);

    /**
     * 新增送件
     * 
     * @param sysSending 送件
     * @return 结果
     */
    public int insertSysSending(SysSending sysSending);

    /**
     * 修改送件
     * 
     * @param sysSending 送件
     * @return 结果
     */
    public int updateSysSending(SysSending sysSending);

    /**
     * 批量删除送件
     * 
     * @param ids 需要删除的送件主键集合
     * @return 结果
     */
    public int deleteSysSendingByIds(Long[] ids);

    /**
     * 删除送件信息
     * 
     * @param id 送件主键
     * @return 结果
     */
    public int deleteSysSendingById(Long id);

    public List<SysSending> selectSysSendingListForProjectIds(List<Long> projectIds);
}
