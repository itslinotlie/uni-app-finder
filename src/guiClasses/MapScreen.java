package guiClasses;

import objects.TextFieldLimit;
import objects.UniversitiesInformation;
import objects.UniversityDistance;
import tools.Colour;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Models the Map Screen that allows users to determine their pin-pointed
 * distance to the 14 universities. Users have the option of clicking on a Map
 * to determine their coordinates or type in their postal code into the
 * JTextArea to determine their coordinates. Then the respective distances to
 * all the universities is calculated and displayed.
 */
public class MapScreen implements ActionListener {
	// this is for testing purposes, remember to delete afterwards
	public static void main(String[] args) {
		UniversitiesInformation universities = new UniversitiesInformation();
		new MapScreen(universities);
	}

	final private double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
	final private double LATITUDE_UP = 43.89254, LATITUDE_DOWN = 43.74501;
	final private double LONGITUDE_LEFT = -79.52205, LONGITUDE_RIGHT = -79.20952;
	final private int DISPLAY_MAP_SIZE = 50, DOT_SIZE = 5;
	private double lon = -1, lat = -1;
	private int x = -1, y = -1;
	private boolean reveal = false;
	public static boolean logic = false;
	// the color of the uni dots (rainbow-ish)
	private Color colors[] = { Color.decode("#CC3636"), Color.decode("#CC3366"), Color.decode("#CC33CC"),
			Color.decode("#8636CC"), Color.decode("#3636CC"), Color.decode("#3688CC"), Color.decode("#36CCBA"),
			Color.decode("#36CC87"), Color.decode("#36CC40"), Color.decode("#92CC36"), Color.decode("#CCC136"),
			Color.decode("#CC9236"), Color.decode("#CC7636"), Color.decode("#CC5D36"), };

	private JPanel mapPanel = new JPanel(); // size of panel 920x610
	private JPanel distancePanel = new JPanel();
	private JLabel map = new JLabel();
	private JLabel gif = new JLabel();
	private JLabel mapPreview = new JLabel();
	private JLabel googleMap = new JLabel();
	private JLabel mapPreviewCircle = new JLabel();
	private JLabel dot = new JLabel();
	private JLabel result[] = new JLabel[14];
	public static JTextArea text = new JTextArea();
	private JButton goToDistance = new JButton();
	private JButton goToMap = new JButton();
	private ImageIcon ontarioMap = new ImageIcon(
			new ImageIcon("./resources/misc/ontario-map.png").getImage().getScaledInstance(875 / 2, 611 / 2, 0));
	private ImageIcon mapIcon = new ImageIcon(
			new ImageIcon("./resources/misc/map.png").getImage().getScaledInstance(600, 360, 0));
	private ImageIcon blackDot = new ImageIcon(new ImageIcon("./resources/misc/black-dot.png").getImage()
			.getScaledInstance(105 / DOT_SIZE, 105 / DOT_SIZE, 0));

	private SwingWorker worker = null; // allows "multi-threading"

	private UniversitiesInformation universities;
	private UniversityDistance distance[] = new UniversityDistance[14];

	// constructor to initialize the panels
	public MapScreen(UniversitiesInformation universities) {
		this.universities = universities;
		setupMisc();
		setupMap();
		setupDistance();
	}

