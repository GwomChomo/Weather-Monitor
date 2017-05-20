package factory;


import melbourneweather2.ExceptionException;

import monitor.Monitor;

import java.rmi.RemoteException;
import java.util.ArrayList;


public abstract class MonitorFactory {
	

	
	static ArrayList<Monitor> monitorList = new ArrayList<Monitor>();
	
	String [] temperature, rainfall;
	
	ArrayList<String> locations = new ArrayList<String>();
	
    public MonitorFactory(){
 
    }
    
    public abstract void  createTemperatureMonitor(String location) throws RemoteException, ExceptionException;
    public abstract void createRainfallMonitor(String Location) throws RemoteException, ExceptionException;
    public abstract ArrayList<Monitor> returnMonitors();
 
}
