//******************************************************************************
// Game.java
// 
// A simple game where the user can move through some preset text-based
// rooms and interact using several different commands.
//
//******************************************************************************
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.io.*;

public class Game implements java.io.Serializable {
    private String userInput = ""; // User input commands

    Random rand = new Random();
    Character player = new Character();

    private final Room[] ROOMS; // Array creating the game world aka. rooms
    private final NPC[] NPCS; // Array creating the NPCs

    //--------------------------------------------------------------------------
    // Constructor: Sets the initial values of the game variables and creates
    // the rooms.
    //--------------------------------------------------------------------------
    public Game () throws IOException {
        // Room setup
        RoomSetup roomSetup = new RoomSetup();
        ROOMS = roomSetup.getRooms();
        
        //******************************************************************************
        // NPC SETUP
        //******************************************************************************

        // Sets up the list of NPCs. Change this as the number of NPCs increase
        NPCS = new NPC[220];

        // Note: NPC conversations are located in the conversation() method further below
        // Note #2: The room objects have different parameters as seen below, but they are in a sense also NPCs
        // Should be solved using inheritance or a completely different design in future projects

        NPCS[0] = new NPC ("NPC #0 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com and report it-", 0, 0, 0, 0, 0); // Not used

        // Unkillable NPCs
        NPCS[1] = new NPC ("a butler");
        NPCS[2] = new NPC ("a cheerful merchant");
        NPCS[3] = new NPC ("a pretty lady");
        NPCS[4] = new NPC ("a wandering salesman");
        NPCS[5] = new NPC ("a hardworking man");
        NPCS[6] = new NPC ("a villager");
        NPCS[7] = new NPC ("Grunbald the magician");
        NPCS[8] = new NPC ("Skeld the blacksmith");
        NPCS[9] = new NPC ("a town guard");
        NPCS[10] = new NPC ("a philosopher");
        NPCS[11] = new NPC ("an elderly villager");
        NPCS[12] = new NPC ("a frightened town guard");
        NPCS[13] = new NPC ("Glenn the bard");
        NPCS[14] = new NPC ("Lianne");
        NPCS[15] = new NPC ("Thorulf");
        NPCS[16] = new NPC ("Asta");
        NPCS[17] = new NPC ("a hermit");
        NPCS[18] = new NPC ("a member of the Zulah tribe");
        NPCS[19] = new NPC ("the shaman of the Zulah tribe");
        NPCS[20] = new NPC ("a hunter of the Zulah tribe");
        NPCS[21] = new NPC ("a cackling mad man");
        NPCS[22] = new NPC ("the guardian of the tomb");
        NPCS[23] = new NPC ("Ezme the carpet trader");
        NPCS[24] = new NPC ("Josephine");
        NPCS[25] = new NPC ("Rupert");
        NPCS[26] = new NPC ("an old man smoking his pipe");
        NPCS[27] = new NPC ("a wounded soldier tending to his comrade");
        NPCS[28] = new NPC ("Lieutenant Harken");
        NPCS[29] = new NPC ("a desert trader");
        NPCS[30] = new NPC ("Harald the innkeeper");

        NPCS[100] = new NPC ("Satan Golga"); // Test NPC

        // Killable NPCs
        // ("<NPC name>", <Strength>, <Max Health>, <Gold>, <Vulnerable>, <Dropped Item>)
        NPCS[101] = new NPC ("a black cat", 2, 8, 0, 0, 0); // Used in the quest from the butler. Made invulnerable. In room #1
        NPCS[102] = new NPC ("a red fox", 6, 14, 0, 1, 0);
        NPCS[103] = new NPC ("a donkey", 5, 16, 0, 1, 0);
        NPCS[104] = new NPC ("a tiny bird", 1, 8, 0, 1, 0);
        NPCS[105] = new NPC ("a cockroach", 1, 8, 0, 1, 0);
        NPCS[106] = new NPC ("an owl", 7, 13, 0, 1, 0);
        NPCS[107] = new NPC ("a sleeping bear", 17, 50, 0, 1, 0);
        NPCS[108] = new NPC ("a dirty vagrant", 9, 20, 7, 1, 0);
        NPCS[109] = new NPC ("a brown cow", 6, 17, 0, 1, 0);
        NPCS[110] = new NPC ("a sick-looking wolf", 13, 25, 0, 1, 0);
        NPCS[111] = new NPC ("a sobbing little girl ghost", -10, -10, 0, 1, 0); // Awards negative experience. Testing needed.
        NPCS[112] = new NPC ("a small goblin", 9, 20, 10, 1, 0);
        NPCS[113] = new NPC ("a goblin warrior", 12, 30, 15, 1, 0);
        NPCS[114] = new NPC ("a lazy goblin guard", 11, 25, 13, 1, 0);
        NPCS[115] = new NPC ("a goblin cook", 10, 20, 13, 1, 0);
        NPCS[116] = new NPC ("the Goblin King", 23, 70, 200, 1, 8); // Drops a golden hilt (#8)
        NPCS[117] = new NPC ("an almost lifeless prisoner", 0, 10, 0, 1, 0);
        NPCS[118] = new NPC ("a street thug", 12, 30, 14, 1, 0);
        NPCS[119] = new NPC ("a big rat", 4, 12, 0, 1, 0);
        NPCS[120] = new NPC ("a road bandit", 12, 30, 12, 1, 0);
        NPCS[121] = new NPC ("a mountain lion", 16, 45, 0, 1, 0);
        NPCS[122] = new NPC ("a mountain goat", 5, 15, 0, 1, 0);
        NPCS[123] = new NPC ("a marsh horror", 13, 35, 0, 1, 0);
        NPCS[124] = new NPC ("a poisonous green frog", 15, 10, 0, 1, 0);
        NPCS[125] = new NPC ("a grave robber", 11, 25, 18, 1, 0);
        NPCS[126] = new NPC ("Otri the Undying", 26, 80, 300, 1, 9); // Drops a rune-etched blade (#9)
        NPCS[127] = new NPC ("a chainmail armored skeleton", 12, 40, 20, 1, 0);
        NPCS[128] = new NPC ("a soulless corpse", 8, 40, 10, 1, 0);
        NPCS[129] = new NPC ("a marsh serpent", 13, 35, 0, 1, 0);
        NPCS[130] = new NPC ("a terrified grave robber", 9, 25, 15, 1, 0);
        NPCS[131] = new NPC ("a rattlesnake", 12, 14, 0, 1, 0);
        NPCS[132] = new NPC ("a scorpion", 15, 10, 0, 1, 0);
        NPCS[133] = new NPC ("the minotaur of the maze", 23, 75, 100, 1, 0);
        NPCS[134] = new NPC ("an elite guard trapped in the maze", 16, 50, 40, 1, 0);
        NPCS[135] = new NPC ("a horned satyr", 14, 50, 30, 1, 0);
        NPCS[136] = new NPC ("a huge vampire bat", 15, 40, 0, 1, 0);
        NPCS[137] = new NPC ("an elite guard wearing a black mask", 16, 50, 35, 1, 0);
        NPCS[138] = new NPC ("Zeramus", 50, 500, 500, 0, 0); // Even though he is listed here, he is unkillable in normal combat.
        NPCS[139] = new NPC ("a wooden practice doll", 0, 5, 0, 1, 0);
        NPCS[140] = new NPC ("a stray dog", 6, 14, 0, 1, 0);
        NPCS[141] = new NPC ("a hawk", 10, 20, 0, 1, 0);
        NPCS[142] = new NPC ("a crazy mountaineer", 11, 25, 10, 1, 0);
        NPCS[143] = new NPC ("an evil forest spirit", 8, 25, 0, 1, 0);

        // Objects
        // Note: The effect when used is specified in the useObject() method further below
        // Should also add a short description like the Items to display possible object commands to the player
        NPCS[201] = new NPC ("a marble fountain");
        NPCS[202] = new NPC ("a fortune telling machine");
        NPCS[203] = new NPC ("a small chair");
        NPCS[204] = new NPC ("a scarecrow");
        NPCS[205] = new NPC ("a rotting corpse");
        NPCS[206] = new NPC ("a wooden elevator"); // Going down
        NPCS[207] = new NPC ("a wooden elevator"); // Going up
        NPCS[208] = new NPC ("the frozen corpse of an adventurer");
        NPCS[209] = new NPC ("a bonfire"); // in the Zulah tribe
        NPCS[210] = new NPC ("a wooden chest covered with grime"); // in the marsh
        NPCS[211] = new NPC ("an ancient chest"); // in the tomb
        NPCS[212] = new NPC ("a wooden sign"); // the sign in the marsh
        NPCS[213] = new NPC ("a black cat"); // the cat that the player picks up for the black cat quest
        NPCS[214] = new NPC ("a violet mushroom"); // used for the magic mushroom quest
    }

    //--------------------------------------------------------------------------
    // Runs the application
    //--------------------------------------------------------------------------
    public void runGame()
    {
        System.out.println("\n\n\n");
        System.out.println(""
        + "\n _                               _"              
        + "\n| |                             | |"             
        + "\n| |     ___  __ _  ___ _ __   __| |"             
        + "\n| |    / _ \\/ _` |/ _ \\ '_ \\ / _` |"             
        + "\n| |___|  __/ (_| |  __/ | | | (_| |"             
        + "\n|______\\___|\\__, |\\___|_| |_|\\__,_|"             
        + "\n             __/ |                 "             
        + "\n            |___/                  "             
        + "\n        __   _   _                 "             
        + "\n       / _| | | | |                "             
        + "\n  ___ | |_  | |_| |__   ___        "             
        + "\n / _ \\|  _| | __| '_ \\ / _ \\       "             
        + "\n| (_) | |   | |_| | | |  __/       "             
        + "\n \\___/|_|    \\__|_| |_|\\___|       "             
        + "\n                                   "             
        + "\n                                   "             
        + "\n__             __              _       "             
        + "\n\\ \\         / /             | |      "             
        + "\n \\ \\  /\\  / /_ _ _ __   __| | ___ _ __ ___ _ __ "
        + "\n  \\ \\/  \\/ / _` | '_ \\ / _` |/ _ \\ '__/ _ \\ '__|"
        + "\n   \\  /\\  / (_| | | | | (_| |  __/ | |  __/ |   "
        + "\n    \\/  \\/ \\__,_|_| |_|\\__,_|\\___|_|  \\___|_|   "
        + "\n                                                ");

        System.out.println("\n\n");
        // Displays welcome message (Add credit for the C class as well)
        System.out.println("\nWelcome to Legend of the Wanderer"
                        + "\n\nCode by: \tRobin Fjärem"
                        + "\nE-mail: \trobin.fjarem@gmail.com"
                        + "\nWebsite: \thttp://lazycapybara.com/"
                        + "\nLicensed under http://creativecommons.org/licenses/by/4.0/\n");

        startPrompt();
    }

