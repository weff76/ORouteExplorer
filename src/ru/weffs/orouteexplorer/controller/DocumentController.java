/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.controller;

import com.hs.gpxparser.GPXParser;
import com.hs.gpxparser.modal.GPX;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import javafx.scene.image.Image;
import ru.weffs.orouteexplorer.eventhandler.ORouteMouseEventHandler;
import ru.weffs.orouteexplorer.model.Document;
import ru.weffs.orouteexplorer.model.GUIState;
import ru.weffs.orouteexplorer.model.object.OMap;
import ru.weffs.orouteexplorer.model.object.ORoute;
import ru.weffs.orouteexplorer.view.MainScene;

/**
 *
 * @author dilobachev
 */
public class DocumentController {

    private final MainController mainController;
    private final MainScene mainScene;

    private Document document;

    public DocumentController(MainController mainController) {
        this.mainController = mainController;
        mainScene = mainController.getGUIController().getMainWindow().getMainScene();
        createNewDocument();
    }

    private void createNewDocument() {
        GUIState guiState = mainController.getGUIController().getGuiState();

        document = new Document(mainController);

        document.registerObserver(mainController.getGUIController().getMainWindow().getMainScene());
        document.registerObserver(mainController.getGUIController().getMainWindow().getMainScene().getMenuBar());
        document.registerObserver(mainController.getGUIController().getMainWindow().getMainScene().getStatusBar());

        guiState.registerObserver(mainController.getGUIController().getMainWindow().getMainScene());
        guiState.registerObserver(mainController.getGUIController().getMainWindow().getMainScene().getMenuBar());
        guiState.registerObserver(mainController.getGUIController().getMainWindow().getMainScene().getStatusBar());
    }

    public Document getDocument() {
        return document;
    }

    public void importImage(File file) throws IOException {
        Image img = new Image(Files.newInputStream(file.toPath()));

        OMap oMap = new OMap(mainController);
        oMap.setImage(img);
        oMap.setX(0);
        oMap.setY(0);
        oMap.setPreserveRatio(true);
        oMap.setFitWidth(1024.0);
//        oMap.setFitHeight(img.getHeight());

        addOMap(oMap);

        mainScene.activateControls(true);
    }

    public void importGPX(File file) throws Exception {
        GPXParser p = new GPXParser();
        GPX gpx = p.parseGPX(new FileInputStream(file.getPath()));

        ORoute oRoute = new ORoute(gpx);
        addORoute(oRoute);

        ORouteMouseEventHandler oRouteEventHandler = new ORouteMouseEventHandler(mainController);
        oRoute.getOTrack().getOTrackShadow().setOnMouseMoved(oRouteEventHandler.getMouseMoveEventHandler());
        oRoute.getOTrack().getOTrackShadow().setOnMousePressed(oRouteEventHandler.getMousePressedEventHandler());
        oRoute.getOTrack().getOTrackShadow().setOnMouseReleased(oRouteEventHandler.getMouseReleasedEventHandler());
//        oRoute.getOTrack().getOTrackShadow().setOnMouseDragged(oRouteEventHandler.getMouseDraggedEventHandler());

        mainScene.activateControls(true);
    }

    private void addORoute(ORoute oRoute) {
        document.addORoute(oRoute);
    }

    private void addOMap(OMap oMap) {
        document.addOMap(oMap);
    }

}
