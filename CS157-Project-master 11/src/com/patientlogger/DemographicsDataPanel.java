package com.patientlogger;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * @title	AddNewVisitPanel
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This class is an extension of JPanel
 */
public class DemographicsDataPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	Connection conn;
	
	JScrollPane occupationScroll, educationScroll, workScroll;
	JTable occupationTable, educationTable, workTable;
	JButton addOccupationButton, addEducationButton, addWorkButton,
			editOccupationButton, editEducationButton, editWorkButton;
	
	/**
	 *	@title	DemographicsDataPanel
	 *  @desc	constructor, builds panel
	 *  @param 	c - Is the connection to the database.
	 */
	public DemographicsDataPanel(Connection c)
	{
		conn = c;
		buildPanel();
	}
	
	/**
	 * @title	buildPanel
	 * @desc	Builds the demographics data panel.
	 */
	private void buildPanel()
	{
		// Set the layout
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Create all of the components.
		occupationTable = new JTable();
		educationTable = new JTable();
		workTable = new JTable();
		
		occupationScroll = new JScrollPane(occupationTable);
		educationScroll = new JScrollPane(educationTable);
		workScroll = new JScrollPane(workTable);
		
		addOccupationButton = new JButton("Add Occupation");
		editOccupationButton = new JButton("Edit Occupation");
		addEducationButton = new JButton("Add Education");
		editEducationButton = new JButton("Edit Education");
		addWorkButton = new JButton("Add Work");
		editWorkButton = new JButton("Edit Work");
		
		// Add all the needed action listeners the buttons.
		addOccupationButton.addActionListener(e -> {
			try {
				addOccupation();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		addEducationButton.addActionListener(e -> {
			try {
				addEducation();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		addWorkButton.addActionListener(e -> {
			try {
				addWork();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editOccupationButton.addActionListener(e -> {
			try {
				editOccupation();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editEducationButton.addActionListener(e -> {
			try {
				editEducation();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editWorkButton.addActionListener(e -> {
			try {
				editWork();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		// Fill the frame with needed components.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 175;
		c.ipady = 250;
		add(occupationScroll, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 175;
		c.ipady = 250;
		add(educationScroll, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 175;
		c.ipady = 250;
		add(workScroll, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addOccupationButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editOccupationButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addEducationButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editEducationButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addWorkButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editWorkButton, c);
		
		// Build the tables.
		try 
		{
			buildTables();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @title	buildTables
	 * @throws 	SQLException
	 * @desc	Builds and populates the tables of the panel.
	 */
	public void buildTables() throws SQLException
	{
		// First create needed components.
		String query = "";
		String[] columnNames = {};
		Statement stmt;
		ResultSet rset;
		DefaultTableModel dtm;
		
		// First zipcodes
		query = "SELECT name FROM occupations WHERE id > 0 ORDER BY name;";
		columnNames = new String[] {"Occupations"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Pull data
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set table
		occupationTable.setModel(dtm);
		occupationTable.setAutoCreateRowSorter(true);
		
		// Second  cities
		query = "SELECT name FROM educational_degrees WHERE id > 0  ORDER BY name;";
		columnNames = new String[] {"Educational Degrees"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Pull data
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set table
		educationTable.setModel(dtm);
		educationTable.setAutoCreateRowSorter(true);
		
		// Third states
		query = "SELECT CONCAT(abbreviation, '-', name) FROM work_statuses WHERE id > 0  ORDER BY name;";
		columnNames = new String[] {"Work Status"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Pull data
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set table
		workTable.setModel(dtm);
		workTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * @title	addOccupation
	 * @throws 	SQLException
	 * @desc	Allows the user to input an occupation into the database.
	 */
	public void addOccupation() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Occupation: ");
		JTextField nameField = new JTextField(10);
		String name;
		
		// Build the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameField);
		
		// Probe the user.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter New Occupation", JOptionPane.OK_CANCEL_OPTION);
		
		// Get the result if they said OK, return if they canceled.
		if(result == JOptionPane.OK_OPTION)
		{
			name = nameField.getText();
		}
		else
		{
			return;
		}
		
		String query = "SELECT * FROM occupations WHERE name = '" + name + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		// Check to see if the occupation already exists.
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No occupation is here, continue.
			}
			else
			{
				return;
			}
		}

		// Add it to the database.
		query = "INSERT INTO occupations(id, name) VALUES('" + (occupationTable.getModel().getRowCount() + 1) + "', '" + name + "');";

		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	addEducation
	 * @throws 	SQLException
	 * @desc	Polls the user to add an education field to the database.
	 */
	private void addEducation() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Education: ");
		JTextField nameField = new JTextField(10);
		String name;
		
		// Create the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameField);
		
		// Probe the user for input.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter New Education", JOptionPane.OK_CANCEL_OPTION);
		
		// Grab the input if they said ok, cancel if not.
		if(result == JOptionPane.OK_OPTION)
		{
			name = nameField.getText();
		}
		else
		{
			return;
		}
		
		String query = "SELECT * FROM educational_degrees WHERE name = '" + name + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		// Check to see if the degree exists already.
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No degree is here, continue.
			}
			else
			{
				return;
			}
		}

		// Insert into the database.
		query = "INSERT INTO educational_degrees(id, name) VALUES('" + (educationTable.getModel().getRowCount() + 1) + "', '" + name + "');";

		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	addWork
	 * @throws 	SQLException
	 * @desc	Prompts the user to add a different type of work status.
	 */
	private void addWork() throws SQLException
	{
		// Create components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Work Status: ");
		JLabel abbLabel = new JLabel("Abbreviation: ");
		JTextField nameField = new JTextField(10);
		JTextField abbField = new JTextField(10);
		String name, abb;
		
		// Create the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(abbLabel);
		panel.add(abbField);
		
		// Probe for data.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Work Status and Abbreviation", JOptionPane.OK_CANCEL_OPTION);
		
		// If they gave data then pull it, if not then return.
		if(result == JOptionPane.OK_OPTION)
		{
			name = nameField.getText();
			abb = (String) abbField.getText();
		}
		else
		{
			return;
		}
		
		String query = "SELECT * FROM work_statuses WHERE name = '" + name + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		// Check to see if the work status exists already.
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No work status is here, continue.
			}
			else
			{
				return;
			}
		}
		
		// Push the work status to the database.
		query = "INSERT INTO work_statuses(id, name, abbreviation) VALUES('" + (workTable.getModel().getRowCount() + 1) + "', '" + name + "', '" + abb + "');";
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	editOccupation
	 * @throws 	SQLException
	 * @desc	Prompt the user to change the name of the occupation.
	 */
	private void editOccupation() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Occupation: ");
		JLabel abbLabel = new JLabel("New Name: ");
		JComboBox<String> nameChoices;
		JTextField abb = new JTextField(10);
		String[] nameList = new String[occupationTable.getModel().getRowCount()];
		
		// Fill the list.
		for(int x = 0; x < occupationTable.getModel().getRowCount(); x++)
		{
			nameList[x] = ((String)occupationTable.getModel().getValueAt(x, 0));
		}
		
		nameChoices = new JComboBox<String>(nameList);

		// Create the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameChoices);
		panel.add(abbLabel);
		panel.add(abb);
		
		// Probe the user for data.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Occupation and New Name", JOptionPane.OK_CANCEL_OPTION);
		
		// If they sent data, then push it to the database.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE occupations SET name = '" + abb.getText() + "' WHERE name = '" + nameChoices.getSelectedItem() + "';";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
		}
		else
		{
			return;
		}

		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	editEducation
	 * @throws 	SQLException
	 * @desc	Prompts the user to edit the name of the educational degree.
	 */
	private void editEducation() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Education: ");
		JLabel abbLabel = new JLabel("New Name: ");
		JComboBox<String> nameChoices;
		JTextField abb = new JTextField(10);
		String[] nameList = new String[educationTable.getModel().getRowCount()];
		
		// Pull the list.
		for(int x = 0; x < educationTable.getModel().getRowCount(); x++)
		{
			nameList[x] = ((String)educationTable.getModel().getValueAt(x, 0));
		}
		
		nameChoices = new JComboBox<String>(nameList);

		// Build the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameChoices);
		panel.add(abbLabel);
		panel.add(abb);
		
		// Probe for data.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Education and New Name", JOptionPane.OK_CANCEL_OPTION);
		
		// If they sent data, apply it to the database.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE educational_degrees SET name = '" + abb.getText() + "' WHERE name = '" + nameChoices.getSelectedItem() + "';";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
		}
		else
		{
			return;
		}

		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	editWork
	 * @throws 	SQLException
	 * @desc	Prompt the user for data to edit the work status.
	 */
	private void editWork() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Work Status: ");
		JLabel abbLabel = new JLabel("New Name: ");
		JComboBox<String> nameChoices;
		JTextField abb = new JTextField(10);
		String[] nameList = new String[workTable.getModel().getRowCount()];
		
		// Fill the name list.
		for(int x = 0; x < workTable.getModel().getRowCount(); x++)
		{
			nameList[x] = ((String)workTable.getModel().getValueAt(x, 0)).split("-")[1];
		}
		
		nameChoices = new JComboBox<String>(nameList);

		// Create the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameChoices);
		panel.add(abbLabel);
		panel.add(abb);
		
		// Probe the user for data.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Work Status and New Name", JOptionPane.OK_CANCEL_OPTION);
		
		// If the user gave input, then apply the changes to the database.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE work_statuses SET name = '" + abb.getText() + "' WHERE name = '" + nameChoices.getSelectedItem() + "';";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
		}
		else
		{
			return;
		}

		// Build the tables.
		buildTables();
	}
}
