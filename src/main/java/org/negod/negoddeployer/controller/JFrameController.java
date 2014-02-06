/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.controller;

import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.negod.negoddeployer.enums.JFrames;
import org.negod.negoddeployer.interfaces.EventObserver;
import org.negod.negoddeployer.interfaces.NegodJFrame;
import org.negod.negoddeployer.jframes.MainFrame;

/**
 *
 * @author Joakim
 */
public class JFrameController {

    private HashMap<JFrames, NegodJFrame> views = new HashMap<JFrames, NegodJFrame>();

    public JFrameController() {
    }

    public void startFrame(final JFrames jFrame, final Object data) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NegodJFrame frame = null;
                if (!views.containsKey(jFrame)) {
                    switch (jFrame) {
                        case MainFrame:
                            frame = new MainFrame(data);
                            break;
                    }
                    views.put(jFrame, frame);
                    frame.setView(jFrame);
                    frame.setTitle(jFrame);
                    initFrame(frame);
                }
            }
        });
    }

    private void initFrame(NegodJFrame frame) {
        frame.init();
        frame.setVisible(true);
        if (frame instanceof EventObserver) {
            EventObserver observer = (EventObserver) frame;
            observer.registerAsObserver();
        }
    }

    public void closeFrame(NegodJFrame frame) {
        if (frame.getJFrame() == JFrames.MainFrame) {
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to exit the application?",
                    "Exit Application",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                Application.shutDown();
            }
        } else {
            if (frame instanceof EventObserver && frame.getJFrame() != JFrames.MainFrame) {
                EventObserver observer = (EventObserver) frame;
                Application.getEvents().removeObserver(observer);
            }
            frame.dispose();
            views.remove(frame.getJFrame());
        }
    }
}
