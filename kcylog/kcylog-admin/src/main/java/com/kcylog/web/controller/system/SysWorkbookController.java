package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.config.RuoYiConfig;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.SysWorkbook;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.param.WorkbookParams;
import com.kcylog.system.service.ISysManageFileService;
import com.kcylog.system.service.ISysWorkbookService;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
    public void export(HttpServletResponse response, SysWorkbook sysWorkbook) throws IOException {
        List<SysWorkbook> list = sysWorkbookService.selectSysWorkbookList(sysWorkbook);
        List<String> fileUrl = new ArrayList<>();
        String filePath = RuoYiConfig.getUploadPath();
        for (SysWorkbook workbook:list){
            for (SysManageFile fileList:workbook.getManageFile()){
                fileUrl.add(filePath + fileList.getUrl().replace("/profile/upload", ""));
            }
        }

        // 设置响应头信息
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"output.pdf\"");

        // 获取响应输出流
        OutputStream outputStream = response.getOutputStream();
        PDFMergerUtility merger = new PDFMergerUtility();

        for (String inputFilePath : fileUrl) {
            // 添加要合并的PDF文件
            merger.addSource(new File(inputFilePath));
        }
        merger.setDestinationStream(outputStream);
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        // 关闭输出流
        IOUtils.closeQuietly(outputStream);
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
        String userName = SecurityUtils.getUsername();
        sysWorkbook.setUpdateName(userName);
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
