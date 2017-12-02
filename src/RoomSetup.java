import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//******************************************************************************
// RoomSetup.java
//
// This class only has one purpose: To clean up the huge array blog in Game.java
//******************************************************************************

public class RoomSetup {
    private final Room[] ROOMS; // Array creating the game world aka. rooms
    ArrayList<String> description = new ArrayList<>(); // Array List for the room descriptions which are acquired from rooms.txt
    
    public RoomSetup() {
        //******************************************************************************
        // ROOMS SETUP
        //******************************************************************************

        // Sets up the list of rooms. Change this as the number of rooms increases.
        // CAUTION: If this is not set to the EXACT number of rooms there will be a runtime error with the respawn timer
        // Also there can't be ANY blanks inbetween room instances created. Ie. The number of instances created always
        // needs to be the exact same as what is set here
        ROOMS = new Room[434];

        // Sets up the descriptions of the rooms using the rooms.txt file
        generateRooms();

        // ("<Room description>", <North>, <South>, <West>, <East>, <Up>, <Down>, <NPC>) - leads to those room IDs.
        //
        // The <NPC> variable sets whether the room contains an NPC or not. If set to higher than 0
        // it contains that NPC ID.

        // Starting house
        ROOMS[0] = new Room (description.get(0), 0, 0, 0, 0, 0, 0, 0); // Not used. 
        ROOMS[1] = new Room (description.get(1), 2, 0, 0, 0, 0, 0, 0); // Starting room. Exits: North to 2
        ROOMS[2] = new Room (description.get(2), 5, 1, 3, 4, 0, 0, 0); // Exits: North to 5, South to 1
        ROOMS[3] = new Room (description.get(3), 6, 0, 0, 2, 0, 0, 25); // Exits: North to 6, East to 2. Contains Rupert (#25)
        ROOMS[4] = new Room (description.get(4), 7, 0, 2, 0, 0, 0, 24); // Exits: North to 7, West to 2. Contains Josephine (#24)
        ROOMS[5] = new Room (description.get(5), 8, 2, 6, 7, 0, 0, 0); // Exits: North to 8, South to 2, West to 6, East to 7.
        ROOMS[6] = new Room (description.get(6), 0, 3, 430, 5, 0, 0, 0); // Exits: South to 3, West to 430, East to 5.
        ROOMS[7] = new Room (description.get(7), 0, 4, 5, 0, 0, 0, 26); // Exits: South to 4, West to 5. Contains an old man smoking his pipe (#26)
        ROOMS[8] = new Room (description.get(8), 0, 5, 0, 9, 0, 0, 0); // Exits: South to 5, East to 9
        ROOMS[9] = new Room (description.get(9), 0, 0, 8, 10, 0, 0, 0); // Exits: West to 8, East to 10
        ROOMS[10] = new Room (description.get(10), 0, 0, 9, 11, 0, 0, 0); // Exits: West to 9, East to 11
        ROOMS[11] = new Room (description.get(11), 0, 12, 10, 0, 0, 0, 0); // Exits: South to 12, West to 10
        ROOMS[12] = new Room (description.get(12), 11, 15, 13, 14, 0, 0, 1); // Exits: North to 11, South to 15, West to 13, East to 14. Contains a butler (#1)
        ROOMS[13] = new Room (description.get(13), 0, 0, 0, 12, 0, 0, 0); // Exits: East to 12
        ROOMS[14] = new Room (description.get(14), 0, 0, 12, 0, 0, 0, 0); // Exits: West to 12
        ROOMS[15] = new Room (description.get(15), 12, 16, 0, 0, 0, 0, 0); // Exits: North to 12, South to 16

        // The forest meadow
        ROOMS[16] = new Room (description.get(16), 15, 17, 0, 0, 0, 0, 0); // Exits: North to 15, South to 17.
        ROOMS[17] = new Room (description.get(17), 16, 18, 0, 0, 0, 0, 104); // Exits: North to 16, South to 18. Contains a tiny bird (#104)
        ROOMS[18] = new Room (description.get(18), 17, 21, 19, 20, 0, 0, 0); // Exits: North to 17, South to 21, West to 19, East to 20
        ROOMS[19] = new Room (description.get(19), 0, 22, 0, 18, 0, 0, 104); // Exits: South to 22, East to 18. Contains a tiny bird (#104)
        ROOMS[20] = new Room (description.get(20), 0, 23, 18, 24, 0, 0, 0); // Exits: South to 23, West to 18, East to 24
        ROOMS[21] = new Room (description.get(21), 18, 37, 22, 23, 0, 0, 102); // Exits: North to 18, South to 37, West to 22, East to 23. Contains a red fox (#102)
        ROOMS[22] = new Room (description.get(22), 19, 39, 0, 21, 0, 0, 104); // Exits: North to 19, South to 39, East to 21. Contains a tiny bird (#104)
        ROOMS[23] = new Room (description.get(23), 20, 38, 21, 0, 0, 0, 102); // Exits: North to 20, South to 38, West to 21. Contains a red fox (#102)
        ROOMS[37] = new Room (description.get(37), 21, 42, 39, 38, 0, 0, 0); // Exits: North to 21, South to 42, West to 39, East to 38
        ROOMS[38] = new Room (description.get(38), 23, 0, 37, 40, 0, 0, 0); // Exits: North to 23, West to 37, East to 40
        ROOMS[39] = new Room (description.get(39), 22, 0, 0, 37, 0, 0, 104); // Exits: North to 22, East to 37. Contains a tiny bird (#104)

        // The small cave
        ROOMS[40] = new Room (description.get(40), 0, 0, 38, 41, 0, 0, 0); // Exits: West to 38, East to 41 
        ROOMS[41] = new Room (description.get(41), 0, 0, 40, 0, 0, 0, 107); // Exits: West to 40. Contains a sleeping bear (#107)

        // The pathway and clearing (On the way to the tower)
        ROOMS[24] = new Room (description.get(24), 25, 0, 20, 0, 0, 0, 0); // Exits: North to 25, West to 20
        ROOMS[25] = new Room (description.get(25), 26, 24, 0, 0, 0, 0, 214); // Exits: North to 26, South to 24. Contains a violet mushroom (#214)
        ROOMS[26] = new Room (description.get(26), 27, 25, 0, 0, 0, 0, 0); // Exits: North to 27, South to 25
        ROOMS[27] = new Room (description.get(27), 32, 26, 0, 29, 0, 0, 104); // Exits: North to 32, South to 26, East to 29. Contains a tiny bird (#104)
        ROOMS[28] = new Room (description.get(28), 30, 0, 29, 0, 0, 0, 213); // Exits: North to 30, West to 29. Contains a black cat (#213)
        ROOMS[29] = new Room (description.get(29), 31, 0, 27, 28, 0, 0, 103); // Exits: North to 31, West to 27, East to 28. Contains a donkey (#103)
        ROOMS[30] = new Room (description.get(30), 0, 28, 31, 0, 0, 0, 102); // Exits: South to 28, West to 31. Contains a red fox (#102)
        ROOMS[31] = new Room (description.get(31), 33, 29, 32, 30, 0, 0, 104); // Exits: North to 33, South to 29, West to 32, East 30. Contains a tiny bird (#104)
        ROOMS[32] = new Room (description.get(32), 0, 27, 0, 31, 0, 0, 0); // Exits: South to 27, East to 31

        // The tower
        ROOMS[33] = new Room (description.get(33), 0, 31, 0, 0, 34, 0, 0); // Exits: South to 31, Up to 34
        ROOMS[34] = new Room (description.get(34), 0, 0, 0, 0, 35, 33, 0); // Exits: Down to 33, Up to 35
        ROOMS[35] = new Room (description.get(35), 0, 0, 0, 0, 36, 34, 0); // Exits: Down to 34, Up to 36
        ROOMS[36] = new Room (description.get(36), 0, 0, 0, 0, 0, 35, 106); // Exits: Down to 35. Contains an owl (#106)

        // The pathway leading to the house
        ROOMS[42] = new Room (description.get(42), 37, 43, 0, 0, 0, 0, 0); // Exits: North to 37, South to 43
        ROOMS[43] = new Room (description.get(43), 42, 44, 45, 0, 0, 0, 0); // Exits: North to 42, South to 44, West to 45 
        ROOMS[44] = new Room (description.get(44), 43, 61, 0, 0, 0, 0, 0); // Exits: North to 43, South to 61
        ROOMS[45] = new Room (description.get(45), 0, 0, 46, 43, 0, 0, 204); // Exits: West to 46, East to 43. Contains a scarecrow (#204)
        ROOMS[46] = new Room (description.get(46), 0, 0, 47, 45, 0, 0, 0); // Exits: West to 47, East to 45

        // The garden and the house
        ROOMS[47] = new Room (description.get(47), 48, 49, 50, 46, 0, 0, 0); // Exits: North to 48, South to 49, West to 50, East to 46 
        ROOMS[48] = new Room (description.get(48), 0, 47, 51, 0, 0, 0, 0); // Exits: South to 47, West to 51 
        ROOMS[49] = new Room (description.get(49), 47, 0, 52, 0, 0, 0, 0); // Exits: North to 47, West to 52
        ROOMS[50] = new Room (description.get(50), 51, 52, 0, 47, 0, 0, 203); // Exits: North to 51, South to 52, East to 47. Contains a small chair (#203)
        ROOMS[51] = new Room (description.get(51), 0, 50, 0, 48, 53, 0, 0); // Exits: South to 50, East to 48, Up to 53 
        ROOMS[52] = new Room (description.get(52), 50, 0, 0, 49, 0, 60, 0); // Exits: North to 50, East to 49, Down to 60
        ROOMS[53] = new Room (description.get(53), 0, 55, 0, 54, 0, 51, 0); // Exits: South to 55, East to 54, Down to 51 
        ROOMS[54] = new Room (description.get(54), 0, 56, 53, 0, 0, 0, 0); // Exits: South to 56, West to 53
        ROOMS[55] = new Room (description.get(55), 53, 57, 59, 56, 0, 0, 0); // Exits: North to 53, South to 57, West to 59, East to 56
        ROOMS[56] = new Room (description.get(56), 54, 58, 55, 0, 0, 0, 0); // Exits: North to 54, South to 58, West to 55 
        ROOMS[57] = new Room (description.get(57), 55, 0, 0, 58, 0, 0, 108); // Exits: North to 55, East to 58. Contains a dirty vagrant (#108)
        ROOMS[58] = new Room (description.get(58), 56, 0, 57, 0, 0, 0, 0); // Exits: North to 56, West to 57 
        ROOMS[59] = new Room (description.get(59), 0, 0, 0, 55, 0, 0, 0); // Exits: East to 55
        ROOMS[60] = new Room (description.get(60), 0, 0, 0, 0, 52, 0, 105); // Exits: Up to 52. Contains a cockroach (#105)

        // The road south and the farming field
        ROOMS[61] = new Room (description.get(61), 44, 62, 0, 0, 0, 0, 0); // Exits: North to 44, South to 62
        ROOMS[62] = new Room (description.get(62), 61, 63, 0, 0, 0, 0, 0); // Exits: North to 61, South to 63
        ROOMS[63] = new Room (description.get(63), 62, 64, 0, 0, 0, 0, 4); // Exits: North to 62, South to 64. Contains a wandering salesman (#4)
        ROOMS[64] = new Room (description.get(64), 63, 74, 0, 65, 0, 0, 0); // Exits: North to 63, South to 74, East to 65
        ROOMS[65] = new Room (description.get(65), 0, 68, 64, 66, 0, 0, 0); // Exits: South to 68, West to 64, East to 66
        ROOMS[66] = new Room (description.get(66), 0, 69, 65, 67, 0, 0, 0); // Exits: South to 69, West to 65, East to 67
        ROOMS[67] = new Room (description.get(67), 0, 70, 66, 0, 0, 0, 109); // Exits: South to 70, West to 66. Contains a happy cow (#109)
        ROOMS[68] = new Room (description.get(68), 65, 73, 74, 69, 0, 0, 204); // Exits: North to 65, South to 73, West to 74, East to 69. Contains a scarecrow (#204)
        ROOMS[69] = new Room (description.get(69), 66, 72, 68, 70, 0, 0, 109); // Exits: North to 66, South to 72, West to 68, East to 70. Contains a happy cow (#109)
        ROOMS[70] = new Room (description.get(70), 67, 71, 69, 0, 0, 0, 0); // Exits: North to 67, South to 71, West to 69
        ROOMS[71] = new Room (description.get(71), 70, 0, 72, 0, 0, 0, 103); // Exits: North to 70, West to 72. Contains a donkey (#103)
        ROOMS[72] = new Room (description.get(72), 69, 0, 73, 71, 0, 0, 0); // Exits: North to 69, West to 73, East to 71
        ROOMS[73] = new Room (description.get(73), 68, 0, 75, 72, 0, 0, 5); // Exits: North to 68, West to 75, East to 72. Contains a hardworking man (#5)
        ROOMS[74] = new Room (description.get(74), 64, 75, 0, 68, 0, 0, 140); // Exits: North to 64, South to 75, East to 68. Contains a stray dog (#140)
        ROOMS[75] = new Room (description.get(75), 74, 76, 0, 73, 0, 0, 0); // Exits: North to 74, South to 76, East to 73
        ROOMS[76] = new Room (description.get(76), 75, 77, 0, 0, 0, 0, 103); // Exits: North to 75, South to 77. Contains a donkey (#103)
        ROOMS[77] = new Room (description.get(77), 76, 78, 0, 0, 0, 0, 0); // Exits: North to 76, South to 78
        ROOMS[78] = new Room (description.get(78), 77, 143, 79, 0, 0, 0, 0); // Exits: North to 77, South to 143, West to 79

        // The ghostly road to The Goblin King's dungeon
        ROOMS[79] = new Room (description.get(79), 0, 0, 80, 78, 0, 0, 0); // Exits: West to 80, East to 78
        ROOMS[80] = new Room (description.get(80), 0, 0, 81, 79, 0, 0, 0); // Exits: West to 81, East to 79
        ROOMS[81] = new Room (description.get(81), 0, 0, 82, 80, 0, 0, 0); // Exits: West to 82, East to 80
        ROOMS[82] = new Room (description.get(82), 0, 0, 83, 81, 0, 0, 0); // Exits: West to 83, East to 81
        ROOMS[83] = new Room (description.get(83), 0, 0, 84, 82, 0, 0, 0); // Exits: West to 84, East to 82
        ROOMS[84] = new Room (description.get(84), 85, 0, 0, 83, 0, 0, 143); // Exits: North to 85, East to 83. Contains an evil forest spirit (#143)
        ROOMS[85] = new Room (description.get(85), 87, 84, 0, 86, 0, 0, 0); // Exits: North to 87, South to 84, East to 86
        ROOMS[86] = new Room (description.get(86), 0, 0, 85, 0, 0, 0, 111); // Exits: West to 85. Contains a sobbing little girl ghost (#111)
        ROOMS[87] = new Room (description.get(87), 0, 85, 88, 0, 0, 0, 110); // Exits: South to 85, West to 88. Contains a sick-looking wolf (#110)
        ROOMS[88] = new Room (description.get(88), 0, 0, 89, 87, 0, 0, 214); // Exits: West to 89, East to 87. Contains a violet mushroom (#214)
        ROOMS[89] = new Room (description.get(89), 0, 90, 0, 88, 0, 0, 0); // Exits: South to 90, East to 88
        ROOMS[90] = new Room (description.get(90), 89, 91, 0, 0, 0, 0, 110); // Exits: North to 89, South to 91. Contains a sick-looking wolf (#110)
        ROOMS[91] = new Room (description.get(91), 90, 0, 92, 0, 0, 0, 0); // Exits: North to 90, West to 92

        // the Goblin King's dungeon 1F
        ROOMS[92] = new Room (description.get(92), 93, 94, 95, 91, 0, 0, 0); // Exits: North to 93, South to 94, West to 95, East to 91
        ROOMS[93] = new Room (description.get(93), 0, 92, 96, 0, 0, 0, 0); // Exits: South to 92, West to 96
        ROOMS[94] = new Room (description.get(94), 92, 0, 97, 0, 0, 0, 0); // Exits: North to 92, West to 97
        ROOMS[95] = new Room (description.get(95), 96, 97, 99, 92, 0, 0, 112); // Exits: North to 96, South to 97, West to 99, East to 92. Contains a small goblin (#112)
        ROOMS[96] = new Room (description.get(96), 101, 95, 98, 93, 0, 0, 112); // Exits: North to 101, South to 95, West to 98, East to 93. Contains a small goblin (#112)
        ROOMS[97] = new Room (description.get(97), 95, 0, 100, 94, 0, 0, 112); // Exits: North to 95, West to 100, East to 94. Contains a small goblin (#112)
        ROOMS[98] = new Room (description.get(98), 0, 99, 104, 96, 0, 0, 0); // Exits: South to 99, West to 104, East to 96
        ROOMS[99] = new Room (description.get(99), 98, 100, 103, 95, 0, 0, 0); // Exits: North to 98, South to 100, West to 103, East to 95
        ROOMS[100] = new Room (description.get(100), 99, 0, 102, 97, 0, 0, 0); // Exits: North to 99, West to 102, East to 97
        ROOMS[101] = new Room (description.get(101), 0, 96, 0, 0, 0, 0, 205); // Exits: South to 96. Contains a rotting corpse (#205)
        ROOMS[102] = new Room (description.get(102), 103, 0, 0, 100, 0, 0, 105); // Exits: North to 103, East to 100. Contains a cockroach (#105)
        ROOMS[103] = new Room (description.get(103), 104, 102, 105, 99, 0, 0, 0); // Exits: North to 104, South to 102, West to 105, East to 99
        ROOMS[104] = new Room (description.get(104), 0, 103, 0, 98, 0, 0, 112); // Exits: South to 103, East to 98. Contains a small goblin (#112)
        ROOMS[105] = new Room (description.get(105), 0, 0, 106, 103, 0, 0, 0); // Exits: West to 106, East to 103
        ROOMS[106] = new Room (description.get(106), 107, 0, 0, 105, 0, 0, 112); // Exits: North to 107, East to 105. Contains a small goblin (#112)
        ROOMS[107] = new Room (description.get(107), 108, 106, 0, 0, 0, 0, 0); // Exits: North to 108, South to 106
        ROOMS[108] = new Room (description.get(108), 109, 107, 0, 0, 0, 0, 105); // Exits: North to 109, South to 107. Contains a cockroach (#105)
        ROOMS[109] = new Room (description.get(109), 110, 108, 0, 0, 0, 0, 0); // Exits: North to 110, South to 108
        ROOMS[110] = new Room (description.get(110), 0, 109, 0, 0, 0, 111, 0); // Exits: South to 109, Down to 111

        // the Goblin King's dungeon B1F
        ROOMS[111] = new Room (description.get(111), 0, 0, 0, 112, 110, 0, 0); // Exits: East to 112, Up to 110
        ROOMS[112] = new Room (description.get(112), 115, 114, 111, 113, 0, 0, 0); // Exits: North to 115, South to 114, West to 111, East to 113
        ROOMS[113] = new Room (description.get(113), 116, 117, 112, 190, 0, 0, 0); // Exits: North to 116, South to 117, West to 112, East to 190
        // #190 (Made a mistake in the layout, hence the strange order)
        ROOMS[190] = new Room (description.get(190), 0, 0, 113, 118, 0, 0, 113); // Exits: West to 113, East to 118. Contains a goblin warrior (#113)
        ROOMS[114] = new Room (description.get(114), 112, 127, 0, 117, 0, 0, 0); // Exits: North to 112, South to 127, East to 117
        ROOMS[115] = new Room (description.get(115), 0, 112, 0, 116, 0, 0, 114); // Exits: South to 112, East to 116. Contains a lazy goblin guard (#114)
        ROOMS[116] = new Room (description.get(116), 131, 113, 115, 0, 0, 0, 0); // Exits: North to 131, South to 113, West to 115
        ROOMS[117] = new Room (description.get(117), 113, 129, 114, 0, 0, 0, 0); // Exits: North to 113, South to 129, West to 114
        ROOMS[118] = new Room (description.get(118), 0, 0, 190, 119, 0, 0, 0); // Exits: West to 190, East to 119
        ROOMS[119] = new Room (description.get(119), 0, 0, 118, 120, 0, 0, 0); // Exits: West to 118, East to 120
        ROOMS[120] = new Room (description.get(120), 121, 122, 119, 123, 0, 0, 113); // Exits: North to 121, South to 122, West to 119, East to 123. Contains a goblin warrior (#113)
        ROOMS[121] = new Room (description.get(121), 0, 120, 0, 124, 0, 0, 0); // Exits: South to 120, East to 124
        ROOMS[122] = new Room (description.get(122), 120, 0, 0, 125, 0, 0, 0); // Exits: North to 120, East to 125
        ROOMS[123] = new Room (description.get(123), 124, 125, 120, 126, 0, 0, 113); // Exits: North to 124, South to 125, West to 120, East to 126. Contains a goblin warrior (#113)
        ROOMS[124] = new Room (description.get(124), 0, 123, 121, 0, 0, 0, 0); // Exits: South to 123, West to 121
        ROOMS[125] = new Room (description.get(125), 123, 0, 122, 0, 0, 0, 113); // Exits: North to 123, West to 122. Contains a goblin warrior (#113)
        ROOMS[126] = new Room (description.get(126), 0, 0, 123, 0, 0, 0, 116); // Exits: West to 123. Contains the Goblin King (#116)
        ROOMS[127] = new Room (description.get(127), 114, 0, 0, 129, 0, 128, 0); // Exits: North to 114, East to 129, Down to 128
        ROOMS[128] = new Room (description.get(128), 0, 0, 0, 0, 127, 0, 214); // Exits: Up to 127. Contains a violet mushroom (#214)
        ROOMS[129] = new Room (description.get(129), 117, 0, 127, 137, 0, 130, 0); // Exits: North to 117, West to 127, East to 137, Down to 130
        ROOMS[130] = new Room (description.get(130), 0, 0, 0, 0, 129, 0, 0); // Exits: Up to 129
        ROOMS[131] = new Room (description.get(131), 132, 116, 0, 0, 0, 0, 0); // Exits: North to 132, South to 116
        ROOMS[132] = new Room (description.get(132), 0, 131, 0, 133, 0, 0, 0); // Exits: South to 131, East to 133
        ROOMS[133] = new Room (description.get(133), 0, 134, 132, 136, 0, 0, 0); // Exits: South to 134, West to 132, East to 136
        ROOMS[134] = new Room (description.get(134), 133, 0, 0, 135, 0, 0, 0); // Exits: North to 133, East to 135
        ROOMS[135] = new Room (description.get(135), 136, 0, 134, 0, 0, 0, 115); // Exits: North to 136, West to 134. Contains a goblin cook (#115)
        ROOMS[136] = new Room (description.get(136), 0, 135, 133, 0, 0, 0, 0); // Exits: South to 135, West to 133
        ROOMS[137] = new Room (description.get(137), 0, 0, 129, 139, 0, 138, 0); // Exits: West to 129, East to 139, Down to 138
        ROOMS[138] = new Room (description.get(138), 0, 0, 0, 0, 137, 0, 117); // Exits: Up to 137. Contains a prisoner (#117)
        ROOMS[139] = new Room (description.get(139), 0, 141, 137, 140, 0, 0, 0); // Exits: South to 141, West to 137, East to 140
        ROOMS[140] = new Room (description.get(140), 0, 142, 139, 0, 0, 0, 114); // Exits: South to 142, West to 139. Contains a lazy goblin guard (#114)
        ROOMS[141] = new Room (description.get(141), 139, 0, 0, 142, 0, 0, 0); // Exits: North to 139, East to 142
        ROOMS[142] = new Room (description.get(142), 140, 0, 141, 0, 0, 0, 114); // Exits: North to 140, West to 141. Contains a lazy goblin guard (#114)

        // The road to Halin village. (Continues from 78)
        ROOMS[143] = new Room (description.get(143), 78, 144, 0, 0, 0, 0, 0); // Exits: North to 78, South to 144
        ROOMS[144] = new Room (description.get(144), 143, 145, 0, 0, 0, 0, 120); // Exits: North to 143, South to 145. Contains a road bandit (#120)
        ROOMS[145] = new Room (description.get(145), 144, 146, 0, 0, 0, 0, 0); // Exits: North to 144, South to 146
        ROOMS[146] = new Room (description.get(146), 145, 147, 0, 0, 0, 0, 0); // Exits: North to 145, South to 147

        // Halin village
        ROOMS[147] = new Room (description.get(147), 146, 148, 176, 156, 0, 0, 9); // Exits: North to 146, South to 148, West to 176, East to 156. Contains a town guard (#9)
        ROOMS[148] = new Room (description.get(148), 147, 149, 0, 166, 0, 0, 0); // Exits: North to 147, South to 149, East to 166
        ROOMS[149] = new Room (description.get(149), 148, 150, 0, 167, 0, 0, 0); // Exits: North to 148, South to 150, East to 167
        ROOMS[150] = new Room (description.get(150), 149, 151, 173, 168, 0, 0, 0); // Exits: North to 149, South to 151, West to 173, East to 168
        ROOMS[151] = new Room (description.get(151), 150, 152, 174, 169, 0, 0, 201); // Exits: North to 150, South to 152, West to 174, East to 169. Contains a fountain (#201)
        ROOMS[152] = new Room (description.get(152), 151, 153, 175, 170, 0, 0, 0); // Exits: North to 151, South to 153, West to 175, East to 170
        ROOMS[153] = new Room (description.get(153), 152, 154, 0, 0, 0, 0, 0); // Exits: North to 152, South to 154
        ROOMS[154] = new Room (description.get(154), 153, 155, 187, 172, 0, 0, 0); // Exits: North to 153, South to 155, West to 187, East to 172
        ROOMS[155] = new Room (description.get(155), 154, 191, 188, 189, 0, 0, 9); // Exits: North to 154, South to 191, West to 188, East to 189. Contains a town guard (#9)
        ROOMS[156] = new Room (description.get(156), 0, 0, 147, 157, 0, 0, 0); // Exits: West to 147, East to 157
        ROOMS[157] = new Room (description.get(157), 0, 158, 156, 0, 0, 0, 9); // Exits: South to 158, West to 156. Contains a town guard (#9)
        ROOMS[158] = new Room (description.get(158), 157, 159, 0, 0, 0, 0, 0); // Exits: North to 157, South to 159
        ROOMS[159] = new Room (description.get(159), 158, 160, 0, 0, 0, 0, 118); // Exits: North to 158, South to 160. Contains a street thug (#118)
        ROOMS[160] = new Room (description.get(160), 159, 161, 168, 0, 0, 0, 0); // Exits: North to 159, South to 161, West to 168
        ROOMS[161] = new Room (description.get(161), 160, 162, 169, 0, 0, 0, 0); // Exits: North to 160, South to 162, West to 169
        ROOMS[162] = new Room (description.get(162), 161, 163, 170, 0, 0, 0, 0); // Exits: North to 161, South to 163, West to 170
        ROOMS[163] = new Room (description.get(163), 162, 164, 171, 0, 0, 0, 0); // Exits: North to 162, South to 164, West to 171
        ROOMS[164] = new Room (description.get(164), 163, 165, 172, 0, 0, 0, 119); // Exits: North to 163, South to 165, West to 172. Contains a big rat (#119)
        ROOMS[165] = new Room (description.get(165), 164, 0, 189, 0, 0, 0, 11); // Exits: North to 164, West to 189. Contains an elderly villager (#11)
        ROOMS[166] = new Room (description.get(166), 0, 0, 148, 0, 0, 0, 30); // Exits: West to 148. Contains Harald the innkeeper (#30)
        ROOMS[167] = new Room (description.get(167), 0, 0, 149, 0, 0, 0, 2); // Exits: West to 149. Contains a shopkeeper (#2)
        ROOMS[168] = new Room (description.get(168), 0, 169, 150, 160, 0, 0, 0); // Exits: South to 169, West to 150, East to 160
        ROOMS[169] = new Room (description.get(169), 168, 170, 151, 161, 0, 0, 6); // Exits: North to 168, South to 170, West to 151, East to 161. Contains a villager (#6)
        ROOMS[170] = new Room (description.get(170), 169, 0, 152, 162, 0, 0, 0); // Exits: North to 169, West to 152, East to 162
        ROOMS[171] = new Room (description.get(171), 0, 0, 0, 163, 0, 0, 7); // Exits: East to 163. Contains Grunbald the magician (#7)
        ROOMS[172] = new Room (description.get(172), 0, 189, 154, 164, 0, 0, 0); // Exits: South to 189, West to 154, East to 164
        ROOMS[173] = new Room (description.get(173), 0, 174, 180, 150, 0, 0, 202); // Exits: South to 174, West to 180, East to 150. Contains a fortune telling machine (#202)
        ROOMS[174] = new Room (description.get(174), 173, 175, 181, 151, 0, 0, 6); // Exits: North to 173, South to 175, West to 181, East to 151. Contains a villager (#6)
        ROOMS[175] = new Room (description.get(175), 174, 0, 182, 152, 0, 0, 140); // Exits: North to 174, West to 182, East to 152. Contains a stray dog (#140)
        ROOMS[176] = new Room (description.get(176), 0, 0, 177, 147, 0, 0, 0); // Exits: West to 177, East to 147
        ROOMS[177] = new Room (description.get(177), 0, 178, 0, 176, 0, 0, 0); // Exits: South to 178, East to 176
        ROOMS[178] = new Room (description.get(178), 177, 179, 0, 0, 0, 0, 214); // Exits: North to 177, South to 179. Contains a violet mushroom (#214)
        ROOMS[179] = new Room (description.get(179), 178, 180, 0, 0, 0, 0, 10); // Exits: North to 178, South to 180. Contains a philosopher (#10)
        ROOMS[180] = new Room (description.get(180), 179, 181, 0, 173, 0, 0, 0); // Exits: North to 179, South to 181, East to 173
        ROOMS[181] = new Room (description.get(181), 180, 182, 0, 174, 0, 0, 119); // Exits: North to 180, South to 182, East to 174. Contains a big rat (#119)
        ROOMS[182] = new Room (description.get(182), 181, 183, 0, 175, 0, 0, 0); // Exits: North to 181, South to 183, East to 175
        ROOMS[183] = new Room (description.get(183), 182, 184, 0, 186, 0, 0, 0); // Exits: North to 182, South to 184, East to 186
        ROOMS[184] = new Room (description.get(184), 183, 185, 0, 187, 0, 0, 0); // Exits: North to 183, South to 185, East to 187
        ROOMS[185] = new Room (description.get(185), 184, 0, 0, 188, 0, 0, 9); // Exits: North to 184, East to 188. Contains a town guard (#9)
        ROOMS[186] = new Room (description.get(186), 0, 0, 183, 0, 0, 0, 8); // Exits: West to 183. Contains Skeld the blacksmith (#8)
        ROOMS[187] = new Room (description.get(187), 0, 188, 184, 154, 0, 0, 140); // Exits: South to 188, West to 184, East to 154. Contains a stray dog (#140)
        ROOMS[188] = new Room (description.get(188), 187, 0, 185, 155, 0, 0, 0); // Exits: North to 187, West to 185, East to 155
        ROOMS[189] = new Room (description.get(189), 172, 0, 155, 165, 0, 0, 0); // Exits: North to 172, West to 155, East to 165
        // #190 is in the Goblin King's dungeon

        // The south road and the small camp
        ROOMS[191] = new Room (description.get(191), 155, 192, 0, 0, 0, 0, 0); // Exits: North to 155, South to 192
        ROOMS[192] = new Room (description.get(192), 191, 194, 0, 193, 0, 0, 0); // Exits: North to 191, South to 194, East to 193
        ROOMS[193] = new Room (description.get(193), 0, 0, 192, 0, 0, 0, 12); // Exits: West to 192. Contains a frightened town guard (#12)
        ROOMS[194] = new Room (description.get(194), 192, 195, 0, 0, 0, 0, 0); // Exits: North to 192, South to 195
        ROOMS[195] = new Room (description.get(195), 194, 0, 0, 196, 0, 0, 0); // Exits: North to 194, East to 196
        ROOMS[196] = new Room (description.get(196), 0, 0, 195, 197, 0, 0, 120); // Exits: West to 195, East to 197. Contains a road bandit (#120)
        ROOMS[197] = new Room (description.get(197), 0, 0, 196, 198, 0, 0, 0); // Exits: West to 196, East to 198
        ROOMS[198] = new Room (description.get(198), 0, 199, 197, 206, 0, 0, 0); // Exits: South to 199, West to 197, East to 206
        ROOMS[199] = new Room (description.get(199), 198, 200, 0, 0, 0, 0, 119); // Exits: North to 198, South to 200. Contains a big rat (#119)
        ROOMS[200] = new Room (description.get(200), 199, 204, 202, 201, 0, 0, 0); // Exits: North to 199, South to 204, West to 202, East to 201
        ROOMS[201] = new Room (description.get(201), 0, 205, 200, 0, 0, 0, 14); // Exits: South to 205, West to 200. Contains Lianne (#14)
        ROOMS[202] = new Room (description.get(202), 0, 203, 0, 200, 0, 0, 16); // Exits: South to 203, East to 200. Contains Asta (#16)
        ROOMS[203] = new Room (description.get(203), 202, 0, 0, 204, 0, 0, 15); // Exits: North to 202, East to 204. Contains Thorulf (#15)
        ROOMS[204] = new Room (description.get(204), 200, 0, 203, 205, 0, 0, 0); // Exits: North to 200, West to 203, East to 205
        ROOMS[205] = new Room (description.get(205), 201, 0, 204, 0, 0, 0, 13); // Exits: North to 201, West to 204. Contains Glenn the bard (#13)
        ROOMS[206] = new Room (description.get(206), 0, 0, 198, 207, 0, 0, 120); // Exits: West to 198, East to 207. Contains a road bandit (#120)
        ROOMS[207] = new Room (description.get(207), 0, 0, 206, 208, 0, 0, 0); // Exits: West to 206, East to 208

        // The path to the mountain and climbing the mountain
        ROOMS[208] = new Room (description.get(208), 0, 209, 207, 0, 0, 0, 0); // Exits: South to 209, West to 207
        ROOMS[209] = new Room (description.get(209), 208, 210, 0, 0, 0, 0, 0); // Exits: North to 208, South to 210
        ROOMS[210] = new Room (description.get(210), 209, 0, 0, 211, 0, 0, 0); // Exits: North to 209, East to 211
        ROOMS[211] = new Room (description.get(211), 0, 0, 210, 212, 0, 0, 142); // Exits: West to 210, East to 212. Contains a mountaineer (#142)
        ROOMS[212] = new Room (description.get(212), 0, 0, 211, 0, 213, 0, 214); // Exits: West to 211, Up to 213. Contains a violet mushroom (#214)
        ROOMS[213] = new Room (description.get(213), 214, 0, 0, 0, 0, 212, 0); // Exits: North to 214, Down to 212
        ROOMS[214] = new Room (description.get(214), 215, 213, 0, 0, 0, 0, 141); // Exits: North to 215, South to 213. Contains a hawk (#141)
        ROOMS[215] = new Room (description.get(215), 0, 214, 0, 0, 216, 0, 0); // Exits: South to 214, Up to 216
        ROOMS[216] = new Room (description.get(216), 0, 0, 217, 0, 0, 215, 122); // Exits: West to 217, Down to 215. Contains a mountain goat (#122)
        ROOMS[217] = new Room (description.get(217), 0, 0, 218, 216, 0, 0, 0); // Exits: West to 218, East to 216
        ROOMS[218] = new Room (description.get(218), 0, 0, 0, 217, 219, 0, 142); // Exits: East to 217, Up to 219. Contains a crazy mountaineer (#142)
        ROOMS[219] = new Room (description.get(219), 0, 0, 0, 220, 0, 218, 0); // Exits: East to 220, Down to 218
        ROOMS[220] = new Room (description.get(220), 0, 0, 219, 221, 0, 0, 121); // Exits: West to 219, East to 221. Contains a mountain lion (#121)
        ROOMS[221] = new Room (description.get(221), 0, 0, 220, 222, 0, 0, 0); // Exits: West to 220, East to 222
        ROOMS[222] = new Room (description.get(222), 0, 223, 221, 0, 224, 0, 0); // Exits: South to 223, West to 221, Up to 224
        ROOMS[223] = new Room (description.get(223), 222, 0, 0, 0, 0, 0, 208); // Exits: North to 222. Contains the frozen corpse of an adventurer (#208)

        // The summit of the mountain
        ROOMS[224] = new Room (description.get(224), 0, 225, 0, 0, 0, 222, 121); // Exits: South to 225, Down to 222. Contains a mountain lion (#121)
        ROOMS[225] = new Room (description.get(225), 224, 226, 0, 0, 0, 0, 0); // Exits:  North to 224, South to 226
        ROOMS[226] = new Room (description.get(226), 225, 0, 227, 229, 0, 0, 0); // Exits: North to 225, West to 227, East to 229
        ROOMS[227] = new Room (description.get(227), 0, 0, 0, 226, 0, 0, 122); // Exits: East to 226. Contains a mountain goat (#122)
        ROOMS[228] = new Room (description.get(228), 0, 229, 0, 0, 0, 0, 17); // Exits: South to 229. Contains a hermit (#17)
        ROOMS[229] = new Room (description.get(229), 228, 0, 226, 230, 0, 0, 0); // Exits: North to 228, West to 226, East to 230
        ROOMS[230] = new Room (description.get(230), 0, 0, 229, 231, 0, 0, 0); // Exits: West to 229, East to 231
        ROOMS[231] = new Room (description.get(231), 0, 0, 230, 0, 0, 0, 206); // Exits: West to 230. Contains a wooden elevator (#206)

        // The tribal village
        ROOMS[232] = new Room (description.get(232), 0, 0, 0, 233, 0, 0, 207); // Exits: East to 233. Contains a wooden elevator (#207)
        ROOMS[233] = new Room (description.get(233), 234, 235, 232, 237, 0, 0, 0); // Exits: North to 234, South to 235, West to 232, East to 237
        ROOMS[234] = new Room (description.get(234), 0, 233, 0, 238, 0, 0, 0); // Exits: South to 233, East to 238
        ROOMS[235] = new Room (description.get(235), 233, 0, 0, 236, 0, 0, 0); // Exits: North to 233, East to 236
        ROOMS[236] = new Room (description.get(236), 237, 239, 235, 240, 0, 0, 0); // Exits: North to 237, South to 239, West to 235, East to 240
        ROOMS[237] = new Room (description.get(237), 238, 236, 233, 241, 0, 0, 209); // Exits: North to 238, South to 236, West to 233, East to 241. Contains a bonfire (#209)
        ROOMS[238] = new Room (description.get(238), 244, 237, 234, 242, 0, 0, 0); // Exits: North to 244, South to 237, West to 234, East to 242
        ROOMS[239] = new Room (description.get(239), 236, 0, 0, 0, 0, 0, 19); // Exits: North to 236. Contains the shaman of the Zulah tribe (#19)
        ROOMS[240] = new Room (description.get(240), 241, 0, 236, 0, 0, 0, 0); // Exits: North to 241, West to 236
        ROOMS[241] = new Room (description.get(241), 242, 240, 237, 245, 0, 0, 20); // Exits: North to 242, South to 240, West to 237, East to 245. Contains a hunter of the Zulah tribe (#20)
        ROOMS[242] = new Room (description.get(242), 243, 241, 238, 0, 0, 0, 0); // Exits: North to 243, South to 241, West to 238
        ROOMS[243] = new Room (description.get(243), 0, 242, 0, 0, 0, 0, 18); // Exits: South to 242. Contains a member of the Zulah tribe (#18)
        ROOMS[244] = new Room (description.get(244), 0, 238, 0, 0, 0, 0, 18); // Exits: South to 238. Contains a member of the Zulah tribe (#18)

        // The narrow bridge to the marsh and the beginning of the marsh
        ROOMS[245] = new Room (description.get(245), 0, 0, 241, 246, 0, 0, 0); // Exits: West to 241, East to 246
        ROOMS[246] = new Room (description.get(246), 0, 0, 245, 247, 0, 0, 0); // Exits: West to 245, East to 247
        ROOMS[247] = new Room (description.get(247), 0, 0, 246, 248, 0, 0, 0); // Exits: West to 246, East to 248
        ROOMS[248] = new Room (description.get(248), 0, 0, 247, 249, 0, 0, 0); // Exits: West to 247, East to 249
        ROOMS[249] = new Room (description.get(249), 0, 0, 248, 250, 0, 0, 0); // Exits: West to 248, East to 250
        ROOMS[250] = new Room (description.get(250), 0, 0, 249, 251, 0, 0, 0); // Exits: West to 249, East to 251
        ROOMS[251] = new Room (description.get(251), 0, 252, 250, 253, 0, 0, 0); // Exits: South to 252, West to 250, East to 253
        ROOMS[252] = new Room (description.get(252), 251, 256, 0, 254, 0, 0, 124); // Exits: North to 251, South to 256, East to 254. Contains a poisonous green frog (#124)
        ROOMS[253] = new Room (description.get(253), 0, 254, 251, 0, 0, 0, 0); // Exits: South to 254, West to 251
        ROOMS[254] = new Room (description.get(254), 253, 255, 252, 257, 0, 0, 0); // Exits: North to 253, South to 255, West to 252, East to 257
        ROOMS[255] = new Room (description.get(255), 254, 0, 256, 0, 0, 0, 0); // Exits: North to 254, West to 256
        ROOMS[256] = new Room (description.get(256), 252, 0, 0, 255, 0, 0, 0); // Exits: North to 252, East to 255
        ROOMS[257] = new Room (description.get(257), 0, 0, 254, 258, 0, 0, 212); // Exits: West to 254, East to 258. Contains a wooden sign (#212)

        // The marsh of the dead
        ROOMS[258] = new Room (description.get(258), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
        ROOMS[259] = new Room (description.get(259), 0, 0, 0, 0, 0, 0, 124); // Exits: Random from 257-271. Contains a poisonous green frog (#124)
        ROOMS[260] = new Room (description.get(260), 0, 0, 0, 0, 0, 0, 214); // Exits: Random from 257-271. Contains a violet mushroom (#214)
        ROOMS[261] = new Room (description.get(261), 0, 0, 0, 0, 0, 0, 123); // Exits: Random from 257-271. Contains a marsh horror (#123)
        ROOMS[262] = new Room (description.get(262), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
        ROOMS[263] = new Room (description.get(263), 0, 0, 0, 0, 0, 0, 123); // Exits: Random from 257-271. Contains a marsh horror (#123)
        ROOMS[264] = new Room (description.get(264), 0, 0, 0, 0, 0, 0, 129); // Exits: Random from 257-271. Contains a marsh serpent (#129)
        ROOMS[265] = new Room (description.get(265), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
        ROOMS[266] = new Room (description.get(266), 0, 0, 0, 0, 0, 0, 210); // Exits: Random from 257-271. Contains a wooden chest covered with grime (#210)
        ROOMS[267] = new Room (description.get(267), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
        ROOMS[268] = new Room (description.get(268), 0, 0, 0, 0, 0, 0, 123); // Exits: Random from 257-271. Contains a marsh horror (#123)
        ROOMS[269] = new Room (description.get(269), 0, 0, 0, 0, 0, 0, 0); // Exits: Random from 257-271
        ROOMS[270] = new Room (description.get(270), 0, 0, 0, 0, 0, 0, 21); // Exits: Random from 257-271. Contains a cackling mad man (#21)
        ROOMS[271] = new Room (description.get(271), 272, 274, 270, 273, 0, 0, 129); // Exits: North to 272, South to 274, West to 270, East to 273. Contains a marsh serpent (#129)

        // Before the crypts
        ROOMS[272] = new Room (description.get(272), 0, 271, 0, 276, 0, 0, 125); // Exits: South to 271, East to 276. Contains a grave robber (#125)
        ROOMS[273] = new Room (description.get(273), 276, 275, 271, 278, 0, 0, 0); // Exits: North to 276, South to 275, West to 271, East to 278
        ROOMS[274] = new Room (description.get(274), 271, 0, 0, 275, 0, 0, 0); // Exits: North to 271, East to 275
        ROOMS[275] = new Room (description.get(275), 273, 280, 274, 279, 0, 0, 125); // Exits: North to 273, South to 280, West to 274, East to 279. Contains a grave robber (#125)
        ROOMS[276] = new Room (description.get(276), 0, 273, 272, 277, 0, 0, 0); // Exits: South to 273, West to 272, East to 277
        ROOMS[277] = new Room (description.get(277), 0, 278, 276, 0, 0, 0, 0); // Exits: South to 278, West to 276
        ROOMS[278] = new Room (description.get(278), 277, 279, 273, 313, 0, 0, 0); // Exits: North to 277, South to 279, West to 273, East to 313
        ROOMS[279] = new Room (description.get(279), 278, 0, 275, 0, 0, 0, 129); // Exits: North to 278, West to 275. Contains a marsh serpent (#129)
        ROOMS[280] = new Room (description.get(280), 275, 0, 0, 0, 0, 281, 0); // Exits: North to 275, Down to 281

        // Inside the crypts
        ROOMS[281] = new Room (description.get(281), 0, 282, 0, 0, 280, 0, 0); // Exits: Up to 280, South to 282
        ROOMS[282] = new Room (description.get(282), 281, 283, 0, 0, 0, 0, 0); // Exits: North to 281, South to 283
        ROOMS[283] = new Room (description.get(283), 282, 0, 284, 0, 0, 0, 0); // Exits: North to 282, West to 284
        ROOMS[284] = new Room (description.get(284), 285, 286, 292, 283, 0, 0, 127); // Exits: North to 285, South to 286, West to 292, East to 283. Contains a chainmail armored skeleton (#127)
        ROOMS[285] = new Room (description.get(285), 311, 284, 293, 0, 0, 0, 0); // Exits: North to 311, South to 284, West to 293
        ROOMS[286] = new Room (description.get(286), 284, 289, 291, 287, 0, 0, 0); // Exits: North to 284, South to 289, West to 291, East to 287
        ROOMS[287] = new Room (description.get(287), 0, 288, 286, 0, 0, 0, 127); // Exits: South to 288, West to 286. Contains a chainmail armored skeleton (#127)
        ROOMS[288] = new Room (description.get(288), 287, 296, 289, 0, 0, 0, 0); // Exits: North to 287, South to 296, West to 289
        ROOMS[289] = new Room (description.get(289), 286, 295, 290, 288, 0, 0, 0); // Exits: North to 286, South to 295, West to 290, East to 288
        ROOMS[290] = new Room (description.get(290), 291, 294, 0, 289, 0, 0, 0); // Exits: North to 291, South to 294, East to 289
        ROOMS[291] = new Room (description.get(291), 292, 290, 297, 286, 0, 0, 127); // Exits: North to 292, South to 290, West to 297, East to 286. Contains a chainmail armored skeleton (#127)
        ROOMS[292] = new Room (description.get(292), 293, 291, 0, 284, 0, 0, 0); // Exits: North to 293, South to 291, East to 284
        ROOMS[293] = new Room (description.get(293), 310, 292, 0, 285, 0, 0, 0); // Exits: North to 310, South to 292, East to 285
        ROOMS[294] = new Room (description.get(294), 290, 307, 0, 295, 0, 0, 0); // Exits: North to 290, South to 307, East to 295
        ROOMS[295] = new Room (description.get(295), 289, 308, 294, 296, 0, 0, 0); // Exits: North to 289, South to 308, West to 294, East to 296
        ROOMS[296] = new Room (description.get(296), 288, 309, 295, 0, 0, 0, 0); // Exits: North to 288, South to 309, West to 295

        // The tomb
        ROOMS[297] = new Room (description.get(297), 0, 0, 0, 291, 0, 0, 22); // Exits: East to 291. Contains the guardian of the tomb (#22)
        ROOMS[298] = new Room (description.get(298), 299, 300, 302, 297, 0, 0, 127); // Exits: North to 299, South to 300, West to 302, East to 297. Contains a chainmail armored skeleton (#127)
        ROOMS[299] = new Room (description.get(299), 0, 298, 303, 0, 0, 0, 0); // Exits: South to 298, West to 303
        ROOMS[300] = new Room (description.get(300), 298, 0, 301, 0, 0, 0, 0); // Exits: North to 298, West to 301
        ROOMS[301] = new Room (description.get(301), 302, 0, 306, 300, 0, 0, 0); // Exits: North to 302, West to 306, East to 300
        ROOMS[302] = new Room (description.get(302), 303, 301, 305, 298, 0, 0, 126); // Exits: North to 303, South to 301, West to 305, East to 298. Contains Otri the Undying (#126)
        ROOMS[303] = new Room (description.get(303), 0, 302, 304, 299, 0, 0, 0); // Exits: South to 302, West to 304, East to 299
        ROOMS[304] = new Room (description.get(304), 0, 305, 0, 303, 0, 0, 127); // Exits: South to 305, East to 303.  Contains a chainmail armored skeleton (#127)
        ROOMS[305] = new Room (description.get(305), 304, 306, 312, 302, 0, 0, 0); // Exits: North to 304, South to 306, West to 312, East to 302
        ROOMS[306] = new Room (description.get(306), 305, 0, 0, 301, 0, 0, 0); // Exits: North to 305, East to 301

        // The small crypts
        ROOMS[307] = new Room (description.get(307), 294, 0, 0, 0, 0, 0, 128); // Exits: North to 294. Contains a soulless corpse (#128)
        ROOMS[308] = new Room (description.get(308), 295, 0, 0, 0, 0, 0, 0); // Exits: North to 295
        ROOMS[309] = new Room (description.get(309), 296, 0, 0, 0, 0, 0, 128); // Exits: North to 296. Contains a soulless corpse (#128)
        ROOMS[310] = new Room (description.get(310), 0, 293, 0, 0, 0, 0, 128); // Exits: South to 293. Contains a soulless corpse (#128)
        ROOMS[311] = new Room (description.get(311), 0, 285, 0, 0, 0, 0, 130); // Exits: South to 285. Contains a terrified grave robber (#130)

        // The treasure room inside the tomb
        ROOMS[312] = new Room (description.get(312), 0, 0, 0, 305, 0, 0, 211); // Exits: East to 305. Contains an ancient chest (#211)

        // The start of the desert and Ezme's flying carpet shop
        ROOMS[313] = new Room (description.get(313), 0, 314, 278, 316, 0, 0, 0); // Exits: South to 314, West to 278, East to 316
        ROOMS[314] = new Room (description.get(314), 313, 319, 0, 315, 0, 0, 132); // Exits: North to 313, South to 319, East to 315. Contains a scorpion (#132)
        ROOMS[315] = new Room (description.get(315), 316, 318, 314, 0, 0, 0, 29); // Exits: North to 316, South to 318, West to 314. Contains a desert trader (#29)
        ROOMS[316] = new Room (description.get(316), 0, 315, 313, 317, 0, 0, 0); // Exits: South to 315, West to 313, East to 317
        ROOMS[317] = new Room (description.get(317), 0, 0, 316, 0, 0, 0, 23); // Exits: West to 316. Contains Ezme the magic carpet salesman (#23)
        ROOMS[318] = new Room (description.get(318), 315, 0, 319, 0, 0, 0, 131); // Exits: North to 315, West to 319. Contains a rattlesnake (#131)
        ROOMS[319] = new Room (description.get(319), 314, 0, 0, 318, 0, 0, 0); // Exits: North to 314, East to 318

        // The end of the desert and the settlement
        ROOMS[320] = new Room (description.get(320), 0, 323, 0, 321, 0, 0, 0); // Exits: South to 323, East to 321
        ROOMS[321] = new Room (description.get(321), 0, 324, 320, 322, 0, 0, 0); // Exits: South to 324, West to 320, East to 322
        ROOMS[322] = new Room (description.get(322), 0, 325, 321, 0, 0, 0, 131); // Exits: South to 325, West to 321. Contains a rattlesnake (#131)
        ROOMS[323] = new Room (description.get(323), 320, 326, 0, 324, 0, 0, 132); // Exits: North to 320, South to 326, East to 324. Contains a scorpion (#132)
        ROOMS[324] = new Room (description.get(324), 321, 327, 323, 325, 0, 0, 0); // Exits: North to 321, South to 327, West to 323, East to 325
        ROOMS[325] = new Room (description.get(325), 322, 328, 324, 329, 0, 0, 0); // Exits: North to 322, South to 328, West to 324, East to 329
        ROOMS[326] = new Room (description.get(326), 323, 0, 0, 327, 0, 0, 0); // Exits: North to 323, East to 327
        ROOMS[327] = new Room (description.get(327), 324, 336, 326, 328, 0, 0, 28); // Exits: North to 324, South to 336, West to 326, East to 328. Contains Lieutenant Harken (#28)
        ROOMS[328] = new Room (description.get(328), 325, 0, 327, 0, 0, 0, 0); // Exits: North to 325, West to 327
        ROOMS[329] = new Room (description.get(329), 0, 0, 325, 330, 0, 0, 0); // Exits: West to 325, East to 330
        ROOMS[330] = new Room (description.get(330), 331, 332, 329, 334, 0, 0, 0); // Exits: North to 331, South to 332, West to 329, East to 334
        ROOMS[331] = new Room (description.get(331), 0, 330, 0, 335, 0, 0, 0); // Exits: South to 330, East to 335
        ROOMS[332] = new Room (description.get(332), 330, 0, 0, 333, 0, 0, 0); // Exits: North to 330, East to 333
        ROOMS[333] = new Room (description.get(333), 334, 0, 332, 0, 0, 0, 0); // Exits: North to 334, West to 332
        ROOMS[334] = new Room (description.get(334), 335, 333, 330, 0, 0, 0, 27); // Exits: North to 335, South to 333, West to 330. Contains a wounded soldier tending to his comrade (#27)
        ROOMS[335] = new Room (description.get(335), 0, 334, 331, 0, 0, 0, 0); // Exits: South to 334, West to 331

        // The road to the hedge maze
        ROOMS[336] = new Room (description.get(336), 327, 337, 0, 0, 0, 0, 0); // Exits: North to 327, South to 337
        ROOMS[337] = new Room (description.get(337), 336, 338, 0, 0, 0, 0, 0); // Exits: North to 336, South to 338
        ROOMS[338] = new Room (description.get(338), 337, 339, 0, 0, 0, 0, 0); // Exits: North to 337, South to 339
        ROOMS[339] = new Room (description.get(339), 338, 340, 0, 0, 0, 0, 0); // Exits: North to 338, South to 340

        // The hedge maze
        ROOMS[340] = new Room (description.get(340), 339, 341, 343, 0, 0, 0, 0); // Exits: North to 339, South to 341, West to 343
        ROOMS[341] = new Room (description.get(341), 340, 0, 342, 0, 0, 0, 0); // Exits: North to 340, West to 342
        ROOMS[342] = new Room (description.get(342), 0, 0, 0, 341, 0, 0, 0); // Exits: East to 341
        ROOMS[343] = new Room (description.get(343), 0, 0, 344, 340, 0, 0, 0); // Exits: West to 344, East to 340
        ROOMS[344] = new Room (description.get(344), 0, 345, 0, 343, 0, 0, 0); // Exits: South to 345, East to 343
        ROOMS[345] = new Room (description.get(345), 344, 346, 0, 0, 0, 0, 0); // Exits: North to 344, South to 346
        ROOMS[346] = new Room (description.get(346), 345, 0, 0, 347, 0, 0, 0); // Exits: North to 345, East to 347
        ROOMS[347] = new Room (description.get(347), 0, 0, 346, 348, 0, 0, 0); // Exits: West to 346, East to 348
        ROOMS[348] = new Room (description.get(348), 0, 359, 347, 349, 0, 0, 133); // Exits: South to 359, West to 347, East to 349. Contains the minotaur of the maze (#133)
        ROOMS[349] = new Room (description.get(349), 350, 354, 348, 0, 0, 0, 0); // Exits: North to 350, South to 354, West to 348
        ROOMS[350] = new Room (description.get(350), 0, 349, 0, 351, 0, 0, 0); // Exits: South to 349, East to 351
        ROOMS[351] = new Room (description.get(351), 352, 0, 350, 0, 0, 0, 0); // Exits: North to 352, West to 350
        ROOMS[352] = new Room (description.get(352), 0, 351, 353, 0, 0, 0, 0); // Exits: South to 351, West to 353
        ROOMS[353] = new Room (description.get(353), 0, 0, 0, 352, 0, 0, 134); // Exits: East to 352. Contains a guard trapped in the maze (#134)
        ROOMS[354] = new Room (description.get(354), 349, 357, 0, 355, 0, 0, 0); // Exits: North to 349, South to 357, East to 355
        ROOMS[355] = new Room (description.get(355), 356, 0, 354, 0, 0, 0, 214); // Exits: North to 356, West to 354. Contains a violet mushroom (#214)
        ROOMS[356] = new Room (description.get(356), 0, 355, 0, 0, 0, 0, 134); // Exits: South to 355. Contains a guard trapped in the maze (#134)
        ROOMS[357] = new Room (description.get(357), 354, 0, 0, 358, 0, 0, 0); // Exits: North to 354, East to 358
        ROOMS[358] = new Room (description.get(358), 0, 0, 357, 0, 0, 0, 135); // Exits: West to 357. Contains a horned satyr (#135)
        ROOMS[359] = new Room (description.get(359), 348, 0, 360, 0, 0, 0, 0); // Exits: North to 348, West to 360
        ROOMS[360] = new Room (description.get(360), 0, 0, 361, 359, 0, 0, 0); // Exits: West to 361, East to 359
        ROOMS[361] = new Room (description.get(361), 0, 362, 0, 360, 0, 0, 135); // Exits: South to 362, East to 360. Contains a horned satyr (#135)
        ROOMS[362] = new Room (description.get(362), 361, 0, 0, 363, 0, 0, 0); // Exits: North to 361, East to 363
        ROOMS[363] = new Room (description.get(363), 0, 0, 362, 364, 0, 0, 0); // Exits: West to 362, East to 364
        ROOMS[364] = new Room (description.get(364), 0, 365, 363, 0, 0, 0, 0); // Exits: South to 365, West to 363

        // The secret path to the moat
        ROOMS[365] = new Room (description.get(365), 364, 366, 0, 0, 0, 0, 0); // Exits: North to 364, South to 366
        ROOMS[366] = new Room (description.get(366), 365, 367, 0, 0, 0, 0, 0); // Exits: North to 365, South to 367
        ROOMS[367] = new Room (description.get(367), 366, 368, 0, 0, 0, 0, 0); // Exits: North to 366, South to 368
        ROOMS[368] = new Room (description.get(368), 367, 0, 0, 369, 0, 0, 0); // Exits: North to 367, East to 369
        ROOMS[369] = new Room (description.get(369), 0, 0, 368, 370, 0, 0, 0); // Exits: West to 368, East to 370

        // The moat
        ROOMS[370] = new Room (description.get(370), 371, 397, 369, 0, 0, 0, 0); // Exits: North 371, South to 397, West to 369
        ROOMS[371] = new Room (description.get(371), 372, 370, 0, 0, 0, 0, 137); // Exits: North to 372, South to 370. Contains a guard wearing a black mask (#137)
        ROOMS[372] = new Room (description.get(372), 373, 371, 0, 0, 0, 0, 0); // Exits: North to 373, South to 371
        ROOMS[373] = new Room (description.get(373), 374, 372, 0, 0, 0, 0, 0); // Exits: North to 374, South to 372
        ROOMS[374] = new Room (description.get(374), 0, 373, 0, 375, 0, 0, 0); // Exits: South to 373, East to 375
        ROOMS[375] = new Room (description.get(375), 0, 0, 374, 376, 0, 0, 0); // Exits: West to 374, East to 376
        ROOMS[376] = new Room (description.get(376), 0, 0, 375, 377, 0, 0, 136); // Exits: West to 375, East to 377. Contains a vampire bat (#136)
        ROOMS[377] = new Room (description.get(377), 0, 0, 376, 378, 0, 0, 0); // Exits: West to 376, East to 378
        ROOMS[378] = new Room (description.get(378), 0, 0, 377, 379, 0, 0, 0); // Exits: West to 377, East to 379
        ROOMS[379] = new Room (description.get(379), 0, 0, 378, 380, 0, 0, 0); // Exits: West to 378, East to 380
        ROOMS[380] = new Room (description.get(380), 0, 381, 379, 0, 0, 0, 136); // Exits: South to 381, West to 379. Contains a vampire bat (#136)
        ROOMS[381] = new Room (description.get(381), 380, 382, 0, 0, 0, 0, 0); // Exits: North to 380, South to 382
        ROOMS[382] = new Room (description.get(382), 381, 383, 0, 0, 0, 0, 137); // Exits: North to 381, South to 383. Contains a guard wearing a black mask (#137)
        ROOMS[383] = new Room (description.get(383), 382, 384, 0, 0, 0, 0, 0); // Exits: North to 382, South to 384
        ROOMS[384] = new Room (description.get(384), 383, 385, 0, 0, 0, 0, 0); // Exits: North to 383, South to 385
        ROOMS[385] = new Room (description.get(385), 384, 386, 0, 0, 0, 0, 0); // Exits: North to 384, South to 386
        ROOMS[386] = new Room (description.get(386), 385, 387, 398, 0, 0, 0, 0); // Exits: North to 385, South to 387, West to 398
        ROOMS[387] = new Room (description.get(387), 386, 388, 0, 0, 0, 0, 137); // Exits: North to 386, South to 388. Contains a guard wearing a black mask (#137)
        ROOMS[388] = new Room (description.get(388), 387, 0, 389, 0, 0, 0, 214); // Exits: North to 387, West to 389. Contains a violet mushroom (#214)
        ROOMS[389] = new Room (description.get(389), 0, 0, 390, 388, 0, 0, 0); // Exits: West to 390, East to 388
        ROOMS[390] = new Room (description.get(390), 0, 0, 391, 389, 0, 0, 0); // Exits: West to 391, East to 389
        ROOMS[391] = new Room (description.get(391), 0, 0, 392, 390, 0, 0, 0); // Exits: West to 392, East to 390
        ROOMS[392] = new Room (description.get(392), 0, 0, 393, 391, 0, 0, 0); // Exits: West to 393, East to 391
        ROOMS[393] = new Room (description.get(393), 0, 0, 394, 392, 0, 0, 136); // Exits: West to 394, East to 392. Contains a vampire bat (#136)
        ROOMS[394] = new Room (description.get(394), 395, 0, 0, 393, 0, 0, 0); // Exits: North to 395, East to 393
        ROOMS[395] = new Room (description.get(395), 396, 394, 0, 0, 0, 0, 0); // Exits: North to 396, South to 394
        ROOMS[396] = new Room (description.get(396), 397, 395, 0, 0, 0, 0, 0); // Exits: North to 397, South to 395
        ROOMS[397] = new Room (description.get(397), 370, 396, 0, 0, 0, 0, 0); // Exits: North to 370, South to 396

        // The hidden passage into the castle
        ROOMS[398] = new Room (description.get(398), 0, 0, 0, 386, 399, 0, 0); // Exits: East to 386, Up to 399
        ROOMS[399] = new Room (description.get(399), 0, 0, 0, 0, 400, 398, 0); // Exits: Up to 400, Down to 398
        ROOMS[400] = new Room (description.get(400), 0, 0, 0, 0, 401, 399, 0); // Exits: Up to 401, Down to 399

        // Inside the castle
        ROOMS[401] = new Room (description.get(401), 0, 0, 0, 402, 0, 400, 0); // Exits: East to 402, Down to 400
        ROOMS[402] = new Room (description.get(402), 403, 404, 401, 405, 0, 0, 0); // Exits: North to 403, South to 404, West to 401, East to 405
        ROOMS[403] = new Room (description.get(403), 0, 402, 0, 0, 0, 0, 0); // Exits: South to 402
        ROOMS[404] = new Room (description.get(404), 402, 0, 0, 0, 0, 0, 0); // Exits: North to 402
        ROOMS[405] = new Room (description.get(405), 0, 0, 402, 406, 0, 0, 0); // Exits: West to 402, East to 406 
        ROOMS[406] = new Room (description.get(406), 407, 0, 405, 0, 0, 0, 0); // Exits: North to 407, West to 405
        ROOMS[407] = new Room (description.get(407), 0, 406, 408, 0, 0, 0, 0); // Exits: South to 406, West to 408
        ROOMS[408] = new Room (description.get(408), 0, 409, 0, 407, 0, 0, 0); // Exits: South to 409, East to 407
        ROOMS[409] = new Room (description.get(409), 408, 0, 0, 410, 0, 0, 0); // Exits: North to 408, East to 410
        ROOMS[410] = new Room (description.get(410), 411, 0, 409, 0, 0, 0, 0); // Exits: North to 411, West to 409
        ROOMS[411] = new Room (description.get(411), 0, 410, 412, 0, 0, 0, 0); // Exits: South to 410, West to 412
        ROOMS[412] = new Room (description.get(412), 0, 413, 0, 411, 0, 0, 0); // Exits: South to 413, East to 411
        ROOMS[413] = new Room (description.get(413), 412, 0, 0, 414, 0, 0, 0); // Exits: North to 412, East to 414
        ROOMS[414] = new Room (description.get(414), 415, 0, 413, 0, 0, 0, 0); // Exits: North to 415, West to 413
        ROOMS[415] = new Room (description.get(415), 0, 414, 416, 0, 0, 0, 0); // Exits: South to 414, West to 416
        ROOMS[416] = new Room (description.get(416), 0, 417, 0, 415, 0, 0, 0); // Exits: South to 417, East to 415

        // The circular room of Zeramus
        ROOMS[417] = new Room (description.get(417), 416, 418, 0, 0, 0, 0, 0); // Exits: North to 416, South to 418
        ROOMS[418] = new Room (description.get(418), 417, 429, 419, 420, 0, 0, 0); // Exits: North to 417, South to 429, West to 419, East to 420
        ROOMS[419] = new Room (description.get(419), 0, 427, 0, 418, 0, 0, 0); // Exits: South to 427, East to 418
        ROOMS[420] = new Room (description.get(420), 0, 421, 418, 0, 0, 0, 0); // Exits: South to 421, West to 418
        ROOMS[421] = new Room (description.get(421), 420, 423, 429, 422, 0, 0, 0); // Exits: North to 420, South to 423, West to 429, East to 422
        ROOMS[422] = new Room (description.get(422), 0, 0, 421, 0, 0, 0, 0); // Exits: West to 421
        ROOMS[423] = new Room (description.get(423), 421, 0, 424, 0, 0, 0, 0); // Exits: North to 421, West to 424
        ROOMS[424] = new Room (description.get(424), 429, 425, 426, 423, 0, 0, 0); // Exits: North to 429, South to 425, West to 426, East to 423
        ROOMS[425] = new Room (description.get(425), 424, 0, 0, 0, 0, 0, 0); // Exits: North to 424
        ROOMS[426] = new Room (description.get(426), 427, 0, 0, 424, 0, 0, 0); // Exits: North to 427, East to 424
        ROOMS[427] = new Room (description.get(427), 419, 426, 428, 429, 0, 0, 0); // Exits: North to 419, South to 426, West to 428, East to 429
        ROOMS[428] = new Room (description.get(428), 0, 0, 0, 427, 0, 0, 0); // Exits: East to 427

        // Zeramus
        ROOMS[429] = new Room (description.get(429), 418, 424, 427, 421, 0, 0, 138); // Exits: North to 418, South to 424, West to 427, East to 421. Contains Zeramus (#138)

        // The tutorial garden
        ROOMS[430] = new Room (description.get(430), 0, 431, 433, 6, 0, 0, 0); // Exits: South to 431, West to 433, East to 6
        ROOMS[431] = new Room (description.get(431), 430, 0, 432, 0, 0, 0, 0); // Exits: North to 430, West to 432
        ROOMS[432] = new Room (description.get(432), 433, 0, 0, 431, 0, 0, 139); // Exits: North to 433, East to 431. Contains a practice doll (#139)
        ROOMS[433] = new Room (description.get(433), 0, 432, 0, 430, 0, 0, 201); // Exits: South to 432, East to 430. Contains a marble fountain (#201)

    }
    
    //--------------------------------------------------------------------------
    // Sets up the Array List of Room descriptions and adds all the room
    // descriptions from rooms.txt
    //--------------------------------------------------------------------------
    private void generateRooms() {
        try {	
            Scanner scanFile = new Scanner (new File ("rooms.db")); // Contains the room descriptions, in the root folder of this project

            description.add("Room #0 -If you see this, it's a bug. Please send an e-mail to robin.neko@gmail.com  and report it-"); // Adds the room #0 to the ArrayList. This room is not used in the game. Do not remove this line.

            // Adds the room descriptions in order from room.txt to the ArrayList description
            while (scanFile.hasNextLine()) {
                description.add(scanFile.nextLine());
            }

            scanFile.close();		
        }
        catch (FileNotFoundException e) { // (If the rooms.db does not exist)
            System.out.println ("rooms.db file not found! Please e-mail robin.fjarem@gmail.com if you need help resolving this. \n\n");
            System.err.println(e.getMessage());
            //System.exit(1);
        }
    }
    
    public Room[] getRooms() {
        return ROOMS;
    }
}
