//***************************************************************************
// InfoPanel.java
//
// Creates and handles the info panel of the GUI
//***************************************************************************

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.*;

import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class InfoPanel extends JPanel
{
	Character player = new Character();
	
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	private JPanel panel5;
	
	private JLabel nameLabel;
	private JLabel levelLabel;
	private JLabel expLabel;
	private JLabel strLabel;
	private JLabel hpLabel;
	private JLabel goldLabel;
	
	private JButton mapButton;
	private JButton cmdButton;
	private JButton helpButton;
	private JButton clearButton;
	private JButton creditsButton;
	
	private JList invList;
	private JScrollPane scrollPane;
	
	String name = "";
	String level = "";
	String exp = "";
	String strength = "";
	String health = "";
	String maxHealth = "";
	String gold = "";
	
	ArrayList<String> inventory = new ArrayList<String>();
	InventoryModel model = new InventoryModel(inventory);
	
	//----------------------------------------------------------------
	// Constructor: Sets up a panel displaying info
	//
	// TODO:
	// Add a button for displaying invs
	// Add scrolling pane for inventory
	// Add a button for displaying maps
	// Change color of titles
	// Double-clicking items for use in the game
	//----------------------------------------------------------------
	public InfoPanel ()
	{
		loadData();
		
		ButtonListener listener = new ButtonListener(); // Creates the listener
		
		createInv();
		
		name = player.getName();
		level = Integer.toString(player.getLevel());
		exp = Integer.toString(player.getExperience());
		health = Integer.toString(player.getHealth());
		maxHealth = Integer.toString(player.getMaxHealth());
		
		// Set up main panel
		setLayout (new BoxLayout (this, BoxLayout.Y_AXIS));
		setBorder (BorderFactory.createEmptyBorder(8, 8, 8, 8));
		//setBackground (panelColor);
		setPreferredSize (new Dimension(300, 100));
		
		Color panelColor = new Color (72, 77, 82);
		
		// Set up sub-panels
		panel1 = new JPanel();
		panel1.setBackground (panelColor);
		panel1.setBorder(BorderFactory.createTitledBorder("Player"));
		panel1.setLayout (new GridLayout(3, 0)); // Default: 3, 0
		
		panel2 = new JPanel();
		panel2.setBackground (panelColor);
		panel2.setBorder(BorderFactory.createTitledBorder("Attributes"));
		panel2.setLayout (new GridLayout(2, 0)); // Default: 2, 0
		
		panel3 = new JPanel();
		panel3.setBackground (panelColor);
		panel3.setBorder(BorderFactory.createTitledBorder("Gold"));
		panel3.setLayout (new GridLayout(1, 1)); // Default: 1,1
		
		panel4 = new JPanel();
		panel4.setBackground (panelColor);
		panel4.setForeground (Color.white);
		panel4.setBorder(BorderFactory.createTitledBorder("Inventory"));
		panel4.setLayout (new GridLayout(1, 1)); // Default: 5 (22 works)
		
		panel5 = new JPanel();
		panel5.setBackground (panelColor);
		panel5.setBorder(BorderFactory.createTitledBorder("Help"));
		panel5.setLayout (new GridLayout(5, 1));
		
		// Set up labels, lists and buttons
		nameLabel = new JLabel ("Name: " + name);
		nameLabel.setForeground(Color.white);
		levelLabel = new JLabel ("Level: " + level);
		levelLabel.setForeground(Color.white);
		expLabel = new JLabel ("Exp: " + exp + "/" + player.getNextLevelExperience());
		expLabel.setForeground(Color.white);
		
		strLabel = new JLabel ("Strength: " + strength);
		strLabel.setForeground(Color.white);
		hpLabel = new JLabel ("Health: " + health + "/" + maxHealth);
		hpLabel.setForeground(Color.white);
		
		goldLabel = new JLabel (gold + "g");
		goldLabel.setForeground(Color.white);
		
		invList = new JList (model); // Default: (model)
		invList.setBackground(panelColor);
		invList.setForeground(Color.white);
		
		scrollPane = new JScrollPane(invList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		mapButton = new JButton ("Maps");
		cmdButton = new JButton ("Commands");
		helpButton = new JButton ("Help");
		clearButton = new JButton ("Clear");
		creditsButton = new JButton ("Credit");
		
		// Add labels to sub-panels
		panel1.add (nameLabel);
		panel1.add (levelLabel);
		panel1.add (expLabel);
		panel2.add (strLabel);
		panel2.add (hpLabel);
		panel3.add (goldLabel);
		panel4.add (scrollPane);
		panel5.add (mapButton);
		panel5.add (cmdButton);
		panel5.add (helpButton);
		panel5.add (clearButton);
		panel5.add (creditsButton);
		
		mapButton.addActionListener(listener);
		cmdButton.addActionListener(listener);
		helpButton.addActionListener(listener);
		clearButton.addActionListener(listener);
		creditsButton.addActionListener(listener);
			
		// Add sub-panels to main-panel
		add (panel1);
		add (panel2);
		add (panel3);
		add (panel4);
		add (panel5);
	}
	
	//--------------------------------------------------------------------------
	// Loads the character data
	//--------------------------------------------------------------------------
	private void loadData () {
                // Tries to read data
		try {
                    FileInputStream fileIn = new FileInputStream ("Character.sav");
                    ObjectInputStream in = new ObjectInputStream (fileIn);
                    player = (Character) in.readObject();
                    in.close();
                    fileIn.close();
                    
		}
		catch (IOException e) {
                    try {
                        System.err.println("Creating new empty save file");
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Character.sav"));
                        out.writeObject(player);
                        out.close();
                    } 
                    catch (IOException x) {
                        x.printStackTrace();
                    }
		}
		catch (ClassNotFoundException e) {
                    System.out.println ("Character class not found");
                    e.printStackTrace();
                    
                    return;
		}
		//player.loadInventorySize(); // Workaround, see Character.java. Deactivated for now. This should not be allowed to alter the other classes
	}
	
	//--------------------------------------------------------------------------
	// Refresh on-screen data
	//
	// CAUTION: Don't forget to uncomment backgroundSave() in Game.java
	//--------------------------------------------------------------------------
	public void refreshData ()
	{
		loadData(); // If it stops working, comment away this and start the game, create a new character and then remove the comment
		nameLabel.setText ("Name: " + player.getName());
		levelLabel.setText ("Level: " + player.getLevel());
		expLabel.setText ("Exp: " + player.getExperience() + "/" + player.getNextLevelExperience());
		strLabel.setText ("Strength: " + player.getStrength());
		hpLabel.setText ("Health: " + player.getHealth() + "/" + player.getMaxHealth());
		goldLabel.setText (player.getGold() + "g");

		// Do not change or the inventory handling will stop functioning
		inventory.clear();
		model.removeAllElements();
		createInv();
		model.update();

	}
	
	//--------------------------------------------------------------------------
	// Add values to inventory for displaying
	// This causes ArrayIndexOutOfBoundsException
	//--------------------------------------------------------------------------
	private void createInv() 
	{
		int counter = 0;
		
		while (counter <= player.getItemLimit() -1)
		{
			if (player.getItem(counter).getPlayerHas() >= 1)
			{
				//model.addElement(player.getItemDescription(counter) + " (" + player.getItem(counter).getPlayerHas() + ")");
				inventory.add (player.getItemDescription(counter) + " (" + player.getItem(counter).getPlayerHas() + ")");
			}
			counter++;
		}
	}
	
	//*********************************************************************
	// Represents the listener for all buttons
	//*********************************************************************
	private class ButtonListener implements ActionListener
	{
		//--------------------------------------------------------------------
		// Performs action when pressed depending on which button was pressed
		//--------------------------------------------------------------------
		public void actionPerformed (ActionEvent event)
		{
			Object source = event.getSource();
			
			// The map button displays a dialogue box displaying a set of alternatives
			// If OK is pressed it displays the corresponding map image, if canceled it does nothing
			if (source == mapButton)
			{	
				JFrame mapFrame = new JFrame();
				mapFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
	        	ImageIcon mapImage = new ImageIcon();
	        	JScrollPane mapScrollPane = new JScrollPane();
	        	
	        	String selectedMap = "";
	        	
				String[] mapSelection = {"1. Forest, Road, Goblin Dungeon, Halin Village", // Object[] mapSelection = {"1. Forest, Road, Goblin Dungeon, Halin Village",
						"2. Forest House - 2nd Floor",
						"3. Goblin Dungeon Basement",
						"4. Zulah, Marsh, Graveyard, Start of Desert",
						"5. Crypts",
						"6. End of Desert, Maze",
						"7. Castle Moat",
						"8. Inside Castle",
						"9. Crystal Room"};
				
				selectedMap = (String)JOptionPane.showInputDialog(mapFrame, "Select zone:", "Maps", JOptionPane.QUESTION_MESSAGE, null, mapSelection, mapSelection[0]);
				                 
				try
				{
					switch (selectedMap)
					{
						case ("0"):
							break;
						case ("1. Forest, Road, Goblin Dungeon, Halin Village"): 		        	
							mapImage = new ImageIcon("maps/1.png");
							break;
						case ("2. Forest House - 2nd Floor"): 		        	
							mapImage = new ImageIcon("maps/2.png");
							break;
						case ("3. Goblin Dungeon Basement"): 		        	
							mapImage = new ImageIcon("maps/3.png");
							break;
						case ("4. Zulah, Marsh, Graveyard, Start of Desert"): 		        	
							mapImage = new ImageIcon("maps/4.png");
							break;
						case ("5. Crypts"): 		        	
							mapImage = new ImageIcon("maps/5.png");
							break;
						case ("6. End of Desert, Maze"): 		        	
							mapImage = new ImageIcon("maps/6.png");
							break;
						case ("7. Castle Moat"): 		        	
							mapImage = new ImageIcon("maps/7.png");
							break;
						case ("8. Inside Castle"): 		        	
							mapImage = new ImageIcon("maps/8.png");
							break;
						case ("9. Crystal Room"): 		        	
							mapImage = new ImageIcon("maps/9.png");
							break;
						default: 
							break;
					}
					mapScrollPane = new JScrollPane(new JLabel (mapImage));
					
		            mapFrame.setPreferredSize(new Dimension (640, 480));
		        	mapFrame.getContentPane().add(mapScrollPane);
		        	mapFrame.pack();
		        	mapFrame.setVisible(true);
				}
				catch (NullPointerException e)
				{
					
				}
			}
			else
				if (source == cmdButton)
				{
					JLabel navigation = new JLabel ("Navigation:");
					navigation.setForeground(Color.red);
					JOptionPane.showMessageDialog(new JFrame(), ("<html><span style='color:green'>Navigation:</span></html>"
							+ "\nnorth, south, west, east, up, down"
							+ "\nn, s, w, e, u, d"
							+ "\nlook, l"
							+ "\nexits"

							+ "\n"
							+ "\n<html><span style='color:red'>Information:</span></html>"
							+ "\nscore, sc"
							+ "\ngold"
							+ "\nconsider, cons"
							+ "\ninventory, inv, i"
							+ "\ninvs (Displays the short description of the items for the \"use\" command)"
							+ "\nexperience, exp"
							
							+ "\n"
							+ "\n<html><span style='color:blue'>Actions:</span></html>"
							+ "\nuse <item from inventory>"
							+ "\nin, interact (To interact with an object in the room)"
							+ "\ntalk"
							+ "\nkill, k"
							
							+ "\n"
							+ "\n<html><span style='color:orange'>Other:</span></html>"
							+ "\ncredit"
							+ "\nclear"
							+ "\nsave (Not needed in this version)"
							+ "\nquit"));
				}
				else
					if (source == helpButton)
						JOptionPane.showMessageDialog(new JFrame(), "Type \"invs\" to display the short item descriptions for the \"use\" command. For example \"use potion\"."
								+ "\n\nType \"in\" to interact with an object in the room (For example picking something up from the ground)."
								+ "\n\n If you get completely stuck, send an e-mail to robin.neko@gmail.com for hints.");
					else
						if (source == clearButton)
							JOptionPane.showMessageDialog(new JFrame(), "Type \"clear\" to clear the screen. This helps if the game starts to run slow.");			
						else
							if (source == creditsButton)
								JOptionPane.showMessageDialog(new JFrame(), "\nCode: Robin Fjärem"
										+ "\nStory and maps: Robin Fjärem"
										+ "\nhttp://robinsuu.com"
										+ "\n\nMusic: http://rolemusic.sawsquarenoise.com/"
										+ "\nLicensed under http://creativecommons.org/licenses/by/4.0/"		
										+ "\n\nTesters:"
										+ "\nMarcus Folgert"
										+ "\nEmil Ehrs"
										+ "\nFrancois Larouche (Special thanks for your coding advice!)"
										+ "\nEmanuel Högild"
										+ "\n\nOther:"
										+ "\nhttp://crownlessking.com (GUI io console class and GUI coding advice)"
										+ "\nSaad Benbouzid @ stackoverflow (Line-break code snippet)"
										+ "\nhttp://lauzet.com/argon/ (Online mapping tool)"
										+ "\nhttp://www.javazoom.net/jlgui/api.html (MP3 player)");
		}
	}
	
	//--------------------------------------------------------------------------
	// A custom model for handling the inventory ArrayList and JList
	//--------------------------------------------------------------------------
	private class InventoryModel extends DefaultListModel
	{
	     private ArrayList items;

	    public InventoryModel(ArrayList testing) 
	    {
	    	this.items = testing;
	    }

	    @Override
	    public Object getElementAt(int index) 
	    {
	        return items.get(index);
	    }

	    @Override
	    public int getSize()
	    {
	        return items.size();
	    }

	    public void update() 
	    {
	        this.fireContentsChanged(this, 0, items.size() - 1);
	    }
	}
}