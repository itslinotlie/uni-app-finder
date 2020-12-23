package main;

import guiClasses.Welcome;
import objects.UniversitiesInformation;

/*
 * Name:
 * Jordan Chu, Michael Li, Mithun Siva, Brittany Chan, Jeffrey Jiang
 * 
 * Date:
 * December 18th, 2020
 * 
 * Course Code:
 * ICS4U1 - 51
 * Mr. Fernandes
 * 
 * Title:
 * Post-Secondary App
 * 
 * Description:
 * In this project, we developed an app "School Finder". The purpose of
 * this app is for users such as myself or other grade 12 students who 
 * are trying to look for a university with an appropriate engineering program.
 * This application should match the user to a university based on their grades
 * and preferences of factors such as university ranking, distance, tuition cost,
 * etc.. Furthermore, the application will match the user to their ideal program,
 * by doing a personality test. This information will be saved into their account
 * for the next time the user logs in.
 * 
 * Features:
 * Searching, filtering and bookmarking universities
 * Personality test for ideal program
 * Sliders to indicated the weighting of certain factors for university decisions
 * Map API for distance calculations
 * Help screen with directions on how to use the app
 * User friendly interface
 * Savable account information
 * 
 * Major Skills:
 * Organization skills - since this was a larger group project keeping organized
 * may be quite difficult, we made sure to name our class and methods accurately
 * the first time to avoid confusion later, we commented as we coded so our
 * group members would understand what is happening in each section of code. We
 * use multiple packages to separate the types of classes.
 * 
 * GUI - through this project we further developed our GUI skills. The main skill
 * developed was being able to link everybody's GUIs together after they have been
 * built. We had to create the GUI a certain way they would all create on
 * cohesive application.
 * 
 * Communication and collaboration - we communicated thoroughly so make sure all
 * understood our own role, the current timeline and deadlines for pieces of code.
 * We help each other debug and fix logic errors as well as adapt our code to fit
 * each other's. We collaborated well by respecting the ideas of others which
 * created a healthy environment for all of us.
 * 
 * Creativity - we created a unique GUI that looks different from every other group's.
 * This GUI was formed through the creative minds of all our groupmates coming together
 * to choose a colour scheme and contributing a couple of screens each.
 * 
 * Areas of Concern:
 * Hardware: App can only fit on screens 1152 x 648, this app may take longer to load
 * based on how fast the machine that is running it is.
 * 
 * Folders: dataTables, descriptions, membersFiles, misc, types, uniLogos, uniPictures,
 * uniPictures2
 * 
 * Files: There are many files that are required to run this program, they should lay
 * within the folders mentioned above
 * 
 */
public class Launcher {

	public static void main(String[] args) {

		// Create and launch first screen
		Welcome.CreateWelcome();
		Welcome.GUI.setVisible(true);

		// Setup universities information
		UniversitiesInformation.setUniversities();
	}

}