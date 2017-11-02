package NeuralNetwork;

public class OutputNeuron extends Neuron{

	public OutputNeuron(int connections) {
		super(connections);	
	}
	

	public double activate (double sum){
		return sum;	
	}//end activation function
	
	public double transmitError(int i){
		//this function transmits the error of the last training iteration to the upper layer
		double error;
		error=shadoweights[i]*delta;
		return error;
	}
	
	public void train(double inputs[],double error){
		
		delta=error;
		
		//limit the error to avoid overflow
		if(delta>10000)
			delta=10000;
		
		if(delta<-10000)
			delta=-10000;
		
		//fix weights
		for (int i=0;i<this.connections;i++){
		   shadoweights[i]=weights[i];
		   weights[i]=weights[i]+inputs[i]*delta*learningRate;
		}
		
		//artificial input
		weights[this.connections]=weights[this.connections]+1*delta*learningRate;
		
	}//end train
	
	

}
