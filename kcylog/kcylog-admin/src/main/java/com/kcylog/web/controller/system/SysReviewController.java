package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.common.utils.DateUtils;
import com.kcylog.common.utils.SecurityUtils;
import com.kcylog.common.utils.poi.ExcelUtil;
import com.kcylog.system.domain.SysProcessConfigInfo;
import com.kcylog.system.domain.SysReview;
import com.kcylog.system.domain.SysReviewEmployee;
import com.kcylog.system.domain.SysReviewProcess;
import com.kcylog.system.service.ISysProcessConfigInfoService;
import com.kcylog.system.service.ISysReviewEmployeeService;
import com.kcylog.system.service.ISysReviewProcessService;
import com.kcylog.system.service.ISysReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 审核单Controller
 * 
 * @author ruoyi
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/system/review")
public class SysReviewController extends BaseController
{
    final int PassStatus = 2;
    final int NoPassStatus = 3;

    @Autowired
    private ISysReviewService sysReviewService;
    @Autowired
    private ISysProcessConfigInfoService sysProcessConfigInfoService;

    @Autowired
    private ISysReviewProcessService sysReviewProcessService;

    @Autowired
    private ISysReviewEmployeeService sysReviewEmployeeService;

    /**
     * 查询审核单列表
     */
    @PreAuthorize("@ss.hasPermi('system:review:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysReview sysReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReview.setUserId(userId);
        List<SysReview> list = sysReviewService.selectSysReviewList(sysReview);

        return getDataTable(list);
    }

