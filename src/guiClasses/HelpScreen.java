package guiClasses;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tools.Colour;

/*
 * Jordan's Screen
 * The class HelpScreen will direct the user to show them how
 * our program is used. It will display how to traverse our program step by step
 */
public class HelpScreen extends JPanel {

	// GUI
	public static JPanel helpPanel;

	// Private variables
	private static final int SCREEN_WIDTH = 648;
	private static final int SCREEN_HEIGHT = 1152;

	public static void CreateHelp() {

		// Create panel
		helpPanel = new JPanel();
		helpPanel.setBackground(Colour.bg);
		helpPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		helpPanel.setLayout(null);
		Welcome.GUI.getContentPane().add(helpPanel);

		// Create title
		JLabel title = new JLabel("Help Screen");
		title.setFont(new Font("Tahoma", Font.BOLD, 36));
		title.setBackground(Colour.bg);
		title.setForeground(Colour.strongHighlight);
		title.setBounds(500, 25, 400, 75);
		helpPanel.add(title);

		// Create step labels
		JLabel[] step = new JLabel[8];

		for (int counter = 0; counter < step.length; counter++) {
			step[counter] = new JLabel();
			step[counter].setFont(new Font("Tahoma", Font.PLAIN, 16));
			step[counter].setBackground(Colour.bg);

			if (counter % 2 == 1)
				step[counter].setForeground(Colour.strike);
			else
				step[counter].setForeground(Colour.contrast);
			step[counter].setBounds(25, 75 + 60 * counter, 1000, 100);
			helpPanel.add(step[counter]);
		}

		// Create custom text in each label
		step[0].setText(
				"<html>Step 1: Login in if you have an account, otherwise, create an account by clicking on the create account button<html>");
		step[1].setText(
				"<html>Step 2: After logging in or creating a new account, you will then be sent to the dashboard. From the dashboard you may access the University Information tab, View Map tab, My Preferences tab, or the Take Survey Tab<html>");
		step[2].setText(
				"<html>Step 3: In the University Information tab you can view all engineering universities, the screen displays the university name, the logo, the description, and an image of the campus<html>");
		step[3].setText(
				"<html>Step 4: In the View Map tab, you can set a point on the map by clicking on it, or enter your postal code to find the distance from your home to each university<html>");
		step[4].setText(
				"<html>Step 5: In the My Preferences tab, you can view and enter information about yourself and your preferences to assist in you choosing a university<html>");
		step[5].setText(
				"<html>Step 6: After entering your information, you can click on the results screen to view 2 universities that you have matched with.<html>");
		step[6].setText(
				"<html>Step 7: After, in the Take Survey tab, you can complete a multiple choice survey to match yourself to a stream of engineering<html>");
		step[7].setText(
				"<html>Step 8: Upon completion, you can view the stream you have matched and a short description of what the stream is<html>");

		JButton back = new JButton("Back");

		back.setBounds(1000, 550, 100, 30);
		back.setBackground(Colour.strike);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Welcome.signIn) {
					helpPanel.setVisible(false);
					Dashboard.dashboardPanel.setVisible(true);
				} else {
					helpPanel.setVisible(false);
					Welcome.welcomePanel.setVisible(true);
				}
			}
		});
		helpPanel.add(back);

	}

}
