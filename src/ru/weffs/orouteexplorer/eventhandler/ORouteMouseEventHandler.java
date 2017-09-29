/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.OTrack;

/**
 *
 * @author dilobachev
 */
public class ORouteMouseEventHandler extends MouseEventHandler {

    public ORouteMouseEventHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return event -> {
            super.getMouseMoveEventHandler().handle(event);
            OTrack oTrackShadow = (OTrack) event.getSource();
            Point2D point2D = getNearestTrackPoint(event, oTrackShadow.getORoute().getOTrack());
            if (point2D != null) {
                oTrackShadow.getORoute().showOTrackPoint(point2D.getX(), point2D.getY());
            } else {
                oTrackShadow.getORoute().hideOTrackPoint();
            }
            document.notifyObservers();
        };
    }

    private Point2D getNearestTrackPoint(MouseEvent event, OTrack oTrack) {
        for (Point2D point2D : oTrack.getTrackFlatCoords()) {
            if (Math.abs(point2D.getY() - event.getY()) < 10.0 && Math.abs(point2D.getX() - event.getX()) < 10.0) {
                return point2D;
            }
        }
        return null;
    }

}
