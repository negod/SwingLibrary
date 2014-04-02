package org.negod.negoddeployer;

import com.negod.swinglibrary.controller.Application;
import com.negod.swinglibrary.controller.ApplicationConfig;
import com.negod.swinglibrary.controller.jframes.JFrameController;
import com.negod.swinglibrary.controller.lookandfeel.ThemeTypes;
import org.negod.negoddeployer.enums.JFrames;
import org.negod.negoddeployer.jframes.MainFrame;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {

        JFrameController contr = new JFrameController(JFrames.class);
        contr.addJFrame(JFrames.MAINFRAME, MainFrame.class);

        ApplicationConfig startup = new ApplicationConfig(contr);

        Application app = new Application(startup);

        Application.getLookAndFeel().setLookAndFeel(ThemeTypes.NIMBUS.NIMBUS);
        Application.getViews().start(JFrames.MAINFRAME);

    }
}
