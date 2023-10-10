package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysCarReviewConfigInfo;
import com.kcylog.system.service.ISysCarReviewConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 车辆使用流程配置过程Controller
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/system/carReviewConfigInfo")
public class SysCarReviewConfigInfoController extends BaseController
{
    @Autowired
    private ISysCarReviewConfigInfoService sysCarReviewConfigInfoService;

    /**
     * 查询车辆使用流程配置过程列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        startPage();
        List<SysCarReviewConfigInfo> list = sysCarReviewConfigInfoService.selectSysCarReviewConfigInfoList(sysCarReviewConfigInfo);
        return getDataTable(list);
    }

    /**
     * 导出车辆使用流程配置过程列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "车辆使用流程配置过程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        List<SysCarReviewConfigInfo> list = sysCarReviewConfigInfoService.selectSysCarReviewConfigInfoList(sysCarReviewConfigInfo);
        ExcelUtil<SysCarReviewConfigInfo> util = new ExcelUtil<SysCarReviewConfigInfo>(SysCarReviewConfigInfo.class);
        util.exportExcel(response, list, "车辆使用流程配置过程数据");
    }

    /**
     * 获取车辆使用流程配置过程详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId)
    {
        return success(sysCarReviewConfigInfoService.selectSysCarReviewConfigInfoByInfoId(infoId));
    }

    /**
     * 新增车辆使用流程配置过程
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "车辆使用流程配置过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        return toAjax(sysCarReviewConfigInfoService.insertSysCarReviewConfigInfo(sysCarReviewConfigInfo));
    }

    /**
     * 修改车辆使用流程配置过程
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "车辆使用流程配置过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCarReviewConfigInfo sysCarReviewConfigInfo)
    {
        return toAjax(sysCarReviewConfigInfoService.updateSysCarReviewConfigInfo(sysCarReviewConfigInfo));
    }

    /**
     * 删除车辆使用流程配置过程
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "车辆使用流程配置过程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds)
    {
        return toAjax(sysCarReviewConfigInfoService.deleteSysCarReviewConfigInfoByInfoIds(infoIds));
    }
}
