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
import com.kcylog.web.controller.common.ProjectHJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private IBcProjectService bcProjectService;

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
        List<SysProjectGeoinfo> sysProjectGeoinfo = new ArrayList<SysProjectGeoinfo>();
        if (projectId.size() > 0  && projectId.size() < 200){
            sysProjectGeoinfo = sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectIds(projectId);
        }
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
            List<Long> projectId = new ArrayList<>();
            List<Long> projectIds = new ArrayList<>();
            for (SysProject project : list){
                projectId.add(project.getProjectId());
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

            if (projectId.size() > 0 && projectId.size() < 200){
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
            projectId.add(projectProcess.getProjectList().getProjectId());
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

        if (projectId.size() > 0 && projectId.size() < 200){
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
        if (projectId.size() > 0  && projectId.size() < 200){
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
        if (projectId.size() > 0 && projectId.size() < 200){
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
        if (projectId.size() > 0 && projectId.size() < 200) {
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

    @Log(title = "项目范围线", businessType = BusinessType.INSERT)
    @PostMapping("/rangeLine")
    public AjaxResult rangeLine(@RequestBody BcProject bcProject) throws ParseException {
        SysProject sysProject = sysProjectService.selectSysProjectByProjectNum(bcProject.getXmbh());
        if (sysProject == null){
            return error("项目不存在");
        }
        BcProject oldObj = bcProjectService.selectBcProjectByXmbh(bcProject.getXmbh());
        String wkt = bcProject.getShape();
        if (wkt.length() > 3000) {
            List<String> fragments = new ArrayList<>();
            int length = wkt.length();
            for (int i = 0; i < length; i += 3000) {
                fragments.add(wkt.substring(i, Math.min(length, i + 3000)));
            }
            bcProject.setClobFragments(fragments);
        }
        if (oldObj != null){
            bcProjectService.updateBcProject(bcProject);
        }else {
            bcProject.setXmmc(sysProject.getProjectNameAlias());
            bcProject.setXmbh(sysProject.getProjectNum());
            bcProject.setXmlx(sysProject.getProjectType());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (sysProject.getRegisterTime() != null && !Objects.equals(sysProject.getRegisterTime(), "")){
                Date djsj = sdf.parse(sysProject.getRegisterTime());
                bcProject.setDjsj(djsj);
            }
            bcProject.setFzr(sysProject.getUserNameAlias());
            bcProject.setWtdw(sysProject.getRequesterAlias());
            if (sysProject.getProjectStartAlias() != null && !Objects.equals(sysProject.getProjectStartAlias(), "")){
                Date kssj = sdf.parse(sysProject.getProjectStartAlias());
                bcProject.setKssj(kssj);
            }
            if (sysProject.getProjectEndAlias() != null && !Objects.equals(sysProject.getProjectEndAlias(), "")){
                Date jssj = sdf.parse(sysProject.getProjectEndAlias());
                bcProject.setJssj(jssj);
            }
            bcProject.setGznr(sysProject.getWorkcontentAlias());
            bcProject.setBm(sysProject.getDepartment());
            bcProjectService.insertBcProject(bcProject);
        }

        List<SysProjectGeoinfo> sysProjectGeoinfo =  sysProjectGeoinfoService.selectSysProjectGeoinfoByProjectId(sysProject.getProjectId());
        SysProjectGeoinfo newSysProjectGeoinfo = new SysProjectGeoinfo();
        newSysProjectGeoinfo.setGeometry(wkt);
        newSysProjectGeoinfo.setGeometry2000(wkt);
        newSysProjectGeoinfo.setBufferGeometry(wkt);
        newSysProjectGeoinfo.setBufferGeometry2000(wkt);
        newSysProjectGeoinfo.setGeometryGauss2000(wkt);
        newSysProjectGeoinfo.setBufferGeometryGauss2000(wkt);
        newSysProjectGeoinfo.setBufferDistance(BigDecimal.ZERO);
        newSysProjectGeoinfo.setProjectId(sysProject.getProjectId());
        if (sysProjectGeoinfo != null && sysProjectGeoinfo.size() > 0){
            sysProjectGeoinfoService.updateSysProjectGeoinfo(newSysProjectGeoinfo);
        }else {
            sysProjectGeoinfoService.insertSysProjectGeoinfo(newSysProjectGeoinfo);
        }
        return toAjax(1);
    }

    @Log(title = "预估产值金额", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/editYgmoney")
    public AjaxResult editYgmoney(@RequestBody SysProject sysProject) {
        SysProject obj = new SysProject();
        obj.setYgmoney(sysProject.getYgmoney());
        obj.setProjectId(sysProject.getProjectId());
        sysProjectService.updateSysProject(obj);
        return toAjax(1);
    }

    @Log(title = "成果送达", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/cgsd")
    public AjaxResult cgsd(@RequestBody SysProject sysProject) {
        SysProject obj = new SysProject();
        obj.setCgsdtime(sysProject.getCgsdtime());
        obj.setProjectId(sysProject.getProjectId());
        sysProjectService.updateSysProject(obj);
        return toAjax(1);
    }

    @GetMapping("/listProjectHj")
    public TableDataInfo listProjectHj(SysProject sysProject) {
        Long userId = getUserId();
        SysUser user = sysUserService.selectUserById(userId);
        sysProject.setGzStatus((long)-1);
        sysProject.setTjStatus(user.getTjStatus());
        sysProject.setUserId(userId);
        sysProject.setDeptId(user.getDept().getDeptId());
        sysProject.setUserNameAlias(getUsername());
        sysProject.setDepartment(user.getDept().getDeptName());
        List<SysProject> list = sysProjectService.selectZyz(sysProject);
        List<ProjectHJ> projectHJ = new ArrayList<>();
        // 作业中项目统计
        ProjectHJ projectHJ1 = new ProjectHJ();
        projectHJ1.setXmglwz("作业中项目");
        projectHJ1.setGcchb(0);
        projectHJ1.setGxgcb(0);
        projectHJ1.setBdcchb(0);
        projectHJ1.setDlxxb(0);
        projectHJ1.setHj(0);
        for (SysProject obj : list){
            switch (obj.getDepartment()){
                case "工程测绘部":
                    projectHJ1.setGcchb(projectHJ1.getGcchb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                case "管线工程部":
                    projectHJ1.setGxgcb(projectHJ1.getGxgcb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                case "不动产测绘部":
                    projectHJ1.setBdcchb(projectHJ1.getBdcchb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                case "地理信息部":
                    projectHJ1.setDlxxb(projectHJ1.getDlxxb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                default:
                    break;
            }
        }
        //任务安排统计
        List<SysProject> rojectTj1 = sysProjectService.selectRwapz(sysProject);
        ProjectHJ projectHJ2 = new ProjectHJ();
        projectHJ2.setXmglwz("任务安排");
        projectHJ2.setGcchb(0);
        projectHJ2.setGxgcb(0);
        projectHJ2.setBdcchb(0);
        projectHJ2.setDlxxb(0);
        projectHJ2.setHj(0);
        for (SysProject obj1 : rojectTj1){
            switch (obj1.getDepartment()){
                case "工程测绘部":
                    projectHJ2.setGcchb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                case "管线工程部":
                    projectHJ2.setGxgcb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ2.setBdcchb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                case "地理信息部":
                    projectHJ2.setDlxxb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                default:
                    break;
            }
        }
        //项目分包数
        List<SysProject> rojectTj2 = sysProjectService.selectXmfbs(sysProject);
        ProjectHJ projectHJ3 = new ProjectHJ();
        projectHJ3.setXmglwz("项目分包数");
        projectHJ3.setGcchb(0);
        projectHJ3.setGxgcb(0);
        projectHJ3.setBdcchb(0);
        projectHJ3.setDlxxb(0);
        projectHJ3.setHj(0);
        for (SysProject obj2 : rojectTj2){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ3.setGcchb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ3.setGxgcb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ3.setBdcchb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ3.setDlxxb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }
        //作业办结
        List<SysProject> rojectTj3 = sysProjectService.selectZybj(sysProject);
        ProjectHJ projectHJ4 = new ProjectHJ();
        projectHJ4.setXmglwz("作业办结");
        projectHJ4.setGcchb(0);
        projectHJ4.setGxgcb(0);
        projectHJ4.setBdcchb(0);
        projectHJ4.setDlxxb(0);
        projectHJ4.setHj(0);
        for (SysProject obj2 : rojectTj3){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ4.setGcchb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ4.setGxgcb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ4.setBdcchb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ4.setDlxxb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //作业办结经营产值(预估)
        List<SysProject> rojectTj4 = sysProjectService.selectYgmoney(sysProject);
        ProjectHJ projectHJ5 = new ProjectHJ();
        projectHJ5.setXmglwz("作业办结经营产值(预估)");
        projectHJ5.setGcchb(0);
        projectHJ5.setGxgcb(0);
        projectHJ5.setBdcchb(0);
        projectHJ5.setDlxxb(0);
        projectHJ5.setHj(0);
        for (SysProject obj2 : rojectTj4){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ5.setGcchb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "管线工程部":
                    projectHJ5.setGxgcb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "不动产测绘部":
                    projectHJ5.setBdcchb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "地理信息部":
                    projectHJ5.setDlxxb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                default:
                    break;
            }
        }

        //分院一检办结
        List<SysProject> rojectTj5 = sysProjectService.selectYj(sysProject);
        ProjectHJ projectHJ6 = new ProjectHJ();
        projectHJ6.setXmglwz("分院一检办结");
        projectHJ6.setGcchb(0);
        projectHJ6.setGxgcb(0);
        projectHJ6.setBdcchb(0);
        projectHJ6.setDlxxb(0);
        projectHJ6.setHj(0);
        for (SysProject obj2 : rojectTj5){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ6.setGcchb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ6.setGxgcb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ6.setBdcchb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ6.setDlxxb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //分院二检办结
        List<SysProject> rojectTj6 = sysProjectService.selectEj(sysProject);
        ProjectHJ projectHJ7 = new ProjectHJ();
        projectHJ7.setXmglwz("分院二检办结");
        projectHJ7.setGcchb(0);
        projectHJ7.setGxgcb(0);
        projectHJ7.setBdcchb(0);
        projectHJ7.setDlxxb(0);
        projectHJ7.setHj(0);
        for (SysProject obj2 : rojectTj6){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ7.setGcchb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ7.setGxgcb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ7.setBdcchb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ7.setDlxxb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //项目出件（市场经营）
        List<SysProject> rojectTj7 = sysProjectService.selectXmcjscjy(sysProject);
        ProjectHJ projectHJ8 = new ProjectHJ();
        projectHJ8.setXmglwz("项目出件（市场经营）");
        projectHJ8.setGcchb(0);
        projectHJ8.setGxgcb(0);
        projectHJ8.setBdcchb(0);
        projectHJ8.setDlxxb(0);
        projectHJ8.setHj(0);
        for (SysProject obj2 : rojectTj7){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ8.setGcchb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ8.setGxgcb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ8.setBdcchb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ8.setDlxxb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //成果送达（市场经营）
        List<SysProject> rojectTj8 = sysProjectService.selectCgsd(sysProject);
        ProjectHJ projectHJ9 = new ProjectHJ();
        projectHJ9.setXmglwz("成果送达（市场经营）");
        projectHJ9.setGcchb(0);
        projectHJ9.setGxgcb(0);
        projectHJ9.setBdcchb(0);
        projectHJ9.setDlxxb(0);
        projectHJ9.setHj(0);
        for (SysProject obj2 : rojectTj8){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ9.setGcchb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ9.setGxgcb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ9.setBdcchb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ9.setDlxxb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //项目经营产值未填报
        List<SysProject> rojectTj9 = sysProjectService.selectJyczwtb(sysProject);
        ProjectHJ projectHJ10 = new ProjectHJ();
        projectHJ10.setXmglwz("项目经营产值未填报");
        projectHJ10.setGcchb(0);
        projectHJ10.setGxgcb(0);
        projectHJ10.setBdcchb(0);
        projectHJ10.setDlxxb(0);
        projectHJ10.setHj(0);
        for (SysProject obj2 : rojectTj9){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ10.setGcchb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ10.setGxgcb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ10.setBdcchb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ10.setDlxxb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //年度完成经营产值
        List<SysProject> rojectTj10 = sysProjectService.selectNdjycz(sysProject);
        ProjectHJ projectHJ11 = new ProjectHJ();
        projectHJ11.setXmglwz("年度完成经营产值");
        projectHJ11.setGcchb(0);
        projectHJ11.setGxgcb(0);
        projectHJ11.setBdcchb(0);
        projectHJ11.setDlxxb(0);
        projectHJ11.setHj(0);
        for (SysProject obj2 : rojectTj10){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ11.setGcchb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "管线工程部":
                    projectHJ11.setGxgcb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "不动产测绘部":
                    projectHJ11.setBdcchb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "地理信息部":
                    projectHJ11.setDlxxb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                default:
                    break;
            }
        }

        //年度完成经营净产值（扣分包）
        ProjectHJ projectHJ12 = new ProjectHJ();
        projectHJ12.setXmglwz("年度完成经营净产值（扣分包）");
        projectHJ12.setGcchb(0);
        projectHJ12.setGxgcb(0);
        projectHJ12.setBdcchb(0);
        projectHJ12.setDlxxb(0);
        projectHJ12.setHj(0);
        for (SysProject obj2 : rojectTj10){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ12.setGcchb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "管线工程部":
                    projectHJ12.setGxgcb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "不动产测绘部":
                    projectHJ12.setBdcchb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "地理信息部":
                    projectHJ12.setDlxxb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                default:
                    break;
            }
        }

        //项目工期超期
        List<SysProject> rojectTj11 = sysProjectService.selectXMCQ(sysProject);
        ProjectHJ projectHJ13 = new ProjectHJ();
        projectHJ13.setXmglwz("项目工期超期");
        projectHJ13.setGcchb(0);
        projectHJ13.setGxgcb(0);
        projectHJ13.setBdcchb(0);
        projectHJ13.setDlxxb(0);
        projectHJ13.setHj(0);
        for (SysProject obj2 : rojectTj11){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ13.setGcchb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ13.setGxgcb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ13.setBdcchb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ13.setDlxxb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //项目工期超期超1一个月
        List<SysProject> rojectTj12 = sysProjectService.selectXMCQYGY(sysProject);
        ProjectHJ projectHJ14 = new ProjectHJ();
        projectHJ14.setXmglwz("项目工期超期超1一个月");
        projectHJ14.setGcchb(0);
        projectHJ14.setGxgcb(0);
        projectHJ14.setBdcchb(0);
        projectHJ14.setDlxxb(0);
        projectHJ14.setHj(0);
        for (SysProject obj2 : rojectTj12){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ14.setGcchb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ14.setGxgcb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ14.setBdcchb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ14.setDlxxb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //作业办结后未一检办结（超1周）
        List<SysProject> rojectTj13 = sysProjectService.selectWYIBJZ(sysProject);
        ProjectHJ projectHJ15 = new ProjectHJ();
        projectHJ15.setXmglwz("作业办结后未一检办结（超1周）");
        projectHJ15.setGcchb(0);
        projectHJ15.setGxgcb(0);
        projectHJ15.setBdcchb(0);
        projectHJ15.setDlxxb(0);
        projectHJ15.setHj(0);
        for (SysProject obj2 : rojectTj13){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ15.setGcchb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ15.setGxgcb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ15.setBdcchb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ15.setDlxxb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //作业办结后未一检办结（超1个月）
        List<SysProject> rojectTj14 = sysProjectService.selectWYIBJY(sysProject);
        ProjectHJ projectHJ16 = new ProjectHJ();
        projectHJ16.setXmglwz("作业办结后未一检办结（超1个月）");
        projectHJ16.setGcchb(0);
        projectHJ16.setGxgcb(0);
        projectHJ16.setBdcchb(0);
        projectHJ16.setDlxxb(0);
        projectHJ16.setHj(0);
        for (SysProject obj2 : rojectTj14){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ16.setGcchb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ16.setGxgcb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ16.setBdcchb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ16.setDlxxb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //一检后未二检办结（超1周）
        List<SysProject> rojectTj15 = sysProjectService.selectWEJZ(sysProject);
        ProjectHJ projectHJ17 = new ProjectHJ();
        projectHJ17.setXmglwz("一检后未二检办结（超1周）");
        projectHJ17.setGcchb(0);
        projectHJ17.setGxgcb(0);
        projectHJ17.setBdcchb(0);
        projectHJ17.setDlxxb(0);
        projectHJ17.setHj(0);
        for (SysProject obj2 : rojectTj15){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ17.setGcchb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ17.setGxgcb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ17.setBdcchb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ17.setDlxxb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //一检后未二检办结（超1个月）
        List<SysProject> rojectTj16 = sysProjectService.selectWEJY(sysProject);
        ProjectHJ projectHJ18 = new ProjectHJ();
        projectHJ18.setXmglwz("一检后未二检办结（超1个月）");
        projectHJ18.setGcchb(0);
        projectHJ18.setGxgcb(0);
        projectHJ18.setBdcchb(0);
        projectHJ18.setDlxxb(0);
        projectHJ18.setHj(0);
        for (SysProject obj2 : rojectTj16){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ18.setGcchb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ18.setGxgcb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ18.setBdcchb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ18.setDlxxb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        projectHJ.add(projectHJ1);
        projectHJ.add(projectHJ2);
        projectHJ.add(projectHJ3);
        projectHJ.add(projectHJ4);
        projectHJ.add(projectHJ5);
        projectHJ.add(projectHJ6);
        projectHJ.add(projectHJ7);
        projectHJ.add(projectHJ8);
        projectHJ.add(projectHJ9);
        projectHJ.add(projectHJ10);
        projectHJ.add(projectHJ11);
        projectHJ.add(projectHJ12);
        projectHJ.add(projectHJ13);
        projectHJ.add(projectHJ14);
        projectHJ.add(projectHJ15);
        projectHJ.add(projectHJ16);
        projectHJ.add(projectHJ17);
        projectHJ.add(projectHJ18);
        return getDataTable(projectHJ);
    }

    @Log(title = "周报导出", businessType = BusinessType.EXPORT)
    @PostMapping("/exporTj")
    public void exporTj(HttpServletResponse response, SysProject sysProject) {
        sysProject.setGzStatus((long)-1);
        List<SysProject> list = sysProjectService.selectZyz(sysProject);
        List<ProjectHJ> projectHJ = new ArrayList<>();
        // 作业中项目统计
        ProjectHJ projectHJ1 = new ProjectHJ();
        projectHJ1.setXmglwz("作业中项目");
        projectHJ1.setGcchb(0);
        projectHJ1.setGxgcb(0);
        projectHJ1.setBdcchb(0);
        projectHJ1.setDlxxb(0);
        projectHJ1.setHj(0);
        for (SysProject obj : list){
            switch (obj.getDepartment()){
                case "工程测绘部":
                    projectHJ1.setGcchb(projectHJ1.getGcchb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                case "管线工程部":
                    projectHJ1.setGxgcb(projectHJ1.getGxgcb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                case "不动产测绘部":
                    projectHJ1.setBdcchb(projectHJ1.getBdcchb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                case "地理信息部":
                    projectHJ1.setDlxxb(projectHJ1.getDlxxb() + 1);
                    projectHJ1.setHj(projectHJ1.getHj() + 1);
                    break;
                default:
                    break;
            }
        }
        //任务安排统计
        List<SysProject> rojectTj1 = sysProjectService.selectRwapz(sysProject);
        ProjectHJ projectHJ2 = new ProjectHJ();
        projectHJ2.setXmglwz("任务安排");
        projectHJ2.setGcchb(0);
        projectHJ2.setGxgcb(0);
        projectHJ2.setBdcchb(0);
        projectHJ2.setDlxxb(0);
        projectHJ2.setHj(0);
        for (SysProject obj1 : rojectTj1){
            switch (obj1.getDepartment()){
                case "工程测绘部":
                    projectHJ2.setGcchb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                case "管线工程部":
                    projectHJ2.setGxgcb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ2.setBdcchb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                case "地理信息部":
                    projectHJ2.setDlxxb(obj1.getNum());
                    projectHJ2.setHj(projectHJ2.getHj() + obj1.getNum());
                    break;
                default:
                    break;
            }
        }
        //项目分包数
        List<SysProject> rojectTj2 = sysProjectService.selectXmfbs(sysProject);
        ProjectHJ projectHJ3 = new ProjectHJ();
        projectHJ3.setXmglwz("项目分包数");
        projectHJ3.setGcchb(0);
        projectHJ3.setGxgcb(0);
        projectHJ3.setBdcchb(0);
        projectHJ3.setDlxxb(0);
        projectHJ3.setHj(0);
        for (SysProject obj2 : rojectTj2){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ3.setGcchb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ3.setGxgcb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ3.setBdcchb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ3.setDlxxb(obj2.getNum());
                    projectHJ3.setHj(projectHJ3.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }
        //作业办结
        List<SysProject> rojectTj3 = sysProjectService.selectZybj(sysProject);
        ProjectHJ projectHJ4 = new ProjectHJ();
        projectHJ4.setXmglwz("作业办结");
        projectHJ4.setGcchb(0);
        projectHJ4.setGxgcb(0);
        projectHJ4.setBdcchb(0);
        projectHJ4.setDlxxb(0);
        projectHJ4.setHj(0);
        for (SysProject obj2 : rojectTj3){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ4.setGcchb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ4.setGxgcb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ4.setBdcchb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ4.setDlxxb(obj2.getNum());
                    projectHJ4.setHj(projectHJ4.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //作业办结经营产值(预估)
        List<SysProject> rojectTj4 = sysProjectService.selectYgmoney(sysProject);
        ProjectHJ projectHJ5 = new ProjectHJ();
        projectHJ5.setXmglwz("作业办结经营产值(预估)");
        projectHJ5.setGcchb(0);
        projectHJ5.setGxgcb(0);
        projectHJ5.setBdcchb(0);
        projectHJ5.setDlxxb(0);
        projectHJ5.setHj(0);
        for (SysProject obj2 : rojectTj4){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ5.setGcchb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "管线工程部":
                    projectHJ5.setGxgcb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "不动产测绘部":
                    projectHJ5.setBdcchb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "地理信息部":
                    projectHJ5.setDlxxb(obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ5.setHj(projectHJ5.getHj() + obj2.getYgmoney().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                default:
                    break;
            }
        }

        //分院一检办结
        List<SysProject> rojectTj5 = sysProjectService.selectYj(sysProject);
        ProjectHJ projectHJ6 = new ProjectHJ();
        projectHJ6.setXmglwz("分院一检办结");
        projectHJ6.setGcchb(0);
        projectHJ6.setGxgcb(0);
        projectHJ6.setBdcchb(0);
        projectHJ6.setDlxxb(0);
        projectHJ6.setHj(0);
        for (SysProject obj2 : rojectTj5){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ6.setGcchb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ6.setGxgcb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ6.setBdcchb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ6.setDlxxb(obj2.getNum());
                    projectHJ6.setHj(projectHJ6.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //分院二检办结
        List<SysProject> rojectTj6 = sysProjectService.selectEj(sysProject);
        ProjectHJ projectHJ7 = new ProjectHJ();
        projectHJ7.setXmglwz("分院二检办结");
        projectHJ7.setGcchb(0);
        projectHJ7.setGxgcb(0);
        projectHJ7.setBdcchb(0);
        projectHJ7.setDlxxb(0);
        projectHJ7.setHj(0);
        for (SysProject obj2 : rojectTj6){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ7.setGcchb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ7.setGxgcb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ7.setBdcchb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ7.setDlxxb(obj2.getNum());
                    projectHJ7.setHj(projectHJ7.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //项目出件（市场经营）
        List<SysProject> rojectTj7 = sysProjectService.selectXmcjscjy(sysProject);
        ProjectHJ projectHJ8 = new ProjectHJ();
        projectHJ8.setXmglwz("项目出件（市场经营）");
        projectHJ8.setGcchb(0);
        projectHJ8.setGxgcb(0);
        projectHJ8.setBdcchb(0);
        projectHJ8.setDlxxb(0);
        projectHJ8.setHj(0);
        for (SysProject obj2 : rojectTj7){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ8.setGcchb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ8.setGxgcb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ8.setBdcchb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ8.setDlxxb(obj2.getNum());
                    projectHJ8.setHj(projectHJ8.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //成果送达（市场经营）
        List<SysProject> rojectTj8 = sysProjectService.selectCgsd(sysProject);
        ProjectHJ projectHJ9 = new ProjectHJ();
        projectHJ9.setXmglwz("成果送达（市场经营）");
        projectHJ9.setGcchb(0);
        projectHJ9.setGxgcb(0);
        projectHJ9.setBdcchb(0);
        projectHJ9.setDlxxb(0);
        projectHJ9.setHj(0);
        for (SysProject obj2 : rojectTj8){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ9.setGcchb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ9.setGxgcb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ9.setBdcchb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ9.setDlxxb(obj2.getNum());
                    projectHJ9.setHj(projectHJ9.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //项目经营产值未填报
        List<SysProject> rojectTj9 = sysProjectService.selectJyczwtb(sysProject);
        ProjectHJ projectHJ10 = new ProjectHJ();
        projectHJ10.setXmglwz("项目经营产值未填报");
        projectHJ10.setGcchb(0);
        projectHJ10.setGxgcb(0);
        projectHJ10.setBdcchb(0);
        projectHJ10.setDlxxb(0);
        projectHJ10.setHj(0);
        for (SysProject obj2 : rojectTj9){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ10.setGcchb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ10.setGxgcb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ10.setBdcchb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ10.setDlxxb(obj2.getNum());
                    projectHJ10.setHj(projectHJ10.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //年度完成经营产值
        List<SysProject> rojectTj10 = sysProjectService.selectNdjycz(sysProject);
        ProjectHJ projectHJ11 = new ProjectHJ();
        projectHJ11.setXmglwz("年度完成经营产值");
        projectHJ11.setGcchb(0);
        projectHJ11.setGxgcb(0);
        projectHJ11.setBdcchb(0);
        projectHJ11.setDlxxb(0);
        projectHJ11.setHj(0);
        for (SysProject obj2 : rojectTj10){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ11.setGcchb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "管线工程部":
                    projectHJ11.setGxgcb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "不动产测绘部":
                    projectHJ11.setBdcchb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "地理信息部":
                    projectHJ11.setDlxxb(obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ11.setHj(projectHJ11.getHj() + obj2.getMoneyhj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                default:
                    break;
            }
        }

        //年度完成经营净产值（扣分包）
        ProjectHJ projectHJ12 = new ProjectHJ();
        projectHJ12.setXmglwz("年度完成经营净产值（扣分包）");
        projectHJ12.setGcchb(0);
        projectHJ12.setGxgcb(0);
        projectHJ12.setBdcchb(0);
        projectHJ12.setDlxxb(0);
        projectHJ12.setHj(0);
        for (SysProject obj2 : rojectTj10){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ12.setGcchb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "管线工程部":
                    projectHJ12.setGxgcb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "不动产测绘部":
                    projectHJ12.setBdcchb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                case "地理信息部":
                    projectHJ12.setDlxxb(obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    projectHJ12.setHj(projectHJ12.getHj() + obj2.getProfitMoneyHj().setScale(0, RoundingMode.HALF_UP).intValue());
                    break;
                default:
                    break;
            }
        }

        //项目工期超期
        List<SysProject> rojectTj11 = sysProjectService.selectXMCQ(sysProject);
        ProjectHJ projectHJ13 = new ProjectHJ();
        projectHJ13.setXmglwz("项目工期超期");
        projectHJ13.setGcchb(0);
        projectHJ13.setGxgcb(0);
        projectHJ13.setBdcchb(0);
        projectHJ13.setDlxxb(0);
        projectHJ13.setHj(0);
        for (SysProject obj2 : rojectTj11){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ13.setGcchb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ13.setGxgcb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ13.setBdcchb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ13.setDlxxb(obj2.getNum());
                    projectHJ13.setHj(projectHJ13.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //项目工期超期超1一个月
        List<SysProject> rojectTj12 = sysProjectService.selectXMCQYGY(sysProject);
        ProjectHJ projectHJ14 = new ProjectHJ();
        projectHJ14.setXmglwz("项目工期超期超1一个月");
        projectHJ14.setGcchb(0);
        projectHJ14.setGxgcb(0);
        projectHJ14.setBdcchb(0);
        projectHJ14.setDlxxb(0);
        projectHJ14.setHj(0);
        for (SysProject obj2 : rojectTj12){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ14.setGcchb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ14.setGxgcb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ14.setBdcchb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ14.setDlxxb(obj2.getNum());
                    projectHJ14.setHj(projectHJ14.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //作业办结后未一检办结（超1周）
        List<SysProject> rojectTj13 = sysProjectService.selectWYIBJZ(sysProject);
        ProjectHJ projectHJ15 = new ProjectHJ();
        projectHJ15.setXmglwz("作业办结后未一检办结（超1周）");
        projectHJ15.setGcchb(0);
        projectHJ15.setGxgcb(0);
        projectHJ15.setBdcchb(0);
        projectHJ15.setDlxxb(0);
        projectHJ15.setHj(0);
        for (SysProject obj2 : rojectTj13){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ15.setGcchb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ15.setGxgcb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ15.setBdcchb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ15.setDlxxb(obj2.getNum());
                    projectHJ15.setHj(projectHJ15.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //作业办结后未一检办结（超1个月）
        List<SysProject> rojectTj14 = sysProjectService.selectWYIBJY(sysProject);
        ProjectHJ projectHJ16 = new ProjectHJ();
        projectHJ16.setXmglwz("作业办结后未一检办结（超1个月）");
        projectHJ16.setGcchb(0);
        projectHJ16.setGxgcb(0);
        projectHJ16.setBdcchb(0);
        projectHJ16.setDlxxb(0);
        projectHJ16.setHj(0);
        for (SysProject obj2 : rojectTj14){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ16.setGcchb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ16.setGxgcb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ16.setBdcchb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ16.setDlxxb(obj2.getNum());
                    projectHJ16.setHj(projectHJ16.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //一检后未二检办结（超1周）
        List<SysProject> rojectTj15 = sysProjectService.selectWEJZ(sysProject);
        ProjectHJ projectHJ17 = new ProjectHJ();
        projectHJ17.setXmglwz("一检后未二检办结（超1周）");
        projectHJ17.setGcchb(0);
        projectHJ17.setGxgcb(0);
        projectHJ17.setBdcchb(0);
        projectHJ17.setDlxxb(0);
        projectHJ17.setHj(0);
        for (SysProject obj2 : rojectTj15){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ17.setGcchb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ17.setGxgcb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ17.setBdcchb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ17.setDlxxb(obj2.getNum());
                    projectHJ17.setHj(projectHJ17.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        //一检后未二检办结（超1个月）
        List<SysProject> rojectTj16 = sysProjectService.selectWEJY(sysProject);
        ProjectHJ projectHJ18 = new ProjectHJ();
        projectHJ18.setXmglwz("一检后未二检办结（超1个月）");
        projectHJ18.setGcchb(0);
        projectHJ18.setGxgcb(0);
        projectHJ18.setBdcchb(0);
        projectHJ18.setDlxxb(0);
        projectHJ18.setHj(0);
        for (SysProject obj2 : rojectTj16){
            switch (obj2.getDepartment()){
                case "工程测绘部":
                    projectHJ18.setGcchb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                case "管线工程部":
                    projectHJ18.setGxgcb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                case "不动产测绘部":
                    projectHJ18.setBdcchb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                case "地理信息部":
                    projectHJ18.setDlxxb(obj2.getNum());
                    projectHJ18.setHj(projectHJ18.getHj() + obj2.getNum());
                    break;
                default:
                    break;
            }
        }

        projectHJ.add(projectHJ1);
        projectHJ.add(projectHJ2);
        projectHJ.add(projectHJ3);
        projectHJ.add(projectHJ4);
        projectHJ.add(projectHJ5);
        projectHJ.add(projectHJ6);
        projectHJ.add(projectHJ7);
        projectHJ.add(projectHJ8);
        projectHJ.add(projectHJ9);
        projectHJ.add(projectHJ10);
        projectHJ.add(projectHJ11);
        projectHJ.add(projectHJ12);
        projectHJ.add(projectHJ13);
        projectHJ.add(projectHJ14);
        projectHJ.add(projectHJ15);
        projectHJ.add(projectHJ16);
        projectHJ.add(projectHJ17);
        projectHJ.add(projectHJ18);
        ExcelMultUtil<ProjectHJ> util = new ExcelMultUtil<ProjectHJ>(ProjectHJ.class, 4);
        util.exportExcel(response, projectHJ, "福清分院项目管理网站月周报");
    }

    @GetMapping("/listProjectHjMonth")
    public Map<String, Map<String, Map<Integer, BigDecimal>>> listProjectHjMonth(SysProject sysProject) {
        Long userId = getUserId();
        SysUser userObj = sysUserService.selectUserById(userId);
        sysProject.setGzStatus((long)-1);
        sysProject.setTjStatus(userObj.getTjStatus());
        sysProject.setUserId(userId);
        sysProject.setDeptId(userObj.getDept().getDeptId());
        sysProject.setUserNameAlias(getUsername());
        sysProject.setDepartment(userObj.getDept().getDeptName());
        List<OperatingExport> operatingExportList = new ArrayList<>();
        List<SysProjectValue> list = sysProjectValueService.listProjectHjMonth(sysProject);
        List<SysProjectValue> listSpecial = sysProjectValueService.listProjectOperateTJForSpecialPersonnelMonth(sysProject);

        for (SysProjectValue value1 : listSpecial){
            OperatingExport operatingExport = new OperatingExport();
            operatingExport.setSettleTime(value1.getFqSysProject().getSettleTime());
            operatingExport.setDept(value1.getFqSysProject().getDepartment());
            operatingExport.setMoney(value1.getMoney());
            operatingExport.setProfitMoney(value1.getProfitMoney());
            operatingExportList.add(operatingExport);
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

        for (SysProjectValue objTemp : list){
            OperatingExport operatingExport = new OperatingExport();
            if (userMap.containsKey(objTemp.getUserName())){
                String dept = userMap.get(objTemp.getUserName());
                operatingExport.setDept(dept);
                operatingExport.setSettleTime(objTemp.getFqSysProject().getSettleTime());
                operatingExport.setMoney(objTemp.getMoney());
                operatingExport.setProfitMoney(objTemp.getProfitMoney());
                operatingExportList.add(operatingExport);
            }

        }

        Map<String, Map<Integer, BigDecimal>> result = operatingExportList.stream()
                .filter(o -> o.getSettleTime() != null)
                .collect(Collectors.groupingBy(
                        OperatingExport::getDept, // 一级分组：部门
                        Collectors.groupingBy(
                                o -> {
                                    // 从 Date 转换为月份（1~12）
                                    LocalDate date = o.getSettleTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return date.getMonthValue();
                                },
                                Collectors.mapping(
                                        o -> Optional.ofNullable(o.getMoney()).orElse(BigDecimal.ZERO),
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                )
                        )
                ));

        Map<String, Map<Integer, BigDecimal>> result1 = operatingExportList.stream()
                .filter(o -> o.getSettleTime() != null)
                .collect(Collectors.groupingBy(
                        OperatingExport::getDept, // 一级分组：部门
                        Collectors.groupingBy(
                                o -> {
                                    // 从 Date 转换为月份（1~12）
                                    LocalDate date = o.getSettleTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                                    return date.getMonthValue();
                                },
                                Collectors.mapping(
                                        o -> Optional.ofNullable(o.getProfitMoney()).orElse(BigDecimal.ZERO),
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                )
                        )
                ));

        Map<String, Map<String, Map<Integer, BigDecimal>>> resultObj = new HashMap<>();
        resultObj.put("经营产值", result);
        resultObj.put("利润", result1);
        return resultObj;
    }


}
