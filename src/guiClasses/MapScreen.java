package guiClasses;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapScreen {
    public static void main(String[] args) {
        new MapScreen();
    }
    private JFrame frame = new JFrame();
    private JPanel mapPanel = new JPanel();
    private JPanel coordPanel = new JPanel();
    private JLabel map = new JLabel();
    private JLabel circle = new JLabel();
    private ImageIcon mapeh = new ImageIcon(new ImageIcon("./res/map.png").getImage().getScaledInstance(600, 360, 0));
    private Color bg = Color.decode("#072540");
    private Color highlight = Color.decode("#9C4668");
    private Color strongHighlight = Color.decode("#FF8AE2");
    private Border mapBorder = BorderFactory.createLineBorder(strongHighlight, 10);
    public MapScreen() { //920x610
        setupPanel();
        setupMap();
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
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
        mapPanel.add(map);
    }
    void setupPanel() {
        mapPanel.setLayout(null);
        mapPanel.setBackground(bg);
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

        JButton go = new JButton("GO");
        go.setFont(new Font("Tahoma", Font.PLAIN, 32));
        go.setBounds(800, 500, 50, 50);
        go.setForeground(highlight);
        go.setBackground(strongHighlight);
        go.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        go.setBorderPainted(false);
        go.setFocusPainted(false);
        go.set
        mapPanel.add(go);

        JTextArea text = new JTextArea();
        text.setFont(new Font("Tahoma", Font.PLAIN, 18));
        text.setBounds(400, 500, 100, 25);
        text.setForeground(highlight);
        text.setBackground(strongHighlight);
        text.setDocument(new TextFieldLimit());
        text.setText("A1B2C3"); //so people know its postal code
        mapPanel.add(text);

        circle.setIcon(new ImageIcon(new ImageIcon("./res/circle.png").getImage().getScaledInstance(50, 50, 0)));
        mapPanel.add(circle);

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
}
