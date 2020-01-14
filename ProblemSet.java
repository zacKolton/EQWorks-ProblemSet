import java.io.*;
import java.util.*;
import java.awt.Color;


// Class:	ProblemSet
// Purpose:	Run functions to show problem work
public class ProblemSet
{
	public static final int X_SIZE = 750;	// Declaring canvas size x-axis
	public static final int Y_SIZE = 750;	// Declaring canvas size y-axis
	
	public static void main(String[] args) 
	{
		
		//----------------------------------------------------Program Setup----------------------------------------------------------//
		Scanner in = new Scanner(System.in);
		System.out.println("Hello, this program requires the file path of:\n- DataSample.csv\n- POIList.csv\nPlease paste the path for DataSample.csv");
		String dataSample = in.nextLine();
		//String dataSample = "..." <--- or just paste it here and comment out line 9-11, whatever is less annoying
		
		System.out.println("Thank you, and now please paste the path for POIList.csv");
		String poiList = in.nextLine();
		//String poiList = "..." <---- or just paste it here and comment out line 14-15, whatever is less annoying 
		
		//----------------------------------------------------Problem Set Questions----------------------------------------------------//
		//Problem #1
		LL recordList = cleanData(dataSample);	//Returns a list of records/requests that have been cleaned of suspicious events
		
		//Problem #2
		LL pList = label(recordList,poiList);	//Returns a list of POI's
		
		//Problem #3(Q.1 and 2)
		pList = analysis(pList);				// Performs analysis on the POI list 
		
		//Problem #4a
		DataScience(pList);						// Performs model on the POI list
		
	}
	
	// PROBLEM #1
	// Method: 	cleanData(String)
	// Purpose: Clean requests from DataSample.csv
	public static LL cleanData(String path)
	{
		System.out.println("--------------------------------------------");
		System.out.println("PROBLEM #1");
		Scanner in 	= openFile(path);										// Start a new scanner
		String firstLine = in.nextLine();									// Skip/save first line
		
		LL list = new LL();													// New list for Records
		System.out.println("Scan requests - flagging suspicious requests");
		while(in.hasNextLine())
		{
			String line 	= in.nextLine();								// Grab line
			String[] parts 	= line.split(",");								// Split into parts using "," for delimiter 
			Data e 	= new Record(line, parts[1],parts[5],parts[6]);			// Create new Data subclass Record
			list.addAndFlag(e);												// Attempt to add to list
		}
		System.out.println("Scan complete");
		System.out.println("Starting clean of flagged data");
		list = list.clean();												// Returning a new list was easier than keeping the same one and trying to remove everything
		System.out.println("Clean complete - Saved to original file");
		String cleanText = firstLine +"\n"+list.toString();					// Text that is sent to file
		saveToFile(path,cleanText);											// Save to file
		
		in.close();															// Closing the scanner
		return list;
	}
	

	
	// PROBLEM #2
	// Method:	label(LL, String)
	// Purpose:	Assign requests to POI's
	public static LL label(LL recordList,String path)
	{
		System.out.println("--------------------------------------------");
		System.out.println("PROBLEM #2");
		Scanner in			= openFile(path);									// Start a new scanner
		String firstLine 	= in.nextLine();									// Save and skip first line
		
		LL poiList = new LL();													// New list for POI's
		System.out.println("Scan POI List - skipping identical POI's");
		while(in.hasNextLine())
		{
			String line 	= in.nextLine();									// Grab line
			String[] parts 	= line.split(",");									// Split into parts using "," for delimiter 
			Data poi = new PointOfInterest(line, parts[0],parts[1],parts[2]);	// Create new Data subclass PointOfInterest
			poiList.addAndFlag(poi);											// Attempt to add to list
		}
		// I could use the clean function here but since the POIList is so small i don't see a point 
		// It works just fine without it 
		System.out.println("Scan complete");
		System.out.println("Assigning requests to POI's");
		recordList.assignRecords(poiList);										// Start assigning Records to specific POI's
		System.out.println("Assigning complete");
		
		in.close();																// Close scanner
		
		return poiList;
	}
	
