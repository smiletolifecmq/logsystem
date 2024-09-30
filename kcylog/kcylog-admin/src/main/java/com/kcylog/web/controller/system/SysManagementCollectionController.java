package com.kcylog.web.controller.system;

import com.kcylog.common.annotation.Log;
import com.kcylog.common.core.controller.BaseController;
import com.kcylog.common.core.domain.AjaxResult;
import com.kcylog.common.core.page.TableDataInfo;
import com.kcylog.common.enums.BusinessType;
import com.kcylog.system.common.Cstj;
import com.kcylog.system.common.Fhtj;
import com.kcylog.system.common.Jyybtj;
import com.kcylog.system.domain.SysManagementArrival;
import com.kcylog.system.domain.SysManagementCollection;
import com.kcylog.system.domain.SysManagementInvoicing;
import com.kcylog.system.service.ISysManagementArrivalService;
import com.kcylog.system.service.ISysManagementCollectionService;
import com.kcylog.system.service.ISysManagementInvoicingService;
import com.kcylog.web.controller.common.ExcelManySheetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 应收账款Controller
 * 
 * @author ruoyi
 * @date 2024-09-23
 */
@RestController
@RequestMapping("/system/collection")
public class SysManagementCollectionController extends BaseController
{
    @Autowired
    private ISysManagementCollectionService sysManagementCollectionService;

    @Autowired
    private ISysManagementInvoicingService sysManagementInvoicingService;

    @Autowired
    private ISysManagementArrivalService sysManagementArrivalService;

