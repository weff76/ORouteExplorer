/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author dilobachev
 */
public class MenuBar extends javafx.scene.control.MenuBar {
    
    private final MainController mainController;
    
    private MenuItem addMapMenuItem;
    private MenuItem addRouteMenuItem;
    private MenuItem fileExitMenuItem;
    
    private CheckMenuItem viewMenuBarMenuItem;
    private CheckMenuItem viewFullScreenMenuItem;

    MenuBar(MainController mainController) {
        super();
        
        this.mainController = mainController;
        
        Menu fileMenu = buildFileMenu();
        Menu viewMenu = buildViewMenu();
        
        getMenus().addAll(
                fileMenu,
                viewMenu
        );
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
        
        menu.getItems().addAll(
                viewMenuBarMenuItem,
                new SeparatorMenuItem(),
                viewFullScreenMenuItem
        );
        
        return menu;
    }

    void selectViewMenuBarItem(boolean selected) {
        viewMenuBarMenuItem.setSelected(selected);
    }
    
    
}
