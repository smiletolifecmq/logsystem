package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.domain.SysKnowledgeShare;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.service.ISysKnowledgeShareService;
import com.kcylog.system.service.ISysManageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识分享Controller
 *
 * @author ruoyi
 * @date 2023-06-21
 */
@RestController
@RequestMapping("/system/knowledgeShare")
public class SysKnowledgeShareController extends BaseController {
    @Autowired
    private ISysKnowledgeShareService sysKnowledgeShareService;

    @Autowired
    private ISysManageFileService sysManageFileService;

    /**
     * 查询知识分享列表
     */
    @PreAuthorize("@ss.hasPermi('system:knowledgeShare:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysKnowledgeShare sysKnowledgeShare) {
        startPage();
        List<SysKnowledgeShare> list = sysKnowledgeShareService.selectSysKnowledgeShareList(sysKnowledgeShare);
        return getDataTable(list);
    }

    /**
     * 获取知识分享详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:knowledgeShare:query')")
    @GetMapping(value = "/{knowledgeId}")
    public AjaxResult getInfo(@PathVariable("knowledgeId") Long knowledgeId) {
        return success(sysKnowledgeShareService.selectSysKnowledgeShareByKnowledgeId(knowledgeId));
    }

    /**
     * 新增知识分享
     */
    @PreAuthorize("@ss.hasPermi('system:knowledgeShare:add')")
    @Log(title = "知识分享", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysKnowledgeShare sysKnowledgeShare) {
        Long userId = SecurityUtils.getUserId();
        sysKnowledgeShare.setUserId(userId);
        sysKnowledgeShareService.insertSysKnowledgeShare(sysKnowledgeShare);
        for (UploadFileList file:sysKnowledgeShare.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysKnowledgeShare.getKnowledgeId());
            sysManageFile.setModuleType((long)2);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 修改知识分享
     */
    @PreAuthorize("@ss.hasPermi('system:knowledgeShare:edit')")
    @Log(title = "知识分享", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysKnowledgeShare sysKnowledgeShare) {
        return toAjax(sysKnowledgeShareService.updateSysKnowledgeShare(sysKnowledgeShare));
    }

    /**
     * 删除知识分享
     */
    @PreAuthorize("@ss.hasPermi('system:knowledgeShare:remove')")
    @Log(title = "知识分享", businessType = BusinessType.DELETE)
    @DeleteMapping("/{knowledgeIds}")
    public AjaxResult remove(@PathVariable Long[] knowledgeIds) {
        return toAjax(sysKnowledgeShareService.deleteSysKnowledgeShareByKnowledgeIds(knowledgeIds));
    }
}
