/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author dilobachev
 */
public class OTrackPointer extends Circle {

    private final OTrack oTrack;

    public OTrackPointer(OTrack oTrack) {
        super(4.0, Color.TRANSPARENT);

        setMouseTransparent(true);
        this.oTrack = oTrack;
    }

    public OTrack getOTrack() {
        return oTrack;
    }
    
}
