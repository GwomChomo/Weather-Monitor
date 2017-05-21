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
	


	@Override
	public TemperatureMonitor createTemperatureMonitor(Location location, String [] temperature){
		temperatureMonitor = new TemperatureMonitor(location, temperature);
		return temperatureMonitor;
		//temperatureMonitors.add(temperatureMonitor);
		//monitorList.add(temperatureMonitor);
	}

	@Override
	public RainfallMonitor createRainfallMonitor(Location location, String [] rainfall) {
		rainMonitor = new RainfallMonitor(location, rainfall);
		return rainMonitor;

	}
   
   public CompositeMonitor createCompositeMonitor(Location location, String [] temperature, String [] rainfall){
	  // System.out.println("Inside composite");
	   temperatureMonitor =  createTemperatureMonitor(location, temperature);
	   rainMonitor = createRainfallMonitor(location, rainfall);
	   compositeMonitor = new CompositeMonitor(location, temperatureMonitor, rainMonitor);
	   return compositeMonitor;

	  //System.out.println(compositeMonitor);
	  // compositeMonitors.add(compositeMonitor);
	  // monitorList.add(compositeMonitor);
	   
   }
   
   
   
   
   /*public ArrayList<CompositeMonitor> getCompositeMonitors(){
   	return compositeMonitors;
   }*/


     
}
