package com.kcylog.system.service;

import com.kcylog.system.domain.SysProfit;

import java.util.List;

/**
 * 福清分公司净利润申报表Service接口
 * 
 * @author ruoyi
 * @date 2025-09-18
 */
public interface ISysProfitService 
{
    /**
     * 查询福清分公司净利润申报表
     * 
     * @param id 福清分公司净利润申报表主键
     * @return 福清分公司净利润申报表
     */
    public SysProfit selectSysProfitById(Long id);

    /**
     * 查询福清分公司净利润申报表列表
     * 
     * @param sysProfit 福清分公司净利润申报表
     * @return 福清分公司净利润申报表集合
     */
    public List<SysProfit> selectSysProfitList(SysProfit sysProfit);

    /**
     * 新增福清分公司净利润申报表
     * 
     * @param sysProfit 福清分公司净利润申报表
     * @return 结果
     */
    public int insertSysProfit(SysProfit sysProfit);

    /**
     * 修改福清分公司净利润申报表
     * 
     * @param sysProfit 福清分公司净利润申报表
     * @return 结果
     */
    public int updateSysProfit(SysProfit sysProfit);

    /**
     * 批量删除福清分公司净利润申报表
     * 
     * @param ids 需要删除的福清分公司净利润申报表主键集合
     * @return 结果
     */
    public int deleteSysProfitByIds(Long[] ids);

    /**
     * 删除福清分公司净利润申报表信息
     * 
     * @param id 福清分公司净利润申报表主键
     * @return 结果
     */
    public int deleteSysProfitById(Long id);

    public List<SysProfit> selectSysProfitListLastMonth();

    public List<SysProfit> getlistProfitOne();
}
