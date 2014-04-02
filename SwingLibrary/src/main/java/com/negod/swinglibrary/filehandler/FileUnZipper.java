/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.filehandler;

import com.negod.genericlibrary.dto.Dto;
import com.negod.swinglibrary.controller.Application;
import com.negod.swinglibrary.controller.events.NegodEvent;
import com.negod.swinglibrary.controller.events.global.ProgressBarEvent;
import com.negod.swinglibrary.controller.events.global.ZipFileEvent;
import com.negod.swinglibrary.progressbar.NegodProgressBar.NegodProgressBarData;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author jojoha
 */
public class FileUnZipper extends Thread {

    public enum FileUnzipData {

        FILE_TO_UNZIP, UNZIP_FILE_PATH, MESSAGE;
    }

    public void UnZipFile(Dto<FileUnzipData> settings) {
        new ZipFileHandler(settings).start();
    }

    private class ZipFileHandler extends Thread {

        Dto<NegodProgressBarData> progressBarEvent = new Dto<NegodProgressBarData>(NegodProgressBarData.class);
        Dto<FileUnzipData> settings;

        public ZipFileHandler(Dto<FileUnzipData> settings) {
            this.settings = settings;
        }

        @Override
        public void run() {

            progressBarEvent.set(NegodProgressBarData.PROGRESS_TEXT, "Unzipping file");
            progressBarEvent.set(NegodProgressBarData.PROGRESS_VALUE, 0);

            Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarEvent));

            byte[] buffer = new byte[1024];
            int counter = 0;

            try {

                File folder = new File(settings.<String>get(FileUnzipData.UNZIP_FILE_PATH));
                if (!folder.exists()) {
                    folder.mkdir();
                }

                ZipInputStream zis = new ZipInputStream(new FileInputStream(settings.<String>get(FileUnzipData.FILE_TO_UNZIP)));
                ZipEntry ze = zis.getNextEntry();

                while (ze != null) {

                    String fileName = ze.getName();
                    File newFile = new File(settings.<String>get(FileUnzipData.UNZIP_FILE_PATH) + File.separator + fileName);

                    settings.set(FileUnzipData.MESSAGE, "File unziping : " + newFile.getAbsoluteFile());
                    progressBarEvent.set(NegodProgressBarData.PROGRESS_TEXT, settings.<String>get(FileUnzipData.MESSAGE));

                    Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarEvent));
                    Application.getEvents().notifyObservers(new NegodEvent(ZipFileEvent.FILE_UNZIPPING_IN_PROGRESS, settings));

                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                        progressBarEvent.set(NegodProgressBarData.PROGRESS_VALUE, counter++);
                        Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarEvent));
                    }

                    fos.close();
                    ze = zis.getNextEntry();
                }

                zis.closeEntry();
                zis.close();

                progressBarEvent.set(NegodProgressBarData.PROGRESS_VALUE, 100);
                Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarEvent));

                settings.set(FileUnzipData.MESSAGE, "File Successfully unzipped ");
                Application.getEvents().notifyObservers(new NegodEvent(ZipFileEvent.FILE_UNZIPPED_SUCCESS, settings));
                this.join();
            } catch (IOException ex) {
                progressBarEvent.set(NegodProgressBarData.PROGRESS_TEXT, "Failed to UNZIP file");
                progressBarEvent.set(NegodProgressBarData.PROGRESS_VALUE, 100);
                Application.getEvents().notifyObservers(new NegodEvent(ZipFileEvent.FILE_UNZIPPED_FAILURE, settings));
                Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarEvent));
            } catch (InterruptedException ex) {
                progressBarEvent.set(NegodProgressBarData.PROGRESS_TEXT, "Failed to UNZIP file");
                progressBarEvent.set(NegodProgressBarData.PROGRESS_VALUE, 100);
                Application.getEvents().notifyObservers(new NegodEvent(ZipFileEvent.FILE_UNZIPPED_FAILURE, settings));
                Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarEvent));
                Logger.getLogger(FileUnZipper.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    this.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FileUnZipper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
