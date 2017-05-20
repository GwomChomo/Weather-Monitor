package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import monitor.Monitor;



public abstract class Controller {
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
	
	
	
	
}

