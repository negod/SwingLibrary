/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.lookandfeel;

/**
 *
 * @author Joakim
 */
public enum ThemeTypes {

    NIMBUS("Nimbus");
    String name;

    ThemeTypes(String name) {
        this.name = name;
    }
    
    public String getLookAndFeelName(){
        return name;
    }
}
