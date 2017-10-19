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
    private int segmentIndex = -1;

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

//            System.out.println(oTrackSegment.getFromIndex() + " " + oTrackSegment.getToIndex() + " " + index);

            index = oTrackSegment.getNearestToPoint(event.getX(), event.getY());
            if (index != -1) {
                origPointX = oTrackSegment.getOTrack().getCoordFlat().get(index).getX();
                origPointY = oTrackSegment.getOTrack().getCoordFlat().get(index).getY();
                oTrackSegment.getOTrack().getOTrackPointer().setOTrackPointer(origPointX, origPointY);
            } else {
                oTrackSegment.getOTrack().getOTrackPointer().hideOTrackPointer();
            }
            document.notifyObservers();
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseDraggedEventHandler() {
        return ((MouseEvent event) -> {
            System.out.println("Drag");
            if (segmentIndex != -1) {
//                oTrackSegment.getOTrack().processBinding(
//                        segmentIndex,
//                        new Point2D(event.getX() - origPointX, event.getY() - origPointY)
//                );
//                origPointX = event.getX();
//                origPointY = event.getY();
////                oTrackSegment.getOTrack().hideOBindingPointer();
                document.notifyObservers();
            }
        });
    }

    @Override
    public EventHandler<MouseEvent> getMousePressedEventHandler() {
        return (MouseEvent event) -> {
            System.out.println("Press");
            if (event.isPrimaryButtonDown() && index != -1) {
                oTrackSegment.getOTrack().getOTrackPointer().hideOTrackPointer();
                segmentIndex = oTrackSegment.getOTrack().splitTrackSegment( index, oTrackSegment );
//                oTrackSegment.getOTrack().setOBindingPointer(origPointX, origPointY);
                document.notifyObservers();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseReleasedEventHandler() {
        return (MouseEvent event) -> {
            System.out.println("Release");
            if (segmentIndex != -1) {
                oTrackSegment.getOTrack().processBinding(
                        segmentIndex,
                        new Point2D(event.getX() - origPointX, event.getY() - origPointY)
                );
                segmentIndex = -1;
//                oTrackSegment.getOTrack().hideOBindingPointer();
                document.notifyObservers();
            }
        };
    }

    @Override
    public EventHandler<MouseEvent> getMouseExitedEventHandler() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
