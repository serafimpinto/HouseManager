package temphum.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import temphum.service.TempHumService;

public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  
	  System.out.println("\nTempHum Service is starting!");
	  TempHumImpl service = new TempHumImpl();
	  
	  context.registerService(TempHumService.class.getName(), service, new Hashtable<String, Object>());
	  System.out.println("Service registered: TempHumService");
  }

  public void stop(BundleContext context) throws Exception {
  }
}