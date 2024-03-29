//******************************************************************************
// NPC.java
//******************************************************************************
public class NPC implements java.io.Serializable {
    private String name = "Unknown NPC";
    private int strength = 0;
    private int maxHealth = 0;
    private int health = 0;
    private int vulnerable = 0; // By default the NPC should not be vulnerable aka. killable (vulnerable is used to set if an NPC can be attacked or not)
    private int experience; // How much experience is awarded for this NPC
    private int gold;
    private final int usable = 0; // Sets which kind of use it has Note 171202: What do I mean by "kind of use"?
    private int droppedItem = 0; // Sets which item the monster will drop when killed
    private int powerLevel = 0;

    //--------------------------------------------------------------------------
    // Constructor for a *regular* NPC
    //--------------------------------------------------------------------------
    public NPC (String name) {
        this.name = name;
    }

    //--------------------------------------------------------------------------
    // Constructor for MONSTER/KILLABLE
    //--------------------------------------------------------------------------
    public NPC (String name, int strength, int maxHealth, int gold, int vulnerable, int droppedItem) {
        this.name = name;
        this.strength = strength;
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.gold = gold;
        this.vulnerable = vulnerable;
        this.droppedItem = droppedItem;
        powerLevel = (getStrength() + getMaxHealth());
    }

    //--------------------------------------------------------------------------
    // Constructor for OBJECT: Sets up an NPC object using these values
    // Note 171202: Why is this even here?
    //--------------------------------------------------------------------------
    //public NPC (String name)
    //{
    //	this.name = name;
    //	this.usable = usable;
    //}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setStrength (int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public void setmaxHealth (int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth (int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public int getExperience() {
        int a = 0;
        int b = 0;
        int c = 0;

        if (powerLevel <= 20) {
            a = (powerLevel / 6);
            b = (a * 2);
            c = (a + b);
            experience = c;
        }
        else if (powerLevel >= 20 && powerLevel <= 30) {
            a = (powerLevel / 5);
            b = (a * 2);
            c = (a + b);
            experience = c;
        }
        else if (powerLevel >= 30 && powerLevel <= 40) {
            a = (powerLevel / 4);
            b = (a * 2);
            c = (a + b);
            experience = c;
        }
        else if (powerLevel >= 40 && powerLevel <= 50) {
            a = (powerLevel / 3);
            b = (a * 2);
            c = (a + b);
            experience = c;
        }
        else if (powerLevel >= 50) {
            a = (powerLevel / 2);
            b = (a * 2);
            c = (a + b);
            experience = c;
        }
        
        return experience;
    }

    public int getPowerLevel() {
        powerLevel = (getStrength() + getMaxHealth());
        return powerLevel;
    }

    //--------------------------------------------------------------------------
    // Returns the gold awarded by the NPC
    //--------------------------------------------------------------------------
    public int getGold() {
        return gold;
    }

    //--------------------------------------------------------------------------
    // Returns which item is awarded by the NPC
    //--------------------------------------------------------------------------
    public int getDroppedItem() {
        return droppedItem;
    }

    //--------------------------------------------------------------------------
    // Sets which item is awarded by the NPC
    //--------------------------------------------------------------------------
    public void setDroppedItem (int droppedItem) {
        this.droppedItem = droppedItem;
    }

    public void setVulnerable (int vulnerable) {
        this.vulnerable = vulnerable;
    }

    public int getVulnerable() {
        return vulnerable;
    }

    public int getUsable() {
        return usable;
    }

    //--------------------------------------------------------------------------
    // Returns a string representation of the NPC (its name)
    //--------------------------------------------------------------------------
    public String toString() {
        return name;
    }
}