    /**
     * 导出审核单列表
     */
    @Log(title = "审核单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysReview sysReview)
    {
        List<SysReview> list = sysReviewService.selectSysReviewList(sysReview);
        List<SysReview> reviewList = new ArrayList<SysReview>();

        for (SysReview sysReviewTemp:list){
            SysReview sysReviewObj = new SysReview();
            List<SysReviewEmployee> reviewEmployee = sysReviewEmployeeService.selectSysReviewEmployeeByReviewId(sysReviewTemp.getReviewId());
            sysReviewObj.setDept(sysReviewTemp.getDept());
            sysReviewObj.setSerialNum(sysReviewTemp.getSerialNum());
            sysReviewObj.setProjectName(sysReviewTemp.getProjectName());
            sysReviewObj.setRequester(sysReviewTemp.getRequester());
            sysReviewObj.setPorjectMoney(sysReviewTemp.getPorjectMoney());
            sysReviewObj.setWorkload(sysReviewTemp.getWorkload());
            sysReviewObj.setUser(sysReviewTemp.getUser());
            sysReviewObj.setReviewEmployee(reviewEmployee);
            reviewList.add(sysReviewObj);
        }

        ExcelUtil<SysReview> util = new ExcelUtil<SysReview>(SysReview.class);
        util.exportExcel(response, reviewList, "审核单数据");
    }

    /**
     * 获取审核单详细信息
     */
    @GetMapping(value = "/{reviewId}")
    public AjaxResult getInfo(@PathVariable("reviewId") String reviewId)
    {
        return success(sysReviewService.selectSysReviewByReviewId(reviewId));
    }

    /**
     * 新增审核单
     */
    @Log(title = "审核单", businessType = BusinessType.INSERT)
    @Transactional
    @PostMapping
    public AjaxResult add(@RequestBody SysReview sysReview)
    {
        Long userId = SecurityUtils.getUserId();
        Long deptId = SecurityUtils.getDeptId();
        sysReview.setUserId(userId);
        sysReview.setDeptId(deptId);
        sysReviewService.insertSysReview(sysReview);
        List<SysProcessConfigInfo> list = sysProcessConfigInfoService.selectSysProcessConfigInfoListByDeptId(deptId);
        List<SysReviewProcess> reviewProcess = new ArrayList<>();
        for (SysProcessConfigInfo configInfo : list) {
            SysReviewProcess review = new SysReviewProcess();
            review.setReviewId((sysReview.getReviewId()));
            review.setUserId(configInfo.getUserId());
            review.setStatus((long)0);
            review.setNode(Long.parseLong(configInfo.getNode()));
            reviewProcess.add(review);
        }
        int result = sysReviewProcessService.insertSysReviewProcessBatch(reviewProcess);

        return toAjax(result);
    }

    /**
     * 修改审核单
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysReview sysReview)
    {
        return toAjax(sysReviewService.updateSysReview(sysReview));
    }

    /**
     * 删除审核单
     */
    @Log(title = "审核单", businessType = BusinessType.DELETE)
    @Transactional
	@DeleteMapping("/{reviewIds}")
    public AjaxResult remove(@PathVariable String[] reviewIds)
    {
        sysReviewService.deleteSysReviewByReviewIds(reviewIds);
        int result = sysReviewProcessService.deleteSysReviewProcessByReviewIds(reviewIds);
        return toAjax(result);
    }


    /**
     * 发起审核单审核
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_status")
    public AjaxResult setStatus(@RequestBody SysReview sysReview)
    {
        //设置审核单为进行中
        sysReviewService.setSysReviewStatusByReviewId(sysReview);
        //先将审核单流程状态全部设置成未开始
        sysReviewProcessService.setStatusNotStartByReviewId(sysReview.getReviewId());
        //将审核单第一个流程设置成进行中
        int result = sysReviewProcessService.setStatusByReviewIdFirst(sysReview.getReviewId());
        return toAjax(result);
    }

    /**
     * 获取待办审核单
     */
    @GetMapping("/upcomingListReview")
    public TableDataInfo upcomingListReview(SysReview sysReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReview.setUserId(userId);
        List<SysReview> list = sysReviewService.selectSysUpcomingReviewList(sysReview);
        return getDataTable(list);
    }


    /**
     * 修改审核是否通过
     */
    @Log(title = "审核单", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/set_review_process")
    public AjaxResult setReviewProcessStatus(@RequestBody SysReviewProcess sysReviewProcess)
    {
        //修改审核流程中流程信息
        Long userId = SecurityUtils.getUserId();
        sysReviewProcess.setUserId(userId);
        sysReviewProcess.setReviewTime(DateUtils.getNowDate());
        sysReviewProcessService.setStatusByUserIdAndReviewId(sysReviewProcess);

        SysReview review = new SysReview();
        review.setReviewId(sysReviewProcess.getReviewId());
        if (sysReviewProcess.getStatus() == (long)this.PassStatus){
            //审核通过
            //获取审核单审核流程
            List<SysReviewProcess> list = sysReviewProcessService.selectSysReviewProcessList(sysReviewProcess);
            int passNum = 0;
            for (SysReviewProcess obj : list) {
                if (obj.getStatus() == (long)this.PassStatus){
                    passNum ++;
                }
            }
            //判断流程是否都已经通过了
            if (passNum == list.size()){
                //审核单通过
                review.setStatus((long)this.PassStatus);
                sysReviewService.setSysReviewStatusByReviewId(review);
            }else {
                //审核单流程没有全部通过，进入下一个流程审核
                sysReviewProcessService.setNextStatusByReviewId(sysReviewProcess.getReviewId());
            }

        }else {
            //审核不通过
            review.setStatus((long)this.NoPassStatus);
            sysReviewService.setSysReviewStatusByReviewId(review);
        }
        return toAjax(1);
    }

    /**
     * 获取全部已通过的审核单
     */
    @GetMapping("/completedListReview")
    public TableDataInfo completedReview(SysReview sysReview)
    {
        startPage();
        List<SysReview> list = sysReviewService.selectCompletedReviewList(sysReview);
        return getDataTable(list);
    }

    @GetMapping("/doneListReview")
    public TableDataInfo doneReview(SysReview sysReview)
    {
        startPage();
        Long userId = SecurityUtils.getUserId();
        sysReview.setUserId(userId);
        List<SysReview> list = sysReviewService.selectDoneReviewList(sysReview);
        return getDataTable(list);
    }
}
