/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model;

import java.util.ArrayList;
import java.util.List;
import ru.weffs.orouteexplorer.controller.MainController;

/**
 *
 * @author Weff
 */
public class GUIState {
    
    private final MainController mainController;
    private final List<Observer> observers;
    
    private double zoomLevel;

    public GUIState(MainController mainController) {
        this.mainController = mainController;
        this.observers = new ArrayList<>();
        
        zoomLevel = 1.0;
    }
    
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }    

    public void setZoomLevel(double zoomLevel) {
        zoomLevel = Math.min(zoomLevel, 5);
        zoomLevel = Math.max(zoomLevel, 0.1);

        this.zoomLevel = zoomLevel;
        notifyObservers();
    }

    public double getZoomLevel() {
        return zoomLevel;
    }    
}
