/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.Image;
import ru.weffs.orouteexplorer.model.object.ORoute;

/**
 *
 * @author dilobachev
 */
public class Document {
    
    private final MainController mainController;
    
    private final List<Observer> observers;
    
    private final List<Image> maps;
    private final List<ORoute> oroutes;
    
    private double width;
    private double height;
    
    private final boolean isSaved;
    
    public Document(MainController mainController) {
        this.mainController = mainController;
        
        maps = new ArrayList<>();
        oroutes = new ArrayList<>();
        observers = new ArrayList<>();
        
        isSaved = false;                
    }

    public void addMap(Image image) {
        maps.add(image);
    }

    public void addRoute(ORoute oRoute) {
        oroutes.add(oRoute);
    }

    public void setDimensions(double width, double height) {
        this.width = width;
        this.height = height;
        notifyObservers();        
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
    
    public double getWidth() {
        if (width == 0) width = 1024.0;
        return width;
    }
            
    public double getHeight() {
        if (height == 0) height = 768.0;
        return height;
    }

    public List<ORoute> getRoutes() {
        return oroutes;
    }

    public List<Image> getMaps() {
        return maps;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
}
