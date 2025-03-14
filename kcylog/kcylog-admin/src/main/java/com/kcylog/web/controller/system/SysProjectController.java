package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Anonymous;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.domain.entity.SysUser;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelMultUtil;
import com.kcylog.common.utils.poi.ExcelUtil;
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

    @Autowired
    private IFqProjectProcessService fqProjectProcessService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询项目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        List<Long> projectId = new ArrayList<>();
        for (SysProject project : list) {
            project.setMapShow((long)0);
            projectId.add(project.getProjectId());
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

        List<SysProjectGeoinfo> sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectId);
        Map<Long, Integer> keyValueMap = new HashMap<>();
        for (SysProjectGeoinfo obj : sysProjectGeoinfo){
            keyValueMap.put(obj.getProjectId(), 1);
        }
        for (SysProject project : list) {
            if (keyValueMap.containsKey(project.getProjectId())){
                project.setMapShow((long)1);
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
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody SysProject sysProject) {
        sysProjectService.updateSysProject(sysProject);
        // 经营产值计算
        String[] stringArray = new String[1];
        stringArray[0] = String.valueOf(sysProject.getProjectId());
        sysProjectService.jsProjectCz(stringArray);
        sysProjectService.jsProjectCzXs(stringArray);
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
                project.setMapShow((long)0);
            }
            List<SysProjectValue> projectValue = sysProjectValueService.selectSysProjectValueListByProjectIds(projectIds);
            Map<Long, BigDecimal> hashMap = new HashMap<>();
            for (SysProjectValue value : projectValue){
                if (!hashMap.containsKey(value.getProjectId())){
                    hashMap.put(value.getProjectId(), value.getProportion());
                }else {
                    hashMap.put(value.getProjectId(), hashMap.get(value.getProjectId()).add(value.getProportion()));
                }
            }

            List<FqProjectProcess> fqProjectProcess = fqProjectProcessService.selectFqProjectProcessByProjectIds(projectIds);
            for (SysProject project : list){
                if (hashMap.containsKey(project.getProjectId())){
                    if (hashMap.get(project.getProjectId()).compareTo(new BigDecimal(100)) == 0){
                        project.setOperateStatus(1);
                    }else {
                        project.setOperateStatus(0);
                    }
                }else {
                    project.setOperateStatus(0);
                }
                if (project.getFqProjectProcessList() == null){
                    project.setFqProjectProcessList(new ArrayList<>());
                }
                for (FqProjectProcess fqProjectProcess1 : fqProjectProcess){
                    if (fqProjectProcess1.getProjectId().equals(project.getProjectId())){
                        project.getFqProjectProcessList().add(fqProjectProcess1);
                    }
                }
            }
            List<SysProjectGeoinfo> sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectIds);
            Map<Long, Integer> keyValueMap = new HashMap<>();
            for (SysProjectGeoinfo obj : sysProjectGeoinfo){
                keyValueMap.put(obj.getProjectId(), 1);
            }
            for (SysProject project : list) {
                if (keyValueMap.containsKey(project.getProjectId())){
                    project.setMapShow((long)1);
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
        List<FqProjectProcess> list = fqProjectProcessService.selectFqProjectProcessList(sysProject);
        List<Long> projectId = new ArrayList<>();
        for (FqProjectProcess projectProcess : list) {
            projectProcess.setMapShow((long)0);
            projectId.add(projectProcess.getProjectId());
                //计算收件提前天数
                if (projectProcess.getReceiveStatus() != null && projectProcess.getReceiveStatus() == 2){
                    if (projectProcess.getReceiveTime() == null || projectProcess.getReceiveTime().isEmpty() || projectProcess.getReceiveCutoffTime() == null || projectProcess.getReceiveCutoffTime().isEmpty()) {
                        projectProcess.getProjectList().setReceiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(projectProcess.getReceiveCutoffTime());
                        Date endDate = dateFormat.parse(projectProcess.getReceiveTime());
                        long differenceInMilliseconds = startDate.getTime() - endDate.getTime();
                        long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                        projectProcess.getProjectList().setReceiveDays((int)differenceInDays);
                    }
                }else {
                    if (projectProcess.getReceiveCutoffTime() == null || projectProcess.getReceiveCutoffTime().isEmpty()) {
                        projectProcess.getProjectList().setReceiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(projectProcess.getReceiveCutoffTime());
                        Date currentDate = new Date();
                        long timeDifference = startDate.getTime() - currentDate.getTime();
                        long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                        projectProcess.getProjectList().setReceiveDays((int)daysDifference);
                    }
                }

                //计算归档提前天数
                if (projectProcess.getCheckStatus() != null && projectProcess.getCheckStatus() == 2){
                    if (projectProcess.getArchiveTime() == null || projectProcess.getArchiveTime().isEmpty() || projectProcess.getRectifyCutoffTime() == null || projectProcess.getRectifyCutoffTime().isEmpty()) {
                        projectProcess.getProjectList().setArchiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(projectProcess.getRectifyCutoffTime());
                        Date endDate = dateFormat.parse(projectProcess.getArchiveTime());
                        long differenceInMilliseconds = startDate.getTime() - endDate.getTime();
                        long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                        projectProcess.getProjectList().setArchiveDays((int)differenceInDays);
                    }
                }else {
                    if (projectProcess.getRectifyCutoffTime() == null || projectProcess.getRectifyCutoffTime().isEmpty()) {
                        projectProcess.getProjectList().setArchiveDays(0);
                    }else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(projectProcess.getRectifyCutoffTime());
                        Date currentDate = new Date();
                        long timeDifference = startDate.getTime() - currentDate.getTime();
                        long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                        projectProcess.getProjectList().setArchiveDays((int)daysDifference);
                    }
                }
        }

        List<SysProjectGeoinfo> sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectId);
        Map<Long, Integer> keyValueMap = new HashMap<>();
        for (SysProjectGeoinfo obj : sysProjectGeoinfo){
            keyValueMap.put(obj.getProjectId(), 1);
        }
        for (FqProjectProcess projectProcess : list) {
            if (keyValueMap.containsKey(projectProcess.getProjectId())){
                projectProcess.setMapShow((long)1);
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/listProjectWaitOneCheck")
    public TableDataInfo listProjectWaitOneCheck(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectWaitOneCheck(sysProject);
        List<Long> projectId = new ArrayList<>();
        for (SysProject project : list) {
            project.setMapShow((long)0);
            projectId.add(project.getProjectId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (project.getProjectEndAlias() != null && !project.getProjectEndAlias().isEmpty() && !project.getProjectEndAlias().equals("")){
                Date startDate = dateFormat.parse(project.getProjectEndAlias());
                Date currentDate = new Date();
                long timeDifference = startDate.getTime() - currentDate.getTime();
                long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                project.setOneCheckDays((int)daysDifference);
            }else {
                project.setOneCheckDays(0);
            }
        }
        List<SysProjectGeoinfo> sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectId);
        Map<Long, Integer> keyValueMap = new HashMap<>();
        for (SysProjectGeoinfo obj : sysProjectGeoinfo){
            keyValueMap.put(obj.getProjectId(), 1);
        }
        for (SysProject project : list) {
            if (keyValueMap.containsKey(project.getProjectId())){
                project.setMapShow((long)1);
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/listProjectWaitTwoCheck")
    public TableDataInfo listProjectWaitTwoCheck(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectWaitTwoCheck(sysProject);
        List<Long> projectId = new ArrayList<>();
        for (SysProject project : list) {
            project.setMapShow((long)0);
            projectId.add(project.getProjectId());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (project.getProjectEndAlias() != null && !project.getProjectEndAlias().isEmpty() && !project.getProjectEndAlias().equals("")){
                Date startDate = dateFormat.parse(project.getProjectEndAlias());
                Date currentDate = new Date();
                long timeDifference = startDate.getTime() - currentDate.getTime();
                long daysDifference = (long) Math.ceil((double) timeDifference / (1000 * 60 * 60 * 24));
                project.setTwoCheckDays((int)daysDifference);
            }else {
                project.setTwoCheckDays(0);
            }
        }
        List<SysProjectGeoinfo> sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectId);
        Map<Long, Integer> keyValueMap = new HashMap<>();
        for (SysProjectGeoinfo obj : sysProjectGeoinfo){
            keyValueMap.put(obj.getProjectId(), 1);
        }
        for (SysProject project : list) {
            if (keyValueMap.containsKey(project.getProjectId())){
                project.setMapShow((long)1);
            }
        }
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


    @GetMapping("/listProjectStatisticsData")
    public TableDataInfo listProjectStatisticsData(SysProject sysProject) {
        List<SysProject> list = sysProjectService.listProjectOperateTJ(sysProject);

        return getDataTable(list);
    }

    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping("/projectCqBz")
    public AjaxResult editProjectCqBz(@RequestBody SysProject sysProject) {
        sysProjectService.updateSysProjectCqBz(sysProject);
        return toAjax(1);
    }

    @GetMapping("/listProjectOperateCq")
    public TableDataInfo listProjectOperateCq(SysProject sysProject) {
        List<SysProject> list = sysProjectService.listProjectOperateCq(sysProject);
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

    @PostMapping("/exportOperating")
    public void exportOperating(HttpServletResponse response, SysProject sysProject) throws ParseException {
        List<SysProject> list = sysProjectService.listProjectOperateExport(sysProject);
        List<OperatingExport> operatingExportList = new ArrayList<>();
        int num = 0;
        for (SysProject project : list){
            if (project.getSubpackageType() != null && project.getSubpackageType() != 0 && project.getSubpackageType() != 1){
                //有分包
                for (ProjectChargeInfo chargeInfo : project.getProjectChargeInfo()){
                    OperatingExport operatingExport = new OperatingExport();
                    num ++;
                    operatingExport.setProjectNum(project.getProjectNum());
                    operatingExport.setProjectNameAlias(project.getProjectNameAlias());
                    operatingExport.setRequesterAlias(project.getRequesterAlias());
                    operatingExport.setValueType("分院自联业务");
                    operatingExport.setProjectType(project.getProjectType());
                    operatingExport.setReceptionist(project.getReceptionist());
                    operatingExport.setWorkloadAlias(project.getWorkloadAlias());
                    operatingExport.setUserNameAlias(project.getUserNameAlias());
                    operatingExport.setDepartment(project.getDepartment());
                    operatingExport.setOperate(project.getOperate());
                    operatingExport.setProjectCoefficientMoney(project.getProjectCoefficientMoney());
                    operatingExport.setContractNo(project.getContractNo());
                    operatingExport.setContractAmount(project.getContractAmount());
                    operatingExport.setProjectStartAlias(project.getProjectStartAlias());
                    operatingExport.setTwoCheck(project.getTwoCheck());
                    operatingExport.setSubpackageType("是");
                    if (project.getAfterTime() != null && !project.getAfterTime().equals("")){
                        operatingExport.setAfterTime("是");
                    }else {
                        operatingExport.setAfterTime("否");
                    }
                    if (project.getProjectEndAlias() != null && project.getTwoCheck() != null && !project.getProjectEndAlias().equals("") && !project.getTwoCheck().equals("")){
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(project.getProjectEndAlias());
                        Date endDate = dateFormat.parse(project.getTwoCheck());
                        long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
                        long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                        if (differenceInDays >= 0){
                            operatingExport.setIsOverdue("否");
                        }else {
                            operatingExport.setIsOverdue("是");
                            operatingExport.setOverDay((int) differenceInDays);
                        }
                    }else {
                        operatingExport.setIsOverdue("安排结束时间缺失");
                        operatingExport.setOverDay(0);
                    }

                    operatingExport.setDurationFactor(project.getDurationFactor());
                    operatingExport.setProjectCoefficient(project.getProjectCoefficient());
                    operatingExport.setQualityCoefficient(project.getQualityCoefficient());
                    operatingExport.setNum(num);
                    operatingExport.setFirmName(chargeInfo.getFirmName());
                    operatingExport.setSubcontractNo(chargeInfo.getSubcontractNo());
                    operatingExport.setSettleMoney(project.getFbMoney());
                    operatingExport.setBjRemark(project.getBjRemark());
                    operatingExportList.add(operatingExport);
                }
            }else {
                //无分包
                OperatingExport operatingExport = new OperatingExport();
                num ++;
                operatingExport.setNum(num);
                operatingExport.setProjectNum(project.getProjectNum());
                operatingExport.setProjectNameAlias(project.getProjectNameAlias());
                operatingExport.setRequesterAlias(project.getRequesterAlias());
                operatingExport.setValueType("分院自联业务");
                operatingExport.setProjectType(project.getProjectType());
                operatingExport.setReceptionist(project.getReceptionist());
                operatingExport.setWorkloadAlias(project.getWorkloadAlias());
                operatingExport.setUserNameAlias(project.getUserNameAlias());
                operatingExport.setDepartment(project.getDepartment());
                operatingExport.setOperate(project.getOperate());
                operatingExport.setProjectCoefficientMoney(project.getProjectCoefficientMoney());
                operatingExport.setContractNo(project.getContractNo());
                operatingExport.setContractAmount(project.getContractAmount());
                operatingExport.setProjectStartAlias(project.getProjectStartAlias());
                operatingExport.setTwoCheck(project.getTwoCheck());
                operatingExport.setSubpackageType("否");
                if (project.getAfterTime() != null && !project.getAfterTime().equals("")){
                    operatingExport.setAfterTime("是");
                }else {
                    operatingExport.setAfterTime("否");
                }
                if (project.getProjectEndAlias() != null && project.getTwoCheck() != null && !project.getProjectEndAlias().equals("") && !project.getTwoCheck().equals("")) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = dateFormat.parse(project.getProjectEndAlias());
                    Date endDate = dateFormat.parse(project.getTwoCheck());
                    long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
                    long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                    if (differenceInDays >= 0){
                        operatingExport.setIsOverdue("否");
                    }else {
                        operatingExport.setIsOverdue("是");
                        operatingExport.setOverDay((int) differenceInDays);
                    }
                }else {
                    operatingExport.setIsOverdue("安排结束时间缺失");
                    operatingExport.setOverDay(0);
                }
                operatingExport.setSettleMoney(project.getFbMoney());
                operatingExport.setDurationFactor(project.getDurationFactor());
                operatingExport.setProjectCoefficient(project.getProjectCoefficient());
                operatingExport.setQualityCoefficient(project.getQualityCoefficient());
                operatingExport.setBjRemark(project.getBjRemark());
                operatingExportList.add(operatingExport);
            }
        }
        ExcelUtil<OperatingExport> util = new ExcelUtil<OperatingExport>(OperatingExport.class);
        util.exportExcel(response, operatingExportList, "项目结算单");
    }

    @GetMapping("/listProductionDetails")
    public TableDataInfo listProductionDetails(SysProject sysProject) {
        List<SysProjectValue> projectValue = sysProjectValueService.selectSysProjectValueListProductionDetails(sysProject);
        return getDataTable(projectValue);
    }

    @Log(title = "抽签过程", businessType = BusinessType.UPDATE)
    @PutMapping("/projectDrawStatus")
    public AjaxResult editProjectDrawStatus(@RequestBody SysProject sysProject) {
        sysProjectService.updateSysProjectDrawStatus(sysProject);
        return toAjax(1);
    }

    /**
     * 查询项目列表
     */
    @GetMapping("/listForFb")
    public TableDataInfo listForFb(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectListForFb(sysProject);
        List<Long> projectId = new ArrayList<>();
        for (SysProject project : list) {
            project.setMapShow((long)0);
            projectId.add(project.getProjectId());
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
        List<SysProjectGeoinfo> sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectId);
        Map<Long, Integer> keyValueMap = new HashMap<>();
        for (SysProjectGeoinfo obj : sysProjectGeoinfo){
            keyValueMap.put(obj.getProjectId(), 1);
        }
        for (SysProject project : list) {
            if (keyValueMap.containsKey(project.getProjectId())){
                project.setMapShow((long)1);
            }
        }
        return getDataTable(list);
    }

    @PostMapping("/exportOperatingAssignPersonnel")
    public void exportOperatingAssignPersonnel(HttpServletResponse response, SysProject sysProject) throws ParseException {
        List<SysProject> list = sysProjectService.listProjectOperateExport(sysProject);
        List<OperatingExport> operatingExportList = new ArrayList<>();

        SysUser user = new SysUser();
        List<SysUser> userList = sysUserService.selectUserList(user);
        Map<String, String> userMap = new HashMap<>();
        for (SysUser obj : userList){
            userMap.put(obj.getUserName(),obj.getDept().getDeptName());
        }
        String[] dl = {"蔡龙洲1", "蔡龙洲2", "蔡龙洲3", "蔡龙洲4", "蔡龙洲5", "蔡龙洲6", "蔡龙洲7", "蔡龙洲8", "蔡龙洲9", "蔡龙洲10"};
        String[] gc = {"张功锋1", "张功锋2", "张功锋3", "张功锋4", "张功锋5", "张功锋6", "张功锋7", "张功锋8", "张功锋9", "张功锋10"};
        String[] bd = {"简煊祥1", "简煊祥2", "简煊祥3", "简煊祥4", "简煊祥5", "简煊祥6", "简煊祥7", "简煊祥8", "简煊祥9", "简煊祥10"};
        String[] gx = {"朱化弟1", "朱化弟2", "朱化弟3", "朱化弟4", "朱化弟5", "朱化弟6", "朱化弟7", "朱化弟8", "朱化弟9", "朱化弟10"};
        for (String obj1 : dl){
            userMap.put(obj1, "地理信息部");
        }
        for (String obj2 : gc){
            userMap.put(obj2, "工程测绘部");
        }
        for (String obj3 : bd){
            userMap.put(obj3, "不动产测绘部");
        }
        for (String obj4 : gx){
            userMap.put(obj4, "管线工程部");
        }

        int num = 0;
        for (SysProject project : list){
            if (project.getSubpackageType() != null && project.getSubpackageType() != 0 && project.getSubpackageType() != 1){
                //有分包
                for (ProjectChargeInfo chargeInfo : project.getProjectChargeInfo()) {
                    num ++;
                    for (SysProjectValue obj1 : project.getProjectValue()) {
                        OperatingExport operatingExport = new OperatingExport();
                        operatingExport.setProjectNum(project.getProjectNum());
                        operatingExport.setProjectNameAlias(project.getProjectNameAlias());
                        operatingExport.setRequesterAlias(project.getRequesterAlias());
                        operatingExport.setValueType("分院自联业务");
                        operatingExport.setProjectType(project.getProjectType());
                        operatingExport.setReceptionist(project.getReceptionist());
                        operatingExport.setWorkloadAlias(project.getWorkloadAlias());
                        operatingExport.setUserNameAlias(project.getUserNameAlias());
                        operatingExport.setDepartment(project.getDepartment());
                        operatingExport.setOperate(project.getOperate());
                        operatingExport.setProjectCoefficientMoney(project.getProjectCoefficientMoney());
                        operatingExport.setContractNo(project.getContractNo());
                        operatingExport.setContractAmount(project.getContractAmount());
                        operatingExport.setProjectStartAlias(project.getProjectStartAlias());
                        operatingExport.setTwoCheck(project.getTwoCheck());
                        operatingExport.setSubpackageType("是");
                        if (project.getAfterTime() != null && !project.getAfterTime().equals("")) {
                            operatingExport.setAfterTime("是");
                        } else {
                            operatingExport.setAfterTime("否");
                        }
                        if (project.getProjectEndAlias() != null && project.getTwoCheck() != null && !project.getProjectEndAlias().equals("") && !project.getTwoCheck().equals("")) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date startDate = dateFormat.parse(project.getProjectEndAlias());
                            Date endDate = dateFormat.parse(project.getTwoCheck());
                            long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
                            long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                            if (differenceInDays >= 0) {
                                operatingExport.setIsOverdue("否");
                            } else {
                                operatingExport.setIsOverdue("是");
                                operatingExport.setOverDay((int) differenceInDays);
                            }
                        } else {
                            operatingExport.setIsOverdue("安排结束时间缺失");
                            operatingExport.setOverDay(0);
                        }

                        operatingExport.setDurationFactor(project.getDurationFactor());
                        operatingExport.setProjectCoefficient(project.getProjectCoefficient());
                        operatingExport.setQualityCoefficient(project.getQualityCoefficient());
                        operatingExport.setNum(num);
                        operatingExport.setFirmName(chargeInfo.getFirmName());
                        operatingExport.setSubcontractNo(chargeInfo.getSubcontractNo());
                        operatingExport.setSettleMoney(project.getFbMoney());
                        operatingExport.setBjRemark(project.getBjRemark());
                        // 人员安排信息
                        operatingExport.setUserName(obj1.getUserName());
                        if (userMap.containsKey(obj1.getUserName())){
                            operatingExport.setDept(userMap.get(obj1.getUserName()));
                        }
                        operatingExport.setProportion(obj1.getProportion());
                        operatingExport.setMoney(obj1.getMoney());
                        operatingExport.setProfitMoney(obj1.getProfitMoney());
                        operatingExport.setCoefficientMoney(obj1.getCoefficientMoney());
                        operatingExport.setCoefficientProfitMoney(obj1.getCoefficientProfitMoney());
                        operatingExportList.add(operatingExport);
                    }
                }
            }else {
                num ++;
                for(SysProjectValue obj1 : project.getProjectValue()){
                    //无分包
                    OperatingExport operatingExport = new OperatingExport();
                    operatingExport.setNum(num);
                    operatingExport.setProjectNum(project.getProjectNum());
                    operatingExport.setProjectNameAlias(project.getProjectNameAlias());
                    operatingExport.setRequesterAlias(project.getRequesterAlias());
                    operatingExport.setValueType("分院自联业务");
                    operatingExport.setProjectType(project.getProjectType());
                    operatingExport.setReceptionist(project.getReceptionist());
                    operatingExport.setWorkloadAlias(project.getWorkloadAlias());
                    operatingExport.setUserNameAlias(project.getUserNameAlias());
                    operatingExport.setDepartment(project.getDepartment());
                    operatingExport.setOperate(project.getOperate());
                    operatingExport.setProjectCoefficientMoney(project.getProjectCoefficientMoney());
                    operatingExport.setContractNo(project.getContractNo());
                    operatingExport.setContractAmount(project.getContractAmount());
                    operatingExport.setProjectStartAlias(project.getProjectStartAlias());
                    operatingExport.setTwoCheck(project.getTwoCheck());
                    operatingExport.setSubpackageType("否");
                    if (project.getAfterTime() != null && !project.getAfterTime().equals("")){
                        operatingExport.setAfterTime("是");
                    }else {
                        operatingExport.setAfterTime("否");
                    }
                    if (project.getProjectEndAlias() != null && project.getTwoCheck() != null && !project.getProjectEndAlias().equals("") && !project.getTwoCheck().equals("")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date startDate = dateFormat.parse(project.getProjectEndAlias());
                        Date endDate = dateFormat.parse(project.getTwoCheck());
                        long differenceInMilliseconds = endDate.getTime() - startDate.getTime();
                        long differenceInDays = (long) Math.ceil((double) differenceInMilliseconds / (1000 * 3600 * 24));
                        if (differenceInDays >= 0){
                            operatingExport.setIsOverdue("否");
                        }else {
                            operatingExport.setIsOverdue("是");
                            operatingExport.setOverDay((int) differenceInDays);
                        }
                    }else {
                        operatingExport.setIsOverdue("安排结束时间缺失");
                        operatingExport.setOverDay(0);
                    }
                    operatingExport.setSettleMoney(project.getFbMoney());
                    operatingExport.setDurationFactor(project.getDurationFactor());
                    operatingExport.setProjectCoefficient(project.getProjectCoefficient());
                    operatingExport.setQualityCoefficient(project.getQualityCoefficient());
                    operatingExport.setBjRemark(project.getBjRemark());
                    // 人员安排信息
                    operatingExport.setUserName(obj1.getUserName());
                    if (userMap.containsKey(obj1.getUserName())){
                        operatingExport.setDept(userMap.get(obj1.getUserName()));
                    }
                    operatingExport.setProportion(obj1.getProportion());
                    operatingExport.setMoney(obj1.getMoney());
                    operatingExport.setProfitMoney(obj1.getProfitMoney());
                    operatingExport.setCoefficientMoney(obj1.getCoefficientMoney());
                    operatingExport.setCoefficientProfitMoney(obj1.getCoefficientProfitMoney());
                    operatingExportList.add(operatingExport);
                }

            }
        }
        ExcelUtil<OperatingExport> util = new ExcelUtil<OperatingExport>(OperatingExport.class);
        util.exportExcel(response, operatingExportList, "项目结算单");
    }

    @GetMapping("/listForFbTwoCheck")
    public TableDataInfo listForFbTwoCheck(SysProject sysProject) throws ParseException {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectListForFbTwoCheck(sysProject);
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

    @GetMapping("/listProjectStatisticsDataForDept")
    public AjaxResult listProjectStatisticsDataForDept(SysProject sysProject) {
        List<SysProjectValue> list = sysProjectValueService.listProjectOperateTJ(sysProject);
        List<SysProjectValue> listSpecial = sysProjectValueService.listProjectOperateTJForSpecialPersonnel(sysProject);
        Map<String, SysProjectValue> listSpecialMap = new HashMap<>();
        for (SysProjectValue value1 : listSpecial){
            if (listSpecialMap.containsKey(value1.getFqSysProject().getDepartment())){
                SysProjectValue newSysProjectValue = new SysProjectValue();
                newSysProjectValue.setMoney(listSpecialMap.get(value1.getFqSysProject().getDepartment()).getMoney().add(value1.getMoney()));
                newSysProjectValue.setProfitMoney(listSpecialMap.get(value1.getFqSysProject().getDepartment()).getProfitMoney().add(value1.getProfitMoney()));
                listSpecialMap.put(value1.getFqSysProject().getDepartment(), newSysProjectValue);
            }else {
                SysProjectValue newSysProjectValue = new SysProjectValue();
                newSysProjectValue.setMoney(value1.getMoney());
                newSysProjectValue.setProfitMoney(value1.getProfitMoney());
                listSpecialMap.put(value1.getFqSysProject().getDepartment(),newSysProjectValue);
            }
        }
        SysUser user = new SysUser();
        List<SysUser> userList = sysUserService.selectUserList(user);
        Map<String, String> userMap = new HashMap<>();
        for (SysUser obj : userList){
            userMap.put(obj.getUserName(),obj.getDept().getDeptName());
        }
        String[] dl = {"蔡龙洲1", "蔡龙洲2", "蔡龙洲3", "蔡龙洲4", "蔡龙洲5", "蔡龙洲6", "蔡龙洲7", "蔡龙洲8", "蔡龙洲9", "蔡龙洲10"};
        String[] gc = {"张功锋1", "张功锋2", "张功锋3", "张功锋4", "张功锋5", "张功锋6", "张功锋7", "张功锋8", "张功锋9", "张功锋10"};
        String[] bd = {"简煊祥1", "简煊祥2", "简煊祥3", "简煊祥4", "简煊祥5", "简煊祥6", "简煊祥7", "简煊祥8", "简煊祥9", "简煊祥10"};
        String[] gx = {"朱化弟1", "朱化弟2", "朱化弟3", "朱化弟4", "朱化弟5", "朱化弟6", "朱化弟7", "朱化弟8", "朱化弟9", "朱化弟10"};
        for (String obj1 : dl){
            userMap.put(obj1, "地理信息部");
        }
        for (String obj2 : gc){
            userMap.put(obj2, "工程测绘部");
        }
        for (String obj3 : bd){
            userMap.put(obj3, "不动产测绘部");
        }
        for (String obj4 : gx){
            userMap.put(obj4, "管线工程部");
        }

        Map<String, SysProjectValue> moneyMap = new HashMap<>();
        for (SysProjectValue objTemp : list){
            if (userMap.containsKey(objTemp.getUserName())){
                String dept = userMap.get(objTemp.getUserName());
                if (moneyMap.containsKey(dept)){
                    SysProjectValue newSysProjectValue = new SysProjectValue();
                    newSysProjectValue.setMoney(moneyMap.get(dept).getMoney().add(objTemp.getMoney()));
                    newSysProjectValue.setProfitMoney(moneyMap.get(dept).getProfitMoney().add(objTemp.getProfitMoney()));
                    moneyMap.put(dept, newSysProjectValue);
                }else {
                    SysProjectValue newSysProjectValue = new SysProjectValue();
                    newSysProjectValue.setMoney(objTemp.getMoney());
                    newSysProjectValue.setProfitMoney(objTemp.getProfitMoney());
                    moneyMap.put(dept, newSysProjectValue);
                }
            }
        }

        for (Map.Entry<String, SysProjectValue> entry : listSpecialMap.entrySet()) {
            if (moneyMap.containsKey(entry.getKey())){
                SysProjectValue newSysProjectValue = new SysProjectValue();
                newSysProjectValue.setMoney(moneyMap.get(entry.getKey()).getMoney().add(entry.getValue().getMoney()));
                newSysProjectValue.setProfitMoney(moneyMap.get(entry.getKey()).getProfitMoney().add(entry.getValue().getProfitMoney()));
                moneyMap.put(entry.getKey(), newSysProjectValue);
            }else {
                SysProjectValue newSysProjectValue = new SysProjectValue();
                newSysProjectValue.setMoney(entry.getValue().getMoney());
                newSysProjectValue.setProfitMoney(entry.getValue().getProfitMoney());
                moneyMap.put(entry.getKey(), newSysProjectValue);
            }
        }

        return success(moneyMap);
    }

    @GetMapping("/listProjectOperateValue")
    public TableDataInfo listProjectOperateValue(SysProject sysProject) {
        List<SysProjectValue> list = sysProjectValueService.listProjectOperateValue(sysProject);
        for(SysProjectValue obj : list){
            obj.setProjectNum((long)0);
            obj.setFbNum((long)0);
        }
        List<SysProject> projectList = sysProjectService.listProjectOperateValue(sysProject);
        for(SysProjectValue obj1 : list){
            for(SysProject obj2 : projectList){
                if (Objects.equals(obj1.getUserName(), obj2.getUserNameAlias())){
                    obj1.setProjectNum(obj1.getProjectNum() + 1);
                    if (obj2.getSubpackageType() == 2 || obj2.getSubpackageType() == 3){
                        obj1.setFbNum(obj1.getFbNum() + 1);
                    }
                }
            }
        }
        return getDataTable(list);
    }

}
