package com.kcylog.web.controller.common;

import com.kcylog.common.annotation.Excel;

public class ProjectHJ {
    @Excel(name = "项目管理网站流程\\部门", needMerge = true)
    private String xmglwz;
    @Excel(name = "工程测绘部", needMerge = true)
    private int gcchb;
    @Excel(name = "管线工程部", needMerge = true)
    private int gxgcb;
    @Excel(name = "不动产测绘部", needMerge = true)
    private int bdcchb;
    @Excel(name = "地理信息部", needMerge = true)
    private int dlxxb;
    @Excel(name = "合计", needMerge = true)
    private int hj;
    private int num;
    private String dept;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getXmglwz() {
        return xmglwz;
    }

    public void setXmglwz(String xmglwz) {
        this.xmglwz = xmglwz;
    }

    public int getGcchb() {
        return gcchb;
    }

    public void setGcchb(int gcchb) {
        this.gcchb = gcchb;
    }

    public int getGxgcb() {
        return gxgcb;
    }

    public void setGxgcb(int gxgcb) {
        this.gxgcb = gxgcb;
    }

    public int getBdcchb() {
        return bdcchb;
    }

    public void setBdcchb(int bdcchb) {
        this.bdcchb = bdcchb;
    }

    public int getDlxxb() {
        return dlxxb;
    }

    public void setDlxxb(int dlxxb) {
        this.dlxxb = dlxxb;
    }

    public int getHj() {
        return hj;
    }

    public void setHj(int hj) {
        this.hj = hj;
    }
}
