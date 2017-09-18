/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.view;

import java.util.concurrent.Callable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.eventhandler.KeyEventHandler;
import ru.weffs.orouteexplorer.model.Document;
import ru.weffs.orouteexplorer.model.Observer;

/**
 *
 * @author dilobachev
 */
public class MainScene extends Scene implements Observer {

    private final MainController mainController;
    private final MainWindow mainWindow;
    
    private final BorderPane mainBorderPane;
    private final VBox topPane;
    private final MenuBar menuBar;
    
    private ArtBoard artBoard;
    private final Group artBoardGroup;
    private final Group artBoardZoomGroup;
    
    MainScene(MainController mainController, MainWindow mainWindow) {
        super(new BorderPane(), 1024, 768);
        
        getStylesheets().add(ClassLoader.getSystemResource("css/general.css").toExternalForm());
        
        this.mainController = mainController;
        this.mainWindow = mainWindow;
        
        mainBorderPane = new BorderPane();
        topPane = new VBox();
        
        menuBar = new MenuBar(this.mainController);
        showMenuBar(true);
        
        mainBorderPane.setTop(topPane);
 
        artBoardGroup = new Group();
        artBoardGroup.getStyleClass().add("no-focus-outline");
        
        artBoardZoomGroup = new Group(artBoardGroup);
        artBoardZoomGroup.getStyleClass().add("no-focus-outline");
        
        StackPane stackPane = new StackPane(artBoardZoomGroup);
        stackPane.getStyleClass().add("no-focus-outline");
        
        ScrollPane scrollPane = new ScrollPane(stackPane);
        scrollPane.getStyleClass().add("no-focus-outline");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding(scrollPane.getViewportBounds()::getWidth, scrollPane.viewportBoundsProperty()));        
        stackPane.minHeightProperty().bind(Bindings.createDoubleBinding(scrollPane.getViewportBounds()::getHeight, scrollPane.viewportBoundsProperty()));
        
        mainBorderPane.setCenter(scrollPane);
        
        KeyEventHandler keyEventHandler = new KeyEventHandler(mainController);
        setOnKeyPressed(keyEventHandler.getKeyPressedEventHandler());
        
        setRoot(mainBorderPane);
    }

    public void showMenuBar(boolean show) {
        if (show) {
            if (!topPane.getChildren().contains(menuBar)) {
                topPane.getChildren().add(0, menuBar);
            }
        }
        else {
            topPane.getChildren().remove(menuBar);
        }  
        menuBar.selectViewMenuBarItem(show);
    }

    public void setArtBoard(int width, int height) {
        if (!artBoardGroup.getChildren().isEmpty()) {
            clearArtBoard();
        }

        artBoard = new ArtBoard(mainController, width, height);
        artBoardGroup.getChildren().add(artBoard);        
    }

    private void clearArtBoard() {
        artBoard = null;
        artBoardGroup.getChildren().clear();
    }

    @Override
    public void update() {
        Document document = mainController.getDocumentController().getDocument();

        if (artBoard != null) {
            artBoardGroup.getChildren().clear();
            artBoardGroup.getChildren().add(artBoard);
            artBoardGroup.getChildren().addAll(document.getObjects());
        }    
    }
}
