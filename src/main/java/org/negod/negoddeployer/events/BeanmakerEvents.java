/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.negod.negoddeployer.enums.EventTypes;
import org.negod.negoddeployer.interfaces.EventObserver;
import org.negod.negoddeployer.interfaces.EventSubscriber;


/**
 *
 * @author Joakim
 */
public class BeanmakerEvents implements EventSubscriber {

    private ArrayList<EventObserver> observers = new ArrayList<EventObserver>();
    private final List<EventObserver> syncedList = Collections.synchronizedList(observers);

    /**
     * Adds a class that implements the interface EventObserver
     * to the list
     * @param observer
     */
    @Override
    public void addObserver(EventObserver observer) {
        observers.add(observer);
    }

    /**
     *Removes the observer if it exists in the list
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
    }

    /**
     *Notifies all the observers with the eventtype selected from the class
     * that fires the event.
     *
     * @param eventType
     * @param data
     */
    @Override
    public void notifyObservers(EventTypes eventType, Object data) {
        synchronized (syncedList) {
            for (int i = 0; i < syncedList.size(); i++) {
                syncedList.get(i).update(eventType, data);
            }
        }
    }
}
