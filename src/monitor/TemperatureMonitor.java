package monitor;

import subject.Location;
import subject.Subject;

public class TemperatureMonitor implements WeatherMonitor{
	
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

	public TemperatureMonitor(String location, String [] temperature){
		this.location = location;
		placeholder = "-";
		this.temperature = temperature[TEMPERATUREINDEX];
		this.time = temperature[TIMESTAMPINDEX];
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
	public void update(String [] rainfall, String[] temperature) {

		/*this.temperature = temperature;
		this.time = time;*/
	}

	public void update (String [] temperature){
		setTemperature(temperature);
		setTime(temperature);
	}

	@Override
	public String getTime() {
		return time;
	}
	
	public String toString(){
		return location + " " + placeholder + " " +   temperature + " " + time;
	}

}
