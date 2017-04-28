package application;

import java.io.IOException;
import java.util.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.axis2.AxisFault;

//import org.apache.axis2.AxisFault;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;

import javafx.scene.control.CheckBox;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import melbourneweather2.ExceptionException;
import melbourneweather2.MelbourneWeather2Stub;
import melbourneweather2.MelbourneWeather2Stub.*;
import javafx.scene.control.TableColumn;
public class MainController implements Initializable{
	@FXML
	private Button refresh, remove, view;
	@FXML
	private ComboBox<String> locationsMenu;
	@FXML
	private CheckBox showRainfall;
	@FXML
	private CheckBox showTemperature;
	@FXML
	private TableView<WeatherMonitor> mainTable;
	@FXML
	private TableColumn<WeatherMonitor, String> locationColumn, rainfallColumn, temperatureColumn, timeColumn;
	
	
	Weather weather;
	ArrayList<Weather> weatherObjects =new ArrayList<Weather>();
	ArrayList<String> locs = new ArrayList<>();
	String [] rainfall; 
	String [] temperature;
	String choice;
	ArrayList<Weather> weatherDetails = new ArrayList<Weather>();
	ObservableList<String> list; 
	
	
	WeatherMonitor weatherMonitor;
	ObservableList<WeatherMonitor> data;
	ArrayList<WeatherMonitor> monitorList = new ArrayList<WeatherMonitor>();

	GetLocationsResponse LocationsResponse;
	GetRainfallResponse RainfallResponse;
	GetTemperatureResponse TemperatureResponse;
	MelbourneWeather2Stub MelbourneWeatherService;
	
	public void makeClient () throws AxisFault{
		  MelbourneWeatherService = new MelbourneWeather2Stub();
	}
	
							
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*refresh.setDisable(true);
		remove.setDisable(true);
		view.setDisable(true);*/
		ArrayList<String> locs = new ArrayList<>();
		try {
			makeClient();
		} catch (AxisFault e1) {
			e1.printStackTrace();
		}
				
		try {
			locs =getLocations();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		//fjfjfjf
		list = FXCollections.observableArrayList(locs);
		System.out.println('A');
		System.out.println(list);
		System.out.println(locationsMenu);
		locationsMenu.setItems(list);
	}
	
	
	/*public void getWeather() throws RemoteException, ExceptionException{

		  	//locs = getLocations();
		  	rainfall = new String[locs.size()];
		  	temperature = new String[locs.size()];
		  	for(int i = 0; i<locs.size();i++){
		  		rainfall = getRainfall(locs.get(i));
				temperature = getTemperature(locs.get(i));
				weather = new Weather(locs.get(i), rainfall, temperature);
				weatherObjects.add(weather);
		  	}

		 }*/
	
	
	
	public void getWeather(String choice) throws RemoteException, ExceptionException{

		rainfall = new String[locs.size()];
	  	temperature = new String[locs.size()];
	  		rainfall = getRainfall(choice);
			temperature = getTemperature(choice);
			weather = new Weather(choice, rainfall, temperature);
			weatherObjects.add(weather);

	}
	
	public ArrayList<String> getLocations() throws RemoteException, ExceptionException{
		LocationsResponse = MelbourneWeatherService.getLocations();
		locs = new ArrayList<String>(Arrays.asList(LocationsResponse.get_return()));
		return locs;
	}
		 	
	
	public String [] getRainfall(String location) throws RemoteException, ExceptionException{
		  
			 GetRainfall RainfallRequest = new GetRainfall();
			 RainfallRequest.setLocation(location);
			 RainfallResponse = MelbourneWeatherService.getRainfall(RainfallRequest);
			 rainfall= RainfallResponse.get_return();
		return rainfall;
	}
	
	
	public String [] getTemperature(String location) throws RemoteException, ExceptionException{
			 GetTemperature TemperatureRequest = new GetTemperature();
			 TemperatureRequest.setLocation(location);
			 TemperatureResponse = MelbourneWeatherService.getTemperature(TemperatureRequest);
			 temperature = TemperatureResponse.get_return();
		return temperature;
	}
	

	public void comboChanged(ActionEvent ae){
		choice = locationsMenu.getValue();
		try {
			getWeather(choice);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ExceptionException e) {
			e.printStackTrace();
		}
		for(Weather w: weatherObjects){
			if(w.getLoc().equals(choice)){
				weatherDetails.add(w);
			}
		}
	}
	
	public void viewWeather(ActionEvent ae){
        if(showTemperature.isSelected() && showRainfall.isSelected()){
            for(Weather w: weatherDetails){
                if(w.getLoc().equals(choice)){
                    weatherMonitor = new WeatherMonitor(w.getLoc(), w.getRain(),w.getTemp(), w.getTempTime());
                }
            }
            
            monitorList.add(weatherMonitor);
            if(monitorList.size() == 1){
                setUpTable(weatherMonitor);
            }
            
            updateTable(monitorList);
        }
    }
			
			

	  public void setUpTable(Monitor m){
		if (m instanceof WeatherMonitor){
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
		
	}
	
	  
	public void updateTable(List<WeatherMonitor> m){
		data = FXCollections.observableList(m);
		//data = FXCollections.observableList(monitorList);
		mainTable.setItems(data); 
	}
	
	
	
	public void removeMonitor(ActionEvent ae){
		ObservableList<WeatherMonitor> monitorSelected, allMonitors;
		allMonitors = mainTable.getItems();
		monitorSelected = mainTable.getSelectionModel().getSelectedItems();
		monitorSelected.forEach(allMonitors::remove);
		
		
	}
	
	public void refreshMonitors() throws RemoteException, ExceptionException{
		ArrayList<WeatherMonitor> toUpdate = new ArrayList<>();
		ObservableList<WeatherMonitor> currentItems;
		String [] rainfall, temperature;
		String location = null, time;
	
		currentItems = mainTable.getItems();
		for(int i = 0; i<currentItems.size(); i++){
			location = currentItems.get(i).getLocation();
			rainfall = getRainfall(location);
			temperature = getTemperature(location);
			time = rainfall[0];
			weatherMonitor = new WeatherMonitor (location, rainfall[1], temperature[1], time);
			toUpdate.add(weatherMonitor);
		}
		currentItems =  FXCollections.observableList(toUpdate);
		mainTable.setItems(currentItems);
	}
	
	@FXML
	public void changeView(ActionEvent e) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("detailMonitor.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root1));
		stage.show();
	}
	
}
