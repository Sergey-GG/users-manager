package springboot.webapp.usersmanager.CustomGeometry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomPolygon implements CustomGeometry {
    final private GeometryType type = GeometryType.POLYGON;

    private List<List<CustomPoint>> points;

    public GeometryType getType() {
        return this.type;
    }

    public String toString() {
        return type + points.toString();
    }

    public double getArea() {
        return Math.abs(ofRingSigned(twoListsToArrayConverter(this.points)));
    }

    public static CustomPoint[] twoListsToArrayConverter(List<List<CustomPoint>> polygon) {
        List<CustomPoint> polygonCoordinates = polygon.get(0);
        CustomPoint[] points = new CustomPoint[polygon.get(0).size()];
        for (int i = 0; i < points.length; i++) {
            points[i] = polygonCoordinates.get(i);
        }
        return points;
    }

    public static double ofRingSigned(CustomPoint[] points) {
        if (points.length < 3) {
            return 0.0D;
        } else {
            double sum = 0.0D;
            double x0 = points[0].getX();

            for (int i = 1; i < points.length - 1; ++i) {
                double x = points[i].getX() - x0;
                double y1 = points[i + 1].getY();
                double y2 = points[i - 1].getY();
                sum += x * (y2 - y1);
            }

            return sum / 2.0D;
        }

    }
}
