/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.interfaces;

import javax.swing.JFrame;
import org.negod.negoddeployer.enums.Views;

/**
 *
 * @author Admin
 */
public interface JFrameInterface {

    public void init();

    public void close();

    public void setVisible(boolean value);

    public void dispose();

    public JFrame getInstance();

    public void setView(Views view);

    public Views getView();

    public void unregisterJPanelsFromEvents();

}
