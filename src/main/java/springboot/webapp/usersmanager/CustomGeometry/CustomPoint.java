package springboot.webapp.usersmanager.CustomGeometry;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CustomPoint implements CustomGeometry {

    final private GeometryType type = GeometryType.POINT;

    private double x;
    private double y;


    public String toString(){
        return x +" "+ y;
    }
}
