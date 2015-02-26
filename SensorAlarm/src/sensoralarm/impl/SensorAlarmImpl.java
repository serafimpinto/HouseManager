package sensoralarm.impl;

import environment.Environment;
import sensoralarm.service.SensorAlarmService;

public class SensorAlarmImpl implements SensorAlarmService{
	
	private double tempMin, tempMax; 
	private boolean alarmingTemp;
	
	private double humMin, humMax; 
	private boolean alarmingHum;
	
	private double lampMin, lampMax; 
	private boolean alarmingLamp;
	
	private boolean window;
	private boolean alarmingWindow;
	
	public SensorAlarmImpl()
	{
		this.tempMin = 10;
		this.tempMax = 20;
		this.humMin = 0;
		this.humMax = 100;
		this.lampMin = 0;
		this.lampMax = 300;
		this.window = false;
		
		this.alarmingTemp = false;
		this.alarmingHum = false;
		this.alarmingLamp = false;
		this.alarmingWindow = false;
	};
	
	
	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		if(tempMin <= this.tempMax)
			this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		if(tempMax >= this.tempMin)
			this.tempMax = tempMax;
	}

	public boolean isAlarmingTemp() {
		double ti = Environment.getTemperature();
		if((ti < this.tempMin) || ti > this.tempMax)
			this.alarmingTemp = true;
		else
			this.alarmingTemp = false;
		
		return alarmingTemp;
	}

	public double getHumMin() {
		return humMin;
	}

	public void setHumMin(double humMin) {
		if(humMin <= this.humMax)
			this.humMin = humMin;
	}

	public double getHumMax() {
		return humMax;
	}

	public void setHumMax(double humMax) {
		if(humMax >= this.humMin)
			this.humMax = humMax;
	}

	public boolean isAlarmingHum() {
		double hi = Environment.getHumidity();
		if((hi < this.humMin) || hi > this.humMax)
			this.alarmingHum = true;
		else
			this.alarmingHum = false;
		
		return alarmingHum;
	}

	public double getLampMin() {
		return lampMin;
	}

	public void setLampMin(double lampMin) {
		if(lampMin <= this.lampMax)
			this.lampMin = lampMin;
	}

	public double getLampMax() {
		return lampMax;
	}

	public void setLampMax(double lampMax) {
		if(lampMax >= this.lampMin)
			this.lampMax = lampMax;
	}

	public boolean isAlarmingLamp() {
		double li = Environment.getLuminosity();
		if((li < this.lampMin) || li > this.lampMax)
			this.alarmingLamp = true;
		else
			this.alarmingLamp = false;
		
		return alarmingLamp;
	}

	public void setWindow(boolean window) {
		this.window = window;
	}

	public boolean isAlarmingWindow() {
		boolean w = this.window;
		if(Environment.isWindow_open() != w)
			this.alarmingWindow = true;
		else
			this.alarmingWindow = false;
		
		return alarmingWindow;
	}
	
	public boolean getWindow() {
		return this.window;
	}
}
