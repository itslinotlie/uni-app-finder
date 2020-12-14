package main;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
class surveyScreen  {

	// JFrame
	public static JButton[] progressButtons = new JButton[2];
	static JFrame f;
	public static JComboBox q0answers;
	public static JComboBox q1answers;
	public static JComboBox q2answers;
	public static JComboBox q3answers;
	public static JComboBox q4answers;
	public static JComboBox q5answers; 
	
	public static JLabel halt;
	
	public static int civilEngineer = 0;
	public static int softwareEngineer = 0;
	public static int aerospaceEngineer = 0;
	public static int mechanicalEngineer = 0;
	public static int electricalEngineer = 0;
	
	static JPanel survey;

	public static void createSurvey() {
		
		
		survey = new JPanel();
		survey.setLayout(null);
		f = new JFrame ("Survey");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel invalid = new JLabel("INVALID option");
		invalid.setForeground(Color.red);
		invalid.setBounds(400, 400, 260, 50);
		survey.add(invalid);
		invalid.setVisible(false);
		
		halt = new JLabel("You have not completed the survey.");
		halt.setForeground(Color.red);
		halt.setBounds(350, 400, 460, 50);
		survey.add(halt);
		halt.setVisible(false);

		// setup screen
		// survey.setBackground(Color.);
		// buttons
		survey.setBackground(Color.WHITE);
		
		progressButtons[0] = new JButton("Dashboard");
		progressButtons[0].setBounds(300, 450, 100, 50);
		survey.add(progressButtons[0]);
		progressButtons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == progressButtons[0]) {
					Dashboard.CreateDashboard();
				}
			}
		});

		String[] q0answerList = { "Select one", "Lego", "Video games", "Remote vehicles", "Building planes" };
		q0answers = new JComboBox(q0answerList);
		q0answers.setSelectedIndex(0);
		//q0answers.addActionListener(this);
		q0answers.setBounds(290, 135, 150, 25);
		survey.add(q0answers);
		q0answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q0answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				}
				else if (q0answers.getSelectedIndex() == 1) {
					civilEngineer++;
				}
				else if (q0answers.getSelectedIndex() == 2) {
					softwareEngineer++;
				}
				else if (q0answers.getSelectedIndex() == 3) {
					aerospaceEngineer++;
				}
				else if (q0answers.getSelectedIndex() == 4) {
					electricalEngineer++;
				}
				if (q0answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});
		
		String[] q1answerList = {"Select one", "Tinkerer", "Outdoorsy", "Logical", "Adventurous", "Crafty"};
		q1answers = new JComboBox(q1answerList);
		q1answers.setSelectedIndex(0);
		q1answers.setBounds(290, 235, 150, 25);
		survey.add(q1answers);
		q1answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q1answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				}
				else if (q1answers.getSelectedIndex() == 1) {
					electricalEngineer++;
				}
				else if (q1answers.getSelectedIndex() == 2) {
					civilEngineer++;
				}
				else if (q1answers.getSelectedIndex() == 3) {
					softwareEngineer++;
				}
				else if (q1answers.getSelectedIndex() == 4) {
					aerospaceEngineer++;
				}
				else if (q1answers.getSelectedIndex() == 5) {
					mechanicalEngineer++;
				}
				if (q1answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});
		
		String[] q2answerList = { "Select one", "Computer Science", "Astrology", "Social Science", "Phys. Science", "Physics" };
		q2answers = new JComboBox(q2answerList);
		q2answers.setSelectedIndex(0);
		q2answers.setBounds(290, 335, 150, 25);
		survey.add(q2answers);
		q2answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q2answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				}
				else if (q2answers.getSelectedIndex() == 1) {
					softwareEngineer++;
				}
				else if (q2answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				}
				else if (q2answers.getSelectedIndex() == 3) {
					electricalEngineer++;
				}
				else if (q2answers.getSelectedIndex() == 4) {
					mechanicalEngineer++;
				}
				else if (q2answers.getSelectedIndex() == 5) {
					civilEngineer++;
				}
				if (q2answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});
		
		String[] q3answerList = { "Select one", "Programming", "Flying drones", "Tuning an engine", "Testing water samples", "Visiting construction site" };
		q3answers = new JComboBox(q3answerList);
		q3answers.setSelectedIndex(0);
		q3answers.setBounds(720, 135, 155, 25);
		survey.add(q3answers);
		q3answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q3answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				}
				else if (q3answers.getSelectedIndex() == 1) {
					softwareEngineer++;
				}
				else if (q3answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				}
				else if (q3answers.getSelectedIndex() == 3) {
					electricalEngineer++;
				}
				else if (q3answers.getSelectedIndex() == 4) {
					civilEngineer++;
				}
				else if (q3answers.getSelectedIndex() == 5) {
					mechanicalEngineer++;
				}
				if (q3answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});
		
		String[] q4answerList = { "Select one", "National Park", "Airshow", "Big city", "Theme park", "Home" };
		q4answers = new JComboBox(q4answerList);
		q4answers.setSelectedIndex(0);
		q4answers.setBounds(720, 235, 155, 25);
		survey.add(q4answers);
		q4answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q4answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				}
				else if (q4answers.getSelectedIndex() == 1) {
					civilEngineer++;
				}
				else if (q4answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				}
				else if (q4answers.getSelectedIndex() == 3) {
					electricalEngineer++;
				}
				else if (q4answers.getSelectedIndex() == 4) {
					mechanicalEngineer++;
				}
				else if (q4answers.getSelectedIndex() == 5) {
					softwareEngineer++;
				}
				if (q4answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});
		
		String[] q5answerList = { "Select one", "Building a fighter jet", "Making a spaceship", "Designing a future city", "Invent solar power phones", "Writing board game rules" };
		q5answers = new JComboBox(q5answerList);
		q5answers.setSelectedIndex(0);
		q5answers.setBounds(720, 335, 155, 25);
		survey.add(q5answers);
		q5answers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (q5answers.getSelectedIndex() == 0) {
					invalid.setVisible(true);
				}
				else if (q5answers.getSelectedIndex() == 1) {
					mechanicalEngineer++;
				}
				else if (q5answers.getSelectedIndex() == 2) {
					aerospaceEngineer++;
				}
				else if (q5answers.getSelectedIndex() == 3) {
					civilEngineer++;
				}
				else if (q5answers.getSelectedIndex() == 4) {
					electricalEngineer++;
				}
				else if (q5answers.getSelectedIndex() == 5) {
					softwareEngineer++;
				}
				if (q5answers.getSelectedIndex() != 0) {
					invalid.setVisible(false);
				}
			}
		});

		// COLORS ARE NOT ACCURATE, to be changed
