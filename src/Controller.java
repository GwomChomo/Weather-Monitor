import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXML.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

/**
 * Created by Ernest Keita on 4/21/2017.
 */
public class Controller implements Initializable {
    ArrayList<Weather> array = new ArrayList<>();
    int monitorIndex= 0;
    PauseTransition wait;

    @FXML
    private javafx.scene.control.Label locName, dateTime,temp,rainFall;
    @FXML
    private javafx.scene.control.Button rightButton, leftButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeArray();
        wait= new PauseTransition(Duration.seconds(10));
        wait.play();
        rightButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                nextPage();


            }
        });

        wait.setOnFinished(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                nextPage();
                System.out.println("am here");
            }
        });
        leftButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                try{
                    if (monitorIndex >0){
                        monitorIndex -=1;
                        locName.setText(array.get(monitorIndex).getName());
                        dateTime.setText(array.get(monitorIndex).getDate());
                        temp.setText(array.get(monitorIndex).getTemp());
                        rainFall.setText(array.get(monitorIndex).getRainFall());
                        System.out.println(array);


                    }else if(monitorIndex==array.size()){

                    }

                }catch(IndexOutOfBoundsException e){

                    e.printStackTrace();
                    }

            }
        });



    }
    public void makeArray(){
        Weather weather = new Weather("Ernest","28th jan","35","25");
        array.add(weather);
        weather = new Weather("gwom","28th jan","35","25");
        array.add(weather);
        weather = new Weather("Jenyo","28th jan","35","25");
        array.add(weather);


    }

    public void nextPage(){
                if (monitorIndex <array.size()){
                    monitorIndex +=1;
                    locName.setText(array.get(monitorIndex).getName());
                    dateTime.setText(array.get(monitorIndex).getDate());
                    temp.setText(array.get(monitorIndex).getTemp());
                    rainFall.setText(array.get(monitorIndex).getRainFall());
                    System.out.println(array);


                }else if(monitorIndex==array.size()){

                }
            }


}
