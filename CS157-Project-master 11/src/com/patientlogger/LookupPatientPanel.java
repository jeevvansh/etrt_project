package com.patientlogger;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * @title	LookupPatientPanel
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This class is an extension of JPanel which allows the user to lookup a patient in 
 * 			the database.
 */
public class LookupPatientPanel extends JPanel
{
	//Go through and instantiate all of the needed variables.
	private static final long serialVersionUID = 1L;
	Connection conn;
	
	//Field to choose how to lookup patients
	String[] searchOptions = {"Choose One", "Name", "THC Number", "SSN"};
	
	//Drop Down menu for search
	JComboBox<String> searchCriteria;
	
	//Field to insert search terms
	JTextField searchBox;
	
	//Initialize Search Button
	JButton searchButton;
	
	//text fields with patient data	
	JTextField THCNumberField, currentDateField, firstNameField, middleNameField, lastNameField,
			   yearField, emailField, addressField, zipField, insuranceField,
	   		   occupationField, workStatusField, educationField, tOnsetField, tEtioField, hOnsetField,
	   		   hEtioField, genderField, cityField, stateField, countryField, monthField,dayField,
	   		   areaCodeField, phone1Field, phone2Field, ssn1Field, ssn2Field, ssn3Field;

	//Patient's comment field	
	JTextArea commentField;

	//Patient's photo
	JLabel photoField;

	//Used for fields with multiple entry boxes
	JPanel dobField, patientPanel, phoneField, ssnField;

	//labels to be used in lookup of patient data
	JLabel THCNumberLabel, currentDateLabel, firstNameLabel, middleNameLabel, lastNameLabel, dobLabel,
		   genderLabel, phoneLabel, emailLabel, addressLabel, cityLabel, stateLabel, zipLabel, countryLabel,
		   photoLabel, ssnLabel, insuranceLabel, blankSpace1, blankSpace2, demographicsLabel, occupationLabel,
		   workStatusLabel, educationLabel, tOnsetLabel, tEtioLabel, hOnsetLabel, hEtioLabel, commentLabel;
	
	//make pane scrollable
	JScrollPane patientScrollable;
	
	//Initialize all buttons
	JButton editPatientButton, addNewVisitButton, currentVisitButton;
	
	boolean hasPatient;
	
	/**
	 * @title	LookupPatientPanel
	 * @desc	constructor, builds panel
	 * @param 	c - Is the connection to the database.
	 */
	public LookupPatientPanel(Connection c)
	{
		hasPatient = false;
		conn = c;
		setLayout(new GridBagLayout());
		buildPanel();
	}
	
