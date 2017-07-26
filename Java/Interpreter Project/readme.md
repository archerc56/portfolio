#I.	Assignment/Objective
This lab focuses on the idea of mutual exclusion, synchronization enforcement, deadlock, and 
starvation avoidance. For this part of the lab, I started implementing these principles, by locking
memory to one user at a time. The purpose of this program is to develop and explore operating system components, 
an architecture and its platform. I will be implementing a machine language interpreter (hardware simulator) for the 
architecture given in the assignment sheet using the following specification outline. This will provide the basis for the 
“hardware system” which was somewhat designed in CS214. 



#II.	Sample Execution
    Sample Input-
      The input will be read in from the files “program1.dat” and “program2.dat” which will be submitted with the program. The commands 
      for 
      the user and OS will be entered in from the keyboard.
      Input:
      program1.dat:
      0000100000001010
      0001000000000110
      0000100100000101
      0100000100000000
      0001000000000111
      1111000000000000

      program2.dat:
      0000100000011001
      0001000001101010
      0000100100000101
      0101000100000000
      0001000000000111
      1111000000000000

    Sample Output- 
      The output was taken from running the program in UNIX and issuing commands via the keyboard.
      Output:
      In User Mode: User 1 Please enter one of the following commands(nop, run):
      nop
      In User Mode: User 2 Please enter one of the following commands(nop, run):
      run
      Printing the PC as each instruction is ran:
              PC: 0000000001100101
              PC: 0000000001100110
      In Kernal Mode: Please enter one of the following commands (dmp, nop, stp):
      nop
      In User Mode: User 1 Please enter one of the following commands(nop, run):
      run
      Cannot issue run command currently: Memory has been locked while another user's program is running
      Running User 2s Program. Printing the PC as each instruction is ran:
              PC: 0000000001100111
              PC: 0000000001101000
              PC: 0000000001101001
      In Kernal Mode: Please enter one of the following commands (dmp, nop, stp):
      dmp
      User 1 memory dump
      RA: 0000000000000000
      R1: 0000000000000000
      R2: 0000000000000000
      R3: 0000000000000000
      PC: 0000000000000000

      User 2 memory dump
      RA: 0000000000011001
      R1: 1111011111111011
      R2: 0000000000000000
      R3: 0000000000000000
      PC: 0000000001101001


      Dump of Main Memory:
      Line 0   0000100000001010
      Line 1   0001000000000110
      Line 2   0000100100000101
      Line 3   0100000100000000
      Line 4   0001000000000111
      Line 5   1111000000000000
      Line 7   0000000000011001
      Line 100   0000100000011001
      Line 101   0001000001101010
      Line 102   0000100100000101
      Line 103   0101000100000000
      Line 104   0001000000000111
      Line 105   1111000000000000
      Line 106   0000000000011001
      In User Mode: User 1 Please enter one of the following commands(nop, run):
      run
      Cannot issue run command currently: Memory has been locked while another user's program is running
      Running User 2s Program. Printing the PC as each instruction is ran:
              PC: 0000000001101010
      User 2s program has finished running

      In Kernal Mode: Please enter one of the following commands (dmp, nop, stp):
      stp
      Exiting the program. Doing a final memory dump...
      User 1 memory dump
      RA: 0000000000000000
      R1: 0000000000000000
      R2: 0000000000000000
      R3: 0000000000000000
      PC: 0000000000000000

      User 2 memory dump
      RA: 0000000000011001
      R1: 1111011111111011
      R2: 0000000000000000
      R3: 0000000000000000
      PC: 0000000001100100


      Dump of Main Memory:
      Line 0   0000100000001010
      Line 1   0001000000000110
      Line 2   0000100100000101
      Line 3   0100000100000000
      Line 4   0001000000000111
      Line 5   1111000000000000
      Line 7   0000000000011001
      Line 100   0000100000011001
      Line 101   0001000001101010
      Line 102   0000100100000101
      Line 103   0101000100000000
      Line 104   0001000000000111
      Line 105   1111000000000000
      Line 106   0000000000011001

#III.	Dictionary of Variables
 
  Machine.java-
      public int rA: holds the value of the special register rA as an integer
      public int r1: holds the value of the first register as an integer
      public int r2: holds the value of the second register as an integer
      public int r3: hold the value of the third register as an integer
      public int pc: holds program counter
      public String flag: holds the conditions code flag 
      public int statLoc: holds the user’s program’s starting location in memory

  Interpreter.java- 
      public String instruction: holds the translated opcode in a String
      public String opcode: holds the opcode portion of the instruction as a binary String
      public String addressModeCode: holds the address Mode portion of the instruction as a binary String
      public String addressMode: holds the translated address Mode
      public Strig register: holds the translated register
      public String dataCode: holds the last 8 bits of the instruction read in from memory
      public String registerCode: holds the register portion of the instruction as a binary String
      public int data: holds the either a memory location or data, depending on the addressing mode 
      
  In User.java
    public Machine state: Creates the user’s machine object which holds the users  data(registers, pc, condition code flag, 
    and starting location)
    
    public boolean displayUI: used to let the program know whether or not to display a prompt
    
    public boolean check: used to tell whether the program is running
    
  In Loader.java
    Scanner input: Creates a scanner object used to read the file
    
    int i: holds the starting memory location for the user’s program. This helps load the user’s program into the correct location in 
    memory.
    
  In OS.java
    static User [ ] users: Each initialized index of the array will hold a User object which holds the users Machine object and two 
    boolean variables that will signal when user has a program running
    
    static Interpreter interpreter: Creates the Interpreter object that will be used to decode and execute the user programs in memory
    
    String[ ] mem: Creates a String array, with a size of 256,  which will act as the main memory of the OS
    
    File inputFile1: holds the file “program1.dat”, which holds User 1’s program
    
    File inputFile2: holds the file “program1.dat”, which holds User 2’s program
    
    Loader loader: Creates a loader object that will load the programs into the memory
    
    Scanner input: creates a Scanner object to read in input from the user
    
    boolean semaphore: used to lock main memory, when a user has a program running in memory. If semaphore is false, then memory is free 
    and any user can access memory by issuing a run command. If semaphore is true, then memory is locked until the program stops 
    running, meaning that the other user, whose program isn’t running, cannot issue a run command. 
    
    boolean running: Used to stop the while loop from continuing to run. Will be set to false when the OS issues a stp command
    
    String command: String that will hold the inputted command
    
    int userNum: will be a counter that will help loop through each of the two users and the OS
    
    int currentUser: Says which specific user is currently working. If currentUser equals 0 then User 1 is currently working, if 
    currentUser equa1s 1 then User 2 is currently working, and if currentUser equals 2 then the OS is currently working. Since 
    currentUser is intialized to 0, User 1 will be first to work.
