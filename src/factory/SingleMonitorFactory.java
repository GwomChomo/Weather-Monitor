package factory;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.axis2.AxisFault;

import connectors.Connector;
import melbourneweather2.ExceptionException;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;

public class SingleMonitorFactory extends MonitorFactory  {
	TemperatureMonitor temperatureMonitor;
	RainfallMonitor rainMonitor;
	ArrayList<TemperatureMonitor>temperatureMonitors = new ArrayList<TemperatureMonitor>();
	String [] rainfall = null;
	String [] temperature = null;
	Connector connect;
	String location;
	
	
	public SingleMonitorFactory() {
		 try {
			connect = new Connector();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	

    @Override
    public void createTemperatureMonitor(String location) throws RemoteException, ExceptionException {
    	temperatureMonitor = new TemperatureMonitor(location, connect.getTemperature(location));
  
    	temperatureMonitors.add(temperatureMonitor);
    	monitorList.add(temperatureMonitor);
    }

    @Override
    public void createRainfallMonitor(String location) throws RemoteException, ExceptionException {
    	rainMonitor = new RainfallMonitor(location, connect.getRainfall(location));
    	monitorList.add(rainMonitor);
    }
    
    
    public ArrayList<TemperatureMonitor> getTemperatureMonitors(){
    	return temperatureMonitors;
    }
    
    public ArrayList<Monitor> returnMonitors(){
    	return monitorList;
    }
    
}
