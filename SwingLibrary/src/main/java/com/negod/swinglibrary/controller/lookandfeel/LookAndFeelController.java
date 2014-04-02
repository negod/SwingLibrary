/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.lookandfeel;

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

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LookAndFeelController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}
