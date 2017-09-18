/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.view;

import javafx.scene.canvas.Canvas;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author dilobachev
 */
public class ArtBoard extends Canvas {
    
    private final MainController mainController;
            
    public ArtBoard(MainController mainController, int width, int height) {
        super(width, height);
        
        this.mainController = mainController;
        
        getStyleClass().add("no-focus-outline");
    }
    
}
