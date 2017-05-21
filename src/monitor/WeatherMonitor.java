package monitor;

public abstract class WeatherMonitor extends Monitor{

	protected final int TIMESTAMPINDEX = 0;
	protected final int TEMPERATUREINDEX = 1;
	protected final int RAINFALLINDEX =1;
	
	public abstract String getLocation();


}
