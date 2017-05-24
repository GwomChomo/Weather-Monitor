package monitor;

import application.SingleMonitorGraphController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import subject.Location;
import subject.Subject;

public class TemperatureMonitor implements WeatherMonitor{

    SingleMonitorGraphController controller;
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

	public void updateGraph(){
	    controller.populateGraph(Double.parseDouble(temperature), time);
    }

	public void update (String [] temperature){
		setTemperature(temperature);
		setTime(temperature);
		if(controller!=null){
            updateGraph();
        }

	}

	@Override
	public String getTime() {
		return time;
	}

	public void view (){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/singleMonitorGraphController.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                controller = fxmlLoader.getController();

                controller.populateGraph(Double.parseDouble(temperature), time);

                Stage stage = new Stage();
                stage.setTitle("Weather Graph");
                stage.setScene(new Scene(root));


                stage.show();
            } catch(Exception e) {
                e.printStackTrace();
            }

    }
	
	public String toString(){
		return location + " " + placeholder + " " +   temperature + " " + time;
	}


}
