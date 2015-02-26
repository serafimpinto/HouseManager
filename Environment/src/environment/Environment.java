package environment;

import java.util.Random;

public class Environment 
{
	//Actuadores
	//Ar condicionado
	private static boolean AC_on = false;
	private static double AC_temp = 17;
	public static void increaseACTemp(){if (AC_temp < 100) AC_temp++;};
	public static void decreaseACTemp(){if (AC_temp > 0) AC_temp--;};
	public static boolean isAC_on() {return AC_on;}
	public static void setAC_on(boolean aC_on) {AC_on = aC_on;}

	//Desumidificador
	private static boolean dehumidifier_ON = false;
	private static double dehumidifier_intensity = 50; //0 - 100
	public static void increaseDehumidifierIntensity(){if (dehumidifier_intensity < 100) dehumidifier_intensity++;}
	public static void decreaseDehumidifierIntensity(){if (dehumidifier_intensity > 0) dehumidifier_intensity--;}
	public static boolean isDehumidifier_ON() {return dehumidifier_ON;}
	public static void setDehumidifier_ON(boolean dehumidifier_ON) {Environment.dehumidifier_ON = dehumidifier_ON;}

	//Janela
	private static boolean window_open = false;
	public static boolean isWindow_open() {return window_open;}
	public static void setWindow_open(boolean window_open) {Environment.window_open = window_open;}

	//Lâmpada
	private static final int LAMP_POWER = 300;
	private static boolean lamp_on = false;
	private static double lamp_intensity = 50; //0 - 100
	public static void increaseLampIntensity(){lamp_intensity++;}
	public static void decreaseLampIntensity(){lamp_intensity--;}
	public static boolean isLamp_on() {return lamp_on;}
	public static void setLamp_on(boolean lamp_on) {Environment.lamp_on = lamp_on;}

	//Outdoors Sensors
	private static double outsideTemp = 18;
	private static double outsideHumidity = 50;
	private static double outsideLuminosity = 300; // lux
	
	public static double getOutsideTemp() {return outsideTemp;}
	public static double getOutsideHumidity() {return outsideHumidity;}
	public static double getOutsideLuminosity() {return outsideLuminosity;}

	//Indoors Sensors
	private static double temperature;
	private static double humidity;
	private static double luminosity;
	
	public static double getTemperature() {return temperature;}
	public static double getHumidity() {return humidity;}
	public static double getLuminosity() {return luminosity;}
	
	//GetIntensity
	public static double getACTemp() {return AC_temp;}
	public static double getDehumidifierIntensity() {return dehumidifier_intensity;}
	public static double getLamIntensity() {return lamp_intensity;}

	
	/**
	 * Avança a simulação 1 turno, gerando novos valores que têm em consideração alterações que entretanto tenham ocorrido nos actuadores
	 * 
	 * Deve ser invocado antes de obter valores dos sensores pela primeira vez
	 * 
	 * */
	public static void nextTurn()
	{
		Random r = new Random();
		
		//outsideTemp
		double change = 0.5 + (2 - 0.5) * r.nextDouble();
		double upOpDown = r.nextDouble();
		
		if (upOpDown > 0.5)
			outsideTemp += change;
		else
			outsideTemp -= change;
		
		//outsideHumidity
		change = 0.5 + (2 - 0.5) * r.nextDouble();
		upOpDown = r.nextDouble();

		if (upOpDown > 0.5 && outsideHumidity + change <= 100)
			outsideHumidity += change;
		else if (outsideHumidity - change >= 0)
			outsideTemp -= change;
		
		
		//outsideLuminosity
		change = 5 + (20 - 5) * r.nextDouble();
		upOpDown = r.nextDouble();

		if (upOpDown > 0.5)
			outsideLuminosity += change;
		else if (outsideLuminosity - change >= 0)
			outsideLuminosity -= change;

		
		
		
		//insideTemp
		if (window_open && AC_on)
			temperature = 0.5*outsideTemp + 0.5*AC_temp;
		else if (window_open && !AC_on)
			temperature = outsideTemp;
		else if (!window_open && !AC_on)
			temperature = 0.7*outsideTemp;
		else if (!window_open && AC_on)
			temperature = AC_temp;
		
		//insideHumidity
		if (window_open && dehumidifier_ON)
		{
			humidity = outsideHumidity - 30;
			if (humidity < 0)
				humidity = 0;
		}
		else if (window_open && !dehumidifier_ON)
		{
			humidity = outsideHumidity;
		}
		else if (!window_open && !dehumidifier_ON)
		{
			humidity = 0.1 * outsideHumidity;
		}
		
		//insideLuminosity
		if (window_open && lamp_on) luminosity = outsideLuminosity + ((lamp_intensity/100) * LAMP_POWER);
		else if (window_open && !lamp_on) luminosity = outsideLuminosity;
		else if (!window_open && lamp_on) luminosity = LAMP_POWER;
		else luminosity = 0;
	}
	
	public static void printEnvironmentStatus()
	{
		System.out.println("********************************************************");
		
		System.out.println("\n\nEstado dos actuadores:");
		System.out.println("Ar condicionado ligado: "+Environment.isAC_on());
		System.out.println("Desumidificador ligado: "+Environment.isDehumidifier_ON());
		System.out.println("Lâmpada ligada: "+Environment.isLamp_on());
		System.out.println("Janela aberta: "+Environment.isWindow_open());
		
		System.out.println("\n\nTempo no exterior: ");
		System.out.println("Humidade: "+Environment.getOutsideHumidity()+"%");
		System.out.println("Temperatura: "+Environment.getOutsideTemp()+" º");
		System.out.println("Luminosidade: "+Environment.getOutsideLuminosity()+" lux");
		
		System.out.println("\n\nTempo no interior: ");
		System.out.println("Humidade: "+Environment.getHumidity()+"%");
		System.out.println("Temperatura: "+Environment.getTemperature()+" º");
		System.out.println("Luminosidade: "+Environment.getLuminosity()+" lux");
	}
	
}
