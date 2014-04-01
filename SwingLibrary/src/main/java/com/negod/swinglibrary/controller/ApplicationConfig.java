package com.negod.swinglibrary.controller;

import com.negod.swinglibrary.controller.lookandfeel.LookAndFeelController;
import com.negod.swinglibrary.controller.events.EventController;
import com.negod.swinglibrary.controller.jframes.ViewController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 * @param <Jframes>
 */
public class ApplicationConfig {

    private ViewController viewController;
    private static LookAndFeelController lookAndFeelController;
    private EventController eventController;

    public ApplicationConfig(ViewController jFrameContr) {
        this.viewController = jFrameContr;
        this.eventController = new EventController();
        startup();
    }

    private void startup() {

        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Starting Application");

        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Initiating Look And Feel Controler");
        lookAndFeelController = new LookAndFeelController();
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Look And Feel Controler initiated");

        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Initiating EventController");
        eventController = new EventController();
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "EventController initiated");

        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Startup complete");
    }

    public static void shutDown() {
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Successful shutdown.... exiting program");
        System.exit(0);
    }

    public LookAndFeelController getLookAndFeel() {
        return lookAndFeelController;
    }

    public ViewController getJFrames() {
        return viewController;
    }

    public EventController getEvents() {
        return eventController;
    }
}
