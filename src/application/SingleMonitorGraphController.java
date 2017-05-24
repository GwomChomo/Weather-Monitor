package application;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Gwom HP on 5/22/2017.
 */
public class SingleMonitorGraphController implements Initializable {

    @FXML
    private NumberAxis y;

    @FXML
    private CategoryAxis time;

    @FXML
    private LineChart<?, ?> Linechart;
   // XYChart.Series  series = new XYChart.Series();
    double data;
    String timeStamp;
    XYChart.Series  series = new XYChart.Series();
    int count = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //viewGraph(Monitor monitor);


    }

    public void populateGraph(double data, String timeStamp){
        time.setLabel("Time");

        //Linechart.setAnimated(false);
        series.getData().add(new XYChart.Data(timeStamp, data));
        if(count == 0){
            Linechart.getData().add(series);
            count++;
        }


    }


   /* public void viewGraph(Monitor monitor){
            if(monitor instanceof RainfallMonitor){

               data = Double.parseDouble (((RainfallMonitor) monitor).getRainfall());
               timeStamp = monitor.getTime();
               populateGraph(data, timeStamp);
            }


            if(monitor instanceof TemperatureMonitor){
                data = Double.parseDouble(((TemperatureMonitor) monitor).getTemperature());
                timeStamp = monitor.getTime();
                populateGraph(data, timeStamp);
            }
    }*/


}
