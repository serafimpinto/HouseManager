package actuador.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import actuador.service.ActuadorService;

import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  
	  System.out.println("\nActuador Service is starting!");
	  ActuadorImpl service = new ActuadorImpl();
	  
	  context.registerService(ActuadorService.class.getName(), service, new Hashtable<String, Object>());
	  System.out.println("Service registered: ActuadorService");
  }

  public void stop(BundleContext context) throws Exception {
  }
}
