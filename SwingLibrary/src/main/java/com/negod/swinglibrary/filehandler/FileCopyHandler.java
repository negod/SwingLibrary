/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.filehandler;

import com.negod.genericlibrary.dto.Dto;
import com.negod.swinglibrary.controller.Application;
import com.negod.swinglibrary.controller.events.NegodEvent;
import com.negod.swinglibrary.controller.events.global.FileCopyEvent;
import com.negod.swinglibrary.controller.events.global.ProgressBarEvent;
import com.negod.swinglibrary.progressbar.NegodProgressBar.NegodProgressBarData;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jojoha
 */
public class FileCopyHandler extends Thread {

    String fileName;
    Dto<FileCopyData> fileCopyData;
    private static final String COPYING_FILE = "Copying file";

    public enum FileCopyData {

        PATH_FROM, PATH_TO, MESSAGE;
    }

    public FileCopyHandler() {
    }

    @Override
    public void run() {
        Dto<NegodProgressBarData> progressBarData = new Dto<>(NegodProgressBarData.class);

        progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, COPYING_FILE);
        progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, 75);
        progressBarData.set(NegodProgressBarData.MIN_VALUE, 0);
        progressBarData.set(NegodProgressBarData.MAX_VALUE, 100);

        Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.SET_MIN_MAX_VALUES, progressBarData));
        Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarData));

        try {
            File sourceFile = new File(fileCopyData.<String>get(FileCopyData.PATH_FROM));
            File destinationFile = new File(fileCopyData.<String>get(FileCopyData.PATH_TO));

            this.fileName = sourceFile.getName();
            FileUtils.copyFileToDirectory(sourceFile, destinationFile);

            fileCopyData.set(FileCopyData.MESSAGE, " ****  File " + fileName + " successfully copied! ****");

            progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, fileCopyData.<String>get(FileCopyData.MESSAGE));
            progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, 100);

            Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarData));
            Application.getEvents().notifyObservers(new NegodEvent(FileCopyEvent.FILE_COPIED, fileCopyData));

        } catch (IOException ex) {
            fileCopyData.set(FileCopyData.MESSAGE, " ****  File " + fileName + " could not be copied! ****");
            progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, fileCopyData.<String>get(FileCopyData.MESSAGE));
            Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarData));
            Application.getEvents().notifyObservers(new NegodEvent(FileCopyEvent.FILE_COPIED, fileCopyData));
            Logger.getLogger(FileCopyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        stop();
    }

    public void copyFile(Dto<FileCopyData> fileCopyData) {
        this.fileCopyData = fileCopyData;
        start();
    }
}
