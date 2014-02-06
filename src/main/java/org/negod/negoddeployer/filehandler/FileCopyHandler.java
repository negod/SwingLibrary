/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.filehandler;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.negod.negoddeployer.controller.Application;
import org.negod.negoddeployer.entity.Settings;
import org.negod.negoddeployer.enums.EventTypes;

/**
 *
 * @author jojoha
 */
public class FileCopyHandler extends Thread {

    Application app;
    String fileName;
    Settings setting;

    public FileCopyHandler(Application app) {
        this.app = app;
    }

    @Override
    public void run() {
        try {
            Application.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_TEXT, "Copying file");
            Application.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 75);
            File sourceFile = new File(setting.getZipPath());
            File destinationFile = new File(setting.getDeployPath());
            this.fileName = sourceFile.getName();
            FileUtils.copyFileToDirectory(sourceFile, destinationFile);
            Application.getEvents().notifyObservers(EventTypes.ADD_CMD_TEXT, " ****  File " + fileName + " successfully copied! ****");
            Application.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 100);
        } catch (IOException ex) {
            Application.getEvents().notifyObservers(EventTypes.ADD_CMD_TEXT, " ****  File " + fileName + " could not be copied! ****");
            Logger.getLogger(FileCopyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop();
    }

    public void copyFile(Settings setting) {
        this.setting = setting;
        start();
    }
}
