package monitor;

import javafx.application.Application;
import subject.Location;
import subject.Subject;

public class CompositeMonitor implements WeatherMonitor {
	
	String rainfall, temperature, time, location;
	Subject subject;
	
	
	
	public CompositeMonitor(Location subject, TemperatureMonitor temperature, RainfallMonitor rain){
		this.subject = subject;
		this.subject.addMonitors(this);
		location = subject.getName();
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

	@Override
	public void update(String [] rainfall, String [] temperature) {
		setRainfall(rainfall);
		setTemperature(temperature);
		//this.rainfall = rainfall;
		//this.time = time;
		//this.temperature = temperature;
	}

	public void viewGraph(){


	}

	public String toString(){
		return location + " " + temperature + " " + rainfall + " " + time;
	}

}
