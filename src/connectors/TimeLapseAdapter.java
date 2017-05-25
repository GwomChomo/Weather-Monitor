package connectors;

import melbourneweathertimelapse.ExceptionException;
import melbourneweathertimelapse.*;
import monitor.CompositeMonitor;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;
import org.apache.axis2.AxisFault;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.*;


/**
 * Created by Ernest Keita on 5/21/2017.
 */
public class TimeLapseAdapter extends WeatherData {
    MelbourneWeatherTimeLapseStub service;
    ArrayList<String> locations;
    String [] temperature;
    String [] rainfall;
    MelbourneWeatherTimeLapseStub.GetWeather WeatherRequest;
    MelbourneWeatherTimeLapseStub.GetWeatherResponse WeatherResponse;

    public void setAdaptedRainfall(String[] adaptedRainfall) {
        this.adaptedRainfall = adaptedRainfall;
    }

    public void setAdaptedTemperature(String[] adaptedTemperature) {
        this.adaptedTemperature = adaptedTemperature;
    }

    String [] adaptedRainfall;
    String [] adaptedTemperature;

    public TimeLapseAdapter() throws AxisFault {
        service = new MelbourneWeatherTimeLapseStub();
        locations = new ArrayList<String>();
        adaptedTemperature =new String[2];
        adaptedRainfall =new String[2];

    }

    @Override
    public ArrayList<String> getLocations() {
        try {
            MelbourneWeatherTimeLapseStub.GetLocationsResponse LocationsResponse = service.getLocations();
            locations = new ArrayList<String>(Arrays.asList(LocationsResponse.get_return()));
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionException e) {
            e.printStackTrace();
        }

        return locations;
    }

    @Override
    public String[] getTemperature(String location) {
        WeatherRequest = new MelbourneWeatherTimeLapseStub.GetWeather();
        WeatherRequest.setLocation(location);
        try {
            WeatherResponse = service.getWeather(WeatherRequest);


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionException e) {
            e.printStackTrace();
        }
        String[] Weather = WeatherResponse.get_return();
        String time = Weather[0];
        double temperature = Double.parseDouble(Weather[1]);
        temperature = Math.round(temperature-273);
        adaptedTemperature [0]= time;
        adaptedTemperature[1] = Double.toString(temperature);
        return adaptedTemperature;
    }

    @Override
    public String[] getRainfall(String location) {
        MelbourneWeatherTimeLapseStub.GetWeather WeatherRequest = new MelbourneWeatherTimeLapseStub.GetWeather();
        WeatherRequest.setLocation(location);

        try {
            WeatherResponse = service.getWeather(WeatherRequest);



        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionException e) {
            e.printStackTrace();
        }
        String[] Weather = WeatherResponse.get_return();
        String time = Weather[0];
        double rainfall = Double.parseDouble(Weather[2]);
        rainfall = Math.round(rainfall*10);
        adaptedRainfall [0]= time;
        adaptedRainfall[1] = Double.toString(rainfall);
        return adaptedRainfall;
    }

    @Override
    public ArrayList<Monitor> refresh(ArrayList<Monitor> monitor) throws RemoteException, melbourneweather2.ExceptionException {
        ArrayList<Monitor> updated = new ArrayList<Monitor>();
        String [] rain, temperature;
        String location;
        for(Monitor m: monitor){
            if(m instanceof CompositeMonitor){
                location = m.getLocation();
                rain = getRainfall(location);
                temperature = getTemperature(location);
                ((CompositeMonitor) m).update(rain, temperature);
                updated.add(m);
            }
            else if(m instanceof RainfallMonitor){
                location = m.getLocation();
                rain = getRainfall(location);
                ((RainfallMonitor) m).update(rain);
                updated.add(m);
            }
            else {
                location = m.getLocation();
                temperature = getTemperature(location);
                ((TemperatureMonitor) m).update(temperature);
                //((TemperatureMonitor) m).setTime(temperature);
                updated.add(m);
            }
        }
        return updated;
    }


}
