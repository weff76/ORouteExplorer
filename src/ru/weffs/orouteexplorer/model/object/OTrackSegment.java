/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import javafx.scene.shape.Path;

/**
 *
 * @author dilobachev
 */
public class OTrackSegment extends Path {

    private final OTrack oTrack;
    private final int fromIndex;
    private final int size;

    public OTrackSegment(OTrack oTrack, int fromIndex, int size) {
        this.oTrack = oTrack;
        this.fromIndex = fromIndex;
        this.size = size;
    }

    public OTrack getOTrack() {
        return oTrack;
    }
    
    private void setPath() {
        this.getElements().clear();
        this.getElements().addAll(
                oTrack.getOTrackData().getPathSegment(fromIndex, size).getElements()
        );
    }
    
}