	// PROBLEM #3
	// Method: 	analysis(LL)
	// Purpose	
	//		- Perform analysis on the cleaned and assigned data 
	//		- Visually represent data 
	// Remarks:
	//	- This is where i figured out that recursion just would not hold up with his amount of data
	//	- Recursion was causing a stackOverflow due to the amount of function calls
	public static LL analysis(LL poiList)
	{
		System.out.println("--------------------------------------------");
		System.out.println("PROBLEM #3");

		
		System.out.println("Calculating average for each POI");			
		poiList.calcAverage();											// Calculate and set averages for each POI
		
		System.out.println("Calculating std. deviation for each POI");
		poiList.calcStandardDeviation();								// Calculate and set std. deviations for each POI
		
		System.out.println("Calculating radius for each POI");
		poiList.calcRadius();											// Calculate and set radii for each POI
		
		
		System.out.println("Calculating density for each POI");
		poiList.calcDensity();											// Calculate and set density for each POI
		System.out.println("Display POI list:");
		System.out.println(poiList);									// Show finished calculations
		
		
		System.out.println("Start draw");	
		poiList.draw(X_SIZE,Y_SIZE);									// Call draw(int,int) to visualize data
		
		// This is important because StdDraw.java seems to only allow one window/canvas 
		// So we will just save this image 
		StdDraw.save("Analysis Q2.png");
		System.out.println("Draw complete - Saved as: 'Analysis Q2.png' in src directory");
		
		return poiList;
		
	}
	
	// PROBLEM: 4a Model
	// Method:	DataScience(LL)
	// Purpose:	Models data to scale of [-10,10]
	//
	// Remarks:	Not sure if this is what you are looking for (model(int,int))- gave it my best shot
	//
	public static void DataScience(LL poiList)
	{
		System.out.println("--------------------------------------------");
		System.out.println("PROBLEM #4a");
		
		System.out.println("Start model");
		poiList.model(X_SIZE,Y_SIZE);
		StdDraw.save("Model 4a.png");
		System.out.println("Model complete - Saved as: 'Model.png' in src directory");
		
	}
	
	//-------------------------
	// Helper Methods
	//-------------------------
	
	// Method:	openFile(String)
	// Purpose: Open a file and return a scanner to read it
	// Reasoning: To clean and reuse code for problems 
	private static Scanner openFile(String path)
	{
		Scanner result = null;
		try
		{
			result = new Scanner(new FileReader(path));
		}catch(FileNotFoundException e)
		{
			System.out.println("Couldn't read file in Main "+e.getMessage());
		}
		return result;
	}
	
	// Method: saveToFile(String, String)
	// Purpose: Save to designated file given the path
	// Reasoning: Cleaner code in main methods
	private static void saveToFile(String path, String text)
	{
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(path,false));
			writer.write(text);
			writer.close();
		}catch(IOException e)
		{
			System.out.println(e.getMessage());
		}	
	}

}

// Class:	Record
// Purpose:	
//		- Organize and use information coming from the DataSample.csv
//		- Provide easily accessible data for future functions
//
// Remarks: 
//		- timeSt and date didn't really needed to be parsed into doubles 
//			- I thought it would be helpful if i was to order the Records in the POIList into ascending order
//			- I started out using a Binary Tree - but then the recursive calls called a stackOverflow so i switched to using a linked list
//				- thought it be cool to not only clean the list but also sort it 
//		- I never ended up ordering them - no point
//		- Having the super class Data really cleaned up the code in this assignment 
//			- Specifically for parsing Longitude and Latitude
class Record extends Data
{
	private String line;			// Original line from .csv file
	private boolean flagged;		// If it is equal to another Record
	private double distanceToPOI;	// Hold the distance to its assigned POI
	private double latitude;		// Holds latitude
	private double longitude;		// Holds longitude
	private double timeSt;			// Holds time stamp
	private int date;				// Holds date 
	
	//-------------------------
	// Constructor
	//-------------------------
	public Record(String l, String t, String lat, String lng)// [Original line] [date & time][lat][long]
	{
		timeSt			= parseTime(t);			// Here is what i mean by parsing the time into a double 
		date			= parseDate(t);			// Here is what i mean by parsing the date into a double
		latitude		= parseLatAndLong(lat);	// Super (Data) method
		longitude		= parseLatAndLong(lng);	// Super (data) method
		line			= l;					// Saving original line 
		flagged 		= false;				// flagged is initialized as false to start with 
		distanceToPOI	= Double.MAX_VALUE; 	// Specifically to initialize variable - for good practice
	}
	
	//-------------------------
	// Method(s)
	//-------------------------
	
