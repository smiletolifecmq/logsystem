package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Anonymous;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelMultUtil;
import com.kcylog.system.common.*;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 项目Controller
 *
 * @author ruoyi
 * @date 2023-09-07
 */
@RestController
@RequestMapping("/system/project")
public class SysProjectController extends BaseController {
    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysProjectRelationService sysProjectRelationService;

    @Autowired
    private ISysProjectValueService sysProjectValueService;

    @Autowired
    private ISysReviewSubService sysReviewSubService;

    @Autowired
    private ISysReviewSubEmployeeService sysReviewSubEmployeeService;

    @Autowired
    private ISysReviewSubProcessService sysReviewSubProcessService;

    @Autowired
    private ISysProjectGeoinfoService sysProjectGeoinfoService;

    @Autowired
    private ISysProjectSelectmapTfinfoService sysProjectSelectmapTfinfoService;

    /**
     * 查询项目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        for (SysProject project : list) {
            if (project.getWorkStatus() != null && project.getWorkStatus() == 4){
                if (project.getProjectEndAlias() == null || project.getProjectEndAlias().isEmpty() || project.getDoTime() == null || project.getDoTime().isEmpty()) {
                    project.setLeadTime(0);
                }else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = dateFormat.parse(project.getProjectEndAlias());
                    Date endDate = dateFormat.parse(project.getDoTime());
                    long differenceInMilliseconds = startDate.getTime() - endDate.getTime();
                    long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                    project.setLeadTime((int)differenceInDays);
                }
            }else {
                if (project.getProjectEndAlias() == null || project.getProjectEndAlias().isEmpty()) {
                    project.setLeadTime(0);
                }else {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = dateFormat.parse(project.getProjectEndAlias());
                    Date currentDate = new Date();
                    long timeDifference = startDate.getTime() - currentDate.getTime();
                    long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                    project.setLeadTime((int)daysDifference);
                }
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出项目列表
     */
    @Log(title = "项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProject sysProject) {
        List<SysProject> list = sysProjectService.selectSysProjectExportList(sysProject);
        for (SysProject project : list) {
            //占位
            if (project.getProjectValue() == null || project.getProjectValue().size() == 0){
                List<SysProjectValue> projectValueObj = new ArrayList<>();
                SysProjectValue projectValue = new SysProjectValue();
                projectValueObj.add(projectValue);
                project.setProjectValue(projectValueObj);
            }
            if (project.getProjectCar() == null || project.getProjectCar().size() == 0){
                List<SysProjectCar> projectCarObj = new ArrayList<>();
                SysProjectCar projectCar = new SysProjectCar();
                projectCarObj.add(projectCar);
                project.setProjectCar(projectCarObj);
            }

            List<ProjectEmployee> employeeObj = new ArrayList<>();
            List<ProjectSubcontract> subcontractObj = new ArrayList<>();
            project.setProjectEmployee(employeeObj);
            project.setProjectSubcontract(subcontractObj);
            for (SysReviewSub reviewSub:project.getReviewSub()){
                ProjectEmployee  employee = new ProjectEmployee();
                employee.setWorkload(reviewSub.getWorkload());
                employee.setPorjectMoney(reviewSub.getPorjectMoney());
                employee.setSubcontract(reviewSub.getSubcontract());
                employee.setEmploymentReason(reviewSub.getEmploymentReason());
                employee.setStartTime(reviewSub.getStartTime());
                employee.setEndTime(reviewSub.getEndTime());
                employee.setPeopleNum(reviewSub.getPeopleNum());
                employee.setBudgetDay(reviewSub.getBudgetDay());
                employee.setBudgetMoney(reviewSub.getBudgetMoney());
                project.getProjectEmployee().add(employee);

                ProjectSubcontract subcontract = new ProjectSubcontract();
                subcontract.setWorkcontent(reviewSub.getWorkcontent());
                subcontract.setSubType(reviewSub.getSubType());
                subcontract.setSubWorkload(reviewSub.getSubWorkload());
                subcontract.setRealWorkload(reviewSub.getRealWorkload());
                subcontract.setWinUnit(reviewSub.getWinUnit());
                subcontract.setLotTime(reviewSub.getLotTime());
                project.getProjectSubcontract().add(subcontract);
            }

            if (project.getProjectEmployee().size() == 0){
                ProjectEmployee  employee = new ProjectEmployee();
                project.getProjectEmployee().add(employee);
            }
        }
        ExcelMultUtil<SysProject> util = new ExcelMultUtil<SysProject>(SysProject.class, 4);
        util.exportExcel(response, list, "项目数据");
    }

