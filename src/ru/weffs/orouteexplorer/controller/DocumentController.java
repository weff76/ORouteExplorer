/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.controller;

import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import javafx.scene.Node;
import ru.weffs.orouteexplorer.model.Document;
import ru.weffs.orouteexplorer.model.GUIState;
import ru.weffs.orouteexplorer.model.object.Image;
import ru.weffs.orouteexplorer.model.object.ORoute;
import ru.weffs.orouteexplorer.view.MainScene;

/**
 *
 * @author dilobachev
 */
public class DocumentController {
    
    private MainController mainController;
    private Document document;
    private MainScene mainScene;
    
    public DocumentController(MainController mainController) {
        this.mainController = mainController;
        mainScene = mainController.getGUIController().getMainWindow().getMainScene();
    }
    
    public Document getDocument() {
        if (document==null) {
            createEmptyDocument();
            document.registerObserver(mainController.getGUIController().getMainWindow().getMainScene());
            document.registerObserver(mainController.getGUIController().getMainWindow().getMainScene().getMenuBar());

            GUIState guiState = mainController.getGUIController().getGuiState();
            guiState.registerObserver(mainController.getGUIController().getMainWindow().getMainScene());
            guiState.registerObserver(mainController.getGUIController().getMainWindow().getMainScene().getMenuBar());
        }
        return document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    public void addObject(Node object) {
        if (object instanceof Image) {
            document.addMap((Image) object);
        }
        
        if (object instanceof ORoute) {
            document.addRoute((ORoute) object);
        }
    }

    public Image importImage(File file) throws IOException {
        javafx.scene.image.Image img = new javafx.scene.image.Image(Files.newInputStream(file.toPath()));
        
        Image image = new Image();
        image.setImage(img);
        image.setX(0);
        image.setY(0);
        image.setFitWidth(img.getWidth());
        image.setFitHeight(img.getHeight());
        
        addObject(image);
        setDimensions(img.getWidth(), img.getHeight());
        
        return image;
    }

    void createEmptyDocument() {
        document = new Document(mainController);
        mainScene.activateControls(true);
    }

    private void setDimensions(double width, double height) {
        document.setDimensions(width, height);
    }

    ORoute importGPX(File file) throws Exception {

        GPXParser p = new GPXParser();
        GPX gpx = p.parseGPX(new FileInputStream(file.getPath()));                
        
        ORoute oRoute = new ORoute(gpx, document.getWidth(), document.getHeight());
        addObject(oRoute);
        
        return oRoute;
    }
    
}