    private void startPrompt() {
        // Starting menu
        String startAnswer = "";

        System.out.println ("Would you like to start a new game or load a game?");

        System.out.println ("\n(new/load):");
        startAnswer = C.io.nextLine();

        switch (startAnswer) {
            case ("new"):
                String confirmAnswer = "";

                System.out.println ("Are you sure you want to start a new character? Any old data will be overwritten.");
                System.out.println ("\n(yes/no)");
                confirmAnswer = C.io.nextLine();

                switch (confirmAnswer) {
                    case ("yes"):
                        createCharacter(); // Generates a new character
                    break;
                    case ("no"):
                        runGame();
                    break;
                    default:
                        System.out.println ("Invalid input.");
                        runGame();
                    break;
                }

                // Displays character info
                System.out.println (player);

                // Displays the first message of the game
                System.out.println ("You are " + player.getName() + " the wanderer, and your story begins here.\n");

                System.out.println ("You have been sent out by your lord from the lands of the north to find out what is happening in this area. "
                                + "There has been rumors of war, of a shadow drawing in from the far south, of unspeakable horrors. "
                                + "You must do everything in your power to find out what is behind all of this and report back to your lord.\n");

                System.out.println ("'What is this pain..' You think as you wake up in\n"
                                + "a bed with all your clothes on and a splitting headache. Maybe you shouldn't have finished that last bottle.\n");

                System.out.println("Maybe you should go take some rest in the garden outside the west part of the house.\n");
            break;
            case ("load"):
                loadGame();
                displayRoom();
            break;
            default:
                System.out.println ("Invalid input.");
                runGame();
            break;
        }

        // Starts the respawn timer
        respawnTimer();
        // Begins the game
        navigate();
    }

    //--------------------------------------------------------------------------
    // Generates a character
    //--------------------------------------------------------------------------
    private void createCharacter()
    {
        System.out.print ("Please enter your name: ");
        userInput = C.io.nextLine(); // MUST BE C.io.nextLine(); to work in the GUI
        if (userInput.length() > 30) {
            System.out.println ("The name is too long, please try again.");
            createCharacter();
        }
        player.setName (userInput);

        player.setStrength (10);
        player.setmaxHealth (30);
        player.setHealth(player.getMaxHealth());
        player.setLevel(player.getCurrentLevel());
        player.setExpDisplay();
        player.setGold(0);

        // Sets starting items
        player.getItem(1).addPlayerHas(3); // #1 a health potion
        player.getItem(2).addPlayerHas(1); // #2 a newbie guide book
        player.getItem(15).addPlayerHas(1); // #15 a shortsword
        player.getItem(17).addPlayerHas(1); // #17 a leather armor

        // ADMIN ITEMS
        //player.getItem(5).addPlayerHas(1); // #5 a teleporter
        //player.addGold(1000);
        //player.getItem(20).addPlayerHas(1);
        //player.getItem(6).addPlayerHas(1);
        //player.getItem(7).addPlayerHas(1);
        //player.getItem(8).addPlayerHas(2);
        //player.getItem(9).addPlayerHas(1);
        //player.getItem(10).addPlayerHas(4);
    }

    //--------------------------------------------------------------------------
    // Command line (Shows HP status and this is also where the user input
    // their commands
    // Add this in order to track rooms as admin:
    // System.out.print ("#" + player.getCurrentRoom() + "<" + player.getHealth() + "/" + player.getMaxHealth() + ">");
    //--------------------------------------------------------------------------
    private void commandLine()
    { 
        //System.out.print ("#" + player.getCurrentRoom() + "<" + player.getHealth() + "/" + player.getMaxHealth() + ">"); <--- ADMIN TRACKING
        System.out.print ("<" + player.getHealth() + "/" + player.getMaxHealth() + ">");
        userInput = C.io.nextLine(); // MUST BE C.io.nextLine(); to work in the GUI. Original is scan.nextLine();
        userInput = userInput.toLowerCase(); // User input is not case-sensitive as all commands are converted to lower case
    }

    //--------------------------------------------------------------------------
    // Calls the relevant methods based on what was input at commandLine()
    //--------------------------------------------------------------------------
    private void navigate () {
        backgroundSave(); // THIS IS NEEDED TO UPDATE THE GUI IN InfoPanel.java. It is the same as saveGame() except it doesn't display a message
        commandLine();

        // Far from optimal location, but will remain here until I find a better way to make it
        theMarsh(); // The marsh randomizer

        switch (userInput) {
            // Navigation commands
            case ("n"):
            case ("north"):
                goNorth();
                break;
            case ("s"):
            case ("south"):
                goSouth();
                break;
            case ("w"):
            case ("west"):
                goWest();
                break;
            case ("e"):
            case ("east"):
                goEast();
                break;
            case ("u"):
            case ("up"):
                goUp();
                break;
            case ("d"):
            case ("down"):
                goDown();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("look"):
            case ("l"):
                displayRoom(); // Displays the current room
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("experience"):
            case ("exp"):
                player.displayExpRequired();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("inventory"):
            case ("inv"):
            case ("i"):
                player.displayInventory();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("invs"):
                player.displayInventoryShort();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("exits"):
                System.out.println (ROOMS[player.getCurrentRoom()].showExits()); // Displays the current room exits
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("score"):
            case ("sc"):
                System.out.println (player); // Displays the character sheet
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("gold"):
                System.out.println ("Gold: " + player.getGold());
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("clear"):
                C.io.clear();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("consider"):
            case ("cons"):
                consider();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("talk"):
                talkTo();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("interact"):
            case ("in"):
                useObject();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("kill"):
            case ("k"):
                attack();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("commands"):
            case ("help"):
                System.out.println ("\n\t__________________________________"
                                + "\n\tNavigation commands"
                                + "\n\t__________________________________"
                                + "\n\tnorth, south, west, east, up, down"
                                + "\n\tn, s, w, e, u, d"
                                + "\n\tlook, l"
                                + "\n\texits"

                                + "\n\n\t__________________________________"
                                + "\n\tInformation commands"
                                + "\n\t__________________________________"
                                + "\n\tscore, sc"
                                + "\n\tgold"
                                + "\n\tconsider, cons"
                                + "\n\tinventory, inv, i"
                                + "\n\tinvs (Displays the short description of the items for the \"use\" command)"
                                + "\n\texperience, exp"

                                + "\n\n\t__________________________________"
                                + "\n\tAction commands"
                                + "\n\t__________________________________"
                                + "\n\tuse <item from inventory>"
                                + "\n\tin, interact (To interact with an object in the room)"
                                + "\n\ttalk"
                                + "\n\tkill, k"

                                + "\n\n\t__________________________________"
                                + "\n\tOther commands"
                                + "\n\t__________________________________"
                                + "\n\tcredit"
                                + "\n\tclear"
                                + "\n\tsave"
                                + "\n\tquit");
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("save"):
                saveGame();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("credit"):
                credit();
                break;
    //-----------------------------------------------------------------------------------------------------------
            case ("quit"):
                quitGame();
                break;
    //-----------------------------------------------------------------------------------------------------------
            default:
                if (userInput.contains("use"))
                    useItem();
                else
                    System.out.println ("Invalid input. Try again.");
                break;
        }
        navigate();
    }

    //--------------------------------------------------------------------------
    // Sets the NPC of the current room as well as displaying
    // the room and the NPCs it contain
    //--------------------------------------------------------------------------
    private void displayRoom() {
        displayCompass();

        System.out.println (ROOMS[player.getCurrentRoom()]);

        player.setCurrentNPC(ROOMS[player.getCurrentRoom()].getNPC()); // Sets the current NPC to the NPC specified in the current Room

        if (player.getCurrentNPC() >= 1)
                System.out.println ("\t" + NPCS[player.getCurrentNPC()]);	
    }

    //--------------------------------------------------------------------------
    // Displays the compass with the possible exits
    //--------------------------------------------------------------------------
    private void displayCompass() {
        String north = "-";
        String south = "-";
        String west = "-";
        String east = "-";
        String up = "-";
        String down = "-";

        if (ROOMS[player.getCurrentRoom()].getNorth() >= 1)
                north = "N";
        if (ROOMS[player.getCurrentRoom()].getSouth() >= 1)
                south = "S";
        if (ROOMS[player.getCurrentRoom()].getWest() >= 1)
                west = "W";
        if (ROOMS[player.getCurrentRoom()].getEast() >= 1)
                east= "E";
        if (ROOMS[player.getCurrentRoom()].getUp() >= 1)
                up = "U";
        if (ROOMS[player.getCurrentRoom()].getDown() >= 1)
                down= "D";

        // Top row
        System.out.print ("\n---");
        if (north.equals("N"))
            C.io.print(north, Color.orange);
        else
            System.out.print (north);
        System.out.print("---\n");

        // Middle row
        if (west.equals("W"))
            C.io.print(west, Color.orange);
        else
            System.out.print (west);
        System.out.print ("-");
        if (up.equals("U"))
            C.io.print(up, Color.orange);
        else
            System.out.print (up);
        System.out.print ("-");
        if (down.equals("D"))
            C.io.print(down, Color.orange);
        else
            System.out.print (down);
        System.out.print ("-");
        if (east.equals("E"))
            C.io.print(east, Color.orange);
        else
            System.out.print (east);

        // Bottom row
        System.out.print ("\n---");
        if (south.equals("S"))
            C.io.print(south, Color.orange);
        else
            System.out.print (south);
        System.out.print ("---\n");
    }

    //--------------------------------------------------------------------------
    // For walking north and displaying the current room
    // Also sets the current room
    //--------------------------------------------------------------------------
    private void goNorth() {
        if (ROOMS[player.getCurrentRoom()].getNorth() >= 1) {
            player.setCurrentRoom(ROOMS[player.getCurrentRoom()].getNorth());
            displayRoom();
        }
        else {
            System.out.println ("You can't go north.");
            navigate();
        }
    }

    //--------------------------------------------------------------------------
    // For walking south and displaying the current room
    // Also sets the current room
    //--------------------------------------------------------------------------
    private void goSouth() {
        if (ROOMS[player.getCurrentRoom()].getSouth() >= 1) {
            player.setCurrentRoom(ROOMS[player.getCurrentRoom()].getSouth());
            displayRoom();
        }
        else {
            System.out.println ("You can't go south.");
            navigate();
        }
    }

    //--------------------------------------------------------------------------
    // For walking west and displaying the current room
    // Also sets the current room
    //--------------------------------------------------------------------------
    private void goWest() {
        if (ROOMS[player.getCurrentRoom()].getWest() >= 1) {
            player.setCurrentRoom(ROOMS[player.getCurrentRoom()].getWest());
            displayRoom();
        }
        else {
            System.out.println ("You can't go west.");
            navigate();
        }
    }

    //--------------------------------------------------------------------------
    // For walking east and displaying the current room
    // Also sets the current room
    //--------------------------------------------------------------------------
    private void goEast() {
        if (ROOMS[player.getCurrentRoom()].getEast() >= 1) {
            player.setCurrentRoom(ROOMS[player.getCurrentRoom()].getEast());
            displayRoom();
        }
        else {
            System.out.println ("You can't go east.");
            navigate();
        }
    }

    //--------------------------------------------------------------------------
    // For walking up and displaying the current room
    // Also sets the current room
    //--------------------------------------------------------------------------
    private void goUp() {
        if (ROOMS[player.getCurrentRoom()].getUp() >= 1) {
            player.setCurrentRoom(ROOMS[player.getCurrentRoom()].getUp());
            displayRoom();
        }
        else {
            System.out.println ("You can't go up.");
            navigate();
        }
    }

