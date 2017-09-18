/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventType;
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
    
    private boolean isSaved;
    
    public Document(MainController mainController) {
        this.mainController = mainController;
        
        objects = new ArrayList<>();
        observers = new ArrayList<>();
        
        isSaved = false;                
    }

    public void addObject(Node object) {
        objects.add(object);
    }
    
}
