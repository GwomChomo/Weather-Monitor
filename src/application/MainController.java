package application;

import java.util.*;
import java.net.URL;
import java.rmi.RemoteException;


import connectors.TimeLapseAdapter;
import connectors.WeatherData;
import javafx.event.EventHandler;
import org.apache.axis2.AxisFault;

import connectors.MW2WeatherData;
import factory.CompositeMonitorFactory;
import factory.MonitorFactory;
import factory.SingleMonitorFactory;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import melbourneweather2.ExceptionException;

import monitor.Monitor;
import subject.Location;


public class MainController extends Controller implements Initializable{
	ArrayList<Monitor> m = new ArrayList<>();


	String choice;
	ObservableList<String> list; 
	
	ObservableList<Monitor> data;
	ArrayList<String> locations = new ArrayList<>();
	//ArrayList<Monitor> monitors= new ArrayList<>();

	WeatherData connect;

	MonitorFactory singleMonitorFactory = new SingleMonitorFactory();

	MonitorFactory compositeMonitorFactory = new CompositeMonitorFactory();

    PauseTransition wait;
	Monitor toView;
	int count = 0;

    Location location;

    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void initalizeGui(){
		locations = getLocations();
		list = FXCollections.observableArrayList(locations);
		locationsMenu.setItems(list);
	}

	public ArrayList<String> getLocations(){
        locations = connect.getLocations();
		return locations;
	}

	public String [] getTemperature(String choice){
        String [] temperature;
        temperature = connect.getTemperature(choice);
        return temperature;
    }


    public String [] getRainfall(String choice){
	    String [] rainfall;
	    rainfall = connect.getRainfall(choice);
	    return rainfall;
    }

	public void comboChanged(ActionEvent ae){
		choice = locationsMenu.getValue();
	}

	public void viewWeather(ActionEvent ae) {

		if(choice!=null){
			if(showRainfall.isSelected() && showTemperature.isSelected()){
				String [] rainfall = getRainfall(choice);
				String [] temperature = getTemperature(choice);
				m.add(compositeMonitorFactory.createCompositeMonitor(new Location(choice), temperature, rainfall));

			}
			else if(showTemperature.isSelected() ){
				String [] temperature = getTemperature(choice);
				m.add(singleMonitorFactory.createTemperatureMonitor(new Location(choice), temperature));
			}
			else if(showRainfall.isSelected()){
				String [] rainfall = getRainfall(choice);
				m.add(singleMonitorFactory.createRainfallMonitor(new Location (choice), rainfall));
			}
			if(count == 0){
				setUpTable();
				count++;
			}
			displayMonitors(m);
		}


		wait.playFromStart();
	        
	        wait.setOnFinished(new EventHandler<ActionEvent>(){
	    		@Override
	    	public void handle(javafx.event.ActionEvent event){
					refreshMonitors();

	    	}
	    	});
	}
	
	public void setUpTable(){
			locationColumn.setCellValueFactory(
				    new PropertyValueFactory<>("location")
				);
				rainfallColumn.setCellValueFactory(
				    new PropertyValueFactory<>("rainfall")
				);
				
				temperatureColumn.setCellValueFactory(
					    new PropertyValueFactory<>("temperature")
					);
				
				timeColumn.setCellValueFactory(
				    new PropertyValueFactory<>("time")
				);
	}

	public void displayMonitors(ArrayList<Monitor> m){
		data = FXCollections.observableList(m);
		mainTable.setItems(data);
	}
	
	public void removeMonitor(ActionEvent ae){
		Monitor selected;
		selected = mainTable.getSelectionModel().getSelectedItem();
		if(!mainTable.getItems().isEmpty()){
			mainTable.getItems().remove(selected);
		}
		for(Monitor monitor: m){
			if(m!= null){
				int index = m.indexOf(selected);
				m.remove(index);
			}
		}
	}
	
	public void refreshMonitors() {
		System.out.println("Updated");
		ArrayList<Monitor> toUpdate = new ArrayList<>();
		ObservableList<Monitor> currentItems;
		currentItems = mainTable.getItems();

		for(Monitor m: currentItems){
			toUpdate.add(m);
		}

		try {
			toUpdate =connect.refresh(toUpdate);
			mainTable.getItems().clear();
			mainTable.setItems( FXCollections.observableList(toUpdate));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}

		//System.out.println("Hello World");
		wait.playFromStart();
	}

	@Override
	public void viewGraph(ActionEvent ae){
		toView = mainTable.getSelectionModel().getSelectedItem();
		if(toView!= null){
			toView.view();
		}
	}



	public void connectorChoice(String which){
		if (which.equalsIgnoreCase("MW2Service")){

			try {
				connect = new MW2WeatherData();
				 wait = new PauseTransition(Duration.minutes(5));
				initalizeGui();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}
		else if(which.equalsIgnoreCase("TimeLapse")){
			try {
				connect = new TimeLapseAdapter();
				 wait = new PauseTransition(Duration.seconds(20));
				initalizeGui();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}
	}

}

