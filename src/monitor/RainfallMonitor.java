package monitor;

import application.SingleMonitorGraphController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import subject.Location;
import subject.Subject;

import java.util.ArrayList;

public class RainfallMonitor implements WeatherMonitor {
	ArrayList<View> views = new ArrayList<>();

	SingleMonitorGraphController controller;
	String location, rainfall, time, placeholder;
	Subject subject;
	
	
	public RainfallMonitor(Location subject, String [] rainfall){
		this.subject = subject;
		this.subject.addMonitors(this);
		this.location = subject.getName();
		placeholder = "-";
		if(rainfall[RAINFALLINDEX].equals("-") || rainfall[RAINFALLINDEX].equalsIgnoreCase("trace")){
				this.rainfall = "0";
		}
		else{
			this.rainfall = rainfall [RAINFALLINDEX];
		}
		this.time = rainfall[TIMESTAMPINDEX];
		views.add(new View (Double.parseDouble(this.rainfall), time));
	}

	public RainfallMonitor(String location, String [] rainfall){
		this.location = location;
		placeholder = "-";
		this.rainfall = rainfall[RAINFALLINDEX];
		this.time = rainfall[TIMESTAMPINDEX];
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


	/*public void update(String [] rainfall, String [] temperature) {

		update(rainfall);
	}*/

	public void populateGraph(){
		views.add(new View(Double.parseDouble(this.rainfall), time));
		controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(rainfall), time);
	}

	public void update( String [] rainfall){
		setRainfall(rainfall);
		setTime(rainfall);
		if(controller!=null){
			populateGraph();
		}
	}

	public void view (){
		try {
			FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/singleMonitorGraphController.fxml"));
			Parent root = fxmlLoader.load();
			controller = fxmlLoader.getController();
			if (!views.isEmpty()){
				for (View view: views ){

					controller.populateGraph(this.getClass().getSimpleName(),getLocation(), view.getData(), view.getTime());
				}
			}
			else{
				controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(rainfall), time);
			}
			//controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(rainfall), time);

			Stage stage = new Stage();
			stage.setTitle("Rainfall at" + location);

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
