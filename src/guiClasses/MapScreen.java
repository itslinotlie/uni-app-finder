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

public class MapScreen implements ActionListener {
    public static void main(String[] args) {
        new MapScreen();
    }
    private JFrame frame = new JFrame();
    private JPanel mapPanel = new JPanel();
    private JPanel coordPanel = new JPanel();
    private JLabel map = new JLabel();
    private JLabel circle = new JLabel();
    private JLabel gif = new JLabel();
    private JTextArea text = new JTextArea();
    private JButton goMap = new JButton("GO");
    private JButton goCoord = new JButton("BACK");
    private ImageIcon mapeh = new ImageIcon(new ImageIcon("./res/map.png").getImage().getScaledInstance(600, 360, 0));
    private Color bg = Color.decode("#072540");
    private Color highlight = Color.decode("#9C4668");
    private Color strongHighlight = Color.decode("#FF8AE2");
    private Border mapBorder = BorderFactory.createLineBorder(strongHighlight, 10);
    private int x = -1, y = -1;
    private boolean reveal = false;
    public MapScreen() { //920x610
        setupCoord();
        setupMap();
        setupFrame();
        frame.repaint();
        System.out.println("map "+mapPanel.isVisible());
        System.out.println("coord "+coordPanel.isVisible());
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

        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

        JLabel header = new JLabel("Budget Google Map");
        header.setFont(new Font("Tahoma", Font.BOLD, 32));
        header.setBounds(80 , 20, 350, 75);
        header.setForeground(highlight);
        header.setBorder(border);
        mapPanel.add(header);

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

        goMap.setFont(new Font("Tahoma", Font.PLAIN, 32));
        goMap.setBounds(800, 500, 50, 50);
        goMap.setForeground(highlight);
        goMap.setBackground(strongHighlight);
        goMap.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        goMap.setBorderPainted(false);
        goMap.setFocusPainted(false);
        goMap.addActionListener(this);
        mapPanel.add(goMap);

        text.setFont(new Font("Tahoma", Font.PLAIN, 18));
        text.setBounds(400, 500, 100, 25);
        text.setForeground(highlight);
        text.setBackground(strongHighlight);
        text.setDocument(new TextFieldLimit());
        text.setText("A1B2C3"); //so people know its postal code
        mapPanel.add(text);

        circle.setIcon(new ImageIcon(new ImageIcon("./res/circle.png").getImage().getScaledInstance(50, 50, 0)));
        mapPanel.add(circle);
        map = new JLabel();
        map.setIcon(mapeh);
        map.setBounds(80, 100, 600, 360);
        map.setBorder(mapBorder);
        map.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("SDFJKLSD"+e.getX()+" "+e.getY());
                circle.setBounds(map.getX()+e.getX()-25, map.getY()+e.getY()-25, 50, 50);
                x = map.getX()+e.getX()-25;
                y = map.getY()+e.getY()-25;
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        mapPanel.add(map);

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

        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

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
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource()==goMap) {
            if(!text.getText().equals("A1B2C3") && text.getText().length()==6) {
                gif.setVisible(true);
                mapPanel.setVisible(reveal);
                coordPanel.setVisible(!reveal);
                System.out.println("POSTAL");
                reveal = !reveal;
                frame.repaint();
            }
            else if(x!=-1 && y!=-1) {
                gif.setVisible(true);
                mapPanel.setVisible(reveal);
                coordPanel.setVisible(!reveal);
                System.out.println("MAP");
                reveal = !reveal;
                System.out.println("map "+mapPanel.isVisible());
                System.out.println("coord "+coordPanel.isVisible());
                frame.repaint();
            }
            gif.setVisible(false);
        }
        else if(event.getSource()==goCoord) {
            mapPanel.setVisible(reveal);
            coordPanel.setVisible(!reveal);
            reveal = !reveal;
            System.out.println("map "+mapPanel.isVisible());
            System.out.println("coord "+coordPanel.isVisible());
            frame.repaint();
        }
        frame.repaint();
    }
}
