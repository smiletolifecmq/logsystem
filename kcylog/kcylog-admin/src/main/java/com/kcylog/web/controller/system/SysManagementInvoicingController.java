package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManagementInvoicing;
import com.kcylog.system.service.ISysManagementInvoicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 经营开票统计Controller
 * 
 * @author ruoyi
 * @date 2024-09-20
 */
@RestController
@RequestMapping("/system/invoicing")
public class SysManagementInvoicingController extends BaseController
{
    @Autowired
    private ISysManagementInvoicingService sysManagementInvoicingService;

    /**
     * 查询经营开票统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementInvoicing sysManagementInvoicing)
    {
        sysManagementInvoicing.setKpFzr(SecurityUtils.getUsername());
        startPage();
        List<SysManagementInvoicing> list = sysManagementInvoicingService.selectSysManagementInvoicingList(sysManagementInvoicing);
        return getDataTable(list);
    }

    /**
     * 导出经营开票统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:export')")
    @Log(title = "经营开票统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementInvoicing sysManagementInvoicing)
    {
        sysManagementInvoicing.setKpFzr(SecurityUtils.getUsername());
        List<SysManagementInvoicing> list = sysManagementInvoicingService.selectSysManagementInvoicingList(sysManagementInvoicing);
        int num = 0;
        for (SysManagementInvoicing obj : list){
            num ++;
            obj.setNum(num);
        }
        ExcelUtil<SysManagementInvoicing> util = new ExcelUtil<SysManagementInvoicing>(SysManagementInvoicing.class);
        util.exportExcel(response, list, "经营开票统计数据");
    }

    /**
     * 获取经营开票统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:query')")
    @GetMapping(value = "/{kpId}")
    public AjaxResult getInfo(@PathVariable("kpId") Long kpId)
    {
        return success(sysManagementInvoicingService.selectSysManagementInvoicingByKpId(kpId));
    }

    /**
     * 新增经营开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:add')")
    @Log(title = "经营开票统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysManagementInvoicing sysManagementInvoicing)
    {
        sysManagementInvoicing.setKpFzr(SecurityUtils.getUsername());
        sysManagementInvoicing.setKpFph(sysManagementInvoicing.getKpFph().trim());
        if (sysManagementInvoicing.getKpHcph() != null){
            sysManagementInvoicing.setKpHcph(sysManagementInvoicing.getKpHcph().trim());
        }
        SysManagementInvoicing invoicing = sysManagementInvoicingService.selectSysManagementInvoicingByKpFPH(sysManagementInvoicing.getKpFph());
        if(invoicing != null){
            return error("新增失败，发票号:" + sysManagementInvoicing.getKpFph() +"已存在");
        }
        return toAjax(sysManagementInvoicingService.insertSysManagementInvoicing(sysManagementInvoicing));
    }

    /**
     * 修改经营开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:edit')")
    @Log(title = "经营开票统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementInvoicing sysManagementInvoicing)
    {
        return toAjax(sysManagementInvoicingService.updateSysManagementInvoicing(sysManagementInvoicing));
    }

    /**
     * 删除经营开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:remove')")
    @Log(title = "经营开票统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{kpIds}")
    public AjaxResult remove(@PathVariable Long[] kpIds)
    {
        return toAjax(sysManagementInvoicingService.deleteSysManagementInvoicingByKpIds(kpIds));
    }

    @GetMapping(value = "/getByKpFph/{kpFph}")
    public AjaxResult getByKpFph(@PathVariable("kpFph") String kpFph)
    {
        return success(sysManagementInvoicingService.selectSysManagementInvoicingByKpFPH(kpFph));
    }
}
