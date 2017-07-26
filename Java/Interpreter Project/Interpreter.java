/*
PROGRAM NAME:Project 2, Operating System Multiuser
PROGRAMMER: Cade Archer

DATE STARTED: February 11, 2016
DUE DATE:  February 25, 2016

Explanation of the Interpreter class:
 The machine language interpreter used in this project is where each user's program is executed. The interpreter 
 will store each users data (i.e. condition code flag, pc, and registers) in the Machine class. The execution for 
 each users program will actually take place in the Interpreter class. The different programs in memory will be 
 ran by the OS calling the decode method passing in a Machine object, which will allow the Interpreter save the data
 for each user seperately, and an integer time, which will let the decode method know how many instructions to 
 execute. For each time cycle a binary String instruction will be read in from the location in memory that the program
 counter (pc) points to. The first four bits of the instruction will be the opcode, the next bit will be the address 
 mode, the next three bits will be the register, and the last eight bits of the instruction will be data 
 (either a location in memory or a value, depending on the address mode). The divided parts of the instruction will 
 be sent to the decodeOpcode, decodeAddressMode, decodeReigster, and decodeData methods so that they can be decoded 
 and used for execution. Then the method checks if the read in instruction was HLT. If it was, then that signals that 
 the program is finished, so true is returned, to indicate that the users program has finished executing. If the HLT
 command was not read in from memory, the execute method is called and the instruction is executed. Then it jumps 
 back to the beginning of the for loop, where it will continue to decode instructions until the time 
 limit is reached (when i equals time). When the loop has finished, the program returns false to indicate that the 
 users program is still running (the HLT command has not been read in).
   
*/


//The Interpreter class will handle the loading of memory and the decoding and execution of instructions
public class Interpreter{
 
 //Will hold the translated opcode in a string
 private String instruction;

 //Will hold the opcode portion of the instruction as a binary string
 private String opcode;
 
 //Will hold the address Mode portion of the instruction as a binary string
 private String addressModeCode;
 
 //Will hold the translated address mode
 private String addressMode;
 
 //Will hold the translated register
 private String register;
 
 //Will hold the last 8 bits of the instruction read in from memory
 private String dataCode;
 
 //Will hold the register portion of the instruction as a binary string
 private String registerCode;
 
 //Will hold the either a memory location or data, depending on the addressing mode
 private int data;
 

 
 //Constructor for the interpreter, that intializes the Machine object
  Interpreter(){
 
  }//end Interpreter constructor

  
  //Method that will take in a a time, and will decode and execute that number of instructions. It will return true
  //if the program in memory is finished and false if the program is not finished
  public boolean decode(int time, Machine state){
    
    
    //Loops through, decoding and executing time number of instructions or executing for time "clock ticks"
    for(int i=0;i<time; i++){
    
    //Gets the instruction from memory
    String newInstruction=OS.mem[state.pc];
    
    //Increments the program counter by one
    state.pc+=1; 
    
    //Prints the current PC of the running program
    System.out.println("\tPC: "+int2BinReturn(state.pc));
    
    //Splits the sixteen bit binary instruction into the opcode, address mode, register, and data portions
    opcode= newInstruction.substring(0,4);
    addressModeCode= newInstruction.substring(4,5);
    registerCode= newInstruction.substring(5,8);
    dataCode=newInstruction.substring(8,16);
    
    //decodes each part of the split up instruction
    decodeAddressMode();
    decodeRegister();
    decodeOpcode();
    decodeData();
    
    //If the command is HLT, that signals the end of the program, so true is returned
    if(instruction.equals("HLT")){
     state.pc=state.startLoc;
     return true; 
      
    }
    
    //execute the instruction
    execute(state);   
    }
     
     //If the HLT command has not yet been reached then return false to signal that the program is still running
     return false;
  }//end decode
 
  //decodes the data portion of the instruction, and stores it as an integer
  private void decodeData(){
    
  data= Integer.parseInt(dataCode,2);
        
  }//end decodeData
  
