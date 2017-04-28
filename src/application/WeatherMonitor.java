package application;

import javafx.beans.property.SimpleStringProperty;

public class WeatherMonitor extends Monitor{
	
	private SimpleStringProperty location, time, rainfall,temperature;
	
	public WeatherMonitor(String location, String rainfall, String temperature, String time){
		this.location =  new SimpleStringProperty(location);
		this.rainfall = new SimpleStringProperty(rainfall);
		this.temperature =  new SimpleStringProperty(temperature);
		this.time =  new SimpleStringProperty(time);
	}
	
	public String getLocation(){
		return location.getValue();
	}
	
	public String getRainfall(){
		return rainfall.getValue();
	}
	
	public String getTemperature(){
		return temperature.getValue();
	}
	
	public String getTime(){
		return time.getValue();
	}
	
	public String toString(){
		return "Location: " + location + " Rainfall: " + rainfall + "Temperature : " + temperature +  " Time: " + time;
	}

}
