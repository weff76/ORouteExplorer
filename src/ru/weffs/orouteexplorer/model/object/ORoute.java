/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model.object;

import com.hs.gpxparser.modal.GPX;
import java.util.ArrayList;
import javafx.scene.paint.Color;

/**
 *
 * @author dilobachev
 */
public class ORoute {

    private final OTrack oTrack;
    private final OTrackPointer oTrackPointer;
    private final OBindingPointer oBindingPointer;
    private final ArrayList<OBinding> oBindings;

    private boolean modeTrackBinding;

    public ORoute(GPX gpx) {
        oTrack = new OTrack(this, gpx);
        oTrackPointer = new OTrackPointer(this);
        oBindingPointer = new OBindingPointer(this);
        oBindings = new ArrayList<>();
    }

    public OTrack getOTrack() {
        return oTrack;
    }

    public OTrackPointer getOTrackPointer() {
        return oTrackPointer;
    }

    public void setOTrackPointer(double x, double y) {
        oTrackPointer.setCenterX(x);
        oTrackPointer.setCenterY(y);
        oTrackPointer.setFill(Color.RED);
    }

    public void hideOTrackPointer() {
        oTrackPointer.setFill(Color.TRANSPARENT);
    }

    public OBindingPointer getOBindingPointer() {
        return oBindingPointer;
    }

    public void setOBindingPointer(double x, double y) {
        oBindingPointer.setCenterX(x);
        oBindingPointer.setCenterY(y);
        oBindingPointer.setStroke(Color.BLUE);
    }

    public void hideOBindingPointer() {
        oBindingPointer.setStroke(Color.TRANSPARENT);
    }

    public void setModeTrackBinding(double x, double y) {
        modeTrackBinding = true;
        hideOTrackPointer();
        setOBindingPointer(x, y);
    }

    public boolean isModeTrackBinding() {
        return modeTrackBinding;
    }

    public void doTrackBinding(int index, double deltaX, double deltaY) {
        // Simple move
        if (oBindings.isEmpty()) {
            oTrack.moveTrack(index, deltaX, deltaY);
            oBindings.add(new OBinding(this, index));
        }
        hideOBindingPointer();
        modeTrackBinding = false;
    }
    
    public ArrayList<OBinding> getOBindings() {
        return oBindings;
    }
}
