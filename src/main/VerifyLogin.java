package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextField;

import guiClasses.CreateAccount;

public class VerifyLogin {

	static Scanner input;
	static Scanner input1;
	static Scanner input2;
	private static String newLine;
	private static String path = new File("").getAbsolutePath();
	private static File membersInfo = new File(path + "/resources/membersFiles/membersInformation");

	/*
	 * Checks if the username and password pair entered matches a pair in the
	 * members.txt file
	 */
	public static boolean verifyLogin(String username, String password) { // method retrieved from
																			// https://www.youtube.com/watch?v=XrktMbcoeis&list=WL&index=10&t=1s
		String tempUsername;
		String tempPassword;
		boolean found = false;

		try {
			input = new Scanner(new File(path + "/resources/membersFiles/members.txt"));
			input.useDelimiter(",");

			while (input.hasNext() && !found) {
				tempUsername = input.next();
				tempPassword = input.next();

				if (tempUsername.trim().equals(username.trim()) && tempPassword.trim().equals(password.trim())) {
					found = true;
				}
			}

		} catch (Exception e) {

		}
		return found;
	}

	/*
	 * Checks if the username they entered when creating account already exists. If
	 * so, they will not be allowed to create another account
	 */
	public static boolean existingUsername(String username) {
		int index = 0;
		try {
			input = new Scanner(new File(path + "/resources/membersFiles/members.txt"));
			input.useDelimiter(",");

			while (input.hasNext()) {
				if (index % 9 == 0) {
					String existingUser = input.next();
					if (existingUser.trim().equals(username.trim())) {
						return false;
					}
				}
				index++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Cannot locate members.txt file");
		}
		return true;
	}

	/*
	 * Checks if the password they entered matches the second password. This
	 * confirms they have entered the correct password.
	 */
	public static boolean verifyPassword(String password, String password1) {
		if (password.trim().equals(password1.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Checks if there are any spaces or commas. There cannot be any spaces/commas
	 * as we use .trim() which removes spaces, and the comma is used to separate the
	 * username and password.
	 */
	public static boolean unwantedCharacter(String username, String password) {
		char[] name = username.toCharArray();
		char[] pw = password.toCharArray();

		for (int x = 0; x < name.length; x++) { // Checks if there is comma or space in the username
			if (name[x] == ',' || name[x] == ' ') {
				return true;
			}
		}
		for (int x = 0; x < pw.length; x++) { // Checks if there is comma or space in the password
			if (pw[x] == ',' || pw[x] == ' ') {
				return true;
			}
		}
		return false;
	}

	/*
	 * When the user creates their account, this method saves it to the members.txt
	 * file.
	 */
	public static void saveLogin(String username, String password) throws IOException {
		File file = new File(path + "/resources/membersFiles/members.txt");
		FileWriter fw = new FileWriter(file, true);
		PrintWriter pw = new PrintWriter(fw);
		CreateAccount.username = username;
		CreateAccount.password = password;

		pw.println(username + "," + password + ",");
		pw.close();
	}

	/*
	 * Accepts the user entries made on the preferences tab and sasves it to the
	 * membersInformation file.
	 */
	public static void saveInformation(String username, String password, JTextField[] gradeTxtField,
			JTextField[] coursestxtField, int ranking, int tuition, int uniSize, int distance, int residence,
			int classSize, int slider, int slider1, int slider2, int slider3, int slider4, int slider5)
			throws Exception {
		FileWriter fw = new FileWriter(membersInfo, true);
		PrintWriter pw = new PrintWriter(fw);

		String[] grade = new String[6];
		String[] courses = new String[6];

		for (int x = 0; x < 6; x++) {
			grade[x] = gradeTxtField[x].getText();
			courses[x] = coursestxtField[x].getText();
		}
		if (!verifyInformation()) { // if the user has not completed the preferences tab
			newLine = username + "," + password + "," + grade[0] + "," + grade[1] + "," + grade[2] + "," + grade[3]
					+ "," + grade[4] + "," + grade[5] + "," + courses[0] + "," + courses[1] + "," + courses[2] + ","
					+ courses[3] + "," + courses[4] + "," + courses[5] + "," + ranking + "," + tuition + "," + uniSize
					+ "," + distance + "," + residence + "," + classSize + "," + slider + "," + slider1 + "," + slider2
					+ "," + slider3 + "," + slider4 + "," + slider5 + ",";
			pw.println(newLine);
			pw.close();
		} else { // if the user has completed the preferences tab
			removeLine();
			newLine = username + "," + password + "," + grade[0] + "," + grade[1] + "," + grade[2] + "," + grade[3]
					+ "," + grade[4] + "," + grade[5] + "," + courses[0] + "," + courses[1] + "," + courses[2] + ","
					+ courses[3] + "," + courses[4] + "," + courses[5] + "," + ranking + "," + tuition + "," + uniSize
					+ "," + distance + "," + residence + "," + classSize + "," + slider + "," + slider1 + "," + slider2
					+ "," + slider3 + "," + slider4 + "," + slider5 + ",";
			pw.println(newLine);
			pw.close();

		}
	}

	/*
	 * Checks if the user has already completed the preferences tab
	 */
	public static boolean verifyInformation() {
		boolean found = false;

		try {
			input2 = new Scanner(membersInfo);

			if (!CreateAccount.newAccount) {
				while (input2.hasNextLine() && !found) {
					String line = input2.nextLine();

					if (line.startsWith(CreateAccount.username + "," + CreateAccount.password)) {
						found = true;
					}
				}
				input2.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Cannot locate membersInformation file");
		}
		return found;
	}

	/*
	 * Loads the user's saved information into an ArrayList
	 */
	public static ArrayList<String> loadInformation() {
		String tempUsername;
		String tempPassword;
		boolean found = false;
		ArrayList<String> Information = new ArrayList<String>();

		try {
			input1 = new Scanner(membersInfo);
			input1.useDelimiter(",");

			while (input1.hasNextLine() && !found) {
				tempUsername = input1.next();
				tempPassword = input1.next();
				// Checking if their username exists in the text file
				if (tempUsername.trim().equals(CreateAccount.username.trim())
						&& tempPassword.trim().equals(CreateAccount.password.trim())) {
					found = true;
					for (int x = 0; x < 24; x++) {
						Information.add(x, input1.next());
					}
				} else {
					found = false;
				}

			}
			input1.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot load information from membersInformation");
		}
		return Information;

	}

	/*
	 * Removes the line if the user has already completed the preferences tab, in
	 * order for them to add their new changes.
	 */
	public static void removeLine() throws Exception {
		StringBuilder sb = new StringBuilder();
		try (Scanner input = new Scanner(membersInfo)) { // Retrieved from
															// https://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
			String currentLine;
			while (input.hasNext()) {
				currentLine = input.nextLine();
				if (currentLine.startsWith(CreateAccount.username + "," + CreateAccount.password)) { // if the line
																										// contains the
																										// username and
																										// password, it
																										// skips the
																										// line
					continue;
				}
				sb.append(currentLine).append("\n"); // otherwise it will add the line to the stringbuilder
			}
		}
		// Deletes all content from the file
		PrintWriter pw = new PrintWriter(membersInfo);
		pw.close();

		// Rewrites all lines, except for the one that was skipped
		BufferedWriter writer = new BufferedWriter(new FileWriter(membersInfo, true));
		writer.append(sb.toString());
		writer.close();

	}

}