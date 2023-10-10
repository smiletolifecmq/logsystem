package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysCarReviewConfig;
import com.kcylog.system.service.ISysCarReviewConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 车辆审核流程配置Controller
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/system/carReviewConfig")
public class SysCarReviewConfigController extends BaseController
{
    @Autowired
    private ISysCarReviewConfigService sysCarReviewConfigService;

    /**
     * 查询车辆审核流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCarReviewConfig sysCarReviewConfig)
    {
        startPage();
        List<SysCarReviewConfig> list = sysCarReviewConfigService.selectSysCarReviewConfigList(sysCarReviewConfig);
        return getDataTable(list);
    }

    /**
     * 导出车辆审核流程配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @Log(title = "车辆审核流程配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCarReviewConfig sysCarReviewConfig)
    {
        List<SysCarReviewConfig> list = sysCarReviewConfigService.selectSysCarReviewConfigList(sysCarReviewConfig);
        ExcelUtil<SysCarReviewConfig> util = new ExcelUtil<SysCarReviewConfig>(SysCarReviewConfig.class);
        util.exportExcel(response, list, "车辆审核流程配置数据");
    }

    /**
     * 获取车辆审核流程配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{reviewConfigId}")
    public AjaxResult getInfo(@PathVariable("reviewConfigId") Long reviewConfigId)
    {
        return success(sysCarReviewConfigService.selectSysCarReviewConfigByReviewConfigId(reviewConfigId));
    }

    /**
     * 新增车辆审核流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @Log(title = "车辆审核流程配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCarReviewConfig sysCarReviewConfig)
    {
        return toAjax(sysCarReviewConfigService.insertSysCarReviewConfig(sysCarReviewConfig));
    }

    /**
     * 修改车辆审核流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "车辆审核流程配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCarReviewConfig sysCarReviewConfig)
    {
        return toAjax(sysCarReviewConfigService.updateSysCarReviewConfig(sysCarReviewConfig));
    }

    /**
     * 删除车辆审核流程配置
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "车辆审核流程配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewConfigIds}")
    public AjaxResult remove(@PathVariable Long[] reviewConfigIds)
    {
        return toAjax(sysCarReviewConfigService.deleteSysCarReviewConfigByReviewConfigIds(reviewConfigIds));
    }
}
