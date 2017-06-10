import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Based on {@code java.util.LinkedList} and adapted especially for this assignment.
 * @see java.util.LinkedList
 */
public class ContainerLinkedList implements List
{
	//--------------------fields--------------------
	private Container first, last;
	private int size;
	private final boolean axis;
	
	//--------------------constructors--------------------
	/**
	 * constructs a new Container-list with no Containers.
	 * @param axis The order of the points: {@code true} for the points to be arranged by x coordinate and {@code false} for the points to be arranged by y coordinate.
	 */
	ContainerLinkedList(boolean axis)
	{
		this.axis=axis;
	}
	
	/**
	 * Constructs a sub-list.
	 * @param first The first container of the list.
	 * @param last The first container of the list.
	 * @param axis The order of the points: {@code true} for the points to be arranged by x coordinate and {@code false} for the points to be arranged by y coordinate.
	 */
	ContainerLinkedList(Container first, Container last, boolean axis)
	{
		if ((first==null && last!=null) || (first!=null && last==null))
			throw new IllegalArgumentException("first="+first+", last="+last);
		this.first=first;
		this.last=last;
		this.axis=axis;
		for (Container temp=first; temp!=last && temp!=null; temp=temp.getNext())
			size++;
		if (first!=null)
			size++;
	}
	
	//--------------------methods--------------------
	/**
	 * Returns the Container list's description.
	 */
	public String toString()
	{
		StringBuilder output=new StringBuilder("[");
		Container current=first;
		while (current!=last)
		{
			output.append(current).append(", ");
			current=current.getNext();
		}
		return output.append(current).append("]").toString();
	}
	
	/**
	 * Checks if this list is equals to other's list.
	 * @param other a list.
	 * @return If this list is equal to the {@code other} list.
	 */
	public boolean equals(Object other)
	{
		if (!(other instanceof ContainerLinkedList) || size()!=((ContainerLinkedList)other).size())
			return false;
		ListIterator thisI=iterator(), otherI=((ContainerLinkedList)other).iterator();
		while (thisI.hasNext())
			if (!thisI.next().equals(otherI.next()))
				return false;
		return true;
	}
	
	/**
	 * Adds a new {@link Container} to the Container list after a given index found by {@link #searchFor(Container)}.
	 */
	@Override
	public void add(int index, Object element)
	{
		if (!isEmpty())
			rangeCheck(index);
		nullCheck(element);
		if (!(element instanceof Point) && !(element instanceof Container))
			throw new IllegalArgumentException("Not an instance of Point or Container.");
		Container prev=null;
		Container curr=first;
		for (int i=0; i<index; i++)
		{
			prev=curr;
			curr=curr.getNext();
		}
		Container toAdd;
		if (element instanceof Point)
			toAdd=new Container((Point)element, curr, prev);
		else
		{
			toAdd=(Container)element;
			toAdd.setNext(curr);
			toAdd.setPrev(prev);
		}
		if (prev!=null)
			prev.setNext(toAdd);
		else//if index==0
			first=toAdd;
		if (curr!=null)
			curr.setPrev(toAdd);
		if (index==size())
			last=toAdd;
		size++;
	}
	
	/**
	 * Adds a new {@link Container} to the Container list by calling {@link #add(int, Object)}.
	 * @param element the element to add.
	 */
	@Override
	public void add(Object element) throws IllegalArgumentException
	{
		nullCheck(element);
		if (!(element instanceof Point) && !(element instanceof Container))
			throw new IllegalArgumentException("Not an instance of Point or Container.");
		add(searchFor(element instanceof Point ? new Container((Point)element) : (Container)element), element);
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element in this list.
	 * @param element the element to search for.
	 * @return the index of the first occurrence of the specified element in this list.
	 */
	private int searchFor(Container element)
	{
		Container con=first;
		int counter=0;
		while (con!=null)
			if (element.compareTo(con.getData(), isAxis())<0)
				return counter;
			else
			{
				con=con.getNext();
				counter++;
			}
		return counter;
	}
	
	/**
	 * Unlinks a container.
	 * @param x the container to be unlinked.
	 */
	void unlink(Container x)
	{
//		if (x.getData()==null)
//			return null;
//		final Point element=x.getData();
		final Container next=x.getNext(), prev=x.getPrev();
		
		if (prev==null)
			first=next;
		else
		{
			prev.setNext(next);
			x.setPrev(null);
		}
		
		if (next==null)
			last=prev;
		else
		{
			next.setPrev(prev);
			x.setNext(null);
		}

//		x.setOther(null);
//		x.setData(null);
		size--;
	}
	
	/**
	 * @return The size of the list.
	 */
	@Override
	public int size()
	{
		return size;
	}
	
	/**
	 * @param element element whose presence in this list is to be tested.
	 * @return {@code true} if this list contains the specified element.
	 */
	@Override
	public boolean contains(Object element)
	{
		for (Object d : this)
			if (d.equals(element))
				return true;
		return false;
	}
	
	/**
	 * @return {@code true} if this list contains no elements.
	 */
	@Override
	public boolean isEmpty()
	{
		return first==null && last==null;
	}
	
	/**
	 * @return the first point in this list.
	 * @throws NoSuchElementException if this list is empty.
	 */
	Point getFirst()
	{
		if (isEmpty())
			throw new NoSuchElementException();
		return first.getData();
	}
	
	/**
	 * @return the first container in this list.
	 */
	Container getFirstCon()
	{
		return first;
	}
	
	/**
	 * @return the last point in this list.
	 * @throws java.util.NoSuchElementException if this list is empty.
	 */
	Point getLast()
	{
		if (isEmpty())
			throw new NoSuchElementException();
		return last.getData();
	}
	
	/**
	 * @return the last container in this list.
	 */
	Container getLastCon()
	{
		return last;
	}
	
	/**
	 * @return {@code true} if the points in this list are sorted by x coordinate or {@code false} otherwise.
	 */
	boolean isAxis()
	{
		return axis;
	}
	
	/**
	 * Returns the element at the specified position in this list.
	 * @param index index of the element to return.
	 * @return the element at the specified position in this list.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	@Override
	public Object get(int index)
	{
		rangeCheck(index);
		Container temp=first;
		for (int i=0; i<index; i++)
			temp=temp.getNext();
		return temp;
	}
	
	/**
	 * Replaces the element at the specified position in this list with the
	 * specified element.
	 * @param index index of the element to replace.
	 * @param element element to be stored at the specified position.
	 * @return the element previously at the specified position.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	@Override
	public Object set(int index, Object element)
	{
		rangeCheck(index);
		nullCheck(element);
		if (!(element instanceof Point))
			throw new IllegalArgumentException("Not instance of Point.");
		Container temp=first;
		for (int i=0; i<index; i++)
			temp=temp.getNext();
		return temp.setData((Point)element);
	}
	
	/**
	 * Returns a list-iterator of the elements in this list (in proper
	 * sequence), starting at the specified position in the list.
	 * @return a ListIterator of the elements in this list (in proper
	 * sequence), starting at the specified position in the list.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 * @see LinkedListIterator
	 */
	@Override
	public ListIterator iterator()
	{
		return new LinkedListIterator(first, last, size());
	}
	
	/**
	 * @param index the element's index to be checked.
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	private void rangeCheck(int index)
	{
		if (index<0 || index>size())
			throw new IndexOutOfBoundsException("Index: "+index+", Size: "+size());
	}
	
	/**
	 *
	 * @param element the element in question.
	 * @throws NullPointerException if the given element is null.
	 */
	private void nullCheck(Object element)
	{
		if (element==null)
			throw new NullPointerException();
	}
}