	// Method: 	toString()
	// Purpose: Standard toString() that prints out the original line
	// Reasoning: Various
	//		- Mainly for testing
	//		- Mandatory since Record is a subclass of Data
	//		- Prints out original line to file 
	public String toString()
	{
		return line;
	}
	
	// Method: 	equals(Data)
	// Purpose: Checks and casts Data to Record to check if THIS Record is equal to the inputed Record
	// Reasoning: 
	//			- Needed some way to check for equal records
	//			- Mandatory since Record is a subclass of Data
	public boolean equals(Data e)
	{
		return(timeSt == ((Record)e).timeSt) && (date == ((Record)e).date) && (latitude == ((Record)e).latitude) && (longitude == ((Record)e).longitude);
	}
	
	// Method: 	flag()
	// Purpose: Flags THIS Record
	// Reasoning:
	//			- Needed to keep track of suspicious Records
	//			- Mandatory since Record is a subclass of Data
	//
	// Remark: I should have named this "isSuspicious"
	public void flag() {flagged = true;}
	
	// Method: 	getDistance(Data)
	// Purpose: Gets the distance to another Data (always PointOfInterest)
	// Reasoning: 
	//			- Needed to get distance to PointOfInterest
	//			- To find the minimum distance out of the PointOfInterests'
	//			- For various calculations
	public double getDistance(Data p)
	{
		return Math.sqrt(Math.pow(latitude - p.getLatitude(),2) + Math.pow(longitude - p.getLongitude(),2));
	}
	

	//-------------------------
	// Private Helper Method(s)
	//-------------------------
	
	// Method: 	parseTime(String)
	// Purpose: Parses the time into a double
	// Reasoning: Cleaner methods 
	//
	// Remark: The concatenation would be used later to compare times, however...we don't do that here (Black panther joke)
	private double parseTime(String l)
	{
		double result 	= 0.0;
		String hour		= l.substring(11,13);	// Get the hour
		String min		= l.substring(14,16);	// Get the minute
		String sec 		= l.substring(17);		// Get the second 
		
		String all		= hour + min + sec;		// Concatenating
		
		try										// Try-catch for parsing
		{
			result = Double.parseDouble(all);
		}catch(NumberFormatException nf)
		{
			System.out.println("Parsing time did not work in Record"+nf.getMessage());
		}
		return result;
	}
	
	// Method: 	parseDate(String)
	// Purpose: Parses the date into a double
	// Reasoning: Cleaner methods
	//
	// Remark: The concatenation would be used later to compare times, however...we don't do that
	private int parseDate(String l)
	{
		int result		= 0;
		String year		= l.substring(0,4);		// Get the year
		String month	= l.substring(5,7);		// Get the month 
		String day		= l.substring(8,10);	// Get the day
		
		String all		= year + month + day;	// Concatenating
		try
		{
			result = Integer.parseInt(all);
		}
		catch(NumberFormatException nf)
		{
			System.out.println("Parsing date did not work in Record"+nf.getMessage());
		}
		return result;
	}
	
	//-------------------------
	// Getters/Setters
	//-------------------------
	
	public boolean isFlagged() {return flagged;}
	public double getLatitude() {return latitude;}
	public double getLongitude() { return longitude;}
	public double getDistanceToPOI(){ return distanceToPOI;} 
	
	public void setDistanceToPOI(double d) {distanceToPOI = d;}
		
}

// Class: PointOfInterest 
// Purpose:  
//			- Organize and use information coming from the POIList.csv
//			- Provide easily accessible data for future functions
//			- Hold a list of records for better organization 
//
// Remarks:
//			- I needed the isFlagged() here to make the addAndFag function in LL to work with the polymorphism
class PointOfInterest extends Data
{
	private double latitude;		//Since float holds up to 7 digits 
	private double longitude;
	private double average;			// Average distance from THIS POI to its assigned requests
	private double stDeviation;		// Standard deviation from THIS POI to its assigned requests
	private double radius;			// This will be the length of the greatest minimum distance entry
	private double density;			// Holds density of requests for THIS POI
	private double minRecord;		// Record with the absolute minimum distance away in the recList
	private String id;				// This may help in the future for identification
	private String line;			// Holds the original line
	private boolean flagged;		// Fairly useless for this particular POIList - would be more useful in a longer one
	
	private LL recList;				// Holds a list of records
	
