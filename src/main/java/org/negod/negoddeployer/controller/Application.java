/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import org.negod.negoddeployer.cmdhandler.CmdExecuter;
import org.negod.negoddeployer.filehandler.FileCopyHandler;
import org.negod.negoddeployer.filehandler.FileUnZipper;
import org.negod.negoddeployer.filehandler.XmlFileHandler;

/**
 *
 * @author Fam Johansson
 */
public class Application {

    private static JFrameController viewController;
    private static LookAndFeelController lookAndFeelController;
    private static EventController eventController;
    private static JPanelController panelController;
    private static FileCopyHandler filecopy;
    private static FileUnZipper fileUnzip;
    private static XmlFileHandler xmlFile;
    private static CmdExecuter cmdController;

    public Application() {
        startup();
    }

    private void startup() {

        Logger.getLogger(Application.class.getName()).log(Level.INFO, "Startup");
        viewController = new JFrameController();
        lookAndFeelController = new LookAndFeelController();
        eventController = new EventController();
        panelController = new JPanelController();

        filecopy = new FileCopyHandler(this);
        fileUnzip = new FileUnZipper(this);
        xmlFile = new XmlFileHandler();
        cmdController = new CmdExecuter(this);

        Logger.getLogger(Application.class.getName()).log(Level.INFO, "Startup complete");
    }

    public static void shutDown() {
        Logger.getLogger(Application.class.getName()).log(Level.INFO, "Successful shutdown.... exiting program");
        System.exit(0);
    }

    public static LookAndFeelController getLookAndFeel() {
        return lookAndFeelController;
    }

    public static JFrameController getViews() {
        return viewController;
    }

    public static EventController getEvents() {
        return eventController;
    }

    public static JPanelController getPanels() {
        return panelController;
    }

    public static FileCopyHandler getFileCopy() {
        return filecopy;
    }

    public static FileUnZipper getFileUnzipper() {
        return fileUnzip;
    }

    public static XmlFileHandler getXmlFileHandler() {
        return xmlFile;
    }

    public static CmdExecuter getCmdExecuter() {
        return cmdController;
    }

}
