/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller;

import com.negod.swinglibrary.controller.lookandfeel.LookAndFeelController;
import com.negod.swinglibrary.controller.jframes.JFrameController;
import com.negod.swinglibrary.controller.events.EventController;
import com.negod.swinglibrary.controller.jframes.NegodJFrame;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fam Johansson
 */
public class ApplicationConfig<Jframes extends Enum<Jframes>> {

    private JFrameController<Jframes, NegodJFrame> viewController;
    private static LookAndFeelController lookAndFeelController;
    private static EventController eventController;
    Class<Jframes> jFrames;

    public ApplicationConfig(Class<Jframes> jFrames) {
        this.jFrames = jFrames;
        startup();
    }

    private void startup() {

        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Starting Application");

        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Initiating JFrameController");
        viewController = new JFrameController(jFrames);
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "JFrameController initiated");

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

    public static LookAndFeelController getLookAndFeel() {
        return lookAndFeelController;
    }

    public JFrameController getJFrames() {
        return viewController;
    }

    public EventController getEvents() {
        return eventController;
    }
}
