import java.io.IOException;
import Drivers.FileManager;
import EvolutionAlgorithm.DNA;
import NeuralNetwork.DataHolder;
import NeuralNetwork.NeuralNetwork;



public class Main {

	public static void main(String[] args) throws IOException {
		
		//Change path 
		//String  csvPath = "C:\\path\\to\\testFile\\test7.csv";
		String  csvPath = "E:\\Desktop\\test7.csv";
		
        //load dataset
		DataHolder dh;
		FileManager fm = new FileManager();
        
        // getDatafile function (filepath, input, output, trainingSetSize )
		//(exempli gratia: trainingSetSize=50 will use records 1-50 as training set, the remaining records for testing)		      
		System.out.println("Loading data...");
		dh= fm.getDatafile(csvPath, 14, 10, 50);
	
	
    	//Create new dna for 14 inputs 10 ouputs, compatible with the DataSet above
    	DNA dna = new DNA(14,10);
    	
    	//choose hidden layer structure and learning rate
    	int [] structure = new int[1];
    	structure[0]=20;
    	//structure[1]=15;
    	dna.setStructure(structure);
    	dna.recursive=false; 
    	dna.learningRate=0.002;
    	
    	//print DNA parameters in the console
    	dna.printDNA();
		
    	
    	//initialise Network
    	NeuralNetwork nn = new NeuralNetwork(dna); 	
    	
    	//test the network with the data provided before or from any other data source (inputs[][], ouputs[][], records)
       	double performance=nn.testPerformance(dh.gettestingInputs(), dh.gettestingOutputs(), dh.getTestingSetSize());
       	System.out.println("Measuring performance before training");
    	System.out.println("(Sum of errors)^2 * (-1) = " + performance);
    	
    	//train the network: 15000 iterations on the trainingSet
    	System.out.println("Now training...");
    	nn.train(15000, dh);
    	
    	performance=nn.testPerformance(dh.gettestingInputs(), dh.gettestingOutputs(), dh.getTestingSetSize());
    	System.out.println("(Sum of errors)^2 * (-1) = "+performance);
    	
    	
    	//get result from one specific input array
    	double results[]=nn.feedforward(dh.gettestingInputs()[34]);
    	
    	
    	// ==== EXPORT AND IMPORT the Network ====
    	
    	
    	//save parameters and weights to text file
    	System.out.println("Exporting Neural Network and weights values...");
    	fm.saveDNAas("dna.txt", dna);
    	fm.saveWeightsAs("weights.txt", nn);
    	
    	
    	//how to reload the DNA
    	System.out.println("Importing Neural Network and weights values...");
    	DNA dna2 = fm.loadDNAFrom("dna.txt");
    	NeuralNetwork anotherNN = new NeuralNetwork(dna2);
    	
    	//how to reload the weights 
    	anotherNN.loadWeights(fm.loadWeightsFrom("weights.txt", dna2));
    	
    	//check that the performance is  the same as before and cheer
    	double perf2=anotherNN.testPerformance(dh.gettestingInputs(), dh.gettestingOutputs(), dh.getTestingSetSize());
    	System.out.println("performance is still "+perf2 + " hurray!");
    	    	

    	System.out.println("Done");

	}//end main


}//end class
