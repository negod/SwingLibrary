/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.events;

import com.negod.genericlibrary.dto.Dto;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class NegodEvent {

    private Enum eventEnum;
    private Dto values;

    public NegodEvent(Enum eventEnum, Dto values) {
        this.eventEnum = eventEnum;
        this.values = values;
    }

    public boolean equalsEvent(Enum enumValue) {
        return enumValue.equals(eventEnum);
    }

    public Enum getEvent() {
        return eventEnum;
    }

    public Dto getValues() {
        return values;
    }
}
