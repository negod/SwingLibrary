/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.jframes;

import com.negod.swinglibrary.controller.ApplicationConfig;
import com.negod.swinglibrary.controller.events.EventObserver;
import java.util.EnumMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Joakim
 */
public class JFrameController<T extends Enum<T>, F extends NegodJFrame> {

    private EnumMap<T, Class<F>> views;

    public JFrameController(Class<T> inEnum) {
        views = new EnumMap<>(inEnum);
    }

    public void addJFrame(T key, Class<F> jFrame) {
        views.put(key, jFrame);
    }

    public void startFrame(final T jFrameKey, final Object data) {
        if (views.containsKey(jFrameKey)) {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        F frame = views.get(jFrameKey).newInstance();
                        initFrame(frame);
                    } catch (InstantiationException | IllegalAccessException ex) {
                        Logger.getLogger(JFrameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    private void initFrame(F frame) {
        frame.init();
        frame.setVisible(true);
        if (frame instanceof EventObserver) {
            EventObserver observer = (EventObserver) frame;
            observer.registerAsObserver();
        }
    }

    public void closeFrame(NegodJFrame frame) {
        /*if (frame.isMainFrame()) {
         int result = JOptionPane.showConfirmDialog(
         null,
         "Are you sure you want to exit the application?",
         "Exit Application",
         JOptionPane.YES_NO_OPTION);
         if (result == JOptionPane.YES_OPTION) {
         ApplicationStartup.shutDown();
         }
         } else {
         if (frame instanceof EventObserver && frame.isMainFrame()) {
         EventObserver observer = (EventObserver) frame;
         ApplicationStartup.getEvents().removeObserver(observer);
         }
         frame.dispose();
         }*/
    }
}
