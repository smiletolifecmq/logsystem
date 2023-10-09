package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Anonymous;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysProject;
import com.kcylog.system.domain.SysProjectRelation;
import com.kcylog.system.domain.SysProjectValue;
import com.kcylog.system.service.ISysProjectRelationService;
import com.kcylog.system.service.ISysProjectService;
import com.kcylog.system.service.ISysProjectValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 项目Controller
 * 
 * @author ruoyi
 * @date 2023-09-07
 */
@RestController
@RequestMapping("/system/project")
public class SysProjectController extends BaseController
{
    @Autowired
    private ISysProjectService sysProjectService;

    @Autowired
    private ISysProjectRelationService sysProjectRelationService;

    @Autowired
    private ISysProjectValueService sysProjectValueService;

    /**
     * 查询项目列表
     */
    @PreAuthorize("@ss.hasPermi('system:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProject sysProject)
    {
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
    public void export(HttpServletResponse response, SysProject sysProject)
    {
        List<SysProject> list = sysProjectService.selectSysProjectList(sysProject);
        ExcelUtil<SysProject> util = new ExcelUtil<SysProject>(SysProject.class);
        util.exportExcel(response, list, "项目数据");
    }

    /**
     * 获取项目详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:project:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") String projectId)
    {
        return success(sysProjectService.selectSysProjectByProjectId(projectId));
    }

    /**
     * 新增项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:add')")
    @Log(title = "项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysProject sysProject)
    {
        return toAjax(sysProjectService.insertSysProject(sysProject));
    }

    /**
     * 修改项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:edit')")
    @Log(title = "项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProject sysProject)
    {
        return toAjax(sysProjectService.updateSysProject(sysProject));
    }

    /**
     * 删除项目
     */
    @PreAuthorize("@ss.hasPermi('system:project:remove')")
    @Log(title = "项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable String[] projectIds)
    {
        return toAjax(sysProjectService.deleteSysProjectByProjectIds(projectIds));
    }

    @GetMapping("/getProjectRelation/{reviewType}")
    public TableDataInfo getProjectRelation (@PathVariable("reviewType") Integer reviewType)
    {
        List<SysProjectRelation> list = sysProjectRelationService.selectProjectRelationByReviewType(reviewType);
        return getDataTable(list);
    }


    @PreAuthorize("@ss.hasPermi('system:project:detail')")
    @GetMapping(value = "/detail/{projectId}")
    public AjaxResult getProjectDetail(@PathVariable("projectId") String projectId)
    {
        return success(sysProjectService.selectSysProjectByProjectId(projectId));
    }

//    @Log(title = "项目管理安排数据", businessType = BusinessType.INSERT)
    @Anonymous
    @CrossOrigin
    @PostMapping(value = "/addProject")
    public AjaxResult addProject(@RequestBody SysProject sysProject)
    {
        if (sysProjectService.checkProjectKeyUnique(sysProject.getProjectNum()) != null )
        {
            return error("项目'" + sysProject.getProjectNum() + "'安排失败，该项目在综合管理系统中已存在，请删除之后再安排～");
        }
        return toAjax(sysProjectService.insertSysProject(sysProject));
    }

//    @Log(title = "项目管理安排数据", businessType = BusinessType.UPDATE)
    @Anonymous
    @CrossOrigin
    @PutMapping(value = "/updateProject")
    public AjaxResult editProject(@RequestBody SysProject sysProject) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date twoCheckTime = dateFormat.parse(sysProject.getTwoCheck());
        sysProject.setTwoCheckTime(twoCheckTime);
        return toAjax(sysProjectService.updateSysProjectByProjectNum(sysProject));
    }

    @GetMapping("/listProjectOperate")
    public TableDataInfo listProjectOperate(SysProject sysProject)
    {
        startPage();
        List<SysProject> list = sysProjectService.listProjectOperate(sysProject);
        return getDataTable(list);
    }

    @Log(title = "产值结算", businessType = BusinessType.UPDATE)
    @PutMapping("/projectValue")
    @Transactional
    public AjaxResult editProjectValue(@RequestBody SysProject sysProject)
    {
        if (sysProject.getOutputStatus() == 1){
            sysProjectService.updateOutputStatusByProjectId(sysProject);
        }
        sysProjectValueService.deleteSysProjectValueByProjectId(sysProject.getProjectId());
        if (sysProject.getProjectValue().size() != 0){
            for (int i = 0; i < sysProject.getProjectValue().size(); i++) {
                SysProjectValue projectValue = sysProject.getProjectValue().get(i);
                sysProjectValueService.insertSysProjectValue(projectValue);
            }
        }
        return toAjax(1);
    }
}
