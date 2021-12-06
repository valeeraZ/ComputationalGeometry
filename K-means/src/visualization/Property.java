package visualization;

public class Property {
    double totalDistance;
    double centerX;
    double centerY;
    int population;

    protected Property(double d, double cX, double cY, int p) {
        this.totalDistance = d;
        this.centerX = cX;
        this.centerY = cY;
        this.population = p;
    }
}
