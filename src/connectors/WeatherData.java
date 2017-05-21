package connectors;

import java.rmi.RemoteException;
import java.util.ArrayList;

import melbourneweather2.ExceptionException;

public abstract class WeatherData extends Connect {


		public void getData(String location){

			getLocations();
		}

		public abstract ArrayList<String> getLocations();
	public abstract String [] getTemperature(String location);
	public abstract String [] getRainfall(String location);

}

