/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.OShadowSegment;
import ru.weffs.orouteexplorer.model.object.OTrackSegment;

/**
 *
 * @author dilobachev
 */
public class OTrackSegmentMEHandler extends MouseEventHandler {

    private OTrackSegment oTrackSegment;

    private double origPointX = 0.0;
    private double origPointY = 0.0;
    private int index = -1;
    
//    private double deltaX;
//    private double deltaY;
//
//    private boolean pointerOverTrack;

    public OTrackSegmentMEHandler(MainController mainController) {
        super(mainController);
    }

    @Override
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return (MouseEvent event) -> {
            super.getMouseMoveEventHandler().handle(event);
            oTrackSegment = ((OShadowSegment) event.getSource()).getOTrackSegment();

            index = oTrackSegment.getNearestToPoint(event.getX(), event.getY());
            if (index != -1) {
                origPointX = oTrackSegment.getOTrack().getCoordFlat().get(index).getX();
                origPointY = oTrackSegment.getOTrack().getCoordFlat().get(index).getY();
                oTrackSegment.getOTrack().setOTrackPointer(origPointX, origPointY);
            } else {
                oTrackSegment.getOTrack().hideOTrackPointer();
            }
            document.notifyObservers();
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseDraggedEventHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EventHandler<MouseEvent> getMousePressedEventHandler() {
        return (MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && index != -1) {
                oTrackSegment.addBinding(index);
                oTrackSegment.getOTrack().hideOTrackPointer();
                oTrackSegment.getOTrack().setOBindingPointer(origPointX, origPointY);
                document.notifyObservers();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseReleasedEventHandler() {
        return (MouseEvent event) -> {
            oTrackSegment.getOTrack().processBinding(oTrackSegment);
//            int leftIndex = oTrackSegment.getOTrack().getOTrackSegments().indexOf(oTrackSegment);
//            OTrackSegment rightSegment = 
//            oTrackSegment.processBindingRight();
            oTrackSegment.getOTrack().hideOBindingPointer();
//            if (oRoute.isModeTrackBinding()) {
//                oRoute.doTrackBinding(index, (event.getX() - (origPointX + deltaX)), (event.getY() - (origPointY + deltaY)));
//                document.notifyObservers();
//            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
