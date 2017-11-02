![title](https://user-images.githubusercontent.com/21087227/32349893-81e07dd4-c010-11e7-9742-8289f9285bb1.png)

Project Parrot is an object-oriented implementation of a Neural Network, trainable by backpropagation. The network structure is defined by a tuple of parameters contained in the class DNA, which makes it conveniently combinable with evolutionary algorithms. 

The network structure can be set up with very few intuitive commads:

![1](https://user-images.githubusercontent.com/21087227/32351006-f523ba7e-c013-11e7-8bd0-8d7adc8f6279.png)

The network can then be trained saved and exported. The main.java contains a full example of all the methods available to pull the data, customize the structure, train the network, save results and export and the network itself. 


## Note on the data structure: 
All you need is to import a csv file with the structure below and feed it to the network:

![2](https://user-images.githubusercontent.com/21087227/32345507-5289e5e2-c002-11e7-9bc5-51ba7a5754fc.PNG)



--- 

An implementation of one evolutionary algorithm is project HelloDarwin, which extends the Parrot framework, by making it mutable and shapeshifting. 

These tools were built during a  research project that aimed to find the most efficient network configuration to solve different kinds of problems, among which time series forecasting. 
