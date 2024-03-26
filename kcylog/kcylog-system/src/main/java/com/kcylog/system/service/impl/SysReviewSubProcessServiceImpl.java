package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysReviewSubProcess;
import com.kcylog.system.mapper.SysReviewSubProcessMapper;
import com.kcylog.system.service.ISysReviewSubProcessService;
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
public class SysReviewSubProcessServiceImpl implements ISysReviewSubProcessService
{
    @Autowired
    private SysReviewSubProcessMapper sysReviewSubProcessMapper;

    /**
     * 查询审核单流程
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 审核单流程
     */
    @Override
    public SysReviewSubProcess selectSysReviewSubProcessByReviewProcessId(Long reviewProcessId)
    {
        return sysReviewSubProcessMapper.selectSysReviewSubProcessByReviewProcessId(reviewProcessId);
    }

    /**
     * 查询审核单流程列表
     * 
     * @param sysReviewSubProcess 审核单流程
     * @return 审核单流程
     */
    @Override
    public List<SysReviewSubProcess> selectSysReviewSubProcessList(SysReviewSubProcess sysReviewSubProcess)
    {
        return sysReviewSubProcessMapper.selectSysReviewSubProcessList(sysReviewSubProcess);
    }

    /**
     * 新增审核单流程
     * 
     * @param sysReviewProcess 审核单流程
     * @return 结果
     */
    @Override
    public int insertSysReviewSubProcess(SysReviewSubProcess sysReviewSubProcess)
    {
        return sysReviewSubProcessMapper.insertSysReviewSubProcess(sysReviewSubProcess);
    }

    /**
     * 修改审核单流程
     * 
     * @param sysReviewSubProcess 审核单流程
     * @return 结果
     */
    @Override
    public int updateSysReviewSubProcess(SysReviewSubProcess sysReviewSubProcess)
    {
        return sysReviewSubProcessMapper.updateSysReviewSubProcess(sysReviewSubProcess);
    }

    /**
     * 批量删除审核单流程
     * 
     * @param reviewProcessIds 需要删除的审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubProcessByReviewProcessIds(Long[] reviewProcessIds)
    {
        return sysReviewSubProcessMapper.deleteSysReviewSubProcessByReviewProcessIds(reviewProcessIds);
    }

    /**
     * 删除审核单流程信息
     * 
     * @param reviewProcessId 审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubProcessByReviewProcessId(Long reviewProcessId)
    {
        return sysReviewSubProcessMapper.deleteSysReviewSubProcessByReviewProcessId(reviewProcessId);
    }

    /**
     * 批量新增审核单流程
     *
     * @param sysReviewSubProcess 审核单流程
     * @return 结果
     */
    @Override
    public int insertSysReviewSubProcessBatch(List<SysReviewSubProcess> sysReviewSubProcess)
    {
        return sysReviewSubProcessMapper.insertSysReviewSubProcessBatch(sysReviewSubProcess);
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
        return sysReviewSubProcessMapper.setStatusByReviewIdFirst(reviewId);
    }

    /**
     * 批量删除审核单流程
     *
     * @param reviewIds 需要删除的审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysReviewSubProcessByReviewIds(String[] reviewIds)
    {
        return sysReviewSubProcessMapper.deleteSysReviewSubProcessByReviewIds(reviewIds);
    }

    /**
     * 修改审核单流程状态
     *
     * @param sysReviewSubProcess 审核单参数
     * @return 结果
     */
    @Override
    public int setStatusByUserIdAndReviewId(SysReviewSubProcess sysReviewSubProcess)
    {
        return sysReviewSubProcessMapper.setStatusByUserIdAndReviewId(sysReviewSubProcess);
    }

    @Override
    public int setNextStatusByReviewId(Long reviewId)
    {
        return sysReviewSubProcessMapper.setNextStatusByReviewId(reviewId);
    }

    @Override
    public int setStatusNotStartByReviewId(Long reviewId)
    {
        return sysReviewSubProcessMapper.setStatusNotStartByReviewId(reviewId);
    }

    @Override
    public int updateStatusByUserIdReviewId(Long reviewId)
    {
        return sysReviewSubProcessMapper.updateStatusByUserIdReviewId(reviewId);
    }

    @Override
    public int reSetStatusByReviewId(Long reviewId)
    {
        return sysReviewSubProcessMapper.reSetStatusByReviewId(reviewId);
    }
}
