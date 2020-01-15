# EQWorks-ProblemSet
Work Sample Problems - Data Role

Hello!

This is a implementation of a problem set sent by EQWorks. 

Instructions to run:
  - You will need the file path of DataSample.csv
  - You will need the file path of POIList.csv 
    - I have put these both in the src folder
    - Along with another file DataSample Cleaned.csv if that is of any use
  - When prompted, paste the file path requested in the consol 
  
 General Process:
  - A .cvs file that has a list of requests (including time stamps, locations, and id's) is provided 
    - The data from this .cvs file is then "cleaned" by getting rid of requests that have identical time stamps and locations 
    - It is then saved into a linked list and saved into the original file, and we move onto the next problem
      - For this particular submission, i named the cleaned data .cvs file "DataSample Cleaned.cvs"
  
Remarks:
  - This program uses StdDraw.java to draw the data
    - StdDraw.java methods can be found here "https://introcs.cs.princeton.edu/java/15inout/javadoc/StdDraw.html"
    - Disclaimer: I do not own/create StdDraw.java
  - There is no graphing or statistical libraries used
  - I feel Question 4a isn't completely finished/correct
