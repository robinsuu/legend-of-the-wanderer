//******************************************************************************
// Character.java
// 
// Represents one Character with the following parameters:
//
// name (String)
// strength (Integer)
// maxHealth (Integer)
// health (integer)
// level (integer)
// gold (integer)
//
// And a set of methods to control those parameters.
//******************************************************************************
import java.io.*;
import javax.naming.Context;

public class Character implements java.io.Serializable
{
	final int LEVEL_LIMIT = 20; // Sets the limit to how many times you can level up
	final int ITEM_LIMIT = 22; // Sets the limit on how many items that exist. This must be equivalent to the number of item objects created below
	
	static int inventorySize = 1; // This value stays the same regardless of object referenced. Must be 1 because of the template item #0
	
	// TEST VARIABLES FOR InfoPanel
	static String infoHealth = "50";
	
	private int inventorySizeSaved = 1; // workaround for making inventory size function after loading a game
	private String name = "Unknown Character";
	private int strength = 0;
	private int maxHealth = 0;
	private int health = 0;
	private int experience = 0;
	private int nextLevelExperience = 0;
	private int level = 0;
	private int gold = 0;
	private int powerLevel = 0;
	private int currentRoom = 1; // The room which the user is currently in
	private int currentNPC = 0; // The current NPC in the room
	private int currentLevel = 1; // Starting level = 1. Internal level counter for this class. Do not remove
	private int blackCatQuest = 0; // Used to set if the black cat quest is finished or not
	private int mushroomQuest = 0; // Used to set how many mushrooms the player has collected (Finishes at 5)
	private int respawnLocation = 1; // Used to set the location when respawning
	
	//--------------------------------------------------------------------------
	// Variables for permanent removal. Workaround due to poor original design
	//--------------------------------------------------------------------------
	private int hardworkingMan = 0;
	private int blackCat = 0;
	private int zeramus = 0;
	private int rottingCorpse = 0;
	private int frozenCorpse = 0; 
	private int woodenChest = 0; 
	private int ancientChest = 0; 
	private int violetMushroom1 = 0;
	private int violetMushroom2 = 0;
	private int violetMushroom3 = 0;
	private int violetMushroom4 = 0;
	private int violetMushroom5 = 0;
	private int violetMushroom6 = 0;
	private int violetMushroom7 = 0;
	private int violetMushroom8 = 0;
	
	//--------------------------------------------------------------------------
	// Variables for permanent item removal. Workaround due to poor original design.
	//--------------------------------------------------------------------------
	private int goldenHilt = 0;
	private int runeEtchedBlade = 0;
	
	private Item[] items; // Array creating the items
	int[] expLadder = new int[LEVEL_LIMIT]; // Array setting up the experience required to level up with a max level of 20 (21 values)
	
