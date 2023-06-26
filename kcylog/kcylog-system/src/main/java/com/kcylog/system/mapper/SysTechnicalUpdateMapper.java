package com.kcylog.system.mapper;

import com.kcylog.system.domain.SysTechnicalUpdate;
import com.kcylog.system.param.TechnicalUpdateParams;

import java.util.List;

/**
 * 技术更新Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface SysTechnicalUpdateMapper 
{
    /**
     * 查询技术更新
     * 
     * @param technicalId 技术更新主键
     * @return 技术更新
     */
    public SysTechnicalUpdate selectSysTechnicalUpdateByTechnicalId(Long technicalId);

    /**
     * 查询技术更新列表
     * 
     * @param sysTechnicalUpdate 技术更新
     * @return 技术更新集合
     */
    public List<SysTechnicalUpdate> selectSysTechnicalUpdateList(SysTechnicalUpdate sysTechnicalUpdate);

    /**
     * 新增技术更新
     * 
     * @param sysTechnicalUpdate 技术更新
     * @return 结果
     */
    public int insertSysTechnicalUpdate(SysTechnicalUpdate sysTechnicalUpdate);

    /**
     * 修改技术更新
     * 
     * @param sysTechnicalUpdate 技术更新
     * @return 结果
     */
    public int updateSysTechnicalUpdate(SysTechnicalUpdate sysTechnicalUpdate);

    /**
     * 删除技术更新
     * 
     * @param technicalId 技术更新主键
     * @return 结果
     */
    public int deleteSysTechnicalUpdateByTechnicalId(Long technicalId);

    /**
     * 批量删除技术更新
     * 
     * @param technicalUpdateParams 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysTechnicalUpdateByTechnicalIds(TechnicalUpdateParams technicalUpdateParams);
}
