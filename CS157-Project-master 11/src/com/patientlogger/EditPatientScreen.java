package com.patientlogger;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

/**
 * @title	EditPatientScreen
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This JPanel is an extension of the AddNewPatients Panel, similar except
 * 			it allows for patient editing instead of creation.
 */
public class EditPatientScreen extends AddNewPatientsPanel
{
	// Instantiate the needed variables.
	private static final long serialVersionUID = 1L;
	String myTHC;
	
	/**
	 * @title	EditPatientScreen
	 * @param	conn - The connection to the database.
	 * @param	account - The account to be edited.
	 * @desc	Send the connection to super and then fill the info.
	 */
	public EditPatientScreen(Connection conn, String THCNUMBER)
	{
		super(conn);
		myTHC = THCNUMBER;
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
	 * @desc	Imports all of the Patient's info into the form.
	 */
	private void fillInfo() throws SQLException
	{	
		//Connects with database
		//Search begins empty
		Statement stmt = conn.createStatement();
		
		String query = "";
		
		// Set the picture to the patient's stored picture and scale it.
		ImageIcon ogUnknownPicture = null;
		ImageIcon unknownPicture = null;

		// Looks in database for patient with inputed thc number
		query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', patients.firstname AS 'FIRSTNAME', patients.middlename AS 'MIDDLENAME', patients.lastname AS 'LASTNAME', patients.photo AS 'PHOTO', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', patients.phone AS 'PHONE', patients.email AS 'EMAIL', patients.streetaddress AS 'ADDRESS', cities.name AS 'CITY', states.name AS 'STATE', patients.zip AS 'ZIP', countries.name AS 'COUNTRY', patients.ssid AS 'SSID', patients.insurance AS 'INSURANCE', occupations.name AS 'OCCUPATION', work_statuses.name AS 'WORKSTATUS', educational_degrees.name AS 'EDUCATION', patients.tonset AS 'TONSET', patients.tetiology AS 'TETIO', patients.honset AS 'HONSET', patients.hetiology AS 'HETIO', patients.comments AS 'COMMENTS' " + 
		   	    "FROM patients, zipcodes, cities, states, occupations, countries, educational_degrees, work_statuses " + 
				"WHERE patients.THCNumber = '" + myTHC + "' AND patients.occupation = occupations.id AND patients.workstatus = work_statuses.id AND patients.educationaldegree = educational_degrees.id AND zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id;";		
		
		ResultSet rset = stmt.executeQuery(query); 
		
		// Pull All of the information.
		while(rset.next())
		{
			THCNumberField.setText(rset.getString("THC"));
			firstNameField.setText(rset.getString("FIRSTNAME"));
			middleNameField.setText(rset.getString("MIDDLENAME"));
			lastNameField.setText(rset.getString("LASTNAME"));
			monthField.setSelectedItem(rset.getString("BIRTHDAY").substring(5,7));
			dayField.setSelectedItem(rset.getString("BIRTHDAY").substring(8,10));
			yearField.setText(rset.getString("BIRTHDAY").substring(0,4));
			genderField.setSelectedItem(rset.getString("GENDER"));
			areaCodeField.setText(rset.getString("PHONE").substring(0,3));
			phone1Field.setText(rset.getString("PHONE").substring(3,6));
			phone2Field.setText(rset.getString("PHONE").substring(6,10));	
			emailField.setText(rset.getString("EMAIL"));
			addressField.setText(rset.getString("ADDRESS"));
			cityField.setSelectedItem(rset.getString("CITY"));
			stateField.setSelectedItem(rset.getString("STATE"));
			zipField.setText(rset.getString("ZIP"));
			countryField.setSelectedItem(rset.getString("COUNTRY"));
			
			ogUnknownPicture = new ImageIcon("src/images/" + rset.getString("PHOTO"));
			unknownPicture = new ImageIcon(ogUnknownPicture.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
			photoLabel.setIcon(unknownPicture);
			photoLabel.repaint();
			
			ssn1Field.setText(rset.getString("SSID").substring(0,3));
			ssn2Field.setText(rset.getString("SSID").substring(3,5));
			ssn3Field.setText(rset.getString("SSID").substring(5,9));
			insuranceField.setText(rset.getString("INSURANCE"));
			occupationField.setSelectedItem(rset.getString("OCCUPATION"));
			workStatusField.setSelectedItem(rset.getString("WORKSTATUS"));
			educationField.setSelectedItem(rset.getString("EDUCATION"));
			tOnsetField.setText(rset.getString("TONSET"));
			tEtioField.setText(rset.getString("TETIO"));
			hOnsetField.setText(rset.getString("HONSET"));
			hEtioField.setText(rset.getString("HETIO"));
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
	 * @desc	Closes the patient edit form
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
		
		// Delete the current patient from database.
		PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM PATIENTS WHERE THCNumber ='" + myTHC + "';");
		deleteStmt.execute();

		insertZip();
		
		String occupation = null;
		String workstatus = null;
		String education = null;
		if(!occupationField.getSelectedItem().equals("Select One"))
		{
			occupation = (String)occupationField.getSelectedItem();
		}
		else
		{
			occupation = "N/A";
		}
		if(!workStatusField.getSelectedItem().equals("Select One"))
		{
			workstatus = (String)workStatusField.getSelectedItem();
		}
		else
		{
			workstatus = "N/A";
		}
		if(!educationField.getSelectedItem().equals("Select One"))
		{
			education = (String)educationField.getSelectedItem();
		}
		else
		{
			education = "N/A";
		}

		// Create the query for submitting all the information into the patients table.
		String query = "INSERT INTO Patients(THCNumber, Date, FirstName, MiddleName, LastName, DOB, Gender, Phone, Email, StreetAddress, Zip, Photo, SSID, Insurance, Occupation, WorkStatus, EducationalDegree, TOnset, TEtiology, HOnset, HEtiology, Comments) "
								   + "VALUES(" + THCNumberField.getText() + ", "
								   		  + "'" + dtf.format(localDate) + "', "
								   		  + "'" + firstNameField.getText() + "', "
								   		  + "'" + middleNameField.getText() + "', "
								   		  + "'" + lastNameField.getText() + "', "
										  + "'" + yearField.getText() + "/" + monthField.getSelectedItem() + "/" + dayField.getSelectedItem() + "', "
								   		  + "'" + genderField.getSelectedItem() + "', "
								   		  + "'" + areaCodeField.getText() + phone1Field.getText() + phone2Field.getText() + "', "
								   		  + "'" + emailField.getText() + "', "
								   		  + "'" + addressField.getText() + "', "
								   		  + "'" + zipField.getText() + "', "
								   		  + "'" + "src/images/" + THCNumberField.getText() + ".png', "
								   		  + "'" + ssn1Field.getText() + ssn2Field.getText() + ssn3Field.getText() + "', "
								   		  + "'" + insuranceField.getText() + "', "
								   		  + "(SELECT id FROM occupations WHERE name = '" + occupation + "'), "
								   		  + "(SELECT id FROM work_statuses WHERE name = '" + workstatus + "'), "
										  + "(SELECT id FROM educational_degrees WHERE name = '" + education + "'), "
								   		  + "'" + tOnsetField.getText() + "', "
								   		  + "'" + tEtioField.getText() + "', "
								   		  + "'" + hOnsetField.getText() + "', "
								   		  + "'" + hEtioField.getText() + "', "
								   		  + "'" + commentField.getText() + "')";
		
		// Perform query.
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Close the patient screen.
		SwingUtilities.windowForComponent(this).dispose();
		
		return;
	}
	
	/**
	 * @title	insertZip
	 * @throws 	SQLException
	 * @desc	Inputs zipcodes into the database.
	 */
	private void insertZip() throws SQLException
	{
		// Variable to be used if zipcode is already existing or not.
		boolean isThere = false;
		
		String query = "SELECT * FROM Zipcodes WHERE Zipcode = '" + zipField.getText() + "';";
		
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
					// City and zip are both there so return.
					return;
				}
				else
				{
					// This means we can input the city.
					isThere = true;
				}
			}
		}
		
		// This means we need to just put in the city.
		if(isThere)
		{
			query = "UPDATE Zipcodes SET City = (SELECT id FROM Cities WHERE Name = '" + cityField.getSelectedItem() + "') where Zipcode = '" + zipField.getText() + "';";
		}
		// This means we need to put in the zipcode and the city.
		else
		{
			query = "INSERT INTO Zipcodes(Zipcode, City) DATA('" + zipField.getText() + "', '" + stateField.getSelectedItem() + "');";
		}
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
	}
}
