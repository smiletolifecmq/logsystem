package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysCarReviewConfigInfo;

import java.util.List;

/**
 * 车辆使用流程配置过程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
public interface SysCarReviewConfigInfoMapper 
{
    /**
     * 查询车辆使用流程配置过程
     * 
     * @param infoId 车辆使用流程配置过程主键
     * @return 车辆使用流程配置过程
     */
    public SysCarReviewConfigInfo selectSysCarReviewConfigInfoByInfoId(Long infoId);

    /**
     * 查询车辆使用流程配置过程列表
     * 
     * @param sysCarReviewConfigInfo 车辆使用流程配置过程
     * @return 车辆使用流程配置过程集合
     */
    public List<SysCarReviewConfigInfo> selectSysCarReviewConfigInfoList(SysCarReviewConfigInfo sysCarReviewConfigInfo);

    /**
     * 新增车辆使用流程配置过程
     * 
     * @param sysCarReviewConfigInfo 车辆使用流程配置过程
     * @return 结果
     */
    public int insertSysCarReviewConfigInfo(SysCarReviewConfigInfo sysCarReviewConfigInfo);

    /**
     * 修改车辆使用流程配置过程
     * 
     * @param sysCarReviewConfigInfo 车辆使用流程配置过程
     * @return 结果
     */
    public int updateSysCarReviewConfigInfo(SysCarReviewConfigInfo sysCarReviewConfigInfo);

    /**
     * 删除车辆使用流程配置过程
     * 
     * @param infoId 车辆使用流程配置过程主键
     * @return 结果
     */
    public int deleteSysCarReviewConfigInfoByInfoId(Long infoId);

    /**
     * 批量删除车辆使用流程配置过程
     * 
     * @param infoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCarReviewConfigInfoByInfoIds(Long[] infoIds);

    public List<SysCarReviewConfigInfo> selectCarReviewConfigInfoListByDeptId(Long deptId);
}
