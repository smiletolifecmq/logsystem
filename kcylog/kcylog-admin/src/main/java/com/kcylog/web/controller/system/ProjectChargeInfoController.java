package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.ProjectChargeInfo;
import com.kcylog.system.service.IProjectChargeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分包明细表Controller
 * 
 * @author ruoyi
 * @date 2024-05-12
 */
@RestController
@RequestMapping("/system/chargeInfo")
public class ProjectChargeInfoController extends BaseController
{
    @Autowired
    private IProjectChargeInfoService projectChargeInfoService;

    /**
     * 查询分包明细表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ProjectChargeInfo projectChargeInfo)
    {
        startPage();
        List<ProjectChargeInfo> list = projectChargeInfoService.selectProjectChargeInfoList(projectChargeInfo);
        return getDataTable(list);
    }

    /**
     * 导出分包明细表列表
     */
    @Log(title = "分包明细表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProjectChargeInfo projectChargeInfo)
    {
        List<ProjectChargeInfo> list = projectChargeInfoService.selectProjectChargeInfoList(projectChargeInfo);
        ExcelUtil<ProjectChargeInfo> util = new ExcelUtil<ProjectChargeInfo>(ProjectChargeInfo.class);
        util.exportExcel(response, list, "分包明细表数据");
    }

    /**
     * 获取分包明细表详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(projectChargeInfoService.selectProjectChargeInfoById(id));
    }

    /**
     * 新增分包明细表
     */
    @Log(title = "分包明细表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProjectChargeInfo projectChargeInfo)
    {
        return toAjax(projectChargeInfoService.insertProjectChargeInfo(projectChargeInfo));
    }

    /**
     * 修改分包明细表
     */
    @Log(title = "分包明细表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProjectChargeInfo projectChargeInfo)
    {
        return toAjax(projectChargeInfoService.updateProjectChargeInfo(projectChargeInfo));
    }

    /**
     * 删除分包明细表
     */
    @Log(title = "分包明细表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(projectChargeInfoService.deleteProjectChargeInfoByIds(ids));
    }
}
