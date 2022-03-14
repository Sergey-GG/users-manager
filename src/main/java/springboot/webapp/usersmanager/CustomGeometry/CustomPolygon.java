package springboot.webapp.usersmanager.CustomGeometry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomPolygon implements CustomGeometry {
    final private GeometryType TYPE = GeometryType.POLYGON;

    private List<List<CustomPoint>> points;

    public String toString() {
        String geometry = getPoints().toString().replace('[', '(');
        geometry = geometry.replace(']', ')');
        return getTYPE() + geometry;
    }

}
