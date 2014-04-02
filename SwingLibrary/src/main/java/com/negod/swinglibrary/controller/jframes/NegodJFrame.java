/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.controller.jframes;

import com.negod.swinglibrary.controller.Application;
import com.negod.swinglibrary.controller.events.EventObserver;
import com.negod.swinglibrary.controller.events.NegodEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public abstract class NegodJFrame extends javax.swing.JFrame implements EventObserver {

    public NegodJFrame() {
    }

    @Override
    public void setDefaultCloseOperation(int i) {
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                NegodJFrame frame = (NegodJFrame) e.getSource();
                Application.getViews().close(frame);
            }
        });
    }

    void close() {
        this.dispose();
    }

    @Override
    public void registerAsObserver() {
        Application.getEvents().addObserver(this);
    }

    public JFrame getInstance() {
        return this;
    }

    public abstract void init();
}
