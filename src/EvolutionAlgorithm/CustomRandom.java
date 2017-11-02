package EvolutionAlgorithm;

import java.util.Random;

public class CustomRandom {
	
	public CustomRandom (){
		
		//costruttore
	}
	
	public int randomIntBetween (int max, int min){
		
		int randomint;
		Random r = new Random();
		randomint=(int) (min + (max - min) * r.nextDouble());
		
		return randomint;
		
	}
	
	
	public double randomDoubleBetween (double max, double min){
		
		double randomDouble;
		Random r = new Random();
		randomDouble= (min + (max - min) * r.nextDouble());
		
		return randomDouble;
		
	}
	

}
