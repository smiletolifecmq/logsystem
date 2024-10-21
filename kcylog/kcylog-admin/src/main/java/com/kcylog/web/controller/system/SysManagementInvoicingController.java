package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.domain.SysManagementCollection;
import com.kcylog.system.domain.SysManagementInvoicing;
import com.kcylog.system.service.ISysManagementCollectionService;
import com.kcylog.system.service.ISysManagementInvoicingService;
import com.kcylog.web.controller.common.ExcelUtilNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
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

    @Autowired
    private ISysManagementCollectionService sysManagementCollectionService;
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
        ExcelUtilNum<SysManagementInvoicing> util = new ExcelUtilNum<SysManagementInvoicing>(SysManagementInvoicing.class);
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
    @Transactional
    public AjaxResult add(@RequestBody SysManagementInvoicing sysManagementInvoicing)
    {
        if (sysManagementInvoicing.getKpFzr() == null){
            sysManagementInvoicing.setKpFzr(SecurityUtils.getUsername());
        }
        sysManagementInvoicing.setKpFzr(sysManagementInvoicing.getKpFzr().trim());
        sysManagementInvoicing.setKpFph(sysManagementInvoicing.getKpFph().trim());
        if (sysManagementInvoicing.getKpHcph() != null){
            sysManagementInvoicing.setKpHcph(sysManagementInvoicing.getKpHcph().trim());
        }
        SysManagementInvoicing invoicing = sysManagementInvoicingService.selectSysManagementInvoicingByKpFPH(sysManagementInvoicing.getKpFph());
        if(invoicing != null){
            return error("新增失败，发票号:" + sysManagementInvoicing.getKpFph() +"已存在");
        }
        if (sysManagementInvoicing.getKpType() == 2){
            SysManagementInvoicing invoicingHc = sysManagementInvoicingService.selectSysManagementInvoicingByKpFPH(sysManagementInvoicing.getKpHcph());
            if(invoicingHc == null){
                return error("新增失败，被红冲发票号:" + sysManagementInvoicing.getKpHcph() +"在开票记录中不存在，请先补充该开票信息~");
            }
        }
        SysManagementCollection sysManagementCollection = sysManagementCollectionService.selectSysManagementCollectionByYsFph(sysManagementInvoicing.getKpFph());
        if (sysManagementCollection == null && sysManagementInvoicing.getKpType() == 1){
            // 开正常票逻辑
            SysManagementCollection newCollection = new SysManagementCollection();
            newCollection.setYsHtmc(sysManagementInvoicing.getKpHtmc());
            newCollection.setYsHtbh(sysManagementInvoicing.getKpHtbh());
            newCollection.setYsFzbm(sysManagementInvoicing.getKpFzbm());
            newCollection.setYsFzr(sysManagementInvoicing.getKpFzr());
            newCollection.setYsKhmc(sysManagementInvoicing.getKpKhmc());
            newCollection.setYsKhfl(sysManagementInvoicing.getKpKhfl());
            newCollection.setYsHtje(sysManagementInvoicing.getKpHtje());
            newCollection.setYsKprq(sysManagementInvoicing.getKpKprq());
            newCollection.setYsKpje(sysManagementInvoicing.getKpKpje());
            newCollection.setYsYdzje(BigDecimal.ZERO);
            newCollection.setYsWdzje(sysManagementInvoicing.getKpKpje());
            newCollection.setYsFph(sysManagementInvoicing.getKpFph());
            sysManagementCollectionService.insertSysManagementCollection(newCollection);
        }

        if (sysManagementInvoicing.getKpType() == 2){
            SysManagementCollection sysCollection = sysManagementCollectionService.selectSysManagementCollectionByYsFph(sysManagementInvoicing.getKpHcph());
            if (sysCollection != null){
                if (sysCollection.getYsKpje().abs().compareTo(sysManagementInvoicing.getKpKpje().abs()) != 0){
                    return error("红冲金额绝对值不等于被红冲的金额~");
                }
                // 开红冲票逻辑
                SysManagementCollection newCollection = new SysManagementCollection();
                newCollection.setYsId(sysCollection.getYsId());
                newCollection.setYsYdzje(sysManagementInvoicing.getKpKpje().abs());
                newCollection.setYsWdzje(BigDecimal.ZERO);
                newCollection.setYsStatus((long)2);
                sysManagementCollectionService.updateCollectionDzInfo(newCollection);
            }
        }
        return toAjax(sysManagementInvoicingService.insertSysManagementInvoicing(sysManagementInvoicing));
    }

    /**
     * 修改经营开票统计
     */
    @PreAuthorize("@ss.hasPermi('system:invoicing:edit')")
    @Log(title = "经营开票统计", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody SysManagementInvoicing sysManagementInvoicing)
    {
        if (sysManagementInvoicing.getKpFzr() == null){
            sysManagementInvoicing.setKpFzr(SecurityUtils.getUsername());
        }
        SysManagementCollection newCollection = new SysManagementCollection();
        newCollection.setYsHtmc(sysManagementInvoicing.getKpHtmc());
        newCollection.setYsHtbh(sysManagementInvoicing.getKpHtbh());
        newCollection.setYsFzbm(sysManagementInvoicing.getKpFzbm());
        newCollection.setYsFzr(sysManagementInvoicing.getKpFzr().trim());
        newCollection.setYsKhmc(sysManagementInvoicing.getKpKhmc());
        newCollection.setYsKhfl(sysManagementInvoicing.getKpKhfl());
        newCollection.setYsHtje(sysManagementInvoicing.getKpHtje());
        newCollection.setYsKprq(sysManagementInvoicing.getKpKprq());
        newCollection.setYsFph(sysManagementInvoicing.getKpFph());
        sysManagementCollectionService.updateSysManagementCollectionForFph(newCollection);
        sysManagementInvoicing.setKpFzr(sysManagementInvoicing.getKpFzr().trim());
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

    @PreAuthorize("@ss.hasPermi('system:invoicing:list')")
    @GetMapping("/listAll")
    public TableDataInfo listAll(SysManagementInvoicing sysManagementInvoicing)
    {
        startPage();
        List<SysManagementInvoicing> list = sysManagementInvoicingService.selectSysManagementInvoicingList(sysManagementInvoicing);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:invoicing:export')")
    @Log(title = "经营开票统计", businessType = BusinessType.EXPORT)
    @PostMapping("/exportAll")
    public void exportAll(HttpServletResponse response, SysManagementInvoicing sysManagementInvoicing)
    {
        List<SysManagementInvoicing> list = sysManagementInvoicingService.selectSysManagementInvoicingList(sysManagementInvoicing);
        int num = 0;
        for (SysManagementInvoicing obj : list){
            num ++;
            obj.setNum(num);
        }
        ExcelUtilNum<SysManagementInvoicing> util = new ExcelUtilNum<SysManagementInvoicing>(SysManagementInvoicing.class);
        util.exportExcel(response, list, "经营开票统计数据");
    }
}
