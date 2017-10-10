/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author dilobachev
 */
public class OTrackSegment extends Path {

    private final OTrack oTrack;

    private final int fromIndex;
    private final int toIndex;

    private Point2D pivotPoint;
    private double rotateAngle;

    private Point2D translatePoint;

    private boolean isChanged;

    public OTrackSegment(OTrack oTrack, int fromIndex, int toIndex) {
        super();

        this.oTrack = oTrack;

        this.fromIndex = fromIndex;
        this.toIndex = toIndex;

        this.rotateAngle = 0.0;
        this.translatePoint = new Point2D(0.0, 0.0);

        // Setup path
        this.setStrokeWidth(2.0);
        this.setStroke(Color.BLUE);
        this.setOpacity(0.75);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        
        isChanged = true;
    }

    public OTrackSegment getPathSegment() {
        if (isChanged) {
            ArrayList<Point2D> coordFlat = oTrack.getCoordFlat();

            this.getElements().clear();
            for (int i = fromIndex; i <= toIndex; i++) {
                if (i == fromIndex) {
                    this.getElements().add(new MoveTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
                } else {
                    this.getElements().add(new LineTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
                }
            }
        }
        return this;
    }

}
