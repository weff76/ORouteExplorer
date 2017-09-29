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
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author dilobachev
 */
public class ORoute extends Path {

    private static final double EARTH_RADIUS = 6371;

    private Path visibleTrack;

    private final ArrayList<Point2D> coordSperical;
    private final ArrayList<Point2D> coordFlat;

    public ORoute(GPX gpx) {
        super();

        coordSperical = new ArrayList<>();
        coordFlat = new ArrayList<>();

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
            resizeTrackData();
            buildTrackPath();
        }
    }

    private void resizeTrackData() {

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
        final double resizeCoeff;

        if (Math.min(routeWidth, routeHeight) / Math.max(routeWidth, routeHeight) <= 0.75) {
            resizeCoeff = 1024.0 / routeWidth;
        } else {
            resizeCoeff = 768.0 / routeHeight;
        }

        Point2D subtractPoint = new Point2D(minFlatX, minFlatY);
        coordFlat.forEach((Point2D point2D) -> {
            coordFlat.set(coordFlat.indexOf(point2D), point2D.subtract(subtractPoint).multiply(resizeCoeff));
        });
    }

    private void buildTrackPath() {
        coordFlat.forEach((Point2D point2D) -> {
            if (coordFlat.indexOf(point2D) == 0) {
                this.getElements().add(new MoveTo(point2D.getX(), point2D.getY()));
                if (visibleTrack == null) {
                    visibleTrack = new Path();
                }
                visibleTrack.getElements().add(new MoveTo(point2D.getX(), point2D.getY()));
            } else {
                this.getElements().add(new LineTo(point2D.getX(), point2D.getY()));
                visibleTrack.getElements().add(new LineTo(point2D.getX(), point2D.getY()));
            }
        });
        this.setStrokeWidth(20.0);
        this.setStroke(Color.TRANSPARENT);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        
        visibleTrack.setStrokeWidth(3.0);
        visibleTrack.setStroke(Color.BLUE);
        visibleTrack.setStrokeLineCap(StrokeLineCap.ROUND);
    }

    public ArrayList<Point2D> getTrackFlatCoords() {
        return coordFlat;
    }

    private double getSphericalDistance(Point2D point1, Point2D point2) {
        return EARTH_RADIUS * Math.acos(
                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
                + Math.cos(point1.getY()) * Math.cos(point2.getY())
        );
    }

    public Path getVisibleTrack() {
        return visibleTrack;
    }

}
