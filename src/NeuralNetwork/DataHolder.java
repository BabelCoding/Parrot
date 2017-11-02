package NeuralNetwork;


public class DataHolder {
	
	 public double [][] trainingInputs;
	 public double [][] trainingOuputs;
	 public double [][] testingInputs;
	 public double [][] testingOuputs;
	 
	 //data sections
	 public int inputCount;
	 public int outputCount;
	 public int trainingSetSize;
	 public int testingSetSize;
	 public int recordCount;
	 
	 
	public  DataHolder(int inputCount, int outputCount, int trainingSetSize, int records){
		 
		this.inputCount=inputCount;
		this.outputCount=outputCount;
		this.trainingSetSize=trainingSetSize;	
		this.recordCount=records;
		
		this.testingSetSize= this.recordCount-this.trainingSetSize;
			
		
		trainingInputs= new double[trainingSetSize][inputCount];
		trainingOuputs=new double[trainingSetSize][inputCount];
		testingInputs=new double[records-trainingSetSize][inputCount];
		testingOuputs=new double[records-trainingSetSize][outputCount];
	 
		
	}//constructor

	
	public void  setTrainingInputs(double[][] yourArray, int records){
		
		this.trainingInputs=null;
		this.trainingInputs=new double [records][yourArray[0].length];
		
		for(int r =0; r<records;r++ ){
			for (int i=0; i<yourArray[0].length; i++){
				this.trainingInputs[r][i]=yourArray[r][i];
			}//for each value		
		}//for each record
		
		
	}//end set trainingInputs
	
	public void  setTrainingOutputs(double[][] yourArray, int records){
		
		this.trainingOuputs=null;
		this.trainingOuputs=new double [records][yourArray[0].length];
		
		for(int r =0; r<records;r++ ){
			for (int i=0; i<yourArray[0].length; i++){
				this.trainingOuputs[r][i]=yourArray[r][i];
			}//for each value		
		}//for each record
		
		
	}//end set trainingOutputs
	
	public void  setTestingInputs(double[][] yourArray, int records){
		
		this.testingInputs=null;
		this.testingInputs=new double [records][yourArray[0].length];
		
		for(int r =0; r<records;r++ ){
			for (int i=0; i<yourArray[0].length; i++){
				this.testingInputs[r][i]=yourArray[r][i];
			}//for each value		
		}//for each record
		
		
	}//end set testingInputs
	
	public void  setTestingOutputs(double[][] yourArray, int records){
		
		this.testingOuputs=null;
		this.testingOuputs=new double [records][yourArray[0].length];
		
		for(int r =0; r<records;r++ ){
			for (int i=0; i<yourArray[0].length; i++){
				this.testingOuputs[r][i]=yourArray[r][i];
			}//for each value		
		}//for each record
		
		
	}//end set trainingOutputs
	
	public void refresh(){
		
		inputCount=this.trainingInputs[0].length;
		outputCount=this.trainingOuputs[0].length;
		trainingSetSize=this.trainingInputs.length;
		recordCount=this.trainingInputs.length+this.testingOuputs.length;
		this.testingSetSize= this.recordCount-this.trainingSetSize;
		
	}
	
}
