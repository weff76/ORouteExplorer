/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.GUIState;
import ru.weffs.orouteexplorer.model.Observer;

/**
 *
 * @author dilobachev
 */
public class StatusBar extends BorderPane implements Observer {
    
    private final MainController mainController;
    
    private final HBox leftHBox;
    private final Label coordinatesLabel;
    private final Label deltaLabel;

    private final HBox rightHBox;
    private final Label zoomLabel;    
    
    public StatusBar(MainController mainController) {
        super();
        
        this.mainController = mainController;

        leftHBox = new HBox();

        coordinatesLabel = new Label();
        coordinatesLabel.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("icons/16x16/derivatives.png"))));
        coordinatesLabel.getStyleClass().add("statusbar-label");
        coordinatesLabel.getStyleClass().add("border-right");
        coordinatesLabel.setMinWidth(150);

        deltaLabel = new Label();
        deltaLabel.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("icons/16x16/pencil_ruler.png"))));
        deltaLabel.getStyleClass().add("statusbar-label");
        deltaLabel.getStyleClass().add("border-right");
        deltaLabel.setMinWidth(150);

        leftHBox.getChildren().addAll(
                coordinatesLabel,
                deltaLabel
        );

        rightHBox = new HBox();

        zoomLabel = new Label();
        zoomLabel.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("icons/16x16/zoom.png"))));
        zoomLabel.getStyleClass().add("statusbar-label");
        zoomLabel.getStyleClass().add("border-left");
        zoomLabel.setMinWidth(150);

        rightHBox.getChildren().addAll(
                zoomLabel
        );

        setLeft(leftHBox);
        setRight(rightHBox);
    }

    public void setCoordinatesLabel(int x, int y) {
        coordinatesLabel.setText("(" + x + ", " + y + ")");
    }

    public void setDeltaLabel(int x, int y) {
        deltaLabel.setText("(" + x + ", " + y + ")");
    }

    @Override
    public void update() {
        GUIState guiState = mainController.getGUIController().getGuiState();

        int zoomLevel = (int) (guiState.getZoomLevel() * 100);
        zoomLabel.setText(zoomLevel + "%");
    }

    public void clear() {
        coordinatesLabel.setText("");
        zoomLabel.setText("");
    }
    
}
