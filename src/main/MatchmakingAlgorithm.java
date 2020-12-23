package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import guiClasses.UniMatchmakerInfoEdit;
import objects.UniversitiesInformation;

/*
 * Jordan's matchmaking algorithm
 * The MatchmakingAlgorithm class matchmakes the user's university preferences
 * and grades to a university
 */
public class MatchmakingAlgorithm {

	// Public variables
	public static double[] score = new double[14];
	public static int greatestIndex;
	public static int secondGreatestIndex = 0;
	public static double personAverage;

	public static void Matchmaker() {

		// Variables
		int[] dropDownValue = new int[6];
		int[] slidersValue = new int[6];

		// Save values of the user's preferences
		for (int counter = 0; counter < dropDownValue.length; counter++) {
			dropDownValue[counter] = UniMatchmakerInfoEdit.dropDownLists[counter].getSelectedIndex();
			slidersValue[counter] = UniMatchmakerInfoEdit.sliders[counter].getValue();
		}

		// Variables
		double[] uniCutoff = new double[14];
		double[] distance = new double[14];
		int[] ranking = new int[14];
		int[] tuition = new int[14];
		int[] uniSize = new int[14];
		double[] residence = new double[14];
		int[] classSize = new int[14];

		personAverage = 0;

		// Gets the user's average
		for (int counter = 0; counter < 6; counter++)
			if (UniMatchmakerInfoEdit.gradeTextField[counter].getText().length() <= 3
					&& UniMatchmakerInfoEdit.gradeTextField[counter].getText().length() > 0)
				personAverage += Integer.parseInt(UniMatchmakerInfoEdit.gradeTextField[counter].getText());
		personAverage /= 6;

		// Loop for the amount of universities
		for (int counter = 0; counter < 14; counter++) {

			// Save information of each university
			uniCutoff[counter] = UniversitiesInformation.universities.get(counter).getCutoff();
			distance[counter] = UniversitiesInformation.distances.get(0)[counter].getDistance();
			ranking[counter] = UniversitiesInformation.universities.get(counter).getRanking();
			tuition[counter] = UniversitiesInformation.universities.get(counter).getTuition();
			uniSize[counter] = UniversitiesInformation.universities.get(counter).getUniSize();
			residence[counter] = UniversitiesInformation.universities.get(counter).getResidenceCost();
			classSize[counter] = UniversitiesInformation.universities.get(counter).getClassSize();

			score[counter] = 0;

			// Increase score based on user's average
			if (personAverage >= uniCutoff[counter])
				score[counter] += 20;
			if (personAverage >= UniversitiesInformation.universities.get(counter).getAverage())
				score[counter] += 5;

			// Start on first factor
			int factor = 0;

			// Increase score based on rank preference
			if (dropDownValue[factor] == 0 && ranking[counter] <= 5)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 1 && ranking[counter] <= 5)
				score[counter] += slidersValue[factor] * 2;
			else if (dropDownValue[factor] == 1 && ranking[counter] <= 10)
				score[counter] += slidersValue[factor];

			// Move to next factor
			factor++;

			// Increase score based on tuition cost preference
			if (dropDownValue[factor] == 0 && tuition[counter] < 10000)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 1 && tuition[counter] >= 10000 && tuition[counter] <= 15000)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 2 && tuition[counter] > 15000)
				score[counter] += slidersValue[factor];

			// Move to next factor
			factor++;

			// Increase score based on university size preference
			if (dropDownValue[factor] == 0 && uniSize[counter] < 10000)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 1 && uniSize[counter] >= 10000 && uniSize[counter] <= 50000)
				score[counter] += slidersValue[factor];
			else if (uniSize[counter] > 50000 && dropDownValue[factor] == 2)
				score[counter] += slidersValue[factor];

			// Move to next factor
			factor++;

			// Increase score based on distance preference
			if (dropDownValue[factor] == 0 && distance[counter] <= 30)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 1 && distance[counter] > 30 && distance[counter] < 150)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 2 && distance[counter] >= 150 && distance[counter] < 300)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 3 && distance[counter] >= 300)
				score[counter] += slidersValue[factor];

			// Move to next factor
			factor++;

			// Increase score based on residence cost preference
			if (dropDownValue[factor] == 0 && residence[counter] < 10000)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 1 && residence[counter] >= 10000 && residence[counter] <= 12000)
				score[counter] += slidersValue[factor];
			else if (dropDownValue[factor] == 2 && residence[counter] > 12000)
				score[counter] += slidersValue[factor];

			// Move to next factor
			factor++;

			// Increase score based on class size preference
			if (classSize[counter] < 100 && dropDownValue[factor] == 0)
				score[counter] += slidersValue[factor];
			else if (classSize[counter] >= 100 && classSize[counter] <= 300 && dropDownValue[factor] == 1)
				score[counter] += slidersValue[factor];
			else if (classSize[counter] > 300 && dropDownValue[factor] == 2)
				score[counter] += slidersValue[factor];

		}

		double greatest = score[0];

		for (int counter = 1; counter < 14; counter++) {
			if (greatest < score[counter]) {
				greatest = score[counter];
				greatestIndex = counter;
			}
		}

		double secondGreatest = 0;

		for (int counter = 0; counter < 14; counter++) {
			if (secondGreatest <= score[counter] && counter != greatestIndex) {
				secondGreatest = score[counter];
				secondGreatestIndex = counter;
			}
		}

	}

}
