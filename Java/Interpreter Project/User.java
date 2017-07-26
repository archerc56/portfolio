/*
PROGRAM NAME:Project 2, Operating System Multiuser
PROGRAMMER: Cade Archer
CLASS: CSC 341

DATE STARTED: February 11, 2016
DUE DATE:  February 25, 2016

Explanation of the User class:
 Holds each users Machine object, as well as a boolean that tells whether the program is running(check), 
 and a boolean to let the program know whether or not to display a prompt(displyUI). The Machine object will
 hold the users registers, pc, and starting location
 
 */

//The User class will hold a users interpreter object, aswell as a boolean that tells whether the program 
//is running(check), and a boolean to let the program know whether or not to display a prompt(displyUI)
class User{
 
  //Will tell if the UI should be displayed to the user. displayUI will equal true if the UI should be displayed and 
 //it will equal false if the UI should not be displayed`
 public boolean displayUI; 
 
 //Will tell if the user has a program running in the interpreter. True means that they do not have a program runnning
 //and false means that they do have a program running(or the program hasn't began to run) 
 public boolean check;
 
 //Creates a users Machine object, which will holds their registers and pc
 public Machine state; 
 
 //Constructor for User that will intialize displayUI to true and check to false, and will create the users Machine
 //object
 User(int startLoc){
   
   state= new Machine(startLoc);
   displayUI=true;
   check=false;
    
 }//End User constructor
 
 
}//End User class