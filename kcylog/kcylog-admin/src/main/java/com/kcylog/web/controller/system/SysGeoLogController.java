package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoLog;
import com.kcylog.system.domain.SysGeoUser;
import com.kcylog.system.service.ISysGeoLogService;
import com.kcylog.system.service.ISysGeoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 地理部门日志Controller
 * 
 * @author ruoyi
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/system/geoLog")
public class SysGeoLogController extends BaseController
{
    @Autowired
    private ISysGeoLogService sysGeoLogService;

    @Autowired
    private ISysGeoUserService sysGeoUserService;

    /**
     * 查询地理部门日志列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoLog sysGeoLog)
    {
        startPage();
        //查找登录用户可以查看的用户日志权限
        Long userId = SecurityUtils.getUserId();
        List<SysGeoUser> geoUsers = sysGeoUserService.selectSysAssessUserByGeoUser(userId);
        List<Long> longIdsList = new ArrayList<>();
        sysGeoLog.setLookUserIds(longIdsList);
        sysGeoLog.getLookUserIds().add(userId);
        for (SysGeoUser sysGeoUser:geoUsers){
            sysGeoLog.getLookUserIds().add(sysGeoUser.getUserId());
        }
        //查找拥有查看日志的记录
        List<SysGeoLog> list = sysGeoLogService.selectSysGeoLogList(sysGeoLog);
        return getDataTable(list);
    }

    /**
     * 导出地理部门日志列表
     */
    @Log(title = "地理部门日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoLog sysGeoLog)
    {
        List<SysGeoLog> list = sysGeoLogService.selectSysGeoLogList(sysGeoLog);
        ExcelUtil<SysGeoLog> util = new ExcelUtil<SysGeoLog>(SysGeoLog.class);
        util.exportExcel(response, list, "地理部门日志数据");
    }

    /**
     * 获取地理部门日志详细信息
     */
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId)
    {
        return success(sysGeoLogService.selectSysGeoLogByLogId(logId));
    }

    /**
     * 新增地理部门日志
     */
    @Log(title = "地理部门日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoLog sysGeoLog)
    {
        return toAjax(sysGeoLogService.insertSysGeoLog(sysGeoLog));
    }

    /**
     * 修改地理部门日志
     */
    @Log(title = "地理部门日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoLog sysGeoLog)
    {
        return toAjax(sysGeoLogService.updateSysGeoLog(sysGeoLog));
    }

    /**
     * 删除地理部门日志
     */
    @Log(title = "地理部门日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable Long[] logIds)
    {
        return toAjax(sysGeoLogService.deleteSysGeoLogByLogIds(logIds));
    }
}
