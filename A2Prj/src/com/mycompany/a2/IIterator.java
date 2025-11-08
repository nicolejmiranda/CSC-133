package com.mycompany.a2;

/*
 * This interface provides the 
 * general functions for Iterators.
 */
public interface IIterator {
	/*
	 * Returns true if there are more elements to process
	 */
	public boolean hasNext();
	
	/*
	 * Returns the next element in the collection
	 */
	public Object getNext();
}
