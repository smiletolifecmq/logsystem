package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.domain.entity.SysUser;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.StringUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysGeoUser;
import com.kcylog.system.service.ISysGeoUserService;
import com.kcylog.system.service.ISysUserService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地理用户Controller
 * 
 * @author ruoyi
 * @date 2023-12-07
 */
@RestController
@RequestMapping("/system/geoUser")
public class SysGeoUserController extends BaseController
{
    @Autowired
    private ISysGeoUserService sysGeoUserService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询地理用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysGeoUser sysGeoUser)
    {
        startPage();
        List<SysGeoUser> list = sysGeoUserService.selectSysGeoUserList(sysGeoUser);
        return getDataTable(list);
    }

    /**
     * 导出地理用户列表
     */
    @Log(title = "地理用户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysGeoUser sysGeoUser)
    {
        List<SysGeoUser> list = sysGeoUserService.selectSysGeoUserList(sysGeoUser);
        ExcelUtil<SysGeoUser> util = new ExcelUtil<SysGeoUser>(SysGeoUser.class);
        util.exportExcel(response, list, "地理用户数据");
    }

    /**
     * 获取地理用户详细信息
     */
    @GetMapping(value = "/{geoUserId}")
    public AjaxResult getInfo(@PathVariable("geoUserId") Long geoUserId)
    {
        return success(sysGeoUserService.selectSysGeoUserByGeoUserId(geoUserId));
    }

    /**
     * 新增地理用户
     */
    @Log(title = "地理用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysGeoUser sysGeoUser)
    {
        SysGeoUser geoUser = sysGeoUserService.selectSysGeoUserByUserId(sysGeoUser.getUserId());
        if (geoUser != null)
        {
            return error("新增用户'" + geoUser.getUserName() + "'失败，用户名称已存在");
        }

        if (sysGeoUser.getParentId() != null){
            SysGeoUser geoUserTemp = sysGeoUserService.selectSysGeoUserByUserId(sysGeoUser.getParentId());
            sysGeoUser.setAncestors(geoUserTemp.getAncestors() + "," +Long.toString(sysGeoUser.getUserId()));
        }else {
            sysGeoUser.setAncestors("0" + "," + Long.toString(sysGeoUser.getUserId()));
        }
        SysUser user = sysUserService.selectUserById(sysGeoUser.getUserId());
        sysGeoUser.setUserName(user.getUserName());

        return toAjax(sysGeoUserService.insertSysGeoUser(sysGeoUser));
    }

    /**
     * 修改地理用户
     */
    @Log(title = "地理用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysGeoUser sysGeoUser)
    {
        return toAjax(sysGeoUserService.updateSysGeoUser(sysGeoUser));
    }

    /**
     * 删除地理用户
     */
    @Log(title = "地理用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{geoUserIds}")
    public AjaxResult remove(@PathVariable Long[] geoUserIds)
    {
        return toAjax(sysGeoUserService.deleteSysGeoUserByGeoUserIds(geoUserIds));
    }

    @GetMapping("/list/exclude/{userId}")
    public AjaxResult excludeChild(@PathVariable(value = "userId", required = false) Long userId)
    {
        List<SysGeoUser> geoUser = sysGeoUserService.selectSysGeoUserList(new SysGeoUser());
        geoUser.removeIf(d -> d.getUserId().intValue() == userId || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), userId + ""));
        return success(geoUser);
    }
}
