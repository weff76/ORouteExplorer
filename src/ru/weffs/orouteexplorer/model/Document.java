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

/**
 *
 * @author dilobachev
 */
public class Document {
    
    private final MainController mainController;
    
    private final List<Observer> observers;
    private final List<Node> objects;
    
    private double width;
    private double height;
    
    private final boolean isSaved;
    
    public Document(MainController mainController) {
        this.mainController = mainController;
        
        objects = new ArrayList<>();
        observers = new ArrayList<>();
        
        isSaved = false;                
    }

    public void addObject(Node object) {
        objects.add(object);
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

    public List<Node> getObjects() {
        return objects;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
}
