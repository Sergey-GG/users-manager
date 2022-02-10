package springboot.webapp.usersmanager.CustomGeometry;

import org.springframework.expression.ParseException;

import java.util.ArrayList;
import java.util.List;

public class WKTParser {
    public CustomGeometry parse(String wkt) {
        StringBuilder type = new StringBuilder();
        StringBuilder stCoordinates = new StringBuilder();
        char[] chars = wkt.toCharArray();
        int i = 0;
        char delimiter = '(';
        while (chars[i] != delimiter) {
            type.append(chars[i]);
            i++;
        }
        while (i < chars.length) {
            stCoordinates.append(chars[i]);
            i++;
        }
        if (type.toString().equals(GeometryType.POLYGON.toString())) {
            return polygonParse(stCoordinates.toString());
        } else throw new ParseException(0, "Unknown geometry type");

    }

    private CustomPolygon polygonParse(String stCoordinates) {
        List<CustomPoint> coordinates = new ArrayList<>();
        char[] chars = stCoordinates.toCharArray();
        int i = 0;
        if (chars[i] == '(') {
            do {
                i++;
                i++;
                StringBuilder stX = new StringBuilder();
                StringBuilder stY = new StringBuilder();
                while (chars[i] != ' ') {
                    stX.append(chars[i]);
                    i++;
                }
                i++;
                while (chars[i] != ',') {
                    if (chars[i] == ')') {
                        break;
                    }
                    stY.append(chars[i]);
                    i++;
                }

                coordinates.add(new CustomPoint(Double.parseDouble(stX.toString()),
                        Double.parseDouble(stY.toString())));
                if (chars[i] == ')') {
                    break;
                }
            }
            while (i < chars.length);

        } else throw new ParseException(0, "Invalid values of coordinates");

        return new CustomPolygon(List.of(coordinates));
    }
}
