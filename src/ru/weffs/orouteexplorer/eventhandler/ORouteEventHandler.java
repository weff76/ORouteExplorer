/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import java.io.PrintStream;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.ORoute;

/**
 *
 * @author dilobachev
 */
public class ORouteEventHandler extends MouseEventHandler {

    public ORouteEventHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return event -> {
            super.getMouseMoveEventHandler().handle(event);
            System.out.println("X:" + event.getX() + " Y:" + event.getY() + " XS:" + event.getSceneX() + " YS:" + event.getSceneY());
//            ORoute oRoute = (ORoute) event.getSource();
//            Point2D point2D = getNearestPathPoint(event, oRoute);
//            
//            System.out.println(event.toString());
//            oRoute.setTrackPoint(event.getX(), event.getY());
//            mainController.getDocumentController().getDocument().notifyObservers();
        };
    }

    private Point2D getNearestPathPoint(MouseEvent event, ORoute oRoute) {
        return oRoute.getMapPlainCoords().stream().filter((point2D) -> {
            return Math.abs(point2D.getY() - event.getY()) < 20.0
                    && Math.abs(point2D.getX() - event.getX()) < 20.0;
        }).findFirst().get();
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        return event -> {
            ORoute oRoute = (ORoute) event.getSource();

            oRoute.setShowTrackPoint(false);
            mainController.getDocumentController().getDocument().notifyObservers();
        };
    }

}
