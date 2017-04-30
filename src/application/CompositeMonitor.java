package application;

public class CompositeMonitor extends WeatherMonitor {
	
	String rainfall, temperature, time, location;
	
	
	
	public CompositeMonitor(RainfallMonitor rain, TemperatureMonitor temperature){
		location = rain.getLocation();
		this.temperature = temperature.getTemperature();
		rainfall = rain.getRainfall();
		time = rain.getTime();
	}
	
	
	
	public void setTemperature( String [] temperature){
		this.temperature = temperature[TEMPERATUREINDEX];
	}
	
	public void setRainfall (String [] rainfall){
		this.rainfall = rainfall[RAINFALLINDEX];
	}
	
	public void setTime(String [] temp){
		time = temp[TIMESTAMPINDEX];
	}
	
	public String getLocation(){
		return location;
	}
	
	
	public String getTemperature(){
		return temperature;
	}

	public String getRainfall(){
		return rainfall;
	}
	
	
	@Override
	public String getTime() {
		return time;
	}
	
	public String toString(){
		return location + " " + temperature + " " + rainfall + " " + time;
	}

	
	
}
