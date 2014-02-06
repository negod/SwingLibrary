/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.controller;

import java.util.HashMap;
import javax.swing.JPanel;
import org.negod.negoddeployer.enums.JPanels;
import org.negod.negoddeployer.interfaces.EventObserver;
import org.negod.negoddeployer.panels.CmdOutputPanel;

/**
 *
 * @author Joakim
 */
public class JPanelController {

    public JPanelController() {
    }

    private HashMap<JPanels, JPanel> panels = new HashMap<JPanels, JPanel>();

    public JPanel getJPanel(JPanels panel) {
        if (!panels.containsKey(panel)) {
            switch (panel) {
                case CMD_PANEL:
                    panels.put(panel, new CmdOutputPanel());
                    break;
            }
            initPanel(panel);
        }
        return panels.get(panel);
    }

    public void initPanel(JPanels panel) {
        if (panels.containsKey(panel)) {
            if (panels.get(panel) instanceof EventObserver) {
                EventObserver observer = (EventObserver) panels.get(panel);
                observer.registerAsObserver();
            }
        }
    }
}
