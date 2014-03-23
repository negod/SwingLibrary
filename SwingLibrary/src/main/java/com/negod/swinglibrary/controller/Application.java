/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller;

import com.negod.swinglibrary.controller.events.EventController;
import com.negod.swinglibrary.controller.jframes.JFrameController;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class Application {

    public static ApplicationConfig applicationStartup;

    public Application(ApplicationConfig applicationStartup) {
        Application.applicationStartup = applicationStartup;
    }

    public static JFrameController getViews() {
        return applicationStartup.getJFrames();
    }

    public static EventController getEvents() {
        return applicationStartup.getEvents();
    }
}
