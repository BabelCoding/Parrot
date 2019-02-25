package EvolutionAlgorithm;
import java.util.ArrayList;




public class Detector extends ArrayList<Double> {
	

	public double avg() {
		
		double sum=0;
		
		for (int i=0; i<this.size();i++) 	sum = sum+ this.get(i);
		
		return sum/(double) this.size();
		
	}
	
	public double  stdDev () {
		
		double sum=0;;
		double avg = this.avg();
		
		for (int i=0; i<this.size();i++) sum = sum + Math.pow((this.get(i)-avg),2);
		
		return Math.sqrt(sum/((double) this.size()-1));
		
	}
	
	public void standardise () {
		
		double avg = this.avg();
		double stddev = this.stdDev();
		
		for (int i=0; i<this.size();i++) {
			double z = (this.get(i)-avg)/stddev;
			this.set(i, z);
		}
		
		
		
	}
	
	public void detect(double sigma) {
		this.standardise();
		
		for (int i=0; i<this.size();i++) {
			
			if(this.get(i)>=sigma) this.set(i, 1.0);
			else this.set(i, 0.0);
		}
		
		
	} 


	
	
	
	
}