    //--------------------------------------------------------------------------
    // For walking down and displaying the current room
    // Also sets the current room
    //--------------------------------------------------------------------------
    private void goDown() {
        if (ROOMS[player.getCurrentRoom()].getDown() >= 1) {
            player.setCurrentRoom(ROOMS[player.getCurrentRoom()].getDown());
            displayRoom();
        }
        else {
            System.out.println ("You can't go down.");
            navigate();
        }
    }

    //--------------------------------------------------------------------------
    // For talking to an NPC
    //--------------------------------------------------------------------------
    private void talkTo() {
        if (ROOMS[player.getCurrentRoom()].getNPC() >= 1) {
            conversation();
        }
        else
            System.out.println ("There's nobody to talk to.");
    }

    //--------------------------------------------------------------------------
    // Events happening when talking to the different NPCs
    // The cases represent the NPC index in the array
    // Future: Create line break for conversations too (Can use the static
    // method breakLines from Room.java)
    //--------------------------------------------------------------------------
    private void conversation() {
        switch (player.getCurrentNPC()) {	
                //******************************
                // Unkillable NPCs
                //******************************
                case 1: // a butler
                    if (player.getItem(19).getPlayerHas() == 0 && player.getBlackCatQuest() == 0)
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Have you seen a black cat around? If you find Mr. Whiskers, please bring him to me.'");
                    else if (player.getItem(19).getPlayerHas() == 1 && player.getBlackCatQuest() == 0) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Mr. Whiskers! I've been so worried! Don't you ever run away again!'\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'We cannot thank you enough. Please accept this as a token of my gratitude.'\n");

                        player.getItem(19).removePlayerHas(1);

                        player.addExperience(20);
                        player.addGold(30);
                        player.getItem(1).addPlayerHas(1);
                        System.out.println ("You received 20 experience.");
                        System.out.println ("You received 30 gold.");
                        System.out.println ("You received a health potion.");

                        player.levelUp();

                        ROOMS[1].addNPC(101);

                        player.setBlackCatQuest(1);
                    }
                    else if (player.getBlackCatQuest() == 1)
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Oh my! Please don't step on the carpet with those filthy shoes!'");				
                    break;	
                case 2: // a cheerful merchant
                    String answerTwo = "";

                    //System.out.println (npcs[player.getCurrentNPC()] + " says: 'Welcome! Please have a look at my wares.'");
                    System.out.println ("\n\tItems for sale:\n");
                    System.out.println ("\t1: a health potion\t 15 (You have: " + player.getItem(1).getPlayerHas() + ")");
                    System.out.println ("\t2: a longsword\t 120");
                    System.out.println ("\t3: a chainmail armor\t 150");
                    System.out.println ("\t4: a training manual\t 200");

                    System.out.println ("\nGold: " + player.getGold());

                    System.out.println ("\nWhat would you like to buy?\n");
                                    System.out.print("Choose: (0 for nothing) ");
                    answerTwo = C.io.nextLine();

                    switch (answerTwo)
                    {
                        case ("0"):
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Come again.'");
                            break;
                        case ("1"): // a health potion
                            if (player.getGold() >= 15) {
                                player.removeGold(15);
                                player.getItem(1).addPlayerHas(1);
                                System.out.println("You pay 15 gold and get a health potion in return.");
                                conversation();
                            }
                            else if (player.getGold() < 15)
                                System.out.println ("You don't have enough gold.");
                            break;
                        case ("2"): // a longsword
                            if (player.getItem(11).getPlayerHas() == 0 && player.getGold() >= 120) {
                                player.removeGold(120);
                                player.getItem(11).addPlayerHas(1);
                                System.out.println ("You pay 120 gold and get a longsword in return.");
                                System.out.println ("The longsword increases your damage in combat by 1 point.");
                                player.addStrength(1);

                                System.out.println ("You decide to sell your old sword to the merchant.");
                                player.addGold(30);
                                player.getItem(15).removePlayerHas(1);
                                System.out.println ("You received 30 gold.");
                            }
                            else if (player.getItem(11).getPlayerHas() == 1)
                                    System.out.println ("Why would you want another one?");
                            else if (player.getGold() < 150)
                                System.out.println ("You don't have enough gold.");
                            break;
                        case ("3"): // a chainmail armor
                            if (player.getItem(16).getPlayerHas() == 0 && player.getGold() >= 150) {
                                player.removeGold(150);
                                player.getItem(16).addPlayerHas(1);
                                System.out.println ("You pay 150 gold and get a chainmail armor in return.");
                                System.out.println ("The chainmail increases the amount of damage you can withstand by 10 points.");
                                player.addMaxHealth(10);

                                System.out.println ("You decide to sell your old armor to the merchant.");
                                player.addGold(20);
                                player.getItem(17).removePlayerHas(1);
                                System.out.println ("You received 20 gold.");
                            }
                            else if (player.getGold() < 150)
                                System.out.println ("You don't have enough gold.");
                            break;
                        case ("4"): // a training manual
                            if (player.getGold() >= 200) {
                                player.removeGold(200);
                                player.getItem(14).addPlayerHas(1);
                                System.out.println ("You pay 200 gold and get a training manual in return.");
                                System.out.println ("The training manual increases your experience by some points when used.");
                            }
                            else if (player.getGold() < 200)
                                System.out.println ("You don't have enough gold.");
                            break;
                        default:
                            System.out.println ("Invalid input.");
                            break;
                    }
                        break;
                case 3: // a pretty lady
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Don't you think I look lovely in this dress?'");
                    break;
                case 4: // a wandering salesman
                    String answerFour = "";
                    // if the player doesn't have enough gold, and also doesn't have the gemstone or the Shadowdrinker
                    if (player.getGold() < 500 && player.getItem(10).getPlayerHas() == 0 && player.getItem(7).getPlayerHas() == 0)
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I have a special item for sale, but it looks like you can't afford it.'");
                    else if (player.getGold() > 500 && player.getItem(10).getPlayerHas() == 0 && player.getItem(7).getPlayerHas() == 0) { // if the player has enough gold, and also doesn't have the gemstone or the shadowdrinker
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I stumbled upon this gemstone the other day. It's yours for only 500 gold pieces."
                                        + " How about it? (yes/no)'");
                        answerFour = C.io.nextLine();

                        switch (answerFour) {
                            case ("yes"):
                                player.removeGold(500);
                                System.out.println ("You give 500 gold pieces to the salesman.");
                                System.out.println (NPCS[player.getCurrentNPC()] + " hands you " + player.getItem(10) + ".");
                                player.getItem(10).addPlayerHas(1);
                                break;
                            case ("no"):
                                System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Don't think you can barter with me!'");
                                break;
                            default:
                                System.out.println ("Invalid input.");
                                break;
                        }
                    }
                    else if (player.getItem(10).getPlayerHas() == 1 || player.getItem(7).getPlayerHas() == 1) // if the player has the gemstone or the Shadowdrinker
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I'm sorry to say everything is sold out for the moment.'");    		
                    break;
                case 5: // a hardworking man
                    String answerSeven = "";
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Why don't you give me hand instead of just standing there?' (yes/no)");
                    answerSeven = C.io.nextLine();

