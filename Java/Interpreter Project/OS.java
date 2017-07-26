/*
PROGRAM NAME:Project 2, Operating System Multiuser
PROGRAMMER: Cade Archer
CLASS: CSC 341

DATE STARTED: February 11, 2016
DUE DATE:  February 25, 2016

Explanation of the OS class:
 The OS class will be the main driver and executor of the project. The main method in the OS will intialize two indexes
 in the User array for user 1 and user 2. Each users User object will hold their Macine object(which holds the 
 users program data) and boolean variables that will be used as signals to say when the user has a program running. 
 The main prorgram will then call the Loaders load method to load each users program into memory. Then the method 
 will use a while loop to cycle through the users and the OS, issuing prompts, taking in commands and then executing 
 those commands. The OS uses the mod functinon to keep cycling through the users and the OS. If the current 
 user(currUser) is the OS then a prompt will be brought up asking for a command. The OS can enter dmp, which will 
 produce a memory dump for each user, a stp command, which will end the program, or a nop, which will move the loop 
 along to the next user. If an invalid command is entered then an error message will be displayed, and the OS will 
 be prompted again. If currUser is a either user 1 or user 2 then a different prompt will be brought up asking for a 
 command, unless the current user has a program running. The user can nop, which will move the loop along to the 
 next user, or run, which will begin running the users program that has been loaded into memory at that users staring
 location, and set semaphore to true, which will lock memory, preventing the other user from accessing memory. 
 If an invalid command is entered then an error message will be displayed, and the user will be prompted again. If the 
 current user has a program already running, then a message will be displayed telling the user that their program is 
 still running. Also, if the user enters a run cmd when another users program is already running, then a message will 
 be displayed telling the user that their program cannot be ran currently, and the os will move to the next user 
 The decode method will then be called, where that users program will run for three clock cycles. When the users 
 program has ended (when the HLT command is reached), the  decode method will return true, signaling to the main 
 program that the users program has finished running in the interpreter. This will also set semaphore back to false, 
 meaning that memory is no longer locked. The main programs loop will keep running until the OS issues a stp command 
 which will exit the loop, print a message stating that the program is ending and call the dmp method to issue a 
 final memory dump.


*/


import java.util.*;
import java.io.*;


//The OS class will be what runs the project, by calling methods, creating objects, and the handling of the UI
public class OS{
    
  
  //Creates a static array of users. Each intialized index of the array will hold a User object which holds
  //the users Machine object and two boolean variables that will signal when user has a pr0gram running
  static User[] users; 
  
  //Creates the Interpreter object that will be used to decode and execute the user programs in memory
  static Interpreter interpreter =new Interpreter();
   
   //Creates a String array, with a size of 256, which will act as the main memory of the OS
   public  static String[] mem= new String[256];
   
