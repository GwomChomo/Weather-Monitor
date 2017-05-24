package factory;



import java.rmi.RemoteException;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

import org.apache.axis2.AxisFault;

//import melbourneweather2.MelbourneWeather2Stub;

import melbourneweather2.ExceptionException;
import monitor.CompositeMonitor;
//import melbourneweather2.MelbourneWeather2Stub.*;
//import org.apache.axis2.AxisFault;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;
import subject.Location;

/**
 * Created by Ernest Keita on 4/28/2017.
 */


public class CompositeMonitorFactory extends MonitorFactory{
	
	ArrayList<RainfallMonitor> rainMonitors = new ArrayList<RainfallMonitor>();
	RainfallMonitor rainMonitor;
	TemperatureMonitor temperatureMonitor;
	CompositeMonitor compositeMonitor;
	ArrayList <CompositeMonitor> compositeMonitors = new ArrayList<CompositeMonitor>();
	ArrayList<Monitor>monitorList = new ArrayList<Monitor>();
	


	@Override
	public Monitor createTemperatureMonitor(Location location, String [] temperature){
		return null;
	}

	public TemperatureMonitor createTemperatureMonitor(String location, String [] temperature){
		temperatureMonitor = new TemperatureMonitor(location, temperature);
		//monitorList.add(temperatureMonitor);
		return temperatureMonitor;
		//temperatureMonitors.add(temperatureMonitor);
	}



	@Override
	public Monitor createRainfallMonitor(Location location, String [] rainfall) {
		return null;
	}


	public RainfallMonitor createRainfallMonitor(String location, String [] rainfall){
		rainMonitor = new RainfallMonitor(location, rainfall);
		return rainMonitor;
	}


   
   public Monitor createCompositeMonitor(Location location, String [] temperature, String [] rainfall){
	  // System.out.println("Inside composite");
	   temperatureMonitor =  createTemperatureMonitor(location.getName(), temperature);
	   rainMonitor = createRainfallMonitor(location.getName(), rainfall);
	   compositeMonitor = new CompositeMonitor(location, temperatureMonitor, rainMonitor);
	   monitorList.add(compositeMonitor);
	   return compositeMonitor;

	  //System.out.println(compositeMonitor);
	  // compositeMonitors.add(compositeMonitor);

	   
   }
   
   
   
   
   /*public ArrayList<CompositeMonitor> getCompositeMonitors(){
   	return compositeMonitors;
   }*/

	public ArrayList<Monitor> returnMonitors(){
		return monitorList;
	}


     
}
