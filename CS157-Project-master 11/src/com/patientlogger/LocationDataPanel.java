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
* @title	LocationDataPanel
* @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
* @desc		This class is an extension of JPanel
*/
public class LocationDataPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	Connection conn;
	
	JScrollPane zipcodeScroll, cityScroll, stateScroll, countryScroll;
	JTable zipcodeTable, cityTable, stateTable, countryTable;
	
	JButton addZipButton, editZipButton, addCityButton, editCityButton, addStateButton, 
			editStateButton, addCountryButton, editCountryButton;
	
	/**
	 *	@title	LocationDataPanel
	 *  @desc	constructor, builds panel
	 *  @param 	c - Is the connection to the database.
	 */
	public LocationDataPanel(Connection c)
	{
		conn = c;
		buildPanel();
	}
	
	/**
	 * @title	buildPanel
	 * @desc	Builds the location data panel.
	 */
	private void buildPanel()
	{
		// Set the layout.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Initialize everything.
		zipcodeTable = new JTable();
		cityTable = new JTable();
		stateTable = new JTable();
		countryTable = new JTable();
		
		zipcodeScroll = new JScrollPane(zipcodeTable);
		cityScroll = new JScrollPane(cityTable);
		stateScroll = new JScrollPane(stateTable);
		countryScroll = new JScrollPane(countryTable);
		
		addZipButton = new JButton("Add Zipcode");
		editZipButton = new JButton("Edit Zipcode");
		addCityButton = new JButton("Add City");
		editCityButton = new JButton("Edit City");
		addStateButton = new JButton("Add State");
		editStateButton = new JButton("Edit State");
		addCountryButton = new JButton("Add Country");
		editCountryButton = new JButton("Edit Country");

		// Add the needed listeners to the buttons.
		addZipButton.addActionListener(e -> {
			try {
				addZip();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		addCityButton.addActionListener(e -> {
			try {
				addCity();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		addStateButton.addActionListener(e -> {
			try {
				addState();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		addCountryButton.addActionListener(e -> {
			try {
				addCountry();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editZipButton.addActionListener(e -> {
			try {
				editZip();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editCityButton.addActionListener(e -> {
			try {
				editCity();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editStateButton.addActionListener(e -> {
			try {
				editState();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
		editCountryButton.addActionListener(e -> {
			try {
				editCountry();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		// Load all of the components into the frame.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 50;
		c.ipady = 250;
		add(zipcodeScroll, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 120;
		c.ipady = 250;
		add(cityScroll, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 120;
		c.ipady = 250;
		add(stateScroll, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 175;
		c.ipady = 250;
		add(countryScroll, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addZipButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editZipButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addCityButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editCityButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addStateButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editStateButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(addCountryButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipadx = 0;
		c.ipady = 0;
		add(editCountryButton, c);

		// Then build the required tables.
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
	 * @title	buildTables()
	 * @throws 	SQLException
	 * @desc	Fills the tables from this panel with the needed information.
	 */
	private void buildTables() throws SQLException
	{
		// Initalize the variables.
		String query = "";
		String[] columnNames = {};
		Statement stmt;
		ResultSet rset;
		DefaultTableModel dtm;
		
		// First zipcodes
		query = "SELECT zipcode FROM zipcodes ORDER BY zipcode;";
		columnNames = new String[] {"Zipcodes"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Pull all of the information and add it to the table.
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set the table.
		zipcodeTable.setModel(dtm);
		zipcodeTable.setAutoCreateRowSorter(true);
		
		// Second  cities
		query = "SELECT name FROM cities ORDER BY name;";
		columnNames = new String[] {"Cities"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Pull all of the information.
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set the table.
		cityTable.setModel(dtm);
		cityTable.setAutoCreateRowSorter(true);
		
		// Third states
		query = "SELECT CONCAT(abbreviation, '-', name) FROM states ORDER BY name;";
		columnNames = new String[] {"States"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Load all of the information.
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set the table.
		stateTable.setModel(dtm);
		stateTable.setAutoCreateRowSorter(true);
		
		// Fourth countries
		query = "SELECT CONCAT(abbreviation, '-', name) FROM countries ORDER BY name;";
		columnNames = new String[] {"Countries"};
		dtm = new DefaultTableModel(columnNames, 0);
		stmt = conn.createStatement();
		rset = stmt.executeQuery(query);
		
		// Load the information.
		while(rset.next())
		{
			String[] data = {rset.getString(1)};
			dtm.addRow(data);
		}
		
		// Set the table.
		countryTable.setModel(dtm);
		countryTable.setAutoCreateRowSorter(true);
	}
	
	/**
	 * @title	addZip
	 * @throws 	SQLException
	 * @desc	Provides an alert box that when utilized submits infromation to
	 * 			the Zipcodes table.
	 */
	private void addZip() throws SQLException
	{
		// Initalize that data.
		JPanel panel = new JPanel();
		JLabel zipField = new JLabel("Zipcode: ");
		JLabel citField = new JLabel("Cities: ");
		JTextField zip = new JTextField(10);
		String[] cities = new String[cityTable.getModel().getRowCount()];
		
		String zipcode, city;
		
		// Pull all of the cities that the zipcode could be in.
		for(int x = 0; x < cityTable.getModel().getRowCount(); x++)
		{
			cities[x] = (String)cityTable.getModel().getValueAt(x, 0);
		}

		// Make the citiy combo box.
		JComboBox<String> cit = new JComboBox<String>(cities);
		
		// Add everything to the panel for the dialog box.
		panel.setLayout(new GridLayout(2,2));
		panel.add(zipField);
		panel.add(zip);
		panel.add(citField);
		panel.add(cit);
		
		// Push the alert.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Zipcode and City", JOptionPane.OK_CANCEL_OPTION);
		
		// If the user said ok, then submit the data.
		if(result == JOptionPane.OK_OPTION)
		{
			zipcode = zip.getText();
			city = (String) cit.getSelectedItem();
			
		}
		// If not then don't do anything.
		else
		{
			return;
		}
		
		// Check to see if the zipcode is already there.
		boolean isThere = false;
		
		String query = "SELECT * FROM Zipcodes WHERE Zipcode = '" + zipcode + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No zip is here, continue.
			}
			else
			{
				if(rset.getString(2) == null)
				{
					// This means we can input the city.
					isThere = true;
				}
				else
				{
					// City and zip are both there so return.
					return;
				}
			}
		}
		
		// This means we need to just put in the city.
		if(isThere)
		{
			query = "UPDATE Zipcodes SET City = (SELECT id FROM Cities WHERE Name = '" + city + "') where Zipcode = '" + zipcode + "';";
		
		}
		// This means we need to put in the zipcode and the city.
		else
		{
			query = "INSERT INTO Zipcodes(Zipcode, City) VALUES('" + zipcode + "', (SELECT id FROM cities WHERE name = '" + city + "'));";
		}
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the table.
		buildTables();
	}
	
	/**
	 * @title	editZip
	 * @throws 	SQLException
	 * @desc	Gives functionality to be able to edit the zipcode's city in the
	 * 			database.
	 */
	private void editZip() throws SQLException
	{
		// Initialize all of the components.
		JPanel panel = new JPanel();
		JLabel primaryLabel = new JLabel("Zipcode: ");
		JLabel secondaryLabel = new JLabel("City: ");
		JComboBox<String> primaryChoices, secondaryChoices;
		String[] primaryList, secondaryList;
		
		// Create the zipcode list and city list.
		primaryList = new String[zipcodeTable.getModel().getRowCount()];
		secondaryList = new String[cityTable.getModel().getRowCount()];
		
		// Populate those lists
		for(int x = 0; x < zipcodeTable.getModel().getRowCount(); x++)
		{
			primaryList[x] = (String)zipcodeTable.getModel().getValueAt(x, 0);
		}
		for(int x = 0; x < cityTable.getModel().getRowCount(); x++)
		{
			secondaryList[x] = (String)cityTable.getModel().getValueAt(x, 0);
		}
		
		// Create the drop downs.
		primaryChoices = new JComboBox<String>(primaryList);
		secondaryChoices = new JComboBox<String>(secondaryList);
		
		// Create the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(primaryLabel);
		panel.add(primaryChoices);
		panel.add(secondaryLabel);
		panel.add(secondaryChoices);
		
		// Poll the user.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Zip and New City", JOptionPane.OK_CANCEL_OPTION);

		// If the user wants to push the data, then update the zipcode.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE zipcodes SET city = (SELECT id FROM cities WHERE Name = '" + secondaryChoices.getSelectedItem() + "') where zipcode = '" + primaryChoices.getSelectedItem() + "';";
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
	 * @title	addCity
	 * @throws 	SQLException
	 * @desc	Polls the user asking to see if they want to add another city
	 * 			to the database and then adds one if so.
	 */
	private void addCity() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel cityField = new JLabel("City: ");
		JLabel stateField = new JLabel("States: ");
		JTextField ct = new JTextField(10);
		String[] states = new String[stateTable.getModel().getRowCount()];
		
		String city, state;
		
		// Fill the state screen.
		for(int x = 0; x < stateTable.getModel().getRowCount(); x++)
		{
			states[x] = ((String)stateTable.getModel().getValueAt(x, 0)).substring(3);
		}

		JComboBox<String> st = new JComboBox<String>(states);
		
		panel.setLayout(new GridLayout(2,2));
		panel.add(cityField);
		panel.add(ct);
		panel.add(stateField);
		panel.add(st);
		
		// Poll for the city and the state to add it to.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter City and State", JOptionPane.OK_CANCEL_OPTION);
		
		// Get the information and continue if prompted.
		if(result == JOptionPane.OK_OPTION)
		{
			city = ct.getText();
			state = (String) st.getSelectedItem();
			
		}
		// Else, dont do anything.
		else
		{
			return;
		}
		
		// Check to see if it is there.
		boolean isThere = false;
		
		String query = "SELECT * FROM cities WHERE name = '" + city + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No city is here, continue.
			}
			else
			{
				if(rset.getString(2) == null)
				{
					// This means we can input the state.
					isThere = true;
				}
				else
				{
					// City and state are both there so return.
					return;
				}
			}
		}
		
		// This means we need to just put in the state.
		if(isThere)
		{
			query = "UPDATE cities SET state = (SELECT id FROM states WHERE Name = '" + state + "') where name = '" + city + "';";
		
		}
		// This means we need to put in the city and the state.
		else
		{
			query = "INSERT INTO cities(id, name, state) VALUES('" + (cityTable.getModel().getRowCount() + 1) + "', '" + city + "', (SELECT id FROM states WHERE name = '" + state + "'));";
		}
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	editCity
	 * @throws 	SQLException
	 * @desc	Prompts the user to edit the states that cities are assigned to.
	 */
	private void editCity() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel primaryLabel = new JLabel("City: ");
		JLabel secondaryLabel = new JLabel("State: ");
		JComboBox<String> primaryChoices, secondaryChoices;
		String[] primaryList, secondaryList;
		
		primaryList = new String[cityTable.getModel().getRowCount()];
		secondaryList = new String[stateTable.getModel().getRowCount()];
		
		// Build the lists.
		for(int x = 0; x < cityTable.getModel().getRowCount(); x++)
		{
			primaryList[x] = (String)cityTable.getModel().getValueAt(x, 0);
		}
		for(int x = 0; x < stateTable.getModel().getRowCount(); x++)
		{
			secondaryList[x] = ((String)stateTable.getModel().getValueAt(x, 0)).substring(3);
		}
		
		primaryChoices = new JComboBox<String>(primaryList);
		secondaryChoices = new JComboBox<String>(secondaryList);
		
		// Build the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(primaryLabel);
		panel.add(primaryChoices);
		panel.add(secondaryLabel);
		panel.add(secondaryChoices);
		
		// Poll for the city and new state.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter City and New State", JOptionPane.OK_CANCEL_OPTION);

		// Update the city if prompted.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE cities SET state = (SELECT id FROM states WHERE Name = '" + secondaryChoices.getSelectedItem() + "') where name = '" + primaryChoices.getSelectedItem() + "';";
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
	 * @title	addState
	 * @throws 	SQLException
	 * @desc	Prompts the user to add another state.
	 */
	private void addState() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("State: ");
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
		
		// Poll the user for the state and abbreviation, we are assuming it is a state
		// of the united states.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter State and Abbreviation", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION)
		{
			name = nameField.getText();
			abb = (String) abbField.getText();
		}
		else
		{
			return;
		}
		
		String query = "SELECT * FROM states WHERE name = '" + name + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		// Check to see if the state is already in the list.
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No state is here, continue.
			}
			else
			{
				return;
			}
		}

		// If not, then enter in the state.
		query = "INSERT INTO states(id, name, abbreviation, country) VALUES('" + (stateTable.getModel().getRowCount() + 1) + "', '" + name + "', '" + abb + "', 1);";

		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	editState
	 * @throws 	SQLException
	 * @desc	Adds the ability to edit the abbreviation of a state.
	 */
	private void editState() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("State: ");
		JLabel abbLabel = new JLabel("Abbreviation: ");
		JComboBox<String> nameChoices;
		JTextField abb = new JTextField(10);
		String[] nameList = new String[stateTable.getModel().getRowCount()];
		
		// Build the list of states.
		for(int x = 0; x < stateTable.getModel().getRowCount(); x++)
		{
			nameList[x] = ((String)stateTable.getModel().getValueAt(x, 0)).substring(3);
		}
		
		nameChoices = new JComboBox<String>(nameList);

		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameChoices);
		panel.add(abbLabel);
		panel.add(abb);
		
		// Poll for the new abbreviation
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter State and New Abbreviation", JOptionPane.OK_CANCEL_OPTION);
		
		// If they say to change it, do it.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE states SET abbreviation = '" + abb.getText() + "' WHERE name = '" + nameChoices.getSelectedItem() + "';";
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
	 * @title 	addCountry
	 * @throws 	SQLException
	 * @desc	Prompts the user to add a country to the database.
	 */
	private void addCountry() throws SQLException
	{
		// Add the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Country: ");
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
		
		// Poll for the new country and its abbreviation.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Country and Abbreviation", JOptionPane.OK_CANCEL_OPTION);
		
		// Get the results if they said OK, return if not.
		if(result == JOptionPane.OK_OPTION)
		{
			name = nameField.getText();
			abb = (String) abbField.getText();
		}
		else
		{
			return;
		}
		
		// Make sure it isnt there already.
		String query = "SELECT * FROM countries WHERE name = '" + name + "';";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		while(rset.next())
		{
			if(rset.getString(1) == null)
			{
				// No country is here, continue.
			}
			else
			{
				return;
			}
		}
		
		// Push it to the database.
		query = "INSERT INTO countries(id, name, abbreviation) VALUES('" + (countryTable.getModel().getRowCount() + 1) + "', '" + name + "', '" + abb + "');";
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the tables.
		buildTables();
	}
	
	/**
	 * @title	editCountry
	 * @throws 	SQLException
	 * @desc	Allows the user to edit the abbreviation of a country.
	 */
	private void editCountry() throws SQLException
	{
		// Create the components.
		JPanel panel = new JPanel();
		JLabel nameLabel = new JLabel("Country: ");
		JLabel abbLabel = new JLabel("Abbreviation: ");
		JComboBox<String> nameChoices;
		JTextField abb = new JTextField(10);
		String[] nameList = new String[countryTable.getModel().getRowCount()];
		
		// Fill the name list.
		for(int x = 0; x < countryTable.getModel().getRowCount(); x++)
		{
			nameList[x] = ((String)countryTable.getModel().getValueAt(x, 0)).split("-")[1];
		}
		
		nameChoices = new JComboBox<String>(nameList);

		// Create the panel.
		panel.setLayout(new GridLayout(2,2));
		panel.add(nameLabel);
		panel.add(nameChoices);
		panel.add(abbLabel);
		panel.add(abb);
		
		// Poll the user.
		int result = JOptionPane.showConfirmDialog(null, panel, "Please Enter Country and New Abbreviation", JOptionPane.OK_CANCEL_OPTION);
		
		// If they say ok, then push changes to the database.
		if(result == JOptionPane.OK_OPTION)
		{
			String query = "UPDATE countries SET abbreviation = '" + abb.getText() + "' WHERE name = '" + nameChoices.getSelectedItem() + "';";
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
}
