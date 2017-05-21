package monitor;

import subject.Location;
import subject.Subject;

public class RainfallMonitor extends WeatherMonitor {
	
	String location, rainfall, time, placeholder;
	Subject subject;
	
	
	public RainfallMonitor(Location subject, String [] rainfall){
		this.subject.addMonitors(this);
		this.location = subject.getName();
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

	@Override
	public void update(String rainfall, String temperature, String time) {
		this.rainfall = rainfall;
		this.time = time;
	}

	public String toString(){
		return location + " " +  rainfall + " " + placeholder + " " + time;
	}
	
	
	
	
	

}