	//-------------------------
	// Constructor
	//-------------------------
	public PointOfInterest(String l, String i, String lat, String lng)
	{
		latitude 	= parseLatAndLong(lat);	// Super (Data) method
		longitude 	= parseLatAndLong(lng);	// Super (Data) method
		line		= l;					// Saving original line
		average		= 0.0;					// Initializing for good practice
		stDeviation	= 0.0;					// Initializing for good practice
		radius		= 0.0;					// Initializing for good practice
		minRecord	= 0.0;					// Initializing for good practice
		id			= i;					// Setting id 'POI1"
		recList 	= new LL();				// Initializing for good practice
	}
	
	//-------------------------
	// Methods
	//-------------------------
	
	// Method: 	toString()
	// Purpose: Standard toString() that prints out the original line
	// Reasoning: 
	//			- Displaying information in Problem #3
	//			- Mandatory since PointOfInterest is a subclass of Data
	//			- Testing
	public String toString()
	{
		return "\n"+id +"\nAverage:\t"+average +"\nStd. Deviaion:\t"+stDeviation+"\nRadius:\t\t"+radius+"\nDensity:\t"+density;
	}
	
	// Method: 	equals(Data)
	// Purpose: Checks and casts Data to PointOfInterest to check if THIS PointOfInterest is equal to the inputed PointOfInterest
	// Reasoning:
	//		- Needed some way to check for equal records
	//		- Mandatory since PointOfInterest is a subclass of Data
	public boolean equals(Data d)
	{
		return (latitude == ((PointOfInterest)d).latitude) && (longitude == ((PointOfInterest)d).longitude);
	}
	
	// Method: 	flag()
	// Purpose:	Flag if equal to another
	// Reasoning: Mandatory since PointOfInterest is a subclass of Data
	//		
	// Remarks:	Not really used to its full extent with the size the POIList is
	public void flag()
	{
		flagged = true;
	}
	
	// Method:	add(Record)
	// Purpose:	Add a record to the recordList for THIS POI
	// Reasoning: Cleaner code in main methods
	public void add(Record r)
	{
		recList.add(r);
	}
	
	// Method:	calcDenisty()
	// Purpose:	
	//		- Calculate density
	//		- Sets the density after calculating
	// Reasoning: No need for loop since we have been keeping track of the "requests"/size the whole time
	public void calcDensity()
	{
		int numReq 	= recList.getSize();			//Number of requests/records in list
		double area = Math.PI * Math.pow(radius,2);	//area of a circle
		density 	= numReq/area;					//Calculating density
	}
	
	//-------------------------
	// Private Helper Method(s)
	//-------------------------
	
	// None for this class
	
	//-------------------------
	// Getters/Setters
	//-------------------------
	public boolean isFlagged() {return flagged;}
	public double getLatitude() {return latitude;}
	public double getLongitude() { return longitude;}
	public double getAverage() {return average;}
	public double getRadius() { return radius;}
	public double getMinDistance() {return minRecord;}
	public LL getList()	{return recList;}
	public String getID() { return id;}
	
	
	
	public void setAverage(double d) { average = d;}
	public void setStandardDeviation(double d) { stDeviation = d;}
	public void setRadius(double d) {radius = d;}
	public void setMinDistance(double d) {minRecord = d;}	// Min distance
	
}

// Class: 	LL (or Linked List)
// Purpose: 
//		- Hold a list of Nodes that contain Data
//		- Perform various calculations and set Data subclass variables
//
// Remarks:
//		- In the previous version this was a Binary Tree 
//		- Due to the amount of function calls it was more safe to use a Linked List (than a binary tree)
//		- A binary tree would have been the optimal choice if we were ordering the Records/requests by time
//			- or ordering the Records/requests in the POI lists - but we are not
class LL
{
	private int size;
	//private node class for encapsulation
	private class Node
	{
		Data data;
		Node next;
		
		//-------------------------
		// Constructor
		//-------------------------
		public Node(Data d)
		{
			data = d;
		}
		
		public Node(Data d, Node n)
		{
			data = d;
			next = n;
		}
		
		//-------------------------
		// Getters/Setters
		//-------------------------
		public Data getData() { return data;}
		public Node getNext() { return next;}
		
		public void setData(Data d) { data = d;}
		public void setNext(Node n) { next = n;}
	}
	
	private Node head;			//Start of list
	private Node last;
	
	//-------------------------
	// Constructor
	//-------------------------
	public LL()
	{
		head = last = null;
	}
	
	//-------------------------
	// Methods
	//-------------------------
	
	// Method:	isEmpty()
	// Purpose: Easy way to figure out if the list is empty
	public boolean isEmpty()
	{
		return head == null;
	}
	
