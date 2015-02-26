package actuador.impl;

import actuador.service.ActuadorService;
import environment.Environment;

public class ActuadorImpl implements ActuadorService {

	public double getIntensityTemp() {
		double t = Environment.getACTemp();
		return t;
	}
	
	public double getIntensityHum() {
		double h = Environment.getDehumidifierIntensity();
		return h;
	}
	
	public double getIntensityLight() {
		double l = Environment.getLamIntensity();
		return l;
	}
	
	public boolean getStateAC() {
		boolean ac = Environment.isAC_on();
		return ac;
	}
	
	public boolean getStateHum() {
		boolean hum = Environment.isDehumidifier_ON();
		return hum;
	}
	
	public boolean getStateLight() {
		boolean light = Environment.isLamp_on();
		return light;
	}
	
	public boolean getStateWindow() {
		boolean wi = Environment.isWindow_open();
		return wi;
	}
	
	public void setACOn(boolean ac) {
		Environment.setAC_on(ac);
	}
	
	public void setHumOn(boolean hum) {
		Environment.setDehumidifier_ON(hum);
	}
	
	public void setLightOn(boolean lamp) {
		Environment.setLamp_on(lamp);
	}
	
	public void setWindow(boolean w) {
		Environment.setWindow_open(w);
	}
	
	public void increaseAC() {
		Environment.increaseACTemp();
	}
	
	public void decreaseAC() {
		Environment.decreaseACTemp();
	}
	
	public void increaseHum() {
		Environment.increaseDehumidifierIntensity();
	}
	
	public void decreaseHum() {
		Environment.decreaseDehumidifierIntensity();
	}
	
	public void increaseLight() {
		Environment.increaseLampIntensity();
	}
	
	public void decreaseLight() {
		Environment.decreaseLampIntensity();
	}
}