package weatherstation.service;

public interface WeatherStationService {
	
	public double getTempOut();
	public double getHumOut();
	public double getLumOut();
	public void pushTemp(double x);
	public void pushHum(double x);
	public void pushLight(double x);
	public void printElements();
	public Double[] getLeiturasTemp();
	public Double[] getLeiturasHum();
	public Double[] getLeiturasLight();
}
