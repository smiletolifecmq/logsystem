package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysSubcontractProcessConfig;
import com.kcylog.system.service.ISysSubcontractProcessConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分包审核流程配置Controller
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/system/subcontractProcessConfig")
public class SysSubcontractProcessConfigController extends BaseController
{
    @Autowired
    private ISysSubcontractProcessConfigService sysSubcontractProcessConfigService;

    /**
     * 查询分包审核流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        startPage();
        List<SysSubcontractProcessConfig> list = sysSubcontractProcessConfigService.selectSysSubcontractProcessConfigList(sysSubcontractProcessConfig);
        return getDataTable(list);
    }

    /**
     * 导出分包审核流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfig:export')")
    @Log(title = "分包审核流程配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        List<SysSubcontractProcessConfig> list = sysSubcontractProcessConfigService.selectSysSubcontractProcessConfigList(sysSubcontractProcessConfig);
        ExcelUtil<SysSubcontractProcessConfig> util = new ExcelUtil<SysSubcontractProcessConfig>(SysSubcontractProcessConfig.class);
        util.exportExcel(response, list, "分包审核流程配置数据");
    }

    /**
     * 获取分包审核流程配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfig:query')")
    @GetMapping(value = "/{processConfigId}")
    public AjaxResult getInfo(@PathVariable("processConfigId") Long processConfigId)
    {
        return success(sysSubcontractProcessConfigService.selectSysSubcontractProcessConfigByProcessConfigId(processConfigId));
    }

    /**
     * 新增分包审核流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfig:add')")
    @Log(title = "分包审核流程配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        return toAjax(sysSubcontractProcessConfigService.insertSysSubcontractProcessConfig(sysSubcontractProcessConfig));
    }

    /**
     * 修改分包审核流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfig:edit')")
    @Log(title = "分包审核流程配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSubcontractProcessConfig sysSubcontractProcessConfig)
    {
        return toAjax(sysSubcontractProcessConfigService.updateSysSubcontractProcessConfig(sysSubcontractProcessConfig));
    }

    /**
     * 删除分包审核流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfig:remove')")
    @Log(title = "分包审核流程配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processConfigIds}")
    public AjaxResult remove(@PathVariable Long[] processConfigIds)
    {
        return toAjax(sysSubcontractProcessConfigService.deleteSysSubcontractProcessConfigByProcessConfigIds(processConfigIds));
    }
}
