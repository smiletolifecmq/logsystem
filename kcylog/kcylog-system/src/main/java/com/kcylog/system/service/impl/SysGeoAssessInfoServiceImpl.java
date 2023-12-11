package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysGeoAssessInfo;
import com.kcylog.system.mapper.SysGeoAssessInfoMapper;
import com.kcylog.system.service.ISysGeoAssessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评定详情表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@Service
public class SysGeoAssessInfoServiceImpl implements ISysGeoAssessInfoService 
{
    @Autowired
    private SysGeoAssessInfoMapper sysGeoAssessInfoMapper;

    /**
     * 查询评定详情表
     * 
     * @param assessInfoId 评定详情表主键
     * @return 评定详情表
     */
    @Override
    public SysGeoAssessInfo selectSysGeoAssessInfoByAssessInfoId(Long assessInfoId)
    {
        return sysGeoAssessInfoMapper.selectSysGeoAssessInfoByAssessInfoId(assessInfoId);
    }

    /**
     * 查询评定详情表列表
     * 
     * @param sysGeoAssessInfo 评定详情表
     * @return 评定详情表
     */
    @Override
    public List<SysGeoAssessInfo> selectSysGeoAssessInfoList(SysGeoAssessInfo sysGeoAssessInfo)
    {
        return sysGeoAssessInfoMapper.selectSysGeoAssessInfoList(sysGeoAssessInfo);
    }

    /**
     * 新增评定详情表
     * 
     * @param sysGeoAssessInfo 评定详情表
     * @return 结果
     */
    @Override
    public int insertSysGeoAssessInfo(SysGeoAssessInfo sysGeoAssessInfo)
    {
        return sysGeoAssessInfoMapper.insertSysGeoAssessInfo(sysGeoAssessInfo);
    }

    /**
     * 修改评定详情表
     * 
     * @param sysGeoAssessInfo 评定详情表
     * @return 结果
     */
    @Override
    public int updateSysGeoAssessInfo(SysGeoAssessInfo sysGeoAssessInfo)
    {
        return sysGeoAssessInfoMapper.updateSysGeoAssessInfo(sysGeoAssessInfo);
    }

    /**
     * 批量删除评定详情表
     * 
     * @param assessInfoIds 需要删除的评定详情表主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoAssessInfoByAssessInfoIds(Long[] assessInfoIds)
    {
        return sysGeoAssessInfoMapper.deleteSysGeoAssessInfoByAssessInfoIds(assessInfoIds);
    }

    /**
     * 删除评定详情表信息
     * 
     * @param assessInfoId 评定详情表主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoAssessInfoByAssessInfoId(Long assessInfoId)
    {
        return sysGeoAssessInfoMapper.deleteSysGeoAssessInfoByAssessInfoId(assessInfoId);
    }

    @Override
    public int deleteSysGeoAssessInfoByAssessIds(Long[] assessIds)
    {
        return sysGeoAssessInfoMapper.deleteSysGeoAssessInfoByAssessIds(assessIds);
    }
}
