package monitor;

import application.SingleMonitorGraphController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import subject.Location;
import subject.Subject;

import java.util.ArrayList;

public class TemperatureMonitor implements WeatherMonitor{
	ArrayList<View> views = new ArrayList<>();

    SingleMonitorGraphController controller;
	public String temperature, location, time, placeholder, className;
	Subject subject;
	
	public TemperatureMonitor(Location subject, String [] temperature){
		this.subject = subject;
		this.subject.addMonitors(this);
		this.location = subject.getName();
		placeholder = "-";
		this.time =temperature[TIMESTAMPINDEX];
		if(temperature[TEMPERATUREINDEX].equalsIgnoreCase("-") || temperature[TEMPERATUREINDEX].equalsIgnoreCase("trace")){
			this.temperature = "0";
		}
		else
		{
			this.temperature = temperature[TEMPERATUREINDEX];
		}
		views.add(new View (Double.parseDouble(this.temperature), time));
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

	//@Override
	/*public void update(String [] rainfall, String[] temperature) {

		/*this.temperature = temperature;
		this.time = time;
	}*/

	public void populateGraph(){
		views.add(new View(Double.parseDouble(this.temperature), time));
		//System.out.println(views);
		controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(temperature), time);
    }

	public void update (String [] temperature){
		setTemperature(temperature);
		setTime(temperature);
		if(controller!=null){
            populateGraph();
        }

	}

	@Override
	public String getTime() {
		return time;
	}


	public String getType(){
		return className;
	}

	public void view (){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/singleMonitorGraphController.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                controller = fxmlLoader.getController();
               if (!views.isEmpty()){
				   for (View view: views ){

					   controller.populateGraph(this.getClass().getSimpleName(),getLocation(), view.getData(), view.getTime());
				   }
			   }
			   else{
				   controller.populateGraph(this.getClass().getSimpleName(),getLocation(), Double.parseDouble(temperature), time);
			   }


                Stage stage = new Stage();
                stage.setTitle("Temperature at " + location);
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