    /**
     * 获取项目详细信息
     */
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") String projectId) {
        SysProject project = sysProjectService.selectSysProjectByProjectId(projectId);
        SysProjectRelation projectRelation = sysProjectRelationService.selectSysProjectRelationByProjectId(project.getProjectId());
        if (projectRelation != null){
            SysReviewSub reviewSub = sysReviewSubService.selectSysReviewSubByReviewId(projectRelation.getReviewId().toString());
            List<SysReviewSubEmployee> reviewSubEmployee = sysReviewSubEmployeeService.selectSysReviewSubEmployeeByReviewId(projectRelation.getReviewId());
            reviewSub.setReviewEmployee(reviewSubEmployee);
            SysReviewSubProcess reviewSubProcessObj = new SysReviewSubProcess();
            reviewSubProcessObj.setReviewId(projectRelation.getReviewId());
            List<SysReviewSubProcess> reviewSubProcess = sysReviewSubProcessService.selectSysReviewSubProcessList(reviewSubProcessObj);
            reviewSub.setReviewSubProcess(reviewSubProcess);
            project.setReviewSubOne(reviewSub);
        }
        return success(project);
    }

    /**
     * 新增项目
     */
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProject sysProject) {
        return toAjax(sysProjectService.insertSysProject(sysProject));
    }

    /**
     * 修改项目
     */
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProject sysProject) {
        sysProjectService.updateSysProject(sysProject);
        // 经营产值计算
        String[] stringArray = new String[1];
        stringArray[0] = String.valueOf(sysProject.getProjectId());
        sysProjectService.jsProjectCz(stringArray);
        // 利润产值计算
        SysProject project = sysProjectService.selectSysProjectByProjectId(String.valueOf(sysProject.getProjectId()));
        SysProjectRelation projectRelation = sysProjectRelationService.selectSysProjectRelationByProjectId(project.getProjectId());
        if (projectRelation != null){
            // 存在审核单
            SysReviewSub reviewSub = sysReviewSubService.selectSysReviewSubByReviewId(projectRelation.getReviewId().toString());
            SysReviewSubProcess reviewSubProcessObj = new SysReviewSubProcess();
            reviewSubProcessObj.setReviewId(projectRelation.getReviewId());
            List<SysReviewSubProcess> reviewSubProcess = sysReviewSubProcessService.selectSysReviewSubProcessList(reviewSubProcessObj);
            List<SysReviewSubEmployee> reviewSubEmployee = sysReviewSubEmployeeService.selectSysReviewSubEmployeeByReviewId(projectRelation.getReviewId());
            if (reviewSubEmployee != null && reviewSubEmployee.size() > 0 && ((reviewSub.getStatus() == 2 || reviewSub.getStatus() == 4) || reviewSubProcess.get(3).getStatus() == 1)){
                BigDecimal money = new BigDecimal(0);
                for (SysReviewSubEmployee reviewSubEmployee1 : reviewSubEmployee){
                    money = money.add(reviewSubEmployee1.getCost());
                }
                project.setGuGongMoney(money);
                sysProjectService.jsProjectLiRunCzForReview(project);
            }else {
                sysProjectService.jsProjectLiRunCz(stringArray);
            }
        }else {
            // 不存在审核单
            sysProjectService.jsProjectLiRunCz(stringArray);
        }

        return toAjax(1);
    }

    /**
     * 删除项目
     */
    @Log(title = "项目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable String[] projectIds) {
        return toAjax(sysProjectService.deleteSysProjectByProjectIds(projectIds));
    }

    @GetMapping("/getProjectRelation/{reviewType}")
    public TableDataInfo getProjectRelation(@PathVariable("reviewType") Integer reviewType) {
        List<SysProjectRelation> list = sysProjectRelationService.selectProjectRelationByReviewType(reviewType);
        return getDataTable(list);
    }


    @GetMapping(value = "/detail/{projectId}")
    public AjaxResult getProjectDetail(@PathVariable("projectId") String projectId) {
        return success(sysProjectService.selectSysProjectByProjectId(projectId));
    }

    //    @Log(title = "项目管理安排数据", businessType = BusinessType.INSERT)
    @Anonymous
    @CrossOrigin
    @PostMapping(value = "/addProject")
    public AjaxResult addProject(@RequestBody SysProject sysProject) {
        if (sysProjectService.checkProjectKeyUnique(sysProject.getProjectNum()) != null) {
            sysProjectService.updateSysProjectByProjectNum(sysProject);
        }else {
            sysProjectService.insertSysProject(sysProject);
        }
        return toAjax(1);
    }

    //    @Log(title = "项目管理安排数据", businessType = BusinessType.UPDATE)
    @Anonymous
    @CrossOrigin
    @PutMapping(value = "/updateProject")
    public AjaxResult editProject(@RequestBody SysProject sysProject) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (sysProject.getTwoCheck() != null && !sysProject.getTwoCheck().equals("")){
            Date twoCheckTime = dateFormat.parse(sysProject.getTwoCheck());
            sysProject.setTwoCheckTime(twoCheckTime);
        }
        return toAjax(sysProjectService.updateSysProjectByProjectNum(sysProject));
    }

    /**
     * 获取项目详细信息
     */
    @Anonymous
    @CrossOrigin
    @GetMapping(value = "/projectReview/{serialNum}")
    public TableDataInfo getProjectReviewInfo(@PathVariable("serialNum") String serialNum) {
        return getDataTable(sysReviewSubService.selectSysReviewBySerialNum(serialNum));
    }

    @GetMapping("/listProjectOperate")
    public TableDataInfo listProjectOperate(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.listProjectOperate(sysProject);
        if (list.size() != 0){
            List<Long> projectIds = new ArrayList<>();
            for (SysProject project : list){
                projectIds.add(project.getProjectId());
            }
            List<SysProjectValue> projectValue = sysProjectValueService.selectSysProjectValueListByProjectIds(projectIds);
            Map<Long, Integer> hashMap = new HashMap<>();
            for (SysProjectValue value : projectValue){
                if (!hashMap.containsKey(value.getProjectId())){
                    hashMap.put(value.getProjectId(), 1);
                }
            }
            for (SysProject project : list){
                if (hashMap.containsKey(project.getProjectId())){
                    project.setOperateStatus(hashMap.get(project.getProjectId()));
                }else {
                    project.setOperateStatus(0);
                }
            }
        }
        return getDataTable(list);
    }

    @Log(title = "产值结算", businessType = BusinessType.UPDATE)
    @PutMapping("/projectValue")
    @Transactional
    public AjaxResult editProjectValue(@RequestBody SysProject sysProject) {
        if (sysProject.getOutputStatus() == 1) {
            sysProjectService.updateOutputStatusByProjectId(sysProject);
        }
        sysProjectValueService.deleteSysProjectValueByProjectId(sysProject.getProjectId());
        if (sysProject.getProjectValue().size() != 0) {
            for (int i = 0; i < sysProject.getProjectValue().size(); i++) {
                SysProjectValue projectValue = sysProject.getProjectValue().get(i);
                sysProjectValueService.insertSysProjectValue(projectValue);
            }
        }
        return toAjax(1);
    }

    @Anonymous
    @CrossOrigin
    @PutMapping(value = "/updateLatterTime")
    public AjaxResult updateLatterTime(@RequestBody SysProject sysProject) {
        return toAjax(sysProjectService.updateLatterTime(sysProject));
    }

    @Log(title = "计算项目产值", businessType = BusinessType.UPDATE)
    @PostMapping("/jsProjectCz/{projectIds}")
    @Transactional
    public AjaxResult jsProjectCz(@PathVariable String[] projectIds, @RequestBody SysProject sysProject) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectIds", projectIds);
        params.put("fbMoney", sysProject.getFbMoney());
        sysProjectService.updateFbMoney(params);
        return toAjax(sysProjectService.jsProjectCz(projectIds));
    }

    @GetMapping("/listUpcoming")
    public TableDataInfo listUpcoming(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectListUpcoming(sysProject);
        return getDataTable(list);
    }

    @GetMapping("/listHandover")
    public TableDataInfo listHandover(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectListHandover(sysProject);

        for (SysProject project : list) {
            if (project.getFqProjectProcess() != null){
                //计算收件提前天数
                if (project.getFqProjectProcess().getReceiveStatus() != null && project.getFqProjectProcess().getReceiveStatus() == 2){
                    if (project.getFqProjectProcess().getReceiveTime() == null || project.getFqProjectProcess().getReceiveTime().isEmpty() || project.getFqProjectProcess().getReceiveCutoffTime() == null || project.getFqProjectProcess().getReceiveCutoffTime().isEmpty()) {
                        project.setReceiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(project.getFqProjectProcess().getReceiveCutoffTime());
                        Date endDate = dateFormat.parse(project.getFqProjectProcess().getReceiveTime());
                        long differenceInMilliseconds = startDate.getTime() - endDate.getTime();
                        long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                        project.setReceiveDays((int)differenceInDays);
                    }
                }else {
                    if (project.getFqProjectProcess().getReceiveCutoffTime() == null || project.getFqProjectProcess().getReceiveCutoffTime().isEmpty()) {
                        project.setReceiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(project.getFqProjectProcess().getReceiveCutoffTime());
                        Date currentDate = new Date();
                        long timeDifference = startDate.getTime() - currentDate.getTime();
                        long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                        project.setReceiveDays((int)daysDifference);
                    }
                }

                //计算归档提前天数
                if (project.getFqProjectProcess().getCheckStatus() != null && project.getFqProjectProcess().getCheckStatus() == 2){
                    if (project.getFqProjectProcess().getArchiveTime() == null || project.getFqProjectProcess().getArchiveTime().isEmpty() || project.getFqProjectProcess().getRectifyCutoffTime() == null || project.getFqProjectProcess().getRectifyCutoffTime().isEmpty()) {
                        project.setArchiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(project.getFqProjectProcess().getRectifyCutoffTime());
                        Date endDate = dateFormat.parse(project.getFqProjectProcess().getArchiveTime());
                        long differenceInMilliseconds = startDate.getTime() - endDate.getTime();
                        long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                        project.setArchiveDays((int)differenceInDays);
                    }
                }else {
                    if (project.getFqProjectProcess().getRectifyCutoffTime() == null || project.getFqProjectProcess().getRectifyCutoffTime().isEmpty()) {
                        project.setArchiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(project.getFqProjectProcess().getRectifyCutoffTime());
                        Date currentDate = new Date();
                        long timeDifference = startDate.getTime() - currentDate.getTime();
                        long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                        project.setArchiveDays((int)daysDifference);
                    }
                }
            }else {
                project.setReceiveDays(0);
                project.setArchiveDays(0);
            }

        }

        return getDataTable(list);
    }

    @GetMapping("/listProjectWaitOneCheck")
    public TableDataInfo listProjectWaitOneCheck(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectWaitOneCheck(sysProject);
        return getDataTable(list);
    }

    @GetMapping("/listProjectWaitTwoCheck")
    public TableDataInfo listProjectWaitTwoCheck(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectWaitTwoCheck(sysProject);
        return getDataTable(list);
    }

    @Anonymous
    @CrossOrigin
    @GetMapping("/get_project_geo/{projectId}")
    public AjaxResult projectGeo(@PathVariable("projectId") String projectId)
    {
        SysProject project = sysProjectService.selectSysProjectByProjectId(projectId);
        List<SysProjectGeoinfo> projectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectId(Long.valueOf(projectId));
        List<SysProjectSelectmapTfinfo> projectSelectmapTfinfo = sysProjectSelectmapTfinfoService.selectSysProjectSelectmapTfinfoByProjectId(Long.valueOf(projectId));
        ProjectGeoList projectGeoList = new ProjectGeoList();
        List<ProjectGeo> projectGeoArr = new ArrayList<>();
        List<Geotfinfo> geotfinfoArr = new ArrayList<>();
        if (project != null )
        {
            projectGeoList.setProjectCode(project.getProjectNum());
            projectGeoList.setProjectName(project.getProjectNameAlias());
            projectGeoList.setRequester(project.getRequesterAlias());
            projectGeoList.setRegisterTime(project.getRegisterTime());
            projectGeoList.setMapScale(project.getMapScale());
            if (projectGeoinfo != null){
                for (SysProjectGeoinfo geoInfo : projectGeoinfo){
                    ProjectGeo projectGeo = new ProjectGeo();
                    projectGeo.setGeometry(geoInfo.getGeometry());
                    projectGeo.setGeometry2000(geoInfo.getGeometry2000());
                    projectGeo.setBufferGeometry(geoInfo.getBufferGeometry());
                    projectGeo.setBufferGeometry2000(geoInfo.getBufferGeometry2000());
                    projectGeo.setGeometryGauss2000(geoInfo.getGeometryGauss2000());
                    projectGeo.setBufferGeometryGauss2000(geoInfo.getBufferGeometryGauss2000());
                    projectGeo.setBufferDistance(geoInfo.getBufferDistance());
                    projectGeoArr.add(projectGeo);
                }
            }
            if (projectSelectmapTfinfo != null){
                for (SysProjectSelectmapTfinfo selectmapTfinfo : projectSelectmapTfinfo){
                    Geotfinfo geotfinfo = new Geotfinfo();
                    geotfinfo.setMapCode(selectmapTfinfo.getMapCode());
                    geotfinfo.setMapAddinfo(selectmapTfinfo.getMapAddinfo());
                    geotfinfoArr.add(geotfinfo);
                }
            }
            projectGeoList.setProjectGeo(projectGeoArr);
            projectGeoList.setGeotfinfo(geotfinfoArr);
        }
        return success(projectGeoList);
    }
}
