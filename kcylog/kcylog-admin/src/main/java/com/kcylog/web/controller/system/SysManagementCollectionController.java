package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.system.domain.SysManagementCollection;
import com.kcylog.system.service.ISysManagementCollectionService;
import com.kcylog.web.controller.common.ExcelManySheetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            if (sysManagementCollection.getYsKprqCs() != null){
                currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            }
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
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:collection:export')")
    @Log(title = "应收账款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(HttpServletResponse response, SysManagementCollection sysManagementCollection)
    {
        if (sysManagementCollection.getYsKprqCs() == null){
            return error("未选择截止日期");
        }
        // 获取上次上报时间
        LocalDate lastDayOfLastMonth = LocalDate.parse(sysManagementCollection.getYsKprqLast());

        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        List<SysManagementCollection> withinOneYearList = new ArrayList<>();
        List<SysManagementCollection> oneToThreeYearList = new ArrayList<>();
        List<SysManagementCollection> overThreeYearList = new ArrayList<>();
        List<SysManagementCollection> toThreeYearList = new ArrayList<>();

        for (SysManagementCollection obj : list) {
            Date ysKprq = obj.getYsKprq();
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            Period period = Period.between(ysKprqLocalDate, currentDate);
            int years = period.getYears();
            if (years < 1) {
                obj.setYsZl("1年以内");
                withinOneYearList.add(obj);
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
                oneToThreeYearList.add(obj);
            } else {
                obj.setYsZl("3年以上");
                overThreeYearList.add(obj);
            }
            Period lastPeriod = Period.between(ysKprqLocalDate, lastDayOfLastMonth);
            int lastYears = lastPeriod.getYears();

            if (years >= 3 && lastYears < 3){
                toThreeYearList.add(obj);
            }

        }
        // 定义输出格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日");
        // 格式化为指定字符串
        LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
        String formattedDate = currentDate.format(formatter);

        Map<String, List<SysManagementCollection>> map = new LinkedHashMap<>();
        map.put("总表", list);
        map.put("1年内清单", withinOneYearList);
        map.put("1年至3年清单", oneToThreeYearList);
        map.put("3年以上清单", overThreeYearList);
        map.put("本上报周期新增的3年期以上清单", toThreeYearList);

        ExcelManySheetUtil<SysManagementCollection> util = new ExcelManySheetUtil<>(SysManagementCollection.class);
        util.exportExcel(response, map,"截止"+formattedDate+"应收账款（单位：元）");
        return null;
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

    @PreAuthorize("@ss.hasPermi('system:collection:list')")
    @GetMapping("/ydz")
    public TableDataInfo listYdz(SysManagementCollection sysManagementCollection)
    {
        startPage();
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionListYdz(sysManagementCollection);
        for (SysManagementCollection obj : list){
            Date ysKprq = obj.getYsKprq();
            // 将 Date 转换为 LocalDate
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            if (sysManagementCollection.getYsKprqCs() != null){
                currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            }
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
}
