/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author dilobachev
 */
public class OShadowSegment extends Path {

    private final OTrackSegment oTrackSegment;

    public OShadowSegment(OTrackSegment oTrackSegment) {
        super();
        this.oTrackSegment = oTrackSegment;
        setPath();
    }

    private void setPath() {
        this.setStrokeWidth(25.0);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        this.getElements().addAll(oTrackSegment.getElements());
        this.setStroke(Color.TRANSPARENT);
//        this.setStroke(Color.MAGENTA);
//        this.setOpacity(0.75);
    }

    public OTrackSegment getOTrackSegment() {
        return oTrackSegment;
    }            

}
