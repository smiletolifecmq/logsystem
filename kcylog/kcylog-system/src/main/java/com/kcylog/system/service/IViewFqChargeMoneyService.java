package com.kcylog.system.service;

import com.kcylog.system.domain.ViewFqChargeMoney;

import java.util.List;

/**
 * 分包金额同步Service接口
 * 
 * @author ruoyi
 * @date 2024-04-08
 */
public interface IViewFqChargeMoneyService 
{
    /**
     * 查询分包金额同步
     * 
     * @param projectCode 分包金额同步主键
     * @return 分包金额同步
     */
    public ViewFqChargeMoney selectViewFqChargeMoneyByProjectCode(String projectCode);

    /**
     * 查询分包金额同步列表
     * 
     * @param viewFqChargeMoney 分包金额同步
     * @return 分包金额同步集合
     */
    public List<ViewFqChargeMoney> selectViewFqChargeMoneyList(ViewFqChargeMoney viewFqChargeMoney);

    /**
     * 新增分包金额同步
     * 
     * @param viewFqChargeMoney 分包金额同步
     * @return 结果
     */
    public int insertViewFqChargeMoney(ViewFqChargeMoney viewFqChargeMoney);

    /**
     * 修改分包金额同步
     * 
     * @param viewFqChargeMoney 分包金额同步
     * @return 结果
     */
    public int updateViewFqChargeMoney(ViewFqChargeMoney viewFqChargeMoney);

    /**
     * 批量删除分包金额同步
     * 
     * @param projectCodes 需要删除的分包金额同步主键集合
     * @return 结果
     */
    public int deleteViewFqChargeMoneyByProjectCodes(String[] projectCodes);

    /**
     * 删除分包金额同步信息
     * 
     * @param projectCode 分包金额同步主键
     * @return 结果
     */
    public int deleteViewFqChargeMoneyByProjectCode(String projectCode);
}
