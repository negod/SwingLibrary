/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.enums;

/**
 *
 * @author Joakim
 */
public enum JFrames {

    MainFrame("Supa dupa Inspection deployment Tool"),
    ProgressBar("Mr Progress bar");
    
    String viewName;

    JFrames(String jFrameName) {
        this.viewName = jFrameName;
    }

    public String getJFrameTitle() {
        return viewName;
    }
}
