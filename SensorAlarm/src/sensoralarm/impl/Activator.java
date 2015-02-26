package sensoralarm.impl;

import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import sensoralarm.service.SensorAlarmService;


public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  
	  System.out.println("\nSensorAlam Service is starting!");
	  SensorAlarmImpl service = new SensorAlarmImpl();
	  
	  context.registerService(SensorAlarmService.class.getName(), service, new Hashtable<String, Object>());
	  System.out.println("Service registered: SensorAlarmService");
  }

  public void stop(BundleContext context) throws Exception {
  }
}