	// Method:	addAndFlag(Data)
	// Purpose:	Adds data to a list and flags it if there are identical pairs
	// Reasoning: Needed a function to add and/or flag nodes
	// Remarks: 
	//		- I really wanted to remove the Node that was already in the list (after it gets flagged)
	//			- However, then we wouldn't have anything to compare the future nodes to
	//			- since there could be more than 2 suspicious Records
	//		- I used a head and last pointer since we are not keeping the list in order 
	//			- This is much faster than going through the whole list 
	//			- We go through the whole list anyways here...but in the next function we do not
	//		- This is one of the functions where the polymorphism really comes in handy
	public void addAndFlag(Data d)
	{
		if(isEmpty())									
		{
			head = last = new Node(d);		
			size++;
		}
		else
		{
			Node curr = head;
			Node prev = null;
			boolean found = false;				// To stop loop (we will just clean it later)
			while((curr != null) && !found)		// While curr != null, boolean flag to stop
			{
				if(curr.getData().equals(d))	// check if there is one equal
				{								// if so flag the data for later cleaning
					curr.getData().flag();		// Also we wont add the new Data d
					d.flag();
					found = true;
				}
				prev = curr;					// keep track of the previous node
				curr = curr.getNext();			// get the next one
			}
			if(!found) 
			{
			Node newNode = new Node(d);
			last.setNext(newNode);
			last = newNode;
			size++;
			}
		}
	}
	
	// Method: 	add(Data)
	// Purpose:	Adds to a list (Simple method)
	// Reasoning: 
	//		- Used after everything has been cleaned or its unnecessary
	//		- we didn't need to use addAndFlag AGAIN after everything was cleaned - that would be a waste of time 
	public void add(Data d)
	{
		if(isEmpty())
		{
			head = last = new Node(d);
		}
		else
		{
			Node temp = new Node(d);
			last.setNext(temp);
			last = temp;
		}
		size++;
	}
	// Method: 	assignRecords(LL)
	// Purpose:	Assigns Records to the POI that is the minimum distance away
	// Reasoning:
	//		- Build the RecList for POI's for later use
	//
	// Remarks: 
	//		- The add(Data) method is used here
	//		- This function felt so backwards to me because we are comparing the bigger list (recordList from main) to the smaller list (poiList from main)
	public void assignRecords(LL poiList)
	{
		Node curr = head;											// Head of recordList
		while(curr != null)
		{
			Record recordTemp 		= (Record)curr.getData();		// Get a pointer for readability 
			PointOfInterest poiTemp = poiList.getMinPOI(recordTemp);// Find the POI whereas it is the minimum (of all the POI's) distance away from the Record
																	// More on getMinPOI(Record) later in the comments (helper method section)
			poiTemp.add(recordTemp);								// Uses the simple add(Data) function
			curr = curr.getNext();
		}
	}
	
	// Method:	clean()
	// Purpose:	
	//		- Does a final clean of the file 
	//		- Returns a new list 
	//Reasoning: This was more maintainable than trying to keep the same list and removing the flagged Records/PointsOfInterest
	public LL clean()
	{
		LL result = new LL();												// Returning a new list (cleaned)
		Node curr = head;													// Get head of THIS list
		while(curr != null)
		{
			if(!curr.getData().isFlagged())									// Check if not flagged
			{
				if(result.isEmpty())
				{
					result.head = result.last = new Node(curr.getData());	// Special case for adding to empty lists
				}
				else
				{
					Node newNode = new Node(curr.getData());				// Otherwise just add it at the end
					result.last.setNext(newNode);
					result.last = newNode;
				}
			}
			curr = curr.getNext();											// Move to next node
		}
		return result;
	}
	
	// Method:	calcAverage()
	// Purpose:	Calculate and set the average for each POI 
	// Reasoning: I figured it was more efficient to just set it here rather than returning anything
	public void calcAverage()
	{
		Node currPOI = head;													// Head of POIList
		while(currPOI != null) 
		{
			PointOfInterest tempPOI = (PointOfInterest)currPOI.getData();		// Pointer - readability
			LL recordList 	= tempPOI.getList();
			Node currRecord	= recordList.head;									// Again, since we are in the same class (.head)
			double sum = 0.0;
			double avg = 0.0;
			while(currRecord != null)
			{
				sum += ((Record)currRecord.getData()).getDistanceToPOI();		// increase sum for each distance
				currRecord = currRecord.getNext();								// get next record
			}
			avg = sum/(recordList.getSize());									// calculate average
			tempPOI.setAverage(avg);											// set for later use
			currPOI = currPOI.getNext();										// get next POI
		}
		
	}
	
