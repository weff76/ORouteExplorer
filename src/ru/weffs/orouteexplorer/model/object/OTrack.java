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

/**
 *
 * @author dilobachev
 */
public class OTrack {

    private final ArrayList<Point2D> coordSpherical;
    private final ArrayList<Point2D> coordFlat;
    private final ArrayList<OTrackSegment> oTrackSegments;

    public OTrack(GPX gpx) {
        coordSpherical = new ArrayList<>();
        coordFlat = new ArrayList<>();
        oTrackSegments = new ArrayList<>();
        setTrackData(gpx);
        oTrackSegments.add(new OTrackSegment(this, 0, coordFlat.size() - 1));
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
//    public Path getPathSegment(int fromIndex, int size) {
//        Path path = new Path();
//        for (int i = fromIndex; i < fromIndex + size; i++) {
//            if (i == fromIndex) {
//                path.getElements().add(new MoveTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
//            } else {
//                path.getElements().add(new LineTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
//            }
//        }
//        if (fromIndex + size < coordFlat.size()) {
//            path.getElements().add(new LineTo(coordFlat.get(fromIndex + size).getX(), coordFlat.get(fromIndex + size).getY()));
//        }
//        return path;
//    }
//
//    public int getOTrackDataSize() {
//        return coordFlat.size();
//    }
//    
//    public ArrayList<Point2D> getOTrackDataSegment(int segmentIndex, int segmentLength) {
//        return (ArrayList)coordFlat.subList(segmentIndex, segmentIndex + segmentLength);
//    }
//
//    final OTrackData oTrackData;
////    final ArrayList<OTrackSegment> oTrackSegments;
//    
//    public OTrack(GPX gpx) {
//        super();
//        oTrackData = new OTrackData(gpx);
//
////        oTrackSegments = new ArrayList<>();
////        oTrackSegments.add(new OTrackSegment(this, 0, oTrackData.getOTrackDataSize()));
//    }
// 
//    public OTrackData getOTrackData() {
//        return oTrackData;
//    }
//    
//    
//    
////    public OTrackSegment[] getOTrack() {
////        return oTrackSegments;
////    }
}

//public class OTrack extends Path {
//
////    private static final double EARTH_RADIUS = 6371;
//    private final ORoute oRoute;
//
//    private ArrayList<Point2D> coordSpherical;
//    private ArrayList<Point2D> coordFlat;
//
//    private OTrack shadowTrack;
//
//    public OTrack(ORoute oRoute) {
//        super();
//
//        this.oRoute = oRoute;
//    }
//
//    public OTrack(ORoute oRoute, GPX gpx) {
//        super();
//
//        this.oRoute = oRoute;
//
//        shadowTrack = new OTrack(oRoute);
//
//        coordSpherical = new ArrayList<>();
//        coordFlat = new ArrayList<>();
//
//        loadTrackData(gpx);
//    }
//
//    private void drawTrackPath() {
//        this.getElements().clear();
//        coordFlat.forEach((Point2D point2D) -> {
//            if (coordFlat.indexOf(point2D) == 0) {
//                this.getElements().add(new MoveTo(point2D.getX(), point2D.getY()));
//            } else {
//                this.getElements().add(new LineTo(point2D.getX(), point2D.getY()));
//            }
//        });
//        shadowTrack.getElements().clear();
//        shadowTrack.getElements().addAll(this.getElements());
//    }
//
//    private void setTrackPath() {
//        this.setStrokeWidth(2.0);
//        this.setStroke(Color.RED);
//        this.setOpacity(0.5);
//        this.setStrokeLineCap(StrokeLineCap.ROUND);
//        this.setMouseTransparent(true);
//
//        shadowTrack.setStrokeWidth(25.0);
//        shadowTrack.setStroke(Color.TRANSPARENT);
//        shadowTrack.setStrokeLineCap(StrokeLineCap.ROUND);
//    }
//    
////    private double getSphericalDistance(Point2D point1, Point2D point2) {
////        return EARTH_RADIUS * Math.acos(
////                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
////                + Math.cos(point1.getY()) * Math.cos(point2.getY())
////        );
////    }
//    public ArrayList<Point2D> getTrackFlatCoords() {
//        return coordFlat;
//    }
//
//    public ArrayList<Point2D> getTrackSphericalCoords() {
//        return coordSpherical;
//    }
//
//    public ORoute getORoute() {
//        return oRoute;
//    }
//
//    public OTrack getOTrackShadow() {
//        return shadowTrack;
//    }
//
//    void moveTrack(int index, double deltaX, double deltaY) {
//        Point2D delta = new Point2D(deltaX, deltaY);
//        coordFlat.forEach((Point2D point2D) -> {
//            coordFlat.set(coordFlat.indexOf(point2D), point2D.add(delta));
//        });
//        
//        drawTrackPath();
//    }
//
//}
