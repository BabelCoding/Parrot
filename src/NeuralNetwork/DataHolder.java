package NeuralNetwork;


public class DataHolder {
	
	 double [][] trainingInputs;
	 double [][] trainingOuputs;
	 double [][] testingInputs;
	 double [][] testingOutputs;
	 
	 //data sections
	 int inputCount;
	 int outputCount;
	 int trainingSetSize;
	 int testingSetSize;
	 int recordCount;
	 
	 
	public  DataHolder(int inputCount, int outputCount, int trainingSetSize, int records){
		 
		this.inputCount=inputCount;
		this.outputCount=outputCount;
		this.trainingSetSize=trainingSetSize;	
		this.recordCount=records;
		
		this.testingSetSize= this.recordCount-this.trainingSetSize;
			
		
		trainingInputs= new double[trainingSetSize][inputCount];
		trainingOuputs=new double[trainingSetSize][inputCount];
		testingInputs=new double[records-trainingSetSize][inputCount];
		testingOutputs=new double[records-trainingSetSize][outputCount];
	 
		
	}//constructor

	public void  setTrainingInputs(double[][] yourArray){
		
		//update structure
		this.inputCount=yourArray[0].length;
		this.trainingSetSize=yourArray.length;
		this.recordCount = this.trainingSetSize + this.testingSetSize;
		
		//update trainingSet
		this.trainingInputs=null;
		this.trainingInputs= yourArray;
		
	}//end set trainingInputs
	
	public void  setTrainingOutputs(double[][] yourArray){
		
		//update structure
		this.outputCount=yourArray[0].length;
		this.trainingSetSize=yourArray.length;
		this.recordCount = this.trainingSetSize + this.testingSetSize;
		
		//update trainingSet 
		this.trainingOuputs=null;
		this.trainingOuputs=yourArray;
			
	}//end set trainingOutputs
	
	public void  setTestingInputs(double[][] yourArray){
		
		//update structure
		this.testingSetSize=yourArray.length;
		this.recordCount = this.trainingSetSize + this.testingSetSize;
		
		
		//update testing set 
		this.testingInputs=null;
		this.testingInputs=yourArray;
		
	}//end set testingInputs
	
	public void  setTestingOutputs(double[][] yourArray){
		
		//update structure
		this.testingSetSize=yourArray.length;
		this.recordCount = this.trainingSetSize + this.testingSetSize;
		
		
		//update testing set
		this.testingOutputs=null;
		this.testingOutputs=yourArray;
		
	}//end set trainingOutputs
	
	//get methods
	public  double [][] gettrainingInputs(){return this.trainingInputs;}
	public  double [][] gettrainingOuputs(){return this.trainingOuputs;}	
	public  double [][] gettestingInputs(){return this.testingInputs;}
	public  double [][] gettestingOutputs(){return this.testingOutputs;} 
	
	public int countInput() {return this.inputCount;}
	public int countOutput() {return this.outputCount;}
	public int getTrainingSetSize() {return this.trainingSetSize;}
	public int getTestingSetSize() {return this.testingSetSize;}
	
	
	//debug
	public void printStructure() {
		
		System.out.println("Dataset structure: "+inputCount+" input" + " and "+this.outputCount+" output. Training set is "+this.trainingSetSize+" whereas "+this.testingSetSize+" records + are for testing. Total:"+this.recordCount+" records)");
	}
	
}
