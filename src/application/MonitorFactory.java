package application;

import melbourneweather2.MelbourneWeather2Stub;

import melbourneweather2.ExceptionException;
import melbourneweather2.MelbourneWeather2Stub.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.axis2.AxisFault;

/**
 * Created by Ernest Keita on 4/28/2017.
 */
public abstract class MonitorFactory {
	
	GetLocationsResponse LocationsResponse;
	GetRainfallResponse RainfallResponse;
	GetTemperatureResponse TemperatureResponse;
	MelbourneWeather2Stub MelbourneWeatherService;
	
	static ArrayList<Monitor> monitorList = new ArrayList<Monitor>();
	
	String [] temperature, rainfall;
	
	ArrayList<String> locations = new ArrayList<String>();
	
    public MonitorFactory(){
    
    try{
    	 final MelbourneWeather2Stub MelbourneWeatherService = new MelbourneWeather2Stub();
    }catch(AxisFault a){
    	
	}
    
    }
    
    public abstract void  createTemperatureMonitor(String location) throws RemoteException, ExceptionException;
    public abstract void createRainfallMonitor(String Location) throws RemoteException, ExceptionException;
    public abstract ArrayList<Monitor> returnMonitors();
 
}
