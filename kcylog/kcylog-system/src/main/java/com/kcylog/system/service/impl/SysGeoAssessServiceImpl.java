package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysGeoAssess;
import com.kcylog.system.mapper.SysGeoAssessMapper;
import com.kcylog.system.service.ISysGeoAssessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评定表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
@Service
public class SysGeoAssessServiceImpl implements ISysGeoAssessService 
{
    @Autowired
    private SysGeoAssessMapper sysGeoAssessMapper;

    /**
     * 查询评定表
     * 
     * @param assessId 评定表主键
     * @return 评定表
     */
    @Override
    public SysGeoAssess selectSysGeoAssessByAssessId(Long assessId)
    {
        return sysGeoAssessMapper.selectSysGeoAssessByAssessId(assessId);
    }

    /**
     * 查询评定表列表
     * 
     * @param sysGeoAssess 评定表
     * @return 评定表
     */
    @Override
    public List<SysGeoAssess> selectSysGeoAssessList(SysGeoAssess sysGeoAssess)
    {
        return sysGeoAssessMapper.selectSysGeoAssessList(sysGeoAssess);
    }

    /**
     * 新增评定表
     * 
     * @param sysGeoAssess 评定表
     * @return 结果
     */
    @Override
    public int insertSysGeoAssess(SysGeoAssess sysGeoAssess)
    {
        return sysGeoAssessMapper.insertSysGeoAssess(sysGeoAssess);
    }

    /**
     * 修改评定表
     * 
     * @param sysGeoAssess 评定表
     * @return 结果
     */
    @Override
    public int updateSysGeoAssess(SysGeoAssess sysGeoAssess)
    {
        return sysGeoAssessMapper.updateSysGeoAssess(sysGeoAssess);
    }

    /**
     * 批量删除评定表
     * 
     * @param assessIds 需要删除的评定表主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoAssessByAssessIds(Long[] assessIds)
    {
        return sysGeoAssessMapper.deleteSysGeoAssessByAssessIds(assessIds);
    }

    /**
     * 删除评定表信息
     * 
     * @param assessId 评定表主键
     * @return 结果
     */
    @Override
    public int deleteSysGeoAssessByAssessId(Long assessId)
    {
        return sysGeoAssessMapper.deleteSysGeoAssessByAssessId(assessId);
    }

    @Override
    public SysGeoAssess selectSysGeoAssessByUserIdAndDate(SysGeoAssess sysGeoAssess)
    {
        return sysGeoAssessMapper.selectSysGeoAssessByUserIdAndDate(sysGeoAssess);
    }

    @Override
    public SysGeoAssess selectSysGeoAssessByUserIdAndDateNotSelf(SysGeoAssess sysGeoAssess)
    {
        return sysGeoAssessMapper.selectSysGeoAssessByUserIdAndDateNotSelf(sysGeoAssess);
    }
}
