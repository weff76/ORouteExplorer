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
            ORoute oRoute = (ORoute) event.getSource();
            Point2D point2D = getNearestTrackPoint(event, oRoute);
            if (point2D != null) {
//                System.out.println("X:" + point2D.getX() + " Y:" + point2D.getY());
                document.showTrackPoint(point2D.getX(), point2D.getY());
            } else {
                document.hideTrackPoint();
            }
            document.notifyObservers();
        };
    }

    private Point2D getNearestTrackPoint(MouseEvent event, ORoute oRoute) {
        for (Point2D point2D : oRoute.getTrackFlatCoords()) {
            if (Math.abs(point2D.getY() - event.getY()) < 10.0 && Math.abs(point2D.getX() - event.getX()) < 10.0) {
                return point2D;
            }
        }
        return null;
    }

}
