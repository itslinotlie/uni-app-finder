package guiClasses;

import java.awt.event.*;
import java.awt.*;
import java.awt.Color;

import javax.swing.*;

import tools.Colour;

public class SurveyScreen {

// Buttons and Combo boxes to progress to next screen and choose answers.
	private static JButton progressButtons;
	private static JComboBox q0answers;
	private static JComboBox q1answers;
	private static JComboBox q2answers;
	private static JComboBox q3answers;
	private static JComboBox q4answers;
	private static JComboBox q5answers;

//Popup message, notifies user that they cannot continue
	private static JLabel halt;

//integer variables to calculate results of which engineering the user should go into
	private static int civilEngineer = 0;
	private static int softwareEngineer = 0;
	private static int aerospaceEngineer = 0;
	private static int mechanicalEngineer = 0;
	private static int electricalEngineer = 0;

//creates Panel
	public static JPanel survey;

//creates the screen
	public SurveyScreen() {

		// create the panel and frame
		survey = new JPanel();
		survey.setBounds(210, 0, 930, 610);
		survey.setBackground(Colour.bg);
		survey.setLayout(null);

		// popup message if the user chooses an invalid option
		JLabel invalid = new JLabel("INVALID option");
		invalid.setForeground(Color.red); // set color to red
		invalid.setBounds(520, 400, 260, 50); // places message on screen
		survey.add(invalid); // add message to panel
		invalid.setVisible(false); // set the visibility to false. The message should not appear unless the user
									// chooses an invalid option in their answers

		// label for if the user tries to proceed without completing the survey
		halt = new JLabel("You have not completed the survey.");
		halt.setForeground(Color.red); // set text to red
		halt.setBounds(470, 425, 460, 50); // place label
		survey.add(halt); // put label onto panel
		halt.setVisible(false); // label does not appear until necessary

		// combo box answer list for first question
		String[] q0answerList = { "Select one", "Lego", "Video games", "Remote vehicles", "Building planes" };
		q0answers = new JComboBox(q0answerList); // combo box to store answers
		q0answers.setSelectedIndex(0); // set the beginning answer to "Select one"
		q0answers.setBounds(295, 135, 160, 25); // place button
		survey.add(q0answers); // add button onto panel
		q0answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q0answers.getSelectedIndex() == 0) { // if the user chooses "Select one" as their answer, invalid
															// option message will appear
					invalid.setVisible(true);
				} else if (q0answers.getSelectedIndex() == 1) { // adds to the result depending on which answer the user
																// chooses.
					civilEngineer++;
				} else if (q0answers.getSelectedIndex() == 2) {
					softwareEngineer++;
				} else if (q0answers.getSelectedIndex() == 3) {
					aerospaceEngineer++;
				} else if (q0answers.getSelectedIndex() == 4) {
					electricalEngineer++;
				}
				if (q0answers.getSelectedIndex() != 0) { // if the user chooses any valid answer, the invalid options
															// message will go away.
					invalid.setVisible(false);
				}
			}
		});

		// combo box for question 2
		String[] q1answerList = { "Select one", "Tinkerer", "Outdoorsy", "Logical", "Adventurous", "Crafty" };
		q1answers = new JComboBox(q1answerList);
		q1answers.setSelectedIndex(0);
		q1answers.setBounds(295, 235, 160, 25);
		survey.add(q1answers);
		q1answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q1answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				} else if (q1answers.getSelectedIndex() == 1) {
					electricalEngineer++;
				} else if (q1answers.getSelectedIndex() == 2) {
					civilEngineer++;
				} else if (q1answers.getSelectedIndex() == 3) {
					softwareEngineer++;
				} else if (q1answers.getSelectedIndex() == 4) {
					aerospaceEngineer++;
				} else if (q1answers.getSelectedIndex() == 5) {
					mechanicalEngineer++;
				}
				if (q1answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});

		// combo box for question 3
		String[] q2answerList = { "Select one", "Computer Science", "Astrology", "Social Science", "Phys. Science",
				"Physics" };
		q2answers = new JComboBox(q2answerList);
		q2answers.setSelectedIndex(0);
		q2answers.setBounds(295, 335, 160, 25);
		survey.add(q2answers);
		q2answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q2answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				} else if (q2answers.getSelectedIndex() == 1) {
					softwareEngineer++;
				} else if (q2answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				} else if (q2answers.getSelectedIndex() == 3) {
					electricalEngineer++;
				} else if (q2answers.getSelectedIndex() == 4) {
					mechanicalEngineer++;
				} else if (q2answers.getSelectedIndex() == 5) {
					civilEngineer++;
				}
				if (q2answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});

		// combo box for question 4
		String[] q3answerList = { "Select one", "Programming", "Flying drones", "Tuning an engine",
				"Testing water samples", "Visiting construction site" };
		q3answers = new JComboBox(q3answerList);
		q3answers.setSelectedIndex(0);
		q3answers.setBounds(745, 135, 160, 25);
		survey.add(q3answers);
		q3answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q3answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				} else if (q3answers.getSelectedIndex() == 1) {
					softwareEngineer++;
				} else if (q3answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				} else if (q3answers.getSelectedIndex() == 3) {
					electricalEngineer++;
				} else if (q3answers.getSelectedIndex() == 4) {
					civilEngineer++;
				} else if (q3answers.getSelectedIndex() == 5) {
					mechanicalEngineer++;
				}
				if (q3answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});

		// combo box for question 5
		String[] q4answerList = { "Select one", "National Park", "Airshow", "Big city", "Theme park", "Home" };
		q4answers = new JComboBox(q4answerList);
		q4answers.setSelectedIndex(0);
		q4answers.setBounds(745, 235, 160, 25);
		survey.add(q4answers);
		q4answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q4answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				} else if (q4answers.getSelectedIndex() == 1) {
					civilEngineer++;
				} else if (q4answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				} else if (q4answers.getSelectedIndex() == 3) {
					electricalEngineer++;
				} else if (q4answers.getSelectedIndex() == 4) {
					mechanicalEngineer++;
				} else if (q4answers.getSelectedIndex() == 5) {
					softwareEngineer++;
				}
				if (q4answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});

		// combo box for question 6
		String[] q5answerList = { "Select one", "Building a fighter jet", "Making a spaceship",
				"Designing a future city", "Invent solar power phones", "Writing board game rules" };
		q5answers = new JComboBox(q5answerList);
		q5answers.setSelectedIndex(0);
		q5answers.setBounds(745, 335, 160, 25);
		survey.add(q5answers);
		q5answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q5answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				} else if (q5answers.getSelectedIndex() == 1) {
					mechanicalEngineer++;
				} else if (q5answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				} else if (q5answers.getSelectedIndex() == 3) {
					civilEngineer++;
				} else if (q5answers.getSelectedIndex() == 4) {
					electricalEngineer++;
				} else if (q5answers.getSelectedIndex() == 5) {
					softwareEngineer++;
				}
				if (q5answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});

		// Add labels for questions and title
		JLabel q = new JLabel("Survey");
		q.setBounds(25, -50, 200, 200);
		q.setFont(new Font("Serif", Font.PLAIN, 30));
		q.setForeground(new Color(255, 138, 226));
		survey.add(q);

		// instructions for screen
		JLabel description = new JLabel("Select the option that best applies to you.");
		description.setForeground(new Color(255, 138, 226));
		description.setBounds(25, 60, 250, 50);
		survey.add(description);

		// List of questions
		JLabel q0 = new JLabel("What is your favourite activity?");
		q0.setForeground(new Color(255, 138, 226));
		q0.setBounds(20, 125, 260, 50);
		survey.add(q0);

		JLabel q1 = new JLabel("What word would others use to describe you?");
		q1.setForeground(new Color(255, 138, 226));
		q1.setBounds(20, 225, 260, 50);
		survey.add(q1);
		JLabel q2 = new JLabel("Which of these topics do you like best?");
		q2.setForeground(new Color(255, 138, 226));
		q2.setBounds(20, 325, 260, 50);
		survey.add(q2);
		JLabel q3 = new JLabel("How would you like to spend your weekend?");
		q3.setForeground(new Color(255, 138, 226));
		q3.setBounds(475, 125, 260, 50);
		survey.add(q3);
		JLabel q4 = new JLabel("Where would you most like to vacation?");
		q4.setForeground(new Color(255, 138, 226));
		q4.setBounds(475, 225, 260, 50);
		survey.add(q4);
		JLabel q5 = new JLabel("Which of these sounds most fun to you?");
		q5.setForeground(new Color(255, 138, 226));
		q5.setBounds(475, 325, 260, 50);
		survey.add(q5);

		// Button for when the user is ready to proceed
		progressButtons = new JButton("Done");
		progressButtons.setBounds(750, 500, 100, 40);
		progressButtons.setBackground(Colour.strike);
		// progressButtons[1].addActionListener(this);
		survey.add(progressButtons);
		progressButtons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == progressButtons) {
					if (q0answers.getSelectedIndex() == 0 || q1answers.getSelectedIndex() == 0
							|| q2answers.getSelectedIndex() == 0 || q3answers.getSelectedIndex() == 0
							|| q4answers.getSelectedIndex() == 0 || q5answers.getSelectedIndex() == 0) {
						halt.setVisible(true);
						invalid.setVisible(false);
					} else {
						halt.setVisible(false);
						survey.setVisible(false);
						new SurveyResults();
					}

				}
			}
		});

	}

	public static String calculateResults() {
		String results = null;

		if (aerospaceEngineer >= civilEngineer && aerospaceEngineer >= softwareEngineer
				&& aerospaceEngineer >= mechanicalEngineer && aerospaceEngineer >= electricalEngineer)
			results = "Aerospace Engineer";
		else if (civilEngineer >= aerospaceEngineer && civilEngineer >= softwareEngineer
				&& civilEngineer >= mechanicalEngineer && civilEngineer >= electricalEngineer)
			results = "Civil Engineer";
		else if (softwareEngineer >= aerospaceEngineer && softwareEngineer >= civilEngineer
				&& softwareEngineer >= mechanicalEngineer && softwareEngineer >= electricalEngineer)
			results = "Software Engineer";
		else if (mechanicalEngineer >= aerospaceEngineer && mechanicalEngineer >= softwareEngineer
				&& mechanicalEngineer >= civilEngineer && mechanicalEngineer >= electricalEngineer)
			results = "Mechanical Engineer";
		else if (electricalEngineer >= aerospaceEngineer && electricalEngineer >= softwareEngineer
				&& electricalEngineer >= civilEngineer && electricalEngineer >= mechanicalEngineer)
			results = "Electrical Engineer";

		return results;

	}
}