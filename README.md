![logoparrot](https://user-images.githubusercontent.com/21087227/32345744-078f0daa-c003-11e7-84ee-b7242ba06ee6.png)


Project Parrot is an object-oriented implementation of a Neural Network, trainable by backpropagation. The network structure is defined by a tuple of parameters contained in the class DNA, which makes it conveniently combinable with evolutionary algorithms. 

The network structure can be set up with very few intuitive commads:

![1](https://user-images.githubusercontent.com/21087227/32345566-7a91cfb4-c002-11e7-9f05-a11ea82f676a.png)




The main contains a working example with all the methods available to pull the data, customize the structure, train the network export and save results and the network itself. 



#Note on the data structure: 

![2](https://user-images.githubusercontent.com/21087227/32345507-5289e5e2-c002-11e7-9bc5-51ba7a5754fc.PNG)


An implementation of one evolutionary algorithm is project HelloDarwin, which extends the Parrot framework, by making it mutable and shapeshifting. 

These tools were built during a  research project that aimed to find the most efficient network configuration to solve different kind of problems, among which time series forecasting. 
