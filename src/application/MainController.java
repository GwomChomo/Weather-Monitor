package application;

import java.util.*;
import java.net.URL;
import java.rmi.RemoteException;


import connectors.TimeLapseAdapter;
import connectors.WeatherData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import monitor.CompositeMonitor;
import monitor.TemperatureMonitor;
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
import monitor.RainfallMonitor;
import subject.Location;


public class MainController extends Controller implements Initializable{
    ArrayList<Location> locs = new ArrayList<>();
    ArrayList <CompositeMonitor> compositeMonitors = new ArrayList<CompositeMonitor>();
    ArrayList <TemperatureMonitor> temperatureMonitors = new ArrayList<TemperatureMonitor>();
    ArrayList <RainfallMonitor> rainfallMonitors = new ArrayList<RainfallMonitor>();
	SingleMonitorGraphController controller;
	ArrayList<Monitor> m = new ArrayList<>();



	String choice;
	ObservableList<String> list; 
	
	ObservableList<Monitor> data;
	ArrayList<String> locations = new ArrayList<>();
	ArrayList<Monitor> monitors= new ArrayList<>();

	//MW2WeatherData connect;
	WeatherData connect;
	//MonitorFactory me;
	
	SingleMonitorFactory singleMonitorFactory = new SingleMonitorFactory();

	CompositeMonitorFactory compositeMonitorFactory = new CompositeMonitorFactory();

	ArrayList<Location> subjects = new ArrayList<>();
   // ArrayList<Monitor> allMonitors = new ArrayList<>();

    PauseTransition wait = new PauseTransition(Duration.seconds(5));
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
			if(showRainfall.isSelected() && showTemperature.isSelected()  ){
				String [] rainfall = getRainfall(choice);
				String [] temperature = getTemperature(choice);
				m.add(compositeMonitorFactory.createCompositeMonitor(new Location(choice), temperature, rainfall));
				//monitors = compositeMonitorFactory.returnMonitors();
				//m.addAll(monitors);
			}
			else if(showTemperature.isSelected() ){
				String [] temperature = getTemperature(choice);
				m.add(singleMonitorFactory.createTemperatureMonitor(new Location(choice), temperature));
				//monitors = singleMonitorFactory.returnMonitors();
				//m.addAll(monitors);
			}
			else if(showRainfall.isSelected()){
				String [] rainfall = getRainfall(choice);
				m.add(singleMonitorFactory.createRainfallMonitor(new Location (choice), rainfall));
				//monitors = singleMonitorFactory.returnMonitors();
				//m.addAll(monitors);
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
		System.out.println(data);
		mainTable.setItems(data);
		System.out.print(m.size());
	}
	
	public void removeMonitor(ActionEvent ae){
		ObservableList<Monitor> monitorSelected, allMonitors;
		allMonitors = mainTable.getItems();
		monitorSelected = mainTable.getSelectionModel().getSelectedItems();
		Monitor toUnsubscribe = mainTable.getSelectionModel().getSelectedItem();
		if(!mainTable.getItems().isEmpty()){

			//remove monitors from their respective lists. could have been done better
			if(toUnsubscribe instanceof RainfallMonitor){
				int index  = rainfallMonitors.indexOf(toUnsubscribe);
				rainfallMonitors.remove(index);
			}
			else if(toUnsubscribe instanceof TemperatureMonitor){
				int index  = temperatureMonitors.indexOf(toUnsubscribe);
				temperatureMonitors.remove(index);
			}
			else if(toUnsubscribe instanceof CompositeMonitor){
				int index  = compositeMonitors.indexOf(toUnsubscribe);
				compositeMonitors.remove(index);
			}

			//unsubscribe monitor
			location.removeMonitors(toUnsubscribe);

			//remove monitor from table
			monitorSelected.forEach(allMonitors::remove);

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
			toUpdate =connect.refresh(toUpdate);
			mainTable.getItems().clear();
			mainTable.setItems( FXCollections.observableList(toUpdate));
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}

		System.out.println("Hello World");
		wait.playFromStart();
	}

	@Override
	public void viewGraph(ActionEvent ae){
		//SingleMonitorGraphController s = new SingleMonitorGraphController();
		toView = mainTable.getSelectionModel().getSelectedItem();
		toView.view();
	}



	public void connectorChoice(String which){
		if (which.equalsIgnoreCase("MW2Service")){

			try {
				connect = new MW2WeatherData();
				initalizeGui();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}
		else if(which.equalsIgnoreCase("TimeLapse")){
			try {
				connect = new TimeLapseAdapter();
				initalizeGui();
			} catch (AxisFault e) {
				e.printStackTrace();
			}
		}
	}

}

