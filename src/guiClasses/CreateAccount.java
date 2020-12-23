package guiClasses;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import tools.Colour;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.VerifyLogin;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class CreateAccount extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JButton createAccountBtn;
	static JFrame frame = new JFrame();
	private JButton returnBtn;
	public static String username;
	public static String password;
	public static boolean newAccount = false;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public CreateAccount() {
		frame.setVisible(true);
		frame.setBounds(100, 100, 257, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		frame.getContentPane().add(contentPane);
		contentPane.setBackground(Colour.bg);

		JLabel createAccount = new JLabel("Create a New Account");
		createAccount.setFont(new Font("Tahoma", Font.BOLD, 20));
		createAccount.setForeground(Colour.strongHighlight);
		createAccount.setBounds(10, 19, 244, 37);
		contentPane.add(createAccount);

		JLabel userName = new JLabel("User Name");
		userName.setFont(new Font("Tahoma", Font.BOLD, 16));
		userName.setForeground(Colour.strongHighlight);
		userName.setBounds(10, 67, 106, 14);
		contentPane.add(userName);

		userNameField = new JTextField();
		userNameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userNameField.setBounds(10, 86, 96, 20);
		contentPane.add(userNameField);
		userNameField.setColumns(10);

		JLabel passwordLbl = new JLabel("Password");
		passwordLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		passwordLbl.setForeground(Colour.strongHighlight);
		passwordLbl.setBounds(10, 117, 106, 14);
		contentPane.add(passwordLbl);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(10, 142, 96, 20);
		contentPane.add(passwordField);

		JLabel retypePasswordLbl = new JLabel("Retype Password");
		retypePasswordLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		retypePasswordLbl.setForeground(Colour.strongHighlight);
		retypePasswordLbl.setBounds(10, 173, 150, 20);
		contentPane.add(retypePasswordLbl);

		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		confirmPasswordField.setBounds(10, 200, 96, 20);
		contentPane.add(confirmPasswordField);

		createAccountBtn = new JButton("Create Account");
		createAccountBtn.setBounds(10, 247, 130, 23);
		createAccountBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		createAccountBtn.setBackground(Colour.strike);
		createAccountBtn.addActionListener(this);
		contentPane.add(createAccountBtn);

		returnBtn = new JButton("Return");
		returnBtn.setBounds(135, 333, 100, 23);
		returnBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		returnBtn.setBackground(Colour.strike);
		returnBtn.addActionListener(this);
		contentPane.add(returnBtn);

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == createAccountBtn) {
			username = userNameField.getText();
			password = String.valueOf(passwordField.getPassword());

			if (!VerifyLogin.existingUsername(username)) { // Checks if the username exists in the file
				JOptionPane.showMessageDialog(contentPane, "Username already exists.");
				userNameField.setText("");
				passwordField.setText("");
				confirmPasswordField.setText("");
			} else if (!VerifyLogin.verifyPassword(password, String.valueOf(confirmPasswordField.getPassword()))) { // Checks
																													// if
																													// the
																													// second
																													// password
																													// matches
																													// the
																													// first
				JOptionPane.showMessageDialog(contentPane, "Passwords do not match!");
				userNameField.setText("");
				passwordField.setText("");
				confirmPasswordField.setText("");
			} else if (VerifyLogin.unwantedCharacter(username, password)) { // Checks if there is an unwanted character.
				JOptionPane.showMessageDialog(contentPane, "Commas & Spaces are prohibited!");
				userNameField.setText("");
				passwordField.setText("");
				confirmPasswordField.setText("");
			} else if (userNameField.getText().equals("") || password.equals("")) {
				JOptionPane.showMessageDialog(contentPane, "Everything must be filled in!");
				userNameField.setText("");
				passwordField.setText("");
				confirmPasswordField.setText("");
			} else {
				try {
					VerifyLogin.saveLogin(username, password);
					newAccount = true;
					Welcome.GUI.setVisible(true);
					Welcome.welcomePanel.setVisible(false);
					Dashboard.CreateDashboard();
					Dashboard.dashboardPanel.setVisible(true);
				} catch (IOException e) {
					System.out.println("error");
				}
			}
		}

		if (event.getSource() == returnBtn) {
			frame.dispose();
			Welcome.CreateWelcome();
			Welcome.GUI.setVisible(true);
		}

	}
}
