package com.kcylog.system.common;

import java.util.List;

public class ProjectGeoList {
    private String projectName;
    private String projectCode;
    private String requester;
    private String registerTime;
    private Integer mapScale;
    private List<ProjectGeo> projectGeo;
    private List<Geotfinfo> geotfinfo;

    public Integer getMapScale() {
        return mapScale;
    }

    public void setMapScale(Integer mapScale) {
        this.mapScale = mapScale;
    }

    public List<Geotfinfo> getGeotfinfo() {
        return geotfinfo;
    }

    public void setGeotfinfo(List<Geotfinfo> geotfinfo) {
        this.geotfinfo = geotfinfo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public List<ProjectGeo> getProjectGeo() {
        return projectGeo;
    }

    public void setProjectGeo(List<ProjectGeo> projectGeo) {
        this.projectGeo = projectGeo;
    }
}
