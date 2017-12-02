Legend of the Wanderer version 2.1
A classic text game made in Java.

Code and story by: Robin Fjärem
E-mail: robin.fjarem@gmail.com
Website: http://lazycapybara.com

_____________________________________________________
			Credit

Francois Larouche (Coding advice and testing)
Emil Ehrs (Gameplay testing)
Marcus Folgert (Gameplay testing)
Emanuel Högild (Gameplay testing)

http://crownlessking.com (GUI io console class and GUI coding advice)
Saad Benbouzid @ stackoverflow (Line-break code snippet)
http://lauzet.com/argon/ (Online mapping tool)
_____________________________________________________
IMPORTANT: Do not move the rooms.db from the root folder of the game executable.

NOTE:	The game saves your progress automatically.

NOTE: 	Use the 'clear' command if you experience that
	the game is running slowly. This will remove
	all text from the game window. Recommended to
	use once every 20-30 minutes.

NOTE:	If you wish to start a new character, be aware
	that your old file will be overwritten. If you
	want to save it, simply make a copy of the
	Character.sav file and put the copy in another
	location.
_____________________________________________________

An explanation of the game commands:

north, south, west, east, up, down
n, s, w, e, u, d			Navigate
look, l					Displays the current room
exits					Displays the room exits
score, sc				Displays your character sheet
gold					Displays the amount of gold you have
consider, cons				Displays the power difference between you and an enemy
inventory, inv, i			Displays your inventory
invs					Displays your inventory in short descriptions (Used together with "use")
experience, exp				Displays the amount of experience required to level up
use <item from inventory>		Use an item. 
					IMPORTANT: Use this command together with the short description of the item. 
					Type invs to show the short descriptions of your inventory.
interact, in				Interact with an object in the world
talk					Talk with an NPC
kill, k					Attack an NPC
credits					Display the game credits
clear					Clear the screen from text (Use if you experience lag)
save					Save the game (Not needed in this version)
quit					Save and quit the game