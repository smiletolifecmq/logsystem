package com.kcylog.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kcylog.system.mapper.SysVersionHandoverMapper;
import com.kcylog.system.domain.SysVersionHandover;
import com.kcylog.system.service.ISysVersionHandoverService;

/**
 * 多版本移交Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-29
 */
@Service
public class SysVersionHandoverServiceImpl implements ISysVersionHandoverService 
{
    @Autowired
    private SysVersionHandoverMapper sysVersionHandoverMapper;

    /**
     * 查询多版本移交
     * 
     * @param id 多版本移交主键
     * @return 多版本移交
     */
    @Override
    public SysVersionHandover selectSysVersionHandoverById(Long id)
    {
        return sysVersionHandoverMapper.selectSysVersionHandoverById(id);
    }

    /**
     * 查询多版本移交列表
     * 
     * @param sysVersionHandover 多版本移交
     * @return 多版本移交
     */
    @Override
    public List<SysVersionHandover> selectSysVersionHandoverList(SysVersionHandover sysVersionHandover)
    {
        return sysVersionHandoverMapper.selectSysVersionHandoverList(sysVersionHandover);
    }

    /**
     * 新增多版本移交
     * 
     * @param sysVersionHandover 多版本移交
     * @return 结果
     */
    @Override
    public int insertSysVersionHandover(SysVersionHandover sysVersionHandover)
    {
        return sysVersionHandoverMapper.insertSysVersionHandover(sysVersionHandover);
    }

    /**
     * 修改多版本移交
     * 
     * @param sysVersionHandover 多版本移交
     * @return 结果
     */
    @Override
    public int updateSysVersionHandover(SysVersionHandover sysVersionHandover)
    {
        return sysVersionHandoverMapper.updateSysVersionHandover(sysVersionHandover);
    }

    /**
     * 批量删除多版本移交
     * 
     * @param ids 需要删除的多版本移交主键
     * @return 结果
     */
    @Override
    public int deleteSysVersionHandoverByIds(Long[] ids)
    {
        return sysVersionHandoverMapper.deleteSysVersionHandoverByIds(ids);
    }

    /**
     * 删除多版本移交信息
     * 
     * @param id 多版本移交主键
     * @return 结果
     */
    @Override
    public int deleteSysVersionHandoverById(Long id)
    {
        return sysVersionHandoverMapper.deleteSysVersionHandoverById(id);
    }
}
