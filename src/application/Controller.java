package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import monitor.Monitor;

import java.util.ArrayList;


public abstract class Controller {
	@FXML
	public Button refresh, remove, views, graphView;
	@FXML
	public ComboBox<String> locationsMenu;
	@FXML
	public CheckBox showRainfall;
	@FXML
	public CheckBox showTemperature;
	@FXML
	public TableView<Monitor> mainTable;
	@FXML
	public TableColumn<Monitor, String> locationColumn, rainfallColumn, temperatureColumn, timeColumn;
	
	

	public abstract ArrayList<String>getLocations();
	public abstract void comboChanged(ActionEvent ae);
	public abstract void viewWeather(ActionEvent ae);
	public abstract void setUpTable();
	public abstract void displayMonitors(ArrayList<Monitor> m);
	//public abstract void removeMonitor(ActionEvent ae);
	public abstract void refreshMonitors();
	public abstract void viewGraph(ActionEvent ae);
}

