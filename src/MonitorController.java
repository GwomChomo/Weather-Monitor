/**
 * Created by Ernest Keita on 4/21/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class MonitorController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("detailMonitor.fxml"));
            primaryStage.setTitle("Detailed View");
            primaryStage.setScene(new Scene(root,325,440));
            primaryStage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }



    }

}
