package com.kcylog.web.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.common.FileJson;
import com.kcylog.system.domain.SysDepartmentDaily;
import com.kcylog.system.service.ISysDepartmentDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门日常Controller
 * 
 * @author ruoyi
 * @date 2023-07-11
 */
@RestController
@RequestMapping("/system/departmentDaily")
public class SysDepartmentDailyController extends BaseController
{
    @Autowired
    private ISysDepartmentDailyService sysDepartmentDailyService;

    /**
     * 查询部门日常列表
     */
    @PreAuthorize("@ss.hasPermi('system:departmentDaily:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDepartmentDaily sysDepartmentDaily) throws JsonProcessingException {
        startPage();
        ObjectMapper objectMapper = new ObjectMapper();
        List<SysDepartmentDaily> list = sysDepartmentDailyService.selectSysDepartmentDailyList(sysDepartmentDaily);
        for (SysDepartmentDaily departmentDaily:list){
            List<FileJson> imgJson = objectMapper.readValue(departmentDaily.getImg(), objectMapper.getTypeFactory().constructCollectionType(List.class, FileJson.class));
            List<FileJson> checkInJson = objectMapper.readValue(departmentDaily.getCheckIn(), objectMapper.getTypeFactory().constructCollectionType(List.class, FileJson.class));
            departmentDaily.setImgJson(imgJson);
            departmentDaily.setCheckInJson(checkInJson);
        }
        return getDataTable(list);
    }

    /**
     * 导出部门日常列表
     */
    @PreAuthorize("@ss.hasPermi('system:departmentDaily:export')")
    @Log(title = "部门日常", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDepartmentDaily sysDepartmentDaily)
    {
        List<SysDepartmentDaily> list = sysDepartmentDailyService.selectSysDepartmentDailyList(sysDepartmentDaily);
        ExcelUtil<SysDepartmentDaily> util = new ExcelUtil<SysDepartmentDaily>(SysDepartmentDaily.class);
        util.exportExcel(response, list, "部门日常数据");
    }

    /**
     * 获取部门日常详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:departmentDaily:query')")
    @GetMapping(value = "/{dailyId}")
    public AjaxResult getInfo(@PathVariable("dailyId") Long dailyId) throws JsonProcessingException {
        SysDepartmentDaily departmentDaily = sysDepartmentDailyService.selectSysDepartmentDailyByDailyId(dailyId);
        ObjectMapper objectMapper = new ObjectMapper();
        List<FileJson> imgJson = objectMapper.readValue(departmentDaily.getImg(), objectMapper.getTypeFactory().constructCollectionType(List.class, FileJson.class));
        List<FileJson> checkInJson = objectMapper.readValue(departmentDaily.getCheckIn(), objectMapper.getTypeFactory().constructCollectionType(List.class, FileJson.class));
        departmentDaily.setImgJson(imgJson);
        departmentDaily.setCheckInJson(checkInJson);
        return success(departmentDaily);
    }

    /**
     * 新增部门日常
     */
    @PreAuthorize("@ss.hasPermi('system:departmentDaily:add')")
    @Log(title = "部门日常", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDepartmentDaily sysDepartmentDaily) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String imgJson = mapper.writeValueAsString(sysDepartmentDaily.getImgJson());
        String checkInJson = mapper.writeValueAsString(sysDepartmentDaily.getCheckInJson());
        sysDepartmentDaily.setImg(imgJson);
        sysDepartmentDaily.setCheckIn(checkInJson);
        Long userId = SecurityUtils.getUserId();
        sysDepartmentDaily.setUserId(userId);
        return toAjax(sysDepartmentDailyService.insertSysDepartmentDaily(sysDepartmentDaily));
    }

    /**
     * 修改部门日常
     */
    @PreAuthorize("@ss.hasPermi('system:departmentDaily:edit')")
    @Log(title = "部门日常", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDepartmentDaily sysDepartmentDaily) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String imgJson = mapper.writeValueAsString(sysDepartmentDaily.getImgJson());
        String checkInJson = mapper.writeValueAsString(sysDepartmentDaily.getCheckInJson());
        sysDepartmentDaily.setImg(imgJson);
        sysDepartmentDaily.setCheckIn(checkInJson);
        return toAjax(sysDepartmentDailyService.updateSysDepartmentDaily(sysDepartmentDaily));
    }

    /**
     * 删除部门日常
     */
    @PreAuthorize("@ss.hasPermi('system:departmentDaily:remove')")
    @Log(title = "部门日常", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dailyIds}")
    public AjaxResult remove(@PathVariable Long[] dailyIds)
    {
        return toAjax(sysDepartmentDailyService.deleteSysDepartmentDailyByDailyIds(dailyIds));
    }
}
