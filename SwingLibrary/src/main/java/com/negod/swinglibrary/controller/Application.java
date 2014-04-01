/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller;

import com.negod.genericlibrary.xml.XmlFileHandler;
import com.negod.swinglibrary.commandpromt.CmdExecuter;
import com.negod.swinglibrary.controller.events.EventController;
import com.negod.swinglibrary.controller.jframes.ViewController;
import com.negod.swinglibrary.controller.lookandfeel.LookAndFeelController;
import com.negod.swinglibrary.filehandler.FileChooser;
import com.negod.swinglibrary.filehandler.FileUnZipper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class Application {

    private static ApplicationConfig applicationStartup;
    private static XmlFileHandler xmlFileHandler;
    private static CmdExecuter cmdExecuter;
    private static FileChooser fileChooser;
    private static FileUnZipper fileUnZipper;

    public Application(ApplicationConfig applicationStartup) {
        Application.applicationStartup = applicationStartup;
        xmlFileHandler = new XmlFileHandler();
        fileChooser = new FileChooser();
        fileUnZipper = new FileUnZipper();
        cmdExecuter = new CmdExecuter();
    }

    public static ViewController getViews() {
        return applicationStartup.getJFrames();
    }

    public static EventController getEvents() {
        return applicationStartup.getEvents();
    }

    public static LookAndFeelController getLookAndFeel() {
        return applicationStartup.getLookAndFeel();
    }

    public static XmlFileHandler getXmlFileHandler() {
        return xmlFileHandler;
    }

    public static FileChooser getFileChooser() {
        return fileChooser;
    }

    public static CmdExecuter getCmdExecuter() {
        return cmdExecuter;
    }

    public static FileUnZipper getFileUnZipper() {
        return fileUnZipper;
    }

    public static void shutDown() {
        Logger.getLogger(Application.class.getName()).log(Level.INFO, "Exiting program");
        System.exit(0);
    }
}
