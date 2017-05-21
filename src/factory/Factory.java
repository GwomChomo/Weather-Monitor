package factory;

import monitor.Monitor;
import monitor.TemperatureMonitor;
import subject.Location;

/**
 * Created by Gwom HP on 5/21/2017.
 */
public interface Factory {



    public TemperatureMonitor createTemperatureMonitor(Location location, String [] temperature);
    public TemperatureMonitor createRainfallMonitor(Location location,  String [] rainfall);


}
