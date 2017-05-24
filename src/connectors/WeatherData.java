package connectors;

import java.rmi.RemoteException;
import java.util.ArrayList;

import melbourneweather2.ExceptionException;
import monitor.Monitor;

public abstract class WeatherData extends Connect {


		public void getData(String location){

			getLocations();
		}

		public abstract ArrayList<String> getLocations();
	public abstract String [] getTemperature(String location);
	public abstract String [] getRainfall(String location);
	public abstract ArrayList<Monitor> refresh(ArrayList<Monitor> monitor) throws RemoteException, ExceptionException;

}

