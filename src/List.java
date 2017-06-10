public interface List extends Iterable
{
	// returns the number of elements in the list
	int size();
	
	// returns true if the list is empty
	boolean isEmpty();
	
	// Returns true if this list contains the specified element.
	boolean contains(Object element);
	
	// Replaces the element at the specified position in this list with the specified element.
	Object set(int index, Object element);
	
	// Returns the element at the specified position in this list.
	Object get(int index);
	
	// Appends the specified element to the end of this list.
	void add(Object element);
	
	// Inserts the specified element at the specified position in this list.
	void add(int index, Object element);
}