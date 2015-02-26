package luminosidade.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import luminosidade.service.LuminosidadeService;

import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  
	  System.out.println("\nLuminosidade Service is starting!");
	  LuminosidadeImpl service = new LuminosidadeImpl();
	  
	  context.registerService(LuminosidadeService.class.getName(), service, new Hashtable<String, Object>());
	  System.out.println("Service registered: LuminosidadeService");
  }

  public void stop(BundleContext context) throws Exception {
  }
}