	//--------------------------------------------------------------------------
	// Constructor: Sets up a Character object using these values (Empty in this version)
	//--------------------------------------------------------------------------
	public Character ()
	{	
		//******************************************************************************
		// 
		//									ITEM SETUP
		//
		//******************************************************************************
		// Sets up the list of Items. Change this as the number of Items increase.
		items = new Item[ITEM_LIMIT];
		
		// (<Item ID>, "<Long Description>", "<Short Description>", <Consumable>)
		items[0] = new Item (0, "Item #0 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com and report it-", "-ITEM #0 ERROR-"); // Not used
		items[1] = new Item (1, "a health potion", "potion");
		items[2] = new Item (2, "a newbie guide book", "guide");
		items[3] = new Item (3, "a map of the forest", "forest map"); // Not used
		items[4] = new Item (4, "a strength potion", "strength potion"); // Not used
		items[5] = new Item (5, "a teleporter", "teleporter"); // Admin item. Teleports to the room specified
		items[6] = new Item (6, "a small note", "note");
		items[7] = new Item (7, "the Shadowdrinker", "shadowdrinker");
		items[8] = new Item (8, "a golden hilt", "hilt");
		items[9] = new Item (9, "a rune-etched blade", "blade");
		items[10] = new Item (10, "a beautifully cut red gemstone", "gemstone");
		items[11] = new Item (11, "a longsword", "longsword");
		items[12] = new Item (12, "a flying carpet", "carpet");
		items[13] = new Item (13, "an obsidian camel", "camel");
		items[14] = new Item (14, "a training manual", "manual");
		items[15] = new Item (15, "a shortsword", "shortsword");
		items[16] = new Item (16, "a chainmail armor", "chainmail");
		items[17] = new Item (17, "a leather armor", "leather");
		items[18] = new Item (18, "a red crystal pendant", "pendant");
		items[19] = new Item (19, "a black cat", "cat"); // Used for the black cat quest
		items[20] = new Item (20, "a violet mushroom", "mushroom"); // Used for the magic mushroom quest
		items[21] = new Item (21, "a greater health potion", "greater");
		
		//******************************************************************************
		// 
		//									EXPERIENCE SETUP
		//
		//******************************************************************************
		int multiple = 0;
		
		// Initializes the array values
		for (int index = 0; index < LEVEL_LIMIT; index++)
		{
			multiple += 10; // Sets the multiple by how much the experience required should increase for every level
			expLadder[index] = index * multiple;
		}
	}
	
	//--------------------------------------------------------------------------
	// Sets the name of the character
	//--------------------------------------------------------------------------
	public void setName (String name)
	{
		this.name = name;
	}
	
	//--------------------------------------------------------------------------
	// Returns the name of the character
	//--------------------------------------------------------------------------
	public String getName()
	{
		return name;
	}
	
	//--------------------------------------------------------------------------
	// Sets the strength of the character
	//--------------------------------------------------------------------------
	public void setStrength (int strength)
	{
		this.strength = strength;
	}
	
	//--------------------------------------------------------------------------
	// Adds strength to the character
	//--------------------------------------------------------------------------
	public void addStrength (int strength)
	{
		this.strength += strength;
	}
	
	//--------------------------------------------------------------------------
	// Returns the strength of the character
	//--------------------------------------------------------------------------
	public int getStrength()
	{
		return strength;
	}
	
	//--------------------------------------------------------------------------
	// Sets the max health of the character
	//--------------------------------------------------------------------------
	public void setmaxHealth (int maxHealth)
	{
		this.maxHealth = maxHealth;
	}
	
	//--------------------------------------------------------------------------
	// Adds max health to the character
	//--------------------------------------------------------------------------
	public void addMaxHealth (int maxHealth)
	{
		this.maxHealth += maxHealth;
	}
	
