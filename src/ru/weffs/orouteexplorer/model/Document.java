/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.weffs.orouteexplorer.model;

import java.util.ArrayList;
import java.util.List;
import ru.weffs.orouteexplorer.controller.MainController;
import ru.weffs.orouteexplorer.model.object.OMap;
import ru.weffs.orouteexplorer.model.object.ORoute;
import ru.weffs.orouteexplorer.model.object.OTrack;

/**
 *
 * @author dilobachev
 */
public class Document {
    
    private final MainController mainController;
    
    private final List<Observer> observers;
    
    private final List<OMap> oMaps;
    private final List<ORoute> oRoutes;
    private final ArrayList<OTrack> oTracks;
    
    private final boolean isSaved;
    
    public Document(MainController mainController) {
        this.mainController = mainController;
        
        oMaps = new ArrayList<>();
        oRoutes = new ArrayList<>();
        oTracks = new ArrayList<>();

        observers = new ArrayList<>();
        
        isSaved = false;                
    }

    public void addOTrack(OTrack oTrack) {
        oTrack.setEvents(mainController);
        oTracks.add(oTrack);
        notifyObservers();
    }
    
    public  ArrayList<OTrack> getOTracks() {
        return oTracks;
    }
    
    public void addOMap(OMap oMap) {
        oMaps.add(oMap);
        notifyObservers();
    }

    public void addORoute(ORoute oRoute) {
        oRoutes.add(oRoute);
        notifyObservers();
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
    
    public List<ORoute> getORoutes() {
        return oRoutes;
    }

    public List<OMap> getOMaps() {
        return oMaps;
    }
    

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

}
