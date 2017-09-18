/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.eventhandler;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author dilobachev
 */
public class KeyEventHandler {
    
    private final MainController mainController;
    
    public KeyEventHandler(MainController mainController) {
        this.mainController = mainController;
    }
    
    public EventHandler<KeyEvent> getKeyPressedEventHandler() {
        return (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ALT)) {
                mainController.getGUIController().getMainWindow().getMainScene().showMenuBar(true);
            }
        };
    }
}
