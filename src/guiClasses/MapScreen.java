package guiClasses;

import main.UniversitiesInformation;
import objects.TextFieldLimit;
import objects.UniversityDistance;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Models the Map Screen that allows users to determine their pin-pointed distance to the 14 universities.
 * Users have the option of clicking on a Map to determine their coordinates
 * or type in their postal code into the JTextArea to determine their coordinates.
 * Then the respective distances to all the universities is calculated and displayed.
 */
public class MapScreen implements ActionListener {
    //this is for testing purposes, remember to delete afterwards
    public static void main(String[] args) {
        UniversitiesInformation universities = new UniversitiesInformation();
        new MapScreen(universities);
    }
    
    final private double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    final private double LATITUDE_UP = 43.89254, LATITUDE_DOWN = 43.74501;
    final private double LONGITUDE_LEFT = -79.52205, LONGITUDE_RIGHT = -79.20952;
    private double lon = -1, lat = -1;
    private int x = -1, y = -1;
    private boolean reveal = false;
    
    private JFrame frame = new JFrame();
    private JPanel mapPanel = new JPanel(); //size of panel 920x610
    private JPanel distancePanel = new JPanel();
    private JLabel map = new JLabel();
    private JLabel gif = new JLabel();
    private JLabel result[] = new JLabel[14];
    private JTextArea text = new JTextArea();
    private JButton goToDistance = new JButton();
    private JButton goToMap = new JButton();
    private Color bg = Color.decode("#072540");
    private Color highlight = Color.decode("#9C4668");
    private Color strongHighlight = Color.decode("#FF8AE2");
    private Border mapBorder = BorderFactory.createLineBorder(strongHighlight, 5); //border covers the JLabel ):
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

    private SwingWorker worker = null;

    private UniversitiesInformation universities;

    //constructor to initialize the panels
    public MapScreen(UniversitiesInformation universities) { 
        this.universities = universities;
        setupMap();
        setupDistance();
        setupFrame();
        frame.repaint();
    }
    
