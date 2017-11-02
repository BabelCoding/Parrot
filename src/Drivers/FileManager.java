package Drivers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import EvolutionAlgorithm.DNA;
import NeuralNetwork.DataHolder;
import NeuralNetwork.NeuralNetwork;

public class FileManager {

	public FileManager() {
		//nothing here
	}

	public DNA loadDNAFrom(String filename){
		
		DNA dnafromfile;
		File configFile = new File(filename);
		 
		try {
			
		    FileReader reader = new FileReader(configFile);
		    Properties props = new Properties();
		    props.load(reader);
		    
		    //get properties as Strings
		   
		    String inputSizeS = props.getProperty("InputSize");
		    String outputSizeS = props.getProperty("OutputSize");
		    String inputS = props.getProperty("inputSelector");
		    String tempS= props.getProperty("Structure");
		    String learningRateS = props.getProperty("LearningRate");
		    String recursiveS = props.getProperty("Recursive");
		    String traningEpocheS = props.getProperty("TraningEpoches");
		    String mutableStructureS = props.getProperty("MutableStructure");
		    String mutableLearningRateS = props.getProperty("MutableLearningRate");
		    String mutableInputSelectorS = props.getProperty("MutableInputSelector");   
		    
		    reader.close();
		    
		    //now save into DNA
		    dnafromfile= new DNA(Integer.parseInt(inputSizeS),Integer.parseInt(outputSizeS));	    
		    
		    //Hidden Layer
		    String [] stringArray  = new String[this.countItems(tempS)];
		    stringArray = tempS.split(",");
		    int [] hLayerSize = new int [stringArray.length];
		    for(int k=0; k<stringArray.length;k++)	hLayerSize[k]=Integer.parseInt(stringArray[k]);
		    dnafromfile.setStructure(hLayerSize);
		    
		    //InputSelector
		    stringArray=new String[this.countItems(inputS)];
		    stringArray = inputS.split(",");
		    boolean [] inputSelector = new boolean [stringArray.length];
		    for(int k=0; k<stringArray.length;k++)	inputSelector[k]=this.parseBool(stringArray[k]);
		    dnafromfile.setInputSelector(inputSelector);
		   
		    //other properties
		    dnafromfile.recursive=this.parseBool(recursiveS);
		    dnafromfile.learningRate=Double.parseDouble(learningRateS);
		    dnafromfile.trainingEpochs=Integer.parseInt(traningEpocheS);
		    dnafromfile.mutableStructure=this.parseBool(mutableStructureS);
		    dnafromfile.mutableLearningRate=this.parseBool(mutableLearningRateS);
		    dnafromfile.mutableInputSelector=this.parseBool(mutableInputSelectorS);
			
		    return dnafromfile;
		    
		} catch (FileNotFoundException ex) {
		    // file does not exist
			System.out.print("No Champion config found");
			return null;
		} catch (IOException ex) {
			return null;
		}
	}//end load DNA
	
	public String [] loadWeightsFrom(String filename, DNA dna){
		
		
		//load Weights from file
		try {
			//one line for each neuron
			String lineString []= new String[dna.countNeurons()]; 	
			String currentLine;
			BufferedReader br= new BufferedReader(new FileReader(filename));
			int n=0;
			while ((currentLine = br.readLine()) != null) {
				lineString[n]=currentLine;
				n++;
			}//end of file
			
			br.close();
			return lineString;
			
		} catch (IOException e) {e.printStackTrace();return null;}
	}//end load Weights	

