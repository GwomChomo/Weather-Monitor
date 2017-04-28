
/**
 * ExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package melbourneweather2;

public class ExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1492016971058L;
    
    private melbourneweather2.MelbourneWeather2Stub.ExceptionE faultMessage;

    
        public ExceptionException() {
            super("ExceptionException");
        }

        public ExceptionException(java.lang.String s) {
           super(s);
        }

        public ExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(melbourneweather2.MelbourneWeather2Stub.ExceptionE msg){
       faultMessage = msg;
    }
    
    public melbourneweather2.MelbourneWeather2Stub.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    