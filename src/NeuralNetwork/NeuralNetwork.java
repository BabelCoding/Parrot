package NeuralNetwork;


import java.util.Arrays;
import EvolutionAlgorithm.DNA;


	public class NeuralNetwork {
	
	DNA dna;
	//saves the state of each neuron
    double Hmatrix[][]; 
    
    //layers
    Neuron[] outputLayer; 
    Neuron[][] hiddenLayer; 

     public NeuralNetwork (DNA netdna) {
    	 	
    	 	dna = new DNA(netdna.countInputs(), netdna.getOutputSize());
    	 	this.dna.clone(netdna);
    	 	
    	 	//count of hidden layers
    	 	int layersCount= dna.getStructure().length;  
            int neuronMax= getNetworkWidth(dna.getStructure());
            
         
            //initialise Matrix to 0
            Hmatrix= new double[layersCount][neuronMax]; 
            for (int l = 0; l < layersCount; l ++) {
                for (int n = 0; n < neuronMax; n++){
                    Hmatrix[l][n]=0;
                }
            }//end for
            
            outputLayer=new OutputNeuron[dna.getOutputSize()];
            
            //start with output layer. inputs from last hidden layer
            for (int i=0;i<dna.getOutputSize();i++) {
            	outputLayer[i]=new OutputNeuron(dna.getStructure()[layersCount-1]);
            	outputLayer[i].learningRate=dna.learningRate;
            	//TODO SET activation function
            }
                    
            //grid of hidden neurons. Only some will be activated
            hiddenLayer=new Neuron[layersCount][neuronMax];
            
            for (int l=0; l<layersCount;l++) { //for each layer    
                for (int n=0; n<neuronMax;n++){ //for each neuron
                     
                    if (n<dna.getStructure()[l]){		//...if current layer is not over
                        if (l==0){ 					//...if first layer use  inputs
                            hiddenLayer[l][n]= new Neuron(dna.countInputs());
                        }else { 					//...if any other layer use previous layer's outputs
                            hiddenLayer[l][n]= new Neuron(dna.getStructure()[l-1]);
                        }//end else
                        //set neuron properties
                        hiddenLayer[l][n].learningRate=dna.learningRate;
                        hiddenLayer[l][n].recursion=dna.recursive;
                    }// end if layer not over
                    
                }//end for each neuron
            }//end for each layer
            
        
        }//end contructor
            
     private int getNetworkWidth(int[] sizeArray) {
        	//finds the largest hidden layer
            int max=0;
            for (int i=0;i<sizeArray.length;++i) { 
                if (sizeArray[i]>max) max= sizeArray[i];
            }//fine for
            return max;
        }
	 
     private double[] selectInputs (double [] originalVector){
    			
		//returns a subset of the inputArray 
    	// new input array is shorter
		 double [] inputSelection= new double[dna.countInputs()];
		 int j=0;
		 
		 for (int i=0; i<dna.getInputSelector().length; i ++){
			 if(dna.getInputSelector()[i]){ 
				 inputSelection[j]=originalVector[i];
				 j++;
			 }//end if
		 }//end for 
	
		 return inputSelection;
   
     }
     
     public double[] feedforward(double[] inputVector){
    	 
    	 double input[]; //input for current Layer
         double prevH[]= new double [1]; //output from previous Layer
         
         //function output
         double[] result= new double [outputLayer.length];
         
           
           //for each layer
            for (int l=0;l<dna.getStructure().length;l++) {
                			
                if(l==0) {			//.. first layer gets the input vector (resized by inputSelector)
                	input= new double[dna.countInputs()]; 
                	input=Arrays.copyOf(this.selectInputs(inputVector), dna.countInputs());         
                } else { 			//... any other layer gets previous layer state
                	input= new double[prevH.length]; 
                	input=Arrays.copyOf(prevH,prevH.length);
                }//end else
                    
	            //now refresh prevH (which has size of current layer)
                prevH= new double[dna.getStructure()[l]];
                //save prevH into a matrix of states (important for backpropagation)
	            for (int n=0;n<dna.getStructure()[l];n++){ 
	                prevH[n]=hiddenLayer[l][n].feedforward(input);
	                Hmatrix[l][n]=prevH[n];
	            } //end for
	               
                //Dopo che è finita sta cazzo di odissea tra i vari nascosti, devo
                //fare il feedforward sul neurone di output.        
                if (l==dna.getStructure().length-1)  
                	for (int o=0;o<outputLayer.length;o++) result[o]=outputLayer[o].feedforward(prevH);
             
            }//end for each layer
        
        return result; 
        
        }// END FEEDFORWARD
        
     private void backPropagation(double[] inputVector, double []target){
    	
        	double[] guess =new double[outputLayer.length];
    		double[] error=new double[outputLayer.length];

    		
        	guess=this.feedforward(inputVector);
        	
        	//calculate error
        	for (int n=0;n<outputLayer.length;n++) error[n]=target[n]-guess[n];
                      
            //output training 
        	for (int n=0;n<outputLayer.length;n++) outputLayer[n].train(Hmatrix[dna.getStructure().length-1], error[n]);																											//CurrentH
            
         
        	// for each layer starting from LAST one
            for (int l=dna.getStructure().length-1; l>=0;l-- ) { 
                
                //last layer takes the error from the output layer
                if (l==dna.getStructure().length-1) {
            		            	
                	//for each neuron
                	for(int n=0; n<dna.getStructure()[l];n++){
                		//collect error for each neuron
                		double aggregatedError=0; 
            			for (int o=0;o<outputLayer.length;o++){
            				aggregatedError = aggregatedError+ outputLayer[o].transmitError(n);
            			}
            		//start the training
            		hiddenLayer[l][n].train(Hmatrix[l-1], aggregatedError);
            		}//end for each neuron
            		
                }else{
            	   	//any other layer  aggregates the error of the FOLLOWING layer
            	
	                //for each neuron 
                	for(int n=0; n<dna.getStructure()[l];n++) { 
                		//collect error for each neuron 
	            		double aggregatedError=0;
		                for (int i=0; i<dna.getStructure()[l+1];i++){  
		                	aggregatedError=aggregatedError+ hiddenLayer[l+1][i].transmitError(n);
		                }
	                
		              	//any layer but the fist takes the previous layer as input
		                if (l>0) 
		                	hiddenLayer[l][n].train( Hmatrix[l-1], aggregatedError);
		                else 
		                	// first layer I use the real inputs for the training	
		                	hiddenLayer[l][n].train(this.selectInputs(inputVector), aggregatedError);
	           
                	}//end for each neuron
            	}//end else (any other layer)																										
            }//end for each layer         
        }// ============= END BACKPROPAGATION =============
     
     public void train(double epochs, DataHolder dh){
    	 
    	//TRAIN
 		for (int epoch=1;epoch<=epochs;epoch++){		
 			for (int record=0; record<dh.trainingSetSize; record++){
 					this.backPropagation(dh.trainingInputs[record], dh.trainingOuputs[record]);
 			}//end for each records	
 		}//end for each epoque
    	 
     }//end train

     public double testPerformance(double [][] testInputs,double [][] testOutputs, int records){
 		//measures performance on the TestingSet
    	 
 		double [][] guess= new double[records][testOutputs[0].length];
 		double [][] error= new double[records][testOutputs[0].length];
 		double sumsquared=0;
 		
 		for (int r=0; r<records; r++){			
 			guess[r]=this.feedforward(testInputs[r]);		
 			for (int o=0;o<testOutputs[0].length;o++) {
 				error[r][o]=guess[r][o]-testOutputs[r][o];
 				sumsquared=sumsquared+Math.pow(error[r][o],2);
 			}//for each output			
 		}//for each record
 		
 		//I want a fitness index, so I make it negative 

 		return (-1)*sumsquared;
 		
 		}//end calculate performance;
      
     public void printStructure(){
    	 
  		 int input=0;
    	 int output=0;
    	 
    	 System.out.println("");
    	 System.out.println("----------");
    	 
    	 
    	 
    	 for (int n = 0; n < getNetworkWidth(dna.getStructure()); n ++) {
         	 System.out.println("");
        	 if(input<dna.countInputs()){System.out.print("i");}else{System.out.print(" ");}
    		 
    		 for (int l = 0; l < dna.getStructure().length; l++){
            	 if(n<dna.getStructure()[l]){System.out.print("-h");}else{System.out.print("  ");};
             }//end for layer
    		 
    		 if(output<this.outputLayer.length)System.out.print("-o");
        	 input++;
        	 output++;
    	 }//end for row
    	 
    	 //complete if there are still other inputs not yet printed
    	 System.out.println("");
    	 for(int i=0;i<dna.countInputs()-getNetworkWidth(dna.getStructure());i++)System.out.println("i");
    	 System.out.println("");
    	 System.out.println("----------");
     }//end print
     
     public String exportWeights(){
    	 
    	 
    	 //careful /n/r works only on windows. Use  System.getProperty("line.separator"); to make it work on any platform
  		String networkString="";
  		
  		//print hidden layer
    	 for (int l = 0; l < dna.getStructure().length; l++){
    		 for (int n = 0; n < getNetworkWidth(dna.getStructure()); n ++) {
    			 if(n<dna.getStructure()[l])networkString=networkString+hiddenLayer[l][n].printWeights()+"\r\n";
    		 }//for each neuron 
    	 }//end for each layer
    	
    	 //print output Layer 
    	 for (int n = 0; n < this.outputLayer.length; n ++)networkString=networkString+this.outputLayer[n].printWeights()+"\r\n";

    	 return networkString;
    	 
     }//end print
     
     public void loadWeights(String [] weightsString){
    	 
 	 	int layersCount= dna.getStructure().length;  
        int neuronMax= getNetworkWidth(dna.getStructure());
      	int k=0;
    	 
         for (int l=0; l<layersCount;l++) { //per neurone     
             for (int n=0; n<neuronMax;n++){ // e per strato     	 
            	 if (n<dna.getStructure()[l]){
	            	 hiddenLayer[l][n].setWeights(weightsString[k]);
	                 k++;
            	 }
             }//for neurons
         }//for layers
         
         //load outputLayer
         for(int n=0; n<outputLayer.length;n++){
        	outputLayer[n].setWeights(weightsString[k]);
        	k++;	 
         }
         
     }//end loadWeights
     
     
	}//end class
