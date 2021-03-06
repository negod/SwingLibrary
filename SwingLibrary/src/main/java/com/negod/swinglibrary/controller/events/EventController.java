/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.events;

import com.negod.genericlibrary.dto.Dto;

/**
 *
 * @author Joakim
 */
public class EventController implements EventSubscriber {

    Events events = new Events();

    @Override
    public void addObserver(EventObserver frame) {
        events.addObserver(frame);
    }

    @Override
    public void removeObserver(EventObserver frame) {
        events.removeObserver(frame);
    }

    @Override
    public void notifyObservers(Enum event, Dto data) {
        events.notifyObservers(event, data);
    }
}