    /**
     * 查询应收账款列表
     */
    @PreAuthorize("@ss.hasPermi('system:collection:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysManagementCollection sysManagementCollection)
    {
        startPage();
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        for (SysManagementCollection obj : list){
            Date ysKprq = obj.getYsKprq();
            // 将 Date 转换为 LocalDate
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            if (sysManagementCollection.getYsKprqCs() != null){
                currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            }
            // 计算两个日期之间的差异
            Period period = Period.between(ysKprqLocalDate, currentDate);
            // 获取年份差异
            int years = period.getYears();
            // 判断时间段
            if (years < 1) {
                obj.setYsZl("1年以内");
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
            } else {
                obj.setYsZl("3年以上");
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出应收账款列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:collection:export')")
    @Log(title = "应收账款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(HttpServletResponse response, SysManagementCollection sysManagementCollection)
    {
        if (sysManagementCollection.getYsKprqCs() == null){
            return error("未选择截止日期");
        }
        // 获取上次上报时间
        LocalDate lastDayOfLastMonth = LocalDate.parse(sysManagementCollection.getYsKprqLast());

        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        List<SysManagementCollection> withinOneYearList = new ArrayList<>();
        List<SysManagementCollection> oneToThreeYearList = new ArrayList<>();
        List<SysManagementCollection> overThreeYearList = new ArrayList<>();
        List<SysManagementCollection> toThreeYearList = new ArrayList<>();

        for (SysManagementCollection obj : list) {
            Date ysKprq = obj.getYsKprq();
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            Period period = Period.between(ysKprqLocalDate, currentDate);
            int years = period.getYears();
            if (years < 1) {
                obj.setYsZl("1年以内");
                withinOneYearList.add(obj);
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
                oneToThreeYearList.add(obj);
            } else {
                obj.setYsZl("3年以上");
                overThreeYearList.add(obj);
            }
            Period lastPeriod = Period.between(ysKprqLocalDate, lastDayOfLastMonth);
            int lastYears = lastPeriod.getYears();

            if (years >= 3 && lastYears < 3){
                toThreeYearList.add(obj);
            }

        }
        // 定义输出格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年M月d日");
        // 格式化为指定字符串
        LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
        String formattedDate = currentDate.format(formatter);

        Map<String, List<SysManagementCollection>> map = new LinkedHashMap<>();
        map.put("总表", list);
        map.put("1年内清单", withinOneYearList);
        map.put("1年至3年清单", oneToThreeYearList);
        map.put("3年以上清单", overThreeYearList);
        map.put("本上报周期新增的3年期以上清单", toThreeYearList);

        ExcelManySheetUtil<SysManagementCollection> util = new ExcelManySheetUtil<>(SysManagementCollection.class);
        util.exportExcel(response, map,"截止"+formattedDate+"应收账款（单位：元）");
        return null;
    }

    /**
     * 获取应收账款详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:collection:query')")
    @GetMapping(value = "/{ysId}")
    public AjaxResult getInfo(@PathVariable("ysId") Long ysId)
    {
        return success(sysManagementCollectionService.selectSysManagementCollectionByYsId(ysId));
    }

    /**
     * 新增应收账款
     */
    @PreAuthorize("@ss.hasPermi('system:collection:add')")
    @Log(title = "应收账款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysManagementCollection sysManagementCollection)
    {
        return toAjax(sysManagementCollectionService.insertSysManagementCollection(sysManagementCollection));
    }

    /**
     * 修改应收账款
     */
    @PreAuthorize("@ss.hasPermi('system:collection:edit')")
    @Log(title = "应收账款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysManagementCollection sysManagementCollection)
    {
        return toAjax(sysManagementCollectionService.updateSysManagementCollection(sysManagementCollection));
    }

    /**
     * 删除应收账款
     */
    @PreAuthorize("@ss.hasPermi('system:collection:remove')")
    @Log(title = "应收账款", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ysIds}")
    public AjaxResult remove(@PathVariable Long[] ysIds)
    {
        return toAjax(sysManagementCollectionService.deleteSysManagementCollectionByYsIds(ysIds));
    }

    @PreAuthorize("@ss.hasPermi('system:collection:list')")
    @GetMapping("/ydz")
    public TableDataInfo listYdz(SysManagementCollection sysManagementCollection)
    {
        startPage();
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionListYdz(sysManagementCollection);
        for (SysManagementCollection obj : list){
            Date ysKprq = obj.getYsKprq();
            // 将 Date 转换为 LocalDate
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            if (sysManagementCollection.getYsKprqCs() != null){
                currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            }
            // 计算两个日期之间的差异
            Period period = Period.between(ysKprqLocalDate, currentDate);
            // 获取年份差异
            int years = period.getYears();
            // 判断时间段
            if (years < 1) {
                obj.setYsZl("1年以内");
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
            } else {
                obj.setYsZl("3年以上");
            }
        }
        return getDataTable(list);
    }

    @GetMapping(value = "/exportCs")
    public AjaxResult exportCs(SysManagementCollection sysManagementCollection)
    {
        if (sysManagementCollection.getYsKprqCs() == null){
            return error("未选择上报日期");
        }
        // 获取本月
        LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = firstDayOfMonth.format(formatter);
        sysManagementCollection.setYsKprqLast(formattedDate);
        Fhtj fhtj = new Fhtj();
        int bycsh = 0;
        int bylsh = 0;
        int byqs = 0;
        int zgcsh = 0;
        int zglsh = 0;
        int gzqs = 0;
        int snys = 0;
        int snqs = 0;
        List<SysManagementCollection> listForThisMonth = sysManagementCollectionService.selectSysManagementCollectionThisMonth(sysManagementCollection);
        for (SysManagementCollection obj : listForThisMonth){
            if (obj.getYsFhlx() != null){
                int count1 = countOccurrences(obj.getYsFhlx(), "催款函");
                int count2 = countOccurrences(obj.getYsFhlx(), "律师函");
                int count3 = countOccurrences(obj.getYsFhlx(), "起诉");
                bycsh = bycsh + count1;
                bylsh = bylsh + count2;
                byqs = byqs + count3;
            }
        }
        fhtj.setBycsh(bycsh);
        fhtj.setBylsh(bylsh);
        fhtj.setByqs(byqs);
        // 获取全部
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        for (SysManagementCollection obj : list) {
            Date ysKprq = obj.getYsKprq();
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period period = Period.between(ysKprqLocalDate, currentDate);
            int years = period.getYears();
            if (obj.getYsFhlx() != null){
                int count1 = countOccurrences(obj.getYsFhlx(), "催款函");
                int count2 = countOccurrences(obj.getYsFhlx(), "律师函");
                int count3 = countOccurrences(obj.getYsFhlx(), "起诉");
                zgcsh = zgcsh + count1;
                zglsh = zglsh + count2;
                gzqs = gzqs + count3;
            }
            if (years >= 3) {
                snys ++;
                if (obj.getYsFhlx() != null && !Objects.equals(obj.getYsFhlx(), "")){
                    snqs ++;
                }
            }

        }
        fhtj.setZgcsh(zgcsh);
        fhtj.setZglsh(zglsh);
        fhtj.setGzqs(gzqs);
        fhtj.setSnys(snys);
        fhtj.setSnqs(snqs);
        return success(fhtj);
    }

    @GetMapping(value = "/exportYs")
    public AjaxResult exportYs(SysManagementCollection sysManagementCollection)
    {
        if (sysManagementCollection.getYsKprqCs() == null){
            return error("未选择上报日期");
        }
        // 获取上次上报时间
        LocalDate lastDayOfLastMonth = LocalDate.parse(sysManagementCollection.getYsKprqLast());
        LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
        Cstj cstj = new Cstj();
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal syclOne = BigDecimal.ZERO;
        BigDecimal byxzOne = BigDecimal.ZERO;
        BigDecimal byhkOne = BigDecimal.ZERO;
        BigDecimal hcOne = BigDecimal.ZERO;
        BigDecimal oneTwo = BigDecimal.ZERO;
        BigDecimal totalOne = BigDecimal.ZERO;
        BigDecimal syclTwo = BigDecimal.ZERO;
        BigDecimal byxzTwo = BigDecimal.ZERO;
        BigDecimal byhkTwo = BigDecimal.ZERO;
        BigDecimal hcTwo = BigDecimal.ZERO;
        BigDecimal twoThree = BigDecimal.ZERO;
        BigDecimal totalTwo = BigDecimal.ZERO;
        BigDecimal syclThree = BigDecimal.ZERO;
        BigDecimal byxzThree = BigDecimal.ZERO;
        BigDecimal byhkThree = BigDecimal.ZERO;
        BigDecimal hcThree = BigDecimal.ZERO;
        BigDecimal hzThree = BigDecimal.ZERO;
        BigDecimal totalThree = BigDecimal.ZERO;
        BigDecimal wy = new BigDecimal("10000");

        BigDecimal gyOne = BigDecimal.ZERO;
        BigDecimal myOne = BigDecimal.ZERO;
        BigDecimal gymyOne = BigDecimal.ZERO;
        BigDecimal gyTwo = BigDecimal.ZERO;
        BigDecimal myTwo = BigDecimal.ZERO;
        BigDecimal gymyTwo = BigDecimal.ZERO;
        BigDecimal gyThree = BigDecimal.ZERO;
        BigDecimal myThree = BigDecimal.ZERO;
        BigDecimal gymyThree = BigDecimal.ZERO;
        BigDecimal all = BigDecimal.ZERO;

        //获取本次统计日期上月最后一天
        LocalDate lastMonthLastDay = currentDate.minusMonths(1).withDayOfMonth(currentDate.minusMonths(1).lengthOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SysManagementCollection collectionMonthLastDay = new SysManagementCollection();
        collectionMonthLastDay.setYsKprqCs(lastMonthLastDay.format(formatter));
        //获取上个月数据
        List<SysManagementCollection> lastMonthLastDayList = sysManagementCollectionService.selectSysManagementCollectionList(collectionMonthLastDay);
        for (SysManagementCollection temp : lastMonthLastDayList){
            Date ysKprq = temp.getYsKprq();
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period period = Period.between(ysKprqLocalDate, lastMonthLastDay);
            int years = period.getYears();
            // 判断时间段
            if (years < 1) {
                //红冲1年内
                syclOne = syclOne.add(temp.getYsWdzje());
            } else if (years >= 1 && years < 3) {
                //红冲1-3年
                syclTwo = syclTwo.add(temp.getYsWdzje());
            } else {
                //红冲3年以上
                syclThree = syclThree.add(temp.getYsWdzje());
            }
        }

        // 获取全部
        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        for (SysManagementCollection obj : list) {
            Date ysKprq = obj.getYsKprq();
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period period = Period.between(ysKprqLocalDate, currentDate);
            int years = period.getYears();

            //应收账款总计
            total = total.add(obj.getYsWdzje());
            // 判断时间段
            Period lastPeriod = Period.between(ysKprqLocalDate, lastDayOfLastMonth);
            int lastYears = lastPeriod.getYears();

            //一年期内-移至1-3年  1-3年期内本月新增
            if (years >= 1 && years < 3 && lastYears < 1 && (lastDayOfLastMonth.isAfter(ysKprqLocalDate) || lastDayOfLastMonth.isEqual(ysKprqLocalDate))){
                oneTwo = oneTwo.add(obj.getYsWdzje());
                byxzTwo = byxzTwo.add(obj.getYsWdzje());
            }

            //1-3年期内移至3年期以上  3年期以上本月新增
            if (years >= 3 && lastYears < 3 && (lastDayOfLastMonth.isAfter(ysKprqLocalDate) || lastDayOfLastMonth.isEqual(ysKprqLocalDate))){
                twoThree = twoThree.add(obj.getYsWdzje());
                byxzThree = byxzThree.add(obj.getYsWdzje());
            }

            //坏账
            if(obj.getYsIshz() == 1){
                hzThree = hzThree.add(obj.getYsWdzje());
            }

            if (years < 1) {
                if (obj.getYsKhfl().contains("事业单位") || obj.getYsKhfl().contains("国企")){
                    gyOne = gyOne.add(obj.getYsWdzje());
                }else {
                    myOne = myOne.add(obj.getYsWdzje());
                }
                gymyOne = gymyOne.add(obj.getYsWdzje());
            } else if (years >= 1 && years < 3) {
                if (obj.getYsKhfl().contains("事业单位") || obj.getYsKhfl().contains("国企")){
                    gyTwo = gyTwo.add(obj.getYsWdzje());
                }else {
                    myTwo = myTwo.add(obj.getYsWdzje());
                }
                gymyTwo = gymyTwo.add(obj.getYsWdzje());
            } else {
                if (obj.getYsKhfl().contains("事业单位") || obj.getYsKhfl().contains("国企")){
                    gyThree = gyThree.add(obj.getYsWdzje());
                }else {
                    myThree = myThree.add(obj.getYsWdzje());
                }
                gymyThree = gymyThree.add(obj.getYsWdzje());
            }
            all = all.add(obj.getYsWdzje());
        }

        //获取本次月份开票
        SysManagementInvoicing sysManagementInvoicing = new SysManagementInvoicing();
        sysManagementInvoicing.setMonthString(sysManagementCollection.getYsKprqCs());
        List<SysManagementInvoicing> invoicing = sysManagementInvoicingService.selectInvoicingListHcMonth(sysManagementInvoicing);

        //获取本月到账
        SysManagementArrival sysManagementArrival = new SysManagementArrival();
        sysManagementArrival.setMonthString(sysManagementCollection.getYsKprqCs());
        List<SysManagementArrival> arrival = sysManagementArrivalService.selectSysManagementArrivalList(sysManagementArrival);
        Map<String, Boolean> arrivalMap = new HashMap<>();
        for (SysManagementArrival obj : arrival){
            arrivalMap.put(obj.getDzFph(), true);
        }

        for (SysManagementInvoicing obj1 : invoicing){
            if (obj1.getKpType() == 2 && obj1.getKpYfpsj() != null){
                Date yfpsj = obj1.getKpYfpsj();
                LocalDate ysKprqLocalDate = yfpsj.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Period period = Period.between(ysKprqLocalDate, currentDate);
                int years = period.getYears();
                // 判断时间段
                if (years < 1) {
                    //红冲1年内
                    hcOne = hcOne.add(obj1.getKpKpje().abs());
                } else if (years >= 1 && years < 3) {
                    //红冲1-3年
                    hcTwo = hcTwo.add(obj1.getKpKpje().abs());
                } else {
                    //红冲3年以上
                    hcThree = hcThree.add(obj1.getKpKpje().abs());
                }
            }
            //一年期内 本月新增且不回款
            if (!arrivalMap.containsKey(obj1.getKpFph())){
                byxzOne = byxzOne.add(obj1.getKpKpje());
            }
        }

        for (SysManagementArrival obj2 : arrival){
            // 本月回款
            Date dzKprq = obj2.getDzKprq();
            LocalDate ysDzKprqLocalDate = dzKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period period = Period.between(ysDzKprqLocalDate, currentDate);
            int years = period.getYears();
            // 判断时间段
            if (!isSameYearAndMonth(obj2.getDzKprq(),obj2.getDzRq())){
                if (years < 1) {
                    //1年内
                    byhkOne = byhkOne.add(obj2.getDzMoney());
                } else if (years >= 1 && years < 3) {
                    //1-3年
                    byhkTwo = byhkTwo.add(obj2.getDzMoney());
                } else {
                    //3年以上
                    byhkThree = byhkThree.add(obj2.getDzMoney().abs());
                }
            }
            Period period1 = Period.between(ysDzKprqLocalDate, lastMonthLastDay);
            int years1 = period1.getYears();
            // 判断时间段
            if (years1 < 1 && (lastMonthLastDay.isAfter(ysDzKprqLocalDate) || lastMonthLastDay.isEqual(ysDzKprqLocalDate))) {
                //1年内
                syclOne = syclOne.add(obj2.getDzMoney());
            } else if (years1 >= 1 && years1 < 3 && lastMonthLastDay.isAfter(ysDzKprqLocalDate) || lastMonthLastDay.isEqual(ysDzKprqLocalDate)) {
                //1-3年
                syclTwo = syclTwo.add(obj2.getDzMoney());
            } else if(lastMonthLastDay.isAfter(ysDzKprqLocalDate) || lastMonthLastDay.isEqual(ysDzKprqLocalDate)){
                //3年以上
                syclThree = syclThree.add(obj2.getDzMoney().abs());
            }
        }

        totalOne = syclOne.add(byxzOne).subtract(byhkOne).subtract(hcOne).subtract(oneTwo);
        totalTwo = syclTwo.add(byxzTwo).subtract(byhkTwo).subtract(hcTwo).subtract(twoThree);
        totalThree = syclThree.add(byxzThree).subtract(byhkThree).subtract(hcThree).subtract(hzThree);

        cstj.setTotal(total.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setSyclOne(syclOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setByxzOne(byxzOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setByhkOne(byhkOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setHcOne(hcOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setOneTwo(oneTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setTotalOne(totalOne.divide(wy, 4, RoundingMode.HALF_UP));

        cstj.setSyclTwo(syclTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setByxzTwo(byxzTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setByhkTwo(byhkTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setHcTwo(hcTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setTwoThree(twoThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setTotalTwo(totalTwo.divide(wy, 4, RoundingMode.HALF_UP));

        cstj.setSyclThree(syclThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setByxzThree(byxzThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setByhkThree(byhkThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setHcThree(hcThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setHzThree(hzThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setTotalThree(totalThree.divide(wy, 4, RoundingMode.HALF_UP));

        cstj.setGyOne(gyOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setMyOne(myOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setGymyOne(gymyOne.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setGyTwo(gyTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setMyTwo(myTwo.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setGymyTwo(gymyTwo.divide(wy, 4, RoundingMode.HALF_UP));

        cstj.setGyThree(gyThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setMyThree(myThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setGymyThree(gymyThree.divide(wy, 4, RoundingMode.HALF_UP));
        cstj.setAll(all.divide(wy, 4, RoundingMode.HALF_UP));

        return success(cstj);
    }

    @GetMapping(value = "/exportJyyb")
    public AjaxResult exportJyyb(SysManagementCollection sysManagementCollection)
    {
        if (sysManagementCollection.getYsKprqCs() == null){
            return error("未选择上报日期");
        }

        BigDecimal kpmb1 = new BigDecimal("50920000");
        BigDecimal kpmb2 = new BigDecimal("43920000");
        BigDecimal dzmb1 = new BigDecimal("43710000");
        BigDecimal dzmb2 = new BigDecimal("37700000");
        BigDecimal bfs = new BigDecimal("100");
        BigDecimal wy = new BigDecimal("10000");
        int qtkp = 0;
        int qtdz = 0;
        int htkp = 0;
        int htdz = 0;
        BigDecimal qtkpje = BigDecimal.ZERO;
        BigDecimal qtdzje = BigDecimal.ZERO;
        BigDecimal htkpje = BigDecimal.ZERO;
        BigDecimal htdzje = BigDecimal.ZERO;
        BigDecimal kpzbOne = BigDecimal.ZERO;
        BigDecimal kpzbTwo = BigDecimal.ZERO;
        BigDecimal htzbOne = BigDecimal.ZERO;
        BigDecimal htzbTwo = BigDecimal.ZERO;
        BigDecimal snjdyszk = BigDecimal.ZERO;
        BigDecimal lshk = BigDecimal.ZERO;
        BigDecimal bndxz = BigDecimal.ZERO;
        BigDecimal xzzk = BigDecimal.ZERO;
        BigDecimal qbyszk = BigDecimal.ZERO;
        BigDecimal zjl = BigDecimal.ZERO;

        BigDecimal bndkpdz = BigDecimal.ZERO;

        Jyybtj jyybtj = new Jyybtj();

        //获取本年开票
        SysManagementInvoicing sysManagementInvoicing = new SysManagementInvoicing();
        sysManagementInvoicing.setYearString(sysManagementCollection.getYsKprqCs());
        List<SysManagementInvoicing> invoicing = sysManagementInvoicingService.selectInvoicingListHcMonth(sysManagementInvoicing);
        for (SysManagementInvoicing obj1 : invoicing){
            if (Objects.equals(obj1.getKpYwxz(), "行政性")){
                qtkp ++;
                qtkpje = qtkpje.add(obj1.getKpKpje());
            }else if (Objects.equals(obj1.getKpYwxz(), "合同")){
                htkp ++;
                htkpje = htkpje.add(obj1.getKpKpje());
            }
        }

        //获取本年到账
        SysManagementArrival sysManagementArrival = new SysManagementArrival();
        sysManagementArrival.setYearString(sysManagementCollection.getYsKprqCs());
        List<SysManagementArrival> arrival = sysManagementArrivalService.selectSysManagementArrivalList(sysManagementArrival);
        for (SysManagementArrival obj2 : arrival){
            if (Objects.equals(obj2.getDzYwxz(), "行政性")){
                qtdz ++;
                qtdzje = qtdzje.add(obj2.getDzMoney());
            }else if (Objects.equals(obj2.getDzYwxz(), "合同")){
                htdz ++;
                htdzje = htdzje.add(obj2.getDzMoney());
            }
            if (Objects.equals(obj2.getDzType(), "本年度开票本年度到账")){
                bndkpdz = bndkpdz.add(obj2.getDzMoney());
            }
            if (Objects.equals(obj2.getDzType(), "非本年度开票到账")){
                lshk = lshk.add(obj2.getDzMoney());
            }
        }

        kpzbOne = qtkpje.add(htkpje).divide(kpmb1, 4, RoundingMode.HALF_UP).multiply(bfs);
        kpzbTwo = qtkpje.add(htkpje).divide(kpmb2, 4, RoundingMode.HALF_UP).multiply(bfs);
        htzbOne = qtdzje.add(htdzje).divide(dzmb1, 4, RoundingMode.HALF_UP).multiply(bfs);
        htzbTwo = qtdzje.add(htdzje).divide(dzmb2, 4, RoundingMode.HALF_UP).multiply(bfs);

        //获取全部应收账款
        List<SysManagementCollection> collection = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);
        for (SysManagementCollection obj3 : collection){
            qbyszk = qbyszk.add(obj3.getYsWdzje());
        }

        bndxz = qtkpje.add(htkpje).subtract(bndkpdz);
        snjdyszk = qbyszk.subtract(bndxz).add(lshk);
        xzzk = qtkpje.add(htkpje).subtract(qtdzje).subtract(htdzje);
        jyybtj.setQtkp(qtkp);
        jyybtj.setQtkpje(qtkpje.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setHtkp(htkp);
        jyybtj.setHtkpje(htkpje.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setKpzbOne(kpzbOne);
        jyybtj.setKpzbTwo(kpzbTwo);
        jyybtj.setQtdz(qtdz);
        jyybtj.setQtdzje(qtdzje.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setHtdz(htdz);
        jyybtj.setHtdzje(htdzje.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setHtzbOne(htzbOne);
        jyybtj.setHtzbTwo(htzbTwo);
        jyybtj.setQbyszk(qbyszk.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setXzzk(xzzk.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setBndxz(bndxz.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setLshk(lshk.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setSnjdyszk(snjdyszk.divide(wy, 4, RoundingMode.HALF_UP));
        jyybtj.setZjl(xzzk.divide(snjdyszk, 4, RoundingMode.HALF_UP).multiply(bfs));

        return success(jyybtj);
    }

    @PostMapping("/getExport")
    public AjaxResult getExport(@RequestBody SysManagementCollection sysManagementCollection)
    {
        if (sysManagementCollection.getYsKprqCs() == null){
            return error("未选择截止日期");
        }
        // 获取上次上报时间
        LocalDate lastDayOfLastMonth = LocalDate.parse(sysManagementCollection.getYsKprqLast());

        List<SysManagementCollection> list = sysManagementCollectionService.selectSysManagementCollectionList(sysManagementCollection);

        BigDecimal zb = BigDecimal.ZERO;
        BigDecimal yn = BigDecimal.ZERO;
        BigDecimal ysn = BigDecimal.ZERO;
        BigDecimal sn = BigDecimal.ZERO;
        BigDecimal xzsn = BigDecimal.ZERO;
        BigDecimal wy = new BigDecimal("10000");

        for (SysManagementCollection obj : list) {
            zb = zb.add(obj.getYsWdzje());
            Date ysKprq = obj.getYsKprq();
            LocalDate ysKprqLocalDate = ysKprq.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.parse(sysManagementCollection.getYsKprqCs());
            Period period = Period.between(ysKprqLocalDate, currentDate);
            int years = period.getYears();
            if (years < 1) {
                obj.setYsZl("1年以内");
                yn = yn.add(obj.getYsWdzje());
            } else if (years >= 1 && years < 3) {
                obj.setYsZl("1-3年");
                ysn = ysn.add(obj.getYsWdzje());
            } else {
                obj.setYsZl("3年以上");
                sn = sn.add(obj.getYsWdzje());
            }
            Period lastPeriod = Period.between(ysKprqLocalDate, lastDayOfLastMonth);
            int lastYears = lastPeriod.getYears();

            if (years >= 3 && lastYears < 3){
                xzsn = xzsn.add(obj.getYsWdzje());
            }

        }

        Map<String, BigDecimal> map = new LinkedHashMap<>();
        map.put("zb", zb.divide(wy, 4, RoundingMode.HALF_UP));
        map.put("yn", yn.divide(wy, 4, RoundingMode.HALF_UP));
        map.put("ysn", ysn.divide(wy, 4, RoundingMode.HALF_UP));
        map.put("sn", sn.divide(wy, 4, RoundingMode.HALF_UP));
        map.put("xzsn", xzsn.divide(wy, 4, RoundingMode.HALF_UP));
        return success(map);
    }

    public static int countOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length(); // 移动到下一个位置
        }

        return count;
    }

    public static boolean isSameYearAndMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
