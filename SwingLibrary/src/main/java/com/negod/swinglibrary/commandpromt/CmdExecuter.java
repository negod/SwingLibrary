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
    private static final String EXECUTED_COMMAND_SUCCESSFUL = "Command executed successfully!";
    private static final String EXECUTED_COMMAND_FAILURE = "Command execution failed!";

    public enum CmdExecuterData {

        LINE;
    }

    public void ExecuteCommand(ProcessBuilder builder) {
        new CommandLineExecuter(builder).start();
    }

    private class CommandLineExecuter extends Thread {

        ProcessBuilder builder;
        Dto<NegodProgressBarData> progressBarData = new Dto(NegodProgressBarData.class);
        Dto<CmdExecuterData> cmdData = new Dto(CmdExecuterData.class);

        public CommandLineExecuter(ProcessBuilder builder) {
            this.builder = builder;
        }

        @Override
        public void run() {

            notifySetMinMaxProgressValue(0, 100);
            notifyProgress(EXECUTING_COMMAND, 5);

            try {

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
                    notifyProgress(EXECUTING_COMMAND, counter + 5);
                    notifyNewLine(line);
                }

                notifyProgress(EXECUTED_COMMAND_SUCCESSFUL, 100);
                notifySuccess(EXECUTED_COMMAND_SUCCESSFUL);

            } catch (IOException ex) {
                notifyProgress(EXECUTED_COMMAND_FAILURE, 100);
                notifyFailure(EXECUTED_COMMAND_FAILURE);
                Logger.getLogger(CmdExecuter.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    this.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CommandLineExecuter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void notifySuccess(String message) {
            cmdData.set(CmdExecuterData.LINE, message);
            Application.getEvents().notifyObservers(CmdExecuterEvent.CMD_COMMAND_EXECUTED_SUCCESS, cmdData);
        }

        private void notifyFailure(String message) {
            cmdData.set(CmdExecuterData.LINE, message);
            Application.getEvents().notifyObservers(CmdExecuterEvent.CMD_COMMAND_EXECUTED_FAILED, cmdData);
        }

        private void notifyNewLine(String message) {
            cmdData.set(CmdExecuterData.LINE, message);
            Application.getEvents().notifyObservers(CmdExecuterEvent.NEW_LINE, cmdData);
        }

        private void notifyProgress(String message, Integer progressValue) {
            progressBarData.set(NegodProgressBarData.PROGRESS_TEXT, message);
            progressBarData.set(NegodProgressBarData.PROGRESS_VALUE, progressValue);
            Application.getEvents().notifyObservers(ProgressBarEvent.CHANGE_PROGRESS, progressBarData);
        }

        private void notifySetMinMaxProgressValue(Integer min, Integer max) {
            progressBarData.set(NegodProgressBarData.MIN_VALUE, 0);
            progressBarData.set(NegodProgressBarData.MAX_VALUE, 100);
            Application.getEvents().notifyObservers(ProgressBarEvent.SET_MIN_MAX_VALUES, progressBarData);
        }
    }
}
