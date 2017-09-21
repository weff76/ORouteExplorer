/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import com.hs.gpxparser.modal.GPX;
import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

/**
 *
 * @author dilobachev
 */
public class RouteXYZ {

    private static double EARTH_RADIUS = 6371;
    private final ArrayList<Point2D> sphericalXY;
    private final ArrayList<Point3D> planeXYZ;

    public RouteXYZ(GPX gpx) {
        sphericalXY = new ArrayList<>();
        planeXYZ = new ArrayList<>();
        setRoute(gpx);
    }

    private void setRoute(GPX gpx) {
        if (!gpx.getTracks().isEmpty()) {
            gpx.getTracks().forEach((track) -> {
                track.getTrackSegments().forEach((trackSegment) -> {
                    trackSegment.getWaypoints().forEach((waypoint) -> {
                        double radLongitude = Math.toRadians(waypoint.getLongitude());
                        double radLatitude = Math.toRadians(90 - waypoint.getLatitude());
                        sphericalXY.add(new Point2D(
                                radLongitude,
                                radLatitude)
                        );
                        planeXYZ.add(new Point3D(
                                Math.cos(radLongitude) * Math.sin(radLatitude),
                                Math.sin(radLongitude) * Math.sin(radLatitude),
                                Math.cos(radLongitude)
                        ));
                    });
                });
            });
        }
    }

    public double getSphericalDistance(Point2D point1, Point2D point2) {
        return EARTH_RADIUS * Math.acos(
                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
                + Math.cos(point1.getY()) * Math.cos(point2.getY())
        );
    }

    public double getPlaneDistance(Point3D point1, Point3D point2) {
        return EARTH_RADIUS * Math.sqrt(
                Math.pow(point1.getX() - point2.getX(), 2)
                + Math.pow(point1.getY() - point2.getY(), 2)
                + Math.pow(point1.getZ() - point2.getZ(), 2)
        );
    }
}
