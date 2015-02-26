package sensoralarm.service;

public interface SensorAlarmService {
	public double getTempMin();
	public void setTempMin(double tempMin);
	public double getTempMax();
	public void setTempMax(double tempMax);
	public boolean isAlarmingTemp();
	public double getHumMin();
	public void setHumMin(double humMin);
	public double getHumMax();
	public void setHumMax(double humMax);
	public boolean isAlarmingHum();
	public double getLampMin();
	public void setLampMin(double lampMin);
	public double getLampMax();
	public void setLampMax(double lampMax);
	public boolean isAlarmingLamp();
	public void setWindow(boolean window);
	public boolean isAlarmingWindow();
	public boolean getWindow();
}
