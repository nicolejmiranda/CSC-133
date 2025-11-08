package com.mycompany.a2;
import com.codename1.charts.models.Point;

/**
 * This class defines GameObjects that are "Fixed".
 * Fixed objects cannot change locations once instantiated.
 */
public abstract class Fixed extends GameObject {

	// Constructor
	public Fixed(int size, Point location, int color ) {
		super(size, location, color);
	}
	
	// Setter
	
	/**
	 * Overloads setLocation() since fixed objects cannot change location
	 */
	public void setLocation(Point newLocation) {}

}