   public static void main(String[] args) throws Exception{
     

    //Creates a loader object
    Loader loader= new Loader();
         
    //Creates the input files that will be passed to the loader  
    File inputFile1 = new File("program1.dat");
    File inputFile2= new File("program2.dat");
   
     //Loads the first program into main memory, starting at location 0
     loader.load(inputFile1, 0);
     
     //Loads the second program into main memory, starting at location 100
     loader.load(inputFile2, 100);      
   
    //Intialzes two spots in the User array, meaning that there will be two users
    users= new User[2];
    users[0]=new User(0);
    users[1]=new User(100);
    
    
    
     //File inputFile = new File("part1.dat");
    //Creates a Scanner object
    Scanner input = new Scanner(System.in);
    
    //Used to stop the while loop from continuing to run. Will be set to false when the OS issues a stp command
     boolean running =true;
     
  /*used to lock main memory, when a user has a program running in memory. If semaphore is false, 
  then memory is free and any user can access memory by issuing a run command. If semaphore is true,
  then memory is locked until the program stops running, meaning that the other user, whose program
  isnt running, cannot issue a run command. */
    boolean semaphore=false;
     
     //String that will hold the inputted command
     String command;
     
     //userNum will be a counter that will help loop through each of the two users and the OS
     int userNum=0;
     
    //Says which specific user is currently working. If currentUser equals 0 then User 1 is currently working, 
    //if currentUser equa1s 1 then User 2 is currently working, and if currentUser equals 2 then the OS is
    //curently working. Since currentUser is intialized to 0, User 1 will be first to work.
     int currentUser=0;
     
     //while the OS has not issued the stp command, the program will continue to run
      while(running){
        
        //Using the mod function, the program will keep moving through the two users and the OS
        currentUser=userNum%3;
        
        //if it is the OS
          if(currentUser==2){
            
            //Prompt the OS and read in another command 
            System.out.println("In Kernal Mode: Please enter one of the following commands (dmp, nop, stp): ");
            command=input.next(); 
            switch(command){
              
              //If the command is dump, call the dump method and print out both users memory
              case "dmp":
                dump();
                break;
                
              //If the command entered is nop, move to the next user
              case "nop":
                
                break;
                
              //If the command is stp, print a message, do a final memory dump and set 
              //running to false, which will exit the loop and end the program  
              case "stp":
                System.out.println("Exiting the program. Doing a final memory dump...");
                
                dump();
                
                running = false;                
                break;
                
              //If the OS enters an invalid command, print an error message and have them enter another command
              //This does not count for the time cycle  
              default:
               System.out.println("Invalid command entered! Try Again!");
               userNum--;
                break;
              
            }
          }
          
      //If it is user 1 or 2
      else if(currentUser==0||currentUser==1){
        
        //if the current user is going to display the UI
        if(users[currentUser].displayUI){  
           System.out.println("In User Mode: User "+(currentUser+1)+
                         " Please enter one of the following commands(nop, run): ");
            command=input.next(); 
            switch(command){
              
              //if the command entered is run
              case "run":
                
                //if memory is not locked
                if(!semaphore){
                
                //sets semaphore to false, which will prevent the other user from running their program
                semaphore=true;
                
                //sets it to where the UI will not be displayed
                users[currentUser].displayUI=false;
                
                //tells the program that the user has a program running
                users[currentUser].check=false;
                
                
                System.out.println("Printing the PC as each instruction is ran:");
                
                //Runs the users program for the remaining two cycles
               interpreter.decode(2, users[currentUser].state);
             
              }
               
                //if memory is locked, print an error message and move to the next user
            else
             System.out.println("Cannot issue run command currently: Memory has been locked while another user's " 
                                +"program is running");
                  
                break;
                
              //If the command entered is nop, move to the next user  
              case "nop":
                
                break;
              
          //If the user enters an invalid command, print an error message and have them enter another command
          //This does not count for the time cycle  
              default:
               System.out.println("Invalid command entered! Try Again!");
               userNum--;
                break;
              
            }
        
            
          
        }
        
       //if the current user is not going to display the UI, then that means that they have a program currently running
        else{
          
        //Tell the user that their program is still running
        System.out.println("Running User "+(currentUser+1)+"s Program. Printing the PC as each instruction is ran:");
          
          //Run the users program for the full three clock cycles
          users[currentUser].check=interpreter.decode(3, users[currentUser].state);
          
          //If the users program returns true, then it means their program has finished running, so they should be
          //prompted on their next "turn"
          if(users[currentUser].check){
           System.out.println("User "+ (currentUser+1)+ "s program has finished running");
            System.out.println();
           users[currentUser].displayUI=true; 
           
           //Unlocks memory so that another user can access memory by issuing a run command
           semaphore=false;
          } 
          
        }       
      }
      
        //Increments userNum, which moves to the next user/ the OS
        userNum++;
      
      }
      //Closes the Scanner input
      input.close();
   }//end main
   
   //Method that will print out both users memory 
   public static void dump(){
     
     //Prints out each users registers and pc, by passing the users Machine object to the memoryDump method
      for(int i=0; i<users.length;i++){
        System.out.println("User " + (i+1)+ " memory dump");
        interpreter.memoryDump(users[i].state);
        System.out.println();
      }
   
    //Prints the non null portions of the main memory array
    System.out.println("\nDump of Main Memory:");
    for(int i = 0; i<OS.mem.length; i++){
     if(OS.mem[i]==null||OS.mem[i].equals(".nd")){
      continue;
     }
     else{ 
    System.out.print("Line " +i +"   ");
    int2Bin(Integer.parseInt(OS.mem[i],2)); 
     }
    
     
    }  
      
   }//end dump
   
   //Converts an integer to 16-bit binary string and then prints that string
   public static void int2Bin(int val){
    
    //Converts the passed in value to a binary string
    String bin = Integer.toBinaryString(0x10000 | val).substring(1);
    
    //if the value passed in was negative
    if(val<0){
      
  /*Cuts off the unnecsary 1 bits that will be in the binary string since it was converted using twos complent. This
  converts it down from 31 bits to 16 bits, which is the maximum number of bits each binary string can be in this 
  program  */
    bin=bin.substring(15,31);
    
    }
    
    
    //prints out the binary string
    System.out.println(bin);
    
  }//end int2Bin
}//end OS

