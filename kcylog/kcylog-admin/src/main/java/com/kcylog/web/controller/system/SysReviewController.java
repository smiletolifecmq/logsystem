package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysReview;
import com.kcylog.system.service.ISysReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 审核单Controller
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/system/review")
public class SysReviewController extends BaseController
{
    @Autowired
    private ISysReviewService sysReviewService;

    /**
     * 查询审核单列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysReview sysReview)
    {
        startPage();
        List<SysReview> list = sysReviewService.selectSysReviewList(sysReview);
        return getDataTable(list);
    }

    /**
     * 导出审核单列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:export')")
    @Log(title = "审核单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReview sysReview)
    {
        List<SysReview> list = sysReviewService.selectSysReviewList(sysReview);
        ExcelUtil<SysReview> util = new ExcelUtil<SysReview>(SysReview.class);
        util.exportExcel(response, list, "审核单数据");
    }

    /**
     * 获取审核单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:review:query')")
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") String reviewId)
    {
        return success(sysReviewService.selectSysReviewByReviewId(reviewId));
    }

    /**
     * 新增审核单
     */
    @PreAuthorize("@ss.hasPermi('system:review:add')")
    @Log(title = "审核单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysReview sysReview)
    {
        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        sysReview.setUserId(userId);
        sysReview.setDeptId(deptId);
        return toAjax(sysReviewService.insertSysReview(sysReview));
    }

    /**
     * 修改审核单
     */
    @PreAuthorize("@ss.hasPermi('system:review:edit')")
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysReview sysReview)
    {
        return toAjax(sysReviewService.updateSysReview(sysReview));
    }

    /**
     * 删除审核单
     */
    @PreAuthorize("@ss.hasPermi('system:review:remove')")
    @Log(title = "审核单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable String[] reviewIds)
    {
        return toAjax(sysReviewService.deleteSysReviewByReviewIds(reviewIds));
    }


    /**
     * 发起审核单审核
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @PutMapping("/set_status")
    public AjaxResult setStatus(@RequestBody SysReview sysReview)
    {
        return toAjax(sysReviewService.setSysReviewStatusByReviewId(sysReview));
    }
}