	// Method:	calcStandardDeviation()
	// Purpose:	Calculate and set the Standard deviation for each POI
	// Reasoning: I figured it was more efficient to just set it here rather than returning anything
	public void calcStandardDeviation()
	{
		Node currPOI = head;													// Head of POIList
		while(currPOI != null) 
		{
			PointOfInterest tempPOI = (PointOfInterest)currPOI.getData();		// Get a pointer for readability
			LL recordList 	= tempPOI.getList();								// Pointer again for readability
			Node currRecord	= recordList.head;									// Again, since we are in the same class (.head)
			double stdDev	= 0.0;												// Initialize for after/during while
			while(currRecord != null)
			{
				stdDev += Math.pow(((Record)currRecord.getData()).getDistanceToPOI() - tempPOI.getAverage(),2);	// Get numerator for the standard deviation formula
				currRecord = currRecord.getNext();
			}
			stdDev = Math.sqrt(stdDev/recordList.getSize());					// Finish calculating the standard deviation
			tempPOI.setStandardDeviation(stdDev);								// Set POI pointer's standard deviation formula
			currPOI = currPOI.getNext();
		}
	}
	
	// Method: 	calcRadius()
	// Purpose:	Calculates and sets the radius for each POI
	// Reasoning: I figured it was more efficient to just set it here rather than returning anything
	public void calcRadius()
	{
		Node currPOI = head;													// Get head of POI list
		while(currPOI != null)
		{
			PointOfInterest tempPOI = (PointOfInterest)currPOI.getData();		// Getting a pointer for easier readability
			LL recordList 	= tempPOI.getList();						
			Node currRecord	= recordList.head;									// Again, since we are in the same class (.head)
			double max = -Double.MAX_VALUE;										// Radius should be the greatest min distance from POI
			
			while(currRecord != null)
			{
				Record tempRec = (Record)currRecord.getData();					// Again, pointer for readability
				double distance = tempRec.getDistanceToPOI();					// Call distance function from Record (it has already been calculated in getMinPOI)
				if(distance > max)												
				{
					max = distance;												// Since distance > max - update max 
				}
				currRecord = currRecord.getNext();
			}
			tempPOI.setRadius(max);												// POI pointer setting radius
			currPOI = currPOI.getNext();										// Node
		}
	}
	
	// Method:	calcDenisty()
	// Purpose:	Just calls calcDensity() in each POI to set it
	// Reasoning: No point in doing calculations here if we already have all the info we need set in the POI now 
	public void calcDensity()
	{
		Node curr = head;
		while(curr != null)
		{
			PointOfInterest tempPOI = (PointOfInterest)curr.getData();			// Getting a pointer for easier readability
			tempPOI.calcDensity();												// Call POI's function
			curr = curr.getNext();
		}
	}
	
	// Method:	draw(int,int)
	// Purpose: 
	//		- Draw POI's
	//		- Draw each Record/request for those POI's
	//		- Visually represent data
	//
	// Remarks:
	//		- Since we are only working with 3 POI's i only used 3 colors
	//		- Reasons for NOT accounting for more POI's
	//			- There is a finite amount of colors to choose from 
	//				- ie. if there are more POI's than colors then we would have to resort to creating new ones in an editor
	//				- Which was not needed for this problem
	//				- If this is a problem pleas let me know and i CAN implement it
	//			- If we hypothetically had more POI's than colors 
	//				- We (or i at least) would have a variable in the POI class that holds a color 
	//				- Which would be set every time a new POI is created 
	//				- Eventually we would run out of ways to efficiently choose colors that differentiate from each other enough to make any meaningful observations from our visualization
	//		- You will notice the circle drawn in the nested while loop has the radius = 0.005
	//			- This was just what i felt looked the best in the visualization
	//		- The coordinates are scaled to the canvas 
	//			- So any reasonable canvas size should still represent the data in a fairly comprehensible manner
	public void draw(int xSize,int ySize)
	{
		StdDraw.setCanvasSize(xSize,ySize);										// Set canvas
		
		final double r = 0.0025;												// Set radius for Record circles
		double centerX = xSize/2;												// Get center of the x-axis for the canvas
		double centerY = ySize/2;												// Get center of the y-axis for the canvas
		
		Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};					// So we can alternate between colors for a good visualization
		int colorIndex = 0;														// Keep track of color
		double j = xSize/size;
		double n = j/xSize;														// Evenly spread out the text
		int i = 1;

