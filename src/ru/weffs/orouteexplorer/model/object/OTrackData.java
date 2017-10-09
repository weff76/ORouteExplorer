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
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

/**
 *
 * @author dilobachev
 */
public final class OTrackData {

    private final ArrayList<Point2D> coordSpherical;
    private final ArrayList<Point2D> coordFlat;

    public OTrackData(GPX gpx) {
        coordSpherical = new ArrayList<>();
        coordFlat = new ArrayList<>();
        setTrackData(gpx);
    }

    public final void setTrackData(GPX gpx) {
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
        }
    }

    public final void multiplyAll(double scaleCoeff) {
        multiply(0, coordFlat.size(), scaleCoeff);
    }

    public final void multiply(int fromIndex, int size, double scaleCoeff) {
        for (int i = fromIndex; i < fromIndex + size; i++) {
            coordFlat.set(i, coordFlat.get(i).multiply(scaleCoeff));
        }
    }

    public Path getPathSegment(int fromIndex, int size) {
        Path path = new Path();
        for (int i = fromIndex; i < fromIndex + size; i++) {
            if (i == fromIndex) {
                path.getElements().add(new MoveTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
            } else {
                path.getElements().add(new LineTo(coordFlat.get(i).getX(), coordFlat.get(i).getY()));
            }
        }
        if (fromIndex + size < coordFlat.size()) {
            path.getElements().add(new LineTo(coordFlat.get(fromIndex + size).getX(), coordFlat.get(fromIndex + size).getY()));
        }
        return path;
    }

    public int getOTrackDataSize() {
        return coordFlat.size();
    }
}
