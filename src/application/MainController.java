package application;

import java.util.*;
import java.net.URL;
import java.rmi.RemoteException;




import org.apache.axis2.AxisFault;

import connectors.MW2WeatherData;
//import connectors.Connector;
import connectors.WeatherData;
import factory.CompositeMonitorFactory;
import factory.MonitorFactory;
import factory.SingleMonitorFactory;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;

import javafx.scene.control.CheckBox;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import melbourneweather2.ExceptionException;
import melbourneweather2.MelbourneWeather2Stub;
import monitor.Monitor;
import monitor.RainfallMonitor;
import javafx.scene.control.TableColumn;
import subject.Location;


public class MainController extends Controller implements Initializable{


	String choice;
	ObservableList<String> list; 
	
	ObservableList<Monitor> data;
	ArrayList<String> locations = new ArrayList<String>();
	ArrayList<Monitor> monitors= new ArrayList<Monitor>();
	
	
	//RainfallMonitor rainMonitor;
	
	//MelbourneWeather2Stub MelbourneWeatherService;
	//Connector connect;
	MW2WeatherData connect;
	MonitorFactory me;
	
	SingleMonitorFactory singleMonitorFactory = new SingleMonitorFactory();
	CompositeMonitorFactory compositeMonitorFactory = new CompositeMonitorFactory();
	
	PauseTransition wait = new PauseTransition(Duration.minutes(5));

	
							
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 try {
			//connect = new Connector();
			 connect = new MW2WeatherData();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		 
		 
		// ArrayList<String> locations = new ArrayList<String>();
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
		ArrayList<Monitor> m = new ArrayList<Monitor>();
		if(choice!=null){
			if(showRainfall.isSelected() && showTemperature.isSelected()  ){
				    String [] rainfall = getRainfall(choice);
				    String [] temperature = getTemperature(choice);
				    Location location = new Location(choice);
					Monitor monitor = compositeMonitorFactory.createCompositeMonitor(location, temperature, rainfall);
					location.addMonitors(monitor);


				//monitors = compositeMonitorFactory.returnMonitors();
				//m.addAll(monitors);
			}
			else if(showTemperature.isSelected() ){
                    String [] temperature = getTemperature(choice);
                    Location location = new Location(choice);
                    Monitor monitor = singleMonitorFactory.createTemperatureMonitor(location, temperature);
                    location.addMonitors(monitor);
					//singleMonitorFactory.createTemperatureMonitor(choice);

				//monitors = singleMonitorFactory.returnMonitors();
				//m.addAll(monitors);
			}
			else if(showRainfall.isSelected()){
                    String [] rainfall = getRainfall(choice);
                    Location location = new Location(choice);
                    Monitor monitor = singleMonitorFactory.createRainfallMonitor(location, rainfall);
                    location.addMonitors(monitor);

				//monitors = singleMonitorFactory.returnMonitors();
				//m.addAll(monitors);
			} 
			if(monitors.size() == 1){
				setUpTable();
			}
			displayMonitors(m);
		}
     
	      
	        wait.playFromStart();
	        
	        wait.setOnFinished(new EventHandler<javafx.event.ActionEvent>(){
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
		ObservableList<Monitor> monitorSelected, allMonitors;
		allMonitors = mainTable.getItems();
		monitorSelected = mainTable.getSelectionModel().getSelectedItems();
		monitorSelected.forEach(allMonitors::remove);
		
		for(int i = 0; i<monitors.size();i++){
			monitors.remove(monitorSelected);
		}
		
		if(mainTable.getItems().isEmpty()){
			monitors.clear();
		}
	}
	
	public void refreshMonitors() {

		ArrayList<Monitor> toUpdate = new ArrayList<>();
		ObservableList<Monitor> currentItems;
		currentItems = mainTable.getItems();

		for(Monitor m: currentItems){
			toUpdate.add(m);
		}

		try {
			toUpdate = ((MW2WeatherData) connect).refresh(toUpdate);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		mainTable.getItems().clear();
		mainTable.setItems( FXCollections.observableList(toUpdate));
		wait.playFromStart();
		
	}
}

