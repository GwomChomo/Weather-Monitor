package factory;



import java.rmi.RemoteException;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

import org.apache.axis2.AxisFault;

import connectors.Connector;

//import melbourneweather2.MelbourneWeather2Stub;

import melbourneweather2.ExceptionException;
import monitor.CompositeMonitor;
//import melbourneweather2.MelbourneWeather2Stub.*;
//import org.apache.axis2.AxisFault;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;

/**
 * Created by Ernest Keita on 4/28/2017.
 */


public class CompositeMonitorFactory extends MonitorFactory{
	
	ArrayList<RainfallMonitor> rainMonitors = new ArrayList<RainfallMonitor>();
	RainfallMonitor rainMonitor;
	TemperatureMonitor temperatureMonitor;
	CompositeMonitor compositeMonitor;
	ArrayList <CompositeMonitor> compositeMonitors = new ArrayList<CompositeMonitor>();
	Connector connect;
	
	public CompositeMonitorFactory(){
		try {
			connect = new Connector();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
    public void createTemperatureMonitor(String location) throws RemoteException, ExceptionException {
    	 temperatureMonitor = new TemperatureMonitor(location, connect.getTemperature(location));
    }
	
	@Override
   public void createRainfallMonitor(String location) throws RemoteException, ExceptionException{
	   	rainMonitor = new RainfallMonitor(location, connect.getRainfall(location));
    }
   
   public void createCompositeMonitor(String location) throws RemoteException, ExceptionException{
	  // System.out.println("Inside composite");
	   createTemperatureMonitor(location);
	   createRainfallMonitor(location);
	   compositeMonitor = new CompositeMonitor(rainMonitor, temperatureMonitor);
	  //System.out.println(compositeMonitor);
	  // compositeMonitors.add(compositeMonitor);
	   monitorList.add(compositeMonitor);
	   
   }
   
   
   
   
   public ArrayList<CompositeMonitor> getCompositeMonitors(){
   	return compositeMonitors;
   }




@Override
public ArrayList<Monitor> returnMonitors() {
	return monitorList;
}
   
   
     
}
