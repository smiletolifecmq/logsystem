package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.SysTechnicalUpdate;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.param.TechnicalUpdateParams;
import com.kcylog.system.service.ISysManageFileService;
import com.kcylog.system.service.ISysTechnicalUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 技术更新Controller
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@RestController
@RequestMapping("/system/technicalUpdate")
public class SysTechnicalUpdateController extends BaseController
{
    @Autowired
    private ISysTechnicalUpdateService sysTechnicalUpdateService;

    @Autowired
    private ISysManageFileService sysManageFileService;

    /**
     * 查询技术更新列表
     */
    @PreAuthorize("@ss.hasPermi('system:technicalUpdate:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTechnicalUpdate sysTechnicalUpdate)
    {
        startPage();
        List<SysTechnicalUpdate> list = sysTechnicalUpdateService.selectSysTechnicalUpdateList(sysTechnicalUpdate);
        return getDataTable(list);
    }

    /**
     * 获取技术更新详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:technicalUpdate:query')")
    @GetMapping(value = "/{technicalId}")
    public AjaxResult getInfo(@PathVariable("technicalId") Long technicalId)
    {
        return success(sysTechnicalUpdateService.selectSysTechnicalUpdateByTechnicalId(technicalId));
    }

    /**
     * 新增技术更新
     */
    @PreAuthorize("@ss.hasPermi('system:technicalUpdate:add')")
    @Log(title = "技术更新", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysTechnicalUpdate sysTechnicalUpdate)
    {
        Long userId = SecurityUtils.getUserId();
        sysTechnicalUpdate.setUserId(userId);
        sysTechnicalUpdateService.insertSysTechnicalUpdate(sysTechnicalUpdate);
        for (UploadFileList file:sysTechnicalUpdate.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysTechnicalUpdate.getTechnicalId());
            sysManageFile.setModuleType((long)1);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 修改技术更新
     */
    @PreAuthorize("@ss.hasPermi('system:technicalUpdate:edit')")
    @Log(title = "技术更新", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTechnicalUpdate sysTechnicalUpdate)
    {
        sysTechnicalUpdateService.updateSysTechnicalUpdate(sysTechnicalUpdate);
        SysManageFile sysManageFileObj = new SysManageFile();
        sysManageFileObj.setModuleId(sysTechnicalUpdate.getTechnicalId());
        sysManageFileObj.setModuleType((long)1);
        sysManageFileService.deleteSysManageFileByModuleIdAndType(sysManageFileObj);
        for (UploadFileList file:sysTechnicalUpdate.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysTechnicalUpdate.getTechnicalId());
            sysManageFile.setModuleType((long)1);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 删除技术更新
     */
    @PreAuthorize("@ss.hasPermi('system:technicalUpdate:remove')")
    @Log(title = "技术更新", businessType = BusinessType.DELETE)
	@DeleteMapping("/{technicalIds}")
    public AjaxResult remove(@PathVariable Long[] technicalIds)
    {
        TechnicalUpdateParams technicalUpdateParams = new TechnicalUpdateParams();
        technicalUpdateParams.setTechnicalIds(technicalIds);
        technicalUpdateParams.setUserId(SecurityUtils.getUserId());
        return toAjax(sysTechnicalUpdateService.deleteSysTechnicalUpdateByTechnicalIds(technicalUpdateParams));
    }
}
