package springboot.webapp.usersmanager.CustomGeometry;

import org.springframework.expression.ParseException;

public interface CustomGeometry {

    GeometryType getType();

    default double getArea() {
        throw new ParseException(0, "Impossible to calculate the area of selected geometry type.");
    }
}
