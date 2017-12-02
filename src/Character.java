//******************************************************************************
// Character.java
//******************************************************************************

public class Character implements java.io.Serializable {
    private final int LEVEL_LIMIT = 20; // Sets the limit to how many times you can level up
    private final int ITEM_LIMIT = 22; // Sets the limit on how many items that exist. This must be equivalent to the number of item objects created below

    public static int inventorySize = 1; // This value stays the same regardless of object referenced. Must be 1 because of the template item #0

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
    // Variables for permanent removal of items and NPCs found in the world
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

    private final Item[] ITEMS; // Array creating the items
    private final int[] EXP_LADDER = new int[LEVEL_LIMIT]; // Array setting up the experience required to level up with a max level of 20 (21 values)

    public Character () {	
        //******************************************************************************
        // ITEM SETUP
        //******************************************************************************
        // Sets up the list of Items. Change this as the number of Items increase.
        ITEMS = new Item[ITEM_LIMIT];

        // (<Item ID>, "<Long Description>", "<Short Description>", <Consumable>)
        ITEMS[0] = new Item (0, "Item #0 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com and report it-", "-ITEM #0 ERROR-"); // Not used
        ITEMS[1] = new Item (1, "a health potion", "potion");
        ITEMS[2] = new Item (2, "a newbie guide book", "guide");
        ITEMS[3] = new Item (3, "a map of the forest", "forest map"); // Not used
        ITEMS[4] = new Item (4, "a strength potion", "strength potion"); // Not used
        ITEMS[5] = new Item (5, "a teleporter", "teleporter"); // Admin item. Teleports to the room specified
        ITEMS[6] = new Item (6, "a small note", "note");
        ITEMS[7] = new Item (7, "the Shadowdrinker", "shadowdrinker");
        ITEMS[8] = new Item (8, "a golden hilt", "hilt");
        ITEMS[9] = new Item (9, "a rune-etched blade", "blade");
        ITEMS[10] = new Item (10, "a beautifully cut red gemstone", "gemstone");
        ITEMS[11] = new Item (11, "a longsword", "longsword");
        ITEMS[12] = new Item (12, "a flying carpet", "carpet");
        ITEMS[13] = new Item (13, "an obsidian camel", "camel");
        ITEMS[14] = new Item (14, "a training manual", "manual");
        ITEMS[15] = new Item (15, "a shortsword", "shortsword");
        ITEMS[16] = new Item (16, "a chainmail armor", "chainmail");
        ITEMS[17] = new Item (17, "a leather armor", "leather");
        ITEMS[18] = new Item (18, "a red crystal pendant", "pendant");
        ITEMS[19] = new Item (19, "a black cat", "cat"); // Used for the black cat quest
        ITEMS[20] = new Item (20, "a violet mushroom", "mushroom"); // Used for the magic mushroom quest
        ITEMS[21] = new Item (21, "a greater health potion", "greater");

        //******************************************************************************
        // EXPERIENCE SETUP
        //******************************************************************************
        int multiple = 0;

        // Initializes the array values
        for (int index = 0; index < LEVEL_LIMIT; index++) {
                multiple += 10; // Sets the multiple by how much the experience required should increase for every level
                EXP_LADDER[index] = index * multiple;
        }
    }
	
