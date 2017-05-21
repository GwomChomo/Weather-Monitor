package factory;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;

import melbourneweather2.ExceptionException;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;
import subject.Location;

public class SingleMonitorFactory extends MonitorFactory  {
	TemperatureMonitor temperatureMonitor;
	RainfallMonitor rainMonitor;
	ArrayList<TemperatureMonitor>temperatureMonitors = new ArrayList<TemperatureMonitor>();
	String [] rainfall = null;
	String [] temperature = null;
	//Connector connect;
	String location;
	
	
	/*public SingleMonitorFactory() {
		 try {
			connect = new Connector();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}*/
	

    @Override
    public Monitor createTemperatureMonitor(Location location, String [] temperature){
    	temperatureMonitor = new TemperatureMonitor(location, temperature);
  		return temperatureMonitor;
    	//temperatureMonitors.add(temperatureMonitor);
    	//monitorList.add(temperatureMonitor);
    }

    @Override
    public Monitor createRainfallMonitor(Location location, String [] rainfall) {
    	rainMonitor = new RainfallMonitor(location, rainfall);
    	return rainMonitor;

    	//monitorList.add(rainMonitor);
    }
    
    
    public ArrayList<TemperatureMonitor> getTemperatureMonitors(){
    	return temperatureMonitors;
    }
    
    public ArrayList<Monitor> returnMonitors(){
    	return monitorList;
    }
    
}