	/**
	 *	@title	buildPanel
	 *	@desc	Local method used to build this panel, everything from assigning listeners to putting the
	 *			components in the correct place.
	 */
	private void buildPanel()
	{
		// Take care of the picture, we need to pull the unknown avatar and scale it to the needed size.
		ImageIcon ogUnknownPicture = new ImageIcon("src/images/unknownPicture.png");
		//Scales picture to fit box
		ImageIcon unknownPicture = new ImageIcon(ogUnknownPicture.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		
		//Initialize the following for the main JFrame.
		searchCriteria = new JComboBox<String>(searchOptions);
		searchBox = new JTextField(10);
		searchButton = new JButton("Search");
		
		//Side Panel with patient info
		patientPanel = new JPanel();
		photoLabel = new JLabel(unknownPicture);
		firstNameLabel = new JLabel("Unkown", SwingConstants.CENTER);
		lastNameLabel = new JLabel("Patient", SwingConstants.CENTER);
		THCNumberLabel = new JLabel("THC#: ", SwingConstants.CENTER);
		
		editPatientButton = new JButton("Edit Patient");
		addNewVisitButton = new JButton("New Visit");
		currentVisitButton = new JButton("Current Visit");
		
		addNewVisitButton.addActionListener(e -> newVisit());
		
		currentVisitButton.addActionListener(e -> {
			try {
				currentVisit();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		});
		
		//Sets pane with patient lookup info
		patientPanel.setLayout(new GridBagLayout());
		buildPatientPanel();
		
		patientScrollable = new JScrollPane(patientPanel);
		patientScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Add listener when search button clicked
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
		
		//Add listener when edit patient button clicked
		editPatientButton.addActionListener(e -> edit());
		
		GridBagConstraints c = new GridBagConstraints();
		
		// All of the following is to design the panel and put all the components in the correct place.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(searchCriteria, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 250;
		add(searchBox, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 50;
		add(searchButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 10;
		c.ipady = 300;
		c.ipadx = 300;
		add(patientScrollable, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(photoLabel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(firstNameLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(lastNameLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(THCNumberLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(editPatientButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(addNewVisitButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 0;
		c.ipadx = 0;
		add(currentVisitButton, c);
	}
	
	/**
	 * @title	buildPatientPanel
	 * @desc	Builds the patient frame and changes on search.
	 */
	private void buildPatientPanel()
	{	
		// Initialize all variables
		dobField = new JPanel(new GridLayout(1, 3));
		monthField = new JTextField("MM");
		dayField = new JTextField("DD");
		yearField = new JTextField("YYYY");
		dobField.add(monthField);
		dobField.add(dayField);
		dobField.add(yearField);
		genderField = new JTextField();
		phoneField = new JPanel(new GridLayout(1, 3));
		areaCodeField = new JTextField("(XXX)");
		phone1Field = new JTextField("XXX");
		phone2Field = new JTextField("XXXX");
		phoneField.add(areaCodeField);
		phoneField.add(phone1Field);
		phoneField.add(phone2Field);
		emailField = new JTextField();
		addressField = new JTextField("");
		cityField = new JTextField();
		stateField = new JTextField();
		zipField = new JTextField();
		countryField = new JTextField();
		ssnField = new JPanel(new GridLayout(1, 3));
		ssn1Field = new JTextField("XXX");
		ssn2Field = new JTextField("XX");
		ssn3Field = new JTextField("XXXX");
		ssnField.add(ssn1Field);
		ssnField.add(ssn2Field);
		ssnField.add(ssn3Field);
		insuranceField = new JTextField();	
		occupationField = new JTextField();
		workStatusField = new JTextField();
		educationField = new JTextField();
		tOnsetField = new JTextField();
		tEtioField = new JTextField();
		hOnsetField = new JTextField();
		hEtioField = new JTextField();
		commentField = new JTextArea(4, 30);
		dobLabel = new JLabel("Date of Birth");
		genderLabel = new JLabel("Gender");
		phoneLabel = new JLabel("Phone");
		emailLabel = new JLabel("Email");
		addressLabel = new JLabel("Street Address");
		cityLabel = new JLabel("City");
		stateLabel = new JLabel("State");
		zipLabel = new JLabel("Zipcode");
		countryLabel = new JLabel("Country");
		ssnLabel = new JLabel("SSN");
		insuranceLabel = new JLabel("Insurance");
		occupationLabel = new JLabel("Occupation");
		workStatusLabel = new JLabel("Work Status");
		educationLabel = new JLabel("Educational Degree");
		tOnsetLabel = new JLabel("Tinnitus Onset");
		tEtioLabel = new JLabel("Tinnitus Etiology");
		hOnsetLabel = new JLabel("Hyperacusis Onset");
		hEtioLabel = new JLabel("Hyperacusis Etiology");
		commentLabel = new JLabel("Additional Comments");
		
		//Make field non editable
		monthField.setEditable(false);
		dayField.setEditable(false);
		yearField.setEditable(false);
		genderField.setEditable(false);
		areaCodeField.setEditable(false);
		phone1Field.setEditable(false);
		phone2Field.setEditable(false);
		emailField.setEditable(false);
		addressField.setEditable(false);
		cityField.setEditable(false);
		stateField.setEditable(false);
		zipField.setEditable(false);
		countryField.setEditable(false);
		ssn1Field.setEditable(false);
		ssn2Field.setEditable(false);
		ssn3Field.setEditable(false);
		insuranceField.setEditable(false);	
		occupationField.setEditable(false);
		workStatusField.setEditable(false);
		educationField.setEditable(false);
		tOnsetField.setEditable(false);
		tEtioField.setEditable(false);
		hOnsetField.setEditable(false);
		hEtioField.setEditable(false);
		commentField.setEditable(false);
		
		GridBagConstraints c = new GridBagConstraints();
		
		// The following code designs the panel and puts all the components in the correct place.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(dobLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(dobField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(genderLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(genderField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(phoneLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(phoneField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(emailLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(emailField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(addressLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(addressField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(cityLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(cityField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(stateLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(stateField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(zipLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(zipField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(countryLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(countryField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(ssnLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(ssnField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(insuranceLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 11;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(insuranceField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(occupationLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 12;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(occupationField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 13;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(workStatusLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 13;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(workStatusField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 14;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(educationLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 14;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(educationField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 15;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(tOnsetLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 15;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(tOnsetField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 16;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(tEtioLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 16;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(tEtioField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 17;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(hOnsetLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 17;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(hOnsetField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 18;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(hEtioLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 18;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(hEtioField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 19;
		c.gridwidth = 1;
		c.gridheight = 1;
		patientPanel.add(commentLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 20;
		c.gridwidth = 2;
		c.gridheight = 3;
		patientPanel.add(commentField, c);
	}
	
	/**
	 *	@title	search
	 *	@desc	search method for finding patients
	 */
	private void search() throws SQLException
	{
		//Doesn't search if nothing inputed
		if(searchBox.getText().equals(""))
		{
			return;
		}
		
		//Connects with database
		//Search begins empty
		Statement stmt = conn.createStatement();
		
		String query = "";
		
		// Set the picture to the patient's stored picture and scale it.
		ImageIcon ogUnknownPicture = null;
		ImageIcon unknownPicture = null;
		
		switch((String)searchCriteria.getSelectedItem())
		{
			// Default will be for THC and select search
			default:
				//looks in database for patient with inputed thc number
				query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', patients.firstname AS 'FIRSTNAME', patients.lastname AS 'LASTNAME', patients.photo AS 'PHOTO', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', patients.phone AS 'PHONE', patients.email AS 'EMAIL', patients.streetaddress AS 'ADDRESS', cities.name AS 'CITY', states.name AS 'STATE', patients.zip AS 'ZIP', countries.name AS 'COUNTRY', patients.ssid AS 'SSID', patients.insurance AS 'INSURANCE', occupations.name AS 'OCCUPATION', work_statuses.name AS 'WORKSTATUS', educational_degrees.name AS 'EDUCATION', patients.tonset AS 'TONSET', patients.tetiology AS 'TETIO', patients.honset AS 'HONSET', patients.hetiology AS 'HETIO', patients.comments AS 'COMMENTS' " + 
					   	   "FROM patients, zipcodes, cities, states, occupations, countries, educational_degrees, work_statuses " + 
					   	   "WHERE patients.THCNumber = '" + searchBox.getText() + "' AND patients.occupation = occupations.id AND patients.workstatus = work_statuses.id AND patients.educationaldegree = educational_degrees.id AND zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id;";		
				break;
				
			case "Name":
				//looks in database for patient with inputed name
				String[] name = searchBox.getText().split(" ");
				query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', patients.firstname AS 'FIRSTNAME', patients.lastname AS 'LASTNAME', patients.photo AS 'PHOTO', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', patients.phone AS 'PHONE', patients.email AS 'EMAIL', patients.streetaddress AS 'ADDRESS', cities.name AS 'CITY', states.name AS 'STATE', patients.zip AS 'ZIP', countries.name AS 'COUNTRY', patients.ssid AS 'SSID', patients.insurance AS 'INSURANCE', occupations.name AS 'OCCUPATION', work_statuses.name AS 'WORKSTATUS', educational_degrees.name AS 'EDUCATION', patients.tonset AS 'TONSET', patients.tetiology AS 'TETIO', patients.honset AS 'HONSET', patients.hetiology AS 'HETIO', patients.comments AS 'COMMENTS' " + 
					   	   "FROM patients, zipcodes, cities, states, occupations, countries, educational_degrees, work_statuses " + 
					   	   "WHERE patients.firstname = '" + name[0] + "' AND patients.lastName = '" + name[1] + "' AND patients.occupation = occupations.id AND patients.workstatus = work_statuses.id AND patients.educationaldegree = educational_degrees.id AND zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id;";
				break;
			
			case "SSN":
				//looks in database for patient with inputed SSN
				query = "SELECT zipcodes.zipcode AS 'N/A', zipcodes.city AS 'N/A', cities.id AS 'N/A', cities.state AS 'N/A', states.id AS 'N/A', patients.thcnumber AS 'THC', patients.firstname AS 'FIRSTNAME', patients.lastname AS 'LASTNAME', patients.photo AS 'PHOTO', patients.dob AS 'BIRTHDAY',patients.gender AS 'GENDER', patients.phone AS 'PHONE', patients.email AS 'EMAIL', patients.streetaddress AS 'ADDRESS', cities.name AS 'CITY', states.name AS 'STATE', patients.zip AS 'ZIP', countries.name AS 'COUNTRY', patients.ssid AS 'SSID', patients.insurance AS 'INSURANCE', occupations.name AS 'OCCUPATION', work_statuses.name AS 'WORKSTATUS', educational_degrees.name AS 'EDUCATION', patients.tonset AS 'TONSET', patients.tetiology AS 'TETIO', patients.honset AS 'HONSET', patients.hetiology AS 'HETIO', patients.comments AS 'COMMENTS' " + 
					   	   "FROM patients, zipcodes, cities, states, occupations, countries, educational_degrees, work_statuses " + 
					   	   "WHERE patients.ssid = '" + searchBox.getText() + "' AND patients.occupation = occupations.id AND patients.workstatus = work_statuses.id AND patients.educationaldegree = educational_degrees.id AND zipcodes.zipcode = patients.zip AND zipcodes.city = cities.id AND cities.state = states.id;";
				break;
		}
		
		ResultSet rset = stmt.executeQuery(query); 
		
		// Pull all of the data.
		while(rset.next())
		{
			THCNumberLabel.setText("THC#: " + rset.getString("THC"));
			firstNameLabel.setText(rset.getString("FIRSTNAME"));
			lastNameLabel.setText(rset.getString("LASTNAME"));
			monthField.setText(rset.getString("BIRTHDAY").substring(5,7));
			dayField.setText(rset.getString("BIRTHDAY").substring(8,10));
			yearField.setText(rset.getString("BIRTHDAY").substring(0,4));
			genderField.setText(rset.getString("GENDER"));
			areaCodeField.setText(rset.getString("PHONE").substring(0,3));
			phone1Field.setText(rset.getString("PHONE").substring(3,6));
			phone2Field.setText(rset.getString("PHONE").substring(6,10));	
			emailField.setText(rset.getString("EMAIL"));
			addressField.setText(rset.getString("ADDRESS"));
			cityField.setText(rset.getString("CITY"));
			stateField.setText(rset.getString("STATE"));
			zipField.setText(rset.getString("ZIP"));
			countryField.setText(rset.getString("COUNTRY"));
			
			ogUnknownPicture = new ImageIcon("src/images/" + rset.getString("PHOTO"));
			unknownPicture = new ImageIcon(ogUnknownPicture.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
			photoLabel.setIcon(unknownPicture);
			photoLabel.repaint();
			
			ssn1Field.setText(rset.getString("SSID").substring(0,3));
			ssn2Field.setText(rset.getString("SSID").substring(3,5));
			ssn3Field.setText(rset.getString("SSID").substring(5,9));
			insuranceField.setText(rset.getString("INSURANCE"));
			occupationField.setText(rset.getString("OCCUPATION"));
			workStatusField.setText(rset.getString("WORKSTATUS"));
			educationField.setText(rset.getString("EDUCATION"));
			tOnsetField.setText(rset.getString("TONSET"));
			tEtioField.setText(rset.getString("TETIO"));
			hOnsetField.setText(rset.getString("HONSET"));
			hEtioField.setText(rset.getString("HETIO"));
			commentField.setText(rset.getString("COMMENTS"));
			
			hasPatient = true;
		}
	}
	
	/**
	 *	@title	edit
	 *	@desc	Launches new JFrame to edit searched patient
	 */
	private void edit()
	{
		//edit only if searched patient
		if(hasPatient == false)
			return;
		
		// Build a edit patient screen.
		JFrame frame = new JFrame("eTRT - Edit Patient");
		frame.add(new EditPatientScreen(conn, THCNumberLabel.getText().substring(6)));
		frame.setSize(new Dimension(600, 450));
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width/2-frame.getSize().width/2, d.height/2-frame.getSize().height/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * @title	newVisit
	 * @desc	Opens the new visit tab with the given THC number.
	 */
	private void newVisit()
	{
		String thc = THCNumberLabel.getText().substring(6);
		
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
	 * @throws	SQLException
	 * @desc	Pulls the currentVisit tab open with the given THC.
	 */
	private void currentVisit() throws SQLException
	{
		String thc = THCNumberLabel.getText().substring(6);
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