  //decodes the opcode, and stores the instruction name in a string
  private void decodeOpcode(){
    switch(opcode){
      
      //Sets the instruction to LOD
      case "0000":
        
        instruction="LOD";        
        break;
      
      //Sets the instruction to STO  
      case "0001":
        
        instruction="STO";
        break;
        
      //Sets the instruction to ADD  
      case "0010":
        
        instruction="ADD";
        break;
      
      //Sets the instruction to SUB  
      case "0011":
        
      instruction="SUB";
        break;
        
      //Sets the instruction to ADR  
      case "0100":
        
       instruction="ADR";
        break;
      
      //Sets the instruction to SUR
      case "0101":
        
       instruction="SUR";
        break;
      
      //Sets the instruction to AND  
      case "0110":
        
        instruction="AND";
        break;
      
      //Sets the instruction to IOR  
      case "0111":
        
        instruction= "IOR";
        break;
      
      //Sets the instruction to NOT  
      case "1000": 
        
        instruction="NOT";
        break;
      
      //Sets the instruction to JMP  
      case "1001":
        
        instruction="JMP";
        break;
       
      //Sets the instruction to JEQ  
      case "1010":
        
        instruction= "JEQ";
        break;
        
      //Sets the instruction to JGT  
      case "1011":
        
        instruction="JGT";
        break;
        
      //Sets the instruction to JLT  
      case "1100":
        
        instruction= "JLT";
        break;
        
      //Sets the instruction to CMP  
      case "1101":
        
        instruction="CMP";
        break;
      
      //Sets the instruction to CLR  
      case "1110":
        
        instruction= "CLR";
        break;
      
      //Sets the instruction to HLT   
      case "1111":
        
      instruction = "HLT";
      break;
      
      //Prints an error message if no matching opcode is match
      default:
        System.out.println("Error in decode opcode's switch statement");
    
      
    } 
    
  }//end decode opcode's switch statement
  
  //decodes the reigster code and stores the register's name in a string
  private void decodeRegister(){
    
    switch(registerCode){
      
      //Sets the register to rA
      case "000":
        
        register="RA";
        break;
     
      //Sets the register to R1  
      case "001":
        
        register="R1";
        break;
       
      //Sets the register to R2  
      case "010":
        
        register="R2";
        break;
      
      //Sets the register to R3  
      case "011":
        register="R3";
        break;
      
      //Prints an error message if none of the registerCodes match  
      default:
        System.out.println("Error in register switch statement");
       
    }
    
    
  }//end decodeRegister
  
  //deocdes the address mode and stores the result in a string
  private void decodeAddressMode(){
     switch(addressModeCode){
     
      //Sets the address mode to DIR(direct)
      case "0":
         addressMode="DIR";
        break;
      
      //Sets the address mode to IMM(immediate)
      case "1":
          addressMode="IMM";
        break;
       
      //Prints an error message if none of the addressModeCodes match  
      default:
        System.out.println("Error in addressModeCode switch statement");
        
    }
          
  }//end decodeAddressMode
  
