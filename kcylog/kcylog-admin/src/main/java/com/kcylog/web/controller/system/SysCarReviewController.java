package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysCarReview;
import com.kcylog.system.service.ISysCarReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 车辆使用审核Controller
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/system/carReview")
public class SysCarReviewController extends BaseController
{
    @Autowired
    private ISysCarReviewService sysCarReviewService;

    /**
     * 查询车辆使用审核列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysCarReview sysCarReview)
    {
        startPage();
        List<SysCarReview> list = sysCarReviewService.selectSysCarReviewList(sysCarReview);
        return getDataTable(list);
    }

    /**
     * 导出车辆使用审核列表
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCarReview sysCarReview)
    {
        List<SysCarReview> list = sysCarReviewService.selectSysCarReviewList(sysCarReview);
        ExcelUtil<SysCarReview> util = new ExcelUtil<SysCarReview>(SysCarReview.class);
        util.exportExcel(response, list, "车辆使用审核数据");
    }

    /**
     * 获取车辆使用审核详细信息
     */
    @GetMapping(value = "/{carReviewId}")
    public AjaxResult getInfo(@PathVariable("carReviewId") Long carReviewId)
    {
        return success(sysCarReviewService.selectSysCarReviewByCarReviewId(carReviewId));
    }

    /**
     * 新增车辆使用审核
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCarReview sysCarReview)
    {
        return toAjax(sysCarReviewService.insertSysCarReview(sysCarReview));
    }

    /**
     * 修改车辆使用审核
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCarReview sysCarReview)
    {
        return toAjax(sysCarReviewService.updateSysCarReview(sysCarReview));
    }

    /**
     * 删除车辆使用审核
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{carReviewIds}")
    public AjaxResult remove(@PathVariable Long[] carReviewIds)
    {
        return toAjax(sysCarReviewService.deleteSysCarReviewByCarReviewIds(carReviewIds));
    }
}
