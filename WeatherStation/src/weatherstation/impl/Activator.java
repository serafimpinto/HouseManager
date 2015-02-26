package weatherstation.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import weatherstation.service.WeatherStationService;

import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  
	  System.out.println("\nWeatherStation Service is starting!");
	  WeatherStationImpl service = new WeatherStationImpl();
	  
	  context.registerService(WeatherStationService.class.getName(), service, new Hashtable<String, Object>());
	  System.out.println("Service registered: WeatherStationService");
  }

  public void stop(BundleContext context) throws Exception {
  }
}