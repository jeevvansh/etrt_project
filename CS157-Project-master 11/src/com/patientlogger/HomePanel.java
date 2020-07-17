package com.patientlogger;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @title	HomePanel
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This class is an extension of JPanel. Serves as starting page on launch
 */
public class HomePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	GroupLayout layout;

	JLabel welcomeLabel, instructionLabel;
	
	/**
	 * @title	AddNewPatientsPanel
	 * @desc	constructor, builds panel
	 */
	public HomePanel()
	{
		buildPanel();
	}
	
	/**
	 *	@title	buildPanel
	 *	@desc	Local method used to build this panel, puts the components in the correct place.
	 */
	private void buildPanel()
	{			
		// Handle the layout of the panel.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Initialize the label for the main JFrame.
		welcomeLabel = new JLabel("Welcome to eTRT: The Decision Support System for "
								   + "Tinnitus Restraining Therapy");
		instructionLabel = new JLabel("Select a tab above to get started");

		
		// All of the following is to design the panel and put all the components in the correct place.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 2;
		c.gridheight = 3;
		add(welcomeLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 6;
		c.gridwidth = 2;
		c.gridheight = 1;
		add(instructionLabel, c);
	}
}
