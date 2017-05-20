package test;


public class Weather {
	private String rainfall;
	private String temperature;
	private String timestamp;
	//private String location;
	
	public Weather (String rainfall, String temperature, String timestamp){
		this.rainfall = rainfall;
		this.temperature = temperature;
		this.timestamp = timestamp;	
		//this.location = location;
		}
	
	public Weather() {
		// TODO Auto-generated constructor stub
	}
	
	
	/*public void setLoc(String location){
		this.location = location;
	}
	
	public String getLoc(){
		return location;
	}*/
	
	
	
	public void setTime(String timestamp){
		this.timestamp = timestamp;
	}
	
	public String getTime(){
		return timestamp;
	}
	
	
	
	public void setTemperature(String temperature){
		this.temperature = temperature;
	}
	
	public String getTemperature(){
		return temperature;
	}
	
	
	public void setRain(String rainfall){
		this.rainfall = rainfall;
	}

	public String getRain(){
		return rainfall;
	}
	
	/*
	public String toString(){
		return "location: " + location + " \n Temperature: " + temperature + " \n rainfall: " + rainfall;
	}*/
	
	public String display(){
		return toString();
	}
}
