/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.controller;

import org.negod.negoddeployer.enums.EventTypes;
import org.negod.negoddeployer.events.BeanmakerEvents;
import org.negod.negoddeployer.interfaces.EventObserver;
import org.negod.negoddeployer.interfaces.EventSubscriber;

/**
 *
 * @author Joakim
 */
public class EventController implements EventSubscriber {

    BeanmakerEvents events = new BeanmakerEvents();

    @Override
    public void addObserver(EventObserver frame) {
        events.addObserver(frame);
    }

    @Override
    public void removeObserver(EventObserver frame) {
        events.removeObserver(frame);
    }

    @Override
    public void notifyObservers(EventTypes eventType, Object data) {
        events.notifyObservers(eventType, data);
    }
}