	// Setting up the misc labels, needs to be placed on labels first
	private void setupMisc() {
		try {
			Scanner in = new Scanner(new File(new File("").getAbsolutePath() + "/resources/misc/location.txt"));
			for (int i = 0; i < 14; i++) {
				double uniLat = universities.getUniversities().get(i).getLatitude();
				double uniLon = universities.getUniversities().get(i).getLongitude();
				distance[i] = new UniversityDistance(universities.getUniversities().get(i).getName(),
						calculateDistance(lat, lon, uniLat, uniLon));
				distance[i].setX(400 + googleMap.getX() + in.nextInt());
				distance[i].setY(150 + googleMap.getY() + in.nextInt());
				distance[i].getDot().setVisible(false);
				distancePanel.add(distance[i].getButton());
				distancePanel.add(distance[i].getDot());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Had issue loading mapCoords for each uni");
		}
	}

	// sets up the Map JPanel
	private void setupMap() {
		// map panel
		mapPanel.setLayout(null);
		mapPanel.setBackground(Colour.bg);
		mapPanel.setVisible(true);
//        mapPanel.setBounds(0, 0, 920, 610);

		JLabel header = new JLabel("Budget Google Map");
		header.setFont(new Font("Tahoma", Font.BOLD, 32));
		header.setBounds(80, 20, 350, 75);
		header.setForeground(Colour.highlight);
		mapPanel.add(header);

		// To have a \n in a JLabel, you can use html's \n (<br>) to achieve the same
		// effect
		JLabel headerText = new JLabel(
				"<html>Click on your approximate location<br>and distance to universities will be calculated*</html>");
		headerText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		headerText.setBounds(500, 20, 500, 75);
		headerText.setForeground(Colour.highlight);
		mapPanel.add(headerText);

		JLabel otherText = new JLabel(
				"<html>OR send your postal code and the <br>distance will also be calculated*</html>");
		otherText.setFont(new Font("Tahoma", Font.PLAIN, 18));
		otherText.setBounds(80, 480, 300, 125);
		otherText.setForeground(Colour.highlight);
		mapPanel.add(otherText);

		JLabel info = new JLabel("<html>*Postal code takes<br>priority over the map");
		info.setFont(new Font("Tahoma", Font.PLAIN, 12));
		info.setBounds(600, 500, 150, 75);
		info.setForeground(Colour.highlight);
		mapPanel.add(info);

		// button to direct user to distance JPanel
		goToDistance.setText("GO");
		goToDistance.setFont(new Font("Tahoma", Font.PLAIN, 32));
		goToDistance.setBounds(800, 500, 50, 50);
		goToDistance.setForeground(Colour.highlight);
		goToDistance.setBackground(Colour.strongHighlight);
		goToDistance.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		goToDistance.setBorderPainted(false);
		goToDistance.setFocusPainted(false);
		goToDistance.addActionListener(this);
		mapPanel.add(goToDistance);

		// text field
		text.setFont(new Font("Tahoma", Font.PLAIN, 18));
		text.setBounds(400, 500, 100, 25);
		text.setForeground(Colour.highlight);
		text.setBackground(Colour.strongHighlight);
		text.setDocument(new TextFieldLimit());
		text.setText("A1B2C3"); // so people know its postal code
		mapPanel.add(text);

		// cursor circle to indicate where the map has been clicked
		JLabel circle = new JLabel();
		circle.setIcon(
				new ImageIcon(new ImageIcon("./resources/misc/circle.png").getImage().getScaledInstance(50, 50, 0)));
		mapPanel.add(circle);

		// screenshot of Google Map
		map = new JLabel();
		map.setIcon(new ImageIcon(new ImageIcon("./resources/misc/map.png").getImage().getScaledInstance(600, 360, 0)));
		map.setBounds(80, 100, 600, 360);
		map.setBorder(BorderFactory.createLineBorder(Colour.strongHighlight, 5)); // border covers the JLabel ):);
		map.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				x = map.getX() + e.getX();
				y = map.getY() + e.getY();
				circle.setBounds(x - 25, y - 25, 50, 50);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		mapPanel.add(map);

		// loading gif
		gif.setIcon(
				new ImageIcon(new ImageIcon("./resources/misc/load.gif").getImage().getScaledInstance(100, 100, 0)));
		gif.setBounds(750, 225, 100, 100);
		gif.setVisible(false);
		mapPanel.add(gif);
	}

	// sets up the distances of the university JPanel
	public void setupDistance() {
		// distance panel
		distancePanel.setLayout(null);
		distancePanel.setBackground(Colour.bg);
		distancePanel.setVisible(false);
		distancePanel.setBounds(0, 0, 920, 610);

		JLabel header = new JLabel("University Proximity");
		header.setFont(new Font("Tahoma", Font.BOLD, 32));
		header.setBounds(80, 20, 350, 75);
		header.setForeground(Colour.highlight);
		distancePanel.add(header);

		JLabel info = new JLabel(
				"<html>You can verify the distance(s) manually on <br>https://www.nhc.noaa.gov/gccalc.shtml</html>");
		info.setFont(new Font("Tahoma", Font.PLAIN, 12));
		info.setBounds(75, 550, 300, 75);
		info.setForeground(Colour.highlight);
		distancePanel.add(info);

		JLabel extraInfo = new JLabel(
				"<html>Impressive as this may seem, errors up to +/-5km <br> may occur. Insufficient budget ):</html>");
		extraInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		extraInfo.setBounds(350, 550, 300, 75);
		extraInfo.setForeground(Colour.highlight);
		distancePanel.add(extraInfo);

		JLabel mapPreviewInfo = new JLabel("You last clicked here:");
		mapPreviewInfo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		mapPreviewInfo.setBounds(500, 50, 300, 50);
		mapPreviewInfo.setForeground(Colour.highlight);
		distancePanel.add(mapPreviewInfo);

		JLabel userInfo = new JLabel(
				"<html>Black dot is you<br>(Assuming your postal code is in-bounds or valid)</html>");
		userInfo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userInfo.setBounds(650, 550, 300, 75);
		userInfo.setForeground(Colour.highlight);
		distancePanel.add(userInfo);

		JButton all = new JButton("ALL");
		all.setBounds(10, 570, 50, 35);
		all.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < 14; i++) {
					final int j = i;
					switchVisibility(j);
				}
			}
		});
		all.setForeground(Color.WHITE);
		all.setBackground(Color.BLACK);
		all.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		all.setBorderPainted(false);
		all.setFocusPainted(false);
		distancePanel.add(all);

		// cursor circle to indicate where the map has been clicked on the mapPreview
		mapPreviewCircle.setIcon(
				new ImageIcon(new ImageIcon("./resources/misc/circle.png").getImage().getScaledInstance(50, 50, 0)));
		mapPreviewCircle.setBounds(700 + 25, 20 + 25, 50, 50);
		mapPreviewCircle.setVisible(false);
		distancePanel.add(mapPreviewCircle);

		// black dot to locate user in ontario map
		dot.setIcon(blackDot);
		distancePanel.add(dot);

		// map preview of where the user clicked
		mapPreview.setBounds(700, 20, DISPLAY_MAP_SIZE * 2, DISPLAY_MAP_SIZE * 2);
		distancePanel.add(mapPreview);

		// google map preview of unis
		googleMap.setBounds(400, 150, ontarioMap.getIconWidth(), ontarioMap.getIconHeight());
		googleMap.setIcon(ontarioMap);
		distancePanel.add(googleMap);

		// button to direct users to the Map JPanel
		goToMap.setText("BACK");
		goToMap.setFont(new Font("Tahoma", Font.PLAIN, 32));
		goToMap.setBounds(775, 500, 100, 50);
		goToMap.setForeground(Colour.highlight);
		goToMap.setBackground(Colour.strongHighlight);
		goToMap.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		goToMap.setBorderPainted(false);
		goToMap.setFocusPainted(false);
		goToMap.addActionListener(this);
		distancePanel.add(goToMap);

		// displaying the JLabels that will show the distance of the universities
		for (int i = 0; i < 14; i++) {
			result[i] = new JLabel();
			result[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
			result[i].setForeground(Colour.highlight);
			result[i].setBounds(75, 80 + 35 * (i % 14), 425, 25);
			distancePanel.add(result[i]);
		}
	}

	// swithces the visiblity on the university's dots
	private void switchVisibility(int i) {
		distance[i].getDot().setVisible(distance[i].getVisbility());
		distance[i].setVisibility(!distance[i].getVisbility());
	}

	// sorts the distance array to match the default from .txt files
	private void resetDistance() {
		for (int i = 0; i < 14; i++) {
			for (int j = i + 1; j < 14; j++) {
				if (distance[j].getName().equals(universities.getUniversities().get(i).getName())) {
					UniversityDistance swatch = distance[j];
					distance[j] = distance[i];
					distance[i] = swatch;
				}
			}
		}
	}

	// refreshes the labels that display the distance of universities
	// also updates the mapPreview of where the user clicked (not accurate for areas
	// clicked near the border)
	// ^^ too much math to properly implement that
	public void refresh(boolean click) {
		UniversityDistance something[] = new UniversityDistance[14];
		resetDistance();
		for (int i = 0; i < 14; i++) {
			double uniLat = universities.getUniversities().get(i).getLatitude();
			double uniLon = universities.getUniversities().get(i).getLongitude();
			something[i] = new UniversityDistance(universities.getUniversities().get(i).getName(),
					calculateDistance(lat, lon, uniLat, uniLon));
			distance[i].setDistance(calculateDistance(lat, lon, uniLat, uniLon));
		}
		UniversityDistance copy[] = new UniversityDistance[14]; // need to add a copy that isn't sorted to universities
		for (int i = 0; i < 14; i++) {
			copy[i] = something[i];
		}
		universities.getUniversityDistances().add(copy); // adding the copy, while I still have the sorted array
		Arrays.sort(distance); // since the UniversityDistance has implements Comparable, this works
		for (int i = 0; i < 14; i++) { // this places the dots on the map with the right color
			distance[i].setColor(colors[i]);
			distance[i].setID(String.format("%02d", i + 1));
			distance[i].getDot()
					.setIcon(new ImageIcon(new ImageIcon("./resources/misc/dots-" + distance[i].getID() + ".png")
							.getImage().getScaledInstance(105 / DOT_SIZE, 105 / DOT_SIZE, 5)));
			distance[i].getDot().setBounds(distance[i].getX(), distance[i].getY() - 150, 105 / DOT_SIZE,
					105 / DOT_SIZE);
		}
		for (int i = 0; i < 14; i++) { // displays the sorted arrays with their names and the proper buttons
			result[i].setText(distance[i].toString());
			// need to remove all actionListeners or else they'll pile up on each other
			for (ActionListener al : distance[i].getButton().getActionListeners()) {
				distance[i].getButton().removeActionListener(al);
			}
			distance[i].getButton().setBounds(result[i].getX() - 65, result[i].getY(), 50, 35);
			distance[i].getButton().setText("SEE");
			final int tmp = i;
			distance[i].getButton().addActionListener(e -> switchVisibility(tmp));
			distance[i].getButton().setForeground(Color.WHITE);
			distance[i].getButton().setBackground(distance[i].getColor());
			distance[i].getButton().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			distance[i].getButton().setBorderPainted(false);
			distance[i].getButton().setFocusPainted(false);
			distance[i].getButton().addActionListener(this);
		}
		universities.getUniversityDistances().add(distance);

		if (click) { // if user clicked on the map --> display preview
			// you need a bufferedImage to get a snippet of an image
			BufferedImage mapCopy = new BufferedImage(mapIcon.getIconWidth(), mapIcon.getIconHeight(),
					BufferedImage.TYPE_INT_RGB);

			// creates a new bufferedImage from the actual map with proper dimensions
			// if the user clicks on the edge, instead of having a DISPLAY_MAP_SIZE padding
			// on one side
			// it'll over-compensate on the other size (intended-ish)
			int newX = Math.min(Math.max(0, x - map.getX() - DISPLAY_MAP_SIZE),
					mapIcon.getIconWidth() - 2 * DISPLAY_MAP_SIZE);
			int newY = Math.min(Math.max(0, y - map.getY() - DISPLAY_MAP_SIZE),
					mapIcon.getIconHeight() - 2 * DISPLAY_MAP_SIZE);
			BufferedImage mapDisplay = mapCopy.getSubimage(newX, newY, 2 * DISPLAY_MAP_SIZE, 2 * DISPLAY_MAP_SIZE);

			// draws the updated changes onto the mapCopy
			Graphics g = mapCopy.createGraphics();
			mapIcon.paintIcon(null, g, 0, 0);
			g.dispose();
			// adding the updated BufferedImage as an icon to mapPreview
			mapPreview.setIcon(new ImageIcon(mapDisplay));
		}

		// if postal code lies within these bounds, place black dot
		double TL = 41.52450;
		double TR = 46.88475;
		double BL = -84.88621;
		double BR = -74.07128;
		if (TL <= lat && lat <= TR && BL <= lon && lon <= BR) {
			double dotLat = googleMap.getX() + googleMap.getWidth() * (lat - TL) / (TR - TL);
			double dotLon = googleMap.getY() + googleMap.getHeight() * (lon - BR) / (BL - BR);
			dot.setBounds((int) dotLat, (int) dotLon, 105 / DOT_SIZE, 105 / DOT_SIZE);
			dot.setVisible(true);
		} else {
			dot.setVisible(false);
		}
	}

	// changes the visibility of the two panels
	private void switchPanel() {
		mapPanel.setVisible(reveal);
		distancePanel.setVisible(!reveal);
		reveal = !reveal;
	}

	// warns users if they enter an invalid postal code
	private boolean valid(String text) {
		text = text.toUpperCase();
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0) { // letter
				if (!('A' <= text.charAt(i) && text.charAt(i) <= 'Z')) {
					return false;
				}
			} else { // number
				if (!('0' <= text.charAt(i) && text.charAt(i) <= '9')) {
					return false;
				}
			}
		}
		return true;
	}

	// logic for JButtons
	@Override
	public void actionPerformed(ActionEvent event) {
		// if the button was on the Map JPanel
		if (event.getSource() == goToDistance) {
			if ((!text.getText().equals("A1B2C3") && text.getText().length() == 6)) {
				if (valid(text.getText())) {
					gif.setVisible(true);
					logic = true;
					setupWorker();
					worker.execute();
				} else {
					JOptionPane.showMessageDialog(mapPanel, "Invalid Postal Code");
				}
			} else if ((x != -1 && y != -1)) {
				gif.setVisible(true);
				logic = true;
				setupWorker();
				worker.execute();
			}
		}
		// if the button was on the Distance JPanel
		else if (event.getSource() == goToMap) {
			switchPanel();
		}
	}

	// this allows "multi-threading" since you can't normally display a GIF when a
	// button is performed (can't do two tasks at once)
	private void setupWorker() {
		worker = new SwingWorker() {
			// This is the "2nd" task that is being done simultaneously
			private boolean click = false;

			@Override
			protected Void doInBackground() {
				if (!text.getText().equals("A1B2C3") && text.getText().length() == 6) {
					callGeocoder();
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else if (x != -1 && y != -1) {
					click = true;
					lat = LATITUDE_UP - (double) (y - map.getY()) / map.getHeight() * (LATITUDE_UP - LATITUDE_DOWN);
					lon = LONGITUDE_LEFT
							- (double) (x - map.getX()) / map.getWidth() * (LONGITUDE_LEFT - LONGITUDE_RIGHT);
				}
				return null;
			}

			// this is what occurs when the background task is completed
			@Override
			protected void done() {
				gif.setVisible(false);
				if (click) {
					mapPreviewCircle.setVisible(true);
				}
				switchPanel();
				refresh(click);
				super.done();
			}
		};
	}

	// calls a website to find the latitude and longitude points given a postal code
	private void callGeocoder() {
		URL url = null; // the url being called
		HttpURLConnection httpCon = null; // establishes a connection to the website
		BufferedReader br = null; // reads the information from the established connection
		String arr[] = {}; // used to parse the information from the website

		try {
			// this is how the website wants the input to be formated
			url = new URL("https://geocoder.ca/?locate=" + text.getText().toUpperCase() + "&json=1");
			// sending http response
			httpCon = (HttpURLConnection) url.openConnection();
			// reading http response
			br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
			// basic parsing by splitting the entries by commas
			arr = br.readLine().split("[,]");
		} catch (Exception e) {
			System.out.println("Could not connect to geocoder.ca");
		}

		// fine-tuning parsing
		for (int i = 0; i < arr.length; i++) {
			// parsing JSON into something I can read
			arr[i] = arr[i].replaceAll("[^a-z0-9:.-]", ""); // ReGeX op
			// tldr; the "[^a-z0-9:.-]" replaces everything that isn't a to z, 0 to 9, :, .,
			// - with "" aka nothing

			if (arr[i].startsWith("longt")) {
				lon = Double.parseDouble(arr[i].split(":")[1]);
			} else if (arr[i].startsWith("latt")) {
				lat = Double.parseDouble(arr[i].split(":")[1]);
			}
		}
	}

	// Haversine formula orz, it pretty much determines the distance given two
	// latitude/longtitude points using fancy math
	private double calculateDistance(double startLat, double startLng, double endLat, double endLng) {
		double scale = 100; // used for rounding purposes
		double RadstartLat = Math.toRadians(startLat);
		double RadendLat = Math.toRadians(endLat);
		double latDistance = Math.toRadians(endLat - startLat);
		double lngDistance = Math.toRadians(endLng - startLng);

		double a = haversin(latDistance) + haversin(lngDistance) * Math.cos(RadstartLat) * Math.cos(RadendLat);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c * scale) / scale;
	}

	// helper switchVisibilityction to simplify the math of the Haversince formula
	private double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	// returns the mapPanel
	public JPanel getMapPanel() {
		return mapPanel;
	}

	// returns distancePanel
	public JPanel getDistancePanel() {
		return distancePanel;
	}
}