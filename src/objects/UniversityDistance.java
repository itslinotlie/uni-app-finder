package objects;

public class UniversityDistance implements Comparable<UniversityDistance> {
    private String name;
    private double distance;
    public UniversityDistance(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }
    public int compareTo(UniversityDistance uni) {
        return Double.compare(distance, uni.distance);
    }

    @Override
    public String toString() {
        return name+" | "+distance+"km";
    }
}
