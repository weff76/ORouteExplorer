/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.shape.Path;
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
    private final List<Path> paths;
    
    private final boolean isSaved;
    
    public Document(MainController mainController) {
        this.mainController = mainController;
        
        maps = new ArrayList<>();
        paths = new ArrayList<>();
        observers = new ArrayList<>();
        
        isSaved = false;                
    }

    public void addMap(Image image) {
        maps.add(image);
    }

    public void addPath(Path path) {
        paths.add(path);
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
    
    public List<Path> getPaths() {
        return paths;
    }

    public List<Image> getMaps() {
        return maps;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
}
