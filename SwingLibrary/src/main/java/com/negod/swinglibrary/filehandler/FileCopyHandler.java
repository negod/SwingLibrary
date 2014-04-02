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
public class FileCopyHandler {

    private static final String COPYING_FILE = "Copying file";

    public enum FileCopyData {

        PATH_FROM, PATH_TO, MESSAGE;
    }

    public FileCopyHandler() {
    }

    private class FileCopy extends Thread {

        String fileName;
        Dto<FileCopyData> fileCopyData;
        Dto<NegodProgressBarData> progressBarData = new Dto<>(NegodProgressBarData.class);

        public FileCopy(Dto<FileCopyData> fileCopyData) {
            this.fileCopyData = fileCopyData;
        }

        private void notifySetMinMaxProgressValue(int min, int max) {
            progressBarData.set(NegodProgressBarData.MIN_VALUE, min);
            progressBarData.set(NegodProgressBarData.MAX_VALUE, max);
            Application.getEvents().notifyObservers(ProgressBarEvent.SET_MIN_MAX_VALUES, progressBarData);
        }

        private void notifyProgress(String message, int progressValue) {
            progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, message);
            progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, progressValue);
            Application.getEvents().notifyObservers(ProgressBarEvent.SET_MIN_MAX_VALUES, progressBarData);
        }

        private void notifyFileCopy(String message) {
            fileCopyData.set(FileCopyData.MESSAGE, message);
            Application.getEvents().notifyObservers(FileCopyEvent.FILE_COPIED, fileCopyData);
        }

        @Override
        public void run() {

            notifySetMinMaxProgressValue(0, 100);
            notifyProgress(COPYING_FILE, 75);

            try {
                File sourceFile = new File(fileCopyData.<String>get(FileCopyData.PATH_FROM));
                File destinationFile = new File(fileCopyData.<String>get(FileCopyData.PATH_TO));

                this.fileName = sourceFile.getName();
                FileUtils.copyFileToDirectory(sourceFile, destinationFile);

                notifyProgress("File successfully copied!", 100);
                notifyFileCopy(fileName + " successfully copied!");

            } catch (IOException ex) {
                notifyFileCopy(fileName + " could not be copied!");
                notifyProgress("FileCopy failure!", 100);
                Logger.getLogger(FileCopyHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    this.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FileCopyHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void copyFile(Dto<FileCopyData> fileCopyData) {
        new FileCopy(fileCopyData).start();
    }
}
