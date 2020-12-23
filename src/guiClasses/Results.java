package guiClasses;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import main.MatchmakingAlgorithm;
import objects.UniversitiesInformation;
import tools.Colour;

/*
 * Jordan's screen
 * The class Results creates the results screen based on the
 * preferences and grades of the user entered in the my preferences tab.
 * This will display 2 universities the closely matches the user's
 * preferences.
 */
public class Results extends JPanel {

	// GUI
	public static JPanel resultsPanel;

	// Private variables
	private static JLabel[] nameLabel = new JLabel[2];
	private static JLabel[] admissionLabel = new JLabel[2];
	private static JLabel[] tuitionLabel = new JLabel[2];
	private static JLabel[] distanceLabel = new JLabel[2];
	private static JLabel[] residenceLabel = new JLabel[2];
	private static JLabel[] uniSizeLabel = new JLabel[2];
	private static JLabel[] classSizeLabel = new JLabel[2];
	private static JLabel[] pictureLabel = new JLabel[2];
	private static JPanel[] uniPanel = new JPanel[2];
	private static JLabel[] clickHere = new JLabel[2];

	// Public variable
	public static int y = 0;

	public static void CreateResults() {

		// Results screen has been created
		Dashboard.results = true;

		// Create boarder
		Border border = BorderFactory.createLineBorder(Colour.strongHighlight, 3);
		Border averageBorder = BorderFactory.createLineBorder(Colour.strike, 2);

		// Create results panel
		resultsPanel = new JPanel();
		Dashboard.displayPanel.add(resultsPanel);
		resultsPanel.setBackground(Colour.bg);
		resultsPanel.setLayout(null);
		resultsPanel.setVisible(true);

		// Create title label
		JLabel titleLabel = new JLabel("School Finder");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 28));
		titleLabel.setForeground(Colour.strongHighlight);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(200, 10, 400, 65);
		resultsPanel.add(titleLabel);

		// Create caption label
		JLabel captionLabel = new JLabel("Your Results");
		captionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		captionLabel.setForeground(Colour.strongHighlight);
		captionLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		captionLabel.setBounds(200, 65, 400, 25);
		resultsPanel.add(captionLabel);

		// Create description label
		JLabel descriptionLabel = new JLabel("Based on your answers, the following programs are best suited for you");
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		descriptionLabel.setForeground(Colour.strongHighlight);
		descriptionLabel.setBounds(150, 100, 500, 25);
		resultsPanel.add(descriptionLabel);

		// Create back button
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		backButton.setBackground(Colour.strike);
		backButton.setBounds(30, 30, 100, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dashboard.hidePanel();
				UniMatchmaker.accountPanel.setVisible(true);
			}
		});
		resultsPanel.add(backButton);

		// Create average label
		JLabel average = new JLabel();
		average.setText(String.format("Your Average: %.2f%%", MatchmakingAlgorithm.personAverage));
		average.setFont(new Font("Tahoma", Font.PLAIN, 18));
		average.setHorizontalAlignment(SwingConstants.CENTER);
		average.setBorder(averageBorder);
		average.setForeground(Colour.strike);
		average.setBounds(665, 90, 225, 35);
		resultsPanel.add(average);

		// Create NSSE Button
		JButton nsseButton = new JButton("NSSE Table");
		nsseButton.setBackground(Colour.strike);
		nsseButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nsseButton.setHorizontalAlignment(SwingConstants.CENTER);
		nsseButton.setBounds(700, 30, 150, 35);
		nsseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NSSE.CreateNSSE();
			}
		});
		resultsPanel.add(nsseButton);

		// Variables
		int[] num = new int[2];
		num[0] = MatchmakingAlgorithm.greatestIndex;
		num[1] = MatchmakingAlgorithm.secondGreatestIndex;

		// Variables
		String[] uni = new String[2];
		double[] cutoff = new double[2];
		double[] distance = new double[2];
		double[] tuition = new double[2];
		double[] residence = new double[2];
		int[] uniSize = new int[2];
		int[] classSize = new int[2];
		String[] link = new String[2];

		// Add university info label
		for (int x = 0; x < nameLabel.length; x++) {

			// Store the top 2 universities' information
			uni[x] = UniversitiesInformation.universities.get(num[x]).getName();
			cutoff[x] = UniversitiesInformation.universities.get(num[x]).getCutoff();
			distance[x] = UniversitiesInformation.distances.get(0)[num[x]].getDistance();
			tuition[x] = UniversitiesInformation.universities.get(num[x]).getTuition();
			residence[x] = UniversitiesInformation.universities.get(num[x]).getResidenceCost();
			uniSize[x] = UniversitiesInformation.universities.get(num[x]).getUniSize();
			classSize[x] = UniversitiesInformation.universities.get(num[x]).getClassSize();
			link[x] = UniversitiesInformation.universities.get(num[x]).getLink();

			// Create 2 panels, one for each university
			uniPanel[x] = new JPanel();
			uniPanel[x].setBounds(50 + 455 * x, 140, 385, 450);
			uniPanel[x].setBackground(Colour.bg);
			uniPanel[x].setLayout(null);
			uniPanel[x].setBorder(border);

			// Create information labels for universities
			nameLabel[x] = new JLabel("Institution #" + (x + 1) + " Name: " + uni[x]);
			nameLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			nameLabel[x].setForeground(Colour.strongHighlight);
			nameLabel[x].setBounds(10, 10, 300, 25);
			uniPanel[x].add(nameLabel[x]);

			distanceLabel[x] = new JLabel("Distance: " + distance[x] + " km");
			distanceLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			distanceLabel[x].setForeground(Colour.strongHighlight);
			distanceLabel[x].setBounds(10, 40, 300, 25);
			uniPanel[x].add(distanceLabel[x]);

			admissionLabel[x] = new JLabel("Cutoff Average: " + cutoff[x] + "%");
			admissionLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			admissionLabel[x].setForeground(Colour.strongHighlight);
			admissionLabel[x].setBounds(10, 70, 300, 25);
			uniPanel[x].add(admissionLabel[x]);

			tuitionLabel[x] = new JLabel("Tuition (Annually): $" + tuition[x]);
			tuitionLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			tuitionLabel[x].setForeground(Colour.strongHighlight);
			tuitionLabel[x].setBounds(10, 100, 300, 25);
			uniPanel[x].add(tuitionLabel[x]);

			residenceLabel[x] = new JLabel("Residence Cost: $" + residence[x]);
			residenceLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			residenceLabel[x].setForeground(Colour.strongHighlight);
			residenceLabel[x].setBounds(10, 130, 300, 25);
			uniPanel[x].add(residenceLabel[x]);

			uniSizeLabel[x] = new JLabel("University Population: " + uniSize[x] + " students");
			uniSizeLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			uniSizeLabel[x].setForeground(Colour.strongHighlight);
			uniSizeLabel[x].setBounds(10, 160, 300, 25);
			uniPanel[x].add(uniSizeLabel[x]);

			classSizeLabel[x] = new JLabel("Average Class Size: " + classSize[x] + " students");
			classSizeLabel[x].setFont(new Font("Tahoma", Font.PLAIN, 14));
			classSizeLabel[x].setForeground(Colour.strongHighlight);
			classSizeLabel[x].setBounds(10, 190, 300, 25);
			uniPanel[x].add(classSizeLabel[x]);

			clickHere[x] = new JLabel("Click to visit website:");
			clickHere[x].setFont(new Font("Tahoma", Font.PLAIN, 12));
			clickHere[x].setForeground(Colour.strongHighlight);
			clickHere[x].setBounds(43, 220, 200, 25);
			uniPanel[x].add(clickHere[x]);

			// Make picture labels pressable
			// Code used from
			// https://www.codejava.net/java-se/swing/how-to-create-hyperlink-with-jlabel-in-java-swing
			pictureLabel[x] = new JLabel(new ImageIcon("resources/uniPictures2/" + uni[x] + ".jpg"));
			pictureLabel[x].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			pictureLabel[x].setBounds(35, 240, 300, 200);
			pictureLabel[x].addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(link[y]));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.print("Link not found!");
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						System.out.print("Link not found!");
					}

					y++;
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
			uniPanel[x].add(pictureLabel[x]);

			resultsPanel.add(uniPanel[x]);

		}

	}

}
