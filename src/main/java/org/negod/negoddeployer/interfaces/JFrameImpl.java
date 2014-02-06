/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.interfaces;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.negod.negoddeployer.controller.Application;
import org.negod.negoddeployer.enums.JFrames;
import org.negod.negoddeployer.enums.JPanels;
import org.negod.negoddeployer.enums.ViewStates;

/**
 *
 * @author Admin
 */
public class JFrameImpl extends javax.swing.JFrame implements NegodJFrame {

    private ViewStates viewState;
    private Object data;
    private boolean isMainFrame = false;
    private JFrames jFrame;
    private HashMap<JPanels, JPanel> panels = new HashMap<JPanels, JPanel>();

    public JFrameImpl(Object data) {
        if (data == null) {
            viewState = ViewStates.NEW;
        } else {
            viewState = ViewStates.EDIT;
            this.data = data;
        }

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                NegodJFrame frame = (NegodJFrame) e.getSource();
                Application.getViews().closeFrame(frame);
            }
        });
    }

    public Object getData() {
        return data;
    }

    /**
     * Is the data in this Jframe to be saves as new or Updated
     *
     * @return
     */
    public boolean isNew() {
        switch (viewState) {
            case NEW:
                return true;
            case EDIT:
                return false;
            default:
                return true;
        }
    }

    @Override
    public void close() {
        this.dispose();
    }

    /**
     * Override this to init your app, this MUST be overrided!!
     */
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JFrame getInstance() {
        return this;
    }

    @Override
    public void setView(JFrames jFrame) {
        this.jFrame = jFrame;
    }

    @Override
    public JFrames getJFrame() {
        return jFrame;
    }

    @Override
    public void setTitle(JFrames jFrame) {
        super.setTitle(jFrame.getJFrameTitle());
    }

    @Override
    public HashMap<JPanels, JPanel> getJPanels() {
        return panels;
    }
}
