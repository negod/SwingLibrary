/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.progressbar;

import com.negod.genericlibrary.dto.Dto;
import com.negod.swinglibrary.controller.Application;
import com.negod.swinglibrary.controller.events.EventObserver;
import com.negod.swinglibrary.controller.events.NegodEvent;
import com.negod.swinglibrary.controller.events.global.CmdExecuterEvent;
import com.negod.swinglibrary.controller.events.global.ProgressBarEvent;
import com.negod.swinglibrary.controller.events.global.ZipFileEvent;
import com.negod.swinglibrary.filehandler.FileUnZipper;

/**
 *
 * @author jojoha
 */
public class NegodProgressBar extends javax.swing.JPanel implements EventObserver {

    /**
     * Creates new form TaskProgressBar
     */
    public NegodProgressBar() {
        initComponents();
        jLabel1.setText("");
    }

    public enum NegodProgressBarData {

        PROGRESS_VALUE, PROGRESS_TEXT, MIN_VALUE, MAX_VALUE;
    }

    @Override
    public void registerAsObserver() {
        Application.getEvents().addObserver(this);
    }

    public void setProgress(Dto<NegodProgressBarData> value) {
        jProgressBar1.setValue(value.<Integer>get(NegodProgressBarData.PROGRESS_VALUE));
    }

    public void setText(String data) {
        jLabel1.setText(data);
    }

    public void setMinValue(Dto<NegodProgressBarData> value) {
        jProgressBar1.setMinimum(value.<Integer>get(NegodProgressBarData.MIN_VALUE));
    }

    public void setMaxValue(Dto<NegodProgressBarData> value) {
        jProgressBar1.setMaximum(value.<Integer>get(NegodProgressBarData.MAX_VALUE));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(NegodEvent event) {
        if (event.equalsEvent(ProgressBarEvent.CHANGE_PROGRESS)) {
            Dto<NegodProgressBarData> dto = event.getValues();
            setProgress(dto);
            setText(dto.<String>get(NegodProgressBarData.PROGRESS_TEXT));
        } else if (event.equalsEvent(ProgressBarEvent.SET_MIN_MAX_VALUES)) {
            Dto<NegodProgressBarData> dto = event.getValues();
            setMinValue(dto);
            setMaxValue(dto);
        } else if (event.equalsEvent(CmdExecuterEvent.CMD_COMMAND_EXECUTED_SUCCESS)) {
            Dto<NegodProgressBarData> dto = event.getValues();
            setMinValue(dto);
            setMaxValue(dto);
        } else if (event.equalsEvent(ZipFileEvent.FILE_UNZIPPING_IN_PROGRESS)) {
            Dto<FileUnZipper.FileUnzipData> dto = event.getValues();
            setText(dto.<String>get(FileUnZipper.FileUnzipData.MESSAGE));
        }
    }
}
