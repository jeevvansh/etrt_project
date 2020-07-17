package com.patientlogger;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.SwingUtilities;

/**
 * @title	EditVisitScreen
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This JPanel is an extension of the AddNewVisit Panel, similar except
 * 			it allows for visit editing instead of creation.
 */
public class EditVisitScreen extends AddNewVisitPanel
{
	private static final long serialVersionUID = 1L;
	String myID;

	/**
	 * @title	EditVisitScreen
	 * @param	c - The connection to the database.
	 * @param	visit - The account to be edited.
	 * @desc	Send the connection to super and then fill the info.
	 */
	public EditVisitScreen(Connection c, String id) 
	{
		super(c);
		myID = id;
		try
		{
			fillInfo();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * @throws SQLException 
	 * @title	fillInfo
	 * @desc	Imports Visit info into the form.
	 */
	private void fillInfo() throws SQLException
	{
		
		String query = "SELECT visits.visitid AS 'ID', visits.date AS 'DATE', CONCAT(visits.thcnumber, '-', patients.firstname, ' ', patients.lastname) AS NAME, visits.thcnumber AS 'THC', visits.visitsequence AS 'VISIT', visits.problemrank AS 'PROB', visits.category AS 'C', visits.protocol AS 'CC', visits.instrument AS 'INST', visits.rem AS 'REM', visits.fu AS 'FU', visits.comments AS 'COMMENTS', visits.nextvisit AS 'NEXTVISIT' " +
				       "FROM visits, patients " +
				       "WHERE visits.thcnumber = patients.thcnumber AND visits.visitID = '" + myID + "';";
	
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery(query);
		
		while(rset.next())
		{
			//Import the data to all of the fields.
			visitIDField.setText(rset.getString("ID"));
			visitDateField.setText(rset.getString("DATE"));
			nameField.setText(rset.getString("NAME"));
			thcField.setText(rset.getString("THC"));
			visitSequenceField.setText(rset.getString("VISIT"));
			problemRankField.setSelectedItem(rset.getString("PROB"));
			categoryField.setSelectedItem(rset.getString("C"));
			protocolField.setSelectedItem(rset.getString("CC"));
			fuField.setSelectedItem(rset.getString("FU"));
			instrumentField.setSelectedItem(rset.getString("INST"));
			monthField.setSelectedItem(rset.getString("NEXTVISIT").substring(5,7));
			dayField.setSelectedItem(rset.getString("NEXTVISIT").substring(8,10));
			yearField.setText(rset.getString("NEXTVISIT").substring(0,4));		
			remField.setText(rset.getString("NEXTVISIT"));
			commentField.setText(rset.getString("COMMENTS"));
		}
		

		
		// Removes action listeners of the save and cancel buttons. This is needed
		// because they are linked with the super's action listeners, which mess up the
		// editing process
		for(ActionListener al : super.getSaveButton().getActionListeners())
		{
			super.getSaveButton().removeActionListener(al);
		}
		
		for(ActionListener al : super.getCancelButton().getActionListeners())
		{
			super.getCancelButton().removeActionListener(al);
		}
		
		// Add the edit patient version of the action listeners to the buttons.
		cancelButton.addActionListener(e -> newCancel());
		saveButton.addActionListener(e -> {
			try 
			{
				submitNewInformation();
			} 
			catch (SQLException ex) 
			{
				ex.printStackTrace();
			}
		});		
	}
	
	/**
	 * @title	newCancel
	 * @desc	Closes the visit edit form
	 */
	private void newCancel()
	{
		SwingUtilities.windowForComponent(this).dispose();
	}
	
	/**
	 * @title	submitNewInformation
	 * @throws	SQLException - If the form has a problem with submitting data to the database.
	 * @desc	Submits newest information to the table by deleting the user, and inputting the new data.
	 */
	private void submitNewInformation() throws SQLException
	{	
		// Get current date.
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		
		//For CheckBox
		String rem = "";
		
		if(remField.isSelected())
		{
			rem += "1";
		}
		else
		{
			rem += "0";
		}
		
		// Delete the current patient from database.
		PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM VISITS WHERE VisitID ='" + myID + "';");
		deleteStmt.execute();

		// Re-input the user.
		String query = "INSERT INTO Visits(VisitID, Date, THCNumber, VisitSequence, ProblemRank, Category, Protocol, FU, Instrument, REM, Comments, NextVisit) "
								   + "VALUES(" + visitIDField.getText() + ", "
								   	  + "'" + dtf.format(localDate) + "', "
							   		  + "'" + thcField.getText() + "', "
							   		  + "'" + visitSequenceField.getText() + "', "
							   		  + "'" + problemRankField.getSelectedItem() + "', "
							   		  + "'" + categoryField.getSelectedItem() + "', "
							   		  + "'" + protocolField.getSelectedItem() + "', "
							   		  + "'" + fuField.getSelectedItem() + "', "
							   		  + "'" + instrumentField.getSelectedItem() + "', "
							   		  + "'" + rem + "', "
							   		  + "'" + commentField.getText() + "', "
									  + "'" + dayField.getSelectedItem() + "-" + monthField.getSelectedItem() + "-" + yearField.getText() + "')";

		// Perform query
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Close the patient screen.
		SwingUtilities.windowForComponent(this).dispose();
		
		return;
	}
}
