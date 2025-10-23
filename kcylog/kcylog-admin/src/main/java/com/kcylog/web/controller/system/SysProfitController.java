package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysProfit;
import com.kcylog.system.service.ISysProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 福清分公司净利润申报表Controller
 * 
 * @author ruoyi
 * @date 2025-09-18
 */
@RestController
@RequestMapping("/system/profit")
public class SysProfitController extends BaseController
{
    @Autowired
    private ISysProfitService sysProfitService;

    /**
     * 查询福清分公司净利润申报表列表
     */
    @PreAuthorize("@ss.hasPermi('system:profit:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysProfit sysProfit)
    {
        startPage();
        List<SysProfit> list = sysProfitService.selectSysProfitList(sysProfit);
        return getDataTable(list);
    }

    /**
     * 导出福清分公司净利润申报表列表
     */
    @PreAuthorize("@ss.hasPermi('system:profit:export')")
    @Log(title = "福清分公司净利润申报表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysProfit sysProfit)
    {
        List<SysProfit> list = sysProfitService.selectSysProfitList(sysProfit);
        ExcelUtil<SysProfit> util = new ExcelUtil<SysProfit>(SysProfit.class);
        util.exportExcel(response, list, "福清分公司净利润申报表数据");
    }

    /**
     * 获取福清分公司净利润申报表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:profit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysProfitService.selectSysProfitById(id));
    }

    /**
     * 新增福清分公司净利润申报表
     */
    @PreAuthorize("@ss.hasPermi('system:profit:add')")
    @Log(title = "福清分公司净利润申报表", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody SysProfit sysProfit)
    {
        SysProfit checkForm = new SysProfit();
        checkForm.setNf(sysProfit.getNf());
        checkForm.setYf(sysProfit.getYf());
        List<SysProfit> objList = sysProfitService.selectSysProfitList(checkForm);
        if (objList != null && objList.size() > 0){
            return error("当月申报表已生成，不能重复生成");
        }
        // 获取上月预留值
        List<SysProfit> objListLast = sysProfitService.selectSysProfitListLastMonth();
        Map<String, SysProfit> map = new HashMap<>();
        for (SysProfit temp : objListLast){
            map.put(temp.getBm(), temp);
        }


        List<SysProfit> sysProfitList = new ArrayList<>();
        SysProfit obj1 = new SysProfit();
        obj1.setBm("工程测绘部");
        obj1.setHdnbcz(sysProfit.getGcchnb());
        obj1.setJysr(sysProfit.getGcchbNumWork());
        obj1.setSbjysr(sysProfit.getGcchbNumWork().add(map.get("工程测绘部").getYl()));
        obj1.setJssyyl(map.get("工程测绘部").getYl());
        obj1.setNf(sysProfit.getNf());
        obj1.setYf(sysProfit.getYf());
        obj1.setBj((long)0);
        sysProfitList.add(obj1);

        SysProfit obj2 = new SysProfit();
        obj2.setBm("不动产测绘部");
        obj2.setJysr(sysProfit.getBdcchbWork());
        obj2.setHdnbcz(sysProfit.getBdcnb());
        obj2.setSbjysr(sysProfit.getBdcchbWork().add(map.get("不动产测绘部").getYl()));
        obj2.setJssyyl(map.get("不动产测绘部").getYl());
        obj2.setNf(sysProfit.getNf());
        obj2.setYf(sysProfit.getYf());
        obj2.setBj((long)0);
        sysProfitList.add(obj2);

        SysProfit obj3 = new SysProfit();
        obj3.setBm("管线工程部");
        obj3.setJysr(sysProfit.getGxgcbWork());
        obj3.setHdnbcz(sysProfit.getGxgcnb());
        obj3.setSbjysr(sysProfit.getGxgcbWork().add(map.get("管线工程部").getYl()));
        obj3.setJssyyl(map.get("管线工程部").getYl());
        obj3.setNf(sysProfit.getNf());
        obj3.setYf(sysProfit.getYf());
        obj3.setBj((long)0);
        sysProfitList.add(obj3);

        SysProfit obj4 = new SysProfit();
        obj4.setBm("地理信息部");
        obj4.setJysr(sysProfit.getDlxxbWork());
        obj4.setHdnbcz(sysProfit.getDlnb());
        obj4.setSbjysr(sysProfit.getDlxxbWork().add(map.get("地理信息部").getYl()));
        obj4.setJssyyl(map.get("地理信息部").getYl());
        obj4.setNf(sysProfit.getNf());
        obj4.setYf(sysProfit.getYf());
        obj4.setBj((long)0);
        sysProfitList.add(obj4);
        for (SysProfit obj : sysProfitList){
            sysProfitService.insertSysProfit(obj);
        }
        return toAjax(1);
    }

    /**
     * 修改福清分公司净利润申报表
     */
    @PreAuthorize("@ss.hasPermi('system:profit:edit')")
    @Log(title = "福清分公司净利润申报表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysProfit sysProfit)
    {
        if (sysProfit.getJysr() != null){
            sysProfit.setSbjysr(sysProfit.getJysr().subtract(sysProfit.getHjsyyj()).add(sysProfit.getJssyyl()).subtract(sysProfit.getYl()).add(sysProfit.getYj()));
        }
        return toAjax(sysProfitService.updateSysProfit(sysProfit));
    }

    /**
     * 删除福清分公司净利润申报表
     */
    @PreAuthorize("@ss.hasPermi('system:profit:remove')")
    @Log(title = "福清分公司净利润申报表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysProfitService.deleteSysProfitByIds(ids));
    }

    @GetMapping("/getlistProfitOne")
    public TableDataInfo getlistProfitOne()
    {
        List<SysProfit> list = sysProfitService.getlistProfitOne();
        return getDataTable(list);
    }
}
