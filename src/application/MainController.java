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




public class MainController implements Initializable{
	@FXML
	private Button refresh, remove, views, details;
	@FXML
	private ComboBox<String> locationsMenu;
	@FXML
	private CheckBox showRainfall;
	@FXML
	private CheckBox showTemperature;
	@FXML
	private TableView<Monitor> mainTable;
	@FXML
	private TableColumn<Monitor, String> locationColumn, rainfallColumn, temperatureColumn, timeColumn;
	

	String choice;
	ObservableList<String> list; 
	
	ObservableList<Monitor> data;
	ArrayList<String> locations = new ArrayList<String>();
	ArrayList<Monitor> monitors= new ArrayList<Monitor>();
	
	
	RainfallMonitor rainMonitor;
	
	MelbourneWeather2Stub MelbourneWeatherService; 
	//Connector connect;
	WeatherData connect;
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
		 
		 
		 ArrayList<String> locations = new ArrayList<String>();
		 
		 try {
			locations = getLocations();
		} catch (RemoteException e) {
		
			e.printStackTrace();
		} catch (ExceptionException e) {

			e.printStackTrace();
		}
		
		list = FXCollections.observableArrayList(locations);
		locationsMenu.setItems(list);
	}
	
	public ArrayList<String> getLocations() throws RemoteException, ExceptionException{
		 locations = connect.getLocations();
		 return locations;
	}
	
	public void comboChanged(ActionEvent ae){
		choice = locationsMenu.getValue();
	}
	
	
	
	
	public void viewWeather(ActionEvent ae) throws RemoteException, ExceptionException{
		ArrayList<Monitor> m = new ArrayList<Monitor>();
		if(choice!=null){
			if(showRainfall.isSelected() && showTemperature.isSelected()  ){
				compositeMonitorFactory.createCompositeMonitor(choice);
				monitors = compositeMonitorFactory.returnMonitors();
				m.addAll(monitors);
			}
			else if(showTemperature.isSelected() ){
				singleMonitorFactory.createTemperatureMonitor(choice);
				monitors = singleMonitorFactory.returnMonitors();
				m.addAll(monitors);
			}
			else if(showRainfall.isSelected()){
				singleMonitorFactory.createRainfallMonitor(choice);
				monitors = singleMonitorFactory.returnMonitors();
				m.addAll(monitors);
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
	    		try {
					refreshMonitors();
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (ExceptionException e) {
					e.printStackTrace();
				}
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
	
	public void refreshMonitors() throws RemoteException, ExceptionException{
		ArrayList<Monitor> toUpdate = new ArrayList<>();
		ObservableList<Monitor> currentItems;
		currentItems = mainTable.getItems();
		
		for(Monitor m: currentItems){
			toUpdate.add(m);
		}
		
		toUpdate = ((MW2WeatherData) connect).refresh(toUpdate);
		mainTable.getItems().clear();
		mainTable.setItems( FXCollections.observableList(toUpdate));
		wait.playFromStart();
		
	}
}

