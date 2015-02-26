package temphum.service;

public interface TempHumService {
	public double getTempIn();
	public double getHumIn();
	public void pushTemp(double x);
	public void pushHum(double x);
	public void printElements();
	public Double[] getLeiturasTemp();
	public Double[] getLeiturasHum();
}
