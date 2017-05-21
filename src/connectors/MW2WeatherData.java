package connectors;

import melbourneweather2.MelbourneWeather2Stub;

import melbourneweather2.ExceptionException;
import melbourneweather2.MelbourneWeather2Stub.*;
import monitor.CompositeMonitor;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;

import java.lang.Exception;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.axis2.AxisFault;

//import factory.CompositeMonitorFactory;
//import factory.SingleMonitorFactory;

//import javafx.collections.ObservableList;


public class MW2WeatherData extends WeatherData {
	
	GetLocationsResponse LocationsResponse;
	GetRainfallResponse RainfallResponse;
	GetTemperatureResponse TemperatureResponse;
	MelbourneWeather2Stub MelbourneWeatherService;
	String [] temperature, rainfall;
	//SingleMonitorFactory singleMonitor;
	//CompositeMonitorFactory compositeMonitor;
	ArrayList<String> locations = new ArrayList<String>();
	
	public MW2WeatherData() throws AxisFault{
		MelbourneWeatherService = new MelbourneWeather2Stub();
	}
	

	public String [] getTemperature(String location) {

    	GetTemperature TemperatureRequest = new GetTemperature();
		 TemperatureRequest.setLocation(location);
		try {
			TemperatureResponse = MelbourneWeatherService.getTemperature(TemperatureRequest);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		temperature = TemperatureResponse.get_return();
		 return temperature;
    }
	
	public String [] getRainfall(String location){
    	GetRainfall RainfallRequest = new GetRainfall();
		 RainfallRequest.setLocation(location);
		try {
			RainfallResponse = MelbourneWeatherService.getRainfall(RainfallRequest);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		rainfall= RainfallResponse.get_return();
		 return rainfall;
    }
	
	
	public ArrayList<Monitor> refresh(ArrayList<Monitor> monitor) throws RemoteException, ExceptionException{
		ArrayList<Monitor> updated = new ArrayList<Monitor>();
		String [] rain, temperature;
		String location;
		for(Monitor m: monitor){
			if(m instanceof CompositeMonitor){
				location = ((CompositeMonitor) m).getLocation();
				rain = getRainfall(location);
				temperature = getTemperature(location);
				((CompositeMonitor) m).setRainfall(rain);
				((CompositeMonitor) m).setTemperature(temperature);
				((CompositeMonitor) m).setTime(temperature);
				updated.add(m);
				
			}
			else if(m instanceof RainfallMonitor){
				location = ((RainfallMonitor) m).getLocation();
				rain = getRainfall(location);
				((RainfallMonitor) m).setRainfall(rain);
				((RainfallMonitor) m).setTime(rain);
				updated.add(m);
			}
			else {
				location = ((TemperatureMonitor) m).getLocation();
				temperature = getTemperature(location);
				((TemperatureMonitor) m).setTemperature(temperature);
				((TemperatureMonitor) m).setTime(temperature);
				updated.add(m);
			}
		}
		return updated;
	}
	
	public ArrayList<String> getLocations(){
		try {
			LocationsResponse = MelbourneWeatherService.getLocations();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		locations = new ArrayList<String>(Arrays.asList(LocationsResponse.get_return()));
		return locations;
    }
	
	

}
