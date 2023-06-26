package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysTechnicalUpdate;
import com.kcylog.system.mapper.SysTechnicalUpdateMapper;
import com.kcylog.system.param.TechnicalUpdateParams;
import com.kcylog.system.service.ISysTechnicalUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 技术更新Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@Service
public class SysTechnicalUpdateServiceImpl implements ISysTechnicalUpdateService 
{
    @Autowired
    private SysTechnicalUpdateMapper sysTechnicalUpdateMapper;

    /**
     * 查询技术更新
     * 
     * @param technicalId 技术更新主键
     * @return 技术更新
     */
    @Override
    public SysTechnicalUpdate selectSysTechnicalUpdateByTechnicalId(Long technicalId)
    {
        return sysTechnicalUpdateMapper.selectSysTechnicalUpdateByTechnicalId(technicalId);
    }

    /**
     * 查询技术更新列表
     * 
     * @param sysTechnicalUpdate 技术更新
     * @return 技术更新
     */
    @Override
    public List<SysTechnicalUpdate> selectSysTechnicalUpdateList(SysTechnicalUpdate sysTechnicalUpdate)
    {
        return sysTechnicalUpdateMapper.selectSysTechnicalUpdateList(sysTechnicalUpdate);
    }

    /**
     * 新增技术更新
     * 
     * @param sysTechnicalUpdate 技术更新
     * @return 结果
     */
    @Override
    public int insertSysTechnicalUpdate(SysTechnicalUpdate sysTechnicalUpdate)
    {
        sysTechnicalUpdate.setCreateTime(DateUtils.getNowDate());
        return sysTechnicalUpdateMapper.insertSysTechnicalUpdate(sysTechnicalUpdate);
    }

    /**
     * 修改技术更新
     * 
     * @param sysTechnicalUpdate 技术更新
     * @return 结果
     */
    @Override
    public int updateSysTechnicalUpdate(SysTechnicalUpdate sysTechnicalUpdate)
    {
        sysTechnicalUpdate.setUpdateTime(DateUtils.getNowDate());
        return sysTechnicalUpdateMapper.updateSysTechnicalUpdate(sysTechnicalUpdate);
    }

    /**
     * 批量删除技术更新
     * 
     * @param technicalUpdateParams 需要删除的技术更新主键
     * @return 结果
     */
    @Override
    public int deleteSysTechnicalUpdateByTechnicalIds(TechnicalUpdateParams technicalUpdateParams)
    {
        return sysTechnicalUpdateMapper.deleteSysTechnicalUpdateByTechnicalIds(technicalUpdateParams);
    }

    /**
     * 删除技术更新信息
     * 
     * @param technicalId 技术更新主键
     * @return 结果
     */
    @Override
    public int deleteSysTechnicalUpdateByTechnicalId(Long technicalId)
    {
        return sysTechnicalUpdateMapper.deleteSysTechnicalUpdateByTechnicalId(technicalId);
    }
}
