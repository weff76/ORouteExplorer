/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author dilobachev
 */
public class OBindingPointer extends Circle {
 
    private final ORoute oRoute;
    
    public OBindingPointer(ORoute oRoute) {
        super(6.0, Color.TRANSPARENT);
        
        setMouseTransparent(true);
        this.oRoute = oRoute;
    }
    
}
