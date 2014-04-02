/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.commandpromt;

import com.negod.genericlibrary.dto.Dto;
import com.negod.swinglibrary.controller.Application;
import com.negod.swinglibrary.controller.events.NegodEvent;
import com.negod.swinglibrary.controller.events.global.CmdExecuterEvent;
import com.negod.swinglibrary.controller.events.global.ProgressBarEvent;
import com.negod.swinglibrary.progressbar.NegodProgressBar.NegodProgressBarData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jojoha
 */
public class CmdExecuter {

    private static final String EXECUTING_COMMAND = "Executing command";
    private static final String EXECUTED_COMMAND_SUCCESSFUL = "Command ececuted successfully!";
    private static final String EXECUTED_COMMAND_FAILURE = "Command execution failed!";

    public enum CmdExecuterData {

        LINE;
    }

    public void ExecuteCommand(ProcessBuilder builder) {
        new CommandLineExecuter(builder).start();
    }

    private class CommandLineExecuter extends Thread {

        ProcessBuilder builder;

        public CommandLineExecuter(ProcessBuilder builder) {
            this.builder = builder;
        }

        @Override
        public void run() {

            Dto<NegodProgressBarData> progressBarData = new Dto(NegodProgressBarData.class);
            progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, EXECUTING_COMMAND);
            progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, 5);
            progressBarData.set(NegodProgressBarData.MIN_VALUE, 0);
            progressBarData.set(NegodProgressBarData.MAX_VALUE, 100);

            Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.SET_MIN_MAX_VALUES, progressBarData));
            Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarData));

            try {
                Dto<CmdExecuterData> cmdData = new Dto(CmdExecuterData.class);
                builder.redirectErrorStream(true);
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line;
                while ((line = r.readLine()) != null) {
                    cmdData.set(CmdExecuterData.LINE, line);
                    if (cmdData.getValue(CmdExecuterData.LINE).isNull()) {
                        break;
                    }

                    if (progressBarData.<Integer>get(NegodProgressBarData.PROGRESS_VALUE) + 5 > 100) {
                        progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, 5);
                    }

                    Integer counter = progressBarData.<Integer>get(NegodProgressBarData.PROGRESS_VALUE);
                    progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, counter + 5);
                    progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, cmdData.<String>get(CmdExecuterData.LINE));
                    Application.getEvents().notifyObservers(new NegodEvent(ProgressBarEvent.CHANGE_PROGRESS, progressBarData));
                }

                progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, 100);
                progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, EXECUTED_COMMAND_SUCCESSFUL);
                Application.getEvents().notifyObservers(new NegodEvent(CmdExecuterEvent.CMD_COMMAND_EXECUTED_SUCCESS, progressBarData));

            } catch (IOException ex) {
                progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, 100);
                progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, EXECUTED_COMMAND_FAILURE);
                Application.getEvents().notifyObservers(new NegodEvent(CmdExecuterEvent.CMD_COMMAND_EXECUTED_FAILED, progressBarData));
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
