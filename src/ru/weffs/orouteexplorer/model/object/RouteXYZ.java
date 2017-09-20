/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import com.hs.gpxparser.modal.GPX;
import com.hs.gpxparser.modal.TrackSegment;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.geometry.Point3D;

/**
 *
 * @author dilobachev
 */
public class RouteXYZ {
    private final ArrayList<Point3D> routeXYZ;
    
    public RouteXYZ(GPX gpx) {
        routeXYZ = new  ArrayList<>();
        setRoute(gpx);
    }
    
    private void setRoute(GPX gpx) {
        if (!gpx.getTracks().isEmpty()) {
            Iterator tracks = gpx.getTracks().iterator();
            while (tracks.hasNext()) {
                TrackSegment trackSegment = (TrackSegment) tracks.next();
                
            }
        }
    }
}
