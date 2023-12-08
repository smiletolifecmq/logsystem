package com.kcylog.system.service;

import com.kcylog.system.domain.SysGeoAssess;

import java.util.List;

/**
 * 评定表Service接口
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public interface ISysGeoAssessService 
{
    /**
     * 查询评定表
     * 
     * @param assessId 评定表主键
     * @return 评定表
     */
    public SysGeoAssess selectSysGeoAssessByAssessId(Long assessId);

    /**
     * 查询评定表列表
     * 
     * @param sysGeoAssess 评定表
     * @return 评定表集合
     */
    public List<SysGeoAssess> selectSysGeoAssessList(SysGeoAssess sysGeoAssess);

    /**
     * 新增评定表
     * 
     * @param sysGeoAssess 评定表
     * @return 结果
     */
    public int insertSysGeoAssess(SysGeoAssess sysGeoAssess);

    /**
     * 修改评定表
     * 
     * @param sysGeoAssess 评定表
     * @return 结果
     */
    public int updateSysGeoAssess(SysGeoAssess sysGeoAssess);

    /**
     * 批量删除评定表
     * 
     * @param assessIds 需要删除的评定表主键集合
     * @return 结果
     */
    public int deleteSysGeoAssessByAssessIds(Long[] assessIds);

    /**
     * 删除评定表信息
     * 
     * @param assessId 评定表主键
     * @return 结果
     */
    public int deleteSysGeoAssessByAssessId(Long assessId);

    public SysGeoAssess selectSysGeoAssessByUserIdAndDate(SysGeoAssess sysGeoAssess);
}
