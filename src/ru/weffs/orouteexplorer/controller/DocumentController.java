/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javafx.scene.Node;
import ru.weffs.orouteexplorer.model.Document;
import ru.weffs.orouteexplorer.model.object.Image;
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
        }
        return document;
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    public void addObject(Node object) {
        document.addObject(object);
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
        
        return image;
    }

    void createEmptyDocument() {
        document = new Document(mainController);
        setDimensions(100, 100);
    }

    public void setDimensions(int width, int height) {
        document.setDimensions(width, height);
        mainScene.setArtBoard(document.getWidth(), document.getHeight());        
    }
}
