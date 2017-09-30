/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.ORoute;
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
            }
            document.notifyObservers();
        };
    }

    private Point2D getNearestTrackPoint(MouseEvent event, OTrack oTrack) {
        double minDistance = 10.0;
        Point2D minPoint = null;
        for (Point2D point2D : oTrack.getTrackFlatCoords()) {
            if (Math.abs(point2D.getX() - event.getX()) < minDistance && Math.abs(point2D.getY() - event.getY()) < minDistance) {
                double currDistance = Math.sqrt(Math.pow(point2D.getX() - event.getX(), 2) + Math.pow(point2D.getY() - event.getY(), 2));
                if (currDistance < minDistance) {
                    minDistance = currDistance;
                    minPoint = point2D;
                }
            }
        }
        return minPoint;
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        return (MouseEvent event) -> {
            document.getORoutes().forEach(ORoute::hideOTrackPoint);
            document.notifyObservers();
        };
    }

}