		Node currPOI = head;													// Since we are using the POIList
		while(currPOI != null)
		{
			PointOfInterest tempPOI = (PointOfInterest)currPOI.getData();		// Getting a pointer for easier readability
			StdDraw.setPenColor(colors[colorIndex]);							// Update pen color 
			
			double radius = (tempPOI.getRadius())/(xSize);						// Get radius - Doesn't really matter if we use xSize or ySize
			double xPos	= (centerX - tempPOI.getLongitude())/xSize;				// Get x position for drawing - and scale to canvas size
			double yPos = (centerY - tempPOI.getLatitude())/ySize;				// Get y position for drawing - and scale to canvas size
			
			double poiPos = (n*i)-0.15;											// (n*i)-0.15 (or poiPOS) is to adjust where on the x-axis this text/POI should go
			StdDraw.text(poiPos, 0.90, tempPOI.getID());						// Indicates which POI is which
			StdDraw.circle(xPos,yPos,radius);									// Draw POI circle 
			
			Node currRec = tempPOI.getList().head;								// Get head of RecList for the tempPOI
			while(currRec != null)
			{
				xPos = (centerX - currRec.getData().getLongitude())/xSize;		// Get x position for drawing - scale to canvas size
				yPos = (centerY - currRec.getData().getLatitude())/ySize;		// Get y position for drawing - scale to canvas size
				

				StdDraw.filledCircle(xPos,yPos,r);								// Draw Record - notice radius from Remarks
				currRec = currRec.getNext();
			}
			i++;
			colorIndex++;														// Advance to next color
			currPOI  = currPOI.getNext();
		}
	}
	
	// Method: 	model(int,int)
	// Purpose:	Visualize popularity of each POI on a scale from -10 to 10
	// 
	// Remarks:	
	//		- I wasn't too sure on how to implement this unfortunately
	//		- You will see i tried to add some better visualization by spreading out the Records on the scale
	//			- Before i just had the x-value be set to one exact point and it didn't really show for much
	//			- The x-values are carefully spread out using Math.random() with a specific range
	public void model(int xSize, int ySize)
	{
		StdDraw.setCanvasSize(xSize,ySize);								// Set canvas
		final int T_MIN		= -10;										// From problem set
		final int T_MAX 	= 10;										// From problem set
		final double r = 0.0025;										// Set radius for Record circles
		double centerY = ySize/2;										// Get center of the y-axis for the canvas
		setUp(xSize,ySize);												// Set up the look inside the canvas
		
		
		Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};			// So we can alternate between colors for a good visualization
		double j = xSize/size;
		double n = (j/xSize);											// Evenly spread out the text
		int i = 1;														// For each POI
		int index = 0;													// For colors
		
		Node currPOI = head;
		while(currPOI != null)
		{
			StdDraw.setPenColor(colors[index]);							// Alternating pen color
			PointOfInterest tempPOI = (PointOfInterest)currPOI.getData();// Getting a pointer for easier readability
			
			double poiPos = (n*i)-0.20;									// Adjusting for each POI
			double xPos	= 0.0;											// x - axis location for POI's
			double yPos = 0.95;											// y - axis location for POI's
			
			StdDraw.text(poiPos, yPos, tempPOI.getID());				// Indicates which POI is which
			
			Node currRec = tempPOI.getList().head;
			while(currRec != null)
			{
				double longitude = currRec.getData().getLongitude();	// Getting longitude of Record
				double latitude  = currRec.getData().getLatitude();		// Getting latitude of Record
				
				double scaledY = scale(tempPOI,latitude,T_MIN,T_MAX);	// Scaling latitude (since the y values seem to be the ones that matter here)
				
				
				if(latitude >= tempPOI.getAverage())					// If Above (or equal to) the average 
				{
					yPos = ((centerY + scaledY)/ySize);					// Draw above (or on) the x- axis
				}
				else
				{
					yPos = ((centerY - scaledY)/ySize);					// Else draw below
				}
				
				// These are calculations for the look of the x values 
				// Spreads out the data a little more 
				// Rather than having one little dot of information 
				double max = n + 0.025;		
				double min = n - 0.025;
				double range = (max - min) + 0.025;						//         | POI1 |
				if((longitude/xSize) >= (n/2))							//		   |   |  |
				{														//           (n/2)
					xPos = (poiPos + (Math.random()* range));			// This is basically showing you what's going on
				}														// If the long. is greater than half of n (where the poi is)
				else													// then place it in a random spot between n/2 and n+0.025
				{										
					xPos = (poiPos - (Math.random()* range));			// and vise versa for if long. < n
				}
				
				StdDraw.filledCircle(xPos, yPos,r);						// Draw the Record
				currRec = currRec.getNext();
			}
			i++;														// Advance next "spot" on graph
			index++;													// Advance to next color
			currPOI  = currPOI.getNext();
		}
	}
	
	// Method:	toString()
	// Purpose:	Create a String from a given list 
	// Reasoning:
	//		- Testing
	//		- Writing to file 
	//Remark: This is also a function that uses polymorpishim very nicely 
	public String toString()
	{
		String result = "";
		Node curr = head;
		while(curr != null)
		{
			result += curr.getData().toString()+"\n";
			curr = curr.getNext();
		}
		return result;
	}
	
	//-------------------------
	// Private Helpers
	//-------------------------
	
	// Method:	getMinPOI(Record)
	// Purpose:	Find the minimum distance away from a POI and return that POI
	// Reasoning: Private helper for assignRecords(LL)
	private PointOfInterest getMinPOI(Record r)
	{
		PointOfInterest result = null;							
		Node curr = head;										// Head of POI List
		
		double min = Double.MAX_VALUE;
		while(curr != null)
		{
			double distance = r.getDistance(curr.getData());	// Call Records getDistance(Data) function to calculate distance 
			if(distance < min)
			{
				min = distance;									// If distance < min - update the min
				result = (PointOfInterest)curr.getData();
				r.setDistanceToPOI(min);						// Set the distance to the nearest POI in Record
			}
			curr = curr.getNext();
		}
		return result;
	}
	
	// Method:	getMinRecord()
	// Purpose:	
	// Reasoning: Needed the min Record in each POI for mathematical model
	//
	// Remarks:
	//		- May have been an okay idea to order the list...
	//		- not to get confused with getMinPOI
	public void setMinDistance(PointOfInterest tempPOI)
	{
		double min = Double.MAX_VALUE;
		
		LL tempList = tempPOI.getList();
		Node currRec = tempList.head;											// Since we are in the same class
		while(currRec != null)
		{
			double distance = ((Record)currRec.getData()).getDistanceToPOI();
			if(distance < min)
			{
				min = distance;										
			}
			currRec = currRec.getNext();
		}
		tempPOI.setMinDistance(min);
	}
	
	
	// Method:	setUp(int,int)
	// Purpose:	Set up the general look of the canvas 
	// Reasoning: 
	//		- I thought this would have made the model() method way too big 
	//		- Cleaner code
	private void setUp(int xSize, int ySize)
	{
		final double LEFT 	= 0.05;	
		final double RIGHT	= 0.95;
		final double TOP  	= 0.95;
		final double BOTTOM	= 0.05;
		final double HALF	= 0.50;
		
		
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.line(LEFT, TOP, LEFT,BOTTOM);								// Y - axis
		StdDraw.line(LEFT, HALF,RIGHT, HALF);								// X - axis
		
		StdDraw.text(RIGHT + 0.02, HALF, "X");								// Labeling x-axis
		StdDraw.text(LEFT,TOP + 0.02,"Y");									// Labeling y-axis
		
		
		Node curr = head;
		while(curr != null)
		{
			PointOfInterest tempPOI = (PointOfInterest)curr.getData();		// Getting a pointer for easier readability
			setMinDistance(tempPOI);
			curr = curr.getNext();
		}
	}
	
	// Method: 	scale(PointOfInterest,double,int,int)
	// Purpose:	scale values down to a given interval
	//
	// Remarks:
	//		- This formula can be found on various websites
	private double scale(PointOfInterest p,double x, int tMin, int tMax)
	{
		double rMin = p.getMinDistance();
		double rMax = p.getRadius();
		
		return ((x - rMin)/(rMax - rMin)) * (tMax - tMin) + tMin;
	}
	
	
	//-------------------------
	// Getters/Setters
	//-------------------------
	
	// Method:	getSize()
	// Purpose: Return the size of the list
	public int getSize() {return size;}
}



