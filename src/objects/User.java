package objects;

import java.util.ArrayList;

public class User extends Login {
	public double average;
	public int tuition;
	public int preferredClassSize;
	public double latitude;
	public double longitude;

	public static ArrayList<University> bookmarked = new ArrayList<>();

	// Constructor method User
	public User(String name, String password, double average, int tuition, int preferredClassSize, double latitude,
			double longitude) {
		super(name, password);
		this.average = average;
		this.tuition = tuition;
		this.preferredClassSize = preferredClassSize;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	// getters and setters
	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public int getTuition() {
		return tuition;
	}

	public void setTuition(int tuition) {
		this.tuition = tuition;
	}

	public int getPreferredClassSize() {
		return preferredClassSize;
	}

	public void setPreferredClassSize(int preferredClassSize) {
		this.preferredClassSize = preferredClassSize;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}