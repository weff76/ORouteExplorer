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
import ru.weffs.orouteexplorer.model.object.ORoute;
import ru.weffs.orouteexplorer.model.object.OTrack;

/**
 *
 * @author dilobachev
 */
public class ORouteMouseEventHandler extends MouseEventHandler {

    private double origPointX;
    private double origPointY;
    private double deltaX;
    private double deltaY;
    
    private boolean pointerOverTrack;
    private ORoute oRoute;

    private Point2D getNearestTrackPoint(MouseEvent event, OTrack oTrack) {
        double minDistance = 10.0;
        double deltaX;
        double deltaY;
        Point2D minPoint = null;
        
        for (Point2D point2D : oTrack.getTrackFlatCoords()) {
            deltaX = point2D.getX() - event.getX();
            deltaY = point2D.getY() - event.getY();
            if (Math.abs(deltaX) < minDistance && Math.abs(deltaY) < minDistance) {
                double currDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
                if (currDistance < minDistance) {
                    minDistance = currDistance;
                    minPoint = point2D;
                    this.deltaX = deltaX;
                    this.deltaY = deltaY;
                }
            }
        }
        return minPoint;
    }

    public ORouteMouseEventHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return (MouseEvent event) -> {
            super.getMouseMoveEventHandler().handle(event);
            oRoute = ((OTrack) event.getSource()).getORoute();
            Point2D point2D = getNearestTrackPoint(event, oRoute.getOTrack());
            if (point2D != null) {
                origPointX = point2D.getX();
                origPointY = point2D.getY();
                oRoute.setOTrackPointer(origPointX, origPointY);
                pointerOverTrack = true;
            } else {
                oRoute.hideOTrackPointer();
                oRoute.hideOBindingPointer();
                pointerOverTrack = false;
            }
            document.notifyObservers();
        };
    }

    @Override
    public EventHandler<MouseEvent> getMousePressedEventHandler() {
        return (MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && pointerOverTrack) {
                oRoute.setModeTrackBinding(origPointX, origPointY);
                document.notifyObservers();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseReleasedEventHandler() {
        return (MouseEvent event) -> {
            if (oRoute.isModeTrackBinding()) {
                oRoute.doTrackBinding(deltaX, deltaY);
                document.notifyObservers();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        return null;
    }

}
