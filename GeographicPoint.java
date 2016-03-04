
import java.awt.geom.Point2D.Double;

public class GeographicPoint extends Double implements Comparable {
	
	public GeographicPoint(double latitude, double longitude)
	{
		super(latitude, longitude);
	}
	
    public String toString()
    {
    	return "Lat: " + getX() + ", Lon: " + getY();
    }

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