    //sets up the JFrame
    void setupFrame() {
        frame.setBounds(0, 0, 920, 610);
        frame.setVisible(true);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    //sets up the Map JPanel
    void setupMap() {
        //map panel
        mapPanel.setLayout(null);
        mapPanel.setBackground(bg);
        mapPanel.setVisible(true);
        mapPanel.setBounds(0, 0, 920, 610);
        frame.add(mapPanel);

        JLabel header = new JLabel("Budget Google Map");
        header.setFont(new Font("Tahoma", Font.BOLD, 32));
        header.setBounds(80 , 20, 350, 75);
        header.setForeground(highlight);
        header.setBorder(border);
        mapPanel.add(header);

        //To have a \n in a JLabel, you can use html's \n (<br>) to achieve the same effect
        JLabel headerText = new JLabel("<html>Click on your approximate location<br>and distance to universities will be calculated*</html>");
        headerText.setFont(new Font("Tahoma", Font.PLAIN, 18));
        headerText.setBounds(500, 20, 500, 75);
        headerText.setForeground(highlight);
        headerText.setBorder(border);
        mapPanel.add(headerText);

        JLabel otherText = new JLabel("<html>OR send your postal code and the <br>distance will also be calculated*</html>");
        otherText.setFont(new Font("Tahoma", Font.PLAIN, 18));
        otherText.setBounds(80, 480, 300, 75);
        otherText.setForeground(highlight);
        otherText.setBorder(border);
        mapPanel.add(otherText);

        JLabel info = new JLabel("<html>*Postal code takes<br>priority over the map");
        info.setFont(new Font("Tahoma", Font.PLAIN, 12));
        info.setBounds(600, 500, 150, 75);
        info.setForeground(highlight);
        info.setBorder(border);
        mapPanel.add(info);

        //button to direct user to distance JPanel
        goToDistance.setText("GO");
        goToDistance.setFont(new Font("Tahoma", Font.PLAIN, 32));
        goToDistance.setBounds(800, 500, 50, 50);
        goToDistance.setForeground(highlight);
        goToDistance.setBackground(strongHighlight);
        goToDistance.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        goToDistance.setBorderPainted(false);
        goToDistance.setFocusPainted(false);
        goToDistance.addActionListener(this);
        mapPanel.add(goToDistance);

        //text field
        text.setFont(new Font("Tahoma", Font.PLAIN, 18));
        text.setBounds(400, 500, 100, 25);
        text.setForeground(highlight);
        text.setBackground(strongHighlight);
        text.setDocument(new TextFieldLimit());
        text.setText("A1B2C3"); //so people know its postal code
        mapPanel.add(text);

        //cursor circle to indicate where the map has been clicked
        JLabel circle = new JLabel();
        circle.setIcon(new ImageIcon(new ImageIcon("./res/circle.png").getImage().getScaledInstance(50, 50, 0)));
        mapPanel.add(circle);

        //screenshot of Google Map
        map = new JLabel();
        map.setIcon(new ImageIcon(new ImageIcon("./res/map.png").getImage().getScaledInstance(600, 360, 0)));
        map.setBounds(80, 100, 600, 360);
        map.setBorder(mapBorder);
        map.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.printf("Mouse click = (%d, %d)\n", e.getX(), e.getY());
                x = map.getX()+e.getX();
                y = map.getY()+e.getY();
                circle.setBounds(x-25, y-25, 50, 50);
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        mapPanel.add(map);

        //loading gif
        gif.setIcon(new ImageIcon(new ImageIcon("./res/load.gif").getImage().getScaledInstance(100, 100, 0)));
        gif.setBounds(750, 225, 100, 100);
        gif.setVisible(false);
        mapPanel.add(gif);
    }
    
    //sets up the distances of the university JPanel
    public void setupDistance() {
        //distance panel
        distancePanel.setLayout(null);
        distancePanel.setBackground(bg);
        distancePanel.setVisible(false);
        distancePanel.setBounds(0, 0, 920, 610);
        frame.add(distancePanel);

        JLabel header = new JLabel("University Proximity");
        header.setFont(new Font("Tahoma", Font.BOLD, 32));
        header.setBounds(80 , 20, 350, 75);
        header.setForeground(highlight);
        header.setBorder(border);
        distancePanel.add(header);

        JLabel info = new JLabel("<html>You can verify the distance(s) manually on <br>https://www.nhc.noaa.gov/gccalc.shtml</html>");
        info.setFont(new Font("Tahoma", Font.PLAIN, 12));
        info.setBounds(25, 500, 300, 75);
        info.setForeground(highlight);
        info.setBorder(border);
        distancePanel.add(info);


        //button to direct users to the Map JPanel
        goToMap.setText("BACK");
        goToMap.setFont(new Font("Tahoma", Font.PLAIN, 32));
        goToMap.setBounds(775, 500, 100, 50);
        goToMap.setForeground(highlight);
        goToMap.setBackground(strongHighlight);
        goToMap.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        goToMap.setBorderPainted(false);
        goToMap.setFocusPainted(false);
        goToMap.addActionListener(this);
        distancePanel.add(goToMap);

        //displaying the JLabels that will show the distance of the universities
        for (int i=0;i<14;i++) {
            result[i] = new JLabel();
            result[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
            result[i].setForeground(highlight);
            result[i].setBounds(25+425*(i/7), 150+50*(i%7), 425, 50);
            result[i].setBorder(border);
            distancePanel.add(result[i]);
        }
    }

    //refreshes the lables that display the distance of universites
    public void refresh() {
        UniversityDistance distance[] = new UniversityDistance[14];
        for (int i=0;i<14;i++) {
            double uniLat = universities.getUniversities().get(i).getLatitude();
            double uniLon = universities.getUniversities().get(i).getLongitude();
            distance[i] = new UniversityDistance(universities.getUniversities().get(i).getName(),
                    calculateDistance(lat, lon, uniLat, uniLon));
        }

        Arrays.sort(distance); //since the UniversityDistance has implements Comparable, it works
        for (int i=0;i<14;i++) {
            result[i].setText(distance[i].toString());
        }
        universities.getUniversityDistances().add(distance);
    }

    //changes the visibility of the two panels
    private void switchPanel() {
        mapPanel.setVisible(reveal);
        distancePanel.setVisible(!reveal);
        reveal = !reveal;
    }

    //logic for JButtons
    @Override
    public void actionPerformed(ActionEvent event) {
        //if the button was on the Map JPanel
        if(event.getSource()==goToDistance) {
            if((!text.getText().equals("A1B2C3") && text.getText().length()==6) || (x!=-1 && y!=-1)) {
                gif.setVisible(true);
                setupWorker();
                worker.execute();
            }
        }
        //if the button was on the Distance JPanel
        else if(event.getSource()==goToMap) {
            switchPanel();
        }
    }
    
    //this allows "multi-threading" since you can't normally display a GIF when a button is performed (can't do two tasks at once)
    private void setupWorker() {
        worker = new SwingWorker() {
            //This is the "2nd" task that is being done simultaneously
            @Override
            protected Void doInBackground() {
                if(!text.getText().equals("A1B2C3") && text.getText().length()==6) {
                    callGeocoder();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (x!=-1 && y!=-1) {
                    lat = LATITUDE_UP-(double)(y-map.getY())/map.getHeight()*(LATITUDE_UP-LATITUDE_DOWN);
                    lon = LONGITUDE_LEFT-(double)(x-map.getX())/map.getWidth()*(LONGITUDE_LEFT-LONGITUDE_RIGHT);
                }
                System.out.printf("(lat, lon) = (%f, %f)\n", lat, lon);
                return null;
            }

            //this is what occurs when the background task is completed
            @Override
            protected void done() {
                gif.setVisible(false);
                switchPanel();
                refresh();
                super.done();
            }
        };
    }
    
    //calls a website to find the latitude and longitude points given a postal code
    private void callGeocoder() {
        URL url = null; //the url being called
        HttpURLConnection httpCon = null; //establishes a connection to the website
        BufferedReader br = null; //reads the information from the established connection
        String arr[] = {}; //used to parse the information from the website

        try {
            //this is how the website wants the input to be formated
            url = new URL("https://geocoder.ca/?locate="+text.getText()+"&json=1");
            //sending http response
            httpCon = (HttpURLConnection) url.openConnection();
            //reading http response
            br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            //basic parsing by splitting the entries by commas
            arr = br.readLine().split("[,]");
        } catch(Exception e) {
            System.out.println("Could not connect to geocoder.ca");
        }

        //fine-tuning parsing
        for (int i=0;i<arr.length;i++) {
            //parsing JSON into something I can read
            arr[i] = arr[i].replaceAll("[^a-z0-9:.-]", ""); //ReGeX op
            //tldr; the "[^a-z0-9:.-]" replaces everything that isn't a to z, 0 to 9, :, ., - with "" aka nothing

            if(arr[i].startsWith("longt")) {
                lon = Double.parseDouble(arr[i].split(":")[1]);
            }
            else if(arr[i].startsWith("latt")) {
                lat = Double.parseDouble(arr[i].split(":")[1]);
            }
        }
    }

    // Haversine formula orz, it pretty much determines the distance given two latitude/longtitude points using fancy math
    private double calculateDistance(double startLat, double startLng, double endLat, double endLng) {
        double scale = 100; //used for rounding purposes
        double RadstartLat = Math.toRadians(startLat);
        double RadendLat = Math.toRadians(endLat);
        double latDistance = Math.toRadians(endLat-startLat);
        double lngDistance = Math.toRadians(endLng-startLng);
        
        double a = haversin(latDistance) + haversin(lngDistance) * Math.cos(RadstartLat) * Math.cos(RadendLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c * scale) / scale;
    }
    
    //helper function to simplify the math of the Haversince formula
    private double haversin(double val) {
        return Math.pow(Math.sin(val/2), 2);
    }
}
