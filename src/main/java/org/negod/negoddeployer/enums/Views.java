/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.enums;

/**
 *
 * @author Joakim
 */
public enum Views {

    MainFrame("Beanmaker V2"),
    DatabaseSettings("Databasesettings"),
    DatabaseSettingsEditor("Edit database setting"),
    NewProjectWizard("Create new project"),
    ViewProjectsEditor("Edit project settings");
    String viewName;

    Views(String viewName) {
        this.viewName = viewName;
    }

    public String getViewTitle() {
        return viewName;
    }
}
