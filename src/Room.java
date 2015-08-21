//******************************************************************************
// Room.java
// 
// Represents one Room with a description and possible exits.
//
// The corresponding north/south/west/east integer values are the possible 
// exits of the room. 
//
// If set to 0, it's not a possible exit. 
// If set to any number, it leads to that Room ID in the Array (In Game.java).
//******************************************************************************

public class Room 
{
	private String desc;
	private int north = 0;
	private int south = 0;
	private int west = 0;
	private int east = 0;
	private int up = 0;
	private int down = 0;
	private int npc = 0;
	private int originalNPC = 0;

	//--------------------------------------------------------------------------
	// Constructor: Sets up a Room object using these values
	//--------------------------------------------------------------------------
	public Room (String desc, int north, int south, int west, int east, int up, int down, int npc)
	{
		this.desc = desc;
		this.north = north;
		this.south = south;
		this.west = west;
		this.east = east;
		this.up = up;
		this.down = down;
		this.npc = npc;
		originalNPC = npc;
	}
	
	//--------------------------------------------------------------------------
	// Returns value of north
	//--------------------------------------------------------------------------
	public int getNorth()
	{
		return north;
	}
	
	//--------------------------------------------------------------------------
	// Returns value of south
	//--------------------------------------------------------------------------
	public int getSouth()
	{
		return south;
	}
	
	//--------------------------------------------------------------------------
	// Returns value of west
	//--------------------------------------------------------------------------
	public int getWest()
	{
		return west;
	}
	
	//--------------------------------------------------------------------------
	// Returns value of east
	//--------------------------------------------------------------------------
	public int getEast()
	{
		return east;
	}
	
	//--------------------------------------------------------------------------
	// Returns value of up
	//--------------------------------------------------------------------------
	public int getUp()
	{
		return up;
	}
	
	//--------------------------------------------------------------------------
	// Returns value of down
	//--------------------------------------------------------------------------
	public int getDown()
	{
		return down;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of north
	//--------------------------------------------------------------------------
	public void setNorth (int north)
	{
		this.north = north;
	}

	//--------------------------------------------------------------------------
	// Sets the value of south
	//--------------------------------------------------------------------------
	public void setSouth (int south)
	{
		this.south = south;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of west
	//--------------------------------------------------------------------------
	public void setWest (int west)
	{
		this.west = west;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of east
	//--------------------------------------------------------------------------
	public void setEast (int east)
	{
		this.east = east;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of up
	//--------------------------------------------------------------------------
	public void setUp (int up)
	{
		this.up = up;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of down
	//--------------------------------------------------------------------------
	public void setDown (int down)
	{
		this.down = down;
	}
	
	//--------------------------------------------------------------------------
	// Returns the possible room exits as a string
	//--------------------------------------------------------------------------
	public String showExits()
	{
		String exits = "";
		String nExit = "North";
		String sExit = "South";
		String wExit = "West";
		String eExit = "East";
		String uExit = "Up";
		String dExit = "Down";
		
		if (north >= 1)
			exits += nExit + " ";
		if (south >= 1)
			exits += sExit + " ";
		if (west >= 1)
			exits += wExit + " ";
		if (east >= 1)
			exits += eExit + " ";
		if (up >= 1)
			exits += uExit + " ";
		if (down >= 1)
			exits += dExit + " ";
		if (north + south + west + east + up + down <= 0)
			exits = "There are no exits.";
		return exits;
	}
	
	//--------------------------------------------------------------------------
	// Adds an NPC by ID (From the array in Game.java) to the room.
	// But only if there's no previous NPC. Only 1 NPC / room.
	//--------------------------------------------------------------------------
	public void addNPC (int npc)
	{
			this.npc = npc;
	}
	
	//--------------------------------------------------------------------------
	// Respawns an NPC
	//--------------------------------------------------------------------------
	public void respawnNPC()
	{
			npc = originalNPC;
	}
	
	//--------------------------------------------------------------------------
	// Removes an NPC
	//--------------------------------------------------------------------------
	public void removeNPC()
	{
		npc = 0;
	}
	
	//--------------------------------------------------------------------------
	// Removes an NPC permanently
	//--------------------------------------------------------------------------
	public void permanentRemoveNPC ()
	{
		originalNPC = 0;
	}
	
	//--------------------------------------------------------------------------
	// Returns which NPC ID (From the array in Game.java) the room contains. 
	// If 0, contains no NPC
	//--------------------------------------------------------------------------
	public int getNPC()
	{
		return npc;
	}
	
	//--------------------------------------------------------------------------
	// Returns a description of this Room
	//--------------------------------------------------------------------------
	public String toString()
	{
		String formattedDesc = "";
		
		// Removes the first 6 lines of the string.
		// Ie. the description ID from rooms.txt, which we don't want to display.
		formattedDesc = desc.substring (6); 
		
		return breakLines(formattedDesc, 70); // Breaks line at the number of input lines using the breakLines static method
	}
	
	//--------------------------------------------------------------------------
	// Breaks up lines with the parameters <String>, <Break length>
	// Modified code from: http://stackoverflow.com/questions/7528045/large-string-split-into-lines-with-maximum-length-in-java
	//--------------------------------------------------------------------------
	private static String breakLines(String input, int maxLineLength) 
	{
		final char NEWLINE = '\n';
		final String SPACE_SEPARATOR = " ";
		
	    String[] tokens = input.split(SPACE_SEPARATOR);
	    StringBuilder output = new StringBuilder(input.length());
	    int lineLength = 0;
	    for (int i = 0; i < tokens.length; i++) 
	    {
	        String word = tokens[i];

	        if (lineLength + (SPACE_SEPARATOR + word).length() > maxLineLength) 
	        {
	            if (i > 0) {
	                output.append(NEWLINE);
	            }
	            lineLength = 0;
	        }
	        if (i < tokens.length - 1 && (lineLength + (word + SPACE_SEPARATOR).length() + tokens[i + 1].length() <=
	                maxLineLength)) 
	        {
	            word += SPACE_SEPARATOR;
	        }
	        output.append(word);
	        lineLength += word.length();
	    }
	    return output.toString();
	}
}
