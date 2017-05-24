package application;

import java.util.*;
import java.net.URL;
import java.rmi.RemoteException;


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



	String choice;
	ObservableList<String> list; 
	
	ObservableList<Monitor> data;
	ArrayList<String> locations = new ArrayList<>();
	ArrayList<Monitor> monitors= new ArrayList<>();

	MW2WeatherData connect;
	//MonitorFactory me;
	
	SingleMonitorFactory singleMonitorFactory = new SingleMonitorFactory();

	CompositeMonitorFactory compositeMonitorFactory = new CompositeMonitorFactory();

	ArrayList<Location> subjects = new ArrayList<>();
    ArrayList<Monitor> allMonitors = new ArrayList<>();

    PauseTransition wait = new PauseTransition(Duration.seconds(5));
	Monitor toView;



    Location location;

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
        location = new Location(choice);
	}

	public void viewWeather(ActionEvent ae) {


	    /* if there is no subject, add the current location to the subject list, else if the subject in the list is same as the current location
	        dont do anything. This will allow for multiple monitors in the same location.
	     */
        if(subjects.size()<1){
            subjects.add(location);
        }
        else{
            if(!subjects.contains(location)){
                subjects.add(location);
            }

        }

		if(choice!=null){
			if(showRainfall.isSelected() && showTemperature.isSelected() ){

			        /*put an if statement here to check if there is already a composite monitor for this location.
			        * if there is, take that monitor and just add it to the list of monitors to display rather than calling the web service again.
			        * //ArrayList <CompositeMonitor> compositemonitors = new CompositeMonitor<>();
			         *
			        * */
                    int count = 0;
                    if(compositeMonitors!=null){
                        for(CompositeMonitor c: compositeMonitors){
                            if(c.getLocation().equals(choice)){
                                count ++;
                            }
                        }
                        if(count == 0){
                            String [] rainfall = getRainfall(choice);
                            String [] temperature = getTemperature(choice);
                            CompositeMonitor monitor = compositeMonitorFactory.createCompositeMonitor(location, temperature, rainfall);
                           // location.addMonitors(monitor);
                            compositeMonitors.add(monitor);
                            allMonitors.add(monitor);
                        }
                        else{
                            System.out.println("There is a composite monitor for that location");
                        }
                    }


			}
			else if(showTemperature.isSelected() ){
                int count = 0;
                if (temperatureMonitors!= null){
                    for(TemperatureMonitor r: temperatureMonitors){
                        if(r.getLocation().equals(choice)){

                            count++;
                        }
                    }

                    if (count == 0){
                        String [] temperature = getTemperature(choice);
                        TemperatureMonitor monitor= singleMonitorFactory.createTemperatureMonitor(location, temperature);
                        temperatureMonitors.add(monitor);
                        allMonitors.add(monitor);
                    }
                    else{
                        System.out.println("There is a Temperature Monitor for that location");
                    }
                }

			}
			else if(showRainfall.isSelected()){
			        int count = 0;
			        if (rainfallMonitors!= null){
                           for(RainfallMonitor r: rainfallMonitors){
                               if(r.getLocation().equals(choice)){

                                   count++;
                               }
                           }
                        if (count == 0){
                            String [] rainfall = getRainfall(choice);
                             RainfallMonitor  monitor= singleMonitorFactory.createRainfallMonitor(location, rainfall);
                            rainfallMonitors.add(monitor);
                            allMonitors.add(monitor);
                        }
                        else{
                            System.out.println("There is a Rainfall Monitor for that location");
                        }
                    }
			}


			if(allMonitors.size() == 1){
			    setUpTable();
			}


			    displayMonitors(allMonitors);
		}
		System.out.println("Number of subjects: " + subjects.size());
        for(Location l: subjects){
           System.out.println("Monitors in:  " +  l.getName() + " " + l.getMonitors());
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


			//remove monitor from general monitor list
			/*for(int i = 0; i<allMonitors.size();i++){
				allMonitors.remove(toUnsubscribe);
			}*/

			//clear all lists when table is empty. including subjects, as no one is watching them
			/*if(mainTable.getItems().isEmpty()){
				if(!subjects.isEmpty()){
					for(Location l: subjects){
						l.clearMonitors();
					}
					subjects.clear();
				}

			}*/
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
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		mainTable.getItems().clear();
		mainTable.setItems( FXCollections.observableList(toUpdate));
		System.out.println("Hello World");
		wait.playFromStart();
	}


	@Override
	public void viewGraph(ActionEvent ae){

		SingleMonitorGraphController s = new SingleMonitorGraphController();
		toView = mainTable.getSelectionModel().getSelectedItem();
		System.out.println(toView);
		toView.view();
		//System.out.println(toView);
		/*if(toView!= null){
			try {
				FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/singleMonitorGraphController.fxml"));
				Parent root = (Parent) fxmlLoader.load();
				controller = fxmlLoader.getController();

				controller.viewGraph(toView);

				Stage stage = new Stage();
				stage.setTitle("Melbourne Weather Graph");
				stage.setScene(new Scene(root));


				stage.show();

			} catch(Exception e) {
				e.printStackTrace();
			}
		}*/

	}

}