                    switch (answerSeven) {
                        case ("yes"):
                            System.out.println ("You help the man with his work. After all, you need to do something about that beer belly you acquired as of lately\n");
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Well thank you! Here is a few gold coins!'");
                            System.out.println ("\nYou recieved 20 gold.\n");
                            System.out.println ("\nYou recieved 10 experience.\n");
                            player.addGold(20);
                            player.addExperience(10);
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Thanks to you I'll be home for dinner. See you next time!'\n");
                            System.out.println (NPCS[player.getCurrentNPC()] + " leaves with a smile on his face. Perhaps he just escaped a scolding from his wife?");
                            ROOMS[player.getCurrentRoom()].removeNPC();
                            ROOMS[player.getCurrentRoom()].permanentRemoveNPC();
                            break;
                        case ("no"):
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Hmph.. You lazy cityfolk.'");
                            break;
                        default:
                            System.out.println ("Invalid input.");
                            break;
                    }
                        break;
                case 6: // a villager
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'These days nobody dares to leave the village.'");
                    break;
                case 7: // Grunbald the magician
                    if (player.getMushroomQuest() < 8 && player.getItem(20).getPlayerHas() == 0)
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'If you were to find any violet mushrooms, please take them to me.'");
                    else if (player.getMushroomQuest() < 8 && player.getItem(20).getPlayerHas() >= 1) {
                        System.out.println ("You give a violet mushroom to Grunbald.\n");

                        player.getItem(20).removePlayerHas(1);
                        player.addMushroomQuest(1);

                        player.addExperience(25);
                        player.addGold(40);
                        System.out.println ("You received 25 experience.");
                        System.out.println ("You received 40 gold.\n");

                        player.levelUp();

                        if (player.getMushroomQuest() <= 7)
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Thank you! Now I only need " + (8 - player.getMushroomQuest()) + " more mushrooms!'");
                        else if (player.getMushroomQuest() == 8) {
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Fantastic work! That's the last one! Now I can finally finish my project.'");

                            player.addExperience(200);
                            System.out.println ("\nYou received 200 experience.\n");

                            player.levelUp();
                        }
                    }
                    else if (player.getMushroomQuest() == 8) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I don't need any more mushrooms.'");
                    }
                    break;
                case 8: // Skeld the blacksmith
                    if (player.getItem(8).getPlayerHas() <= 0 || player.getItem(9).getPlayerHas() <= 0 || player.getItem(10).getPlayerHas() <= 0)
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I'm so tired of repairing armor and making "
                                            + "cheap swords for this stupid war. I wish I could go back to the time when I was the King's royal blacksmith.'");
                    if (player.getItem(8).getPlayerHas() >= 1 && player.getItem(9).getPlayerHas() >= 1 && player.getItem(10).getPlayerHas() >= 1) {
                        System.out.println ("You show the blade, hilt, and gemstone to Skeld.");
                        System.out.println ("\nSkeld turns silent as he runs his fingers along the cold blade.\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " whispers: 'I have never seen the like of this blade before. It's stunningly beautiful and although"
                                        + " I can tell it's an ancient relic, it's still sharp. These items hold terrific power. Where did you get your hands on them?'");
                        System.out.println ("\nYou explain your situation to Skeld and you can see a small spark light up in his icy blue eyes.\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " says with resolve in his voice: 'It would be an honor to complete this weapon for you."
                                        + " Come back tomorrow when dawn breaks.'");

                        player.getItem(8).removePlayerHas(1);
                        player.getItem(9).removePlayerHas(1);
                        player.getItem(10).removePlayerHas(1);

                        System.out.println ("\nYou head over to the local inn and get a good night's sleep. (Press any key to continue)\n");
                        C.io.nextLine();

                        System.out.println ("You return to the blacksmith at dawn just to find him sitting on a chair next to the forge, snoring loudly."
                                        + " 'He must have worked hard all night' you think for yourself.");
                        System.out.println ("\nYou poke him lightly in the rib and he wakes up in a flash.");
                        System.out.println ("\nHe smiles at you and stands up. He heads over to the anvil and brings an item wrapped in cloth.");
                        System.out.println ("\nHe removes the cloth, revealing a sword in its sheath. The sheath looks like it's made in the finest leather"
                                        + " and it has beautiful etchings on it, no doubt the signature of the blacksmith himself.");

                        System.out.println ("\n'I present to you..' he says as he moves his hand to the hilt of the sword and pulls it out with a swift"
                                        + " move, stretching it straight towards the sky with his strong arm. 'Shadowdrinker!'.");

                        System.out.println ("\nYou are stunned by the beauty of the sword. It's as if it's practically singing of joy as it"
                                        + " can finally bathe in the sunlight for the first time in thousands of years. It is truly a magnificent piece of work.");

                        player.getItem(7).addPlayerHas(1);

                        System.out.println ("\nYou received the Shadowdrinker.");
                    }

                    break;
                case 9: // a town guard
                    int randomConv9 = rand.nextInt(3) +1; // Randomizes the conversation

                    switch (randomConv9) {
                        case 1:
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Keep out of trouble!'");
                            break;
                        case 2:
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Have you seen that weird magician in town? I bet he's up to something fishy..'");
                            break;
                        case 3:
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I once used the fortune machine. It tells the truth I tell you!'");
                            break;
                        default:
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Keep out of trouble!'");
                            break;
                    }
                    break;
                case 10: // a philosopher
                    //System.out.println (npcs[player.getCurrentNPC()] + " says: 'Not all those who wander are lost.'"); // Quote from JRR Tolkien

                    String answerTen = "";
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Would you like to play a game?' (yes/no)");
                    answerTen = C.io.nextLine();

                    switch (answerTen) {
                        case ("yes"):
                            mathQuiz();
                            break;
                        case ("no"):
                            System.out.println (NPCS[player.getCurrentNPC()] + " frowns and returns to what he was doing.");
                            break;
                        default:
                            System.out.println ("Invalid input.");
                            break;
                    }
                    break;
                case 11: // an elderly villager
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I wish myself back to brighter days..'");
                    break;
                case 12: // a frightened town guard
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I saw.. things..'");
                    break;
                case 13: // Glenn the bard
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Play a tune you say? Such rudeness! I'm in the process of creating a masterpiece for my beloved Asta.'");
                    break;
                case 14: // Lianne
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Oh how will I ever get that handsome Glenn to look my way.."
                                    + "\nI wish my father wouldn't watch my every move.'");
                    break;
                case 15: // Thorulf
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'There is only one thing on my mind right now, and that's keeping that"
                                    + "\nslimy bard away from my daughter!'");
                    break;
                case 16: // Asta
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Did you meet the bard yet? Isn't he amazing? Much more of a man than my good for nothing husband.'");
                    break;
                case 17: // a hermit
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Welcome " + player.getName() + ". How do I know your name you ask? There are many things"
                                    + " you do not know about this world.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I know why you have come here, even if you might not know it yourself yet.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'A terrible shadow has befallen our lands, and you are the one who will find the light"
                                    + " and cast the shadow to the abyss where it belongs. You are the one the prophecies have been telling me about.'\n");

                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'The person who brought the war to our lands is a powerful warlock who goes by the"
                                    + " name of Zeramus.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You must destroy this man to bring peace to the world once again. Needless to say,"
                                    + " it will not be an easy task and you will need help.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Other than the brave souls I have foreseen giving you assistance on your quest, there is one"
                                    + " thing that stands above them all. None other than the legendary sword 'Shadowdrinker'.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'However. One thousand years ago to the day, the Shadowdrinker was destroyed and the pieces"
                                    + " shattered to different parts of our land. You must gather these pieces and assemble the sword to be able to put and end to"
                                    + " Zeramus' terror.'\n");

                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'The first piece, the hilt, is held by the ruler of the goblins to the northwest of Halin"
                                    + " village.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'The second piece, the blade, was last known to be held by a long dead lord named Otri,"
                                    + " whose domains used to be where the marsh is today.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'The last piece, the radiant gemstone of Ashaar, has unfortunately been completely"
                                    + " lost in the passing of time. I am afraid you are on your own with this one.'\n");

                    System.out.println (NPCS[player.getCurrentNPC()] + " says: '" + player.getName() +  "! Seek out the pieces of the legendary sword, find a blacksmith"
                                    + " worthy of recreating it, and destroy Zeramus once and for all!'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Now go, you must make haste!'");
                    break;
                case 18: // a member of the Zulah tribe
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Don't enter the marsh! There are.. things.. out there.'");
                    break;
                case 19: // the shaman of the Zulah tribe (Heals the player to maximum health)
                    if (player.getHealth() == player.getMaxHealth())
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Visit me if you are in need of healing.'");
                    else if (player.getHealth() < player.getMaxHealth()) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " starts to mumble some words...'");

                        heal ( (player.getMaxHealth()) - (player.getHealth()) );
                        System.out.println ("\nYou have been healed.");
                    }
                    break;
                case 20: // a hunter of the Zulah tribe (Will always give out a health potion if the player doesn't have one)
                    if (player.getItem(1).getPlayerHas() < 1) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Here, take this. You will need it.'");
                        player.getItem(1).addPlayerHas(1); // Adds a health potion
                        System.out.println ("\nYou received a health potion.");
                    }
                    else
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Be careful out there. You can never be sure about what is lurking in the mists.'");
                    break;
                case 21: // a cackling mad man
                    System.out.println (NPCS[player.getCurrentNPC()] + " seems to be completely out of his mind. He keeps babbling about some treasure in the graveyard.");
                    break;
                case 22: // the guardian of the tomb (Used to open the door into the tomb)
                    if (ROOMS[player.getCurrentRoom()].getWest() == 0) {
                        String answerTwentyOne = "";

                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Those who wish to enter the tomb must speak out the password.'");
                        System.out.println ("\nWhat do you wish to say? (Type now)");
                        answerTwentyOne = C.io.nextLine();

                        switch (answerTwentyOne) {
                            case ("mother"):
                            case ("Mother"):
                                System.out.println (NPCS[player.getCurrentNPC()] + " says: 'That is correct. You may now enter the tomb.'");
                                System.out.println ("\nYou can hear a loud noise as the door starts moving aside, allowing you to go inside the tomb.");
                                ROOMS[player.getCurrentRoom()].setWest(298);
                                break;
                            default:
                                System.out.println (NPCS[player.getCurrentNPC()] + " says: 'That is the wrong password. Begone!'");
                                break;
                        }
                    }
                    else if (ROOMS[player.getCurrentRoom()].getWest() == 298)
                        System.out.println (NPCS[player.getCurrentNPC()] + " is silent.");
                    break;
                case 23: // Ezme the carpet trader
                    String answerTwentyThree = "";

                    // If the player doesn't have the carpet or the camel
                    if (player.getItem(12).getPlayerHas() == 0 && player.getItem(13).getPlayerHas() == 0) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You are looking to cross the desert you say? There is no way you will make the trip without one of my very special flying carpets!'\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I will also make a very special price only for you my friend! Only 5000 gold pieces! How about it?' (yes/no)");
                        answerTwentyThree = C.io.nextLine();

                        switch (answerTwentyThree) {
                            case ("yes"):
                                    if (player.getGold() >= 5000) {
                                        System.out.println (NPCS[player.getCurrentNPC()] + "'s eyes widen as you show him the gold pieces.");
                                        System.out.println ("You close the sack of gold after giving him a glimpse. Like you'd ever pay that ridiculous price for an old rug!");
                                        System.out.println (NPCS[player.getCurrentNPC()] + " starts crying.");
                                    }
                                    else if (player.getGold() <= 5000) {
                                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Don't try to fool me!'");
                                    }
                                    break;
                            case ("no"):
                                System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Well it was worth a try.. I suppose if you really want the carpet you could just bring me the "
                                                + "final piece for my outstanding limited edition camel figurine collection. Find number 26 and I will give you the carpet.'");
                        }	
                    }

                    // if the player doesn't have the carpet but does have the camel
                    if (player.getItem(12).getPlayerHas() == 0 && player.getItem(13).getPlayerHas() == 1) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'If you want to cross the desert you will need one of my flying carpets. It's 5000 go.. Wait! "
                                        + "What's THAT sticking out of your backpack?!'\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " looks almost obsessed as his hand stretches out for the silly camel figurine in your backpack.\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Give it to me!'\n");
                        System.out.println ("You look at him with a puzzled expression and hand the camel over.\n");

                        player.getItem(13).removePlayerHas(1);

                        System.out.println (NPCS[player.getCurrentNPC()] + "'s eyes fill up with tears of joy as he starts dancing around the room with the camel held high in his hands.\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " says excitedly: 'The carpets are over there! Grab any one of them! Here, have some gold too!'\n");

                        player.addGold(50);
                        System.out.println ("You received 50 gold.");

                        player.getItem(12).addPlayerHas(1);
                        System.out.println ("You take a flying carpet.\n");

                        System.out.println ("Ezme is still dancing around. You'd better leave him to it before he changes his mind.");	
                    }
                    else if (player.getItem(12).getPlayerHas() == 1)
                        System.out.println ("Ezme is still dancing around. You'd better leave him to it before he changes his mind.");
                    break;
                case 24: // Josephine
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I'm so tired of being holed up in this place. I wish the war would end soon. Why are they fighting anyway?'");
                    break;
                case 25: // Rupert
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I don't believe in all the nonsense people speak about. A black magician in the desert? "
                                    + "Monsters? Hah! Those are all bedtime stories for children.'");
                    break;
                case 26: // an old man smoking his pipe
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I am afraid by the time the young ones realize what is about to happen it will be too late."
                                    + " There are dark clouds coming from the desert.'");
                    break;
                case 27: // a wounded soldier tending to his comrade
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'All I've got left in life is this young boy, and we just met yesterday. A few hours later our group were ambushed by raiders."
                                    + " Yes. We are the only two survivors. I don't know what to do but keep trying to heal his wounds.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Everything feels hopeless.'");
                    break;
                case 28: // Lieutenant Harken
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You there soldier! Why aren't you on the front line with the others?'");
                    System.out.println("\nYou explain your situation to him.\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Well if it's of any help, my scouts found out that there is some lunatic man "
                                    + "claiming to be a wizard holed up in the castle to the south. Wouldn't want to go there myself though.'\n");
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'If you follow the road south here you will get to the castle's hedge maze, it should be your "
                                    + "best shot at getting there undetected. Stay alert though. I heard.. Stories.. About that place.'");
                    break;
                case 29: // a desert trader
                    String answerTwentyNine = "";

                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Please have a look at my wares.'");

                    System.out.println ("\n\tItems for sale:\n");
                    System.out.println ("\t1: a health potion\t\t 15 (You have: " + player.getItem(1).getPlayerHas() + ")");
                    System.out.println ("\t2: a greather health potion\t 30 (You have: " + player.getItem(21).getPlayerHas() + ")");

                    System.out.println ("\nGold: " + player.getGold());

                    System.out.println ("\nWhat would you like to buy?\n");
                                    System.out.print("Choose: (0 for nothing) ");
                    answerTwentyNine = C.io.nextLine();

                    switch (answerTwentyNine) {
                        case ("0"):
                            System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Come again.'");
                            break;
                        case ("1"): // a health potion
                            if (player.getGold() >= 15) {
                                player.removeGold(15);
                                player.getItem(1).addPlayerHas(1);
                                System.out.println("You pay 15 gold and get a health potion in return.");
                                conversation();
                            }
                            else if (player.getGold() < 15)
                                System.out.println ("You don't have enough gold.");
                            break;
                        case ("2"): // a greater health potion
                            if (player.getGold() >= 30) {
                                player.removeGold(30);
                                player.getItem(21).addPlayerHas(1);
                                System.out.println("You pay 30 gold and get a greater health potion in return.");
                                conversation();
                            }
                            else if (player.getGold() < 30)
                                System.out.println ("You don't have enough gold.");
                            break;
                        default:
                            System.out.println ("Invalid input.");
                            break;
                    }
                    break;
                case 30: // Harald the innkeeper			
                    if (player.getRespawnLocation() == 1) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Welcome to my humble inn! Please have a rest.'");
                        player.setHealth(player.getMaxHealth());
                        player.setRespawnLocation(166);
                        System.out.println ("\nYou rest at the inn.\n");
                        System.out.println ("You will now respawn at the inn if you die.");
                    }
                    else {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Did you meet the strange salesman up the north road yet? I wonder what he's up to..'\n");
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Please have a rest.'");
                        player.setHealth(player.getMaxHealth());
                        System.out.println ("\nYou rest at the inn.");
                    }
                    break;

                //******************************
                // Test NPCs
                //******************************
                case 100: // test npc
                    System.out.println ("case 100 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com and report it-");
                    break;

                //******************************
                // Killable NPCs
                //******************************
                case 101: // a black cat
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Meoooooow!'");
                    break;
                case 102: // a red fox
                    System.out.println (NPCS[player.getCurrentNPC()] + " growls at you");
                    break;
                case 103: // a donkey
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Eeeeee Awwww...!'");
                    break;
                case 104: // a tiny bird
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 105: // a cockroach
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 106: // an owl
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 107: // a sleeping bear
                    System.out.println (NPCS[player.getCurrentNPC()] + " grunts and falls back to sleep.");
                    break;
                case 108: // a dirty vagrant
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'What are you doing here? This is my place! Get out!'");
                    break;
                case 109: // a happy cow
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Mooooooooo!'");
                    break;
                case 110: // a sick-looking wolf
                    System.out.println (NPCS[player.getCurrentNPC()] + " stares hungrily at you.");
                    break;
                case 111: // a sobbing little girl ghost
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Stop looking at me! I just want to be left alone..'" + NPCS[player.getCurrentNPC()] + " turns around and starts sobbing again.");
                    break;
                case 112: // a small goblin
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You shouldn't be here! Guards!!!'");
                    break;
                case 113: // a goblin warrior
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I hope you enjoy the feeling of a rusty blade piercing your body!'");
                    break;
                case 114: // a lazy goblin guard
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'They told me there wouldn't be intruders when I got this job!'");
                    break;
                case 115: // a goblin cook
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Oh, the main dish has arrived! Prepare the big pot!'");
                    break;
                case 116: // The Goblin King
                    System.out.println (NPCS[player.getCurrentNPC()] + " grins at you, baring his gruesome teeth. 'You are DEAD!'");
                    break;
                case 117: // an almost lifeless prisoner
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Please.. End me..'");
                    break;
                case 118: // a street thug
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Empty your pockets!'");
                    break;
                case 119: // a big rat
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 120: // a road bandit
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Hand it all over before I slit your throat!'");
                    break;
                case 121: // a mountain lion
                    System.out.println (NPCS[player.getCurrentNPC()] + " growls at you.");
                    break;
                case 122: // a mountain goat
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 123: // a marsh horror
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 124: // a poisonous green frog
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 125: // a grave robber
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'I found this place first! You'd better leave, or an \"accident\" might just happen.'");
                    break;
                case 126: // Otri the Undying
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You are a brave one. Now, join me in the afterlife!'");
                    break;
                case 127: // a chainmail armored skeleton
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 128: // a soulless corpse
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 129: // a marsh serpent
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 130: // a terrified grave robber
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Get me out of here! This isn't how it was supposed to be..'");
                    break;
                case 131: // a rattlesnake
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 132: // a scorption
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 133: // the minotaur of the maze
                    System.out.println (NPCS[player.getCurrentNPC()] + " stares hatefully at you with his red eyes. There is smoke coming out of his nostrils. He lifts his huge axe..");
                    break;
                case 134: // an elite guard trapped in the maze
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'If you don't show me the way out I will kill you right now!'");
                    break;
                case 135: // a horned satyr
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
                case 136: // a huge vampire bat
                    System.out.println (NPCS[player.getCurrentNPC()] + " swoops down and aims for your neck with its fangs.");
                    break;
                case 137: // an elite guard wearing a black mask
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You little worm! A lifetime of pain awaits you in our torture chambers!'");
                    break;
                case 138: // Zeramus
                    if (player.getItem(7).getPlayerHas() == 0) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You little ant! I will crush you beneath my foot!'\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " starts doing elaborate movements with his arms and hands as he speaks the words of an ancient toungue.\n");

                        System.out.println ("White hot fire starts to erupt from his fingers, and he binds it into a large ball of energy before he casts it towards you.\n");

                        System.out.println ("You are completely engulfed in the fire, and die slowly as you roll around the floor, trying to make the fire go out.\n");

                        gameOver();
                    }
                    else if (player.getItem(7).getPlayerHas() == 1) {
                        System.out.println (NPCS[player.getCurrentNPC()] + " turns around slowly, facing you. His face is a pale white with deep wrinkles. If it wasn't for his eyes, one would "
                                        + "take him for just about any old man. Where a normal person would have white in their eyes, his is a pure black, not much different than the "
                                        + "black polished floor. As you feel his gaze upon you, you feel like you are drowning in a bottomless pool of pure hatred.\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You have a lot of nerve coming here. Though, I was alerted by your presence hours ago, I decided "
                                        + "to let you pass. You see, I'm a very curious man.'\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " throws back his head with a powerful laughter, seemingly making the room shake.\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'You see this thing here? It's called the Pyrestone. And once I learn how it works I will not need "
                                        + "my army anymore. It will allow me to shape the lands by my own will, to awaken the underground volcanos, and to unleash true "
                                        + "hell on earth!'\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " once again focus his eyes of bottomless darkness upon you.\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Now, it is time for you to leave this world.'\n");

                        System.out.println ("You draw the Shadowdrinker, readying yourself for battle.\n");

                        System.out.println ("Press enter to continue..");
                        C.io.nextLine();

                        System.out.println (NPCS[player.getCurrentNPC()] + " starts doing elaborate movements with his arms and hands as he speaks the words of an ancient toungue.\n");

                        System.out.println ("White hot fire starts to erupt from his fingers, and he binds it into a large ball of energy before he casts it towards you.\n");

                        System.out.println ("You suddenly feel the Shadowdrinker turning icy cold in your hands as you brace yourself for the impact.\n");

                        System.out.println ("The white ball of energy suddenly bounces away, as if it hit an invisible wall. It explodes with a roar a few metres away.\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " says: 'What is this?! No, no..! It was supposed to be destroyed!'\n");

                        System.out.println (NPCS[player.getCurrentNPC()] + " starts to cower as you charge towards him, decapitating his head with one swift move.\n");

                        System.out.println ("Press enter to continue..");
                        C.io.nextLine();

                        System.out.println ("As you see his head roll down the pitch black floor, you also notice that the darkness in his eyes has disappeared. They look completely normal now.\n");

                        System.out.println ("You lean down over his body and pick up a pendant that had been hanging around his neck. It consists of a thick silver chain "
                                        + "and one of the red crystals. You can feel a sensation running through your body as you touch it.\n");

                        System.out.println ("After removing the pendant from its owner, you start hearing the voices of thousands of people outside the castle.\n");

                        System.out.println ("You head over to the west window and look outside. Everyone are fleeing towards the castle! The enemy is giving up! Truly, "
                                        + "it would seem like Zeramus' power is what made them obey.\n");

                        System.out.println ("Press enter to continue..");
                        C.io.nextLine();

                        player.getItem(18).addPlayerHas(1);
                        player.addGold(500);
                        player.addExperience(300);
                        System.out.println ("You received a red crystal pendant.");
                        System.out.println ("You received 300 experience points.");
                        System.out.println ("You received 500 gold.\n");
                        player.addMaxHealth(10); // Bonus for wearing a red crystal pendant
                        player.addStrength(1); // Bonus for wearing a red crystal pendant

                        player.levelUp();

                        System.out.println ("Congratulations! You have saved the world and finished the game!\n");

                        System.out.println ("Thank you for playing, and if you liked it please fill out the review form included with the game and e-mail me at "
                                        + "robin.neko@gmail.com.\n");

                        System.out.println ("You may now continue exploring the game world if you wish.");

                        ROOMS[player.getCurrentRoom()].removeNPC();
                        ROOMS[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
                    }
                    break;
                case 142: // a crazy mountaineer
                    System.out.println (NPCS[player.getCurrentNPC()] + " says: 'Watch for icy spots!'");
                    break;

                //******************************
                // Default
                //******************************
                default:
                    System.out.println ("You can't talk to " + NPCS[player.getCurrentNPC()]);
                    break;
        }
    }

    //--------------------------------------------------------------------------
    // Method for using an object (NPC)
    //--------------------------------------------------------------------------
    private void useObject() {	
        switch (player.getCurrentNPC())
        {
            //--------------------------------------------------------------------------
            // #201 a marble fountain
            //--------------------------------------------------------------------------
            case (201):
                System.out.println ("You drink from " + NPCS[player.getCurrentNPC()]);
                heal(5);
                break;
            //--------------------------------------------------------------------------
            // #202 a fortune telling machine
            //--------------------------------------------------------------------------
            case (202):
                String answer = "no";

                System.out.println ("Do you wish to use the machine for 10 gold? (yes/no)");
                answer = C.io.nextLine();

                switch (answer) {
                    case ("yes"):
                        if (player.getGold() >= 10) {
                            System.out.println ("You pay 10 gold.");
                            player.removeGold(10);

                            int chance = 0;

                            chance = rand.nextInt(8) +1;

                            switch (chance) {
                                case 1:
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'A great misfortune will fall upon you. Watch your step in the marsh.'");
                                    break;
                                case 2:
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'Don't let your eyes fool you. You might have met someone with more power than you think.'");
                                    break;
                                case 3:
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'There is someone in this village who will help you in the very last part of your quest.'");
                                    break;
                                case 4: 
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'Even the most unthinkable person can hold what you seek.'");
                                    break;
                                case 5: 
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'You will need about 500 gold pieces to make your dreams come true.'");
                                    break;
                                case 6: 
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'The only source of knowledge is experience.'");
                                    player.addExperience(30);
                                    System.out.println ("\nYou received 30 experience points.\n");
                                    break;
                                case 7: 
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'You should seek out someone living in the mountain.'");
                                    break;
                                case 8: 
                                    System.out.println ("The machine makes some strange sounds and prints out a small piece of parchment:"
                                                    + "\n'Your future holds a visit in a very dark place, where the dead are still walking.'");
                                    break;
                            }
                        }
                        else if (player.getGold() < 10)
                            System.out.println ("You don't have enough gold.");
                        break;
                    case ("no"):
                        System.out.println ("Maybe next time.");
                        break;
                    default:
                        System.out.println ("Invalid input.");
                        break;
                }
                break;
            //--------------------------------------------------------------------------
            // #203 a chair
            //--------------------------------------------------------------------------
            case (203):
                System.out.println ("You sit on the chair. How relaxing.");
                break;
            //--------------------------------------------------------------------------
            // #205 a rotting corpse
            //--------------------------------------------------------------------------
            case (205):
                System.out.println ("You check if there's anything of value on the rotting corpse. That's really disgusting...");
                System.out.println ("You find a health potion!");
                player.getItem(1).addPlayerHas(1); // add a health potion (#1)
                ROOMS[player.getCurrentRoom()].removeNPC();
                ROOMS[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
                break;
            //--------------------------------------------------------------------------
            // #206 a wooden elevator (Down)
            //--------------------------------------------------------------------------
            case (206):
                System.out.println ("You use the elevator. \"Whirr whirr whirr!\" It starts go down.\n.\n.\n.\n."
                                + "\nThe elevator is shaking violently.\n.\n.\n.\n."
                                + "\nThe elevator is swinging from left to right.\n.\n.\n.\n."
                                + "\nIt sounds like the ropes to the elevator could snap any moment\n.\n.\n.\n.");
                System.out.println ("You practically crash land and feel a bit shaky after the bumpy ride.\n");
                player.setCurrentRoom(232); // Moves the player to room #232
                displayRoom();
                break;
            //--------------------------------------------------------------------------
            //  #207 a wooden elevator (Up)
            //--------------------------------------------------------------------------
            case(207):
                System.out.println ("You use the elevator. \"Whirr whirr whirr!\" It starts go up.\n.\n.\n.\n."
                                + "\nThe elevator is shaking violently.\n.\n.\n.\n."
                                + "\nThe elevator is swinging from left to right.\n.\n.\n.\n."
                                + "\nIt sounds like the ropes to the elevator could snap any moment.\n.\n.\n.\n.");
                System.out.println ("You finally get to the summit, not a second too late. You feel like throwing up.\n");
                player.setCurrentRoom(231); // Moves the player to room #231
                displayRoom();
                break;
            //--------------------------------------------------------------------------
            // #208 the frozen corpse of an adventurer
            //--------------------------------------------------------------------------
            case (208):
                System.out.println ("You check the pockets of the frozen corpse. Don't you have any shame?");
                System.out.println ("\nYou find a small note\n");
                System.out.println ("As you grab the note, you can hear the wind howling and your bones suddenly start to ache"
                                + " from a sudden decrease in temperature. The strong wind blows the corpse to dust.");
                player.getItem(6).addPlayerHas(1); // adds a small note (#6)
                ROOMS[player.getCurrentRoom()].removeNPC();
                ROOMS[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
                break;
            //--------------------------------------------------------------------------
            // #209 a bonfire
            //--------------------------------------------------------------------------
            case (209):
                System.out.println ("You are mezmerised as you stare deep into the bonfire.");
                break;
            //--------------------------------------------------------------------------
            // #210 a wooden chest covered with grime
            //--------------------------------------------------------------------------
            case (210):
                System.out.println ("You pry open the lid of the chest and discover a big pile of gold!");
                System.out.println ("\nYou received 100 gold.\n");
                player.addGold(100);
                ROOMS[player.getCurrentRoom()].removeNPC();
                ROOMS[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
                break;
            //--------------------------------------------------------------------------
            // #211 an ancient chest
            //--------------------------------------------------------------------------
            case (211):
                if (player.getItem(9).getPlayerHas() >= 1) {
                    System.out.println ("As you open the lid, you see a small camel figurine carved in obsidian. You can see the numbers"
                                    + " 26/30 etched under its right foot.");
                    System.out.println ("\nYou received an obsidian camel.\n");
                    player.getItem(13).addPlayerHas(1);
                    ROOMS[player.getCurrentRoom()].removeNPC();
                    ROOMS[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
                }
                else if (player.getItem(9).getPlayerHas() == 0)
                    System.out.println ("There seems to be some strange energy keeping this chest sealed.");
                break;
            //--------------------------------------------------------------------------
            // #212 a wooden sign
            //--------------------------------------------------------------------------
            case (212):
                System.out.println ("You look upon the sign and see the text 'Turn back!' together with a skull painted in black.");
                break;
            //--------------------------------------------------------------------------
            // #213 a black cat
            //--------------------------------------------------------------------------
            case (213):
                System.out.println ("\nThe cat meows at you softly. It freely jumps right into your bag. What a strange cat.\n");
                ROOMS[player.getCurrentRoom()].removeNPC();
                ROOMS[player.getCurrentRoom()].permanentRemoveNPC();
                player.getItem(19).addPlayerHas(1);
                break;
            //--------------------------------------------------------------------------
            // #214 a violet mushroom
            //--------------------------------------------------------------------------
            case (214):
                System.out.println ("\nYou pick a violet mushroom.\n");
                ROOMS[player.getCurrentRoom()].removeNPC();
                ROOMS[player.getCurrentRoom()].permanentRemoveNPC();
                player.getItem(20).addPlayerHas(1);
                break;	
            //--------------------------------------------------------------------------
            // Default action
            //--------------------------------------------------------------------------
            default:
                System.out.println ("You can't do that.");
                break;
        }
    }

    //--------------------------------------------------------------------------
    // Method for using an item
    //--------------------------------------------------------------------------
    private void useItem()
    {
        String usedItem = "";

        usedItem = userInput.substring(3); // Removes the "use " part of the input and turns it into the usedItem String variable

        switch (usedItem) {
            //******************************
            // #1 a health potion (Heals 20 points)
            //******************************
            case (" potion"):
                if (player.getItem(1).getPlayerHas() >= 1) {
                    if (player.getHealth() >= player.getMaxHealth())
                        System.out.println ("You are already at max health!");
                    else {
                        System.out.println ("You used " + player.getItem(1));
                        player.getItem(1).removePlayerHas(1); // Removes the item from the inventory
                        heal(20);
                    }
                }
                else if (player.getItem(1).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #2 a newbie guide book
            //******************************
            case (" guide"):
                if (player.getItem(2).getPlayerHas() >= 1) {
                    System.out.println ("You start reading " + player.getItem(2));
                    System.out.println ("\nSurviving your first adventure\n");
                    System.out.println ("#1: Always use 'cons' or 'consider' before starting a fight to assess the ability of your opponent.");
                    System.out.println ("#2: Always heal up before a fight. Fountains are one way to heal yourself.");
                    System.out.println ("#3: Talk to everyone you meet. You never know what kind of useful information they might give you.");
                    System.out.println ("#4: If you get stuck, try checking your previously explored areas for clues.");
                    System.out.println ("#5: It is possible to 'interact' with or 'in' objects inside a room. Simply type it as it is.");
                    System.out.println ("#6: If you die you will lose some experience and gold.");
                    System.out.println ("#7: Remember to 'save' often. Always use 'quit' when you are finished playing or your progress will not be saved.");
                }
                else if (player.getItem(2).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #3 a map of the forest
            //******************************
            case (" forest map"):
                if (player.getItem(3).getPlayerHas() >= 1) {
                    System.out.println ("You look at " + player.getItem(3));
                }
                else if (player.getItem(3).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;	
            //******************************
            // #4 a strength potion
            //******************************
            case (" strength potion"):
                if (player.getItem(4).getPlayerHas() >= 1) {
                    System.out.println ("You used " + player.getItem(4));
                    player.getItem(4).removePlayerHas(1); // Removes the item from the inventory
                }
                else if (player.getItem(4).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #5 a teleporter
            // Note: This is an admin item
            //******************************
            case (" teleporter"):
                if (player.getItem(5).getPlayerHas() >= 1) {
                    System.out.println ("Please enter the room you wish to teleport to: "); // Only use an integer value or the game will crash
                    player.setCurrentRoom(C.io.nextInt());
                    System.out.println ("Whish whosh! The teleporter does its magic and you appear in a different place!\n");
                    displayRoom();
                }
                else if (player.getItem(5).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #6 a small note
            //******************************
            case (" note"):
                if (player.getItem(6).getPlayerHas() >= 1) {
                    System.out.println ("You can just make out one word on the note:\n\n'Mother'\n");
                }
                else if (player.getItem(6).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #7 the Shadowdrinker
            //******************************
            case (" shadowdrinker"):
                if (player.getItem(7).getPlayerHas() == 1) {
                    System.out.println ("You are already wielding it.");
                }
                else if (player.getItem(7).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #8 a golden hilt
            //******************************
            case (" hilt"):
                if (player.getItem(8).getPlayerHas() == 1) {
                    System.out.println ("It's useless in its current state.");
                }
                else if (player.getItem(8).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #9 a rune-etched blade
            //******************************
            case (" blade"):
                if (player.getItem(9).getPlayerHas() == 1) {
                    System.out.println ("It's useless in its current state.");
                }
                else if (player.getItem(9).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #10 a beautifully cut red gemstone
            //******************************
            case (" gemstone"):
                if (player.getItem(10).getPlayerHas() == 1) {
                    System.out.println ("It's useless in its current state.");
                }
                else if (player.getItem(10).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #11 a longsword
            //******************************
            case (" longsword"):
                if (player.getItem(11).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() <= 0) {
                    System.out.println ("You are already wielding it.");
                }
                else if (player.getItem(11).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() == 1)
                    System.out.println ("Why don't you use the Shadowdrinker instead?");
                else if (player.getItem(11).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");	
                break;
            //******************************
            // #12 a flying carpet
            //******************************
            case (" carpet"):
                if (player.getItem(12).getPlayerHas() == 1) {
                    String answerCarpet = "";

                    System.out.println ("You roll out the flying carpet and step on it. It starts to hover a bit up in the air.\n");
                    System.out.println ("Where do you want to go?\n");

                    System.out.println ("1: The forest");
                    System.out.println ("2: Halin village");
                    System.out.println ("3: The Zulah tribe");
                    System.out.println ("4: The carpet trader's tent");
                    System.out.println ("5: Across the desert");

                    answerCarpet = C.io.nextLine();

                    switch (answerCarpet) {
                        case ("1"):
                            System.out.println ("The carpet starts to rise higher up in the air and suddenly takes off at high speed.");
                            System.out.println ("\nYou travel swiftly across the land, traveling faster than any horse would be able to.");
                            player.setCurrentRoom(44);
                            System.out.println ("\nYou arrive at your destination.\n");
                            displayRoom();
                            break;
                        case ("2"):
                            System.out.println ("The carpet starts to rise higher up in the air and suddenly takes off at high speed.");
                            System.out.println ("\nYou travel swiftly across the land, traveling faster than any horse would be able to.");
                            player.setCurrentRoom(151);
                            System.out.println ("\nYou arrive at your destination.\n");
                            displayRoom();
                            break;
                        case ("3"):
                            System.out.println ("The carpet starts to rise higher up in the air and suddenly takes off at high speed.");
                            System.out.println ("\nYou travel swiftly across the land, traveling faster than any horse would be able to.");
                            player.setCurrentRoom(237);
                            System.out.println ("\nYou arrive at your destination.\n");
                            displayRoom();
                            break;
                        case ("4"):
                            System.out.println ("The carpet starts to rise higher up in the air and suddenly takes off at high speed.");
                            System.out.println ("\nYou travel swiftly across the land, traveling faster than any horse would be able to.");
                            player.setCurrentRoom(316);
                            System.out.println ("\nYou arrive at your destination.\n");
                            displayRoom();
                            break;
                        case ("5"):
                            System.out.println ("The carpet starts to rise higher up in the air and suddenly takes off at high speed.");
                            System.out.println ("\nYou travel swiftly across the land, traveling faster than any horse would be able to.");
                            player.setCurrentRoom(321);
                            System.out.println ("\nYou arrive at your destination.\n");
                            displayRoom();
                            break;
                        default:
                            System.out.println ("Invalid input.");
                            break;
                    }

                }
                else if (player.getItem(12).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #13 an obsidian camel
            //******************************
            case (" camel"):
                if (player.getItem(13).getPlayerHas() == 1) {
                    System.out.println ("It's useless.");
                }
                else if (player.getItem(13).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #14 a training manual
            //******************************
            case (" manual"):
                if (player.getItem(14).getPlayerHas() >= 1) {
                    int expIncrease = 0;

                    expIncrease = rand.nextInt((200 - 50) + 1) + 50; // Randomizes a number from 257 to 271

                    System.out.println ("\nYou study a training manual intensely for a while.\n");
                    System.out.println ("Your experience increased by " + expIncrease + ".\n");
                    player.getItem(14).removePlayerHas(1);
                    player.addExperience(expIncrease);
                    player.levelUp();
                }
                else if (player.getItem(14).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #15 a shortsword
            //******************************
            case (" shortsword"):
                if (player.getItem(15).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() <= 0) {
                    System.out.println ("You are already wielding it.");
                }
                else if (player.getItem(15).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() == 1)
                    System.out.println ("Why don't you use the Shadowdrinker instead?");
                else if (player.getItem(15).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");	
                break;
            //******************************
            // #16 a chainmail armor
            //******************************
            case (" chainmail"):
                if (player.getItem(16).getPlayerHas() == 1) {
                    System.out.println ("You are already wearing it.");
                }
                else if (player.getItem(16).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #17 a leather armor
            //******************************
            case (" leather"):
                if (player.getItem(17).getPlayerHas() == 1) {
                    System.out.println ("You are already wearing it.");
                }
                else if (player.getItem(17).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #18 a red crystal pendant
            //******************************
            case (" pendant"):
                if (player.getItem(18).getPlayerHas() == 1) {
                    System.out.println ("You are already wearing it. You feel more powerful than before.");
                }
                else if (player.getItem(18).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #19 a black cat
            //******************************
            case (" cat"):
                if (player.getItem(19).getPlayerHas() == 1) {
                    System.out.println ("The cat meows at you. It looks oddly comfortable in your bag.");
                }
                else if (player.getItem(19).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // #20 a violet mushroom
            //******************************
            case (" mushroom"):
                if (player.getItem(20).getPlayerHas() >= 1) {
                    System.out.println ("Why would you want to use that?");
                }
                else if (player.getItem(20).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;	
            //******************************
            // #21 a greater health potion (Heals 45 points)
            //******************************
            case (" greater"):
                if (player.getItem(21).getPlayerHas() >= 1) {
                    if (player.getHealth() >= player.getMaxHealth())
                        System.out.println ("You are already at max health!");
                    else {
                        System.out.println ("You used " + player.getItem(21));
                        player.getItem(21).removePlayerHas(1); // Removes the item from the inventory
                        heal(45);
                    }
                }
                else if (player.getItem(21).getPlayerHas() == 0)
                    System.out.println ("You don't have that item.");		
                break;
            //******************************
            // Default case
            //******************************
            default:
                System.out.println ("You don't have that item.");
                break;
        }
    }

    //--------------------------------------------------------------------------
    // Method for attacking an NPC
    //--------------------------------------------------------------------------
    private void attack() {		
        if (ROOMS[player.getCurrentRoom()].getNPC() >= 1 && NPCS[player.getCurrentNPC()].getVulnerable() >= 1) // If an NPC exists and is vulnerable
            startCombat(); // Initiates a combat
        else if (ROOMS[player.getCurrentRoom()].getNPC() >= 1 && NPCS[player.getCurrentNPC()].getVulnerable() <= 0) // If an NPC exists but is invulnerable
            System.out.println ("You can't attack that.");
        else if (ROOMS[player.getCurrentRoom()].getNPC() <= 0) // If no NPC exists
            System.out.println ("There is nothing to attack.");
    }

    private void quitGame() {
        System.out.print ("Exit the game? (y/n)");
        userInput = C.io.nextLine(); // MUST BE C.io.nextLine(); to work in the GUI. Original is scan.nextLine();

        switch (userInput) {
            case ("y"):
                System.out.println ("Goodbye!");
                saveGame();
                System.exit(1);
                break;
            case ("n"):
                navigate();
                break;
            default:
                System.out.println ("Invalid input. Try again.");
                quitGame();
        }
    }

    private void gameOver() {
        player.setHealth(1);

        // Removing experience
        if (player.getLevel() > 1 && player.getLevel() <= 3)
            player.removeExperience(20);
        else if (player.getLevel() > 3 && player.getLevel() <= 6)
            player.removeExperience(50);
        else if (player.getLevel() > 6)
            player.removeExperience(100);

        // Removing gold
        if (player.getGold() > 0 && player.getGold() <= 100)
            player.removeGold(player.getGold() / 2);
        else if (player.getGold() > 100 && player.getGold() <= 200)
            player.removeGold(player.getGold() / 3);
        else if (player.getGold() > 200 && player.getGold() <= 300)
            player.removeGold(player.getGold() / 4);
        else if (player.getGold() > 300)
            player.removeGold(player.getGold() / 5);

        player.setCurrentRoom(player.getRespawnLocation());

        System.out.println ("\nYou have died!\n");
        System.out.println ("You lost some experience and gold.");
        System.out.println ("Press enter to continue..");
        C.io.nextLine();

        displayRoom();
    }

    //--------------------------------------------------------------------------
    // Checks how strong the monster is relative to yourself
    //--------------------------------------------------------------------------
    private void consider() {
        if (NPCS[player.getCurrentNPC()].getVulnerable() > 0)
            powerDifference();
        else
            System.out.println ("You can't consider that.");
    }

    //--------------------------------------------------------------------------
    // Power difference (Compare the monster's power to your own)
    // Could most likely be improved with better algorithms
    //--------------------------------------------------------------------------
    private void powerDifference() {
        int powerDifference = 0;

        powerDifference = ( (player.getPowerLevel()) - (NPCS[player.getCurrentNPC()].getPowerLevel()) );

        if (powerDifference < -20)
            System.out.println (NPCS[player.getCurrentNPC()] + " looks very powerful compared to you. Be careful.");
        else if (powerDifference < -10)
            System.out.println (NPCS[player.getCurrentNPC()] + " looks much stronger than you.");
        else if (powerDifference < 0)
            System.out.println (NPCS[player.getCurrentNPC()] + " looks stronger than you.");
        else if (powerDifference == 0)
            System.out.println (NPCS[player.getCurrentNPC()] + " looks about as strong as yourself.");
        else if (powerDifference >= 0 && powerDifference <= 10)
            System.out.println (NPCS[player.getCurrentNPC()] + " looks like an even match.");
        else if (powerDifference >= 10)
            System.out.println (NPCS[player.getCurrentNPC()] + " looks weaker than you.");
    }
    //--------------------------------------------------------------------------
    // Starts (And eventually ends) a combat with an NPC
    //--------------------------------------------------------------------------
    private void startCombat() {	
        if (player.getHealth() > 0 && NPCS[player.getCurrentNPC()].getHealth() > 0) {
            combatSequence();
        }
        
        if (NPCS[player.getCurrentNPC()].getHealth() <= 0) {
            System.out.println ("You killed " + NPCS[player.getCurrentNPC()].getName());
            rewards(); // Rewards for killing the monster
            ROOMS[player.getCurrentRoom()].removeNPC(); // Removes the NPC when killed
            NPCS[player.getCurrentNPC()].setHealth(NPCS[player.getCurrentNPC()].getMaxHealth()); // Resets the NPC to max health after it being killed
        }
        else if (player.getHealth() <= 0) {
            System.out.println ("You were killed by " + NPCS[player.getCurrentNPC()].getName());
            NPCS[player.getCurrentNPC()].setHealth(NPCS[player.getCurrentNPC()].getMaxHealth()); // Resets the NPC health if you die
            gameOver();
        }
    }	

    //--------------------------------------------------------------------------
    // Starts a respawn timer for the NPCs (Probably needs to be optimized for
    // saving memory). Note that this timer is currently global, running
    // continuously, and never canceled
    //--------------------------------------------------------------------------
    private void respawnTimer() {
        int seconds = 300; // The rate at which monsters respawn (in seconds)

        Timer timer = new Timer();

        TimerTask task = new java.util.TimerTask()  {
            public void run() {
                int index = 1;
                while (index < ROOMS.length) {
                    ROOMS[index].respawnNPC();
                    index++;
                }
            }
         };
         timer.scheduleAtFixedRate(task, 0, seconds*1000);
    }

    //--------------------------------------------------------------------------
    // Method for the actual combat sequence
    //--------------------------------------------------------------------------
    private void combatSequence() {
        int playerHP = player.getHealth();
        int enemyHP = NPCS[player.getCurrentNPC()].getHealth();
        int playerHit = 0;
        int enemyHit = 0;

        System.out.println ("You attacked " + NPCS[player.getCurrentNPC()]);

        while (playerHP > 0 && enemyHP > 0) {
            // Player round
            playerHit = rand.nextInt(player.getStrength() +1); // Calculates the player damage made
            enemyHP -= playerHit;
            NPCS[player.getCurrentNPC()].setHealth(enemyHP); // Sets the enemy HP after the hit
            if (playerHit == 0)
                System.out.println ("You missed " + NPCS[player.getCurrentNPC()].getName());
            else
                System.out.println ("You hit " + NPCS[player.getCurrentNPC()].getName() + " for " + playerHit + " damage" + displayEnemyHealth());

            // Enemy round
            if (enemyHP > 0) {
                enemyHit = rand.nextInt(NPCS[player.getCurrentNPC()].getStrength() +1); // Calculates the enemy damage made
                if (enemyHit == 0)
                        System.out.println (NPCS[player.getCurrentNPC()].getName() + " missed you");
                else
                        System.out.println (NPCS[player.getCurrentNPC()].getName() + " hit you for " + enemyHit + " damage");
                playerHP -= enemyHit;
                player.setHealth(playerHP); // Sets the player HP after the hit	
            }
        }
    }

    private String displayEnemyHealth() {
        int enemyHP = NPCS[player.getCurrentNPC()].getHealth();
        String display = "";

        if (enemyHP > 0)
            display = (" (" + NPCS[player.getCurrentNPC()] + " has " + enemyHP + " hp left)");

        return display;
    }

    //--------------------------------------------------------------------------
    // Adds experience and gold to the character when a monster is killed
    // Also runs the methods for item drops and level up
    //--------------------------------------------------------------------------
    private void rewards() {
        player.addExperience(NPCS[player.getCurrentNPC()].getExperience());
        player.addGold(NPCS[player.getCurrentNPC()].getGold());
        System.out.println ("You received " + NPCS[player.getCurrentNPC()].getExperience() + " experience points and " + NPCS[player.getCurrentNPC()].getGold() + " gold");

        dropItem();
        player.levelUp();
    }

    //--------------------------------------------------------------------------
    // Checks if the monster drops and item, and if so, gives the item
    // For now the NPCs can only drop an item once, since this feature is only
    // used for quest items
    //--------------------------------------------------------------------------
    private void dropItem() {
        if (NPCS[player.getCurrentNPC()].getDroppedItem() >= 1) {
            player.getItem(NPCS[player.getCurrentNPC()].getDroppedItem()).addPlayerHas(1);
            System.out.println ("You received " + player.getItem(NPCS[player.getCurrentNPC()].getDroppedItem()));
            NPCS[player.getCurrentNPC()].setDroppedItem(0); // Makes sure the NPC can only drop an item ONCE
        }
    }

    //--------------------------------------------------------------------------
    // The method for getting lost in the marsh
    // Needs to be better optimized in the future. Right now it's run every time
    // the player moves Note 171202: Wow... Haha
    //--------------------------------------------------------------------------
    private void theMarsh() {
        int randomRoom = 0; // Used for the marsh 

        for (int i=258; i<271; i++) {
            randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
            ROOMS[i].setNorth(randomRoom);
        }

        for (int i=258; i<271; i++) {
            randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
            ROOMS[i].setSouth(randomRoom);
        }

        for (int i=258; i<271; i++) {
            randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
            ROOMS[i].setWest(randomRoom);
        }

        for (int i=258; i<271; i++) {
            randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
            ROOMS[i].setEast(randomRoom);
        }
    }

    //--------------------------------------------------------------------------
    // Heals the player for the value specified
    //--------------------------------------------------------------------------
    private void heal (int healAmount) {
        int healing = 0; 
        int counter = 0;

        healing = healAmount;

        while (counter < healing) {
            if (player.getHealth() < player.getMaxHealth())
                player.addHealth(1);
            counter++;
        }	
    }

    //--------------------------------------------------------------------------
    // Display the game credits
    //--------------------------------------------------------------------------
    private void credit() {
        System.out.println ("*************");
        System.out.println ("Game credit");
        System.out.println ("*************");
        System.out.println ("\nCode: Robin Fjärem");
        System.out.println ("Story and maps: Robin Fjärem");
        System.out.println ("http://robinsuu.com");
        System.out.println("Music by: http://rolemusic.sawsquarenoise.com/"
                        + "\n(Licensed under http://creativecommons.org/licenses/by/4.0/)");

        System.out.println ("\nTesters:");
        System.out.println ("Marcus Folgert");
        System.out.println ("Emil Ehrs");
        System.out.println ("Francois Larouche (Special thanks for your coding advice!)");
        System.out.println ("Emanuel Högild");

        System.out.println("\nOther:");
        System.out.println ("http://crownlessking.com (GUI io console console class and GUI coding advice)");
        System.out.println ("Saad Benbouzid @ stackoverflow (Line-break code snippet)");
        System.out.println ("http://lauzet.com/argon/ (Online mapping tool");
    }

    //--------------------------------------------------------------------------
    // Saves the character data in a file
    //--------------------------------------------------------------------------
    private void saveGame() {
        System.out.println ("Saved the game.");
        saveNPC(); // Workaround
        saveItem();
        player.saveInventorySize(Character.getInventorySize()); // Workaround, see Character.java

        try {
            //Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream("Character.sav"));
            out.writeObject(player);
            out.close();
        }
        catch (IOException e) {
            System.out.println("Failed to read save file");
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    // Background saving for the GUI
    //--------------------------------------------------------------------------
    private void backgroundSave() {
        saveNPC(); // Workaround
        saveItem();
        player.saveInventorySize(Character.getInventorySize()); // Workaround, see Character.java
        try {
            //Serialize data object to a file
            ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream("Character.sav"));
            out.writeObject(player);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    // Loads the character data
    //--------------------------------------------------------------------------
    private void loadGame() {
        try {
            FileInputStream fileIn = new FileInputStream ("Character.sav");
            ObjectInputStream in = new ObjectInputStream (fileIn);
            player = (Character) in.readObject();
            in.close();
            fileIn.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException c) {
            System.out.println ("Character class not found");
            c.printStackTrace();
            return;
        }
        if(player.getName().equals("Unknown Character")) {
            System.out.println("Invalid save file, please create a new character.");
            startPrompt();
        }
        else {
            loadNPC(); // Workaround
            loadItem();
            player.loadInventorySize(); // Workaround, see Character.java
        }
    }

    //--------------------------------------------------------------------------
    // Set permanently removed NPC data from Rooms to Character.java
    //--------------------------------------------------------------------------
    private void saveNPC() {
        // Violet mushrooms
        if (ROOMS[25].getNPC() == 0)
            player.setVioletMushroom1(1);

        if (ROOMS[88].getNPC() == 0)
            player.setVioletMushroom2(1);

        if (ROOMS[128].getNPC() == 0)
            player.setVioletMushroom3(1);

        if (ROOMS[178].getNPC() == 0)
            player.setVioletMushroom4(1);

        if (ROOMS[212].getNPC() == 0)
            player.setVioletMushroom5(1);

        if (ROOMS[260].getNPC() == 0)
            player.setVioletMushroom6(1);

        if (ROOMS[355].getNPC() == 0)
            player.setVioletMushroom7(1);

        if (ROOMS[388].getNPC() == 0)
            player.setVioletMushroom8(1);

        // Black cat
        if (ROOMS[28].getNPC() == 0)
            player.setBlackCat(1);

        // Hardworking man
        if (ROOMS[73].getNPC() == 0)
            player.setHardworkingMan(1);

        // Rotting corpse
        if (ROOMS[101].getNPC() == 0)
            player.setRottingCorpse(1);

        // Frozen corpse
        if (ROOMS[223].getNPC() == 0)
            player.setFrozenCorpse(1);

        // Wooden chest
        if (ROOMS[266].getNPC() == 0)
            player.setWoodenChest(1);

        // Ancient chest
        if (ROOMS[312].getNPC() == 0)
            player.setAncientChest(1);

        // Zeramus
        if (ROOMS[429].getNPC() == 0)
            player.setZeramus(1);
    }

    //--------------------------------------------------------------------------
    // Set permanently removed NPCs in the Room objects after loading data
    //--------------------------------------------------------------------------
    private void loadNPC() {
        if (player.getMushroom1() == 1) {
            ROOMS[25].removeNPC();
            ROOMS[25].permanentRemoveNPC();
        }

        if (player.getMushroom2() == 1) {
            ROOMS[88].removeNPC();
            ROOMS[88].permanentRemoveNPC();
        }

        if (player.getMushroom3() == 1) {
            ROOMS[128].removeNPC();
            ROOMS[128].permanentRemoveNPC();
        }

        if (player.getMushroom4() == 1) {
            ROOMS[178].removeNPC();
            ROOMS[178].permanentRemoveNPC();
        }

        if (player.getMushroom5() == 1) {
            ROOMS[212].removeNPC();
            ROOMS[212].permanentRemoveNPC();
        }

        if (player.getMushroom6() == 1) {
            ROOMS[260].removeNPC();
            ROOMS[260].permanentRemoveNPC();
        }

        if (player.getMushroom7() == 1) {
            ROOMS[355].removeNPC();
            ROOMS[355].permanentRemoveNPC();
        }

        if (player.getMushroom8() == 1) {
            ROOMS[388].removeNPC();
            ROOMS[388].permanentRemoveNPC();
        }

        if (player.getBlackCat() == 1) {
            ROOMS[28].removeNPC();
            ROOMS[28].permanentRemoveNPC();
        }

        if (player.getHardworkingMan() == 1) {
            ROOMS[73].removeNPC();
            ROOMS[73].permanentRemoveNPC();
        }

        if (player.getRottingCorpse() == 1) {
            ROOMS[101].removeNPC();
            ROOMS[101].permanentRemoveNPC();
        }

        if (player.getFrozenCorpse() == 1) {
            ROOMS[223].removeNPC();
            ROOMS[223].permanentRemoveNPC();
        }

        if (player.getWoodenChest() == 1) {
            ROOMS[266].removeNPC();
            ROOMS[266].permanentRemoveNPC();
        }

        if (player.getAncientChest() == 1) {
            ROOMS[312].removeNPC();
            ROOMS[312].permanentRemoveNPC();
        }

        if (player.getZeramus() == 1) {
            ROOMS[429].removeNPC();
            ROOMS[429].permanentRemoveNPC();
        }
    }

    //--------------------------------------------------------------------------
    // Set permanently removed Item data from NPC to Character.java
    //--------------------------------------------------------------------------
    private void saveItem() {
        // Goblin king
        if (NPCS[116].getDroppedItem() == 0)
            player.setGoldenHilt(1);

        // Otri the Undying
        if (NPCS[126].getDroppedItem() == 0)
            player.setRuneEtchedBlade(1);
    }

    //--------------------------------------------------------------------------
    // Set permanently removed Items in the NPC objects after loading data
    //--------------------------------------------------------------------------
    private void loadItem() {
        // Goblin king
        if (player.getGoldenHilt() == 1)
            NPCS[116].setDroppedItem(0);

        if (player.getRuneEtchedBlade() == 1)
            NPCS[126].setDroppedItem(0);
    }

    //--------------------------------------------------------------------------
    // Math quiz (Designed for the philosopher in town but can be used anywhere)
    //--------------------------------------------------------------------------
    private void mathQuiz() {
        int mathProblem = 0;
        int a = 0;
        int b = 0;
        int c = 0;

        String rightAnswer = "";
        String playerAnswer = "";

        mathProblem = rand.nextInt(3) +1; // Randomizes the type of math problem asked

        switch (mathProblem) {
                case 1:
                    a = rand.nextInt(20) +1;
                    b = rand.nextInt(20) +1;
                    c = a*b;

                    rightAnswer = Integer.toString(c);

                    System.out.println ("\n" + NPCS[player.getCurrentNPC()] + " says: 'What is " + a + "*" + b + "?'");
                    System.out.print("\nAnswer: ");
                    playerAnswer = C.io.nextLine();
                    break;
                case 2:
                    a = rand.nextInt(1000) +1;
                    b = rand.nextInt(1000) +1;
                    c = a+b;

                    rightAnswer = Integer.toString(c);

                    System.out.println ("\n" + NPCS[player.getCurrentNPC()] + " says: 'What is " + a + "+" + b + "?'");
                    System.out.print("\nAnswer: ");
                    playerAnswer = C.io.nextLine();
                    break;
                case 3:
                    a = rand.nextInt(1000) +1;
                    b = rand.nextInt(1000) +1;
                    c = a-b;

                    rightAnswer = Integer.toString(c);

                    System.out.println ("\n" + NPCS[player.getCurrentNPC()] + " says: 'What is " + a + "-" + b + "?'");
                    System.out.print("\nAnswer: ");
                    playerAnswer = C.io.nextLine();
                    break;
                default:
                    System.out.println ("ERROR! Please report this bug to: robin.neko@gmail.com");
                    break;
        }

        if (playerAnswer.equals(rightAnswer)) {
            System.out.println ("\n" + NPCS[player.getCurrentNPC()] + " says: 'You are correct!'");
            System.out.println ("\nYou received 3 experience.\n");
            player.addExperience(3);
        }
        else {
            System.out.println ("\n" + NPCS[player.getCurrentNPC()] + " says: 'That's wrong! The right answer is: " + rightAnswer + ".'\n");
        }
    }
}
