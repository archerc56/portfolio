/*
 PROGRAM NAME:Project 2, Operating System Multiuser
PROGRAMMER: Cade Archer

DATE STARTED: February 11, 2016
DUE DATE:  February 25, 2016

Explanation of the Machine class:
 The Machine class will be used to hold each users program related information. This includes their registers, pc, 
 condition code flag, and their programs starting location in memory. This saves each users state, so that each user
 doesn't write over the other users data.
 
 */


//will hold state of each user
public class Machine{
 
 //holds the program counter
 public int pc;
 
 //holds the value of the special register rA
 public int rA;
 
 //holds the value of the first register
 public int r1;
 
 //holds the value of the second register
 public int r2;
 
 //holds the value of the third register
 public int r3;
 
 //holds the condition code flag
 public String flag;
 
 //holds the users program start location in memory
 public int startLoc;
  
//Constructor for the machine class that intializes variables in the class. Takes in the starting location
//of the users program in memory 
 Machine(int newStartLoc){
   
   startLoc=newStartLoc;
   pc=startLoc;
   rA=0;
   r1=0;
   r2=0;
   r3=0;

 }//end Machine constructor 
  
}//end MAchine class
