

// Class: 	Data
// Purpose: Abstract class the glue Record and PointOfInterest together 
// Reasoning: 
// 		- Saved tons of code
//		- Made for much more cleaner and readable code/methods (at least thats what i thought)
public abstract class Data 
{

	//-------------------------
	// Abstract Methods
	//-------------------------

	public abstract String toString();
	public abstract boolean equals(Data d);
	
	public abstract boolean isFlagged();
	public abstract void flag();			// Mainly for record class but i suppose we could use it in Point class as well
	public abstract double getLatitude();	// I suppose i could have used just the variables themselves but that felt bad and error prone
	public abstract double getLongitude();	// I suppose i could have used just the variables themselves but that felt bad and error prone
	
	//-------------------------
	// Methods
	//-------------------------
	
	// Method: 	parseLatAndLong(String)
	// Purpose:	Parse lat/long into a double format 
	// Reasoning: 
	//		- To save code in Record and PointOfInterest classes
	//		- Cleaner methods
	//		- Performing various calculations
	//
	// Remarks: 
	//		- Unlike time and date parsing, this is actually important for various calculations that are done 
	
	public double parseLatAndLong(String l)
	{
		double result = 0.0;
		int start = 0;
		if(l.charAt(0) == ' ')
		{
			start = 1;
		}
		try
		{
			result = Double.parseDouble(l.substring(start));
		}catch(NumberFormatException nf)
		{
			System.out.println("Counldnt parse lat/long in Data "+ nf.getMessage());
		}
		return result;
	}
	
}
