import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedListIterator implements ListIterator
{
	private Container nextLink, prevLink;
	private int nextIndex, previousIndex;
	
	/**
	 * Creates a new  LinkedListIterator Object.
	 * @param first the first container of the list.
	 * @param last the last container of the list.
	 * @param size the size of the list.
	 */
	LinkedListIterator(Container first, Container last, int size)
	{
		nextLink=first;
		prevLink=last;
		nextIndex=-1;
		previousIndex=size;
	}
	
	/**
	 * Returns {@code true} if this list iterator has more elements when
	 * traversing the list in the forward direction. (In other words,
	 * returns {@code true} if {@link #next} would return an element rather
	 * than throwing an exception.)
	 * @return {@code true} if the list iterator has more elements when
	 * traversing the list in the forward direction.
	 */
	@Override
	public boolean hasNext()
	{
		return nextLink!=null/* && has()*/;
	}//Checks if there's more links left
	
	public boolean hasPrevious()
	{
		return prevLink!=null/* && has()*/;
	}
	
	/**
	 * Returns the next element in the list and advances the cursor position.
	 * This method may be called repeatedly to iterate through the list.
	 * @return the next element in the list.
	 * @throws NoSuchElementException if the iteration has no next element.
	 */
	@Override
	public Point next()
	{
		if (!hasNext())
			throw new NoSuchElementException();
		
		Point nextElement=nextLink.getData();
		nextLink=nextLink.getNext();
		nextIndex++;
		return nextElement;
	}
	
	/**
	 * Returns the previous element in the list (from the end of the list) and advances the cursor position.
	 * This method may be called repeatedly to iterate through the list.
	 * @return the next element in the list.
	 * @throws NoSuchElementException if the iteration has no next element.
	 */
	public Point previous()
	{
		if (!hasPrevious())
			throw new NoSuchElementException();
		
		Point prevElement=prevLink.getData();
		prevLink=prevLink.getPrev();
		previousIndex--;
		return prevElement;
	}
	
	/**
	 * Returns the index of the element that would be returned by a
	 * subsequent call to {@link #next}. (Returns list size if the list
	 * iterator is at the end of the list.)
	 * @return the index of the element that would be returned by a
	 * subsequent call to {@code next}, or list size if the list
	 * iterator is at the end of the list
	 */
	@Override
	public int nextIndex()
	{
		return nextIndex;
	}
	
	/**
	 * Returns the index of the element that would be returned by a
	 * subsequent call to {@link #previous}. (Returns -1 if the list
	 * iterator is at the beginning of the list.)
	 * @return the index of the element that would be returned by a
	 * subsequent call to {@code previous}, or -1 if the list
	 * iterator is at the beginning of the list
	 */
	@Override
	public int previousIndex()
	{
		return previousIndex;
	}
	
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Replaces the last element returned by {@link #next} or
	 * {@link #previous} with the specified element (optional operation).
	 * This call can be made only if neither {@link #remove} nor {@link
	 * #add} have been called after the last call to {@code next} or
	 * {@code previous}.
	 * @param o the element with which to replace the last element returned by
	 * {@code next} or {@code previous}
	 * @throws UnsupportedOperationException if the {@code set} operation
	 * is not supported by this list iterator
	 * @throws ClassCastException if the class of the specified element
	 * prevents it from being added to this list
	 * @throws IllegalArgumentException if some aspect of the specified
	 * element prevents it from being added to this list
	 * @throws IllegalStateException if neither {@code next} nor
	 * {@code previous} have been called, or {@code remove} or
	 * {@code add} have been called after the last call to
	 * {@code next} or {@code previous}
	 */
	@Override
	public void set(Object o)
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Inserts the specified element into the list (optional operation).
	 * The element is inserted immediately before the element that
	 * would be returned by {@link #next}, if any, and after the element
	 * that would be returned by {@link #previous}, if any.  (If the
	 * list contains no elements, the new element becomes the sole element
	 * on the list.)  The new element is inserted before the implicit
	 * cursor: a subsequent call to {@code next} would be unaffected, and a
	 * subsequent call to {@code previous} would return the new element.
	 * (This call increases by one the value that would be returned by a
	 * call to {@code nextIndex} or {@code previousIndex}.)
	 * @param o the element to insert
	 * @throws UnsupportedOperationException if the {@code add} method is
	 * not supported by this list iterator
	 * @throws ClassCastException if the class of the specified element
	 * prevents it from being added to this list
	 * @throws IllegalArgumentException if some aspect of this element
	 * prevents it from being added to this list
	 */
	@Override
	public void add(Object o)
	{
		throw new UnsupportedOperationException();
	}
}