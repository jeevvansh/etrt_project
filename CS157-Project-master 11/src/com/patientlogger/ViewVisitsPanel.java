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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * @title 	ViewVisitsPanel Class
 * @author 	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This class is an extension of JPanel. Builds the panel to view visits.
 */
public class ViewVisitsPanel extends JPanel
{
	// Create all of the needed variables.
	private static final long serialVersionUID = 1L;

	Connection conn;
	
	//Used for drop down menus
	JComboBox<String> searchCriteria;
	
	//initializing buttons for use
	JButton viewVisitButton, deleteVisitButton, analyzeVisitButton,
			searchButton, refreshButton;
	
	//Used for fields with multiple entry boxes
	JPanel visitsPaneButtons, visitsPanel;
	
	//Pane that can scroll
	JScrollPane visitsScrollPane;
	
	//text fields to get raw input from entry	
	JTextField searchBox;
	
	//Sets table w/ all visits
	JTable visitTable;
	
	//Options for search menu
	final String[] searchOptions = {"Filter Options", "Name", "Date"};
	
	/**
	 * @title	ViewVisitsPanel
	 * @desc	constructor, builds panel
	 * @param 	c - Is the connection to the database.
	 */
	public ViewVisitsPanel(Connection c)
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
		// Set the layout and create the constraints of the panel.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Create all of the needed components of the panel.
		searchCriteria = new JComboBox<String>(searchOptions);
		viewVisitButton = new JButton("View/Edit");
		deleteVisitButton = new JButton("Delete");
		analyzeVisitButton = new JButton("Analyze Visit");
		searchButton = new JButton("Search");
		refreshButton = new JButton("Refresh");
		visitTable = new JTable();
		searchBox = new JTextField(10);
		
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
		
		//Listener adds ability to search for a visit.
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
		
		//Listener adds the ability to delete a visit.
		deleteVisitButton.addActionListener(e -> {
			try 
			{
				delete();
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		});
		
		// Listener adds the ability to edit a visit.
		viewVisitButton.addActionListener(e -> edit());
		
		// Populate the visit table.
		try 
		{
			populate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// Create the scrollable pane.
		visitsScrollPane = new JScrollPane(visitTable);
		
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
		c.ipadx = 50;
		add(visitsScrollPane, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 21;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipady = 0;
		add(viewVisitButton, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(deleteVisitButton, c);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 21;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		add(analyzeVisitButton, c);
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
		
		String query = "";
		
		switch((String)searchCriteria.getSelectedItem())
		{
			// Name and default
			default:
				//looks in database for visit with patient with inputed name
				String[] name = searchBox.getText().split(" ");
				query = "SELECT visits.visitid AS 'ID', visits.date AS 'DATE', CONCAT(visits.thcnumber, '-', patients.firstname, ' ', patients.lastname) AS NAME, visits.visitsequence AS 'VISIT', visits.problemrank AS 'PROB', visits.category AS 'C', visits.protocol AS 'CC', visits.instrument AS 'INST', visits.rem AS 'REM', visits.fu AS 'FU', visits.comments AS 'COMMENTS' " +
						   "FROM visits, patients " +
						   "WHERE visits.thcnumber = patients.thcnumber AND patients.firstname = '" + name[0] + "' AND patients.lastname = '" + name[1] + "';";
				break;
			
			case "Date":
				//looks in database for visit with inputed date
				query = "SELECT visits.visitid AS 'ID', visits.date AS 'DATE', CONCAT(visits.thcnumber, '-', patients.firstname, ' ', patients.lastname) AS NAME, visits.visitsequence AS 'VISIT', visits.problemrank AS 'PROB', visits.category AS 'C', visits.protocol AS 'CC', visits.instrument AS 'INST', visits.rem AS 'REM', visits.fu AS 'FU', visits.comments AS 'COMMENTS' " +
						   "FROM visits, patients " +
						   "WHERE visits.thcnumber = patients.thcnumber AND DATE = '" + searchBox.getText() + "';";
				break;
		}
		
		
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		// Start setting up the table.
		String[] columnNames = {"ID", "Date", "Patient", "Visit", "Prob", "C", "CC", "Instr", "REM", "FU", "Comments"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
		
		// Pull all of the table's information row by row.
		while(rset.next())
		{
			String[] data = {rset.getString("ID"), rset.getString("DATE"), rset.getString("NAME"), rset.getString("VISIT"), rset.getString("PROB"), rset.getString("C"), rset.getString("CC"), rset.getString("INST"), rset.getString("REM"), rset.getString("FU"), rset.getString("COMMENTS")};
			dtm.addRow(data);
		}
		
		// Build the table.
		visitTable.setModel(dtm);
		visitTable.setAutoCreateRowSorter(true);
		visitTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(3).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(4).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(5).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(6).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(7).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(8).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(9).setPreferredWidth(30);
	}
	
	/**
	 * @title	delete
	 * @throws 	SQLException - If the database has problems deleting information.
	 */
	private void delete() throws SQLException
	{
		// Pulls the ID number to be deleted.
		String IDNumber = (String)visitTable.getValueAt(visitTable.getSelectedRow(), 0);
		
		// Deletes the visit from the table.
		PreparedStatement preparedStmt = conn.prepareStatement("DELETE FROM Visits WHERE VisitID ='" + IDNumber + "';");
		preparedStmt.execute();
		
		populate();
	}
	
	/**
	 * @title	edit
	 * @desc	Launches new JFrame to edit a visit
	 */
	private void edit()
	{	
		// Build a new edit visit screen.
		JFrame frame = new JFrame("eTRT - Edit Patient");
		frame.add(new EditVisitScreen(conn, (String)visitTable.getValueAt(visitTable.getSelectedRow(), 0)));
		frame.setSize(new Dimension(600, 450));
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//Center the new frame
		frame.setLocation(d.width/2-frame.getSize().width/2, d.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
	
	/**
	 * @title	populate
	 * @desc	gets visit data and fills table
	 * @throws 	SQLException - If the database can't retrieve information.
	 */
	private void populate() throws SQLException
	{
		String query = "SELECT visits.visitid AS 'ID', visits.date AS 'DATE', CONCAT(visits.thcnumber, '-', patients.firstname, ' ', patients.lastname) AS NAME, visits.visitsequence AS 'VISIT', visits.problemrank AS 'PROB', visits.category AS 'C', visits.protocol AS 'CC', visits.instrument AS 'INST', visits.rem AS 'REM', visits.fu AS 'FU', visits.comments AS 'COMMENTS' " +
					   "FROM visits, patients " +
					   "WHERE visits.thcnumber = patients.thcnumber";
		
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		String[] columnNames = {"ID", "Date", "Patient", "Visit", "Prob", "C", "CC", "Instr", "REM", "FU", "Comments"};
		DefaultTableModel dtm = new DefaultTableModel(columnNames, 0);
		
		while(rset.next())
		{
			String[] data = {rset.getString("ID"), rset.getString("DATE"), rset.getString("NAME"), rset.getString("VISIT"), rset.getString("PROB"), rset.getString("C"), rset.getString("CC"), rset.getString("INST"), rset.getString("REM"), rset.getString("FU"), rset.getString("COMMENTS")};
			dtm.addRow(data);
		}
		
		// Build the table.
		visitTable.setModel(dtm);
		visitTable.setAutoCreateRowSorter(true);
		visitTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(3).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(4).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(5).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(6).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(7).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(8).setPreferredWidth(30);
		visitTable.getColumnModel().getColumn(9).setPreferredWidth(30);
	}
	

}
