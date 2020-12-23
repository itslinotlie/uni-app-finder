package guiClasses;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * Jordan's Screen
 * The class NSSE is used to create and display the NSSE table
 * for the 14 universities in Ontario that offer engineering programs
 */
public class NSSE {

	// GUI
	public static JFrame frame;

	public static void CreateNSSE() {

		// Create a frame
		frame = new JFrame();
		frame.setSize(537, 585);
		frame.setLayout(null);
		frame.setVisible(true);

		// Display the table on the frame
		JLabel table = new JLabel(new ImageIcon("resources/dataTables/NSSE.png"));
		table.setBounds(0, 0, 517, 542);
		frame.add(table);

	}

}