    public void setName (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStrength (int strength) {
        this.strength = strength;
    }

    public void addStrength (int strength) {
        this.strength += strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setmaxHealth (int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void addMaxHealth (int maxHealth) {
        this.maxHealth += maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void addHealth (int health) {
        this.health += health;
    }

    public void setHealth (int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void addGold (int gold) {
        this.gold += gold;
    }

    public void removeGold (int gold) {
        this.gold -= gold;
    }

    public void setGold (int gold) {
        this.gold = gold;
    }

    public int getGold() {
        return gold;
    }

    public void addExperience (int experience) {
        this.experience += experience;
    }

    public void removeExperience (int experience) {
        this.experience -= experience;
    }

    public void setExperience (int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    //--------------------------------------------------------------------------
    // Sets the experience required until next level
    //--------------------------------------------------------------------------
    public void setNextLevelExperience (int nextLevelExperience) {
        this.nextLevelExperience = nextLevelExperience;
    }

    //--------------------------------------------------------------------------
    // Returns the experience required until next level
    //--------------------------------------------------------------------------
    public int getNextLevelExperience() {
        return nextLevelExperience;
    }

    public int getPowerLevel() {
        powerLevel = ( strength + maxHealth );
        return powerLevel;
    }


    public void addLevel() {
        this.level += 1;
    }

    public void setLevel (int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    //--------------------------------------------------------------------------
    // Returns the current level of the character // Especially for displaying exp
    // Note 171202: What's the difference between currentLevel and level?
    //--------------------------------------------------------------------------
    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentRoom (int currentRoom) {
        this.currentRoom = currentRoom;
    }

    public int getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentNPC (int currentNPC) {
        this.currentNPC = currentNPC;
    }

    public int getCurrentNPC() {
        return currentNPC;
    }

    public void setBlackCatQuest (int blackCatQuest) {
        this.blackCatQuest = blackCatQuest;
    }

    public int getBlackCatQuest() {
        return blackCatQuest;
    }

    public void setMushroomQuest (int mushroomQuest) {
        this.mushroomQuest = mushroomQuest;
    }

    public void addMushroomQuest (int mushroomQuest) {
        this.mushroomQuest += mushroomQuest;
    }

    public int getMushroomQuest() {
        return mushroomQuest;
    }

    public int getItemLimit() {
        return ITEM_LIMIT;
    }

    public Item getItem (int itemID) {
        return ITEMS[itemID];
    }

    
    //--------------------------------------------------------------------------
    // Get an item description (Used for InfoPanel.java)
    // MAY BE USELESS. TRY TO MAKE THIS WORK WITH getItem() instead
    // Note 171202: Leaving it for now, it's better that this method handles toString instead of getItem doing it
    //--------------------------------------------------------------------------
    public String getItemDescription (int itemID)
    {
            String returnString = ITEMS[itemID].toString();
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
    public void displayInventory() {
        System.out.println ("\tInventory: " + "(" + (getInventorySize() -1) + ")" + "\n"); // Has to be -1 or it will display the wrong number

        int i = 1;

        while (i <= ITEM_LIMIT-1) { // Has to be -1 or it will throw an exception error
            try {
                if (getItem(i).getPlayerHas() >= 1)
                    System.out.println ("\t" + getItem(i) + " (" + getItem(i).getPlayerHas() + ")");
                i++;
            }
            catch (NullPointerException e) {
                System.out.println(e.getMessage() + " counter = " + i);
                e.printStackTrace();
            }
        }
    }

    //--------------------------------------------------------------------------
    // Displays the short descriptions of the inventory
    //--------------------------------------------------------------------------
    public void displayInventoryShort() {
        System.out.println ("\tInventory: " + "(" + (getInventorySize() -1) + ")" + "\n"); // Has to be -1 or it will display the wrong number

        int i = 1;

        while (i <= ITEM_LIMIT-1) { // Has to be -1 or it will throw an exception error
            try
            {
                if (getItem(i).getPlayerHas() >= 1)
                    System.out.println ("\t" + getItem(i).getShortDesc() + " (" + getItem(i).getPlayerHas() + ")");
                i++;
            }

            catch (NullPointerException exception) {
                System.out.println("EXCEPTION ERROR" + " counter = " + i);
            }
        }
    }

    public static int getInventorySize() {
        return inventorySize;
    }

    //-----------------------------------------------------------------------
    // Workaround for making inventory size work after loading a game
    // Set every time the game is saved in Game.java
    //-----------------------------------------------------------------------
    public void saveInventorySize(int invSet) {
        inventorySizeSaved = invSet;
    }

    //-----------------------------------------------------------------------
    // Workaround for making inventory size work after loading a game
    // Run every time the game is loaded in Game.java
    //-----------------------------------------------------------------------
    public void loadInventorySize() {
        inventorySize = inventorySizeSaved;
    }

    public void setBlackCat (int blackCat) {
        this.blackCat = blackCat;
    }

    public int getBlackCat() {
        return blackCat;
    }

    public void setZeramus (int zeramus) {
        this.zeramus = zeramus;
    }

    public int getZeramus() {
        return zeramus;
    }

    public void setHardworkingMan (int hardworkingMan) {
        this.hardworkingMan = hardworkingMan;
    }

    public int getHardworkingMan() {
        return hardworkingMan;
    }

    public void setRottingCorpse (int rottingCorpse) {
        this.rottingCorpse = rottingCorpse;
    }

    public int getRottingCorpse() {
        return rottingCorpse;
    }

    public void setFrozenCorpse (int frozenCorpse) {
        this.frozenCorpse = frozenCorpse;
    }

    public int getFrozenCorpse() {
        return frozenCorpse;
    }

    public void setWoodenChest (int woodenChest) {
        this.woodenChest = woodenChest;
    }

    public int getWoodenChest() {
        return woodenChest;
    }

    public void setAncientChest (int ancientChest) {
        this.ancientChest = ancientChest;
    }

    public int getAncientChest() {
        return ancientChest;
    }

    // Note 171202: Why didn't I use a collection for these?
    public void setVioletMushroom1 (int mushroom) {
        this.violetMushroom1 = mushroom;
    }

    public void setVioletMushroom2 (int mushroom) {
        this.violetMushroom2 = mushroom;
    }

    public void setVioletMushroom3 (int mushroom) {
        this.violetMushroom3 = mushroom;
    }

    public void setVioletMushroom4 (int mushroom) {
        this.violetMushroom4 = mushroom;
    }

    public void setVioletMushroom5 (int mushroom) {
        this.violetMushroom5 = mushroom;
    }

    public void setVioletMushroom6 (int mushroom) {
        this.violetMushroom6 = mushroom;
    }

    public void setVioletMushroom7 (int mushroom) {
        this.violetMushroom7 = mushroom;
    }

    public void setVioletMushroom8 (int mushroom) {
        this.violetMushroom8 = mushroom;
    }

    public int getMushroom1() {
        return violetMushroom1;
    }

    public int getMushroom2() {
        return violetMushroom2;
    }

    public int getMushroom3() {
        return violetMushroom3;
    }

    public int getMushroom4() {
        return violetMushroom4;
    }

    public int getMushroom5() {
        return violetMushroom5;
    }

    public int getMushroom6() {
        return violetMushroom6;
    }

    public int getMushroom7() {
        return violetMushroom7;
    }

    public int getMushroom8() {
        return violetMushroom8;
    }

    public void setGoldenHilt (int goldenHilt) {
        this.goldenHilt = goldenHilt;
    }

    public int getGoldenHilt() {
        return goldenHilt;
    }

    public void setRuneEtchedBlade (int runeEtchedBlade) {
        this.runeEtchedBlade = runeEtchedBlade;
    }

    public int getRuneEtchedBlade() {
        return runeEtchedBlade;
    }

    public void setCurrentLevel (int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void addCurrentLevel (int currentLevel) {
        this.currentLevel += currentLevel;
    }

    //--------------------------------------------------------------------------
    // Displays a table of required experience to level up
    //--------------------------------------------------------------------------
    public void displayExpRequired() {
        int displayLevel = 1; // Used to keep track of which level is displayed in the following list

        // Goes through every level in the expLadder array and displays the value
        for (int value : EXP_LADDER) {
            System.out.print ("Level " + displayLevel + ": " + value + "\n");
            displayLevel += 1;
        }
    }

    //--------------------------------------------------------------------------
    // For display uses only (In the Character toString() method)
    // NOT USED????
    //--------------------------------------------------------------------------
    public void setExpDisplay() {
        setNextLevelExperience(EXP_LADDER[currentLevel]);
    }

    //--------------------------------------------------------------------------
    // Checks if a player should level up, and if so, levels up
    //--------------------------------------------------------------------------
    public void levelUp() {
        while (experience >= EXP_LADDER[currentLevel]) {
            addLevel(); // Adds an actual level to the player
            removeExperience(EXP_LADDER[currentLevel]); // Deducts the experience required for the level
            addStrength(1);
            addMaxHealth(5);

            System.out.println("\nLevel up! You are now level " + level + "\n");

            currentLevel += 1; // Adds a level to the Character class internal level counter

            health = maxHealth; // Heals to 100%

            setNextLevelExperience(EXP_LADDER[currentLevel]);
        }
    }

    public void setRespawnLocation (int respawnLocation) {
        this.respawnLocation = respawnLocation;
    }

    public int getRespawnLocation() {
        return respawnLocation;
    }

    public String toString() {
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