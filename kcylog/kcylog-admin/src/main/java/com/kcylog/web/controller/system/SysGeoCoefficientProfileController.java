package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoCoefficientProfile;
import com.kcylog.system.domain.SysManageFile;
import com.kcylog.system.domain.UploadFileList;
import com.kcylog.system.service.ISysGeoCoefficientProfileService;
import com.kcylog.system.service.ISysManageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系数存档Controller
 * 
 * @author ruoyi
 * @date 2023-12-19
 */
@RestController
@RequestMapping("/system/geoCoefficientProfile")
public class SysGeoCoefficientProfileController extends BaseController
{
    @Autowired
    private ISysGeoCoefficientProfileService sysGeoCoefficientProfileService;

    @Autowired
    private ISysManageFileService sysManageFileService;

    /**
     * 查询系数存档列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        startPage();
        List<SysGeoCoefficientProfile> list = sysGeoCoefficientProfileService.selectSysGeoCoefficientProfileList(sysGeoCoefficientProfile);
        return getDataTable(list);
    }

    /**
     * 导出系数存档列表
     */
    @Log(title = "系数存档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        List<SysGeoCoefficientProfile> list = sysGeoCoefficientProfileService.selectSysGeoCoefficientProfileList(sysGeoCoefficientProfile);
        ExcelUtil<SysGeoCoefficientProfile> util = new ExcelUtil<SysGeoCoefficientProfile>(SysGeoCoefficientProfile.class);
        util.exportExcel(response, list, "系数存档数据");
    }

    /**
     * 获取系数存档详细信息
     */
    @GetMapping(value = "/{profileId}")
    public AjaxResult getInfo(@PathVariable("profileId") Long profileId)
    {
        return success(sysGeoCoefficientProfileService.selectSysGeoCoefficientProfileByProfileId(profileId));
    }

    /**
     * 新增系数存档
     */
    @Log(title = "系数存档", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        sysGeoCoefficientProfileService.insertSysGeoCoefficientProfile(sysGeoCoefficientProfile);
        for (UploadFileList file:sysGeoCoefficientProfile.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysGeoCoefficientProfile.getProfileId());
            sysManageFile.setModuleType((long)7);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 修改系数存档
     */
    @Log(title = "系数存档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoCoefficientProfile sysGeoCoefficientProfile)
    {
        sysGeoCoefficientProfileService.updateSysGeoCoefficientProfile(sysGeoCoefficientProfile);
        SysManageFile sysManageFileObj = new SysManageFile();
        sysManageFileObj.setModuleId(sysGeoCoefficientProfile.getProfileId());
        sysManageFileObj.setModuleType((long)7);
        sysManageFileService.deleteSysManageFileByModuleIdAndType(sysManageFileObj);
        for (UploadFileList file:sysGeoCoefficientProfile.getUploadFileList()){
            SysManageFile sysManageFile = new SysManageFile();
            sysManageFile.setNewFileName(file.getNewFileName());
            sysManageFile.setOldFileName(file.getOldFileName());
            sysManageFile.setUrl(file.getUrl());
            sysManageFile.setFileName(file.getFileName());
            sysManageFile.setModuleId(sysGeoCoefficientProfile.getProfileId());
            sysManageFile.setModuleType((long)7);
            sysManageFileService.insertSysManageFile(sysManageFile);
        }
        return toAjax(1);
    }

    /**
     * 删除系数存档
     */
    @Log(title = "系数存档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{profileIds}")
    public AjaxResult remove(@PathVariable Long[] profileIds)
    {
        return toAjax(sysGeoCoefficientProfileService.deleteSysGeoCoefficientProfileByProfileIds(profileIds));
    }
}
