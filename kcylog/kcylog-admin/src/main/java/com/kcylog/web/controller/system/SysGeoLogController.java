package com.kcylog.web.controller.system;

import com.google.gson.Gson;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.system.common.LogExport;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ISysGeoProjectService sysGeoProjectService;

    @Autowired
    private ISysGeoAssessInfoService sysGeoAssessInfoService;

    @Autowired
    private ISysGeoUserCoefficientService sysGeoUserCoefficientService;

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
        //获取符合日期以及权限的用户数据
        List<SysGeoLog> list = sysGeoLogService.selectSysGeoLogListExport(sysGeoLog);
        //获取符合日期的所有用户数据
        List<SysGeoLog> listForDate = sysGeoLogService.selectSysGeoLogListExportByDate(sysGeoLog);
        //获取所有项目
        List<SysGeoProject> projects = sysGeoProjectService.selectSysGeoProjectAll();

        Map<String, List<SysGeoLog>> geoLogMap = new HashMap<>();
        Map<String, Boolean> userCheckMap = new HashMap<>();
        Map<Long, BigDecimal> projectMoneyMap = new HashMap<>();
        Map<String, List<Long>> projectsMap = new HashMap<>();

        BigDecimal allUserMoney = BigDecimal.ZERO;
        for (SysGeoProject geoProject : projects) {
            if (projectsMap.containsKey(geoProject.getUserName())) {
                List<Long> projectIdArr = projectsMap.get(geoProject.getUserName());
                projectIdArr.add(geoProject.getProjectId());
                projectsMap.put(geoProject.getUserName(),projectIdArr);
            } else {
                List<Long> projectIdArr = new ArrayList<>();
                projectIdArr.add(geoProject.getProjectId());
                projectsMap.put(geoProject.getUserName(), projectIdArr);
            }
        }

        for (SysGeoLog geoLog : list) {
            if (geoLogMap.containsKey(geoLog.getUserName())) {
                List<SysGeoLog> gl = geoLogMap.get(geoLog.getUserName());
                gl.add(geoLog);
                geoLogMap.put(geoLog.getUserName(), gl);
            } else {
                List<SysGeoLog> gl = new ArrayList<>();
                gl.add(geoLog);
                geoLogMap.put(geoLog.getUserName(), gl);
            }
            if (geoLog.getGeoUser().getIsCheck() == 1) {
                userCheckMap.put(geoLog.getUserName(), true);
            }
        }
        for (SysGeoLog geoLog : listForDate) {
            for (SysGeoLogInfo geoLogInfo : geoLog.getGeoLogInfo()) {
                BigDecimal difficultyDegree = BigDecimal.valueOf(geoLogInfo.getDifficultyDegree());
                BigDecimal workload = BigDecimal.valueOf(geoLogInfo.getWorkload());
                BigDecimal jinEr = difficultyDegree.multiply(workload).multiply(geoLogInfo.getTypeMoney());
                if (geoLog.getGeoUser().getIsCheck() != 1 && geoLogInfo.getProjectId() != null && geoLogInfo.getProjectId() != 0) {
                    allUserMoney = allUserMoney.add(jinEr);
                }
                if (geoLogInfo.getProjectId() != null && geoLogInfo.getProjectId() != 0) {
                    if (projectMoneyMap.containsKey(geoLogInfo.getProjectId())) {
                        projectMoneyMap.put(geoLogInfo.getProjectId(), projectMoneyMap.get(geoLogInfo.getProjectId()).add(jinEr));
                    } else {
                        BigDecimal projectMoney = BigDecimal.ZERO;
                        projectMoneyMap.put(geoLogInfo.getProjectId(), projectMoney.add(jinEr));
                    }
                }
            }
        }
        // 遍历值
        List<LogExport> exportList = new ArrayList<>();
        for (Map.Entry<String, List<SysGeoLog>> entry : geoLogMap.entrySet()) {
            String userName = entry.getKey();
            List<SysGeoLog> value = entry.getValue();
            LogExport logExport = this.integratedOutputValueSelf(value, userName);
            //检查人
            if (userCheckMap.containsKey(userName)) {
                BigDecimal multiplier = new BigDecimal("0.05");
                allUserMoney = allUserMoney.multiply(multiplier);
                logExport.setType52_jr(allUserMoney);
                logExport.setTotal_money(logExport.getTotal_money().add(allUserMoney));
            }
            //负责人
            if (projectsMap.containsKey(userName)) {
                BigDecimal allProjectMoney = BigDecimal.ZERO;
                List<Long> projectIdArr = projectsMap.get(userName);
                for (Long pId : projectIdArr){
                    if (projectMoneyMap.containsKey(pId)) {
                        allProjectMoney = allProjectMoney.add(projectMoneyMap.get(pId));
                    }
                }
                BigDecimal multiplier = new BigDecimal("0.08");
                allProjectMoney = allProjectMoney.multiply(multiplier);
                logExport.setType51_jr(allProjectMoney);
                logExport.setTotal_money(logExport.getTotal_money().add(allProjectMoney));
            }

            exportList.add(logExport);
        }
        return getDataTable(exportList);
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
            if (geoLogInfo.getDegree() == 0) {
                geoLogInfo.setDisabled(true);
            } else if (geoLogInfo.getDegree() == 1) {
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

    public LogExport integratedOutputValueSelf(List<SysGeoLog> sysGeoLogList, String userName) {
        LogExport logExport = new LogExport();
        //用户名
        logExport.setUser_name(userName);
        //工作量
        logExport.setType1_gzl((double) 0);
        logExport.setType2_gzl((double) 0);
        logExport.setType3_gzl((double) 0);
        logExport.setType4_gzl((double) 0);
        logExport.setType5_gzl((double) 0);
        logExport.setType6_gzl((double) 0);
        logExport.setType7_gzl((double) 0);
        logExport.setType8_gzl((double) 0);
        logExport.setType9_gzl((double) 0);
        logExport.setType10_gzl((double) 0);
        logExport.setType11_gzl((double) 0);
        logExport.setType12_gzl((double) 0);
        logExport.setType13_gzl((double) 0);
        logExport.setType14_gzl((double) 0);
        logExport.setType15_gzl((double) 0);
        logExport.setType16_gzl((double) 0);
        logExport.setType17_gzl((double) 0);
        logExport.setType18_gzl((double) 0);
        logExport.setType19_gzl((double) 0);
        logExport.setType20_gzl((double) 0);
        logExport.setType21_gzl((double) 0);
        logExport.setType22_gzl((double) 0);
        logExport.setType23_gzl((double) 0);
        logExport.setType24_gzl((double) 0);
        logExport.setType25_gzl((double) 0);
        logExport.setType26_gzl((double) 0);
        logExport.setType27_gzl((double) 0);
        logExport.setType28_gzl((double) 0);
        logExport.setType29_gzl((double) 0);
        logExport.setType30_gzl((double) 0);
        logExport.setType31_gzl((double) 0);
        logExport.setType32_gzl((double) 0);
        logExport.setType33_gzl((double) 0);
        logExport.setType34_gzl((double) 0);
        logExport.setType35_gzl((double) 0);
        logExport.setType36_gzl((double) 0);
        logExport.setType37_gzl((double) 0);
        logExport.setType38_gzl((double) 0);
        logExport.setType39_gzl((double) 0);
        logExport.setType40_gzl((double) 0);
        logExport.setType41_gzl((double) 0);
        logExport.setType42_gzl((double) 0);
        logExport.setType43_gzl((double) 0);
        logExport.setType44_gzl((double) 0);
        logExport.setType45_gzl((double) 0);
        logExport.setType46_gzl((double) 0);
        logExport.setType47_gzl((double) 0);
        logExport.setType48_gzl((double) 0);
        logExport.setType49_gzl((double) 0);
        logExport.setType50_gzl((double) 0);
        logExport.setType51_gzl((double) 0);
        logExport.setType52_gzl((double) 0);
        logExport.setType53_gzl((double) 0);
        //产值金额
        logExport.setType1_jr(BigDecimal.valueOf(0));
        logExport.setType2_jr(BigDecimal.valueOf(0));
        logExport.setType3_jr(BigDecimal.valueOf(0));
        logExport.setType4_jr(BigDecimal.valueOf(0));
        logExport.setType5_jr(BigDecimal.valueOf(0));
        logExport.setType6_jr(BigDecimal.valueOf(0));
        logExport.setType7_jr(BigDecimal.valueOf(0));
        logExport.setType8_jr(BigDecimal.valueOf(0));
        logExport.setType9_jr(BigDecimal.valueOf(0));
        logExport.setType10_jr(BigDecimal.valueOf(0));
        logExport.setType11_jr(BigDecimal.valueOf(0));
        logExport.setType12_jr(BigDecimal.valueOf(0));
        logExport.setType13_jr(BigDecimal.valueOf(0));
        logExport.setType14_jr(BigDecimal.valueOf(0));
        logExport.setType15_jr(BigDecimal.valueOf(0));
        logExport.setType16_jr(BigDecimal.valueOf(0));
        logExport.setType17_jr(BigDecimal.valueOf(0));
        logExport.setType18_jr(BigDecimal.valueOf(0));
        logExport.setType19_jr(BigDecimal.valueOf(0));
        logExport.setType20_jr(BigDecimal.valueOf(0));
        logExport.setType21_jr(BigDecimal.valueOf(0));
        logExport.setType22_jr(BigDecimal.valueOf(0));
        logExport.setType23_jr(BigDecimal.valueOf(0));
        logExport.setType24_jr(BigDecimal.valueOf(0));
        logExport.setType25_jr(BigDecimal.valueOf(0));
        logExport.setType26_jr(BigDecimal.valueOf(0));
        logExport.setType27_jr(BigDecimal.valueOf(0));
        logExport.setType28_jr(BigDecimal.valueOf(0));
        logExport.setType29_jr(BigDecimal.valueOf(0));
        logExport.setType30_jr(BigDecimal.valueOf(0));
        logExport.setType31_jr(BigDecimal.valueOf(0));
        logExport.setType32_jr(BigDecimal.valueOf(0));
        logExport.setType33_jr(BigDecimal.valueOf(0));
        logExport.setType34_jr(BigDecimal.valueOf(0));
        logExport.setType35_jr(BigDecimal.valueOf(0));
        logExport.setType36_jr(BigDecimal.valueOf(0));
        logExport.setType37_jr(BigDecimal.valueOf(0));
        logExport.setType38_jr(BigDecimal.valueOf(0));
        logExport.setType39_jr(BigDecimal.valueOf(0));
        logExport.setType40_jr(BigDecimal.valueOf(0));
        logExport.setType41_jr(BigDecimal.valueOf(0));
        logExport.setType42_jr(BigDecimal.valueOf(0));
        logExport.setType43_jr(BigDecimal.valueOf(0));
        logExport.setType44_jr(BigDecimal.valueOf(0));
        logExport.setType45_jr(BigDecimal.valueOf(0));
        logExport.setType46_jr(BigDecimal.valueOf(0));
        logExport.setType47_jr(BigDecimal.valueOf(0));
        logExport.setType48_jr(BigDecimal.valueOf(0));
        logExport.setType49_jr(BigDecimal.valueOf(0));
        logExport.setType50_jr(BigDecimal.valueOf(0));
        logExport.setType51_jr(BigDecimal.valueOf(0));
        logExport.setType52_jr(BigDecimal.valueOf(0));
        //总产值
        logExport.setTotal_money(BigDecimal.valueOf(0));

        for (SysGeoLog geoLog : sysGeoLogList) {
            for (SysGeoLogInfo geoLogInfo : geoLog.getGeoLogInfo()) {
                int typeId = Math.toIntExact(geoLogInfo.getTypeId());
                BigDecimal difficultyDegree = BigDecimal.valueOf(geoLogInfo.getDifficultyDegree());
                BigDecimal workload = BigDecimal.valueOf(geoLogInfo.getWorkload());
                double workloadDouble = geoLogInfo.getWorkload();
                BigDecimal jinEr = difficultyDegree.multiply(workload).multiply(geoLogInfo.getTypeMoney());
                logExport.setTotal_money(logExport.getTotal_money().add(jinEr));
                switch (typeId) {
                    case 2:
                        logExport.setType1_gzl(logExport.getType1_gzl() + workloadDouble);
                        logExport.setType1_jr(logExport.getType1_jr().add(jinEr));
                        break;
                    case 3:
                        logExport.setType2_gzl(logExport.getType2_gzl() + workloadDouble);
                        logExport.setType2_jr(logExport.getType2_jr().add(jinEr));
                        break;
                    case 4:
                        logExport.setType3_gzl(logExport.getType3_gzl() + workloadDouble);
                        logExport.setType3_jr(logExport.getType3_jr().add(jinEr));
                        break;
                    case 5:
                        logExport.setType4_gzl(logExport.getType4_gzl() + workloadDouble);
                        logExport.setType4_jr(logExport.getType4_jr().add(jinEr));
                        break;
                    case 6:
                        logExport.setType5_gzl(logExport.getType5_gzl() + workloadDouble);
                        logExport.setType5_jr(logExport.getType5_jr().add(jinEr));
                        break;
                    case 7:
                        logExport.setType6_gzl(logExport.getType6_gzl() + workloadDouble);
                        logExport.setType6_jr(logExport.getType6_jr().add(jinEr));
                        break;
                    case 9:
                        logExport.setType7_gzl(logExport.getType7_gzl() + workloadDouble);
                        logExport.setType7_jr(logExport.getType7_jr().add(jinEr));
                        break;
                    case 10:
                        logExport.setType8_gzl(logExport.getType8_gzl() + workloadDouble);
                        logExport.setType8_jr(logExport.getType8_jr().add(jinEr));
                        break;
                    case 11:
                        logExport.setType9_gzl(logExport.getType9_gzl() + workloadDouble);
                        logExport.setType9_jr(logExport.getType9_jr().add(jinEr));
                        break;
                    case 12:
                        logExport.setType10_gzl(logExport.getType10_gzl() + workloadDouble);
                        logExport.setType10_jr(logExport.getType10_jr().add(jinEr));
                        break;
                    case 13:
                        logExport.setType11_gzl(logExport.getType11_gzl() + workloadDouble);
                        logExport.setType11_jr(logExport.getType11_jr().add(jinEr));
                        break;
                    case 14:
                        logExport.setType12_gzl(logExport.getType12_gzl() + workloadDouble);
                        logExport.setType12_jr(logExport.getType12_jr().add(jinEr));
                        break;
                    case 15:
                        logExport.setType13_gzl(logExport.getType13_gzl() + workloadDouble);
                        logExport.setType13_jr(logExport.getType13_jr().add(jinEr));
                        break;
                    case 16:
                        logExport.setType14_gzl(logExport.getType14_gzl() + workloadDouble);
                        logExport.setType14_jr(logExport.getType14_jr().add(jinEr));
                        break;
                    case 17:
                        logExport.setType15_gzl(logExport.getType15_gzl() + workloadDouble);
                        logExport.setType15_jr(logExport.getType15_jr().add(jinEr));
                        break;
                    case 18:
                        logExport.setType16_gzl(logExport.getType16_gzl() + workloadDouble);
                        logExport.setType16_jr(logExport.getType16_jr().add(jinEr));
                        break;
                    case 20:
                        logExport.setType17_gzl(logExport.getType17_gzl() + workloadDouble);
                        logExport.setType17_jr(logExport.getType17_jr().add(jinEr));
                        break;
                    case 21:
                        logExport.setType18_gzl(logExport.getType18_gzl() + workloadDouble);
                        logExport.setType18_jr(logExport.getType18_jr().add(jinEr));
                        break;
                    case 22:
                        logExport.setType19_gzl(logExport.getType19_gzl() + workloadDouble);
                        logExport.setType19_jr(logExport.getType19_jr().add(jinEr));
                        break;
                    case 23:
                        logExport.setType20_gzl(logExport.getType20_gzl() + workloadDouble);
                        logExport.setType20_jr(logExport.getType20_jr().add(jinEr));
                        break;
                    case 25:
                        logExport.setType21_gzl(logExport.getType21_gzl() + workloadDouble);
                        logExport.setType21_jr(logExport.getType21_jr().add(jinEr));
                        break;
                    case 26:
                        logExport.setType22_gzl(logExport.getType22_gzl() + workloadDouble);
                        logExport.setType22_jr(logExport.getType22_jr().add(jinEr));
                        break;
                    case 27:
                        logExport.setType23_gzl(logExport.getType23_gzl() + workloadDouble);
                        logExport.setType23_jr(logExport.getType23_jr().add(jinEr));
                        break;
                    case 28:
                        logExport.setType24_gzl(logExport.getType24_gzl() + workloadDouble);
                        logExport.setType24_jr(logExport.getType24_jr().add(jinEr));
                        break;
                    case 29:
                        logExport.setType25_gzl(logExport.getType25_gzl() + workloadDouble);
                        logExport.setType25_jr(logExport.getType25_jr().add(jinEr));
                        break;
                    case 30:
                        logExport.setType26_gzl(logExport.getType26_gzl() + workloadDouble);
                        logExport.setType26_jr(logExport.getType26_jr().add(jinEr));
                        break;
                    case 31:
                        logExport.setType27_gzl(logExport.getType27_gzl() + workloadDouble);
                        logExport.setType27_jr(logExport.getType27_jr().add(jinEr));
                        break;
                    case 32:
                        logExport.setType28_gzl(logExport.getType28_gzl() + workloadDouble);
                        logExport.setType28_jr(logExport.getType28_jr().add(jinEr));
                        break;
                    case 33:
                        logExport.setType29_gzl(logExport.getType29_gzl() + workloadDouble);
                        logExport.setType29_jr(logExport.getType29_jr().add(jinEr));
                        break;
                    case 34:
                        logExport.setType30_gzl(logExport.getType30_gzl() + workloadDouble);
                        logExport.setType30_jr(logExport.getType30_jr().add(jinEr));
                        break;
                    case 35:
                        logExport.setType31_gzl(logExport.getType31_gzl() + workloadDouble);
                        logExport.setType31_jr(logExport.getType31_jr().add(jinEr));
                        break;
                    case 36:
                        logExport.setType32_gzl(logExport.getType32_gzl() + workloadDouble);
                        logExport.setType32_jr(logExport.getType32_jr().add(jinEr));
                        break;
                    case 37:
                        logExport.setType33_gzl(logExport.getType33_gzl() + workloadDouble);
                        logExport.setType33_jr(logExport.getType33_jr().add(jinEr));
                        break;
                    case 38:
                        logExport.setType34_gzl(logExport.getType34_gzl() + workloadDouble);
                        logExport.setType34_jr(logExport.getType34_jr().add(jinEr));
                        break;
                    case 39:
                        logExport.setType35_gzl(logExport.getType35_gzl() + workloadDouble);
                        logExport.setType35_jr(logExport.getType35_jr().add(jinEr));
                        break;
                    case 40:
                        logExport.setType36_gzl(logExport.getType36_gzl() + workloadDouble);
                        logExport.setType36_jr(logExport.getType36_jr().add(jinEr));
                        break;
                    case 41:
                        logExport.setType37_gzl(logExport.getType37_gzl() + workloadDouble);
                        logExport.setType37_jr(logExport.getType37_jr().add(jinEr));
                        break;
                    case 42:
                        logExport.setType38_gzl(logExport.getType38_gzl() + workloadDouble);
                        logExport.setType38_jr(logExport.getType38_jr().add(jinEr));
                        break;
                    case 43:
                        logExport.setType39_gzl(logExport.getType39_gzl() + workloadDouble);
                        logExport.setType39_jr(logExport.getType39_jr().add(jinEr));
                        break;
                    case 44:
                        logExport.setType40_gzl(logExport.getType40_gzl() + workloadDouble);
                        logExport.setType40_jr(logExport.getType40_jr().add(jinEr));
                        break;
                    case 45:
                        logExport.setType41_gzl(logExport.getType41_gzl() + workloadDouble);
                        logExport.setType41_jr(logExport.getType41_jr().add(jinEr));
                        break;
                    case 46:
                        logExport.setType42_gzl(logExport.getType42_gzl() + workloadDouble);
                        logExport.setType42_jr(logExport.getType42_jr().add(jinEr));
                        break;
                    case 47:
                        logExport.setType43_gzl(logExport.getType43_gzl() + workloadDouble);
                        logExport.setType43_jr(logExport.getType43_jr().add(jinEr));
                        break;
                    case 48:
                        logExport.setType44_gzl(logExport.getType44_gzl() + workloadDouble);
                        logExport.setType44_jr(logExport.getType44_jr().add(jinEr));
                        break;
                    case 49:
                        logExport.setType45_gzl(logExport.getType45_gzl() + workloadDouble);
                        logExport.setType45_jr(logExport.getType45_jr().add(jinEr));
                        break;
                    case 50:
                        logExport.setType46_gzl(logExport.getType46_gzl() + workloadDouble);
                        logExport.setType46_jr(logExport.getType46_jr().add(jinEr));
                        break;
                    case 51:
                        logExport.setType47_gzl(logExport.getType47_gzl() + workloadDouble);
                        logExport.setType47_jr(logExport.getType47_jr().add(jinEr));
                        break;
                    case 54:
                        logExport.setType48_gzl(logExport.getType48_gzl() + workloadDouble);
                        logExport.setType48_jr(logExport.getType48_jr().add(jinEr));
                        break;
                    case 55:
                        logExport.setType49_gzl(logExport.getType49_gzl() + workloadDouble);
                        logExport.setType49_jr(logExport.getType49_jr().add(jinEr));
                        break;
                    case 56:
                        logExport.setType50_gzl(logExport.getType50_gzl() + workloadDouble);
                        logExport.setType50_jr(logExport.getType50_jr().add(jinEr));
                        break;
                }
            }
        }
        return logExport;
    }

    @Log(title = "地理部门日志", businessType = BusinessType.EXPORT)
    @GetMapping("/listLogExportWord")
    public TableDataInfo exportWord(SysGeoLog sysGeoLog) {
        Long userId = SecurityUtils.getUserId();
        List<SysGeoUser> geoUsers = sysGeoUserService.selectSysAssessUserByGeoUser(userId);
        List<Long> longIdsList = new ArrayList<>();
        sysGeoLog.setLookUserIds(longIdsList);
        sysGeoLog.getLookUserIds().add(userId);
        for (SysGeoUser sysGeoUser : geoUsers) {
            sysGeoLog.getLookUserIds().add(sysGeoUser.getUserId());
        }
        //获取符合日期以及权限的用户数据
        List<SysGeoLog> list = sysGeoLogService.selectSysGeoLogListExport(sysGeoLog);
        //获取符合日期的所有用户数据
        List<SysGeoLog> listForDate = sysGeoLogService.selectSysGeoLogListExportByDate(sysGeoLog);
        //获取所有项目
        List<SysGeoProject> projects = sysGeoProjectService.selectSysGeoProjectAll();
        //获取评定表
        List<SysGeoAssessInfo> geoAssessInfo = sysGeoAssessInfoService.selectSysGeoAssessInfoListByDate(sysGeoLog);
        //获取个人系数
        SysGeoUserCoefficient geoUserCoefficientObj = new SysGeoUserCoefficient();
        List<SysGeoUserCoefficient> geoUserCoefficient = sysGeoUserCoefficientService.selectSysGeoUserCoefficientList(geoUserCoefficientObj);

        Map<String, List<SysGeoLog>> geoLogMap = new HashMap<>();
        Map<String, Boolean> userCheckMap = new HashMap<>();
        Map<Long, BigDecimal> projectMoneyMap = new HashMap<>();
        Map<String, List<Long>> projectsMap = new HashMap<>();
        Map<String, Double> fitCoefficientMap = new HashMap<>();
        Map<String, Double> workCoefficientMap = new HashMap<>();
        Map<String, Double> geoUserCoefficientMap = new HashMap<>();

        BigDecimal allUserMoney = BigDecimal.ZERO;
        for (SysGeoAssessInfo geoAssessInfoValue : geoAssessInfo){
            fitCoefficientMap.put(geoAssessInfoValue.getUserName(),geoAssessInfoValue.getFitCoefficient());
            workCoefficientMap.put(geoAssessInfoValue.getUserName(),geoAssessInfoValue.getWorkCoefficient());
        }

        for (SysGeoUserCoefficient userCoefficient:geoUserCoefficient){
            geoUserCoefficientMap.put(userCoefficient.getName(),userCoefficient.getCoefficient());
        }

        for (SysGeoProject geoProject : projects) {
            if (projectsMap.containsKey(geoProject.getUserName())) {
                List<Long> projectIdArr = projectsMap.get(geoProject.getUserName());
                projectIdArr.add(geoProject.getProjectId());
                projectsMap.put(geoProject.getUserName(),projectIdArr);
            } else {
                List<Long> projectIdArr = new ArrayList<>();
                projectIdArr.add(geoProject.getProjectId());
                projectsMap.put(geoProject.getUserName(), projectIdArr);
            }
        }

        for (SysGeoLog geoLog : list) {
            if (geoLogMap.containsKey(geoLog.getUserName())) {
                List<SysGeoLog> gl = geoLogMap.get(geoLog.getUserName());
                gl.add(geoLog);
                geoLogMap.put(geoLog.getUserName(), gl);
            } else {
                List<SysGeoLog> gl = new ArrayList<>();
                gl.add(geoLog);
                geoLogMap.put(geoLog.getUserName(), gl);
            }
            if (geoLog.getGeoUser().getIsCheck() == 1) {
                userCheckMap.put(geoLog.getUserName(), true);
            }
        }

        for (SysGeoLog geoLog : listForDate) {
            for (SysGeoLogInfo geoLogInfo : geoLog.getGeoLogInfo()) {
                BigDecimal difficultyDegree = BigDecimal.valueOf(geoLogInfo.getDifficultyDegree());
                BigDecimal workload = BigDecimal.valueOf(geoLogInfo.getWorkload());
                BigDecimal jinEr = difficultyDegree.multiply(workload).multiply(geoLogInfo.getTypeMoney());
                if (geoLog.getGeoUser().getIsCheck() != 1 && geoLogInfo.getProjectId() != null && geoLogInfo.getProjectId() != 0) {
                    allUserMoney = allUserMoney.add(jinEr);
                }
                if (geoLogInfo.getProjectId() != null && geoLogInfo.getProjectId() != 0) {
                    if (projectMoneyMap.containsKey(geoLogInfo.getProjectId())) {
                        projectMoneyMap.put(geoLogInfo.getProjectId(), projectMoneyMap.get(geoLogInfo.getProjectId()).add(jinEr));
                    } else {
                        BigDecimal projectMoney = BigDecimal.ZERO;
                        projectMoneyMap.put(geoLogInfo.getProjectId(), projectMoney.add(jinEr));
                    }
                }
            }
        }
        // 遍历值
        List<LogExport> exportList = new ArrayList<>();
        for (Map.Entry<String, List<SysGeoLog>> entry : geoLogMap.entrySet()) {
            String userName = entry.getKey();
            List<SysGeoLog> value = entry.getValue();
            LogExport logExport = this.integratedOutputValueSelf(value, userName);
            //检查人
            if (userCheckMap.containsKey(userName)) {
                BigDecimal multiplier = new BigDecimal("0.05");
                allUserMoney = allUserMoney.multiply(multiplier);
                logExport.setType52_jr(allUserMoney);
                logExport.setTotal_money(logExport.getTotal_money().add(allUserMoney));
            }
            //负责人
            if (projectsMap.containsKey(userName)) {
                BigDecimal allProjectMoney = BigDecimal.ZERO;
                List<Long> projectIdArr = projectsMap.get(userName);
                for (Long pId : projectIdArr){
                    if (projectMoneyMap.containsKey(pId)) {
                        allProjectMoney = allProjectMoney.add(projectMoneyMap.get(pId));
                    }
                }
                BigDecimal multiplier = new BigDecimal("0.08");
                allProjectMoney = allProjectMoney.multiply(multiplier);
                logExport.setType51_jr(allProjectMoney);
                logExport.setTotal_money(logExport.getTotal_money().add(allProjectMoney));
            }

            Double fitCoefficient = (double)1;
            Double workCoefficient = (double)1;
            Double userCoefficientMap = (double)1;
            if (fitCoefficientMap.containsKey(userName)){
                fitCoefficient = fitCoefficientMap.get(userName);
            }
            if (workCoefficientMap.containsKey(userName)){
                workCoefficient = workCoefficientMap.get(userName);
            }
            if (geoUserCoefficientMap.containsKey(userName)){
                userCoefficientMap = geoUserCoefficientMap.get(userName);
            }

            logExport.setType51_gzl(fitCoefficient);
            logExport.setType52_gzl(workCoefficient);
            logExport.setType53_gzl(userCoefficientMap);

            BigDecimal bigDecimalFitCoefficient = new BigDecimal(fitCoefficient);
            BigDecimal bigDecimalWorkCoefficient = new BigDecimal(workCoefficient);
            BigDecimal bigDecimalUserCoefficient = new BigDecimal(userCoefficientMap);
            logExport.setTotal_money(logExport.getTotal_money().multiply(bigDecimalFitCoefficient).multiply(bigDecimalWorkCoefficient).multiply(bigDecimalUserCoefficient));

            exportList.add(logExport);
        }
        return getDataTable(exportList);
    }
}
