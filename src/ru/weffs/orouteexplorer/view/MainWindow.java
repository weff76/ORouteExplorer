/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.view;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author dilobachev
 */
public class MainWindow {

    private final MainController mainController;
    private final Stage stage;
    private final MainScene mainScene;
    
    public MainWindow(MainController mainController, Stage stage) {
        this.mainController = mainController;
        this.stage = stage;
        
        mainScene = new MainScene(mainController, this);
        stage.setFullScreenExitKeyCombination(new KeyCodeCombination(KeyCode.F11));
        stage.setScene(mainScene);
    }

    public void open() {
//        SceneMouseEventHandler sceneMouseEventHandler = new SceneMouseEventHandler(mainController);
//        mainScene.addEventFilter(MouseEvent.MOUSE_MOVED, sceneMouseEventHandler.getMouseMoveEventHandler());
//        GroupMouseEventHandler groupMouseEventHandler = new GroupMouseEventHandler(mainController);
//        mainScene.getArtBoardZoomGroup().addEventFilter(MouseEvent.MOUSE_MOVED, groupMouseEventHandler.getMouseMoveEventHandler());
        stage.show();
        stage.requestFocus();
    }

    public MainScene getMainScene() {
        return mainScene;
    }

    void SetFullscreen(boolean fullscreen) {
        stage.setFullScreen(fullscreen);
    }

    Stage getStage() {
        return stage;
    }
    
}
