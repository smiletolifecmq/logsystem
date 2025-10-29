package com.kcylog.system.service;

import java.util.List;
import com.kcylog.system.domain.SysVersionHandover;

/**
 * 多版本移交Service接口
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
public interface ISysVersionHandoverService 
{
    /**
     * 查询多版本移交
     * 
     * @param id 多版本移交主键
     * @return 多版本移交
     */
    public SysVersionHandover selectSysVersionHandoverById(Long id);

    /**
     * 查询多版本移交列表
     * 
     * @param sysVersionHandover 多版本移交
     * @return 多版本移交集合
     */
    public List<SysVersionHandover> selectSysVersionHandoverList(SysVersionHandover sysVersionHandover);

    /**
     * 新增多版本移交
     * 
     * @param sysVersionHandover 多版本移交
     * @return 结果
     */
    public int insertSysVersionHandover(SysVersionHandover sysVersionHandover);

    /**
     * 修改多版本移交
     * 
     * @param sysVersionHandover 多版本移交
     * @return 结果
     */
    public int updateSysVersionHandover(SysVersionHandover sysVersionHandover);

    /**
     * 批量删除多版本移交
     * 
     * @param ids 需要删除的多版本移交主键集合
     * @return 结果
     */
    public int deleteSysVersionHandoverByIds(Long[] ids);

    /**
     * 删除多版本移交信息
     * 
     * @param id 多版本移交主键
     * @return 结果
     */
    public int deleteSysVersionHandoverById(Long id);
}
