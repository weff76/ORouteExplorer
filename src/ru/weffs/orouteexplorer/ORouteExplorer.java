/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author dilobachev
 */
public class ORouteExplorer extends Application {

    public static void exit() {
        System.exit(0);
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        new MainController(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
