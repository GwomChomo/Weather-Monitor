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
    XYChart.Series  series2 = new XYChart.Series();
    int count = 0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         //viewGraph(Monitor monitor);


    }

    public void populateGraph(String className, String location, double data, String timeStamp){




        series.getData().add(new XYChart.Data(timeStamp, data));
        if (className.equalsIgnoreCase("RainfallMonitor") || className.equalsIgnoreCase("TemperatureMonitor")){
            time.setLabel("Time");
            //y.setLabel(className);
            series.setName(className);
            if(count == 0){
                Linechart.getData().add(series);
                count++;
            }
        }
    }

    public void populateGraph (String className, String location, double data, double data2, String timeStamp){
        series.getData().add(new XYChart.Data(timeStamp, data));
        series2.getData().add(new XYChart.Data(timeStamp, data2));
        time.setLabel("Time");
        series.setName(className);
        if(count == 0){
            Linechart.getData().addAll(series, series2);
            count++;
        }
    }


}
