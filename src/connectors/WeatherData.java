package connectors;

import java.rmi.RemoteException;
import java.util.ArrayList;

import melbourneweather2.ExceptionException;

public abstract class WeatherData{
	
		
		//public abstract void getWeatherData(String location);	
		public abstract ArrayList<String> getLocations() throws RemoteException, ExceptionException;
	
}
