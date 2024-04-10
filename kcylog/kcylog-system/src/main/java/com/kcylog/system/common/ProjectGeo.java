package com.kcylog.system.common;

import java.math.BigDecimal;

public class ProjectGeo {
    //福州本地坐标系
    private String geometry;

    //2000大地坐标系
    private String geometry2000;

    //缓冲区坐标串，福州本地坐标系
    private String bufferGeometry;

    //缓冲区坐标串，2000大地坐标系
    private String bufferGeometry2000;

    //国家2000平面，福州
    private String geometryGauss2000;

    //缓冲区坐标串，国家2000平面，福州
    private String bufferGeometryGauss2000;

    private BigDecimal bufferDistance;

    public BigDecimal getBufferDistance() {
        return bufferDistance;
    }

    public void setBufferDistance(BigDecimal bufferDistance) {
        this.bufferDistance = bufferDistance;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public String getGeometry2000() {
        return geometry2000;
    }

    public void setGeometry2000(String geometry2000) {
        this.geometry2000 = geometry2000;
    }

    public String getBufferGeometry() {
        return bufferGeometry;
    }

    public void setBufferGeometry(String bufferGeometry) {
        this.bufferGeometry = bufferGeometry;
    }

    public String getBufferGeometry2000() {
        return bufferGeometry2000;
    }

    public void setBufferGeometry2000(String bufferGeometry2000) {
        this.bufferGeometry2000 = bufferGeometry2000;
    }

    public String getGeometryGauss2000() {
        return geometryGauss2000;
    }

    public void setGeometryGauss2000(String geometryGauss2000) {
        this.geometryGauss2000 = geometryGauss2000;
    }

    public String getBufferGeometryGauss2000() {
        return bufferGeometryGauss2000;
    }

    public void setBufferGeometryGauss2000(String bufferGeometryGauss2000) {
        this.bufferGeometryGauss2000 = bufferGeometryGauss2000;
    }
}
