/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import java.util.ArrayList;
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
    private int index;

    private boolean pointerOverTrack;
    private ORoute oRoute;

//    private boolean getNearestTrackPoint(MouseEvent event, OTrack oTrack) {
//        double deltaX;
//        double deltaY;
//
//        boolean found = false;
//        double minDistance = 10.0;
//        ArrayList<Point2D> trackFlatCoords = oTrack.getTrackFlatCoords();
//
//        for (int i = 0; i < trackFlatCoords.size(); i++) {
//            deltaX = trackFlatCoords.get(i).getX() - event.getX();
//            deltaY = trackFlatCoords.get(i).getY() - event.getY();
//            if (Math.abs(deltaX) < minDistance && Math.abs(deltaY) < minDistance) {
//                double currDistance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
//                if (currDistance < minDistance) {
//                    minDistance = currDistance;
//                    origPointX = trackFlatCoords.get(i).getX();
//                    origPointY = trackFlatCoords.get(i).getY();
//                    this.deltaX = deltaX;
//                    this.deltaY = deltaY;
//                    index = i;
//                    found = true;
//                }
//            }
//        }
//        return found;
//    }

    public ORouteMouseEventHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return (MouseEvent event) -> {
//            super.getMouseMoveEventHandler().handle(event);
//            oRoute = ((OTrack) event.getSource()).getORoute();
//            if (getNearestTrackPoint(event, oRoute.getOTrack())) {
//                oRoute.setOTrackPointer(origPointX, origPointY);
//                pointerOverTrack = true;
//            } else {
//                oRoute.hideOTrackPointer();
//                oRoute.hideOBindingPointer();
//                pointerOverTrack = false;
//            }
//            document.notifyObservers();
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
                oRoute.doTrackBinding(index, (event.getX() - (origPointX + deltaX)), (event.getY() - (origPointY + deltaY)));
                document.notifyObservers();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        return null;
    }

    @Override
    public EventHandler<MouseEvent> getMouseDraggedEventHandler() {
        return ((MouseEvent event) -> {
//            if (oRoute.isModeTrackBinding() && event.isPrimaryButtonDown()) {
//                oRoute.doTrackDragging(index, (event.getX() - (origPointX + deltaX)), (event.getY() - (origPointY + deltaY)));
//                document.notifyObservers();
//            }
        });
    }

}
