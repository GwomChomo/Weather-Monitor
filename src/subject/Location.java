package subject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import monitor.Monitor;
import monitor.RainfallMonitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gwom HP on 5/21/2017.
 */
public class Location extends Subject {
   ArrayList <Monitor> m;
    ObservableList<Monitor>monitors;
    private String name;

    public Location(String name){
        this.name = name;
        m = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    @Override
    public void addMonitors(Monitor monitor) {
        m.add(monitor);
        monitors = FXCollections.observableList(m);
    }
        //this was an arraylist
    public ArrayList<Monitor> getMonitors(){
        return m;
    }

   /* public void addMonitors(RainfallMonitor monitor){
        monitors.add(monitor);
    }*/

    @Override
    public void removeMonitors(Monitor monitor) {
        int index  = m.indexOf(monitor);
        m.remove(index);
    }

    public void clearMonitors(){
        monitors.clear();
    }

   // @Override
    /*public void notifyAllMonitors() {
        for(Monitor m: monitors){
            m.update(rainfall, temperature, time);
        }
    }*/
}
