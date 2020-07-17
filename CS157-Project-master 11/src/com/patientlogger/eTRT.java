package com.patientlogger;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * @title	eTRT Class
 * @author	Jeevvansh Lota, Nick Fulton, Jack Fogerson
 * @desc	The main application. Holds all tabbed panes.
 */
public class eTRT 
{
	// Creates the frame and connection.
	JFrame mainFrame;
	Connection conn;
	
	public eTRT()
	{
		try
		{
			// Establishs connection to the database.
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection("jdbc:mysql://localhost/eTRTSchema?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","User1", "SJSUcs157");
		}
		catch(SQLException e)
		{
			// Error out if the connection can not be established.
			e.printStackTrace();
		}
	}
	
	/**
	 * @title	launch
	 * @desc	Starts the program.
	 */
	public void launch()
	{
		buildFrames();
	}
	
	HomePanel homePanel;
	PatientsPanel patientsPanel;
	VisitsPanel visitsPanel;
	OtherPanel otherPanel;
	/**
	 * @title	buildFrames
	 * @desc	Builds everything inside of the application.
	 */
	private void buildFrames()
	{
		// Creates frame and sets the layout.
		mainFrame = new JFrame("eTRT - Decision Support System for Tinnitus Restraining Therapy");
		mainFrame.setLayout(new CardLayout());
		
		try
		{
			// Sets application icon to the eTRT logo
			mainFrame.setIconImage(ImageIO.read(getClass().getResourceAsStream("/images/eTRT_icon.png")));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		// Creates JTabbedPane for the application.
		JTabbedPane mainPane = new JTabbedPane();
		
		// Create all of the panels to be put into the JTabbedPane.
		homePanel = new HomePanel();
		patientsPanel = new PatientsPanel(conn);
		visitsPanel = new VisitsPanel(conn);
		otherPanel = new OtherPanel(conn);
		
		// Add tabs to the JTabbedPane.
		mainPane.addTab("Home", null, homePanel, "Spash Screen");
		mainPane.addTab("Patients", null, patientsPanel, "Information for Patients");
		mainPane.addTab("Visits", null, visitsPanel, "Information for Visits");
		mainPane.addTab("Other", null, otherPanel, "Other Information");
		
		mainPane.addChangeListener(e -> refreshTabs());
		
		// Add the JTabbedPane to the main frame.
		mainFrame.add(mainPane);
		
		// Finish JFrame settings.
		mainFrame.setSize(new Dimension(650, 450));
		mainFrame.setResizable(false);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		//Center Frame
		mainFrame.setLocation(d.width/2-mainFrame.getSize().width/2, d.height/2-mainFrame.getSize().height/2);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	} 
	
	/**
	 * @title	refreshTabs
	 * @desc	Rebuilds the tabs when called.
	 */
	private void refreshTabs()
	{
		patientsPanel.refresh();
		visitsPanel.refresh();
		otherPanel.refresh();
	}
}
