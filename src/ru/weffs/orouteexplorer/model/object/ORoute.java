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
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import ru.weffs.orouteexplorer.eventhandler.ORouteEventHandler;

/**
 *
 * @author dilobachev
 */
public class ORoute extends Path {

    private static final double EARTH_RADIUS = 6371;

    private double resizeCoeff = 1.0;

    private final ArrayList<Point2D> coordSperical;
    private final ArrayList<Point2D> coordFlat;

    private final Circle trackPoint;
    private boolean showTrackPoint;

    public ORoute(GPX gpx) {
        super();

        coordSperical = new ArrayList<>();
        coordFlat = new ArrayList<>();

        trackPoint = new Circle(5.0, Color.RED);

        loadTrackData(gpx);
    }

    private void loadTrackData(GPX gpx) {
        if (!gpx.getTracks().isEmpty()) {
            gpx.getTracks().forEach((Track track) -> {
                track.getTrackSegments().forEach((TrackSegment trackSegment) -> {
                    trackSegment.getWaypoints().forEach((Waypoint waypoint) -> {
                        double radLongitude = Math.toRadians(waypoint.getLongitude());
                        double radLatitude = Math.toRadians(90 - waypoint.getLatitude());
                        coordSperical.add(new Point2D(
                                (double) radLongitude,
                                (double) radLatitude)
                        );
                        coordFlat.add(new Point2D(
                                (double) Math.cos(radLongitude) * Math.sin(radLatitude),
                                (double) Math.sin(radLongitude) * Math.sin(radLatitude)));
                    });
                });
            });
            calcResizeCoeff();
            adjustTrackData();
            buildPath();
        }
    }

    private void adjustTrackData() {
        coordFlat.forEach((Point2D point2D) -> {
            point2D.multiply(resizeCoeff);
        });
    }

    private void calcResizeCoeff() {
        
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
        double routeWidth = maxFlatX - minFlatX;
        double routeHeight = maxFlatY - minFlatY;
        if (routeWidth / routeHeight <= 0.75) {
            resizeCoeff = 1024.0 / routeWidth;
        } else {
            resizeCoeff = 768.0 / routeHeight;
        }
    }

    private void buildPath() {
        mapPlainCoords.forEach((Point2D point2D) -> {
            if (mapPlainCoords.indexOf(point2D) == 0) {
                getElements().add(new MoveTo(point2D.getX(), point2D.getY()));
            } else {
                getElements().add(new LineTo(point2D.getX(), point2D.getY()));
                setStrokeWidth(1.0);
                setStroke(Color.RED);
                setStrokeLineCap(StrokeLineCap.ROUND);
//                setEffect(new DropShadow(10, Color.RED));
            }
        });

//        addEventHandler(MouseEvent.MOUSE_MOVED, (MouseEvent event) -> {
//            Point2D point2D = getNearestPathPoint(event);
//            if (!point2D.equals(null)) {
//                Circle circle = new Circle(point2D.getX(), point2D.getY(), 5);
//            }
//        });
    }

    public Circle getTrackPoint() {
        return trackPoint;
    }

    public boolean isShowTrackPoint() {
        return showTrackPoint;
    }

    public void setShowTrackPoint(boolean state) {
        showTrackPoint = state;
    }

    public void setTrackPoint(double x, double y) {
        trackPoint.setCenterX(x);
        trackPoint.setCenterY(y);
        setShowTrackPoint(true);
    }

//    private double getSpherDistance(Point2D point1, Point2D point2) {
//        return EARTH_RADIUS * Math.acos(
//                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
//                + Math.cos(point1.getY()) * Math.cos(point2.getY())
//        );
//    }
//    private double getPlaneDistance(Point3D point1, Point3D point2) {
//        return EARTH_RADIUS * Math.sqrt(
//                Math.pow(point1.getX() - point2.getX(), 2)
//                + Math.pow(point1.getY() - point2.getY(), 2)
//                + Math.pow(point1.getZ() - point2.getZ(), 2)
//        );
//    }
//    private Point2D getNearestPathPoint(MouseEvent event) {
//        return mapPlainCoords.stream().filter((point2D) -> {
//            return Math.abs(point2D.getY() - event.getY()) < 5.0
//                    && Math.abs(point2D.getX() - event.getX()) < 5.0;
//        }).findFirst().get();
//    }
}
