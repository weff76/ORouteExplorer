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
import javafx.geometry.Point3D;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author dilobachev
 */
public class ORoute extends Path {

    private static final double EARTH_RADIUS = 6371;

    private final double mapWidth;
    private final double mapHeight;

    private final Path path;
    double minPlaneX = 0.0;
    double minPlaneY = 0.0;
    double maxPlaneX = 0.0;
    double maxPlaneY = 0.0;
    double mapCoeff = 0.0;

    private final ArrayList<Point2D> spherCoords;
    private final ArrayList<Point2D> planeCoords;
    private final ArrayList<Point2D> mapPlainCoords;

    public ORoute(GPX gpx, double width, double height) {
        mapWidth = width;
        mapHeight = height;

        spherCoords = new ArrayList<>();
        planeCoords = new ArrayList<>();
        mapPlainCoords = new ArrayList<>();

        path = new Path();
        loadTrackData(gpx);
    }

    private void loadTrackData(GPX gpx) {
        if (!gpx.getTracks().isEmpty()) {
            gpx.getTracks().forEach((Track track) -> {
                track.getTrackSegments().forEach((TrackSegment trackSegment) -> {
                    trackSegment.getWaypoints().forEach((Waypoint waypoint) -> {
                        double radLongitude = Math.toRadians(waypoint.getLongitude());
                        double radLatitude = Math.toRadians(90 - waypoint.getLatitude());
                        spherCoords.add(new Point2D(
                                (double) radLongitude,
                                (double) radLatitude)
                        );
                        planeCoords.add(new Point2D(
                                (double) Math.cos(radLongitude) * Math.sin(radLatitude),
                                (double) Math.sin(radLongitude) * Math.sin(radLatitude)));
//                                Math.cos(radLongitude)));
                    });
                });
            });
            calcCoeffs();
            adjustTrackData();
            buildPath();
        }
    }

    private double getSpherDistance(Point2D point1, Point2D point2) {
        return EARTH_RADIUS * Math.acos(
                Math.sin(point1.getY()) * Math.sin(point2.getY()) * Math.cos(point1.getX() - point2.getX())
                + Math.cos(point1.getY()) * Math.cos(point2.getY())
        );
    }

    private double getPlaneDistance(Point3D point1, Point3D point2) {
        return EARTH_RADIUS * Math.sqrt(
                Math.pow(point1.getX() - point2.getX(), 2)
                + Math.pow(point1.getY() - point2.getY(), 2)
                + Math.pow(point1.getZ() - point2.getZ(), 2)
        );
    }

    private void adjustTrackData() {
        planeCoords.forEach((Point2D point2D) -> {
            mapPlainCoords.add(new Point2D(
                    (point2D.getX() - minPlaneX) * mapCoeff + mapWidth * 0.05,
                    (point2D.getY() - minPlaneY) * mapCoeff + mapHeight * 0.05));
        });
    }

    private void calcCoeffs() {
        for (Point2D point2D : planeCoords) {
            if (minPlaneX > point2D.getX() || minPlaneX == 0.0) {
                minPlaneX = point2D.getX();
            }
            if (minPlaneY > point2D.getY() || minPlaneY == 0.0) {
                minPlaneY = point2D.getY();
            }
            if (maxPlaneX < point2D.getX()) {
                maxPlaneX = point2D.getX();
            }
            if (maxPlaneY < point2D.getY()) {
                maxPlaneY = point2D.getY();
            }
        }
        double minMapDimension = Math.min(mapWidth, mapHeight);
        double maxPlaneDimension = Math.max(maxPlaneX - minPlaneX, maxPlaneY - minPlaneY);
        mapCoeff = minMapDimension * 0.9 / maxPlaneDimension;
    }

    private void buildPath() {
        mapPlainCoords.forEach((Point2D point2D) -> {
            if (mapPlainCoords.indexOf(point2D) == 0) {
                path.getElements().add(new MoveTo(point2D.getX(), point2D.getY()));
            } else {
                path.getElements().add(new LineTo(point2D.getX(), point2D.getY()));
                path.setStrokeWidth(5.0);
                path.setStroke(Color.BLUE);
                path.setStrokeLineCap(StrokeLineCap.ROUND);
            }
        });

        path.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event -> {
//            path.setEffect(new DropShadow(20, Color.BLACK));
        });

        path.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, event -> {
//            path.setEffect(null);
        });

        path.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
            if (mouseNearPath(event)) {
            }
        });
    }

    public Path getPath() {
        return path;
    }

    private boolean mouseNearPath(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        mapPlainCoords.forEach((Point2D point2D) -> {
            if (Math.abs(point2D.getX() - mouseX) < 5.0
                    && Math.abs(point2D.getY() - mouseY) < 5.0) {
                System.out.println(point2D.toString());
            }
        });

        return true;
    }
}
