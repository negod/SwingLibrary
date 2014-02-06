package org.negod.negoddeployer;

import org.negod.negoddeployer.controller.Application;
import org.negod.negoddeployer.enums.JFrames;
import org.negod.negoddeployer.enums.ThemeTypes;


/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        Application controller = new Application();
        Application.getLookAndFeel().setLookAndFeel(ThemeTypes.NIMBUS);
        Application.getViews().startFrame(JFrames.MainFrame, null);

    }
}
