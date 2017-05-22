package monitor;

public interface WeatherMonitor extends Monitor{

	 final int TIMESTAMPINDEX = 0;
	final int TEMPERATUREINDEX = 1;
	 final int RAINFALLINDEX =1;
	
	public abstract String getLocation();


}
