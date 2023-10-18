package com.kcylog.system.service;

import com.kcylog.system.domain.SysCarReviewProcess;

import java.util.List;

/**
 * 车辆使用审核单流程Service接口
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public interface ISysCarReviewProcessService 
{
    /**
     * 查询车辆使用审核单流程
     * 
     * @param carProcessId 车辆使用审核单流程主键
     * @return 车辆使用审核单流程
     */
    public SysCarReviewProcess selectSysCarReviewProcessByCarProcessId(Long carProcessId);

    /**
     * 查询车辆使用审核单流程列表
     * 
     * @param sysCarReviewProcess 车辆使用审核单流程
     * @return 车辆使用审核单流程集合
     */
    public List<SysCarReviewProcess> selectSysCarReviewProcessList(SysCarReviewProcess sysCarReviewProcess);

    /**
     * 新增车辆使用审核单流程
     * 
     * @param sysCarReviewProcess 车辆使用审核单流程
     * @return 结果
     */
    public int insertSysCarReviewProcess(SysCarReviewProcess sysCarReviewProcess);

    /**
     * 修改车辆使用审核单流程
     * 
     * @param sysCarReviewProcess 车辆使用审核单流程
     * @return 结果
     */
    public int updateSysCarReviewProcess(SysCarReviewProcess sysCarReviewProcess);

    /**
     * 批量删除车辆使用审核单流程
     * 
     * @param carProcessIds 需要删除的车辆使用审核单流程主键集合
     * @return 结果
     */
    public int deleteSysCarReviewProcessByCarProcessIds(Long[] carProcessIds);

    /**
     * 删除车辆使用审核单流程信息
     * 
     * @param carProcessId 车辆使用审核单流程主键
     * @return 结果
     */
    public int deleteSysCarReviewProcessByCarProcessId(Long carProcessId);

    public int insertSysCarReviewBatch(List<SysCarReviewProcess> carReviewProcess);

    public int deleteSysCarReviewProcessByCarReviewIds(Long[] carReviewIds);

    public int setStatusNotStartByCarReviewId(Long carReviewId);

    public int setStatusByReviewIdFirst(Long carReviewId);

    public int setStatusByUserIdAndCarReviewId(SysCarReviewProcess sysCarReviewProcess);

    public int setNextStatusByReviewId(Long carReviewId);
}
