/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.events;

/**
 *
 * @author Joakim
 */
public interface EventObserver extends EventRegistry {

    public void update(EventType eventType, Object data);
}
