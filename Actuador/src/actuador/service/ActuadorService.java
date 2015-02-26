package actuador.service;

public interface ActuadorService {
	
	public double getIntensityTemp();
	public double getIntensityHum();
	public double getIntensityLight();
	
	public boolean getStateAC();
	public boolean getStateHum();
	public boolean getStateLight();
	public boolean getStateWindow();
	
	public void setACOn(boolean a);
	public void setHumOn(boolean h);
	public void setLightOn(boolean l);
	public void setWindow(boolean w);
	
	public void increaseAC();
	public void decreaseAC();
	public void increaseHum();
	public void decreaseHum();
	public void increaseLight();
	public void decreaseLight();	
}
