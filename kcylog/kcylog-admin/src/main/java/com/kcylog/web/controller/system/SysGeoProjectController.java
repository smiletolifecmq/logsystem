package com.kcylog.web.controller.system;

import com.google.gson.Gson;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.domain.entity.SysUser;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.core.redis.RedisCache;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoLogInfo;
import com.kcylog.system.domain.SysGeoProject;
import com.kcylog.system.service.ISysGeoLogInfoService;
import com.kcylog.system.service.ISysGeoProjectService;
import com.kcylog.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 地理项目Controller
 * 
 * @author ruoyi
 * @date 2023-12-06
 */
@RestController
@RequestMapping("/system/geoProject")
public class SysGeoProjectController extends BaseController
{
    @Autowired
    private ISysGeoProjectService sysGeoProjectService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysGeoLogInfoService sysGeoLogInfoService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询地理项目列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoProject sysGeoProject)
    {
        startPage();
        Map<String, Integer> sortProject =  redisCache.getCacheMap(SecurityUtils.getUsername());
        List<SysGeoProject> list = sysGeoProjectService.selectSysGeoProjectList(sysGeoProject);
        for (SysGeoProject project : list){
            if (sortProject.containsKey(project.getProjectId().toString())) {
                project.setSort(sortProject.get(project.getProjectId().toString()));
            }else {
                project.setSort(0);
            }
        }
        Collections.sort(list, Comparator.comparingInt(SysGeoProject::getSort).reversed());
        return getDataTable(list);
    }

    /**
     * 导出地理项目列表
     */
    @Log(title = "地理项目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoProject sysGeoProject)
    {
        List<SysGeoProject> list = sysGeoProjectService.selectSysGeoProjectList(sysGeoProject);
        ExcelUtil<SysGeoProject> util = new ExcelUtil<SysGeoProject>(SysGeoProject.class);
        util.exportExcel(response, list, "地理项目数据");
    }

    /**
     * 获取地理项目详细信息
     */
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        SysGeoProject geoProject = sysGeoProjectService.selectSysGeoProjectByProjectId(projectId);
        Gson gson = new Gson();
        Long[] oneArray = gson.fromJson(geoProject.getOneCheck(), Long[].class);
        geoProject.setOneCheckJson(oneArray);
        Long[] twoArray = gson.fromJson(geoProject.getTwoCheck(), Long[].class);
        geoProject.setTwoCheckJson(twoArray);
        return success(geoProject);
    }

    /**
     * 新增地理项目
     */
    @Log(title = "地理项目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoProject sysGeoProject)
    {
        SysGeoProject geoProject = sysGeoProjectService.selectSysGeoProjectByProjectNum(sysGeoProject);
        if (geoProject != null) {
            return error("该项目编号已存在～");
        }
        Gson gson = new Gson();
        String oneJson = gson.toJson(sysGeoProject.getOneCheckJson());
        sysGeoProject.setOneCheck(oneJson);
        String twoJson = gson.toJson(sysGeoProject.getTwoCheckJson());
        sysGeoProject.setTwoCheck(twoJson);
        SysUser sysUser = userService.selectUserById(sysGeoProject.getUserId());
        sysGeoProject.setUserName(sysUser.getUserName());
        return toAjax(sysGeoProjectService.insertSysGeoProject(sysGeoProject));
    }

    /**
     * 修改地理项目
     */
    @Log(title = "地理项目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoProject sysGeoProject)
    {
        SysGeoProject geoProject = sysGeoProjectService.selectSysGeoProjectByProjectNumNotSelf(sysGeoProject);
        if (geoProject != null) {
            return error("修改后的项目编号已存在～");
        }
        Gson gson = new Gson();
        String oneJson = gson.toJson(sysGeoProject.getOneCheckJson());
        sysGeoProject.setOneCheck(oneJson);
        String twoJson = gson.toJson(sysGeoProject.getTwoCheckJson());
        sysGeoProject.setTwoCheck(twoJson);
        SysUser sysUser = userService.selectUserById(sysGeoProject.getUserId());
        sysGeoProject.setUserName(sysUser.getUserName());
        return toAjax(sysGeoProjectService.updateSysGeoProject(sysGeoProject));
    }

    /**
     * 删除地理项目
     */
    @Log(title = "地理项目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        List<SysGeoLogInfo> geoLogInfo = sysGeoLogInfoService.selectSysGeoLogInfoByProjectId(projectIds);
        if (geoLogInfo.size() != 0) {
            return error("项目已关联日志，不可删除～");
        }
        return toAjax(sysGeoProjectService.deleteSysGeoProjectByProjectIds(projectIds));
    }
}
