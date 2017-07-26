/*
PROGRAM NAME:Project 2, Operating System Multiuser
PROGRAMMER: Cade Archer

DATE STARTED: February 11, 2016
DUE DATE:  February 25, 2016

Explanation of the Loader class:
 The Loader Class will use the load method to read in the users program from a file and will then load it, line by 
 line, into the OS main memory (the mem array), starting at the passed in memory location(startLoc).
*/
import java.util.*;
import java.io.*;

//The loader class will be responsible for loading a program into a users interpreter
public class Loader{

  //Default constructor for the Loader
  public Loader(){
   
   }
   
   //the load method will take in a file and an Interpreter object and load the program thats in the file, into the 
   //Interpreter's memory  
   public void load(File program, int startLoc) throws Exception{
      
     //Creates a Scanner Object to look through the passed in file
     Scanner input = new Scanner(program);
       
     //i will be a counter that loads the program into memory starting at the passed in starting location
       int i= startLoc;
       
       
       //while the input file isn't empty
       while(input.hasNext()){
      
       //Load the new string from the file into the location in the memory array  
       OS.mem[i]=input.next();
      
       i++;
     }//end while
      
  
   }//end load 
   
}//end Loader class