package objects;

import javax.swing.*;
import java.awt.*;

public class University {

	private String name;
	private double average;
	private double cutoff;
	private int tuition;
	private int classSize;
	private double latitude;
	private double longitude;
	private int nationalRank;
	private int uniSize;
	private double residenceCost;
	private int ranking;
	private String description;
	private ImageIcon icon;
	private Image iconImage;
	private ImageIcon logo;

	public Image getIconImage() {
		return iconImage;
	}

	public void setIconImage(Image iconImage) {
		this.iconImage = iconImage;
	}

	private String keywords;
	private String link;
	private Image image;
	private boolean isBookmarked;
	private double excellent;
	private double fair;
	private double good;
	private double poor;

	// constructor method University
	public University(String name, double average, double cutoff, int tuition, int classSize, double latitude,
			double longitude, int nationalRank, int uniSize, double residenceCost, int ranking, String link,
			double excellent, double good, double fair, double poor) {
		this.name = name;
		this.average = average;
		this.cutoff = cutoff;
		this.tuition = tuition;
		this.classSize = classSize;
		this.latitude = latitude;
		this.longitude = longitude;
		this.nationalRank = nationalRank;
		this.uniSize = uniSize;
		this.residenceCost = residenceCost;
		this.ranking = ranking;
		this.link = link;
		this.excellent = excellent;
		this.good = good;
		this.fair = fair;
		this.poor = poor;
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getCutoff() {
		return cutoff;
	}

	public void setCutoff(double cutoff) {
		this.cutoff = cutoff;
	}

	public int getTuition() {
		return tuition;
	}

	public void setTuition(int tuition) {
		this.tuition = tuition;
	}

	public int getClassSize() {
		return classSize;
	}

	public void setClassSize(int classSize) {
		this.classSize = classSize;
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

	public int getNationalRank() {
		return nationalRank;
	}

	public void setNationalRank(int nationalRank) {
		this.nationalRank = nationalRank;
	}

	public int getUniSize() {
		return uniSize;
	}

	public void setUniSize(int uniSize) {
		this.uniSize = uniSize;
	}

	public double getResidenceCost() {
		return residenceCost;
	}

	public void setResidenceCost(double residenceCost) {
		this.residenceCost = residenceCost;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public ImageIcon getLogo() {
		return (logo);
	}

	public void setLogo(ImageIcon logo) {
		this.logo = logo;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean getisBookmarked() {
		return isBookmarked;
	}

	public void setBookmarked(boolean bookmarked) {
		isBookmarked = bookmarked;

	}

	public double getExcellent() {
		return excellent;
	}

	public void setExcellent(double excellent) {
		this.excellent = excellent;
	}

	public double getFair() {
		return fair;
	}

	public void setFair(double fair) {
		this.fair = fair;
	}

	public double getGood() {
		return good;
	}

	public void setGood(double good) {
		this.good = good;
	}

	public double getPoor() {
		return poor;
	}

	public void setPoor(double poor) {
		this.poor = poor;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
