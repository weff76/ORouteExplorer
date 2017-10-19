/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.Track;
import com.hs.gpxparser.modal.TrackSegment;
import com.hs.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.eventhandler.OTrackSegmentMEHandler;

/**
 *
 * @author dilobachev
 */
public class OTrack {

    //    private static final double EARTH_RADIUS = 6371;
    private final ArrayList<Point2D> coordSpherical;
    private final ArrayList<Point2D> coordFlat;
    private final ArrayList<OTrackSegment> oTrackSegments;
    private final ArrayList<OShadowSegment> oShadowSegments;

    private final OTrackPointer oTrackPointer;
    private final OBindingPointer oBindingPointer;

    public OTrack(GPX gpx) {
        coordSpherical = new ArrayList<>();
        coordFlat = new ArrayList<>();
        oTrackSegments = new ArrayList<>();
        oShadowSegments = new ArrayList<>();

        oTrackPointer = new OTrackPointer(this);
        oBindingPointer = new OBindingPointer(this);

        setTrackData(gpx);
        setNewTrackSegment(null, 0, coordFlat.size() - 1);
    }

    public final int setNewTrackSegment(OTrackSegment sourceSegment, int fromIndex, int toIndex) {
        OTrackSegment newSegment = new OTrackSegment(this, fromIndex, toIndex);
        int index = 0;
        if (sourceSegment == null) {
            oTrackSegments.add(newSegment);
            oShadowSegments.add(newSegment.getOShadowSegment());
        } else {
            newSegment.setBindLeft(true);
//            newSegment.setPivotPoint(coordFlat.get(fromIndex));
            newSegment.setTranslatePoint(sourceSegment.getTranslatePoint());

            index = oTrackSegments.indexOf(sourceSegment) + 1;
            if (index < oTrackSegments.size()) {
                newSegment.setBindRight(true);
            }
            oTrackSegments.add(index, newSegment);
            oShadowSegments.add(index, newSegment.getOShadowSegment());
            copyEvents(sourceSegment.getOShadowSegment(), newSegment.getOShadowSegment());
        }
        return index;
    }

    public int splitTrackSegment(int bindingIndex, OTrackSegment sourceSegment) {
        int index = setNewTrackSegment(sourceSegment, bindingIndex, sourceSegment.getToIndex());

        sourceSegment.setToIndex(bindingIndex);
        sourceSegment.setBindRight(true);
//        sourceSegment.setPivotPoint(oTrack.getCoordFlat().get(index));
        sourceSegment.setPath();

        return index - 1;
    }

    public void processBinding(int segmentIndex, Point2D point) {

        OTrackSegment leftSegment = oTrackSegments.get(segmentIndex);
        OTrackSegment rightSegment = oTrackSegments.get(segmentIndex + 1);

        // Move track
        if (!leftSegment.getBindLeft() && !rightSegment.getBindRight()) {
            leftSegment.setTranslatePoint(leftSegment.getTranslatePoint().add(point));
            rightSegment.setTranslatePoint(rightSegment.getTranslatePoint().add(point));
            oTrackPointer.setTranslatePoint(oTrackPointer.getTranslatePoint().add(point));
        }
    }

    private void setTrackData(GPX gpx) {
        if (!gpx.getTracks().isEmpty()) {
            gpx.getTracks().forEach((Track track) -> {
                track.getTrackSegments().forEach((TrackSegment trackSegment) -> {
                    trackSegment.getWaypoints().forEach((Waypoint waypoint) -> {
                        double radLongitude = Math.toRadians(waypoint.getLongitude());
                        double radLatitude = Math.toRadians(90 - waypoint.getLatitude());
                        coordSpherical.add(new Point2D(
                                (double) radLongitude,
                                (double) radLatitude)
                        );
                        coordFlat.add(new Point2D(
                                (double) Math.cos(radLongitude) * Math.sin(radLatitude),
                                (double) Math.sin(radLongitude) * Math.sin(radLatitude)));
                    });
                });
            });
            adaptTrackDataToScreen();
        }
    }

