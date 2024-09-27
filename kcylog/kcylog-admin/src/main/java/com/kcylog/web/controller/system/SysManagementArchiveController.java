package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.SysManagementArchive;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.service.ISysManageFileService;
import com.kcylog.system.service.ISysManagementArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 经营归档Controller
 * 
 * @author ruoyi
 * @date 2024-09-27
 */
@RestController
@RequestMapping("/system/archive")
public class SysManagementArchiveController extends BaseController
{
    @Autowired
    private ISysManagementArchiveService sysManagementArchiveService;

    @Autowired
    private ISysManageFileService sysManageFileService;

    /**
     * 查询经营归档列表
     */
    @PreAuthorize("@ss.hasPermi('system:archive:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementArchive sysManagementArchive)
    {
        startPage();
        List<SysManagementArchive> list = sysManagementArchiveService.selectSysManagementArchiveList(sysManagementArchive);
        return getDataTable(list);
    }

    /**
     * 导出经营归档列表
     */
    @PreAuthorize("@ss.hasPermi('system:archive:export')")
    @Log(title = "经营归档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementArchive sysManagementArchive)
    {
        List<SysManagementArchive> list = sysManagementArchiveService.selectSysManagementArchiveList(sysManagementArchive);
        ExcelUtil<SysManagementArchive> util = new ExcelUtil<SysManagementArchive>(SysManagementArchive.class);
        util.exportExcel(response, list, "经营归档数据");
    }

    /**
     * 获取经营归档详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:archive:query')")
    @GetMapping(value = "/{technicalId}")
    public AjaxResult getInfo(@PathVariable("technicalId") Long technicalId)
    {
        return success(sysManagementArchiveService.selectSysManagementArchiveByTechnicalId(technicalId));
    }

    /**
     * 新增经营归档
     */
    @PreAuthorize("@ss.hasPermi('system:archive:add')")
    @Log(title = "经营归档", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody SysManagementArchive sysManagementArchive)
    {
        sysManagementArchive.setUserName(SecurityUtils.getUsername());
        sysManagementArchiveService.insertSysManagementArchive(sysManagementArchive);
        for (UploadFileList file:sysManagementArchive.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysManagementArchive.getTechnicalId());
            sysManageFile.setModuleType((long)5);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 修改经营归档
     */
    @PreAuthorize("@ss.hasPermi('system:archive:edit')")
    @Log(title = "经营归档", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody SysManagementArchive sysManagementArchive)
    {
        sysManagementArchiveService.updateSysManagementArchive(sysManagementArchive);
        SysManageFile sysManageFileObj = new SysManageFile();
        sysManageFileObj.setModuleId(sysManagementArchive.getTechnicalId());
        sysManageFileObj.setModuleType((long)5);
        sysManageFileService.deleteSysManageFileByModuleIdAndType(sysManageFileObj);
        for (UploadFileList file:sysManagementArchive.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysManagementArchive.getTechnicalId());
            sysManageFile.setModuleType((long)5);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 删除经营归档
     */
    @PreAuthorize("@ss.hasPermi('system:archive:remove')")
    @Log(title = "经营归档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{technicalIds}")
    public AjaxResult remove(@PathVariable Long[] technicalIds)
    {
        return toAjax(sysManagementArchiveService.deleteSysManagementArchiveByTechnicalIds(technicalIds));
    }
}
