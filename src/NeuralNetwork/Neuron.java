package NeuralNetwork;

import java.text.DecimalFormat;
import java.util.Random;

public class Neuron {

	int connections;
	double weights[];
	double shadoweights[];// stores previous value
	double state;
	double shadowState;
	boolean recursion;
	public double delta; //delta error
	public double learningRate=0.05;
	
	public Neuron (int connections){
		
		//initialise 
		this.connections=connections;
		this.shadowState=0;
		this.weights=new double[this.connections+2];
		this.shadoweights=new double[this.connections+2];
		this.recursion=false;
		
		//random weights
		Random r = new Random();
		for (int i=0;i<this.connections+2;i++){
			double randomValue = -1 + (2) * r.nextDouble();
			weights[i]=randomValue;
		}
	}//end constructor
	
	public double feedforward( double inputs[]){
		
		double sum=0;
		
		//compute connections
		for(int i=0;i<connections;i++)	sum=sum+inputs[i]*this.weights[i];
		
		//add artificial input
		sum=sum+1*this.weights[this.connections];
		
		//add previous state 
		sum=sum+shadowState*this.weights[this.connections+1];
		
		//save state (if recursive, otherwise it will remain forever zero)
		if(recursion && state!=0) this.shadowState=state;
		
		state=activate(sum);
		return state;
		
	}//end feedforward

	
	public double activate (double sum){
		return 1/(1+Math.exp(-sum));	
	}//end activation function
	

	
	public void train(double inputs[],double error){
		
		
		delta=state*(1-state)*error;
		
		//fix weights for connections
		for (int i=0;i<this.connections;i++){
		   shadoweights[i]=weights[i];
		   weights[i]=weights[i]+inputs[i]*delta*learningRate;
		}
		
		//fix artificial input
		weights[this.connections]=weights[this.connections]+1*delta*learningRate;
		
		//fix shadowstate
		weights[this.connections+1]=weights[this.connections+1]+shadowState*delta*learningRate;
				
	}//end train
	
	
	public double transmitError(int i){
		//this function transmits the error of the last training iteration to the upper layer
		double error;
		error=shadoweights[i]*delta;
		return error;
	}
	
	public int getConnections(){ 
            
            return connections;
     }

	public String printWeights(){
		
		String ws="";
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		
		//connections
		for(int i=0;i<this.connections;i++) 	ws=ws + numberFormat.format(weights[i])+";";
		
		//artificial input
		ws=ws + numberFormat.format(weights[this.connections])+";";
		
		//shadowState
		ws=ws + numberFormat.format(weights[this.connections+1]);
		
		return ws;
		
	}//end method
	
	public void setWeights(String weightsString){
		
		//catch errors
		if((weightsString.length()-weightsString.replace(";","").length())+1!=this.connections+2){
			
			System.out.print("incompatibility Error");
			System.out.println(weightsString);
			System.out.print("size: ");
			System.out.println(weightsString.length()-weightsString.replace(";","").length()+1);
			System.out.print("Connections: ");
			System.out.println(this.connections);
		}// end if error
		
		String arrayString []= new String[this.connections+2];
		
		arrayString= weightsString.split(";");
		
		for(int i=0;i<this.connections+2;i++) this.weights[i]=Double.parseDouble((arrayString[i]));
		
	
	}//end method
	
	
}//class
