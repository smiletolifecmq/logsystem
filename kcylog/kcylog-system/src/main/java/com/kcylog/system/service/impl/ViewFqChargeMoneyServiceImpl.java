package com.kcylog.system.service.impl;

import com.kcylog.system.domain.ViewFqChargeMoney;
import com.kcylog.system.mapper.ViewFqChargeMoneyMapper;
import com.kcylog.system.service.IViewFqChargeMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分包金额同步Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-08
 */
@Service
public class ViewFqChargeMoneyServiceImpl implements IViewFqChargeMoneyService 
{
    @Autowired
    private ViewFqChargeMoneyMapper viewFqChargeMoneyMapper;

    /**
     * 查询分包金额同步
     * 
     * @param projectCode 分包金额同步主键
     * @return 分包金额同步
     */
    @Override
    public ViewFqChargeMoney selectViewFqChargeMoneyByProjectCode(String projectCode)
    {
        return viewFqChargeMoneyMapper.selectViewFqChargeMoneyByProjectCode(projectCode);
    }

    /**
     * 查询分包金额同步列表
     * 
     * @param viewFqChargeMoney 分包金额同步
     * @return 分包金额同步
     */
    @Override
    public List<ViewFqChargeMoney> selectViewFqChargeMoneyList(ViewFqChargeMoney viewFqChargeMoney)
    {
        return viewFqChargeMoneyMapper.selectViewFqChargeMoneyList(viewFqChargeMoney);
    }

    /**
     * 新增分包金额同步
     * 
     * @param viewFqChargeMoney 分包金额同步
     * @return 结果
     */
    @Override
    public int insertViewFqChargeMoney(ViewFqChargeMoney viewFqChargeMoney)
    {
        return viewFqChargeMoneyMapper.insertViewFqChargeMoney(viewFqChargeMoney);
    }

    /**
     * 修改分包金额同步
     * 
     * @param viewFqChargeMoney 分包金额同步
     * @return 结果
     */
    @Override
    public int updateViewFqChargeMoney(ViewFqChargeMoney viewFqChargeMoney)
    {
        return viewFqChargeMoneyMapper.updateViewFqChargeMoney(viewFqChargeMoney);
    }

    /**
     * 批量删除分包金额同步
     * 
     * @param projectCodes 需要删除的分包金额同步主键
     * @return 结果
     */
    @Override
    public int deleteViewFqChargeMoneyByProjectCodes(String[] projectCodes)
    {
        return viewFqChargeMoneyMapper.deleteViewFqChargeMoneyByProjectCodes(projectCodes);
    }

    /**
     * 删除分包金额同步信息
     * 
     * @param projectCode 分包金额同步主键
     * @return 结果
     */
    @Override
    public int deleteViewFqChargeMoneyByProjectCode(String projectCode)
    {
        return viewFqChargeMoneyMapper.deleteViewFqChargeMoneyByProjectCode(projectCode);
    }
}
