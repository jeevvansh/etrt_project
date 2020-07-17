package com.patientlogger;

import java.awt.CardLayout;
import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @title	VisitsPanel Class
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	Builds the VisitsPanel 
 */
public class VisitsPanel extends JPanel
{
	// Declare initial variables.
	private static final long serialVersionUID = 1L;
	Connection conn;
	JTabbedPane mainPane;
	
	/**
	 * @title	VisitsPanel Method
	 * @param 	c - Connection to the database.
	 * @desc	Sets the connection then builds the panel.
	 */
	public VisitsPanel(Connection c)
	{
		conn = c;
		setLayout(new CardLayout());
		buildPanel();
	}
	

	AddNewVisitPanel addNewVisitPanel;
	ViewVisitsPanel viewVisitsPanel;
	/**
	 * @title	buildPanel
	 * @desc	Builds visits panel.
	 */
	private void buildPanel()
	{
		// Create the JTabbedPane.
		mainPane = new JTabbedPane();
		
		// Create the inner tabs.
		AddNewVisitPanel addNewVisitPanel = new AddNewVisitPanel(conn);
		ViewVisitsPanel viewVisitsPanel = new ViewVisitsPanel(conn);
		
		// Add these tabs to the main pane.
		mainPane.addTab("Add New Visit", null, addNewVisitPanel, "Add A New Visit");
		mainPane.addTab("View/Edit Visits", null, viewVisitsPanel, "View/Edit Visits");
		
		//Add the pane to the vists panel.
		add(mainPane);
	}
	
	// Rebuilds the frame.
	public void refresh()
	{
		remove(mainPane);
		buildPanel();
	}
}
