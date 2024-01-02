package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.domain.entity.SysUser;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoHoliday;
import com.kcylog.system.service.ISysGeoHolidayService;
import com.kcylog.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地理员工请假Controller
 * 
 * @author ruoyi
 * @date 2024-01-02
 */
@RestController
@RequestMapping("/system/geoHoliday")
public class SysGeoHolidayController extends BaseController
{
    @Autowired
    private ISysGeoHolidayService sysGeoHolidayService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询地理员工请假列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoHoliday sysGeoHoliday)
    {
        startPage();
        List<SysGeoHoliday> list = sysGeoHolidayService.selectSysGeoHolidayList(sysGeoHoliday);
        return getDataTable(list);
    }

    /**
     * 导出地理员工请假列表
     */
    @Log(title = "地理员工请假", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoHoliday sysGeoHoliday)
    {
        List<SysGeoHoliday> list = sysGeoHolidayService.selectSysGeoHolidayList(sysGeoHoliday);
        ExcelUtil<SysGeoHoliday> util = new ExcelUtil<SysGeoHoliday>(SysGeoHoliday.class);
        util.exportExcel(response, list, "地理员工请假数据");
    }

    /**
     * 获取地理员工请假详细信息
     */
    @GetMapping(value = "/{holidayId}")
    public AjaxResult getInfo(@PathVariable("holidayId") Long holidayId)
    {
        return success(sysGeoHolidayService.selectSysGeoHolidayByHolidayId(holidayId));
    }

    /**
     * 新增地理员工请假
     */
    @Log(title = "地理员工请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoHoliday sysGeoHoliday)
    {
        SysUser sysUser = userService.selectUserById(sysGeoHoliday.getUserId());
        sysGeoHoliday.setUserName(sysUser.getUserName());
        return toAjax(sysGeoHolidayService.insertSysGeoHoliday(sysGeoHoliday));
    }

    /**
     * 修改地理员工请假
     */
    @Log(title = "地理员工请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoHoliday sysGeoHoliday)
    {
        SysUser sysUser = userService.selectUserById(sysGeoHoliday.getUserId());
        sysGeoHoliday.setUserName(sysUser.getUserName());
        return toAjax(sysGeoHolidayService.updateSysGeoHoliday(sysGeoHoliday));
    }

    /**
     * 删除地理员工请假
     */
    @Log(title = "地理员工请假", businessType = BusinessType.DELETE)
	@DeleteMapping("/{holidayIds}")
    public AjaxResult remove(@PathVariable Long[] holidayIds)
    {
        return toAjax(sysGeoHolidayService.deleteSysGeoHolidayByHolidayIds(holidayIds));
    }
}
