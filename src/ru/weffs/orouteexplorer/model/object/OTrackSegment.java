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
import javafx.scene.transform.Translate;

/**
 *
 * @author dilobachev
 */
public class OTrackSegment extends Path {

    private final OTrack oTrack;

    private OShadowSegment oShadowSegment;

    private int fromIndex;
    private int toIndex;

    private boolean bindLeft = false;
    private boolean bindRight = false;

    private Point2D pivotPoint = new Point2D(0.0, 0.0);
    private double rotateAngle = 0.0;

    private Point2D translatePoint = new Point2D(0.0, 0.0);

    public OTrackSegment(OTrack oTrack, int fromIndex, int toIndex) {
        super();

        this.oTrack = oTrack;

        this.fromIndex = fromIndex;
        this.toIndex = toIndex;

        setPath();
    }

    public boolean splitSegment(int index) {
        if (fromIndex < index) {
            oTrack.setNewTrackSegment(this, index, toIndex);

            toIndex = index;
            setBindRight(true);
            setPivotPoint(oTrack.getCoordFlat().get(index));
            setPath();

            return true;
        }
        return false;
    }

    protected void setPath() {
        this.setStrokeWidth(3.0);
        this.setStroke(Color.BLUE);
        this.setOpacity(0.75);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        this.setMouseTransparent(true);

        ArrayList<Point2D> coordFlat = oTrack.getCoordFlat();

        this.getElements().clear();
        for (int i = fromIndex; i <= toIndex; i++) {
            if (i == fromIndex) {
                this.getElements().add(new MoveTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
            } else {
                this.getElements().add(new LineTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
            }
        }
        if (oShadowSegment == null) {
            oShadowSegment = new OShadowSegment(this);
        } else {
            oShadowSegment.setPath();
        }
    }

    public void transformPath() {
        this.getTransforms().clear();
        oShadowSegment.getTransforms().clear();
        if (!translatePoint.equals(Point2D.ZERO)) {
            this.getTransforms().add(new Translate(translatePoint.getX(), translatePoint.getY()));
            oShadowSegment.getTransforms().add(new Translate(translatePoint.getX(), translatePoint.getY()));
        }
    }

    public int getNearestToPoint(double x, double y) {
        double deltaX;
        double deltaY;
        int index = -1;

        double minDistance = 10.0;
        ArrayList<Point2D> coordFlat = getOTrack().getCoordFlat();

        for (int i = fromIndex; i <= toIndex; i++) {
            deltaX = coordFlat.get(i).getX() - x;
            deltaY = coordFlat.get(i).getY() - y;
            if (Math.abs(deltaX) < minDistance && Math.abs(deltaY) < minDistance) {
                double currDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
                if (currDistance < minDistance) {
                    minDistance = currDistance;
                    index = i;
                }
            }
        }
        return index;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int index) {
        fromIndex = index;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int index) {
        toIndex = index;
    }

    public double getRotateAngle() {
        return rotateAngle;
    }

    public Point2D getPivotPoint() {
        return pivotPoint;
    }

    public final void setPivotPoint(Point2D point) {
        pivotPoint = point;
    }

    public Point2D getTranslatePoint() {
        return translatePoint;
    }

    public final void setTranslatePoint(Point2D point) {
        translatePoint = point;
    }

    public OShadowSegment getOShadowSegment() {
        return oShadowSegment;
    }

    public OTrack getOTrack() {
        return oTrack;
    }

    public boolean getBindLeft() {
        return bindLeft;
    }

    public final void setBindLeft(boolean state) {
        bindLeft = state;
    }

    public boolean getBindRight() {
        return bindRight;
    }

    public final void setBindRight(boolean state) {
        bindRight = state;
    }

}
