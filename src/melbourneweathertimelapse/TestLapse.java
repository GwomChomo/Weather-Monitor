package melbourneweathertimelapse;
import java.lang.Exception;

import melbourneweathertimelapse.MelbourneWeatherTimeLapseStub;
import melbourneweathertimelapse.MelbourneWeatherTimeLapseStub.*;

public class TestLapse {
	public static void main(String[] args) throws Exception {

		final MelbourneWeatherTimeLapseStub MelbourneWeatherService = new MelbourneWeatherTimeLapseStub();
		
		GetLocationsResponse LocationsResponse = MelbourneWeatherService.getLocations();
		String[] Locations = LocationsResponse.get_return();
		for (int i = 0; i < Locations.length; i++) {
			GetWeather WeatherRequest = new GetWeather();
			WeatherRequest.setLocation(Locations[i]);
			GetWeatherResponse WeatherResponse = MelbourneWeatherService.getWeather(WeatherRequest);
			String[] Weather = WeatherResponse.get_return();
		
			System.out.println(
					Locations[i]
							+ " @ " + Weather[0]
							+ ":\n\tTemperature:\t" + Weather[1]
							+ "\n\tRainfall:\t" + Weather[2]
							+ "\n\n"
			);
		}
	}

}
