/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Translate;

/**
 *
 * @author dilobachev
 */
public class OTrackPointer extends Circle {

    private final OTrack oTrack;

    private Point2D translatePoint = new Point2D(0.0, 0.0);

    public OTrackPointer(OTrack oTrack) {
        super(4.0, Color.TRANSPARENT);

        setMouseTransparent(true);
        this.oTrack = oTrack;
    }

    public OTrack getOTrack() {
        return oTrack;
    }

    public Point2D getTranslatePoint() {
        return translatePoint;
    }
    
    public void setTranslatePoint(Point2D point) {
        translatePoint = point;
        tranformPointer();
    }

    public void setOTrackPointer(double x, double y) {
        
        setCenterX(x);
        setCenterY(y);
        setFill(Color.RED);
    }

    public void hideOTrackPointer() {
        setFill(Color.TRANSPARENT);
    }

    private void tranformPointer() {
        this.getTransforms().clear();
        this.getTransforms().add(new Translate(translatePoint.getX(), translatePoint.getY()));
    }
}

