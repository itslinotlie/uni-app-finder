package guiClasses;

import objects.UniversitiesInformation;
import objects.University;
import objects.User;
import tools.Colour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import jdk.dynalink.linker.ConversionComparator.Comparison;
import static java.awt.Color.*;

/*
	* Jeffrey's Screen
 * The AllPrograms class creates a JPanel that will display to the user
 * all of the programs, 1 by 1. It allows the user to sort either alphabetically, or
 * by average, and in reverse respectively. It displays a basic description about the
 * university, their national rank, a picture, and whether the user's grades
 * would be enough to get in, as well as the NSSE information.
 */
public class AllPrograms extends JPanel implements ActionListener {

	private JLabel title = new JLabel("All Programs");
	private JButton nextBtn = new JButton();
	private JButton backBtn = new JButton();
	private JComboBox<String> combobox1 = new JComboBox<>();
	private JComboBox<String> combobox2 = new JComboBox<>();
	private JLabel sortBy = new JLabel("Sort by:");
	private JLabel picture = new JLabel();
	private ArrayList<University> uniArrayCopy;
	private int currentPage = 0;
	private boolean reversed = false;
	UniversitiesInformation uniClass = new UniversitiesInformation();
	private JTextField keyword = new JTextField();
	private JButton searchButton = new JButton("Search");
	private JLabel searchLabel = new JLabel("Search");
	private JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 1, 0, 14);
	private String[] sorting = new String[] { "Alpha: A-Z", "Alpha: Z-A", "Average: Low to High",
			"Average: High to Low", "Bookmarked Universities" };
	private JPanel uniPanel;
	private ArrayList<University> customList = new ArrayList<>(0);
	private int maxIndex = 14;
	private JLabel searchFailed = new JLabel();
	private boolean searchFailedTF;
	public static JButton resetButton = new JButton();
	public static JPanel overallPanel = new JPanel();
	private JLabel currentLocation = new JLabel();
	private JButton website = new JButton();
	private JButton bookmark = new JButton();
	private int totalMoves = 0;
	private JButton nsseButton = new JButton();

	// constructor to setup the GUI screen
	public AllPrograms() {
		overallPanel = new JPanel();
		overallPanel.setBounds(0, 0, 920, 610);
		overallPanel.setLayout(null);
		overallPanel.setVisible(true);
		overallPanel.setBackground(Colour.bg);
		// Setting up the GUI panel
		UniversitiesInformation.setUniversities();
		uniArrayCopy = new ArrayList<>(uniClass.getUniversities());
		setLayout(null);

		uniPanel = createUniPanel(uniArrayCopy.get(0));
		uniPanel.setBounds(020, 120, 900, 900);
		uniPanel.setBackground(Colour.bg);
		overallPanel.add(uniPanel);
		uniPanel.setVisible(true);

		currentPage = 0;

		combobox1.addItem("Select a Program");
		// setting up combobox1, with the names of all the unis
		for (University uni : uniArrayCopy) {
			combobox1.addItem(uni.getName());
		}
		// setting up the picture
		picture.setIcon(new ImageIcon(uniArrayCopy.get(0).getIconImage()));
		picture.setBounds(300, 35, 700, 500);
		picture.setForeground(white);
		overallPanel.add(picture);

		// setting up the combobox
		combobox1.setBounds(20, 80, 200, 40);
		combobox1.addActionListener(this);
		combobox1.setVisible(true);
		combobox1.setBackground(Colour.strike);
		combobox1.setBorder(BorderFactory.createLineBorder(Colour.strike));
		combobox1.setFont(new Font("Tahoma", Font.BOLD, 12));
		overallPanel.add(combobox1);

		// setting up the sorting combobox(adding the options)
		for (String sorting : sorting) {
			combobox2.addItem(sorting);
		}
		combobox2.setBounds(330, 80, 200, 40);
		combobox2.addActionListener(this);
		combobox2.setVisible(true);
		combobox2.setBackground(Colour.strike);
		combobox2.setFont(new Font("Tahoma", Font.BOLD, 12));
		combobox2.setBorder(BorderFactory.createLineBorder(Colour.strike));
		overallPanel.add(combobox2);

		sortBy.setBounds(330, 50, 100, 30);
		sortBy.setVisible(true);
		sortBy.setForeground(Colour.strongHighlight);
		sortBy.setFont(new Font("Tahoma", Font.BOLD, 12));
		overallPanel.add(sortBy);

		title.setBounds(20, 10, 500, 50);
		title.setFont(new Font("Tahoma", Font.BOLD, 12));
		title.setForeground(Colour.strongHighlight);
		overallPanel.add(title);

		// setting up the buttons for forward and back
		nextBtn.setText("Next");
		nextBtn.setBounds(700, 180, 100, 50);
		nextBtn.addActionListener(this);
		nextBtn.setVisible(true);
		nextBtn.setBackground(Colour.strike);
		nextBtn.setBorder(BorderFactory.createLineBorder(Colour.lightBg));
		nextBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		overallPanel.add(nextBtn);

		backBtn.setText("Back");
		backBtn.setBounds(600, 180, 100, 50);
		backBtn.addActionListener(this);
		backBtn.setVisible(true);
		backBtn.setBackground(Colour.strike);
		backBtn.setBorder(BorderFactory.createLineBorder(Colour.lightBg));
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		overallPanel.add(backBtn);

		resetButton.setText("Reset");
		resetButton.setBounds(720, 110, 80, 35);
		resetButton.addActionListener(this);
		resetButton.setVisible(true);
		resetButton.setBackground(Colour.strike);
		resetButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		resetButton.setBorder(BorderFactory.createLineBorder(Colour.lightBg));
		overallPanel.add(resetButton);

		// Textfield for searching
		keyword.setBounds(600, 80, 200, 30);
		keyword.addActionListener(this);
		keyword.setBackground(Colour.strike);
		keyword.setBorder(BorderFactory.createLineBorder(Colour.lightBg));
		overallPanel.add(keyword);

		searchLabel.setBounds(600, 50, 50, 30);
		searchLabel.setForeground(Colour.strongHighlight);
		searchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		overallPanel.add(searchLabel);

		// Button to begin search
		searchButton.setBounds(600, 110, 100, 35);
		searchButton.addActionListener(this);
		searchButton.setBackground(Colour.strike);
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchButton.setBorder(BorderFactory.createLineBorder(Colour.lightBg));

		// Current location within the array, shown at the top
		currentLocation.setText(currentPage + 1 + "/" + uniArrayCopy.size());
		currentLocation.setBounds(500, 15, 90, 50);
		currentLocation.setFont(new Font("Tahoma", Font.BOLD, 24));
		currentLocation.setForeground(Colour.strongHighlight);
		overallPanel.add(currentLocation);
		overallPanel.add(searchButton);

		// Link to the website
		website.setText("Website");
		website.setBounds(55, 360, 100, 30);
		website.setBackground(Colour.strike);
		website.setBorder(BorderFactory.createLineBorder(Colour.lightBg));
		website.addActionListener(this);
		website.setFont(new Font("Tahoma", Font.PLAIN, 12));
		overallPanel.add(website);

		nsseButton.setText("All Universities NSSE Table");
		nsseButton.setBounds(590, 350, 300, 50);
		nsseButton.setBackground(Colour.strike);
		nsseButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nsseButton.addActionListener(this);
		overallPanel.add(nsseButton);

		setVisible(true);
	}

	// Override Method
	@Override
	public void actionPerformed(ActionEvent event) {
		// COMBOBOX2 is the Sorting combobox, changes the uniarraycopy based on which
		// sorting the user chose
		if (event.getSource() == nsseButton) {
			NSSE.CreateNSSE();
		}

		if (event.getSource() == combobox2) {

			if (combobox2.getSelectedIndex() == 0) { // if its A-Z
				alphaSort();
				reversed = false;
			} else if (combobox2.getSelectedIndex() == 1) { // if its Z-A
				alphaSort();
				reverse();
				reversed = true;
				currentPage = 0;
			} else if (combobox2.getSelectedIndex() == 2) { // Low to High, Average
				insertionSort();
			} else if (combobox2.getSelectedIndex() == 3) { // high to low, average
				insertionSort();
				reverse();
				reversed = true;
			} else if (combobox2.getSelectedIndex() == 4) {// bookmarked universities
				uniArrayCopy.clear();
				if (User.bookmarked.size() <= 0) {
					uniArrayCopy = new ArrayList<>(uniClass.getUniversities());
					JOptionPane.showMessageDialog(overallPanel, "Bookmark some universities first!");

					resetButton.doClick();
				} else {
					for (int x = 0; x < User.bookmarked.size(); x++) {
						uniArrayCopy.add(x, User.bookmarked.get(x)); // adds all the users bookmarked universities
																		// into
						// the uniArrayCopy
					}
					int index = 0;
					while (index < uniArrayCopy.size() - 1) {
						if (uniArrayCopy.get(index).getName().equals(uniArrayCopy.get(index + 1).getName())) {
							uniArrayCopy.remove(index + 1);
						} else {
							index++;
						}
					}
				}
			}

			maxIndex = uniArrayCopy.size();

			currentPage = 0;
			overallPanel.remove(uniPanel);

			// creates a new unipanel(top right corner), and changes the picture
			uniPanel = createUniPanel(uniArrayCopy.get(currentPage));

			uniPanel.setBounds(020, 120, 900, 900);
			overallPanel.add(uniPanel);
			currentLocation.setText(currentPage + 1 + "/" + uniArrayCopy.size());

			overallPanel.repaint();
		}

		// COMBOBOX1 is the combobox where you can individually select a university to
		// look at
		if (event.getSource() == combobox1) {

			if (combobox1.getSelectedIndex() != 0) {
				reversed = false;
				uniArrayCopy = new ArrayList<>(uniClass.getUniversities());
				alphaSort();
				int comboboxIndex = combobox1.getSelectedIndex();
				overallPanel.remove(uniPanel);
				currentPage = comboboxIndex - 1;
				uniPanel = createUniPanel(uniArrayCopy.get(comboboxIndex - 1));
				uniPanel.setBounds(020, 120, 900, 900);
				scrollBar.setValue(comboboxIndex);

				currentLocation.setText(currentPage + 1 + "/" + uniArrayCopy.size());
				overallPanel.add(uniPanel);

				overallPanel.repaint();
			}
		}

		// "next" button, goes forward 1 in the array
		if (event.getSource() == nextBtn) {
			maxIndex = uniArrayCopy.size();
			if (uniArrayCopy.size() != 1) {
				totalMoves += 1;
				currentPage += 1;
				if (currentPage == maxIndex) {
					currentPage = 0;
				}
				if (currentPage == -1) {
					currentPage = maxIndex;
				}

				overallPanel.remove(uniPanel);

				uniPanel = createUniPanel(uniArrayCopy.get(currentPage));
				uniPanel.setBounds(020, 120, 900, 900);
				combobox1.setSelectedIndex(0);
				currentLocation.setText(currentPage + 1 + "/" + uniArrayCopy.size());

				overallPanel.add(uniPanel);
				currentLocation.repaint();
				combobox1.repaint();
			}

			overallPanel.repaint();
		}
		// "back" button, goes back 1 in the array
		if (event.getSource() == backBtn) {
			maxIndex = uniArrayCopy.size();
			if (uniArrayCopy.size() != 1) {
				currentPage -= 1;
				if (currentPage == -1) {
					currentPage = maxIndex - 1;
				}
				if (currentPage == maxIndex) {
					currentPage = 0;
				}
				overallPanel.remove(uniPanel);

				uniPanel = createUniPanel(uniArrayCopy.get(currentPage));

				uniPanel.setBounds(020, 120, 900, 900);

				overallPanel.add(uniPanel);
				combobox1.repaint();
				currentLocation.setText(currentPage + 1 + "/" + uniArrayCopy.size());
				currentLocation.repaint();
				overallPanel.repaint();
			}
		}

		// Button to reset the screen
		if (event.getSource() == resetButton) {
			maxIndex = 14;
			uniArrayCopy = new ArrayList<>(uniClass.getUniversities());
			keyword.setText("");
			currentPage = 0;
			overallPanel.remove(uniPanel);

			// creates a new unipanel(top right corner), and changes the picture
			uniPanel = createUniPanel(uniArrayCopy.get(currentPage));

			uniPanel.setBounds(020, 120, 900, 900);
			overallPanel.add(uniPanel);

			combobox2.setSelectedIndex(0);
			currentLocation.setText(currentPage + 1 + "/" + uniArrayCopy.size());

			currentLocation.repaint();
			overallPanel.repaint();

			nextBtn.setBackground(Colour.strike);
			backBtn.setBackground(Colour.strike);

			nextBtn.setEnabled(true);
			backBtn.setEnabled(true);

		}

		// Button to link the user to the university website
		if (event.getSource() == website) {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI(uniArrayCopy.get(currentPage).getLink()));
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
				}
			}
		}

		// button to start search
		if (event.getSource() == searchButton) {
			customList.clear();
			// tempnames is the names of the university without the "university of" and
			// lowercase, ex. University of Toronto --> toronto
			ArrayList<String> tempNames = new ArrayList<>();
			for (University uni : uniClass.getUniversities()) {
				tempNames.add(uni.getName());
			}
			for (int x = 0; x < tempNames.size(); x++) {
				tempNames.set(x, replaceFiller(tempNames.get(x)));
			}
			currentPage = 0;

			String text = keyword.getText();
			text = replaceFiller(text);

			if (text.equals("queen's")) {
				text = "queens";
			}
			// checks if what the user entered is the university name
			for (int i = 0; i < tempNames.size(); i++) {
				if (text.equalsIgnoreCase(tempNames.get(i))) {
					customList.add(uniClass.getUniversities().get(i));

				}
			}
			// shows a no matches label if there are no matches
			if (customList.size() == 0) {
				searchFailed.setText("No Matches");
				searchFailed.setFont(new Font("Tahoma", Font.BOLD, 12));
				searchFailed.setBounds(20, 300, 100, 35);
				searchFailed.setForeground(Colour.strongHighlight);
				searchFailed.setVisible(true);
				overallPanel.add(searchFailed);
				searchFailedTF = true;
				uniArrayCopy = new ArrayList<>(uniClass.getUniversities());
			} else if (text.equalsIgnoreCase("")) {

				maxIndex = 14;
				uniArrayCopy = new ArrayList<>(uniClass.getUniversities());
			}

			// updates the uriarraycopy with the matching universities
			else {

				if (searchFailedTF)
					overallPanel.remove(searchFailed);
				searchFailedTF = false;
				currentPage = 0;
				uniArrayCopy = new ArrayList<>(customList);

				maxIndex = customList.size();
				overallPanel.remove(uniPanel);
				uniPanel = createUniPanel(uniArrayCopy.get(currentPage));
				uniPanel.setBounds(020, 120, 900, 900);
				combobox2.setSelectedIndex(0);
				combobox2.repaint();
				nextBtn.setBackground(gray);
				backBtn.setBackground(gray);

				nextBtn.setEnabled(false);
				backBtn.setEnabled(false);
				overallPanel.add(uniPanel);

			}
			overallPanel.repaint();

		}

		// If the bookmark button is pressed (adds the university to another array, and
		// changes the text
		if (event.getSource() == bookmark) {
			if (!uniArrayCopy.get(currentPage).getisBookmarked()) {
				User.bookmarked.add(uniArrayCopy.get(currentPage));
				bookmark.setText("Bookmarked!");

			} else {
				User.bookmarked.remove(uniArrayCopy.get(currentPage));
				bookmark.setText("Bookmark University");
			}
		}
	}

	// Reverses the sorting on the array
	public void reverse() {
		ArrayList<University> temp = new ArrayList<>(14);

		for (int i = uniArrayCopy.size(); i > 0; i--) {
			temp.add(uniArrayCopy.get(i - 1));
		}
		uniArrayCopy = temp;
	}

	// Creates the text info and the pictures of each uni, and the comparisons
	public JPanel createUniPanel(University uni) {

		// Setup the main panel
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setSize(900, 900);

		// Setup the labels
		JLabel nameLabel = new JLabel();
		JLabel infoLabel = new JLabel("<html>" + uni.getDescription() + "<html>");
		JLabel nationalRankLabel = new JLabel("Rank: " + uni.getNationalRank());
		JLabel logo = new JLabel();
		JLabel comparison = new JLabel("Comparison");
		JLabel comparisonStatement = new JLabel();
		JLabel[] surveyCategories = new JLabel[4];
		String[] surveyCategoriesNames = new String[] { "Excellent", "Good", "Fair", "Poor" };
		JLabel nsseLabel = new JLabel();
		JLabel averagesLabel = new JLabel();
		JLabel cutoffLabel = new JLabel();
		JLabel averagesTitleLabel = new JLabel();
		JLabel userAverageLabel = new JLabel();

		logo.setIcon(new ImageIcon(uni.getImage()));
		logo.setBounds(0, 50, 300, 200);

		averagesTitleLabel.setText("<html>Secondary School Averages of Full-Time, First Year Students</html>");
		averagesTitleLabel.setBounds(580, 300, 300, 50);
		averagesTitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		averagesTitleLabel.setForeground(Colour.strongHighlight);
		panel.add(averagesTitleLabel);

		averagesLabel.setText("<html>Average" + "<br>" + uni.getAverage() + "%</html>");
		averagesLabel.setBounds(580, 325, 100, 100);
		averagesLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		averagesLabel.setForeground(Colour.strongHighlight);
		panel.add(averagesLabel);

		cutoffLabel.setText("<html>Cutoff" + "<br>" + uni.getCutoff() + "%</html>");
		cutoffLabel.setBounds(680, 325, 100, 100);
		cutoffLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cutoffLabel.setForeground(Colour.strongHighlight);
		panel.add(cutoffLabel);

		nsseLabel.setText("National Survey of Student Engagement");
		nsseLabel.setBounds(580, 120, 300, 30);
		nsseLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nsseLabel.setBackground(Colour.bg);
		nsseLabel.setForeground(Colour.strongHighlight);
		panel.add(nsseLabel);
		for (int i = 0; i < surveyCategories.length; i++) {
			surveyCategories[i] = new JLabel();
			switch (i) {
			case 0:
				surveyCategories[i]
						.setText("<html>" + surveyCategoriesNames[i] + "<br><br>" + uni.getExcellent() + "%</html>");
				break;
			case 1:
				surveyCategories[i]
						.setText("<html>" + surveyCategoriesNames[i] + "<br><br>" + uni.getGood() + "%</html>");
				break;
			case 2:
				surveyCategories[i]
						.setText("<html>" + surveyCategoriesNames[i] + "<br><br>" + uni.getFair() + "%</html>");
				break;
			case 3:
				surveyCategories[i]
						.setText("<html>" + surveyCategoriesNames[i] + "<br><br>" + uni.getPoor() + "%</html>");
				break;
			}
			surveyCategories[i].setBounds(580 + i * 80, 150, 200, 50);
			surveyCategories[i].setBackground(Colour.bg);
			surveyCategories[i].setForeground(Colour.strongHighlight);
			surveyCategories[i].setFont(new Font("Tahoma", Font.PLAIN, 12));

			panel.add(surveyCategories[i]);
		}

		nameLabel.setText(uni.getName());
		nameLabel.setBounds(0, 0, 400, 80);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		nameLabel.setForeground(Colour.strongHighlight);

		nationalRankLabel.setBounds(0, 30, 500, 80);
		nationalRankLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nationalRankLabel.setForeground(Colour.strongHighlight);

		infoLabel.setBounds(0, 135, 220, 500);
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoLabel.setForeground(Colour.strongHighlight);

		// Checks if the uni is in the bookmarked list
		if (User.bookmarked.contains(uni)) {

			bookmark.setText("Bookmarked!");
		} else {
			bookmark.setText("Bookmark University");
		}
		bookmark.setBounds(340, 20, 170, 50);
		bookmark.setFont(new Font("Tahoma", Font.BOLD, 12));
		bookmark.setBackground(Colour.strike);
		bookmark.setBorder(BorderFactory.createLineBorder(Colour.lightBg));
		bookmark.addActionListener(this);

		comparison.setBounds(360, 270, 150, 30);
		comparison.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comparison.setForeground(Colour.strongHighlight);

		if (!(UniMatchmakerInfoEdit.save)) {
			comparisonStatement
					.setText("<html>Go to preferences tab to see how your averages compare to the universities.<html>");
			comparisonStatement.setBounds(300, 300, 250, 100);
			comparisonStatement.setFont(new Font("Tahoma", Font.PLAIN, 14));
			comparisonStatement.setForeground(Colour.strongHighlight);

		} else if (UniMatchmakerInfoEdit.save) {
			double average = 0;
			for (int x = 0; x < 6; x++) {
				average += Integer.parseInt(UniMatchmakerInfoEdit.gradeTextField[x].getText());
			}
			average = average / 6;
			if (uni.getCutoff() > average) {
				comparisonStatement.setText("<html>As of now, you do not meet the cutoff for this university.<html>");
				comparisonStatement.setBounds(300, 300, 250, 100);
				comparisonStatement.setFont(new Font("Tahoma", Font.PLAIN, 14));
				comparisonStatement.setForeground(Colour.strongHighlight);

			} else if (uni.getCutoff() < average && average < uni.getAverage()) {
				comparisonStatement.setText(
						"<html>You meet the cutoff requirement, however, you do not meet the admission average (2016).<html>");
				comparisonStatement.setBounds(300, 300, 250, 100);
				comparisonStatement.setFont(new Font("Tahoma", Font.PLAIN, 14));
				comparisonStatement.setForeground(Colour.strongHighlight);

			} else {
				comparisonStatement.setText(
						"<html>Your current average surpasses the cutoff requirement and the admission average (2016).<html>");
				comparisonStatement.setBounds(300, 300, 250, 100);
				comparisonStatement.setFont(new Font("Tahoma", Font.PLAIN, 14));
				comparisonStatement.setForeground(Colour.strongHighlight);

			}

		}

		panel.add(comparisonStatement);

		panel.add(bookmark);
		panel.add(nameLabel);
		panel.add(logo);
		panel.add(nationalRankLabel);
		panel.add(infoLabel);
		panel.add(comparison);
		panel.add(comparisonStatement);
		picture.setIcon(new ImageIcon(uniArrayCopy.get(currentPage).getIconImage()));
		picture.setVisible(true);
		panel.setOpaque(false);
		panel.repaint();

		return panel;
	}

	// Used to sort the universities by increasing average
	public void insertionSort() {
		// Runs for every index of the array except the first
		for (int x = 1; x < uniArrayCopy.size(); x++) {
			/*
			 * In the for loop below it will... take the value in the index x (which is one
			 * greater in each iteration) and continue to "shift" it until the value is
			 * greater than the value of the contents in index y thus moving it into its
			 * sorted place in the array
			 */
			for (int y = x - 1; y >= 0; y--) {
				if (uniArrayCopy.get(x).getAverage() < uniArrayCopy.get(y).getAverage()) {
					swap(uniArrayCopy, x--, y);
				} else {
					break;
				}
			}
		}
	}

	// Part of insertion sort
	public void swap(ArrayList<University> universities, int x, int smallest) {
		University temp = universities.get(x);
		universities.set(x, universities.get(smallest));
		universities.set(smallest, temp);
	}

	// Sorts alphabetically
	public void alphaSort() {
		University temp;
		for (int i = 0; i < uniArrayCopy.size(); i++) {
			for (int j = i + 1; j < uniArrayCopy.size(); j++) {
				if (uniArrayCopy.get(i).getName().compareTo(uniArrayCopy.get(j).getName()) > 0) {
					temp = uniArrayCopy.get(i);
					uniArrayCopy.set(i, uniArrayCopy.get(j));
					uniArrayCopy.set(j, temp);
				}
			}
		}
	}

	// Gets the "name" of the university ex. university of toronto ---> toronto
	public String replaceFiller(String text) {
		text = text.toLowerCase();
		text = text.replace("university", "");
		text = text.replace("of", "");
		while (text.contains(" ")) {
			text = text.replace(" ", "");
		}
		return text;
	}

}