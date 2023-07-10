package com.kcylog.system.service;

import com.kcylog.system.domain.SysWorkbook;
import com.kcylog.system.param.WorkbookParams;

import java.util.List;

/**
 * 作业手册Service接口
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
public interface ISysWorkbookService 
{
    /**
     * 查询作业手册
     * 
     * @param workbookId 作业手册主键
     * @return 作业手册
     */
    public SysWorkbook selectSysWorkbookByWorkbookId(Long workbookId);

    /**
     * 查询作业手册列表
     * 
     * @param sysWorkbook 作业手册
     * @return 作业手册集合
     */
    public List<SysWorkbook> selectSysWorkbookList(SysWorkbook sysWorkbook);

    /**
     * 新增作业手册
     * 
     * @param sysWorkbook 作业手册
     * @return 结果
     */
    public int insertSysWorkbook(SysWorkbook sysWorkbook);

    /**
     * 修改作业手册
     * 
     * @param sysWorkbook 作业手册
     * @return 结果
     */
    public int updateSysWorkbook(SysWorkbook sysWorkbook);

    /**
     * 批量删除作业手册
     * 
     * @param workbookParams 需要删除的作业手册主键集合
     * @return 结果
     */
    public int deleteSysWorkbookByWorkbookIds(WorkbookParams workbookParams);

    /**
     * 删除作业手册信息
     * 
     * @param workbookId 作业手册主键
     * @return 结果
     */
    public int deleteSysWorkbookByWorkbookId(Long workbookId);

    public List<SysWorkbook> selectSysWorkbookListExport(SysWorkbook sysWorkbook);

}
