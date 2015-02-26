package environment;

public class EnvironmentDemo {

	public static void main(String[] args) 
	{
		//Começar por invocar o nextTurn() para gerar os valores dos sensores
		Environment.nextTurn();
		
		Environment.printEnvironmentStatus();
		
		Environment.setAC_on(true);
		Environment.increaseACTemp();
		Environment.increaseACTemp();
		Environment.increaseACTemp();
		
		Environment.nextTurn();
		Environment.printEnvironmentStatus();
	}
	
	

}
