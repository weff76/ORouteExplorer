/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import java.util.ArrayList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.ORoute;

/**
 *
 * @author Weff
 */
public class GroupMouseEventHandler extends MouseEventHandler {

    public GroupMouseEventHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return event -> {
            System.out.println("GX:" + event.getX() + " GY:" + event.getY() + " GXS:" + event.getSceneX() + " GYS:" + event.getSceneY());

//            super.getMouseMoveEventHandler().handle(event);
////            document.getRoutes().forEach((ORoute oRoute) -> {
//
//            ORoute oRoute = null;
//            if (!document.getRoutes().isEmpty()) {
//                oRoute = document.getRoutes().get(0);
//                Point2D point2D = getNearestORoutePoint(event, oRoute);
//                if (point2D != null) {
//                    oRoute.setTrackPoint(point2D.getX(), point2D.getY());
//                    mainController.getDocumentController().getDocument().notifyObservers();
//                }
//            };
//            });
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Point2D getNearestORoutePoint(MouseEvent event, ORoute oRoute) {

        for (Point2D point2D : oRoute.getMapPlainCoords()) {
                if (Math.abs(point2D.getY() - event.getY()) < 10.0 && Math.abs(point2D.getX() - event.getX()) < 10.0) {
                    return point2D;
                }
        }
        
        return null;
//            oRoute.getMapPlainCoords().forEach(point2D -> {
//                if (Math.abs(point2D.getY() - event.getY()) < 3.0 && Math.abs(point2D.getX() - event.getX()) < 3.0) {
//                    
//                }
//            });
//        Optional<Point2D> result = oRoute.getMapPlainCoords().stream().filter((point2D) -> {
//            mainScene.getStatusBar().setDeltaLabel((int) Math.abs(point2D.getX() - event.getX()), (int) Math.abs(point2D.getY() - event.getY()));
//
//            return Math.abs(point2D.getY() - event.getY()) < 3.0
//                    && Math.abs(point2D.getX() - event.getX()) < 3.0;
//        }).findFirst();
//        if (result.isPresent()) {
//            return result.get();
//        } else {
//            return null;
//        }
    }

}
