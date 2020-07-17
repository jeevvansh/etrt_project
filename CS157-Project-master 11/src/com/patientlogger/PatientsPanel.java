package com.patientlogger;

import java.awt.CardLayout;
import java.sql.Connection;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * @title	PatientsPanel Class
 * @author 	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	Builds the patients panel.
 */
public class PatientsPanel extends JPanel
{
	// Declare all of the needed variables.
	private static final long serialVersionUID = 1L;
	JTabbedPane mainPane;
	Connection conn;
	
	/**
	 * @title	PatientsPanel Method
	 * @param 	c - Connection to the database.
	 * @desc	Sets the connection then builds the panel.
	 */
	public PatientsPanel(Connection c)
	{
		this.conn = c;
		setLayout(new CardLayout());
		buildPanel();
	}
	
	/**
	 * @title	buildPanel
	 * @desc	Builds patients panel.
	 */
	private void buildPanel()
	{
		// Create the JTabbedPane.
		mainPane = new JTabbedPane();
		
		// Create the inner panels.
		AddNewPatientsPanel addNewPatientsPanel = new AddNewPatientsPanel(conn);
		ViewPatientsPanel viewPatientsPanel = new ViewPatientsPanel(conn);
		LookupPatientPanel lookupPatientsPanel = new LookupPatientPanel(conn);
		
		// Add these panels to the main pane.
		mainPane.addTab("Add New Patient", null, addNewPatientsPanel, "Add A New Patient");
		mainPane.addTab("View/Edit Patients", null, viewPatientsPanel, "View/Edit Patients");
		mainPane.addTab("Lookup Patients", null, lookupPatientsPanel, "Lookup Patients");
		
		// Add the main pane to the patients panel.
		add(mainPane);
	}
	
	/**
	 * @title	refresh
	 * @desc	Rebuilds the panel.
	 */
	public void refresh()
	{
		remove(mainPane);
		buildPanel();
	}
}
