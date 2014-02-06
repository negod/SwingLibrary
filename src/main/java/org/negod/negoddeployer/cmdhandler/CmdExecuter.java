/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.cmdhandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.negod.negoddeployer.controller.Application;
import org.negod.negoddeployer.enums.EventTypes;
import org.negod.negoddeployer.filehandler.FileUnZipper;
import org.negod.negoddeployer.panels.CmdOutputPanel;

/**
 *
 * @author jojoha
 */
public class CmdExecuter {

    Application app;

    public CmdExecuter(Application app) {
        this.app = app;
    }

    public void ExecuteCommand(ProcessBuilder builder) {
        new CommandLineExecuter(app, builder).start();
    }

    private class CommandLineExecuter extends Thread {

        Application app;
        ProcessBuilder builder;

        public CommandLineExecuter(Application app, ProcessBuilder builder) {
            this.app = app;
            this.builder = builder;
        }

        @Override
        public void run() {

            int counter = 5;

            app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_TEXT, "Executing command");
            try {
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while (true) {
                    line = r.readLine();
                    if (line == null) {
                        break;
                    }
                    app.getEvents().notifyObservers(EventTypes.ADD_CMD_TEXT, line);

                    if (counter + 5 > 100) {
                        counter = 5;
                    }

                    app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, counter += 5);
                }
                app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 100);
                app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_TEXT, "Command ececuted successfully!");
                app.getEvents().notifyObservers(EventTypes.CMD_COMMAND_EXECUTED_SUCCESS, null);
            } catch (IOException ex) {
                Logger.getLogger(CmdExecuter.class.getName()).log(Level.SEVERE, null, ex);
                Application.getEvents().notifyObservers(EventTypes.CMD_COMMAND_EXECUTED_FAILURE, null);
                app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_BAR, 100);
                app.getEvents().notifyObservers(EventTypes.CHANGE_PROGRESS_TEXT, "Command execution failed!");
                Logger.getLogger(CmdExecuter.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    this.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommandLineExecuter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
