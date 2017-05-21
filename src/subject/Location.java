package subject;

import monitor.Monitor;

import java.util.ArrayList;

/**
 * Created by Gwom HP on 5/21/2017.
 */
public class Location extends Subject {
    ArrayList <Monitor> monitors;
    private String name;

    public Location(String name){
        this.name = name;
        monitors = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    @Override
    public void addMonitors(Monitor monitor) {
        monitors.add(monitor);
    }

    @Override
    public void removeMonitors(Monitor monitor) {
        int index  = monitors.indexOf(monitor);
        monitors.remove(index);
    }

    @Override
    public void notifyAllMonitors() {
        for(Monitor m: monitors){
            m.update(rainfall, temperature, time);
        }
    }
}
