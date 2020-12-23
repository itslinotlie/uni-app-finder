package objects;

import objects.University;
import objects.UniversityDistance;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UniversitiesInformation {

	public static ArrayList<University> universities = new ArrayList<>(14);
	public static ArrayList<UniversityDistance[]> distances = new ArrayList<>();

	// Reads information from Universities.txt, and creates University objects for
	// each University.
	// Stores each instance in an ArrayList.
	public static void setUniversities() {
		String[] names = new String[14];
		String path = new File("").getAbsolutePath();
		double[] overallAverages = new double[14];
		double[] cutoff = new double[14];
		int[] tuition = new int[14];
		int[] classSize = new int[14];
		double[] longitude = new double[14];
		double[] latitude = new double[14];
		int[] nationalRank = new int[14];
		String keywords = "";

		int index = 0;
		int nameIndex = 0;
		int overallAvgIndex = 0;
		int cutoffIndex = 0;
		int tuitionIndex = 0;
		int classSizeIndex = 0;
		int longitudeIndex = 0;
		int latitudeIndex = 0;
		int nationalRankIndex = 0;
		int descriptionIndex = 0;

		Scanner input;
		try {
			input = new Scanner(new File(path + "/resources/dataTables/All Data.csv"));
			input.useDelimiter(",");
			universities.clear();

			index = 0;

			while (input.hasNext()) {
				universities.add(index, new University(input.next().replaceAll("\n", "").replaceAll("\r", ""),
						input.nextDouble(), input.nextDouble(), input.nextInt(), input.nextInt(), input.nextDouble(),
						input.nextDouble(), input.nextInt(), input.nextInt(), input.nextDouble(), input.nextInt(),
						input.next(), input.nextDouble(), input.nextDouble(), input.nextDouble(), input.nextDouble()));
				index++;

			}

			/*
			 * if (index % 9 == 0) { String value = input.next(); names[nameIndex] = value;
			 * nameIndex++; } if (index % 9 == 1) { double value = input.nextDouble();
			 * overallAverages[overallAvgIndex] = value; overallAvgIndex++; } if (index % 9
			 * == 2) { double value = input.nextDouble(); cutoff[cutoffIndex] = value;
			 * cutoffIndex++; } if (index % 9 == 3) { int value = input.nextInt();
			 * tuition[tuitionIndex] = value; tuitionIndex++; } if (index % 9 == 4) { int
			 * value = input.nextInt(); classSize[classSizeIndex] = value; classSizeIndex++;
			 * } if (index % 9 == 5) { double value = input.nextDouble();
			 * longitude[longitudeIndex] = value; longitudeIndex++; } if (index % 9 == 6) {
			 * double value = input.nextDouble(); latitude[latitudeIndex] = value;
			 * latitudeIndex++; } if (index % 9 == 7) { int value = input.nextInt();
			 * nationalRank[nationalRankIndex] = value; nationalRankIndex++; } index++; }
			 * universities.clear(); for (int i = 0; i < names.length; i++) {
			 * universities.add(new University(names[i], overallAverages[i], cutoff[i],
			 * tuition[i], classSize[i], longitude[i], latitude[i], nationalRank[i])); }
			 */

			input.close();

			for (University uni : universities) {

				try {

					String value = "";
					input = new Scanner(
							new File(path + "/resources/descriptions/" + uni.getName() + " Description.txt"));
					while (input.hasNext()) {

						value = value + " " + input.next();
					}

					uni.setDescription(value);
					input.close();

				} catch (FileNotFoundException e) {
					System.out.println("File not Found :( (description)");
				}

				uni.setIcon(new ImageIcon(path + "/resources/uniPictures/" + uni.getName() + ".jpg"));
				uni.setLogo(new ImageIcon(path + "/resources/uniLogos/" + uni.getName() + ".png"));

				uni.setIconImage(uni.getIcon().getImage().getScaledInstance(uni.getIcon().getIconWidth() / 2,
						uni.getIcon().getIconHeight() / 2, 0));
				uni.setImage(uni.getLogo().getImage().getScaledInstance(uni.getLogo().getIconWidth() / 3,
						uni.getLogo().getIconHeight() / 3, 0));

			}

			for (University university : universities) {
				try {
					input = new Scanner(new File(path + "/resources/keyWords/" + university.getName() + ".txt"));

					String keyWords = "";

					while (input.hasNext()) {
						keywords = input.next();

					}
					university.setKeywords(keywords);
					input.close();

				} catch (FileNotFoundException e) {
					// System.out.println((int) university.getName().charAt(0));
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("File not Found :( (all other fields)");
		}
	}

	public ArrayList<University> getUniversities() {
		return universities;
	}

	public ArrayList<UniversityDistance[]> getUniversityDistances() {
		return distances;
	}

}