package weatherstation.impl;

import environment.Environment;
import weatherstation.service.WeatherStationService;

public class WeatherStationImpl implements WeatherStationService {
	private static final int capacity = 10;
	Double[] leiturasTemp = new Double[capacity];
	int top = -1;
	Double[] leiturasHum = new Double[capacity];
	int top2 = -1;
	Double[] leiturasLight = new Double[capacity];
	int top3 = -1;

	public double getTempOut() {
		double t = Environment.getOutsideTemp();
		return t;
	}

	public double getHumOut() {
		double h = Environment.getOutsideHumidity();
		return h;
	}
	public double getLumOut() {
		double l = Environment.getOutsideLuminosity();
		return l;
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
	
	public void pushLight(double x) {
		if(top3 == capacity -1)
			top3 = -1;
		if(top3 < capacity - 1) {
			top3++;
			leiturasLight[top3] = x;	
		}
	}
	
	 public void printElements() {  
		   System.out.println("Leituras WeatherStation:");  
		   for (int i = 0; i < capacity; i++) {  
		    //System.out.println(leiturasTemp[i]);
		    //System.out.println(leiturasHum[i]);
		    System.out.println(leiturasLight[i]);
		   }
	 }
	 
	 public Double[] getLeiturasTemp() {
		 return this.leiturasTemp;	
	}
	 public Double[] getLeiturasHum() {
		 return this.leiturasHum;	
	}
	 public Double[] getLeiturasLight() {
		 return this.leiturasLight;	
	}
}
