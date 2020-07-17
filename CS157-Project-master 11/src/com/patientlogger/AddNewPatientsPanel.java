package com.patientlogger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @title	AddNewPatientsPanel
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	This class is an extension of JPanel which allows the user to add new patients to the
 * 			database.
 */
public class AddNewPatientsPanel extends JPanel
{
	// First thing's first, go through and instantiate all of the needed variables.
	private static final long serialVersionUID = 1L;
	
	GroupLayout layout;
	
	//These six variable fields are for drop down menu on Add New Patient tab
	
	//field to collect gender
	final String[] genderList = {"Select One", "Male", "Female", "Other"};
	
	//field to collect current city they live in
	String[] cityList;
	
	//field to collect current state they live in
	String[] stateList;
	
	//field to collect current country
	String[] countryList;
	
	//field for dob month
	final String[] monthList = {"--", "01", "02", "03", "04", "05", "06",
								"07", "08", "09", "10", "12", "12"};
	
	//field for dob day
	final String[] dayList = {"--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
							  "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
							  "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	
	//field for education
	String[] eduList;
	
	//field for work status
	String[] statusList;
	
	String[] occupationList;
	
	JFrame demographicsFrame;
	
	Connection conn;	
	
	//text fields to get raw input from entry	
	JTextField THCNumberField, currentDateField, firstNameField, middleNameField, lastNameField,
	 		   yearField, emailField, addressField, zipField, insuranceField,
	 		   tOnsetField, tEtioField, hOnsetField,
	 		   hEtioField, ssn1Field, ssn2Field, ssn3Field, areaCodeField, phone1Field,
	 		   phone2Field;
	
	//text area to get doctor comments on patient
	JTextArea commentField;
	
	//Used for drop down menus
	JComboBox<String> monthField, dayField, genderField, cityField, stateField, countryField, educationField, workStatusField, occupationField;
	
	//Add photo button
	JButton photoField;
	
	//Used for fields with multiple entry boxes
	JPanel dobField, ssnField, phoneField;
	
	//labels to be used in gathering of patient data
	JLabel THCNumberLabel, currentDateLabel, firstNameLabel, middleNameLabel, lastNameLabel, dobLabel,
		   genderLabel, phoneLabel, emailLabel, addressLabel, cityLabel, stateLabel, zipLabel, countryLabel,
		   photoLabel, ssnLabel, insuranceLabel, blankSpace1, blankSpace2, demographicsLabel, occupationLabel,
		   workStatusLabel, educationLabel, tOnsetLabel, tEtioLabel, hOnsetLabel, hEtioLabel, commentLabel;
	
	//initializing buttons for use
	JButton saveButton, addDemoButton, newVisitButton, cancelButton, demoSaveButton, demoCancelButton;
	
	/**
	 * @title	AddNewPatientsPanel
	 * @desc	constructor, builds panel
	 * @param 	c - Is the connection to the database.
	 */
	public AddNewPatientsPanel(Connection c)
	{
		this.conn = c;
		try 
		{
			getDemographicsLists();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		buildPanel();
	}
	
	/**
	 * @title	getDemographicsLists
	 * @throws 	SQLException
	 * @desc	This method fills all of the scrolldowns from the demographics with
	 * 			database supplied data.
	 */
	private void getDemographicsLists() throws SQLException
	{

		// Start the number of each entry at 0;
		int cities = 0, states = 0, countries = 0, educations = 0, statuses = 0, occupations = 0;
		
		// Pull the ammount of each type to use later.
		Statement stmt = conn.createStatement();
		ResultSet rset = stmt.executeQuery("SELECT MAX(cities.ID), MAX(states.ID), MAX(countries.ID), MAX(educational_degrees.ID), MAX(work_statuses.ID), MAX(occupations.ID) FROM cities, states, countries, educational_degrees, work_statuses, occupations;");
		
		// Pull all of the results from the database probe.
		while(rset.next())
		{
			cities = Integer.parseInt(rset.getString(1));
			states = Integer.parseInt(rset.getString(2));
			countries = Integer.parseInt(rset.getString(3));
			educations = Integer.parseInt(rset.getString(4));
			statuses = Integer.parseInt(rset.getString(5));
			occupations = Integer.parseInt(rset.getString(6));
		}
		
		// Start off each list with Select One
		cityList = new String[cities + 1];
		cityList[0] = "Select One";
		stateList = new String[states + 1];
		stateList[0] = "Select One";
		countryList = new String[countries + 1];
		countryList[0] = "Select One";
		eduList = new String[educations + 1];
		eduList[0] = "Select One";
		statusList = new String[statuses + 1];
		statusList[0] = "Select One";
		occupationList = new String[occupations + 1];
		occupationList[0] = "Select One";
		
		// The following statements and while loops are to fill the dropdowns.
		stmt = conn.createStatement();
		rset = stmt.executeQuery("Select Name From Cities ORDER BY Name;");
		
		int x = 1;
		while(rset.next())
		{
			cityList[x] = rset.getString(1);
			x++;
		}
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery("Select Name From States ORDER BY Name;");
		
		x = 1;
		while(rset.next())
		{
			stateList[x] = rset.getString(1);
			x++;
		}
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery("Select Name From Countries ORDER BY Name;");
		
		x = 1;
		while(rset.next())
		{
			countryList[x] = rset.getString(1);
			x++;
		}
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery("Select Name From educational_degrees;");
		
		x = 1;
		while(rset.next())
		{
			String edu = rset.getString(1);
			if(!edu.equals("N/A"))
			{
				eduList[x] = rset.getString(1);
				x++;
			}
		}
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery("Select Name From work_statuses;");
		
		x = 1;
		while(rset.next())
		{
			String status = rset.getString(1);
			if(!status.equals("N/A"))
			{
				statusList[x] = rset.getString(1);
				x++;
			}
		}
		
		stmt = conn.createStatement();
		rset = stmt.executeQuery("Select Name From occupations;");
		
		x = 1;
		while(rset.next())
		{
			String occ = rset.getString(1);
			if(!occ.equals("N/A"))
			{
				occupationList[x] = rset.getString(1);
				x++;
			}
		}
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
		
		// Handle the layout of the panel.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Initialize the following for the main JFrame.
		THCNumberField = new JTextField(10);
		currentDateField = new JTextField(10);
		firstNameField = new JTextField(10);
		middleNameField = new JTextField(15);
		lastNameField = new JTextField(15);
		dobField = new JPanel(new GridLayout(1, 3));
		monthField = new JComboBox<String>(monthList);
		dayField = new JComboBox<String>(dayList);
		yearField = new JTextField("YYYY");
		dobField.add(monthField);
		dobField.add(dayField);
		dobField.add(yearField);
		genderField = new JComboBox<String>(genderList);
		phoneField = new JPanel(new GridLayout(1, 3));
		areaCodeField = new JTextField("XXX");
		phone1Field = new JTextField("XXX");
		phone2Field = new JTextField("XXXX");
		phoneField.add(areaCodeField);
		phoneField.add(phone1Field);
		phoneField.add(phone2Field);
		emailField = new JTextField(30);
		addressField = new JTextField("House Number and Street");
		cityField = new JComboBox<String>(cityList);
		stateField = new JComboBox<String>(stateList);
		zipField = new JTextField(9);
		countryField = new JComboBox<String>(countryList);
		photoField = new JButton(unknownPicture);
		ssnField = new JPanel(new GridLayout(1, 3));
		ssn1Field = new JTextField("XXX");
		ssn2Field = new JTextField("XX");
		ssn3Field = new JTextField("XXXX");
		ssnField.add(ssn1Field);
		ssnField.add(ssn2Field);
		ssnField.add(ssn3Field);		
		insuranceField = new JTextField(30);
		THCNumberLabel = new JLabel("THC Number");
		currentDateLabel = new JLabel("Current Date");
		firstNameLabel = new JLabel("First Name");
		middleNameLabel = new JLabel("Middle Name");
		lastNameLabel = new JLabel("Last Name");
		dobLabel = new JLabel("Date of Birth");
		genderLabel = new JLabel("Gender");
		phoneLabel = new JLabel("Phone");
		emailLabel = new JLabel("Email");
		addressLabel = new JLabel("Street Address");
		cityLabel = new JLabel("City");
		stateLabel = new JLabel("State");
		zipLabel = new JLabel("Zipcode");
		countryLabel = new JLabel("Country");
		photoLabel = new JLabel("Photo");
		ssnLabel = new JLabel("SSN");
		insuranceLabel = new JLabel("Insurance Provider");	
		blankSpace1 = new JLabel();
		blankSpace2 = new JLabel();
		saveButton = new JButton("Save");
		addDemoButton = new JButton("Add Demographics");
		newVisitButton = new JButton("New Visit");
		cancelButton = new JButton("Cancel");	

		// Listeners to revert the box to empty once clicked.
		yearField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				yearField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		areaCodeField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				areaCodeField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		phone1Field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				phone1Field.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		phone2Field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				phone2Field.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		addressField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				addressField.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		ssn1Field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				ssn1Field.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		ssn2Field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				ssn2Field.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		ssn3Field.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				ssn3Field.setText("");
			}
			@Override
			public void focusLost(FocusEvent e) {
				// Do Nothing	
			}
		});
		
