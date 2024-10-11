package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManagementResponsibilities;
import com.kcylog.system.service.ISysManagementResponsibilitiesService;
import com.kcylog.system.service.ISysManagementResponsibilitiesInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 责权发生制Controller
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@RestController
@RequestMapping("/system/responsibilities")
public class SysManagementResponsibilitiesController extends BaseController
{
    @Autowired
    private ISysManagementResponsibilitiesService sysManagementResponsibilitiesService;

    @Autowired
    private ISysManagementResponsibilitiesInfoService sysManagementResponsibilitiesInfoService;
    /**
     * 查询责权发生制列表
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilities:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementResponsibilities sysManagementResponsibilities)
    {
        startPage();
        List<SysManagementResponsibilities> list = sysManagementResponsibilitiesService.selectSysManagementResponsibilitiesList(sysManagementResponsibilities);
        return getDataTable(list);
    }

    /**
     * 导出责权发生制列表
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilities:export')")
    @Log(title = "责权发生制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementResponsibilities sysManagementResponsibilities)
    {
        List<SysManagementResponsibilities> list = sysManagementResponsibilitiesService.selectSysManagementResponsibilitiesList(sysManagementResponsibilities);
        int num = 0;
        for (SysManagementResponsibilities obj : list){
            num ++;
            obj.setNum(num);
        }
        ExcelUtil<SysManagementResponsibilities> util = new ExcelUtil<SysManagementResponsibilities>(SysManagementResponsibilities.class);
        util.exportExcel(response, list, "责权发生制数据");
    }

    /**
     * 获取责权发生制详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilities:query')")
    @GetMapping(value = "/{zqId}")
    public AjaxResult getInfo(@PathVariable("zqId") Long zqId)
    {
        return success(sysManagementResponsibilitiesService.selectSysManagementResponsibilitiesByZqId(zqId));
    }

    /**
     * 新增责权发生制
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilities:add')")
    @Log(title = "责权发生制", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysManagementResponsibilities sysManagementResponsibilities)
    {
        sysManagementResponsibilities.setZqZrr(SecurityUtils.getUsername());
        return toAjax(sysManagementResponsibilitiesService.insertSysManagementResponsibilities(sysManagementResponsibilities));
    }

    /**
     * 修改责权发生制
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilities:edit')")
    @Log(title = "责权发生制", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementResponsibilities sysManagementResponsibilities)
    {
        return toAjax(sysManagementResponsibilitiesService.updateSysManagementResponsibilities(sysManagementResponsibilities));
    }

    /**
     * 删除责权发生制
     */
    @PreAuthorize("@ss.hasPermi('system:responsibilities:remove')")
    @Log(title = "责权发生制", businessType = BusinessType.DELETE)
	@DeleteMapping("/{zqIds}")
    public AjaxResult remove(@PathVariable Long[] zqIds)
    {
        return toAjax(sysManagementResponsibilitiesService.deleteSysManagementResponsibilitiesByZqIds(zqIds));
    }

    @GetMapping(value = "/info/{zqId}")
    public AjaxResult getResponsibilitiesInfo(@PathVariable("zqId") Long zqId)
    {
        return success(sysManagementResponsibilitiesInfoService.selectSysManagementResponsibilitiesInfoByZqId(zqId));
    }
}
