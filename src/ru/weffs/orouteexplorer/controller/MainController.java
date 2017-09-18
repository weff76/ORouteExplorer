/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.controller;

import javafx.stage.Stage;
import ru.weffs.orouteexplorer.ORouteExplorer;

/**
 *
 * @author dilobachev
 */
public class MainController {

    private final GUIController guiController;
    private final DocumentController documentController;
    
    public MainController(Stage stage) {
        guiController = new GUIController(this, stage);
        documentController = new DocumentController(this);

        guiController.openMainWindow();
    }

    public void exit() {
        ORouteExplorer.exit();
    }

    public GUIController getGUIController() {
        return guiController;
    }

    public DocumentController getDocumentController() {
        return documentController;
    }
    
}
