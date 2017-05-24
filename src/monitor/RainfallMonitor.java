package monitor;

import application.SingleMonitorGraphController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import subject.Location;
import subject.Subject;

public class RainfallMonitor implements WeatherMonitor {
	SingleMonitorGraphController controller;
	String location, rainfall, time, placeholder;
	Subject subject;
	
	
	public RainfallMonitor(Location subject, String [] rainfall){
		this.subject = subject;
		this.subject.addMonitors(this);
		this.location = subject.getName();
		System.out.println("Monitor Location: " + location);
		if(rainfall[RAINFALLINDEX].equals("-") || rainfall[RAINFALLINDEX].equalsIgnoreCase("trace")){
				this.rainfall = "0";
		}
		this.rainfall = rainfall [RAINFALLINDEX];
		this.time = rainfall[TIMESTAMPINDEX];
		placeholder = "-";
	}

	public RainfallMonitor(String location, String [] rainfall){
		this.location = location;
		placeholder = "-";
		this.rainfall = rainfall[RAINFALLINDEX];
		this.time = rainfall[TIMESTAMPINDEX];
	}

	public RainfallMonitor(){
		System.out.println("In here");
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
	public void update(String [] rainfall, String [] temperature) {

		update(rainfall);
	}

	public void update( String [] rainfall){
		setRainfall(rainfall);
		setTime(rainfall);
	}


	public void view (){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/singleMonitorGraphController.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			controller = fxmlLoader.getController();

			controller.populateGraph(Double.parseDouble(rainfall), time);

			Stage stage = new Stage();
			stage.setTitle("Melbourne Weather Graph");
			stage.setScene(new Scene(root));


			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public String toString(){
		return location + " " +  rainfall + " " + placeholder + " " + time;
	}
	
	
	
	
	

}
