package leituras.impl;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import leituras.service.LeiturasService;

import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  
	  System.out.println("\nLeituras Service is starting!");
	  LeiturasImpl service = new LeiturasImpl();
	  
	  context.registerService(LeiturasService.class.getName(), service, new Hashtable<String, Object>());
	  System.out.println("Service registered: LeiturasService");
  }

  public void stop(BundleContext context) throws Exception {
  }
}