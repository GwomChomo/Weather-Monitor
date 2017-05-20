package test;

import java.lang.Exception;
import java.rmi.RemoteException;
import java.util.ArrayList;

import melbourneweather2.ExceptionException;
import melbourneweather2.MelbourneWeather2Stub;
import melbourneweather2.MelbourneWeather2Stub.*;

public class Tester {
	// set up some constants to index into the result arrays
			private final int TimestampIndex = 0;
			private final int RainfallIndex = 1;
			private final int TemperatureIndex = 1;
			
			private  ArrayList<Weather> weatherList = new ArrayList<Weather>();
			
			public static void main(String[] args) throws Exception {
				
				Tester tester = new Tester();
				tester.start();
			}
			
			
			void start() throws RemoteException, ExceptionException{
              final MelbourneWeather2Stub MelbourneWeatherService = new MelbourneWeather2Stub();
				
				// Get the available locations from the web service
				GetLocationsResponse LocationsResponse = MelbourneWeatherService.getLocations();
				String[] Locations = LocationsResponse.get_return();
				
				//Arrays are set to the size of the locations array
				String [] rain = new String[Locations.length];
				String [] temperature = new String[Locations.length];
				
				
				// Loop over the locations, and display the temperature and rainfall at each
				for (int i = 0; i < Locations.length; i++) {
					// Get rainfall
					GetRainfall RainfallRequest = new GetRainfall();
					//send request to get the rainfall of a particular location
					RainfallRequest.setLocation(Locations[i]);
					GetRainfallResponse RainfallResponse;
					String[] Rainfall = null;
					try {
						RainfallResponse = MelbourneWeatherService.getRainfall(RainfallRequest);
						Rainfall = RainfallResponse.get_return();
					} catch (RemoteException e) {
						e.getMessage();
						e.printStackTrace();
					} catch (ExceptionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					// Get temperature
					GetTemperature TemperatureRequest = new GetTemperature();
					TemperatureRequest.setLocation(Locations[i]);
					GetTemperatureResponse TemperatureResponse = MelbourneWeatherService.getTemperature(TemperatureRequest);
					String[] Temperature = TemperatureResponse.get_return();
					
					
					
					
					createWeather(Rainfall[RainfallIndex], Temperature[TemperatureIndex], Rainfall[TimestampIndex]);					
					
					
					
					System.out.print(
						Locations[i]
						+ " @ " + Rainfall[TimestampIndex]
						+ ":\n\tTemperature:\t" + Temperature[TemperatureIndex]
						+ "\n\tRainfall:\t" + Rainfall[RainfallIndex]
						+ "\n\n"
					);
				}
				
			}
			
			
			public void createWeather(String rainfall, String temperature, String timestamp){
				Weather weather = new Weather(rainfall, temperature, timestamp);
				weatherList.add(weather);
				
			}
}
