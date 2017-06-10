/**
 * Acts like a {@code Node} in {@code LinkedList}
 * @see java.util.LinkedList.Node
 * @see java.util.LinkedList
 */
//Don't change the class name
public class Container
{
	//--------------------fields--------------------
	private Point data;//Don't delete or change this field;
	private Container next, prev, other;
	
	//--------------------constructors--------------------
	
	/**
	 * Constructs a Container with specified data and next Container.
	 * @param data holds a point.
	 * @param next the next container.
	 * @param prev the previous container.
	 * @see Point
	 */
	Container(Point data, Container next, Container prev)
	{
		this.data=data;
		this.next=next;
		this.prev=prev;
	}
	
	/**
	 * Constructs a Container with no previous and next containers.
	 * @param data holds a point.
	 * @see Point
	 */
	Container(Point data)
	{
		this(data, null, null);
	}//Constructs a Container with specified data.
	
	//--------------------methods--------------------
	
	/**
	 * @return a point.
	 */
	//Don't delete or change this function
	Point getData()
	{
		return data;
	}
	
	/**
	 * Replaces the data field in another.
	 * @param data the point to replace with.
	 * @return the point before the change.
	 */
	Point setData(Point data)
	{
		Point tmp=getData();
		this.data=data;
		return tmp;
	}
	
	/**
	 * @return the next container.
	 */
	Container getNext()
	{
		return next;
	}
	
	/**
	 * Replaces the next container with a new one.
	 * @param next the container that replacing the old one.
	 */
	void setNext(Container next)
	{
		this.next=next;
	}
	
	/**
	 * @return the previous container.
	 */
	Container getPrev()
	{
		return prev;
	}//Returns the next Container
	
	/**
	 * Replaces the previous container with a new one.
	 * @param prev the container that replacing the old one.
	 */
	void setPrev(Container prev)
	{
		this.prev=prev;
	}
	
	/**
	 * @return the other container in the list that arranged according to the opposite axis.
	 */
	Container getOther()
	{
		return other;
	}
	
	/**
	 * Replaces the other container with a new one.
	 * @param other the container that replacing the old one.
	 */
	void setOther(Container other)
	{
		this.other=other;
	}
	
	public String toString()
	{
		return getData().toString();
	}
	
	public boolean equals(Object other)
	{
		return other instanceof Container && getData().equals(((Container)other).getData());
	}
	
	/**
	 * compares this container with another by axis.
	 * @param p another container to be compared with.
	 * @param axis if {@code true} the x coordinates are compared, else, the y coordinates are compared
	 * @return 1 if this point's coordinate is bigger then p's, 0 if this point's coordinate is equals p's and -1 otherwise.
	 */
	int compareTo(Point p, boolean axis)
	{
//		Comparator<Point> comparator=axis ? new PointCompareX() : new PointCompareY();
//		return comparator.compare(getData(), p);
		if (axis)
		{
			if (getData().getX()<p.getX())
				return -1;
			if (getData().getX()==p.getX())
				return 0;
		}
		else
		{
			if (getData().getY()<p.getY())
				return -1;
			if (getData().getY()==p.getY())
				return 0;
		}
		return 1;
	}
}