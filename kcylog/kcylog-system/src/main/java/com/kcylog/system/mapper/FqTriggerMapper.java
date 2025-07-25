package com.kcylog.system.mapper;

import java.util.List;
import com.kcylog.system.domain.FqTrigger;

/**
 * 百川项目操作Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-23
 */
public interface FqTriggerMapper 
{
    /**
     * 查询百川项目操作
     * 
     * @param id 百川项目操作主键
     * @return 百川项目操作
     */
    public FqTrigger selectFqTriggerById(String id);

    /**
     * 查询百川项目操作列表
     * 
     * @param fqTrigger 百川项目操作
     * @return 百川项目操作集合
     */
    public List<FqTrigger> selectFqTriggerList(FqTrigger fqTrigger);

    /**
     * 新增百川项目操作
     * 
     * @param fqTrigger 百川项目操作
     * @return 结果
     */
    public int insertFqTrigger(FqTrigger fqTrigger);

    /**
     * 修改百川项目操作
     * 
     * @param fqTrigger 百川项目操作
     * @return 结果
     */
    public int updateFqTrigger(FqTrigger fqTrigger);

    /**
     * 删除百川项目操作
     * 
     * @param id 百川项目操作主键
     * @return 结果
     */
    public int deleteFqTriggerById(String id);

    /**
     * 批量删除百川项目操作
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFqTriggerByIds(String[] ids);
}
