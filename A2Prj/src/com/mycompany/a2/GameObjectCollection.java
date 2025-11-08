 package com.mycompany.a2;
import java.util.Vector;

/*
 * This class implements a collection of GameObjects
 * All game objects are contained in this single collection.
 * Accessed via an iterator (IIterator)
 */

public class GameObjectCollection implements ICollection {
	private Vector<GameObject> theCollection;
	
	public GameObjectCollection() {
		theCollection = new Vector<>();
	}
	
	@Override
	public void add(Object newObject) {
		theCollection.add((GameObject)newObject);
		
	}

	@Override
	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	/*
	 * Clears all objects in the container
	 */
	public void clear() {
	    theCollection.removeAllElements();
	}

	
	/*
	 * Start of private iterator class
	 */
	private class GameObjectIterator implements IIterator {
		
		private int currElementIndex;
		
		public GameObjectIterator() {
			currElementIndex = -1;
		}

		@Override
		public boolean hasNext() {
			if (theCollection.size ( ) <= 0) return false;
			if (currElementIndex == theCollection.size()-1)
				return false;
			return true;
		}

		@Override
		public GameObject getNext() {
			currElementIndex++;
			return(theCollection.elementAt(currElementIndex));
		}
		
	}

}
