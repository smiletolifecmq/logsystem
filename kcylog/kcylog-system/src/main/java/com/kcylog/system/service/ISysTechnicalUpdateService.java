package com.kcylog.system.service;

import com.kcylog.system.domain.SysTechnicalUpdate;
import com.kcylog.system.param.TechnicalUpdateParams;

import java.util.List;

/**
 * 技术更新Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface ISysTechnicalUpdateService 
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
     * 批量删除技术更新
     * 
     * @param technicalUpdateParams 需要删除的技术更新主键集合
     * @return 结果
     */
    public int deleteSysTechnicalUpdateByTechnicalIds(TechnicalUpdateParams technicalUpdateParams);

    /**
     * 删除技术更新信息
     * 
     * @param technicalId 技术更新主键
     * @return 结果
     */
    public int deleteSysTechnicalUpdateByTechnicalId(Long technicalId);
}
