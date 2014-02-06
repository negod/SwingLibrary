/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.filehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.negod.negoddeployer.controller.Application;
import org.negod.negoddeployer.entity.Settings;
import org.negod.negoddeployer.enums.EventTypes;

/**
 *
 * @author jojoha
 */
public class FileUnZipper extends Thread {

    Application app;

    public FileUnZipper(Application app) {
        this.app = app;
    }

    public void UnZipFile(Settings settings) {
        new ZipFileHandler(app, settings).start();
    }

    private class ZipFileHandler extends Thread {

        Application app;
        Settings settings;

        public ZipFileHandler(Application app, Settings settings) {
            this.app = app;
            this.settings = settings;
        }

        @Override
        public void run() {

            app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_TEXT, "Unzipping file");
            app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 0);
            byte[] buffer = new byte[1024];

            int counter = 0;

            try {

                File folder = new File(settings.getDeployPath());
                if (!folder.exists()) {
                    folder.mkdir();
                }

                ZipInputStream zis = new ZipInputStream(new FileInputStream(settings.getZipPath()));
                ZipEntry ze = zis.getNextEntry();

                while (ze != null) {

                    String fileName = ze.getName();
                    File newFile = new File(settings.getDeployPath() + File.separator + fileName);
                    app.getEvents().notifyObservers(EventTypes.ADD_CMD_TEXT, "file unzip : " + newFile.getAbsoluteFile());

                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                        app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, counter++);
                    }

                    fos.close();
                    ze = zis.getNextEntry();
                }

                zis.closeEntry();
                zis.close();
                app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 100);
                app.getEvents().notifyObservers(EventTypes.FILE_UNZIPPED_SUCCESS, null);
                this.join();
            } catch (IOException ex) {
                app.getEvents().notifyObservers(EventTypes.FILE_UNZIPPED_FAILURE, null);
                app.getEvents().notifyObservers(EventTypes.FILE_UNZIPPED_FAILURE, "Failed to UNZIP file");
                app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 100);
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                app.getEvents().notifyObservers(EventTypes.FILE_UNZIPPED_FAILURE, null);
                app.getEvents().notifyObservers(EventTypes.FILE_UNZIPPED_FAILURE, "Failed to UNZIP file");
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
