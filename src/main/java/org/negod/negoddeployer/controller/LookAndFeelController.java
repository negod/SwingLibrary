/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.controller;

import org.negod.negoddeployer.enums.ThemeTypes;
import org.negod.negoddeployer.jframes.MainFrame;



/**
 *
 * @author Joakim
 */
public class LookAndFeelController {

    private static ThemeTypes currentLookAndFeel;

    public LookAndFeelController() {
    }

    public ThemeTypes getCurrentTheme() {
        return currentLookAndFeel;
    }

    public void setLookAndFeel(ThemeTypes lookAndFeel) {
        currentLookAndFeel = lookAndFeel;
        try {

            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (lookAndFeel.getLookAndFeelName().equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
