package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.*;
import com.kcylog.system.service.ISysCarReviewConfigInfoService;
import com.kcylog.system.service.ISysCarReviewProcessService;
import com.kcylog.system.service.ISysCarReviewService;
import com.kcylog.system.service.ISysProjectCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 车辆使用审核Controller
 * 
 * @author ruoyi
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/system/carReview")
public class SysCarReviewController extends BaseController
{
    final int PassStatus = 2;
    final int NoPassStatus = 3;

    @Autowired
    private ISysCarReviewService sysCarReviewService;

    @Autowired
    private ISysCarReviewConfigInfoService sysCarReviewConfigInfoService;

    @Autowired
    private ISysCarReviewProcessService sysCarReviewProcessService;

    @Autowired
    private ISysProjectCarService sysProjectCarService;


    /**
     * 查询车辆使用审核列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysCarReview sysCarReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysCarReview.setUserId(userId);
        List<SysCarReview> list = sysCarReviewService.selectSysCarReviewList(sysCarReview);
        return getDataTable(list);
    }

    /**
     * 导出车辆使用审核列表
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCarReview sysCarReview)
    {
        List<SysCarReview> list = sysCarReviewService.selectSysCarReviewList(sysCarReview);
        ExcelUtil<SysCarReview> util = new ExcelUtil<SysCarReview>(SysCarReview.class);
        util.exportExcel(response, list, "车辆使用审核数据");
    }

    /**
     * 获取车辆使用审核详细信息
     */
    @GetMapping(value = "/{carReviewId}")
    public AjaxResult getInfo(@PathVariable("carReviewId") Long carReviewId)
    {
        return success(sysCarReviewService.selectSysCarReviewByCarReviewId(carReviewId));
    }

    /**
     * 新增车辆使用审核
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysCarReview sysCarReview)
    {
        if (sysCarReviewService.checkProjectKeyUnique(sysCarReview) != null )
        {
            return error("登记失败，该部门该日期项目使用车辆情况已登记过了～");
        }
        Long userId = SecurityUtils.getUserId();
        sysCarReview.setUserId(userId);
        sysCarReview.setReviewStatus((long)1);
        sysCarReviewService.insertSysCarReview(sysCarReview);
        List<SysCarReviewConfigInfo> carReviewConfigInfoList = sysCarReviewConfigInfoService.selectCarReviewConfigInfoListByDeptId(sysCarReview.getDeptId());

        List<SysCarReviewProcess> carReviewProcess = new ArrayList<>();
        for (SysCarReviewConfigInfo configInfo : carReviewConfigInfoList) {
            SysCarReviewProcess  carReview = new SysCarReviewProcess();
            carReview.setCarReviewId((sysCarReview.getCarReviewId()));
            carReview.setUserId(configInfo.getUserId());
            if (Objects.equals(configInfo.getNode(), "1")){
                carReview.setStatus((long)1);
            }else {
                carReview.setStatus((long)0);
            }
            carReview.setNode(Long.parseLong(configInfo.getNode()));
            carReviewProcess.add(carReview);
        }
        sysCarReviewProcessService.insertSysCarReviewBatch(carReviewProcess);


        List<SysProjectCar> projectCars = new ArrayList<>();
        for (SysProjectCar projectCar : sysCarReview.getProjectCar()) {
            SysProjectCar  sysProjectCar = new SysProjectCar();
            sysProjectCar.setProjectId(projectCar.getProjectId());
            sysProjectCar.setCarReviewId(sysCarReview.getCarReviewId());
            sysProjectCar.setProjectName(projectCar.getProjectName());
            sysProjectCar.setCarNum(projectCar.getCarNum());
            sysProjectCar.setCarType(projectCar.getCarType());
            sysProjectCar.setNumber(projectCar.getNumber());
            sysProjectCar.setRecordTime(sysCarReview.getRecordTime());
            sysProjectCar.setCarExpenses(projectCar.getCarExpenses());
            projectCars.add(sysProjectCar);
        }
        int result = sysProjectCarService.insertProjectCarBatch(projectCars);

        return toAjax(result);
    }

    /**
     * 修改车辆使用审核
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping
    public AjaxResult edit(@RequestBody SysCarReview sysCarReview)
    {
        sysProjectCarService.deleteProjectCarByCarReviewId(sysCarReview.getCarReviewId());
        List<SysProjectCar> projectCars = new ArrayList<>();
        for (SysProjectCar projectCar : sysCarReview.getProjectCar()) {
            SysProjectCar  sysProjectCar = new SysProjectCar();
            sysProjectCar.setProjectId(projectCar.getProjectId());
            sysProjectCar.setCarReviewId(sysCarReview.getCarReviewId());
            sysProjectCar.setProjectName(projectCar.getProjectName());
            sysProjectCar.setCarNum(projectCar.getCarNum());
            sysProjectCar.setCarType(projectCar.getCarType());
            sysProjectCar.setNumber(projectCar.getNumber());
            sysProjectCar.setCarExpenses(projectCar.getCarExpenses());
            sysProjectCar.setRecordTime(sysCarReview.getRecordTime());
            projectCars.add(sysProjectCar);
        }
        sysProjectCarService.insertProjectCarBatch(projectCars);
        return toAjax(sysCarReviewService.updateSysCarReview(sysCarReview));
    }

    /**
     * 删除车辆使用审核
     */
    @Log(title = "车辆使用审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{carReviewIds}")
    public AjaxResult remove(@PathVariable Long[] carReviewIds)
    {
        sysCarReviewProcessService.deleteSysCarReviewProcessByCarReviewIds(carReviewIds);
        sysProjectCarService.deleteProjectCarByCarReviewIds(carReviewIds);
        return toAjax(sysCarReviewService.deleteSysCarReviewByCarReviewIds(carReviewIds));
    }


