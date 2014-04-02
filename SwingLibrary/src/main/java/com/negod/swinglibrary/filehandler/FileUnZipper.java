/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.filehandler;

import com.negod.genericlibrary.dto.Dto;
import com.negod.swinglibrary.controller.Application;
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
public class FileUnZipper {

    public enum FileUnzipData {

        FILE_TO_UNZIP, UNZIP_FILE_PATH, MESSAGE;
    }

    public void UnZipFile(Dto<FileUnzipData> settings) {
        new ZipFileHandler(settings).start();
    }

    private class ZipFileHandler extends Thread {

        Dto<NegodProgressBarData> progressBarData = new Dto<NegodProgressBarData>(NegodProgressBarData.class);
        Dto<FileUnzipData> fileUnzipData;
        int totalBytes;

        public ZipFileHandler(Dto<FileUnzipData> fileUnzipData) {
            this.fileUnzipData = fileUnzipData;
        }

        @Override
        public void run() {

            byte[] buffer = new byte[1024];
            int counter = 0;

            try {

                File folder = new File(fileUnzipData.<String>get(FileUnzipData.UNZIP_FILE_PATH));
                if (!folder.exists()) {
                    folder.mkdir();
                }

                FileInputStream stream = new FileInputStream(fileUnzipData.<String>get(FileUnzipData.FILE_TO_UNZIP));
                ZipInputStream zis = new ZipInputStream(stream);

                ZipEntry ze;
                totalBytes = stream.available();
                int totalBytesRead = 0;
                String fileName = "";

                notifySetMinMaxProgressValue(totalBytesRead, totalBytes);
                notifyProgress("Unzipping file " + fileUnzipData.<String>get(FileUnzipData.FILE_TO_UNZIP), 0);
                notifyUnzipProgress("Unzipping file " + fileUnzipData.<String>get(FileUnzipData.FILE_TO_UNZIP));

                while ((ze = zis.getNextEntry()) != null) {

                    try {
                        totalBytesRead = totalBytes - stream.available();
                        fileName = ze.getName();
                        File newFile = new File(fileUnzipData.<String>get(FileUnzipData.UNZIP_FILE_PATH) + File.separator + fileName);

                        notifyProgress("File unzipping", totalBytesRead);

                        new File(newFile.getParent()).mkdirs();
                        FileOutputStream fos = new FileOutputStream(newFile);

                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }

                        fos.close();
                        ze = zis.getNextEntry();
                        notifyUnzipSuccess("Unzip Successfull: " + fileName);
                    } catch (Exception e) {
                        notifyUnzipfailure("Unzip Failed: " + fileName);
                        notifyProgress("Unzip Failed: " + fileName, totalBytes);
                    }
                }

                notifyProgress("Unzip Successfull", totalBytes);
                zis.closeEntry();
                zis.close();

            } catch (IOException ex) {
                notifyUnzipfailure("Unzip Failed");
                notifyProgress("Unzip Failed", totalBytes);
            } finally {
                try {
                    this.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FileUnZipper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void notifySetMinMaxProgressValue(int min, int max) {
            progressBarData.set(NegodProgressBarData.MIN_VALUE, min);
            progressBarData.set(NegodProgressBarData.MAX_VALUE, max);
            Application.getEvents().notifyObservers(ProgressBarEvent.SET_MIN_MAX_VALUES, progressBarData);
        }

        private void notifyProgress(String message, int progressValue) {
            progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, message);
            progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, progressValue);
            Application.getEvents().notifyObservers(ProgressBarEvent.CHANGE_PROGRESS, progressBarData);
        }

        private void notifyUnzipSuccess(String message) {
            fileUnzipData.set(FileUnzipData.MESSAGE, message);
            Application.getEvents().notifyObservers(ZipFileEvent.FILE_UNZIPPED_SUCCESS, fileUnzipData);
        }

        private void notifyUnzipfailure(String message) {
            fileUnzipData.set(FileUnzipData.MESSAGE, message);
            Application.getEvents().notifyObservers(ZipFileEvent.FILE_UNZIPPED_FAILURE, fileUnzipData);
        }

        private void notifyUnzipProgress(String message) {
            fileUnzipData.set(FileUnzipData.MESSAGE, message);
            Application.getEvents().notifyObservers(ZipFileEvent.FILE_UNZIPPING_IN_PROGRESS, fileUnzipData);
        }
    }
}
