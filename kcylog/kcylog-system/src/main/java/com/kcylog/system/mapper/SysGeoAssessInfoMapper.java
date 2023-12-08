package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysGeoAssessInfo;

/**
 * 评定详情表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public interface SysGeoAssessInfoMapper 
{
    /**
     * 查询评定详情表
     * 
     * @param assessInfoId 评定详情表主键
     * @return 评定详情表
     */
    public SysGeoAssessInfo selectSysGeoAssessInfoByAssessInfoId(Long assessInfoId);

    /**
     * 查询评定详情表列表
     * 
     * @param sysGeoAssessInfo 评定详情表
     * @return 评定详情表集合
     */
    public List<SysGeoAssessInfo> selectSysGeoAssessInfoList(SysGeoAssessInfo sysGeoAssessInfo);

    /**
     * 新增评定详情表
     * 
     * @param sysGeoAssessInfo 评定详情表
     * @return 结果
     */
    public int insertSysGeoAssessInfo(SysGeoAssessInfo sysGeoAssessInfo);

    /**
     * 修改评定详情表
     * 
     * @param sysGeoAssessInfo 评定详情表
     * @return 结果
     */
    public int updateSysGeoAssessInfo(SysGeoAssessInfo sysGeoAssessInfo);

    /**
     * 删除评定详情表
     * 
     * @param assessInfoId 评定详情表主键
     * @return 结果
     */
    public int deleteSysGeoAssessInfoByAssessInfoId(Long assessInfoId);

    /**
     * 批量删除评定详情表
     * 
     * @param assessInfoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoAssessInfoByAssessInfoIds(Long[] assessInfoIds);
}