    /**
     * 发起审核
     */
    @PutMapping(value = "/setReviewStatus")
    public AjaxResult setReviewStatus(@RequestBody SysCarReview sysCarReview)
    {
        sysCarReviewService.setSysReviewStatusByCarReviewId(sysCarReview);
        sysCarReviewProcessService.setStatusNotStartByCarReviewId(sysCarReview.getCarReviewId());
        int result = sysCarReviewProcessService.setStatusByReviewIdFirst(sysCarReview.getCarReviewId());
        return success(result);
    }

    /**
     * 待审核列表
     */
    @GetMapping("/upcomingCarReview")
    public TableDataInfo upcomingCarReview(SysCarReview sysCarReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysCarReview.setUserId(userId);
        List<SysCarReview> list = sysCarReviewService.selectSysUpcomingReviewList(sysCarReview);
        return getDataTable(list);
    }

    /**
     * 修改审核是否通过
     */
    @Log(title = "车辆登记审核", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/setCarProcessStatus")
    public AjaxResult setReviewProcessStatus(@RequestBody SysCarReviewProcess sysCarReviewProcess)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        sysCarReviewProcess.setUserId(userId);
        sysCarReviewProcess.setReviewTime(DateUtils.getNowDate());
        sysCarReviewProcessService.setStatusByUserIdAndCarReviewId(sysCarReviewProcess);

        SysCarReview carReview = new SysCarReview();
        carReview.setCarReviewId(sysCarReviewProcess.getCarReviewId());
        if (sysCarReviewProcess.getStatus() == (long)this.PassStatus){
            //审核通过
            //获取审核单审核流程
            List<SysCarReviewProcess> list = sysCarReviewProcessService.selectSysCarReviewProcessList(sysCarReviewProcess);
            int passNum = 0;
            for (SysCarReviewProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                carReview.setReviewStatus((long)this.PassStatus);
                sysCarReviewService.setSysReviewStatusByCarReviewId(carReview);
            }  else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysCarReviewProcessService.setNextStatusByReviewId(sysCarReviewProcess.getCarReviewId());
            }

        }else {
            //审核不通过
            carReview.setReviewStatus((long)this.NoPassStatus);
            sysCarReviewService.setSysReviewStatusByCarReviewId(carReview);
        }
        return toAjax(1);
    }

    /**
     * 已审核列表
     */
    @GetMapping("/doneCarReview")
    public TableDataInfo doneCarReview(SysCarReview sysCarReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysCarReview.setUserId(userId);
        List<SysCarReview> list = sysCarReviewService.selectSysDoneReviewList(sysCarReview);
        return getDataTable(list);
    }

    /**
     * 已通过列表
     */
    @GetMapping("/completedCarReview")
    public TableDataInfo completedCarReview(SysCarReview sysCarReview)
    {
        startPage();
        List<SysCarReview> list = sysCarReviewService.selectSysCarReviewList(sysCarReview);
        return getDataTable(list);
    }
}
