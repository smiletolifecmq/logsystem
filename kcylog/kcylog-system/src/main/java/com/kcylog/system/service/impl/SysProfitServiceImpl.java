package com.kcylog.system.service.impl;

import com.kcylog.system.domain.SysProfit;
import com.kcylog.system.mapper.SysProfitMapper;
import com.kcylog.system.service.ISysProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 福清分公司净利润申报表Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-09-18
 */
@Service
public class SysProfitServiceImpl implements ISysProfitService 
{
    @Autowired
    private SysProfitMapper sysProfitMapper;

    /**
     * 查询福清分公司净利润申报表
     * 
     * @param id 福清分公司净利润申报表主键
     * @return 福清分公司净利润申报表
     */
    @Override
    public SysProfit selectSysProfitById(Long id)
    {
        return sysProfitMapper.selectSysProfitById(id);
    }

    /**
     * 查询福清分公司净利润申报表列表
     * 
     * @param sysProfit 福清分公司净利润申报表
     * @return 福清分公司净利润申报表
     */
    @Override
    public List<SysProfit> selectSysProfitList(SysProfit sysProfit)
    {
        return sysProfitMapper.selectSysProfitList(sysProfit);
    }

    /**
     * 新增福清分公司净利润申报表
     * 
     * @param sysProfit 福清分公司净利润申报表
     * @return 结果
     */
    @Override
    public int insertSysProfit(SysProfit sysProfit)
    {
        return sysProfitMapper.insertSysProfit(sysProfit);
    }

    /**
     * 修改福清分公司净利润申报表
     * 
     * @param sysProfit 福清分公司净利润申报表
     * @return 结果
     */
    @Override
    public int updateSysProfit(SysProfit sysProfit)
    {
        return sysProfitMapper.updateSysProfit(sysProfit);
    }

    /**
     * 批量删除福清分公司净利润申报表
     * 
     * @param ids 需要删除的福清分公司净利润申报表主键
     * @return 结果
     */
    @Override
    public int deleteSysProfitByIds(Long[] ids)
    {
        return sysProfitMapper.deleteSysProfitByIds(ids);
    }

    /**
     * 删除福清分公司净利润申报表信息
     * 
     * @param id 福清分公司净利润申报表主键
     * @return 结果
     */
    @Override
    public int deleteSysProfitById(Long id)
    {
        return sysProfitMapper.deleteSysProfitById(id);
    }

    @Override
    public List<SysProfit> selectSysProfitListLastMonth()
    {
        return sysProfitMapper.selectSysProfitListLastMonth();
    }

    @Override
    public List<SysProfit> getlistProfitOne()
    {
        return sysProfitMapper.getlistProfitOne();
    }
}
