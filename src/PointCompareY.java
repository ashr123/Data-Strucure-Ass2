import java.util.Comparator;

public class PointCompareY implements Comparator<Point>
{
	@Override
	public int compare(Point point1, Point point2)
	{
		if (point1.getY()<point2.getY())
			return -1;
		if (point1.getY()==point2.getY())
			return 0;
		return 1;
	}
}