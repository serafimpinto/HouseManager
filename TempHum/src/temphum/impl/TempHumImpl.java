package temphum.impl;

import environment.Environment;
import temphum.service.TempHumService;

public class TempHumImpl implements TempHumService{
	private static final int capacity = 10;
	Double[] leiturasTemp = new Double[capacity];
	Double[] leiturasHum = new Double[capacity];
	int top = -1;
	int top2 = -1;
	
	public double getTempIn() {
		double t = Environment.getTemperature();
		return t;
	}

	public double getHumIn() {
		double h = Environment.getHumidity();
		return h;
	}
	
	public void pushTemp(double x) {
		if(top == capacity -1)
			top = -1;
		if(top < capacity - 1) {
			top++;
			leiturasTemp[top] = x;	
		}
	}
	
	public void pushHum(double x) {
		if(top2 == capacity -1)
			top2 = -1;
		if(top2 < capacity - 1) {
			top2++;
			leiturasHum[top2] = x;	
		}
	}
	
	 public void printElements() {  
		   System.out.println("Leituras TempHum:");  
		   for (int i = 0; i < capacity; i++) {  
		    System.out.println(leiturasTemp[i]); 
		    //System.out.println(leiturasHum[i]); 
		   }
	 }
	 
	 public Double[] getLeiturasTemp() {
			return this.leiturasTemp;	
	}
	 public Double[] getLeiturasHum() {
		return this.leiturasHum;	
	}
}
