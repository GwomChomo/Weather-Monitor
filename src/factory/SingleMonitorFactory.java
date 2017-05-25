package factory;

//import java.rmi.RemoteException;
import java.util.ArrayList;

//import org.apache.axis2.AxisFault;

//import melbourneweather2.ExceptionException;
import monitor.Monitor;
import monitor.RainfallMonitor;
import monitor.TemperatureMonitor;
import subject.Location;

public class SingleMonitorFactory extends MonitorFactory  {
	TemperatureMonitor temperatureMonitor;
	RainfallMonitor rainMonitor;
	ArrayList<TemperatureMonitor>temperatureMonitors = new ArrayList<TemperatureMonitor>();
	//ArrayList<Monitor>monitorList = new ArrayList<Monitor>();
	//String [] rainfall = null;
	//String [] temperature = null;
	//Connector connect;
	//String location;

	

    @Override
    public Monitor createTemperatureMonitor(Location location, String [] temperature){

    	temperatureMonitor = new TemperatureMonitor(location, temperature);
		monitorList.add(temperatureMonitor);
		return temperatureMonitor;
  		//return temperatureMonitor;
    	//temperatureMonitors.add(temperatureMonitor);

    }


    public Monitor createRainfallMonitor(Location location, String [] rainfall) {

		//System.out.println("In factory");
    	rainMonitor = new RainfallMonitor(location, rainfall);
    	//System.out.println("Rainfall Monitor Object: " + rainMonitor);
		monitorList.add(rainMonitor);
		return rainMonitor;
    	//return rainMonitor;


    }

    public ArrayList<Monitor> returnMonitors(){
    	return monitorList;
    }
    
}
