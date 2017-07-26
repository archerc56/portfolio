# I.	Description of Problem
  The purpose of this assignment was to create an example RSA encryption program that works based on user input. The program takes in 
  values for p and q and creates a public key, e, and a private key, d, that are used to encrypt and decrypt a message entered by the 
  user. The message that the user enters will be encrypted with the generated private key, printed out, and then unencrypted with the 
  public key and once more be printed out. The program could encrypt with the public key and then decrypt with the private, and it would 
  work the same way just in the opposite order. 
	
# II.	How I solved the Problem
  Process involved in building the software (Why I did what I did):
    My goal for this program was to make it as simple as possible while still maintaining the purpose of the assignment. For example, 
    when selecting a value for d, the private key, I wanted it to be simple so I just used the random.randint function to get the value, 
    but I made sure the value returned was smaller than phi(n)-1 but also sufficiently large. I did this by setting the floor of the 
    andint function to phi/10 so that d is never too small (i.e. a single digit). 

    I also incorporated only a very small amount of error checking into the program. I didn’t think it was necessary to overcomplicate 
    the assignment by trying to make the program as error-free as possible. The only error-checking the program does is to uphold the 
    correctness of the RSA algorithm. This includes looping until the user enters correct prime values for p and q, and again looping to 
    make sure the value of d that is randomly chosen is a valid value. No other error-checking is done by the program. If the user 
    enters a string when asked for the value of p, the program will crash. This decision was made under the assumption that this would 
    be running in more of a lab type environment, where only the correctness of the program was being tested and not its security. 


  Tools used:
    Programming Language Used: Python version 3.6
  	Imported from math, specifically gcd, and random libraries 
  	Coded on PyCharm by JetBrains


# III.	Degree of Success
  I think I was very successful in implementing a solution to the given problem. The program works as intended in a controlled 
  environment, so it doesn’t include error checking or handling of large p and q values. Taking that into consideration though, this 
  program serves as a simple example of how RSA encryption works. Through writing this program, I was able to really get a good 
  understanding of the math behind RSA encryption and how it works. 

# IV.	Sample Execution 

  Please enter a prime integer for p: 61
  Please enter a prime integer for q: 53
  p= 61
  q= 53
  n= 3233 
  phi(n)= 3120
  e= 487 
  d= 583
  Please enter a message to encrypt: The quick brown fox jumps over the lazy dog
  
  Start text to encrypt: The quick brown fox jumps over the lazy dog
  Encrypted Message: ÜऐଋொࢽӔௌਚࡾொ௢ڋÂరȸொؐÂîொࡈӔाফ֍ொÂƇଋڋொƤऐଋொ਱τ୰պொǭÂ࢐
  Decrypted Message: The quick brown fox jumps over the lazy dog
