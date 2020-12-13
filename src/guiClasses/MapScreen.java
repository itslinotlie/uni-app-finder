package guiClasses;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MapScreen implements ActionListener {
    public static void main(String[] args) {
        new MapScreen();
    }
    final private double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    final private double LATITUDE_UP = 43.89254, LATITUDE_DOWN = 43.74501;
    final private double LONGITUDE_LEFT = -79.52205, LONGITUDE_RIGHT = -79.20952;
    private JFrame frame = new JFrame();
    private JPanel mapPanel = new JPanel();
    private JPanel coordPanel = new JPanel();
    private JLabel map = new JLabel();
    private JLabel gif = new JLabel();
    private JTextArea text = new JTextArea();
    private JButton goMap = new JButton("GO");
    private JButton goCoord = new JButton("BACK");
    private Color bg = Color.decode("#072540");
    private Color highlight = Color.decode("#9C4668");
    private Color strongHighlight = Color.decode("#FF8AE2");
    private Border mapBorder = BorderFactory.createLineBorder(strongHighlight, 5); //border covers the JLabel ):
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
    private int x = -1, y = -1;
    private double lon = -1, lat = -1;
    private boolean reveal = false;

    public MapScreen() { //920x610
        setupMap();
        setupCoord();
        setupFrame();
        frame.repaint();
    }
    void setupFrame() {
        frame.setBounds(0, 0, 920, 610);
        frame.setVisible(true);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    void setupMap() {
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
        JLabel headerText = new JLabel("<html>Click on your approximate location<br>and distance to universities will be calculated</html>");
        headerText.setFont(new Font("Tahoma", Font.PLAIN, 18));
        headerText.setBounds(500, 20, 500, 75);
        headerText.setForeground(highlight);
        headerText.setBorder(border);
        mapPanel.add(headerText);

        JLabel otherText = new JLabel("<html>OR send your postal code and the <br>distance will also be calculated</html>");
        otherText.setFont(new Font("Tahoma", Font.PLAIN, 18));
        otherText.setBounds(80, 480, 300, 75);
        otherText.setForeground(highlight);
        otherText.setBorder(border);
        mapPanel.add(otherText);

        //map button
        goMap.setFont(new Font("Tahoma", Font.PLAIN, 32));
        goMap.setBounds(800, 500, 50, 50);
        goMap.setForeground(highlight);
        goMap.setBackground(strongHighlight);
        goMap.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        goMap.setBorderPainted(false);
        goMap.setFocusPainted(false);
        goMap.addActionListener(this);
        mapPanel.add(goMap);

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
        gif.setBounds(800, 300, 100, 100);
        gif.setVisible(false);
        mapPanel.add(gif);
    }
    public void setupCoord() {
        coordPanel.setLayout(null);
        coordPanel.setBackground(bg);
        coordPanel.setVisible(false);
        coordPanel.setBounds(0, 0, 920, 610);
        frame.add(coordPanel);

        JLabel header = new JLabel("University Proximity");
        header.setFont(new Font("Tahoma", Font.BOLD, 32));
        header.setBounds(80 , 20, 350, 75);
        header.setForeground(highlight);
        header.setBorder(border);
        coordPanel.add(header);

        goCoord.setFont(new Font("Tahoma", Font.PLAIN, 32));
        goCoord.setBounds(775, 500, 100, 50);
        goCoord.setForeground(highlight);
        goCoord.setBackground(strongHighlight);
        goCoord.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        goCoord.setBorderPainted(false);
        goCoord.setFocusPainted(false);
        goCoord.addActionListener(this);
        coordPanel.add(goCoord);

        JLabel result[] = new JLabel[12];
        for (int i=0;i<12;i++) {
            result[i] = new JLabel("University #"+(i+1)+": Waterloo | 500km");
            result[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
            result[i].setForeground(highlight);
            result[i].setBounds(50+400*(i/6), 150+50*(i%6), 400, 50);
            result[i].setBorder(border);
            coordPanel.add(result[i]);
        }
    }

    //this overrides the default Document to set a limit on how many characters can be in a JTextArea
    static class TextFieldLimit extends PlainDocument {
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if((getLength()+str.length())<=6) {
                super.insertString(offset, str, attr);
            }
        }
    }

    private SwingWorker worker = null;
    private void setupWorker() {
        worker = new SwingWorker() {
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
            @Override
            protected void done() {
                gif.setVisible(false);
                switchPanel();
                System.out.printf("Your location to Carleton: %.2fkm\n", calculateDistance(lat, lon, 45.3876, -75.6960));
                super.done();
            }
        };
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource()==goMap) {
            if((!text.getText().equals("A1B2C3") && text.getText().length()==6) || (x!=-1 && y!=-1)) {
                gif.setVisible(true);
                setupWorker();
                worker.execute();
            }
        }
        else if(event.getSource()==goCoord) {
            switchPanel();
        }
    }
    private void switchPanel() {
        mapPanel.setVisible(reveal);
        coordPanel.setVisible(!reveal);
        reveal = !reveal;
    }
    private void callGeocoder() {
        URL url = null;
        HttpURLConnection httpCon = null;
        BufferedReader br = null;
        String arr[] = {};
        try {
            url = new URL("https://geocoder.ca/?locate="+text.getText()+"&json=1");
            //sending http response
            httpCon = (HttpURLConnection) url.openConnection();
            //reading http response
            br = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            arr = br.readLine().split("[,]");
        } catch(Exception e) {
            System.out.println("Could not connect to geocoder.ca");
        }

        for (int i=0;i<arr.length;i++) {
            //parsing JSON into something I can read
            arr[i] = arr[i].replaceAll("[^a-z0-9:.-]", ""); //ReGeX op
            //tldr; the "[^a-z0-9:.-]" replaces everything that isn't a to z, 0 to 9, :, ., - with "" aka nothing

            if(arr[i].startsWith("longt")) {
                System.out.println(arr[i]);
                lon = Double.parseDouble(arr[i].split(":")[1]);
            }
            if(arr[i].startsWith("latt")) {
                System.out.println(arr[i]);
                lat = Double.parseDouble(arr[i].split(":")[1]);
            }
        }
    }
    // Haversine formula orz
    private double calculateDistance(double startLat, double startLng, double endLat, double endLng) {
        double scale = 100;
        double latDistance = Math.toRadians(endLat-startLat);
        double lngDistance = Math.toRadians(endLng-startLng);
        double a = haversin(latDistance) + haversin(lngDistance) * Math.cos(startLat) * Math.cos(endLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c * scale) / scale;
    }
    private double haversin(double val) {
        return Math.pow(Math.sin(val/2), 2);
    }
}
