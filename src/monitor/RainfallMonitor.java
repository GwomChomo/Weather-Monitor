package monitor;

public class RainfallMonitor extends WeatherMonitor {
	
	String location, rainfall, time, placeholder;
	
	
	public RainfallMonitor(String location, String [] rainfall){
		this.location = location;
		this.rainfall = rainfall [RAINFALLINDEX];
		this.time = rainfall[TIMESTAMPINDEX];
		placeholder = "-";
	}
	
	
	public void setRainfall(String [] rain){
		rainfall = rain[ RAINFALLINDEX ];
	}
	
	public void setTime(String [] temp){
		time = temp[TIMESTAMPINDEX];
	}
	
	
	public String getLocation(){
		return location;
	}
	
	public String getRainfall(){
		return rainfall;
	}

	@Override
	public String getTime() {
		return time;
	}
	
	public String toString(){
		return location + " " +  rainfall + " " + placeholder + " " + time;
	}
	
	
	
	
	

}
