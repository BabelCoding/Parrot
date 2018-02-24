package EvolutionAlgorithm;

import java.util.Arrays;

public class DNA {
	
	//structure
	private int inputSize;
	private int outputSize;
	private int [] hLayerSize;
	
	//input mask (boolean)
	private boolean inputSelector[];
	
	
	//other parameters
	public boolean recursive;
	public double learningRate;
	
	public boolean mutableStructure=true;
	public boolean mutableLearningRate=true;
	public boolean mutableInputSelector=true;
	
	
	public DNA (int inputSize, int outputSize){
		
		//default values		
		learningRate = 0.04; 
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
	
	//structure GET/SET
	public void setStructure(int[]hLayerSize){		
		this.hLayerSize=Arrays.copyOf(hLayerSize, hLayerSize.length);	
	}

	public  int [] getStructure() { return this.hLayerSize; }
	
	//InputMask  GET/SET
	public void setInputSelector(boolean inputSelector[]){
		this.inputSelector=Arrays.copyOf(inputSelector, inputSelector.length);
	}
	
	public boolean [] getInputSelector() 	{	return inputSelector;	}

	//input and output SIZE  GET/SET
	public int getOutputSize	()	{	return this.outputSize;		}
	
	public void setOutputSize (int outputSize) 	{	this.outputSize =outputSize;	}

	public int getInputSize	()	{	return this.inputSize;		}
	
	public void setInputSize (int inputSize) 	{	this.inputSize =inputSize;	}

	//other
	
	public int countNeurons(){
		//counts hidden + output
		int neuronsCount=0;
		for(int i=0; i<hLayerSize.length;i++)	neuronsCount=neuronsCount+hLayerSize[i];
		neuronsCount=neuronsCount+this.outputSize;
		
		return neuronsCount;
	}
	
	public int countInputs(){
		//counts how many input the network has selected
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
		
		final int PRINTLIMIT = 20;
		
		System.out.println("Printing DNA ============ ");
		System.out.println("Input Selection:");
		for(int i=0; i<Math.min(PRINTLIMIT,inputSelector.length);i++) {
			if (inputSelector[i])
				System.out.print(" 1 |");
			else
				System.out.print(" 0 | ");	
			
			if(i==PRINTLIMIT-1)System.out.println("....");
		}//end for
		
		System.out.println("");
		System.out.println("LR: "+learningRate);
		System.out.print("Structure:");
		for(int s=0; s<hLayerSize.length; s++)System.out.print(" " + hLayerSize[s]+" x ");
		System.out.println("");
		System.out.println("Recursive: " + this.recursive);
		System.out.println("END DNA ===============  ");;
		
	}
	
	public void clone(DNA original){
		
		//input output layers
		this.inputSize=original.inputSize;
		this.outputSize=original.outputSize;
		
		//inputmask
		this.setInputSelector(original.getInputSelector());
		
		//hidden layer
		this.setStructure(original.getStructure());

		//neuron
		this.learningRate=original.learningRate;
		this.recursive=original.recursive;
		this.mutableStructure=original.mutableStructure;
		this.mutableLearningRate=original.mutableLearningRate;
		this.mutableInputSelector=original.mutableInputSelector;

	}
	

	
}//endClass
