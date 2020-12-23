package guiClasses;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.VerifyLogin;
import tools.Colour;

/*
 * Jordan's screen
 * The UniMatchmakerInfoEdit class allows the user to edit all of their
 * information such as their courses, grades and external university
 * desicion factors.
 */
public class UniMatchmakerInfoEdit extends JPanel {

	public static JPanel accountEditPanel;
	public static JTextField[] courseTextField = new JTextField[6];
	public static JTextField[] gradeTextField = new JTextField[6];
	private static JLabel[] headings = new JLabel[6];
	public static JComboBox[] dropDownLists = new JComboBox[6];
	public static JSlider[] sliders = new JSlider[6];
	public static boolean save = false;
	public static int a;

	public static void EditAccount() {

		Border border = BorderFactory.createLineBorder(Colour.strongHighlight, 3);

		// Create panel
		accountEditPanel = new JPanel();
		accountEditPanel.setBackground(Colour.bg);
		Dashboard.displayPanel.add(accountEditPanel);
		accountEditPanel.setLayout(null);

		// Create panel for courses
		JPanel coursesPanel = new JPanel();
		coursesPanel.setBounds(10, 90, 300, 510);
		coursesPanel.setBackground(Colour.bg);
		coursesPanel.setLayout(null);
		coursesPanel.setBorder(border);
		accountEditPanel.add(coursesPanel);

		// Create panel for external factors
		JPanel externalPanel = new JPanel();
		externalPanel.setBounds(325, 90, 590, 510);
		externalPanel.setBackground(Colour.bg);
		externalPanel.setLayout(null);
		externalPanel.setBorder(border);
		accountEditPanel.add(externalPanel);

		// Create title
		JLabel titleLabel = new JLabel("Preference University Matchmaker");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		titleLabel.setForeground(Colour.strongHighlight);
		titleLabel.setBounds(200, 10, 500, 65);
		accountEditPanel.add(titleLabel);

		// Create description
		JLabel descriptionLabel = new JLabel("External Factors:");
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		descriptionLabel.setForeground(Colour.strongHighlight);
		descriptionLabel.setBounds(25, 15, 280, 40);
		externalPanel.add(descriptionLabel);

		// Create courses and grades heading
		JLabel courseGradeLabel = new JLabel("Courses and Grades:");
		courseGradeLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
		courseGradeLabel.setForeground(Colour.strongHighlight);
		courseGradeLabel.setBounds(40, 15, 280, 40);
		coursesPanel.add(courseGradeLabel);

		// Create save buttons
		JButton saveButton = new JButton("Save");
		saveButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		saveButton.setBackground(Colour.strike);
		saveButton.setBounds(800, 30, 75, 30);
		saveButton.addActionListener(new ActionListener() {

			// Save person's data and goes to results screen
			public void actionPerformed(ActionEvent e) {
				try {
					VerifyLogin.saveInformation(CreateAccount.username, CreateAccount.password, gradeTextField,
							courseTextField, dropDownLists[0].getSelectedIndex(), dropDownLists[1].getSelectedIndex(),
							dropDownLists[2].getSelectedIndex(), dropDownLists[3].getSelectedIndex(),
							dropDownLists[4].getSelectedIndex(), dropDownLists[5].getSelectedIndex(),
							sliders[0].getValue(), sliders[1].getValue(), sliders[2].getValue(), sliders[3].getValue(),
							sliders[4].getValue(), sliders[5].getValue());
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				save = true;
				Dashboard.hidePanel();
				UniMatchmaker.CreateAccount();
				UniMatchmaker.accountPanel.setVisible(true);
			}
		});
		accountEditPanel.add(saveButton);

		// Create instructions label
		JLabel instructions = new JLabel(
				"<html>Remember to use the sliders to indicate the level of weight of each external factor as it will have an impact on which universities you may get!<br>*Note: weight 0 and option \"Does not matter\" will remove the factor from you result*<html>");
		instructions.setFont(new Font("Tahoma", Font.PLAIN, 14));
		instructions.setForeground(Colour.strike);
		instructions.setBounds(25, 55, 550, 60);
		externalPanel.add(instructions);

		// Create external factors headings
		headings[0] = new JLabel("Ranking:");
		headings[1] = new JLabel("Tuition:");
		headings[2] = new JLabel("University Size:");
		headings[3] = new JLabel("Distance:");
		headings[4] = new JLabel("Residence Cost:");
		headings[5] = new JLabel("Class Size:");

		// Create courses and grades label + external factors headings + slider and
		// combo boxes
		for (int counter = 0; counter < courseTextField.length; counter++) {
			courseTextField[counter] = new JTextField("Course");
			courseTextField[counter].setBounds(30, 75 + 75 * counter, 125, 30);
			coursesPanel.add(courseTextField[counter]);

			gradeTextField[counter] = new JTextField("Grade");
			gradeTextField[counter].setBounds(160, 75 + 75 * counter, 70, 30);
			coursesPanel.add(gradeTextField[counter]);

			headings[counter].setFont(new Font("Tahoma", Font.PLAIN, 18));
			headings[counter].setForeground(Colour.strongHighlight);

			dropDownLists[counter] = new JComboBox();

			sliders[counter] = new JSlider();
			sliders[counter].setBackground(Colour.bg);
			sliders[counter].setForeground(Colour.strongHighlight);
			sliders[counter].setMajorTickSpacing(1);
			sliders[counter].setMinimum(0);
			sliders[counter].setMaximum(10);
			sliders[counter].setPaintTicks(true);
			sliders[counter].setPaintLabels(true);
			sliders[counter].setSnapToTicks(true);

			if (counter <= 2) {
				headings[counter].setBounds(25, 120 + 130 * counter, 200, 30);
				dropDownLists[counter].setBounds(25, 155 + 130 * counter, 200, 30);
				sliders[counter].setBounds(20, 195 + 130 * counter, 210, 40);
			} else {
				headings[counter].setBounds(300, 120 + 130 * (counter - 3), 200, 30);
				dropDownLists[counter].setBounds(300, 155 + 130 * (counter - 3), 200, 30);
				sliders[counter].setBounds(295, 195 + 130 * (counter - 3), 210, 40);
			}

			externalPanel.add(headings[counter]);
			externalPanel.add(dropDownLists[counter]);
			externalPanel.add(sliders[counter]);

		}

		// Edit combo boxes
		dropDownLists[0].addItem("Top 5");
		dropDownLists[0].addItem("Top 10");
		dropDownLists[0].addItem("Does not matter");

		dropDownLists[1].addItem("<$10,000");
		dropDownLists[1].addItem("$10,000 - $15,000");
		dropDownLists[1].addItem(">$15,000");
		dropDownLists[1].addItem("Does not matter");

		dropDownLists[2].addItem("<10,000");
		dropDownLists[2].addItem("10,000 - 50,000");
		dropDownLists[2].addItem(">50,000");
		dropDownLists[2].addItem("Does not matter");

		dropDownLists[3].addItem("<30 km (Commutable)");
		dropDownLists[3].addItem("30 km - 150 km (1 - 2 hrs)");
		dropDownLists[3].addItem("150 km - 300 km (2 - 4 hrs)");
		dropDownLists[3].addItem(">300 km (4 hrs +)");
		dropDownLists[3].addItem("Does not matter");

		dropDownLists[4].addItem("<$10,000");
		dropDownLists[4].addItem("$10,000 - $12,000");
		dropDownLists[4].addItem(">$12,000");
		dropDownLists[4].addItem("Does not matter");

		dropDownLists[5].addItem("<100");
		dropDownLists[5].addItem("100 - 300");
		dropDownLists[5].addItem(">300");
		dropDownLists[5].addItem("Does not matter");

		// Load if user's information exists
		if (VerifyLogin.verifyInformation()) {
			int index = 0;
			ArrayList<String> Information = VerifyLogin.loadInformation();

			for (int x = 0; x < 6; x++) {
				gradeTextField[x].setText(Information.get(index));
				++index;
				;
			}
			for (int x = 0; x < 6; x++) {
				courseTextField[x].setText(Information.get(index));
				++index;
			}
			for (int x = 0; x < 6; x++) {
				dropDownLists[x].setSelectedIndex(Integer.parseInt(Information.get(index)));
				++index;
			}
			for (int x = 0; x < 6; x++) {
				sliders[x].setValue(Integer.parseInt(Information.get(index)));
				++index;
			}
		}
	}

}
