package springboot.webapp.usersmanager.CustomGeometry;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CustomPoint implements CustomGeometry {

    final private GeometryType TYPE = GeometryType.POINT;

    private double x;
    private double y;


    public String toString(){
        return x +" "+ y;
    }
}
