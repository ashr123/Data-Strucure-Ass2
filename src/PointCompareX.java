import java.util.Comparator;

public class PointCompareX implements Comparator<Point>
{
	@Override
	public int compare(Point point1, Point point2)
	{
		if (point1.getX()<point2.getX())
			return -1;
		if (point1.getX()==point2.getX())
			return 0;
		return 1;
	}
}