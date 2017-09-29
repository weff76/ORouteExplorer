/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import javafx.scene.image.ImageView;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author dilobachev
 */
public class OMap extends ImageView {
    
    private final MainController mainController;
    
    public OMap(MainController mainController) {
        super();
        
        this.mainController = mainController;
    }
    
}
