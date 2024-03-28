package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Anonymous;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelMultUtil;
import com.kcylog.system.common.ProjectEmployee;
import com.kcylog.system.common.ProjectSubcontract;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProject sysProject) {
        startPage();
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        return getDataTable(list);
    }

    /**
     * 导出项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:export')")
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
    @PreAuthorize("@ss.hasPermi('system:project:query')")
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
    @PreAuthorize("@ss.hasPermi('system:project:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProject sysProject) {
        return toAjax(sysProjectService.insertSysProject(sysProject));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:edit')")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProject sysProject) {
        return toAjax(sysProjectService.updateSysProject(sysProject));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:remove')")
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


    @PreAuthorize("@ss.hasPermi('system:project:detail')")
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
}
