package factory;

import monitor.Monitor;
import subject.Location;

/**
 * Created by Gwom HP on 5/21/2017.
 */
public interface Factory {



    public Monitor createTemperatureMonitor(Location location, String [] temperature);
    public Monitor createRainfallMonitor(Location location,  String [] rainfall);


}
