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
public interface EventObserver {
    public void update(EventTypes eventType, Object data);
    public void registerAsObserver();
}

