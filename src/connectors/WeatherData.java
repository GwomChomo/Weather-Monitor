package connectors;

import java.rmi.RemoteException;
import java.util.ArrayList;

import melbourneweather2.ExceptionException;

public abstract class WeatherData {


		public void getData(String location){

			getLocations();
		}

		public abstract ArrayList<String> getLocations();

}