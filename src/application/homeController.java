package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class homeController {
	@FXML
	private Button melbWeather;
	@FXML
	private Button timelapse;
	@FXML
	private Button exit;

	// Event Listener on Button[#melbWeather].onAction
	@FXML
	public void melbButtonHandler(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader (getClass().getResource("/application/Main.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Melbourne Weather");
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Event Listener on Button[#timelapse].onAction
	@FXML
	public void timelapseButtonHandler(ActionEvent event) {
		
	}
	// Event Listener on Button[#exit].onAction
	@FXML
	public void exitApplication(ActionEvent event) {
		// TODO Autogenerated
	}
}