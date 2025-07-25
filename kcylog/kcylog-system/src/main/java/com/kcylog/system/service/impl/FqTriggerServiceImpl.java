package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.FqTriggerMapper;
import com.kcylog.system.domain.FqTrigger;
import com.kcylog.system.service.IFqTriggerService;

/**
 * 百川项目操作Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-23
 */
@Service
public class FqTriggerServiceImpl implements IFqTriggerService 
{
    @Autowired
    private FqTriggerMapper fqTriggerMapper;

    /**
     * 查询百川项目操作
     * 
     * @param id 百川项目操作主键
     * @return 百川项目操作
     */
    @Override
    public FqTrigger selectFqTriggerById(String id)
    {
        return fqTriggerMapper.selectFqTriggerById(id);
    }

    /**
     * 查询百川项目操作列表
     * 
     * @param fqTrigger 百川项目操作
     * @return 百川项目操作
     */
    @Override
    public List<FqTrigger> selectFqTriggerList(FqTrigger fqTrigger)
    {
        return fqTriggerMapper.selectFqTriggerList(fqTrigger);
    }

    /**
     * 新增百川项目操作
     * 
     * @param fqTrigger 百川项目操作
     * @return 结果
     */
    @Override
    public int insertFqTrigger(FqTrigger fqTrigger)
    {
        return fqTriggerMapper.insertFqTrigger(fqTrigger);
    }

    /**
     * 修改百川项目操作
     * 
     * @param fqTrigger 百川项目操作
     * @return 结果
     */
    @Override
    public int updateFqTrigger(FqTrigger fqTrigger)
    {
        return fqTriggerMapper.updateFqTrigger(fqTrigger);
    }

    /**
     * 批量删除百川项目操作
     * 
     * @param ids 需要删除的百川项目操作主键
     * @return 结果
     */
    @Override
    public int deleteFqTriggerByIds(String[] ids)
    {
        return fqTriggerMapper.deleteFqTriggerByIds(ids);
    }

    /**
     * 删除百川项目操作信息
     * 
     * @param id 百川项目操作主键
     * @return 结果
     */
    @Override
    public int deleteFqTriggerById(String id)
    {
        return fqTriggerMapper.deleteFqTriggerById(id);
    }
}
