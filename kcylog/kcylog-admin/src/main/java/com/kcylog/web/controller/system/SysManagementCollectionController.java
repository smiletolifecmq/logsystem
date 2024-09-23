package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysManagementCollection;
import com.kcylog.system.service.ISysManagementCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 应收账款Controller
 * 
 * @author ruoyi
 * @date 2024-09-23
 */
@RestController
@RequestMapping("/system/collection")
public class SysManagementCollectionController extends BaseController
{
    @Autowired
    private ISysManagementCollectionService sysManagementCollectionService;

    /**
     * 查询应收账款列表
     */
    @PreAuthorize("@ss.hasPermi('system:collection:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementCollection sysManagementCollection)
    {
        startPage();
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        for (SysManagementCollection obj : list){
            Date ysKprq = obj.getYsKprq();
            // 将 Date 转换为 LocalDate
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 计算两个日期之间的差异
            Period period = Period.between(ysKprqLocalDate, currentDate);
            // 获取年份差异
            int years = period.getYears();
            // 判断时间段
            if (years < 1) {
                obj.setYsZl("1年以内");
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
            } else {
                obj.setYsZl("3年以上");
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出应收账款列表
     */
    @PreAuthorize("@ss.hasPermi('system:collection:export')")
    @Log(title = "应收账款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysManagementCollection sysManagementCollection)
    {
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        for (SysManagementCollection obj : list){
            Date ysKprq = obj.getYsKprq();
            // 将 Date 转换为 LocalDate
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 计算两个日期之间的差异
            Period period = Period.between(ysKprqLocalDate, currentDate);
            // 获取年份差异
            int years = period.getYears();
            // 判断时间段
            if (years < 1) {
                obj.setYsZl("1年以内");
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
            } else {
                obj.setYsZl("3年以上");
            }
        }
        ExcelUtil<SysManagementCollection> util = new ExcelUtil<SysManagementCollection>(SysManagementCollection.class);
        util.exportExcel(response, list, "应收账款数据");
    }

    /**
     * 获取应收账款详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:collection:query')")
    @GetMapping(value = "/{ysId}")
    public AjaxResult getInfo(@PathVariable("ysId") Long ysId)
    {
        return success(sysManagementCollectionService.selectSysManagementCollectionByYsId(ysId));
    }

    /**
     * 新增应收账款
     */
    @PreAuthorize("@ss.hasPermi('system:collection:add')")
    @Log(title = "应收账款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysManagementCollection sysManagementCollection)
    {
        return toAjax(sysManagementCollectionService.insertSysManagementCollection(sysManagementCollection));
    }

    /**
     * 修改应收账款
     */
    @PreAuthorize("@ss.hasPermi('system:collection:edit')")
    @Log(title = "应收账款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementCollection sysManagementCollection)
    {
        return toAjax(sysManagementCollectionService.updateSysManagementCollection(sysManagementCollection));
    }

    /**
     * 删除应收账款
     */
    @PreAuthorize("@ss.hasPermi('system:collection:remove')")
    @Log(title = "应收账款", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ysIds}")
    public AjaxResult remove(@PathVariable Long[] ysIds)
    {
        return toAjax(sysManagementCollectionService.deleteSysManagementCollectionByYsIds(ysIds));
    }
}
