package application;

import melbourneweather2.MelbourneWeather2Stub;

import melbourneweather2.ExceptionException;
import melbourneweather2.MelbourneWeather2Stub.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.axis2.AxisFault;

import javafx.collections.ObservableList;


public class Connector {
	
	GetLocationsResponse LocationsResponse;
	GetRainfallResponse RainfallResponse;
	GetTemperatureResponse TemperatureResponse;
	MelbourneWeather2Stub MelbourneWeatherService;
	String [] temperature, rainfall;
	SingleMonitorFactory singleMonitor;
	CompositeMonitorFactory compositeMonitor;
	ArrayList<String> locations = new ArrayList<String>();
	
	Connector() throws AxisFault{
		System.out.println("Inside connector constructor");
		MelbourneWeatherService = new MelbourneWeather2Stub();
	}
		 
	public String [] getTemperature(String location) throws RemoteException, ExceptionException {
		System.out.println("in temp");
    	GetTemperature TemperatureRequest = new GetTemperature();
		 TemperatureRequest.setLocation(location);
		 TemperatureResponse = MelbourneWeatherService.getTemperature(TemperatureRequest);
		 temperature = TemperatureResponse.get_return();
		 return temperature;
    }
	
	public String [] getRainfall(String location) throws RemoteException, ExceptionException{
    	GetRainfall RainfallRequest = new GetRainfall();
		 RainfallRequest.setLocation(location);
		 RainfallResponse = MelbourneWeatherService.getRainfall(RainfallRequest);
		 rainfall= RainfallResponse.get_return();
		 return rainfall;
    }
	
	
	public ArrayList<Monitor> refresh(ArrayList<Monitor> monitor) throws RemoteException, ExceptionException{
		String [] rain, temperature;
		String location;
		
		
		for(Monitor m: monitor){
			if(m instanceof CompositeMonitor){
				location = ((CompositeMonitor) m).getLocation();
				rain = getRainfall(location);
				temperature = getTemperature(location);
				System.out.println(temperature);
				System.out.println("Before : " + m);
				((CompositeMonitor) m).setRainfall(rain);
				((CompositeMonitor) m).setTemperature(temperature);
				System.out.println("After: " + m);
				
				
				///System.out.println(location);
				/* ((CompositeMonitor) m).setRainfall(getRainfall(((CompositeMonitor) m).getLocation()));
				 ((CompositeMonitor) m).setTemperature(getRainfall(((CompositeMonitor) m).getLocation()));
				 ((CompositeMonitor) m).setTime(getRainfall(((CompositeMonitor) m).getLocation()));*/
				// System.out.println("After: " + m);
			}
			else if(m instanceof RainfallMonitor){
				//System.out.println("it's a rainfall monitor");
				((RainfallMonitor) m).setRainfall(getRainfall(((RainfallMonitor) m).getLocation()));
			
				
			}
			else {
				//System.out.println("it's a temperature monitor");
				((TemperatureMonitor) m).setTemperature(getRainfall(((TemperatureMonitor) m).getLocation()));
				((TemperatureMonitor) m).setTime(getRainfall(((TemperatureMonitor) m).getLocation()));
				
			}
			
		}
		
		return monitor;
	}
	
	public ArrayList<String> getLocations() throws RemoteException, ExceptionException{
		System.out.println("inside connector get locations");
    	LocationsResponse = MelbourneWeatherService.getLocations();
		locations = new ArrayList<String>(Arrays.asList(LocationsResponse.get_return()));
		System.out.println(locations);
		return locations;
    }
	
	

}