		// Make photo button look more like a picture, not like a button.
		photoField.setBorderPainted(false);
		// Changes the picture on click
		photoField.addActionListener(event -> changePicture(photoField));
		
		// Figure out what the next THC number is and then fill the THC field with it.
		// This field will be grayed out since it is not editable.
		int currentTHC = getRowCount();
		THCNumberField.setText(Integer.toString(currentTHC + 1));
		THCNumberField.setEditable(false);
		THCNumberField.setBackground(Color.GRAY);
		
		// Gets today's date.
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate localDate = LocalDate.now();
		
		// Makes current date non-editable
		currentDateField.setEditable(false);
		currentDateField.setText(dtf.format(localDate));
		currentDateField.setBackground(Color.GRAY);
		
		// Listener for save button if clicked. Once clicked, submit the information provided.
		saveButton.addActionListener(e -> {
			try 
			{
				submitInformation();
			} 
			catch (SQLException ex) 
			{
				ex.printStackTrace();
			}
		});
		
		// Listeners for the cancel button and the demographics button
		cancelButton.addActionListener(e -> rebuildPanel());
		// The demographics pane is already open, it just needs to be made visible.
		addDemoButton.addActionListener(e -> demographicsFrame.setVisible(true));
		
		newVisitButton.addActionListener(e -> newVisit());
		