	public void saveDNAas(String filename, DNA dna) throws IOException{
		
		System.out.println("Saving DNA to file...");
		
		//save DNA
		File configFile = new File(filename);
		 		
	    Properties props = new Properties();

	    props.setProperty("InputSize", String.valueOf(dna.inputSelector.length));
	    props.setProperty("OutputSize", String.valueOf(dna.outputSize));
	    props.setProperty("LearningRate", String.valueOf(dna.learningRate));
	    props.setProperty("Recursive", (dna.recursive)? "1":"0");
	    props.setProperty("TraningEpoches", String.valueOf(dna.trainingEpochs));
	    props.setProperty("MutableStructure", (dna.mutableStructure)? "1":"0");
	    props.setProperty("MutableLearningRate", (dna.mutableLearningRate)? "1":"0");
	    props.setProperty("MutableInputSelector", (dna.mutableInputSelector)? "1":"0");
	    
	    
	    //HLAYERSIZE save as comma delimited  3,4,5,6
	    String stringList="";
	    for(int i=0; i<dna.hLayerSize.length-1; i++) stringList=dna.hLayerSize[i]+",";
	    //last one no comma
	    stringList=stringList+dna.hLayerSize[dna.hLayerSize.length-1];
	    props.setProperty("Structure", stringList);
	    
	    //INPUTSELECTOR as comma delimited 1,1,1,0,1
	    stringList="";
	    for(int i=0; i<dna.inputSelector.length; i++){	
	    	if (dna.inputSelector[i])
	    		stringList=stringList+"1";
	    	else
	    		stringList=stringList+"0";
	    	
	    	if(i<dna.inputSelector.length-1) 
	    		stringList=stringList+",";
	    	
		}//end for	    	
	    props.setProperty("inputSelector", stringList);
	       
	    //write config file
	    FileWriter writer = new FileWriter(configFile);
	    props.store(writer, "champion settings");
	    writer.close(); 
	    
	}//end save dna
	
	public void saveWeightsAs(String filename, NeuralNetwork nn){
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);				
			bw.write(nn.saveWeights());
		}catch (IOException e) {e.printStackTrace();}
			
		finally {
			try {
				if (bw != null)	bw.close();
				if (fw != null)	fw.close();
			} catch (IOException ex) {ex.printStackTrace();}
		}//end finally
		
		
	}//end Save weights
	
	public DataHolder getDatafile(String filename, int inputCount, int outputCount, int trainingSetSize){
		
		DataHolder data = new DataHolder(inputCount, outputCount, trainingSetSize, this.countRecords(filename));
		 
		//records
		String currentLine;
		String[] stringArray;
		int r=0;
	
		try{
			
			BufferedReader br= new BufferedReader(new FileReader(filename));

			while ((currentLine = br.readLine()) != null) {
				
				int itemsCount = this.countItems(currentLine);
				
				//catch errors
				if(itemsCount<inputCount+outputCount){
					System.out.print("You've declared a wrong number of Inputs or Outputs");
					br.close();
					return null;
				}//end if error
				
				//get values
				stringArray= new String[itemsCount];
				stringArray=currentLine.split(",");
				
				if(r>0){//skip header
					
					//write values in the correct matrix (nested if)
					for(int i =0; i<itemsCount; i++){
						
						if(r-1<trainingSetSize){ 	// ... IF TRAINING DATA
							
							if(i<inputCount) 	// ... if inputSection
								data.trainingInputs[r-1][i]=Double.parseDouble(stringArray[i]);
							else				// ... if outputSection
								data.trainingOuputs[r-1][i-inputCount]=Double.parseDouble(stringArray[i]);
							
						}else{						// ... IF TESTING DATA
							if(i<inputCount) 	// ... if inputSection
								data.testingInputs[r-1-trainingSetSize][i]=Double.parseDouble(stringArray[i]);
							else				// ... if outputSection
								data.testingOuputs[r-1-trainingSetSize][i-inputCount]=Double.parseDouble(stringArray[i]);
						}//end if testing or training
					}// end for each value
					
				}//skip the header

				
				r++; // next record
			}//end of file
			br.close();
			return data;
			
		}catch(IOException e) {e.printStackTrace(); return null;}

	}//end GetdataFile
		
	private int countRecords(String filename){
		//fast record count 
		
		int count=0;
		System.out.print("Reading file..");

		try{			
			BufferedReader br= new BufferedReader(new FileReader(filename));
			while ((br.readLine()) != null) count++;
			br.close();	
		}catch(IOException e) {e.printStackTrace();}
				
		System.out.println("Record count: " + count);
		return count;
		
	}//end countRecords
	
	private int countItems(String s){
		
		if(s.length()==0) return 0;

		int count=0;
		//count commas
		count =s.length()-s.replace(",","").length();
		return count+1;

	}// end countitems
	
	private boolean parseBool (String string){
		 return (Integer.parseInt(string)==1) ? true:false;
		
	}
}
