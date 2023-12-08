package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.SysGeoAssess;

/**
 * 评定表Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-08
 */
public interface SysGeoAssessMapper 
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
     * 删除评定表
     * 
     * @param assessId 评定表主键
     * @return 结果
     */
    public int deleteSysGeoAssessByAssessId(Long assessId);

    /**
     * 批量删除评定表
     * 
     * @param assessIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysGeoAssessByAssessIds(Long[] assessIds);
}
