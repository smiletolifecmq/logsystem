package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysCarReviewProcessMapper;
import com.kcylog.system.domain.SysCarReviewProcess;
import com.kcylog.system.service.ISysCarReviewProcessService;

/**
 * 车辆使用审核单流程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@Service
public class SysCarReviewProcessServiceImpl implements ISysCarReviewProcessService 
{
    @Autowired
    private SysCarReviewProcessMapper sysCarReviewProcessMapper;

    /**
     * 查询车辆使用审核单流程
     * 
     * @param carProcessId 车辆使用审核单流程主键
     * @return 车辆使用审核单流程
     */
    @Override
    public SysCarReviewProcess selectSysCarReviewProcessByCarProcessId(Long carProcessId)
    {
        return sysCarReviewProcessMapper.selectSysCarReviewProcessByCarProcessId(carProcessId);
    }

    /**
     * 查询车辆使用审核单流程列表
     * 
     * @param sysCarReviewProcess 车辆使用审核单流程
     * @return 车辆使用审核单流程
     */
    @Override
    public List<SysCarReviewProcess> selectSysCarReviewProcessList(SysCarReviewProcess sysCarReviewProcess)
    {
        return sysCarReviewProcessMapper.selectSysCarReviewProcessList(sysCarReviewProcess);
    }

    /**
     * 新增车辆使用审核单流程
     * 
     * @param sysCarReviewProcess 车辆使用审核单流程
     * @return 结果
     */
    @Override
    public int insertSysCarReviewProcess(SysCarReviewProcess sysCarReviewProcess)
    {
        return sysCarReviewProcessMapper.insertSysCarReviewProcess(sysCarReviewProcess);
    }

    /**
     * 修改车辆使用审核单流程
     * 
     * @param sysCarReviewProcess 车辆使用审核单流程
     * @return 结果
     */
    @Override
    public int updateSysCarReviewProcess(SysCarReviewProcess sysCarReviewProcess)
    {
        return sysCarReviewProcessMapper.updateSysCarReviewProcess(sysCarReviewProcess);
    }

    /**
     * 批量删除车辆使用审核单流程
     * 
     * @param carProcessIds 需要删除的车辆使用审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewProcessByCarProcessIds(Long[] carProcessIds)
    {
        return sysCarReviewProcessMapper.deleteSysCarReviewProcessByCarProcessIds(carProcessIds);
    }

    /**
     * 删除车辆使用审核单流程信息
     * 
     * @param carProcessId 车辆使用审核单流程主键
     * @return 结果
     */
    @Override
    public int deleteSysCarReviewProcessByCarProcessId(Long carProcessId)
    {
        return sysCarReviewProcessMapper.deleteSysCarReviewProcessByCarProcessId(carProcessId);
    }

    @Override
    public int insertSysCarReviewBatch(List<SysCarReviewProcess> carReviewProcess)
    {
        return sysCarReviewProcessMapper.insertSysCarReviewBatch(carReviewProcess);
    }
}