		// Saves the given picture locally, and set it to the thcnumber.png
		try 
		{
			Files.copy(new File("src/images/unknownPicture.png").toPath(), new File("src/images/" + THCNumberField.getText() + ".png").toPath(), StandardCopyOption.REPLACE_EXISTING);
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}

		// All of the following is to design the panel and put all the components in the correct place.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 6;
		add(photoField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(THCNumberLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(THCNumberField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(currentDateLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(currentDateField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2; 
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(firstNameLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(firstNameField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(middleNameLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(middleNameField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(lastNameLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(lastNameField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(dobLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(dobField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(genderLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(genderField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(phoneLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 6;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(phoneField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(emailLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(emailField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(addressLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 7;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(addressField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(cityLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(cityField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(stateLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 8;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(stateField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(zipLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(zipField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(countryLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(countryField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(ssnLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(ssnField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(insuranceLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(insuranceField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(saveButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 11;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(addDemoButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 11;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(newVisitButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 11;
		c.gridwidth = 1;
		c.gridheight = 1;
		add(cancelButton, c);
		
		// Build the demographics frame during this stage as well.
		buildDemoFrame();
	}
	
	/**
	 * @title	changePicture
	 * @desc	changes picture button to given image
	 * @param	photoField - The button that houses the picture.
	 */
	private void changePicture(JButton photoField)
	{
		// Instantiate the file that will hold the picture.
		File inputPicture = null;
		
		// Create a file chooser to pull the picture when selected.
		JFileChooser jfc = new JFileChooser();
		// Make sure the file chosen is a picture.
		jfc.setFileFilter(new FileNameExtensionFilter("JPG and PNG", "jpg", "png"));
		int returnVal = jfc.showOpenDialog(null);
		
		// If the file is good, then get the file.
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			inputPicture = jfc.getSelectedFile();
		}
		
		if(inputPicture != null)
		{
			try 
			{
				// Save the file locally under the patient's THC number.
				Files.copy(inputPicture.toPath(), new File("src/images/" + THCNumberField.getText() + ".png").toPath(), StandardCopyOption.REPLACE_EXISTING);
				
				// Scale the image
				ImageIcon ogInPic = new ImageIcon("src/images/" + THCNumberField.getText() + ".png");
				ImageIcon inPic = new ImageIcon(ogInPic.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
				
				// Set the image.
				photoField.setIcon(inPic);
				photoField.repaint();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			photoField.repaint();
		}
	}
	
	/**
	 * @title	errorCheck
	 * @return	Boolean on whether or not there is an error.
	 * @desc	Method is called to check if all necessary fields are
	 * 			completed before submission to database.
	 */
	private boolean errorCheck()
	{
		// Instantiate the needed variables.
		boolean isError = false;
		String errorLog = "";
		
		// All of the following checks to see if the field is empty.
		// If it is, add it to the list of errors.
		if(firstNameField.getText().equals(""))
		{
			errorLog += "First Name, ";
			isError = true;
		}
		if(lastNameField.getText().equals(""))
		{
			errorLog += "Last Name, ";
			isError = true;
		}
		if(monthField.getSelectedItem().equals("Select One"))
		{
			errorLog += "Month of Birth, ";
			isError = true;
		}
		if(dayField.getSelectedItem().equals("Select One"))
		{
			errorLog += "Day of Birth, ";
			isError = true;
		}
		if(yearField.getText().equals("YYYY") || yearField.getText().equals(""))
		{
			errorLog += "Year of Birth";
			isError = true;
		}
		if(genderField.getSelectedItem().equals("Select One"))
		{
			errorLog += "Gender, ";
			isError = true;
		}
		if(areaCodeField.getText().equals("(XXX)"))
		{
			errorLog += "Area Code, ";
			isError = true;
		}
		if(phone1Field.getText().equals("XXX"))
		{
			errorLog += "Phone, ";
			isError = true;
		}
		if(phone2Field.getText().equals("XXXX"))
		{
			errorLog += "Phone, ";
			isError = true;
		}		
		if(addressField.getText().equals(""))
		{
			errorLog += "Street Address, ";
			isError = true;
		}
		if(cityField.getSelectedItem().equals("Select One"))
		{
			errorLog += "City, ";
			isError = true;
		}
		if(stateField.getSelectedItem().equals("Select One"))
		{
			errorLog += "State, ";
			isError = true;
		}
		if(zipField.getText().equals(""))
		{
			errorLog += "Zip, ";
			isError = true;
		}
		if(countryField.getSelectedItem().equals("Select One"))
		{
			errorLog += "Country, ";
			isError = true;
		}
		if(ssn1Field.getText().equals("XXX"))
		{
			errorLog += "SSN, ";
			isError = true;
		}
		if(ssn2Field.getText().equals("XX"))
		{
			errorLog += "SSN, ";
			isError = true;
		}
		if(ssn3Field.getText().equals("XXXX"))
		{
			errorLog += "SSN, ";
			isError = true;
		}
		if(insuranceField.getText().equals(""))
		{
			errorLog += "Insurance, ";
			isError = true;
		}
		
		// If there is an error, alert the user to everywhere it went wrong.
		if(isError)
		{
			errorLog = errorLog.substring(0, errorLog.length() - 2) + ".";
			JOptionPane.showMessageDialog(null, "The following fields can not be empty: " + errorLog, "eTRT - Decision Support System for Tinnitus Restraining Therapy", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(AddNewPatientsPanel.class.getResource("/images/eTRT_icon.png")));
		}
		
		return isError;
	}
	
	/**
	 * @title	insertZip
	 * @throws	SQLException
	 * @desc	Checks to see if a zipcode needs to be inserted into the database. If
	 * 			one is there and it needs a city, input the city. If it isn't there at
	 * 			all push the zipcode and the city.
	 */
	private void insertZip() throws SQLException
	{
		// Check to see if the zipcode exisits boolean.
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
			query = "UPDATE Zipcodes SET City = (SELECT id FROM Cities WHERE Name = '" + cityField.getSelectedItem() + "') where Zipcode = '" + zipField.getText() + "';";
		
		}
		// This means we need to put in the zipcode and the city.
		else
		{
			query = "INSERT INTO Zipcodes(Zipcode, City) VALUES('" + zipField.getText() + "', (SELECT id FROM cities WHERE name = '" + cityField.getSelectedItem() + "'));";
		}
		
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
	}
	 
	/**
	 * @title	submitInformation
	 * @throws 	SQLException - If there is an error when submitting to the database.
	 * @desc	Submits all of the gathered information to the database if there are
	 * 			no errors.
	 */
	private boolean submitInformation() throws SQLException
	{
		// If there is an error, don't submit the information.
		if(errorCheck())
		{
			return false;
		}
		
		// Finds current date again to make it easier for data submission.
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		
		// Push the zip if needed so it is there before all of the other data
		// gets pushed.
		insertZip();
		
		String occupation = null;
		String workstatus = null;
		String education = null;
		
		// Pull all of the demographics if needed.
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
										  + "'" + yearField.getText() + "-" + monthField.getSelectedItem() + "-" + dayField.getSelectedItem() + "', "
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
		
		System.out.println(query);
		// Perform query.
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		
		// Rebuild the panel once it is submitted so it is blank for the next data entry.
		rebuildPanel();
		
		return true;
	}
	
	/**
	 * @title	getRowCount Method
	 * @return	The highest thc number in the table.
	 * @desc	Returns the max thc number from patients in the database.
	 */
	private int getRowCount()
	{
		// Start out at 0.
		int rowCount = 0;
		
		try 
		{
			// Ask the database for the highest THC number.
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery ("SELECT MAX(THCNumber) FROM Patients;");
			rset.next();
			rowCount = rset.getInt(1);
		} 
		catch (SQLException e) 
		{
			System.out.println("Couldn't get row count.");
			e.printStackTrace();
		}		
		// Return the highest THC number added to the table.
		return rowCount;
	}
	
	/**
	 * @title	rebuildPanel
	 * @desc	Destroys and rebuilds panel to clear all data.
	 */
	protected void rebuildPanel()
	{
		remove(THCNumberLabel);
		remove(THCNumberField);
		remove(currentDateLabel);
		remove(currentDateField);
		remove(firstNameLabel);
		remove(firstNameField);
		remove(middleNameLabel);
		remove(middleNameField);
		remove(lastNameLabel);
		remove(lastNameField);
		remove(dobLabel);
		remove(dobField);
		remove(genderLabel);
		remove(genderField);
		remove(phoneLabel);
		remove(phoneField);
		remove(emailLabel);
		remove(emailField);
		remove(addressLabel);
		remove(addressField);
		remove(cityLabel);
		remove(cityField);
		remove(stateLabel);
		remove(stateField);
		remove(zipLabel);
		remove(zipField);
		remove(countryLabel);
		remove(countryField);
		remove(photoLabel);
		remove(photoField);
		remove(ssnLabel);
		remove(ssnField);
		remove(insuranceLabel);
		remove(insuranceField);
		remove(blankSpace1);
		remove(blankSpace2);
		remove(saveButton);
		remove(addDemoButton);
		remove(newVisitButton);
		remove(cancelButton);
		demographicsFrame.dispose();
		buildPanel();
		repaint();
		revalidate();
	}
	
	/**
	 * @title	rebuildDemoFrame
	 * @desc	Destroys and rebuilds demo frame to erase all inputed data.
	 */
	private void rebuildDemoFrame()
	{
		demographicsFrame.dispose();
		buildDemoFrame();
	}
	
	/**
	 * @title	buildDemoFrame
	 * @desc	Builds the demographic frame and makes it not visible.
	 * 			Visible on button click
	 */
	private void buildDemoFrame()
	{
		// Establish Demographic Frame
		demographicsFrame = new JFrame("eTRT - Add Demographics");
		demographicsFrame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Initialize all variables
		occupationField = new JComboBox<String>(occupationList);
		workStatusField	= new JComboBox<String>(statusList);
		educationField = new JComboBox<String>(eduList);
		tOnsetField = new JTextField(10);
		tEtioField = new JTextField(10);
		hOnsetField = new JTextField(10);
		hEtioField = new JTextField(10);
		commentField = new JTextArea(4, 30);
		demographicsLabel = new JLabel("Demographics");
		occupationLabel = new JLabel("Occupation");
		workStatusLabel = new JLabel("Work Status");
		educationLabel = new JLabel("Educational Degree");
		tOnsetLabel = new JLabel("Tinnitus Onset");
		tEtioLabel = new JLabel("Tinnitus Etiology");
		hOnsetLabel = new JLabel("Hyperacusis Onset");
		hEtioLabel = new JLabel("Hyperacusis Etiology");
		commentLabel = new JLabel("Additional Comments");
		demoSaveButton = new JButton("Save");
		demoCancelButton = new JButton("Cancel");
		
		//Set label to center
		demographicsLabel.setHorizontalAlignment(JLabel.CENTER);
		commentField.setLineWrap(true);
		
		// Checks that there are no more than 150 characters in the comment field.
		commentField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) 
			{
				if(commentField.getText().length() >= 150)
					e.consume();
			}
		});
		
		//Listener to save demographic info
		demoSaveButton.addActionListener(e -> demographicsFrame.setVisible(false));
		//Listener for cancel button
		demoCancelButton.addActionListener(e -> rebuildDemoFrame());
		
		
		// All of the following is for positioning the components correctly.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		demographicsFrame.add(demographicsLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(occupationLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(occupationField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(workStatusLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(workStatusField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(educationLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(educationField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(tOnsetLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(tOnsetField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(tEtioLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(tEtioField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(hOnsetLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(hOnsetField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(hEtioLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(hEtioField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(commentLabel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 3;
		c.gridheight = 4;
		c.insets = new Insets(0, 5, 0, 0);
		demographicsFrame.add(commentField, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(demoCancelButton, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 9;
		c.gridwidth = 1;
		c.gridheight = 1;
		demographicsFrame.add(demoSaveButton, c);
		
		// Sets the rest of the JFrame settings.
		demographicsFrame.setSize(new Dimension(700, 300));
		demographicsFrame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		demographicsFrame.setLocation(d.width/2-demographicsFrame.getSize().width/2, d.height/2-demographicsFrame.getSize().height/2);
		demographicsFrame.setVisible(false);
	}
	
	/**
	 * @title	getSaveButton
	 * @return	JButton - The save button of the panel.
	 */
	protected JButton getSaveButton()
	{
		return saveButton;
	}
	
	/**
	 * @tite	getCancelButton
	 * @return	JButton - The cancel button of the panel.
	 */
	protected JButton getCancelButton()
	{
		return cancelButton;
	}
	
	/**
	 * @title	newVisit
	 * @desc	Opens a new visit tab for the supplied patient.
	 */
	private void newVisit()
	{
		String thc = THCNumberField.getText();
		
		// First submit the information of the new patient.
		try 
		{
			if(!submitInformation())
			{
				return;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		// Open the frame for editing the patient's visit.
		JFrame frame = new JFrame("eTRT - Edit Visit");
		frame.add(new AddNewVisitPanel(conn, thc));
		frame.setSize(new Dimension(600, 450));
		frame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//Center the new frame
		frame.setLocation(d.width/2-frame.getSize().width/2, d.height/2-frame.getSize().height/2);
		frame.setVisible(true);
	}
}