    private void adaptTrackDataToScreen() {

        double minFlatX = 0.0;
        double minFlatY = 0.0;
        double maxFlatX = 0.0;
        double maxFlatY = 0.0;

        for (Point2D point2D : coordFlat) {
            if (minFlatX > point2D.getX() || minFlatX == 0.0) {
                minFlatX = point2D.getX();
            }
            if (minFlatY > point2D.getY() || minFlatY == 0.0) {
                minFlatY = point2D.getY();
            }
            if (maxFlatX < point2D.getX() || maxFlatX == 0.0) {
                maxFlatX = point2D.getX();
            }
            if (maxFlatY < point2D.getY() || maxFlatY == 0.0) {
                maxFlatY = point2D.getY();
            }
        }

        double resizeCoeff;

        double routeWidth = maxFlatX - minFlatX;
        double routeHeight = maxFlatY - minFlatY;

        if (routeWidth <= routeHeight) {
            resizeCoeff = 768.0 / routeHeight;
        } else {
            if (routeWidth / routeHeight <= 0.75) {
                resizeCoeff = 1024.0 / routeWidth;
            } else {
                resizeCoeff = 768.0 / routeHeight;
            }
        }

        Point2D subtractPoint = new Point2D(minFlatX, minFlatY);
        for (int i = 0; i < coordFlat.size(); i++) {
            coordFlat.set(i, coordFlat.get(i).subtract(subtractPoint).multiply(resizeCoeff));
        }
    }

    public ArrayList<Point2D> getCoordFlat() {
        return coordFlat;
    }

    public ArrayList<OTrackSegment> getOTrackSegments() {
        return oTrackSegments;
    }

    public ArrayList<OShadowSegment> getOShadowSegments() {
        return oShadowSegments;
    }

    public void setEvents(MainController mainController) {
        OTrackSegmentMEHandler oTrackSegmentMEHandler = new OTrackSegmentMEHandler(mainController);
        oShadowSegments.forEach((OShadowSegment oShadowSegment) -> {
            oShadowSegment.setOnMouseMoved(oTrackSegmentMEHandler.getMouseMoveEventHandler());
            oShadowSegment.setOnMousePressed(oTrackSegmentMEHandler.getMousePressedEventHandler());
            oShadowSegment.setOnMouseReleased(oTrackSegmentMEHandler.getMouseReleasedEventHandler());
            oShadowSegment.setOnMouseDragged(oTrackSegmentMEHandler.getMouseDraggedEventHandler());
        });
    }

    private void copyEvents(OShadowSegment source, OShadowSegment target) {
        target.setOnMouseMoved(source.getOnMouseMoved());
        target.setOnMousePressed(source.getOnMousePressed());
        target.setOnMouseReleased(source.getOnMouseReleased());
        target.setOnMouseDragged(source.getOnMouseDragged());
    }

    public OTrackPointer getOTrackPointer() {
        return oTrackPointer;
    }

    public OBindingPointer getOBindingPointer() {
        return oBindingPointer;
    }

    public void setOBindingPointer(double x, double y) {
        oBindingPointer.setCenterX(x);
        oBindingPointer.setCenterY(y);
        oBindingPointer.setStroke(Color.ORANGE);
    }

    public void hideOBindingPointer() {
        oBindingPointer.setStroke(Color.TRANSPARENT);
    }

//    public final void multiplyAll(double scaleCoeff) {
//        multiply(0, coordFlat.size(), scaleCoeff);
//    }
//
//    public final void multiply(int fromIndex, int size, double scaleCoeff) {
//        for (int i = fromIndex; i < fromIndex + size; i++) {
//            coordFlat.set(i, coordFlat.get(i).multiply(scaleCoeff));
//        }
//    }
//
//    private double getSphericalDistance(Point2D point1, Point2D point2) {
//        return EARTH_RADIUS * Math.acos(
//                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
//                + Math.cos(point1.getY()) * Math.cos(point2.getY())
//        );
//    }
}
