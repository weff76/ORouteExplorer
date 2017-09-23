/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.view;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.GUIState;
import ru.weffs.orouteexplorer.model.Observer;

/**
 *
 * @author dilobachev
 */
public class MenuBar extends javafx.scene.control.MenuBar implements Observer {
    
    private final MainController mainController;
    
    private MenuItem addMapMenuItem;
    private MenuItem addRouteMenuItem;
    private MenuItem fileExitMenuItem;
    
    private CheckMenuItem viewMenuBarMenuItem;
    private CheckMenuItem viewFullScreenMenuItem;

    private MenuItem viewZoomInMenuItem;
    private MenuItem viewZoomOutMenuItem;
    private MenuItem viewZoomResetMenuItem;
    private Slider viewZoomSlider;
    private CustomMenuItem viewZoomSliderMenuItem;
    
    MenuBar(MainController mainController) {
        super();
        
        this.mainController = mainController;
        
        Menu fileMenu = buildFileMenu();
        Menu viewMenu = buildViewMenu();
        
        getMenus().addAll(
                fileMenu,
                viewMenu
        );

        activateControls(false);
    }

    private Menu buildFileMenu() {
        Menu menu = new Menu();
        menu.setText("_File");
        
        addMapMenuItem = new MenuItem();
        addMapMenuItem.setText("Add _map...");
        addMapMenuItem.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("icons/16x16/map_add.png"))));
        addMapMenuItem.setOnAction(event -> mainController.getGUIController().importImage(mainController.getGUIController().getMainWindow().getStage()));
        
        addRouteMenuItem = new MenuItem();
        addRouteMenuItem.setText("Add _route...");
        addRouteMenuItem.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("icons/16x16/chart_curve_add.png"))));
        addRouteMenuItem.setOnAction(event -> mainController.getGUIController().importRoute(mainController.getGUIController().getMainWindow().getStage()));
        
        fileExitMenuItem = new MenuItem();
        fileExitMenuItem.setText("E_xit");
        fileExitMenuItem.setGraphic(new ImageView(new Image(ClassLoader.getSystemResourceAsStream("icons/16x16/door_in.png"))));
        fileExitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCodeCombination.ALT_DOWN));
        fileExitMenuItem.setOnAction(event -> mainController.exit());
        
        menu.getItems().addAll(
                new SeparatorMenuItem(),
                addMapMenuItem,
                addRouteMenuItem,
                new SeparatorMenuItem(),
                fileExitMenuItem
        );
        
        return menu;
    }

    private Menu buildViewMenu() {
        Menu menu = new Menu();
        menu.setText("_View");
        
        viewMenuBarMenuItem = new CheckMenuItem();
        viewMenuBarMenuItem.setText("_Menu Bar");
        viewMenuBarMenuItem.setSelected(true);
        viewMenuBarMenuItem.setOnAction(event -> mainController.getGUIController().getMainWindow().getMainScene().showMenuBar(viewMenuBarMenuItem.isSelected()));
        
        viewFullScreenMenuItem = new CheckMenuItem();
        viewFullScreenMenuItem.setText("_Fullscreen");
        viewFullScreenMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.F11));
        viewFullScreenMenuItem.setOnAction(event -> mainController.getGUIController().getMainWindow().SetFullscreen(viewFullScreenMenuItem.isSelected()));

        viewZoomInMenuItem = new MenuItem();
        viewZoomInMenuItem.setText("_Zoom In");
        viewZoomInMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN));
        viewZoomInMenuItem.setOnAction(event -> mainController.getGUIController().getGuiState().setZoomLevel(mainController.getGUIController().getGuiState().getZoomLevel() + 0.25));

        viewZoomOutMenuItem = new MenuItem();
        viewZoomOutMenuItem.setText("_Zoom Out");
        viewZoomOutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN));
        viewZoomOutMenuItem.setOnAction(event -> mainController.getGUIController().getGuiState().setZoomLevel(mainController.getGUIController().getGuiState().getZoomLevel() - 0.25));

        viewZoomResetMenuItem = new MenuItem();
        viewZoomResetMenuItem.setText("_Reset Zoom");
        viewZoomResetMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.CONTROL_DOWN));
        viewZoomResetMenuItem.setOnAction(event -> mainController.getGUIController().getGuiState().setZoomLevel(1.0));

        viewZoomSlider = new Slider();
        viewZoomSlider.setMin(0.1);
        viewZoomSlider.setMax(5.0);
        viewZoomSlider.setValue(1.0);
        viewZoomSlider.valueProperty().addListener(observable -> mainController.getGUIController().getGuiState().setZoomLevel(viewZoomSlider.getValue()));

        viewZoomSliderMenuItem = new CustomMenuItem(viewZoomSlider);
        viewZoomSliderMenuItem.setHideOnClick(false);
        
        menu.getItems().addAll(
                viewMenuBarMenuItem,
                new SeparatorMenuItem(),
                viewZoomInMenuItem,
                viewZoomOutMenuItem,
                viewZoomResetMenuItem,
                viewZoomSliderMenuItem,
                new SeparatorMenuItem(),
                viewFullScreenMenuItem
        );
        
        return menu;
    }

    void selectViewMenuBarItem(boolean selected) {
        viewMenuBarMenuItem.setSelected(selected);
    }
    
    public void activateControls(boolean activate) {
        viewZoomInMenuItem.setDisable(!activate);
        viewZoomOutMenuItem.setDisable(!activate);
        viewZoomResetMenuItem.setDisable(!activate);
        viewZoomSlider.setDisable(!activate);
        viewZoomSliderMenuItem.setDisable(!activate);
    }

    @Override
    public void update() {
        GUIState guiState = mainController.getGUIController().getGuiState();
        
        viewZoomSlider.setValue(guiState.getZoomLevel());
    }
    
}
