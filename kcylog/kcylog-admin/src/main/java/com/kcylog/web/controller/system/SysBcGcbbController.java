package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysBcGcbb;
import com.kcylog.system.service.ISysBcGcbbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 百川工程报表Controller
 * 
 * @author ruoyi
 * @date 2025-07-08
 */
@RestController
@RequestMapping("/system/gcbb")
public class SysBcGcbbController extends BaseController
{
    @Autowired
    private ISysBcGcbbService sysBcGcbbService;

    /**
     * 查询百川工程报表列表
     */
    @PreAuthorize("@ss.hasPermi('system:gcbb:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBcGcbb sysBcGcbb)
    {
        startPage();
        sysBcGcbb.setYear(sysBcGcbb.getYear());
        List<SysBcGcbb> list = sysBcGcbbService.selectSysBcGcbbList(sysBcGcbb);
        return getDataTable(list);
    }

    /**
     * 导出百川工程报表列表
     */
    @Log(title = "百川工程报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBcGcbb sysBcGcbb)
    {
        List<SysBcGcbb> list = sysBcGcbbService.selectSysBcGcbbList(sysBcGcbb);
        ExcelUtil<SysBcGcbb> util = new ExcelUtil<SysBcGcbb>(SysBcGcbb.class);
        util.exportExcel(response, list, "百川工程报表数据");
    }

    /**
     * 获取百川工程报表详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysBcGcbbService.selectSysBcGcbbById(id));
    }

    /**
     * 新增百川工程报表
     */
    @Log(title = "百川工程报表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysBcGcbb sysBcGcbb)
    {
        return toAjax(sysBcGcbbService.insertSysBcGcbb(sysBcGcbb));
    }

    /**
     * 修改百川工程报表
     */
    @Log(title = "百川工程报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysBcGcbb sysBcGcbb)
    {
        return toAjax(sysBcGcbbService.updateSysBcGcbb(sysBcGcbb));
    }

    /**
     * 删除百川工程报表
     */
    @Log(title = "百川工程报表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysBcGcbbService.deleteSysBcGcbbByIds(ids));
    }

    @GetMapping("/getTypeGcbb")
    public TableDataInfo getTypeGcbb(SysBcGcbb sysBcGcbb)
    {
        startPage();
        int year = java.time.LocalDate.now().getYear();
        String yearStr = String.valueOf(year);
        sysBcGcbb.setYear(yearStr);
        List<SysBcGcbb> list = new ArrayList<>();
        switch (sysBcGcbb.getDataType()){
            case 1:
                list = sysBcGcbbService.selectSysBcGcbbListForTotalCount(sysBcGcbb);
                break;
            case 2:
                list = sysBcGcbbService.selectSysBcGcbbListForWeekCount(sysBcGcbb);
                break;
            case 3:
                list = sysBcGcbbService.selectSysBcGcbbListForSellYearCount(sysBcGcbb);
                break;
            case 4:
                list = sysBcGcbbService.selectSysBcGcbbListForSellWeekCount(sysBcGcbb);
                break;
            case 5:
                list = sysBcGcbbService.selectSysBcGcbbListForPlanYearCount(sysBcGcbb);
                break;
            case 6:
                list = sysBcGcbbService.selectSysBcGcbbListForPlanWeekCount(sysBcGcbb);
                break;
            case 7:
                list = sysBcGcbbService.selectSysBcGcbbListForJiCount(sysBcGcbb);
                break;
            case 8:
                list = sysBcGcbbService.selectSysBcGcbbListForPipeCctvCount(sysBcGcbb);
                break;
            case 9:
                list = sysBcGcbbService.selectSysBcGcbbListForPipeOtherCount(sysBcGcbb);
                break;
            case 10:
                list = sysBcGcbbService.selectSysBcGcbbListForGovYearEarthControlCount(sysBcGcbb);
                break;
            case 11:
                list = sysBcGcbbService.selectSysBcGcbbListForGovYearRoadCount(sysBcGcbb);
                break;
            case 12:
                list = sysBcGcbbService.selectSysBcGcbbListForGovWeekEarthControlCount(sysBcGcbb);
                break;
            case 13:
                list = sysBcGcbbService.selectSysBcGcbbListForGovWeekRoadCount(sysBcGcbb);
                break;

            case 14:
                list = sysBcGcbbService.selectSysBcGcbbListForJiCountMonth(sysBcGcbb);
                break;

            case 15:
                list = sysBcGcbbService.selectSysBcGcbbListForPipeCctvCountMonth(sysBcGcbb);
                break;

            case 16:
                list = sysBcGcbbService.selectSysBcGcbbListForPipeOtherCountMonth(sysBcGcbb);
                break;
        }
        return getDataTable(list);
    }
}
