package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysReviewProcess;
import com.kcylog.system.mapper.SysReviewProcessMapper;
import com.kcylog.system.service.ISysReviewProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 审核单流程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-12
 */
@Service
public class SysReviewProcessServiceImpl implements ISysReviewProcessService 
{
    @Autowired
    private SysReviewProcessMapper sysReviewProcessMapper;

    /**
     * 查询审核单流程
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 审核单流程
     */
    @Override
    public SysReviewProcess selectSysReviewProcessByReviewProcessId(Long reviewProcessId)
    {
        return sysReviewProcessMapper.selectSysReviewProcessByReviewProcessId(reviewProcessId);
    }

    /**
     * 查询审核单流程列表
     * 
     * @param sysReviewProcess 审核单流程
     * @return 审核单流程
     */
    @Override
    public List<SysReviewProcess> selectSysReviewProcessList(SysReviewProcess sysReviewProcess)
    {
        return sysReviewProcessMapper.selectSysReviewProcessList(sysReviewProcess);
    }

    /**
     * 新增审核单流程
     * 
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    @Override
    public int insertSysReviewProcess(SysReviewProcess sysReviewProcess)
    {
        return sysReviewProcessMapper.insertSysReviewProcess(sysReviewProcess);
    }

    /**
     * 修改审核单流程
     * 
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    @Override
    public int updateSysReviewProcess(SysReviewProcess sysReviewProcess)
    {
        return sysReviewProcessMapper.updateSysReviewProcess(sysReviewProcess);
    }

    /**
     * 批量删除审核单流程
     * 
     * @param reviewProcessIds 需要删除的审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewProcessByReviewProcessIds(Long[] reviewProcessIds)
    {
        return sysReviewProcessMapper.deleteSysReviewProcessByReviewProcessIds(reviewProcessIds);
    }

    /**
     * 删除审核单流程信息
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewProcessByReviewProcessId(Long reviewProcessId)
    {
        return sysReviewProcessMapper.deleteSysReviewProcessByReviewProcessId(reviewProcessId);
    }

    /**
     * 批量新增审核单流程
     *
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    @Override
    public int insertSysReviewProcessBatch(List<SysReviewProcess> sysReviewProcess)
    {
        return sysReviewProcessMapper.insertSysReviewProcessBatch(sysReviewProcess);
    }

    /**
     * 修改审核单流程状态
     *
     * @param reviewId 审核单ID
     * @return 结果
     */
    @Override
    public int setStatusByReviewIdFirst(Long reviewId)
    {
        return sysReviewProcessMapper.setStatusByReviewIdFirst(reviewId);
    }

    /**
     * 批量删除审核单流程
     *
     * @param reviewIds 需要删除的审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewProcessByReviewIds(String[] reviewIds)
    {
        return sysReviewProcessMapper.deleteSysReviewProcessByReviewIds(reviewIds);
    }

    /**
     * 修改审核单流程状态
     *
     * @param sysReviewProcess 审核单参数
     * @return 结果
     */
    @Override
    public int setStatusByUserIdAndReviewId(SysReviewProcess sysReviewProcess)
    {
        return sysReviewProcessMapper.setStatusByUserIdAndReviewId(sysReviewProcess);
    }

    @Override
    public int setNextStatusByReviewId(Long reviewId)
    {
        return sysReviewProcessMapper.setNextStatusByReviewId(reviewId);
    }

    @Override
    public int setStatusNotStartByReviewId(Long reviewId)
    {
        return sysReviewProcessMapper.setStatusNotStartByReviewId(reviewId);
    }
}
