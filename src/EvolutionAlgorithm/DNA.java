package EvolutionAlgorithm;

import java.util.Arrays;

public class DNA {
	
	//input and output layers
	private int inputSize;
	public int outputSize;
	
	//input mask (boolean)
	public boolean inputSelector[];
	
	//hidden layer
	public int [] hLayerSize;
	
	//neuron parameters
	public boolean recursive;
	public double learningRate;
	
	//mutation parameters
	public int trainingEpochs;
	public boolean mutableStructure=true;
	public boolean mutableLearningRate=true;
	public boolean mutableInputSelector=true;
	

	public DNA (int inputSize, int outputSize){
		
		//default values		
		learningRate = 0.04; 
		trainingEpochs = 500;
		this.inputSize =inputSize;
		this.outputSize=outputSize;
		
		//inputs mask (all inputs)
		inputSelector = new boolean [inputSize];
		for (int i=0; i<inputSize; i++)inputSelector[i] = true;
		
		//default hidden structure
	    int [] defaultHLayer = new int [2];
	    defaultHLayer[0]=8; 
	    defaultHLayer[1]=4; 
		this.setStructure(defaultHLayer);

	}//end constructor
	
	public void setStructure(int[]hLayerSize){		
		this.hLayerSize=Arrays.copyOf(hLayerSize, hLayerSize.length);	
	}
	
	public void setInputSelector(boolean inputSelector[]){
		this.inputSelector=Arrays.copyOf(inputSelector, inputSelector.length);
	}
		
	public int countNeurons(){
		
		int neuronsCount=0;
		for(int i=0; i<hLayerSize.length;i++)	neuronsCount=neuronsCount+hLayerSize[i];
		neuronsCount=neuronsCount+this.outputSize;
		
		return neuronsCount;
	}
	
	public int countInputs(){
		
		int count=0;
		for(int i=0; i< inputSelector.length;i++){
			if(inputSelector[i]) count++;
		}
		return count;
	}
	
	public void randomMutation(){
		
		//TODO set max and min values as parameters
		
		CustomRandom random = new CustomRandom();
		if(mutableLearningRate)	learningRate = learningRate+random.randomDoubleBetween(0.5,-0.5);
		if(mutableInputSelector){
			for(int i=0; i<inputSelector.length; i++) {			
				if(random.randomDoubleBetween(0, 1)>0.5)
					inputSelector[i]=false;				
				else 
					inputSelector[i]=true;			
			}//end for
		}//end mutable inputSelector
		
		if(mutableStructure){
			int [] randomStructure = new int [random.randomIntBetween(10,2)];
			for(int s=0; s<randomStructure.length; s++) randomStructure[s]=random.randomIntBetween(50, 5);	
			this.hLayerSize=Arrays.copyOf(randomStructure, randomStructure.length);
			
		}//end mutable structure
	   	
	}//end randomMutation
	
	public void printDNA(){
		System.out.println("Printing DNA === ");
		System.out.println("Input Selection:");
		for(int i=0; i<inputSelector.length;i++) {
			if (inputSelector[i])
				System.out.print(" 1 |");
			else
				System.out.print(" 0 | ");	
		}//end for
		
		System.out.println("");
		System.out.println("LR: "+learningRate+"   EP: "+trainingEpochs+"  ");
		System.out.print("Structure:");
		for(int s=0; s<hLayerSize.length; s++)System.out.print(" " + hLayerSize[s]+" x ");
		System.out.println("");
		System.out.println("=== END DNA");;
		
	}
	
	public void clone(DNA original){
		
		//input output layers
		this.inputSize=original.inputSize;
		this.outputSize=original.outputSize;
		
		//inputmask
		this.setInputSelector(original.inputSelector);
		
		//hidden layer
		this.setStructure(original.hLayerSize);

		//neuron
		this.learningRate=original.learningRate;
		this.recursive=original.recursive;
		
		//evolution
		this.trainingEpochs=original.trainingEpochs;
		this.mutableStructure=original.mutableStructure;
		this.mutableLearningRate=original.mutableLearningRate;
		this.mutableInputSelector=original.mutableInputSelector;

	}
	
	
	
}
