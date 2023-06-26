package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.domain.SysJobSpecification;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.param.JobSpecificationParams;
import com.kcylog.system.service.ISysJobSpecificationService;
import com.kcylog.system.service.ISysManageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作业规范Controller
 * 
 * @author ruoyi
 * @date 2023-06-25
 */
@RestController
@RequestMapping("/system/specification")
public class SysJobSpecificationController extends BaseController
{
    @Autowired
    private ISysJobSpecificationService sysJobSpecificationService;

    @Autowired
    private ISysManageFileService sysManageFileService;

    /**
     * 查询作业规范列表
     */
    @PreAuthorize("@ss.hasPermi('system:specification:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysJobSpecification sysJobSpecification)
    {
        startPage();
        List<SysJobSpecification> list = sysJobSpecificationService.selectSysJobSpecificationList(sysJobSpecification);
        return getDataTable(list);
    }

    /**
     * 获取作业规范详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:specification:query')")
    @GetMapping(value = "/{jobId}")
    public AjaxResult getInfo(@PathVariable("jobId") Long jobId)
    {
        return success(sysJobSpecificationService.selectSysJobSpecificationByJobId(jobId));
    }

    /**
     * 新增作业规范
     */
    @PreAuthorize("@ss.hasPermi('system:specification:add')")
    @Log(title = "作业规范", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysJobSpecification sysJobSpecification)
    {
        Long userId = SecurityUtils.getUserId();
        sysJobSpecification.setUserId(userId);
        sysJobSpecificationService.insertSysJobSpecification(sysJobSpecification);
        for (UploadFileList file:sysJobSpecification.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysJobSpecification.getJobId());
            sysManageFile.setModuleType((long)3);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 修改作业规范
     */
    @PreAuthorize("@ss.hasPermi('system:specification:edit')")
    @Log(title = "作业规范", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysJobSpecification sysJobSpecification)
    {
        sysJobSpecificationService.updateSysJobSpecification(sysJobSpecification);
        SysManageFile sysManageFileObj = new SysManageFile();
        sysManageFileObj.setModuleId(sysJobSpecification.getJobId());
        sysManageFileObj.setModuleType((long)3);
        sysManageFileService.deleteSysManageFileByModuleIdAndType(sysManageFileObj);
        for (UploadFileList file:sysJobSpecification.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysJobSpecification.getJobId());
            sysManageFile.setModuleType((long)3);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 删除作业规范
     */
    @PreAuthorize("@ss.hasPermi('system:specification:remove')")
    @Log(title = "作业规范", businessType = BusinessType.DELETE)
	@DeleteMapping("/{jobIds}")
    public AjaxResult remove(@PathVariable Long[] jobIds)
    {
        JobSpecificationParams jobSpecificationParams = new JobSpecificationParams();
        jobSpecificationParams.setJobIds(jobIds);
        jobSpecificationParams.setUserId(SecurityUtils.getUserId());
        return toAjax(sysJobSpecificationService.deleteSysJobSpecificationByJobIds(jobSpecificationParams));
    }
}
