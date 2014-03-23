/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.events;

import com.negod.swinglibrary.controller.ApplicationConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joakim
 */
public class Events implements EventSubscriber {

    private ArrayList<EventObserver> observers = new ArrayList<EventObserver>();
    private final List<EventObserver> syncedList = Collections.synchronizedList(observers);

    /**
     * Adds a class that implements the interface EventObserver to the list
     *
     * @param observer
     */
    @Override
    public void addObserver(EventObserver observer) {
        observers.add(observer);
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Observer added " + observer.getClass().getName());
    }

    /**
     * Removes the observer if it exists in the list
     *
     * @param observer
     */
    @Override
    public void removeObserver(EventObserver observer) {
        synchronized (syncedList) {
            for (int i = 0; i < syncedList.size(); i++) {
                if (syncedList.get(i).equals(observer)) {
                    syncedList.remove(i);
                    break;
                }
            }
        }
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Observer removed " + observer.getClass().getName());
    }

    /**
     * Notifies all the observers with the eventtype selected from the class
     * that fires the event.
     *
     * @param eventType
     * @param data
     */
    @Override
    public void notifyObservers(EventType eventType, Object data) {
        Logger.getLogger(ApplicationConfig.class.getName()).log(Level.INFO, "Notifying observers " + eventType.getClass().getName());
        synchronized (syncedList) {
            for (int i = 0; i < syncedList.size(); i++) {
                syncedList.get(i).update(eventType, data);
            }
        }
    }
}
