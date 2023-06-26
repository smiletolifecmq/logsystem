package com.kcylog.system.service.impl;

import com.kcylog.common.utils.DateUtils;
import com.kcylog.system.domain.SysWorkbook;
import com.kcylog.system.mapper.SysWorkbookMapper;
import com.kcylog.system.param.WorkbookParams;
import com.kcylog.system.service.ISysWorkbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作业手册Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@Service
public class SysWorkbookServiceImpl implements ISysWorkbookService 
{
    @Autowired
    private SysWorkbookMapper sysWorkbookMapper;

    /**
     * 查询作业手册
     * 
     * @param workbookId 作业手册主键
     * @return 作业手册
     */
    @Override
    public SysWorkbook selectSysWorkbookByWorkbookId(Long workbookId)
    {
        return sysWorkbookMapper.selectSysWorkbookByWorkbookId(workbookId);
    }

    /**
     * 查询作业手册列表
     * 
     * @param sysWorkbook 作业手册
     * @return 作业手册
     */
    @Override
    public List<SysWorkbook> selectSysWorkbookList(SysWorkbook sysWorkbook)
    {
        return sysWorkbookMapper.selectSysWorkbookList(sysWorkbook);
    }

    /**
     * 新增作业手册
     * 
     * @param sysWorkbook 作业手册
     * @return 结果
     */
    @Override
    public int insertSysWorkbook(SysWorkbook sysWorkbook)
    {
        sysWorkbook.setCreateTime(DateUtils.getNowDate());
        return sysWorkbookMapper.insertSysWorkbook(sysWorkbook);
    }

    /**
     * 修改作业手册
     * 
     * @param sysWorkbook 作业手册
     * @return 结果
     */
    @Override
    public int updateSysWorkbook(SysWorkbook sysWorkbook)
    {
        sysWorkbook.setUpdateTime(DateUtils.getNowDate());
        return sysWorkbookMapper.updateSysWorkbook(sysWorkbook);
    }

    /**
     * 批量删除作业手册
     * 
     * @param workbookParams 需要删除的作业手册主键
     * @return 结果
     */
    @Override
    public int deleteSysWorkbookByWorkbookIds(WorkbookParams workbookParams)
    {
        return sysWorkbookMapper.deleteSysWorkbookByWorkbookIds(workbookParams);
    }

    /**
     * 删除作业手册信息
     * 
     * @param workbookId 作业手册主键
     * @return 结果
     */
    @Override
    public int deleteSysWorkbookByWorkbookId(Long workbookId)
    {
        return sysWorkbookMapper.deleteSysWorkbookByWorkbookId(workbookId);
    }
}
