//***************************************************************************
// Item.java
//
// Represents one item object.
//
// itemID: Simply the item's ID-number
// longDesc: The item's long description (Displayed description)
// shortDesc: The item's short description (Supposed to be input by player)
//***************************************************************************
public class Item implements java.io.Serializable
{
	private int itemID = 0;
	private String longDesc = "";
	private String shortDesc = "";

	private int playerHas = 0;
	
	//-----------------------------------------------------------------------
	// Constructor: Sets up an item's characteristics
	//-----------------------------------------------------------------------
	public Item (int itemID, String longDesc, String shortDesc)
	{
		this.itemID = itemID;
		this.longDesc = longDesc;
		this.shortDesc = shortDesc;
		playerHas = 0;
	}
	
	//-----------------------------------------------------------------------
	// Returns the item ID
	//-----------------------------------------------------------------------
	public int getItemID()
	{
		return itemID;
	}
	
	//-----------------------------------------------------------------------
	// Returns the short description if the item
	//-----------------------------------------------------------------------
	public String getShortDesc()
	{
		return shortDesc;
	}
	
	//-----------------------------------------------------------------------
	// Returns the amount of items a player has of this specific item
	//-----------------------------------------------------------------------
	public int getPlayerHas()
	{
		return playerHas;
	}
	
	//-----------------------------------------------------------------------
	// Adds to the amount of items a player has
	//-----------------------------------------------------------------------
	public void addPlayerHas (int addedItems)
	{
		this.playerHas += addedItems;
		Character.inventorySize += addedItems;
	}
	
	//-----------------------------------------------------------------------
	// Decreses the amount of items a player has
	//-----------------------------------------------------------------------
	public void removePlayerHas (int removedItems)
	{
		playerHas -= removedItems;
		Character.inventorySize -= removedItems;
	}
	
	//-----------------------------------------------------------------------
	// Returns a string representation of the item (Long description)
	//-----------------------------------------------------------------------
	public String toString()
	{
		return longDesc;
	}

}
