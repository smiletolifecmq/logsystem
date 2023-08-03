package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysSubcontractProcessConfigInfo;
import com.kcylog.system.service.ISysSubcontractProcessConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 流程配置过程Controller
 * 
 * @author ruoyi
 * @date 2023-08-03
 */
@RestController
@RequestMapping("/system/subcontractProcessConfigInfo")
public class SysSubcontractProcessConfigInfoController extends BaseController
{
    @Autowired
    private ISysSubcontractProcessConfigInfoService sysSubcontractProcessConfigInfoService;

    /**
     * 查询流程配置过程列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfigInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        startPage();
        List<SysSubcontractProcessConfigInfo> list = sysSubcontractProcessConfigInfoService.selectSysSubcontractProcessConfigInfoList(sysSubcontractProcessConfigInfo);
        return getDataTable(list);
    }

    /**
     * 导出流程配置过程列表
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfigInfo:export')")
    @Log(title = "流程配置过程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        List<SysSubcontractProcessConfigInfo> list = sysSubcontractProcessConfigInfoService.selectSysSubcontractProcessConfigInfoList(sysSubcontractProcessConfigInfo);
        ExcelUtil<SysSubcontractProcessConfigInfo> util = new ExcelUtil<SysSubcontractProcessConfigInfo>(SysSubcontractProcessConfigInfo.class);
        util.exportExcel(response, list, "流程配置过程数据");
    }

    /**
     * 获取流程配置过程详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfigInfo:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId)
    {
        return success(sysSubcontractProcessConfigInfoService.selectSysSubcontractProcessConfigInfoByInfoId(infoId));
    }

    /**
     * 新增流程配置过程
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfigInfo:add')")
    @Log(title = "流程配置过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        return toAjax(sysSubcontractProcessConfigInfoService.insertSysSubcontractProcessConfigInfo(sysSubcontractProcessConfigInfo));
    }

    /**
     * 修改流程配置过程
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfigInfo:edit')")
    @Log(title = "流程配置过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysSubcontractProcessConfigInfo sysSubcontractProcessConfigInfo)
    {
        return toAjax(sysSubcontractProcessConfigInfoService.updateSysSubcontractProcessConfigInfo(sysSubcontractProcessConfigInfo));
    }

    /**
     * 删除流程配置过程
     */
    @PreAuthorize("@ss.hasPermi('system:subcontractProcessConfigInfo:remove')")
    @Log(title = "流程配置过程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(sysSubcontractProcessConfigInfoService.deleteSysSubcontractProcessConfigInfoByInfoIds(infoIds));
    }
}
