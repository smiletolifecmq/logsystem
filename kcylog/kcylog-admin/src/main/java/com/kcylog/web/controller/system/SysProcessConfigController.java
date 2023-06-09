package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysProcessConfig;
import com.kcylog.system.service.ISysProcessConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 流程配置Controller
 * 
 * @author ruoyi
 * @date 2023-06-08
 */
@RestController
@RequestMapping("/system/processConfig")
public class SysProcessConfigController extends BaseController
{
    @Autowired
    private ISysProcessConfigService sysProcessConfigService;

    /**
     * 查询流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:processConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProcessConfig sysProcessConfig)
    {
        startPage();
        List<SysProcessConfig> list = sysProcessConfigService.selectSysProcessConfigList(sysProcessConfig);
        return getDataTable(list);
    }

    /**
     * 导出流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:processConfig:export')")
    @Log(title = "流程配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProcessConfig sysProcessConfig)
    {
        List<SysProcessConfig> list = sysProcessConfigService.selectSysProcessConfigList(sysProcessConfig);
        ExcelUtil<SysProcessConfig> util = new ExcelUtil<SysProcessConfig>(SysProcessConfig.class);
        util.exportExcel(response, list, "流程配置数据");
    }

    /**
     * 获取流程配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:processConfig:query')")
    @GetMapping(value = "/{processConfigId}")
    public AjaxResult getInfo(@PathVariable("processConfigId") String processConfigId)
    {
        return success(sysProcessConfigService.selectSysProcessConfigByProcessConfigId(processConfigId));
    }

    /**
     * 新增流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:processConfig:add')")
    @Log(title = "流程配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProcessConfig sysProcessConfig)
    {
        return toAjax(sysProcessConfigService.insertSysProcessConfig(sysProcessConfig));
    }

    /**
     * 修改流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:processConfig:edit')")
    @Log(title = "流程配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProcessConfig sysProcessConfig)
    {
        return toAjax(sysProcessConfigService.updateSysProcessConfig(sysProcessConfig));
    }

    /**
     * 删除流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:processConfig:remove')")
    @Log(title = "流程配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{processConfigIds}")
    public AjaxResult remove(@PathVariable String[] processConfigIds)
    {
        return toAjax(sysProcessConfigService.deleteSysProcessConfigByProcessConfigIds(processConfigIds));
    }
}
