package objects;

/**
 * Relates universities with their appropriate distance (calculated from MapScreen)
 * and the comparable method allows the objects to be sorted
 */
public class UniversityDistance implements Comparable<UniversityDistance> {
    private String name;
    private double distance;

    //constructor that assigns values to the UniversityDistance object
    public UniversityDistance(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }

    //modifies the built-in compareTo method to compare distances in increasing order (small --> big)
    @Override
    public int compareTo(UniversityDistance uni) {
        return Double.compare(distance, uni.distance);
    }

    //modifies the built-in toString method to easily visualize the fields of the UniversityDistance object
    @Override
    public String toString() {
        return name+" | "+distance+"km";
    }
}
