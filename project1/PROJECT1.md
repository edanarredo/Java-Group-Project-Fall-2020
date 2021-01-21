# Programming project 1: Amazon Review Exploratory Data Analysis
* Due date: October 11 2020, 11:59 pm

In your first CS 3354 programming assignment, you will implement an exploratory data analysis module for the rating analysisof various rating summaries.  We are using a real example of ratings from Amazon [data], (http://jmcauley.ucsd.edu/data/amazon/links.html).  and have provided Amazon Instant Video and Music files as examples in data folder. 

## Data

The project simulated database by marking a set location for the data file, and data.csv. **Location** of the [data](https://git.txstate.edu/CS3354/2020Fall/tree/master/data) folder.

We will refer to it as **DATA** folder in the project.  This is a **default** data location, if your program does not find it, there is an option for the user to enter it in the commandline. The folder contains the raw files, processed files and data.csv file. 

* data.csv contains the provided filenames, number of reviews, and datase Id string identifiers, used for output summar filenames. 

```
DataID,RAW_FILE,RATINGS_NO,STAT_FILE
SMALL,small_test.csv,2,ratingSummary_SMALL.csv
TEST,test.csv,5,ratingSummary_TEST.csv
VIDEO,Instant_Video.csv,583,933,ratingSummary_VIDEO.csv
MUSIC,Digital_Music.csv,836,006,ratingSummary_MUSIC.csv
```

### Input Raw File Format

* name - specified in data.csv or by the user
* format - every lines in .csv file corresponds to: 
```
ReviewerID,ProductID,ReviewScore, 
```
  * ReviewerID - starts with A
  * ProductID - starts with B
  * ReviewerScore - integer number on scale from 1 to 5

### Input Stat File Format
* name - ratingSummary_<DATA_ID>.csv - corresponds to the name in data.csv file entry, DATA_ID column. 
* format - every lines in .csv file corresponds to: 
```
Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev
A00625243BI8W1SSZNLMD,8,4.875,0.353553391,4.486763483,0.250884209
B00I3MPDP4,455,4.23,1.04,3.98,0.69
```
see **Step 4** dor more details. 

## Goal: The goal of this assignment is to help students familiarize themselves with the following Java programming concepts:

1.	Input/Output to and from the terminal. 
2.	Reading from a file, processing data, and storing data in a file.
3.	Creating object-oriented classes and methods to handle data.
4.	Using data structures to store data in main memory (e.g. HashSet, ArrayList).
5.	Working with character strings.
6.	Using Javadoc comments and generating and html documentation of the program.
7.	Using Java Exceptions to improve the error handling capabilities of the program.

# Project Description:

For this assignment you are to write a Java program that will compute statistics on reviewers and products for amazon review data, and identify products and reviewers whose review scores significantly differ from the dataset.  Statistics is computed on two specific subsets of scores per product, and per reviewer, and sumamrized as average (avg), and standard deviation (st. dev.) score for those subsets. 

# Program flow

## Step 1: Start the program

Your submission should be in package project1, and should compile from commandline using the following commands: 

```
javac -d bin ./NET_ID/project1/*.java
java -cp bin project1.RatingStatsApp 
javadoc -d docs ./NET_ID/project1/*.java
```

## Step 2: Load existing database of movie reviews

Every time your program loads, it should first check if there exists a database file **data.csv** exists in **data** directory. **data** directory is assumed to be in the local hierarchy at the same level as src and NetID directories. You do not need to submit **ratingSummary_<dataset>.csv** for your homework; we will test your code by running it against provided datasets.  See the format of ratingSummary_SMALL.csv test example below, so you can test your code and see if it produces the correct output. Corresponding output datasets should correspond to dataID as explained in the .csv above 
-> If such file exists, the program should ask user if to load file's contents or compute new statistics

## Step 3: Present the user with an interaction menu

```
Choose one of the following functions:
	1. Display computed statistics for specific dataID. 
    2. Add new collection and compute statistics.
	0. Exit program.
```
0. Exit program, and save (upload) data.csv file. 
1. Display computed statistics for one of the datasets already processed in **DATA** folder
2. Load new dataset: 
   * user should provide unique dataID (TEST, SMALL, MUSIC, VIDEO)
   * user should provide path to new dataset inside **data** folder 
   If the dataset already exists, user gets more options: 
```
Choose one of the following functions:
	3. Use existing stat data.
	4. Process statistics again, I have new data.
```
For 1 or 2, the code displays statistics as specified in DataAnalysis.printReport() method; 

## Step 4: When the user selects “0”, save the database file and exit the program.

* save the statistics on the products and reviews in ratingSummary_<DATA_ID>.csv
* update the data.csv with DATA_ID, raw file that was processed, or create new one. 
* save ratingSummary_<DATA_ID>.csv .. ask user if you should overwrite the existing one. 
```
Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev
A00625243BI8W1SSZNLMD,8,4.875,0.353553391,4.486763483,0.250884209
B00I3MPDP4,455,4.23,1.04,3.98,0.69
```
* Id (Column A): If the Id starts with letter B it is a **product**, if the Id starts with letter A it is a **reviewer** in the ratingSummary.csv. 
* degree (Column B): the number of times that specific Id shows up in the raw file.
* product avg. (Column C): the computation depends if it is a product or a review, see below
* product st.dev. (Column D): the computation depends if it is a product or a review, see below
* reviewer avg (Column E): the computation depends if it is a product or a review, see below
* reviewer st.dev (Column F): the computation depends if it is a product or a review, see below

We compute the statistics as follows: 

For **reviewer** (Id starts with letter A): 
* **degree** marks the number of reviews from Id (appearance in raw file Column A). 
* Statistics of all review scores **reviewer** provided is computed and stored as: 
  * reviewer avg (Column E)
  * reviewer st.dev (Column F)
* Statistics of **all** possible reviews (from other reviewers and this reviewer) of **all** products **reviewer** scored is computed and stored as: 
  * product avg. (Column C)
  * product st.dev. (Column D)

For **product** (Id starts with letter B)
* **degree** marks the number of reviews from Id (appearance in raw file Column B)
* Statistics of **all** possible review of the product is computed and stored as: 
  * product avg. (Column C)
  * product st.dev. (Column D)
* Statistics of **all** possible reviews from  **all** **reviewers** that reviewd this specific **product** is computed and stored as:
  * reviewer avg (Column E)
  * reviewer st.dev (Column F)

## Example 

Input small_test.csv file has only 2 reviews for one product: 

```
A00019103H5DUGXXX2UPR,B007427XS4,3
A109ATZ5GBCII1,B007427XS4,5
```
Output ratingsSummary_SMALL.csv file should look like the following: 
```
Id,degree,product avg,product st.dev,reviewer avg,reviewer st.dev
A00019103H5DUGXXX2UPR,1,4.0,1.0,3.0,0.0
A109ATZ5GBCII,1,4.0,1.0,5.0,0.0
B007427XS4,2,4.0,1.0,4.0,1.0 
```

## Step 5: Test

* All results are written in report_<dataID>.csv when done with the collection 
* Check if your input matches for small_test.csv input (SMALL) and test.csv input (TEST) 
* Repeat steps for VIDEO and MUSIC datasets, to check code scalability  
* submit .java files only to your git.txstate.edu/CS3354/NetID.git -> project1 folder

# Notes: 

* The above interaction menu should be coded in a loop, so that the user can choose among the different options multiple times, until they choose option “0”, in which case the program terminates.

* Every time your program loads, it should first check if there exists **data.csv** file in **data** directory. 
* For a collection, it should check if stats are computed, and let user chose the options. 
* When the program exits (user selects action “0”), it should save the new database contents back to the database file, replacing the old one.
* When the user selects option “1”:
  * Upon loading new raw file, data should be loaded into collection, and statistics computed for database
  * Then the main memory Set (HashSet) is updated to include this new statistics 
  * All data analysis results should be printed in a formatted manner
    -> specified in DataAnalysis.printReport(List<AbstractRatingSummary> inList,int k) method. 

# Implementation hints:

Java provides the method split() which allows us to split a String into multiple tokens by specifying a separator character. 
E.g.
String animals = "dog cat bear elephant giraffe";
String[] animalsArray = animals.split("\\s+");

  * Step 0: Open .csv file.
  * Step 1: Tokenize (split) lines using comma as separating character.
  * Step 2: Compute the statistics for each reviewer and each product.
  * Step 3: Close .csv file.

# Tasks:
1. Extend the given code in src.git under project1 to create and object-oriented Java program that implements the functionality described above.  The code skeleton is given, do not change AbstractRatingSummary, Rating, RatingStatsApp and Data Analysis if possible.    
2. Try to make your program as robust as possible: 
  * exception handling to deal with possible problems that may occur during the program execution
  * use Java streams to streamline the data statistics computation 
3. Use object oriented principles to implement the missing methods: 
   * Encapsulation: minimal set of setters and getters.  Minimal constructor set. 
   * Abstraction: program to the interface not the implementaton, see AbstractRatingSummary 
   * Inheritance: where is the greatest code reuse? 
   * Polymorphism: make the code robust enough so it there is a different way to compute RatingSummary and collect it, it would require minimal integration effort. 
4. Use javadoc comments for all of your public classes and methods.

# Logistics:
* This assignment should be completed in groups of up to 3 students: please indicate in the @author tag
* The provided code should be cloned (first time) or pulled from https://git.txstate.edu/CS3354/src  according to the instructions provided in class (project1 folder), see README.md for more details
* The completed code should be pushed to <NETID> repo
  * **DO NOT** include executable .class or .jar files in your submission. Only .java files.
* You may use an IDE (VS Code, IntelliJ, Netbeans, etc) or just an editor and command line operations (javac, java) in Unix or Windows/DOS to develop your program.
* Use Object Oriented Design Principles. 
* Use a package project1 for your classes and put your files in the appropriate directory structure.
* Use a standard Java coding style to improve your program’s visual appearance and make it more readable, e.g. Google Java style guide: https://google.github.io/styleguide/javaguide.html 


