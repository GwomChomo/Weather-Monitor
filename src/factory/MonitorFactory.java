package factory;


import melbourneweather2.ExceptionException;

import monitor.Monitor;
import subject.Location;

import java.rmi.RemoteException;
import java.util.ArrayList;


public abstract class MonitorFactory {
	

	
	static ArrayList<Monitor> monitorList = new ArrayList<Monitor>();
	
	String [] temperature, rainfall;
	
	ArrayList<String> locations = new ArrayList<String>();
	
    public MonitorFactory(){
 
    }
    
    public abstract Monitor createTemperatureMonitor(Location location, String [] temperature);
    public abstract Monitor createRainfallMonitor(Location location, String [] rainfall);
    //public abstract ArrayList<Monitor> returnMonitors();
 
}
