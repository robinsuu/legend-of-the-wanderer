//******************************************************************************
// NPC.java
// 
// Represents one NPC with the following parameters:
//
// name (String)
// strength (integer)
// maxHealth (integer)
// health (integer)
// vulnerable (integer) (vulnerable is used to set if an NPC can be attacked or not)
// experience (integer) How much experience is awarded for this NPC
// droppedItem (integer) Sets which item the monster will drop when killed
// usable (String) Sets which kind of use it has
//
// And a set of methods to control those parameters.
//******************************************************************************
import java.io.*;

public class NPC implements java.io.Serializable
{
	private String name = "Unknown NPC";
	private int strength = 0;
	private int maxHealth = 0;
	private int health = 0;
	private int vulnerable = 0; // By default the NPC should not be vulnerable aka. killable
	private int experience;
	private int gold;
	private int usable = 0;
	private int droppedItem = 0;
	private int powerLevel = 0;
	
	//--------------------------------------------------------------------------
	// Constructor for a regular NPC: Sets up an NPC object using these values
	//--------------------------------------------------------------------------
	public NPC (String name)
	{
		this.name = name;
	}
	
	//--------------------------------------------------------------------------
	// Constructor for MONSTER/KILLABLE: Sets up an NPC object using these values
	//--------------------------------------------------------------------------
	public NPC (String name, int strength, int maxHealth, int gold, int vulnerable, int droppedItem)
	{
		this.name = name;
		this.strength = strength;
		this.maxHealth = maxHealth;
		health = maxHealth;
		this.gold = gold;
		this.vulnerable = vulnerable;
		this.droppedItem = droppedItem;
		powerLevel = ( getStrength() + getMaxHealth() );
	}
	
	//--------------------------------------------------------------------------
	// Constructor for OBJECT: Sets up an NPC object using these values
	//--------------------------------------------------------------------------
	//public NPC (String name)
	//{
	//	this.name = name;
	//	this.usable = usable;
	//}
	
	//--------------------------------------------------------------------------
	// Sets the name of the NPC
	//--------------------------------------------------------------------------
	public void setName(String name)
	{
		this.name = name;
	}
	
	//--------------------------------------------------------------------------
	// Returns the name of the NPC
	//--------------------------------------------------------------------------
	public String getName()
	{
		return name;
	}
	
	//--------------------------------------------------------------------------
	// Sets the strength of the NPC
	//--------------------------------------------------------------------------
	public void setStrength (int strength)
	{
		this.strength = strength;
	}
	
	//--------------------------------------------------------------------------
	// Returns the strength of the NPC
	//--------------------------------------------------------------------------
	public int getStrength()
	{
		return strength;
	}
	
	//--------------------------------------------------------------------------
	// Sets the max health of the NPC
	//--------------------------------------------------------------------------
	public void setmaxHealth (int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	//--------------------------------------------------------------------------
	// Returns the max health of the NPC
	//--------------------------------------------------------------------------
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current health of the NPC
	//--------------------------------------------------------------------------
	public void setHealth (int health)
	{
		this.health = health;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current health of the NPC
	//--------------------------------------------------------------------------
	public int getHealth()
	{
		return health;
	}
	
	//--------------------------------------------------------------------------
	// Returns the experience awarded by the NPC
	//--------------------------------------------------------------------------
	public int getExperience()
	{
		int a = 0;
		int b = 0;
		int c = 0;
		
		if (powerLevel <= 20)
		{
			a = (powerLevel / 6);
			b = (a * 2);
			c = (a + b);
			experience = c;
		}
		else
			if (powerLevel >= 20 && powerLevel <= 30)
			{
				a = (powerLevel / 5);
				b = (a * 2);
				c = (a + b);
				experience = c;
			}
			else
				if (powerLevel >= 30 && powerLevel <= 40)
				{
					a = (powerLevel / 4);
					b = (a * 2);
					c = (a + b);
					experience = c;
				}
				else
					if (powerLevel >= 40 && powerLevel <= 50)
					{
						a = (powerLevel / 3);
						b = (a * 2);
						c = (a + b);
						experience = c;
					}
					else
						if (powerLevel >= 50)
						{
							a = (powerLevel / 2);
							b = (a * 2);
							c = (a + b);
							experience = c;
						}
		return experience;
	}
	
	//--------------------------------------------------------------------------
	// Returns the power level of the NPC
	//--------------------------------------------------------------------------
	public int getPowerLevel()
	{
		powerLevel = ( getStrength() + getMaxHealth() );
		return powerLevel;
	}
	
	//--------------------------------------------------------------------------
	// Returns the gold awarded by the NPC
	//--------------------------------------------------------------------------
	public int getGold()
	{
		return gold;
	}
	
	//--------------------------------------------------------------------------
	// Returns which item is awarded by the NPC
	//--------------------------------------------------------------------------
	public int getDroppedItem()
	{
		return droppedItem;
	}
	
	//--------------------------------------------------------------------------
	// Sets which item is awarded by the NPC
	//--------------------------------------------------------------------------
	public void setDroppedItem (int droppedItem)
	{
		this.droppedItem = droppedItem;
	}
	
	//--------------------------------------------------------------------------
	// Sets whether the NPC should be vulnerable or not
	//--------------------------------------------------------------------------
	public void setVulnerable (int vulnerable)
	{
		this.vulnerable = vulnerable;
	}
	
	//--------------------------------------------------------------------------
	// Returns whether the NPC is vulnerable or not
	//--------------------------------------------------------------------------
	public int getVulnerable()
	{
		return vulnerable;
	}
	
	//--------------------------------------------------------------------------
	// Returns if usable or not
	//--------------------------------------------------------------------------
	public int getUsable()
	{
		return usable;
	}
	
	//--------------------------------------------------------------------------
	// Returns a string representation of the NPC name
	//--------------------------------------------------------------------------
	public String toString()
	{
		return name;
	}
	
}
