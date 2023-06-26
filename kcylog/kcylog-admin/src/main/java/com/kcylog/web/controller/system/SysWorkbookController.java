package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.SysWorkbook;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.param.WorkbookParams;
import com.kcylog.system.service.ISysManageFileService;
import com.kcylog.system.service.ISysWorkbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作业手册Controller
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@RestController
@RequestMapping("/system/workbook")
public class SysWorkbookController extends BaseController
{
    @Autowired
    private ISysWorkbookService sysWorkbookService;

    @Autowired
    private ISysManageFileService sysManageFileService;

    /**
     * 查询作业手册列表
     */
    @PreAuthorize("@ss.hasPermi('system:workbook:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWorkbook sysWorkbook)
    {
        startPage();
        List<SysWorkbook> list = sysWorkbookService.selectSysWorkbookList(sysWorkbook);
        return getDataTable(list);
    }

    /**
     * 导出作业手册列表
     */
    @PreAuthorize("@ss.hasPermi('system:workbook:export')")
    @Log(title = "作业手册", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWorkbook sysWorkbook)
    {
        List<SysWorkbook> list = sysWorkbookService.selectSysWorkbookList(sysWorkbook);
        ExcelUtil<SysWorkbook> util = new ExcelUtil<SysWorkbook>(SysWorkbook.class);
        util.exportExcel(response, list, "作业手册数据");
    }

    /**
     * 获取作业手册详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:workbook:query')")
    @GetMapping(value = "/{workbookId}")
    public AjaxResult getInfo(@PathVariable("workbookId") Long workbookId)
    {
        return success(sysWorkbookService.selectSysWorkbookByWorkbookId(workbookId));
    }

    /**
     * 新增作业手册
     */
    @PreAuthorize("@ss.hasPermi('system:workbook:add')")
    @Log(title = "作业手册", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWorkbook sysWorkbook)
    {
        Long userId = SecurityUtils.getUserId();
        sysWorkbook.setUserId(userId);
        sysWorkbookService.insertSysWorkbook(sysWorkbook);
        for (UploadFileList file:sysWorkbook.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysWorkbook.getWorkbookId());
            sysManageFile.setModuleType((long)4);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 修改作业手册
     */
    @PreAuthorize("@ss.hasPermi('system:workbook:edit')")
    @Log(title = "作业手册", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWorkbook sysWorkbook)
    {
        sysWorkbookService.updateSysWorkbook(sysWorkbook);
        SysManageFile sysManageFileObj = new SysManageFile();
        sysManageFileObj.setModuleId(sysWorkbook.getWorkbookId());
        sysManageFileObj.setModuleType((long)4);
        sysManageFileService.deleteSysManageFileByModuleIdAndType(sysManageFileObj);
        for (UploadFileList file:sysWorkbook.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysWorkbook.getWorkbookId());
            sysManageFile.setModuleType((long)4);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 删除作业手册
     */
    @PreAuthorize("@ss.hasPermi('system:workbook:remove')")
    @Log(title = "作业手册", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workbookIds}")
    public AjaxResult remove(@PathVariable Long[] workbookIds)
    {
        WorkbookParams workbookParams = new WorkbookParams();
        workbookParams.setWorkbookIds(workbookIds);
        workbookParams.setUserId(SecurityUtils.getUserId());
        return toAjax(sysWorkbookService.deleteSysWorkbookByWorkbookIds(workbookParams));
    }
}
