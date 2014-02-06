/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.interfaces;

import org.negod.negoddeployer.enums.EventTypes;

/**
 *
 * @author Joakim
 */
public interface EventSubscriber {
    public void addObserver(EventObserver observer);
    public void removeObserver(EventObserver observer);
    public void notifyObservers(EventTypes eventType, Object data);
}
