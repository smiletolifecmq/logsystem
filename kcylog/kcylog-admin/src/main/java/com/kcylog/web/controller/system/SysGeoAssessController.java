package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoAssess;
import com.kcylog.system.domain.SysGeoAssessInfo;
import com.kcylog.system.domain.SysGeoUser;
import com.kcylog.system.service.ISysGeoAssessInfoService;
import com.kcylog.system.service.ISysGeoAssessService;
import com.kcylog.system.service.ISysGeoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 评定表Controller
 *
 * @author ruoyi
 * @date 2023-12-08
 */
@RestController
@RequestMapping("/system/geoAssess")
public class SysGeoAssessController extends BaseController {
    @Autowired
    private ISysGeoAssessService sysGeoAssessService;

    @Autowired
    private ISysGeoUserService sysGeoUserService;

    @Autowired
    private ISysGeoAssessInfoService sysGeoAssessInfoService;

    /**
     * 查询评定表列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoAssess sysGeoAssess) {
        startPage();
        sysGeoAssess.setAssessUserId(SecurityUtils.getUserId());
        List<SysGeoAssess> list = sysGeoAssessService.selectSysGeoAssessList(sysGeoAssess);
        return getDataTable(list);
    }

    /**
     * 导出评定表列表
     */
    @Log(title = "评定表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoAssess sysGeoAssess) {
        List<SysGeoAssess> list = sysGeoAssessService.selectSysGeoAssessList(sysGeoAssess);
        ExcelUtil<SysGeoAssess> util = new ExcelUtil<SysGeoAssess>(SysGeoAssess.class);
        util.exportExcel(response, list, "评定表数据");
    }

    /**
     * 获取评定表详细信息
     */
    @GetMapping(value = "/{assessId}")
    public AjaxResult getInfo(@PathVariable("assessId") Long assessId) {
        return success(sysGeoAssessService.selectSysGeoAssessByAssessId(assessId));
    }

    /**
     * 新增评定表
     */
    @Log(title = "评定表", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoAssess sysGeoAssess) {
        sysGeoAssess.setAssessUserId(SecurityUtils.getUserId());
        sysGeoAssess.setAssessUserName(SecurityUtils.getUsername());
        SysGeoAssess geoAssess = sysGeoAssessService.selectSysGeoAssessByUserIdAndDate(sysGeoAssess);
        if (geoAssess != null) {
            return error("该日期已经完成评定了～");
        }
        sysGeoAssessService.insertSysGeoAssess(sysGeoAssess);
        for (SysGeoAssessInfo geoAssessInfo : sysGeoAssess.getGeoAssessInfo()) {
            geoAssessInfo.setAssessId(sysGeoAssess.getAssessId());
            geoAssessInfo.setAssessDate(sysGeoAssess.getAssessDate());
            sysGeoAssessInfoService.insertSysGeoAssessInfo(geoAssessInfo);
        }
        return toAjax(1);
    }

    /**
     * 修改评定表
     */
    @Log(title = "评定表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoAssess sysGeoAssess) {
        sysGeoAssess.setAssessUserId(SecurityUtils.getUserId());
        sysGeoAssess.setAssessUserName(SecurityUtils.getUsername());
        SysGeoAssess geoAssess = sysGeoAssessService.selectSysGeoAssessByUserIdAndDateNotSelf(sysGeoAssess);
        if (geoAssess != null) {
            return error("该日期已经完成评定了～");
        }
        Long[] assessIds = new Long[]{sysGeoAssess.getAssessId()};
        sysGeoAssessInfoService.deleteSysGeoAssessInfoByAssessIds(assessIds);
        for (SysGeoAssessInfo geoAssessInfo : sysGeoAssess.getGeoAssessInfo()) {
            geoAssessInfo.setAssessId(sysGeoAssess.getAssessId());
            geoAssessInfo.setAssessDate(sysGeoAssess.getAssessDate());
            sysGeoAssessInfoService.insertSysGeoAssessInfo(geoAssessInfo);
        }
        return toAjax(sysGeoAssessService.updateSysGeoAssess(sysGeoAssess));
    }

    /**
     * 删除评定表
     */
    @Log(title = "评定表", businessType = BusinessType.DELETE)
    @Transactional
    @DeleteMapping("/{assessIds}")
    public AjaxResult remove(@PathVariable Long[] assessIds) {
        sysGeoAssessInfoService.deleteSysGeoAssessInfoByAssessIds(assessIds);
        return toAjax(sysGeoAssessService.deleteSysGeoAssessByAssessIds(assessIds));
    }

    @GetMapping("/listAssessUser")
    public TableDataInfo listAssessUser(SysGeoAssess sysGeoAssess) {
        Long userId = SecurityUtils.getUserId();
        List<SysGeoUser> geoUser = sysGeoUserService.selectSysAssessUserByGeoUser(userId);
        List<SysGeoAssessInfo> geoAssessInfo = new ArrayList<SysGeoAssessInfo>();
        for (SysGeoUser user : geoUser) {
            SysGeoAssessInfo newGeoAssessInfo = new SysGeoAssessInfo();
            newGeoAssessInfo.setUserId(user.getUserId());
            newGeoAssessInfo.setUserName(user.getUserName());
            newGeoAssessInfo.setFitCoefficient((double) 1);
            newGeoAssessInfo.setWorkCoefficient((double) 1);
            newGeoAssessInfo.setRemark("");
            geoAssessInfo.add(newGeoAssessInfo);
        }
        return getDataTable(geoAssessInfo);
    }
}
