import java.util.Arrays;
import java.util.Comparator;
import java.util.ListIterator;

public class DataStructure implements DT
{
	private ContainerLinkedList byXList, byYList;
	
	/**
	 * Constructs a Data structure with two lists of points: one is arranged by their x coordinate and another is arranged by their y coordinate.
	 * @see ContainerLinkedList
	 */
	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	DataStructure()
	{
		byXList=new ContainerLinkedList(true);
		byYList=new ContainerLinkedList(false);
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public void addPoint(Point point)
	{
		Container container=new Container(point), other=new Container(point);
		container.setOther(other);
		other.setOther(container);
		byXList.add(container);
		byYList.add(other);
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis)
	{
		if (max<min)
			return new Point[0];
		ContainerLinkedList fromList=axis ? byXList : byYList;
		ListIterator iterator=fromList.iterator();
		int indexMin=100, indexMax=-1;
		Container next=null, prev=null;
//		System.out.print(indexMax-indexMin+1);
		while (iterator.hasNext())
		{
			next=new Container((Point)iterator.next());
			if (next.compareTo(new Point(min, min), axis)>=0)
			{
				indexMin=iterator.nextIndex();
				break;
			}
		}
		while (iterator.hasPrevious())
		{
			prev=new Container((Point)iterator.previous());
			if (prev.compareTo(new Point(max, max), axis)<=0)
			{
				indexMax=iterator.previousIndex();
				break;
			}
		}
		
		Point[] pointsInRange;
		try
		{
			pointsInRange=new Point[indexMax-indexMin+1];
		}
		catch (Exception e)
		{
			return new Point[0];
		}
		if (pointsInRange.length!=0)
		{
			pointsInRange[0]=next!=null ? next.getData() : fromList.getFirst();
			for (int i=1; i<pointsInRange.length-1; i++)
				pointsInRange[i]=iterator.hasNext() ? (Point)iterator.next() : fromList.getLast();
			pointsInRange[pointsInRange.length-1]=prev!=null ? prev.getData() : fromList.getLast();
		}
		return pointsInRange;
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis)
	{
		Point[] pointsInRange=getPointsInRangeRegAxis(min, max, axis);
		ContainerLinkedList fromList=!axis ? byXList : byYList;
		ListIterator iterator=fromList.iterator();
		Comparator<Point> comparator=axis ? new PointCompareX() : new PointCompareY();
		int i=0;
		while (iterator.hasNext())
		{
			Point toAdd=(Point)iterator.next();
			if (comparator.compare(new Point(min, min), toAdd)<=0 && comparator.compare(new Point(max, max), toAdd)>=0)
				pointsInRange[i++]=toAdd;
		}
		return pointsInRange;
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public double getDensity()
	{
		return byXList.size()/((byXList.getLast().getX()-byXList.getFirst().getX())*
		                       (byYList.getLast().getY()-byYList.getFirst().getY()));
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public void narrowRange(int min, int max, Boolean axis)
	{
		ContainerLinkedList fromList=axis ? byXList : byYList;
		ContainerLinkedList otherList=!axis ? byXList : byYList;
		Container next=fromList.getFirstCon(), prev=fromList.getLastCon();
		while (next!=null)
		{
			next=fromList.getFirstCon();
			if (next==null || next.compareTo(new Point(min, min), axis)>=0)
				break;
			otherList.unlink(next.getOther());
			fromList.unlink(next);
		}
//		System.out.println(this);
		
		while (prev!=null)
		{
			prev=fromList.getLastCon();
			if (prev==null || prev.compareTo(new Point(max, max), axis)<=0)
				break;
			otherList.unlink(prev.getOther());
			fromList.unlink(prev);
		}
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public Boolean getLargestAxis()
	{
		return byXList.getLast().getX()-byXList.getFirst().getX()>byYList.getLast().getY()-byYList.getFirst().getY();
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public Container getMedian(Boolean axis)
	{
		return axis ? (Container)byXList.get(byXList.size()/2) : (Container)byYList.get(byYList.size()/2);
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public Point[] nearestPairInStrip(Container container, double width, Boolean axis)
	{
		if (width<2)
			return new Point[0];
		
		//Finds the min' and max' coordinates according to axis.
		int maxCoo=(int)Math.floor(axis ? container.getData().getX()+width/2 : container.getData().getY()+width/2);
		int minCoo=(int)Math.floor(
				(axis ? container.getData().getX()-width/2 : container.getData().getY()-width/2.0)+.5);
		ContainerLinkedList fromList=axis ? byXList : byYList;
		
		//Finds the leftest container.
		Container temp=container;
		while (temp!=null)
		{
			if (temp.compareTo(new Point(minCoo, minCoo), axis)<=0)
				break;
			temp=temp.getPrev();
		}
		if (temp==null)
			temp=fromList.getFirstCon();
		Container minCon=temp;
		
		//Finds the rightest container
		temp=container;
		while (temp!=null)
		{
			if (temp.compareTo(new Point(maxCoo, maxCoo), axis)>=0)
				break;
			temp=temp.getNext();
		}
		if (temp==null)
			temp=fromList.getLastCon();
		Container maxCon=temp;
		
		//Transfers the sub-list between the leftest and rightest containers to array of points.
		ContainerLinkedList subList=new ContainerLinkedList(minCon, maxCon, fromList.isAxis());
		Point[] subPoints;
		if (subList.size()*(Math.log(subList.size())/Math.log(2))<fromList.size())
		{
			subPoints=new Point[subList.size()];
			temp=minCon;
			for (int i=0; i<subPoints.length; i++, temp=temp.getNext())
				subPoints[i]=temp.getData();
			Arrays.sort(subPoints, axis ? new PointCompareY() : new PointCompareX());
		}
		else
		{
			subPoints=new Point[fromList.size()];
			temp=axis ? byYList.getFirstCon() : byXList.getFirstCon();
			for (int i=0; i<fromList.size(); i++, temp=temp.getNext())
				subPoints[i]=temp.getData();
		}
		
		//Calculates the shortest distance between all the points in the strip.
		double shortestDistance=Double.MAX_VALUE;
		Point[] closestPair=new Point[2];
		for (int i=0; i<subPoints.length; i++)
			for (int j=i+1; j<subPoints.length && j<i+7; j++)
			{
				if ((axis ? subPoints[j].getY()-subPoints[i].getY() : subPoints[j].getX()-subPoints[i].getX())>=
				    shortestDistance)
					break;
				if (findDistance(subPoints[i], subPoints[j])<shortestDistance)
				{
					closestPair[0]=subPoints[i];
					closestPair[1]=subPoints[j];
					shortestDistance=findDistance(subPoints[i], subPoints[j]);
				}
			}
		return closestPair;
	}
	
	/**
	 * @param subPoints an array of points.
	 * @param axis the order in which the points are ordered.
	 * @return pair of the closest points.
	 */
	private Point[] nearestPair(Point[] subPoints, boolean axis)
	{
		if (subPoints.length<=3)
			return bruteForce(subPoints);
		
		Point[] leftSubPoints=Arrays.copyOfRange(subPoints, 0, subPoints.length/2);//Creates a sub-array from the first point in subPoints to the point i the middle.
		Point[] rightSubPoints=Arrays.copyOfRange(subPoints, subPoints.length/2, subPoints.length);//Creates a sub-array of points from the point in middle of subPoints to the last point in subPoints.
		
		Point[] closestPair=nearestPair(leftSubPoints, axis);
		Point[] closestPairRight=nearestPair(rightSubPoints, axis);
		
		if (findDistance(closestPairRight[0], closestPairRight[1])<findDistance(closestPair[0], closestPair[1]))//Finds the closest pair of points between the right half and the left half of the array.
			closestPair=closestPairRight;
		
		double shortestDistance=findDistance(closestPair[0], closestPair[1]);//calculates the distance if the closest pair of points.
		
		Point[] nearestInStrip=nearestPairInStrip(getMedian(axis), shortestDistance*2, axis);
		if (findDistance(nearestInStrip[0], nearestInStrip[1])<shortestDistance)
			return nearestInStrip;
		return closestPair;
	}
	
	/**
	 * Finds the closest points by brute force.
	 * @param points an array of points.
	 * @return an array of the pair of the closest points in the array {@code points}.
	 */
	private static Point[] bruteForce(Point[] points)
	{
		if (points==null || points.length<2)
			return null;
		Point[] pair=new Point[]{points[0], points[1]};
		if (points.length>2)
			for (int i=0; i<points.length-1; i++)
				for (int j=i+1; j<points.length; j++)
					if (findDistance(points[i], points[j])<findDistance(pair[0], pair[1]))
					{
						pair[0]=points[i];
						pair[1]=points[j];
					}
		return pair;
	}
	
	/**
	 * @param point1 a point
	 * @param point2 a point
	 * @return calculates the distance of those two points, if {@code point1==point2} returns {@code Double.MAX_VALUE}.
	 */
	static double findDistance(Point point1, Point point2)
	{
		double dist=Math.sqrt(Math.pow(point1.getX()-point2.getX(), 2)+Math.pow(point1.getY()-point2.getY(), 2));
		return dist!=0 ? dist : Double.MAX_VALUE;
	}
	
	/**
	 * specified if the pdf description file of Assignment 2.
	 */
	@Override
	public Point[] nearestPair()
	{
		return nearestPair(getPointsInRangeRegAxis(Integer.MIN_VALUE, Integer.MAX_VALUE, true), true);
	}
	
	public String toString()
	{
		return "byXList: "+byXList+"\nbyYList: "+byYList;
	}
}