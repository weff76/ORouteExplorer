/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.Document;
import ru.weffs.orouteexplorer.view.MainScene;

/**
 *
 * @author dilobachev
 */
public abstract class MouseEventHandler {
    
    protected final MainController mainController;
    protected final MainScene mainScene;
    protected final Document document;
    
    public MouseEventHandler(MainController mainController){
        
        this.mainController = mainController;
        mainScene = mainController.getGUIController().getMainWindow().getMainScene();
        document = mainController.getDocumentController().getDocument();
    }
    
    public EventHandler<MouseEvent> getMouseMoveEventHandler() {
        return event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();

            mainScene.getStatusBar().setCoordinatesLabel(x, y);
        };
    };
    
    public abstract EventHandler<MouseEvent> getMouseExitedEventHandler();

}
