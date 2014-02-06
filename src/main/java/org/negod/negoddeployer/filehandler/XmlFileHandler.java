/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.negod.negoddeployer.filehandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.negod.negoddeployer.constants.SettingsType;
import static org.negod.negoddeployer.constants.SettingsType.COMPILE_URL;
import static org.negod.negoddeployer.constants.SettingsType.DEPLOY_URL;
import static org.negod.negoddeployer.constants.SettingsType.UNZIP;
import static org.negod.negoddeployer.constants.SettingsType.ZIP_URL;
import org.negod.negoddeployer.constants.XmlSettings;
import org.negod.negoddeployer.entity.Settings;

/**
 *
 * @author jojoha
 */
public class XmlFileHandler {

    public void saveSettings(String setting, SettingsType settingsType) {

        switch (settingsType) {
            case DEPLOY_URL:
            case ZIP_URL:
        }

    }

    public boolean fileExists() {
        return new File(XmlSettings.FILE_NAME).exists();
    }

    public Settings getSettings() {
        if (fileExists()) {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(XmlSettings.FILE_NAME);
            try {
                Document document = (Document) builder.build(xmlFile);
                Element rootNode = document.getRootElement();

                Element zipPath = rootNode.getChild(XmlSettings.ZIP_PATH);
                Element deploypath = rootNode.getChild(XmlSettings.DOMAIN_PATH);
                Element unzip = rootNode.getChild(XmlSettings.UNZIP);
                Element compilePath = rootNode.getChild(XmlSettings.COMPILE_PATH);
                Element compile = rootNode.getChild(XmlSettings.COMPILE);


                Settings setings = new Settings(
                        zipPath.getText(),
                        deploypath.getText());

                setings.setCompilePath(compilePath.getText());

                if (unzip.getText().equals(XmlSettings.YES)) {
                    setings.setUnzip(true);
                } else {
                    setings.setUnzip(false);
                }

                if (compile.getText().equals(XmlSettings.YES)) {
                    setings.setCompile(true);
                } else {
                    setings.setCompile(false);
                }

                return setings;
            } catch (IOException io) {
                System.out.println(io.getMessage());
            } catch (JDOMException jdomex) {
                System.out.println(jdomex.getMessage());
            }
            return new Settings("", "");
        } else {
            createFile();
            return new Settings("", "");
        }
    }

    public String modifySetting(String setting, SettingsType settingsType) {
        try {
            SAXBuilder builder = new SAXBuilder();
            File xmlFile = new File(XmlSettings.FILE_NAME);

            Document doc = (Document) builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            switch (settingsType) {
                case DEPLOY_URL:
                    rootNode.getChild(XmlSettings.DOMAIN_PATH).setText(setting);
                    break;
                case ZIP_URL:
                    rootNode.getChild(XmlSettings.ZIP_PATH).setText(setting);
                    break;
                case UNZIP:
                    rootNode.getChild(XmlSettings.UNZIP).setText(setting);
                    break;
                case COMPILE_URL:
                    rootNode.getChild(XmlSettings.COMPILE_PATH).setText(setting);
                    break;
                case COMPILE:
                    rootNode.getChild(XmlSettings.COMPILE).setText(setting);
                    break;
            }

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(XmlSettings.FILE_NAME));
            return setting;

        } catch (IOException io) {
            io.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void createFile() {
        try {
            Element company = new Element(XmlSettings.ROOT_NODE);
            Document doc = new Document(company);
            doc.setRootElement(company);

            Element zipPath = new Element(XmlSettings.ZIP_PATH);
            doc.getRootElement().addContent(zipPath);
            Element domainPath = new Element(XmlSettings.DOMAIN_PATH);
            doc.getRootElement().addContent(domainPath);
            Element unZip = new Element(XmlSettings.UNZIP);
            doc.getRootElement().addContent(unZip);
            Element compilePath = new Element(XmlSettings.COMPILE_PATH);
            doc.getRootElement().addContent(compilePath);
            Element compile = new Element(XmlSettings.COMPILE);
            doc.getRootElement().addContent(compile);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(XmlSettings.FILE_NAME));
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
