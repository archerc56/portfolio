/*
Program Name: Exploration 5: Warshalls Algorithm

Programmers: Cade Archer, Jace Archer
Class: CSC 342.001
Instructor: Dr. Becnel
Date Started: November 8th, 2016
Date Completed: November 12, 2016


Explanation of warshall.java:

For warshall.java, we read in a String representing a file name.
This file holds an adjacency matrix that will be converted into a
transitive closure. To build the adjacency matrix, we read in the file
line by line and split the numbers using the spaces in between them.
Each line is put into a different row in a two-dimensional, integer array.
Once the adjacency matrix is initialized, the transitive closure is built by
using Warshalls algorithm. Inside the warshallAlgorithm method, we passed in
the two-dimensional array, the adjacency matrix. This passed in matrix is
assigned to R, another two-dimensional, integer array. We also store
the matrixs length in an integer, n. The bulk of the algorithm is done
in the next stage of the method, where three for-loops build the transitive
closure. A node is looked at to see if a 1 needs to placed or not. A
1 is placed in a node if there is already a 1 in that nodes column
and in that nodes row. If one of, or neither, has a 1 in that place,
a 0 is stored. This is done for each node in the matrix. Once this
is completed, the transitive closure stored in R, is returned.
The last part of our program is the print method, that will
print out the transitive closure passed in.


 */

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class warshall{

  public static void main(String[] args) throws FileNotFoundException{

    //Reads in the file name from the command line
    String fileName= args[0];
    File inputFile= new File(fileName);
    Scanner input= new Scanner(inputFile);

    //Variables for calculating the time of the program
    long startTime, stopTime, overTime, time;

    //Calculates the systems overhead time
    startTime = System.nanoTime();
    stopTime = System.nanoTime();
    overTime = stopTime - startTime;

   //Checks to make sure the passed in file is not empty
  if(input.hasNextLine()){

   String lineOfInput= input.nextLine();
   String[] splitInput;

   //Sets the number of vertices by dividing the length of the line by
   //2, to not count in the spaces and the adds one to round up
   //to get the total number of actual vertices in the adjacency matrix
   int numVertices= (lineOfInput.length()/2)+1;


   //Creates the adjacency matrix that will be read in from the file
   //It will use the length of the first line read in to set the size of the matrix
   int[][] adjacencyMatrix=new int[numVertices][numVertices];

   //Two for loops used to read in the adjacency matrix from the file
   for(int i=0; i<numVertices;i++){

     splitInput=lineOfInput.split(" ");


       for(int j=0; j<numVertices;j++){

         adjacencyMatrix[i][j]=Integer.parseInt(splitInput[j]);

       }//end inner for

     if(input.hasNextLine())
      lineOfInput=input.nextLine();

   }//end outer for



   startTime= System.nanoTime();
   int[][] transitiveClosure= warshallAlgorithm(adjacencyMatrix);
   stopTime = System.nanoTime();

   time = stopTime-startTime-overTime;


   //Prints the transitive closure and the time take
   System.out.println("The Transitive Closure for the " +numVertices+ "x"+
                      numVertices+" adjacency matrix entered: ");
   print(transitiveClosure);
   System.out.printf ("Program Execution Time: %.4f milliseconds\n"
                       ,(time/1000000.0));
  }//emd if to read in

    //if the passed in file was empty an error message displays
    //and the program ends
    else{
     System.out.println("No data found in file passed in");
    }

    input.close();
  }//end main


  public static int[][] warshallAlgorithm(int [][] A){
    /* Implements Warshalls algorithm for computing the transitive closure
     * Input: The adjacency matrix A of a digraph
     * Output: The transitive closure of the digraph, R
     */


      //Sets R equal to the passed in adjacency matrix
      int[][] R= A;

      //Sets n equal to the number of vertices in R
      int n= R.length;

     
      for(int k=0; k<n;k++){
        for(int i=0; i<n;i++){
          for(int j=0; j<n;j++){

            if((R[i][j]==1)||((R[i][k]==1) && (R[k][j]==1))){
             R[i][j]=1;
            }
            else
            {
              R[i][j]=0;
            }
          }//end inner for
        }//end middle for
       }//End outer for

      return R;

  }//end warshallAlgorithm

  //Prints out the two-dimensional array passed in
  public static void print(int[][] R){


      for(int p=0; p<R.length; p++){
        for(int q=0; q<R.length; q++){
          System.out.print(" "+R[p][q]+" ");
        }//end inner for

      System.out.println();
      }//end outer for

    }//end print




}//end warshall