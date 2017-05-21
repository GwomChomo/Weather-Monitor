package subject;


import monitor.Monitor;

/**
 * Created by Gwom HP on 5/21/2017.
 */
public abstract class Subject {
    public String rainfall, temperature, time;

    public abstract void addMonitors(Monitor monitor);

    public abstract void removeMonitors(Monitor monitor);

    public abstract void notifyAllMonitors();

}


