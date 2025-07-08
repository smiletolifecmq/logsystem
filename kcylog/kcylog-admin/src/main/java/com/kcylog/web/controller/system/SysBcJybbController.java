package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysBcJybb;
import com.kcylog.system.service.ISysBcJybbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 查询百川分院经营金额列表
     */
    @PreAuthorize("@ss.hasPermi('system:jybb:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBcJybb sysBcJybb)
    {
        startPage();
        List<SysBcJybb> list = sysBcJybbService.selectSysBcJybbList(sysBcJybb);
        return getDataTable(list);
    }

    /**
     * 导出百川分院经营金额列表
     */
    @Log(title = "百川分院经营金额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBcJybb sysBcJybb)
    {
        List<SysBcJybb> list = sysBcJybbService.selectSysBcJybbList(sysBcJybb);
        ExcelUtil<SysBcJybb> util = new ExcelUtil<SysBcJybb>(SysBcJybb.class);
        util.exportExcel(response, list, "百川分院经营金额数据");
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
}
