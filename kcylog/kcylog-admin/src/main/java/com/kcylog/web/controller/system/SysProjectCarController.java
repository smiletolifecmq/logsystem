package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysProjectCar;
import com.kcylog.system.service.ISysProjectCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 项目车辆使用情况登记Controller
 * 
 * @author ruoyi
 * @date 2023-09-27
 */
@RestController
@RequestMapping("/system/car")
public class SysProjectCarController extends BaseController
{
    @Autowired
    private ISysProjectCarService sysProjectCarService;

    /**
     * 查询项目车辆使用情况登记列表
     */
    @PreAuthorize("@ss.hasPermi('system:car:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProjectCar sysProjectCar)
    {
        startPage();
        List<SysProjectCar> list = sysProjectCarService.selectSysProjectCarList(sysProjectCar);
        return getDataTable(list);
    }

    /**
     * 导出项目车辆使用情况登记列表
     */
    @PreAuthorize("@ss.hasPermi('system:car:export')")
    @Log(title = "项目车辆使用情况登记", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProjectCar sysProjectCar)
    {
        List<SysProjectCar> list = sysProjectCarService.selectSysProjectCarList(sysProjectCar);
        ExcelUtil<SysProjectCar> util = new ExcelUtil<SysProjectCar>(SysProjectCar.class);
        util.exportExcel(response, list, "项目车辆使用情况登记数据");
    }

    /**
     * 获取项目车辆使用情况登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:car:query')")
    @GetMapping(value = "/{carId}")
    public AjaxResult getInfo(@PathVariable("carId") Long carId)
    {
        return success(sysProjectCarService.selectSysProjectCarByCarId(carId));
    }

    /**
     * 新增项目车辆使用情况登记
     */
    @PreAuthorize("@ss.hasPermi('system:car:add')")
    @Log(title = "项目车辆使用情况登记", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysProjectCar sysProjectCar)
    {

        return toAjax(sysProjectCarService.insertSysProjectCar(sysProjectCar));
    }

    /**
     * 修改项目车辆使用情况登记
     */
    @PreAuthorize("@ss.hasPermi('system:car:edit')")
    @Log(title = "项目车辆使用情况登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProjectCar sysProjectCar)
    {
        return toAjax(sysProjectCarService.updateSysProjectCar(sysProjectCar));
    }

    /**
     * 删除项目车辆使用情况登记
     */
    @PreAuthorize("@ss.hasPermi('system:car:remove')")
    @Log(title = "项目车辆使用情况登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{carIds}")
    public AjaxResult remove(@PathVariable Long[] carIds)
    {
        return toAjax(sysProjectCarService.deleteSysProjectCarByCarIds(carIds));
    }
}
