/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.interfaces;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.negod.negoddeployer.enums.JFrames;
import org.negod.negoddeployer.enums.JPanels;

/**
 *
 * @author Admin
 */
public interface NegodJFrame {

    public void init();

    public void close();

    public void setVisible(boolean value);

    public void dispose();

    public JFrame getInstance();

    public void setView(JFrames jFrame);

    public JFrames getJFrame();

    public HashMap<JPanels, JPanel> getJPanels();

    public String getTitle();

    public void setTitle(JFrames jFrame);

}