 //Executes the decoded instruction
 private void execute(Machine state){
   switch(instruction){
     
     //Execute the load instruction
     case "LOD":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
           //if the register is rA
           if(register.equals("RA")){
         
           //Loads the value from the place in memory into rA
           state.rA=Integer.parseInt(OS.mem[data],2);
          
           //Calls the setFlag method passing in the value thats in rA
           setFlag(state.rA, state);
         
           }
           //if the register is R1
           else if(register.equals("R1")){
             
             //Loads the value from the place in memory into r1
             state.r1=Integer.parseInt(OS.mem[data],2);
             
             //Calls the setFlag method passing in the value thats in r1
             setFlag(state.r1,state);
             
           }
           //if the register is R2
           else if(register.equals("R2")){
             
             //Loads the value from the place in memory into r2
             state.r2=Integer.parseInt(OS.mem[data],2);
             
             //Calls the setFlag method passing in the value thats in r2
             setFlag(state.r2,state);
             
           }
           //if the register is R3
           else if(register.equals("R3")){
             
             //Loads the value from the place in memory into r3
             state.r3=Integer.parseInt(OS.mem[data],2);
             
             //Calls the setFlag method passing in the value thats in r3
             setFlag(state.r3,state);
             
           }      
        }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
           //if the register is rA
           if(register.equals("RA")){
             
             //Loads the value from the place in memory into rA
             state.rA=data;
             
             
             //Calls the setFlag method passing in the value thats in rA
             setFlag(state.rA,state);  
             
           } 
           
           //if the register is R1
           else if(register.equals("R1")){
             
             //Loads the value from the place in memory into r1
             state.r1=data;
             
             //Calls the setFlag method passing in the value thats in r1
             setFlag(state.r1,state);
             
           }
           
           //if the register is R2
           else if(register.equals("R2")){
             
             //Loads the value from the place in memory into r2
             state.r2=data;
             
             //Calls the setFlag method passing in the value thats in r2
             setFlag(state.r2,state);
             
           }
           
           //if the reigster is R3
           else if(register.equals("R3")){
             
             //Loads the value from the place in memory into r3
             state.r3=data;
             
             //Calls the setFlag method passing in the value thats in r3
             setFlag(state.r3,state);
             
           }
         }
       
       break;
     
     //Execute the store instruction  
     case "STO":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
          //if the register is rA
          if(register.equals("RA")){
            
            //Store the value of rA into the place in memory
            OS.mem[data]=int2BinReturn(state.rA);
            
            //Calls the setFlag method passing in the value thats in rA
            setFlag(state.rA,state);
            
          }
          
           //if the register is r1
           else if(register.equals("R1")){
             
            //Store the value of r1 into the place in memory 
            OS.mem[data]=int2BinReturn(state.r1);
            
            //Calls the setFlag method passing in the value thats in r1
            setFlag(state.r1,state);
             
           }
           
           //if the register is r2
           else if(register.equals("R2")){
             
            //Store the value of r2 into the place in memory
            OS.mem[data]=int2BinReturn(state.r2);
              
            //Calls the setFlag method passing in the value thats in r2  
            setFlag(state.r2,state);
                         
           }
           
           //if the register is r3
           else if(register.equals("R3")){
             
            //Store the value of r3 into the place in memory
            OS.mem[data]=int2BinReturn(state.r3);
            
            //Calls the setFlag method passing in the value thats in r3  
            setFlag(state.r3,state);
             
           }
       
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
          //if the register is rA
          if(register.equals("RA")){
            
            //Store the value of rA in memory location rA
            OS.mem[state.rA] = int2BinReturn(state.rA);
            
            //Calls the setFlag method passing in the value thats in rA 
            setFlag(state.rA,state);
            
          }
          
          //if the register is r1
          else if(register.equals("R1")){
            
            //Store the value of r1 in memory location r1
            OS.mem[state.r1] = int2BinReturn(state.r1);
            
            //Calls the setFlag method passing in the value thats in r1
            setFlag(state.r1,state);
             
          }
          
          //if the register is r2
          else if(register.equals("R2")){
            
            //Store the value of r2 in memory location r2
            OS.mem[state.r2] = int2BinReturn(state.r2);
            
            //Calls the setFlag method passing in the value thats in r2
            setFlag(state.r2,state);
             
          }
          
