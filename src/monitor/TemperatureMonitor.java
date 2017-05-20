package monitor;

public class TemperatureMonitor extends WeatherMonitor{
	
	public String temperature, location, time, placeholder;
	
	public TemperatureMonitor(String location, String [] temperature){
		this.location = location;
		placeholder = "-";
		this.temperature = temperature[TEMPERATUREINDEX];
		this.time =temperature[TIMESTAMPINDEX];	
	}
	
	
	public void setTemperature(String [] temp){
		temperature = temp[TEMPERATUREINDEX ];
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

	@Override
	public String getTime() {
		return time;
	}
	
	public String toString(){
		return location + " " + placeholder + " " +   temperature + " " + time;
	}

}
