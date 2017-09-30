/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import com.hs.gpxparser.modal.GPX;
import javafx.scene.paint.Color;

/**
 *
 * @author dilobachev
 */
public class ORoute {

    private final OTrack oTrack;
    private final OTrackPoint oTrackPoint;

    public ORoute(GPX gpx) {
        oTrack = new OTrack(this, gpx);
        oTrackPoint = new OTrackPoint(this);
    }

    public OTrack getOTrack() {
        return oTrack;
    }
    
    public OTrackPoint getOTrackPoint() {
        return oTrackPoint;
    }

    public void showOTrackPoint(double x, double y) {
        oTrackPoint.setCenterX(x);
        oTrackPoint.setCenterY(y);
        oTrackPoint.setFill(Color.RED);
    }

    public void hideOTrackPoint() {
        oTrackPoint.setFill(Color.TRANSPARENT);
    }

}
