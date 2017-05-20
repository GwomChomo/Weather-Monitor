package connectors;

import java.rmi.RemoteException;

import melbourneweather2.ExceptionException;

public abstract class Connect {
		public abstract void getData(String location) throws RemoteException, ExceptionException;
}
