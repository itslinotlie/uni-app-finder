package guiClasses;

import objects.UniversitiesInformation;
import tools.Colour;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Jordan's Screen
 * The dashboard class creates the side taskbar as well as the panel that will
 * display the information when a taskbar button is pressed. As well, the intro
 * screen is created in this class.
 */
public class Dashboard extends JPanel {

	// GUI
	public static JPanel dashboardPanel;
	public static JPanel taskbarPanel;
	public static JPanel displayPanel;
	public static JPanel introPanel;

	// Public variables
	public static boolean results;
	public static boolean results2;

	// Private objects
	private static UniversitiesInformation universities = new UniversitiesInformation();
	private static MapScreen mapScreen = new MapScreen(universities);

	public static void CreateDashboard() {

		// Create dashboard panel
		dashboardPanel = new JPanel();
		Welcome.GUI.getContentPane().add(dashboardPanel);
		dashboardPanel.setLayout(null);

		// Create taskbar panel
		taskbarPanel = new JPanel();
		taskbarPanel.setBounds(0, 0, 210, 610);
		taskbarPanel.setBackground(Colour.lightBg);
		dashboardPanel.add(taskbarPanel);
		taskbarPanel.setLayout(null);

		// Create display panel
		displayPanel = new JPanel();
		displayPanel.setBounds(210, 0, 930, 610);
		dashboardPanel.add(displayPanel);
		displayPanel.setLayout(new CardLayout(0, 0));

		// Create introduction panel
		introPanel = new JPanel();
		displayPanel.add(introPanel);
		introPanel.setLayout(null);
		introPanel.setBackground(Colour.bg);

		// Create all programs screen
		new AllPrograms();
		displayPanel.add(AllPrograms.overallPanel);

		// Create survey screen
		new SurveyScreen();
		displayPanel.add(SurveyScreen.survey);

		// Add map screen
		mapScreen.getMapPanel().setBounds(210, 0, 920, 610);
		displayPanel.add(mapScreen.getMapPanel());

		// Add distances screen
		mapScreen.getDistancePanel().setBounds(210, 0, 920, 610);
		displayPanel.add(mapScreen.getDistancePanel());

		// Add logo
		JLabel logoLabel = new JLabel(new ImageIcon("resources/misc/program-logo-fill(s).png"));
		logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Make logo clickable (goes to the home screen)
		// Code used from
		// https://www.codejava.net/java-se/swing/how-to-create-hyperlink-with-jlabel-in-java-swing
		logoLabel.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				hidePanel();
				introPanel.setVisible(true);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		logoLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoLabel.setBounds(0, 20, 210, 100);
		taskbarPanel.add(logoLabel);

		// Create taskbar button
		JButton uniInfoButton = new JButton("University Information");
		uniInfoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				AllPrograms.overallPanel.setVisible(true);
				AllPrograms.resetButton.doClick();

			}
		});
		uniInfoButton.setBounds(25, 150, 160, 25);
		taskbarPanel.add(uniInfoButton);

		// Create taskbar button
		JButton accountButton = new JButton("My Preferences");
		accountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				UniMatchmaker.accountPanel.setVisible(true);
			}
		});
		accountButton.setBounds(25, 270, 160, 25);
		taskbarPanel.add(accountButton);

		// Create taskbar button
		JButton quizButton = new JButton("Take Survey");
		quizButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				SurveyScreen.survey.setVisible(true);
			}
		});
		quizButton.setBounds(25, 330, 160, 25);
		taskbarPanel.add(quizButton);

		// Create taskbar button
		JButton mapButton = new JButton("View Map");
		mapButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				mapScreen.getMapPanel().setVisible(true);
			}
		});
		mapButton.setBounds(25, 210, 160, 25);
		taskbarPanel.add(mapButton);

		// Create taskbar button
		JButton helpButton2 = new JButton("Help");
		helpButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dashboardPanel.setVisible(false);
				HelpScreen.CreateHelp();
				HelpScreen.helpPanel.setVisible(true);
			}
		});
		helpButton2.setBounds(45, 500, 120, 25);
		taskbarPanel.add(helpButton2);

		// Create taskbar button
		JButton signOutButton = new JButton("Sign Out");
		signOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hidePanel();
				dashboardPanel.setVisible(false);
				Welcome.usernameField.setText("");
				Welcome.passwordField.setText("");
				Welcome.welcomePanel.setVisible(true);
			}
		});
		signOutButton.setBounds(45, 550, 120, 25);
		taskbarPanel.add(signOutButton);

		// Create into screen label
		JLabel titleLabel2 = new JLabel("School Finder");
		titleLabel2.setFont(new Font("Tahoma", Font.PLAIN, 36));
		titleLabel2.setForeground(Colour.strongHighlight);
		titleLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel2.setBounds(0, 30, 925, 90);
		introPanel.add(titleLabel2);

		// Create into screen label
		JLabel quoteLabel = new JLabel(
				"<html>\"Your time is limited, so don't waste it living someone else's life. Don't be trapped by dogma - which is living with the results of other people's thinking.\" - Steve Jobs<htlm>");
		quoteLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quoteLabel.setForeground(Colour.strike);
		quoteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quoteLabel.setBounds(25, 175, 600, 90);
		introPanel.add(quoteLabel);

		// Create into screen label
		JLabel quoteLabel2 = new JLabel(
				"<html>\"If life were predictable it would crease to be life, and be without flavor.\" - Eleanor Roosevelt<htlm>");
		quoteLabel2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quoteLabel2.setForeground(Colour.highlight);
		quoteLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		quoteLabel2.setBounds(150, 300, 600, 75);
		introPanel.add(quoteLabel2);

		// Create into screen label
		JLabel quoteLabel3 = new JLabel(
				"<html>\"Many of life's failures are people who did not realize how close they were to success when they gave up.\" - Thomas A. Edison<htlm>");
		quoteLabel3.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quoteLabel3.setForeground(Colour.contrast);
		quoteLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		quoteLabel3.setBounds(300, 425, 600, 75);
		introPanel.add(quoteLabel3);

		// Create into screen label
		JLabel creatorLabel = new JLabel("Created By: Brittany, Jeffrey, Jordan, Michael and Mithun");
		creatorLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		creatorLabel.setForeground(Colour.strongHighlight);
		creatorLabel.setBounds(20, 550, 895, 60);
		introPanel.add(creatorLabel);

		// Create all other screens
		UniMatchmakerInfoEdit.EditAccount();
		UniMatchmaker.CreateAccount();
		hidePanel();

	}

	// Hide all panels
	public static void hidePanel() {
		introPanel.setVisible(false);
		AllPrograms.overallPanel.setVisible(false);
		mapScreen.getMapPanel().setVisible(false);
		mapScreen.getDistancePanel().setVisible(false);
		UniMatchmaker.accountPanel.setVisible(false);
		UniMatchmakerInfoEdit.accountEditPanel.setVisible(false);
		SurveyScreen.survey.setVisible(false);

		// Hide results panels after they are created
		if (results)
			Results.resultsPanel.setVisible(false);

		if (results2)
			SurveyResults.surveyResults.setVisible(false);

	}

}
