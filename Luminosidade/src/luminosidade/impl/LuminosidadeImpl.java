package luminosidade.impl;

import environment.Environment;
import luminosidade.service.LuminosidadeService;

public class LuminosidadeImpl implements LuminosidadeService{
	private static final int capacity = 10;
	Double[] leituras = new Double[capacity];
	int top = -1;
	
	public double getLumIn() {
		double l = Environment.getLuminosity();
		
		return l;
	}
	
	public Double[] getLeituras() {
		
		return this.leituras;	
	}
	
	public void push(double x) {
		if(top == capacity -1)
			top = -1;
		if(top < capacity - 1) {
			top++;
			leituras[top] = x;	
		}
	}
	
	 public void printElements() {  
		   System.out.println("Leituras Luminosidade:");  
		   for (int i = 0; i < capacity; i++) {  
		    System.out.println(leituras[i]);  
		   }
	 }
}