//		JRadioButton q3answer1 = new JRadioButton("Yes");
//		q3answer1.setForeground(Color.black);
//		//q3answer1.setBackground(Color.blue);
//		q3answer1.setBounds(575, 125, 50, 50);
//		survey.add(q3answer1);

		// Add labels for questions and title
		JLabel q = new JLabel("Survey");
		q.setBounds(50, -50, 200, 200);
		q.setFont(new Font("Serif", Font.PLAIN, 30));
		q.setForeground(Color.pink);
		survey.add(q);

		JLabel description = new JLabel("Select the option that best applies to you.");
		description.setForeground(Color.pink);
		description.setBounds(50, 50, 250, 50);
		survey.add(description);

		JLabel q0 = new JLabel("What is your favourite activity?");
		q0.setForeground(Color.pink);
		q0.setBounds(20, 125, 260, 50);
		survey.add(q0);

		JLabel q1 = new JLabel("What word would others use to describe you?");
		q1.setForeground(Color.pink);
		q1.setBounds(20, 225, 260, 50);
		survey.add(q1);
		JLabel q2 = new JLabel("Which of these topics do you like best?");
		q2.setForeground(Color.pink);
		q2.setBounds(20, 325, 260, 50);
		survey.add(q2);
		JLabel q3 = new JLabel("How would you like to spend your weekend?");
		q3.setForeground(Color.pink);
		q3.setBounds(450, 125, 260, 50);
		survey.add(q3);
		JLabel q4 = new JLabel("Where would you most like to vacation?");
		q4.setForeground(Color.pink);
		q4.setBounds(450, 225, 260, 50);
		survey.add(q4);
		JLabel q5 = new JLabel("Which of these sounds most fun to you?");
		q5.setForeground(Color.pink);
		q5.setBounds(450, 325, 260, 50);
		survey.add(q5);
		
		progressButtons[1] = new JButton("Done");
		progressButtons[1].setBounds(450, 450, 100, 50);
		// progressButtons[1].addActionListener(this);
		survey.add(progressButtons[1]);
		progressButtons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == progressButtons[1]) {
					if (q0answers.getSelectedIndex() == 0 ||  q1answers.getSelectedIndex() == 0 || q2answers.getSelectedIndex() == 0 || q3answers.getSelectedIndex() == 0 || q4answers.getSelectedIndex() == 0 || q5answers.getSelectedIndex() == 0) {
						halt.setVisible(true);
						invalid.setVisible(false);
					}
					else {
						halt.setVisible(false);
					}
					
				}
			}
		});
		
		f.add(survey);
		f.setSize(900, 600);
		f.setVisible(true);

	}
	
	public static String calculateResults() {
		String results = null;
		
		if (aerospaceEngineer >= civilEngineer && aerospaceEngineer >= softwareEngineer && aerospaceEngineer >= mechanicalEngineer && aerospaceEngineer >= electricalEngineer)
			results = "Aerospace Engineer";
		else if (civilEngineer >= aerospaceEngineer && civilEngineer >= softwareEngineer && civilEngineer >= mechanicalEngineer && civilEngineer >= electricalEngineer)
			results = "Civil Engineer";
		else if (softwareEngineer >= aerospaceEngineer && softwareEngineer >= civilEngineer && softwareEngineer >= mechanicalEngineer && softwareEngineer >= electricalEngineer)
			results = "Software Engineer";
		else if(mechanicalEngineer >= aerospaceEngineer && mechanicalEngineer >= softwareEngineer && mechanicalEngineer >= civilEngineer && mechanicalEngineer >= electricalEngineer)
			results = "Mechanical Engineer";
		else if (electricalEngineer >= aerospaceEngineer && electricalEngineer >= softwareEngineer && electricalEngineer >= civilEngineer && electricalEngineer >= mechanicalEngineer)
			results = "Electrical Engineer";
			
		return results;
		
	}

}