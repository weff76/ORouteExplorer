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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author dilobachev
 */
public class OTrack extends Path {
 
//    private static final double EARTH_RADIUS = 6371;

    private final ORoute oRoute;
    
    private ArrayList<Point2D> coordSpherical;
    private ArrayList<Point2D> coordFlat;

    private OTrack shadowTrack;
    
    public OTrack(ORoute oRoute) {
        super();
        
        this.oRoute = oRoute;
    }
    
    public OTrack(ORoute oRoute, GPX gpx) {
        super();
        
        this.oRoute = oRoute;
        
        shadowTrack = new OTrack(oRoute);
        
        coordSpherical = new ArrayList<>();
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

//            coordFlat.clear();
//            for(int i = 100; i<201; i++) {
//                coordFlat.add(new Point2D(i, 100));
//            }
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

        final double resizeCoeff;

        double routeWidth = maxFlatX - minFlatX;
        double routeHeight = maxFlatY - minFlatY;

        if (routeWidth <= routeHeight ) {
            resizeCoeff = 768.0 / routeHeight;
        } else {
            if ( routeWidth / routeHeight <= 0.75 ) {
                resizeCoeff = 1024.0 / routeWidth;
            } else {
                resizeCoeff = 768.0 / routeHeight;
            }
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
            } else {
                this.getElements().add(new LineTo(point2D.getX(), point2D.getY()));
            }
        });
        this.setStrokeWidth(2.0);
        this.setStroke(Color.BLUE);
        this.setStrokeLineCap(StrokeLineCap.ROUND);
        
        shadowTrack.getElements().addAll(this.getElements());
        shadowTrack.setStrokeWidth(20.0);
        shadowTrack.setStroke(Color.TRANSPARENT);
        shadowTrack.setStrokeLineCap(StrokeLineCap.ROUND);
    }

//    private double getSphericalDistance(Point2D point1, Point2D point2) {
//        return EARTH_RADIUS * Math.acos(
//                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
//                + Math.cos(point1.getY()) * Math.cos(point2.getY())
//        );
//    }
    
    public ArrayList<Point2D> getTrackFlatCoords() {
        return coordFlat;
    }

    public ArrayList<Point2D> getTrackSphericalCoords() {
        return coordSpherical;
    }

    public ORoute getORoute() {
        return oRoute;
    }
    
    public OTrack getOTrackShadow() {
        return shadowTrack;
    }
}
