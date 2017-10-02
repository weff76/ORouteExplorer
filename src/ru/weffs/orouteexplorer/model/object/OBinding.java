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
public class OBinding extends Circle {
 
    private final ORoute oRoute;
    private int index;
    private double x;
    private double y;
    
    private OBinding shadowBinding;
    
    public OBinding(ORoute oRoute) {
        super(3.0, Color.TRANSPARENT);
        
        this.oRoute = oRoute;
    }
    
    public OBinding(ORoute oRoute, int index) {
        super(3.0, Color.BLUE);
        
//        setMouseTransparent(true);
        this.oRoute = oRoute;
        this.index = index;
        
        x = oRoute.getOTrack().getTrackFlatCoords().get(index).getX();
        y = oRoute.getOTrack().getTrackFlatCoords().get(index).getY();
        
        this.setCenterX(x);
        this.setCenterY(y);
        
        shadowBinding = new OBinding(oRoute);
        shadowBinding.setCenterX(x);
        shadowBinding.setCenterY(y);
        shadowBinding.setStrokeWidth(20.0);
    }
    
}