	//--------------------------------------------------------------------------
	// Returns the max health of the character
	//--------------------------------------------------------------------------
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	//--------------------------------------------------------------------------
	// Adds health to the character (Can be used for healing)
	//--------------------------------------------------------------------------
	public void addHealth (int health)
	{
		this.health += health;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current health of the character
	//--------------------------------------------------------------------------
	public void setHealth (int health)
	{
		this.health = health;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current health of the character
	//--------------------------------------------------------------------------
	public int getHealth()
	{
		return health;
	}
	
	//--------------------------------------------------------------------------
	// Adds gold to the character
	//--------------------------------------------------------------------------
	public void addGold (int gold)
	{
		this.gold += gold;
	}
	
	//--------------------------------------------------------------------------
	// Removes gold from the character
	//--------------------------------------------------------------------------
	public void removeGold (int gold)
	{
		this.gold -= gold;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current gold of the character
	//--------------------------------------------------------------------------
	public void setGold (int gold)
	{
		this.gold = gold;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current gold of the character
	//--------------------------------------------------------------------------
	public int getGold()
	{
		return gold;
	}
	
	//--------------------------------------------------------------------------
	// Adds experience to the character
	//--------------------------------------------------------------------------
	public void addExperience (int experience)
	{
		this.experience += experience;
	}
	
	//--------------------------------------------------------------------------
	// Removes experience from the character
	//--------------------------------------------------------------------------
	public void removeExperience (int experience)
	{
		this.experience -= experience;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current experience of the character
	//--------------------------------------------------------------------------
	public void setExperience (int experience)
	{
		this.experience = experience;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current experience of the character
	//--------------------------------------------------------------------------
	public int getExperience()
	{
		return experience;
	}
	
	//--------------------------------------------------------------------------
	// Sets the experience required until next level
	//--------------------------------------------------------------------------
	public void setNextLevelExperience (int nextLevelExperience)
	{
		this.nextLevelExperience = nextLevelExperience;
	}
	
	//--------------------------------------------------------------------------
	// Returns the experience required until next level
	//--------------------------------------------------------------------------
	public int getNextLevelExperience()
	{
		return nextLevelExperience;
	}
	
	//--------------------------------------------------------------------------
	// Returns the power level of the character
	//--------------------------------------------------------------------------
	public int getPowerLevel()
	{
		powerLevel = ( strength + maxHealth );
		return powerLevel;
	}
	
	
	//--------------------------------------------------------------------------
	// Adds a level to the character
	//--------------------------------------------------------------------------
	public void addLevel()
	{
		this.level += 1;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current level of the character
	//--------------------------------------------------------------------------
	public void setLevel (int level)
	{
		this.level = level;
	}
	
	//--------------------------------------------------------------------------
	// Returns the level of the character
	//--------------------------------------------------------------------------
	public int getLevel()
	{
		return level;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current level of the character // Especially for displaying exp
	//--------------------------------------------------------------------------
	public int getCurrentLevel()
	{
		return currentLevel;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current room
	//--------------------------------------------------------------------------
	public void setCurrentRoom (int currentRoom)
	{
		this.currentRoom = currentRoom;
	}
	
	//--------------------------------------------------------------------------
	// Gets the current room
	//--------------------------------------------------------------------------
	public int getCurrentRoom()
	{
		return currentRoom;
	}
	
	//--------------------------------------------------------------------------
	// Sets the current NPC
	//--------------------------------------------------------------------------
	public void setCurrentNPC (int currentNPC)
	{
		this.currentNPC = currentNPC;
	}
	
	//--------------------------------------------------------------------------
	// Gets the current NPC
	//--------------------------------------------------------------------------
	public int getCurrentNPC()
	{
		return currentNPC;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of the black cat quest
	//--------------------------------------------------------------------------
	public void setBlackCatQuest (int blackCatQuest)
	{
		this.blackCatQuest = blackCatQuest;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current value of the black cat quest
	//--------------------------------------------------------------------------
	public int getBlackCatQuest()
	{
		return blackCatQuest;
	}
	
	//--------------------------------------------------------------------------
	// Sets the value of the mushroom quest
	//--------------------------------------------------------------------------
	public void setMushroomQuest (int mushroomQuest)
	{
		this.mushroomQuest = mushroomQuest;
	}
	
	//--------------------------------------------------------------------------
	// Adds to the mushroom quest
	//--------------------------------------------------------------------------
	public void addMushroomQuest (int mushroomQuest)
	{
		this.mushroomQuest += mushroomQuest;
	}
	
	//--------------------------------------------------------------------------
	// Returns the current value of the mushroom quest
	//--------------------------------------------------------------------------
	public int getMushroomQuest()
	{
		return mushroomQuest;
	}
	
	//--------------------------------------------------------------------------
	// Returns the ITEM_LIMIT value
	//--------------------------------------------------------------------------
	public int getItemLimit()
	{
		return ITEM_LIMIT;
	}
	
	//--------------------------------------------------------------------------
	// Get an item object
	//--------------------------------------------------------------------------
	public Item getItem (int itemID)
	{
		return items[itemID];
	}
	
	//--------------------------------------------------------------------------
	// Get an item description (Used for InfoPanel.java)
	// MAY BE USELESS. TRY MAKE THIS WORK WITH getItem() instead
	//--------------------------------------------------------------------------
	public String getItemDescription (int itemID)
	{
		String returnString = items[itemID].toString();
		return returnString;
	}
	
	//--------------------------------------------------------------------------
	// Displays the player's inventory
	//
	// (FIXED)BUG: ALWAYS display in order. Once an item is used and removed, the
	// items that come after that item will disappear.
	// The inventorySize static variable is working fine though
	// I want to display ALL the items which are getPlayerHas >= 1
	// Might have to change the while conditional from the Item.getInventorySize()
	// to something else
	//
	// UPDATE: SEEMS TO BE WORKING NOW BUT NEEDS SOLID CONFIRMATION AS TO HOW
	// AND WHY
	//--------------------------------------------------------------------------
	public void displayInventory()
	{
		System.out.println ("\tInventory: " + "(" + (getInventorySize() -1) + ")" + "\n"); // Has to be -1 or it will display the wrong number
		
		int counter = 1;
		
		while (counter <= ITEM_LIMIT-1) // Has to be -1 or it will throw an exception error
		{
			try
			{
				if (getItem(counter).getPlayerHas() >= 1)
					System.out.println ("\t" + getItem(counter) + " (" + getItem(counter).getPlayerHas() + ")");
				
				counter++;
			}
			
			catch (NullPointerException exception)
			{
				System.out.println("EXCEPTION ERROR" + " counter = " + counter);
			}
		}
	}
	
	//--------------------------------------------------------------------------
	// Displays the short descriptions of the inventory
	//--------------------------------------------------------------------------
	public void displayInventoryShort()
	{
		System.out.println ("\tInventory: " + "(" + (getInventorySize() -1) + ")" + "\n"); // Has to be -1 or it will display the wrong number
		
		int counter = 1;
		
		while (counter <= ITEM_LIMIT-1) // Has to be -1 or it will throw an exception error
		{
			try
			{
				if (getItem(counter).getPlayerHas() >= 1)
					System.out.println ("\t" + getItem(counter).getShortDesc() + " (" + getItem(counter).getPlayerHas() + ")");
				
				counter++;
			}
			
			catch (NullPointerException exception)
			{
				System.out.println("EXCEPTION ERROR" + " counter = " + counter);
			}
		}
	}
	
	//-----------------------------------------------------------------------
	// Returns the player inventory size
	//-----------------------------------------------------------------------
	public static int getInventorySize()
	{
		return inventorySize;
	}
	
	//-----------------------------------------------------------------------
	// Workaround for making inventory size work after loading a game
	// Set every time the game is saved in Game.java
	//-----------------------------------------------------------------------
	public void saveInventorySize(int invSet)
	{
		inventorySizeSaved = invSet;
	}
	
	//-----------------------------------------------------------------------
	// Workaround for making inventory size work after loading a game
	// Run every time the game is loaded in Game.java
	//-----------------------------------------------------------------------
	public void loadInventorySize()
	{
		inventorySize = inventorySizeSaved; // workaround for making inventory size function after loading a game
	}
	
	//-----------------------------------------------------------------------
	// Set blackCat
	//-----------------------------------------------------------------------
	public void setBlackCat (int blackCat)
	{
		this.blackCat = blackCat;
	}
	
	//-----------------------------------------------------------------------
	// Get blackCat
	//-----------------------------------------------------------------------
	public int getBlackCat()
	{
		return blackCat;
	}
	
	//-----------------------------------------------------------------------
	// Set zeramus
	//-----------------------------------------------------------------------
	public void setZeramus (int zeramus)
	{
		this.zeramus = zeramus;
	}
	
	//-----------------------------------------------------------------------
	// Get zeramus
	//-----------------------------------------------------------------------
	public int getZeramus()
	{
		return zeramus;
	}
	
	//-----------------------------------------------------------------------
	// Set hardworkingMan
	//-----------------------------------------------------------------------
	public void setHardworkingMan (int hardworkingMan)
	{
		this.hardworkingMan = hardworkingMan;
	}
	
	//-----------------------------------------------------------------------
	// Get hardworkingMan
	//-----------------------------------------------------------------------
	public int getHardworkingMan()
	{
		return hardworkingMan;
	}
	
	//-----------------------------------------------------------------------
	// Set rottingCorpse
	//-----------------------------------------------------------------------
	public void setRottingCorpse (int rottingCorpse)
	{
		this.rottingCorpse = rottingCorpse;
	}
	
	//-----------------------------------------------------------------------
	// Get rottingCorpse
	//-----------------------------------------------------------------------
	public int getRottingCorpse()
	{
		return rottingCorpse;
	}
	
	//-----------------------------------------------------------------------
	// Set frozenCorpse
	//-----------------------------------------------------------------------
	public void setFrozenCorpse (int frozenCorpse)
	{
		this.frozenCorpse = frozenCorpse;
	}
	
	//-----------------------------------------------------------------------
	// Get frozenCorpse
	//-----------------------------------------------------------------------
	public int getFrozenCorpse()
	{
		return frozenCorpse;
	}
	
	//-----------------------------------------------------------------------
	// Set woodenChest
	//-----------------------------------------------------------------------
	public void setWoodenChest (int woodenChest)
	{
		this.woodenChest = woodenChest;
	}
	
	//-----------------------------------------------------------------------
	// Get woodenChest
	//-----------------------------------------------------------------------
	public int getWoodenChest()
	{
		return woodenChest;
	}
	
	//-----------------------------------------------------------------------
	// Set ancientChest
	//-----------------------------------------------------------------------
	public void setAncientChest (int ancientChest)
	{
		this.ancientChest = ancientChest;
	}
	
	//-----------------------------------------------------------------------
	// Get ancientChest
	//-----------------------------------------------------------------------
	public int getAncientChest()
	{
		return ancientChest;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom1
	//-----------------------------------------------------------------------
	public void setVioletMushroom1 (int mushroom)
	{
		this.violetMushroom1 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom2
	//-----------------------------------------------------------------------
	public void setVioletMushroom2 (int mushroom)
	{
		this.violetMushroom2 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom3
	//-----------------------------------------------------------------------
	public void setVioletMushroom3 (int mushroom)
	{
		this.violetMushroom3 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom4
	//-----------------------------------------------------------------------
	public void setVioletMushroom4 (int mushroom)
	{
		this.violetMushroom4 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom5
	//-----------------------------------------------------------------------
	public void setVioletMushroom5 (int mushroom)
	{
		this.violetMushroom5 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom6
	//-----------------------------------------------------------------------
	public void setVioletMushroom6 (int mushroom)
	{
		this.violetMushroom6 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom7
	//-----------------------------------------------------------------------
	public void setVioletMushroom7 (int mushroom)
	{
		this.violetMushroom7 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Set violetMushroom8
	//-----------------------------------------------------------------------
	public void setVioletMushroom8 (int mushroom)
	{
		this.violetMushroom8 = mushroom;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom1
	//-----------------------------------------------------------------------
	public int getMushroom1 ()
	{
				return violetMushroom1;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom2
	//-----------------------------------------------------------------------
	public int getMushroom2 ()
	{
				return violetMushroom2;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom3
	//-----------------------------------------------------------------------
	public int getMushroom3 ()
	{
				return violetMushroom3;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom4
	//-----------------------------------------------------------------------
	public int getMushroom4 ()
	{
				return violetMushroom4;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom5
	//-----------------------------------------------------------------------
	public int getMushroom5 ()
	{
				return violetMushroom5;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom6
	//-----------------------------------------------------------------------
	public int getMushroom6 ()
	{
				return violetMushroom6;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom7
	//-----------------------------------------------------------------------
	public int getMushroom7 ()
	{
				return violetMushroom7;
	}
	
	//-----------------------------------------------------------------------
	// Get violetMushroom8
	//-----------------------------------------------------------------------
	public int getMushroom8 ()
	{
				return violetMushroom8;
	}
	
	//-----------------------------------------------------------------------
	// Set goldenHilt
	//-----------------------------------------------------------------------
	public void setGoldenHilt (int goldenHilt)
	{
		this.goldenHilt = goldenHilt;
	}
	
	//-----------------------------------------------------------------------
	// Get goldenHilt
	//-----------------------------------------------------------------------
	public int getGoldenHilt()
	{
		return goldenHilt;
	}
	
	//-----------------------------------------------------------------------
	// Set runeEtchedBlade
	//-----------------------------------------------------------------------
	public void setRuneEtchedBlade (int runeEtchedBlade)
	{
		this.runeEtchedBlade = runeEtchedBlade;
	}
	
	//-----------------------------------------------------------------------
	// Get runeEtchedBlade
	//-----------------------------------------------------------------------
	public int getRuneEtchedBlade()
	{
		return runeEtchedBlade;
	}
	
	//-----------------------------------------------------------------------
	// Set currentLevel
	//-----------------------------------------------------------------------
	public void setCurrentLevel (int currentLevel)
	{
		this.currentLevel = currentLevel;
	}
	
	//-----------------------------------------------------------------------
	// Add to currentLevel
	//-----------------------------------------------------------------------
	public void addCurrentLevel (int currentLevel)
	{
		this.currentLevel += currentLevel;
	}
	
	//--------------------------------------------------------------------------
	// Displays a table of required experience to level up
	//--------------------------------------------------------------------------
	public void displayExpRequired()
	{
		int displayLevel = 1; // Used to keep track of which level is displayed in the following list
		
		// Goes through every level in the expLadder array and displays the value
		for (int value : expLadder)
		{
			System.out.print ("Level " + displayLevel + ": " + value + "\n");
			displayLevel += 1;
		}
	}
	
	//--------------------------------------------------------------------------
	// For display uses only (In the Character toString() method)
	// NOT USED????
	//--------------------------------------------------------------------------
	public void setExpDisplay()
	{
		setNextLevelExperience(expLadder[currentLevel]);
	}
	
	//--------------------------------------------------------------------------
	// Checks if a player should level up, and if so, levels up
	//--------------------------------------------------------------------------
	public void levelUp()
	{
		while (experience >= expLadder[currentLevel])
		{
			addLevel(); // Adds an actual level to the player
			removeExperience(expLadder[currentLevel]); // Deducts the experience required for the level
			addStrength(1);
			addMaxHealth(5);
			
			System.out.println("\nLevel up! You are now level " + level + "\n");
			
			currentLevel += 1; // Adds a level to the Character class internal level counter
			
			health = maxHealth; // Heals to 100%
			
			setNextLevelExperience(expLadder[currentLevel]);
		}
	}
	
	//-----------------------------------------------------------------------
	// Set respawn point
	//-----------------------------------------------------------------------
	public void setRespawnLocation (int respawnLocation)
	{
		this.respawnLocation = respawnLocation;
	}
	
	//-----------------------------------------------------------------------
	// Get respawn point
	//-----------------------------------------------------------------------
	public int getRespawnLocation()
	{
		return respawnLocation;
	}
	
	//-----------------------------------------------------------------------
	// Returns a string representation of the character
	//-----------------------------------------------------------------------
	public String toString()
	{
		String charInfo;
		
		charInfo = ("\nName: " + name);
		charInfo += ("\nLevel: " + level);
		charInfo += ("\nExperience: " + experience + "/" + nextLevelExperience);
		charInfo += ("\nStrength: " + strength);
		charInfo += ("\nHP: " + health + "/" + maxHealth);
		charInfo += ("\nGold: " + gold + "\n");
		
		return charInfo;
	}
}
