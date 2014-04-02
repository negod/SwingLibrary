/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.negod.swinglibrary.filehandler;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Joakikm Johansson (joakimjohansson@outlook.com)
 */
public class FileChooser {

    private String rootFolder = "c://";

    public void changeRootFolder(String path) {
        rootFolder = path;
    }

    public String getFilePath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setCurrentDirectory(new File(rootFolder));

        switch (chooser.showOpenDialog(null)) {
            case JFileChooser.APPROVE_OPTION:
                File f = chooser.getSelectedFile();
                if (f.isFile()) {
                    return chooser.getSelectedFile().getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(null, "This is not a file!");
                    return "";
                }
        }
        return "";
    }

    public String getFolderPath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File(rootFolder));
        chooser.setVisible(true);

        switch (chooser.showOpenDialog(null)) {
            case JFileChooser.APPROVE_OPTION:
                File f = chooser.getSelectedFile();
                if (f.isDirectory()) {
                    return chooser.getSelectedFile().getAbsolutePath();
                } else {
                    JOptionPane.showMessageDialog(null, "This is not a directory!");
                    return "";
                }
        }
        return "";
    }
}
