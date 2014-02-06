/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.enums;

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
