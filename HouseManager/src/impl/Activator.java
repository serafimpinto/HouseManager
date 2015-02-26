package impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
  
  public void start(BundleContext context) throws Exception {
	  try {
		  RemoteControl frame = new RemoteControl(context);
		  frame.setVisible(true);
		  } 
	  catch (Exception e) {
		  e.printStackTrace();
		  }
  }
  
  public void stop(BundleContext context) throws Exception {
  }
}