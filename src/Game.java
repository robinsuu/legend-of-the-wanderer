//******************************************************************************
// Game.java
// 
// A simple game where the user can move through some preset text-based
// rooms and interact using several different commands.
//
// This is supposed to become version 2.1 of the game
//
//******************************************************************************

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Game implements java.io.Serializable 
{
	private String userInput = ""; // User input commands
	
	//Scanner scan = new Scanner (System.in); // Not needed. Replaced by the C.io method
	Random rand = new Random();
	
	// Creates the initial character object ("<Name">, <Strength>, <Health>)
	Character player = new Character();
	
	private Room[] rooms; // Array creating the game world aka. rooms
	private NPC[] npcs; // Array creating the NPCs
	
	ArrayList<String> description = new ArrayList<String>(); // Array List for the room descriptions which are acquired from rooms.txt
	
	//--------------------------------------------------------------------------
	// Constructor: Sets the initial values of the game variables and creates
	// the rooms.
	//--------------------------------------------------------------------------
	public Game () throws IOException
	{
		//******************************************************************************
		// 
		//									ROOMS SETUP
		//
		//******************************************************************************
		
		// Sets up the list of rooms. Change this as the number of rooms increases.
		// CAUTION: If this is not set to the EXACT number of rooms there will be a runtime error with the respawn timer
		// Also there can't be ANY blanks inbetween room instances created. Ie. The number of instances created always
		// needs to be the exact same as what is set here
		rooms = new Room[434];
		
		// Sets up the descriptions of the rooms using the rooms.txt file
		generateRooms();
		
		// ("<Room description>", <North>, <South>, <West>, <East>, <Up>, <Down>, <NPC>) - leads to those room IDs.
		//
		// The <NPC> variable sets whether the room contains an NPC or not. If set to higher than 0
		// it contains that NPC ID.
		
		// Starting house
		rooms[0] = new Room (description.get(0), 0, 0, 0, 0, 0, 0, 0); // Not used. 
		rooms[1] = new Room (description.get(1), 2, 0, 0, 0, 0, 0, 0); // Starting room. Exits: North to 2
		rooms[2] = new Room (description.get(2), 5, 1, 3, 4, 0, 0, 0); // Exits: North to 5, South to 1
		rooms[3] = new Room (description.get(3), 6, 0, 0, 2, 0, 0, 25); // Exits: North to 6, East to 2. Contains Rupert (#25)
		rooms[4] = new Room (description.get(4), 7, 0, 2, 0, 0, 0, 24); // Exits: North to 7, West to 2. Contains Josephine (#24)
		rooms[5] = new Room (description.get(5), 8, 2, 6, 7, 0, 0, 0); // Exits: North to 8, South to 2, West to 6, East to 7.
		rooms[6] = new Room (description.get(6), 0, 3, 430, 5, 0, 0, 0); // Exits: South to 3, West to 430, East to 5.
		rooms[7] = new Room (description.get(7), 0, 4, 5, 0, 0, 0, 26); // Exits: South to 4, West to 5. Contains an old man smoking his pipe (#26)
		rooms[8] = new Room (description.get(8), 0, 5, 0, 9, 0, 0, 0); // Exits: South to 5, East to 9
		rooms[9] = new Room (description.get(9), 0, 0, 8, 10, 0, 0, 0); // Exits: West to 8, East to 10
		rooms[10] = new Room (description.get(10), 0, 0, 9, 11, 0, 0, 0); // Exits: West to 9, East to 11
		rooms[11] = new Room (description.get(11), 0, 12, 10, 0, 0, 0, 0); // Exits: South to 12, West to 10
		rooms[12] = new Room (description.get(12), 11, 15, 13, 14, 0, 0, 1); // Exits: North to 11, South to 15, West to 13, East to 14. Contains a butler (#1)
		rooms[13] = new Room (description.get(13), 0, 0, 0, 12, 0, 0, 0); // Exits: East to 12
		rooms[14] = new Room (description.get(14), 0, 0, 12, 0, 0, 0, 0); // Exits: West to 12
		rooms[15] = new Room (description.get(15), 12, 16, 0, 0, 0, 0, 0); // Exits: North to 12, South to 16
		
		// The forest meadow
		rooms[16] = new Room (description.get(16), 15, 17, 0, 0, 0, 0, 0); // Exits: North to 15, South to 17.
		rooms[17] = new Room (description.get(17), 16, 18, 0, 0, 0, 0, 104); // Exits: North to 16, South to 18. Contains a tiny bird (#104)
		rooms[18] = new Room (description.get(18), 17, 21, 19, 20, 0, 0, 0); // Exits: North to 17, South to 21, West to 19, East to 20
		rooms[19] = new Room (description.get(19), 0, 22, 0, 18, 0, 0, 104); // Exits: South to 22, East to 18. Contains a tiny bird (#104)
		rooms[20] = new Room (description.get(20), 0, 23, 18, 24, 0, 0, 0); // Exits: South to 23, West to 18, East to 24
		rooms[21] = new Room (description.get(21), 18, 37, 22, 23, 0, 0, 102); // Exits: North to 18, South to 37, West to 22, East to 23. Contains a red fox (#102)
		rooms[22] = new Room (description.get(22), 19, 39, 0, 21, 0, 0, 104); // Exits: North to 19, South to 39, East to 21. Contains a tiny bird (#104)
		rooms[23] = new Room (description.get(23), 20, 38, 21, 0, 0, 0, 102); // Exits: North to 20, South to 38, West to 21. Contains a red fox (#102)
		rooms[37] = new Room (description.get(37), 21, 42, 39, 38, 0, 0, 0); // Exits: North to 21, South to 42, West to 39, East to 38
		rooms[38] = new Room (description.get(38), 23, 0, 37, 40, 0, 0, 0); // Exits: North to 23, West to 37, East to 40
		rooms[39] = new Room (description.get(39), 22, 0, 0, 37, 0, 0, 104); // Exits: North to 22, East to 37. Contains a tiny bird (#104)
		
		// The small cave
		rooms[40] = new Room (description.get(40), 0, 0, 38, 41, 0, 0, 0); // Exits: West to 38, East to 41 
		rooms[41] = new Room (description.get(41), 0, 0, 40, 0, 0, 0, 107); // Exits: West to 40. Contains a sleeping bear (#107)
		
		// The pathway and clearing (On the way to the tower)
		rooms[24] = new Room (description.get(24), 25, 0, 20, 0, 0, 0, 0); // Exits: North to 25, West to 20
		rooms[25] = new Room (description.get(25), 26, 24, 0, 0, 0, 0, 214); // Exits: North to 26, South to 24. Contains a violet mushroom (#214)
		rooms[26] = new Room (description.get(26), 27, 25, 0, 0, 0, 0, 0); // Exits: North to 27, South to 25
		rooms[27] = new Room (description.get(27), 32, 26, 0, 29, 0, 0, 104); // Exits: North to 32, South to 26, East to 29. Contains a tiny bird (#104)
		rooms[28] = new Room (description.get(28), 30, 0, 29, 0, 0, 0, 213); // Exits: North to 30, West to 29. Contains a black cat (#213)
		rooms[29] = new Room (description.get(29), 31, 0, 27, 28, 0, 0, 103); // Exits: North to 31, West to 27, East to 28. Contains a donkey (#103)
		rooms[30] = new Room (description.get(30), 0, 28, 31, 0, 0, 0, 102); // Exits: South to 28, West to 31. Contains a red fox (#102)
		rooms[31] = new Room (description.get(31), 33, 29, 32, 30, 0, 0, 104); // Exits: North to 33, South to 29, West to 32, East 30. Contains a tiny bird (#104)
		rooms[32] = new Room (description.get(32), 0, 27, 0, 31, 0, 0, 0); // Exits: South to 27, East to 31
		
		// The tower
		rooms[33] = new Room (description.get(33), 0, 31, 0, 0, 34, 0, 0); // Exits: South to 31, Up to 34
		rooms[34] = new Room (description.get(34), 0, 0, 0, 0, 35, 33, 0); // Exits: Down to 33, Up to 35
		rooms[35] = new Room (description.get(35), 0, 0, 0, 0, 36, 34, 0); // Exits: Down to 34, Up to 36
		rooms[36] = new Room (description.get(36), 0, 0, 0, 0, 0, 35, 106); // Exits: Down to 35. Contains an owl (#106)
		
		// The pathway leading to the house
		rooms[42] = new Room (description.get(42), 37, 43, 0, 0, 0, 0, 0); // Exits: North to 37, South to 43
		rooms[43] = new Room (description.get(43), 42, 44, 45, 0, 0, 0, 0); // Exits: North to 42, South to 44, West to 45 
		rooms[44] = new Room (description.get(44), 43, 61, 0, 0, 0, 0, 0); // Exits: North to 43, South to 61
		rooms[45] = new Room (description.get(45), 0, 0, 46, 43, 0, 0, 204); // Exits: West to 46, East to 43. Contains a scarecrow (#204)
		rooms[46] = new Room (description.get(46), 0, 0, 47, 45, 0, 0, 0); // Exits: West to 47, East to 45
		
		// The garden and the house
		rooms[47] = new Room (description.get(47), 48, 49, 50, 46, 0, 0, 0); // Exits: North to 48, South to 49, West to 50, East to 46 
		rooms[48] = new Room (description.get(48), 0, 47, 51, 0, 0, 0, 0); // Exits: South to 47, West to 51 
		rooms[49] = new Room (description.get(49), 47, 0, 52, 0, 0, 0, 0); // Exits: North to 47, West to 52
		rooms[50] = new Room (description.get(50), 51, 52, 0, 47, 0, 0, 203); // Exits: North to 51, South to 52, East to 47. Contains a small chair (#203)
		rooms[51] = new Room (description.get(51), 0, 50, 0, 48, 53, 0, 0); // Exits: South to 50, East to 48, Up to 53 
		rooms[52] = new Room (description.get(52), 50, 0, 0, 49, 0, 60, 0); // Exits: North to 50, East to 49, Down to 60
		rooms[53] = new Room (description.get(53), 0, 55, 0, 54, 0, 51, 0); // Exits: South to 55, East to 54, Down to 51 
		rooms[54] = new Room (description.get(54), 0, 56, 53, 0, 0, 0, 0); // Exits: South to 56, West to 53
		rooms[55] = new Room (description.get(55), 53, 57, 59, 56, 0, 0, 0); // Exits: North to 53, South to 57, West to 59, East to 56
		rooms[56] = new Room (description.get(56), 54, 58, 55, 0, 0, 0, 0); // Exits: North to 54, South to 58, West to 55 
		rooms[57] = new Room (description.get(57), 55, 0, 0, 58, 0, 0, 108); // Exits: North to 55, East to 58. Contains a dirty vagrant (#108)
		rooms[58] = new Room (description.get(58), 56, 0, 57, 0, 0, 0, 0); // Exits: North to 56, West to 57 
		rooms[59] = new Room (description.get(59), 0, 0, 0, 55, 0, 0, 0); // Exits: East to 55
		rooms[60] = new Room (description.get(60), 0, 0, 0, 0, 52, 0, 105); // Exits: Up to 52. Contains a cockroach (#105)
		
		// The road south and the farming field
		rooms[61] = new Room (description.get(61), 44, 62, 0, 0, 0, 0, 0); // Exits: North to 44, South to 62
		rooms[62] = new Room (description.get(62), 61, 63, 0, 0, 0, 0, 0); // Exits: North to 61, South to 63
		rooms[63] = new Room (description.get(63), 62, 64, 0, 0, 0, 0, 4); // Exits: North to 62, South to 64. Contains a wandering salesman (#4)
		rooms[64] = new Room (description.get(64), 63, 74, 0, 65, 0, 0, 0); // Exits: North to 63, South to 74, East to 65
		rooms[65] = new Room (description.get(65), 0, 68, 64, 66, 0, 0, 0); // Exits: South to 68, West to 64, East to 66
		rooms[66] = new Room (description.get(66), 0, 69, 65, 67, 0, 0, 0); // Exits: South to 69, West to 65, East to 67
		rooms[67] = new Room (description.get(67), 0, 70, 66, 0, 0, 0, 109); // Exits: South to 70, West to 66. Contains a happy cow (#109)
		rooms[68] = new Room (description.get(68), 65, 73, 74, 69, 0, 0, 204); // Exits: North to 65, South to 73, West to 74, East to 69. Contains a scarecrow (#204)
		rooms[69] = new Room (description.get(69), 66, 72, 68, 70, 0, 0, 109); // Exits: North to 66, South to 72, West to 68, East to 70. Contains a happy cow (#109)
		rooms[70] = new Room (description.get(70), 67, 71, 69, 0, 0, 0, 0); // Exits: North to 67, South to 71, West to 69
		rooms[71] = new Room (description.get(71), 70, 0, 72, 0, 0, 0, 103); // Exits: North to 70, West to 72. Contains a donkey (#103)
		rooms[72] = new Room (description.get(72), 69, 0, 73, 71, 0, 0, 0); // Exits: North to 69, West to 73, East to 71
		rooms[73] = new Room (description.get(73), 68, 0, 75, 72, 0, 0, 5); // Exits: North to 68, West to 75, East to 72. Contains a hardworking man (#5)
		rooms[74] = new Room (description.get(74), 64, 75, 0, 68, 0, 0, 140); // Exits: North to 64, South to 75, East to 68. Contains a stray dog (#140)
		rooms[75] = new Room (description.get(75), 74, 76, 0, 73, 0, 0, 0); // Exits: North to 74, South to 76, East to 73
		rooms[76] = new Room (description.get(76), 75, 77, 0, 0, 0, 0, 103); // Exits: North to 75, South to 77. Contains a donkey (#103)
		rooms[77] = new Room (description.get(77), 76, 78, 0, 0, 0, 0, 0); // Exits: North to 76, South to 78
		rooms[78] = new Room (description.get(78), 77, 143, 79, 0, 0, 0, 0); // Exits: North to 77, South to 143, West to 79
		
		// The ghostly road to The Goblin King's dungeon
		rooms[79] = new Room (description.get(79), 0, 0, 80, 78, 0, 0, 0); // Exits: West to 80, East to 78
		rooms[80] = new Room (description.get(80), 0, 0, 81, 79, 0, 0, 0); // Exits: West to 81, East to 79
		rooms[81] = new Room (description.get(81), 0, 0, 82, 80, 0, 0, 0); // Exits: West to 82, East to 80
		rooms[82] = new Room (description.get(82), 0, 0, 83, 81, 0, 0, 0); // Exits: West to 83, East to 81
		rooms[83] = new Room (description.get(83), 0, 0, 84, 82, 0, 0, 0); // Exits: West to 84, East to 82
		rooms[84] = new Room (description.get(84), 85, 0, 0, 83, 0, 0, 143); // Exits: North to 85, East to 83. Contains an evil forest spirit (#143)
		rooms[85] = new Room (description.get(85), 87, 84, 0, 86, 0, 0, 0); // Exits: North to 87, South to 84, East to 86
		rooms[86] = new Room (description.get(86), 0, 0, 85, 0, 0, 0, 111); // Exits: West to 85. Contains a sobbing little girl ghost (#111)
		rooms[87] = new Room (description.get(87), 0, 85, 88, 0, 0, 0, 110); // Exits: South to 85, West to 88. Contains a sick-looking wolf (#110)
		rooms[88] = new Room (description.get(88), 0, 0, 89, 87, 0, 0, 214); // Exits: West to 89, East to 87. Contains a violet mushroom (#214)
		rooms[89] = new Room (description.get(89), 0, 90, 0, 88, 0, 0, 0); // Exits: South to 90, East to 88
		rooms[90] = new Room (description.get(90), 89, 91, 0, 0, 0, 0, 110); // Exits: North to 89, South to 91. Contains a sick-looking wolf (#110)
		rooms[91] = new Room (description.get(91), 90, 0, 92, 0, 0, 0, 0); // Exits: North to 90, West to 92
		
		// the Goblin King's dungeon 1F
		rooms[92] = new Room (description.get(92), 93, 94, 95, 91, 0, 0, 0); // Exits: North to 93, South to 94, West to 95, East to 91
		rooms[93] = new Room (description.get(93), 0, 92, 96, 0, 0, 0, 0); // Exits: South to 92, West to 96
		rooms[94] = new Room (description.get(94), 92, 0, 97, 0, 0, 0, 0); // Exits: North to 92, West to 97
		rooms[95] = new Room (description.get(95), 96, 97, 99, 92, 0, 0, 112); // Exits: North to 96, South to 97, West to 99, East to 92. Contains a small goblin (#112)
		rooms[96] = new Room (description.get(96), 101, 95, 98, 93, 0, 0, 112); // Exits: North to 101, South to 95, West to 98, East to 93. Contains a small goblin (#112)
		rooms[97] = new Room (description.get(97), 95, 0, 100, 94, 0, 0, 112); // Exits: North to 95, West to 100, East to 94. Contains a small goblin (#112)
		rooms[98] = new Room (description.get(98), 0, 99, 104, 96, 0, 0, 0); // Exits: South to 99, West to 104, East to 96
		rooms[99] = new Room (description.get(99), 98, 100, 103, 95, 0, 0, 0); // Exits: North to 98, South to 100, West to 103, East to 95
		rooms[100] = new Room (description.get(100), 99, 0, 102, 97, 0, 0, 0); // Exits: North to 99, West to 102, East to 97
		rooms[101] = new Room (description.get(101), 0, 96, 0, 0, 0, 0, 205); // Exits: South to 96. Contains a rotting corpse (#205)
		rooms[102] = new Room (description.get(102), 103, 0, 0, 100, 0, 0, 105); // Exits: North to 103, East to 100. Contains a cockroach (#105)
		rooms[103] = new Room (description.get(103), 104, 102, 105, 99, 0, 0, 0); // Exits: North to 104, South to 102, West to 105, East to 99
		rooms[104] = new Room (description.get(104), 0, 103, 0, 98, 0, 0, 112); // Exits: South to 103, East to 98. Contains a small goblin (#112)
		rooms[105] = new Room (description.get(105), 0, 0, 106, 103, 0, 0, 0); // Exits: West to 106, East to 103
		rooms[106] = new Room (description.get(106), 107, 0, 0, 105, 0, 0, 112); // Exits: North to 107, East to 105. Contains a small goblin (#112)
		rooms[107] = new Room (description.get(107), 108, 106, 0, 0, 0, 0, 0); // Exits: North to 108, South to 106
		rooms[108] = new Room (description.get(108), 109, 107, 0, 0, 0, 0, 105); // Exits: North to 109, South to 107. Contains a cockroach (#105)
		rooms[109] = new Room (description.get(109), 110, 108, 0, 0, 0, 0, 0); // Exits: North to 110, South to 108
		rooms[110] = new Room (description.get(110), 0, 109, 0, 0, 0, 111, 0); // Exits: South to 109, Down to 111
		
		// the Goblin King's dungeon B1F
		rooms[111] = new Room (description.get(111), 0, 0, 0, 112, 110, 0, 0); // Exits: East to 112, Up to 110
		rooms[112] = new Room (description.get(112), 115, 114, 111, 113, 0, 0, 0); // Exits: North to 115, South to 114, West to 111, East to 113
		rooms[113] = new Room (description.get(113), 116, 117, 112, 190, 0, 0, 0); // Exits: North to 116, South to 117, West to 112, East to 190
		// #190 (Made a mistake in the layout, hence the strange order)
		rooms[190] = new Room (description.get(190), 0, 0, 113, 118, 0, 0, 113); // Exits: West to 113, East to 118. Contains a goblin warrior (#113)
		rooms[114] = new Room (description.get(114), 112, 127, 0, 117, 0, 0, 0); // Exits: North to 112, South to 127, East to 117
		rooms[115] = new Room (description.get(115), 0, 112, 0, 116, 0, 0, 114); // Exits: South to 112, East to 116. Contains a lazy goblin guard (#114)
		rooms[116] = new Room (description.get(116), 131, 113, 115, 0, 0, 0, 0); // Exits: North to 131, South to 113, West to 115
		rooms[117] = new Room (description.get(117), 113, 129, 114, 0, 0, 0, 0); // Exits: North to 113, South to 129, West to 114
		rooms[118] = new Room (description.get(118), 0, 0, 190, 119, 0, 0, 0); // Exits: West to 190, East to 119
		rooms[119] = new Room (description.get(119), 0, 0, 118, 120, 0, 0, 0); // Exits: West to 118, East to 120
		rooms[120] = new Room (description.get(120), 121, 122, 119, 123, 0, 0, 113); // Exits: North to 121, South to 122, West to 119, East to 123. Contains a goblin warrior (#113)
		rooms[121] = new Room (description.get(121), 0, 120, 0, 124, 0, 0, 0); // Exits: South to 120, East to 124
		rooms[122] = new Room (description.get(122), 120, 0, 0, 125, 0, 0, 0); // Exits: North to 120, East to 125
		rooms[123] = new Room (description.get(123), 124, 125, 120, 126, 0, 0, 113); // Exits: North to 124, South to 125, West to 120, East to 126. Contains a goblin warrior (#113)
		rooms[124] = new Room (description.get(124), 0, 123, 121, 0, 0, 0, 0); // Exits: South to 123, West to 121
		rooms[125] = new Room (description.get(125), 123, 0, 122, 0, 0, 0, 113); // Exits: North to 123, West to 122. Contains a goblin warrior (#113)
		rooms[126] = new Room (description.get(126), 0, 0, 123, 0, 0, 0, 116); // Exits: West to 123. Contains the Goblin King (#116)
		rooms[127] = new Room (description.get(127), 114, 0, 0, 129, 0, 128, 0); // Exits: North to 114, East to 129, Down to 128
		rooms[128] = new Room (description.get(128), 0, 0, 0, 0, 127, 0, 214); // Exits: Up to 127. Contains a violet mushroom (#214)
		rooms[129] = new Room (description.get(129), 117, 0, 127, 137, 0, 130, 0); // Exits: North to 117, West to 127, East to 137, Down to 130
		rooms[130] = new Room (description.get(130), 0, 0, 0, 0, 129, 0, 0); // Exits: Up to 129
		rooms[131] = new Room (description.get(131), 132, 116, 0, 0, 0, 0, 0); // Exits: North to 132, South to 116
		rooms[132] = new Room (description.get(132), 0, 131, 0, 133, 0, 0, 0); // Exits: South to 131, East to 133
		rooms[133] = new Room (description.get(133), 0, 134, 132, 136, 0, 0, 0); // Exits: South to 134, West to 132, East to 136
		rooms[134] = new Room (description.get(134), 133, 0, 0, 135, 0, 0, 0); // Exits: North to 133, East to 135
		rooms[135] = new Room (description.get(135), 136, 0, 134, 0, 0, 0, 115); // Exits: North to 136, West to 134. Contains a goblin cook (#115)
		rooms[136] = new Room (description.get(136), 0, 135, 133, 0, 0, 0, 0); // Exits: South to 135, West to 133
		rooms[137] = new Room (description.get(137), 0, 0, 129, 139, 0, 138, 0); // Exits: West to 129, East to 139, Down to 138
		rooms[138] = new Room (description.get(138), 0, 0, 0, 0, 137, 0, 117); // Exits: Up to 137. Contains a prisoner (#117)
		rooms[139] = new Room (description.get(139), 0, 141, 137, 140, 0, 0, 0); // Exits: South to 141, West to 137, East to 140
		rooms[140] = new Room (description.get(140), 0, 142, 139, 0, 0, 0, 114); // Exits: South to 142, West to 139. Contains a lazy goblin guard (#114)
		rooms[141] = new Room (description.get(141), 139, 0, 0, 142, 0, 0, 0); // Exits: North to 139, East to 142
		rooms[142] = new Room (description.get(142), 140, 0, 141, 0, 0, 0, 114); // Exits: North to 140, West to 141. Contains a lazy goblin guard (#114)
		
		// The road to Halin village. (Continues from 78)
		rooms[143] = new Room (description.get(143), 78, 144, 0, 0, 0, 0, 0); // Exits: North to 78, South to 144
		rooms[144] = new Room (description.get(144), 143, 145, 0, 0, 0, 0, 120); // Exits: North to 143, South to 145. Contains a road bandit (#120)
		rooms[145] = new Room (description.get(145), 144, 146, 0, 0, 0, 0, 0); // Exits: North to 144, South to 146
		rooms[146] = new Room (description.get(146), 145, 147, 0, 0, 0, 0, 0); // Exits: North to 145, South to 147
		
		// Halin village
		rooms[147] = new Room (description.get(147), 146, 148, 176, 156, 0, 0, 9); // Exits: North to 146, South to 148, West to 176, East to 156. Contains a town guard (#9)
		rooms[148] = new Room (description.get(148), 147, 149, 0, 166, 0, 0, 0); // Exits: North to 147, South to 149, East to 166
		rooms[149] = new Room (description.get(149), 148, 150, 0, 167, 0, 0, 0); // Exits: North to 148, South to 150, East to 167
		rooms[150] = new Room (description.get(150), 149, 151, 173, 168, 0, 0, 0); // Exits: North to 149, South to 151, West to 173, East to 168
		rooms[151] = new Room (description.get(151), 150, 152, 174, 169, 0, 0, 201); // Exits: North to 150, South to 152, West to 174, East to 169. Contains a fountain (#201)
		rooms[152] = new Room (description.get(152), 151, 153, 175, 170, 0, 0, 0); // Exits: North to 151, South to 153, West to 175, East to 170
		rooms[153] = new Room (description.get(153), 152, 154, 0, 0, 0, 0, 0); // Exits: North to 152, South to 154
		rooms[154] = new Room (description.get(154), 153, 155, 187, 172, 0, 0, 0); // Exits: North to 153, South to 155, West to 187, East to 172
		rooms[155] = new Room (description.get(155), 154, 191, 188, 189, 0, 0, 9); // Exits: North to 154, South to 191, West to 188, East to 189. Contains a town guard (#9)
		rooms[156] = new Room (description.get(156), 0, 0, 147, 157, 0, 0, 0); // Exits: West to 147, East to 157
		rooms[157] = new Room (description.get(157), 0, 158, 156, 0, 0, 0, 9); // Exits: South to 158, West to 156. Contains a town guard (#9)
		rooms[158] = new Room (description.get(158), 157, 159, 0, 0, 0, 0, 0); // Exits: North to 157, South to 159
		rooms[159] = new Room (description.get(159), 158, 160, 0, 0, 0, 0, 118); // Exits: North to 158, South to 160. Contains a street thug (#118)
		rooms[160] = new Room (description.get(160), 159, 161, 168, 0, 0, 0, 0); // Exits: North to 159, South to 161, West to 168
		rooms[161] = new Room (description.get(161), 160, 162, 169, 0, 0, 0, 0); // Exits: North to 160, South to 162, West to 169
		rooms[162] = new Room (description.get(162), 161, 163, 170, 0, 0, 0, 0); // Exits: North to 161, South to 163, West to 170
		rooms[163] = new Room (description.get(163), 162, 164, 171, 0, 0, 0, 0); // Exits: North to 162, South to 164, West to 171
		rooms[164] = new Room (description.get(164), 163, 165, 172, 0, 0, 0, 119); // Exits: North to 163, South to 165, West to 172. Contains a big rat (#119)
		rooms[165] = new Room (description.get(165), 164, 0, 189, 0, 0, 0, 11); // Exits: North to 164, West to 189. Contains an elderly villager (#11)
		rooms[166] = new Room (description.get(166), 0, 0, 148, 0, 0, 0, 30); // Exits: West to 148. Contains Harald the innkeeper (#30)
		rooms[167] = new Room (description.get(167), 0, 0, 149, 0, 0, 0, 2); // Exits: West to 149. Contains a shopkeeper (#2)
		rooms[168] = new Room (description.get(168), 0, 169, 150, 160, 0, 0, 0); // Exits: South to 169, West to 150, East to 160
		rooms[169] = new Room (description.get(169), 168, 170, 151, 161, 0, 0, 6); // Exits: North to 168, South to 170, West to 151, East to 161. Contains a villager (#6)
		rooms[170] = new Room (description.get(170), 169, 0, 152, 162, 0, 0, 0); // Exits: North to 169, West to 152, East to 162
		rooms[171] = new Room (description.get(171), 0, 0, 0, 163, 0, 0, 7); // Exits: East to 163. Contains Grunbald the magician (#7)
		rooms[172] = new Room (description.get(172), 0, 189, 154, 164, 0, 0, 0); // Exits: South to 189, West to 154, East to 164
		rooms[173] = new Room (description.get(173), 0, 174, 180, 150, 0, 0, 202); // Exits: South to 174, West to 180, East to 150. Contains a fortune telling machine (#202)
		rooms[174] = new Room (description.get(174), 173, 175, 181, 151, 0, 0, 6); // Exits: North to 173, South to 175, West to 181, East to 151. Contains a villager (#6)
		rooms[175] = new Room (description.get(175), 174, 0, 182, 152, 0, 0, 140); // Exits: North to 174, West to 182, East to 152. Contains a stray dog (#140)
		rooms[176] = new Room (description.get(176), 0, 0, 177, 147, 0, 0, 0); // Exits: West to 177, East to 147
		rooms[177] = new Room (description.get(177), 0, 178, 0, 176, 0, 0, 0); // Exits: South to 178, East to 176
		rooms[178] = new Room (description.get(178), 177, 179, 0, 0, 0, 0, 214); // Exits: North to 177, South to 179. Contains a violet mushroom (#214)
		rooms[179] = new Room (description.get(179), 178, 180, 0, 0, 0, 0, 10); // Exits: North to 178, South to 180. Contains a philosopher (#10)
		rooms[180] = new Room (description.get(180), 179, 181, 0, 173, 0, 0, 0); // Exits: North to 179, South to 181, East to 173
		rooms[181] = new Room (description.get(181), 180, 182, 0, 174, 0, 0, 119); // Exits: North to 180, South to 182, East to 174. Contains a big rat (#119)
		rooms[182] = new Room (description.get(182), 181, 183, 0, 175, 0, 0, 0); // Exits: North to 181, South to 183, East to 175
		rooms[183] = new Room (description.get(183), 182, 184, 0, 186, 0, 0, 0); // Exits: North to 182, South to 184, East to 186
		rooms[184] = new Room (description.get(184), 183, 185, 0, 187, 0, 0, 0); // Exits: North to 183, South to 185, East to 187
		rooms[185] = new Room (description.get(185), 184, 0, 0, 188, 0, 0, 9); // Exits: North to 184, East to 188. Contains a town guard (#9)
		rooms[186] = new Room (description.get(186), 0, 0, 183, 0, 0, 0, 8); // Exits: West to 183. Contains Skeld the blacksmith (#8)
		rooms[187] = new Room (description.get(187), 0, 188, 184, 154, 0, 0, 140); // Exits: South to 188, West to 184, East to 154. Contains a stray dog (#140)
		rooms[188] = new Room (description.get(188), 187, 0, 185, 155, 0, 0, 0); // Exits: North to 187, West to 185, East to 155
		rooms[189] = new Room (description.get(189), 172, 0, 155, 165, 0, 0, 0); // Exits: North to 172, West to 155, East to 165
		// #190 is in the Goblin King's dungeon
		
		// The south road and the small camp
		rooms[191] = new Room (description.get(191), 155, 192, 0, 0, 0, 0, 0); // Exits: North to 155, South to 192
		rooms[192] = new Room (description.get(192), 191, 194, 0, 193, 0, 0, 0); // Exits: North to 191, South to 194, East to 193
		rooms[193] = new Room (description.get(193), 0, 0, 192, 0, 0, 0, 12); // Exits: West to 192. Contains a frightened town guard (#12)
		rooms[194] = new Room (description.get(194), 192, 195, 0, 0, 0, 0, 0); // Exits: North to 192, South to 195
		rooms[195] = new Room (description.get(195), 194, 0, 0, 196, 0, 0, 0); // Exits: North to 194, East to 196
		rooms[196] = new Room (description.get(196), 0, 0, 195, 197, 0, 0, 120); // Exits: West to 195, East to 197. Contains a road bandit (#120)
		rooms[197] = new Room (description.get(197), 0, 0, 196, 198, 0, 0, 0); // Exits: West to 196, East to 198
		rooms[198] = new Room (description.get(198), 0, 199, 197, 206, 0, 0, 0); // Exits: South to 199, West to 197, East to 206
		rooms[199] = new Room (description.get(199), 198, 200, 0, 0, 0, 0, 119); // Exits: North to 198, South to 200. Contains a big rat (#119)
		rooms[200] = new Room (description.get(200), 199, 204, 202, 201, 0, 0, 0); // Exits: North to 199, South to 204, West to 202, East to 201
		rooms[201] = new Room (description.get(201), 0, 205, 200, 0, 0, 0, 14); // Exits: South to 205, West to 200. Contains Lianne (#14)
		rooms[202] = new Room (description.get(202), 0, 203, 0, 200, 0, 0, 16); // Exits: South to 203, East to 200. Contains Asta (#16)
		rooms[203] = new Room (description.get(203), 202, 0, 0, 204, 0, 0, 15); // Exits: North to 202, East to 204. Contains Thorulf (#15)
		rooms[204] = new Room (description.get(204), 200, 0, 203, 205, 0, 0, 0); // Exits: North to 200, West to 203, East to 205
		rooms[205] = new Room (description.get(205), 201, 0, 204, 0, 0, 0, 13); // Exits: North to 201, West to 204. Contains Glenn the bard (#13)
		rooms[206] = new Room (description.get(206), 0, 0, 198, 207, 0, 0, 120); // Exits: West to 198, East to 207. Contains a road bandit (#120)
		rooms[207] = new Room (description.get(207), 0, 0, 206, 208, 0, 0, 0); // Exits: West to 206, East to 208
		
		// The path to the mountain and climbing the mountain
		rooms[208] = new Room (description.get(208), 0, 209, 207, 0, 0, 0, 0); // Exits: South to 209, West to 207
		rooms[209] = new Room (description.get(209), 208, 210, 0, 0, 0, 0, 0); // Exits: North to 208, South to 210
		rooms[210] = new Room (description.get(210), 209, 0, 0, 211, 0, 0, 0); // Exits: North to 209, East to 211
		rooms[211] = new Room (description.get(211), 0, 0, 210, 212, 0, 0, 142); // Exits: West to 210, East to 212. Contains a mountaineer (#142)
		rooms[212] = new Room (description.get(212), 0, 0, 211, 0, 213, 0, 214); // Exits: West to 211, Up to 213. Contains a violet mushroom (#214)
		rooms[213] = new Room (description.get(213), 214, 0, 0, 0, 0, 212, 0); // Exits: North to 214, Down to 212
		rooms[214] = new Room (description.get(214), 215, 213, 0, 0, 0, 0, 141); // Exits: North to 215, South to 213. Contains a hawk (#141)
		rooms[215] = new Room (description.get(215), 0, 214, 0, 0, 216, 0, 0); // Exits: South to 214, Up to 216
		rooms[216] = new Room (description.get(216), 0, 0, 217, 0, 0, 215, 122); // Exits: West to 217, Down to 215. Contains a mountain goat (#122)
		rooms[217] = new Room (description.get(217), 0, 0, 218, 216, 0, 0, 0); // Exits: West to 218, East to 216
		rooms[218] = new Room (description.get(218), 0, 0, 0, 217, 219, 0, 142); // Exits: East to 217, Up to 219. Contains a crazy mountaineer (#142)
		rooms[219] = new Room (description.get(219), 0, 0, 0, 220, 0, 218, 0); // Exits: East to 220, Down to 218
		rooms[220] = new Room (description.get(220), 0, 0, 219, 221, 0, 0, 121); // Exits: West to 219, East to 221. Contains a mountain lion (#121)
		rooms[221] = new Room (description.get(221), 0, 0, 220, 222, 0, 0, 0); // Exits: West to 220, East to 222
		rooms[222] = new Room (description.get(222), 0, 223, 221, 0, 224, 0, 0); // Exits: South to 223, West to 221, Up to 224
		rooms[223] = new Room (description.get(223), 222, 0, 0, 0, 0, 0, 208); // Exits: North to 222. Contains the frozen corpse of an adventurer (#208)
		
		// The summit of the mountain
		rooms[224] = new Room (description.get(224), 0, 225, 0, 0, 0, 222, 121); // Exits: South to 225, Down to 222. Contains a mountain lion (#121)
		rooms[225] = new Room (description.get(225), 224, 226, 0, 0, 0, 0, 0); // Exits:  North to 224, South to 226
		rooms[226] = new Room (description.get(226), 225, 0, 227, 229, 0, 0, 0); // Exits: North to 225, West to 227, East to 229
		rooms[227] = new Room (description.get(227), 0, 0, 0, 226, 0, 0, 122); // Exits: East to 226. Contains a mountain goat (#122)
		rooms[228] = new Room (description.get(228), 0, 229, 0, 0, 0, 0, 17); // Exits: South to 229. Contains a hermit (#17)
		rooms[229] = new Room (description.get(229), 228, 0, 226, 230, 0, 0, 0); // Exits: North to 228, West to 226, East to 230
		rooms[230] = new Room (description.get(230), 0, 0, 229, 231, 0, 0, 0); // Exits: West to 229, East to 231
		rooms[231] = new Room (description.get(231), 0, 0, 230, 0, 0, 0, 206); // Exits: West to 230. Contains a wooden elevator (#206)
		
		// The tribal village
		rooms[232] = new Room (description.get(232), 0, 0, 0, 233, 0, 0, 207); // Exits: East to 233. Contains a wooden elevator (#207)
		rooms[233] = new Room (description.get(233), 234, 235, 232, 237, 0, 0, 0); // Exits: North to 234, South to 235, West to 232, East to 237
		rooms[234] = new Room (description.get(234), 0, 233, 0, 238, 0, 0, 0); // Exits: South to 233, East to 238
		rooms[235] = new Room (description.get(235), 233, 0, 0, 236, 0, 0, 0); // Exits: North to 233, East to 236
		rooms[236] = new Room (description.get(236), 237, 239, 235, 240, 0, 0, 0); // Exits: North to 237, South to 239, West to 235, East to 240
		rooms[237] = new Room (description.get(237), 238, 236, 233, 241, 0, 0, 209); // Exits: North to 238, South to 236, West to 233, East to 241. Contains a bonfire (#209)
		rooms[238] = new Room (description.get(238), 244, 237, 234, 242, 0, 0, 0); // Exits: North to 244, South to 237, West to 234, East to 242
		rooms[239] = new Room (description.get(239), 236, 0, 0, 0, 0, 0, 19); // Exits: North to 236. Contains the shaman of the Zulah tribe (#19)
		rooms[240] = new Room (description.get(240), 241, 0, 236, 0, 0, 0, 0); // Exits: North to 241, West to 236
		rooms[241] = new Room (description.get(241), 242, 240, 237, 245, 0, 0, 20); // Exits: North to 242, South to 240, West to 237, East to 245. Contains a hunter of the Zulah tribe (#20)
		rooms[242] = new Room (description.get(242), 243, 241, 238, 0, 0, 0, 0); // Exits: North to 243, South to 241, West to 238
		rooms[243] = new Room (description.get(243), 0, 242, 0, 0, 0, 0, 18); // Exits: South to 242. Contains a member of the Zulah tribe (#18)
		rooms[244] = new Room (description.get(244), 0, 238, 0, 0, 0, 0, 18); // Exits: South to 238. Contains a member of the Zulah tribe (#18)
		
		// The narrow bridge to the marsh and the beginning of the marsh
		rooms[245] = new Room (description.get(245), 0, 0, 241, 246, 0, 0, 0); // Exits: West to 241, East to 246
		rooms[246] = new Room (description.get(246), 0, 0, 245, 247, 0, 0, 0); // Exits: West to 245, East to 247
		rooms[247] = new Room (description.get(247), 0, 0, 246, 248, 0, 0, 0); // Exits: West to 246, East to 248
		rooms[248] = new Room (description.get(248), 0, 0, 247, 249, 0, 0, 0); // Exits: West to 247, East to 249
		rooms[249] = new Room (description.get(249), 0, 0, 248, 250, 0, 0, 0); // Exits: West to 248, East to 250
		rooms[250] = new Room (description.get(250), 0, 0, 249, 251, 0, 0, 0); // Exits: West to 249, East to 251
		rooms[251] = new Room (description.get(251), 0, 252, 250, 253, 0, 0, 0); // Exits: South to 252, West to 250, East to 253
		rooms[252] = new Room (description.get(252), 251, 256, 0, 254, 0, 0, 124); // Exits: North to 251, South to 256, East to 254. Contains a poisonous green frog (#124)
		rooms[253] = new Room (description.get(253), 0, 254, 251, 0, 0, 0, 0); // Exits: South to 254, West to 251
		rooms[254] = new Room (description.get(254), 253, 255, 252, 257, 0, 0, 0); // Exits: North to 253, South to 255, West to 252, East to 257
		rooms[255] = new Room (description.get(255), 254, 0, 256, 0, 0, 0, 0); // Exits: North to 254, West to 256
		rooms[256] = new Room (description.get(256), 252, 0, 0, 255, 0, 0, 0); // Exits: North to 252, East to 255
		rooms[257] = new Room (description.get(257), 0, 0, 254, 258, 0, 0, 212); // Exits: West to 254, East to 258. Contains a wooden sign (#212)
		
		// The marsh of the dead
		rooms[258] = new Room (description.get(258), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
		rooms[259] = new Room (description.get(259), 0, 0, 0, 0, 0, 0, 124); // Exits: Random from 257-271. Contains a poisonous green frog (#124)
		rooms[260] = new Room (description.get(260), 0, 0, 0, 0, 0, 0, 214); // Exits: Random from 257-271. Contains a violet mushroom (#214)
		rooms[261] = new Room (description.get(261), 0, 0, 0, 0, 0, 0, 123); // Exits: Random from 257-271. Contains a marsh horror (#123)
		rooms[262] = new Room (description.get(262), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
		rooms[263] = new Room (description.get(263), 0, 0, 0, 0, 0, 0, 123); // Exits: Random from 257-271. Contains a marsh horror (#123)
		rooms[264] = new Room (description.get(264), 0, 0, 0, 0, 0, 0, 129); // Exits: Random from 257-271. Contains a marsh serpent (#129)
		rooms[265] = new Room (description.get(265), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
		rooms[266] = new Room (description.get(266), 0, 0, 0, 0, 0, 0, 210); // Exits: Random from 257-271. Contains a wooden chest covered with grime (#210)
		rooms[267] = new Room (description.get(267), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
		rooms[268] = new Room (description.get(268), 0, 0, 0, 0, 0, 0, 123); // Exits: Random from 257-271. Contains a marsh horror (#123)
		rooms[269] = new Room (description.get(269), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
		rooms[270] = new Room (description.get(270), 0, 0, 0, 0, 0, 0, 21); // Exits: Random from 257-271. Contains a cackling mad man (#21)
		rooms[271] = new Room (description.get(271), 272, 274, 270, 273, 0, 0, 129); // Exits: North to 272, South to 274, West to 270, East to 273. Contains a marsh serpent (#129)
		
		// Before the crypts
		rooms[272] = new Room (description.get(272), 0, 271, 0, 276, 0, 0, 125); // Exits: South to 271, East to 276. Contains a grave robber (#125)
		rooms[273] = new Room (description.get(273), 276, 275, 271, 278, 0, 0, 0); // Exits: North to 276, South to 275, West to 271, East to 278
		rooms[274] = new Room (description.get(274), 271, 0, 0, 275, 0, 0, 0); // Exits: North to 271, East to 275
		rooms[275] = new Room (description.get(275), 273, 280, 274, 279, 0, 0, 125); // Exits: North to 273, South to 280, West to 274, East to 279. Contains a grave robber (#125)
		rooms[276] = new Room (description.get(276), 0, 273, 272, 277, 0, 0, 0); // Exits: South to 273, West to 272, East to 277
		rooms[277] = new Room (description.get(277), 0, 278, 276, 0, 0, 0, 0); // Exits: South to 278, West to 276
		rooms[278] = new Room (description.get(278), 277, 279, 273, 313, 0, 0, 0); // Exits: North to 277, South to 279, West to 273, East to 313
		rooms[279] = new Room (description.get(279), 278, 0, 275, 0, 0, 0, 129); // Exits: North to 278, West to 275. Contains a marsh serpent (#129)
		rooms[280] = new Room (description.get(280), 275, 0, 0, 0, 0, 281, 0); // Exits: North to 275, Down to 281
		
		// Inside the crypts
		rooms[281] = new Room (description.get(281), 0, 282, 0, 0, 280, 0, 0); // Exits: Up to 280, South to 282
		rooms[282] = new Room (description.get(282), 281, 283, 0, 0, 0, 0, 0); // Exits: North to 281, South to 283
		rooms[283] = new Room (description.get(283), 282, 0, 284, 0, 0, 0, 0); // Exits: North to 282, West to 284
		rooms[284] = new Room (description.get(284), 285, 286, 292, 283, 0, 0, 127); // Exits: North to 285, South to 286, West to 292, East to 283. Contains a chainmail armored skeleton (#127)
		rooms[285] = new Room (description.get(285), 311, 284, 293, 0, 0, 0, 0); // Exits: North to 311, South to 284, West to 293
		rooms[286] = new Room (description.get(286), 284, 289, 291, 287, 0, 0, 0); // Exits: North to 284, South to 289, West to 291, East to 287
		rooms[287] = new Room (description.get(287), 0, 288, 286, 0, 0, 0, 127); // Exits: South to 288, West to 286. Contains a chainmail armored skeleton (#127)
		rooms[288] = new Room (description.get(288), 287, 296, 289, 0, 0, 0, 0); // Exits: North to 287, South to 296, West to 289
		rooms[289] = new Room (description.get(289), 286, 295, 290, 288, 0, 0, 0); // Exits: North to 286, South to 295, West to 290, East to 288
		rooms[290] = new Room (description.get(290), 291, 294, 0, 289, 0, 0, 0); // Exits: North to 291, South to 294, East to 289
		rooms[291] = new Room (description.get(291), 292, 290, 297, 286, 0, 0, 127); // Exits: North to 292, South to 290, West to 297, East to 286. Contains a chainmail armored skeleton (#127)
		rooms[292] = new Room (description.get(292), 293, 291, 0, 284, 0, 0, 0); // Exits: North to 293, South to 291, East to 284
		rooms[293] = new Room (description.get(293), 310, 292, 0, 285, 0, 0, 0); // Exits: North to 310, South to 292, East to 285
		rooms[294] = new Room (description.get(294), 290, 307, 0, 295, 0, 0, 0); // Exits: North to 290, South to 307, East to 295
		rooms[295] = new Room (description.get(295), 289, 308, 294, 296, 0, 0, 0); // Exits: North to 289, South to 308, West to 294, East to 296
		rooms[296] = new Room (description.get(296), 288, 309, 295, 0, 0, 0, 0); // Exits: North to 288, South to 309, West to 295
		
		// The tomb
		rooms[297] = new Room (description.get(297), 0, 0, 0, 291, 0, 0, 22); // Exits: East to 291. Contains the guardian of the tomb (#22)
		rooms[298] = new Room (description.get(298), 299, 300, 302, 297, 0, 0, 127); // Exits: North to 299, South to 300, West to 302, East to 297. Contains a chainmail armored skeleton (#127)
		rooms[299] = new Room (description.get(299), 0, 298, 303, 0, 0, 0, 0); // Exits: South to 298, West to 303
		rooms[300] = new Room (description.get(300), 298, 0, 301, 0, 0, 0, 0); // Exits: North to 298, West to 301
		rooms[301] = new Room (description.get(301), 302, 0, 306, 300, 0, 0, 0); // Exits: North to 302, West to 306, East to 300
		rooms[302] = new Room (description.get(302), 303, 301, 305, 298, 0, 0, 126); // Exits: North to 303, South to 301, West to 305, East to 298. Contains Otri the Undying (#126)
		rooms[303] = new Room (description.get(303), 0, 302, 304, 299, 0, 0, 0); // Exits: South to 302, West to 304, East to 299
		rooms[304] = new Room (description.get(304), 0, 305, 0, 303, 0, 0, 127); // Exits: South to 305, East to 303.  Contains a chainmail armored skeleton (#127)
		rooms[305] = new Room (description.get(305), 304, 306, 312, 302, 0, 0, 0); // Exits: North to 304, South to 306, West to 312, East to 302
		rooms[306] = new Room (description.get(306), 305, 0, 0, 301, 0, 0, 0); // Exits: North to 305, East to 301
		
		// The small crypts
		rooms[307] = new Room (description.get(307), 294, 0, 0, 0, 0, 0, 128); // Exits: North to 294. Contains a soulless corpse (#128)
		rooms[308] = new Room (description.get(308), 295, 0, 0, 0, 0, 0, 0); // Exits: North to 295
		rooms[309] = new Room (description.get(309), 296, 0, 0, 0, 0, 0, 128); // Exits: North to 296. Contains a soulless corpse (#128)
		rooms[310] = new Room (description.get(310), 0, 293, 0, 0, 0, 0, 128); // Exits: South to 293. Contains a soulless corpse (#128)
		rooms[311] = new Room (description.get(311), 0, 285, 0, 0, 0, 0, 130); // Exits: South to 285. Contains a terrified grave robber (#130)
		
		// The treasure room inside the tomb
		rooms[312] = new Room (description.get(312), 0, 0, 0, 305, 0, 0, 211); // Exits: East to 305. Contains an ancient chest (#211)
		
		// The start of the desert and Ezme's flying carpet shop
		rooms[313] = new Room (description.get(313), 0, 314, 278, 316, 0, 0, 0); // Exits: South to 314, West to 278, East to 316
		rooms[314] = new Room (description.get(314), 313, 319, 0, 315, 0, 0, 132); // Exits: North to 313, South to 319, East to 315. Contains a scorpion (#132)
		rooms[315] = new Room (description.get(315), 316, 318, 314, 0, 0, 0, 29); // Exits: North to 316, South to 318, West to 314. Contains a desert trader (#29)
		rooms[316] = new Room (description.get(316), 0, 315, 313, 317, 0, 0, 0); // Exits: South to 315, West to 313, East to 317
		rooms[317] = new Room (description.get(317), 0, 0, 316, 0, 0, 0, 23); // Exits: West to 316. Contains Ezme the magic carpet salesman (#23)
		rooms[318] = new Room (description.get(318), 315, 0, 319, 0, 0, 0, 131); // Exits: North to 315, West to 319. Contains a rattlesnake (#131)
		rooms[319] = new Room (description.get(319), 314, 0, 0, 318, 0, 0, 0); // Exits: North to 314, East to 318
		
		// The end of the desert and the settlement
		rooms[320] = new Room (description.get(320), 0, 323, 0, 321, 0, 0, 0); // Exits: South to 323, East to 321
		rooms[321] = new Room (description.get(321), 0, 324, 320, 322, 0, 0, 0); // Exits: South to 324, West to 320, East to 322
		rooms[322] = new Room (description.get(322), 0, 325, 321, 0, 0, 0, 131); // Exits: South to 325, West to 321. Contains a rattlesnake (#131)
		rooms[323] = new Room (description.get(323), 320, 326, 0, 324, 0, 0, 132); // Exits: North to 320, South to 326, East to 324. Contains a scorpion (#132)
		rooms[324] = new Room (description.get(324), 321, 327, 323, 325, 0, 0, 0); // Exits: North to 321, South to 327, West to 323, East to 325
		rooms[325] = new Room (description.get(325), 322, 328, 324, 329, 0, 0, 0); // Exits: North to 322, South to 328, West to 324, East to 329
		rooms[326] = new Room (description.get(326), 323, 0, 0, 327, 0, 0, 0); // Exits: North to 323, East to 327
		rooms[327] = new Room (description.get(327), 324, 336, 326, 328, 0, 0, 28); // Exits: North to 324, South to 336, West to 326, East to 328. Contains Lieutenant Harken (#28)
		rooms[328] = new Room (description.get(328), 325, 0, 327, 0, 0, 0, 0); // Exits: North to 325, West to 327
		rooms[329] = new Room (description.get(329), 0, 0, 325, 330, 0, 0, 0); // Exits: West to 325, East to 330
		rooms[330] = new Room (description.get(330), 331, 332, 329, 334, 0, 0, 0); // Exits: North to 331, South to 332, West to 329, East to 334
		rooms[331] = new Room (description.get(331), 0, 330, 0, 335, 0, 0, 0); // Exits: South to 330, East to 335
		rooms[332] = new Room (description.get(332), 330, 0, 0, 333, 0, 0, 0); // Exits: North to 330, East to 333
		rooms[333] = new Room (description.get(333), 334, 0, 332, 0, 0, 0, 0); // Exits: North to 334, West to 332
		rooms[334] = new Room (description.get(334), 335, 333, 330, 0, 0, 0, 27); // Exits: North to 335, South to 333, West to 330. Contains a wounded soldier tending to his comrade (#27)
		rooms[335] = new Room (description.get(335), 0, 334, 331, 0, 0, 0, 0); // Exits: South to 334, West to 331
		
		// The road to the hedge maze
		rooms[336] = new Room (description.get(336), 327, 337, 0, 0, 0, 0, 0); // Exits: North to 327, South to 337
		rooms[337] = new Room (description.get(337), 336, 338, 0, 0, 0, 0, 0); // Exits: North to 336, South to 338
		rooms[338] = new Room (description.get(338), 337, 339, 0, 0, 0, 0, 0); // Exits: North to 337, South to 339
		rooms[339] = new Room (description.get(339), 338, 340, 0, 0, 0, 0, 0); // Exits: North to 338, South to 340
		
		// The hedge maze
		rooms[340] = new Room (description.get(340), 339, 341, 343, 0, 0, 0, 0); // Exits: North to 339, South to 341, West to 343
		rooms[341] = new Room (description.get(341), 340, 0, 342, 0, 0, 0, 0); // Exits: North to 340, West to 342
		rooms[342] = new Room (description.get(342), 0, 0, 0, 341, 0, 0, 0); // Exits: East to 341
		rooms[343] = new Room (description.get(343), 0, 0, 344, 340, 0, 0, 0); // Exits: West to 344, East to 340
		rooms[344] = new Room (description.get(344), 0, 345, 0, 343, 0, 0, 0); // Exits: South to 345, East to 343
		rooms[345] = new Room (description.get(345), 344, 346, 0, 0, 0, 0, 0); // Exits: North to 344, South to 346
		rooms[346] = new Room (description.get(346), 345, 0, 0, 347, 0, 0, 0); // Exits: North to 345, East to 347
		rooms[347] = new Room (description.get(347), 0, 0, 346, 348, 0, 0, 0); // Exits: West to 346, East to 348
		rooms[348] = new Room (description.get(348), 0, 359, 347, 349, 0, 0, 133); // Exits: South to 359, West to 347, East to 349. Contains the minotaur of the maze (#133)
		rooms[349] = new Room (description.get(349), 350, 354, 348, 0, 0, 0, 0); // Exits: North to 350, South to 354, West to 348
		rooms[350] = new Room (description.get(350), 0, 349, 0, 351, 0, 0, 0); // Exits: South to 349, East to 351
		rooms[351] = new Room (description.get(351), 352, 0, 350, 0, 0, 0, 0); // Exits: North to 352, West to 350
		rooms[352] = new Room (description.get(352), 0, 351, 353, 0, 0, 0, 0); // Exits: South to 351, West to 353
		rooms[353] = new Room (description.get(353), 0, 0, 0, 352, 0, 0, 134); // Exits: East to 352. Contains a guard trapped in the maze (#134)
		rooms[354] = new Room (description.get(354), 349, 357, 0, 355, 0, 0, 0); // Exits: North to 349, South to 357, East to 355
		rooms[355] = new Room (description.get(355), 356, 0, 354, 0, 0, 0, 214); // Exits: North to 356, West to 354. Contains a violet mushroom (#214)
		rooms[356] = new Room (description.get(356), 0, 355, 0, 0, 0, 0, 134); // Exits: South to 355. Contains a guard trapped in the maze (#134)
		rooms[357] = new Room (description.get(357), 354, 0, 0, 358, 0, 0, 0); // Exits: North to 354, East to 358
		rooms[358] = new Room (description.get(358), 0, 0, 357, 0, 0, 0, 135); // Exits: West to 357. Contains a horned satyr (#135)
		rooms[359] = new Room (description.get(359), 348, 0, 360, 0, 0, 0, 0); // Exits: North to 348, West to 360
		rooms[360] = new Room (description.get(360), 0, 0, 361, 359, 0, 0, 0); // Exits: West to 361, East to 359
		rooms[361] = new Room (description.get(361), 0, 362, 0, 360, 0, 0, 135); // Exits: South to 362, East to 360. Contains a horned satyr (#135)
		rooms[362] = new Room (description.get(362), 361, 0, 0, 363, 0, 0, 0); // Exits: North to 361, East to 363
		rooms[363] = new Room (description.get(363), 0, 0, 362, 364, 0, 0, 0); // Exits: West to 362, East to 364
		rooms[364] = new Room (description.get(364), 0, 365, 363, 0, 0, 0, 0); // Exits: South to 365, West to 363
		
		// The secret path to the moat
		rooms[365] = new Room (description.get(365), 364, 366, 0, 0, 0, 0, 0); // Exits: North to 364, South to 366
		rooms[366] = new Room (description.get(366), 365, 367, 0, 0, 0, 0, 0); // Exits: North to 365, South to 367
		rooms[367] = new Room (description.get(367), 366, 368, 0, 0, 0, 0, 0); // Exits: North to 366, South to 368
		rooms[368] = new Room (description.get(368), 367, 0, 0, 369, 0, 0, 0); // Exits: North to 367, East to 369
		rooms[369] = new Room (description.get(369), 0, 0, 368, 370, 0, 0, 0); // Exits: West to 368, East to 370
		
		// The moat
		rooms[370] = new Room (description.get(370), 371, 397, 369, 0, 0, 0, 0); // Exits: North 371, South to 397, West to 369
		rooms[371] = new Room (description.get(371), 372, 370, 0, 0, 0, 0, 137); // Exits: North to 372, South to 370. Contains a guard wearing a black mask (#137)
		rooms[372] = new Room (description.get(372), 373, 371, 0, 0, 0, 0, 0); // Exits: North to 373, South to 371
		rooms[373] = new Room (description.get(373), 374, 372, 0, 0, 0, 0, 0); // Exits: North to 374, South to 372
		rooms[374] = new Room (description.get(374), 0, 373, 0, 375, 0, 0, 0); // Exits: South to 373, East to 375
		rooms[375] = new Room (description.get(375), 0, 0, 374, 376, 0, 0, 0); // Exits: West to 374, East to 376
		rooms[376] = new Room (description.get(376), 0, 0, 375, 377, 0, 0, 136); // Exits: West to 375, East to 377. Contains a vampire bat (#136)
		rooms[377] = new Room (description.get(377), 0, 0, 376, 378, 0, 0, 0); // Exits: West to 376, East to 378
		rooms[378] = new Room (description.get(378), 0, 0, 377, 379, 0, 0, 0); // Exits: West to 377, East to 379
		rooms[379] = new Room (description.get(379), 0, 0, 378, 380, 0, 0, 0); // Exits: West to 378, East to 380
		rooms[380] = new Room (description.get(380), 0, 381, 379, 0, 0, 0, 136); // Exits: South to 381, West to 379. Contains a vampire bat (#136)
		rooms[381] = new Room (description.get(381), 380, 382, 0, 0, 0, 0, 0); // Exits: North to 380, South to 382
		rooms[382] = new Room (description.get(382), 381, 383, 0, 0, 0, 0, 137); // Exits: North to 381, South to 383. Contains a guard wearing a black mask (#137)
		rooms[383] = new Room (description.get(383), 382, 384, 0, 0, 0, 0, 0); // Exits: North to 382, South to 384
		rooms[384] = new Room (description.get(384), 383, 385, 0, 0, 0, 0, 0); // Exits: North to 383, South to 385
		rooms[385] = new Room (description.get(385), 384, 386, 0, 0, 0, 0, 0); // Exits: North to 384, South to 386
		rooms[386] = new Room (description.get(386), 385, 387, 398, 0, 0, 0, 0); // Exits: North to 385, South to 387, West to 398
		rooms[387] = new Room (description.get(387), 386, 388, 0, 0, 0, 0, 137); // Exits: North to 386, South to 388. Contains a guard wearing a black mask (#137)
		rooms[388] = new Room (description.get(388), 387, 0, 389, 0, 0, 0, 214); // Exits: North to 387, West to 389. Contains a violet mushroom (#214)
		rooms[389] = new Room (description.get(389), 0, 0, 390, 388, 0, 0, 0); // Exits: West to 390, East to 388
		rooms[390] = new Room (description.get(390), 0, 0, 391, 389, 0, 0, 0); // Exits: West to 391, East to 389
		rooms[391] = new Room (description.get(391), 0, 0, 392, 390, 0, 0, 0); // Exits: West to 392, East to 390
		rooms[392] = new Room (description.get(392), 0, 0, 393, 391, 0, 0, 0); // Exits: West to 393, East to 391
		rooms[393] = new Room (description.get(393), 0, 0, 394, 392, 0, 0, 136); // Exits: West to 394, East to 392. Contains a vampire bat (#136)
		rooms[394] = new Room (description.get(394), 395, 0, 0, 393, 0, 0, 0); // Exits: North to 395, East to 393
		rooms[395] = new Room (description.get(395), 396, 394, 0, 0, 0, 0, 0); // Exits: North to 396, South to 394
		rooms[396] = new Room (description.get(396), 397, 395, 0, 0, 0, 0, 0); // Exits: North to 397, South to 395
		rooms[397] = new Room (description.get(397), 370, 396, 0, 0, 0, 0, 0); // Exits: North to 370, South to 396
		
		// The hidden passage into the castle
		rooms[398] = new Room (description.get(398), 0, 0, 0, 386, 399, 0, 0); // Exits: East to 386, Up to 399
		rooms[399] = new Room (description.get(399), 0, 0, 0, 0, 400, 398, 0); // Exits: Up to 400, Down to 398
		rooms[400] = new Room (description.get(400), 0, 0, 0, 0, 401, 399, 0); // Exits: Up to 401, Down to 399
		
		// Inside the castle
		rooms[401] = new Room (description.get(401), 0, 0, 0, 402, 0, 400, 0); // Exits: East to 402, Down to 400
		rooms[402] = new Room (description.get(402), 403, 404, 401, 405, 0, 0, 0); // Exits: North to 403, South to 404, West to 401, East to 405
		rooms[403] = new Room (description.get(403), 0, 402, 0, 0, 0, 0, 0); // Exits: South to 402
		rooms[404] = new Room (description.get(404), 402, 0, 0, 0, 0, 0, 0); // Exits: North to 402
		rooms[405] = new Room (description.get(405), 0, 0, 402, 406, 0, 0, 0); // Exits: West to 402, East to 406 
		rooms[406] = new Room (description.get(406), 407, 0, 405, 0, 0, 0, 0); // Exits: North to 407, West to 405
		rooms[407] = new Room (description.get(407), 0, 406, 408, 0, 0, 0, 0); // Exits: South to 406, West to 408
		rooms[408] = new Room (description.get(408), 0, 409, 0, 407, 0, 0, 0); // Exits: South to 409, East to 407
		rooms[409] = new Room (description.get(409), 408, 0, 0, 410, 0, 0, 0); // Exits: North to 408, East to 410
		rooms[410] = new Room (description.get(410), 411, 0, 409, 0, 0, 0, 0); // Exits: North to 411, West to 409
		rooms[411] = new Room (description.get(411), 0, 410, 412, 0, 0, 0, 0); // Exits: South to 410, West to 412
		rooms[412] = new Room (description.get(412), 0, 413, 0, 411, 0, 0, 0); // Exits: South to 413, East to 411
		rooms[413] = new Room (description.get(413), 412, 0, 0, 414, 0, 0, 0); // Exits: North to 412, East to 414
		rooms[414] = new Room (description.get(414), 415, 0, 413, 0, 0, 0, 0); // Exits: North to 415, West to 413
		rooms[415] = new Room (description.get(415), 0, 414, 416, 0, 0, 0, 0); // Exits: South to 414, West to 416
		rooms[416] = new Room (description.get(416), 0, 417, 0, 415, 0, 0, 0); // Exits: South to 417, East to 415
		
		// The circular room of Zeramus
		rooms[417] = new Room (description.get(417), 416, 418, 0, 0, 0, 0, 0); // Exits: North to 416, South to 418
		rooms[418] = new Room (description.get(418), 417, 429, 419, 420, 0, 0, 0); // Exits: North to 417, South to 429, West to 419, East to 420
		rooms[419] = new Room (description.get(419), 0, 427, 0, 418, 0, 0, 0); // Exits: South to 427, East to 418
		rooms[420] = new Room (description.get(420), 0, 421, 418, 0, 0, 0, 0); // Exits: South to 421, West to 418
		rooms[421] = new Room (description.get(421), 420, 423, 429, 422, 0, 0, 0); // Exits: North to 420, South to 423, West to 429, East to 422
		rooms[422] = new Room (description.get(422), 0, 0, 421, 0, 0, 0, 0); // Exits: West to 421
		rooms[423] = new Room (description.get(423), 421, 0, 424, 0, 0, 0, 0); // Exits: North to 421, West to 424
		rooms[424] = new Room (description.get(424), 429, 425, 426, 423, 0, 0, 0); // Exits: North to 429, South to 425, West to 426, East to 423
		rooms[425] = new Room (description.get(425), 424, 0, 0, 0, 0, 0, 0); // Exits: North to 424
		rooms[426] = new Room (description.get(426), 427, 0, 0, 424, 0, 0, 0); // Exits: North to 427, East to 424
		rooms[427] = new Room (description.get(427), 419, 426, 428, 429, 0, 0, 0); // Exits: North to 419, South to 426, West to 428, East to 429
		rooms[428] = new Room (description.get(428), 0, 0, 0, 427, 0, 0, 0); // Exits: East to 427
		
		// Zeramus
		rooms[429] = new Room (description.get(429), 418, 424, 427, 421, 0, 0, 138); // Exits: North to 418, South to 424, West to 427, East to 421. Contains Zeramus (#138)
		
		// The tutorial garden
		rooms[430] = new Room (description.get(430), 0, 431, 433, 6, 0, 0, 0); // Exits: South to 431, West to 433, East to 6
		rooms[431] = new Room (description.get(431), 430, 0, 432, 0, 0, 0, 0); // Exits: North to 430, West to 432
		rooms[432] = new Room (description.get(432), 433, 0, 0, 431, 0, 0, 139); // Exits: North to 433, East to 431. Contains a practice doll (#139)
		rooms[433] = new Room (description.get(433), 0, 432, 0, 430, 0, 0, 201); // Exits: South to 432, East to 430. Contains a marble fountain (#201)
		
		//******************************************************************************
		// 
		//									NPC SETUP
		//
		//******************************************************************************
		
		// Sets up the list of NPCs. Change this as the number of NPCs increase
		npcs = new NPC[220];
		
		// Note: NPC conversations are located in the conversation() method further below
		// Note #2: The room objects have different parameters as seen below, but they are in a sense also NPCs
		// Should be solved using inheritance or a completely different design in future projects
		
		npcs[0] = new NPC ("NPC #0 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com and report it-", 0, 0, 0, 0, 0); // Not used
		
		// Unkillable NPCs
		npcs[1] = new NPC ("a butler");
		npcs[2] = new NPC ("a cheerful merchant");
		npcs[3] = new NPC ("a pretty lady");
		npcs[4] = new NPC ("a wandering salesman");
		npcs[5] = new NPC ("a hardworking man");
		npcs[6] = new NPC ("a villager");
		npcs[7] = new NPC ("Grunbald the magician");
		npcs[8] = new NPC ("Skeld the blacksmith");
		npcs[9] = new NPC ("a town guard");
		npcs[10] = new NPC ("a philosopher");
		npcs[11] = new NPC ("an elderly villager");
		npcs[12] = new NPC ("a frightened town guard");
		npcs[13] = new NPC ("Glenn the bard");
		npcs[14] = new NPC ("Lianne");
		npcs[15] = new NPC ("Thorulf");
		npcs[16] = new NPC ("Asta");
		npcs[17] = new NPC ("a hermit");
		npcs[18] = new NPC ("a member of the Zulah tribe");
		npcs[19] = new NPC ("the shaman of the Zulah tribe");
		npcs[20] = new NPC ("a hunter of the Zulah tribe");
		npcs[21] = new NPC ("a cackling mad man");
		npcs[22] = new NPC ("the guardian of the tomb");
		npcs[23] = new NPC ("Ezme the carpet trader");
		npcs[24] = new NPC ("Josephine");
		npcs[25] = new NPC ("Rupert");
		npcs[26] = new NPC ("an old man smoking his pipe");
		npcs[27] = new NPC ("a wounded soldier tending to his comrade");
		npcs[28] = new NPC ("Lieutenant Harken");
		npcs[29] = new NPC ("a desert trader");
		npcs[30] = new NPC ("Harald the innkeeper");
		
		npcs[100] = new NPC ("Satan Golga"); // Test NPC
		
		// Killable NPCs
		// ("<NPC name>", <Strength>, <Max Health>, <Gold>, <Vulnerable>, <Dropped Item>)
		npcs[101] = new NPC ("a black cat", 2, 8, 0, 0, 0); // Used in the quest from the butler. Made invulnerable. In room #1
		npcs[102] = new NPC ("a red fox", 6, 14, 0, 1, 0);
		npcs[103] = new NPC ("a donkey", 5, 16, 0, 1, 0);
		npcs[104] = new NPC ("a tiny bird", 1, 8, 0, 1, 0);
		npcs[105] = new NPC ("a cockroach", 1, 8, 0, 1, 0);
		npcs[106] = new NPC ("an owl", 7, 13, 0, 1, 0);
		npcs[107] = new NPC ("a sleeping bear", 17, 50, 0, 1, 0);
		npcs[108] = new NPC ("a dirty vagrant", 9, 20, 7, 1, 0);
		npcs[109] = new NPC ("a brown cow", 6, 17, 0, 1, 0);
		npcs[110] = new NPC ("a sick-looking wolf", 13, 25, 0, 1, 0);
		npcs[111] = new NPC ("a sobbing little girl ghost", -10, -10, 0, 1, 0); // Awards negative experience. Testing needed.
		npcs[112] = new NPC ("a small goblin", 9, 20, 10, 1, 0);
		npcs[113] = new NPC ("a goblin warrior", 12, 30, 15, 1, 0);
		npcs[114] = new NPC ("a lazy goblin guard", 11, 25, 13, 1, 0);
		npcs[115] = new NPC ("a goblin cook", 10, 20, 13, 1, 0);
		npcs[116] = new NPC ("the Goblin King", 23, 70, 200, 1, 8); // Drops a golden hilt (#8)
		npcs[117] = new NPC ("an almost lifeless prisoner", 0, 10, 0, 1, 0);
		npcs[118] = new NPC ("a street thug", 12, 30, 14, 1, 0);
		npcs[119] = new NPC ("a big rat", 4, 12, 0, 1, 0);
		npcs[120] = new NPC ("a road bandit", 12, 30, 12, 1, 0);
		npcs[121] = new NPC ("a mountain lion", 16, 45, 0, 1, 0);
		npcs[122] = new NPC ("a mountain goat", 5, 15, 0, 1, 0);
		npcs[123] = new NPC ("a marsh horror", 13, 35, 0, 1, 0);
		npcs[124] = new NPC ("a poisonous green frog", 15, 10, 0, 1, 0);
		npcs[125] = new NPC ("a grave robber", 11, 25, 18, 1, 0);
		npcs[126] = new NPC ("Otri the Undying", 26, 80, 300, 1, 9); // Drops a rune-etched blade (#9)
		npcs[127] = new NPC ("a chainmail armored skeleton", 12, 40, 20, 1, 0);
		npcs[128] = new NPC ("a soulless corpse", 8, 40, 10, 1, 0);
		npcs[129] = new NPC ("a marsh serpent", 13, 35, 0, 1, 0);
		npcs[130] = new NPC ("a terrified grave robber", 9, 25, 15, 1, 0);
		npcs[131] = new NPC ("a rattlesnake", 12, 14, 0, 1, 0);
		npcs[132] = new NPC ("a scorpion", 15, 10, 0, 1, 0);
		npcs[133] = new NPC ("the minotaur of the maze", 23, 75, 100, 1, 0);
		npcs[134] = new NPC ("an elite guard trapped in the maze", 16, 50, 40, 1, 0);
		npcs[135] = new NPC ("a horned satyr", 14, 50, 30, 1, 0);
		npcs[136] = new NPC ("a huge vampire bat", 15, 40, 0, 1, 0);
		npcs[137] = new NPC ("an elite guard wearing a black mask", 16, 50, 35, 1, 0);
		npcs[138] = new NPC ("Zeramus", 50, 500, 500, 0, 0); // Even though he is listed here, he is unkillable in normal combat.
		npcs[139] = new NPC ("a wooden practice doll", 0, 5, 0, 1, 0);
		npcs[140] = new NPC ("a stray dog", 6, 14, 0, 1, 0);
		npcs[141] = new NPC ("a hawk", 10, 20, 0, 1, 0);
		npcs[142] = new NPC ("a crazy mountaineer", 11, 25, 10, 1, 0);
		npcs[143] = new NPC ("an evil forest spirit", 8, 25, 0, 1, 0);
		
		// Objects
		// Note: The effect when used is specified in the useObject() method further below
		// Should also add a short description like the Items to display possible object commands to the player
		npcs[201] = new NPC ("a marble fountain");
		npcs[202] = new NPC ("a fortune telling machine");
		npcs[203] = new NPC ("a small chair");
		npcs[204] = new NPC ("a scarecrow");
		npcs[205] = new NPC ("a rotting corpse");
		npcs[206] = new NPC ("a wooden elevator"); // Going down
		npcs[207] = new NPC ("a wooden elevator"); // Going up
		npcs[208] = new NPC ("the frozen corpse of an adventurer");
		npcs[209] = new NPC ("a bonfire"); // in the Zulah tribe
		npcs[210] = new NPC ("a wooden chest covered with grime"); // in the marsh
		npcs[211] = new NPC ("an ancient chest"); // in the tomb
		npcs[212] = new NPC ("a wooden sign"); // the sign in the marsh
		npcs[213] = new NPC ("a black cat"); // the cat that the player picks up for the black cat quest
		npcs[214] = new NPC ("a violet mushroom"); // used for the magic mushroom quest
		
	} // Ends the constructor
	
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
		+ "\n__          __             _       "             
		+ "\n\\ \\        / /            | |      "             
		+ "\n \\ \\  /\\  / /_ _ _ __   __| | ___ _ __ ___ _ __ "
		+ "\n  \\ \\/  \\/ / _` | '_ \\ / _` |/ _ \\ '__/ _ \\ '__|"
		+ "\n   \\  /\\  / (_| | | | | (_| |  __/ | |  __/ |   "
		+ "\n    \\/  \\/ \\__,_|_| |_|\\__,_|\\___|_|  \\___|_|   "
		+ "\n                                                ");
		
		System.out.println("\n\n");
		// Displays welcome message (Add credit for the C class as well)
		System.out.println("\nWelcome to Legend of the Wanderer"
				+ "\n\nCode by: \tRobin Fjärem"
				+ "\nE-mail: \trobin.neko@gmail.com"
				+ "\nWebsite: \thttp://www.robinsuu.com/"
				+ "\n\nMusic by: http://rolemusic.sawsquarenoise.com/"
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
	// Sets up the Array List of Room descriptions and adds all the room
	// descriptions from rooms.txt
	//--------------------------------------------------------------------------
	private void generateRooms() throws IOException
	{
		try
		{ // Start of try
			
	//-----------------------------------------------------------------------------------------------------------	
		Scanner scanFile = new Scanner (new File ("rooms.db")); // Contains the room descriptions, in the root folder of this project
		
		description.add("Room #0 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com  and report it-"); // Adds the room #0 to the ArrayList. This room is not used in the game. Do not remove this line.
		
		// Adds the room descriptions in order from room.txt to the ArrayList description
		while (scanFile.hasNextLine())
		{
			description.add(scanFile.nextLine());
		}
	
		scanFile.close();
	//-----------------------------------------------------------------------------------------------------------		
		
		} // End of try
		
		// Catches exception. (If the rooms.txt does not exist)
		catch (FileNotFoundException exception)
		{
			System.out.println ("rooms.txt file not found! \n\nProgram terminated...");
			System.exit(1);
		}
	}
	
	//--------------------------------------------------------------------------
	// Generates a character
	//--------------------------------------------------------------------------
	private void createCharacter()
	{
		System.out.print ("Please enter your name: ");
		userInput = C.io.nextLine(); // MUST BE C.io.nextLine(); to work in the GUI
		if (userInput.length() > 30)
		{
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
		
		// ADMIN ITEMS. REMOVE AT RELEASE
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
	private void navigate ()
	{
			backgroundSave(); // THIS IS NEEDED TO UPDATE THE GUI IN InfoPanel.java. It is the same as saveGame() except it doesn't display a message
			commandLine();
			
			// The marsh randomizer
			// Far from optimal location, but will remain here until I found a better way to make it
			theMarsh();
			
			switch (userInput)
			{
			// Navigation commands
			//-----------------------------------------------------------------------------------------------------------
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
					System.out.println (rooms[player.getCurrentRoom()].showExits()); // Displays the current room exits
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
	private void displayRoom()
	{
		displayCompass();
		
		System.out.println (rooms[player.getCurrentRoom()]);
		
		player.setCurrentNPC(rooms[player.getCurrentRoom()].getNPC()); // Sets the current NPC to the NPC specified in the current Room
		
		if (player.getCurrentNPC() >= 1)
			System.out.println ("\t" + npcs[player.getCurrentNPC()]);	
	}
	
	//--------------------------------------------------------------------------
	// Displays the compass with the possible exits
	//--------------------------------------------------------------------------
	private void displayCompass()
	{
		String north = "-";
		String south = "-";
		String west = "-";
		String east = "-";
		String up = "-";
		String down = "-";
		
		if (rooms[player.getCurrentRoom()].getNorth() >= 1)
			north = "N";
		
		if (rooms[player.getCurrentRoom()].getSouth() >= 1)
			south = "S";
		
		if (rooms[player.getCurrentRoom()].getWest() >= 1)
			west = "W";
		
		if (rooms[player.getCurrentRoom()].getEast() >= 1)
			east= "E";
		
		if (rooms[player.getCurrentRoom()].getUp() >= 1)
			up = "U";
		
		if (rooms[player.getCurrentRoom()].getDown() >= 1)
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
	private void goNorth()
	{
		if (rooms[player.getCurrentRoom()].getNorth() >= 1)
		{
			player.setCurrentRoom(rooms[player.getCurrentRoom()].getNorth());
			displayRoom();
		}
		else
		{
			System.out.println ("You can't go north.");
			navigate();
		}
	}
	
	//--------------------------------------------------------------------------
	// For walking south and displaying the current room
	// Also sets the current room
	//--------------------------------------------------------------------------
	private void goSouth()
	{
		if (rooms[player.getCurrentRoom()].getSouth() >= 1)
		{
			player.setCurrentRoom(rooms[player.getCurrentRoom()].getSouth());
			displayRoom();
		}
		else
		{
			System.out.println ("You can't go south.");
			navigate();
		}
	}
	
	//--------------------------------------------------------------------------
	// For walking west and displaying the current room
	// Also sets the current room
	//--------------------------------------------------------------------------
	private void goWest()
	{
		if (rooms[player.getCurrentRoom()].getWest() >= 1)
		{
			player.setCurrentRoom(rooms[player.getCurrentRoom()].getWest());
			displayRoom();
		}
		else
		{
			System.out.println ("You can't go west.");
			navigate();
		}
	}
	
	//--------------------------------------------------------------------------
	// For walking east and displaying the current room
	// Also sets the current room
	//--------------------------------------------------------------------------
	private void goEast()
	{
		if (rooms[player.getCurrentRoom()].getEast() >= 1)
		{
			player.setCurrentRoom(rooms[player.getCurrentRoom()].getEast());
			displayRoom();
		}
		else
		{
			System.out.println ("You can't go east.");
			navigate();
		}
	}
	
	//--------------------------------------------------------------------------
	// For walking up and displaying the current room
	// Also sets the current room
	//--------------------------------------------------------------------------
	private void goUp()
	{
		if (rooms[player.getCurrentRoom()].getUp() >= 1)
		{
			player.setCurrentRoom(rooms[player.getCurrentRoom()].getUp());
			displayRoom();
		}
		else
		{
			System.out.println ("You can't go up.");
			navigate();
		}
	}
	
	//--------------------------------------------------------------------------
	// For walking down and displaying the current room
	// Also sets the current room
	//--------------------------------------------------------------------------
	private void goDown()
	{
		if (rooms[player.getCurrentRoom()].getDown() >= 1)
		{
			player.setCurrentRoom(rooms[player.getCurrentRoom()].getDown());
			displayRoom();
		}
		else
		{
			System.out.println ("You can't go down.");
			navigate();
		}
	}
	
	//--------------------------------------------------------------------------
	// For talking to an NPC
	//--------------------------------------------------------------------------
	private void talkTo()
	{
			if (rooms[player.getCurrentRoom()].getNPC() >= 1)
			{
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
	private void conversation()
	{
		switch (player.getCurrentNPC())
		{	
			//******************************
			// Unkillable NPCs
			//******************************
			case 1: // a butler
				if (player.getItem(19).getPlayerHas() == 0 && player.getBlackCatQuest() == 0)
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Have you seen a black cat around? If you find Mr. Whiskers, please bring him to me.'");
				else
					if (player.getItem(19).getPlayerHas() == 1 && player.getBlackCatQuest() == 0)
					{
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Mr. Whiskers! I've been so worried! Don't you ever run away again!'\n");
					
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'We cannot thank you enough. Please accept this as a token of my gratitude.'\n");
						
						player.getItem(19).removePlayerHas(1);
						
						player.addExperience(20);
						player.addGold(30);
						player.getItem(1).addPlayerHas(1);
						System.out.println ("You received 20 experience.");
						System.out.println ("You received 30 gold.");
						System.out.println ("You received a health potion.");
						
						player.levelUp();
						
						rooms[1].addNPC(101);
						
						player.setBlackCatQuest(1);
					}
				else
					if (player.getBlackCatQuest() == 1)
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Oh my! Please don't step on the carpet with those filthy shoes!'");				
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
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Come again.'");
						break;
					case ("1"): // a health potion
						if (player.getGold() >= 15)
						{
							player.removeGold(15);
							player.getItem(1).addPlayerHas(1);
							System.out.println("You pay 15 gold and get a health potion in return.");
							conversation();
						}
						else if (player.getGold() < 15)
							System.out.println ("You don't have enough gold.");
						break;
					case ("2"): // a longsword
						if (player.getItem(11).getPlayerHas() == 0 && player.getGold() >= 120)
						{
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
						else 
							if (player.getItem(11).getPlayerHas() == 1)
								System.out.println ("Why would you want another one?");
							else
								if (player.getGold() < 150)
									System.out.println ("You don't have enough gold.");
						break;
					case ("3"): // a chainmail armor
						if (player.getItem(16).getPlayerHas() == 0 && player.getGold() >= 150)
						{
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
						else
							if (player.getGold() < 150)
								System.out.println ("You don't have enough gold.");
						break;
					case ("4"): // a training manual
						if (player.getGold() >= 200)
						{
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
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Don't you think I look lovely in this dress?'");
				break;
			case 4: // a wandering salesman
				String answerFour = "";
				// if the player doesn't have enough gold, and also doesn't have the gemstone or the Shadowdrinker
				if (player.getGold() < 500 && player.getItem(10).getPlayerHas() == 0 && player.getItem(7).getPlayerHas() == 0)
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'I have a special item for sale, but it looks like you can't afford it.'");
				else
					// if the player has enough gold, and also doesn't have the gemstone or the shadowdrinker
					if (player.getGold() > 500 && player.getItem(10).getPlayerHas() == 0 && player.getItem(7).getPlayerHas() == 0)
					{
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'I stumbled upon this gemstone the other day. It's yours for only 500 gold pieces."
								+ " How about it? (yes/no)'");
						answerFour = C.io.nextLine();
						
					    switch (answerFour)
					    {
					    	case ("yes"):
					    		player.removeGold(500);
					    		System.out.println ("You give 500 gold pieces to the salesman.");
					    		System.out.println (npcs[player.getCurrentNPC()] + " hands you " + player.getItem(10) + ".");
					    		player.getItem(10).addPlayerHas(1);
					    		break;
					    	case ("no"):
					    		System.out.println (npcs[player.getCurrentNPC()] + " says: 'Don't think you can barter with me!'");
					    		break;
					    	default:
					    		System.out.println ("Invalid input.");
					    		break;
					    }
					}
					    else
					    	// if the player has the gemstone or the Shadowdrinker
					    	if (player.getItem(10).getPlayerHas() == 1 || player.getItem(7).getPlayerHas() == 1)
					    		System.out.println (npcs[player.getCurrentNPC()] + " says: 'I'm sorry to say everything is sold out for the moment.'");    		
				break;
			case 5: // a hardworking man
				String answerSeven = "";
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Why don't you give me hand instead of just standing there?' (yes/no)");
				answerSeven = C.io.nextLine();
				
			    switch (answerSeven)
			    {
			    	case ("yes"):
			    		System.out.println ("You help the man with his work. After all, you need to do something about that beer belly you acquired as of lately\n");
			    		System.out.println (npcs[player.getCurrentNPC()] + " says: 'Well thank you! Here is a few gold coins!'");
			    		System.out.println ("\nYou recieved 20 gold.\n");
			    		System.out.println ("\nYou recieved 10 experience.\n");
			    		player.addGold(20);
			    		player.addExperience(10);
			    		System.out.println (npcs[player.getCurrentNPC()] + " says: 'Thanks to you I'll be home for dinner. See you next time!'\n");
			    		System.out.println (npcs[player.getCurrentNPC()] + " leaves with a smile on his face. Perhaps he just escaped a scolding from his wife?");
			    		rooms[player.getCurrentRoom()].removeNPC();
			    		rooms[player.getCurrentRoom()].permanentRemoveNPC();
			    		break;
			    	case ("no"):
			    		System.out.println (npcs[player.getCurrentNPC()] + " says: 'Hmph.. You lazy cityfolk.'");
			    		break;
			    	default:
			    		System.out.println ("Invalid input.");
			    		break;
			    }
				break;
			case 6: // a villager
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'These days nobody dares to leave the village.'");
				break;
			case 7: // Grunbald the magician
				if (player.getMushroomQuest() < 8 && player.getItem(20).getPlayerHas() == 0)
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'If you were to find any violet mushrooms, please take them to me.'");
				else
					if (player.getMushroomQuest() < 8 && player.getItem(20).getPlayerHas() >= 1)
					{
						System.out.println ("You give a violet mushroom to Grunbald.\n");
						
						player.getItem(20).removePlayerHas(1);
						player.addMushroomQuest(1);
						
						player.addExperience(25);
						player.addGold(40);
						System.out.println ("You received 25 experience.");
						System.out.println ("You received 40 gold.\n");
						
						player.levelUp();
						
						if (player.getMushroomQuest() <= 7)
							System.out.println (npcs[player.getCurrentNPC()] + " says: 'Thank you! Now I only need " + (8 - player.getMushroomQuest()) + " more mushrooms!'");
						else
							if (player.getMushroomQuest() == 8)
							{
								System.out.println (npcs[player.getCurrentNPC()] + " says: 'Fantastic work! That's the last one! Now I can finally finish my project.'");
								
								player.addExperience(200);
								System.out.println ("\nYou received 200 experience.\n");
								
								player.levelUp();
							}
					}
					else
						if (player.getMushroomQuest() == 8)
						{
							System.out.println (npcs[player.getCurrentNPC()] + " says: 'I don't need any more mushrooms.'");
						}
				break;
			case 8: // Skeld the blacksmith
				if (player.getItem(8).getPlayerHas() <= 0 || player.getItem(9).getPlayerHas() <= 0 || player.getItem(10).getPlayerHas() <= 0)
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'I'm so tired of repairing armor and making "
							+ "cheap swords for this stupid war. I wish I could go back to the time when I was the King's royal blacksmith.'");
				if (player.getItem(8).getPlayerHas() >= 1 && player.getItem(9).getPlayerHas() >= 1 && player.getItem(10).getPlayerHas() >= 1)
				{
					System.out.println ("You show the blade, hilt, and gemstone to Skeld.");
					System.out.println ("\nSkeld turns silent as he runs his fingers along the cold blade.\n");
					System.out.println (npcs[player.getCurrentNPC()] + " whispers: 'I have never seen the like of this blade before. It's stunningly beautiful and although"
							+ " I can tell it's an ancient relic, it's still sharp. These items hold terrific power. Where did you get your hands on them?'");
					System.out.println ("\nYou explain your situation to Skeld and you can see a small spark light up in his icy blue eyes.\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says with resolve in his voice: 'It would be an honor to complete this weapon for you."
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
				
				switch (randomConv9)
				{
					case 1:
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Keep out of trouble!'");
						break;
					case 2:
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Have you seen that weird magician in town? I bet he's up to something fishy..'");
						break;
					case 3:
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'I once used the fortune machine. It tells the truth I tell you!'");
						break;
					default:
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Keep out of trouble!'");
						break;
				}
				break;
			case 10: // a philosopher
				//System.out.println (npcs[player.getCurrentNPC()] + " says: 'Not all those who wander are lost.'"); // Quote from JRR Tolkien
				
				String answerTen = "";
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Would you like to play a game?' (yes/no)");
				answerTen = C.io.nextLine();
				
				switch (answerTen)
				{
					case ("yes"):
						mathQuiz();
						break;
					case ("no"):
						System.out.println (npcs[player.getCurrentNPC()] + " frowns and returns to what he was doing.");
						break;
					default:
						System.out.println ("Invalid input.");
						break;
				}
				break;
			case 11: // an elderly villager
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I wish myself back to brighter days..'");
				break;
			case 12: // a frightened town guard
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I saw.. things..'");
				break;
			case 13: // Glenn the bard
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Play a tune you say? Such rudeness! I'm in the process of creating a masterpiece for my beloved Asta.'");
				break;
			case 14: // Lianne
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Oh how will I ever get that handsome Glenn to look my way.."
						+ "\nI wish my father wouldn't watch my every move.'");
				break;
			case 15: // Thorulf
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'There is only one thing on my mind right now, and that's keeping that"
						+ "\nslimy bard away from my daughter!'");
				break;
			case 16: // Asta
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Did you meet the bard yet? Isn't he amazing? Much more of a man than my good for nothing husband.'");
				break;
			case 17: // a hermit
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Welcome " + player.getName() + ". How do I know your name you ask? There are many things"
							+ " you do not know about this world.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'I know why you have come here, even if you might not know it yourself yet.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'A terrible shadow has befallen our lands, and you are the one who will find the light"
							+ " and cast the shadow to the abyss where it belongs. You are the one the prophecies have been telling me about.'\n");
					
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'The person who brought the war to our lands is a powerful warlock who goes by the"
							+ " name of Zeramus.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'You must destroy this man to bring peace to the world once again. Needless to say,"
							+ " it will not be an easy task and you will need help.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Other than the brave souls I have foreseen giving you assistance on your quest, there is one"
							+ " thing that stands above them all. None other than the legendary sword 'Shadowdrinker'.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'However. One thousand years ago to the day, the Shadowdrinker was destroyed and the pieces"
							+ " shattered to different parts of our land. You must gather these pieces and assemble the sword to be able to put and end to"
							+ " Zeramus' terror.'\n");
					
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'The first piece, the hilt, is held by the ruler of the goblins to the northwest of Halin"
							+ " village.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'The second piece, the blade, was last known to be held by a long dead lord named Otri,"
							+ " whose domains used to be where the marsh is today.'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'The last piece, the radiant gemstone of Ashaar, has unfortunately been completely"
							+ " lost in the passing of time. I am afraid you are on your own with this one.'\n");
					
					System.out.println (npcs[player.getCurrentNPC()] + " says: '" + player.getName() +  "! Seek out the pieces of the legendary sword, find a blacksmith"
							+ " worthy of recreating it, and destroy Zeramus once and for all!'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Now go, you must make haste!'");
				break;
			case 18: // a member of the Zulah tribe
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Don't enter the marsh! There are.. things.. out there.'");
				break;
			case 19: // the shaman of the Zulah tribe (Heals the player to maximum health)
				if (player.getHealth() == player.getMaxHealth())
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Visit me if you are in need of healing.'");
				else
					if (player.getHealth() < player.getMaxHealth())
					{
						System.out.println (npcs[player.getCurrentNPC()] + " starts to mumble some words...'");
						
						heal ( (player.getMaxHealth()) - (player.getHealth()) );
						System.out.println ("\nYou have been healed.");
					}
				break;
			case 20: // a hunter of the Zulah tribe (Will always give out a health potion if the player doesn't have one)
				if (player.getItem(1).getPlayerHas() < 1) 
				{
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Here, take this. You will need it.'");
					player.getItem(1).addPlayerHas(1); // Adds a health potion
					System.out.println ("\nYou received a health potion.");
				}
				else
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Be careful out there. You can never be sure about what is lurking in the mists.'");
				break;
			case 21: // a cackling mad man
				System.out.println (npcs[player.getCurrentNPC()] + " seems to be completely out of his mind. He keeps babbling about some treasure in the graveyard.");
				break;
			case 22: // the guardian of the tomb (Used to open the door into the tomb)
				if (rooms[player.getCurrentRoom()].getWest() == 0)
				{
					String answerTwentyOne = "";
				
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Those who wish to enter the tomb must speak out the password.'");
					System.out.println ("\nWhat do you wish to say? (Type now)");
					answerTwentyOne = C.io.nextLine();
				
					switch (answerTwentyOne)
					{
						case ("mother"):
						case ("Mother"):
							System.out.println (npcs[player.getCurrentNPC()] + " says: 'That is correct. You may now enter the tomb.'");
							System.out.println ("\nYou can hear a loud noise as the door starts moving aside, allowing you to go inside the tomb.");
							rooms[player.getCurrentRoom()].setWest(298);
							break;
						default:
							System.out.println (npcs[player.getCurrentNPC()] + " says: 'That is the wrong password. Begone!'");
							break;
					}
				}
				else
					if (rooms[player.getCurrentRoom()].getWest() == 298)
						System.out.println (npcs[player.getCurrentNPC()] + " is silent.");
				break;
			case 23: // Ezme the carpet trader
				String answerTwentyThree = "";
				
				// If the player doesn't have the carpet or the camel
				if (player.getItem(12).getPlayerHas() == 0 && player.getItem(13).getPlayerHas() == 0)
				{
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'You are looking to cross the desert you say? There is no way you will make the trip without one of my very special flying carpets!'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'I will also make a very special price only for you my friend! Only 5000 gold pieces! How about it?' (yes/no)");
					answerTwentyThree = C.io.nextLine();
					
					switch (answerTwentyThree)
					{
						case ("yes"):
							if (player.getGold() >= 5000)
							{
								System.out.println (npcs[player.getCurrentNPC()] + "'s eyes widen as you show him the gold pieces.");
								System.out.println ("You close the sack of gold after giving him a glimpse. Like you'd ever pay that ridiculous price for an old rug!");
								System.out.println (npcs[player.getCurrentNPC()] + " starts crying.");
							}
							else
								if (player.getGold() <= 5000)
								{
									System.out.println (npcs[player.getCurrentNPC()] + " says: 'Don't try to fool me!'");
								}
							break;
						case ("no"):
							System.out.println (npcs[player.getCurrentNPC()] + " says: 'Well it was worth a try.. I suppose if you really want the carpet you could just bring me the "
									+ "final piece for my outstanding limited edition camel figurine collection. Find number 26 and I will give you the carpet.'");
					}	
				}
				
				// if the player doesn't have the carpet but does have the camel
				if (player.getItem(12).getPlayerHas() == 0 && player.getItem(13).getPlayerHas() == 1)
				{
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'If you want to cross the desert you will need one of my flying carpets. It's 5000 go.. Wait! "
							+ "What's THAT sticking out of your backpack?!'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " looks almost obsessed as his hand stretches out for the silly camel figurine in your backpack.\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Give it to me!'\n");
					System.out.println ("You look at him with a puzzled expression and hand the camel over.\n");
					
					player.getItem(13).removePlayerHas(1);
					
					System.out.println (npcs[player.getCurrentNPC()] + "'s eyes fill up with tears of joy as he starts dancing around the room with the camel held high in his hands.\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says excitedly: 'The carpets are over there! Grab any one of them! Here, have some gold too!'\n");
					
					player.addGold(50);
					System.out.println ("You received 50 gold.");
					
					player.getItem(12).addPlayerHas(1);
					System.out.println ("You take a flying carpet.\n");
					
					System.out.println ("Ezme is still dancing around. You'd better leave him to it before he changes his mind.");	
				}
				else
					if (player.getItem(12).getPlayerHas() == 1)
						System.out.println ("Ezme is still dancing around. You'd better leave him to it before he changes his mind.");
				break;
			case 24: // Josephine
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I'm so tired of being holed up in this place. I wish the war would end soon. Why are they fighting anyway?'");
				break;
			case 25: // Rupert
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I don't believe in all the nonsense people speak about. A black magician in the desert? "
						+ "Monsters? Hah! Those are all bedtime stories for children.'");
				break;
			case 26: // an old man smoking his pipe
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I am afraid by the time the young ones realize what is about to happen it will be too late."
						+ " There are dark clouds coming from the desert.'");
				break;
			case 27: // a wounded soldier tending to his comrade
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'All I've got left in life is this young boy, and we just met yesterday. A few hours later our group were ambushed by raiders."
						+ " Yes. We are the only two survivors. I don't know what to do but keep trying to heal his wounds.'\n");
				
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Everything feels hopeless.'");
				break;
			case 28: // Lieutenant Harken
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'You there soldier! Why aren't you on the front line with the others?'");
				
				System.out.println("\nYou explain your situation to him.\n");
				
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Well if it's of any help, my scouts found out that there is some lunatic man "
						+ "claiming to be a wizard holed up in the castle to the south. Wouldn't want to go there myself though.'\n");
				
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'If you follow the road south here you will get to the castle's hedge maze, it should be your "
						+ "best shot at getting there undetected. Stay alert though. I heard.. Stories.. About that place.'");
				break;
			case 29: // a desert trader
				String answerTwentyNine = "";
				
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Please have a look at my wares.'");
				
				System.out.println ("\n\tItems for sale:\n");
				System.out.println ("\t1: a health potion\t\t 15 (You have: " + player.getItem(1).getPlayerHas() + ")");
				System.out.println ("\t2: a greather health potion\t 30 (You have: " + player.getItem(21).getPlayerHas() + ")");
				
				System.out.println ("\nGold: " + player.getGold());
				
				System.out.println ("\nWhat would you like to buy?\n");
						System.out.print("Choose: (0 for nothing) ");
				answerTwentyNine = C.io.nextLine();
				
				switch (answerTwentyNine)
				{
					case ("0"):
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Come again.'");
						break;
					case ("1"): // a health potion
						if (player.getGold() >= 15)
						{
							player.removeGold(15);
							player.getItem(1).addPlayerHas(1);
							System.out.println("You pay 15 gold and get a health potion in return.");
							conversation();
						}
						else if (player.getGold() < 15)
							System.out.println ("You don't have enough gold.");
						break;
					case ("2"): // a greater health potion
						if (player.getGold() >= 30)
						{
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
				if (player.getRespawnLocation() == 1)
				{
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Welcome to my humble inn! Please have a rest.'");
					player.setHealth(player.getMaxHealth());
					player.setRespawnLocation(166);
					System.out.println ("\nYou rest at the inn.\n");
					System.out.println ("You will now respawn at the inn if you die.");
				}
				else
				{
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Did you meet the strange salesman up the north road yet? I wonder what he's up to..'\n");
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'Please have a rest.'");
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
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Meoooooow!'");
				break;
			case 102: // a red fox
				System.out.println (npcs[player.getCurrentNPC()] + " growls at you");
				break;
			case 103: // a donkey
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Eeeeee Awwww...!'");
				break;
			case 104: // a tiny bird
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 105: // a cockroach
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 106: // an owl
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 107: // a sleeping bear
				System.out.println (npcs[player.getCurrentNPC()] + " grunts and falls back to sleep.");
				break;
			case 108: // a dirty vagrant
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'What are you doing here? This is my place! Get out!'");
				break;
			case 109: // a happy cow
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Mooooooooo!'");
				break;
			case 110: // a sick-looking wolf
				System.out.println (npcs[player.getCurrentNPC()] + " stares hungrily at you.");
				break;
			case 111: // a sobbing little girl ghost
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Stop looking at me! I just want to be left alone..'" + npcs[player.getCurrentNPC()] + " turns around and starts sobbing again.");
				break;
			case 112: // a small goblin
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'You shouldn't be here! Guards!!!'");
				break;
			case 113: // a goblin warrior
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I hope you enjoy the feeling of a rusty blade piercing your body!'");
				break;
			case 114: // a lazy goblin guard
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'They told me there wouldn't be intruders when I got this job!'");
				break;
			case 115: // a goblin cook
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Oh, the main dish has arrived! Prepare the big pot!'");
				break;
			case 116: // The Goblin King
				System.out.println (npcs[player.getCurrentNPC()] + " grins at you, baring his gruesome teeth. 'You are DEAD!'");
				break;
			case 117: // an almost lifeless prisoner
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Please.. End me..'");
				break;
			case 118: // a street thug
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Empty your pockets!'");
				break;
			case 119: // a big rat
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 120: // a road bandit
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Hand it all over before I slit your throat!'");
				break;
			case 121: // a mountain lion
				System.out.println (npcs[player.getCurrentNPC()] + " growls at you.");
				break;
			case 122: // a mountain goat
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 123: // a marsh horror
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 124: // a poisonous green frog
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 125: // a grave robber
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'I found this place first! You'd better leave, or an \"accident\" might just happen.'");
				break;
			case 126: // Otri the Undying
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'You are a brave one. Now, join me in the afterlife!'");
				break;
			case 127: // a chainmail armored skeleton
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 128: // a soulless corpse
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 129: // a marsh serpent
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 130: // a terrified grave robber
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Get me out of here! This isn't how it was supposed to be..'");
				break;
			case 131: // a rattlesnake
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 132: // a scorption
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 133: // the minotaur of the maze
				System.out.println (npcs[player.getCurrentNPC()] + " stares hatefully at you with his red eyes. There is smoke coming out of his nostrils. He lifts his huge axe..");
				break;
			case 134: // an elite guard trapped in the maze
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'If you don't show me the way out I will kill you right now!'");
				break;
			case 135: // a horned satyr
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
			case 136: // a huge vampire bat
				System.out.println (npcs[player.getCurrentNPC()] + " swoops down and aims for your neck with its fangs.");
				break;
			case 137: // an elite guard wearing a black mask
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'You little worm! A lifetime of pain awaits you in our torture chambers!'");
				break;
			case 138: // Zeramus
				if (player.getItem(7).getPlayerHas() == 0)
				{
					System.out.println (npcs[player.getCurrentNPC()] + " says: 'You little ant! I will crush you beneath my foot!'\n");
				
					System.out.println (npcs[player.getCurrentNPC()] + " starts doing elaborate movements with his arms and hands as he speaks the words of an ancient toungue.\n");
				
					System.out.println ("White hot fire starts to erupt from his fingers, and he binds it into a large ball of energy before he casts it towards you.\n");
					
					System.out.println ("You are completely engulfed in the fire, and die slowly as you roll around the floor, trying to make the fire go out.\n");
					
					gameOver();
				}
				else
					if (player.getItem(7).getPlayerHas() == 1)
					{
						System.out.println (npcs[player.getCurrentNPC()] + " turns around slowly, facing you. His face is a pale white with deep wrinkles. If it wasn't for his eyes, one would "
								+ "take him for just about any old man. Where a normal person would have white in their eyes, his is a pure black, not much different than the "
								+ "black polished floor. As you feel his gaze upon you, you feel like you are drowning in a bottomless pool of pure hatred.\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'You have a lot of nerve coming here. Though, I was alerted by your presence hours ago, I decided "
								+ "to let you pass. You see, I'm a very curious man.'\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " throws back his head with a powerful laughter, seemingly making the room shake.\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'You see this thing here? It's called the Pyrestone. And once I learn how it works I will not need "
								+ "my army anymore. It will allow me to shape the lands by my own will, to awaken the underground volcanos, and to unleash true "
								+ "hell on earth!'\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " once again focus his eyes of bottomless darkness upon you.\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'Now, it is time for you to leave this world.'\n");
						
						System.out.println ("You draw the Shadowdrinker, readying yourself for battle.\n");
						
						System.out.println ("Press enter to continue..");
						C.io.nextLine();
						
						System.out.println (npcs[player.getCurrentNPC()] + " starts doing elaborate movements with his arms and hands as he speaks the words of an ancient toungue.\n");
						
						System.out.println ("White hot fire starts to erupt from his fingers, and he binds it into a large ball of energy before he casts it towards you.\n");
						
						System.out.println ("You suddenly feel the Shadowdrinker turning icy cold in your hands as you brace yourself for the impact.\n");
						
						System.out.println ("The white ball of energy suddenly bounces away, as if it hit an invisible wall. It explodes with a roar a few metres away.\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " says: 'What is this?! No, no..! It was supposed to be destroyed!'\n");
						
						System.out.println (npcs[player.getCurrentNPC()] + " starts to cower as you charge towards him, decapitating his head with one swift move.\n");
						
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
						
						rooms[player.getCurrentRoom()].removeNPC();
						rooms[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
						
						// Plays the ending music
						//startPlayer(ending); // Disabled until I figured out a way to hide the output from BasicPlayer
					}
				break;
			case 142: // a crazy mountaineer
				System.out.println (npcs[player.getCurrentNPC()] + " says: 'Watch for icy spots!'");
				break;
				
			//******************************
			// Default
			//******************************
			default:
				System.out.println ("You can't talk to " + npcs[player.getCurrentNPC()]);
				break;
		}
	}
	
	//--------------------------------------------------------------------------
	// Method for using an object (NPC)
	//--------------------------------------------------------------------------
	private void useObject()
	{	
		switch (player.getCurrentNPC())
		{
			//--------------------------------------------------------------------------
			// #201 a marble fountain
			//--------------------------------------------------------------------------
			case (201):
				System.out.println ("You drink from " + npcs[player.getCurrentNPC()]);
				heal(5);
				break;
			//--------------------------------------------------------------------------
			// #202 a fortune telling machine
			//--------------------------------------------------------------------------
			case (202):
				String answer = "no";
					
				System.out.println ("Do you wish to use the machine for 10 gold? (yes/no)");
				answer = C.io.nextLine();
					
				switch (answer)
				{
					case ("yes"):
						if (player.getGold() >= 10)
						{
							System.out.println ("You pay 10 gold.");
							player.removeGold(10);
										
							int chance = 0;
						
							chance = rand.nextInt(8) +1;
						
							switch (chance)
							{
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
						else
							if (player.getGold() < 10)
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
				rooms[player.getCurrentRoom()].removeNPC();
				rooms[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
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
				rooms[player.getCurrentRoom()].removeNPC();
				rooms[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
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
				rooms[player.getCurrentRoom()].removeNPC();
				rooms[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
				break;
			//--------------------------------------------------------------------------
			// #211 an ancient chest
			//--------------------------------------------------------------------------
			case (211):
				if (player.getItem(9).getPlayerHas() >= 1)
				{
					System.out.println ("As you open the lid, you see a small camel figurine carved in obsidian. You can see the numbers"
							+ " 26/30 etched under its right foot.");
					System.out.println ("\nYou received an obsidian camel.\n");
					player.getItem(13).addPlayerHas(1);
					rooms[player.getCurrentRoom()].removeNPC();
					rooms[player.getCurrentRoom()].permanentRemoveNPC(); // Removes this NPC permanently
				}
				else
					if (player.getItem(9).getPlayerHas() == 0)
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
				rooms[player.getCurrentRoom()].removeNPC();
				rooms[player.getCurrentRoom()].permanentRemoveNPC();
				player.getItem(19).addPlayerHas(1);
				break;
			//--------------------------------------------------------------------------
			// #214 a violet mushroom
			//--------------------------------------------------------------------------
			case (214):
				System.out.println ("\nYou pick a violet mushroom.\n");
				rooms[player.getCurrentRoom()].removeNPC();
				rooms[player.getCurrentRoom()].permanentRemoveNPC();
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
		
		switch (usedItem)
		{
			//******************************
			// #1 a health potion (Heals 20 points)
			//******************************
			case (" potion"):
				if (player.getItem(1).getPlayerHas() >= 1)
				{
					if (player.getHealth() >= player.getMaxHealth())
						System.out.println ("You are already at max health!");
					else
					{
						System.out.println ("You used " + player.getItem(1));
						player.getItem(1).removePlayerHas(1); // Removes the item from the inventory
						heal(20);
					}
				}
				else
					if (player.getItem(1).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #2 a newbie guide book
			//******************************
			case (" guide"):
				if (player.getItem(2).getPlayerHas() >= 1)
				{
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
				else
					if (player.getItem(2).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #3 a map of the forest
			//******************************
			case (" forest map"):
				if (player.getItem(3).getPlayerHas() >= 1)
				{
					System.out.println ("You look at " + player.getItem(3));
				}
				else
					if (player.getItem(3).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;	
			//******************************
			// #4 a strength potion
			//******************************
			case (" strength potion"):
				if (player.getItem(4).getPlayerHas() >= 1)
				{
					System.out.println ("You used " + player.getItem(4));
					player.getItem(4).removePlayerHas(1); // Removes the item from the inventory
				}
				else
					if (player.getItem(4).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #5 a teleporter
			// Note: This is an admin item
			//******************************
			case (" teleporter"):
				if (player.getItem(5).getPlayerHas() >= 1)
				{
					System.out.println ("Please enter the room you wish to teleport to: "); // Only use an integer value or the game will crash
					player.setCurrentRoom(C.io.nextInt());
					System.out.println ("Whish whosh! The teleporter does its magic and you appear in a different place!\n");
					displayRoom();
				}
				else
					if (player.getItem(5).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #6 a small note
			//******************************
			case (" note"):
				if (player.getItem(6).getPlayerHas() >= 1)
				{
					System.out.println ("You can just make out one word on the note:\n\n'Mother'\n");
				}
				else
					if (player.getItem(6).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #7 the Shadowdrinker
			//******************************
			case (" shadowdrinker"):
				if (player.getItem(7).getPlayerHas() == 1)
				{
					System.out.println ("You are already wielding it.");
				}
				else
					if (player.getItem(7).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #8 a golden hilt
			//******************************
			case (" hilt"):
				if (player.getItem(8).getPlayerHas() == 1)
				{
					System.out.println ("It's useless in its current state.");
				}
				else
					if (player.getItem(8).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #9 a rune-etched blade
			//******************************
			case (" blade"):
				if (player.getItem(9).getPlayerHas() == 1)
				{
					System.out.println ("It's useless in its current state.");
				}
				else
					if (player.getItem(9).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #10 a beautifully cut red gemstone
			//******************************
			case (" gemstone"):
				if (player.getItem(10).getPlayerHas() == 1)
				{
					System.out.println ("It's useless in its current state.");
				}
				else
					if (player.getItem(10).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #11 a longsword
			//******************************
			case (" longsword"):
				if (player.getItem(11).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() <= 0)
				{
					System.out.println ("You are already wielding it.");
				}
				else
					if (player.getItem(11).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() == 1)
						System.out.println ("Why don't you use the Shadowdrinker instead?");
					else
						if (player.getItem(11).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");	
						
				break;
			//******************************
			// #12 a flying carpet
			//******************************
			case (" carpet"):
				if (player.getItem(12).getPlayerHas() == 1)
				{
					String answerCarpet = "";
					
					System.out.println ("You roll out the flying carpet and step on it. It starts to hover a bit up in the air.\n");
					System.out.println ("Where do you want to go?\n");
					
					System.out.println ("1: The forest");
					System.out.println ("2: Halin village");
					System.out.println ("3: The Zulah tribe");
					System.out.println ("4: The carpet trader's tent");
					System.out.println ("5: Across the desert");
					
					answerCarpet = C.io.nextLine();
					
					switch (answerCarpet)
					{
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
				else
					if (player.getItem(12).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #13 an obsidian camel
			//******************************
			case (" camel"):
				if (player.getItem(13).getPlayerHas() == 1)
				{
					System.out.println ("It's useless.");
				}
				else
					if (player.getItem(13).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #14 a training manual
			//******************************
			case (" manual"):
				if (player.getItem(14).getPlayerHas() >= 1)
				{
					int expIncrease = 0;
					
					expIncrease = rand.nextInt((200 - 50) + 1) + 50; // Randomizes a number from 257 to 271
					
					System.out.println ("\nYou study a training manual intensely for a while.\n");
					System.out.println ("Your experience increased by " + expIncrease + ".\n");
					player.getItem(14).removePlayerHas(1);
					player.addExperience(expIncrease);
					player.levelUp();
				}
				else
					if (player.getItem(14).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #15 a shortsword
			//******************************
			case (" shortsword"):
				if (player.getItem(15).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() <= 0)
				{
					System.out.println ("You are already wielding it.");
				}
				else
					if (player.getItem(15).getPlayerHas() == 1 && player.getItem(7).getPlayerHas() == 1)
						System.out.println ("Why don't you use the Shadowdrinker instead?");
					else
						if (player.getItem(15).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");	
						
				break;
			//******************************
			// #16 a chainmail armor
			//******************************
			case (" chainmail"):
				if (player.getItem(16).getPlayerHas() == 1)
				{
					System.out.println ("You are already wearing it.");
				}
				else
					if (player.getItem(16).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #17 a leather armor
			//******************************
			case (" leather"):
				if (player.getItem(17).getPlayerHas() == 1)
				{
					System.out.println ("You are already wearing it.");
				}
				else
					if (player.getItem(17).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #18 a red crystal pendant
			//******************************
			case (" pendant"):
				if (player.getItem(18).getPlayerHas() == 1)
				{
					System.out.println ("You are already wearing it. You feel more powerful than before.");
				}
				else
					if (player.getItem(18).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #19 a black cat
			//******************************
			case (" cat"):
				if (player.getItem(19).getPlayerHas() == 1)
				{
					System.out.println ("The cat meows at you. It looks oddly comfortable in your bag.");
				}
				else
					if (player.getItem(19).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;
			//******************************
			// #20 a violet mushroom
			//******************************
			case (" mushroom"):
				if (player.getItem(20).getPlayerHas() >= 1)
				{
					System.out.println ("Why would you want to use that?");
				}
				else
					if (player.getItem(20).getPlayerHas() == 0)
						System.out.println ("You don't have that item.");		
				break;	
			//******************************
			// #21 a greater health potion (Heals 45 points)
			//******************************
			case (" greater"):
				if (player.getItem(21).getPlayerHas() >= 1)
				{
					if (player.getHealth() >= player.getMaxHealth())
						System.out.println ("You are already at max health!");
					else
					{
						System.out.println ("You used " + player.getItem(21));
						player.getItem(21).removePlayerHas(1); // Removes the item from the inventory
						heal(45);
					}
				}
				else
					if (player.getItem(21).getPlayerHas() == 0)
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
	private void attack()
	{		
			if (rooms[player.getCurrentRoom()].getNPC() >= 1 && npcs[player.getCurrentNPC()].getVulnerable() >= 1) // If an NPC exists and is vulnerable
				startCombat(); // Initiates a combat
			else
				if (rooms[player.getCurrentRoom()].getNPC() >= 1 && npcs[player.getCurrentNPC()].getVulnerable() <= 0) // If an NPC exists but is invulnerable
					System.out.println ("You can't attack that.");
				else
					if (rooms[player.getCurrentRoom()].getNPC() <= 0) // If no NPC exists
						System.out.println ("There is nothing to attack.");
	}
	
	//--------------------------------------------------------------------------
	// Method for quitting the game
	//--------------------------------------------------------------------------
	private void quitGame()
	{
		System.out.print ("Exit the game? (y/n)");
		userInput = C.io.nextLine(); // MUST BE C.io.nextLine(); to work in the GUI. Original is scan.nextLine();
		
		switch (userInput)
		{
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
	
	//--------------------------------------------------------------------------
	// Method for game over
	//--------------------------------------------------------------------------
	private void gameOver()
	{
		player.setHealth(1);
		
		// Removing experience
		if (player.getLevel() > 1 && player.getLevel() <= 3)
			player.removeExperience(20);
		else
			if (player.getLevel() > 3 && player.getLevel() <= 6)
				player.removeExperience(50);
			else
				if (player.getLevel() > 6)
					player.removeExperience(100);
		
		// Removing gold
		if (player.getGold() > 0 && player.getGold() <= 100)
			player.removeGold(player.getGold() / 2);
		else
			if (player.getGold() > 100 && player.getGold() <= 200)
				player.removeGold(player.getGold() / 3);
			else
				if (player.getGold() > 200 && player.getGold() <= 300)
					player.removeGold(player.getGold() / 4);
				else
					if (player.getGold() > 300)
						player.removeGold(player.getGold() / 5);
		
		player.setCurrentRoom(player.getRespawnLocation());
		
		System.out.println ("\nYou have died!\n");
		System.out.println ("You lost some experience and gold.");
		System.out.println ("Press enter to continue..");
		C.io.nextLine();
		
		displayRoom();
	}
	
	//--------------------------------------------------------------------------
	// Checks if how strong the monster is relative to yourself
	//--------------------------------------------------------------------------
	private void consider()
	{
		if (npcs[player.getCurrentNPC()].getVulnerable() > 0)
			powerDifference();
		else
			System.out.println ("You can't consider that.");
	}
	
	//--------------------------------------------------------------------------
	// Power difference (Compare the monster's power to your own)
	// Could most likely be improved with better algorithms
	//--------------------------------------------------------------------------
	private void powerDifference()
	{
		int powerDifference = 0;
		
		powerDifference = ( (player.getPowerLevel()) - (npcs[player.getCurrentNPC()].getPowerLevel()) );
		
		if (powerDifference < -20)
			System.out.println (npcs[player.getCurrentNPC()] + " looks very powerful compared to you. Be careful.");
		else
			if (powerDifference < -10)
				System.out.println (npcs[player.getCurrentNPC()] + " looks much stronger than you.");
			else
				if (powerDifference < 0)
					System.out.println (npcs[player.getCurrentNPC()] + " looks stronger than you.");
				else
					if (powerDifference == 0)
						System.out.println (npcs[player.getCurrentNPC()] + " looks about as strong as yourself.");
					else
						if (powerDifference >= 0 && powerDifference <= 10)
							System.out.println (npcs[player.getCurrentNPC()] + " looks like an even match.");
						else
							if (powerDifference >= 10)
								System.out.println (npcs[player.getCurrentNPC()] + " looks weaker than you.");
	}
	//--------------------------------------------------------------------------
	// Starts (And eventually ends) a combat with an NPC
	//--------------------------------------------------------------------------
	private void startCombat()
	{	
			if (player.getHealth() > 0 && npcs[player.getCurrentNPC()].getHealth() > 0)
			{
				combatSequence();
			}
				if (npcs[player.getCurrentNPC()].getHealth() <= 0)
				{
					System.out.println ("You killed " + npcs[player.getCurrentNPC()].getName());
					rewards(); // Rewards for killing the monster
					rooms[player.getCurrentRoom()].removeNPC(); // Removes the NPC when killed
					npcs[player.getCurrentNPC()].setHealth(npcs[player.getCurrentNPC()].getMaxHealth()); // Resets the NPC to max health after it being killed
				}
					else
						if (player.getHealth() <= 0)
						{
							System.out.println ("You were killed by " + npcs[player.getCurrentNPC()].getName());
							npcs[player.getCurrentNPC()].setHealth(npcs[player.getCurrentNPC()].getMaxHealth()); // Resets the NPC health if you die
							gameOver();
						}
	}	
	
	//--------------------------------------------------------------------------
	// Starts a respawn timer for the NPCs (Probably needs to be optimized for
	// saving memory). Note that this timer is currently global, running
	// continuously, and never canceled
	//--------------------------------------------------------------------------
	private void respawnTimer()
	{
		int seconds = 300; // The rate at which monsters respawn (in seconds)
		
		Timer timer = new Timer();
		
		TimerTask task = new java.util.TimerTask() 
		{
			   public void run() 
			   {
					int index = 1;
					while (index < rooms.length)
					{
						rooms[index].respawnNPC();
						index++;
					}
			   }
		 };
		 timer.scheduleAtFixedRate(task, 0, seconds*1000);
	}
	
	//--------------------------------------------------------------------------
	// Method for the actual combat sequence
	//--------------------------------------------------------------------------
	private void combatSequence()
	{
		int playerHP = player.getHealth();
		int enemyHP = npcs[player.getCurrentNPC()].getHealth();
		int playerHit = 0;
		int enemyHit = 0;
		
		System.out.println ("You attacked " + npcs[player.getCurrentNPC()]);
		
		while (playerHP > 0 && enemyHP > 0)
		{
			// Player round
			playerHit = rand.nextInt(player.getStrength() +1); // Calculates the player damage made
			enemyHP -= playerHit;
			npcs[player.getCurrentNPC()].setHealth(enemyHP); // Sets the enemy HP after the hit
			if (playerHit == 0)
				System.out.println ("You missed " + npcs[player.getCurrentNPC()].getName());
			else
				System.out.println ("You hit " + npcs[player.getCurrentNPC()].getName() + " for " + playerHit + " damage" + displayEnemyHealth());
			
			// Enemy round
			if (enemyHP > 0)
			{
				enemyHit = rand.nextInt(npcs[player.getCurrentNPC()].getStrength() +1); // Calculates the enemy damage made
				if (enemyHit == 0)
					System.out.println (npcs[player.getCurrentNPC()].getName() + " missed you");
				else
					System.out.println (npcs[player.getCurrentNPC()].getName() + " hit you for " + enemyHit + " damage");
				playerHP -= enemyHit;
				player.setHealth(playerHP); // Sets the player HP after the hit	
			}
		}
	}
	
	//--------------------------------------------------------------------------
	// Displays monster health
	//--------------------------------------------------------------------------
	private String displayEnemyHealth()
	{
		int enemyHP = npcs[player.getCurrentNPC()].getHealth();
		String display = "";
		
		if (enemyHP > 0)
			display = (" (" + npcs[player.getCurrentNPC()] + " has " + enemyHP + " hp left)");
		
		return display;
	}
	
	//--------------------------------------------------------------------------
	// Adds experience and gold to the character when a monster is killed
	// Also runs the methods for item drops and level up
	//--------------------------------------------------------------------------
	private void rewards()
	{
		player.addExperience(npcs[player.getCurrentNPC()].getExperience());
		player.addGold(npcs[player.getCurrentNPC()].getGold());
		System.out.println ("You received " + npcs[player.getCurrentNPC()].getExperience() + " experience points and " + npcs[player.getCurrentNPC()].getGold() + " gold");
		
		dropItem();
		player.levelUp();
	}
	
	//--------------------------------------------------------------------------
	// Checks if the monster drops and item, and if so, gives the item
	// For now the NPCs can only drop an item once, since this feature is only
	// used for quest items
	//--------------------------------------------------------------------------
	private void dropItem()
	{
		if (npcs[player.getCurrentNPC()].getDroppedItem() >= 1)
		{
		player.getItem(npcs[player.getCurrentNPC()].getDroppedItem()).addPlayerHas(1);
		System.out.println ("You received " + player.getItem(npcs[player.getCurrentNPC()].getDroppedItem()));
		npcs[player.getCurrentNPC()].setDroppedItem(0); // Makes sure the NPC can only drop an item ONCE
		}
	}
	
	//--------------------------------------------------------------------------
	// The method for getting lost in the marsh
	// Needs to be better optimized in the future. Right now it's run every time
	// the player moves
	//--------------------------------------------------------------------------
	private void theMarsh()
	{
		int randomRoom = 0; // Used for the marsh 
		
		for (int i=258; i<271; i++)
		{
			randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
			rooms[i].setNorth(randomRoom);
		}
		
		for (int i=258; i<271; i++)
		{
			randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
			rooms[i].setSouth(randomRoom);
		}
		
		for (int i=258; i<271; i++)
		{
			randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
			rooms[i].setWest(randomRoom);
		}
		
		for (int i=258; i<271; i++)
		{
			randomRoom = rand.nextInt((271 - 257) + 1) + 257; // Randomizes a number from 257 to 271
			rooms[i].setEast(randomRoom);
		}
	}
	
	//--------------------------------------------------------------------------
	// Heals the player for the value specified
	//--------------------------------------------------------------------------
	private void heal (int healAmount)
	{
		int healing = 0; 
		int counter = 0;
		
		healing = healAmount;
		
		while (counter < healing)
		{
			if (player.getHealth() < player.getMaxHealth())
				player.addHealth(1);
			counter++;
		}	
	}
	
	//--------------------------------------------------------------------------
	// Display the game credits
	//--------------------------------------------------------------------------
	private void credit()
	{
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
	private void saveGame()
	{
		System.out.println ("Saved the game.");
		saveNPC(); // Workaround
		saveItem();
		player.saveInventorySize(Character.getInventorySize()); // Workaround, see Character.java
		
                try
		{
                    //Serialize data object to a file
                    ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream("Character.sav"));
                    out.writeObject(player);
                    out.close();
		}
		catch (IOException e)
		{
                    System.out.println("Failed to read save file");
                    e.printStackTrace();
		}
	}
	
	//--------------------------------------------------------------------------
	// Background saving for the GUI
	//--------------------------------------------------------------------------
	private void backgroundSave()
	{
		saveNPC(); // Workaround
		saveItem();
		player.saveInventorySize(Character.getInventorySize()); // Workaround, see Character.java
		try
		{
			//Serialize data object to a file
			ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream("Character.sav"));
			out.writeObject(player);
			out.close();
		}
		catch (IOException e)
		{
		}
	}
	
	//--------------------------------------------------------------------------
	// Loads the character data
	//--------------------------------------------------------------------------
	private void loadGame()
	{
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
	private void saveNPC()
	{
		// Violet mushrooms
		if (rooms[25].getNPC() == 0)
			player.setVioletMushroom1(1);
		
		if (rooms[88].getNPC() == 0)
			player.setVioletMushroom2(1);
		
		if (rooms[128].getNPC() == 0)
			player.setVioletMushroom3(1);
		
		if (rooms[178].getNPC() == 0)
			player.setVioletMushroom4(1);
		
		if (rooms[212].getNPC() == 0)
			player.setVioletMushroom5(1);
		
		if (rooms[260].getNPC() == 0)
			player.setVioletMushroom6(1);
		
		if (rooms[355].getNPC() == 0)
			player.setVioletMushroom7(1);
		
		if (rooms[388].getNPC() == 0)
			player.setVioletMushroom8(1);
		
		// Black cat
		if (rooms[28].getNPC() == 0)
			player.setBlackCat(1);
		
		// Hardworking man
		if (rooms[73].getNPC() == 0)
			player.setHardworkingMan(1);
		
		// Rotting corpse
		if (rooms[101].getNPC() == 0)
			player.setRottingCorpse(1);
		
		// Frozen corpse
		if (rooms[223].getNPC() == 0)
			player.setFrozenCorpse(1);
		
		// Wooden chest
		if (rooms[266].getNPC() == 0)
			player.setWoodenChest(1);
		
		// Ancient chest
		if (rooms[312].getNPC() == 0)
			player.setAncientChest(1);
		
		// Zeramus
		if (rooms[429].getNPC() == 0)
			player.setZeramus(1);
	}
	
	//--------------------------------------------------------------------------
	// Set permanently removed NPCs in the Room objects after loading data
	//--------------------------------------------------------------------------
	private void loadNPC()
	{
		if (player.getMushroom1() == 1)
		{
			rooms[25].removeNPC();
			rooms[25].permanentRemoveNPC();
		}
			
		if (player.getMushroom2() == 1)
		{
			rooms[88].removeNPC();
			rooms[88].permanentRemoveNPC();
		}
			
		if (player.getMushroom3() == 1)
		{
			rooms[128].removeNPC();
			rooms[128].permanentRemoveNPC();
		}
			
		if (player.getMushroom4() == 1)
		{
			rooms[178].removeNPC();
			rooms[178].permanentRemoveNPC();
		}
			
		if (player.getMushroom5() == 1)
		{
			rooms[212].removeNPC();
			rooms[212].permanentRemoveNPC();
		}
			
		if (player.getMushroom6() == 1)
		{
			rooms[260].removeNPC();
			rooms[260].permanentRemoveNPC();
		}
			
		if (player.getMushroom7() == 1)
		{
			rooms[355].removeNPC();
			rooms[355].permanentRemoveNPC();
		}
			
		if (player.getMushroom8() == 1)
		{
			rooms[388].removeNPC();
			rooms[388].permanentRemoveNPC();
		}
		
		if (player.getBlackCat() == 1)
		{
			rooms[28].removeNPC();
			rooms[28].permanentRemoveNPC();
		}
		
		if (player.getHardworkingMan() == 1)
		{
			rooms[73].removeNPC();
			rooms[73].permanentRemoveNPC();
		}
		
		if (player.getRottingCorpse() == 1)
		{
			rooms[101].removeNPC();
			rooms[101].permanentRemoveNPC();
		}
		
		if (player.getFrozenCorpse() == 1)
		{
			rooms[223].removeNPC();
			rooms[223].permanentRemoveNPC();
		}
		
		if (player.getWoodenChest() == 1)
		{
			rooms[266].removeNPC();
			rooms[266].permanentRemoveNPC();
		}
		
		if (player.getAncientChest() == 1)
		{
			rooms[312].removeNPC();
			rooms[312].permanentRemoveNPC();
		}
		
		if (player.getZeramus() == 1)
		{
			rooms[429].removeNPC();
			rooms[429].permanentRemoveNPC();
		}
	}
	
	//--------------------------------------------------------------------------
	// Set permanently removed Item data from NPC to Character.java
	//--------------------------------------------------------------------------
	private void saveItem()
	{
		// Goblin king
		if (npcs[116].getDroppedItem() == 0)
			player.setGoldenHilt(1);
		
		// Otri the Undying
		if (npcs[126].getDroppedItem() == 0)
			player.setRuneEtchedBlade(1);

	}
	
	//--------------------------------------------------------------------------
	// Set permanently removed Items in the NPC objects after loading data
	//--------------------------------------------------------------------------
	private void loadItem()
	{
		// Goblin king
		if (player.getGoldenHilt() == 1)
			npcs[116].setDroppedItem(0);
		
		if (player.getRuneEtchedBlade() == 1)
			npcs[126].setDroppedItem(0);
	}
	
	//--------------------------------------------------------------------------
	// Math quiz (Designed for the philosopher in town but can be used anywhere)
	//--------------------------------------------------------------------------
	private void mathQuiz()
	{
		int mathProblem = 0;
		int a = 0;
		int b = 0;
		int c = 0;
		
		String rightAnswer = "";
		String playerAnswer = "";
		
		mathProblem = rand.nextInt(3) +1; // Randomizes the type of math problem asked
		
		switch (mathProblem)
		{
			case 1:
				a = rand.nextInt(20) +1;
				b = rand.nextInt(20) +1;
				c = a*b;
		
				rightAnswer = Integer.toString(c);

				System.out.println ("\n" + npcs[player.getCurrentNPC()] + " says: 'What is " + a + "*" + b + "?'");
				System.out.print("\nAnswer: ");
				playerAnswer = C.io.nextLine();
				break;
			case 2:
				a = rand.nextInt(1000) +1;
				b = rand.nextInt(1000) +1;
				c = a+b;
		
				rightAnswer = Integer.toString(c);

				System.out.println ("\n" + npcs[player.getCurrentNPC()] + " says: 'What is " + a + "+" + b + "?'");
				System.out.print("\nAnswer: ");
				playerAnswer = C.io.nextLine();
				break;
			case 3:
				a = rand.nextInt(1000) +1;
				b = rand.nextInt(1000) +1;
				c = a-b;
		
				rightAnswer = Integer.toString(c);

				System.out.println ("\n" + npcs[player.getCurrentNPC()] + " says: 'What is " + a + "-" + b + "?'");
				System.out.print("\nAnswer: ");
				playerAnswer = C.io.nextLine();
				break;
			default:
				System.out.println ("ERROR! Please report this bug to: robin.neko@gmail.com");
				break;
		}
			
		if (playerAnswer.equals(rightAnswer))
		{
			System.out.println ("\n" + npcs[player.getCurrentNPC()] + " says: 'You are correct!'");
			System.out.println ("\nYou received 3 experience.\n");
			player.addExperience(3);
		}
		else
		{
			System.out.println ("\n" + npcs[player.getCurrentNPC()] + " says: 'That's wrong! The right answer is: " + rightAnswer + ".'\n");
		}
	}
}
