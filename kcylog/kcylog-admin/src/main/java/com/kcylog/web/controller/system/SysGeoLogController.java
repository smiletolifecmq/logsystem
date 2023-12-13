package com.kcylog.web.controller.system;

import com.google.gson.Gson;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.domain.SysGeoLog;
import com.kcylog.system.domain.SysGeoLogInfo;
import com.kcylog.system.domain.SysGeoUser;
import com.kcylog.system.service.ISysGeoLogInfoService;
import com.kcylog.system.service.ISysGeoLogService;
import com.kcylog.system.service.ISysGeoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
public class SysGeoLogController extends BaseController {
    @Autowired
    private ISysGeoLogService sysGeoLogService;

    @Autowired
    private ISysGeoUserService sysGeoUserService;

    @Autowired
    private ISysGeoLogInfoService sysGeoLogInfoService;

    private static double simple = 0.8;

    private static double generally = 1;

    private static double difficulty = 1.2;

    /**
     * 查询地理部门日志列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoLog sysGeoLog) {
        startPage();
        //查找登录用户可以查看的用户日志权限
        Long userId = SecurityUtils.getUserId();
        List<SysGeoUser> geoUsers = sysGeoUserService.selectSysAssessUserByGeoUser(userId);
        List<Long> longIdsList = new ArrayList<>();
        sysGeoLog.setLookUserIds(longIdsList);
        sysGeoLog.getLookUserIds().add(userId);
        for (SysGeoUser sysGeoUser : geoUsers) {
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
    @GetMapping("/listLogExport")
    public TableDataInfo export(SysGeoLog sysGeoLog) {
        Long userId = SecurityUtils.getUserId();
        List<SysGeoUser> geoUsers = sysGeoUserService.selectSysAssessUserByGeoUser(userId);
        List<Long> longIdsList = new ArrayList<>();
        sysGeoLog.setLookUserIds(longIdsList);
        sysGeoLog.getLookUserIds().add(userId);
        for (SysGeoUser sysGeoUser : geoUsers) {
            sysGeoLog.getLookUserIds().add(sysGeoUser.getUserId());
        }
        List<SysGeoLog> list = sysGeoLogService.selectSysGeoLogList(sysGeoLog);
        return getDataTable(list);
    }

    /**
     * 获取地理部门日志详细信息
     */
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId) {
        SysGeoLog geoLog = sysGeoLogService.selectSysGeoLogByLogId(logId);
        for (SysGeoLogInfo geoLogInfo : geoLog.getGeoLogInfo()) {
            Gson gson = new Gson();
            Long[] parsedArray = gson.fromJson(geoLogInfo.getTypeArr(), Long[].class);
            geoLogInfo.setTypeArrJson(parsedArray);
            if (geoLogInfo.getDegree() == 0){
                geoLogInfo.setDisabled(true);
            }else if (geoLogInfo.getDegree() == 1){
                geoLogInfo.setDisabled(false);
            }
        }
        return success(geoLog);
    }

    /**
     * 新增地理部门日志
     */
    @Log(title = "地理部门日志", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoLog sysGeoLog) {
        sysGeoLog.setUserId(SecurityUtils.getUserId());
        sysGeoLog.setUserName(SecurityUtils.getUsername());
        SysGeoLog geoLog = sysGeoLogService.selectSysGeoLogByUserIdAndDate(sysGeoLog);
        if (geoLog != null) {
            return error("该日期已经完成日志填写～");
        }
        sysGeoLogService.insertSysGeoLog(sysGeoLog);
        for (SysGeoLogInfo geoLogInfo : sysGeoLog.getGeoLogInfo()) {
            geoLogInfo.setLogId(sysGeoLog.getLogId());
            Gson gson = new Gson();
            String json = gson.toJson(geoLogInfo.getTypeArrJson());
            geoLogInfo.setTypeArr(json);
            if (geoLogInfo.getDifficulty() == 1) {
                geoLogInfo.setDifficultyDegree(simple);
            } else if (geoLogInfo.getDifficulty() == 3) {
                geoLogInfo.setDifficultyDegree(difficulty);
            } else {
                geoLogInfo.setDifficultyDegree(generally);
            }
            geoLogInfo.setLogDate(sysGeoLog.getLogDate());
            sysGeoLogInfoService.insertSysGeoLogInfo(geoLogInfo);
        }
        return toAjax(1);
    }

    /**
     * 修改地理部门日志
     */
    @Log(title = "地理部门日志", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoLog sysGeoLog) {
        sysGeoLog.setUserId(SecurityUtils.getUserId());
        sysGeoLog.setUserName(SecurityUtils.getUsername());
        SysGeoLog geoLog = sysGeoLogService.selectSysGeoLogByUserIdAndDateNotSelf(sysGeoLog);
        if (geoLog != null) {
            return error("该日期已经完成日志填写～");
        }
        Long[] logIds = {sysGeoLog.getLogId()};
        sysGeoLogInfoService.deleteSysGeoLogInfoByLogIds(logIds);
        for (SysGeoLogInfo geoLogInfo : sysGeoLog.getGeoLogInfo()) {
            geoLogInfo.setLogId(sysGeoLog.getLogId());
            Gson gson = new Gson();
            String json = gson.toJson(geoLogInfo.getTypeArrJson());
            geoLogInfo.setTypeArr(json);
            if (geoLogInfo.getDifficulty() == 1) {
                geoLogInfo.setDifficultyDegree(simple);
            } else if (geoLogInfo.getDifficulty() == 3) {
                geoLogInfo.setDifficultyDegree(difficulty);
            } else {
                geoLogInfo.setDifficultyDegree(generally);
            }
            geoLogInfo.setLogDate(sysGeoLog.getLogDate());
            sysGeoLogInfoService.insertSysGeoLogInfo(geoLogInfo);
        }

        return toAjax(sysGeoLogService.updateSysGeoLog(sysGeoLog));
    }

    /**
     * 删除地理部门日志
     */
    @Log(title = "地理部门日志", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable Long[] logIds) {
        sysGeoLogInfoService.deleteSysGeoLogInfoByLogIds(logIds);
        return toAjax(sysGeoLogService.deleteSysGeoLogByLogIds(logIds));
    }
}
