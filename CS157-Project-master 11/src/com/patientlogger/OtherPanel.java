package com.patientlogger;

import java.awt.CardLayout;
import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @title	OtherPanel Class
 * @author 	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	Builds the Other panel.
 */
public class OtherPanel extends JPanel
{
	// Declare all of the needed variables.
	private static final long serialVersionUID = 1L;
	JTabbedPane mainPane;
	Connection conn;
	
	/**
	 * @title	OtherPanel Method
	 * @param 	c - Connection to the database.
	 * @desc	Sets the connection then builds the panel.
	 */
	public OtherPanel(Connection c)
	{
		this.conn = c;
		setLayout(new CardLayout());
		buildPanel();
	}
	
	/**
	 *	@title	buildPanel
	 *	@desc	Local method used to build this panel, puts the components in the correct place.
	 */
	private void buildPanel()
	{
		// Create the tabbed pane.
		mainPane = new JTabbedPane();
		
		// Create the panels within.
		LocationDataPanel locationDataPanel = new LocationDataPanel(conn);
		DemographicsDataPanel demographicsDataPanel = new DemographicsDataPanel(conn);
		
		// Add these panels to the pane.
		mainPane.addTab("Location Data", null, locationDataPanel, "Add Location Data");
		mainPane.addTab("Demographics Data", null, demographicsDataPanel, "Add Demographics Data");
		
		// Add the main pane to the patients panel.
		add(mainPane);
	}
	
	/**
	 * @title	refresh
	 * @desc	Rebuilds the tab.
	 */
	public void refresh()
	{
		remove(mainPane);
		buildPanel();
	}
}
