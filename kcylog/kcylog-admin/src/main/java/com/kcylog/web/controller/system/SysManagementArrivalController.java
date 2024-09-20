package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManagementArrival;
import com.kcylog.system.domain.SysManagementInvoicing;
import com.kcylog.system.service.ISysManagementArrivalService;
import com.kcylog.system.service.ISysManagementInvoicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 经营到账统计Controller
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
@RestController
@RequestMapping("/system/arrival")
public class SysManagementArrivalController extends BaseController
{
    @Autowired
    private ISysManagementArrivalService sysManagementArrivalService;

    @Autowired
    private ISysManagementInvoicingService sysManagementInvoicingService;

    /**
     * 查询经营到账统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:arrival:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementArrival sysManagementArrival)
    {
        sysManagementArrival.setDzFzr(SecurityUtils.getUsername());
        startPage();
        List<SysManagementArrival> list = sysManagementArrivalService.selectSysManagementArrivalList(sysManagementArrival);
        return getDataTable(list);
    }

    /**
     * 导出经营到账统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:arrival:export')")
    @Log(title = "经营到账统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementArrival sysManagementArrival)
    {
        sysManagementArrival.setDzFzr(SecurityUtils.getUsername());
        List<SysManagementArrival> list = sysManagementArrivalService.selectSysManagementArrivalList(sysManagementArrival);
        int num = 0;
        for (SysManagementArrival obj : list){
            num ++;
            obj.setNum(num);
        }
        ExcelUtil<SysManagementArrival> util = new ExcelUtil<SysManagementArrival>(SysManagementArrival.class);
        util.exportExcel(response, list, "经营到账统计数据");
    }

    /**
     * 获取经营到账统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:arrival:query')")
    @GetMapping(value = "/{dzId}")
    public AjaxResult getInfo(@PathVariable("dzId") Long dzId)
    {
        return success(sysManagementArrivalService.selectSysManagementArrivalByDzId(dzId));
    }

    /**
     * 新增经营到账统计
     */
    @PreAuthorize("@ss.hasPermi('system:arrival:add')")
    @Log(title = "经营到账统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysManagementArrival sysManagementArrival)
    {
        sysManagementArrival.setDzFzr(SecurityUtils.getUsername());
        SysManagementInvoicing invoicing = sysManagementInvoicingService.selectSysManagementInvoicingByKpFPH(sysManagementArrival.getDzFph().trim());
        if(invoicing == null){
            return error("新增失败，发票号:" + sysManagementArrival.getDzFph().trim() +"在开票模块中不存在，请现在开票模块中补充该票号开票记录～");
        }
        sysManagementArrival.setDzFph(sysManagementArrival.getDzFph().trim());
        return toAjax(sysManagementArrivalService.insertSysManagementArrival(sysManagementArrival));
    }

    /**
     * 修改经营到账统计
     */
    @PreAuthorize("@ss.hasPermi('system:arrival:edit')")
    @Log(title = "经营到账统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementArrival sysManagementArrival)
    {
        return toAjax(sysManagementArrivalService.updateSysManagementArrival(sysManagementArrival));
    }

    /**
     * 删除经营到账统计
     */
    @PreAuthorize("@ss.hasPermi('system:arrival:remove')")
    @Log(title = "经营到账统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dzIds}")
    public AjaxResult remove(@PathVariable Long[] dzIds)
    {
        return toAjax(sysManagementArrivalService.deleteSysManagementArrivalByDzIds(dzIds));
    }

    @GetMapping(value = "/getArrivalByDzFph/{dzFph}")
    public TableDataInfo getArrivalByDzFph(@PathVariable("dzFph") String dzFph)
    {
        return getDataTable(sysManagementArrivalService.selectSysManagementArrivalByDzFph(dzFph));
    }
}
