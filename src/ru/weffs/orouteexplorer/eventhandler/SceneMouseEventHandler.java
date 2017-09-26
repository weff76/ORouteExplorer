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
 * @author Weff
 */
public class SceneMouseEventHandler extends MouseEventHandler {

    public SceneMouseEventHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return event -> {
            document.getRoutes().forEach(oRoute -> {
                Point2D point2D = getNearestORoutePoint(event, oRoute);
                if (!point2D.equals(null)) {
                    oRoute.setTrackPoint(point2D.getX(), point2D.getY());
                }
            });
            mainController.getDocumentController().getDocument().notifyObservers();
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Point2D getNearestORoutePoint(MouseEvent event, ORoute oRoute) {
        return oRoute.getMapPlainCoords().stream().filter((point2D) -> {
            return Math.abs(point2D.getY() - event.getY()) < 10.0
                    && Math.abs(point2D.getX() - event.getX()) < 10.0;
        }).findFirst().get();
    }

}
