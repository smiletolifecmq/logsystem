package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysCarReview;
import com.kcylog.system.domain.SysCarReviewConfigInfo;
import com.kcylog.system.domain.SysCarReviewProcess;
import com.kcylog.system.service.ISysCarReviewConfigInfoService;
import com.kcylog.system.service.ISysCarReviewProcessService;
import com.kcylog.system.service.ISysCarReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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

    @Autowired
    private ISysCarReviewConfigInfoService sysCarReviewConfigInfoService;

    @Autowired
    private ISysCarReviewProcessService sysCarReviewProcessService;


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
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysCarReview sysCarReview)
    {
        if (sysCarReviewService.checkProjectKeyUnique(sysCarReview) != null )
        {
            return error("登记失败，该部门该日期项目使用车辆情况已登记过了～");
        }
        Long userId = SecurityUtils.getUserId();
        sysCarReview.setUserId(userId);
        sysCarReviewService.insertSysCarReview(sysCarReview);
        List<SysCarReviewConfigInfo> carReviewConfigInfoList = sysCarReviewConfigInfoService.selectCarReviewConfigInfoListByDeptId(sysCarReview.getDeptId());

        List<SysCarReviewProcess> carReviewProcess = new ArrayList<>();
        for (SysCarReviewConfigInfo configInfo : carReviewConfigInfoList) {
            SysCarReviewProcess  carReview = new SysCarReviewProcess();
            carReview.setCarReviewId((sysCarReview.getCarReviewId()));
            carReview.setUserId(configInfo.getUserId());
            carReview.setStatus((long)0);
            carReview.setNode(Long.parseLong(configInfo.getNode()));
            carReviewProcess.add(carReview);
        }
        int result = sysCarReviewProcessService.insertSysCarReviewBatch(carReviewProcess);
        return toAjax(result);
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
