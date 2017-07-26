"""
Programmer: Cade Archer
Class: CSC 447
Spring 2017

Coded in Python version 3.6

Summary of program:
This program performs a RSA encryption for inputted
p and q values, and then encrypts and decrypts a
message entered by the user.


"""

from math import gcd
import random

def check_prime(n):
    """Checks if the number passed in is prime.
       If the number is prime then True is returned
       If not, False is returned"""
    if n <= 1:
        return False

    elif n <= 3:
        return True

    elif n % 2 == 0 or n % 3 == 0:
        return False

    i=5

    while i * i <= n:
        if n % i == 0 or n % (i + 2) ==0:
            return False
        i+=6
    return True


def extended_gcd(a, b):
    """Returns pair (x, y) such that xa + yb = gcd(a, b)"""
    x, lastx, y, lasty = 0, 1, 1, 0
    while b != 0:
        q, r = divmod(a, b)
        a, b = b, r
        x, lastx = lastx - q * x, x
        y, lasty = lasty - q * y, y
    return lastx, lasty


def multiplicative_inverse(d, n):
    """Find the multiplicative inverse of d mod n."""
    x, y = extended_gcd(d, n)
    if x < 0:
        return n + x
    return x




#Reads in a value for p
p=int(input("Please enter a prime integer for p that is greater than 10: "))

#Loops until the user enters a valid value for p
while not check_prime(p) or p<10:
    print("Number entered for p was not prime. \n")
    p=int(input("Please enter a prime integer for p that is greater than 10 : "))

#Reads in a value for q
q = int(input("Please enter a prime integer for q that is greater than 10: "))

#Loops until the user enters a valid value for p
while not check_prime(q) or q == p or q<10:
    print("Number entered for q was invalid \n")
    q=int(input("Please enter a different prime integer for q that is greater than 10: "))

#Computes the values for n and phi(n)
n= p * q
phi= (p-1)*(q-1)

print("p= ",p)
print("q= ",q)

#Deletes the p and q variables
del(p,q)

print("n= ",n,"\nphi(n)=", phi)

#gets a random value for n in the range of phi/10 to phi-1
d=random.randint(int(phi/10),phi-1)

#loops until a valid for d is produced
while gcd(phi,d)!=1 :
    d = random.randint(int(phi/10), phi - 1)

#computes e by computing the multiplicative inverse of d and phi
e=multiplicative_inverse(d,phi)

print("e=",e,"\nd=",d)

#asks the user to enter a message to encrypt
startMessage=input("Please enter a message to encrypt: ")

encryptedMessage=""
decryptedMessage=""

print("Start text to encrypt:",startMessage)

#encrypts each character of the message by doing (p^e) mod n
for i in range(0,len(startMessage)):
    encryptedMessage+= chr(pow(ord(startMessage[i]),e,n))


print("Encrypted Message:",encryptedMessage)


#decryptes each character of the message by doing (c^d) mod n
for j in range(0,len(encryptedMessage)):
    decryptedMessage+=chr(pow(ord(encryptedMessage[j]),d,n))


print("Decrypted Message:",decryptedMessage)
