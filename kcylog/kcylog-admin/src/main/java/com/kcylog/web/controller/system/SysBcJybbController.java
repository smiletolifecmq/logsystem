package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysBcGcbb;
import com.kcylog.system.domain.SysBcJybb;
import com.kcylog.system.service.ISysBcGcbbService;
import com.kcylog.system.service.ISysBcJybbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 百川分院经营金额Controller
 * 
 * @author ruoyi
 * @date 2025-07-07
 */
@RestController
@RequestMapping("/system/jybb")
public class SysBcJybbController extends BaseController
{
    @Autowired
    private ISysBcJybbService sysBcJybbService;

    @Autowired
    private ISysBcGcbbService sysBcGcbbService;

    /**
     * 查询百川分院经营金额列表
     */
    @PreAuthorize("@ss.hasPermi('system:jybb:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBcJybb sysBcJybb)
    {
        sysBcJybb.setYear(sysBcJybb.getYear());
        List<SysBcJybb> list = sysBcJybbService.selectSysBcJybbList(sysBcJybb);
        SysBcGcbb sysBcGcbb = new SysBcGcbb();
        sysBcGcbb.setYear(sysBcJybb.getYear());
        List<SysBcGcbb> gcbbList = sysBcGcbbService.selectSysBcGcbbTj(sysBcGcbb);
        for(SysBcJybb obj1 : list){
            for(SysBcGcbb obj2 : gcbbList){
                if (Objects.equals(obj1.getOrgName(), obj2.getBranchOrgName())){
                    obj1.setTotalCount(obj2.getTotalCount());
                    obj1.setWeekCount(obj2.getWeekCount());
                    obj1.setSellYearCount(obj2.getSellYearCount());
                    obj1.setSellWeekCount(obj2.getSellWeekCount());
                    obj1.setPlanYearCount(obj2.getPlanYearCount());
                    obj1.setPlanWeekCount(obj2.getPlanWeekCount());
                    obj1.setPipeCctvCount(obj2.getPipeCctvCount());
                    obj1.setPipeOtherCount(obj2.getPipeOtherCount());
                    obj1.setGovYearEarthControlCount(obj2.getGovYearEarthControlCount());
                    obj1.setGovYearRoadCount(obj2.getGovYearRoadCount());
                    obj1.setGovWeekEarthControlCount(obj2.getGovWeekEarthControlCount());
                    obj1.setGovWeekRoadCount(obj2.getGovWeekRoadCount());
                    obj1.setJiCount(obj2.getJiCount());
                    obj1.setJiCountMonth(obj2.getJiCountMonth());
                    obj1.setPipeCctvCountMonth(obj2.getPipeCctvCountMonth());
                    obj1.setPipeOtherCountMonth(obj2.getPipeOtherCountMonth());
                    continue;
                }
            }
        }

        for (Iterator<SysBcJybb> iterator = list.iterator(); iterator.hasNext(); ) {
            SysBcJybb item = iterator.next();
            if ("福清分公司".equals(item.getOrgName())) {
                iterator.remove();        // 从原位置移除
                list.add(0, item);  // 插到最前面
                break;                    // 如果只要一个，就结束
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出百川分院经营金额列表
     */
    @Log(title = "百川分院经营金额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBcJybb sysBcJybb)
    {
        int year = java.time.LocalDate.now().getYear();
        String yearStr = String.valueOf(year);
        sysBcJybb.setYear(yearStr);
        List<SysBcJybb> list = sysBcJybbService.selectSysBcJybbList(sysBcJybb);
        SysBcGcbb sysBcGcbb = new SysBcGcbb();
        sysBcGcbb.setYear(yearStr);
        List<SysBcGcbb> gcbbList = sysBcGcbbService.selectSysBcGcbbTj(sysBcGcbb);
        for(SysBcJybb obj1 : list){
            for(SysBcGcbb obj2 : gcbbList){
                if (Objects.equals(obj1.getOrgName(), obj2.getBranchOrgName())){
                    obj1.setTotalCount(obj2.getTotalCount());
                    obj1.setWeekCount(obj2.getWeekCount());
                    obj1.setSellYearCount(obj2.getSellYearCount());
                    obj1.setSellWeekCount(obj2.getSellWeekCount());
                    obj1.setPlanYearCount(obj2.getPlanYearCount());
                    obj1.setPlanWeekCount(obj2.getPlanWeekCount());
                    obj1.setPipeCctvCount(obj2.getPipeCctvCount());
                    obj1.setPipeOtherCount(obj2.getPipeOtherCount());
                    obj1.setGovYearEarthControlCount(obj2.getGovYearEarthControlCount());
                    obj1.setGovYearRoadCount(obj2.getGovYearRoadCount());
                    obj1.setGovWeekEarthControlCount(obj2.getGovWeekEarthControlCount());
                    obj1.setGovWeekRoadCount(obj2.getGovWeekRoadCount());
                    obj1.setJiCount(obj2.getJiCount());
                    obj1.setJiCountMonth(obj2.getJiCountMonth());
                    obj1.setPipeCctvCountMonth(obj2.getPipeCctvCountMonth());
                    obj1.setPipeOtherCountMonth(obj2.getPipeOtherCountMonth());
                    continue;
                }
            }
        }

        for (Iterator<SysBcJybb> iterator = list.iterator(); iterator.hasNext(); ) {
            SysBcJybb item = iterator.next();
            if ("福清分公司".equals(item.getOrgName())) {
                iterator.remove();        // 从原位置移除
                list.add(0, item);  // 插到最前面
                break;                    // 如果只要一个，就结束
            }
        }

        for(SysBcJybb obj1 : list){
            obj1.setTotalCountYearWeek(safeStr(obj1.getTotalCount()) + " ｜ " + safeStr(obj1.getWeekCount()));
            obj1.setSellYearWeekCount(safeStr(obj1.getSellYearCount()) + " ｜ " + safeStr(obj1.getSellWeekCount()));
            obj1.setPlanYearWeekCount(safeStr(obj1.getPlanYearCount()) + " ｜ " + safeStr(obj1.getPlanWeekCount()));
            obj1.setGovYearWeekEarthControlCount(safeStr(obj1.getGovYearEarthControlCount()) + " ｜ " + safeStr(obj1.getGovWeekEarthControlCount()));
            obj1.setGovYearWeekRoadCount(safeStr(obj1.getGovYearRoadCount()) + " ｜ " + safeStr(obj1.getGovWeekRoadCount()));
            obj1.setJiCountString(safeStr(obj1.getJiCount()) + " ｜ " + safeStr(obj1.getJiCountMonth()));
            obj1.setPipeCctvCountString(safeStr(obj1.getPipeCctvCount()) + " ｜ " + safeStr(obj1.getPipeCctvCountMonth()));
            obj1.setPipeOtherCountString(safeStr(obj1.getPipeOtherCount()) + " ｜ " + safeStr(obj1.getPipeOtherCountMonth()));
        }

        ExcelUtil<SysBcJybb> util = new ExcelUtil<SysBcJybb>(SysBcJybb.class);
        util.exportExcel(response, list, "经营报表");
    }

    /**
     * 获取百川分院经营金额详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysBcJybbService.selectSysBcJybbById(id));
    }

    /**
     * 新增百川分院经营金额
     */
    @Log(title = "百川分院经营金额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBcJybb sysBcJybb)
    {
        return toAjax(sysBcJybbService.insertSysBcJybb(sysBcJybb));
    }

    /**
     * 修改百川分院经营金额
     */
    @Log(title = "百川分院经营金额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBcJybb sysBcJybb)
    {
        return toAjax(sysBcJybbService.updateSysBcJybb(sysBcJybb));
    }

    /**
     * 删除百川分院经营金额
     */
    @Log(title = "百川分院经营金额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysBcJybbService.deleteSysBcJybbByIds(ids));
    }

    static String safeStr(Object val) {
        return val != null ? String.valueOf(val) : "";
    }
}
