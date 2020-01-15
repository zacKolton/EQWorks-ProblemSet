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
  - The first problem we are given a .cvs file that has a list of requests (including time stamps, locations, and id's) is provided
    - I name these "Records" in the problem set submission
    - The data from this .cvs file is then "cleaned" by getting rid of requests that have identical time stamps and locations 
    - It is then saved into a linked list and saved into the original file, and we move onto the next problem
      - For this particular submission, i named the cleaned data .cvs file "DataSample Cleaned.cvs"
      - Otherwise it will just overwrite the original file "DataSample.cvs"
 - The next problem we are given is to assign each request to a specific point of interest (or POI) given by the "POIList.cvs"
    - I name these PointOfInterest in the problem set submission
    - Each request is assigned to the POI that it is closest to
    - Each POI then holds a list of these requests for later use 
 - We then calculate (for each POI)
    - The average distance 
     - Standard deviation
     - Radius (Maximum request distance)
     - Density (requests/area)
 - For analysis, we draw a circle around the POI (radius) and then draw each request surrounding it
    - This result is saved as "Analysis.png"
 - The last question (4a) is were the data is scaled to a interval of[-10,10] and that is drawn out as well
    - This result is saved as "Model.png"
     
  
Remarks:
  - This program uses StdDraw.java to draw the data
    - StdDraw.java methods can be found here "https://introcs.cs.princeton.edu/java/15inout/javadoc/StdDraw.html"
    - Disclaimer: I do not own/create StdDraw.java
  - There is no graphing or statistical libraries used
  - I feel Question 4a isn't completely finished/correct
  - Problem set retrieved from: https://gist.github.com/woozyking/f1d50e1fe1b3bf52e3748bc280cf941f
