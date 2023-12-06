package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.domain.entity.SysUser;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoUserCoefficient;
import com.kcylog.system.service.ISysGeoUserCoefficientService;
import com.kcylog.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 【员工系数】Controller
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
@RestController
@RequestMapping("/system/geoUserCoefficient")
public class SysGeoUserCoefficientController extends BaseController
{
    @Autowired
    private ISysGeoUserCoefficientService sysGeoUserCoefficientService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询【员工系数】列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        startPage();
        List<SysGeoUserCoefficient> list = sysGeoUserCoefficientService.selectSysGeoUserCoefficientList(sysGeoUserCoefficient);
        return getDataTable(list);
    }

    /**
     * 导出【员工系数】列表
     */
    @Log(title = "【员工系数】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        List<SysGeoUserCoefficient> list = sysGeoUserCoefficientService.selectSysGeoUserCoefficientList(sysGeoUserCoefficient);
        ExcelUtil<SysGeoUserCoefficient> util = new ExcelUtil<SysGeoUserCoefficient>(SysGeoUserCoefficient.class);
        util.exportExcel(response, list, "【员工系数】数据");
    }

    /**
     * 获取【员工系数】详细信息
     */
    @GetMapping(value = "/{coefficientId}")
    public AjaxResult getInfo(@PathVariable("coefficientId") String coefficientId)
    {
        return success(sysGeoUserCoefficientService.selectSysGeoUserCoefficientByCoefficientId(coefficientId));
    }

    /**
     * 新增【员工系数】
     */
    @Log(title = "【员工系数】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        SysUser sysUser = userService.selectUserById(sysGeoUserCoefficient.getUserId());
        sysGeoUserCoefficient.setName(sysUser.getUserName());
        return toAjax(sysGeoUserCoefficientService.insertSysGeoUserCoefficient(sysGeoUserCoefficient));
    }

    /**
     * 修改【员工系数】
     */
    @Log(title = "【员工系数】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoUserCoefficient sysGeoUserCoefficient)
    {
        SysUser sysUser = userService.selectUserById(sysGeoUserCoefficient.getUserId());
        sysGeoUserCoefficient.setName(sysUser.getUserName());
        return toAjax(sysGeoUserCoefficientService.updateSysGeoUserCoefficient(sysGeoUserCoefficient));
    }

    /**
     * 删除【员工系数】
     */
    @Log(title = "【员工系数】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{coefficientIds}")
    public AjaxResult remove(@PathVariable String[] coefficientIds)
    {
        return toAjax(sysGeoUserCoefficientService.deleteSysGeoUserCoefficientByCoefficientIds(coefficientIds));
    }
}
