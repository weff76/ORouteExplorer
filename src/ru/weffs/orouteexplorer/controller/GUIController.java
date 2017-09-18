/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.controller;

import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.weffs.orouteexplorer.model.Document;
import ru.weffs.orouteexplorer.model.object.Image;
import ru.weffs.orouteexplorer.view.MainWindow;

/**
 *
 * @author dilobachev
 */
public class GUIController {

    private final MainController mainController;
    private final MainWindow mainWindow;
    
    GUIController(MainController mainController, Stage stage) {
        this.mainController = mainController;
        mainWindow = new MainWindow(mainController, stage);
    }

    void openMainWindow() {
        mainWindow.open();
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public boolean importImage(Stage parent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Map to import");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images (*.jpg, *.jpeg, *.gif, *.png)", "*.jpg", "*.JPG", "*.jpeg", "*.JPEG", "*.gif", "*.GIF", "*.png", "*.PNG"),
            new FileChooser.ExtensionFilter("All Files (*.*)", "*.*"),
            new FileChooser.ExtensionFilter("JPEG Image (*.jpg, *.jpeg)", "*.jpg", "*.JPG", "*.jpeg", "*.JPEG"),
            new FileChooser.ExtensionFilter("GIF Image (*.gif)", "*.gif", "*.GIF"),
            new FileChooser.ExtensionFilter("PNG Image (*.png)", "*.png", "*.PNG")
        );
        
        File file = fileChooser.showOpenDialog(parent);
        
        if (file != null) {
            try {
                DocumentController documentController = mainController.getDocumentController();
                Document document = documentController.getDocument();
                
                Image image = documentController.importImage(file);
            } catch (IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(parent);
                alert.setContentText("An error occured while trying to load the image: " + exception.getMessage());

                alert.showAndWait();
            }
        }
        return false;
    }
    
}
