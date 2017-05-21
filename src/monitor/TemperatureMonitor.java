package monitor;

import subject.Location;
import subject.Subject;

public class TemperatureMonitor extends WeatherMonitor{
	
	public String temperature, location, time, placeholder;
	Subject subject;
	
	public TemperatureMonitor(Location subject, String [] temperature){
		this.subject = subject;
		this.subject.addMonitors(this);
		this.location = subject.getName();
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
	public void update(String rainfall, String temperature, String time) {
		this.temperature = temperature;
		this.time = time;
	}

	@Override
	public String getTime() {
		return time;
	}
	
	public String toString(){
		return location + " " + placeholder + " " +   temperature + " " + time;
	}

}
