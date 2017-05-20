package application;


public class Weather {
	
	private final String location;
	private String rainfall, temperature;
	private String rainTime, temperatureTime;
	
	private final int TIMESTAMPINDEX= 0;
	private final int WEATHERITEMINDEX = 1;
	
	
	public Weather(String location, String [] rainfall, String[]temperature){
		this.location = location;
		this.rainfall =rainfall[WEATHERITEMINDEX];
		rainTime = rainfall[TIMESTAMPINDEX];
		this.temperature = temperature[WEATHERITEMINDEX];
		temperatureTime = temperature [TIMESTAMPINDEX];
	}
	
	
	public String getLoc(){
		return location;
	}
	
	public String getRain(){
		return rainfall;
	}
	
	public String getTemp(){
		return temperature;
	}
	
	public String getRainTime(){
		return rainTime;
	}
	
	public String getTempTime(){
		return temperatureTime;
	}
	


}
