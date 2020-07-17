package com.patientlogger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * @title 	ViewPatientsPanel Class
 * @author 	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This class is an extension of JPanel. Builds panel to view patients.
 */
public class ViewPatientsPanel extends JPanel
{
	// Create all of the needed variables.
	private static final long serialVersionUID = 1L;

	Connection conn;
	
	//Used for drop down menus
	JComboBox<String> searchCriteria;
	
	//initializing buttons for use
	JButton viewPatientButton, editPatientButton, deletePatientButton, addNewVisitButton, showCurrentVisitButton,
			searchButton, refreshButton;
	
	//Used for fields with multiple entry boxes
	JPanel patientsPaneButtons, patientsPanel;
	
	//Pane that can scroll
	JScrollPane patientsScrollPane;
	
	//text fields to get raw input from entry	
	JTextField searchBox;
	
	//Sets table w/ all patients
	JTable patientTable;
	
	//Options for drop down menu
	final String[] searchOptions = {"Filter Options", "THC Number", "Name", "City"};
	
	/**
	 * @title	ViewPatientsPanel
	 * @desc	constructor, builds panel
	 * @param 	c - Is the connection to the database.
	 */
	public ViewPatientsPanel(Connection c)
	{
		this.conn = c;
		buildPanel();
	}

	/**
	 *	@title	buildPanel
	 *	@desc	Local method used to build this panel, everything from assigning listeners to putting the
	 *			components in the correct place.
	 */
	private void buildPanel()
	{
		// Sets the layout and creates the constraints of the panel.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Creates all of the needed components of the panel.
		searchCriteria = new JComboBox<String>(searchOptions);
		viewPatientButton = new JButton("View");
		editPatientButton = new JButton("Edit");
		deletePatientButton = new JButton("Delete");
		addNewVisitButton = new JButton("Add Visit");
		showCurrentVisitButton = new JButton("Current Visit");
		searchButton = new JButton("Search");
		refreshButton = new JButton("Refresh");
		patientTable = new JTable();
		searchBox = new JTextField(10);
		
		// Listener adds the ability to add a new visit for the patient.
		addNewVisitButton.addActionListener(e -> newVisit());
		
		// Listener adds the ability to show the most recent visit.
		showCurrentVisitButton.addActionListener(e -> {
			try {
				currentVisit();
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		});
		
		// Listener adds the ability to refresh the table.
		refreshButton.addActionListener(e -> {
			try 
			{
				populate();
			} 
			catch (SQLException e2) 
			{
				e2.printStackTrace();
			}
		});
		
		//Listener adds ability to search for a patient.
		searchButton.addActionListener(e -> {
			try 
			{
				search();
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		});
		
		//Listener adds the ability to delete a patient.
		deletePatientButton.addActionListener(e -> {
			try 
			{
				delete();
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		});
		
		// Listener adds the ability to edit a patient.
		editPatientButton.addActionListener(e -> edit());
		
		// Populate the patient table.
		try 
		{
			populate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// Create the scrollable pane.
		patientsScrollPane = new JScrollPane(patientTable);
		
		// The following adds all of the components to the panel.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(refreshButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(searchCriteria, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(searchBox, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(searchButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.gridheight = 20;
		c.ipady = 250;
		add(patientsScrollPane, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(viewPatientButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(editPatientButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(deletePatientButton, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(addNewVisitButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(showCurrentVisitButton ,c);
	}
	
	/**
	 * @title	search
	 * @throws 	SQLException - If the database has problems pulling the patients.
	 */
	private void search() throws SQLException
	{
		// If no search query, refresh the table.
		if(searchBox.getText().equals(""))
		{
			populate();
			return;
		}
		
		String query;
		
		// Figure out what information to pull, and go off of that (from the search box)
		switch((String)searchCriteria.getSelectedItem())
		{
			// Default will be for THC and select search
			default:
				//looks in database for patient with inputed thc number
				query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', CONCAT(patients.firstname, ' ', patients.lastname) AS 'NAME', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', cities.name AS 'CITY', states.name AS 'STATE', patients.date AS 'DATE' " + 
						   "FROM patients, zipcodes, cities, states " + 
						   "WHERE zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id AND patients.thcnumber = '" + searchBox.getText() + "';";	
				break;
				
			case "Name":
				//looks in database for patient with inputed name
				String[] name = searchBox.getText().split(" ");

				query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', CONCAT(patients.firstname, ' ', patients.lastname) AS 'NAME', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', cities.name AS 'CITY', states.name AS 'STATE', patients.date AS 'DATE' " + 
						   "FROM patients, zipcodes, cities, states " + 
						   "WHERE AND zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id AND patients.firstname = '" + name[0] + "' AND patients.lastname = '" + name[1] + "';";
				break;
			
			case "City":
				//looks in database for patient with inputed city
				query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', CONCAT(patients.firstname, ' ', patients.lastname) AS 'NAME', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', cities.name AS 'CITY', states.name AS 'STATE', patients.date AS 'DATE' " + 
						   "FROM patients, zipcodes, cities, states " + 
						   "WHERE zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id AND cities.name = '" + searchBox.getText() + "';";	
				break;
		}
		
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		// Start building the table.
		String[] columnNames = {"THC#", "Name", "Age", "Gender", "City", "State", "Date Added"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
		
		// Pull all of the needed information.
		while(rset.next())
		{
			String[] data = {rset.getString("THC"), rset.getString("NAME"), getAge(rset.getString("BIRTHDAY")), rset.getString("GENDER"), rset.getString("CITY"), rset.getString("STATE"), rset.getString("DATE")};
			dtm.addRow(data);
		}

		// Configure the table.
		patientTable.setModel(dtm);
		patientTable.setAutoCreateRowSorter(true);
		patientTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		patientTable.getColumnModel().getColumn(2).setPreferredWidth(30);
		patientTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	}
	
	/**
	 * @title	delete
	 * @throws 	SQLException - If the database has problems deleting information.
	 */
	private void delete() throws SQLException
	{
		// Pulls the THC number to be deleted.
		String THCNumber = (String)patientTable.getValueAt(patientTable.getSelectedRow(), 0);
		
		// Deletes the patient from the table.
		PreparedStatement preparedStmt = conn.prepareStatement("DELETE FROM PATIENTS WHERE THCNumber ='" + THCNumber + "';");
		preparedStmt.execute();
		
		// Pulls the new patient information.
		populate();
	}
	
	/**
	 * @title	edit
	 * @desc	Launches new JFrame to edit a patient
	 */
	private void edit()
	{		
		// Build a new edit patient screen.
		JFrame frame = new JFrame("eTRT - Edit Patient");
		frame.add(new EditPatientScreen(conn, (String)patientTable.getValueAt(patientTable.getSelectedRow(), 0)));
		frame.setSize(new Dimension(600, 450));
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//Center the new frame
		frame.setLocation(d.width/2-frame.getSize().width/2, d.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	/**
	 * @title	populate
	 * @desc	gets patient data and fills table
	 * @throws 	SQLException - If the database can't retrieve information.
	 */
	private void populate() throws SQLException
	{
		String query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', CONCAT(patients.firstname, ' ', patients.lastname) AS 'NAME', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', cities.name AS 'CITY', states.name AS 'STATE', patients.date AS 'DATE' " + 
					   "FROM patients, zipcodes, cities, states " + 
					   "WHERE zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id;";		
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);

		String[] columnNames = {"THC#", "Name", "Age", "Gender", "City", "State", "Date Added"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
		
		while(rset.next())
		{
			String[] data = {rset.getString("THC"), rset.getString("NAME"), getAge(rset.getString("BIRTHDAY")), rset.getString("GENDER"), rset.getString("CITY"), rset.getString("STATE"), rset.getString("DATE")};
			dtm.addRow(data);
		}

		patientTable.setModel(dtm);
		patientTable.setAutoCreateRowSorter(true);
		patientTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		patientTable.getColumnModel().getColumn(2).setPreferredWidth(30);
		patientTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	}
	
	/**
	 * @title	getAge
	 * @param 	bday - The date of their birthday in yyyy-mm-dd format.
	 * @return
	 */
	public String getAge(String bday)
	{
		// Declare the different variables.
		int month, day, year, age;
		
		// Pull the birthday of the patient.
		month = Integer.parseInt(bday.substring(5,7));
		day = Integer.parseInt(bday.substring(8,10));
		year = Integer.parseInt(bday.substring(0,4));
		
		// Find today's date.
		LocalDate today = LocalDate.now();
		
		// Find the age in years.
		age = today.getYear() - year;
		
		// Modify the age if the patient had their birthday already this year.
		if(today.getMonthValue() >= month)
		{
			if(today.getMonthValue() == month && today.getDayOfMonth() >= day)
			{
				age++;
			}
		}
		
		// Return the age in a string.
		return "" + age;
	}
	
	/**
	 * @title	newVisit
	 * @desc	Launches the new visit tab with the entered THC number.
	 */
	private void newVisit()
	{
		String thc = (String)patientTable.getValueAt(patientTable.getSelectedRow(), 0);
		
		JFrame frame = new JFrame("eTRT - Edit Visit");
		frame.add(new AddNewVisitPanel(conn, thc));
		frame.setSize(new Dimension(600, 450));
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//Center the new frame
		frame.setLocation(d.width/2-frame.getSize().width/2, d.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	/**
	 * @title	currentVisit
	 * @throws 	SQLException
	 * @desc	Opens the screen for the current visit tab with the given THC.
	 */
	private void currentVisit() throws SQLException
	{
		String thc = (String)patientTable.getValueAt(patientTable.getSelectedRow(), 0);
		String id = null;
		String query = "SELECT MAX(VisitSequence) FROM visits WHERE thcnumber = '" + thc + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		while(rset.next())
			id = rset.getString(1);
		
		JFrame frame = new JFrame("eTRT - Edit Patient");
		frame.add(new EditVisitScreen(conn, id));
		frame.setSize(new Dimension(600, 450));
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//Center the new frame
		frame.setLocation(d.width/2-frame.getSize().width/2, d.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
}
