package com.mycompany.a2;

/*
 * This interface is used for the collection class.
 * It  provides the functions, add() and getIterator()
 */
public interface ICollection {
	
	public void add(Object newObject);
	public IIterator getIterator();

}