          //if the register is r3
          else if(register.equals("R3")){
            
            //Store the value of r3 in memory location r3
            OS.mem[state.r3] = int2BinReturn(state.r3);
            
            //Calls the setFlag method passing in the value thats in r3  
            setFlag(state.r3,state);
             
          }
       }
       
       break;
         
     //Execute the add instruction  
     case "ADD":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
          //add the value at the given memory location to rA
          state.rA= state.rA + Integer.parseInt(OS.mem[data],2);
          
          //Calls the setFlag method passing in the value thats in rA 
          setFlag(state.rA,state);
       
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
          //add the data value to rA
          state.rA= state.rA + data;
          
          //Calls the setFlag method passing in the value thats in rA 
          setFlag(state.rA,state);
          
       }    
       
       break;
     
     //Execute the subtract instruction   
     case "SUB":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
          
          //subtract the value at the given memory location from rA
          state.rA= state.rA - Integer.parseInt(OS.mem[data],2);
          
          //Calls the setFlag method passing in the value thats in rA 
          setFlag(state.rA,state);
       
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
          //subtract the data value from rA
          state.rA= state.rA - data;
          
          //Calls the setFlag method passing in the value thats in rA 
          setFlag(state.rA,state);
          
       }   
       
       break;
     
     //Execute the add register instruction
     case "ADR":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
          //if the register is rA
          if(register.equals("RA")){
            
            //add the value at the given memory location to rA
            state.rA= state.rA + Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in rA 
            setFlag(state.rA,state);
          }
          
          //if the register is r1
          else if(register.equals("R1")){
            
            //add the value at the given memory location to r1
            state.r1= state.r1 + Integer.parseInt(OS.mem[data], 2);
            
            //Calls the setFlag method passing in the value thats in r1 
            setFlag(state.r1,state);
          }
          
          //if the register is r2
          else if(register.equals("R2")){
            
            //add the value at the given memory location to r2
            state.r2= state.r2 + Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in r2
            setFlag(state.r2,state);
          } 
          
          //if the register r3
          else if(register.equals("R3")){
            
            //add the value at the given memory location to r3
            state.r3= state.r3 + Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in r3 
            setFlag(state.r3,state);
          }        
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
          //if the register is rA
          if(register.equals("RA")){
            
            //add the data value to rA
            state.rA= state.rA + data;
            
            //Calls the setFlag method passing in the value thats in rA
            setFlag(state.rA, state);
          }
          
          //if the register is r1
          else if(register.equals("R1")){
            
            //add the data value to r1
            state.r1= state.r1 + data;
            
            //Calls the setFlag method passing in the value thats in r1
            setFlag(state.r1,state);
          }
          
          //if the register r2
          else if(register.equals("R2")){
            
            //add teh data value to r2
            state.r2= state.r2 + data;
            
            //Calls the setFlag method passing in the value thats in r2
            setFlag(state.r2,state);
          } 
          
          //if the register is r3
          else if(register.equals("R3")){
            
            //add the data value to r3
            state.r3= state.r3 + data;
            
            //Calls the setFlag method passing in the value thats in r3
            setFlag(state.r3,state);
          }  
       }
       
       break;
     
       
     //Execute the subtract register instruction 
     case "SUR":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
          //if the register is rA
          if(register.equals("RA")){
            
            //subtract the value at the given memory location from rA
            state.rA= state.rA - Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in rA
            setFlag(state.rA,state);
          }
          
          //if the register is r1
          else if(register.equals("R1")){
            
            //subtract the value at the given memory location from r1
            state.r1= state.r1 - Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in r1
            setFlag(state.r1,state);
          }
          
          //if the register is r2
          else if(register.equals("R2")){
            
            //subtract the value at the given memory location from r2
            state.r2= state.r2 - Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in r2
            setFlag(state.r2,state);
          } 
          
          //if the register is r3
          else if(register.equals("R3")){
            
            //subtract the value at the given memory location from r3
            state.r3= state.r3 - Integer.parseInt(OS.mem[data],2);
            
            //Calls the setFlag method passing in the value thats in r3
            setFlag(state.r3,state);
          }        
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
          //if the reigster is rA
          if(register.equals("RA")){
            
            //subtract the data value from rA
            state.rA= state.rA - data;
            
            //Calls the setFlag method passing in the value thats in rA
            setFlag(state.rA,state);
          }
          
          //if the reigster is r1
          else if(register.equals("R1")){
            
            //subtract the data value from r1
            state.r1= state.r1 - data;
            
            //Calls the setFlag method passing in the value thats in r1
            setFlag(state.r1,state);
          }
          
          //if the register is r2
          else if(register.equals("R2")){
            
            //subtract the data value from r2
            state.r2= state.r2 - data;
            
            //Calls the setFlag method passing in the value thats in r2
            setFlag(state.r2,state);
          } 
          
          //if the register is r3
          else if(register.equals("R3")){
            
            //subtract the data value from r3
            state.r3= state.r3 - data;
            
            //Calls the setFlag method passing in the value thats in r3
            setFlag(state.r3,state);
          }  
       }
       
       break;
     
     //Execute the and instrution  
     case "AND":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
         //if the register is rA
         if(register.equals("RA")){
           
           //performs the bitwise and operation with the value in the given memory location and rA
           state.rA= state.rA & Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in rA
           setFlag(state.rA,state);
           
         }
         
         //if the register is r1
         else if(register.equals("R1")){
           
           //performs the bitwise and operation with the value in the given memory location and r1
           state.r1= state.r1 & Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in r1
           setFlag(state.r1,state);
           
         }
         
         //if the register is r2
         else if(register.equals("R2")){
           
           //performs the bitwise and operation with the value in the given memory location and r2
           state.r2= state.r2 & Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in r2
           setFlag(state.r2,state);
           
         }
         
         //if the register is r3
         else if(register.equals("R3")){
           
           //performs the bitwise and operation with the value in the given memory location and r3
           state.r3= state.r3 & Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in r3
           setFlag(state.r3,state);
           
         }
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
         //if the register is rA
         if(register.equals("RA")){
           
           //performs the bitwise and operation with data and rA
           state.rA= state.rA & data;
           
           //Calls the setFlag method passing in the value thats in rA
           setFlag(state.rA,state);
           
         }
         
         //if the register is r1
         else if(register.equals("R1")){
           
           //performs the bitwise and operation with data and r1
           state.r1= state.r1 & data;
           
           //Calls the setFlag method passing in the value thats in r1
           setFlag(state.r1,state);
           
         }
         
         //if the register is r2
         else if(register.equals("R2")){
           
           //performs the bitwise and operation with data and r2
           state.r2= state.r2 & data;
           
           //Calls the setFlag method passing in the value thats in r2
           setFlag(state.r2,state);
           
         }
         
         //if the register is r3
         else if(register.equals("R3")){
           
           //performs the bitwise and operation with data and r3
           state.r3= state.r3 & data;
           
           //Calls the setFlag method passing in the value thats in r3
           setFlag(state.r3,state);
           
         }
       }
       break;
       
     //Execute the or instruction  
     case "IOR":
       
       //if the address mode is direct
       if(addressMode.equals("DIR")){
       
         //if the register is rA
         if(register.equals("RA")){
           
        //performs the bitwise or operation with the value in the given memory location and rA
           state.rA= state.rA | Integer.parseInt(OS.mem[data],2);
           
        //Calls the setFlag method passing in the value thats in rA
           setFlag(state.rA,state);
           
         }
         
         //if the register is r1
         else if(register.equals("R1")){
           
      //performs the bitwise or operation with the value in the given memory location and r1
           state.r1= state.r1 | Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in r1
           setFlag(state.r1,state);
           
         }
         
         //if the register is r2
         else if(register.equals("R2")){
           
       //performs the bitwise or operation with the value in the given memory location and r2
           state.r2= state.r2 | Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in r2
           setFlag(state.r2,state);
           
         }
         
         //if the register is r3
         else if(register.equals("R3")){
           
        //performs the bitwise or operation with the value in the given memory location and r3
           state.r3= state.r3 | Integer.parseInt(OS.mem[data],2);
           
           //Calls the setFlag method passing in the value thats in r3
           setFlag(state.r3,state);
           
         }
       }
       
       //if the address mode is immediate
       else if(addressMode.equals("IMM")){
         
         //if the register is rA
         if(register.equals("RA")){
           
           //performs the bitwise or operation with data and rA
           state.rA= state.rA | data;
           
           //Calls the setFlag method passing in the value thats in rA
           setFlag(state.rA,state);
           
         }
         
         //if the register is r1
         else if(register.equals("R1")){
           
           //performs the bitwise or operation with data and r1
           state.r1= state.r1 | data;
           
           //Calls the setFlag method passing in the value thats in r1
           setFlag(state.r1,state);
           
         }
         
         //if the register is r2
         else if(register.equals("R2")){
           
           //performs the bitwise or operation with data and r2
           state.r2= state.r2 | data;
           
           //Calls the setFlag method passing in the value thats in r2
           setFlag(state.r2,state);
           
         }
         
         //if the register is r3
         else if(register.equals("R3")){
           
           //performs the bitwise or operation with data and r3
           state.r3= state.r3 | data;
           
           //Calls the setFlag method passing in the value thats in r3
           setFlag(state.r3,state);
           
         }
       }
       
       break;
     
     //Execute the not instruction
     case "NOT":
           
        //if the register is rA 
        if(register.equals("RA")){
       
          //performs the bitwise not operation on rA
          state.rA=~state.rA;
          
          //Calls the setFlag method passing in the value thats in rA
          setFlag(state.rA,state);
        }
        
        //if the register is r1
        else if(register.equals("R1")){
          
          //performs the bitwise not operation on r1
          state.r1=~state.r1;
          
          //Calls the setFlag method passing in the value thats in r1
          setFlag(state.r1,state);
        
        }
        
        //if the register is r2
        else if(register.equals("R2")){
          
          //performs the bitwise not operation on r2
          state.r2=~state.r2;
          
          //Calls the setFlag method passing in the value thats in r2
          setFlag(state.r2,state);
        
        }
        
        //if the register is r3
        else if(register.equals("R3")){
          
          //performs the bitwise not operation on r3
          state.r3=~state.r3;
          
          //Calls the setFlag method passing in the value thats in r3
          setFlag(state.r3,state);
        
      }
     
        break;
      
      //Execute the jump instruction
      case "JMP":
                 
        //changes the program counter to the data value
        state.pc=data-1;
        
        break;
      
      //Execute the jump equal to instrcution  
      case "JEQ":
        
        //if the flag is 010
        if(state.flag.equals("010")){
        
        //changes the program counter to the data value
        state.pc=data-1;
        }
        
        //if the flag does not equal 010, print an error message
        else{
          System.out.println("Condition 010 not met");   
             
        }
       break;
       
      //Execute the jump greater than instruction 
      case "JGT":
        
        //if the flag is 001
        if(state.flag.equals("001")){
        
        //changes the program counter to the data value
        state.pc=data-1;
        }
        
        //if the flag does not equal 001, print an error message
       else{
          System.out.println("Condition 001 not met");   
             
        }
         
       break;
       
      //Execute the jump less than instruction 
      case "JLT":
      
        //if the flag is 100
        if(state.flag.equals("100")){
        
        //changes the program counter to the data value
        state.pc=data-1;
        }
        
       //if the flag does not equal 100, print an error message 
       else{
          System.out.println("Condition 100 not met");   
             
        }
        
       break;
     
      //Execute the compare instrucion 
      case "CMP":
       
        //if the register is rA
       if(register.equals("RA")){
        
        //if the value in rA is greater than zero
        if(state.rA>0){
     
          //Call the setFlag method, passing in 1
          setFlag(1,state);
        }
        
        //if the value in rA is less than zero
        else if(state.rA<0){
           
          //Calls the setFlag method, passing in -1
          setFlag(-1,state);
        }
        
        //if the value in rA equals zero
        else{
     
          //Calls the setFlag method, passing in 0
          setFlag(0,state);
        }
        
       }
      
      //if the register is r1
      else if(register.equals("R1")){
        
        //if the value in r1 is greater than zero
        if(state.r1>0){
 
          //Calls the setFlag method, passing in 1
          setFlag(1,state);
        }
        
        //if the value in r1 is less than zero
        else if(state.r1<0){
       
          //Calls the setFlag method, passing in -1
          setFlag(-1,state);
        }
        
        //if the value in r1 equals zero
        else{
          
          //Calls the setFlag method, passing in 0
          setFlag(0,state);
        }
        
      }
      
      //if the register in r2
      else if(register.equals("R2")){
        
        //if the value in r2 is greater than zero
        if(state.r2>0){
           
          //Calls the setFlag method, passing in 1
          setFlag(1,state);
        }
        
        //if the value in r2 is less than zero
        else if(state.r2<0){
         
          //Calls the setFlag method, passing in -1
          setFlag(-1,state);
        }
        
        //if the value in r2 equals zero
        else{
 
          //Calls the setFlag method, passing in 0
          setFlag(0,state);
          
        }
        
      }
      
      //if the register is r3
      else if(register.equals("R3")){
        
        //if the value in r3 is greater than zero
        if(state.r3>0){
          
          //Calls the setFlag method, passing in 1
          setFlag(1,state);
          
        }
        
        //if the value in r3 is less than zero
        else if(state.r3<0){
          
          //Calls the setFlag method, passing in -1
          setFlag(-1,state);
          
        }
        
        //if the value in r3 equals zero
        else{
          
          //Calls the setFlag method, passing in 0
          setFlag(0,state);
          
        }
        
      }
        
        break;
        
        
      //Execute the clear instruction  
      case "CLR":
       
        //if the register is rA
        if(register.equals("RA")){
         //set rA to zero
         state.rA=0; 
        }
        
        //if the register is r1
        else if(register.equals("R1")){
          //set r1 to zero
          state.r1=0;
        }
        
        //if the register is r2
        else if(register.equals("R2")){
          //set r2 to zero
          state.r2=0; 
        }
        
        //if the register is r3
        else if(register.equals("R3")){
          //set r3 to zero
          state.r3=0; 
        }
        
        break;
           
     
      //Prints an error message if none of the instructions match
      default:
        System.out.println("Error in execute's switch statement");
      
    }//end switch
    
  }//end execute method
 
 
 //Prints out contents of each of the registers,the pc, and the main memory as binary strings
 public void memoryDump(Machine state){
      
   System.out.print("RA: ");
   int2Bin(state.rA);
   System.out.print("R1: ");
   int2Bin(state.r1);
   System.out.print("R2: ");
   int2Bin(state.r2);
   System.out.print("R3: ");
   int2Bin(state.r3);
   System.out.print("PC: ");
   int2Bin(state.pc);
   
   //Prints the main memory excluding spots in memory that are blank(null)
   /*System.out.println("\nMain Memory Dump: ");
   for(int i = 0; i<OS.mem.length; i++){
     if(OS.mem[i]==null||OS.mem[i].equals(".nd")){
      continue;
     }
     else{ 
    System.out.print("Line " +i +"   ");
    int2Bin(Integer.parseInt(OS.mem[i],2)); 
     }
    
     
   }
   */
 }//end memoryDump
  
  //Method that will take in a integer value and use it to set the condition code flag
  private void setFlag(int value, Machine state){
    
    //If the instruction is not JMP, JEQ, JGT, or JLT
    if(!instruction.equals("JMP")||!instruction.equals("JEQ")||!instruction.equals("JGT")||!instruction.equals("JLT")){
      
      //if the value equals zero then the flag is set to 010
      if(value==0)
        state.flag ="010";
      
      //if the value is less than zero the flag is set to 100
      else if(value<0)
        state.flag="100";
      
      //if the value is greater than zero the falg is set to 001
      else if(value>0)
        state.flag="001";
      
    }
  }//end setFlag
  
  
  //Converts an integer to 16-bit binary string and then prints that string
  public void int2Bin(int val){
    
    //Converts the passed in value to a binary string
    String bin = Integer.toBinaryString(0x10000 | val).substring(1);
    
    //if the value passed in was negative
    if(val<0){
      
//Cuts off the unnecsary 1 bits that will be in the binary string since it was converted using twos complent. This
//converts it down from 31 bits to 16 bits, which is the maximum number of bits each binary string can be in this 
//program  
    bin=bin.substring(15,31);
    
    }
    
    
    //prints out the binary string
    System.out.println(bin);
    
  }//end int2Bin
  
  //Converts an integer to 16-bit binary string and then returns that string
  private String int2BinReturn(int val){
    
   return Integer.toBinaryString(0x10000 | val).substring(1);
    
    
  }//end int2BinReturn

}//end Interpreter class
