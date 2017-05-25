package monitor;

import application.SingleMonitorGraphController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import subject.Location;
import subject.Subject;

import java.util.ArrayList;

public class CompositeMonitor implements WeatherMonitor {
	ArrayList<View> views = new ArrayList<>();//List to store graph views of monitor from when it was created

	String rainfall, temperature, time, location;
	Subject subject;
	SingleMonitorGraphController controller;
	
	
	
	public CompositeMonitor(Location subject, TemperatureMonitor temperature, RainfallMonitor rain){
		this.subject = subject;
		this.subject.addMonitors(this);
		location = subject.getName();
		this.temperature = temperature.getTemperature();
		rainfall = rain.getRainfall();
		time = rain.getTime();

		if(temperature.getTemperature().equals("-") || temperature.getTemperature().equalsIgnoreCase("Trace")){
			this.temperature = "0";
		}
		else if(rain.getRainfall().equals("-") || rain.getRainfall().equalsIgnoreCase("Trace")){
			this.rainfall = "0";
		}

		views.add(new View (Double.parseDouble(this.temperature),Double.parseDouble(this.rainfall), time));
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


	public void update(String [] rainfall, String [] temperature) {
		setRainfall(rainfall);
		setTemperature(temperature);
		setTime(temperature);
		if(controller!=null){
			populateGraph();
		}
	}

	public void populateGraph(){
		views.add(new View (Double.parseDouble(this.temperature),Double.parseDouble(this.rainfall), time));
		controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(temperature), Double.parseDouble(rainfall), time);
	}

	public void view(){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/singleMonitorGraphController.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			controller = fxmlLoader.getController();
			if (!views.isEmpty()){
				for (View view: views ){

					controller.populateGraph(this.getClass().getSimpleName(),getLocation(), view.getTemperature(),view.getRainfall(), view.getTime());
				}
			}
			else{
					controller.populateGraph(this.getClass().getSimpleName(),getLocation(),Double.parseDouble(temperature), Double.parseDouble(rainfall), time);
			}


			//controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(temperature), Double.parseDouble(rainfall), time);

			Stage stage = new Stage();
			stage.setTitle("Temperature and Rainfall at " + location);
			stage.setScene(new Scene(root));

			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public String toString(){
		return location + " " + temperature + " " + rainfall + " " + time;
	}

}
