/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.entity;

/**
 *
 * @author jojoha
 */
public class Settings {

    private String zipPath;
    private String deployPath;
    private String compilePath;
    private boolean unzip;
    private boolean compile;

    public Settings(String zipPath, String deployPath) {
        this.zipPath = zipPath;
        this.deployPath = deployPath;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public String getDeployPath() {
        return deployPath;
    }

    public void setDeployPath(String deployPath) {
        this.deployPath = deployPath;
    }

    public boolean isUnzip() {
        return unzip;
    }

    public void setUnzip(boolean unzip) {
        this.unzip = unzip;
    }

    public String getCompilePath() {
        return compilePath;
    }

    public void setCompilePath(String compilePath) {
        this.compilePath = compilePath;
    }

    public boolean isCompile() {
        return compile;
    }

    public void setCompile(boolean compile) {
        this.compile = compile;
    }

}
